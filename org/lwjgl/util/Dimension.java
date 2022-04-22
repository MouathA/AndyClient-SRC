package org.lwjgl.util;

import java.io.*;

public final class Dimension implements Serializable, ReadableDimension, WritableDimension
{
    static final long serialVersionUID = 1L;
    private int width;
    private int height;
    
    public Dimension() {
    }
    
    public Dimension(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public Dimension(final ReadableDimension size) {
        this.setSize(size);
    }
    
    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setSize(final ReadableDimension readableDimension) {
        this.width = readableDimension.getWidth();
        this.height = readableDimension.getHeight();
    }
    
    public void getSize(final WritableDimension writableDimension) {
        writableDimension.setSize(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ReadableDimension) {
            final ReadableDimension readableDimension = (ReadableDimension)o;
            return this.width == readableDimension.getWidth() && this.height == readableDimension.getHeight();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int n = this.width + this.height;
        return n * (n + 1) / 2 + this.width;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[width=" + this.width + ",height=" + this.height + "]";
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
}
