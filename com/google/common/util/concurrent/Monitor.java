package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.concurrent.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import com.google.common.base.*;

@Beta
public final class Monitor
{
    private final boolean fair;
    private final ReentrantLock lock;
    @GuardedBy("lock")
    private Guard activeGuards;
    
    public Monitor() {
        this(false);
    }
    
    public Monitor(final boolean fair) {
        this.activeGuards = null;
        this.fair = fair;
        this.lock = new ReentrantLock(fair);
    }
    
    public void enter() {
        this.lock.lock();
    }
    
    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }
    
    public boolean enter(final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        final ReentrantLock lock = this.lock;
        if (!this.fair && lock.tryLock()) {
            return true;
        }
        final long n2 = System.nanoTime() + nanos;
        Thread.interrupted();
        final boolean tryLock = lock.tryLock(nanos, TimeUnit.NANOSECONDS);
        if (true) {
            Thread.currentThread().interrupt();
        }
        return tryLock;
    }
    
    public boolean enterInterruptibly(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(n, timeUnit);
    }
    
    public boolean tryEnter() {
        return this.lock.tryLock();
    }
    
    public void enterWhen(final Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean heldByCurrentThread = lock.isHeldByCurrentThread();
        lock.lockInterruptibly();
        if (!guard.isSatisfied()) {
            this.await(guard, heldByCurrentThread);
        }
        if (!true) {
            this.leave();
        }
    }
    
    public void enterWhenUninterruptibly(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean heldByCurrentThread = lock.isHeldByCurrentThread();
        lock.lock();
        if (!guard.isSatisfied()) {
            this.awaitUninterruptibly(guard, heldByCurrentThread);
        }
        if (!true) {
            this.leave();
        }
    }
    
    public boolean enterWhen(final Guard guard, final long n, final TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(n);
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean heldByCurrentThread = lock.isHeldByCurrentThread();
        if (this.fair || !lock.tryLock()) {
            final long n2 = System.nanoTime() + nanos;
            if (!lock.tryLock(n, timeUnit)) {
                return false;
            }
            nanos = n2 - System.nanoTime();
        }
        final boolean b = guard.isSatisfied() || this.awaitNanos(guard, nanos, heldByCurrentThread);
        if (!false) {
            if (false && !heldByCurrentThread) {
                this.signalNextWaiter();
            }
            lock.unlock();
        }
        return false;
    }
    
    public boolean enterWhenUninterruptibly(final Guard guard, final long n, final TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(n);
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final long n2 = System.nanoTime() + nanos;
        lock.isHeldByCurrentThread();
        Thread.interrupted();
        if (this.fair || !lock.tryLock()) {
            do {
                lock.tryLock(nanos, TimeUnit.NANOSECONDS);
                if (!false) {
                    if (true) {
                        Thread.currentThread().interrupt();
                    }
                    return false;
                }
                nanos = n2 - System.nanoTime();
            } while (!false);
        }
        if (guard.isSatisfied() || this.awaitNanos(guard, nanos, false)) {}
        if (!false) {
            lock.unlock();
        }
        if (true) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
    
    public boolean enterIf(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        final boolean satisfied = guard.isSatisfied();
        if (!false) {
            lock.unlock();
        }
        return satisfied;
    }
    
    public boolean enterIfInterruptibly(final Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        final boolean satisfied = guard.isSatisfied();
        if (!false) {
            lock.unlock();
        }
        return satisfied;
    }
    
    public boolean enterIf(final Guard guard, final long n, final TimeUnit timeUnit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.enter(n, timeUnit)) {
            return false;
        }
        final boolean satisfied = guard.isSatisfied();
        if (!false) {
            this.lock.unlock();
        }
        return satisfied;
    }
    
    public boolean enterIfInterruptibly(final Guard guard, final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        if (!lock.tryLock(n, timeUnit)) {
            return false;
        }
        final boolean satisfied = guard.isSatisfied();
        if (!false) {
            lock.unlock();
        }
        return satisfied;
    }
    
    public boolean tryEnterIf(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        if (!lock.tryLock()) {
            return false;
        }
        final boolean satisfied = guard.isSatisfied();
        if (!false) {
            lock.unlock();
        }
        return satisfied;
    }
    
    public void waitFor(final Guard guard) throws InterruptedException {
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (!guard.isSatisfied()) {
            this.await(guard, true);
        }
    }
    
    public void waitForUninterruptibly(final Guard guard) {
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (!guard.isSatisfied()) {
            this.awaitUninterruptibly(guard, true);
        }
    }
    
    public boolean waitFor(final Guard guard, final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long nanos = timeUnit.toNanos(n);
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        return guard.isSatisfied() || this.awaitNanos(guard, nanos, true);
    }
    
    public boolean waitForUninterruptibly(final Guard guard, final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return true;
        }
        final long n2 = System.nanoTime() + nanos;
        Thread.interrupted();
        final boolean awaitNanos = this.awaitNanos(guard, nanos, false);
        if (true) {
            Thread.currentThread().interrupt();
        }
        return awaitNanos;
    }
    
    public void leave() {
        final ReentrantLock lock = this.lock;
        if (lock.getHoldCount() == 1) {
            this.signalNextWaiter();
        }
        lock.unlock();
    }
    
    public boolean isFair() {
        return this.fair;
    }
    
    public boolean isOccupied() {
        return this.lock.isLocked();
    }
    
    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }
    
    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }
    
    public int getQueueLength() {
        return this.lock.getQueueLength();
    }
    
    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }
    
    public boolean hasQueuedThread(final Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }
    
    public boolean hasWaiters(final Guard guard) {
        return this.getWaitQueueLength(guard) > 0;
    }
    
    public int getWaitQueueLength(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        final int waiterCount = guard.waiterCount;
        this.lock.unlock();
        return waiterCount;
    }
    
    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (this.isSatisfied(guard)) {
                guard.condition.signal();
                break;
            }
        }
    }
    
    @GuardedBy("lock")
    private boolean isSatisfied(final Guard guard) {
        return guard.isSatisfied();
    }
    
    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }
    
    @GuardedBy("lock")
    private void beginWaitingFor(final Guard activeGuards) {
        if (activeGuards.waiterCount++ == 0) {
            activeGuards.next = this.activeGuards;
            this.activeGuards = activeGuards;
        }
    }
    
    @GuardedBy("lock")
    private void endWaitingFor(final Guard guard) {
        final int waiterCount = guard.waiterCount - 1;
        guard.waiterCount = waiterCount;
        if (waiterCount == 0) {
            Guard guard2 = this.activeGuards;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.next;
            }
            if (guard3 == null) {
                this.activeGuards = guard2.next;
            }
            else {
                guard3.next = guard2.next;
            }
            guard2.next = null;
        }
    }
    
    @GuardedBy("lock")
    private void await(final Guard guard, final boolean b) throws InterruptedException {
        if (b) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        do {
            guard.condition.await();
        } while (!guard.isSatisfied());
        this.endWaitingFor(guard);
    }
    
    @GuardedBy("lock")
    private void awaitUninterruptibly(final Guard guard, final boolean b) {
        if (b) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        do {
            guard.condition.awaitUninterruptibly();
        } while (!guard.isSatisfied());
        this.endWaitingFor(guard);
    }
    
    @GuardedBy("lock")
    private boolean awaitNanos(final Guard guard, long awaitNanos, final boolean b) throws InterruptedException {
        if (b) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        while (awaitNanos >= 0L) {
            awaitNanos = guard.condition.awaitNanos(awaitNanos);
            if (guard.isSatisfied()) {
                this.endWaitingFor(guard);
                return true;
            }
        }
        this.endWaitingFor(guard);
        return true;
    }
    
    static ReentrantLock access$000(final Monitor monitor) {
        return monitor.lock;
    }
    
    @Beta
    public abstract static class Guard
    {
        final Monitor monitor;
        final Condition condition;
        @GuardedBy("monitor.lock")
        int waiterCount;
        @GuardedBy("monitor.lock")
        Guard next;
        
        protected Guard(final Monitor monitor) {
            this.waiterCount = 0;
            this.monitor = (Monitor)Preconditions.checkNotNull(monitor, (Object)"monitor");
            this.condition = Monitor.access$000(monitor).newCondition();
        }
        
        public abstract boolean isSatisfied();
    }
}
