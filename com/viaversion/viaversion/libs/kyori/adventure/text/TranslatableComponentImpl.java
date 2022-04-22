package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

final class TranslatableComponentImpl extends AbstractComponent implements TranslatableComponent
{
    private final String key;
    private final List args;
    
    TranslatableComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String key, @NotNull final ComponentLike[] args) {
        this(children, style, key, Arrays.asList(args));
    }
    
    TranslatableComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String key, @NotNull final List args) {
        super(children, style);
        this.key = Objects.requireNonNull(key, "key");
        this.args = ComponentLike.asComponents(args);
    }
    
    @NotNull
    @Override
    public String key() {
        return this.key;
    }
    
    @NotNull
    @Override
    public TranslatableComponent key(@NotNull final String key) {
        if (Objects.equals(this.key, key)) {
            return this;
        }
        return new TranslatableComponentImpl(this.children, this.style, Objects.requireNonNull(key, "key"), this.args);
    }
    
    @NotNull
    @Override
    public List args() {
        return this.args;
    }
    
    @NotNull
    @Override
    public TranslatableComponent args(@NotNull final ComponentLike... args) {
        return new TranslatableComponentImpl(this.children, this.style, this.key, args);
    }
    
    @NotNull
    @Override
    public TranslatableComponent args(@NotNull final List args) {
        return new TranslatableComponentImpl(this.children, this.style, this.key, args);
    }
    
    @NotNull
    @Override
    public TranslatableComponent children(@NotNull final List children) {
        return new TranslatableComponentImpl(children, this.style, this.key, this.args);
    }
    
    @NotNull
    @Override
    public TranslatableComponent style(@NotNull final Style style) {
        return new TranslatableComponentImpl(this.children, style, this.key, this.args);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslatableComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final TranslatableComponent translatableComponent = (TranslatableComponent)other;
        return Objects.equals(this.key, translatableComponent.key()) && Objects.equals(this.args, translatableComponent.args());
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * super.hashCode() + this.key.hashCode()) + this.args.hashCode();
    }
    
    @NotNull
    @Override
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.concat((Stream<?>)Stream.of((T[])new ExaminableProperty[] { ExaminableProperty.of("key", this.key), ExaminableProperty.of("args", this.args) }), (Stream<?>)super.examinablePropertiesWithoutChildren());
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
    
    static final class BuilderImpl extends AbstractComponentBuilder implements TranslatableComponent.Builder
    {
        @Nullable
        private String key;
        private List args;
        
        BuilderImpl() {
            this.args = Collections.emptyList();
        }
        
        BuilderImpl(@NotNull final TranslatableComponent component) {
            super(component);
            this.args = Collections.emptyList();
            this.key = component.key();
            this.args = component.args();
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder key(@NotNull final String key) {
            this.key = key;
            return this;
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder args(@NotNull final ComponentBuilder arg) {
            return this.args(Collections.singletonList(arg.build()));
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder args(@NotNull final ComponentBuilder... args) {
            if (args.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Stream.of(args).map((Function<? super ComponentBuilder, ?>)ComponentBuilder::build).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder args(@NotNull final Component arg) {
            return this.args(Collections.singletonList(arg));
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder args(@NotNull final ComponentLike... args) {
            if (args.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Arrays.asList(args));
        }
        
        @NotNull
        @Override
        public TranslatableComponent.Builder args(@NotNull final List args) {
            this.args = ComponentLike.asComponents(args);
            return this;
        }
        
        @NotNull
        @Override
        public TranslatableComponentImpl build() {
            if (this.key == null) {
                throw new IllegalStateException("key must be set");
            }
            return new TranslatableComponentImpl(this.children, this.buildStyle(), this.key, this.args);
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
