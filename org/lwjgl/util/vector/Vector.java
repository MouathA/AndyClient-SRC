package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public abstract class Vector implements Serializable, ReadableVector
{
    protected Vector() {
    }
    
    public final float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }
    
    public abstract float lengthSquared();
    
    public abstract Vector load(final FloatBuffer p0);
    
    public abstract Vector negate();
    
    public final Vector normalise() {
        final float length = this.length();
        if (length != 0.0f) {
            return this.scale(1.0f / length);
        }
        throw new IllegalStateException("Zero length vector");
    }
    
    public abstract Vector store(final FloatBuffer p0);
    
    public abstract Vector scale(final float p0);
}
