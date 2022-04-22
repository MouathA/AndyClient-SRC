package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public abstract class ModernMetaType extends MetaTypeTemplate
{
    private static final int END = 255;
    
    @Override
    public Metadata read(final ByteBuf byteBuf) throws Exception {
        final short unsignedByte = byteBuf.readUnsignedByte();
        if (unsignedByte == 255) {
            return null;
        }
        final MetaType type = this.getType(Type.VAR_INT.readPrimitive(byteBuf));
        return new Metadata(unsignedByte, type, type.type().read(byteBuf));
    }
    
    protected abstract MetaType getType(final int p0);
    
    public void write(final ByteBuf byteBuf, final Metadata metadata) throws Exception {
        if (metadata == null) {
            byteBuf.writeByte(255);
        }
        else {
            byteBuf.writeByte(metadata.id());
            final MetaType metaType = metadata.metaType();
            Type.VAR_INT.writePrimitive(byteBuf, metaType.typeId());
            metaType.type().write(byteBuf, metadata.getValue());
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
