package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.*;

public final class Index
{
    private final Map keyToValue;
    private final Map valueToKey;
    
    private Index(final Map keyToValue, final Map valueToKey) {
        this.keyToValue = keyToValue;
        this.valueToKey = valueToKey;
    }
    
    @NotNull
    public static Index create(final Class type, @NotNull final Function keyFunction) {
        return create(type, keyFunction, (Enum[])type.getEnumConstants());
    }
    
    @SafeVarargs
    @NotNull
    public static Index create(final Class type, @NotNull final Function keyFunction, @NotNull final Enum... values) {
        return create(values, Index::lambda$create$0, keyFunction);
    }
    
    @SafeVarargs
    @NotNull
    public static Index create(@NotNull final Function keyFunction, @NotNull final Object... values) {
        return create(values, HashMap::new, keyFunction);
    }
    
    @NotNull
    public static Index create(@NotNull final Function keyFunction, @NotNull final List constants) {
        return create(constants, HashMap::new, keyFunction);
    }
    
    @NotNull
    private static Index create(final Object[] values, final IntFunction valueToKeyFactory, @NotNull final Function keyFunction) {
        return create(Arrays.asList(values), valueToKeyFactory, keyFunction);
    }
    
    @NotNull
    private static Index create(final List values, final IntFunction valueToKeyFactory, @NotNull final Function keyFunction) {
        final int size = values.size();
        final HashMap hashMap = new HashMap<Object, Object>(size);
        final Map<? extends K, ? extends V> map = valueToKeyFactory.apply(size);
        while (0 < size) {
            final Object value = values.get(0);
            final Object apply = keyFunction.apply(value);
            if (hashMap.putIfAbsent(apply, value) != null) {
                throw new IllegalStateException(String.format("Key %s already mapped to value %s", apply, hashMap.get(apply)));
            }
            if (map.putIfAbsent((K)value, (V)apply) != null) {
                throw new IllegalStateException(String.format("Value %s already mapped to key %s", value, map.get(value)));
            }
            int n = 0;
            ++n;
        }
        return new Index(Collections.unmodifiableMap((Map<?, ?>)hashMap), Collections.unmodifiableMap((Map<?, ?>)map));
    }
    
    @NotNull
    public Set keys() {
        return Collections.unmodifiableSet(this.keyToValue.keySet());
    }
    
    @Nullable
    public Object key(@NotNull final Object value) {
        return this.valueToKey.get(value);
    }
    
    @NotNull
    public Set values() {
        return Collections.unmodifiableSet(this.valueToKey.keySet());
    }
    
    @Nullable
    public Object value(@NotNull final Object key) {
        return this.keyToValue.get(key);
    }
    
    private static Map lambda$create$0(final Class clazz, final int n) {
        return new EnumMap(clazz);
    }
}
