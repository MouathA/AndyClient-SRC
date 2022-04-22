package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.google.common.base.*;

public class ByteArrayType extends Type
{
    public ByteArrayType() {
        super(byte[].class);
    }
    
    public void write(final ByteBuf byteBuf, final byte[] array) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, array.length);
        byteBuf.writeBytes(array);
    }
    
    @Override
    public byte[] read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        Preconditions.checkArgument(byteBuf.isReadable(primitive), (Object)"Length is fewer than readable bytes");
        final byte[] array = new byte[primitive];
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
