package org.apache.commons.io.monitor;

import java.util.concurrent.*;
import java.util.*;

public final class FileAlterationMonitor implements Runnable
{
    private final long interval;
    private final List observers;
    private Thread thread;
    private ThreadFactory threadFactory;
    private boolean running;
    
    public FileAlterationMonitor() {
        this(10000L);
    }
    
    public FileAlterationMonitor(final long interval) {
        this.observers = new CopyOnWriteArrayList();
        this.thread = null;
        this.running = false;
        this.interval = interval;
    }
    
    public FileAlterationMonitor(final long n, final FileAlterationObserver... array) {
        this(n);
        if (array != null) {
            while (0 < array.length) {
                this.addObserver(array[0]);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public long getInterval() {
        return this.interval;
    }
    
    public synchronized void setThreadFactory(final ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }
    
    public void addObserver(final FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            this.observers.add(fileAlterationObserver);
        }
    }
    
    public void removeObserver(final FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            while (this.observers.remove(fileAlterationObserver)) {}
        }
    }
    
    public Iterable getObservers() {
        return this.observers;
    }
    
    public synchronized void start() throws Exception {
        if (this.running) {
            throw new IllegalStateException("Monitor is already running");
        }
        final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().initialize();
        }
        this.running = true;
        if (this.threadFactory != null) {
            this.thread = this.threadFactory.newThread(this);
        }
        else {
            this.thread = new Thread(this);
        }
        this.thread.start();
    }
    
    public synchronized void stop() throws Exception {
        this.stop(this.interval);
    }
    
    public synchronized void stop(final long n) throws Exception {
        if (!this.running) {
            throw new IllegalStateException("Monitor is not running");
        }
        this.running = false;
        this.thread.join(n);
        final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().destroy();
        }
    }
    
    @Override
    public void run() {
        while (this.running) {
            final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().checkAndNotify();
            }
            if (!this.running) {
                break;
            }
            Thread.sleep(this.interval);
        }
    }
}
