package io.netty.channel.local;

import java.util.*;
import java.net.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;

public class LocalServerChannel extends AbstractServerChannel
{
    private final ChannelConfig config;
    private final Queue inboundBuffer;
    private final Runnable shutdownHook;
    private int state;
    private LocalAddress localAddress;
    private boolean acceptInProgress;
    
    public LocalServerChannel() {
        this.config = new DefaultChannelConfig(this);
        this.inboundBuffer = new ArrayDeque();
        this.shutdownHook = new Runnable() {
            final LocalServerChannel this$0;
            
            @Override
            public void run() {
                this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
            }
        };
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
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
        return this.state < 2;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 1;
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
    protected void doRegister() throws Exception {
        ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = 1;
    }
    
    @Override
    protected void doClose() throws Exception {
        if (this.state <= 1) {
            if (this.localAddress != null) {
                LocalChannelRegistry.unregister(this.localAddress);
                this.localAddress = null;
            }
            this.state = 2;
        }
    }
    
    @Override
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.acceptInProgress) {
            return;
        }
        final Queue inboundBuffer = this.inboundBuffer;
        if (inboundBuffer.isEmpty()) {
            this.acceptInProgress = true;
            return;
        }
        final ChannelPipeline pipeline = this.pipeline();
        while (true) {
            final Object poll = inboundBuffer.poll();
            if (poll == null) {
                break;
            }
            pipeline.fireChannelRead(poll);
        }
        pipeline.fireChannelReadComplete();
    }
    
    LocalChannel serve(final LocalChannel localChannel) {
        final LocalChannel localChannel2 = new LocalChannel(this, localChannel);
        if (this.eventLoop().inEventLoop()) {
            this.serve0(localChannel2);
        }
        else {
            this.eventLoop().execute(new Runnable(localChannel2) {
                final LocalChannel val$child;
                final LocalServerChannel this$0;
                
                @Override
                public void run() {
                    LocalServerChannel.access$000(this.this$0, this.val$child);
                }
            });
        }
        return localChannel2;
    }
    
    private void serve0(final LocalChannel localChannel) {
        this.inboundBuffer.add(localChannel);
        if (this.acceptInProgress) {
            this.acceptInProgress = false;
            final ChannelPipeline pipeline = this.pipeline();
            while (true) {
                final Object poll = this.inboundBuffer.poll();
                if (poll == null) {
                    break;
                }
                pipeline.fireChannelRead(poll);
            }
            pipeline.fireChannelReadComplete();
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
    
    static void access$000(final LocalServerChannel localServerChannel, final LocalChannel localChannel) {
        localServerChannel.serve0(localChannel);
    }
}
