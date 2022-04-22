package io.netty.handler.codec.marshalling;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import org.jboss.marshalling.*;

@ChannelHandler.Sharable
public class CompatibleMarshallingEncoder extends MessageToByteEncoder
{
    private final MarshallerProvider provider;
    
    public CompatibleMarshallingEncoder(final MarshallerProvider provider) {
        this.provider = provider;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        final Marshaller marshaller = this.provider.getMarshaller(channelHandlerContext);
        marshaller.start((ByteOutput)new ChannelBufferByteOutput(byteBuf));
        marshaller.writeObject(o);
        marshaller.finish();
        marshaller.close();
    }
}
