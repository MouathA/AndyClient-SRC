package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.examination.string.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

@Debug.Renderer(text = "this.debuggerString()", childrenArray = "this.children().toArray()", hasChildren = "!this.children().isEmpty()")
public abstract class AbstractComponent implements Component
{
    private static final Predicate NOT_EMPTY;
    protected final List children;
    protected final Style style;
    
    protected AbstractComponent(@NotNull final List children, @NotNull final Style style) {
        this.children = ComponentLike.asComponents(children, AbstractComponent.NOT_EMPTY);
        this.style = style;
    }
    
    @NotNull
    @Override
    public final List children() {
        return this.children;
    }
    
    @NotNull
    @Override
    public final Style style() {
        return this.style;
    }
    
    @NotNull
    @Override
    public Component replaceText(@NotNull final Consumer configurer) {
        Objects.requireNonNull(configurer, "configurer");
        return this.replaceText((TextReplacementConfig)Buildable.configureAndBuild(TextReplacementConfig.builder(), configurer));
    }
    
    @NotNull
    @Override
    public Component replaceText(@NotNull final TextReplacementConfig config) {
        Objects.requireNonNull(config, "replacement");
        if (!(config instanceof TextReplacementConfigImpl)) {
            throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
        }
        return TextReplacementRenderer.INSTANCE.render(this, ((TextReplacementConfigImpl)config).createState());
    }
    
    @NotNull
    @Override
    public Component compact() {
        return ComponentCompaction.compact(this, null);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractComponent)) {
            return false;
        }
        final AbstractComponent abstractComponent = (AbstractComponent)other;
        return Objects.equals(this.children, abstractComponent.children) && Objects.equals(this.style, abstractComponent.style);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.children.hashCode() + this.style.hashCode();
    }
    
    private String debuggerString() {
        return (String)StringExaminer.simpleEscaping().examine(this.examinableName(), this.examinablePropertiesWithoutChildren());
    }
    
    protected Stream examinablePropertiesWithoutChildren() {
        return Stream.of(ExaminableProperty.of("style", this.style));
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.concat((Stream<?>)this.examinablePropertiesWithoutChildren(), (Stream<?>)Stream.of((T)ExaminableProperty.of("children", this.children)));
    }
    
    @Override
    public String toString() {
        return (String)this.examine(StringExaminer.simpleEscaping());
    }
    
    private static boolean lambda$static$0(final Component component) {
        return component != Component.empty();
    }
    
    static {
        NOT_EMPTY = AbstractComponent::lambda$static$0;
    }
}
