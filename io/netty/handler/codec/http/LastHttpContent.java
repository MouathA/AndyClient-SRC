package io.netty.handler.codec.http;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.util.*;

public interface LastHttpContent extends HttpContent
{
    public static final LastHttpContent EMPTY_LAST_CONTENT = new LastHttpContent() {
        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }
        
        @Override
        public LastHttpContent copy() {
            return LastHttpContent$1.EMPTY_LAST_CONTENT;
        }
        
        @Override
        public LastHttpContent duplicate() {
            return this;
        }
        
        @Override
        public HttpHeaders trailingHeaders() {
            return HttpHeaders.EMPTY_HEADERS;
        }
        
        @Override
        public DecoderResult getDecoderResult() {
            return DecoderResult.SUCCESS;
        }
        
        @Override
        public void setDecoderResult(final DecoderResult decoderResult) {
            throw new UnsupportedOperationException("read only");
        }
        
        @Override
        public int refCnt() {
            return 1;
        }
        
        @Override
        public LastHttpContent retain() {
            return this;
        }
        
        @Override
        public LastHttpContent retain(final int n) {
            return this;
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
        public String toString() {
            return "EmptyLastHttpContent";
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
    };
    
    HttpHeaders trailingHeaders();
    
    LastHttpContent copy();
    
    LastHttpContent retain(final int p0);
    
    LastHttpContent retain();
}
