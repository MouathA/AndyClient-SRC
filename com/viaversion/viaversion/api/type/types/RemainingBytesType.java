package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class RemainingBytesType extends Type
{
    public RemainingBytesType() {
        super(byte[].class);
    }
    
    @Override
    public byte[] read(final ByteBuf byteBuf) {
        final byte[] array = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(array);
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final byte[] array) {
        byteBuf.writeBytes(array);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (byte[])o);
    }
}
