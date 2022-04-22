package io.netty.handler.codec.http;

import io.netty.buffer.*;
import io.netty.util.*;

public class DefaultFullHttpResponse extends DefaultHttpResponse implements FullHttpResponse
{
    private final ByteBuf content;
    private final HttpHeaders trailingHeaders;
    private final boolean validateHeaders;
    
    public DefaultFullHttpResponse(final HttpVersion httpVersion, final HttpResponseStatus httpResponseStatus) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0));
    }
    
    public DefaultFullHttpResponse(final HttpVersion httpVersion, final HttpResponseStatus httpResponseStatus, final ByteBuf byteBuf) {
        this(httpVersion, httpResponseStatus, byteBuf, true);
    }
    
    public DefaultFullHttpResponse(final HttpVersion httpVersion, final HttpResponseStatus httpResponseStatus, final ByteBuf content, final boolean validateHeaders) {
        super(httpVersion, httpResponseStatus, validateHeaders);
        if (content == null) {
            throw new NullPointerException("content");
        }
        this.content = content;
        this.trailingHeaders = new DefaultHttpHeaders(validateHeaders);
        this.validateHeaders = validateHeaders;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }
    
    @Override
    public ByteBuf content() {
        return this.content;
    }
    
    @Override
    public int refCnt() {
        return this.content.refCnt();
    }
    
    @Override
    public FullHttpResponse retain() {
        this.content.retain();
        return this;
    }
    
    @Override
    public FullHttpResponse retain(final int n) {
        this.content.retain(n);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.content.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.content.release(n);
    }
    
    @Override
    public FullHttpResponse setProtocolVersion(final HttpVersion protocolVersion) {
        super.setProtocolVersion(protocolVersion);
        return this;
    }
    
    @Override
    public FullHttpResponse setStatus(final HttpResponseStatus status) {
        super.setStatus(status);
        return this;
    }
    
    @Override
    public FullHttpResponse copy() {
        final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), this.content().copy(), this.validateHeaders);
        defaultFullHttpResponse.headers().set(this.headers());
        defaultFullHttpResponse.trailingHeaders().set(this.trailingHeaders());
        return defaultFullHttpResponse;
    }
    
    @Override
    public FullHttpResponse duplicate() {
        final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), this.content().duplicate(), this.validateHeaders);
        defaultFullHttpResponse.headers().set(this.headers());
        defaultFullHttpResponse.trailingHeaders().set(this.trailingHeaders());
        return defaultFullHttpResponse;
    }
    
    @Override
    public HttpResponse setProtocolVersion(final HttpVersion protocolVersion) {
        return this.setProtocolVersion(protocolVersion);
    }
    
    @Override
    public HttpResponse setStatus(final HttpResponseStatus status) {
        return this.setStatus(status);
    }
    
    @Override
    public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
        return this.setProtocolVersion(protocolVersion);
    }
    
    @Override
    public FullHttpMessage retain() {
        return this.retain();
    }
    
    @Override
    public FullHttpMessage retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public FullHttpMessage copy() {
        return this.copy();
    }
    
    @Override
    public LastHttpContent retain() {
        return this.retain();
    }
    
    @Override
    public LastHttpContent retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public LastHttpContent copy() {
        return this.copy();
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
}
