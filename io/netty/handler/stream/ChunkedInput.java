package io.netty.handler.stream;

import io.netty.channel.*;

public interface ChunkedInput
{
    boolean isEndOfInput() throws Exception;
    
    void close() throws Exception;
    
    Object readChunk(final ChannelHandlerContext p0) throws Exception;
}
