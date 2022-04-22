package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public interface Int2IntMap extends Int2IntFunction, Map
{
    int size();
    
    default void clear() {
        throw new UnsupportedOperationException();
    }
    
    void defaultReturnValue(final int p0);
    
    int defaultReturnValue();
    
    ObjectSet int2IntEntrySet();
    
    @Deprecated
    default ObjectSet entrySet() {
        return this.int2IntEntrySet();
    }
    
    @Deprecated
    default Integer put(final Integer n, final Integer n2) {
        return super.put(n, n2);
    }
    
    @Deprecated
    default Integer get(final Object o) {
        return super.get(o);
    }
    
    @Deprecated
    default Integer remove(final Object o) {
        return super.remove(o);
    }
    
    IntSet keySet();
    
    IntCollection values();
    
    boolean containsKey(final int p0);
    
    @Deprecated
    default boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    boolean containsValue(final int p0);
    
    @Deprecated
    default boolean containsValue(final Object o) {
        return o != null && this.containsValue((int)o);
    }
    
    default void forEach(final BiConsumer biConsumer) {
        final ObjectSet int2IntEntrySet = this.int2IntEntrySet();
        final Consumer<? super Object> consumer = Int2IntMap::lambda$forEach$0;
        if (int2IntEntrySet instanceof FastEntrySet) {
            ((FastEntrySet)int2IntEntrySet).fastForEach(consumer);
        }
        else {
            int2IntEntrySet.forEach(consumer);
        }
    }
    
    default int getOrDefault(final int n, final int n2) {
        final int value;
        return ((value = this.get(n)) != this.defaultReturnValue() || this.containsKey(n)) ? value : n2;
    }
    
    @Deprecated
    default Integer getOrDefault(final Object o, final Integer n) {
        return super.getOrDefault(o, n);
    }
    
    default int putIfAbsent(final int n, final int n2) {
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(n)) {
            return value;
        }
        this.put(n, n2);
        return defaultReturnValue;
    }
    
    default boolean remove(final int n, final int n2) {
        final int value = this.get(n);
        if (value != n2 || (value == this.defaultReturnValue() && !this.containsKey(n))) {
            return false;
        }
        this.remove(n);
        return true;
    }
    
    default boolean replace(final int n, final int n2, final int n3) {
        final int value = this.get(n);
        if (value != n2 || (value == this.defaultReturnValue() && !this.containsKey(n))) {
            return false;
        }
        this.put(n, n3);
        return true;
    }
    
    default int replace(final int n, final int n2) {
        return this.containsKey(n) ? this.put(n, n2) : this.defaultReturnValue();
    }
    
    default int computeIfAbsent(final int n, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        final int value = this.get(n);
        if (value != this.defaultReturnValue() || this.containsKey(n)) {
            return value;
        }
        final int applyAsInt = intUnaryOperator.applyAsInt(n);
        this.put(n, applyAsInt);
        return applyAsInt;
    }
    
    default int computeIfAbsentNullable(final int n, final IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(n)) {
            return value;
        }
        final Integer n2 = intFunction.apply(n);
        if (n2 == null) {
            return defaultReturnValue;
        }
        final int intValue = n2;
        this.put(n, intValue);
        return intValue;
    }
    
    default int computeIfAbsent(final int n, final Int2IntFunction int2IntFunction) {
        Objects.requireNonNull(int2IntFunction);
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        if (value != defaultReturnValue || this.containsKey(n)) {
            return value;
        }
        if (!int2IntFunction.containsKey(n)) {
            return defaultReturnValue;
        }
        final int value2 = int2IntFunction.get(n);
        this.put(n, value2);
        return value2;
    }
    
    @Deprecated
    default int computeIfAbsentPartial(final int n, final Int2IntFunction int2IntFunction) {
        return this.computeIfAbsent(n, int2IntFunction);
    }
    
    default int computeIfPresent(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        if (value == defaultReturnValue && !this.containsKey(n)) {
            return defaultReturnValue;
        }
        final Integer n2 = biFunction.apply(n, value);
        if (n2 == null) {
            this.remove(n);
            return defaultReturnValue;
        }
        final int intValue = n2;
        this.put(n, intValue);
        return intValue;
    }
    
    default int compute(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        final boolean b = value != defaultReturnValue || this.containsKey(n);
        final Integer n2 = biFunction.apply(n, b ? Integer.valueOf(value) : null);
        if (n2 == null) {
            if (b) {
                this.remove(n);
            }
            return defaultReturnValue;
        }
        final int intValue = n2;
        this.put(n, intValue);
        return intValue;
    }
    
    default int merge(final int n, final int n2, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int value = this.get(n);
        final int defaultReturnValue = this.defaultReturnValue();
        int intValue;
        if (value != defaultReturnValue || this.containsKey(n)) {
            final Integer n3 = biFunction.apply(value, n2);
            if (n3 == null) {
                this.remove(n);
                return defaultReturnValue;
            }
            intValue = n3;
        }
        else {
            intValue = n2;
        }
        this.put(n, intValue);
        return intValue;
    }
    
    default int mergeInt(final int n, final int n2, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        final int value = this.get(n);
        final int n3 = (value != this.defaultReturnValue() || this.containsKey(n)) ? intBinaryOperator.applyAsInt(value, n2) : n2;
        this.put(n, n3);
        return n3;
    }
    
    default int mergeInt(final int n, final int n2, final com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(n, n2, (IntBinaryOperator)intBinaryOperator);
    }
    
    @Deprecated
    default Integer putIfAbsent(final Integer n, final Integer n2) {
        return super.putIfAbsent(n, n2);
    }
    
    @Deprecated
    default boolean remove(final Object o, final Object o2) {
        return super.remove(o, o2);
    }
    
    @Deprecated
    default boolean replace(final Integer n, final Integer n2, final Integer n3) {
        return super.replace(n, n2, n3);
    }
    
    @Deprecated
    default Integer replace(final Integer n, final Integer n2) {
        return super.replace(n, n2);
    }
    
    @Deprecated
    default Integer computeIfAbsent(final Integer n, final java.util.function.Function function) {
        return super.computeIfAbsent(n, function);
    }
    
    @Deprecated
    default Integer computeIfPresent(final Integer n, final BiFunction biFunction) {
        return super.computeIfPresent(n, biFunction);
    }
    
    @Deprecated
    default Integer compute(final Integer n, final BiFunction biFunction) {
        return super.compute(n, biFunction);
    }
    
    @Deprecated
    default Integer merge(final Integer n, final Integer n2, final BiFunction biFunction) {
        return super.merge(n, n2, biFunction);
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
        return this.put((Integer)o, (Integer)o2);
    }
    
    @Deprecated
    default Object merge(final Object o, final Object o2, final BiFunction biFunction) {
        return this.merge((Integer)o, (Integer)o2, biFunction);
    }
    
    @Deprecated
    default Object compute(final Object o, final BiFunction biFunction) {
        return this.compute((Integer)o, biFunction);
    }
    
    @Deprecated
    default Object computeIfPresent(final Object o, final BiFunction biFunction) {
        return this.computeIfPresent((Integer)o, biFunction);
    }
    
    @Deprecated
    default Object computeIfAbsent(final Object o, final java.util.function.Function function) {
        return this.computeIfAbsent((Integer)o, function);
    }
    
    @Deprecated
    default Object replace(final Object o, final Object o2) {
        return this.replace((Integer)o, (Integer)o2);
    }
    
    @Deprecated
    default boolean replace(final Object o, final Object o2, final Object o3) {
        return this.replace((Integer)o, (Integer)o2, (Integer)o3);
    }
    
    @Deprecated
    default Object putIfAbsent(final Object o, final Object o2) {
        return this.putIfAbsent((Integer)o, (Integer)o2);
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
        biConsumer.accept(entry.getIntKey(), entry.getIntValue());
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
        
        @Deprecated
        default Object getKey() {
            return this.getKey();
        }
    }
}
