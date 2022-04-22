package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Vector3f extends Vector implements Serializable, ReadableVector3f, WritableVector3f
{
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;
    
    public Vector3f() {
    }
    
    public Vector3f(final ReadableVector3f readableVector3f) {
        this.set(readableVector3f);
    }
    
    public Vector3f(final float n, final float n2, final float n3) {
        this.set(n, n2, n3);
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
    
    public Vector3f set(final ReadableVector3f readableVector3f) {
        this.x = readableVector3f.getX();
        this.y = readableVector3f.getY();
        this.z = readableVector3f.getZ();
        return this;
    }
    
    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public Vector3f translate(final float n, final float n2, final float n3) {
        this.x += n;
        this.y += n2;
        this.z += n3;
        return this;
    }
    
    public static Vector3f add(final Vector3f vector3f, final Vector3f vector3f2, final Vector3f vector3f3) {
        if (vector3f3 == null) {
            return new Vector3f(vector3f.x + vector3f2.x, vector3f.y + vector3f2.y, vector3f.z + vector3f2.z);
        }
        vector3f3.set(vector3f.x + vector3f2.x, vector3f.y + vector3f2.y, vector3f.z + vector3f2.z);
        return vector3f3;
    }
    
    public static Vector3f sub(final Vector3f vector3f, final Vector3f vector3f2, final Vector3f vector3f3) {
        if (vector3f3 == null) {
            return new Vector3f(vector3f.x - vector3f2.x, vector3f.y - vector3f2.y, vector3f.z - vector3f2.z);
        }
        vector3f3.set(vector3f.x - vector3f2.x, vector3f.y - vector3f2.y, vector3f.z - vector3f2.z);
        return vector3f3;
    }
    
    public static Vector3f cross(final Vector3f vector3f, final Vector3f vector3f2, Vector3f vector3f3) {
        if (vector3f3 == null) {
            vector3f3 = new Vector3f();
        }
        vector3f3.set(vector3f.y * vector3f2.z - vector3f.z * vector3f2.y, vector3f2.x * vector3f.z - vector3f2.z * vector3f.x, vector3f.x * vector3f2.y - vector3f.y * vector3f2.x);
        return vector3f3;
    }
    
    @Override
    public Vector negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
    
    public Vector3f negate(Vector3f vector3f) {
        if (vector3f == null) {
            vector3f = new Vector3f();
        }
        vector3f.x = -this.x;
        vector3f.y = -this.y;
        vector3f.z = -this.z;
        return vector3f;
    }
    
    public Vector3f normalise(Vector3f vector3f) {
        final float length = this.length();
        if (vector3f == null) {
            vector3f = new Vector3f(this.x / length, this.y / length, this.z / length);
        }
        else {
            vector3f.set(this.x / length, this.y / length, this.z / length);
        }
        return vector3f;
    }
    
    public static float dot(final Vector3f vector3f, final Vector3f vector3f2) {
        return vector3f.x * vector3f2.x + vector3f.y * vector3f2.y + vector3f.z * vector3f2.z;
    }
    
    public static float angle(final Vector3f vector3f, final Vector3f vector3f2) {
        float n = dot(vector3f, vector3f2) / (vector3f.length() * vector3f2.length());
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
        return this;
    }
    
    @Override
    public Vector scale(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }
    
    @Override
    public Vector store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.x);
        floatBuffer.put(this.y);
        floatBuffer.put(this.z);
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("Vector3f[");
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
        sb.append(", ");
        sb.append(this.z);
        sb.append(']');
        return sb.toString();
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
        final Vector3f vector3f = (Vector3f)o;
        return this.x == vector3f.x && this.y == vector3f.y && this.z == vector3f.z;
    }
}
