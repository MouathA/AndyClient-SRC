package net.minecraft.world.storage;

import com.google.common.collect.*;
import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    private static final ThreadedFileIOBase threadedIOInstance;
    private List threadedIOQueue;
    private long writeQueuedCounter;
    private long savedIOCounter;
    private boolean isThreadWaiting;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000605";
        threadedIOInstance = new ThreadedFileIOBase();
    }
    
    private ThreadedFileIOBase() {
        this.threadedIOQueue = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        final Thread thread = new Thread(this, "File IO Thread");
        thread.setPriority(1);
        thread.start();
    }
    
    public static ThreadedFileIOBase func_178779_a() {
        return ThreadedFileIOBase.threadedIOInstance;
    }
    
    @Override
    public void run() {
        while (true) {
            this.processQueue();
        }
    }
    
    private void processQueue() {
        while (0 < this.threadedIOQueue.size()) {
            int n2 = 0;
            if (!this.threadedIOQueue.get(0).writeNextIO()) {
                final List threadedIOQueue = this.threadedIOQueue;
                final int n = 0;
                --n2;
                threadedIOQueue.remove(n);
                ++this.savedIOCounter;
            }
            Thread.sleep(this.isThreadWaiting ? 0L : 10L);
            ++n2;
        }
        if (this.threadedIOQueue.isEmpty()) {
            Thread.sleep(25L);
        }
    }
    
    public void queueIO(final IThreadedFileIO threadedFileIO) {
        if (!this.threadedIOQueue.contains(threadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(threadedFileIO);
        }
    }
    
    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }
        this.isThreadWaiting = false;
    }
    
    public static ThreadedFileIOBase getThreadedIOInstance() {
        return ThreadedFileIOBase.threadedIOInstance;
    }
}
