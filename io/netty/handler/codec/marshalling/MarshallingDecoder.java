package io.netty.handler.codec.marshalling;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import org.jboss.marshalling.*;

public class MarshallingDecoder extends LengthFieldBasedFrameDecoder
{
    private final UnmarshallerProvider provider;
    
    public MarshallingDecoder(final UnmarshallerProvider unmarshallerProvider) {
        this(unmarshallerProvider, 1048576);
    }
    
    public MarshallingDecoder(final UnmarshallerProvider provider, final int n) {
        super(n, 0, 4, 0, 4);
        this.provider = provider;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        final ByteBuf byteBuf2 = (ByteBuf)super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        final Unmarshaller unmarshaller = this.provider.getUnmarshaller(channelHandlerContext);
        unmarshaller.start((ByteInput)new ChannelBufferByteInput(byteBuf2));
        final Object object = unmarshaller.readObject();
        unmarshaller.finish();
        final Object o = object;
        unmarshaller.close();
        return o;
    }
    
    @Override
    protected ByteBuf extractFrame(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final int n, final int n2) {
        return byteBuf.slice(n, n2);
    }
}
