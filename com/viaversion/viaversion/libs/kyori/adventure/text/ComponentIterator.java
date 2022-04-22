package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.*;
import java.util.*;

final class ComponentIterator implements Iterator
{
    private Component component;
    private final ComponentIteratorType type;
    private final Set flags;
    private final Deque deque;
    
    ComponentIterator(@NotNull final Component component, @NotNull final ComponentIteratorType type, @NotNull final Set flags) {
        this.component = component;
        this.type = type;
        this.flags = flags;
        this.deque = new ArrayDeque();
    }
    
    @Override
    public boolean hasNext() {
        return this.component != null || !this.deque.isEmpty();
    }
    
    @Override
    public Component next() {
        if (this.component != null) {
            final Component component = this.component;
            this.component = null;
            this.type.populate(component, this.deque, this.flags);
            return component;
        }
        if (this.deque.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.component = this.deque.poll();
        return this.next();
    }
    
    @Override
    public Object next() {
        return this.next();
    }
}
