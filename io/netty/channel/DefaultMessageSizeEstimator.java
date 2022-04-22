package io.netty.channel;

import io.netty.buffer.*;

public final class DefaultMessageSizeEstimator implements MessageSizeEstimator
{
    public static final MessageSizeEstimator DEFAULT;
    private final Handle handle;
    
    public DefaultMessageSizeEstimator(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("unknownSize: " + n + " (expected: >= 0)");
        }
        this.handle = new HandleImpl(n, null);
    }
    
    @Override
    public Handle newHandle() {
        return this.handle;
    }
    
    static {
        DEFAULT = new DefaultMessageSizeEstimator(0);
    }
    
    private static final class HandleImpl implements Handle
    {
        private final int unknownSize;
        
        private HandleImpl(final int unknownSize) {
            this.unknownSize = unknownSize;
        }
        
        @Override
        public int size(final Object o) {
            if (o instanceof ByteBuf) {
                return ((ByteBuf)o).readableBytes();
            }
            if (o instanceof ByteBufHolder) {
                return ((ByteBufHolder)o).content().readableBytes();
            }
            if (o instanceof FileRegion) {
                return 0;
            }
            return this.unknownSize;
        }
        
        HandleImpl(final int n, final DefaultMessageSizeEstimator$1 object) {
            this(n);
        }
    }
}
