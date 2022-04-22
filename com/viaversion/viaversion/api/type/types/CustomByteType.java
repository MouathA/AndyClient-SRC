package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class CustomByteType extends PartialType
{
    public CustomByteType(final Integer n) {
        super(n, byte[].class);
    }
    
    public byte[] read(final ByteBuf byteBuf, final Integer n) throws Exception {
        if (byteBuf.readableBytes() < n) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        final byte[] array = new byte[(int)n];
        byteBuf.readBytes(array);
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Integer n, final byte[] array) throws Exception {
        byteBuf.writeBytes(array);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (Integer)o, (byte[])o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (Integer)o);
    }
}
