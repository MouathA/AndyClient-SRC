package io.netty.channel.local;

import java.net.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.*;
import io.netty.util.*;
import java.util.*;
import java.nio.channels.*;
import io.netty.channel.*;

public class LocalChannel extends AbstractChannel
{
    private static final ChannelMetadata METADATA;
    private static final int MAX_READER_STACK_DEPTH = 8;
    private final ChannelConfig config;
    private final Queue inboundBuffer;
    private final Runnable readTask;
    private final Runnable shutdownHook;
    private int state;
    private LocalChannel peer;
    private LocalAddress localAddress;
    private LocalAddress remoteAddress;
    private ChannelPromise connectPromise;
    private boolean readInProgress;
    private boolean registerInProgress;
    
    public LocalChannel() {
        super(null);
        this.config = new DefaultChannelConfig(this);
        this.inboundBuffer = new ArrayDeque();
        this.readTask = new Runnable() {
            final LocalChannel this$0;
            
            @Override
            public void run() {
                final ChannelPipeline pipeline = this.this$0.pipeline();
                while (true) {
                    final Object poll = LocalChannel.access$000(this.this$0).poll();
                    if (poll == null) {
                        break;
                    }
                    pipeline.fireChannelRead(poll);
                }
                pipeline.fireChannelReadComplete();
            }
        };
        this.shutdownHook = new Runnable() {
            final LocalChannel this$0;
            
            @Override
            public void run() {
                this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
            }
        };
    }
    
    LocalChannel(final LocalServerChannel localServerChannel, final LocalChannel peer) {
        super(localServerChannel);
        this.config = new DefaultChannelConfig(this);
        this.inboundBuffer = new ArrayDeque();
        this.readTask = new Runnable() {
            final LocalChannel this$0;
            
            @Override
            public void run() {
                final ChannelPipeline pipeline = this.this$0.pipeline();
                while (true) {
                    final Object poll = LocalChannel.access$000(this.this$0).poll();
                    if (poll == null) {
                        break;
                    }
                    pipeline.fireChannelRead(poll);
                }
                pipeline.fireChannelReadComplete();
            }
        };
        this.shutdownHook = new Runnable() {
            final LocalChannel this$0;
            
            @Override
            public void run() {
                this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
            }
        };
        this.peer = peer;
        this.localAddress = localServerChannel.localAddress();
        this.remoteAddress = peer.localAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return LocalChannel.METADATA;
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
    }
    
    @Override
    public LocalServerChannel parent() {
        return (LocalServerChannel)super.parent();
    }
    
    @Override
    public LocalAddress localAddress() {
        return (LocalAddress)super.localAddress();
    }
    
    @Override
    public LocalAddress remoteAddress() {
        return (LocalAddress)super.remoteAddress();
    }
    
    @Override
    public boolean isOpen() {
        return this.state < 3;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 2;
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new LocalUnsafe(null);
    }
    
    @Override
    protected boolean isCompatible(final EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }
    
    @Override
    protected void doRegister() throws Exception {
        if (this.peer != null && this.parent() != null) {
            final LocalChannel peer = this.peer;
            this.registerInProgress = true;
            this.state = 2;
            peer.remoteAddress = this.parent().localAddress();
            peer.state = 2;
            peer.eventLoop().execute(new Runnable(peer) {
                final LocalChannel val$peer;
                final LocalChannel this$0;
                
                @Override
                public void run() {
                    LocalChannel.access$202(this.this$0, false);
                    this.val$peer.pipeline().fireChannelActive();
                    LocalChannel.access$300(this.val$peer).setSuccess();
                }
            });
        }
        ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = 1;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        if (this.state <= 2) {
            if (this.localAddress != null) {
                if (this.parent() == null) {
                    LocalChannelRegistry.unregister(this.localAddress);
                }
                this.localAddress = null;
            }
            this.state = 3;
        }
        final LocalChannel peer = this.peer;
        if (peer != null && peer.isActive()) {
            if (peer.eventLoop().inEventLoop() && !this.registerInProgress) {
                peer.unsafe().close(this.unsafe().voidPromise());
            }
            else {
                peer.eventLoop().execute(new Runnable(peer) {
                    final LocalChannel val$peer;
                    final LocalChannel this$0;
                    
                    @Override
                    public void run() {
                        this.val$peer.unsafe().close(this.this$0.unsafe().voidPromise());
                    }
                });
            }
            this.peer = null;
        }
    }
    
