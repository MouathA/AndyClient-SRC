package io.netty.handler.codec.http;

import io.netty.buffer.*;
import io.netty.util.*;

public class DefaultFullHttpRequest extends DefaultHttpRequest implements FullHttpRequest
{
    private final ByteBuf content;
    private final HttpHeaders trailingHeader;
    private final boolean validateHeaders;
    
    public DefaultFullHttpRequest(final HttpVersion httpVersion, final HttpMethod httpMethod, final String s) {
        this(httpVersion, httpMethod, s, Unpooled.buffer(0));
    }
    
    public DefaultFullHttpRequest(final HttpVersion httpVersion, final HttpMethod httpMethod, final String s, final ByteBuf byteBuf) {
        this(httpVersion, httpMethod, s, byteBuf, true);
    }
    
    public DefaultFullHttpRequest(final HttpVersion httpVersion, final HttpMethod httpMethod, final String s, final ByteBuf content, final boolean validateHeaders) {
        super(httpVersion, httpMethod, s, validateHeaders);
        if (content == null) {
            throw new NullPointerException("content");
        }
        this.content = content;
        this.trailingHeader = new DefaultHttpHeaders(validateHeaders);
        this.validateHeaders = validateHeaders;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeader;
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
    public FullHttpRequest retain() {
        this.content.retain();
        return this;
    }
    
    @Override
    public FullHttpRequest retain(final int n) {
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
    public FullHttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
        super.setProtocolVersion(protocolVersion);
        return this;
    }
    
    @Override
    public FullHttpRequest setMethod(final HttpMethod method) {
        super.setMethod(method);
        return this;
    }
    
    @Override
    public FullHttpRequest setUri(final String uri) {
        super.setUri(uri);
        return this;
    }
    
    @Override
    public FullHttpRequest copy() {
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().copy(), this.validateHeaders);
        defaultFullHttpRequest.headers().set(this.headers());
        defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
        return defaultFullHttpRequest;
    }
    
    @Override
    public FullHttpRequest duplicate() {
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().duplicate(), this.validateHeaders);
        defaultFullHttpRequest.headers().set(this.headers());
        defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
        return defaultFullHttpRequest;
    }
    
    @Override
    public HttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
        return this.setProtocolVersion(protocolVersion);
    }
    
    @Override
    public HttpRequest setUri(final String uri) {
        return this.setUri(uri);
    }
    
    @Override
    public HttpRequest setMethod(final HttpMethod method) {
        return this.setMethod(method);
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
