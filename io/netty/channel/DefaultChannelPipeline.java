package io.netty.channel;

import io.netty.util.concurrent.*;
import io.netty.util.internal.*;
import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import io.netty.util.internal.logging.*;
import io.netty.util.*;

final class DefaultChannelPipeline implements ChannelPipeline
{
    static final InternalLogger logger;
    private static final WeakHashMap[] nameCaches;
    final AbstractChannel channel;
    final AbstractChannelHandlerContext head;
    final AbstractChannelHandlerContext tail;
    private final Map name2ctx;
    final Map childExecutors;
    static final boolean $assertionsDisabled;
    
    public DefaultChannelPipeline(final AbstractChannel channel) {
        this.name2ctx = new HashMap(4);
        this.childExecutors = new IdentityHashMap();
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        this.tail = new TailContext(this);
        this.head = new HeadContext(this);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public ChannelPipeline addFirst(final String s, final ChannelHandler channelHandler) {
        return this.addFirst(null, s, channelHandler);
    }
    
    @Override
    public ChannelPipeline addFirst(final EventExecutorGroup eventExecutorGroup, final String s, final ChannelHandler channelHandler) {
        // monitorenter(this)
        this.checkDuplicateName(s);
        this.addFirst0(s, new DefaultChannelHandlerContext(this, eventExecutorGroup, s, channelHandler));
        // monitorexit(this)
        return this;
    }
    
    private void addFirst0(final String s, final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        checkMultiplicity(abstractChannelHandlerContext);
        final AbstractChannelHandlerContext next = this.head.next;
        abstractChannelHandlerContext.prev = this.head;
        abstractChannelHandlerContext.next = next;
        this.head.next = abstractChannelHandlerContext;
        next.prev = abstractChannelHandlerContext;
        this.name2ctx.put(s, abstractChannelHandlerContext);
        this.callHandlerAdded(abstractChannelHandlerContext);
    }
    
    @Override
    public ChannelPipeline addLast(final String s, final ChannelHandler channelHandler) {
        return this.addLast(null, s, channelHandler);
    }
    
    @Override
    public ChannelPipeline addLast(final EventExecutorGroup eventExecutorGroup, final String s, final ChannelHandler channelHandler) {
        // monitorenter(this)
        this.checkDuplicateName(s);
        this.addLast0(s, new DefaultChannelHandlerContext(this, eventExecutorGroup, s, channelHandler));
        // monitorexit(this)
        return this;
    }
    
    private void addLast0(final String s, final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        checkMultiplicity(abstractChannelHandlerContext);
        final AbstractChannelHandlerContext prev = this.tail.prev;
        abstractChannelHandlerContext.prev = prev;
        abstractChannelHandlerContext.next = this.tail;
        prev.next = abstractChannelHandlerContext;
        this.tail.prev = abstractChannelHandlerContext;
        this.name2ctx.put(s, abstractChannelHandlerContext);
        this.callHandlerAdded(abstractChannelHandlerContext);
    }
    
    @Override
    public ChannelPipeline addBefore(final String s, final String s2, final ChannelHandler channelHandler) {
        return this.addBefore(null, s, s2, channelHandler);
    }
    
    @Override
    public ChannelPipeline addBefore(final EventExecutorGroup eventExecutorGroup, final String s, final String s2, final ChannelHandler channelHandler) {
        // monitorenter(this)
        final AbstractChannelHandlerContext contextOrDie = this.getContextOrDie(s);
        this.checkDuplicateName(s2);
        this.addBefore0(s2, contextOrDie, new DefaultChannelHandlerContext(this, eventExecutorGroup, s2, channelHandler));
        // monitorexit(this)
        return this;
    }
    
    private void addBefore0(final String s, final AbstractChannelHandlerContext next, final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        checkMultiplicity(abstractChannelHandlerContext);
        abstractChannelHandlerContext.prev = next.prev;
        abstractChannelHandlerContext.next = next;
        next.prev.next = abstractChannelHandlerContext;
        next.prev = abstractChannelHandlerContext;
        this.name2ctx.put(s, abstractChannelHandlerContext);
        this.callHandlerAdded(abstractChannelHandlerContext);
    }
    
    @Override
    public ChannelPipeline addAfter(final String s, final String s2, final ChannelHandler channelHandler) {
        return this.addAfter(null, s, s2, channelHandler);
    }
    
    @Override
    public ChannelPipeline addAfter(final EventExecutorGroup eventExecutorGroup, final String s, final String s2, final ChannelHandler channelHandler) {
        // monitorenter(this)
        final AbstractChannelHandlerContext contextOrDie = this.getContextOrDie(s);
        this.checkDuplicateName(s2);
        this.addAfter0(s2, contextOrDie, new DefaultChannelHandlerContext(this, eventExecutorGroup, s2, channelHandler));
        // monitorexit(this)
        return this;
    }
    
    private void addAfter0(final String s, final AbstractChannelHandlerContext prev, final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        this.checkDuplicateName(s);
        checkMultiplicity(abstractChannelHandlerContext);
        abstractChannelHandlerContext.prev = prev;
        abstractChannelHandlerContext.next = prev.next;
        prev.next.prev = abstractChannelHandlerContext;
        prev.next = abstractChannelHandlerContext;
        this.name2ctx.put(s, abstractChannelHandlerContext);
        this.callHandlerAdded(abstractChannelHandlerContext);
    }
    
    @Override
    public ChannelPipeline addFirst(final ChannelHandler... array) {
        return this.addFirst((EventExecutorGroup)null, array);
    }
    
    @Override
    public ChannelPipeline addFirst(final EventExecutorGroup eventExecutorGroup, final ChannelHandler... array) {
        if (array == null) {
            throw new NullPointerException("handlers");
        }
        if (array.length == 0 || array[0] == null) {
            return this;
        }
        while (1 < array.length) {
            if (array[1] == null) {
                break;
            }
            int n = 0;
            ++n;
        }
        while (0 >= 0) {
            final ChannelHandler channelHandler = array[0];
            this.addFirst(eventExecutorGroup, this.generateName(channelHandler), channelHandler);
            int n2 = 0;
            --n2;
        }
        return this;
    }
    
    @Override
    public ChannelPipeline addLast(final ChannelHandler... array) {
        return this.addLast((EventExecutorGroup)null, array);
    }
    
    @Override
    public ChannelPipeline addLast(final EventExecutorGroup eventExecutorGroup, final ChannelHandler... array) {
        if (array == null) {
            throw new NullPointerException("handlers");
        }
        while (0 < array.length) {
            final ChannelHandler channelHandler = array[0];
            if (channelHandler == null) {
                break;
            }
            this.addLast(eventExecutorGroup, this.generateName(channelHandler), channelHandler);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    private String generateName(final ChannelHandler channelHandler) {
        final WeakHashMap weakHashMap = DefaultChannelPipeline.nameCaches[(int)(Thread.currentThread().getId() % DefaultChannelPipeline.nameCaches.length)];
        final Class<? extends ChannelHandler> class1 = channelHandler.getClass();
        // monitorenter(weakHashMap2 = weakHashMap)
        String generateName0 = weakHashMap.get(class1);
        if (generateName0 == null) {
            generateName0 = generateName0(class1);
            weakHashMap.put(class1, generateName0);
        }
        // monitorexit(weakHashMap2)
        // monitorenter(this)
        if (this.name2ctx.containsKey(generateName0)) {
            final String substring = generateName0.substring(0, generateName0.length() - 1);
            String string;
            while (true) {
                string = substring + 1;
                if (!this.name2ctx.containsKey(string)) {
                    break;
                }
                int n = 0;
                ++n;
            }
            generateName0 = string;
        }
        // monitorexit(this)
        return generateName0;
    }
    
    private static String generateName0(final Class clazz) {
        return StringUtil.simpleClassName(clazz) + "#0";
    }
    
    @Override
    public ChannelPipeline remove(final ChannelHandler channelHandler) {
        this.remove(this.getContextOrDie(channelHandler));
        return this;
    }
    
    @Override
    public ChannelHandler remove(final String s) {
        return this.remove(this.getContextOrDie(s)).handler();
    }
    
    @Override
    public ChannelHandler remove(final Class clazz) {
        return this.remove(this.getContextOrDie(clazz)).handler();
    }
    
    private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        assert abstractChannelHandlerContext != this.head && abstractChannelHandlerContext != this.tail;
        // monitorenter(this)
        if (!abstractChannelHandlerContext.channel().isRegistered() || abstractChannelHandlerContext.executor().inEventLoop()) {
            this.remove0(abstractChannelHandlerContext);
            // monitorexit(this)
            return abstractChannelHandlerContext;
        }
        final io.netty.util.concurrent.Future submit = abstractChannelHandlerContext.executor().submit((Runnable)new Runnable(abstractChannelHandlerContext) {
            final AbstractChannelHandlerContext val$ctx;
            final DefaultChannelPipeline this$0;
            
            @Override
            public void run() {
                // monitorenter(this$0 = this.this$0)
                this.this$0.remove0(this.val$ctx);
            }
            // monitorexit(this$0)
        });
        // monitorexit(this)
        waitForFuture(submit);
        return abstractChannelHandlerContext;
    }
    
    void remove0(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        final AbstractChannelHandlerContext prev = abstractChannelHandlerContext.prev;
        final AbstractChannelHandlerContext next = abstractChannelHandlerContext.next;
        prev.next = next;
        next.prev = prev;
        this.name2ctx.remove(abstractChannelHandlerContext.name());
        this.callHandlerRemoved(abstractChannelHandlerContext);
    }
    
    @Override
    public ChannelHandler removeFirst() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.head.next).handler();
    }
    
