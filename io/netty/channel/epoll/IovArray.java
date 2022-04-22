package io.netty.channel.epoll;

import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.*;
import io.netty.buffer.*;

final class IovArray implements ChannelOutboundBuffer.MessageProcessor
{
    private static final int ADDRESS_SIZE;
    private static final int IOV_SIZE;
    private static final int CAPACITY;
    private static final FastThreadLocal ARRAY;
    private final long memoryAddress;
    private int count;
    private long size;
    static final boolean $assertionsDisabled;
    
    private IovArray() {
        this.memoryAddress = PlatformDependent.allocateMemory(IovArray.CAPACITY);
    }
    
    long processWritten(final int n, final long n2) {
        final long memoryAddress = this.memoryAddress(n);
        final long n3 = memoryAddress + IovArray.ADDRESS_SIZE;
        if (IovArray.ADDRESS_SIZE == 8) {
            final long long1 = PlatformDependent.getLong(n3);
            if (long1 > n2) {
                PlatformDependent.putLong(memoryAddress, PlatformDependent.getLong(memoryAddress) + n2);
                PlatformDependent.putLong(n3, long1 - n2);
                return -1L;
            }
            return long1;
        }
        else {
            assert IovArray.ADDRESS_SIZE == 4;
            final long n4 = PlatformDependent.getInt(n3);
            if (n4 > n2) {
                PlatformDependent.putInt(memoryAddress, (int)(PlatformDependent.getInt(memoryAddress) + n2));
                PlatformDependent.putInt(n3, (int)(n4 - n2));
                return -1L;
            }
            return n4;
        }
    }
    
    int count() {
        return this.count;
    }
    
    long size() {
        return this.size;
    }
    
    long memoryAddress(final int n) {
        return this.memoryAddress + IovArray.IOV_SIZE * n;
    }
    
    @Override
    public boolean processMessage(final Object o) throws Exception {
        return o instanceof ByteBuf && this == o;
    }
    
    static IovArray get(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        final IovArray iovArray = (IovArray)IovArray.ARRAY.get();
        iovArray.size = 0L;
        iovArray.count = 0;
        channelOutboundBuffer.forEachFlushedMessage(iovArray);
        return iovArray;
    }
    
    IovArray(final IovArray$1 fastThreadLocal) {
        this();
    }
    
    static long access$100(final IovArray iovArray) {
        return iovArray.memoryAddress;
    }
    
    static {
        $assertionsDisabled = !IovArray.class.desiredAssertionStatus();
        ADDRESS_SIZE = PlatformDependent.addressSize();
        IOV_SIZE = 2 * IovArray.ADDRESS_SIZE;
        CAPACITY = Native.IOV_MAX * IovArray.IOV_SIZE;
        ARRAY = new FastThreadLocal() {
            @Override
            protected IovArray initialValue() throws Exception {
                return new IovArray(null);
            }
            
            protected void onRemoval(final IovArray iovArray) throws Exception {
                PlatformDependent.freeMemory(IovArray.access$100(iovArray));
            }
            
            @Override
            protected void onRemoval(final Object o) throws Exception {
                this.onRemoval((IovArray)o);
            }
            
            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }
}
