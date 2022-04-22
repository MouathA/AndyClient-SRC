package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Vector4f extends Vector implements Serializable, ReadableVector4f, WritableVector4f
{
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Vector4f() {
    }
    
    public Vector4f(final ReadableVector4f readableVector4f) {
        this.set(readableVector4f);
    }
    
    public Vector4f(final float n, final float n2, final float n3, final float n4) {
        this.set(n, n2, n3, n4);
    }
    
    public void set(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vector4f set(final ReadableVector4f readableVector4f) {
        this.x = readableVector4f.getX();
        this.y = readableVector4f.getY();
        this.z = readableVector4f.getZ();
        this.w = readableVector4f.getW();
        return this;
    }
    
    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public Vector4f translate(final float n, final float n2, final float n3, final float n4) {
        this.x += n;
        this.y += n2;
        this.z += n3;
        this.w += n4;
        return this;
    }
    
    public static Vector4f add(final Vector4f vector4f, final Vector4f vector4f2, final Vector4f vector4f3) {
        if (vector4f3 == null) {
            return new Vector4f(vector4f.x + vector4f2.x, vector4f.y + vector4f2.y, vector4f.z + vector4f2.z, vector4f.w + vector4f2.w);
        }
        vector4f3.set(vector4f.x + vector4f2.x, vector4f.y + vector4f2.y, vector4f.z + vector4f2.z, vector4f.w + vector4f2.w);
        return vector4f3;
    }
    
    public static Vector4f sub(final Vector4f vector4f, final Vector4f vector4f2, final Vector4f vector4f3) {
        if (vector4f3 == null) {
            return new Vector4f(vector4f.x - vector4f2.x, vector4f.y - vector4f2.y, vector4f.z - vector4f2.z, vector4f.w - vector4f2.w);
        }
        vector4f3.set(vector4f.x - vector4f2.x, vector4f.y - vector4f2.y, vector4f.z - vector4f2.z, vector4f.w - vector4f2.w);
        return vector4f3;
    }
    
    @Override
    public Vector negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
        return this;
    }
    
    public Vector4f negate(Vector4f vector4f) {
        if (vector4f == null) {
            vector4f = new Vector4f();
        }
        vector4f.x = -this.x;
        vector4f.y = -this.y;
        vector4f.z = -this.z;
        vector4f.w = -this.w;
        return vector4f;
    }
    
    public Vector4f normalise(Vector4f vector4f) {
        final float length = this.length();
        if (vector4f == null) {
            vector4f = new Vector4f(this.x / length, this.y / length, this.z / length, this.w / length);
        }
        else {
            vector4f.set(this.x / length, this.y / length, this.z / length, this.w / length);
        }
        return vector4f;
    }
    
    public static float dot(final Vector4f vector4f, final Vector4f vector4f2) {
        return vector4f.x * vector4f2.x + vector4f.y * vector4f2.y + vector4f.z * vector4f2.z + vector4f.w * vector4f2.w;
    }
    
    public static float angle(final Vector4f vector4f, final Vector4f vector4f2) {
        float n = dot(vector4f, vector4f2) / (vector4f.length() * vector4f2.length());
        if (n < -1.0f) {
            n = -1.0f;
        }
        else if (n > 1.0f) {
            n = 1.0f;
        }
        return (float)Math.acos(n);
    }
    
    @Override
    public Vector load(final FloatBuffer floatBuffer) {
        this.x = floatBuffer.get();
        this.y = floatBuffer.get();
        this.z = floatBuffer.get();
        this.w = floatBuffer.get();
        return this;
    }
    
    @Override
    public Vector scale(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
        return this;
    }
    
    @Override
    public Vector store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.x);
        floatBuffer.put(this.y);
        floatBuffer.put(this.z);
        floatBuffer.put(this.w);
        return this;
    }
    
    @Override
    public String toString() {
        return "Vector4f: " + this.x + " " + this.y + " " + this.z + " " + this.w;
    }
    
    public final float getX() {
        return this.x;
    }
    
    public final float getY() {
        return this.y;
    }
    
    public final void setX(final float x) {
        this.x = x;
    }
    
    public final void setY(final float y) {
        this.y = y;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public void setW(final float w) {
        this.w = w;
    }
    
    public float getW() {
        return this.w;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final Vector4f vector4f = (Vector4f)o;
        return this.x == vector4f.x && this.y == vector4f.y && this.z == vector4f.z && this.w == vector4f.w;
    }
}
