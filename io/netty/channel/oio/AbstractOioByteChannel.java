package io.netty.channel.oio;

import java.io.*;
import io.netty.channel.socket.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.internal.*;

public abstract class AbstractOioByteChannel extends AbstractOioChannel
{
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private RecvByteBufAllocator.Handle allocHandle;
    private boolean inputShutdown;
    
    protected AbstractOioByteChannel(final Channel channel) {
        super(channel);
    }
    
    protected boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AbstractOioByteChannel.METADATA;
    }
    
    @Override
    protected void doRead() {
        if (this != 0) {
            return;
        }
        final ChannelConfig config = this.config();
        final ChannelPipeline pipeline = this.pipeline();
        RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
        if (allocHandle == null) {
            allocHandle = (this.allocHandle = config.getRecvByteBufAllocator().newHandle());
        }
        final ByteBuf allocate = allocHandle.allocate(this.alloc());
        final Throwable t = null;
        this.doReadBytes(allocate);
        final int available = this.available();
        if (available > 0) {
            if (!allocate.isWritable()) {
                final int capacity = allocate.capacity();
                final int maxCapacity = allocate.maxCapacity();
                if (capacity != maxCapacity) {
                    if (allocate.writerIndex() + available > maxCapacity) {
                        allocate.capacity(maxCapacity);
                    }
                    else {
                        allocate.ensureWritable(available);
                    }
                }
            }
        }
        allocHandle.record(Integer.MAX_VALUE);
        allocate.release();
        pipeline.fireChannelReadComplete();
        if (t != null) {
            if (t instanceof IOException) {
                this.pipeline().fireExceptionCaught(t);
            }
            else {
                pipeline.fireExceptionCaught(t);
                this.unsafe().close(this.voidPromise());
            }
        }
        this.inputShutdown = true;
        if (this.isOpen()) {
            if (Boolean.TRUE.equals(this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            }
            else {
                this.unsafe().close(this.unsafe().voidPromise());
            }
        }
        if (this.isActive()) {
            this.read();
        }
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            final Object current = channelOutboundBuffer.current();
            if (current == null) {
                break;
            }
            if (current instanceof ByteBuf) {
                final ByteBuf byteBuf = (ByteBuf)current;
                int readableBytes;
                for (int i = byteBuf.readableBytes(); i > 0; i = readableBytes) {
                    this.doWriteBytes(byteBuf);
                    readableBytes = byteBuf.readableBytes();
                    channelOutboundBuffer.progress(i - readableBytes);
                }
                channelOutboundBuffer.remove();
            }
            else if (current instanceof FileRegion) {
                final FileRegion fileRegion = (FileRegion)current;
                final long transfered = fileRegion.transfered();
                this.doWriteFileRegion(fileRegion);
                channelOutboundBuffer.progress(fileRegion.transfered() - transfered);
                channelOutboundBuffer.remove();
            }
            else {
                channelOutboundBuffer.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(current)));
            }
        }
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) throws Exception {
        if (o instanceof ByteBuf || o instanceof FileRegion) {
            return o;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + AbstractOioByteChannel.EXPECTED_TYPES);
    }
    
    protected abstract int available();
    
    protected abstract int doReadBytes(final ByteBuf p0) throws Exception;
    
    protected abstract void doWriteBytes(final ByteBuf p0) throws Exception;
    
    protected abstract void doWriteFileRegion(final FileRegion p0) throws Exception;
    
    static {
        METADATA = new ChannelMetadata(false);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
    }
}
