package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.util.*;
import java.util.function.*;

final class CompoundTagBuilder implements CompoundBinaryTag.Builder
{
    @Nullable
    private Map tags;
    
    private Map tags() {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        return this.tags;
    }
    
    @Override
    public CompoundBinaryTag.Builder put(@NotNull final String key, @NotNull final BinaryTag tag) {
        this.tags().put(key, tag);
        return this;
    }
    
    @Override
    public CompoundBinaryTag.Builder put(@NotNull final CompoundBinaryTag tag) {
        final Map tags = this.tags();
        for (final String key : tag.keySet()) {
            tags.put(key, tag.get(key));
        }
        return this;
    }
    
    @Override
    public CompoundBinaryTag.Builder put(@NotNull final Map tags) {
        this.tags().putAll(tags);
        return this;
    }
    
    @Override
    public CompoundBinaryTag.Builder remove(@NotNull final String key, @Nullable final Consumer removed) {
        if (this.tags != null) {
            final BinaryTag binaryTag = this.tags.remove(key);
            if (removed != null) {
                removed.accept(binaryTag);
            }
        }
        return this;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag build() {
        if (this.tags == null) {
            return CompoundBinaryTag.empty();
        }
        return new CompoundBinaryTagImpl(new HashMap(this.tags));
    }
    
    @Override
    public Object remove(@NotNull final String key, @Nullable final Consumer removed) {
        return this.remove(key, removed);
    }
    
    @Override
    public Object put(@NotNull final Map tags) {
        return this.put(tags);
    }
    
    @Override
    public Object put(@NotNull final CompoundBinaryTag tag) {
        return this.put(tag);
    }
    
    @Override
    public Object put(@NotNull final String key, @NotNull final BinaryTag tag) {
        return this.put(key, tag);
    }
}
