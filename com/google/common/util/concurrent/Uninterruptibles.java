package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;

@Beta
public final class Uninterruptibles
{
    public static void awaitUninterruptibly(final CountDownLatch countDownLatch) {
        countDownLatch.await();
        if (true) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static boolean awaitUninterruptibly(final CountDownLatch countDownLatch, final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        final long n2 = System.nanoTime() + nanos;
        final boolean await = countDownLatch.await(nanos, TimeUnit.NANOSECONDS);
        if (true) {
            Thread.currentThread().interrupt();
        }
        return await;
    }
    
    public static void joinUninterruptibly(final Thread thread) {
        thread.join();
        if (true) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static Object getUninterruptibly(final Future future) throws ExecutionException {
        final Object value = future.get();
        if (true) {
            Thread.currentThread().interrupt();
        }
        return value;
    }
    
    public static Object getUninterruptibly(final Future future, final long n, final TimeUnit timeUnit) throws ExecutionException, TimeoutException {
        final long nanos = timeUnit.toNanos(n);
        final long n2 = System.nanoTime() + nanos;
        final Object value = future.get(nanos, TimeUnit.NANOSECONDS);
        if (true) {
            Thread.currentThread().interrupt();
        }
        return value;
    }
    
    public static void joinUninterruptibly(final Thread thread, final long n, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(thread);
        final long nanos = timeUnit.toNanos(n);
        final long n2 = System.nanoTime() + nanos;
        TimeUnit.NANOSECONDS.timedJoin(thread, nanos);
        if (true) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static Object takeUninterruptibly(final BlockingQueue blockingQueue) {
        final Object take = blockingQueue.take();
        if (true) {
            Thread.currentThread().interrupt();
        }
        return take;
    }
    
    public static void putUninterruptibly(final BlockingQueue blockingQueue, final Object o) {
        blockingQueue.put(o);
        if (true) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void sleepUninterruptibly(final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        final long n2 = System.nanoTime() + nanos;
        TimeUnit.NANOSECONDS.sleep(nanos);
        if (true) {
            Thread.currentThread().interrupt();
        }
    }
    
    private Uninterruptibles() {
    }
}
