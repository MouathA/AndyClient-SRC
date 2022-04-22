package org.apache.commons.io;

import java.util.*;
import java.io.*;
import java.lang.ref.*;

public class FileCleaningTracker
{
    ReferenceQueue q;
    final Collection trackers;
    final List deleteFailures;
    boolean exitWhenFinished;
    Thread reaper;
    
    public FileCleaningTracker() {
        this.q = new ReferenceQueue();
        this.trackers = Collections.synchronizedSet(new HashSet<Object>());
        this.deleteFailures = Collections.synchronizedList(new ArrayList<Object>());
        this.exitWhenFinished = false;
    }
    
    public void track(final File file, final Object o) {
        this.track(file, o, null);
    }
    
    public void track(final File file, final Object o, final FileDeleteStrategy fileDeleteStrategy) {
        if (file == null) {
            throw new NullPointerException("The file must not be null");
        }
        this.addTracker(file.getPath(), o, fileDeleteStrategy);
    }
    
    public void track(final String s, final Object o) {
        this.track(s, o, null);
    }
    
    public void track(final String s, final Object o, final FileDeleteStrategy fileDeleteStrategy) {
        if (s == null) {
            throw new NullPointerException("The path must not be null");
        }
        this.addTracker(s, o, fileDeleteStrategy);
    }
    
    private synchronized void addTracker(final String s, final Object o, final FileDeleteStrategy fileDeleteStrategy) {
        if (this.exitWhenFinished) {
            throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
        }
        if (this.reaper == null) {
            (this.reaper = new Reaper()).start();
        }
        this.trackers.add(new Tracker(s, fileDeleteStrategy, o, this.q));
    }
    
    public int getTrackCount() {
        return this.trackers.size();
    }
    
    public List getDeleteFailures() {
        return this.deleteFailures;
    }
    
    public synchronized void exitWhenFinished() {
        this.exitWhenFinished = true;
        if (this.reaper != null) {
            // monitorenter(reaper = this.reaper)
            this.reaper.interrupt();
        }
        // monitorexit(reaper)
    }
    
    private static final class Tracker extends PhantomReference
    {
        private final String path;
        private final FileDeleteStrategy deleteStrategy;
        
        Tracker(final String path, final FileDeleteStrategy fileDeleteStrategy, final Object o, final ReferenceQueue referenceQueue) {
            super(o, referenceQueue);
            this.path = path;
            this.deleteStrategy = ((fileDeleteStrategy == null) ? FileDeleteStrategy.NORMAL : fileDeleteStrategy);
        }
        
        public String getPath() {
            return this.path;
        }
        
        public boolean delete() {
            return this.deleteStrategy.deleteQuietly(new File(this.path));
        }
    }
    
    private final class Reaper extends Thread
    {
        final FileCleaningTracker this$0;
        
        Reaper(final FileCleaningTracker this$0) {
            this.this$0 = this$0;
            super("File Reaper");
            this.setPriority(10);
            this.setDaemon(true);
        }
        
        @Override
        public void run() {
            while (!this.this$0.exitWhenFinished || this.this$0.trackers.size() > 0) {
                final Tracker tracker = (Tracker)this.this$0.q.remove();
                this.this$0.trackers.remove(tracker);
                if (!tracker.delete()) {
                    this.this$0.deleteFailures.add(tracker.getPath());
                }
                tracker.clear();
            }
        }
    }
}
