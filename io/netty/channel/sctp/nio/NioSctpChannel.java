package io.netty.channel.sctp.nio;

import io.netty.channel.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.nio.*;
import com.sun.nio.sctp.*;
import io.netty.buffer.*;
import io.netty.util.internal.*;
import io.netty.util.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.sctp.*;

public class NioSctpChannel extends AbstractNioMessageChannel implements SctpChannel
{
    private static final ChannelMetadata METADATA;
    private static final InternalLogger logger;
    private final SctpChannelConfig config;
    private final NotificationHandler notificationHandler;
    private RecvByteBufAllocator.Handle allocHandle;
    
    private static com.sun.nio.sctp.SctpChannel newSctpChannel() {
        return com.sun.nio.sctp.SctpChannel.open();
    }
    
    public NioSctpChannel() {
        this(newSctpChannel());
    }
    
    public NioSctpChannel(final com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }
    
    public NioSctpChannel(final Channel channel, final com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(channel, sctpChannel, 1);
        sctpChannel.configureBlocking(false);
        this.config = new NioSctpChannelConfig(this, sctpChannel, null);
        this.notificationHandler = new SctpNotificationHandler(this);
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    public SctpServerChannel parent() {
        return (SctpServerChannel)super.parent();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioSctpChannel.METADATA;
    }
    
    @Override
    public Association association() {
        return this.javaChannel().association();
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
    public SctpChannelConfig config() {
        return this.config;
    }
    
    @Override
    public Set allRemoteAddresses() {
        final Set<SocketAddress> remoteAddresses = this.javaChannel().getRemoteAddresses();
        final HashSet set = new HashSet<InetSocketAddress>(remoteAddresses.size());
        final Iterator<SocketAddress> iterator = remoteAddresses.iterator();
        while (iterator.hasNext()) {
            set.add((InetSocketAddress)iterator.next());
        }
        return set;
    }
    
    @Override
    protected com.sun.nio.sctp.SctpChannel javaChannel() {
        return (com.sun.nio.sctp.SctpChannel)super.javaChannel();
    }
    
    @Override
    public boolean isActive() {
        return this.javaChannel().isOpen() && this.association() != null;
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
    protected SocketAddress remoteAddress0() {
        final Iterator<SocketAddress> iterator = this.javaChannel().getRemoteAddresses().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().bind(socketAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.javaChannel().bind(socketAddress2);
        }
        final boolean connect = this.javaChannel().connect(socketAddress);
        if (!connect) {
            this.selectionKey().interestOps(8);
        }
        final boolean b = connect;
        if (!true) {
            this.doClose();
        }
        return b;
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error();
        }
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        final com.sun.nio.sctp.SctpChannel javaChannel = this.javaChannel();
        RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
        if (allocHandle == null) {
            allocHandle = (this.allocHandle = this.config().getRecvByteBufAllocator().newHandle());
        }
        final ByteBuf allocate = allocHandle.allocate(this.config().getAllocator());
        final ByteBuffer internalNioBuffer = allocate.internalNioBuffer(allocate.writerIndex(), allocate.writableBytes());
        internalNioBuffer.position();
        final MessageInfo receive = javaChannel.receive(internalNioBuffer, null, (NotificationHandler<Object>)this.notificationHandler);
        if (receive == null) {
            allocHandle.record(allocate.readableBytes());
            if (false) {
                allocate.release();
            }
            return 1;
        }
        list.add(new SctpMessage(receive, allocate.writerIndex(allocate.writerIndex() + internalNioBuffer.position() + 1)));
        allocHandle.record(allocate.readableBytes());
        if (false) {
            allocate.release();
        }
        return 1;
    }
    
    @Override
    protected boolean doWriteMessage(final Object o, final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        final SctpMessage sctpMessage = (SctpMessage)o;
        final ByteBuf content = sctpMessage.content();
        final int readableBytes = content.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        final ByteBufAllocator alloc = this.alloc();
        final boolean b = content.nioBufferCount() != 1;
        if (true || content.isDirect() || alloc.isDirectBufferPooled()) {}
        ByteBuffer byteBuffer;
        if (!true) {
            byteBuffer = content.nioBuffer();
        }
        else {
            byteBuffer = alloc.directBuffer(readableBytes).writeBytes(content).nioBuffer();
        }
        final MessageInfo outgoing = MessageInfo.createOutgoing(this.association(), null, sctpMessage.streamIdentifier());
        outgoing.payloadProtocolID(sctpMessage.protocolIdentifier());
        outgoing.streamNumber(sctpMessage.streamIdentifier());
        return this.javaChannel().send(byteBuffer, outgoing) > 0;
    }
    
    @Override
    protected final Object filterOutboundMessage(final Object o) throws Exception {
        if (!(o instanceof SctpMessage)) {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class));
        }
        final SctpMessage sctpMessage = (SctpMessage)o;
        final ByteBuf content = sctpMessage.content();
        if (content.isDirect() && content.nioBufferCount() == 1) {
            return sctpMessage;
        }
        return new SctpMessage(sctpMessage.protocolIdentifier(), sctpMessage.streamIdentifier(), this.newDirectBuffer(sctpMessage, content));
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
                final NioSctpChannel this$0;
                
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
                final NioSctpChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.unbindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
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
    public Channel parent() {
        return this.parent();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    static void access$100(final NioSctpChannel nioSctpChannel, final boolean readPending) {
        nioSctpChannel.setReadPending(readPending);
    }
    
    static {
        METADATA = new ChannelMetadata(false);
        logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
    }
    
    private final class NioSctpChannelConfig extends DefaultSctpChannelConfig
    {
        final NioSctpChannel this$0;
        
        private NioSctpChannelConfig(final NioSctpChannel this$0, final NioSctpChannel nioSctpChannel, final com.sun.nio.sctp.SctpChannel sctpChannel) {
            this.this$0 = this$0;
            super(nioSctpChannel, sctpChannel);
        }
        
        @Override
        protected void autoReadCleared() {
            NioSctpChannel.access$100(this.this$0, false);
        }
        
        NioSctpChannelConfig(final NioSctpChannel nioSctpChannel, final NioSctpChannel nioSctpChannel2, final com.sun.nio.sctp.SctpChannel sctpChannel, final NioSctpChannel$1 runnable) {
            this(nioSctpChannel, nioSctpChannel2, sctpChannel);
        }
    }
}
