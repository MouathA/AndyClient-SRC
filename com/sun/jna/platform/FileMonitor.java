package com.sun.jna.platform;

import java.io.*;
import com.sun.jna.platform.win32.*;
import java.util.*;

public abstract class FileMonitor
{
    public static final int FILE_CREATED = 1;
    public static final int FILE_DELETED = 2;
    public static final int FILE_MODIFIED = 4;
    public static final int FILE_ACCESSED = 8;
    public static final int FILE_NAME_CHANGED_OLD = 16;
    public static final int FILE_NAME_CHANGED_NEW = 32;
    public static final int FILE_RENAMED = 48;
    public static final int FILE_SIZE_CHANGED = 64;
    public static final int FILE_ATTRIBUTES_CHANGED = 128;
    public static final int FILE_SECURITY_CHANGED = 256;
    public static final int FILE_ANY = 511;
    private final Map watched;
    private List listeners;
    
    public FileMonitor() {
        this.watched = new HashMap();
        this.listeners = new ArrayList();
    }
    
    protected abstract void watch(final File p0, final int p1, final boolean p2) throws IOException;
    
    protected abstract void unwatch(final File p0);
    
    public abstract void dispose();
    
    public void addWatch(final File file) throws IOException {
        this.addWatch(file, 511);
    }
    
    public void addWatch(final File file, final int n) throws IOException {
        this.addWatch(file, n, file.isDirectory());
    }
    
    public void addWatch(final File file, final int n, final boolean b) throws IOException {
        this.watched.put(file, new Integer(n));
        this.watch(file, n, b);
    }
    
    public void removeWatch(final File file) {
        if (this.watched.remove(file) != null) {
            this.unwatch(file);
        }
    }
    
    protected void notify(final FileEvent fileEvent) {
        final Iterator<FileListener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().fileChanged(fileEvent);
        }
    }
    
    public synchronized void addFileListener(final FileListener fileListener) {
        final ArrayList<FileListener> listeners = new ArrayList<FileListener>(this.listeners);
        listeners.add(fileListener);
        this.listeners = listeners;
    }
    
    public synchronized void removeFileListener(final FileListener fileListener) {
        final ArrayList listeners = new ArrayList(this.listeners);
        listeners.remove(fileListener);
        this.listeners = listeners;
    }
    
    @Override
    protected void finalize() {
        final Iterator<File> iterator = this.watched.keySet().iterator();
        while (iterator.hasNext()) {
            this.removeWatch(iterator.next());
        }
        this.dispose();
    }
    
    public static FileMonitor getInstance() {
        return Holder.INSTANCE;
    }
    
    private static class Holder
    {
        public static final FileMonitor INSTANCE;
        
        static {
            final String property = System.getProperty("os.name");
            if (property.startsWith("Windows")) {
                INSTANCE = new W32FileMonitor();
                return;
            }
            throw new Error("FileMonitor not implemented for " + property);
        }
    }
    
    public class FileEvent extends EventObject
    {
        private final File file;
        private final int type;
        final FileMonitor this$0;
        
        public FileEvent(final FileMonitor this$0, final File file, final int type) {
            super(this.this$0 = this$0);
            this.file = file;
            this.type = type;
        }
        
        public File getFile() {
            return this.file;
        }
        
        public int getType() {
            return this.type;
        }
        
        @Override
        public String toString() {
            return "FileEvent: " + this.file + ":" + this.type;
        }
    }
    
    public interface FileListener
    {
        void fileChanged(final FileEvent p0);
    }
}
