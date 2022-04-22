package io.netty.channel.sctp.oio;

import io.netty.channel.oio.*;
import java.util.*;
import java.nio.channels.*;
import com.sun.nio.sctp.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.sctp.*;

public class OioSctpServerChannel extends AbstractOioMessageChannel implements SctpServerChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private final com.sun.nio.sctp.SctpServerChannel sch;
    private final SctpServerChannelConfig config;
    private final Selector selector;
    
    private static com.sun.nio.sctp.SctpServerChannel newServerSocket() {
        return com.sun.nio.sctp.SctpServerChannel.open();
    }
    
    public OioSctpServerChannel() {
        this(newServerSocket());
    }
    
    public OioSctpServerChannel(final com.sun.nio.sctp.SctpServerChannel sch) {
        super(null);
        if (sch == null) {
            throw new NullPointerException("sctp server channel");
        }
        (this.sch = sch).configureBlocking(false);
        sch.register(this.selector = Selector.open(), 16);
        this.config = new OioSctpServerChannelConfig(this, sch, null);
        if (!true) {
            sch.close();
        }
    }
    
    @Override
    public ChannelMetadata metadata() {
        return OioSctpServerChannel.METADATA;
    }
    
    @Override
    public SctpServerChannelConfig config() {
        return this.config;
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
    public boolean isOpen() {
        return this.sch.isOpen();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        final Iterator<SocketAddress> iterator = this.sch.getAllLocalAddresses().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    public Set allLocalAddresses() {
        final Set<SocketAddress> allLocalAddresses = this.sch.getAllLocalAddresses();
        final LinkedHashSet set = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
        final Iterator<SocketAddress> iterator = allLocalAddresses.iterator();
        while (iterator.hasNext()) {
            set.add((InetSocketAddress)iterator.next());
        }
        return set;
    }
    
    @Override
    public boolean isActive() {
        return this.isOpen() && this.localAddress0() != null;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.sch.bind(socketAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doClose() throws Exception {
        this.selector.close();
        this.sch.close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        if (!this.isActive()) {
            return -1;
        }
        if (this.selector.select(1000L) > 0) {
            final Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            do {
                final SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    final SctpChannel accept = this.sch.accept();
                    if (accept != null) {
                        list.add(new OioSctpChannel(this, accept));
                        int n = 0;
                        ++n;
                    }
                }
            } while (iterator.hasNext());
            return 0;
        }
        return 0;
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress) {
        return this.bindAddress(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            this.sch.bindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpServerChannel this$0;
                
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
            this.sch.unbindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpServerChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.unbindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
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
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) throws Exception {
        throw new UnsupportedOperationException();
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
    
    static void access$100(final OioSctpServerChannel oioSctpServerChannel, final boolean readPending) {
        oioSctpServerChannel.setReadPending(readPending);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OioSctpServerChannel.class);
        METADATA = new ChannelMetadata(false);
    }
    
    private final class OioSctpServerChannelConfig extends DefaultSctpServerChannelConfig
    {
        final OioSctpServerChannel this$0;
        
        private OioSctpServerChannelConfig(final OioSctpServerChannel this$0, final OioSctpServerChannel oioSctpServerChannel, final com.sun.nio.sctp.SctpServerChannel sctpServerChannel) {
            this.this$0 = this$0;
            super(oioSctpServerChannel, sctpServerChannel);
        }
        
        @Override
        protected void autoReadCleared() {
            OioSctpServerChannel.access$100(this.this$0, false);
        }
        
        OioSctpServerChannelConfig(final OioSctpServerChannel oioSctpServerChannel, final OioSctpServerChannel oioSctpServerChannel2, final com.sun.nio.sctp.SctpServerChannel sctpServerChannel, final OioSctpServerChannel$1 runnable) {
            this(oioSctpServerChannel, oioSctpServerChannel2, sctpServerChannel);
        }
    }
}
