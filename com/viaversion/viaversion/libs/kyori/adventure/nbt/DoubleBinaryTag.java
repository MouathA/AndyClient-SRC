package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface DoubleBinaryTag extends NumberBinaryTag
{
    @NotNull
    default DoubleBinaryTag of(final double value) {
        return new DoubleBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.DOUBLE;
    }
    
    double value();
}
