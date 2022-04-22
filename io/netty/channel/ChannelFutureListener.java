package io.netty.channel;

import io.netty.util.concurrent.*;

public interface ChannelFutureListener extends GenericFutureListener
{
    public static final ChannelFutureListener CLOSE = new ChannelFutureListener() {
        public void operationComplete(final ChannelFuture channelFuture) {
            channelFuture.channel().close();
        }
        
        @Override
        public void operationComplete(final Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener() {
        public void operationComplete(final ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().close();
            }
        }
        
        @Override
        public void operationComplete(final Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener() {
        public void operationComplete(final ChannelFuture channelFuture) {
            if (!channelFuture.isSuccess()) {
                channelFuture.channel().pipeline().fireExceptionCaught(channelFuture.cause());
            }
        }
        
        @Override
        public void operationComplete(final Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
}
