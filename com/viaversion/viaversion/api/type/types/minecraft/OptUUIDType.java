package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class OptUUIDType extends Type
{
    public OptUUIDType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf byteBuf) {
        if (!byteBuf.readBoolean()) {
            return null;
        }
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }
    
    public void write(final ByteBuf byteBuf, final UUID uuid) {
        if (uuid == null) {
            byteBuf.writeBoolean(false);
        }
        else {
            byteBuf.writeBoolean(true);
            byteBuf.writeLong(uuid.getMostSignificantBits());
            byteBuf.writeLong(uuid.getLeastSignificantBits());
        }
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
