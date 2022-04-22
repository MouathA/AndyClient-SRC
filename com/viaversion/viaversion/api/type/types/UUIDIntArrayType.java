package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import java.util.*;
import io.netty.buffer.*;

public class UUIDIntArrayType extends Type
{
    public UUIDIntArrayType() {
        super(UUID.class);
    }
    
    @Override
    public UUID read(final ByteBuf byteBuf) {
        return uuidFromIntArray(new int[] { byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt() });
    }
    
    public void write(final ByteBuf byteBuf, final UUID uuid) {
        final int[] uuidToIntArray = uuidToIntArray(uuid);
        byteBuf.writeInt(uuidToIntArray[0]);
        byteBuf.writeInt(uuidToIntArray[1]);
        byteBuf.writeInt(uuidToIntArray[2]);
        byteBuf.writeInt(uuidToIntArray[3]);
    }
    
    public static UUID uuidFromIntArray(final int[] array) {
        return new UUID((long)array[0] << 32 | ((long)array[1] & 0xFFFFFFFFL), (long)array[2] << 32 | ((long)array[3] & 0xFFFFFFFFL));
    }
    
    public static int[] uuidToIntArray(final UUID uuid) {
        return bitsToIntArray(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }
    
    public static int[] bitsToIntArray(final long n, final long n2) {
        return new int[] { (int)(n >> 32), (int)n, (int)(n2 >> 32), (int)n2 };
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
