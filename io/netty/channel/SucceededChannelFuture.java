package io.netty.channel;

import io.netty.util.concurrent.*;

final class SucceededChannelFuture extends CompleteChannelFuture
{
    SucceededChannelFuture(final Channel channel, final EventExecutor eventExecutor) {
        super(channel, eventExecutor);
    }
    
    @Override
    public Throwable cause() {
        return null;
    }
    
    @Override
    public boolean isSuccess() {
        return true;
    }
}
