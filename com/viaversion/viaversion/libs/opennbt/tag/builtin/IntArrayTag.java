package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.*;
import java.io.*;
import java.util.*;

public class IntArrayTag extends Tag
{
    public static final int ID = 11;
    private int[] value;
    
    public IntArrayTag() {
        this(new int[0]);
    }
    
    public IntArrayTag(final int[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    @Override
    public int[] getValue() {
        return this.value;
    }
    
    public void setValue(final int[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    public int getValue(final int n) {
        return this.value[n];
    }
    
    public void setValue(final int n, final int n2) {
        this.value[n] = n2;
    }
    
    public int length() {
        return this.value.length;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = new int[dataInput.readInt()];
        while (0 < this.value.length) {
            this.value[0] = dataInput.readInt();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        final int[] value = this.value;
        while (0 < value.length) {
            dataOutput.writeInt(value[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Arrays.equals(this.value, ((IntArrayTag)o).value));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public final IntArrayTag clone() {
        return new IntArrayTag(this.value.clone());
    }
    
    @Override
    public int getTagId() {
        return 11;
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
