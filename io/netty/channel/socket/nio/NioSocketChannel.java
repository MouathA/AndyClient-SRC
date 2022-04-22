package io.netty.channel.socket.nio;

import java.nio.channels.spi.*;
import io.netty.util.internal.*;
import io.netty.channel.nio.*;
import io.netty.buffer.*;
import java.nio.channels.*;
import java.nio.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import java.net.*;

public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel
{
    private static final ChannelMetadata METADATA;
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER;
    private final SocketChannelConfig config;
    
    private static java.nio.channels.SocketChannel newSocket(final SelectorProvider selectorProvider) {
        return selectorProvider.openSocketChannel();
    }
    
    public NioSocketChannel() {
        this(newSocket(NioSocketChannel.DEFAULT_SELECTOR_PROVIDER));
    }
    
    public NioSocketChannel(final SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }
    
    public NioSocketChannel(final java.nio.channels.SocketChannel socketChannel) {
        this(null, socketChannel);
    }
    
    public NioSocketChannel(final Channel channel, final java.nio.channels.SocketChannel socketChannel) {
        super(channel, socketChannel);
        this.config = new NioSocketChannelConfig(this, socketChannel.socket(), null);
    }
    
    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioSocketChannel.METADATA;
    }
    
    @Override
    public SocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    protected java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel)super.javaChannel();
    }
    
    @Override
    public boolean isActive() {
        final java.nio.channels.SocketChannel javaChannel = this.javaChannel();
        return javaChannel.isOpen() && javaChannel.isConnected();
    }
    
    @Override
    public boolean isInputShutdown() {
        return super.isInputShutdown();
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
    public boolean isOutputShutdown() {
        return this.javaChannel().socket().isOutputShutdown() || !this.isActive();
    }
    
    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }
    
    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        final NioEventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.javaChannel().socket().shutdownOutput();
            channelPromise.setSuccess();
        }
        else {
            eventLoop.execute(new OneTimeTask(channelPromise) {
                final ChannelPromise val$promise;
                final NioSocketChannel this$0;
                
                @Override
                public void run() {
                    this.this$0.shutdownOutput(this.val$promise);
                }
            });
        }
        return channelPromise;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.javaChannel().socket().bind(socketAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.javaChannel().socket().bind(socketAddress2);
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
    protected int doReadBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.writeBytes(this.javaChannel(), byteBuf.writableBytes());
    }
    
    @Override
    protected int doWriteBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes(this.javaChannel(), byteBuf.readableBytes());
    }
    
    @Override
    protected long doWriteFileRegion(final FileRegion fileRegion) throws Exception {
        return fileRegion.transferTo(this.javaChannel(), fileRegion.transfered());
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (channelOutboundBuffer.size() != 0) {
            long n = 0L;
            final ByteBuffer[] nioBuffers = channelOutboundBuffer.nioBuffers();
            final int nioBufferCount = channelOutboundBuffer.nioBufferCount();
            long nioBufferSize = channelOutboundBuffer.nioBufferSize();
            final java.nio.channels.SocketChannel javaChannel = this.javaChannel();
            switch (nioBufferCount) {
                case 0: {
                    super.doWrite(channelOutboundBuffer);
                    return;
                }
                case 1: {
                    final ByteBuffer byteBuffer = nioBuffers[0];
                    for (int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
                        final int write = javaChannel.write(byteBuffer);
                        if (write == 0) {
                            break;
                        }
                        nioBufferSize -= write;
                        n += write;
                        if (nioBufferSize == 0L) {
                            break;
                        }
                    }
                    break;
                }
                default: {
                    for (int j = this.config().getWriteSpinCount() - 1; j >= 0; --j) {
                        final long write2 = javaChannel.write(nioBuffers, 0, nioBufferCount);
                        if (write2 == 0L) {
                            break;
                        }
                        nioBufferSize -= write2;
                        n += write2;
                        if (nioBufferSize == 0L) {
                            break;
                        }
                    }
                    break;
                }
            }
            channelOutboundBuffer.removeBytes(n);
            if (!true) {
                this.incompleteWrite(true);
                return;
            }
        }
        this.clearOpWrite();
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
    
    static void access$100(final NioSocketChannel nioSocketChannel, final boolean readPending) {
        nioSocketChannel.setReadPending(readPending);
    }
    
    static {
        METADATA = new ChannelMetadata(false);
        DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    }
    
    private final class NioSocketChannelConfig extends DefaultSocketChannelConfig
    {
        final NioSocketChannel this$0;
        
        private NioSocketChannelConfig(final NioSocketChannel this$0, final NioSocketChannel nioSocketChannel, final Socket socket) {
            this.this$0 = this$0;
            super(nioSocketChannel, socket);
        }
        
        @Override
        protected void autoReadCleared() {
            NioSocketChannel.access$100(this.this$0, false);
        }
        
        NioSocketChannelConfig(final NioSocketChannel nioSocketChannel, final NioSocketChannel nioSocketChannel2, final Socket socket, final NioSocketChannel$1 oneTimeTask) {
            this(nioSocketChannel, nioSocketChannel2, socket);
        }
    }
}
