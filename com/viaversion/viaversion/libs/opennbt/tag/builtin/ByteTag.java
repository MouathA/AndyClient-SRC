package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;

public class ByteTag extends NumberTag
{
    public static final int ID = 1;
    private byte value;
    
    public ByteTag() {
        this((byte)0);
    }
    
    public ByteTag(final byte value) {
        this.value = value;
    }
    
    @Deprecated
    @Override
    public Byte getValue() {
        return this.value;
    }
    
    public void setValue(final byte value) {
        this.value = value;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = dataInput.readByte();
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value == ((ByteTag)o).value);
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public final ByteTag clone() {
        return new ByteTag(this.value);
    }
    
    @Override
    public byte asByte() {
        return this.value;
    }
    
    @Override
    public short asShort() {
        return this.value;
    }
    
    @Override
    public int asInt() {
        return this.value;
    }
    
    @Override
    public long asLong() {
        return this.value;
    }
    
    @Override
    public float asFloat() {
        return this.value;
    }
    
    @Override
    public double asDouble() {
        return this.value;
    }
    
    @Override
    public int getTagId() {
        return 1;
    }
    
    @Override
    public Tag clone() {
        return this.clone();
    }
    
    @Deprecated
    @Override
    public Object getValue() {
        return this.getValue();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
