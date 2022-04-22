package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class VectorType extends Type
{
    public VectorType() {
        super(Vector.class);
    }
    
    @Override
    public Vector read(final ByteBuf byteBuf) throws Exception {
        return new Vector(Type.INT.read(byteBuf), Type.INT.read(byteBuf), Type.INT.read(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final Vector vector) throws Exception {
        Type.INT.write(byteBuf, vector.blockX());
        Type.INT.write(byteBuf, vector.blockY());
        Type.INT.write(byteBuf, vector.blockZ());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Vector)o);
    }
}
