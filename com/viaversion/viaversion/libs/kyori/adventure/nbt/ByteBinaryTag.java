package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ByteBinaryTag extends NumberBinaryTag
{
    public static final ByteBinaryTag ZERO = new ByteBinaryTagImpl((byte)0);
    public static final ByteBinaryTag ONE = new ByteBinaryTagImpl((byte)1);
    
    @NotNull
    default ByteBinaryTag of(final byte value) {
        if (value == 0) {
            return ByteBinaryTag.ZERO;
        }
        if (value == 1) {
            return ByteBinaryTag.ONE;
        }
        return new ByteBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.BYTE;
    }
    
    byte value();
}
