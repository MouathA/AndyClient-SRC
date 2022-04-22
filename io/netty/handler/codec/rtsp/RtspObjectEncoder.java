package io.netty.handler.codec.rtsp;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

@ChannelHandler.Sharable
public abstract class RtspObjectEncoder extends HttpObjectEncoder
{
    protected RtspObjectEncoder() {
    }
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return o instanceof FullHttpMessage;
    }
}
