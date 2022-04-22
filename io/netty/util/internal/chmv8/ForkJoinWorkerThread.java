package io.netty.util.internal.chmv8;

public class ForkJoinWorkerThread extends Thread
{
    final ForkJoinPool pool;
    final ForkJoinPool.WorkQueue workQueue;
    
    protected ForkJoinWorkerThread(final ForkJoinPool pool) {
        super("aForkJoinWorkerThread");
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }
    
    public ForkJoinPool getPool() {
        return this.pool;
    }
    
    public int getPoolIndex() {
        return this.workQueue.poolIndex >>> 1;
    }
    
    protected void onStart() {
    }
    
    protected void onTermination(final Throwable t) {
    }
    
    @Override
    public void run() {
        final Throwable t = null;
        this.onStart();
        this.pool.runWorker(this.workQueue);
        this.onTermination(t);
        this.pool.deregisterWorker(this, t);
    }
}
