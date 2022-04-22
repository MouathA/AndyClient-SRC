package io.netty.handler.codec.marshalling;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import org.jboss.marshalling.*;

@ChannelHandler.Sharable
public class MarshallingEncoder extends MessageToByteEncoder
{
    private static final byte[] LENGTH_PLACEHOLDER;
    private final MarshallerProvider provider;
    
    public MarshallingEncoder(final MarshallerProvider provider) {
        this.provider = provider;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        final Marshaller marshaller = this.provider.getMarshaller(channelHandlerContext);
        final int writerIndex = byteBuf.writerIndex();
        byteBuf.writeBytes(MarshallingEncoder.LENGTH_PLACEHOLDER);
        marshaller.start((ByteOutput)new ChannelBufferByteOutput(byteBuf));
        marshaller.writeObject(o);
        marshaller.finish();
        marshaller.close();
        byteBuf.setInt(writerIndex, byteBuf.writerIndex() - writerIndex - 4);
    }
    
    static {
        LENGTH_PLACEHOLDER = new byte[4];
    }
}
