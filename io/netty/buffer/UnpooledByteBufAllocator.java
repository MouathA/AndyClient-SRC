package io.netty.buffer;

import io.netty.util.internal.*;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator
{
    public static final UnpooledByteBufAllocator DEFAULT;
    
    public UnpooledByteBufAllocator(final boolean b) {
        super(b);
    }
    
    @Override
    protected ByteBuf newHeapBuffer(final int n, final int n2) {
        return new UnpooledHeapByteBuf(this, n, n2);
    }
    
    @Override
    protected ByteBuf newDirectBuffer(final int n, final int n2) {
        AbstractReferenceCountedByteBuf abstractReferenceCountedByteBuf;
        if (PlatformDependent.hasUnsafe()) {
            abstractReferenceCountedByteBuf = new UnpooledUnsafeDirectByteBuf(this, n, n2);
        }
        else {
            abstractReferenceCountedByteBuf = new UnpooledDirectByteBuf(this, n, n2);
        }
        return AbstractByteBufAllocator.toLeakAwareBuffer(abstractReferenceCountedByteBuf);
    }
    
    @Override
    public boolean isDirectBufferPooled() {
        return false;
    }
    
    static {
        DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }
}
