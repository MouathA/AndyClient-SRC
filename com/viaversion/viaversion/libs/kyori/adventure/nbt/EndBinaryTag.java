package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface EndBinaryTag extends BinaryTag
{
    @NotNull
    default EndBinaryTag get() {
        return EndBinaryTagImpl.INSTANCE;
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.END;
    }
}
