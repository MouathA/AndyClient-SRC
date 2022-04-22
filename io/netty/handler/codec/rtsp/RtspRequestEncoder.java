package io.netty.handler.codec.rtsp;

import io.netty.buffer.*;
import io.netty.util.*;
import io.netty.handler.codec.http.*;

public class RtspRequestEncoder extends RtspObjectEncoder
{
    private static final byte[] CRLF;
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return o instanceof FullHttpRequest;
    }
    
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpRequest httpRequest) throws Exception {
        HttpHeaders.encodeAscii(httpRequest.getMethod().toString(), byteBuf);
        byteBuf.writeByte(32);
        byteBuf.writeBytes(httpRequest.getUri().getBytes(CharsetUtil.UTF_8));
        byteBuf.writeByte(32);
        HttpObjectEncoder.encodeAscii(httpRequest.getProtocolVersion().toString(), byteBuf);
        byteBuf.writeBytes(RtspRequestEncoder.CRLF);
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpRequest)httpMessage);
    }
    
    static {
        CRLF = new byte[] { 13, 10 };
    }
}
