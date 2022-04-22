package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class ByteType extends Type implements TypeConverter
{
    public ByteType() {
        super(Byte.class);
    }
    
    public byte readPrimitive(final ByteBuf byteBuf) {
        return byteBuf.readByte();
    }
    
    public void writePrimitive(final ByteBuf byteBuf, final byte b) {
        byteBuf.writeByte(b);
    }
    
    @Deprecated
    @Override
    public Byte read(final ByteBuf byteBuf) {
        return byteBuf.readByte();
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Byte b) {
        byteBuf.writeByte(b);
    }
    
    @Override
    public Byte from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).byteValue();
        }
        if (o instanceof Boolean) {
            return (byte)(((boolean)o) ? 1 : 0);
        }
        return (Byte)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Byte)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
