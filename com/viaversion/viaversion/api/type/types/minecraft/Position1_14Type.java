package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;

public class Position1_14Type extends Type
{
    public Position1_14Type() {
        super(Position.class);
    }
    
    @Override
    public Position read(final ByteBuf byteBuf) {
        final long long1 = byteBuf.readLong();
        return new Position((int)(long1 >> 38), (int)(long1 << 52 >> 52), (int)(long1 << 26 >> 38));
    }
    
    public void write(final ByteBuf byteBuf, final Position position) {
        byteBuf.writeLong(((long)position.x() & 0x3FFFFFFL) << 38 | (long)(position.y() & 0xFFF) | ((long)position.z() & 0x3FFFFFFL) << 12);
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
