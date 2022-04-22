package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.concurrent.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;

public abstract class Listenable
{
    private final List listeners;
    
    public Listenable() {
        this.listeners = new CopyOnWriteArrayList();
    }
    
    protected final void forEachListener(@NotNull final Consumer consumer) {
        final Iterator<Object> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }
    
    protected final void addListener0(@NotNull final Object listener) {
        this.listeners.add(listener);
    }
    
    protected final void removeListener0(@NotNull final Object listener) {
        this.listeners.remove(listener);
    }
}
