package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class OldMetaType extends MetaTypeTemplate
{
    private static final int END = 127;
    
    @Override
    public Metadata read(final ByteBuf byteBuf) throws Exception {
        final byte byte1 = byteBuf.readByte();
        if (byte1 == 127) {
            return null;
        }
        final MetaType type = this.getType((byte1 & 0xE0) >> 5);
        return new Metadata(byte1 & 0x1F, type, type.type().read(byteBuf));
    }
    
    protected abstract MetaType getType(final int p0);
    
    public void write(final ByteBuf byteBuf, final Metadata metadata) throws Exception {
        if (metadata == null) {
            byteBuf.writeByte(127);
        }
        else {
            byteBuf.writeByte((metadata.metaType().typeId() << 5 | (metadata.id() & 0x1F)) & 0xFF);
            metadata.metaType().type().write(byteBuf, metadata.getValue());
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Metadata)o);
    }
}
