package io.netty.handler.codec;

import io.netty.util.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;

public abstract class ReplayingDecoder extends ByteToMessageDecoder
{
    static final Signal REPLAY;
    private final ReplayingDecoderBuffer replayable;
    private Object state;
    private int checkpoint;
    
    protected ReplayingDecoder() {
        this(null);
    }
    
    protected ReplayingDecoder(final Object state) {
        this.replayable = new ReplayingDecoderBuffer();
        this.checkpoint = -1;
        this.state = state;
    }
    
    protected void checkpoint() {
        this.checkpoint = this.internalBuffer().readerIndex();
    }
    
    protected void checkpoint(final Object o) {
        this.checkpoint();
        this.state(o);
    }
    
    protected Object state() {
        return this.state;
    }
    
    protected Object state(final Object state) {
        final Object state2 = this.state;
        this.state = state;
        return state2;
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final RecyclableArrayList instance = RecyclableArrayList.newInstance();
        this.replayable.terminate();
        this.callDecode(channelHandlerContext, this.internalBuffer(), instance);
        this.decodeLast(channelHandlerContext, this.replayable, instance);
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
    
    @Override
    protected void callDecode(final ChannelHandlerContext channelHandlerContext, final ByteBuf cumulation, final List list) {
        this.replayable.setCumulation(cumulation);
        while (cumulation.isReadable()) {
            final int readerIndex = cumulation.readerIndex();
            this.checkpoint = readerIndex;
            final int n = readerIndex;
            final int size = list.size();
            final Object state = this.state;
            final int readableBytes = cumulation.readableBytes();
            this.decode(channelHandlerContext, this.replayable, list);
            if (channelHandlerContext.isRemoved()) {
                break;
            }
            if (size == list.size()) {
                if (readableBytes == cumulation.readableBytes() && state == this.state) {
                    throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() must consume the inbound " + "data or change its state if it did not decode anything.");
                }
                continue;
            }
            else {
                if (n == cumulation.readerIndex() && state == this.state) {
                    throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() method must consume the inbound data " + "or change its state if it decoded something.");
                }
                if (this.isSingleDecode()) {
                    break;
                }
                continue;
            }
        }
    }
    
    static {
        REPLAY = Signal.valueOf(ReplayingDecoder.class.getName() + ".REPLAY");
    }
}
