package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.*;
import java.io.*;

public class CompoundTag extends Tag implements Iterable
{
    public static final int ID = 10;
    private Map value;
    
    public CompoundTag() {
        this(new LinkedHashMap());
    }
    
    public CompoundTag(final Map map) {
        this.value = new LinkedHashMap(map);
    }
    
    public CompoundTag(final LinkedHashMap value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    @Override
    public Map getValue() {
        return this.value;
    }
    
    public void setValue(final Map map) {
        Preconditions.checkNotNull(map);
        this.value = new LinkedHashMap(map);
    }
    
    public void setValue(final LinkedHashMap value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    public boolean isEmpty() {
        return this.value.isEmpty();
    }
    
    public boolean contains(final String s) {
        return this.value.containsKey(s);
    }
    
    @Nullable
    public Tag get(final String s) {
        return this.value.get(s);
    }
    
    @Nullable
    public Tag put(final String s, final Tag tag) {
        return this.value.put(s, tag);
    }
    
    @Nullable
    public Tag remove(final String s) {
        return this.value.remove(s);
    }
    
    public Set keySet() {
        return this.value.keySet();
    }
    
    public Collection values() {
        return this.value.values();
    }
    
    public Set entrySet() {
        return this.value.entrySet();
    }
    
    public int size() {
        return this.value.size();
    }
    
    public void clear() {
        this.value.clear();
    }
    
    @Override
    public Iterator iterator() {
        return this.value.entrySet().iterator();
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        while (true) {
            final byte byte1 = dataInput.readByte();
            if (byte1 == 0) {
                break;
            }
            final String utf = dataInput.readUTF();
            final Tag instance = TagRegistry.createInstance(byte1);
            instance.read(dataInput);
            this.value.put(utf, instance);
        }
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        for (final Map.Entry<K, Tag> entry : this.value.entrySet()) {
            final Tag tag = entry.getValue();
            dataOutput.writeByte(tag.getTagId());
            dataOutput.writeUTF((String)entry.getKey());
            tag.write(dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value.equals(((CompoundTag)o).value));
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public final CompoundTag clone() {
        final LinkedHashMap<Object, Tag> linkedHashMap = new LinkedHashMap<Object, Tag>();
        for (final Map.Entry<Object, V> entry : this.value.entrySet()) {
            linkedHashMap.put(entry.getKey(), ((Tag)entry.getValue()).clone());
        }
        return new CompoundTag(linkedHashMap);
    }
    
    @Override
    public int getTagId() {
        return 10;
    }
    
    @Override
    public Tag clone() {
        return this.clone();
    }
    
    @Override
    public Object getValue() {
        return this.getValue();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
