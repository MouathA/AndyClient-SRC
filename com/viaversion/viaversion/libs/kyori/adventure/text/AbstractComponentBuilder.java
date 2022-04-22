package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;

abstract class AbstractComponentBuilder implements ComponentBuilder
{
    protected List children;
    @Nullable
    private Style style;
    private Style.Builder styleBuilder;
    
    protected AbstractComponentBuilder() {
        this.children = Collections.emptyList();
    }
    
    protected AbstractComponentBuilder(@NotNull final BuildableComponent component) {
        this.children = Collections.emptyList();
        final List children = component.children();
        if (!children.isEmpty()) {
            this.children = new ArrayList(children);
        }
        if (component.hasStyling()) {
            this.style = component.style();
        }
    }
    
    @NotNull
    @Override
    public ComponentBuilder append(@NotNull final Component component) {
        if (component == Component.empty()) {
            return this;
        }
        this.prepareChildren();
        this.children.add(Objects.requireNonNull(component, "component"));
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder append(@NotNull final Component... components) {
        Objects.requireNonNull(components, "components");
        while (0 < components.length) {
            final Component component = components[0];
            if (component != Component.empty()) {
                if (!true) {
                    this.prepareChildren();
                }
                this.children.add(Objects.requireNonNull(component, "components[?]"));
            }
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder append(@NotNull final ComponentLike... components) {
        Objects.requireNonNull(components, "components");
        while (0 < components.length) {
            final Component component = components[0].asComponent();
            if (component != Component.empty()) {
                if (!true) {
                    this.prepareChildren();
                }
                this.children.add(Objects.requireNonNull(component, "components[?]"));
            }
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder append(@NotNull final Iterable components) {
        Objects.requireNonNull(components, "components");
        final Iterator<ComponentLike> iterator = components.iterator();
        while (iterator.hasNext()) {
            final Component component = iterator.next().asComponent();
            if (component != Component.empty()) {
                if (!true) {
                    this.prepareChildren();
                }
                this.children.add(Objects.requireNonNull(component, "components[?]"));
            }
        }
        return this;
    }
    
    private void prepareChildren() {
        if (this.children == Collections.emptyList()) {
            this.children = new ArrayList();
        }
    }
    
    @NotNull
    @Override
    public ComponentBuilder applyDeep(@NotNull final Consumer consumer) {
        this.apply(consumer);
        if (this.children == Collections.emptyList()) {
            return this;
        }
        final ListIterator<BuildableComponent> listIterator = this.children.listIterator();
        while (listIterator.hasNext()) {
            final BuildableComponent buildableComponent = listIterator.next();
            if (!(buildableComponent instanceof BuildableComponent)) {
                continue;
            }
            final ComponentBuilder builder = buildableComponent.toBuilder();
            builder.applyDeep(consumer);
            listIterator.set(builder.build());
        }
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder mapChildren(@NotNull final Function function) {
        if (this.children == Collections.emptyList()) {
            return this;
        }
        final ListIterator<BuildableComponent> listIterator = this.children.listIterator();
        while (listIterator.hasNext()) {
            final BuildableComponent buildableComponent = listIterator.next();
            if (!(buildableComponent instanceof BuildableComponent)) {
                continue;
            }
            final BuildableComponent buildableComponent2 = Objects.requireNonNull((BuildableComponent)function.apply(buildableComponent), "mappedChild");
            if (buildableComponent == buildableComponent2) {
                continue;
            }
            listIterator.set(buildableComponent2);
        }
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder mapChildrenDeep(@NotNull final Function function) {
        if (this.children == Collections.emptyList()) {
            return this;
        }
        final ListIterator<BuildableComponent> listIterator = this.children.listIterator();
        while (listIterator.hasNext()) {
            final BuildableComponent buildableComponent = listIterator.next();
            if (!(buildableComponent instanceof BuildableComponent)) {
                continue;
            }
            final BuildableComponent buildableComponent2 = Objects.requireNonNull((BuildableComponent)function.apply(buildableComponent), "mappedChild");
            if (buildableComponent2.children().isEmpty()) {
                if (buildableComponent == buildableComponent2) {
                    continue;
                }
                listIterator.set(buildableComponent2);
            }
            else {
                final ComponentBuilder builder = buildableComponent2.toBuilder();
                builder.mapChildrenDeep(function);
                listIterator.set(builder.build());
            }
        }
        return this;
    }
    
    @NotNull
    @Override
    public List children() {
        return Collections.unmodifiableList((List<?>)this.children);
    }
    
    @NotNull
    @Override
    public ComponentBuilder style(@NotNull final Style style) {
        this.style = style;
        this.styleBuilder = null;
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder style(@NotNull final Consumer consumer) {
        consumer.accept(this.styleBuilder());
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder font(@Nullable final Key font) {
        this.styleBuilder().font(font);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder color(@Nullable final TextColor color) {
        this.styleBuilder().color(color);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder colorIfAbsent(@Nullable final TextColor color) {
        this.styleBuilder().colorIfAbsent(color);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        this.styleBuilder().decoration(decoration, state);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder clickEvent(@Nullable final ClickEvent event) {
        this.styleBuilder().clickEvent(event);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder hoverEvent(@Nullable final HoverEventSource source) {
        this.styleBuilder().hoverEvent(source);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder insertion(@Nullable final String insertion) {
        this.styleBuilder().insertion(insertion);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
        this.styleBuilder().merge(that.style(), merges);
        return this;
    }
    
    @NotNull
    @Override
    public ComponentBuilder resetStyle() {
        this.style = null;
        this.styleBuilder = null;
        return this;
    }
    
    private Style.Builder styleBuilder() {
        if (this.styleBuilder == null) {
            if (this.style != null) {
                this.styleBuilder = this.style.toBuilder();
                this.style = null;
            }
            else {
                this.styleBuilder = Style.style();
            }
        }
        return this.styleBuilder;
    }
    
    protected final boolean hasStyle() {
        return this.styleBuilder != null || this.style != null;
    }
    
    @NotNull
    protected Style buildStyle() {
        if (this.styleBuilder != null) {
            return this.styleBuilder.build();
        }
        if (this.style != null) {
            return this.style;
        }
        return Style.empty();
    }
}
