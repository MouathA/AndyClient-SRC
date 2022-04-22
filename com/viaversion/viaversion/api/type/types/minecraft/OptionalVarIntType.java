package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class OptionalVarIntType extends Type
{
    public OptionalVarIntType() {
        super(Integer.class);
    }
    
    @Override
    public Integer read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        if (primitive == 0) {
            return null;
        }
        return primitive - 1;
    }
    
    public void write(final ByteBuf byteBuf, final Integer n) throws Exception {
        if (n == null) {
            Type.VAR_INT.writePrimitive(byteBuf, 0);
        }
        else {
            Type.VAR_INT.writePrimitive(byteBuf, n + 1);
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Integer)o);
    }
}
