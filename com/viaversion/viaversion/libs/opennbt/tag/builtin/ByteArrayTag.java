package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;
import java.util.*;

public class ByteArrayTag extends Tag
{
    public static final int ID = 7;
    private byte[] value;
    
    public ByteArrayTag() {
        this(new byte[0]);
    }
    
    public ByteArrayTag(final byte[] value) {
        this.value = value;
    }
    
    @Override
    public byte[] getValue() {
        return this.value;
    }
    
    public void setValue(final byte[] value) {
        if (value == null) {
            return;
        }
        this.value = value;
    }
    
    public byte getValue(final int n) {
        return this.value[n];
    }
    
    public void setValue(final int n, final byte b) {
        this.value[n] = b;
    }
    
    public int length() {
        return this.value.length;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        dataInput.readFully(this.value = new byte[dataInput.readInt()]);
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        dataOutput.write(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Arrays.equals(this.value, ((ByteArrayTag)o).value));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @Override
    public final ByteArrayTag clone() {
        return new ByteArrayTag(this.value);
    }
    
    @Override
    public int getTagId() {
        return 7;
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
