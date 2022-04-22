package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class UnsignedByteType extends Type implements TypeConverter
{
    public UnsignedByteType() {
        super("Unsigned Byte", Short.class);
    }
    
    @Override
    public Short read(final ByteBuf byteBuf) {
        return byteBuf.readUnsignedByte();
    }
    
    public void write(final ByteBuf byteBuf, final Short n) {
        byteBuf.writeByte(n);
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
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Short)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
