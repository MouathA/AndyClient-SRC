package io.netty.handler.codec.spdy;

import io.netty.handler.codec.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.util.*;

public class SpdyHttpResponseStreamIdHandler extends MessageToMessageCodec
{
    private static final Integer NO_ID;
    private final Queue ids;
    
    public SpdyHttpResponseStreamIdHandler() {
        this.ids = new LinkedList();
    }
    
    @Override
    public boolean acceptInboundMessage(final Object o) throws Exception {
        return o instanceof HttpMessage || o instanceof SpdyRstStreamFrame;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final HttpMessage httpMessage, final List list) throws Exception {
        final Integer n = this.ids.poll();
        if (n != null && n != (int)SpdyHttpResponseStreamIdHandler.NO_ID && !httpMessage.headers().contains("X-SPDY-Stream-ID")) {
            SpdyHttpHeaders.setStreamId(httpMessage, n);
        }
        list.add(ReferenceCountUtil.retain(httpMessage));
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        if (o instanceof HttpMessage) {
            if (!((HttpMessage)o).headers().contains("X-SPDY-Stream-ID")) {
                this.ids.add(SpdyHttpResponseStreamIdHandler.NO_ID);
            }
            else {
                this.ids.add(SpdyHttpHeaders.getStreamId((HttpMessage)o));
            }
        }
        else if (o instanceof SpdyRstStreamFrame) {
            this.ids.remove(((SpdyRstStreamFrame)o).streamId());
        }
        list.add(ReferenceCountUtil.retain(o));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (HttpMessage)o, list);
    }
    
    static {
        NO_ID = -1;
    }
}
