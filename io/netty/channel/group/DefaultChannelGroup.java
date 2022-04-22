package io.netty.channel.group;

import java.util.concurrent.atomic.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import io.netty.util.*;
import java.util.*;
import io.netty.util.internal.*;

public class DefaultChannelGroup extends AbstractSet implements ChannelGroup
{
    private static final AtomicInteger nextId;
    private final String name;
    private final EventExecutor executor;
    private final ConcurrentSet serverChannels;
    private final ConcurrentSet nonServerChannels;
    private final ChannelFutureListener remover;
    
    public DefaultChannelGroup(final EventExecutor eventExecutor) {
        this("group-0x" + Integer.toHexString(DefaultChannelGroup.nextId.incrementAndGet()), eventExecutor);
    }
    
    public DefaultChannelGroup(final String name, final EventExecutor executor) {
        this.serverChannels = new ConcurrentSet();
        this.nonServerChannels = new ConcurrentSet();
        this.remover = new ChannelFutureListener() {
            final DefaultChannelGroup this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                this.this$0.remove(channelFuture.channel());
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        };
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
        this.executor = executor;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public boolean isEmpty() {
        return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
    }
    
    @Override
    public int size() {
        return this.nonServerChannels.size() + this.serverChannels.size();
    }
    
    @Override
    public boolean contains(final Object o) {
        if (!(o instanceof Channel)) {
            return false;
        }
        final Channel channel = (Channel)o;
        if (o instanceof ServerChannel) {
            return this.serverChannels.contains(channel);
        }
        return this.nonServerChannels.contains(channel);
    }
    
    public boolean add(final Channel channel) {
        final boolean add = ((channel instanceof ServerChannel) ? this.serverChannels : this.nonServerChannels).add(channel);
        if (add) {
            channel.closeFuture().addListener((GenericFutureListener)this.remover);
        }
        return add;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (!(o instanceof Channel)) {
            return false;
        }
        final Channel channel = (Channel)o;
        boolean b;
        if (channel instanceof ServerChannel) {
            b = this.serverChannels.remove(channel);
        }
        else {
            b = this.nonServerChannels.remove(channel);
        }
        if (!b) {
            return false;
        }
        channel.closeFuture().removeListener((GenericFutureListener)this.remover);
        return true;
    }
    
    @Override
    public void clear() {
        this.nonServerChannels.clear();
        this.serverChannels.clear();
    }
    
    @Override
    public Iterator iterator() {
        return new CombinedIterator(this.serverChannels.iterator(), this.nonServerChannels.iterator());
    }
    
    @Override
    public Object[] toArray() {
        final ArrayList list = new ArrayList(this.size());
        list.addAll(this.serverChannels);
        list.addAll(this.nonServerChannels);
        return list.toArray();
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        final ArrayList list = new ArrayList(this.size());
        list.addAll(this.serverChannels);
        list.addAll(this.nonServerChannels);
        return list.toArray(array);
    }
    
    @Override
    public ChannelGroupFuture close() {
        return this.close(ChannelMatchers.all());
    }
    
    @Override
    public ChannelGroupFuture disconnect() {
        return this.disconnect(ChannelMatchers.all());
    }
    
    @Override
    public ChannelGroupFuture deregister() {
        return this.deregister(ChannelMatchers.all());
    }
    
    @Override
    public ChannelGroupFuture write(final Object o) {
        return this.write(o, ChannelMatchers.all());
    }
    
    private static Object safeDuplicate(final Object o) {
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).duplicate().retain();
        }
        if (o instanceof ByteBufHolder) {
            return ((ByteBufHolder)o).duplicate().retain();
        }
        return ReferenceCountUtil.retain(o);
    }
    
    @Override
    public ChannelGroupFuture write(final Object o, final ChannelMatcher channelMatcher) {
        if (o == null) {
            throw new NullPointerException("message");
        }
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (final Channel channel : this.nonServerChannels) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.write(safeDuplicate(o)));
            }
        }
        ReferenceCountUtil.release(o);
        return new DefaultChannelGroupFuture(this, linkedHashMap, this.executor);
    }
    
    @Override
    public ChannelGroup flush() {
        return this.flush(ChannelMatchers.all());
    }
    
    @Override
    public ChannelGroupFuture flushAndWrite(final Object o) {
        return this.writeAndFlush(o);
    }
    
    @Override
    public ChannelGroupFuture writeAndFlush(final Object o) {
        return this.writeAndFlush(o, ChannelMatchers.all());
    }
    
    @Override
    public ChannelGroupFuture disconnect(final ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (final Channel channel : this.serverChannels) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.disconnect());
            }
        }
        for (final Channel channel2 : this.nonServerChannels) {
            if (channelMatcher.matches(channel2)) {
                linkedHashMap.put(channel2, channel2.disconnect());
            }
        }
        return new DefaultChannelGroupFuture(this, linkedHashMap, this.executor);
    }
    
    @Override
    public ChannelGroupFuture close(final ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (final Channel channel : this.serverChannels) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.close());
            }
        }
        for (final Channel channel2 : this.nonServerChannels) {
            if (channelMatcher.matches(channel2)) {
                linkedHashMap.put(channel2, channel2.close());
            }
        }
        return new DefaultChannelGroupFuture(this, linkedHashMap, this.executor);
    }
    
    @Override
    public ChannelGroupFuture deregister(final ChannelMatcher channelMatcher) {
        if (channelMatcher == null) {
            throw new NullPointerException("matcher");
        }
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (final Channel channel : this.serverChannels) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.deregister());
            }
        }
        for (final Channel channel2 : this.nonServerChannels) {
            if (channelMatcher.matches(channel2)) {
                linkedHashMap.put(channel2, channel2.deregister());
            }
        }
        return new DefaultChannelGroupFuture(this, linkedHashMap, this.executor);
    }
    
    @Override
    public ChannelGroup flush(final ChannelMatcher channelMatcher) {
        for (final Channel channel : this.nonServerChannels) {
            if (channelMatcher.matches(channel)) {
                channel.flush();
            }
        }
        return this;
    }
    
    @Override
    public ChannelGroupFuture flushAndWrite(final Object o, final ChannelMatcher channelMatcher) {
        return this.writeAndFlush(o, channelMatcher);
    }
    
    @Override
    public ChannelGroupFuture writeAndFlush(final Object o, final ChannelMatcher channelMatcher) {
        if (o == null) {
            throw new NullPointerException("message");
        }
        final LinkedHashMap<Channel, ChannelFuture> linkedHashMap = new LinkedHashMap<Channel, ChannelFuture>(this.size());
        for (final Channel channel : this.nonServerChannels) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.writeAndFlush(safeDuplicate(o)));
            }
        }
        ReferenceCountUtil.release(o);
        return new DefaultChannelGroupFuture(this, linkedHashMap, this.executor);
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
    
    public int compareTo(final ChannelGroup channelGroup) {
        final int compareTo = this.name().compareTo(channelGroup.name());
        if (compareTo != 0) {
            return compareTo;
        }
        return System.identityHashCode(this) - System.identityHashCode(channelGroup);
    }
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(name: " + this.name() + ", size: " + this.size() + ')';
    }
    
    @Override
    public boolean add(final Object o) {
        return this.add((Channel)o);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ChannelGroup)o);
    }
    
    static {
        nextId = new AtomicInteger();
    }
}
