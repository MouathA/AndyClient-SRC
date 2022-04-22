package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class StorageNBTComponentImpl extends NBTComponentImpl implements StorageNBTComponent
{
    private final Key storage;
    
    StorageNBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final Key storage) {
        super(children, style, nbtPath, interpret, separator);
        this.storage = storage;
    }
    
    @NotNull
    @Override
    public StorageNBTComponent nbtPath(@NotNull final String nbtPath) {
        if (Objects.equals(this.nbtPath, nbtPath)) {
            return this;
        }
        return new StorageNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.storage);
    }
    
    @NotNull
    @Override
    public StorageNBTComponent interpret(final boolean interpret) {
        if (this.interpret == interpret) {
            return this;
        }
        return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.storage);
    }
    
    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }
    
    @NotNull
    @Override
    public StorageNBTComponent separator(@Nullable final ComponentLike separator) {
        return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.storage);
    }
    
    @NotNull
    @Override
    public Key storage() {
        return this.storage;
    }
    
    @NotNull
    @Override
    public StorageNBTComponent storage(@NotNull final Key storage) {
        if (Objects.equals(this.storage, storage)) {
            return this;
        }
        return new StorageNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, storage);
    }
    
    @NotNull
    @Override
    public StorageNBTComponent children(@NotNull final List children) {
        return new StorageNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
    }
    
    @NotNull
    @Override
    public StorageNBTComponent style(@NotNull final Style style) {
        return new StorageNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.storage);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof StorageNBTComponent && super.equals(other) && Objects.equals(this.storage, ((StorageNBTComponentImpl)other).storage()));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.storage.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T)ExaminableProperty.of("storage", this.storage)), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    @Override
    public StorageNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @NotNull
    @Override
    public NBTComponent separator(@Nullable final ComponentLike separator) {
        return this.separator(separator);
    }
    
    @NotNull
    @Override
    public NBTComponent interpret(final boolean interpret) {
        return this.interpret(interpret);
    }
    
    @NotNull
    @Override
    public NBTComponent nbtPath(@NotNull final String nbtPath) {
        return this.nbtPath(nbtPath);
    }
    
    @Override
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }
    
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    @NotNull
    @Override
    public Component style(@NotNull final Style style) {
        return this.style(style);
    }
    
    @NotNull
    @Override
    public Component children(@NotNull final List children) {
        return this.children(children);
    }
    
    static class BuilderImpl extends NBTComponentImpl.BuilderImpl implements StorageNBTComponent.Builder
    {
        @Nullable
        private Key storage;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final StorageNBTComponent component) {
            super(component);
            this.storage = component.storage();
        }
        
        @Override
        public StorageNBTComponent.Builder storage(@NotNull final Key storage) {
            this.storage = storage;
            return this;
        }
        
        @NotNull
        @Override
        public StorageNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.storage == null) {
                throw new IllegalStateException("storage must be set");
            }
            return new StorageNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
        }
        
        @NotNull
        @Override
        public BuildableComponent build() {
            return this.build();
        }
        
        @NotNull
        @Override
        public Object build() {
            return this.build();
        }
    }
}
