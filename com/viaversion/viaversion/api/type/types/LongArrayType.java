package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class LongArrayType extends Type
{
    public LongArrayType() {
        super(long[].class);
    }
    
    @Override
    public long[] read(final ByteBuf byteBuf) throws Exception {
        final long[] array = new long[Type.VAR_INT.readPrimitive(byteBuf)];
        while (0 < array.length) {
            array[0] = Type.LONG.readPrimitive(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final long[] array) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, array.length);
        while (0 < array.length) {
            Type.LONG.writePrimitive(byteBuf, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (long[])o);
    }
}
