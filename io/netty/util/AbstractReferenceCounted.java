package io.netty.util;

import java.util.concurrent.atomic.*;
import io.netty.util.internal.*;

public abstract class AbstractReferenceCounted implements ReferenceCounted
{
    private static final AtomicIntegerFieldUpdater refCntUpdater;
    private int refCnt;
    
    public AbstractReferenceCounted() {
        this.refCnt = 1;
    }
    
    @Override
    public final int refCnt() {
        return this.refCnt;
    }
    
    protected final void setRefCnt(final int refCnt) {
        this.refCnt = refCnt;
    }
    
    @Override
    public ReferenceCounted retain() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, 1);
            }
            if (refCnt == Integer.MAX_VALUE) {
                throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
            }
            if (AbstractReferenceCounted.refCntUpdater.compareAndSet(this, refCnt, refCnt + 1)) {
                return this;
            }
        }
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("increment: " + n + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, 1);
            }
            if (refCnt > Integer.MAX_VALUE - n) {
                throw new IllegalReferenceCountException(refCnt, n);
            }
            if (AbstractReferenceCounted.refCntUpdater.compareAndSet(this, refCnt, refCnt + n)) {
                return this;
            }
        }
    }
    
    @Override
    public final boolean release() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, -1);
            }
            if (!AbstractReferenceCounted.refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
                continue;
            }
            if (refCnt == 1) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    @Override
    public final boolean release(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("decrement: " + n + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt < n) {
                throw new IllegalReferenceCountException(refCnt, -n);
            }
            if (!AbstractReferenceCounted.refCntUpdater.compareAndSet(this, refCnt, refCnt - n)) {
                continue;
            }
            if (refCnt == n) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    protected abstract void deallocate();
    
    static {
        AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater2 = (AtomicIntegerFieldUpdater<AbstractReferenceCounted>)PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCounted.class, "refCnt");
        if (refCntUpdater2 == null) {
            refCntUpdater2 = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
        }
        refCntUpdater = refCntUpdater2;
    }
}
