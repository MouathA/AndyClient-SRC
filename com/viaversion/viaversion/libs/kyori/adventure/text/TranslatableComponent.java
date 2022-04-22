package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.translation.*;
import org.jetbrains.annotations.*;
import java.util.*;

public interface TranslatableComponent extends BuildableComponent, ScopedComponent
{
    @NotNull
    String key();
    
    @Contract(pure = true)
    @NotNull
    default TranslatableComponent key(@NotNull final Translatable translatable) {
        return this.key(Objects.requireNonNull(translatable, "translatable").translationKey());
    }
    
    @Contract(pure = true)
    @NotNull
    TranslatableComponent key(@NotNull final String key);
    
    @NotNull
    List args();
    
    @Contract(pure = true)
    @NotNull
    TranslatableComponent args(@NotNull final ComponentLike... args);
    
    @Contract(pure = true)
    @NotNull
    TranslatableComponent args(@NotNull final List args);
    
    public interface Builder extends ComponentBuilder
    {
        @Contract(pure = true)
        @NotNull
        default Builder key(@NotNull final Translatable translatable) {
            return this.key(Objects.requireNonNull(translatable, "translatable").translationKey());
        }
        
        @Contract("_ -> this")
        @NotNull
        Builder key(@NotNull final String key);
        
        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentBuilder arg);
        
        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentBuilder... args);
        
        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final Component arg);
        
        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final ComponentLike... args);
        
        @Contract("_ -> this")
        @NotNull
        Builder args(@NotNull final List args);
    }
}
