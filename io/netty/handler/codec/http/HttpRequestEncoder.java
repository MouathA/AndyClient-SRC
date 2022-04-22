package io.netty.handler.codec.http;

import io.netty.buffer.*;
import io.netty.util.*;

public class HttpRequestEncoder extends HttpObjectEncoder
{
    private static final char SLASH = '/';
    private static final char QUESTION_MARK = '?';
    private static final byte[] CRLF;
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return super.acceptOutboundMessage(o) && !(o instanceof HttpResponse);
    }
    
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpRequest httpRequest) throws Exception {
        httpRequest.getMethod().encode(byteBuf);
        byteBuf.writeByte(32);
        String s = httpRequest.getUri();
        if (s.length() == 0) {
            s += '/';
        }
        else {
            final int index = s.indexOf("://");
            if (index != -1 && s.charAt(0) != '/') {
                final int n = index + 3;
                final int index2 = s.indexOf(63, n);
                if (index2 == -1) {
                    if (s.lastIndexOf(47) <= n) {
                        s += '/';
                    }
                }
                else if (s.lastIndexOf(47, index2) <= n) {
                    final int length = s.length();
                    final StringBuilder sb = new StringBuilder(length + 1);
                    sb.append(s, 0, index2);
                    sb.append('/');
                    sb.append(s, index2, length);
                    s = sb.toString();
                }
            }
        }
        byteBuf.writeBytes(s.getBytes(CharsetUtil.UTF_8));
        byteBuf.writeByte(32);
        httpRequest.getProtocolVersion().encode(byteBuf);
        byteBuf.writeBytes(HttpRequestEncoder.CRLF);
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf byteBuf, final HttpMessage httpMessage) throws Exception {
        this.encodeInitialLine(byteBuf, (HttpRequest)httpMessage);
    }
    
    static {
        CRLF = new byte[] { 13, 10 };
    }
}
