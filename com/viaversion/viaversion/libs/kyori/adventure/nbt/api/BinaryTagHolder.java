package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

public interface BinaryTagHolder
{
    @NotNull
    default BinaryTagHolder encode(@NotNull final Object nbt, @NotNull final Codec codec) throws Exception {
        return new BinaryTagHolderImpl((String)codec.encode(nbt));
    }
    
    @NotNull
    default BinaryTagHolder of(@NotNull final String string) {
        return new BinaryTagHolderImpl(string);
    }
    
    @NotNull
    String string();
    
    @NotNull
    Object get(@NotNull final Codec codec) throws Exception;
}
