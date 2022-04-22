package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class FloatType extends Type implements TypeConverter
{
    public FloatType() {
        super(Float.class);
    }
    
    public float readPrimitive(final ByteBuf byteBuf) {
        return byteBuf.readFloat();
    }
    
    public void writePrimitive(final ByteBuf byteBuf, final float n) {
        byteBuf.writeFloat(n);
    }
    
    @Deprecated
    @Override
    public Float read(final ByteBuf byteBuf) {
        return byteBuf.readFloat();
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Float n) {
        byteBuf.writeFloat(n);
    }
    
    @Override
    public Float from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).floatValue();
        }
        if (o instanceof Boolean) {
            return o ? 1.0f : 0.0f;
        }
        return (Float)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Float)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
