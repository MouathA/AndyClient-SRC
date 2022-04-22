package io.netty.channel;

import io.netty.util.internal.*;
import io.netty.util.*;

public abstract class SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter
{
    private final TypeParameterMatcher matcher;
    private final boolean autoRelease;
    
    protected SimpleChannelInboundHandler() {
        this(true);
    }
    
    protected SimpleChannelInboundHandler(final boolean autoRelease) {
        this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
        this.autoRelease = autoRelease;
    }
    
    protected SimpleChannelInboundHandler(final Class clazz) {
        this(clazz, true);
    }
    
    protected SimpleChannelInboundHandler(final Class clazz, final boolean autoRelease) {
        this.matcher = TypeParameterMatcher.get(clazz);
        this.autoRelease = autoRelease;
    }
    
    public boolean acceptInboundMessage(final Object o) throws Exception {
        return this.matcher.match(o);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (this.acceptInboundMessage(o)) {
            this.channelRead0(channelHandlerContext, o);
        }
        else {
            channelHandlerContext.fireChannelRead(o);
        }
        if (this.autoRelease && false) {
            ReferenceCountUtil.release(o);
        }
    }
    
    protected abstract void channelRead0(final ChannelHandlerContext p0, final Object p1) throws Exception;
}
