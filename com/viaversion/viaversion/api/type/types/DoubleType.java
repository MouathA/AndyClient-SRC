package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class DoubleType extends Type implements TypeConverter
{
    public DoubleType() {
        super(Double.class);
    }
    
    @Deprecated
    @Override
    public Double read(final ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }
    
    public double readPrimitive(final ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Double n) {
        byteBuf.writeDouble(n);
    }
    
    public void writePrimitive(final ByteBuf byteBuf, final double n) {
        byteBuf.writeDouble(n);
    }
    
    @Override
    public Double from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }
        if (o instanceof Boolean) {
            return o ? 1.0 : 0.0;
        }
        return (Double)o;
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Double)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
