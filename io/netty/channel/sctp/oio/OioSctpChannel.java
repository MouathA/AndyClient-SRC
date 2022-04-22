package io.netty.channel.sctp.oio;

import io.netty.channel.oio.*;
import java.nio.channels.*;
import io.netty.buffer.*;
import java.nio.*;
import io.netty.util.internal.*;
import com.sun.nio.sctp.*;
import java.util.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.sctp.*;

public class OioSctpChannel extends AbstractOioMessageChannel implements SctpChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPE;
    private final com.sun.nio.sctp.SctpChannel ch;
    private final SctpChannelConfig config;
    private final Selector readSelector;
    private final Selector writeSelector;
    private final Selector connectSelector;
    private final NotificationHandler notificationHandler;
    private RecvByteBufAllocator.Handle allocHandle;
    
    private static com.sun.nio.sctp.SctpChannel openChannel() {
        return com.sun.nio.sctp.SctpChannel.open();
    }
    
    public OioSctpChannel() {
        this(openChannel());
    }
    
    public OioSctpChannel(final com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }
    
    public OioSctpChannel(final Channel channel, final com.sun.nio.sctp.SctpChannel ch) {
        super(channel);
        (this.ch = ch).configureBlocking(false);
        this.readSelector = Selector.open();
        this.writeSelector = Selector.open();
        this.connectSelector = Selector.open();
        ch.register(this.readSelector, 1);
        ch.register(this.writeSelector, 4);
        ch.register(this.connectSelector, 8);
        this.config = new OioSctpChannelConfig(this, ch, null);
        this.notificationHandler = new SctpNotificationHandler(this);
        if (!true) {
            ch.close();
        }
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
        return OioSctpChannel.METADATA;
    }
    
    @Override
    public SctpChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }
    
    @Override
    protected int doReadMessages(final List list) throws Exception {
        if (!this.readSelector.isOpen()) {
            return 0;
        }
        if (this.readSelector.select(1000L) <= 0) {
            return 0;
        }
        final Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
        for (final SelectionKey selectionKey : selectedKeys) {
            RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
            if (allocHandle == null) {
                allocHandle = (this.allocHandle = this.config().getRecvByteBufAllocator().newHandle());
            }
            final ByteBuf allocate = allocHandle.allocate(this.config().getAllocator());
            final ByteBuffer nioBuffer = allocate.nioBuffer(allocate.writerIndex(), allocate.writableBytes());
            final MessageInfo receive = this.ch.receive(nioBuffer, null, (NotificationHandler<Object>)this.notificationHandler);
            if (receive == null) {
                allocHandle.record(allocate.readableBytes());
                if (false) {
                    allocate.release();
                }
                selectedKeys.clear();
                return 0;
            }
            nioBuffer.flip();
            list.add(new SctpMessage(receive, allocate.writerIndex(allocate.writerIndex() + nioBuffer.remaining())));
            int n = 0;
            ++n;
            allocHandle.record(allocate.readableBytes());
            if (!false) {
                continue;
            }
            allocate.release();
        }
        selectedKeys.clear();
        return 0;
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (!this.writeSelector.isOpen()) {
            return;
        }
        final int size = channelOutboundBuffer.size();
        if (this.writeSelector.select(1000L) <= 0) {
            return;
        }
        final Set<SelectionKey> selectedKeys = this.writeSelector.selectedKeys();
        if (selectedKeys.isEmpty()) {
            return;
        }
        final Iterator<SelectionKey> iterator = selectedKeys.iterator();
        while (size) {
            iterator.next();
            iterator.remove();
            final SctpMessage sctpMessage = (SctpMessage)channelOutboundBuffer.current();
            if (sctpMessage == null) {
                return;
            }
            final ByteBuf content = sctpMessage.content();
            final int readableBytes = content.readableBytes();
            ByteBuffer byteBuffer;
            if (content.nioBufferCount() != -1) {
                byteBuffer = content.nioBuffer();
            }
            else {
                byteBuffer = ByteBuffer.allocate(readableBytes);
                content.getBytes(content.readerIndex(), byteBuffer);
                byteBuffer.flip();
            }
            final MessageInfo outgoing = MessageInfo.createOutgoing(this.association(), null, sctpMessage.streamIdentifier());
            outgoing.payloadProtocolID(sctpMessage.protocolIdentifier());
            outgoing.streamNumber(sctpMessage.streamIdentifier());
            this.ch.send(byteBuffer, outgoing);
            int n = 0;
            ++n;
            channelOutboundBuffer.remove();
            if (!iterator.hasNext()) {
                return;
            }
        }
    }
    
    @Override
    protected Object filterOutboundMessage(final Object o) throws Exception {
        if (o instanceof SctpMessage) {
            return o;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o) + OioSctpChannel.EXPECTED_TYPE);
    }
    
    @Override
    public Association association() {
        return this.ch.association();
    }
    
    @Override
    public boolean isActive() {
        return this.isOpen() && this.association() != null;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        final Iterator<SocketAddress> iterator = this.ch.getAllLocalAddresses().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    public Set allLocalAddresses() {
        final Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
        final LinkedHashSet set = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
        final Iterator<SocketAddress> iterator = allLocalAddresses.iterator();
        while (iterator.hasNext()) {
            set.add((InetSocketAddress)iterator.next());
        }
        return set;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        final Iterator<SocketAddress> iterator = this.ch.getRemoteAddresses().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    public Set allRemoteAddresses() {
        final Set<SocketAddress> remoteAddresses = this.ch.getRemoteAddresses();
        final LinkedHashSet set = new LinkedHashSet<InetSocketAddress>(remoteAddresses.size());
        final Iterator<SocketAddress> iterator = remoteAddresses.iterator();
        while (iterator.hasNext()) {
            set.add((InetSocketAddress)iterator.next());
        }
        return set;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.ch.bind(socketAddress);
    }
    
    @Override
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.ch.bind(socketAddress2);
        }
        this.ch.connect(socketAddress);
        while (!true) {
            if (this.connectSelector.select(1000L) >= 0) {
                final Set<SelectionKey> selectedKeys = this.connectSelector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().isConnectable()) {
                        selectedKeys.clear();
                        break;
                    }
                }
                selectedKeys.clear();
            }
        }
        this.ch.finishConnect();
        if (!false) {
            this.doClose();
        }
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        closeSelector("read", this.readSelector);
        closeSelector("write", this.writeSelector);
        closeSelector("connect", this.connectSelector);
        this.ch.close();
    }
    
    private static void closeSelector(final String s, final Selector selector) {
        selector.close();
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress) {
        return this.bindAddress(inetAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (this.eventLoop().inEventLoop()) {
            this.ch.bindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpChannel this$0;
                
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
            this.ch.unbindAddress(inetAddress);
            channelPromise.setSuccess();
        }
        else {
            this.eventLoop().execute(new Runnable(inetAddress, channelPromise) {
                final InetAddress val$localAddress;
                final ChannelPromise val$promise;
                final OioSctpChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.unbindAddress(this.val$localAddress, this.val$promise);
                }
            });
        }
        return channelPromise;
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
    
    static void access$100(final OioSctpChannel oioSctpChannel, final boolean readPending) {
        oioSctpChannel.setReadPending(readPending);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
        METADATA = new ChannelMetadata(false);
        EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')';
    }
    
    private final class OioSctpChannelConfig extends DefaultSctpChannelConfig
    {
        final OioSctpChannel this$0;
        
        private OioSctpChannelConfig(final OioSctpChannel this$0, final OioSctpChannel oioSctpChannel, final com.sun.nio.sctp.SctpChannel sctpChannel) {
            this.this$0 = this$0;
            super(oioSctpChannel, sctpChannel);
        }
        
        @Override
        protected void autoReadCleared() {
            OioSctpChannel.access$100(this.this$0, false);
        }
        
        OioSctpChannelConfig(final OioSctpChannel oioSctpChannel, final OioSctpChannel oioSctpChannel2, final com.sun.nio.sctp.SctpChannel sctpChannel, final OioSctpChannel$1 runnable) {
            this(oioSctpChannel, oioSctpChannel2, sctpChannel);
        }
    }
}
