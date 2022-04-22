package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.google.common.base.*;

public class VarIntArrayType extends Type
{
    public VarIntArrayType() {
        super(int[].class);
    }
    
    @Override
    public int[] read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        Preconditions.checkArgument(byteBuf.isReadable(primitive));
        final int[] array = new int[primitive];
        while (0 < array.length) {
            array[0] = Type.VAR_INT.readPrimitive(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final int[] array) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, array.length);
        while (0 < array.length) {
            Type.VAR_INT.writePrimitive(byteBuf, array[0]);
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
        this.write(byteBuf, (int[])o);
    }
}
