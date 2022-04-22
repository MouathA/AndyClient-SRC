package io.netty.buffer;

import io.netty.util.*;
import io.netty.util.internal.*;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator
{
    private static final int DEFAULT_INITIAL_CAPACITY = 256;
    private static final int DEFAULT_MAX_COMPONENTS = 16;
    private final boolean directByDefault;
    private final ByteBuf emptyBuf;
    
    protected static ByteBuf toLeakAwareBuffer(ByteBuf byteBuf) {
        switch (ResourceLeakDetector.getLevel()) {
            case SIMPLE: {
                final ResourceLeak open = AbstractByteBuf.leakDetector.open(byteBuf);
                if (open != null) {
                    byteBuf = new SimpleLeakAwareByteBuf(byteBuf, open);
                    break;
                }
                break;
            }
            case ADVANCED:
            case PARANOID: {
                final ResourceLeak open2 = AbstractByteBuf.leakDetector.open(byteBuf);
                if (open2 != null) {
                    byteBuf = new AdvancedLeakAwareByteBuf(byteBuf, open2);
                    break;
                }
                break;
            }
        }
        return byteBuf;
    }
    
    protected AbstractByteBufAllocator() {
        this(false);
    }
    
    protected AbstractByteBufAllocator(final boolean b) {
        this.directByDefault = (b && PlatformDependent.hasUnsafe());
        this.emptyBuf = new EmptyByteBuf(this);
    }
    
    @Override
    public ByteBuf buffer() {
        if (this.directByDefault) {
            return this.directBuffer();
        }
        return this.heapBuffer();
    }
    
    @Override
    public ByteBuf buffer(final int n) {
        if (this.directByDefault) {
            return this.directBuffer(n);
        }
        return this.heapBuffer(n);
    }
    
    @Override
    public ByteBuf buffer(final int n, final int n2) {
        if (this.directByDefault) {
            return this.directBuffer(n, n2);
        }
        return this.heapBuffer(n, n2);
    }
    
    @Override
    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(256);
        }
        return this.heapBuffer(256);
    }
    
    @Override
    public ByteBuf ioBuffer(final int n) {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(n);
        }
        return this.heapBuffer(n);
    }
    
    @Override
    public ByteBuf ioBuffer(final int n, final int n2) {
        if (PlatformDependent.hasUnsafe()) {
            return this.directBuffer(n, n2);
        }
        return this.heapBuffer(n, n2);
    }
    
    @Override
    public ByteBuf heapBuffer() {
        return this.heapBuffer(256, Integer.MAX_VALUE);
    }
    
    @Override
    public ByteBuf heapBuffer(final int n) {
        return this.heapBuffer(n, Integer.MAX_VALUE);
    }
    
    @Override
    public ByteBuf heapBuffer(final int n, final int n2) {
        if (n == 0 && n2 == 0) {
            return this.emptyBuf;
        }
        validate(n, n2);
        return this.newHeapBuffer(n, n2);
    }
    
    @Override
    public ByteBuf directBuffer() {
        return this.directBuffer(256, Integer.MAX_VALUE);
    }
    
    @Override
    public ByteBuf directBuffer(final int n) {
        return this.directBuffer(n, Integer.MAX_VALUE);
    }
    
    @Override
    public ByteBuf directBuffer(final int n, final int n2) {
        if (n == 0 && n2 == 0) {
            return this.emptyBuf;
        }
        validate(n, n2);
        return this.newDirectBuffer(n, n2);
    }
    
    @Override
    public CompositeByteBuf compositeBuffer() {
        if (this.directByDefault) {
            return this.compositeDirectBuffer();
        }
        return this.compositeHeapBuffer();
    }
    
    @Override
    public CompositeByteBuf compositeBuffer(final int n) {
        if (this.directByDefault) {
            return this.compositeDirectBuffer(n);
        }
        return this.compositeHeapBuffer(n);
    }
    
    @Override
    public CompositeByteBuf compositeHeapBuffer() {
        return this.compositeHeapBuffer(16);
    }
    
    @Override
    public CompositeByteBuf compositeHeapBuffer(final int n) {
        return new CompositeByteBuf(this, false, n);
    }
    
    @Override
    public CompositeByteBuf compositeDirectBuffer() {
        return this.compositeDirectBuffer(16);
    }
    
    @Override
    public CompositeByteBuf compositeDirectBuffer(final int n) {
        return new CompositeByteBuf(this, true, n);
    }
    
    private static void validate(final int n, final int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity: " + n + " (expectd: 0+)");
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", n, n2));
        }
    }
    
    protected abstract ByteBuf newHeapBuffer(final int p0, final int p1);
    
    protected abstract ByteBuf newDirectBuffer(final int p0, final int p1);
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
    }
}
