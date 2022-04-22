package io.netty.channel;

import java.nio.channels.*;
import java.net.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.*;
import io.netty.util.*;

public abstract class AbstractChannel extends DefaultAttributeMap implements Channel
{
    private static final InternalLogger logger;
    static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION;
    static final NotYetConnectedException NOT_YET_CONNECTED_EXCEPTION;
    private MessageSizeEstimator.Handle estimatorHandle;
    private final Channel parent;
    private final long hashCode;
    private final Unsafe unsafe;
    private final DefaultChannelPipeline pipeline;
    private final ChannelFuture succeededFuture;
    private final VoidChannelPromise voidPromise;
    private final VoidChannelPromise unsafeVoidPromise;
    private final CloseFuture closeFuture;
    private SocketAddress localAddress;
    private SocketAddress remoteAddress;
    private EventLoop eventLoop;
    private boolean registered;
    private boolean strValActive;
    private String strVal;
    
    protected AbstractChannel(final Channel parent) {
        this.hashCode = ThreadLocalRandom.current().nextLong();
        this.succeededFuture = new SucceededChannelFuture(this, null);
        this.voidPromise = new VoidChannelPromise(this, true);
        this.unsafeVoidPromise = new VoidChannelPromise(this, false);
        this.closeFuture = new CloseFuture(this);
        this.parent = parent;
        this.unsafe = this.newUnsafe();
        this.pipeline = new DefaultChannelPipeline(this);
    }
    
    @Override
    public boolean isWritable() {
        final ChannelOutboundBuffer outboundBuffer = this.unsafe.outboundBuffer();
        return outboundBuffer != null && outboundBuffer.isWritable();
    }
    
