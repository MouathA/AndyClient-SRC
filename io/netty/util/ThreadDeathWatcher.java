package io.netty.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.*;
import java.util.*;

public final class ThreadDeathWatcher
{
    private static final InternalLogger logger;
    private static final ThreadFactory threadFactory;
    private static final Queue pendingEntries;
    private static final Watcher watcher;
    private static final AtomicBoolean started;
    private static Thread watcherThread;
    
    public static void watch(final Thread thread, final Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        }
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        if (!thread.isAlive()) {
            throw new IllegalArgumentException("thread must be alive.");
        }
        schedule(thread, runnable, true);
    }
    
    public static void unwatch(final Thread thread, final Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        }
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        schedule(thread, runnable, false);
    }
    
    private static void schedule(final Thread thread, final Runnable runnable, final boolean b) {
        ThreadDeathWatcher.pendingEntries.add(new Entry(thread, runnable, b));
        if (ThreadDeathWatcher.started.compareAndSet(false, true)) {
            final Thread thread2 = ThreadDeathWatcher.threadFactory.newThread(ThreadDeathWatcher.watcher);
            thread2.start();
            ThreadDeathWatcher.watcherThread = thread2;
        }
    }
    
    public static boolean awaitInactivity(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        final Thread watcherThread = ThreadDeathWatcher.watcherThread;
        if (watcherThread != null) {
            watcherThread.join(timeUnit.toMillis(n));
            return !watcherThread.isAlive();
        }
        return true;
    }
    
    private ThreadDeathWatcher() {
    }
    
    static Queue access$100() {
        return ThreadDeathWatcher.pendingEntries;
    }
    
    static AtomicBoolean access$200() {
        return ThreadDeathWatcher.started;
    }
    
    static InternalLogger access$300() {
        return ThreadDeathWatcher.logger;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
        threadFactory = new DefaultThreadFactory(ThreadDeathWatcher.class, true, 1);
        pendingEntries = PlatformDependent.newMpscQueue();
        watcher = new Watcher(null);
        started = new AtomicBoolean();
    }
    
    private static final class Entry extends MpscLinkedQueueNode
    {
        final Thread thread;
        final Runnable task;
        final boolean isWatch;
        
        Entry(final Thread thread, final Runnable task, final boolean isWatch) {
            this.thread = thread;
            this.task = task;
            this.isWatch = isWatch;
        }
        
        @Override
        public Entry value() {
            return this;
        }
        
        @Override
        public int hashCode() {
            return this.thread.hashCode() ^ this.task.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            return this.thread == entry.thread && this.task == entry.task;
        }
        
        @Override
        public Object value() {
            return this.value();
        }
    }
    
    private static final class Watcher implements Runnable
    {
        private final List watchees;
        static final boolean $assertionsDisabled;
        
        private Watcher() {
            this.watchees = new ArrayList();
        }
        
        @Override
        public void run() {
            while (true) {
                this.fetchWatchees();
                this.notifyWatchees();
                this.fetchWatchees();
                this.notifyWatchees();
                Thread.sleep(1000L);
                if (this.watchees.isEmpty() && ThreadDeathWatcher.access$100().isEmpty()) {
                    final boolean compareAndSet = ThreadDeathWatcher.access$200().compareAndSet(true, false);
                    assert compareAndSet;
                    if (ThreadDeathWatcher.access$100().isEmpty()) {
                        break;
                    }
                    if (!ThreadDeathWatcher.access$200().compareAndSet(false, true)) {
                        break;
                    }
                    continue;
                }
            }
        }
        
        private void fetchWatchees() {
            while (true) {
                final Entry entry = ThreadDeathWatcher.access$100().poll();
                if (entry == null) {
                    break;
                }
                if (entry.isWatch) {
                    this.watchees.add(entry);
                }
                else {
                    this.watchees.remove(entry);
                }
            }
        }
        
        private void notifyWatchees() {
            final List watchees = this.watchees;
            while (0 < watchees.size()) {
                final Entry entry = watchees.get(0);
                if (!entry.thread.isAlive()) {
                    watchees.remove(0);
                    entry.task.run();
                }
                else {
                    int n = 0;
                    ++n;
                }
            }
        }
        
        Watcher(final ThreadDeathWatcher$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !ThreadDeathWatcher.class.desiredAssertionStatus();
        }
    }
}