    @Override
    public ChannelHandler removeLast() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.tail.prev).handler();
    }
    
    @Override
    public ChannelPipeline replace(final ChannelHandler channelHandler, final String s, final ChannelHandler channelHandler2) {
        this.replace(this.getContextOrDie(channelHandler), s, channelHandler2);
        return this;
    }
    
    @Override
    public ChannelHandler replace(final String s, final String s2, final ChannelHandler channelHandler) {
        return this.replace(this.getContextOrDie(s), s2, channelHandler);
    }
    
    @Override
    public ChannelHandler replace(final Class clazz, final String s, final ChannelHandler channelHandler) {
        return this.replace(this.getContextOrDie(clazz), s, channelHandler);
    }
    
    private ChannelHandler replace(final AbstractChannelHandlerContext abstractChannelHandlerContext, final String s, final ChannelHandler channelHandler) {
        assert abstractChannelHandlerContext != this.head && abstractChannelHandlerContext != this.tail;
        // monitorenter(this)
        if (!abstractChannelHandlerContext.name().equals(s)) {
            this.checkDuplicateName(s);
        }
        final DefaultChannelHandlerContext defaultChannelHandlerContext = new DefaultChannelHandlerContext(this, abstractChannelHandlerContext.executor, s, channelHandler);
        if (!defaultChannelHandlerContext.channel().isRegistered() || defaultChannelHandlerContext.executor().inEventLoop()) {
            this.replace0(abstractChannelHandlerContext, s, defaultChannelHandlerContext);
            // monitorexit(this)
            return abstractChannelHandlerContext.handler();
        }
        final io.netty.util.concurrent.Future submit = defaultChannelHandlerContext.executor().submit((Runnable)new Runnable(abstractChannelHandlerContext, s, (AbstractChannelHandlerContext)defaultChannelHandlerContext) {
            final AbstractChannelHandlerContext val$ctx;
            final String val$newName;
            final AbstractChannelHandlerContext val$newCtx;
            final DefaultChannelPipeline this$0;
            
            @Override
            public void run() {
                // monitorenter(this$0 = this.this$0)
                DefaultChannelPipeline.access$000(this.this$0, this.val$ctx, this.val$newName, this.val$newCtx);
            }
            // monitorexit(this$0)
        });
        // monitorexit(this)
        waitForFuture(submit);
        return abstractChannelHandlerContext.handler();
    }
    
    private void replace0(final AbstractChannelHandlerContext abstractChannelHandlerContext, final String s, final AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        checkMultiplicity(abstractChannelHandlerContext2);
        final AbstractChannelHandlerContext prev = abstractChannelHandlerContext.prev;
        final AbstractChannelHandlerContext next = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext2.prev = prev;
        abstractChannelHandlerContext2.next = next;
        prev.next = abstractChannelHandlerContext2;
        next.prev = abstractChannelHandlerContext2;
        if (!abstractChannelHandlerContext.name().equals(s)) {
            this.name2ctx.remove(abstractChannelHandlerContext.name());
        }
        this.name2ctx.put(s, abstractChannelHandlerContext2);
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
        this.callHandlerAdded(abstractChannelHandlerContext.next = abstractChannelHandlerContext2);
        this.callHandlerRemoved(abstractChannelHandlerContext);
    }
    
    private static void checkMultiplicity(final ChannelHandlerContext channelHandlerContext) {
        final ChannelHandler handler = channelHandlerContext.handler();
        if (handler instanceof ChannelHandlerAdapter) {
            final ChannelHandlerAdapter channelHandlerAdapter = (ChannelHandlerAdapter)handler;
            if (!channelHandlerAdapter.isSharable() && channelHandlerAdapter.added) {
                throw new ChannelPipelineException(channelHandlerAdapter.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
            }
            channelHandlerAdapter.added = true;
        }
    }
    
    private void callHandlerAdded(final ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().isRegistered() && !channelHandlerContext.executor().inEventLoop()) {
            channelHandlerContext.executor().execute(new Runnable(channelHandlerContext) {
                final ChannelHandlerContext val$ctx;
                final DefaultChannelPipeline this$0;
                
                @Override
                public void run() {
                    DefaultChannelPipeline.access$100(this.this$0, this.val$ctx);
                }
            });
            return;
        }
        this.callHandlerAdded0(channelHandlerContext);
    }
    
    private void callHandlerAdded0(final ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.handler().handlerAdded(channelHandlerContext);
    }
    
    private void callHandlerRemoved(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        if (abstractChannelHandlerContext.channel().isRegistered() && !abstractChannelHandlerContext.executor().inEventLoop()) {
            abstractChannelHandlerContext.executor().execute(new Runnable(abstractChannelHandlerContext) {
                final AbstractChannelHandlerContext val$ctx;
                final DefaultChannelPipeline this$0;
                
                @Override
                public void run() {
                    DefaultChannelPipeline.access$200(this.this$0, this.val$ctx);
                }
            });
            return;
        }
        this.callHandlerRemoved0(abstractChannelHandlerContext);
    }
    
    private void callHandlerRemoved0(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.handler().handlerRemoved(abstractChannelHandlerContext);
        abstractChannelHandlerContext.setRemoved();
    }
    
    private static void waitForFuture(final Future future) {
        future.get();
    }
    
    @Override
    public ChannelHandler first() {
        final ChannelHandlerContext firstContext = this.firstContext();
        if (firstContext == null) {
            return null;
        }
        return firstContext.handler();
    }
    
    @Override
    public ChannelHandlerContext firstContext() {
        if (this.head.next == this.tail) {
            return null;
        }
        return this.head.next;
    }
    
    @Override
    public ChannelHandler last() {
        final AbstractChannelHandlerContext prev = this.tail.prev;
        if (prev == this.head) {
            return null;
        }
        return prev.handler();
    }
    
    @Override
    public ChannelHandlerContext lastContext() {
        final AbstractChannelHandlerContext prev = this.tail.prev;
        if (prev == this.head) {
            return null;
        }
        return prev;
    }
    
    @Override
    public ChannelHandler get(final String s) {
        final ChannelHandlerContext context = this.context(s);
        if (context == null) {
            return null;
        }
        return context.handler();
    }
    
    @Override
    public ChannelHandler get(final Class clazz) {
        final ChannelHandlerContext context = this.context(clazz);
        if (context == null) {
            return null;
        }
        return context.handler();
    }
    
    @Override
    public ChannelHandlerContext context(final String s) {
        if (s == null) {
            throw new NullPointerException("name");
        }
        // monitorenter(this)
        // monitorexit(this)
        return this.name2ctx.get(s);
    }
    
    @Override
    public ChannelHandlerContext context(final ChannelHandler channelHandler) {
        if (channelHandler == null) {
            throw new NullPointerException("handler");
        }
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            if (abstractChannelHandlerContext.handler() == channelHandler) {
                return abstractChannelHandlerContext;
            }
        }
        return null;
    }
    
    @Override
    public ChannelHandlerContext context(final Class clazz) {
        if (clazz == null) {
            throw new NullPointerException("handlerType");
        }
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            if (clazz.isAssignableFrom(abstractChannelHandlerContext.handler().getClass())) {
                return abstractChannelHandlerContext;
            }
        }
        return null;
    }
    
    @Override
    public List names() {
        final ArrayList<String> list = new ArrayList<String>();
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != null; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            list.add(abstractChannelHandlerContext.name());
        }
        return list;
    }
    
    @Override
    public Map toMap() {
        final LinkedHashMap<String, ChannelHandler> linkedHashMap = new LinkedHashMap<String, ChannelHandler>();
        for (AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next; abstractChannelHandlerContext != this.tail; abstractChannelHandlerContext = abstractChannelHandlerContext.next) {
            linkedHashMap.put(abstractChannelHandlerContext.name(), abstractChannelHandlerContext.handler());
        }
        return linkedHashMap;
    }
    
    @Override
    public Iterator iterator() {
        return this.toMap().entrySet().iterator();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append('{');
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (true) {
            while (abstractChannelHandlerContext != this.tail) {
                sb.append('(');
                sb.append(abstractChannelHandlerContext.name());
                sb.append(" = ");
                sb.append(abstractChannelHandlerContext.handler().getClass().getName());
                sb.append(')');
                abstractChannelHandlerContext = abstractChannelHandlerContext.next;
                if (abstractChannelHandlerContext == this.tail) {
                    sb.append('}');
                    return sb.toString();
                }
                sb.append(", ");
            }
            continue;
        }
    }
    
    @Override
    public ChannelPipeline fireChannelRegistered() {
        this.head.fireChannelRegistered();
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelUnregistered() {
        this.head.fireChannelUnregistered();
        if (!this.channel.isOpen()) {
            this.teardownAll();
        }
        return this;
    }
    
    private void teardownAll() {
        this.tail.prev.teardown();
    }
    
    @Override
    public ChannelPipeline fireChannelActive() {
        this.head.fireChannelActive();
        if (this.channel.config().isAutoRead()) {
            this.channel.read();
        }
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelInactive() {
        this.head.fireChannelInactive();
        return this;
    }
    
    @Override
    public ChannelPipeline fireExceptionCaught(final Throwable t) {
        this.head.fireExceptionCaught(t);
        return this;
    }
    
    @Override
    public ChannelPipeline fireUserEventTriggered(final Object o) {
        this.head.fireUserEventTriggered(o);
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelRead(final Object o) {
        this.head.fireChannelRead(o);
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelReadComplete() {
        this.head.fireChannelReadComplete();
        if (this.channel.config().isAutoRead()) {
            this.read();
        }
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelWritabilityChanged() {
        this.head.fireChannelWritabilityChanged();
        return this;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress) {
        return this.tail.bind(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress) {
        return this.tail.connect(socketAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        return this.tail.connect(socketAddress, socketAddress2);
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.tail.disconnect();
    }
    
    @Override
    public ChannelFuture close() {
        return this.tail.close();
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.tail.deregister();
    }
    
    @Override
    public ChannelPipeline flush() {
        this.tail.flush();
        return this;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.tail.bind(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        return this.tail.disconnect(channelPromise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise channelPromise) {
        return this.tail.close(channelPromise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        return this.tail.deregister(channelPromise);
    }
    
    @Override
    public ChannelPipeline read() {
        this.tail.read();
        return this;
    }
    
    @Override
    public ChannelFuture write(final Object o) {
        return this.tail.write(o);
    }
    
    @Override
    public ChannelFuture write(final Object o, final ChannelPromise channelPromise) {
        return this.tail.write(o, channelPromise);
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o, final ChannelPromise channelPromise) {
        return this.tail.writeAndFlush(o, channelPromise);
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o) {
        return this.tail.writeAndFlush(o);
    }
    
    private void checkDuplicateName(final String s) {
        if (this.name2ctx.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate handler name: " + s);
        }
    }
    
    private AbstractChannelHandlerContext getContextOrDie(final String s) {
        final AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(s);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(s);
        }
        return abstractChannelHandlerContext;
    }
    
    private AbstractChannelHandlerContext getContextOrDie(final ChannelHandler channelHandler) {
        final AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(channelHandler);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(channelHandler.getClass().getName());
        }
        return abstractChannelHandlerContext;
    }
    
    private AbstractChannelHandlerContext getContextOrDie(final Class clazz) {
        final AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(clazz);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(clazz.getName());
        }
        return abstractChannelHandlerContext;
    }
    
    static void access$000(final DefaultChannelPipeline defaultChannelPipeline, final AbstractChannelHandlerContext abstractChannelHandlerContext, final String s, final AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        defaultChannelPipeline.replace0(abstractChannelHandlerContext, s, abstractChannelHandlerContext2);
    }
    
    static void access$100(final DefaultChannelPipeline defaultChannelPipeline, final ChannelHandlerContext channelHandlerContext) {
        defaultChannelPipeline.callHandlerAdded0(channelHandlerContext);
    }
    
    static void access$200(final DefaultChannelPipeline defaultChannelPipeline, final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        defaultChannelPipeline.callHandlerRemoved0(abstractChannelHandlerContext);
    }
    
    static String access$300(final Class clazz) {
        return generateName0(clazz);
    }
    
    static {
        $assertionsDisabled = !DefaultChannelPipeline.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
        nameCaches = new WeakHashMap[Runtime.getRuntime().availableProcessors()];
        while (0 < DefaultChannelPipeline.nameCaches.length) {
            DefaultChannelPipeline.nameCaches[0] = new WeakHashMap();
            int n = 0;
            ++n;
        }
    }
    
    static final class HeadContext extends AbstractChannelHandlerContext implements ChannelOutboundHandler
    {
        private static final String HEAD_NAME;
        protected final Channel.Unsafe unsafe;
        
        HeadContext(final DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, HeadContext.HEAD_NAME, false, true);
            this.unsafe = defaultChannelPipeline.channel().unsafe();
        }
        
        @Override
        public ChannelHandler handler() {
            return this;
        }
        
        @Override
        public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.bind(socketAddress, channelPromise);
        }
        
        @Override
        public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.connect(socketAddress, socketAddress2, channelPromise);
        }
        
        @Override
        public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.disconnect(channelPromise);
        }
        
        @Override
        public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.close(channelPromise);
        }
        
        @Override
        public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.deregister(channelPromise);
        }
        
        @Override
        public void read(final ChannelHandlerContext channelHandlerContext) {
            this.unsafe.beginRead();
        }
        
        @Override
        public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
            this.unsafe.write(o, channelPromise);
        }
        
        @Override
        public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
            this.unsafe.flush();
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
            channelHandlerContext.fireExceptionCaught(t);
        }
        
        static {
            HEAD_NAME = DefaultChannelPipeline.access$300(HeadContext.class);
        }
    }
    
    static final class TailContext extends AbstractChannelHandlerContext implements ChannelInboundHandler
    {
        private static final String TAIL_NAME;
        
        TailContext(final DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, TailContext.TAIL_NAME, true, false);
        }
        
        @Override
        public ChannelHandler handler() {
            return this;
        }
        
        @Override
        public void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void channelUnregistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void channelWritabilityChanged(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        @Override
        public void userEventTriggered(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
            DefaultChannelPipeline.logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", t);
        }
        
        @Override
        public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
            DefaultChannelPipeline.logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", o);
            ReferenceCountUtil.release(o);
        }
        
        @Override
        public void channelReadComplete(final ChannelHandlerContext channelHandlerContext) throws Exception {
        }
        
        static {
            TAIL_NAME = DefaultChannelPipeline.access$300(TailContext.class);
        }
    }
}
