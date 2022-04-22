package io.netty.bootstrap;

import io.netty.util.*;
import java.util.*;
import io.netty.util.internal.*;
import io.netty.util.internal.logging.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import java.util.concurrent.*;

public final class ServerBootstrap extends AbstractBootstrap
{
    private static final InternalLogger logger;
    private final Map childOptions;
    private final Map childAttrs;
    private EventLoopGroup childGroup;
    private ChannelHandler childHandler;
    
    public ServerBootstrap() {
        this.childOptions = new LinkedHashMap();
        this.childAttrs = new LinkedHashMap();
    }
    
    private ServerBootstrap(final ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
        this.childOptions = new LinkedHashMap();
        this.childAttrs = new LinkedHashMap();
        this.childGroup = serverBootstrap.childGroup;
        this.childHandler = serverBootstrap.childHandler;
        // monitorenter(childOptions = serverBootstrap.childOptions)
        this.childOptions.putAll(serverBootstrap.childOptions);
        // monitorexit(childOptions)
        // monitorenter(childAttrs = serverBootstrap.childAttrs)
        this.childAttrs.putAll(serverBootstrap.childAttrs);
    }
    // monitorexit(childAttrs)
    
    @Override
    public ServerBootstrap group(final EventLoopGroup eventLoopGroup) {
        return this.group(eventLoopGroup, eventLoopGroup);
    }
    
    public ServerBootstrap group(final EventLoopGroup eventLoopGroup, final EventLoopGroup childGroup) {
        super.group(eventLoopGroup);
        if (childGroup == null) {
            throw new NullPointerException("childGroup");
        }
        if (this.childGroup != null) {
            throw new IllegalStateException("childGroup set already");
        }
        this.childGroup = childGroup;
        return this;
    }
    
    public ServerBootstrap childOption(final ChannelOption channelOption, final Object o) {
        if (channelOption == null) {
            throw new NullPointerException("childOption");
        }
        if (o == null) {
            // monitorenter(childOptions = this.childOptions)
            this.childOptions.remove(channelOption);
        }
        // monitorexit(childOptions)
        else {
            // monitorenter(childOptions2 = this.childOptions)
            this.childOptions.put(channelOption, o);
        }
        // monitorexit(childOptions2)
        return this;
    }
    
    public ServerBootstrap childAttr(final AttributeKey attributeKey, final Object o) {
        if (attributeKey == null) {
            throw new NullPointerException("childKey");
        }
        if (o == null) {
            this.childAttrs.remove(attributeKey);
        }
        else {
            this.childAttrs.put(attributeKey, o);
        }
        return this;
    }
    
    public ServerBootstrap childHandler(final ChannelHandler childHandler) {
        if (childHandler == null) {
            throw new NullPointerException("childHandler");
        }
        this.childHandler = childHandler;
        return this;
    }
    
    public EventLoopGroup childGroup() {
        return this.childGroup;
    }
    
    @Override
    void init(final Channel channel) throws Exception {
        final Map options = this.options();
        // monitorenter(map = options)
        channel.config().setOptions(options);
        // monitorexit(map)
        final Map attrs = this.attrs();
        // monitorenter(map2 = attrs)
        for (final Map.Entry<AttributeKey, V> entry : attrs.entrySet()) {
            channel.attr(entry.getKey()).set(entry.getValue());
        }
        // monitorexit(map2)
        final ChannelPipeline pipeline = channel.pipeline();
        if (this.handler() != null) {
            pipeline.addLast(this.handler());
        }
        final EventLoopGroup childGroup = this.childGroup;
        final ChannelHandler childHandler = this.childHandler;
        // monitorenter(childOptions = this.childOptions)
        final Map.Entry[] array = (Map.Entry[])this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
        // monitorexit(childOptions)
        // monitorenter(childAttrs = this.childAttrs)
        final Map.Entry[] array2 = (Map.Entry[])this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
        // monitorexit(childAttrs)
        pipeline.addLast(new ChannelInitializer(childGroup, childHandler, array, array2) {
            final EventLoopGroup val$currentChildGroup;
            final ChannelHandler val$currentChildHandler;
            final Map.Entry[] val$currentChildOptions;
            final Map.Entry[] val$currentChildAttrs;
            final ServerBootstrap this$0;
            
            public void initChannel(final Channel channel) throws Exception {
                channel.pipeline().addLast(new ServerBootstrapAcceptor(this.val$currentChildGroup, this.val$currentChildHandler, this.val$currentChildOptions, this.val$currentChildAttrs));
            }
        });
    }
    
