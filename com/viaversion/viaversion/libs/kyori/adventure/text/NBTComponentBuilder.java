package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

public interface NBTComponentBuilder extends ComponentBuilder
{
    @Contract("_ -> this")
    @NotNull
    NBTComponentBuilder nbtPath(@NotNull final String nbtPath);
    
    @Contract("_ -> this")
    @NotNull
    NBTComponentBuilder interpret(final boolean interpret);
    
    @Contract("_ -> this")
    @NotNull
    NBTComponentBuilder separator(@Nullable final ComponentLike separator);
}
