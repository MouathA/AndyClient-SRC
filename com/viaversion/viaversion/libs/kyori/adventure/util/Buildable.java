package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.*;
import org.jetbrains.annotations.*;

public interface Buildable
{
    @Contract(mutates = "param1")
    @NotNull
    default Buildable configureAndBuild(@NotNull final Builder builder, @Nullable final Consumer consumer) {
        if (consumer != null) {
            consumer.accept(builder);
        }
        return (Buildable)builder.build();
    }
    
    @Contract(value = "-> new", pure = true)
    @NotNull
    Builder toBuilder();
    
    public interface Builder
    {
        @Contract(value = "-> new", pure = true)
        @NotNull
        Object build();
    }
}
