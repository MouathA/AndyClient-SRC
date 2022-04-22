package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

final class ScoreComponentImpl extends AbstractComponent implements ScoreComponent
{
    private final String name;
    private final String objective;
    @Deprecated
    @Nullable
    private final String value;
    
    ScoreComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String name, @NotNull final String objective, @Nullable final String value) {
        super(children, style);
        this.name = name;
        this.objective = objective;
        this.value = value;
    }
    
    @NotNull
    @Override
    public String name() {
        return this.name;
    }
    
    @NotNull
    @Override
    public ScoreComponent name(@NotNull final String name) {
        if (Objects.equals(this.name, name)) {
            return this;
        }
        return new ScoreComponentImpl(this.children, this.style, Objects.requireNonNull(name, "name"), this.objective, this.value);
    }
    
    @NotNull
    @Override
    public String objective() {
        return this.objective;
    }
    
    @NotNull
    @Override
    public ScoreComponent objective(@NotNull final String objective) {
        if (Objects.equals(this.objective, objective)) {
            return this;
        }
        return new ScoreComponentImpl(this.children, this.style, this.name, Objects.requireNonNull(objective, "objective"), this.value);
    }
    
    @Deprecated
    @Nullable
    @Override
    public String value() {
        return this.value;
    }
    
    @Deprecated
    @NotNull
    @Override
    public ScoreComponent value(@Nullable final String value) {
        if (Objects.equals(this.value, value)) {
            return this;
        }
        return new ScoreComponentImpl(this.children, this.style, this.name, this.objective, value);
    }
    
    @NotNull
    @Override
    public ScoreComponent children(@NotNull final List children) {
        return new ScoreComponentImpl(children, this.style, this.name, this.objective, this.value);
    }
    
    @NotNull
    @Override
    public ScoreComponent style(@NotNull final Style style) {
        return new ScoreComponentImpl(this.children, style, this.name, this.objective, this.value);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final ScoreComponent scoreComponent = (ScoreComponent)other;
        return Objects.equals(this.name, scoreComponent.name()) && Objects.equals(this.objective, scoreComponent.objective()) && Objects.equals(this.value, scoreComponent.value());
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * super.hashCode() + this.name.hashCode()) + this.objective.hashCode()) + Objects.hashCode(this.value);
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T[])new ExaminableProperty[] { ExaminableProperty.of("name", this.name), ExaminableProperty.of("objective", this.objective), ExaminableProperty.of("value", this.value) }), (Stream<?>)super.examinablePropertiesWithoutChildren());
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
    
    static final class BuilderImpl extends AbstractComponentBuilder implements ScoreComponent.Builder
    {
        @Nullable
        private String name;
        @Nullable
        private String objective;
        @Nullable
        private String value;
        
        BuilderImpl() {
        }
        
        BuilderImpl(@NotNull final ScoreComponent component) {
            super(component);
            this.name = component.name();
            this.objective = component.objective();
            this.value = component.value();
        }
        
        @NotNull
        @Override
        public ScoreComponent.Builder name(@NotNull final String name) {
            this.name = name;
            return this;
        }
        
        @NotNull
        @Override
        public ScoreComponent.Builder objective(@NotNull final String objective) {
            this.objective = objective;
            return this;
        }
        
        @Deprecated
        @NotNull
        @Override
        public ScoreComponent.Builder value(@Nullable final String value) {
            this.value = value;
            return this;
        }
        
        @NotNull
        @Override
        public ScoreComponent build() {
            if (this.name == null) {
                throw new IllegalStateException("name must be set");
            }
            if (this.objective == null) {
                throw new IllegalStateException("objective must be set");
            }
            return new ScoreComponentImpl(this.children, this.buildStyle(), this.name, this.objective, this.value);
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
