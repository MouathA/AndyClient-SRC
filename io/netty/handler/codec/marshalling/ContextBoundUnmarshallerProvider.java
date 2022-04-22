package io.netty.handler.codec.marshalling;

import io.netty.channel.*;
import org.jboss.marshalling.*;
import io.netty.util.*;

public class ContextBoundUnmarshallerProvider extends DefaultUnmarshallerProvider
{
    private static final AttributeKey UNMARSHALLER;
    
    public ContextBoundUnmarshallerProvider(final MarshallerFactory marshallerFactory, final MarshallingConfiguration marshallingConfiguration) {
        super(marshallerFactory, marshallingConfiguration);
    }
    
    @Override
    public Unmarshaller getUnmarshaller(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final Attribute attr = channelHandlerContext.attr(ContextBoundUnmarshallerProvider.UNMARSHALLER);
        Unmarshaller unmarshaller = (Unmarshaller)attr.get();
        if (unmarshaller == null) {
            unmarshaller = super.getUnmarshaller(channelHandlerContext);
            attr.set(unmarshaller);
        }
        return unmarshaller;
    }
    
    static {
        UNMARSHALLER = AttributeKey.valueOf(ContextBoundUnmarshallerProvider.class.getName() + ".UNMARSHALLER");
    }
}
