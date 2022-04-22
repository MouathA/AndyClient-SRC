package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import org.jetbrains.annotations.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class EntityNBTComponentImpl extends NBTComponentImpl implements EntityNBTComponent
{
    private final String selector;
    
    EntityNBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, final String selector) {
        super(children, style, nbtPath, interpret, separator);
        this.selector = selector;
    }
    
    @NotNull
    @Override
    public EntityNBTComponent nbtPath(@NotNull final String nbtPath) {
        if (Objects.equals(this.nbtPath, nbtPath)) {
            return this;
        }
        return new EntityNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.selector);
    }
    
    @NotNull
    @Override
    public EntityNBTComponent interpret(final boolean interpret) {
        if (this.interpret == interpret) {
            return this;
        }
        return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.selector);
    }
    
    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }
    
    @NotNull
    @Override
    public EntityNBTComponent separator(@Nullable final ComponentLike separator) {
        return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.selector);
    }
    
    @NotNull
    @Override
    public String selector() {
        return this.selector;
    }
    
    @NotNull
    @Override
    public EntityNBTComponent selector(@NotNull final String selector) {
        if (Objects.equals(this.selector, selector)) {
            return this;
        }
        return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, selector);
    }
    
    @NotNull
    @Override
    public EntityNBTComponent children(@NotNull final List children) {
        return new EntityNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.selector);
    }
    
    @NotNull
    @Override
    public EntityNBTComponent style(@NotNull final Style style) {
        return new EntityNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.selector);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof EntityNBTComponent && super.equals(other) && Objects.equals(this.selector, ((EntityNBTComponentImpl)other).selector()));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.selector.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T)ExaminableProperty.of("selector", this.selector)), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    @Override
    public EntityNBTComponent.Builder toBuilder() {
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
    
    static final class BuilderImpl extends NBTComponentImpl.BuilderImpl implements EntityNBTComponent.Builder
    {
        @Nullable
        private String selector;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final EntityNBTComponent component) {
            super(component);
            this.selector = component.selector();
        }
        
        @Override
        public EntityNBTComponent.Builder selector(@NotNull final String selector) {
            this.selector = selector;
            return this;
        }
        
        @NotNull
        @Override
        public EntityNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.selector == null) {
                throw new IllegalStateException("selector must be set");
            }
            return new EntityNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
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
