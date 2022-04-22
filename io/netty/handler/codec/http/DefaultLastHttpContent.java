package io.netty.handler.codec.http;

import io.netty.util.internal.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.util.*;

public class DefaultLastHttpContent extends DefaultHttpContent implements LastHttpContent
{
    private final HttpHeaders trailingHeaders;
    private final boolean validateHeaders;
    
    public DefaultLastHttpContent() {
        this(Unpooled.buffer(0));
    }
    
    public DefaultLastHttpContent(final ByteBuf byteBuf) {
        this(byteBuf, true);
    }
    
    public DefaultLastHttpContent(final ByteBuf byteBuf, final boolean validateHeaders) {
        super(byteBuf);
        this.trailingHeaders = new TrailingHeaders(validateHeaders);
        this.validateHeaders = validateHeaders;
    }
    
    @Override
    public LastHttpContent copy() {
        final DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(this.content().copy(), this.validateHeaders);
        defaultLastHttpContent.trailingHeaders().set(this.trailingHeaders());
        return defaultLastHttpContent;
    }
    
    @Override
    public LastHttpContent duplicate() {
        final DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(this.content().duplicate(), this.validateHeaders);
        defaultLastHttpContent.trailingHeaders().set(this.trailingHeaders());
        return defaultLastHttpContent;
    }
    
    @Override
    public LastHttpContent retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public LastHttpContent retain() {
        super.retain();
        return this;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(StringUtil.NEWLINE);
        this.appendHeaders(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }
    
    private void appendHeaders(final StringBuilder sb) {
        for (final Map.Entry<String, V> entry : this.trailingHeaders()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append((String)entry.getValue());
            sb.append(StringUtil.NEWLINE);
        }
    }
    
    @Override
    public HttpContent retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public HttpContent retain() {
        return this.retain();
    }
    
    @Override
    public HttpContent duplicate() {
        return this.duplicate();
    }
    
    @Override
    public HttpContent copy() {
        return this.copy();
    }
    
    @Override
    public ByteBufHolder retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }
    
    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    private static final class TrailingHeaders extends DefaultHttpHeaders
    {
        TrailingHeaders(final boolean b) {
            super(b);
        }
        
        @Override
        void validateHeaderName0(final CharSequence charSequence) {
            super.validateHeaderName0(charSequence);
            if (HttpHeaders.equalsIgnoreCase("Content-Length", charSequence) || HttpHeaders.equalsIgnoreCase("Transfer-Encoding", charSequence) || HttpHeaders.equalsIgnoreCase("Trailer", charSequence)) {
                throw new IllegalArgumentException("prohibited trailing header: " + (Object)charSequence);
            }
        }
    }
}
