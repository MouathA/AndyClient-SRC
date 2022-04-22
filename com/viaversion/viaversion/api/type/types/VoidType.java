package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class VoidType extends Type implements TypeConverter
{
    public VoidType() {
        super(Void.class);
    }
    
    @Override
    public Void read(final ByteBuf byteBuf) {
        return null;
    }
    
    public void write(final ByteBuf byteBuf, final Void void1) {
    }
    
    @Override
    public Void from(final Object o) {
        return null;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Void)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
