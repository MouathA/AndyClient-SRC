package io.netty.bootstrap;

import java.net.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.*;
import java.util.*;
import io.netty.util.internal.logging.*;

public final class Bootstrap extends AbstractBootstrap
{
    private static final InternalLogger logger;
    private SocketAddress remoteAddress;
    
    public Bootstrap() {
    }
    
    private Bootstrap(final Bootstrap bootstrap) {
        super(bootstrap);
        this.remoteAddress = bootstrap.remoteAddress;
    }
    
    public Bootstrap remoteAddress(final SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }
    
    public Bootstrap remoteAddress(final String s, final int n) {
        this.remoteAddress = new InetSocketAddress(s, n);
        return this;
    }
    
    public Bootstrap remoteAddress(final InetAddress inetAddress, final int n) {
        this.remoteAddress = new InetSocketAddress(inetAddress, n);
        return this;
    }
    
    public ChannelFuture connect() {
        this.validate();
        final SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            throw new IllegalStateException("remoteAddress not set");
        }
        return this.doConnect(remoteAddress, this.localAddress());
    }
    
    public ChannelFuture connect(final String s, final int n) {
        return this.connect(new InetSocketAddress(s, n));
    }
    
    public ChannelFuture connect(final InetAddress inetAddress, final int n) {
        return this.connect(new InetSocketAddress(inetAddress, n));
    }
    
    public ChannelFuture connect(final SocketAddress socketAddress) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.validate();
        return this.doConnect(socketAddress, this.localAddress());
    }
    
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.validate();
        return this.doConnect(socketAddress, socketAddress2);
    }
    
    private ChannelFuture doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        final ChannelFuture initAndRegister = this.initAndRegister();
        final Channel channel = initAndRegister.channel();
        if (initAndRegister.cause() != null) {
            return initAndRegister;
        }
        final ChannelPromise promise = channel.newPromise();
        if (initAndRegister.isDone()) {
            doConnect0(initAndRegister, channel, socketAddress, socketAddress2, promise);
        }
        else {
            initAndRegister.addListener((GenericFutureListener)new ChannelFutureListener(initAndRegister, channel, socketAddress, socketAddress2, promise) {
                final ChannelFuture val$regFuture;
                final Channel val$channel;
                final SocketAddress val$remoteAddress;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final Bootstrap this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    Bootstrap.access$000(this.val$regFuture, this.val$channel, this.val$remoteAddress, this.val$localAddress, this.val$promise);
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return promise;
    }
    
    private static void doConnect0(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        channel.eventLoop().execute(new Runnable(channelFuture, socketAddress2, channel, socketAddress, channelPromise) {
            final ChannelFuture val$regFuture;
            final SocketAddress val$localAddress;
            final Channel val$channel;
            final SocketAddress val$remoteAddress;
            final ChannelPromise val$promise;
            
            @Override
            public void run() {
                if (this.val$regFuture.isSuccess()) {
                    if (this.val$localAddress == null) {
                        this.val$channel.connect(this.val$remoteAddress, this.val$promise);
                    }
                    else {
                        this.val$channel.connect(this.val$remoteAddress, this.val$localAddress, this.val$promise);
                    }
                    this.val$promise.addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                else {
                    this.val$promise.setFailure(this.val$regFuture.cause());
                }
            }
        });
    }
    
    @Override
    void init(final Channel channel) throws Exception {
        channel.pipeline().addLast(this.handler());
        final Map options = this.options();
        // monitorenter(map = options)
        for (final Map.Entry<ChannelOption, V> entry : options.entrySet()) {
            if (!channel.config().setOption(entry.getKey(), entry.getValue())) {
                Bootstrap.logger.warn("Unknown channel option: " + entry);
            }
        }
        // monitorexit(map)
        final Map attrs = this.attrs();
        // monitorenter(map2 = attrs)
        for (final Map.Entry<AttributeKey, V> entry2 : attrs.entrySet()) {
            channel.attr(entry2.getKey()).set(entry2.getValue());
        }
    }
    // monitorexit(map2)
    
    @Override
    public Bootstrap validate() {
        super.validate();
        if (this.handler() == null) {
            throw new IllegalStateException("handler not set");
        }
        return this;
    }
    
    @Override
    public Bootstrap clone() {
        return new Bootstrap(this);
    }
    
    @Override
    public String toString() {
        if (this.remoteAddress == null) {
            return super.toString();
        }
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        sb.append(", remoteAddress: ");
        sb.append(this.remoteAddress);
        sb.append(')');
        return sb.toString();
    }
    
    @Override
    public AbstractBootstrap clone() {
        return this.clone();
    }
    
    @Override
    public AbstractBootstrap validate() {
        return this.validate();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static void access$000(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        doConnect0(channelFuture, channel, socketAddress, socketAddress2, channelPromise);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(Bootstrap.class);
    }
}
