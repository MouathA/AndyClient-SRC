package com.viaversion.viaversion.api.type;

import io.netty.buffer.*;

public abstract class PartialType extends Type
{
    private final Object param;
    
    protected PartialType(final Object param, final Class clazz) {
        super(clazz);
        this.param = param;
    }
    
    protected PartialType(final Object param, final String s, final Class clazz) {
        super(s, clazz);
        this.param = param;
    }
    
    public abstract Object read(final ByteBuf p0, final Object p1) throws Exception;
    
    public abstract void write(final ByteBuf p0, final Object p1, final Object p2) throws Exception;
    
    @Override
    public final Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf, this.param);
    }
    
    @Override
    public final void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, this.param, o);
    }
}
