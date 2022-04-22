package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ByteArrayBinaryTag extends ArrayBinaryTag, Iterable
{
    @NotNull
    default ByteArrayBinaryTag of(final byte... value) {
        return new ByteArrayBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }
    
    byte[] value();
    
    int size();
    
    byte get(final int index);
}
