package io.netty.handler.codec.http;

import io.netty.handler.stream.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class HttpChunkedInput implements ChunkedInput
{
    private final ChunkedInput input;
    private final LastHttpContent lastHttpContent;
    private boolean sentLastChunk;
    
    public HttpChunkedInput(final ChunkedInput input) {
        this.input = input;
        this.lastHttpContent = LastHttpContent.EMPTY_LAST_CONTENT;
    }
    
    public HttpChunkedInput(final ChunkedInput input, final LastHttpContent lastHttpContent) {
        this.input = input;
        this.lastHttpContent = lastHttpContent;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        return this.input.isEndOfInput() && this.sentLastChunk;
    }
    
    @Override
    public void close() throws Exception {
        this.input.close();
    }
    
    @Override
    public HttpContent readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.input.isEndOfInput()) {
            return new DefaultHttpContent((ByteBuf)this.input.readChunk(channelHandlerContext));
        }
        if (this.sentLastChunk) {
            return null;
        }
        this.sentLastChunk = true;
        return this.lastHttpContent;
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}
