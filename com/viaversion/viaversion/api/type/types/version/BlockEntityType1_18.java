package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;

public class BlockEntityType1_18 extends Type
{
    public BlockEntityType1_18() {
        super(BlockEntity.class);
    }
    
    @Override
    public BlockEntity read(final ByteBuf byteBuf) throws Exception {
        return new BlockEntityImpl(byteBuf.readByte(), byteBuf.readShort(), Type.VAR_INT.readPrimitive(byteBuf), (CompoundTag)Type.NBT.read(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final BlockEntity blockEntity) throws Exception {
        byteBuf.writeByte(blockEntity.packedXZ());
        byteBuf.writeShort(blockEntity.y());
        Type.VAR_INT.writePrimitive(byteBuf, blockEntity.typeId());
        Type.NBT.write(byteBuf, blockEntity.tag());
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (BlockEntity)o);
    }
}
