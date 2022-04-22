package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface LongBinaryTag extends NumberBinaryTag
{
    @NotNull
    default LongBinaryTag of(final long value) {
        return new LongBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.LONG;
    }
    
    long value();
}