    @Override
    public Channel parent() {
        return this.parent;
    }
    
    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.config().getAllocator();
    }
    
    @Override
    public EventLoop eventLoop() {
        final EventLoop eventLoop = this.eventLoop;
        if (eventLoop == null) {
            throw new IllegalStateException("channel not registered to an event loop");
        }
        return eventLoop;
    }
    
    @Override
    public SocketAddress localAddress() {
        SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            localAddress = (this.localAddress = this.unsafe().localAddress());
        }
        return localAddress;
    }
    
    protected void invalidateLocalAddress() {
        this.localAddress = null;
    }
    
    @Override
    public SocketAddress remoteAddress() {
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            remoteAddress = (this.remoteAddress = this.unsafe().remoteAddress());
        }
        return remoteAddress;
    }
    
    protected void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }
    
    @Override
    public boolean isRegistered() {
        return this.registered;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress) {
        return this.pipeline.bind(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress) {
        return this.pipeline.connect(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        return this.pipeline.connect(socketAddress, socketAddress2);
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }
    
    @Override
    public ChannelFuture close() {
        return this.pipeline.close();
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }
    
    @Override
    public Channel flush() {
        this.pipeline.flush();
        return this;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.pipeline.bind(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        return this.pipeline.disconnect(channelPromise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise channelPromise) {
        return this.pipeline.close(channelPromise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        return this.pipeline.deregister(channelPromise);
    }
    
    @Override
    public Channel read() {
        this.pipeline.read();
        return this;
    }
    
    @Override
    public ChannelFuture write(final Object o) {
        return this.pipeline.write(o);
    }
    
    @Override
    public ChannelFuture write(final Object o, final ChannelPromise channelPromise) {
        return this.pipeline.write(o, channelPromise);
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o) {
        return this.pipeline.writeAndFlush(o);
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o, final ChannelPromise channelPromise) {
        return this.pipeline.writeAndFlush(o, channelPromise);
    }
    
    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this);
    }
    
    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this);
    }
    
    @Override
    public ChannelFuture newSucceededFuture() {
        return this.succeededFuture;
    }
    
    @Override
    public ChannelFuture newFailedFuture(final Throwable t) {
        return new FailedChannelFuture(this, null, t);
    }
    
    @Override
    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }
    
    @Override
    public Unsafe unsafe() {
        return this.unsafe;
    }
    
    protected abstract AbstractUnsafe newUnsafe();
    
    @Override
    public final int hashCode() {
        return (int)this.hashCode;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }
    
    public final int compareTo(final Channel channel) {
        if (this == channel) {
            return 0;
        }
        final long n = this.hashCode - channel.hashCode();
        if (n > 0L) {
            return 1;
        }
        if (n < 0L) {
            return -1;
        }
        final long n2 = System.identityHashCode(this) - System.identityHashCode(channel);
        if (n2 != 0L) {
            return (int)n2;
        }
        throw new Error();
    }
    
    @Override
    public String toString() {
        final boolean active = this.isActive();
        if (this.strValActive == active && this.strVal != null) {
            return this.strVal;
        }
        final SocketAddress remoteAddress = this.remoteAddress();
        final SocketAddress localAddress = this.localAddress();
        if (remoteAddress != null) {
            SocketAddress socketAddress;
            SocketAddress socketAddress2;
            if (this.parent == null) {
                socketAddress = localAddress;
                socketAddress2 = remoteAddress;
            }
            else {
                socketAddress = remoteAddress;
                socketAddress2 = localAddress;
            }
            this.strVal = String.format("[id: 0x%08x, %s %s %s]", (int)this.hashCode, socketAddress, active ? "=>" : ":>", socketAddress2);
        }
        else if (localAddress != null) {
            this.strVal = String.format("[id: 0x%08x, %s]", (int)this.hashCode, localAddress);
        }
        else {
            this.strVal = String.format("[id: 0x%08x]", (int)this.hashCode);
        }
        this.strValActive = active;
        return this.strVal;
    }
    
    @Override
    public final ChannelPromise voidPromise() {
        return this.voidPromise;
    }
    
    final MessageSizeEstimator.Handle estimatorHandle() {
        if (this.estimatorHandle == null) {
            this.estimatorHandle = this.config().getMessageSizeEstimator().newHandle();
        }
        return this.estimatorHandle;
    }
    
    protected abstract boolean isCompatible(final EventLoop p0);
    
    protected abstract SocketAddress localAddress0();
    
    protected abstract SocketAddress remoteAddress0();
    
    protected void doRegister() throws Exception {
    }
    
    protected abstract void doBind(final SocketAddress p0) throws Exception;
    
    protected abstract void doDisconnect() throws Exception;
    
    protected abstract void doClose() throws Exception;
    
    protected void doDeregister() throws Exception {
    }
    
    protected abstract void doBeginRead() throws Exception;
    
    protected abstract void doWrite(final ChannelOutboundBuffer p0) throws Exception;
    
    protected Object filterOutboundMessage(final Object o) throws Exception {
        return o;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Channel)o);
    }
    
    static EventLoop access$002(final AbstractChannel abstractChannel, final EventLoop eventLoop) {
        return abstractChannel.eventLoop = eventLoop;
    }
    
    static InternalLogger access$200() {
        return AbstractChannel.logger;
    }
    
    static CloseFuture access$300(final AbstractChannel abstractChannel) {
        return abstractChannel.closeFuture;
    }
    
    static boolean access$402(final AbstractChannel abstractChannel, final boolean registered) {
        return abstractChannel.registered = registered;
    }
    
    static DefaultChannelPipeline access$500(final AbstractChannel abstractChannel) {
        return abstractChannel.pipeline;
    }
    
    static boolean access$400(final AbstractChannel abstractChannel) {
        return abstractChannel.registered;
    }
    
    static VoidChannelPromise access$600(final AbstractChannel abstractChannel) {
        return abstractChannel.unsafeVoidPromise;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
        CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
        NOT_YET_CONNECTED_EXCEPTION = new NotYetConnectedException();
        AbstractChannel.CLOSED_CHANNEL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        AbstractChannel.NOT_YET_CONNECTED_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    
    static final class CloseFuture extends DefaultChannelPromise
    {
        CloseFuture(final AbstractChannel abstractChannel) {
            super(abstractChannel);
        }
        
        @Override
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }
        
        @Override
        public ChannelPromise setFailure(final Throwable t) {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean trySuccess() {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean tryFailure(final Throwable t) {
            throw new IllegalStateException();
        }
        
        boolean setClosed() {
            return super.trySuccess();
        }
        
        @Override
        public Promise setFailure(final Throwable failure) {
            return this.setFailure(failure);
        }
    }
    
    protected abstract class AbstractUnsafe implements Unsafe
    {
        private ChannelOutboundBuffer outboundBuffer;
        private boolean inFlush0;
        final AbstractChannel this$0;
        
        protected AbstractUnsafe(final AbstractChannel this$0) {
            this.this$0 = this$0;
            this.outboundBuffer = new ChannelOutboundBuffer(this.this$0);
        }
        
        @Override
        public final ChannelOutboundBuffer outboundBuffer() {
            return this.outboundBuffer;
        }
        
        @Override
        public final SocketAddress localAddress() {
            return this.this$0.localAddress0();
        }
        
        @Override
        public final SocketAddress remoteAddress() {
            return this.this$0.remoteAddress0();
        }
        
        @Override
        public final void register(final EventLoop eventLoop, final ChannelPromise channelPromise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            }
            if (this.this$0.isRegistered()) {
                channelPromise.setFailure((Throwable)new IllegalStateException("registered to an event loop already"));
                return;
            }
            if (!this.this$0.isCompatible(eventLoop)) {
                channelPromise.setFailure((Throwable)new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
                return;
            }
            AbstractChannel.access$002(this.this$0, eventLoop);
            if (eventLoop.inEventLoop()) {
                this.register0(channelPromise);
            }
            else {
                eventLoop.execute(new OneTimeTask(channelPromise) {
                    final ChannelPromise val$promise;
                    final AbstractUnsafe this$1;
                    
                    @Override
                    public void run() {
                        AbstractUnsafe.access$100(this.this$1, this.val$promise);
                    }
                });
            }
        }
        
        private void register0(final ChannelPromise p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokeinterface io/netty/channel/ChannelPromise.setUncancellable:()Z
            //     6: ifeq            14
            //     9: aload_0        
            //    10: aload_1        
            //    11: ifeq            15
            //    14: return         
            //    15: aload_0        
            //    16: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    19: invokevirtual   io/netty/channel/AbstractChannel.doRegister:()V
            //    22: aload_0        
            //    23: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    26: iconst_1       
            //    27: invokestatic    io/netty/channel/AbstractChannel.access$402:(Lio/netty/channel/AbstractChannel;Z)Z
            //    30: pop            
            //    31: aload_0        
            //    32: aload_1        
            //    33: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.safeSetSuccess:(Lio/netty/channel/ChannelPromise;)V
            //    36: aload_0        
            //    37: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    40: invokestatic    io/netty/channel/AbstractChannel.access$500:(Lio/netty/channel/AbstractChannel;)Lio/netty/channel/DefaultChannelPipeline;
            //    43: invokevirtual   io/netty/channel/DefaultChannelPipeline.fireChannelRegistered:()Lio/netty/channel/ChannelPipeline;
            //    46: pop            
            //    47: aload_0        
            //    48: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    51: invokevirtual   io/netty/channel/AbstractChannel.isActive:()Z
            //    54: ifeq            68
            //    57: aload_0        
            //    58: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    61: invokestatic    io/netty/channel/AbstractChannel.access$500:(Lio/netty/channel/AbstractChannel;)Lio/netty/channel/DefaultChannelPipeline;
            //    64: invokevirtual   io/netty/channel/DefaultChannelPipeline.fireChannelActive:()Lio/netty/channel/ChannelPipeline;
            //    67: pop            
            //    68: goto            93
            //    71: astore_2       
            //    72: aload_0        
            //    73: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.closeForcibly:()V
            //    76: aload_0        
            //    77: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    80: invokestatic    io/netty/channel/AbstractChannel.access$300:(Lio/netty/channel/AbstractChannel;)Lio/netty/channel/AbstractChannel$CloseFuture;
            //    83: invokevirtual   io/netty/channel/AbstractChannel$CloseFuture.setClosed:()Z
            //    86: pop            
            //    87: aload_0        
            //    88: aload_1        
            //    89: aload_2        
            //    90: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.safeSetFailure:(Lio/netty/channel/ChannelPromise;Ljava/lang/Throwable;)V
            //    93: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0014 (coming from #0011).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public final void bind(final SocketAddress p0, final ChannelPromise p1) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokeinterface io/netty/channel/ChannelPromise.setUncancellable:()Z
            //     6: ifeq            14
            //     9: aload_0        
            //    10: aload_2        
            //    11: ifeq            15
            //    14: return         
            //    15: invokestatic    io/netty/util/internal/PlatformDependent.isWindows:()Z
            //    18: ifne            103
            //    21: invokestatic    io/netty/util/internal/PlatformDependent.isRoot:()Z
            //    24: ifne            103
            //    27: getstatic       java/lang/Boolean.TRUE:Ljava/lang/Boolean;
            //    30: aload_0        
            //    31: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //    34: invokevirtual   io/netty/channel/AbstractChannel.config:()Lio/netty/channel/ChannelConfig;
            //    37: getstatic       io/netty/channel/ChannelOption.SO_BROADCAST:Lio/netty/channel/ChannelOption;
            //    40: invokeinterface io/netty/channel/ChannelConfig.getOption:(Lio/netty/channel/ChannelOption;)Ljava/lang/Object;
            //    45: invokevirtual   java/lang/Boolean.equals:(Ljava/lang/Object;)Z
            //    48: ifeq            103
            //    51: aload_1        
            //    52: instanceof      Ljava/net/InetSocketAddress;
            //    55: ifeq            103
            //    58: aload_1        
            //    59: checkcast       Ljava/net/InetSocketAddress;
            //    62: invokevirtual   java/net/InetSocketAddress.getAddress:()Ljava/net/InetAddress;
            //    65: invokevirtual   java/net/InetAddress.isAnyLocalAddress:()Z
            //    68: ifne            103
            //    71: invokestatic    io/netty/channel/AbstractChannel.access$200:()Lio/netty/util/internal/logging/InternalLogger;
            //    74: new             Ljava/lang/StringBuilder;
            //    77: dup            
            //    78: invokespecial   java/lang/StringBuilder.<init>:()V
            //    81: ldc             "A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address ("
            //    83: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    86: aload_1        
            //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
            //    90: ldc             ") anyway as requested."
            //    92: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    95: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    98: invokeinterface io/netty/util/internal/logging/InternalLogger.warn:(Ljava/lang/String;)V
            //   103: aload_0        
            //   104: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //   107: invokevirtual   io/netty/channel/AbstractChannel.isActive:()Z
            //   110: istore_3       
            //   111: aload_0        
            //   112: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //   115: aload_1        
            //   116: invokevirtual   io/netty/channel/AbstractChannel.doBind:(Ljava/net/SocketAddress;)V
            //   119: goto            136
            //   122: astore          4
            //   124: aload_0        
            //   125: aload_2        
            //   126: aload           4
            //   128: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.safeSetFailure:(Lio/netty/channel/ChannelPromise;Ljava/lang/Throwable;)V
            //   131: aload_0        
            //   132: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.closeIfClosed:()V
            //   135: return         
            //   136: iload_3        
            //   137: ifne            162
            //   140: aload_0        
            //   141: getfield        io/netty/channel/AbstractChannel$AbstractUnsafe.this$0:Lio/netty/channel/AbstractChannel;
            //   144: invokevirtual   io/netty/channel/AbstractChannel.isActive:()Z
            //   147: ifeq            162
            //   150: aload_0        
            //   151: new             Lio/netty/channel/AbstractChannel$AbstractUnsafe$2;
            //   154: dup            
            //   155: aload_0        
            //   156: invokespecial   io/netty/channel/AbstractChannel$AbstractUnsafe$2.<init>:(Lio/netty/channel/AbstractChannel$AbstractUnsafe;)V
            //   159: invokespecial   io/netty/channel/AbstractChannel$AbstractUnsafe.invokeLater:(Ljava/lang/Runnable;)V
            //   162: aload_0        
            //   163: aload_2        
            //   164: invokevirtual   io/netty/channel/AbstractChannel$AbstractUnsafe.safeSetSuccess:(Lio/netty/channel/ChannelPromise;)V
            //   167: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0014 (coming from #0011).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public final void disconnect(final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            final boolean active = this.this$0.isActive();
            this.this$0.doDisconnect();
            if (active && !this.this$0.isActive()) {
                this.invokeLater(new OneTimeTask() {
                    final AbstractUnsafe this$1;
                    
                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireChannelInactive();
                    }
                });
            }
            this.safeSetSuccess(channelPromise);
            this.closeIfClosed();
        }
        
        @Override
        public final void close(final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            if (this.inFlush0) {
                this.invokeLater(new OneTimeTask(channelPromise) {
                    final ChannelPromise val$promise;
                    final AbstractUnsafe this$1;
                    
                    @Override
                    public void run() {
                        this.this$1.close(this.val$promise);
                    }
                });
                return;
            }
            if (AbstractChannel.access$300(this.this$0).isDone()) {
                this.safeSetSuccess(channelPromise);
                return;
            }
            final boolean active = this.this$0.isActive();
            final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            this.outboundBuffer = null;
            this.this$0.doClose();
            AbstractChannel.access$300(this.this$0).setClosed();
            this.safeSetSuccess(channelPromise);
            outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
            outboundBuffer.close(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
            if (active && !this.this$0.isActive()) {
                this.invokeLater(new OneTimeTask() {
                    final AbstractUnsafe this$1;
                    
                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireChannelInactive();
                    }
                });
            }
            this.deregister(this.voidPromise());
        }
        
        @Override
        public final void closeForcibly() {
            this.this$0.doClose();
        }
        
        @Override
        public final void deregister(final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            if (!AbstractChannel.access$400(this.this$0)) {
                this.safeSetSuccess(channelPromise);
                return;
            }
            this.this$0.doDeregister();
            if (AbstractChannel.access$400(this.this$0)) {
                AbstractChannel.access$402(this.this$0, false);
                this.invokeLater(new OneTimeTask() {
                    final AbstractUnsafe this$1;
                    
                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireChannelUnregistered();
                    }
                });
                this.safeSetSuccess(channelPromise);
            }
            else {
                this.safeSetSuccess(channelPromise);
            }
        }
        
        @Override
        public final void beginRead() {
            if (!this.this$0.isActive()) {
                return;
            }
            this.this$0.doBeginRead();
        }
        
        @Override
        public final void write(Object filterOutboundMessage, final ChannelPromise channelPromise) {
            final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            if (outboundBuffer == null) {
                this.safeSetFailure(channelPromise, AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
                ReferenceCountUtil.release(filterOutboundMessage);
                return;
            }
            filterOutboundMessage = this.this$0.filterOutboundMessage(filterOutboundMessage);
            this.this$0.estimatorHandle().size(filterOutboundMessage);
            outboundBuffer.addMessage(filterOutboundMessage, 0, channelPromise);
        }
        
        @Override
        public final void flush() {
            final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            if (outboundBuffer == null) {
                return;
            }
            outboundBuffer.addFlush();
            this.flush0();
        }
        
        protected void flush0() {
            if (this.inFlush0) {
                return;
            }
            final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
            if (outboundBuffer == null || outboundBuffer.isEmpty()) {
                return;
            }
            this.inFlush0 = true;
            if (!this.this$0.isActive()) {
                if (this.this$0.isOpen()) {
                    outboundBuffer.failFlushed(AbstractChannel.NOT_YET_CONNECTED_EXCEPTION);
                }
                else {
                    outboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
                }
                this.inFlush0 = false;
                return;
            }
            this.this$0.doWrite(outboundBuffer);
            this.inFlush0 = false;
        }
        
        @Override
        public final ChannelPromise voidPromise() {
            return AbstractChannel.access$600(this.this$0);
        }
        
        protected final void safeSetSuccess(final ChannelPromise channelPromise) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.trySuccess()) {
                AbstractChannel.access$200().warn("Failed to mark a promise as success because it is done already: {}", channelPromise);
            }
        }
        
        protected final void safeSetFailure(final ChannelPromise channelPromise, final Throwable t) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(t)) {
                AbstractChannel.access$200().warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, t);
            }
        }
        
        protected final void closeIfClosed() {
            if (this.this$0.isOpen()) {
                return;
            }
            this.close(this.voidPromise());
        }
        
        private void invokeLater(final Runnable runnable) {
            this.this$0.eventLoop().execute(runnable);
        }
        
        static void access$100(final AbstractUnsafe abstractUnsafe, final ChannelPromise channelPromise) {
            abstractUnsafe.register0(channelPromise);
        }
    }
}
