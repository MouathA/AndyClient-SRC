package io.netty.handler.codec;

import io.netty.buffer.*;

public final class Delimiters
{
    public static ByteBuf[] nulDelimiter() {
        return new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { 0 }) };
    }
    
    public static ByteBuf[] lineDelimiter() {
        return new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { 13, 10 }), Unpooled.wrappedBuffer(new byte[] { 10 }) };
    }
    
    private Delimiters() {
    }
}
