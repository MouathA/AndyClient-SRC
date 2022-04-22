package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.google.common.base.*;
import com.viaversion.viaversion.libs.opennbt.*;
import io.netty.buffer.*;
import java.io.*;

public class NBTType extends Type
{
    public NBTType() {
        super(CompoundTag.class);
    }
    
    @Override
    public CompoundTag read(final ByteBuf byteBuf) throws Exception {
        Preconditions.checkArgument(byteBuf.readableBytes() <= 2097152, "Cannot read NBT (got %s bytes)", byteBuf.readableBytes());
        final int readerIndex = byteBuf.readerIndex();
        if (byteBuf.readByte() == 0) {
            return null;
        }
        byteBuf.readerIndex(readerIndex);
        return NBTIO.readTag((DataInput)new ByteBufInputStream(byteBuf));
    }
    
    public void write(final ByteBuf byteBuf, final CompoundTag compoundTag) throws Exception {
        if (compoundTag == null) {
            byteBuf.writeByte(0);
        }
        else {
            NBTIO.writeTag((DataOutput)new ByteBufOutputStream(byteBuf), compoundTag);
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (CompoundTag)o);
    }
}
