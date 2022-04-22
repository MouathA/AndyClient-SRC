package io.netty.handler.codec;

import io.netty.buffer.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.util.internal.*;

public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter
{
    ByteBuf cumulation;
    private boolean singleDecode;
    private boolean decodeWasNull;
    private boolean first;
    
    protected ByteToMessageDecoder() {
        if (this.isSharable()) {
            throw new IllegalStateException("@Sharable annotation is not allowed");
        }
    }
    
    public void setSingleDecode(final boolean singleDecode) {
        this.singleDecode = singleDecode;
    }
    
    public boolean isSingleDecode() {
        return this.singleDecode;
    }
    
    protected int actualReadableBytes() {
        return this.internalBuffer().readableBytes();
    }
    
    protected ByteBuf internalBuffer() {
        if (this.cumulation != null) {
            return this.cumulation;
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    @Override
    public final void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final ByteBuf internalBuffer = this.internalBuffer();
        final int readableBytes = internalBuffer.readableBytes();
        if (internalBuffer.isReadable()) {
            final ByteBuf bytes = internalBuffer.readBytes(readableBytes);
            internalBuffer.release();
            channelHandlerContext.fireChannelRead(bytes);
        }
        else {
            internalBuffer.release();
        }
        this.cumulation = null;
        channelHandlerContext.fireChannelReadComplete();
        this.handlerRemoved0(channelHandlerContext);
    }
    
    protected void handlerRemoved0(final ChannelHandlerContext channelHandlerContext) throws Exception {
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (o instanceof ByteBuf) {
            final RecyclableArrayList instance = RecyclableArrayList.newInstance();
            final ByteBuf cumulation = (ByteBuf)o;
            this.first = (this.cumulation == null);
            if (this.first) {
                this.cumulation = cumulation;
            }
            else {
                if (this.cumulation.writerIndex() > this.cumulation.maxCapacity() - cumulation.readableBytes() || this.cumulation.refCnt() > 1) {
                    this.expandCumulation(channelHandlerContext, cumulation.readableBytes());
                }
                this.cumulation.writeBytes(cumulation);
                cumulation.release();
            }
            this.callDecode(channelHandlerContext, this.cumulation, instance);
            if (this.cumulation != null && !this.cumulation.isReadable()) {
                this.cumulation.release();
                this.cumulation = null;
            }
            final int size = instance.size();
            this.decodeWasNull = (size == 0);
            while (0 < size) {
                channelHandlerContext.fireChannelRead(instance.get(0));
                int n = 0;
                ++n;
            }
            instance.recycle();
        }
        else {
            channelHandlerContext.fireChannelRead(o);
        }
    }
    
    private void expandCumulation(final ChannelHandlerContext channelHandlerContext, final int n) {
        final ByteBuf cumulation = this.cumulation;
        (this.cumulation = channelHandlerContext.alloc().buffer(cumulation.readableBytes() + n)).writeBytes(cumulation);
        cumulation.release();
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1) {
            this.cumulation.discardSomeReadBytes();
        }
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!channelHandlerContext.channel().config().isAutoRead()) {
                channelHandlerContext.read();
            }
        }
        channelHandlerContext.fireChannelReadComplete();
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final RecyclableArrayList instance = RecyclableArrayList.newInstance();
        if (this.cumulation != null) {
            this.callDecode(channelHandlerContext, this.cumulation, instance);
            this.decodeLast(channelHandlerContext, this.cumulation, instance);
        }
        else {
            this.decodeLast(channelHandlerContext, Unpooled.EMPTY_BUFFER, instance);
        }
        if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
        }
        final int size = instance.size();
        while (0 < size) {
            channelHandlerContext.fireChannelRead(instance.get(0));
            int n = 0;
            ++n;
        }
        if (size > 0) {
            channelHandlerContext.fireChannelReadComplete();
        }
        channelHandlerContext.fireChannelInactive();
        instance.recycle();
    }
    
    protected void callDecode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) {
        while (byteBuf.isReadable()) {
            final int size = list.size();
            final int readableBytes = byteBuf.readableBytes();
            this.decode(channelHandlerContext, byteBuf, list);
            if (channelHandlerContext.isRemoved()) {
                break;
            }
            if (size == list.size()) {
                if (readableBytes == byteBuf.readableBytes()) {
                    break;
                }
                continue;
            }
            else {
                if (readableBytes == byteBuf.readableBytes()) {
                    throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() did not read anything but decoded a message.");
                }
                if (this.isSingleDecode()) {
                    break;
                }
                continue;
            }
        }
    }
    
    protected abstract void decode(final ChannelHandlerContext p0, final ByteBuf p1, final List p2) throws Exception;
    
    protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        this.decode(channelHandlerContext, byteBuf, list);
    }
}
