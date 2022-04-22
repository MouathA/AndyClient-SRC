package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class PositionType extends Type
{
    public PositionType() {
        super(Position.class);
    }
    
    @Override
    public Position read(final ByteBuf byteBuf) {
        final long long1 = byteBuf.readLong();
        return new Position((int)(long1 >> 38), (short)(long1 >> 26 & 0xFFFL), (int)(long1 << 38 >> 38));
    }
    
    public void write(final ByteBuf byteBuf, final Position position) {
        byteBuf.writeLong(((long)position.x() & 0x3FFFFFFL) << 38 | ((long)position.y() & 0xFFFL) << 26 | (long)(position.z() & 0x3FFFFFF));
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
