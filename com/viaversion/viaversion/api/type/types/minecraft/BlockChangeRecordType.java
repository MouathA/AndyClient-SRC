package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.*;

public class BlockChangeRecordType extends Type
{
    public BlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }
    
    @Override
    public BlockChangeRecord read(final ByteBuf byteBuf) throws Exception {
        final short primitive = Type.SHORT.readPrimitive(byteBuf);
        return new BlockChangeRecord1_8(primitive >> 12 & 0xF, primitive & 0xFF, primitive >> 8 & 0xF, Type.VAR_INT.readPrimitive(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final BlockChangeRecord blockChangeRecord) throws Exception {
        Type.SHORT.writePrimitive(byteBuf, (short)(blockChangeRecord.getSectionX() << 12 | blockChangeRecord.getSectionZ() << 8 | blockChangeRecord.getY()));
        Type.VAR_INT.writePrimitive(byteBuf, blockChangeRecord.getBlockId());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (BlockChangeRecord)o);
    }
}
