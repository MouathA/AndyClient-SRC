package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class EnumHashBiMap extends AbstractBiMap
{
    private transient Class keyType;
    @GwtIncompatible("only needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static EnumHashBiMap create(final Class clazz) {
        return new EnumHashBiMap(clazz);
    }
    
    public static EnumHashBiMap create(final Map map) {
        final EnumHashBiMap create = create(EnumBiMap.inferKeyType(map));
        create.putAll(map);
        return create;
    }
    
    private EnumHashBiMap(final Class keyType) {
        super(WellBehavedMap.wrap(new EnumMap(keyType)), Maps.newHashMapWithExpectedSize(((Enum[])keyType.getEnumConstants()).length));
        this.keyType = keyType;
    }
    
    Enum checkKey(final Enum enum1) {
        return (Enum)Preconditions.checkNotNull(enum1);
    }
    
    public Object put(final Enum enum1, @Nullable final Object o) {
        return super.put(enum1, o);
    }
    
    public Object forcePut(final Enum enum1, @Nullable final Object o) {
        return super.forcePut(enum1, o);
    }
    
    public Class keyType() {
        return this.keyType;
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyType);
        Serialization.writeMap(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyType = (Class)objectInputStream.readObject();
        this.setDelegates(WellBehavedMap.wrap(new EnumMap(this.keyType)), new HashMap(((Enum[])this.keyType.getEnumConstants()).length * 3 / 2));
        Serialization.populateMap(this, objectInputStream);
    }
    
    @Override
    public Set entrySet() {
        return super.entrySet();
    }
    
    @Override
    public Set values() {
        return super.values();
    }
    
    @Override
    public Set keySet() {
        return super.keySet();
    }
    
    @Override
    public BiMap inverse() {
        return super.inverse();
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public void putAll(final Map map) {
        super.putAll(map);
    }
    
    @Override
    public Object remove(final Object o) {
        return super.remove(o);
    }
    
    @Override
    public Object forcePut(final Object o, final Object o2) {
        return this.forcePut((Enum)o, o2);
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.put((Enum)o, o2);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return super.containsValue(o);
    }
    
    @Override
    Object checkKey(final Object o) {
        return this.checkKey((Enum)o);
    }
}
