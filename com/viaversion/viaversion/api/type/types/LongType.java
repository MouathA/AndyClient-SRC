package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class LongType extends Type implements TypeConverter
{
    public LongType() {
        super(Long.class);
    }
    
    @Deprecated
    @Override
    public Long read(final ByteBuf byteBuf) {
        return byteBuf.readLong();
    }
    
    @Deprecated
    public void write(final ByteBuf byteBuf, final Long n) {
        byteBuf.writeLong(n);
    }
    
    @Override
    public Long from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).longValue();
        }
        if (o instanceof Boolean) {
            return (long)(((boolean)o) ? 1 : 0);
        }
        return (Long)o;
    }
    
    public long readPrimitive(final ByteBuf byteBuf) {
        return byteBuf.readLong();
    }
    
    public void writePrimitive(final ByteBuf byteBuf, final long n) {
        byteBuf.writeLong(n);
    }
    
    @Deprecated
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Deprecated
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Long)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
