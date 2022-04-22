package io.netty.handler.codec.marshalling;

import io.netty.util.concurrent.*;
import io.netty.channel.*;
import org.jboss.marshalling.*;

public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider
{
    private final FastThreadLocal unmarshallers;
    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;
    
    public ThreadLocalUnmarshallerProvider(final MarshallerFactory factory, final MarshallingConfiguration config) {
        this.unmarshallers = new FastThreadLocal();
        this.factory = factory;
        this.config = config;
    }
    
    @Override
    public Unmarshaller getUnmarshaller(final ChannelHandlerContext channelHandlerContext) throws Exception {
        Unmarshaller unmarshaller = (Unmarshaller)this.unmarshallers.get();
        if (unmarshaller == null) {
            unmarshaller = this.factory.createUnmarshaller(this.config);
            this.unmarshallers.set(unmarshaller);
        }
        return unmarshaller;
    }
}