    @Override
    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler == null) {
            throw new IllegalStateException("childHandler not set");
        }
        if (this.childGroup == null) {
            ServerBootstrap.logger.warn("childGroup is not set. Using parentGroup instead.");
            this.childGroup = this.group();
        }
        return this;
    }
    
    private static Map.Entry[] newOptionArray(final int n) {
        return new Map.Entry[n];
    }
    
    private static Map.Entry[] newAttrArray(final int n) {
        return new Map.Entry[n];
    }
    
    @Override
    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        sb.append(", ");
        if (this.childGroup != null) {
            sb.append("childGroup: ");
            sb.append(StringUtil.simpleClassName(this.childGroup));
            sb.append(", ");
        }
        // monitorenter(childOptions = this.childOptions)
        if (!this.childOptions.isEmpty()) {
            sb.append("childOptions: ");
            sb.append(this.childOptions);
            sb.append(", ");
        }
        // monitorexit(childOptions)
        // monitorenter(childAttrs = this.childAttrs)
        if (!this.childAttrs.isEmpty()) {
            sb.append("childAttrs: ");
            sb.append(this.childAttrs);
            sb.append(", ");
        }
        // monitorexit(childAttrs)
        if (this.childHandler != null) {
            sb.append("childHandler: ");
            sb.append(this.childHandler);
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
    
    @Override
    public AbstractBootstrap clone() {
        return this.clone();
    }
    
    @Override
    public AbstractBootstrap validate() {
        return this.validate();
    }
    
    @Override
    public AbstractBootstrap group(final EventLoopGroup eventLoopGroup) {
        return this.group(eventLoopGroup);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static InternalLogger access$000() {
        return ServerBootstrap.logger;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    }
    
    private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter
    {
        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Map.Entry[] childOptions;
        private final Map.Entry[] childAttrs;
        
        ServerBootstrapAcceptor(final EventLoopGroup childGroup, final ChannelHandler childHandler, final Map.Entry[] childOptions, final Map.Entry[] childAttrs) {
            this.childGroup = childGroup;
            this.childHandler = childHandler;
            this.childOptions = childOptions;
            this.childAttrs = childAttrs;
        }
        
        @Override
        public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) {
            final Channel channel = (Channel)o;
            channel.pipeline().addLast(this.childHandler);
            final Map.Entry[] childOptions = this.childOptions;
            int n = 0;
            while (0 < childOptions.length) {
                final Map.Entry entry = childOptions[0];
                if (!channel.config().setOption(entry.getKey(), entry.getValue())) {
                    ServerBootstrap.access$000().warn("Unknown channel option: " + entry);
                }
                ++n;
            }
            final Map.Entry[] childAttrs = this.childAttrs;
            while (0 < childAttrs.length) {
                final Map.Entry entry2 = childAttrs[0];
                channel.attr(entry2.getKey()).set(entry2.getValue());
                ++n;
            }
            this.childGroup.register(channel).addListener((GenericFutureListener)new ChannelFutureListener(channel) {
                final Channel val$child;
                final ServerBootstrapAcceptor this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        ServerBootstrapAcceptor.access$100(this.val$child, channelFuture.cause());
                    }
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        
        private static void forceClose(final Channel channel, final Throwable t) {
            channel.unsafe().closeForcibly();
            ServerBootstrap.access$000().warn("Failed to register an accepted channel: " + channel, t);
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
            final ChannelConfig config = channelHandlerContext.channel().config();
            if (config.isAutoRead()) {
                config.setAutoRead(false);
                channelHandlerContext.channel().eventLoop().schedule((Runnable)new Runnable(config) {
                    final ChannelConfig val$config;
                    final ServerBootstrapAcceptor this$0;
                    
                    @Override
                    public void run() {
                        this.val$config.setAutoRead(true);
                    }
                }, 1L, TimeUnit.SECONDS);
            }
            channelHandlerContext.fireExceptionCaught(t);
        }
        
        static void access$100(final Channel channel, final Throwable t) {
            forceClose(channel, t);
        }
    }
}
