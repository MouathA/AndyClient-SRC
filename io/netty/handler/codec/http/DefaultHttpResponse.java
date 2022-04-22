package io.netty.handler.codec.http;

import io.netty.util.internal.*;

public class DefaultHttpResponse extends DefaultHttpMessage implements HttpResponse
{
    private HttpResponseStatus status;
    
    public DefaultHttpResponse(final HttpVersion httpVersion, final HttpResponseStatus httpResponseStatus) {
        this(httpVersion, httpResponseStatus, true);
    }
    
    public DefaultHttpResponse(final HttpVersion httpVersion, final HttpResponseStatus status, final boolean b) {
        super(httpVersion, b);
        if (status == null) {
            throw new NullPointerException("status");
        }
        this.status = status;
    }
    
    @Override
    public HttpResponseStatus getStatus() {
        return this.status;
    }
    
    @Override
    public HttpResponse setStatus(final HttpResponseStatus status) {
        if (status == null) {
            throw new NullPointerException("status");
        }
        this.status = status;
        return this;
    }
    
    @Override
    public HttpResponse setProtocolVersion(final HttpVersion protocolVersion) {
        super.setProtocolVersion(protocolVersion);
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(decodeResult: ");
        sb.append(this.getDecoderResult());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append(this.getProtocolVersion().text());
        sb.append(' ');
        sb.append(this.getStatus());
        sb.append(StringUtil.NEWLINE);
        this.appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
    
    @Override
    public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
        return this.setProtocolVersion(protocolVersion);
    }
}
