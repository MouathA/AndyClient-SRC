package io.netty.handler.codec;

import io.netty.util.internal.*;
import io.netty.channel.*;
import io.netty.util.*;
import io.netty.buffer.*;

public abstract class MessageToByteEncoder extends ChannelOutboundHandlerAdapter
{
    private final TypeParameterMatcher matcher;
    private final boolean preferDirect;
    
    protected MessageToByteEncoder() {
        this(true);
    }
    
    protected MessageToByteEncoder(final Class clazz) {
        this(clazz, true);
    }
    
    protected MessageToByteEncoder(final boolean preferDirect) {
        this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
        this.preferDirect = preferDirect;
    }
    
    protected MessageToByteEncoder(final Class clazz, final boolean preferDirect) {
        this.matcher = TypeParameterMatcher.get(clazz);
        this.preferDirect = preferDirect;
    }
    
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return this.matcher.match(o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        ReferenceCounted referenceCounted = null;
        if (this.acceptOutboundMessage(o)) {
            final ByteBuf allocateBuffer = this.allocateBuffer(channelHandlerContext, o, this.preferDirect);
            this.encode(channelHandlerContext, o, allocateBuffer);
            ReferenceCountUtil.release(o);
            if (allocateBuffer.isReadable()) {
                channelHandlerContext.write(allocateBuffer, channelPromise);
            }
            else {
                allocateBuffer.release();
                channelHandlerContext.write(Unpooled.EMPTY_BUFFER, channelPromise);
            }
            referenceCounted = null;
        }
        else {
            channelHandlerContext.write(o, channelPromise);
        }
        if (referenceCounted != null) {
            referenceCounted.release();
        }
    }
    
    protected ByteBuf allocateBuffer(final ChannelHandlerContext channelHandlerContext, final Object o, final boolean b) throws Exception {
        if (b) {
            return channelHandlerContext.alloc().ioBuffer();
        }
        return channelHandlerContext.alloc().heapBuffer();
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final Object p1, final ByteBuf p2) throws Exception;
}
