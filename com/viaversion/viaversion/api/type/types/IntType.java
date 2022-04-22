package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class IntType extends Type implements TypeConverter
{
    public IntType() {
        super(Integer.class);
    }
    
    @Override
    public Integer read(final ByteBuf byteBuf) {
        return byteBuf.readInt();
    }
    
    public void write(final ByteBuf byteBuf, final Integer n) {
        byteBuf.writeInt(n);
    }
    
    @Override
    public Integer from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof Boolean) {
            return ((boolean)o) ? 1 : 0;
        }
        return (Integer)o;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Integer)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
