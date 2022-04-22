package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.function.*;
import java.util.*;

public interface Object2IntMap extends Object2IntFunction, Map
{
    int size();
    
    default void clear() {
        throw new UnsupportedOperationException();
    }
    
    void defaultReturnValue(final int p0);
    
    int defaultReturnValue();
    
    ObjectSet object2IntEntrySet();
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.object2IntEntrySet();
    }
    
    @Deprecated
    default Integer put(final Object o, final Integer n) {
        return super.put(o, n);
    }
    
    @Deprecated
    default Integer get(final Object o) {
        return super.get(o);
    }
    
    @Deprecated
    default Integer remove(final Object o) {
        return super.remove(o);
    }
    
    ObjectSet keySet();
    
    IntCollection values();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final int p0);
    
    @Deprecated
    default boolean containsValue(final Object o) {
        return o != null && this.containsValue((int)o);
    }
    
    default void forEach(final BiConsumer biConsumer) {
        final ObjectSet object2IntEntrySet = this.object2IntEntrySet();
        final Consumer<? super Object> consumer = Object2IntMap::lambda$forEach$0;
        if (object2IntEntrySet instanceof FastEntrySet) {
            ((FastEntrySet)object2IntEntrySet).fastForEach(consumer);
        }
        else {
            object2IntEntrySet.forEach(consumer);
        }
    }
    
    default int getOrDefault(final Object o, final int n) {
        final int int1;
        return ((int1 = this.getInt(o)) != this.defaultReturnValue() || this.containsKey(o)) ? int1 : n;
    }
    
    @Deprecated
    default Integer getOrDefault(final Object o, final Integer n) {
        return super.getOrDefault(o, n);
    }
    
    default int putIfAbsent(final Object o, final int n) {
        final int int1 = this.getInt(o);
        final int defaultReturnValue = this.defaultReturnValue();
        if (int1 != defaultReturnValue || this.containsKey(o)) {
            return int1;
        }
        this.put(o, n);
        return defaultReturnValue;
    }
    
    default boolean remove(final Object o, final int n) {
        final int int1 = this.getInt(o);
        if (int1 != n || (int1 == this.defaultReturnValue() && !this.containsKey(o))) {
            return false;
        }
        this.removeInt(o);
        return true;
    }
    
    default boolean replace(final Object o, final int n, final int n2) {
        final int int1 = this.getInt(o);
        if (int1 != n || (int1 == this.defaultReturnValue() && !this.containsKey(o))) {
            return false;
        }
        this.put(o, n2);
        return true;
    }
    
    default int replace(final Object o, final int n) {
        return this.containsKey(o) ? this.put(o, n) : this.defaultReturnValue();
    }
    
    default int computeIfAbsent(final Object o, final ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        final int int1 = this.getInt(o);
        if (int1 != this.defaultReturnValue() || this.containsKey(o)) {
            return int1;
        }
        final int applyAsInt = toIntFunction.applyAsInt(o);
        this.put(o, applyAsInt);
        return applyAsInt;
    }
    
    @Deprecated
    default int computeIntIfAbsent(final Object o, final ToIntFunction toIntFunction) {
        return this.computeIfAbsent(o, toIntFunction);
    }
    
    default int computeIfAbsent(final Object o, final Object2IntFunction object2IntFunction) {
        Objects.requireNonNull(object2IntFunction);
        final int int1 = this.getInt(o);
        final int defaultReturnValue = this.defaultReturnValue();
        if (int1 != defaultReturnValue || this.containsKey(o)) {
            return int1;
        }
        if (!object2IntFunction.containsKey(o)) {
            return defaultReturnValue;
        }
        final int int2 = object2IntFunction.getInt(o);
        this.put(o, int2);
        return int2;
    }
    
    @Deprecated
    default int computeIntIfAbsentPartial(final Object o, final Object2IntFunction object2IntFunction) {
        return this.computeIfAbsent(o, object2IntFunction);
    }
    
    default int computeIntIfPresent(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int int1 = this.getInt(o);
        final int defaultReturnValue = this.defaultReturnValue();
        if (int1 == defaultReturnValue && !this.containsKey(o)) {
            return defaultReturnValue;
        }
        final Integer n = biFunction.apply(o, int1);
        if (n == null) {
            this.removeInt(o);
            return defaultReturnValue;
        }
        final int intValue = n;
        this.put(o, intValue);
        return intValue;
    }
    
    default int computeInt(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int int1 = this.getInt(o);
        final int defaultReturnValue = this.defaultReturnValue();
        final boolean b = int1 != defaultReturnValue || this.containsKey(o);
        final Integer n = biFunction.apply(o, b ? Integer.valueOf(int1) : null);
        if (n == null) {
            if (b) {
                this.removeInt(o);
            }
            return defaultReturnValue;
        }
        final int intValue = n;
        this.put(o, intValue);
        return intValue;
    }
    
    default int merge(final Object o, final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int int1 = this.getInt(o);
        final int defaultReturnValue = this.defaultReturnValue();
        int intValue;
        if (int1 != defaultReturnValue || this.containsKey(o)) {
            final Integer n2 = biFunction.apply(int1, n);
            if (n2 == null) {
                this.removeInt(o);
                return defaultReturnValue;
            }
            intValue = n2;
        }
        else {
            intValue = n;
        }
        this.put(o, intValue);
        return intValue;
    }
    
    default int mergeInt(final Object o, final int n, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        final int int1 = this.getInt(o);
        final int n2 = (int1 != this.defaultReturnValue() || this.containsKey(o)) ? intBinaryOperator.applyAsInt(int1, n) : n;
        this.put(o, n2);
        return n2;
    }
    
    default int mergeInt(final Object o, final int n, final com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(o, n, (IntBinaryOperator)intBinaryOperator);
    }
    
    @Deprecated
    default int mergeInt(final Object o, final int n, final BiFunction biFunction) {
        return this.merge(o, n, biFunction);
    }
    
    @Deprecated
    default Integer putIfAbsent(final Object o, final Integer n) {
        return super.putIfAbsent(o, n);
    }
    
    @Deprecated
    default boolean remove(final Object o, final Object o2) {
        return super.remove(o, o2);
    }
    
    @Deprecated
    default boolean replace(final Object o, final Integer n, final Integer n2) {
        return super.replace(o, n, n2);
    }
    
    @Deprecated
    default Integer replace(final Object o, final Integer n) {
        return super.replace(o, n);
    }
    
    @Deprecated
    default Integer merge(final Object o, final Integer n, final BiFunction biFunction) {
        return super.merge(o, n, biFunction);
    }
    
    @Deprecated
    default Object remove(final Object o) {
        return this.remove(o);
    }
    
    @Deprecated
    default Object getOrDefault(final Object o, final Object o2) {
        return this.getOrDefault(o, (Integer)o2);
    }
    
    @Deprecated
    default Object get(final Object o) {
        return this.get(o);
    }
    
    @Deprecated
    default Object put(final Object o, final Object o2) {
        return this.put(o, (Integer)o2);
    }
    
    @Deprecated
    default Object merge(final Object o, final Object o2, final BiFunction biFunction) {
        return this.merge(o, (Integer)o2, biFunction);
    }
    
    @Deprecated
    default Object replace(final Object o, final Object o2) {
        return this.replace(o, (Integer)o2);
    }
    
    @Deprecated
    default boolean replace(final Object o, final Object o2, final Object o3) {
        return this.replace(o, (Integer)o2, (Integer)o3);
    }
    
    @Deprecated
    default Object putIfAbsent(final Object o, final Object o2) {
        return this.putIfAbsent(o, (Integer)o2);
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
        biConsumer.accept(entry.getKey(), entry.getIntValue());
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
        int getIntValue();
        
        int setValue(final int p0);
        
        @Deprecated
        default Integer getValue() {
            return this.getIntValue();
        }
        
        @Deprecated
        default Integer setValue(final Integer n) {
            return this.setValue((int)n);
        }
        
        @Deprecated
        default Object setValue(final Object o) {
            return this.setValue((Integer)o);
        }
        
        @Deprecated
        default Object getValue() {
            return this.getValue();
        }
    }
}
