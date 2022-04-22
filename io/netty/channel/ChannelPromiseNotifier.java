package io.netty.channel;

import io.netty.util.concurrent.*;

public final class ChannelPromiseNotifier implements ChannelFutureListener
{
    private final ChannelPromise[] promises;
    
    public ChannelPromiseNotifier(final ChannelPromise... array) {
        if (array == null) {
            throw new NullPointerException("promises");
        }
        while (0 < array.length) {
            if (array[0] == null) {
                throw new IllegalArgumentException("promises contains null ChannelPromise");
            }
            int n = 0;
            ++n;
        }
        this.promises = array.clone();
    }
    
    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {
            final ChannelPromise[] promises = this.promises;
            while (0 < promises.length) {
                promises[0].setSuccess();
                int length = 0;
                ++length;
            }
            return;
        }
        final Throwable cause = channelFuture.cause();
        final ChannelPromise[] promises2 = this.promises;
        int length = promises2.length;
        while (0 < 0) {
            promises2[0].setFailure(cause);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void operationComplete(final Future future) throws Exception {
        this.operationComplete((ChannelFuture)future);
    }
}
