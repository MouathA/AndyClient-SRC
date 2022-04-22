package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class EulerAngleType extends Type
{
    public EulerAngleType() {
        super(EulerAngle.class);
    }
    
    @Override
    public EulerAngle read(final ByteBuf byteBuf) throws Exception {
        return new EulerAngle(Type.FLOAT.readPrimitive(byteBuf), Type.FLOAT.readPrimitive(byteBuf), Type.FLOAT.readPrimitive(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final EulerAngle eulerAngle) throws Exception {
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.x());
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.y());
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.z());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (EulerAngle)o);
    }
}
