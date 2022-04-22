package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.*;
import java.io.*;

public class StringTag extends Tag
{
    public static final int ID = 8;
    private String value;
    
    public StringTag() {
        this("");
    }
    
    public StringTag(final String value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }
    
    @Override
    public void read(final DataInput dataInput) throws IOException {
        this.value = dataInput.readUTF();
    }
    
    @Override
    public void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value.equals(((StringTag)o).value));
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public final StringTag clone() {
        return new StringTag(this.value);
    }
    
    @Override
    public int getTagId() {
        return 8;
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
