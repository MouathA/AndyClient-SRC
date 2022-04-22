package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

public interface BinaryTag extends BinaryTagLike, Examinable
{
    @NotNull
    BinaryTagType type();
    
    @NotNull
    default BinaryTag asBinaryTag() {
        return this;
    }
}
