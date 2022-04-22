package io.netty.channel;

import java.util.*;
import io.netty.buffer.*;

public class AdaptiveRecvByteBufAllocator implements RecvByteBufAllocator
{
    static final int DEFAULT_MINIMUM = 64;
    static final int DEFAULT_INITIAL = 1024;
    static final int DEFAULT_MAXIMUM = 65536;
    private static final int INDEX_INCREMENT = 4;
    private static final int INDEX_DECREMENT = 1;
    private static final int[] SIZE_TABLE;
    public static final AdaptiveRecvByteBufAllocator DEFAULT;
    private final int minIndex;
    private final int maxIndex;
    private final int initial;
    
    private static int getSizeTableIndex(final int n) {
        int n2 = 0;
        int i = AdaptiveRecvByteBufAllocator.SIZE_TABLE.length - 1;
        while (i >= n2) {
            if (i == n2) {
                return i;
            }
            final int n3 = n2 + i >>> 1;
            final int n4 = AdaptiveRecvByteBufAllocator.SIZE_TABLE[n3];
            if (n > AdaptiveRecvByteBufAllocator.SIZE_TABLE[n3 + 1]) {
                n2 = n3 + 1;
            }
            else if (n < n4) {
                i = n3 - 1;
            }
            else {
                if (n == n4) {
                    return n3;
                }
                return n3 + 1;
            }
        }
        return n2;
    }
    
    private AdaptiveRecvByteBufAllocator() {
        this(64, 1024, 65536);
    }
    
    public AdaptiveRecvByteBufAllocator(final int n, final int initial, final int n2) {
        if (n <= 0) {
            throw new IllegalArgumentException("minimum: " + n);
        }
        if (initial < n) {
            throw new IllegalArgumentException("initial: " + initial);
        }
        if (n2 < initial) {
            throw new IllegalArgumentException("maximum: " + n2);
        }
        final int sizeTableIndex = getSizeTableIndex(n);
        if (AdaptiveRecvByteBufAllocator.SIZE_TABLE[sizeTableIndex] < n) {
            this.minIndex = sizeTableIndex + 1;
        }
        else {
            this.minIndex = sizeTableIndex;
        }
        final int sizeTableIndex2 = getSizeTableIndex(n2);
        if (AdaptiveRecvByteBufAllocator.SIZE_TABLE[sizeTableIndex2] > n2) {
            this.maxIndex = sizeTableIndex2 - 1;
        }
        else {
            this.maxIndex = sizeTableIndex2;
        }
        this.initial = initial;
    }
    
    @Override
    public Handle newHandle() {
        return new HandleImpl(this.minIndex, this.maxIndex, this.initial);
    }
    
    static int access$000(final int n) {
        return getSizeTableIndex(n);
    }
    
    static int[] access$100() {
        return AdaptiveRecvByteBufAllocator.SIZE_TABLE;
    }
    
    static {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 16; i < 512; i += 16) {
            list.add(i);
        }
        for (int j = 512; j > 0; j <<= 1) {
            list.add(j);
        }
        SIZE_TABLE = new int[list.size()];
        for (int k = 0; k < AdaptiveRecvByteBufAllocator.SIZE_TABLE.length; ++k) {
            AdaptiveRecvByteBufAllocator.SIZE_TABLE[k] = (int)list.get(k);
        }
        DEFAULT = new AdaptiveRecvByteBufAllocator();
    }
    
    private static final class HandleImpl implements Handle
    {
        private final int minIndex;
        private final int maxIndex;
        private int index;
        private int nextReceiveBufferSize;
        private boolean decreaseNow;
        
        HandleImpl(final int minIndex, final int maxIndex, final int n) {
            this.minIndex = minIndex;
            this.maxIndex = maxIndex;
            this.index = AdaptiveRecvByteBufAllocator.access$000(n);
            this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
        }
        
        @Override
        public ByteBuf allocate(final ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(this.nextReceiveBufferSize);
        }
        
        @Override
        public int guess() {
            return this.nextReceiveBufferSize;
        }
        
        @Override
        public void record(final int n) {
            if (n <= AdaptiveRecvByteBufAllocator.access$100()[Math.max(0, this.index - 1 - 1)]) {
                if (this.decreaseNow) {
                    this.index = Math.max(this.index - 1, this.minIndex);
                    this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
                    this.decreaseNow = false;
                }
                else {
                    this.decreaseNow = true;
                }
            }
            else if (n >= this.nextReceiveBufferSize) {
                this.index = Math.min(this.index + 4, this.maxIndex);
                this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
                this.decreaseNow = false;
            }
        }
    }
}
