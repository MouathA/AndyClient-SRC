package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public interface Int2ObjectMap extends Int2ObjectFunction, Map
{
    int size();
    
    default void clear() {
        throw new UnsupportedOperationException();
    }
    
    void defaultReturnValue(final Object p0);
    
    Object defaultReturnValue();
    
    ObjectSet int2ObjectEntrySet();
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.int2ObjectEntrySet();
    }
    
    @Deprecated
    default Object put(final Integer n, final Object o) {
        return super.put(n, o);
    }
    
    @Deprecated
    default Object get(final Object o) {
        return super.get(o);
    }
    
    @Deprecated
    default Object remove(final Object o) {
        return super.remove(o);
    }
    
    IntSet keySet();
    
    ObjectCollection values();
    
    boolean containsKey(final int p0);
    
    @Deprecated
    default boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    default void forEach(final BiConsumer biConsumer) {
        final ObjectSet int2ObjectEntrySet = this.int2ObjectEntrySet();
        final Consumer<? super Object> consumer = Int2ObjectMap::lambda$forEach$0;
        if (int2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet)int2ObjectEntrySet).fastForEach(consumer);
        }
        else {
            int2ObjectEntrySet.forEach(consumer);
        }
    }
    
    default Object getOrDefault(final int n, final Object o) {
        final Object value;
        return ((value = this.get(n)) != this.defaultReturnValue() || this.containsKey(n)) ? value : o;
    }
    
    @Deprecated
    default Object getOrDefault(final Object o, final Object o2) {
        return super.getOrDefault(o, o2);
    }
    
    default Object putIfAbsent(final int n, final Object o) {
        final Object value = this.get(n);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(n)) {
            return value;
        }
        this.put(n, o);
        return defaultReturnValue;
    }
    
    default boolean remove(final int n, final Object o) {
        final Object value = this.get(n);
        if (!Objects.equals(value, o) || (value == this.defaultReturnValue() && !this.containsKey(n))) {
            return false;
        }
        this.remove(n);
        return true;
    }
    
    default boolean replace(final int n, final Object o, final Object o2) {
        final Object value = this.get(n);
        if (!Objects.equals(value, o) || (value == this.defaultReturnValue() && !this.containsKey(n))) {
            return false;
        }
        this.put(n, o2);
        return true;
    }
    
    default Object replace(final int n, final Object o) {
        return this.containsKey(n) ? this.put(n, o) : this.defaultReturnValue();
    }
    
    default Object computeIfAbsent(final int n, final IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        final Object value = this.get(n);
        if (value != this.defaultReturnValue() || this.containsKey(n)) {
            return value;
        }
        final Object apply = intFunction.apply(n);
        this.put(n, apply);
        return apply;
    }
    
    default Object computeIfAbsent(final int n, final Int2ObjectFunction int2ObjectFunction) {
        Objects.requireNonNull(int2ObjectFunction);
        final Object value = this.get(n);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(n)) {
            return value;
        }
        if (!int2ObjectFunction.containsKey(n)) {
            return defaultReturnValue;
        }
        final Object value2 = int2ObjectFunction.get(n);
        this.put(n, value2);
        return value2;
    }
    
    @Deprecated
    default Object computeIfAbsentPartial(final int n, final Int2ObjectFunction int2ObjectFunction) {
        return this.computeIfAbsent(n, int2ObjectFunction);
    }
    
    default Object computeIfPresent(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final Object value = this.get(n);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value == defaultReturnValue && !this.containsKey(n)) {
            return defaultReturnValue;
        }
        final Object apply = biFunction.apply(n, value);
        if (apply == null) {
            this.remove(n);
            return defaultReturnValue;
        }
        this.put(n, apply);
        return apply;
    }
    
    default Object compute(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final Object value = this.get(n);
        final Object defaultReturnValue = this.defaultReturnValue();
        final boolean b = value != defaultReturnValue || this.containsKey(n);
        final Object apply = biFunction.apply(n, b ? value : null);
        if (apply == null) {
            if (b) {
                this.remove(n);
            }
            return defaultReturnValue;
        }
        this.put(n, apply);
        return apply;
    }
    
    default Object merge(final int n, final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(o);
        final Object value = this.get(n);
        final Object defaultReturnValue = this.defaultReturnValue();
        Object o2;
        if (value != defaultReturnValue || this.containsKey(n)) {
            final Object apply = biFunction.apply(value, o);
            if (apply == null) {
                this.remove(n);
                return defaultReturnValue;
            }
            o2 = apply;
        }
        else {
            o2 = o;
        }
        this.put(n, o2);
        return o2;
    }
    
    @Deprecated
    default Object put(final Object o, final Object o2) {
        return this.put((Integer)o, o2);
    }
    
    @Deprecated
    default Set entrySet() {
        return this.entrySet();
    }
    
    default Collection values() {
        return this.values();
    }
    
    default Set keySet() {
        return this.keySet();
    }
    
    default void lambda$forEach$0(final BiConsumer biConsumer, final Entry entry) {
        biConsumer.accept(entry.getIntKey(), entry.getValue());
    }
    
    public interface FastEntrySet extends ObjectSet
    {
        ObjectIterator fastIterator();
        
        default void fastForEach(final Consumer consumer) {
            this.forEach(consumer);
        }
    }
    
    public interface Entry extends Map.Entry
    {
        int getIntKey();
        
        @Deprecated
        default Integer getKey() {
            return this.getIntKey();
        }
        
        @Deprecated
        default Object getKey() {
            return this.getKey();
        }
    }
}
