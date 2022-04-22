package io.netty.bootstrap;

import java.util.*;
import java.net.*;
import io.netty.util.*;
import io.netty.util.internal.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

public abstract class AbstractBootstrap implements Cloneable
{
    private EventLoopGroup group;
    private ChannelFactory channelFactory;
    private SocketAddress localAddress;
    private final Map options;
    private final Map attrs;
    private ChannelHandler handler;
    
    AbstractBootstrap() {
        this.options = new LinkedHashMap();
        this.attrs = new LinkedHashMap();
    }
    
    AbstractBootstrap(final AbstractBootstrap abstractBootstrap) {
        this.options = new LinkedHashMap();
        this.attrs = new LinkedHashMap();
        this.group = abstractBootstrap.group;
        this.channelFactory = abstractBootstrap.channelFactory;
        this.handler = abstractBootstrap.handler;
        this.localAddress = abstractBootstrap.localAddress;
        // monitorenter(options = abstractBootstrap.options)
        this.options.putAll(abstractBootstrap.options);
        // monitorexit(options)
        // monitorenter(attrs = abstractBootstrap.attrs)
        this.attrs.putAll(abstractBootstrap.attrs);
    }
    // monitorexit(attrs)
    
    public AbstractBootstrap group(final EventLoopGroup group) {
        if (group == null) {
            throw new NullPointerException("group");
        }
        if (this.group != null) {
            throw new IllegalStateException("group set already");
        }
        this.group = group;
        return this;
    }
    
