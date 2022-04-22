package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import java.io.*;
import java.lang.reflect.*;

public abstract class Tag implements Cloneable
{
    public abstract Object getValue();
    
    public abstract void read(final DataInput p0) throws IOException;
    
    public abstract void write(final DataOutput p0) throws IOException;
    
    public abstract int getTagId();
    
    public abstract Tag clone();
    
    @Override
    public String toString() {
        String s = "";
        if (this.getValue() != null) {
            s = this.getValue().toString();
            if (this.getValue().getClass().isArray()) {
                final StringBuilder sb = new StringBuilder();
                sb.append("[");
                while (0 < Array.getLength(this.getValue())) {
                    if (0 > 0) {
                        sb.append(", ");
                    }
                    sb.append(Array.get(this.getValue(), 0));
                    int n = 0;
                    ++n;
                }
                sb.append("]");
                s = sb.toString();
            }
        }
        return this.getClass().getSimpleName() + " { " + s + " }";
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
