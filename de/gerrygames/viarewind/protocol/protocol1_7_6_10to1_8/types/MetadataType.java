package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public class MetadataType extends MetaTypeTemplate
{
    @Override
    public Metadata read(final ByteBuf byteBuf) throws Exception {
        final byte byte1 = byteBuf.readByte();
        if (byte1 == 127) {
            return null;
        }
        final MetaType1_7_6_10 byId = MetaType1_7_6_10.byId((byte1 & 0xE0) >> 5);
        return new Metadata(byte1 & 0x1F, byId, byId.type().read(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final Metadata metadata) throws Exception {
        byteBuf.writeByte((metadata.metaType().typeId() << 5 | (metadata.id() & 0x1F)) & 0xFF);
        metadata.metaType().type().write(byteBuf, metadata.getValue());
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
