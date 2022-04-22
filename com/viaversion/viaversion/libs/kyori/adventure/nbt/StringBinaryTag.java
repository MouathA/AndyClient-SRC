package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface StringBinaryTag extends BinaryTag
{
    @NotNull
    default StringBinaryTag of(@NotNull final String value) {
        return new StringBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.STRING;
    }
    
    @NotNull
    String value();
}
