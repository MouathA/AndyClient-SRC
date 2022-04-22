package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.function.*;
import java.util.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ComponentLike
{
    @NotNull
    default List asComponents(@NotNull final List likes) {
        return asComponents(likes, null);
    }
    
    @NotNull
    default List asComponents(@NotNull final List likes, @Nullable final Predicate filter) {
        final int size = likes.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        ArrayList list = null;
        while (0 < size) {
            final Component component = likes.get(0).asComponent();
            if (filter == null || filter.test(component)) {
                if (list == null) {
                    list = new ArrayList<Object>(size);
                }
                list.add(component);
            }
            int n = 0;
            ++n;
        }
        if (list == null) {
            return Collections.emptyList();
        }
        list.trimToSize();
        return Collections.unmodifiableList((List<?>)list);
    }
    
    @Nullable
    default Component unbox(@Nullable final ComponentLike like) {
        return (like != null) ? like.asComponent() : null;
    }
    
    @Contract(pure = true)
    @NotNull
    Component asComponent();
}
