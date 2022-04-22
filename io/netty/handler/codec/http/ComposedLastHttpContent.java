package io.netty.handler.codec.http;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.util.*;

final class ComposedLastHttpContent implements LastHttpContent
{
    private final HttpHeaders trailingHeaders;
    private DecoderResult result;
    
    ComposedLastHttpContent(final HttpHeaders trailingHeaders) {
        this.trailingHeaders = trailingHeaders;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }
    
    @Override
    public LastHttpContent copy() {
        final DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        defaultLastHttpContent.trailingHeaders().set(this.trailingHeaders());
        return defaultLastHttpContent;
    }
    
    @Override
    public LastHttpContent retain(final int n) {
        return this;
    }
    
    @Override
    public LastHttpContent retain() {
        return this;
    }
    
    @Override
    public HttpContent duplicate() {
        return this.copy();
    }
    
    @Override
    public ByteBuf content() {
        return Unpooled.EMPTY_BUFFER;
    }
    
    @Override
    public DecoderResult getDecoderResult() {
        return this.result;
    }
    
    @Override
    public void setDecoderResult(final DecoderResult result) {
        this.result = result;
    }
    
    @Override
    public int refCnt() {
        return 1;
    }
    
    @Override
    public boolean release() {
        return false;
    }
    
    @Override
    public boolean release(final int n) {
        return false;
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
