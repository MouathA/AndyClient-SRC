package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface Pointers extends Buildable
{
    @Contract(pure = true)
    @NotNull
    default Pointers empty() {
        return PointersImpl.EMPTY;
    }
    
    @Contract(pure = true)
    @NotNull
    default Builder builder() {
        return new PointersImpl.BuilderImpl();
    }
    
    @NotNull
    Optional get(@NotNull final Pointer pointer);
    
    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default Object getOrDefault(@NotNull final Pointer pointer, @Nullable final Object defaultValue) {
        return this.get(pointer).orElse(defaultValue);
    }
    
    default Object getOrDefaultFrom(@NotNull final Pointer pointer, @NotNull final Supplier defaultValue) {
        return this.get(pointer).orElseGet(defaultValue);
    }
    
    boolean supports(@NotNull final Pointer pointer);
    
    public interface Builder extends Buildable.Builder
    {
        @Contract("_, _ -> this")
        @NotNull
        default Builder withStatic(@NotNull final Pointer pointer, @Nullable final Object value) {
            return this.withDynamic(pointer, Builder::lambda$withStatic$0);
        }
        
        @Contract("_, _ -> this")
        @NotNull
        Builder withDynamic(@NotNull final Pointer pointer, @NotNull final Supplier value);
        
        default Object lambda$withStatic$0(final Object o) {
            return o;
        }
    }
}
