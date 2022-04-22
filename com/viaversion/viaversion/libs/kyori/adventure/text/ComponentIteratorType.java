package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;
import java.util.*;

@FunctionalInterface
@ApiStatus.NonExtendable
public interface ComponentIteratorType
{
    public static final ComponentIteratorType DEPTH_FIRST = ComponentIteratorType::lambda$static$0;
    public static final ComponentIteratorType BREADTH_FIRST = ComponentIteratorType::lambda$static$1;
    
    void populate(@NotNull final Component component, @NotNull final Deque deque, @NotNull final Set flags);
    
    default void lambda$static$1(final Component component, final Deque deque, final Set set) {
        if (set.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent) {
            deque.addAll(((TranslatableComponent)component).args());
        }
        final HoverEvent hoverEvent = component.hoverEvent();
        if (hoverEvent != null) {
            final HoverEvent.Action action = hoverEvent.action();
            if (set.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && action == HoverEvent.Action.SHOW_ENTITY) {
                deque.addLast(((HoverEvent.ShowEntity)hoverEvent.value()).name());
            }
            else if (set.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && action == HoverEvent.Action.SHOW_TEXT) {
                deque.addLast(hoverEvent.value());
            }
        }
        deque.addAll(component.children());
    }
    
    default void lambda$static$0(final Component component, final Deque deque, final Set set) {
        if (set.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent) {
            final List args = ((TranslatableComponent)component).args();
            for (int i = args.size() - 1; i >= 0; --i) {
                deque.addFirst(args.get(i));
            }
        }
        final HoverEvent hoverEvent = component.hoverEvent();
        if (hoverEvent != null) {
            final HoverEvent.Action action = hoverEvent.action();
            if (set.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && action == HoverEvent.Action.SHOW_ENTITY) {
                deque.addFirst(((HoverEvent.ShowEntity)hoverEvent.value()).name());
            }
            else if (set.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && action == HoverEvent.Action.SHOW_TEXT) {
                deque.addFirst(hoverEvent.value());
            }
        }
        final List children = component.children();
        for (int j = children.size() - 1; j >= 0; --j) {
            deque.addFirst(children.get(j));
        }
    }
}
