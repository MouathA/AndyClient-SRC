package io.netty.channel;

import java.util.*;
import io.netty.util.concurrent.*;

public final class ChannelPromiseAggregator implements ChannelFutureListener
{
    private final ChannelPromise aggregatePromise;
    private Set pendingPromises;
    
    public ChannelPromiseAggregator(final ChannelPromise aggregatePromise) {
        if (aggregatePromise == null) {
            throw new NullPointerException("aggregatePromise");
        }
        this.aggregatePromise = aggregatePromise;
    }
    
    public ChannelPromiseAggregator add(final ChannelPromise... array) {
        if (array == null) {
            throw new NullPointerException("promises");
        }
        if (array.length == 0) {
            return this;
        }
        // monitorenter(this)
        if (this.pendingPromises == null) {
            if (array.length > 1) {
                final int length = array.length;
            }
            this.pendingPromises = new LinkedHashSet(2);
        }
        while (0 < array.length) {
            final ChannelPromise channelPromise = array[0];
            if (channelPromise != null) {
                this.pendingPromises.add(channelPromise);
                channelPromise.addListener((GenericFutureListener)this);
            }
            int n = 0;
            ++n;
        }
        // monitorexit(this)
        return this;
    }
    
    public synchronized void operationComplete(final ChannelFuture channelFuture) throws Exception {
        if (this.pendingPromises == null) {
            this.aggregatePromise.setSuccess();
        }
        else {
            this.pendingPromises.remove(channelFuture);
            if (!channelFuture.isSuccess()) {
                this.aggregatePromise.setFailure(channelFuture.cause());
                final Iterator<ChannelPromise> iterator = this.pendingPromises.iterator();
                while (iterator.hasNext()) {
                    iterator.next().setFailure(channelFuture.cause());
                }
            }
            else if (this.pendingPromises.isEmpty()) {
                this.aggregatePromise.setSuccess();
            }
        }
    }
    
    @Override
    public void operationComplete(final Future future) throws Exception {
        this.operationComplete((ChannelFuture)future);
    }
}
