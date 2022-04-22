package io.netty.util.internal.chmv8;

import sun.misc.*;

public abstract class CountedCompleter extends ForkJoinTask
{
    private static final long serialVersionUID = 5232453752276485070L;
    final CountedCompleter completer;
    int pending;
    private static final Unsafe U;
    private static final long PENDING;
    
    protected CountedCompleter(final CountedCompleter completer, final int pending) {
        this.completer = completer;
        this.pending = pending;
    }
    
    protected CountedCompleter(final CountedCompleter completer) {
        this.completer = completer;
    }
    
    protected CountedCompleter() {
        this.completer = null;
    }
    
    public abstract void compute();
    
    public void onCompletion(final CountedCompleter countedCompleter) {
    }
    
    public boolean onExceptionalCompletion(final Throwable t, final CountedCompleter countedCompleter) {
        return true;
    }
    
    public final CountedCompleter getCompleter() {
        return this.completer;
    }
    
    public final int getPendingCount() {
        return this.pending;
    }
    
    public final void setPendingCount(final int pending) {
        this.pending = pending;
    }
    
    public final void addToPendingCount(final int n) {
        Unsafe u;
        long pending;
        int pending2;
        do {
            u = CountedCompleter.U;
            pending = CountedCompleter.PENDING;
            pending2 = this.pending;
        } while (!u.compareAndSwapInt(this, pending, pending2, pending2 + n));
    }
    
    public final boolean compareAndSetPendingCount(final int n, final int n2) {
        return CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, n, n2);
    }
    
    public final int decrementPendingCountUnlessZero() {
        int pending;
        while ((pending = this.pending) != 0 && !CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, pending, pending - 1)) {}
        return pending;
    }
    
    public final CountedCompleter getRoot() {
        CountedCompleter countedCompleter;
        CountedCompleter completer;
        for (countedCompleter = this; (completer = countedCompleter.completer) != null; countedCompleter = completer) {}
        return countedCompleter;
    }
    
    public final void tryComplete() {
        CountedCompleter countedCompleter;
        CountedCompleter completer = countedCompleter = this;
        while (true) {
            final int pending;
            if ((pending = completer.pending) == 0) {
                completer.onCompletion(countedCompleter);
                if ((completer = (countedCompleter = completer).completer) == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                continue;
            }
            else {
                if (CountedCompleter.U.compareAndSwapInt(completer, CountedCompleter.PENDING, pending, pending - 1)) {
                    return;
                }
                continue;
            }
        }
    }
    
    public final void propagateCompletion() {
        CountedCompleter completer = this;
        while (true) {
            final int pending;
            if ((pending = completer.pending) == 0) {
                final CountedCompleter countedCompleter;
                if ((completer = (countedCompleter = completer).completer) == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                continue;
            }
            else {
                if (CountedCompleter.U.compareAndSwapInt(completer, CountedCompleter.PENDING, pending, pending - 1)) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Override
    public void complete(final Object rawResult) {
        this.setRawResult(rawResult);
        this.onCompletion(this);
        this.quietlyComplete();
        final CountedCompleter completer;
        if ((completer = this.completer) != null) {
            completer.tryComplete();
        }
    }
    
    public final CountedCompleter firstComplete() {
        int pending;
        while ((pending = this.pending) != 0) {
            if (CountedCompleter.U.compareAndSwapInt(this, CountedCompleter.PENDING, pending, pending - 1)) {
                return null;
            }
        }
        return this;
    }
    
    public final CountedCompleter nextComplete() {
        final CountedCompleter completer;
        if ((completer = this.completer) != null) {
            return completer.firstComplete();
        }
        this.quietlyComplete();
        return null;
    }
    
    public final void quietlyCompleteRoot() {
        CountedCompleter countedCompleter;
        CountedCompleter completer;
        for (countedCompleter = this; (completer = countedCompleter.completer) != null; countedCompleter = completer) {}
        countedCompleter.quietlyComplete();
    }
    
    @Override
    void internalPropagateException(final Throwable t) {
        CountedCompleter countedCompleter;
        CountedCompleter completer = countedCompleter = this;
        while (completer.onExceptionalCompletion(t, countedCompleter) && (completer = (countedCompleter = completer).completer) != null && completer.status >= 0 && completer.recordExceptionalCompletion(t) == Integer.MIN_VALUE) {}
    }
    
    @Override
    protected final boolean exec() {
        this.compute();
        return false;
    }
    
    @Override
    public Object getRawResult() {
        return null;
    }
    
    @Override
    protected void setRawResult(final Object o) {
    }
    
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }
    
    static {
        U = getUnsafe();
        PENDING = CountedCompleter.U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
    }
}
