package io.netty.channel.embedded;

import java.net.*;
import java.util.*;
import io.netty.util.internal.*;
import java.nio.channels.*;
import io.netty.util.*;
import io.netty.util.internal.logging.*;
import io.netty.channel.*;

public class EmbeddedChannel extends AbstractChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private final EmbeddedEventLoop loop;
    private final ChannelConfig config;
    private final SocketAddress localAddress;
    private final SocketAddress remoteAddress;
    private final Queue inboundMessages;
    private final Queue outboundMessages;
    private Throwable lastException;
    private int state;
    static final boolean $assertionsDisabled;
    
    public EmbeddedChannel(final ChannelHandler... array) {
        super(null);
        this.loop = new EmbeddedEventLoop();
        this.config = new DefaultChannelConfig(this);
        this.localAddress = new EmbeddedSocketAddress();
        this.remoteAddress = new EmbeddedSocketAddress();
        this.inboundMessages = new ArrayDeque();
        this.outboundMessages = new ArrayDeque();
        if (array == null) {
            throw new NullPointerException("handlers");
        }
        final ChannelPipeline pipeline = this.pipeline();
        while (0 < array.length) {
            final ChannelHandler channelHandler = array[0];
            if (channelHandler == null) {
                break;
            }
            int n = 0;
            ++n;
            pipeline.addLast(channelHandler);
            int n2 = 0;
            ++n2;
        }
        if (!false) {
            throw new IllegalArgumentException("handlers is empty.");
        }
        this.loop.register(this);
        pipeline.addLast(new LastInboundHandler(null));
    }
    
    @Override
    public ChannelMetadata metadata() {
        return EmbeddedChannel.METADATA;
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return this.state < 2;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 1;
    }
    
    public Queue inboundMessages() {
        return this.inboundMessages;
    }
    
    @Deprecated
    public Queue lastInboundBuffer() {
        return this.inboundMessages();
    }
    
    public Queue outboundMessages() {
        return this.outboundMessages;
    }
    
    @Deprecated
    public Queue lastOutboundBuffer() {
        return this.outboundMessages();
    }
    
    public Object readInbound() {
        return this.inboundMessages.poll();
    }
    
    public Object readOutbound() {
        return this.outboundMessages.poll();
    }
    
    public boolean writeInbound(final Object... array) {
        this.ensureOpen();
        if (array.length == 0) {
            return !this.inboundMessages.isEmpty();
        }
        final ChannelPipeline pipeline = this.pipeline();
        while (0 < array.length) {
            pipeline.fireChannelRead(array[0]);
            int n = 0;
            ++n;
        }
        pipeline.fireChannelReadComplete();
        this.runPendingTasks();
        this.checkException();
        return !this.inboundMessages.isEmpty();
    }
    
    public boolean writeOutbound(final Object... array) {
        this.ensureOpen();
        if (array.length == 0) {
            return !this.outboundMessages.isEmpty();
        }
        final RecyclableArrayList instance = RecyclableArrayList.newInstance(array.length);
        int length = array.length;
        while (0 < 0) {
            final Object o = array[0];
            if (o == null) {
                break;
            }
            instance.add(this.write(o));
            int n = 0;
            ++n;
        }
        this.flush();
        while (0 < instance.size()) {
            final ChannelFuture channelFuture = instance.get(0);
            assert channelFuture.isDone();
            if (channelFuture.cause() != null) {
                this.recordException(channelFuture.cause());
            }
            ++length;
        }
        this.runPendingTasks();
        this.checkException();
        final boolean b = !this.outboundMessages.isEmpty();
        instance.recycle();
        return false;
    }
    
    public boolean finish() {
        this.close();
        this.runPendingTasks();
        this.checkException();
        return !this.inboundMessages.isEmpty() || !this.outboundMessages.isEmpty();
    }
    
    public void runPendingTasks() {
        this.loop.runTasks();
    }
    
    private void recordException(final Throwable lastException) {
        if (this.lastException == null) {
            this.lastException = lastException;
        }
        else {
            EmbeddedChannel.logger.warn("More than one exception was raised. Will report only the first one and log others.", lastException);
        }
    }
    
    public void checkException() {
        final Throwable lastException = this.lastException;
        if (lastException == null) {
            return;
        }
        this.lastException = null;
        PlatformDependent.throwException(lastException);
    }
    
    protected final void ensureOpen() {
        if (!this.isOpen()) {
            this.recordException(new ClosedChannelException());
            this.checkException();
        }
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof EmbeddedEventLoop;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.isActive() ? this.localAddress : null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.isActive() ? this.remoteAddress : null;
    }
    
    @Override
    protected void doRegister() throws Exception {
        this.state = 1;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.state = 2;
    }
    
    @Override
    protected void doBeginRead() throws Exception {
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultUnsafe(null);
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            final Object current = channelOutboundBuffer.current();
            if (current == null) {
                break;
            }
            ReferenceCountUtil.retain(current);
            this.outboundMessages.add(current);
            channelOutboundBuffer.remove();
        }
    }
    
    static Queue access$200(final EmbeddedChannel embeddedChannel) {
        return embeddedChannel.inboundMessages;
    }
    
    static void access$300(final EmbeddedChannel embeddedChannel, final Throwable t) {
        embeddedChannel.recordException(t);
    }
    
    static {
        $assertionsDisabled = !EmbeddedChannel.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
        METADATA = new ChannelMetadata(false);
    }
    
    private final class LastInboundHandler extends ChannelInboundHandlerAdapter
    {
        final EmbeddedChannel this$0;
        
        private LastInboundHandler(final EmbeddedChannel this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
            EmbeddedChannel.access$200(this.this$0).add(o);
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
            EmbeddedChannel.access$300(this.this$0, t);
        }
        
        LastInboundHandler(final EmbeddedChannel embeddedChannel, final EmbeddedChannel$1 object) {
            this(embeddedChannel);
        }
    }
    
    private class DefaultUnsafe extends AbstractUnsafe
    {
        final EmbeddedChannel this$0;
        
        private DefaultUnsafe(final EmbeddedChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            this.safeSetSuccess(channelPromise);
        }
        
        DefaultUnsafe(final EmbeddedChannel embeddedChannel, final EmbeddedChannel$1 object) {
            this(embeddedChannel);
        }
    }
}
