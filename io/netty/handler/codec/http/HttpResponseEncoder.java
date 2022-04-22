package io.netty.handler.codec.http;

import io.netty.buffer.*;

public class HttpResponseEncoder extends HttpObjectEncoder
{
    private static final byte[] CRLF;
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return super.acceptOutboundMessage(o) && !(o instanceof HttpRequest);
    }
    
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpResponse httpResponse) throws Exception {
        httpResponse.getProtocolVersion().encode(byteBuf);
        byteBuf.writeByte(32);
        httpResponse.getStatus().encode(byteBuf);
        byteBuf.writeBytes(HttpResponseEncoder.CRLF);
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpResponse)httpMessage);
    }
    
    static {
        CRLF = new byte[] { 13, 10 };
    }
}
