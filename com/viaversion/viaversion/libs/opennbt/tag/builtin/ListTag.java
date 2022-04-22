package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import org.jetbrains.annotations.*;
import com.google.common.base.*;
import com.viaversion.viaversion.libs.opennbt.tag.*;
import java.io.*;
import java.util.*;

public class ListTag extends Tag implements Iterable
{
    public static final int ID = 9;
    private final List value;
    private Class type;
    
    public ListTag() {
        this.value = new ArrayList();
    }
    
    public ListTag(@Nullable final Class type) {
        this.type = type;
        this.value = new ArrayList();
    }
    
    public ListTag(final List value) throws IllegalArgumentException {
        this.value = new ArrayList(value.size());
        this.setValue(value);
    }
    
    @Override
    public List getValue() {
        return this.value;
    }
    
    public void setValue(final List list) throws IllegalArgumentException {
        Preconditions.checkNotNull(list);
        this.type = null;
        this.value.clear();
        final Iterator<Tag> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    public Class getElementType() {
        return this.type;
    }
    
    public boolean add(final Tag tag) throws IllegalArgumentException {
        Preconditions.checkNotNull(tag);
        if (this.type == null) {
            this.type = tag.getClass();
        }
        else if (tag.getClass() != this.type) {
            throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
        }
        return this.value.add(tag);
    }
    
    public boolean remove(final Tag tag) {
        return this.value.remove(tag);
    }
    
    public Tag get(final int n) {
        return this.value.get(n);
    }
    
    public int size() {
        return this.value.size();
    }
    
    @Override
    public Iterator iterator() {
        return this.value.iterator();
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.type = null;
        final byte byte1 = dataInput.readByte();
        if (byte1 != 0) {
            this.type = TagRegistry.getClassFor(byte1);
            if (this.type == null) {
                throw new IOException("Unknown tag ID in ListTag: " + byte1);
            }
        }
        while (0 < dataInput.readInt()) {
            final Tag instance = TagRegistry.createInstance(byte1);
            instance.read(dataInput);
            this.add(instance);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        if (this.type == null) {
            dataOutput.writeByte(0);
        }
        else {
            final int id = TagRegistry.getIdFor(this.type);
            if (id == -1) {
                throw new IOException("ListTag contains unregistered tag class.");
            }
            dataOutput.writeByte(id);
        }
        dataOutput.writeInt(this.value.size());
        final Iterator<Tag> iterator = this.value.iterator();
        while (iterator.hasNext()) {
            iterator.next().write(dataOutput);
        }
    }
    
    @Override
    public final ListTag clone() {
        final ArrayList<Tag> list = new ArrayList<Tag>();
        final Iterator<Tag> iterator = this.value.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().clone());
        }
        return new ListTag(list);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ListTag listTag = (ListTag)o;
        return Objects.equals(this.type, listTag.type) && this.value.equals(listTag.value);
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.type != null) ? this.type.hashCode() : 0) + this.value.hashCode();
    }
    
    @Override
    public int getTagId() {
        return 9;
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
