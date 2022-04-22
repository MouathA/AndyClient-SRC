package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;
import java.util.*;

public final class MonkeyBars
{
    private MonkeyBars() {
    }
    
    @SafeVarargs
    @NotNull
    public static Set enumSet(final Class type, final Enum... constants) {
        final EnumSet<Enum> none = EnumSet.noneOf((Class<Enum>)type);
        Collections.addAll(none, (Enum[])constants);
        return Collections.unmodifiableSet((Set<?>)none);
    }
    
    @NotNull
    public static List addOne(@NotNull final List oldList, final Object newElement) {
        if (oldList.isEmpty()) {
            return Collections.singletonList(newElement);
        }
        final ArrayList list = new ArrayList<Object>(oldList.size() + 1);
        list.addAll((Collection<?>)oldList);
        list.add(newElement);
        return Collections.unmodifiableList((List<?>)list);
    }
}
