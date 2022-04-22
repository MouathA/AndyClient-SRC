package io.netty.channel.sctp.nio;

import io.netty.channel.nio.*;
import java.nio.channels.*;
import java.util.*;
import com.sun.nio.sctp.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.channel.sctp.*;

public class NioSctpServerChannel extends AbstractNioMessageChannel implements SctpServerChannel
{
    private static final ChannelMetadata METADATA;
    private final SctpServerChannelConfig config;
    
    private static com.sun.nio.sctp.SctpServerChannel newSocket() {
        return com.sun.nio.sctp.SctpServerChannel.open();
    }
    
    public NioSctpServerChannel() {
        super(null, newSocket(), 16);
        this.config = new NioSctpServerChannelConfig(this, this.javaChannel(), null);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioSctpServerChannel.METADATA;
    }
    
    @Override
    public Set allLocalAddresses() {
        final Set<SocketAddress> allLocalAddresses = this.javaChannel().getAllLocalAddresses();
        final LinkedHashSet set = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
        final Iterator<SocketAddress> iterator = allLocalAddresses.iterator();
        while (iterator.hasNext()) {
            set.add((InetSocketAddress)iterator.next());
        }
        return set;
    }
    
    @Override
    public SctpServerChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isActive() {
        return this.isOpen() && !this.allLocalAddresses().isEmpty();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    protected com.sun.nio.sctp.SctpServerChannel javaChannel() {
        return (com.sun.nio.sctp.SctpServerChannel)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        final Iterator<SocketAddress> iterator = this.javaChannel().getAllLocalAddresses().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().bind(socketAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final SctpChannel accept = this.javaChannel().accept();
        if (accept == null) {
            return 0;
        }
        list.add(new NioSctpChannel(this, accept));
        return 1;
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress) {
        return this.bindAddress(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            this.javaChannel().bindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final NioSctpServerChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.bindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    public ChannelFuture unbindAddress(final InetAddress inetAddress) {
        return this.unbindAddress(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture unbindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            this.javaChannel().unbindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final NioSctpServerChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.unbindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
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
    protected boolean doWriteMessage(final Object o, final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected SelectableChannel javaChannel() {
        return this.javaChannel();
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }
    
    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    static void access$100(final NioSctpServerChannel nioSctpServerChannel, final boolean readPending) {
        nioSctpServerChannel.setReadPending(readPending);
    }
    
    static {
        METADATA = new ChannelMetadata(false);
    }
    
    private final class NioSctpServerChannelConfig extends DefaultSctpServerChannelConfig
    {
        final NioSctpServerChannel this$0;
        
        private NioSctpServerChannelConfig(final NioSctpServerChannel this$0, final NioSctpServerChannel nioSctpServerChannel, final com.sun.nio.sctp.SctpServerChannel sctpServerChannel) {
            this.this$0 = this$0;
            super(nioSctpServerChannel, sctpServerChannel);
        }
        
        @Override
        protected void autoReadCleared() {
            NioSctpServerChannel.access$100(this.this$0, false);
        }
        
        NioSctpServerChannelConfig(final NioSctpServerChannel nioSctpServerChannel, final NioSctpServerChannel nioSctpServerChannel2, final com.sun.nio.sctp.SctpServerChannel sctpServerChannel, final NioSctpServerChannel$1 runnable) {
            this(nioSctpServerChannel, nioSctpServerChannel2, sctpServerChannel);
        }
    }
}
