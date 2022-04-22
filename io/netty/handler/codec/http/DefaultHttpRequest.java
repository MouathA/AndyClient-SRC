package io.netty.handler.codec.http;

import io.netty.util.internal.*;

public class DefaultHttpRequest extends DefaultHttpMessage implements HttpRequest
{
    private HttpMethod method;
    private String uri;
    
    public DefaultHttpRequest(final HttpVersion httpVersion, final HttpMethod httpMethod, final String s) {
        this(httpVersion, httpMethod, s, true);
    }
    
    public DefaultHttpRequest(final HttpVersion httpVersion, final HttpMethod method, final String uri, final boolean b) {
        super(httpVersion, b);
        if (method == null) {
            throw new NullPointerException("method");
        }
        if (uri == null) {
            throw new NullPointerException("uri");
        }
        this.method = method;
        this.uri = uri;
    }
    
    @Override
    public HttpMethod getMethod() {
        return this.method;
    }
    
    @Override
    public String getUri() {
        return this.uri;
    }
    
    @Override
    public HttpRequest setMethod(final HttpMethod method) {
        if (method == null) {
            throw new NullPointerException("method");
        }
        this.method = method;
        return this;
    }
    
    @Override
    public HttpRequest setUri(final String uri) {
        if (uri == null) {
            throw new NullPointerException("uri");
        }
        this.uri = uri;
        return this;
    }
    
    @Override
    public HttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
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
        sb.append(this.getMethod());
        sb.append(' ');
        sb.append(this.getUri());
        sb.append(' ');
        sb.append(this.getProtocolVersion().text());
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
