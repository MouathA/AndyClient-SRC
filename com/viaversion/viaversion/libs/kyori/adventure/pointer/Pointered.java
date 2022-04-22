package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface Pointered
{
    @NotNull
    default Optional get(@NotNull final Pointer pointer) {
        return this.pointers().get(pointer);
    }
    
    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default Object getOrDefault(@NotNull final Pointer pointer, @Nullable final Object defaultValue) {
        return this.pointers().getOrDefault(pointer, defaultValue);
    }
    
    default Object getOrDefaultFrom(@NotNull final Pointer pointer, @NotNull final Supplier defaultValue) {
        return this.pointers().getOrDefaultFrom(pointer, defaultValue);
    }
    
    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }
}
