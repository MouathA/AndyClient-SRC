package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.*;

public interface KeybindComponent extends BuildableComponent, ScopedComponent
{
    @NotNull
    String keybind();
    
    @Contract(pure = true)
    @NotNull
    KeybindComponent keybind(@NotNull final String keybind);
    
    @Contract(pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull final KeybindLike keybind) {
        return this.keybind(Objects.requireNonNull(keybind, "keybind").asKeybind());
    }
    
    public interface Builder extends ComponentBuilder
    {
        @Contract("_ -> this")
        @NotNull
        Builder keybind(@NotNull final String keybind);
        
        @Contract(pure = true)
        @NotNull
        default Builder keybind(@NotNull final KeybindLike keybind) {
            return this.keybind(Objects.requireNonNull(keybind, "keybind").asKeybind());
        }
    }
    
    public interface KeybindLike
    {
        @NotNull
        String asKeybind();
    }
}
