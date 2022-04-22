package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.lang.reflect.*;
import io.netty.buffer.*;

public class ArrayType extends Type
{
    private final Type elementType;
    
    public ArrayType(final Type elementType) {
        super(elementType.getTypeName() + " Array", getArrayClass(elementType.getOutputClass()));
        this.elementType = elementType;
    }
    
    public static Class getArrayClass(final Class clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }
    
    @Override
    public Object[] read(final ByteBuf byteBuf) throws Exception {
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final Object[] array = (Object[])Array.newInstance(this.elementType.getOutputClass(), primitive);
        while (0 < primitive) {
            array[0] = this.elementType.read(byteBuf);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void write(final ByteBuf byteBuf, final Object[] array) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, array.length);
        while (0 < array.length) {
            this.elementType.write(byteBuf, array[0]);
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
        this.write(byteBuf, (Object[])o);
    }
}
