package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;
import java.util.*;

public final class Services
{
    private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL;
    
    private Services() {
    }
    
    @NotNull
    public static Optional service(@NotNull final Class type) {
        final Iterator<Object> iterator = (Iterator<Object>)Services0.loader(type).iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        final Object next = iterator.next();
        if (iterator.hasNext()) {
            throw new IllegalStateException("Expected to find one service " + type + ", found multiple");
        }
        return Optional.of(next);
    }
    
    static {
        SERVICE_LOAD_FAILURES_ARE_FATAL = Boolean.parseBoolean(System.getProperty(String.join(".", "net", "kyori", "adventure", "serviceLoadFailuresAreFatal"), String.valueOf(true)));
    }
}
