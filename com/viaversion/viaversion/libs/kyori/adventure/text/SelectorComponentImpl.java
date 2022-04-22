package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class SelectorComponentImpl extends AbstractComponent implements SelectorComponent
{
    private final String pattern;
    @Nullable
    private final Component separator;
    
    SelectorComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String pattern, @Nullable final ComponentLike separator) {
        super(children, style);
        this.pattern = pattern;
        this.separator = ComponentLike.unbox(separator);
    }
    
    @NotNull
    @Override
    public String pattern() {
        return this.pattern;
    }
    
    @NotNull
    @Override
    public SelectorComponent pattern(@NotNull final String pattern) {
        if (Objects.equals(this.pattern, pattern)) {
            return this;
        }
        return new SelectorComponentImpl(this.children, this.style, Objects.requireNonNull(pattern, "pattern"), this.separator);
    }
    
    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }
    
    @NotNull
    @Override
    public SelectorComponent separator(@Nullable final ComponentLike separator) {
        return new SelectorComponentImpl(this.children, this.style, this.pattern, separator);
    }
    
    @NotNull
    @Override
    public SelectorComponent children(@NotNull final List children) {
        return new SelectorComponentImpl(children, this.style, this.pattern, this.separator);
    }
    
    @NotNull
    @Override
    public SelectorComponent style(@NotNull final Style style) {
        return new SelectorComponentImpl(this.children, style, this.pattern, this.separator);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SelectorComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final SelectorComponent selectorComponent = (SelectorComponent)other;
        return Objects.equals(this.pattern, selectorComponent.pattern()) && Objects.equals(this.separator, selectorComponent.separator());
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * super.hashCode() + this.pattern.hashCode()) + Objects.hashCode(this.separator);
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T[])new ExaminableProperty[] { ExaminableProperty.of("pattern", this.pattern), ExaminableProperty.of("separator", this.separator) }), (Stream<?>)super.examinablePropertiesWithoutChildren());
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
    
    static final class BuilderImpl extends AbstractComponentBuilder implements SelectorComponent.Builder
    {
        @Nullable
        private String pattern;
        @Nullable
        private Component separator;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final SelectorComponent component) {
            super(component);
            this.pattern = component.pattern();
        }
        
        @NotNull
        @Override
        public SelectorComponent.Builder pattern(@NotNull final String pattern) {
            this.pattern = pattern;
            return this;
        }
        
        @NotNull
        @Override
        public SelectorComponent.Builder separator(@Nullable final ComponentLike separator) {
            this.separator = ComponentLike.unbox(separator);
            return this;
        }
        
        @NotNull
        @Override
        public SelectorComponent build() {
            if (this.pattern == null) {
                throw new IllegalStateException("pattern must be set");
            }
            return new SelectorComponentImpl(this.children, this.buildStyle(), this.pattern, this.separator);
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