    public AbstractBootstrap channel(final Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("channelClass");
        }
        return this.channelFactory(new BootstrapChannelFactory(clazz));
    }
    
    public AbstractBootstrap channelFactory(final ChannelFactory channelFactory) {
        if (channelFactory == null) {
            throw new NullPointerException("channelFactory");
        }
        if (this.channelFactory != null) {
            throw new IllegalStateException("channelFactory set already");
        }
        this.channelFactory = channelFactory;
        return this;
    }
    
    public AbstractBootstrap localAddress(final SocketAddress localAddress) {
        this.localAddress = localAddress;
        return this;
    }
    
    public AbstractBootstrap localAddress(final int n) {
        return this.localAddress(new InetSocketAddress(n));
    }
    
    public AbstractBootstrap localAddress(final String s, final int n) {
        return this.localAddress(new InetSocketAddress(s, n));
    }
    
    public AbstractBootstrap localAddress(final InetAddress inetAddress, final int n) {
        return this.localAddress(new InetSocketAddress(inetAddress, n));
    }
    
    public AbstractBootstrap option(final ChannelOption channelOption, final Object o) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        if (o == null) {
            // monitorenter(options = this.options)
            this.options.remove(channelOption);
        }
        // monitorexit(options)
        else {
            // monitorenter(options2 = this.options)
            this.options.put(channelOption, o);
        }
        // monitorexit(options2)
        return this;
    }
    
    public AbstractBootstrap attr(final AttributeKey attributeKey, final Object o) {
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        if (o == null) {
            // monitorenter(attrs = this.attrs)
            this.attrs.remove(attributeKey);
        }
        // monitorexit(attrs)
        else {
            // monitorenter(attrs2 = this.attrs)
            this.attrs.put(attributeKey, o);
        }
        // monitorexit(attrs2)
        return this;
    }
    
    public AbstractBootstrap validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        }
        if (this.channelFactory == null) {
            throw new IllegalStateException("channel or channelFactory not set");
        }
        return this;
    }
    
    public abstract AbstractBootstrap clone();
    
    public ChannelFuture register() {
        this.validate();
        return this.initAndRegister();
    }
    
    public ChannelFuture bind() {
        this.validate();
        final SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            throw new IllegalStateException("localAddress not set");
        }
        return this.doBind(localAddress);
    }
    
    public ChannelFuture bind(final int n) {
        return this.bind(new InetSocketAddress(n));
    }
    
    public ChannelFuture bind(final String s, final int n) {
        return this.bind(new InetSocketAddress(s, n));
    }
    
    public ChannelFuture bind(final InetAddress inetAddress, final int n) {
        return this.bind(new InetSocketAddress(inetAddress, n));
    }
    
    public ChannelFuture bind(final SocketAddress socketAddress) {
        this.validate();
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        }
        return this.doBind(socketAddress);
    }
    
    private ChannelFuture doBind(final SocketAddress socketAddress) {
        final ChannelFuture initAndRegister = this.initAndRegister();
        final Channel channel = initAndRegister.channel();
        if (initAndRegister.cause() != null) {
            return initAndRegister;
        }
        ChannelPromise promise;
        if (initAndRegister.isDone()) {
            promise = channel.newPromise();
            doBind0(initAndRegister, channel, socketAddress, promise);
        }
        else {
            promise = new PendingRegistrationPromise(channel, (AbstractBootstrap$1)null);
            initAndRegister.addListener((GenericFutureListener)new ChannelFutureListener(initAndRegister, channel, socketAddress, promise) {
                final ChannelFuture val$regFuture;
                final Channel val$channel;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final AbstractBootstrap this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    AbstractBootstrap.access$100(this.val$regFuture, this.val$channel, this.val$localAddress, this.val$promise);
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return promise;
    }
    
    final ChannelFuture initAndRegister() {
        final Channel channel = this.channelFactory().newChannel();
        this.init(channel);
        final ChannelFuture register = this.group().register(channel);
        if (register.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            }
            else {
                channel.unsafe().closeForcibly();
            }
        }
        return register;
    }
    
    abstract void init(final Channel p0) throws Exception;
    
    private static void doBind0(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        channel.eventLoop().execute(new Runnable(channelFuture, channel, socketAddress, channelPromise) {
            final ChannelFuture val$regFuture;
            final Channel val$channel;
            final SocketAddress val$localAddress;
            final ChannelPromise val$promise;
            
            @Override
            public void run() {
                if (this.val$regFuture.isSuccess()) {
                    this.val$channel.bind(this.val$localAddress, this.val$promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                else {
                    this.val$promise.setFailure(this.val$regFuture.cause());
                }
            }
        });
    }
    
    public AbstractBootstrap handler(final ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = handler;
        return this;
    }
    
    final SocketAddress localAddress() {
        return this.localAddress;
    }
    
    final ChannelFactory channelFactory() {
        return this.channelFactory;
    }
    
    final ChannelHandler handler() {
        return this.handler;
    }
    
    public final EventLoopGroup group() {
        return this.group;
    }
    
    final Map options() {
        return this.options;
    }
    
    final Map attrs() {
        return this.attrs;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        if (this.group != null) {
            sb.append("group: ");
            sb.append(StringUtil.simpleClassName(this.group));
            sb.append(", ");
        }
        if (this.channelFactory != null) {
            sb.append("channelFactory: ");
            sb.append(this.channelFactory);
            sb.append(", ");
        }
        if (this.localAddress != null) {
            sb.append("localAddress: ");
            sb.append(this.localAddress);
            sb.append(", ");
        }
        // monitorenter(options = this.options)
        if (!this.options.isEmpty()) {
            sb.append("options: ");
            sb.append(this.options);
            sb.append(", ");
        }
        // monitorexit(options)
        // monitorenter(attrs = this.attrs)
        if (!this.attrs.isEmpty()) {
            sb.append("attrs: ");
            sb.append(this.attrs);
            sb.append(", ");
        }
        // monitorexit(attrs)
        if (this.handler != null) {
            sb.append("handler: ");
            sb.append(this.handler);
            sb.append(", ");
        }
        if (sb.charAt(sb.length() - 1) == '(') {
            sb.append(')');
        }
        else {
            sb.setCharAt(sb.length() - 2, ')');
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static void access$100(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        doBind0(channelFuture, channel, socketAddress, channelPromise);
    }
    
    private static final class PendingRegistrationPromise extends DefaultChannelPromise
    {
        private PendingRegistrationPromise(final Channel channel) {
            super(channel);
        }
        
        @Override
        protected EventExecutor executor() {
            if (this.channel().isRegistered()) {
                return super.executor();
            }
            return GlobalEventExecutor.INSTANCE;
        }
        
        PendingRegistrationPromise(final Channel channel, final AbstractBootstrap$1 channelFutureListener) {
            this(channel);
        }
    }
    
    private static final class BootstrapChannelFactory implements ChannelFactory
    {
        private final Class clazz;
        
        BootstrapChannelFactory(final Class clazz) {
            this.clazz = clazz;
        }
        
        @Override
        public Channel newChannel() {
            return this.clazz.newInstance();
        }
        
        @Override
        public String toString() {
            return StringUtil.simpleClassName(this.clazz) + ".class";
        }
    }
}
