package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.*;
import io.netty.buffer.*;
import java.io.*;

public class NBTType extends Type
{
    public NBTType() {
        super(CompoundTag.class);
    }
    
    @Override
    public CompoundTag read(final ByteBuf byteBuf) {
        if (byteBuf.readShort() < 0) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new ByteBufInputStream(byteBuf));
        final CompoundTag tag = NBTIO.readTag((DataInput)dataInputStream);
        dataInputStream.close();
        return tag;
    }
    
    public void write(final ByteBuf byteBuf, final CompoundTag compoundTag) throws Exception {
        if (compoundTag == null) {
            byteBuf.writeShort(-1);
        }
        else {
            final ByteBuf buffer = byteBuf.alloc().buffer();
            final DataOutputStream dataOutputStream = new DataOutputStream(new ByteBufOutputStream(buffer));
            NBTIO.writeTag((DataOutput)dataOutputStream, compoundTag);
            dataOutputStream.close();
            byteBuf.writeShort(buffer.readableBytes());
            byteBuf.writeBytes(buffer);
            buffer.release();
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
