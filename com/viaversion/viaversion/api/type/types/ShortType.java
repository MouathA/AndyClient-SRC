package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class ShortType extends Type implements TypeConverter
{
    public ShortType() {
        super(Short.class);
    }
    
    public short readPrimitive(final ByteBuf byteBuf) {
        return byteBuf.readShort();
    }
    
    public void writePrimitive(final ByteBuf byteBuf, final short n) {
        byteBuf.writeShort(n);
    }
    
    @Deprecated
    @Override
    public Short read(final ByteBuf byteBuf) {
        return byteBuf.readShort();
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Short n) {
        byteBuf.writeShort(n);
    }
    
    @Override
    public Short from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).shortValue();
        }
        if (o instanceof Boolean) {
            return (short)(((boolean)o) ? 1 : 0);
        }
        return (Short)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Short)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
