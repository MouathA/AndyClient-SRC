package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class OptPosition1_14Type extends Type
{
    public OptPosition1_14Type() {
        super(Position.class);
    }
    
    @Override
    public Position read(final ByteBuf byteBuf) throws Exception {
        if (!byteBuf.readBoolean()) {
            return null;
        }
        return (Position)Type.POSITION1_14.read(byteBuf);
    }
    
    public void write(final ByteBuf byteBuf, final Position position) throws Exception {
        byteBuf.writeBoolean(position != null);
        if (position != null) {
            Type.POSITION1_14.write(byteBuf, position);
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Position)o);
    }
}
