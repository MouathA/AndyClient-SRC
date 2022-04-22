package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;

public interface SelectorComponent extends BuildableComponent, ScopedComponent
{
    @NotNull
    String pattern();
    
    @Contract(pure = true)
    @NotNull
    SelectorComponent pattern(@NotNull final String pattern);
    
    @Nullable
    Component separator();
    
    @NotNull
    SelectorComponent separator(@Nullable final ComponentLike separator);
    
    public interface Builder extends ComponentBuilder
    {
        @Contract("_ -> this")
        @NotNull
        Builder pattern(@NotNull final String pattern);
        
        @Contract("_ -> this")
        @NotNull
        Builder separator(@Nullable final ComponentLike separator);
    }
}
