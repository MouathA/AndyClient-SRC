package io.netty.channel.group;

import io.netty.channel.*;
import java.util.*;
import io.netty.util.concurrent.*;

final class DefaultChannelGroupFuture extends DefaultPromise implements ChannelGroupFuture
{
    private final ChannelGroup group;
    private final Map futures;
    private int successCount;
    private int failureCount;
    private final ChannelFutureListener childListener;
    
    DefaultChannelGroupFuture(final ChannelGroup group, final Collection collection, final EventExecutor eventExecutor) {
        super(eventExecutor);
        this.childListener = new ChannelFutureListener() {
            static final boolean $assertionsDisabled;
            final DefaultChannelGroupFuture this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                final boolean success = channelFuture.isSuccess();
                // monitorenter(this$0 = this.this$0)
                if (success) {
                    DefaultChannelGroupFuture.access$008(this.this$0);
                }
                else {
                    DefaultChannelGroupFuture.access$108(this.this$0);
                }
                final boolean b = DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) == DefaultChannelGroupFuture.access$200(this.this$0).size();
                assert DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) <= DefaultChannelGroupFuture.access$200(this.this$0).size();
                // monitorexit(this$0)
                if (b) {
                    if (DefaultChannelGroupFuture.access$100(this.this$0) > 0) {
                        final ArrayList<DefaultEntry> list = new ArrayList<DefaultEntry>(DefaultChannelGroupFuture.access$100(this.this$0));
                        for (final ChannelFuture channelFuture2 : DefaultChannelGroupFuture.access$200(this.this$0).values()) {
                            if (!channelFuture2.isSuccess()) {
                                list.add(new DefaultEntry(channelFuture2.channel(), channelFuture2.cause()));
                            }
                        }
                        DefaultChannelGroupFuture.access$300(this.this$0, new ChannelGroupException(list));
                    }
                    else {
                        DefaultChannelGroupFuture.access$400(this.this$0);
                    }
                }
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
            
