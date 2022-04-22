package io.netty.handler.codec.marshalling;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import org.jboss.marshalling.*;
import io.netty.handler.codec.*;

public class CompatibleMarshallingDecoder extends ReplayingDecoder
{
    protected final UnmarshallerProvider provider;
    protected final int maxObjectSize;
    private boolean discardingTooLongFrame;
    
    public CompatibleMarshallingDecoder(final UnmarshallerProvider provider, final int maxObjectSize) {
        this.provider = provider;
        this.maxObjectSize = maxObjectSize;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.discardingTooLongFrame) {
            byteBuf.skipBytes(this.actualReadableBytes());
            this.checkpoint();
            return;
        }
        final Unmarshaller unmarshaller = this.provider.getUnmarshaller(channelHandlerContext);
        Object o = new ChannelBufferByteInput(byteBuf);
        if (this.maxObjectSize != Integer.MAX_VALUE) {
            o = new LimitingByteInput((ByteInput)o, this.maxObjectSize);
        }
        unmarshaller.start((ByteInput)o);
        final Object object = unmarshaller.readObject();
        unmarshaller.finish();
        list.add(object);
        unmarshaller.close();
    }
    
    @Override
    protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        switch (byteBuf.readableBytes()) {
            case 0: {
                return;
            }
            case 1: {
                if (byteBuf.getByte(byteBuf.readerIndex()) == 121) {
                    byteBuf.skipBytes(1);
                    return;
                }
                break;
            }
        }
        this.decode(channelHandlerContext, byteBuf, list);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof TooLongFrameException) {
            channelHandlerContext.close();
        }
        else {
            super.exceptionCaught(channelHandlerContext, t);
        }
    }
}
