package io.netty.channel;

import java.net.*;

public abstract class AbstractServerChannel extends AbstractChannel implements ServerChannel
{
    private static final ChannelMetadata METADATA;
    
    protected AbstractServerChannel() {
        super(null);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AbstractServerChannel.METADATA;
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultServerUnsafe(null);
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    static {
        METADATA = new ChannelMetadata(false);
    }
    
    private final class DefaultServerUnsafe extends AbstractUnsafe
    {
        final AbstractServerChannel this$0;
        
        private DefaultServerUnsafe(final AbstractServerChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            this.safeSetFailure(channelPromise, new UnsupportedOperationException());
        }
        
        DefaultServerUnsafe(final AbstractServerChannel abstractServerChannel, final AbstractServerChannel$1 object) {
            this(abstractServerChannel);
        }
    }
}
