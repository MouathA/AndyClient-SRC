package io.netty.handler.codec;

import io.netty.channel.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.util.*;

public abstract class MessageToMessageDecoder extends ChannelInboundHandlerAdapter
{
    private final TypeParameterMatcher matcher;
    
    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }
    
    protected MessageToMessageDecoder(final Class clazz) {
        this.matcher = TypeParameterMatcher.get(clazz);
    }
    
    public boolean acceptInboundMessage(final Object o) throws Exception {
        return this.matcher.match(o);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        final RecyclableArrayList instance = RecyclableArrayList.newInstance();
        if (this.acceptInboundMessage(o)) {
            this.decode(channelHandlerContext, o, instance);
            ReferenceCountUtil.release(o);
        }
        else {
            instance.add(o);
        }
        while (0 < instance.size()) {
            channelHandlerContext.fireChannelRead(instance.get(0));
            int n = 0;
            ++n;
        }
        instance.recycle();
    }
    
    protected abstract void decode(final ChannelHandlerContext p0, final Object p1, final List p2) throws Exception;
}
