package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class KeybindComponentImpl extends AbstractComponent implements KeybindComponent
{
    private final String keybind;
    
    KeybindComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String keybind) {
        super(children, style);
        this.keybind = Objects.requireNonNull(keybind, "keybind");
    }
    
    @NotNull
    @Override
    public String keybind() {
        return this.keybind;
    }
    
    @NotNull
    @Override
    public KeybindComponent keybind(@NotNull final String keybind) {
        if (Objects.equals(this.keybind, keybind)) {
            return this;
        }
        return new KeybindComponentImpl(this.children, this.style, Objects.requireNonNull(keybind, "keybind"));
    }
    
    @NotNull
    @Override
    public KeybindComponent children(@NotNull final List children) {
        return new KeybindComponentImpl(children, this.style, this.keybind);
    }
    
    @NotNull
    @Override
    public KeybindComponent style(@NotNull final Style style) {
        return new KeybindComponentImpl(this.children, style, this.keybind);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof KeybindComponent && super.equals(other) && Objects.equals(this.keybind, ((KeybindComponent)other).keybind()));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.keybind.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T)ExaminableProperty.of("keybind", this.keybind)), (Stream<?>)super.examinablePropertiesWithoutChildren());
    }
    
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
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
    
    @NotNull
    @Override
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }
    
    @NotNull
    @Override
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static final class BuilderImpl extends AbstractComponentBuilder implements KeybindComponent.Builder
    {
        @Nullable
        private String keybind;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final KeybindComponent component) {
            super(component);
            this.keybind = component.keybind();
        }
        
        @NotNull
        @Override
        public KeybindComponent.Builder keybind(@NotNull final String keybind) {
            this.keybind = keybind;
            return this;
        }
        
        @NotNull
        @Override
        public KeybindComponent build() {
            if (this.keybind == null) {
                throw new IllegalStateException("keybind must be set");
            }
            return new KeybindComponentImpl(this.children, this.buildStyle(), this.keybind);
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