            static {
                $assertionsDisabled = !DefaultChannelGroupFuture.class.desiredAssertionStatus();
            }
        };
        if (group == null) {
            throw new NullPointerException("group");
        }
        if (collection == null) {
            throw new NullPointerException("futures");
        }
        this.group = group;
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>();
        for (final ChannelFuture channelFuture : collection) {
            linkedHashMap.put(channelFuture.channel(), channelFuture);
        }
        this.futures = Collections.unmodifiableMap((Map<?, ?>)linkedHashMap);
        final Iterator<ChannelFuture> iterator2 = this.futures.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().addListener((GenericFutureListener)this.childListener);
        }
        if (this.futures.isEmpty()) {
            this.setSuccess0();
        }
    }
    
    DefaultChannelGroupFuture(final ChannelGroup group, final Map map, final EventExecutor eventExecutor) {
        super(eventExecutor);
        this.childListener = new ChannelFutureListener() {
            static final boolean $assertionsDisabled;
            final DefaultChannelGroupFuture this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                final boolean success = channelFuture.isSuccess();
                // monitorenter(this$0 = this.this$0)
                if (success) {
                    DefaultChannelGroupFuture.access$008(this.this$0);
                }
                else {
                    DefaultChannelGroupFuture.access$108(this.this$0);
                }
                final boolean b = DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) == DefaultChannelGroupFuture.access$200(this.this$0).size();
                assert DefaultChannelGroupFuture.access$000(this.this$0) + DefaultChannelGroupFuture.access$100(this.this$0) <= DefaultChannelGroupFuture.access$200(this.this$0).size();
                // monitorexit(this$0)
                if (b) {
                    if (DefaultChannelGroupFuture.access$100(this.this$0) > 0) {
                        final ArrayList<DefaultEntry> list = new ArrayList<DefaultEntry>(DefaultChannelGroupFuture.access$100(this.this$0));
                        for (final ChannelFuture channelFuture2 : DefaultChannelGroupFuture.access$200(this.this$0).values()) {
                            if (!channelFuture2.isSuccess()) {
                                list.add(new DefaultEntry(channelFuture2.channel(), channelFuture2.cause()));
                            }
                        }
                        DefaultChannelGroupFuture.access$300(this.this$0, new ChannelGroupException(list));
                    }
                    else {
                        DefaultChannelGroupFuture.access$400(this.this$0);
                    }
                }
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
            
            static {
                $assertionsDisabled = !DefaultChannelGroupFuture.class.desiredAssertionStatus();
            }
        };
        this.group = group;
        this.futures = Collections.unmodifiableMap((Map<?, ?>)map);
        final Iterator<ChannelFuture> iterator = this.futures.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().addListener((GenericFutureListener)this.childListener);
        }
        if (this.futures.isEmpty()) {
            this.setSuccess0();
        }
    }
    
    @Override
    public ChannelGroup group() {
        return this.group;
    }
    
    @Override
    public ChannelFuture find(final Channel channel) {
        return this.futures.get(channel);
    }
    
    @Override
    public Iterator iterator() {
        return this.futures.values().iterator();
    }
    
    @Override
    public synchronized boolean isPartialSuccess() {
        return this.successCount != 0 && this.successCount != this.futures.size();
    }
    
    @Override
    public synchronized boolean isPartialFailure() {
        return this.failureCount != 0 && this.failureCount != this.futures.size();
    }
    
    @Override
    public DefaultChannelGroupFuture addListener(final GenericFutureListener genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture addListeners(final GenericFutureListener... array) {
        super.addListeners(array);
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture removeListener(final GenericFutureListener genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture removeListeners(final GenericFutureListener... array) {
        super.removeListeners(array);
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture await() throws InterruptedException {
        super.await();
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }
    
    @Override
    public DefaultChannelGroupFuture sync() throws InterruptedException {
        super.sync();
        return this;
    }
    
    @Override
    public ChannelGroupException cause() {
        return (ChannelGroupException)super.cause();
    }
    
    private void setSuccess0() {
        super.setSuccess(null);
    }
    
    private void setFailure0(final ChannelGroupException failure) {
        super.setFailure(failure);
    }
    
    public DefaultChannelGroupFuture setSuccess(final Void void1) {
        throw new IllegalStateException();
    }
    
    public boolean trySuccess(final Void void1) {
        throw new IllegalStateException();
    }
    
    @Override
    public DefaultChannelGroupFuture setFailure(final Throwable t) {
        throw new IllegalStateException();
    }
    
    @Override
    public boolean tryFailure(final Throwable t) {
        throw new IllegalStateException();
    }
    
    @Override
    protected void checkDeadLock() {
        final EventExecutor executor = this.executor();
        if (executor != null && executor != ImmediateEventExecutor.INSTANCE && executor.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }
    
    @Override
    public Promise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public boolean trySuccess(final Object o) {
        return this.trySuccess((Void)o);
    }
    
    @Override
    public Promise setSuccess(final Object o) {
        return this.setSuccess((Void)o);
    }
    
    @Override
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Promise await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public Promise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public Promise sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public Promise removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public Promise removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public Promise addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public Promise addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public Throwable cause() {
        return this.cause();
    }
    
    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public Future removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public Future removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public Future addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public Future addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public ChannelGroupFuture sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ChannelGroupFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ChannelGroupFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ChannelGroupFuture await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ChannelGroupFuture removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ChannelGroupFuture removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ChannelGroupFuture addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ChannelGroupFuture addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    static int access$008(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.successCount++;
    }
    
    static int access$108(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.failureCount++;
    }
    
    static int access$000(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.successCount;
    }
    
    static int access$100(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.failureCount;
    }
    
    static Map access$200(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        return defaultChannelGroupFuture.futures;
    }
    
    static void access$300(final DefaultChannelGroupFuture defaultChannelGroupFuture, final ChannelGroupException failure0) {
        defaultChannelGroupFuture.setFailure0(failure0);
    }
    
    static void access$400(final DefaultChannelGroupFuture defaultChannelGroupFuture) {
        defaultChannelGroupFuture.setSuccess0();
    }
    
    private static final class DefaultEntry implements Map.Entry
    {
        private final Object key;
        private final Object value;
        
        DefaultEntry(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        @Override
        public Object setValue(final Object o) {
            throw new UnsupportedOperationException("read-only");
        }
    }
}
