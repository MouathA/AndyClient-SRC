package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Matrix2f extends Matrix implements Serializable
{
    private static final long serialVersionUID = 1L;
    public float m00;
    public float m01;
    public float m10;
    public float m11;
    
    public Matrix2f() {
        this.setIdentity();
    }
    
    public Matrix2f(final Matrix2f matrix2f) {
        this.load(matrix2f);
    }
    
    public Matrix2f load(final Matrix2f matrix2f) {
        return load(matrix2f, this);
    }
    
    public static Matrix2f load(final Matrix2f matrix2f, Matrix2f matrix2f2) {
        if (matrix2f2 == null) {
            matrix2f2 = new Matrix2f();
        }
        matrix2f2.m00 = matrix2f.m00;
        matrix2f2.m01 = matrix2f.m01;
        matrix2f2.m10 = matrix2f.m10;
        matrix2f2.m11 = matrix2f.m11;
        return matrix2f2;
    }
    
    @Override
    public Matrix load(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix loadTranspose(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m11);
        return this;
    }
    
    @Override
    public Matrix storeTranspose(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m11);
        return this;
    }
    
    public static Matrix2f add(final Matrix2f matrix2f, final Matrix2f matrix2f2, Matrix2f matrix2f3) {
        if (matrix2f3 == null) {
            matrix2f3 = new Matrix2f();
        }
        matrix2f3.m00 = matrix2f.m00 + matrix2f2.m00;
        matrix2f3.m01 = matrix2f.m01 + matrix2f2.m01;
        matrix2f3.m10 = matrix2f.m10 + matrix2f2.m10;
        matrix2f3.m11 = matrix2f.m11 + matrix2f2.m11;
        return matrix2f3;
    }
    
    public static Matrix2f sub(final Matrix2f matrix2f, final Matrix2f matrix2f2, Matrix2f matrix2f3) {
        if (matrix2f3 == null) {
            matrix2f3 = new Matrix2f();
        }
        matrix2f3.m00 = matrix2f.m00 - matrix2f2.m00;
        matrix2f3.m01 = matrix2f.m01 - matrix2f2.m01;
        matrix2f3.m10 = matrix2f.m10 - matrix2f2.m10;
        matrix2f3.m11 = matrix2f.m11 - matrix2f2.m11;
        return matrix2f3;
    }
    
    public static Matrix2f mul(final Matrix2f matrix2f, final Matrix2f matrix2f2, Matrix2f matrix2f3) {
        if (matrix2f3 == null) {
            matrix2f3 = new Matrix2f();
        }
        final float m00 = matrix2f.m00 * matrix2f2.m00 + matrix2f.m10 * matrix2f2.m01;
        final float m2 = matrix2f.m01 * matrix2f2.m00 + matrix2f.m11 * matrix2f2.m01;
        final float m3 = matrix2f.m00 * matrix2f2.m10 + matrix2f.m10 * matrix2f2.m11;
        final float m4 = matrix2f.m01 * matrix2f2.m10 + matrix2f.m11 * matrix2f2.m11;
        matrix2f3.m00 = m00;
        matrix2f3.m01 = m2;
        matrix2f3.m10 = m3;
        matrix2f3.m11 = m4;
        return matrix2f3;
    }
    
    public static Vector2f transform(final Matrix2f matrix2f, final Vector2f vector2f, Vector2f vector2f2) {
        if (vector2f2 == null) {
            vector2f2 = new Vector2f();
        }
        final float x = matrix2f.m00 * vector2f.x + matrix2f.m10 * vector2f.y;
        final float y = matrix2f.m01 * vector2f.x + matrix2f.m11 * vector2f.y;
        vector2f2.x = x;
        vector2f2.y = y;
        return vector2f2;
    }
    
    @Override
    public Matrix transpose() {
        return this.transpose(this);
    }
    
    public Matrix2f transpose(final Matrix2f matrix2f) {
        return transpose(this, matrix2f);
    }
    
    public static Matrix2f transpose(final Matrix2f matrix2f, Matrix2f matrix2f2) {
        if (matrix2f2 == null) {
            matrix2f2 = new Matrix2f();
        }
        final float m10 = matrix2f.m10;
        final float m11 = matrix2f.m01;
        matrix2f2.m01 = m10;
        matrix2f2.m10 = m11;
        return matrix2f2;
    }
    
    @Override
    public Matrix invert() {
        return invert(this, this);
    }
    
    public static Matrix2f invert(final Matrix2f matrix2f, Matrix2f matrix2f2) {
        final float determinant = matrix2f.determinant();
        if (determinant != 0.0f) {
            if (matrix2f2 == null) {
                matrix2f2 = new Matrix2f();
            }
            final float n = 1.0f / determinant;
            final float m00 = matrix2f.m11 * n;
            final float m2 = -matrix2f.m01 * n;
            final float m3 = matrix2f.m00 * n;
            final float m4 = -matrix2f.m10 * n;
            matrix2f2.m00 = m00;
            matrix2f2.m01 = m2;
            matrix2f2.m10 = m4;
            matrix2f2.m11 = m3;
            return matrix2f2;
        }
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.m00).append(' ').append(this.m10).append(' ').append('\n');
        sb.append(this.m01).append(' ').append(this.m11).append(' ').append('\n');
        return sb.toString();
    }
    
    @Override
    public Matrix negate() {
        return this.negate(this);
    }
    
    public Matrix2f negate(final Matrix2f matrix2f) {
        return negate(this, matrix2f);
    }
    
    public static Matrix2f negate(final Matrix2f matrix2f, Matrix2f matrix2f2) {
        if (matrix2f2 == null) {
            matrix2f2 = new Matrix2f();
        }
        matrix2f2.m00 = -matrix2f.m00;
        matrix2f2.m01 = -matrix2f.m01;
        matrix2f2.m10 = -matrix2f.m10;
        matrix2f2.m11 = -matrix2f.m11;
        return matrix2f2;
    }
    
    @Override
    public Matrix setIdentity() {
        return setIdentity(this);
    }
    
    public static Matrix2f setIdentity(final Matrix2f matrix2f) {
        matrix2f.m00 = 1.0f;
        matrix2f.m01 = 0.0f;
        matrix2f.m10 = 0.0f;
        matrix2f.m11 = 1.0f;
        return matrix2f;
    }
    
    @Override
    public Matrix setZero() {
        return setZero(this);
    }
    
    public static Matrix2f setZero(final Matrix2f matrix2f) {
        matrix2f.m00 = 0.0f;
        matrix2f.m01 = 0.0f;
        matrix2f.m10 = 0.0f;
        matrix2f.m11 = 0.0f;
        return matrix2f;
    }
    
    @Override
    public float determinant() {
        return this.m00 * this.m11 - this.m01 * this.m10;
    }
}
