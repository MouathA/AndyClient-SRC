package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.function.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;

public interface ScopedComponent extends Component
{
    @NotNull
    Component children(@NotNull final List children);
    
    @NotNull
    Component style(@NotNull final Style style);
    
    @NotNull
    default Component style(@NotNull final Consumer style) {
        return super.style(style);
    }
    
    @NotNull
    default Component style(final Style.Builder style) {
        return super.style(style);
    }
    
    @NotNull
    default Component mergeStyle(@NotNull final Component that) {
        return super.mergeStyle(that);
    }
    
    @NotNull
    default Component mergeStyle(@NotNull final Component that, final Style.Merge... merges) {
        return super.mergeStyle(that, merges);
    }
    
    @NotNull
    default Component append(@NotNull final Component component) {
        if (component == Component.empty()) {
            return this;
        }
        return this.children(MonkeyBars.addOne(this.children(), Objects.requireNonNull(component, "component")));
    }
    
    @NotNull
    default Component append(@NotNull final ComponentLike component) {
        return super.append(component);
    }
    
    @NotNull
    default Component append(@NotNull final ComponentBuilder builder) {
        return super.append(builder);
    }
    
    @NotNull
    default Component mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
        return super.mergeStyle(that, merges);
    }
    
    @NotNull
    default Component color(@Nullable final TextColor color) {
        return super.color(color);
    }
    
    @NotNull
    default Component colorIfAbsent(@Nullable final TextColor color) {
        return super.colorIfAbsent(color);
    }
    
    @NotNull
    default Component decorate(@NotNull final TextDecoration decoration) {
        return super.decorate(decoration);
    }
    
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final boolean flag) {
        return super.decoration(decoration, flag);
    }
    
    @NotNull
    default Component decoration(@NotNull final TextDecoration decoration, final TextDecoration.State state) {
        return super.decoration(decoration, state);
    }
    
    @NotNull
    default Component clickEvent(@Nullable final ClickEvent event) {
        return super.clickEvent(event);
    }
    
    @NotNull
    default Component hoverEvent(@Nullable final HoverEventSource event) {
        return super.hoverEvent(event);
    }
    
    @NotNull
    default Component insertion(@Nullable final String insertion) {
        return super.insertion(insertion);
    }
}
