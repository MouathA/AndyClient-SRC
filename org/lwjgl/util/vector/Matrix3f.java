package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Matrix3f extends Matrix implements Serializable
{
    private static final long serialVersionUID = 1L;
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;
    
    public Matrix3f() {
        this.setIdentity();
    }
    
    public Matrix3f load(final Matrix3f matrix3f) {
        return load(matrix3f, this);
    }
    
    public static Matrix3f load(final Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (matrix3f2 == null) {
            matrix3f2 = new Matrix3f();
        }
        matrix3f2.m00 = matrix3f.m00;
        matrix3f2.m10 = matrix3f.m10;
        matrix3f2.m20 = matrix3f.m20;
        matrix3f2.m01 = matrix3f.m01;
        matrix3f2.m11 = matrix3f.m11;
        matrix3f2.m21 = matrix3f.m21;
        matrix3f2.m02 = matrix3f.m02;
        matrix3f2.m12 = matrix3f.m12;
        matrix3f2.m22 = matrix3f.m22;
        return matrix3f2;
    }
    
    @Override
    public Matrix load(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m02 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        this.m12 = floatBuffer.get();
        this.m20 = floatBuffer.get();
        this.m21 = floatBuffer.get();
        this.m22 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix loadTranspose(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m20 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        this.m21 = floatBuffer.get();
        this.m02 = floatBuffer.get();
        this.m12 = floatBuffer.get();
        this.m22 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m02);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m11);
        floatBuffer.put(this.m12);
        floatBuffer.put(this.m20);
        floatBuffer.put(this.m21);
        floatBuffer.put(this.m22);
        return this;
    }
    
    @Override
    public Matrix storeTranspose(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m20);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m11);
        floatBuffer.put(this.m21);
        floatBuffer.put(this.m02);
        floatBuffer.put(this.m12);
        floatBuffer.put(this.m22);
        return this;
    }
    
    public static Matrix3f add(final Matrix3f matrix3f, final Matrix3f matrix3f2, Matrix3f matrix3f3) {
        if (matrix3f3 == null) {
            matrix3f3 = new Matrix3f();
        }
        matrix3f3.m00 = matrix3f.m00 + matrix3f2.m00;
        matrix3f3.m01 = matrix3f.m01 + matrix3f2.m01;
        matrix3f3.m02 = matrix3f.m02 + matrix3f2.m02;
        matrix3f3.m10 = matrix3f.m10 + matrix3f2.m10;
        matrix3f3.m11 = matrix3f.m11 + matrix3f2.m11;
        matrix3f3.m12 = matrix3f.m12 + matrix3f2.m12;
        matrix3f3.m20 = matrix3f.m20 + matrix3f2.m20;
        matrix3f3.m21 = matrix3f.m21 + matrix3f2.m21;
        matrix3f3.m22 = matrix3f.m22 + matrix3f2.m22;
        return matrix3f3;
    }
    
    public static Matrix3f sub(final Matrix3f matrix3f, final Matrix3f matrix3f2, Matrix3f matrix3f3) {
        if (matrix3f3 == null) {
            matrix3f3 = new Matrix3f();
        }
        matrix3f3.m00 = matrix3f.m00 - matrix3f2.m00;
        matrix3f3.m01 = matrix3f.m01 - matrix3f2.m01;
        matrix3f3.m02 = matrix3f.m02 - matrix3f2.m02;
        matrix3f3.m10 = matrix3f.m10 - matrix3f2.m10;
        matrix3f3.m11 = matrix3f.m11 - matrix3f2.m11;
        matrix3f3.m12 = matrix3f.m12 - matrix3f2.m12;
        matrix3f3.m20 = matrix3f.m20 - matrix3f2.m20;
        matrix3f3.m21 = matrix3f.m21 - matrix3f2.m21;
        matrix3f3.m22 = matrix3f.m22 - matrix3f2.m22;
        return matrix3f3;
    }
    
    public static Matrix3f mul(final Matrix3f matrix3f, final Matrix3f matrix3f2, Matrix3f matrix3f3) {
        if (matrix3f3 == null) {
            matrix3f3 = new Matrix3f();
        }
        final float m00 = matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m01 + matrix3f.m20 * matrix3f2.m02;
        final float m2 = matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m02;
        final float m3 = matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02;
        final float m4 = matrix3f.m00 * matrix3f2.m10 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m12;
        final float m5 = matrix3f.m01 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m12;
        final float m6 = matrix3f.m02 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12;
        final float m7 = matrix3f.m00 * matrix3f2.m20 + matrix3f.m10 * matrix3f2.m21 + matrix3f.m20 * matrix3f2.m22;
        final float m8 = matrix3f.m01 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m21 * matrix3f2.m22;
        final float m9 = matrix3f.m02 * matrix3f2.m20 + matrix3f.m12 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22;
        matrix3f3.m00 = m00;
        matrix3f3.m01 = m2;
        matrix3f3.m02 = m3;
        matrix3f3.m10 = m4;
        matrix3f3.m11 = m5;
        matrix3f3.m12 = m6;
        matrix3f3.m20 = m7;
        matrix3f3.m21 = m8;
        matrix3f3.m22 = m9;
        return matrix3f3;
    }
    
    public static Vector3f transform(final Matrix3f matrix3f, final Vector3f vector3f, Vector3f vector3f2) {
        if (vector3f2 == null) {
            vector3f2 = new Vector3f();
        }
        final float x = matrix3f.m00 * vector3f.x + matrix3f.m10 * vector3f.y + matrix3f.m20 * vector3f.z;
        final float y = matrix3f.m01 * vector3f.x + matrix3f.m11 * vector3f.y + matrix3f.m21 * vector3f.z;
        final float z = matrix3f.m02 * vector3f.x + matrix3f.m12 * vector3f.y + matrix3f.m22 * vector3f.z;
        vector3f2.x = x;
        vector3f2.y = y;
        vector3f2.z = z;
        return vector3f2;
    }
    
    @Override
    public Matrix transpose() {
        return transpose(this, this);
    }
    
    public Matrix3f transpose(final Matrix3f matrix3f) {
        return transpose(this, matrix3f);
    }
    
    public static Matrix3f transpose(final Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (matrix3f2 == null) {
            matrix3f2 = new Matrix3f();
        }
        final float m00 = matrix3f.m00;
        final float m2 = matrix3f.m10;
        final float m3 = matrix3f.m20;
        final float m4 = matrix3f.m01;
        final float m5 = matrix3f.m11;
        final float m6 = matrix3f.m21;
        final float m7 = matrix3f.m02;
        final float m8 = matrix3f.m12;
        final float m9 = matrix3f.m22;
        matrix3f2.m00 = m00;
        matrix3f2.m01 = m2;
        matrix3f2.m02 = m3;
        matrix3f2.m10 = m4;
        matrix3f2.m11 = m5;
        matrix3f2.m12 = m6;
        matrix3f2.m20 = m7;
        matrix3f2.m21 = m8;
        matrix3f2.m22 = m9;
        return matrix3f2;
    }
    
    @Override
    public float determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.m00).append(' ').append(this.m10).append(' ').append(this.m20).append(' ').append('\n');
        sb.append(this.m01).append(' ').append(this.m11).append(' ').append(this.m21).append(' ').append('\n');
        sb.append(this.m02).append(' ').append(this.m12).append(' ').append(this.m22).append(' ').append('\n');
        return sb.toString();
    }
    
    @Override
    public Matrix invert() {
        return invert(this, this);
    }
    
    public static Matrix3f invert(final Matrix3f matrix3f, Matrix3f matrix3f2) {
        final float determinant = matrix3f.determinant();
        if (determinant != 0.0f) {
            if (matrix3f2 == null) {
                matrix3f2 = new Matrix3f();
            }
            final float n = 1.0f / determinant;
            final float n2 = matrix3f.m11 * matrix3f.m22 - matrix3f.m12 * matrix3f.m21;
            final float n3 = -matrix3f.m10 * matrix3f.m22 + matrix3f.m12 * matrix3f.m20;
            final float n4 = matrix3f.m10 * matrix3f.m21 - matrix3f.m11 * matrix3f.m20;
            final float n5 = -matrix3f.m01 * matrix3f.m22 + matrix3f.m02 * matrix3f.m21;
            final float n6 = matrix3f.m00 * matrix3f.m22 - matrix3f.m02 * matrix3f.m20;
            final float n7 = -matrix3f.m00 * matrix3f.m21 + matrix3f.m01 * matrix3f.m20;
            final float n8 = matrix3f.m01 * matrix3f.m12 - matrix3f.m02 * matrix3f.m11;
            final float n9 = -matrix3f.m00 * matrix3f.m12 + matrix3f.m02 * matrix3f.m10;
            final float n10 = matrix3f.m00 * matrix3f.m11 - matrix3f.m01 * matrix3f.m10;
            matrix3f2.m00 = n2 * n;
            matrix3f2.m11 = n6 * n;
            matrix3f2.m22 = n10 * n;
            matrix3f2.m01 = n5 * n;
            matrix3f2.m10 = n3 * n;
            matrix3f2.m20 = n4 * n;
            matrix3f2.m02 = n8 * n;
            matrix3f2.m12 = n9 * n;
            matrix3f2.m21 = n7 * n;
            return matrix3f2;
        }
        return null;
    }
    
    @Override
    public Matrix negate() {
        return this.negate(this);
    }
    
    public Matrix3f negate(final Matrix3f matrix3f) {
        return negate(this, matrix3f);
    }
    
    public static Matrix3f negate(final Matrix3f matrix3f, Matrix3f matrix3f2) {
        if (matrix3f2 == null) {
            matrix3f2 = new Matrix3f();
        }
        matrix3f2.m00 = -matrix3f.m00;
        matrix3f2.m01 = -matrix3f.m02;
        matrix3f2.m02 = -matrix3f.m01;
        matrix3f2.m10 = -matrix3f.m10;
        matrix3f2.m11 = -matrix3f.m12;
        matrix3f2.m12 = -matrix3f.m11;
        matrix3f2.m20 = -matrix3f.m20;
        matrix3f2.m21 = -matrix3f.m22;
        matrix3f2.m22 = -matrix3f.m21;
        return matrix3f2;
    }
    
    @Override
    public Matrix setIdentity() {
        return setIdentity(this);
    }
    
    public static Matrix3f setIdentity(final Matrix3f matrix3f) {
        matrix3f.m00 = 1.0f;
        matrix3f.m01 = 0.0f;
        matrix3f.m02 = 0.0f;
        matrix3f.m10 = 0.0f;
        matrix3f.m11 = 1.0f;
        matrix3f.m12 = 0.0f;
        matrix3f.m20 = 0.0f;
        matrix3f.m21 = 0.0f;
        matrix3f.m22 = 1.0f;
        return matrix3f;
    }
    
    @Override
    public Matrix setZero() {
        return setZero(this);
    }
    
    public static Matrix3f setZero(final Matrix3f matrix3f) {
        matrix3f.m00 = 0.0f;
        matrix3f.m01 = 0.0f;
        matrix3f.m02 = 0.0f;
        matrix3f.m10 = 0.0f;
        matrix3f.m11 = 0.0f;
        matrix3f.m12 = 0.0f;
        matrix3f.m20 = 0.0f;
        matrix3f.m21 = 0.0f;
        matrix3f.m22 = 0.0f;
        return matrix3f;
    }
}
