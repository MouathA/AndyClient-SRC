package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class ShortByteArrayType extends Type
{
    public ShortByteArrayType() {
        super(byte[].class);
    }
    
    public void write(final ByteBuf byteBuf, final byte[] array) throws Exception {
        byteBuf.writeShort(array.length);
        byteBuf.writeBytes(array);
    }
    
    @Override
    public byte[] read(final ByteBuf byteBuf) throws Exception {
        final byte[] array = new byte[byteBuf.readShort()];
        byteBuf.readBytes(array);
        return array;
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
