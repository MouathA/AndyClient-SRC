package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class UUIDType extends Type
{
    public UUIDType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf byteBuf) {
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }
    
    public void write(final ByteBuf byteBuf, final UUID uuid) {
        byteBuf.writeLong(uuid.getMostSignificantBits());
        byteBuf.writeLong(uuid.getLeastSignificantBits());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (UUID)o);
    }
}
