package io.netty.channel;

import io.netty.util.internal.*;
import java.lang.annotation.*;
import java.util.*;

public abstract class ChannelHandlerAdapter implements ChannelHandler
{
    boolean added;
    
    public boolean isSharable() {
        final Class<? extends ChannelHandlerAdapter> class1 = this.getClass();
        final Map handlerSharableCache = InternalThreadLocalMap.get().handlerSharableCache();
        Boolean value = handlerSharableCache.get(class1);
        if (value == null) {
            value = class1.isAnnotationPresent(Sharable.class);
            handlerSharableCache.put(class1, value);
        }
        return value;
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        channelHandlerContext.fireExceptionCaught(t);
    }
}
