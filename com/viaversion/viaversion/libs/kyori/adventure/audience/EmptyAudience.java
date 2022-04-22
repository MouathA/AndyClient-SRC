package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.identity.*;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.*;

final class EmptyAudience implements Audience
{
    static final EmptyAudience INSTANCE;
    
    @NotNull
    @Override
    public Optional get(@NotNull final Pointer pointer) {
        return Optional.empty();
    }
    
    @Contract("_, null -> null; _, !null -> !null")
    @Nullable
    @Override
    public Object getOrDefault(@NotNull final Pointer pointer, @Nullable final Object defaultValue) {
        return defaultValue;
    }
    
    @Override
    public Object getOrDefaultFrom(@NotNull final Pointer pointer, @NotNull final Supplier defaultValue) {
        return defaultValue.get();
    }
    
    @NotNull
    @Override
    public Audience filterAudience(@NotNull final Predicate filter) {
        return this;
    }
    
    @Override
    public void forEachAudience(@NotNull final Consumer action) {
    }
    
    @Override
    public void sendMessage(@NotNull final ComponentLike message) {
    }
    
    @Override
    public void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message) {
    }
    
    @Override
    public void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message) {
    }
    
    @Override
    public void sendMessage(@NotNull final ComponentLike message, @NotNull final MessageType type) {
    }
    
    @Override
    public void sendMessage(@NotNull final Identified source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
    }
    
    @Override
    public void sendMessage(@NotNull final Identity source, @NotNull final ComponentLike message, @NotNull final MessageType type) {
    }
    
    @Override
    public void sendActionBar(@NotNull final ComponentLike message) {
    }
    
    @Override
    public void sendPlayerListHeader(@NotNull final ComponentLike header) {
    }
    
    @Override
    public void sendPlayerListFooter(@NotNull final ComponentLike footer) {
    }
    
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull final ComponentLike header, @NotNull final ComponentLike footer) {
    }
    
    @Override
    public void openBook(final Book.Builder book) {
    }
    
    @Override
    public boolean equals(final Object that) {
        return this == that;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "EmptyAudience";
    }
    
    static {
        INSTANCE = new EmptyAudience();
    }
}
