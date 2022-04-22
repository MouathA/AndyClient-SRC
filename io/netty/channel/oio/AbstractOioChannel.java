package io.netty.channel.oio;

import java.net.*;
import io.netty.channel.*;

public abstract class AbstractOioChannel extends AbstractChannel
{
    protected static final int SO_TIMEOUT = 1000;
    private boolean readPending;
    private final Runnable readTask;
    
    protected AbstractOioChannel(final Channel channel) {
        super(channel);
        this.readTask = new Runnable() {
            final AbstractOioChannel this$0;
            
            @Override
            public void run() {
                if (!this.this$0.isReadPending() && !this.this$0.config().isAutoRead()) {
                    return;
                }
                this.this$0.setReadPending(false);
                this.this$0.doRead();
            }
        };
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe(null);
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof ThreadPerChannelEventLoop;
    }
    
    protected abstract void doConnect(final SocketAddress p0, final SocketAddress p1) throws Exception;
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.isReadPending()) {
            return;
        }
        this.setReadPending(true);
        this.eventLoop().execute(this.readTask);
    }
    
    protected abstract void doRead();
    
    protected boolean isReadPending() {
        return this.readPending;
    }
    
    protected void setReadPending(final boolean readPending) {
        this.readPending = readPending;
    }
    
    private final class DefaultOioUnsafe extends AbstractUnsafe
    {
        final AbstractOioChannel this$0;
        
        private DefaultOioUnsafe(final AbstractOioChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            final boolean active = this.this$0.isActive();
            this.this$0.doConnect(socketAddress, socketAddress2);
            this.safeSetSuccess(channelPromise);
            if (!active && this.this$0.isActive()) {
                this.this$0.pipeline().fireChannelActive();
            }
        }
        
        DefaultOioUnsafe(final AbstractOioChannel abstractOioChannel, final AbstractOioChannel$1 runnable) {
            this(abstractOioChannel);
        }
    }
}
