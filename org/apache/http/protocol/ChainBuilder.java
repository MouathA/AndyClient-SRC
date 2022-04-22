package org.apache.http.protocol;

import org.apache.http.annotation.*;
import java.util.*;

@NotThreadSafe
final class ChainBuilder
{
    private final LinkedList list;
    private final Map uniqueClasses;
    
    public ChainBuilder() {
        this.list = new LinkedList();
        this.uniqueClasses = new HashMap();
    }
    
    private void ensureUnique(final Object o) {
        final Object remove = this.uniqueClasses.remove(o.getClass());
        if (remove != null) {
            this.list.remove(remove);
        }
        this.uniqueClasses.put(o.getClass(), o);
    }
    
    public ChainBuilder addFirst(final Object o) {
        if (o == null) {
            return this;
        }
        this.ensureUnique(o);
        this.list.addFirst(o);
        return this;
    }
    
    public ChainBuilder addLast(final Object o) {
        if (o == null) {
            return this;
        }
        this.ensureUnique(o);
        this.list.addLast(o);
        return this;
    }
    
    public ChainBuilder addAllFirst(final Collection collection) {
        if (collection == null) {
            return this;
        }
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.addFirst(iterator.next());
        }
        return this;
    }
    
    public ChainBuilder addAllFirst(final Object... array) {
        if (array == null) {
            return this;
        }
        while (0 < array.length) {
            this.addFirst(array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public ChainBuilder addAllLast(final Collection collection) {
        if (collection == null) {
            return this;
        }
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.addLast(iterator.next());
        }
        return this;
    }
    
    public ChainBuilder addAllLast(final Object... array) {
        if (array == null) {
            return this;
        }
        while (0 < array.length) {
            this.addLast(array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public LinkedList build() {
        return new LinkedList(this.list);
    }
}
