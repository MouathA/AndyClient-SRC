package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.function.*;
import java.util.*;

public interface Object2ObjectMap extends Object2ObjectFunction, Map
{
    int size();
    
    default void clear() {
        throw new UnsupportedOperationException();
    }
    
    void defaultReturnValue(final Object p0);
    
    Object defaultReturnValue();
    
    ObjectSet object2ObjectEntrySet();
    
    default ObjectSet entrySet() {
        return this.object2ObjectEntrySet();
    }
    
    default Object put(final Object o, final Object o2) {
        return super.put(o, o2);
    }
    
    default Object remove(final Object o) {
        return super.remove(o);
    }
    
    ObjectSet keySet();
    
    ObjectCollection values();
    
    boolean containsKey(final Object p0);
    
    default void forEach(final BiConsumer biConsumer) {
        final ObjectSet object2ObjectEntrySet = this.object2ObjectEntrySet();
        final Consumer<? super Object> consumer = Object2ObjectMap::lambda$forEach$0;
        if (object2ObjectEntrySet instanceof FastEntrySet) {
            ((FastEntrySet)object2ObjectEntrySet).fastForEach(consumer);
        }
        else {
            object2ObjectEntrySet.forEach(consumer);
        }
    }
    
    default Object getOrDefault(final Object o, final Object o2) {
        final Object value;
        return ((value = this.get(o)) != this.defaultReturnValue() || this.containsKey(o)) ? value : o2;
    }
    
    default Object putIfAbsent(final Object o, final Object o2) {
        final Object value = this.get(o);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(o)) {
            return value;
        }
        this.put(o, o2);
        return defaultReturnValue;
    }
    
    default boolean remove(final Object o, final Object o2) {
        final Object value = this.get(o);
        if (!Objects.equals(value, o2) || (value == this.defaultReturnValue() && !this.containsKey(o))) {
            return false;
        }
        this.remove(o);
        return true;
    }
    
    default boolean replace(final Object o, final Object o2, final Object o3) {
        final Object value = this.get(o);
        if (!Objects.equals(value, o2) || (value == this.defaultReturnValue() && !this.containsKey(o))) {
            return false;
        }
        this.put(o, o3);
        return true;
    }
    
    default Object replace(final Object o, final Object o2) {
        return this.containsKey(o) ? this.put(o, o2) : this.defaultReturnValue();
    }
    
    default Object computeIfAbsent(final Object o, final Object2ObjectFunction object2ObjectFunction) {
        Objects.requireNonNull(object2ObjectFunction);
        final Object value = this.get(o);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(o)) {
            return value;
        }
        if (!object2ObjectFunction.containsKey(o)) {
            return defaultReturnValue;
        }
        final Object value2 = object2ObjectFunction.get(o);
        this.put(o, value2);
        return value2;
    }
    
    @Deprecated
    default Object computeObjectIfAbsentPartial(final Object o, final Object2ObjectFunction object2ObjectFunction) {
        return this.computeIfAbsent(o, object2ObjectFunction);
    }
    
    default Object computeIfPresent(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final Object value = this.get(o);
        final Object defaultReturnValue = this.defaultReturnValue();
        if (value == defaultReturnValue && !this.containsKey(o)) {
            return defaultReturnValue;
        }
        final Object apply = biFunction.apply(o, value);
        if (apply == null) {
            this.remove(o);
            return defaultReturnValue;
        }
        this.put(o, apply);
        return apply;
    }
    
    default Object compute(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final Object value = this.get(o);
        final Object defaultReturnValue = this.defaultReturnValue();
        final boolean b = value != defaultReturnValue || this.containsKey(o);
        final Object apply = biFunction.apply(o, b ? value : null);
        if (apply == null) {
            if (b) {
                this.remove(o);
            }
            return defaultReturnValue;
        }
        this.put(o, apply);
        return apply;
    }
    
    default Object merge(final Object o, final Object o2, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(o2);
        final Object value = this.get(o);
        final Object defaultReturnValue = this.defaultReturnValue();
        Object o3;
        if (value != defaultReturnValue || this.containsKey(o)) {
            final Object apply = biFunction.apply(value, o2);
            if (apply == null) {
                this.remove(o);
                return defaultReturnValue;
            }
            o3 = apply;
        }
        else {
            o3 = o2;
        }
        this.put(o, o3);
        return o3;
    }
    
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
        biConsumer.accept(entry.getKey(), entry.getValue());
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
    }
}
