package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;

abstract class WebSocketProtocolHandler extends MessageToMessageDecoder
{
    protected void decode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        if (webSocketFrame instanceof PingWebSocketFrame) {
            webSocketFrame.content().retain();
            channelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(webSocketFrame.content()));
            return;
        }
        if (webSocketFrame instanceof PongWebSocketFrame) {
            return;
        }
        list.add(webSocketFrame.retain());
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        channelHandlerContext.close();
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)o, list);
    }
}
