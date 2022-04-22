package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.*;
import io.netty.buffer.*;
import java.util.zip.*;
import java.io.*;

public class CompressedNBTType extends Type
{
    public CompressedNBTType() {
        super(CompoundTag.class);
    }
    
    @Override
    public CompoundTag read(final ByteBuf byteBuf) throws IOException {
        final short short1 = byteBuf.readShort();
        if (short1 <= 0) {
            return null;
        }
        final GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufInputStream(byteBuf.readSlice(short1)));
        final CompoundTag tag = NBTIO.readTag(gzipInputStream);
        gzipInputStream.close();
        return tag;
    }
    
    public void write(final ByteBuf byteBuf, final CompoundTag compoundTag) throws Exception {
        if (compoundTag == null) {
            byteBuf.writeShort(-1);
            return;
        }
        final ByteBuf buffer = byteBuf.alloc().buffer();
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new ByteBufOutputStream(buffer));
        NBTIO.writeTag(gzipOutputStream, compoundTag);
        gzipOutputStream.close();
        byteBuf.writeShort(buffer.readableBytes());
        byteBuf.writeBytes(buffer);
        buffer.release();
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
