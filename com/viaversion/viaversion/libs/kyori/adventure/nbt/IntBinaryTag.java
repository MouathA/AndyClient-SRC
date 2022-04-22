package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface IntBinaryTag extends NumberBinaryTag
{
    @NotNull
    default IntBinaryTag of(final int value) {
        return new IntBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.INT;
    }
    
    int value();
}
