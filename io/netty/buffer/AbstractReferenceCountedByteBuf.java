package io.netty.buffer;

import java.util.concurrent.atomic.*;
import io.netty.util.*;
import io.netty.util.internal.*;

public abstract class AbstractReferenceCountedByteBuf extends AbstractByteBuf
{
    private static final AtomicIntegerFieldUpdater refCntUpdater;
    private int refCnt;
    
    protected AbstractReferenceCountedByteBuf(final int n) {
        super(n);
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
    public ByteBuf retain() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, 1);
            }
            if (refCnt == Integer.MAX_VALUE) {
                throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
            }
            if (AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + 1)) {
                return this;
            }
        }
    }
    
    @Override
    public ByteBuf retain(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("increment: " + n + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, n);
            }
            if (refCnt > Integer.MAX_VALUE - n) {
                throw new IllegalReferenceCountException(refCnt, n);
            }
            if (AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + n)) {
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
            if (!AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
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
            if (!AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - n)) {
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
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    static {
        AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater2 = (AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf>)PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
        if (refCntUpdater2 == null) {
            refCntUpdater2 = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
        }
        refCntUpdater = refCntUpdater2;
    }
}
