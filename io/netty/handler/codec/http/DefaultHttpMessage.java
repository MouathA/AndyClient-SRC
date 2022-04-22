package io.netty.handler.codec.http;

import io.netty.util.internal.*;
import java.util.*;

public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage
{
    private HttpVersion version;
    private final HttpHeaders headers;
    
    protected DefaultHttpMessage(final HttpVersion httpVersion) {
        this(httpVersion, true);
    }
    
    protected DefaultHttpMessage(final HttpVersion version, final boolean b) {
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.version = version;
        this.headers = new DefaultHttpHeaders(b);
    }
    
    @Override
    public HttpHeaders headers() {
        return this.headers;
    }
    
    @Override
    public HttpVersion getProtocolVersion() {
        return this.version;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(version: ");
        sb.append(this.getProtocolVersion().text());
        sb.append(", keepAlive: ");
        sb.append(HttpHeaders.isKeepAlive(this));
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        this.appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
    
    @Override
    public HttpMessage setProtocolVersion(final HttpVersion version) {
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.version = version;
        return this;
    }
    
    void appendHeaders(final StringBuilder sb) {
        for (final Map.Entry<String, V> entry : this.headers()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append((String)entry.getValue());
            sb.append(StringUtil.NEWLINE);
        }
    }
}
