package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.*;

public class VarLongBlockChangeRecordType extends Type
{
    public VarLongBlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }
    
    @Override
    public BlockChangeRecord read(final ByteBuf byteBuf) throws Exception {
        final long primitive = Type.VAR_LONG.readPrimitive(byteBuf);
        final short n = (short)(primitive & 0xFFFL);
        return new BlockChangeRecord1_16_2(n >>> 8 & 0xF, n & 0xF, n >>> 4 & 0xF, (int)(primitive >>> 12));
    }
    
    public void write(final ByteBuf byteBuf, final BlockChangeRecord blockChangeRecord) throws Exception {
        Type.VAR_LONG.writePrimitive(byteBuf, (long)blockChangeRecord.getBlockId() << 12 | (long)(short)(blockChangeRecord.getSectionX() << 8 | blockChangeRecord.getSectionZ() << 4 | blockChangeRecord.getSectionY()));
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
