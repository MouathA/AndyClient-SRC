package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Vector2f extends Vector implements Serializable, ReadableVector2f, WritableVector2f
{
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    
    public Vector2f() {
    }
    
    public Vector2f(final ReadableVector2f readableVector2f) {
        this.set(readableVector2f);
    }
    
    public Vector2f(final float n, final float n2) {
        this.set(n, n2);
    }
    
    public void set(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2f set(final ReadableVector2f readableVector2f) {
        this.x = readableVector2f.getX();
        this.y = readableVector2f.getY();
        return this;
    }
    
    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }
    
    public Vector2f translate(final float n, final float n2) {
        this.x += n;
        this.y += n2;
        return this;
    }
    
    @Override
    public Vector negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }
    
    public Vector2f negate(Vector2f vector2f) {
        if (vector2f == null) {
            vector2f = new Vector2f();
        }
        vector2f.x = -this.x;
        vector2f.y = -this.y;
        return vector2f;
    }
    
    public Vector2f normalise(Vector2f vector2f) {
        final float length = this.length();
        if (vector2f == null) {
            vector2f = new Vector2f(this.x / length, this.y / length);
        }
        else {
            vector2f.set(this.x / length, this.y / length);
        }
        return vector2f;
    }
    
    public static float dot(final Vector2f vector2f, final Vector2f vector2f2) {
        return vector2f.x * vector2f2.x + vector2f.y * vector2f2.y;
    }
    
    public static float angle(final Vector2f vector2f, final Vector2f vector2f2) {
        float n = dot(vector2f, vector2f2) / (vector2f.length() * vector2f2.length());
        if (n < -1.0f) {
            n = -1.0f;
        }
        else if (n > 1.0f) {
            n = 1.0f;
        }
        return (float)Math.acos(n);
    }
    
    public static Vector2f add(final Vector2f vector2f, final Vector2f vector2f2, final Vector2f vector2f3) {
        if (vector2f3 == null) {
            return new Vector2f(vector2f.x + vector2f2.x, vector2f.y + vector2f2.y);
        }
        vector2f3.set(vector2f.x + vector2f2.x, vector2f.y + vector2f2.y);
        return vector2f3;
    }
    
    public static Vector2f sub(final Vector2f vector2f, final Vector2f vector2f2, final Vector2f vector2f3) {
        if (vector2f3 == null) {
            return new Vector2f(vector2f.x - vector2f2.x, vector2f.y - vector2f2.y);
        }
        vector2f3.set(vector2f.x - vector2f2.x, vector2f.y - vector2f2.y);
        return vector2f3;
    }
    
    @Override
    public Vector store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.x);
        floatBuffer.put(this.y);
        return this;
    }
    
    @Override
    public Vector load(final FloatBuffer floatBuffer) {
        this.x = floatBuffer.get();
        this.y = floatBuffer.get();
        return this;
    }
    
    @Override
    public Vector scale(final float n) {
        this.x *= n;
        this.y *= n;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("Vector2f[");
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
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
        final Vector2f vector2f = (Vector2f)o;
        return this.x == vector2f.x && this.y == vector2f.y;
    }
}