    @Override
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.readInProgress) {
            return;
        }
        final ChannelPipeline pipeline = this.pipeline();
        final Queue inboundBuffer = this.inboundBuffer;
        if (inboundBuffer.isEmpty()) {
            this.readInProgress = true;
            return;
        }
        final InternalThreadLocalMap value = InternalThreadLocalMap.get();
        final Integer value2 = value.localChannelReaderStackDepth();
        if (value2 < 8) {
            value.setLocalChannelReaderStackDepth(value2 + 1);
            while (true) {
                final Object poll = inboundBuffer.poll();
                if (poll == null) {
                    break;
                }
                pipeline.fireChannelRead(poll);
            }
            pipeline.fireChannelReadComplete();
            value.setLocalChannelReaderStackDepth(value2);
        }
        else {
            this.eventLoop().execute(this.readTask);
        }
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (this.state < 2) {
            throw new NotYetConnectedException();
        }
        if (this.state > 2) {
            throw new ClosedChannelException();
        }
        final LocalChannel peer = this.peer;
        final ChannelPipeline pipeline = peer.pipeline();
        final EventLoop eventLoop = peer.eventLoop();
        if (eventLoop == this.eventLoop()) {
            while (true) {
                final Object current = channelOutboundBuffer.current();
                if (current == null) {
                    break;
                }
                peer.inboundBuffer.add(current);
                ReferenceCountUtil.retain(current);
                channelOutboundBuffer.remove();
            }
            finishPeerRead(peer, pipeline);
        }
        else {
            final Object[] array = new Object[channelOutboundBuffer.size()];
            while (0 < array.length) {
                array[0] = ReferenceCountUtil.retain(channelOutboundBuffer.current());
                channelOutboundBuffer.remove();
                int n = 0;
                ++n;
            }
            eventLoop.execute(new Runnable(peer, array, pipeline) {
                final LocalChannel val$peer;
                final Object[] val$msgsCopy;
                final ChannelPipeline val$peerPipeline;
                final LocalChannel this$0;
                
                @Override
                public void run() {
                    Collections.addAll(LocalChannel.access$000(this.val$peer), this.val$msgsCopy);
                    LocalChannel.access$400(this.val$peer, this.val$peerPipeline);
                }
            });
        }
    }
    
    private static void finishPeerRead(final LocalChannel localChannel, final ChannelPipeline channelPipeline) {
        if (localChannel.readInProgress) {
            localChannel.readInProgress = false;
            while (true) {
                final Object poll = localChannel.inboundBuffer.poll();
                if (poll == null) {
                    break;
                }
                channelPipeline.fireChannelRead(poll);
            }
            channelPipeline.fireChannelReadComplete();
        }
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
    
    static Queue access$000(final LocalChannel localChannel) {
        return localChannel.inboundBuffer;
    }
    
    static boolean access$202(final LocalChannel localChannel, final boolean registerInProgress) {
        return localChannel.registerInProgress = registerInProgress;
    }
    
    static ChannelPromise access$300(final LocalChannel localChannel) {
        return localChannel.connectPromise;
    }
    
    static void access$400(final LocalChannel localChannel, final ChannelPipeline channelPipeline) {
        finishPeerRead(localChannel, channelPipeline);
    }
    
    static int access$500(final LocalChannel localChannel) {
        return localChannel.state;
    }
    
    static ChannelPromise access$302(final LocalChannel localChannel, final ChannelPromise connectPromise) {
        return localChannel.connectPromise = connectPromise;
    }
    
    static LocalChannel access$602(final LocalChannel localChannel, final LocalChannel peer) {
        return localChannel.peer = peer;
    }
    
    static {
        METADATA = new ChannelMetadata(false);
    }
    
    private class LocalUnsafe extends AbstractUnsafe
    {
        final LocalChannel this$0;
        
        private LocalUnsafe(final LocalChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            if (LocalChannel.access$500(this.this$0) == 2) {
                final AlreadyConnectedException ex = new AlreadyConnectedException();
                this.safeSetFailure(channelPromise, ex);
                this.this$0.pipeline().fireExceptionCaught(ex);
                return;
            }
            if (LocalChannel.access$300(this.this$0) != null) {
                throw new ConnectionPendingException();
            }
            LocalChannel.access$302(this.this$0, channelPromise);
            if (LocalChannel.access$500(this.this$0) != 1 && socketAddress2 == null) {
                socketAddress2 = new LocalAddress(this.this$0);
            }
            if (socketAddress2 != null) {
                this.this$0.doBind(socketAddress2);
            }
            final Channel value = LocalChannelRegistry.get(socketAddress);
            if (!(value instanceof LocalServerChannel)) {
                this.safeSetFailure(channelPromise, new ChannelException("connection refused"));
                this.close(this.voidPromise());
                return;
            }
            LocalChannel.access$602(this.this$0, ((LocalServerChannel)value).serve(this.this$0));
        }
        
        LocalUnsafe(final LocalChannel localChannel, final LocalChannel$1 runnable) {
            this(localChannel);
        }
    }
}
