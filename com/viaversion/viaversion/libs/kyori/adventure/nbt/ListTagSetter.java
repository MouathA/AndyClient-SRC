package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface ListTagSetter
{
    @NotNull
    Object add(final BinaryTag tag);
    
    @NotNull
    Object add(final Iterable tags);
}
