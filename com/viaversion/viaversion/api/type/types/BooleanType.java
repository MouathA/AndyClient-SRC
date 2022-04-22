package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;

public class BooleanType extends Type implements TypeConverter
{
    public BooleanType() {
        super(Boolean.class);
    }
    
    @Override
    public Boolean read(final ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }
    
    public void write(final ByteBuf byteBuf, final Boolean b) {
        byteBuf.writeBoolean(b);
    }
    
    @Override
    public Boolean from(final Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue() == 1;
        }
        return (Boolean)o;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Boolean)o);
    }
    
    @Override
    public Object from(final Object o) {
        return this.from(o);
    }
}
