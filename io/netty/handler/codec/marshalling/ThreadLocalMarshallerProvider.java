package io.netty.handler.codec.marshalling;

import io.netty.util.concurrent.*;
import io.netty.channel.*;
import org.jboss.marshalling.*;

public class ThreadLocalMarshallerProvider implements MarshallerProvider
{
    private final FastThreadLocal marshallers;
    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;
    
    public ThreadLocalMarshallerProvider(final MarshallerFactory factory, final MarshallingConfiguration config) {
        this.marshallers = new FastThreadLocal();
        this.factory = factory;
        this.config = config;
    }
    
    @Override
    public Marshaller getMarshaller(final ChannelHandlerContext channelHandlerContext) throws Exception {
        Marshaller marshaller = (Marshaller)this.marshallers.get();
        if (marshaller == null) {
            marshaller = this.factory.createMarshaller(this.config);
            this.marshallers.set(marshaller);
        }
        return marshaller;
    }
}
