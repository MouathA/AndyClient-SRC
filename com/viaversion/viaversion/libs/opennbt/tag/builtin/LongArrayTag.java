package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.*;
import java.io.*;
import java.util.*;

public class LongArrayTag extends Tag
{
    public static final int ID = 12;
    private long[] value;
    
    public LongArrayTag() {
        this(new long[0]);
    }
    
    public LongArrayTag(final long[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    @Override
    public long[] getValue() {
        return this.value;
    }
    
    public void setValue(final long[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    public long getValue(final int n) {
        return this.value[n];
    }
    
    public void setValue(final int n, final long n2) {
        this.value[n] = n2;
    }
    
    public int length() {
        return this.value.length;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = new long[dataInput.readInt()];
        while (0 < this.value.length) {
            this.value[0] = dataInput.readLong();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        final long[] value = this.value;
        while (0 < value.length) {
            dataOutput.writeLong(value[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Arrays.equals(this.value, ((LongArrayTag)o).value));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public final LongArrayTag clone() {
        return new LongArrayTag(this.value.clone());
    }
    
    @Override
    public int getTagId() {
        return 12;
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
