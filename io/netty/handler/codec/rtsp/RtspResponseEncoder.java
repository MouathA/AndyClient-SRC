package io.netty.handler.codec.rtsp;

import io.netty.buffer.*;
import io.netty.util.*;
import io.netty.handler.codec.http.*;

public class RtspResponseEncoder extends RtspObjectEncoder
{
    private static final byte[] CRLF;
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return o instanceof FullHttpResponse;
    }
    
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpResponse httpResponse) throws Exception {
        HttpHeaders.encodeAscii(httpResponse.getProtocolVersion().toString(), byteBuf);
        byteBuf.writeByte(32);
        byteBuf.writeBytes(String.valueOf(httpResponse.getStatus().code()).getBytes(CharsetUtil.US_ASCII));
        byteBuf.writeByte(32);
        HttpObjectEncoder.encodeAscii(String.valueOf(httpResponse.getStatus().reasonPhrase()), byteBuf);
        byteBuf.writeBytes(RtspResponseEncoder.CRLF);
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpResponse)httpMessage);
    }
    
    static {
        CRLF = new byte[] { 13, 10 };
    }
}
