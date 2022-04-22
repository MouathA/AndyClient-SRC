package io.netty.channel;

import io.netty.buffer.*;

public class FixedRecvByteBufAllocator implements RecvByteBufAllocator
{
    private final Handle handle;
    
    public FixedRecvByteBufAllocator(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("bufferSize must greater than 0: " + n);
        }
        this.handle = new HandleImpl(n);
    }
    
    @Override
    public Handle newHandle() {
        return this.handle;
    }
    
    private static final class HandleImpl implements Handle
    {
        private final int bufferSize;
        
        HandleImpl(final int bufferSize) {
            this.bufferSize = bufferSize;
        }
        
        @Override
        public ByteBuf allocate(final ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(this.bufferSize);
        }
        
        @Override
        public int guess() {
            return this.bufferSize;
        }
        
        @Override
        public void record(final int n) {
        }
    }
}
