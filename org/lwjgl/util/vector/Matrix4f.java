package org.lwjgl.util.vector;

import java.io.*;
import java.nio.*;

public class Matrix4f extends Matrix implements Serializable
{
    private static final long serialVersionUID = 1L;
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;
    
    public Matrix4f() {
        this.setIdentity();
    }
    
    public Matrix4f(final Matrix4f matrix4f) {
        this.load(matrix4f);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.m00).append(' ').append(this.m10).append(' ').append(this.m20).append(' ').append(this.m30).append('\n');
        sb.append(this.m01).append(' ').append(this.m11).append(' ').append(this.m21).append(' ').append(this.m31).append('\n');
        sb.append(this.m02).append(' ').append(this.m12).append(' ').append(this.m22).append(' ').append(this.m32).append('\n');
        sb.append(this.m03).append(' ').append(this.m13).append(' ').append(this.m23).append(' ').append(this.m33).append('\n');
        return sb.toString();
    }
    
    @Override
    public Matrix setIdentity() {
        return setIdentity(this);
    }
    
    public static Matrix4f setIdentity(final Matrix4f matrix4f) {
        matrix4f.m00 = 1.0f;
        matrix4f.m01 = 0.0f;
        matrix4f.m02 = 0.0f;
        matrix4f.m03 = 0.0f;
        matrix4f.m10 = 0.0f;
        matrix4f.m11 = 1.0f;
        matrix4f.m12 = 0.0f;
        matrix4f.m13 = 0.0f;
        matrix4f.m20 = 0.0f;
        matrix4f.m21 = 0.0f;
        matrix4f.m22 = 1.0f;
        matrix4f.m23 = 0.0f;
        matrix4f.m30 = 0.0f;
        matrix4f.m31 = 0.0f;
        matrix4f.m32 = 0.0f;
        matrix4f.m33 = 1.0f;
        return matrix4f;
    }
    
    @Override
    public Matrix setZero() {
        return setZero(this);
    }
    
    public static Matrix4f setZero(final Matrix4f matrix4f) {
        matrix4f.m00 = 0.0f;
        matrix4f.m01 = 0.0f;
        matrix4f.m02 = 0.0f;
        matrix4f.m03 = 0.0f;
        matrix4f.m10 = 0.0f;
        matrix4f.m11 = 0.0f;
        matrix4f.m12 = 0.0f;
        matrix4f.m13 = 0.0f;
        matrix4f.m20 = 0.0f;
        matrix4f.m21 = 0.0f;
        matrix4f.m22 = 0.0f;
        matrix4f.m23 = 0.0f;
        matrix4f.m30 = 0.0f;
        matrix4f.m31 = 0.0f;
        matrix4f.m32 = 0.0f;
        matrix4f.m33 = 0.0f;
        return matrix4f;
    }
    
    public Matrix4f load(final Matrix4f matrix4f) {
        return load(matrix4f, this);
    }
    
    public static Matrix4f load(final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        matrix4f2.m00 = matrix4f.m00;
        matrix4f2.m01 = matrix4f.m01;
        matrix4f2.m02 = matrix4f.m02;
        matrix4f2.m03 = matrix4f.m03;
        matrix4f2.m10 = matrix4f.m10;
        matrix4f2.m11 = matrix4f.m11;
        matrix4f2.m12 = matrix4f.m12;
        matrix4f2.m13 = matrix4f.m13;
        matrix4f2.m20 = matrix4f.m20;
        matrix4f2.m21 = matrix4f.m21;
        matrix4f2.m22 = matrix4f.m22;
        matrix4f2.m23 = matrix4f.m23;
        matrix4f2.m30 = matrix4f.m30;
        matrix4f2.m31 = matrix4f.m31;
        matrix4f2.m32 = matrix4f.m32;
        matrix4f2.m33 = matrix4f.m33;
        return matrix4f2;
    }
    
    @Override
    public Matrix load(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m02 = floatBuffer.get();
        this.m03 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        this.m12 = floatBuffer.get();
        this.m13 = floatBuffer.get();
        this.m20 = floatBuffer.get();
        this.m21 = floatBuffer.get();
        this.m22 = floatBuffer.get();
        this.m23 = floatBuffer.get();
        this.m30 = floatBuffer.get();
        this.m31 = floatBuffer.get();
        this.m32 = floatBuffer.get();
        this.m33 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix loadTranspose(final FloatBuffer floatBuffer) {
        this.m00 = floatBuffer.get();
        this.m10 = floatBuffer.get();
        this.m20 = floatBuffer.get();
        this.m30 = floatBuffer.get();
        this.m01 = floatBuffer.get();
        this.m11 = floatBuffer.get();
        this.m21 = floatBuffer.get();
        this.m31 = floatBuffer.get();
        this.m02 = floatBuffer.get();
        this.m12 = floatBuffer.get();
        this.m22 = floatBuffer.get();
        this.m32 = floatBuffer.get();
        this.m03 = floatBuffer.get();
        this.m13 = floatBuffer.get();
        this.m23 = floatBuffer.get();
        this.m33 = floatBuffer.get();
        return this;
    }
    
    @Override
    public Matrix store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m02);
        floatBuffer.put(this.m03);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m11);
        floatBuffer.put(this.m12);
        floatBuffer.put(this.m13);
        floatBuffer.put(this.m20);
        floatBuffer.put(this.m21);
        floatBuffer.put(this.m22);
        floatBuffer.put(this.m23);
        floatBuffer.put(this.m30);
        floatBuffer.put(this.m31);
        floatBuffer.put(this.m32);
        floatBuffer.put(this.m33);
        return this;
    }
    
    @Override
    public Matrix storeTranspose(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.m00);
        floatBuffer.put(this.m10);
        floatBuffer.put(this.m20);
        floatBuffer.put(this.m30);
        floatBuffer.put(this.m01);
        floatBuffer.put(this.m11);
        floatBuffer.put(this.m21);
        floatBuffer.put(this.m31);
        floatBuffer.put(this.m02);
        floatBuffer.put(this.m12);
        floatBuffer.put(this.m22);
        floatBuffer.put(this.m32);
        floatBuffer.put(this.m03);
        floatBuffer.put(this.m13);
        floatBuffer.put(this.m23);
        floatBuffer.put(this.m33);
        return this;
    }
    
    public Matrix store3f(final FloatBuffer floatBuffer) {
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
    
    public static Matrix4f add(final Matrix4f matrix4f, final Matrix4f matrix4f2, Matrix4f matrix4f3) {
        if (matrix4f3 == null) {
            matrix4f3 = new Matrix4f();
        }
        matrix4f3.m00 = matrix4f.m00 + matrix4f2.m00;
        matrix4f3.m01 = matrix4f.m01 + matrix4f2.m01;
        matrix4f3.m02 = matrix4f.m02 + matrix4f2.m02;
        matrix4f3.m03 = matrix4f.m03 + matrix4f2.m03;
        matrix4f3.m10 = matrix4f.m10 + matrix4f2.m10;
        matrix4f3.m11 = matrix4f.m11 + matrix4f2.m11;
        matrix4f3.m12 = matrix4f.m12 + matrix4f2.m12;
        matrix4f3.m13 = matrix4f.m13 + matrix4f2.m13;
        matrix4f3.m20 = matrix4f.m20 + matrix4f2.m20;
        matrix4f3.m21 = matrix4f.m21 + matrix4f2.m21;
        matrix4f3.m22 = matrix4f.m22 + matrix4f2.m22;
        matrix4f3.m23 = matrix4f.m23 + matrix4f2.m23;
        matrix4f3.m30 = matrix4f.m30 + matrix4f2.m30;
        matrix4f3.m31 = matrix4f.m31 + matrix4f2.m31;
        matrix4f3.m32 = matrix4f.m32 + matrix4f2.m32;
        matrix4f3.m33 = matrix4f.m33 + matrix4f2.m33;
        return matrix4f3;
    }
    
    public static Matrix4f sub(final Matrix4f matrix4f, final Matrix4f matrix4f2, Matrix4f matrix4f3) {
        if (matrix4f3 == null) {
            matrix4f3 = new Matrix4f();
        }
        matrix4f3.m00 = matrix4f.m00 - matrix4f2.m00;
        matrix4f3.m01 = matrix4f.m01 - matrix4f2.m01;
        matrix4f3.m02 = matrix4f.m02 - matrix4f2.m02;
        matrix4f3.m03 = matrix4f.m03 - matrix4f2.m03;
        matrix4f3.m10 = matrix4f.m10 - matrix4f2.m10;
        matrix4f3.m11 = matrix4f.m11 - matrix4f2.m11;
        matrix4f3.m12 = matrix4f.m12 - matrix4f2.m12;
        matrix4f3.m13 = matrix4f.m13 - matrix4f2.m13;
        matrix4f3.m20 = matrix4f.m20 - matrix4f2.m20;
        matrix4f3.m21 = matrix4f.m21 - matrix4f2.m21;
        matrix4f3.m22 = matrix4f.m22 - matrix4f2.m22;
        matrix4f3.m23 = matrix4f.m23 - matrix4f2.m23;
        matrix4f3.m30 = matrix4f.m30 - matrix4f2.m30;
        matrix4f3.m31 = matrix4f.m31 - matrix4f2.m31;
        matrix4f3.m32 = matrix4f.m32 - matrix4f2.m32;
        matrix4f3.m33 = matrix4f.m33 - matrix4f2.m33;
        return matrix4f3;
    }
    
    public static Matrix4f mul(final Matrix4f matrix4f, final Matrix4f matrix4f2, Matrix4f matrix4f3) {
        if (matrix4f3 == null) {
            matrix4f3 = new Matrix4f();
        }
        final float m00 = matrix4f.m00 * matrix4f2.m00 + matrix4f.m10 * matrix4f2.m01 + matrix4f.m20 * matrix4f2.m02 + matrix4f.m30 * matrix4f2.m03;
        final float m2 = matrix4f.m01 * matrix4f2.m00 + matrix4f.m11 * matrix4f2.m01 + matrix4f.m21 * matrix4f2.m02 + matrix4f.m31 * matrix4f2.m03;
        final float m3 = matrix4f.m02 * matrix4f2.m00 + matrix4f.m12 * matrix4f2.m01 + matrix4f.m22 * matrix4f2.m02 + matrix4f.m32 * matrix4f2.m03;
        final float m4 = matrix4f.m03 * matrix4f2.m00 + matrix4f.m13 * matrix4f2.m01 + matrix4f.m23 * matrix4f2.m02 + matrix4f.m33 * matrix4f2.m03;
        final float m5 = matrix4f.m00 * matrix4f2.m10 + matrix4f.m10 * matrix4f2.m11 + matrix4f.m20 * matrix4f2.m12 + matrix4f.m30 * matrix4f2.m13;
        final float m6 = matrix4f.m01 * matrix4f2.m10 + matrix4f.m11 * matrix4f2.m11 + matrix4f.m21 * matrix4f2.m12 + matrix4f.m31 * matrix4f2.m13;
        final float m7 = matrix4f.m02 * matrix4f2.m10 + matrix4f.m12 * matrix4f2.m11 + matrix4f.m22 * matrix4f2.m12 + matrix4f.m32 * matrix4f2.m13;
        final float m8 = matrix4f.m03 * matrix4f2.m10 + matrix4f.m13 * matrix4f2.m11 + matrix4f.m23 * matrix4f2.m12 + matrix4f.m33 * matrix4f2.m13;
        final float m9 = matrix4f.m00 * matrix4f2.m20 + matrix4f.m10 * matrix4f2.m21 + matrix4f.m20 * matrix4f2.m22 + matrix4f.m30 * matrix4f2.m23;
        final float m10 = matrix4f.m01 * matrix4f2.m20 + matrix4f.m11 * matrix4f2.m21 + matrix4f.m21 * matrix4f2.m22 + matrix4f.m31 * matrix4f2.m23;
        final float m11 = matrix4f.m02 * matrix4f2.m20 + matrix4f.m12 * matrix4f2.m21 + matrix4f.m22 * matrix4f2.m22 + matrix4f.m32 * matrix4f2.m23;
        final float m12 = matrix4f.m03 * matrix4f2.m20 + matrix4f.m13 * matrix4f2.m21 + matrix4f.m23 * matrix4f2.m22 + matrix4f.m33 * matrix4f2.m23;
        final float m13 = matrix4f.m00 * matrix4f2.m30 + matrix4f.m10 * matrix4f2.m31 + matrix4f.m20 * matrix4f2.m32 + matrix4f.m30 * matrix4f2.m33;
        final float m14 = matrix4f.m01 * matrix4f2.m30 + matrix4f.m11 * matrix4f2.m31 + matrix4f.m21 * matrix4f2.m32 + matrix4f.m31 * matrix4f2.m33;
        final float m15 = matrix4f.m02 * matrix4f2.m30 + matrix4f.m12 * matrix4f2.m31 + matrix4f.m22 * matrix4f2.m32 + matrix4f.m32 * matrix4f2.m33;
        final float m16 = matrix4f.m03 * matrix4f2.m30 + matrix4f.m13 * matrix4f2.m31 + matrix4f.m23 * matrix4f2.m32 + matrix4f.m33 * matrix4f2.m33;
        matrix4f3.m00 = m00;
        matrix4f3.m01 = m2;
        matrix4f3.m02 = m3;
        matrix4f3.m03 = m4;
        matrix4f3.m10 = m5;
        matrix4f3.m11 = m6;
        matrix4f3.m12 = m7;
        matrix4f3.m13 = m8;
        matrix4f3.m20 = m9;
        matrix4f3.m21 = m10;
        matrix4f3.m22 = m11;
        matrix4f3.m23 = m12;
        matrix4f3.m30 = m13;
        matrix4f3.m31 = m14;
        matrix4f3.m32 = m15;
        matrix4f3.m33 = m16;
        return matrix4f3;
    }
    
    public static Vector4f transform(final Matrix4f matrix4f, final Vector4f vector4f, Vector4f vector4f2) {
        if (vector4f2 == null) {
            vector4f2 = new Vector4f();
        }
        final float x = matrix4f.m00 * vector4f.x + matrix4f.m10 * vector4f.y + matrix4f.m20 * vector4f.z + matrix4f.m30 * vector4f.w;
        final float y = matrix4f.m01 * vector4f.x + matrix4f.m11 * vector4f.y + matrix4f.m21 * vector4f.z + matrix4f.m31 * vector4f.w;
        final float z = matrix4f.m02 * vector4f.x + matrix4f.m12 * vector4f.y + matrix4f.m22 * vector4f.z + matrix4f.m32 * vector4f.w;
        final float w = matrix4f.m03 * vector4f.x + matrix4f.m13 * vector4f.y + matrix4f.m23 * vector4f.z + matrix4f.m33 * vector4f.w;
        vector4f2.x = x;
        vector4f2.y = y;
        vector4f2.z = z;
        vector4f2.w = w;
        return vector4f2;
    }
    
    @Override
    public Matrix transpose() {
        return this.transpose(this);
    }
    
    public Matrix4f translate(final Vector2f vector2f) {
        return this.translate(vector2f, this);
    }
    
    public Matrix4f translate(final Vector3f vector3f) {
        return this.translate(vector3f, this);
    }
    
    public Matrix4f scale(final Vector3f vector3f) {
        return scale(vector3f, this, this);
    }
    
    public static Matrix4f scale(final Vector3f vector3f, final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        matrix4f2.m00 = matrix4f.m00 * vector3f.x;
        matrix4f2.m01 = matrix4f.m01 * vector3f.x;
        matrix4f2.m02 = matrix4f.m02 * vector3f.x;
        matrix4f2.m03 = matrix4f.m03 * vector3f.x;
        matrix4f2.m10 = matrix4f.m10 * vector3f.y;
        matrix4f2.m11 = matrix4f.m11 * vector3f.y;
        matrix4f2.m12 = matrix4f.m12 * vector3f.y;
        matrix4f2.m13 = matrix4f.m13 * vector3f.y;
        matrix4f2.m20 = matrix4f.m20 * vector3f.z;
        matrix4f2.m21 = matrix4f.m21 * vector3f.z;
        matrix4f2.m22 = matrix4f.m22 * vector3f.z;
        matrix4f2.m23 = matrix4f.m23 * vector3f.z;
        return matrix4f2;
    }
    
    public Matrix4f rotate(final float n, final Vector3f vector3f) {
        return this.rotate(n, vector3f, this);
    }
    
    public Matrix4f rotate(final float n, final Vector3f vector3f, final Matrix4f matrix4f) {
        return rotate(n, vector3f, this, matrix4f);
    }
    
    public static Matrix4f rotate(final float n, final Vector3f vector3f, final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        final float n2 = (float)Math.cos(n);
        final float n3 = (float)Math.sin(n);
        final float n4 = 1.0f - n2;
        final float n5 = vector3f.x * vector3f.y;
        final float n6 = vector3f.y * vector3f.z;
        final float n7 = vector3f.x * vector3f.z;
        final float n8 = vector3f.x * n3;
        final float n9 = vector3f.y * n3;
        final float n10 = vector3f.z * n3;
        final float n11 = vector3f.x * vector3f.x * n4 + n2;
        final float n12 = n5 * n4 + n10;
        final float n13 = n7 * n4 - n9;
        final float n14 = n5 * n4 - n10;
        final float n15 = vector3f.y * vector3f.y * n4 + n2;
        final float n16 = n6 * n4 + n8;
        final float n17 = n7 * n4 + n9;
        final float n18 = n6 * n4 - n8;
        final float n19 = vector3f.z * vector3f.z * n4 + n2;
        final float m00 = matrix4f.m00 * n11 + matrix4f.m10 * n12 + matrix4f.m20 * n13;
        final float m2 = matrix4f.m01 * n11 + matrix4f.m11 * n12 + matrix4f.m21 * n13;
        final float m3 = matrix4f.m02 * n11 + matrix4f.m12 * n12 + matrix4f.m22 * n13;
        final float m4 = matrix4f.m03 * n11 + matrix4f.m13 * n12 + matrix4f.m23 * n13;
        final float m5 = matrix4f.m00 * n14 + matrix4f.m10 * n15 + matrix4f.m20 * n16;
        final float m6 = matrix4f.m01 * n14 + matrix4f.m11 * n15 + matrix4f.m21 * n16;
        final float m7 = matrix4f.m02 * n14 + matrix4f.m12 * n15 + matrix4f.m22 * n16;
        final float m8 = matrix4f.m03 * n14 + matrix4f.m13 * n15 + matrix4f.m23 * n16;
        matrix4f2.m20 = matrix4f.m00 * n17 + matrix4f.m10 * n18 + matrix4f.m20 * n19;
        matrix4f2.m21 = matrix4f.m01 * n17 + matrix4f.m11 * n18 + matrix4f.m21 * n19;
        matrix4f2.m22 = matrix4f.m02 * n17 + matrix4f.m12 * n18 + matrix4f.m22 * n19;
        matrix4f2.m23 = matrix4f.m03 * n17 + matrix4f.m13 * n18 + matrix4f.m23 * n19;
        matrix4f2.m00 = m00;
        matrix4f2.m01 = m2;
        matrix4f2.m02 = m3;
        matrix4f2.m03 = m4;
        matrix4f2.m10 = m5;
        matrix4f2.m11 = m6;
        matrix4f2.m12 = m7;
        matrix4f2.m13 = m8;
        return matrix4f2;
    }
    
    public Matrix4f translate(final Vector3f vector3f, final Matrix4f matrix4f) {
        return translate(vector3f, this, matrix4f);
    }
    
    public static Matrix4f translate(final Vector3f vector3f, final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        final Matrix4f matrix4f3 = matrix4f2;
        matrix4f3.m30 += matrix4f.m00 * vector3f.x + matrix4f.m10 * vector3f.y + matrix4f.m20 * vector3f.z;
        final Matrix4f matrix4f4 = matrix4f2;
        matrix4f4.m31 += matrix4f.m01 * vector3f.x + matrix4f.m11 * vector3f.y + matrix4f.m21 * vector3f.z;
        final Matrix4f matrix4f5 = matrix4f2;
        matrix4f5.m32 += matrix4f.m02 * vector3f.x + matrix4f.m12 * vector3f.y + matrix4f.m22 * vector3f.z;
        final Matrix4f matrix4f6 = matrix4f2;
        matrix4f6.m33 += matrix4f.m03 * vector3f.x + matrix4f.m13 * vector3f.y + matrix4f.m23 * vector3f.z;
        return matrix4f2;
    }
    
    public Matrix4f translate(final Vector2f vector2f, final Matrix4f matrix4f) {
        return translate(vector2f, this, matrix4f);
    }
    
    public static Matrix4f translate(final Vector2f vector2f, final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        final Matrix4f matrix4f3 = matrix4f2;
        matrix4f3.m30 += matrix4f.m00 * vector2f.x + matrix4f.m10 * vector2f.y;
        final Matrix4f matrix4f4 = matrix4f2;
        matrix4f4.m31 += matrix4f.m01 * vector2f.x + matrix4f.m11 * vector2f.y;
        final Matrix4f matrix4f5 = matrix4f2;
        matrix4f5.m32 += matrix4f.m02 * vector2f.x + matrix4f.m12 * vector2f.y;
        final Matrix4f matrix4f6 = matrix4f2;
        matrix4f6.m33 += matrix4f.m03 * vector2f.x + matrix4f.m13 * vector2f.y;
        return matrix4f2;
    }
    
    public Matrix4f transpose(final Matrix4f matrix4f) {
        return transpose(this, matrix4f);
    }
    
    public static Matrix4f transpose(final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        final float m00 = matrix4f.m00;
        final float m2 = matrix4f.m10;
        final float m3 = matrix4f.m20;
        final float m4 = matrix4f.m30;
        final float m5 = matrix4f.m01;
        final float m6 = matrix4f.m11;
        final float m7 = matrix4f.m21;
        final float m8 = matrix4f.m31;
        final float m9 = matrix4f.m02;
        final float m10 = matrix4f.m12;
        final float m11 = matrix4f.m22;
        final float m12 = matrix4f.m32;
        final float m13 = matrix4f.m03;
        final float m14 = matrix4f.m13;
        final float m15 = matrix4f.m23;
        final float m16 = matrix4f.m33;
        matrix4f2.m00 = m00;
        matrix4f2.m01 = m2;
        matrix4f2.m02 = m3;
        matrix4f2.m03 = m4;
        matrix4f2.m10 = m5;
        matrix4f2.m11 = m6;
        matrix4f2.m12 = m7;
        matrix4f2.m13 = m8;
        matrix4f2.m20 = m9;
        matrix4f2.m21 = m10;
        matrix4f2.m22 = m11;
        matrix4f2.m23 = m12;
        matrix4f2.m30 = m13;
        matrix4f2.m31 = m14;
        matrix4f2.m32 = m15;
        matrix4f2.m33 = m16;
        return matrix4f2;
    }
    
    @Override
    public float determinant() {
        return this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33) - this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33) + this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33) - this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
    }
    
    private static float determinant3x3(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        return n * (n5 * n9 - n6 * n8) + n2 * (n6 * n7 - n4 * n9) + n3 * (n4 * n8 - n5 * n7);
    }
    
    @Override
    public Matrix invert() {
        return invert(this, this);
    }
    
    public static Matrix4f invert(final Matrix4f matrix4f, Matrix4f matrix4f2) {
        final float determinant = matrix4f.determinant();
        if (determinant != 0.0f) {
            if (matrix4f2 == null) {
                matrix4f2 = new Matrix4f();
            }
            final float n = 1.0f / determinant;
            final float determinant3x3 = determinant3x3(matrix4f.m11, matrix4f.m12, matrix4f.m13, matrix4f.m21, matrix4f.m22, matrix4f.m23, matrix4f.m31, matrix4f.m32, matrix4f.m33);
            final float n2 = -determinant3x3(matrix4f.m10, matrix4f.m12, matrix4f.m13, matrix4f.m20, matrix4f.m22, matrix4f.m23, matrix4f.m30, matrix4f.m32, matrix4f.m33);
            final float determinant3x4 = determinant3x3(matrix4f.m10, matrix4f.m11, matrix4f.m13, matrix4f.m20, matrix4f.m21, matrix4f.m23, matrix4f.m30, matrix4f.m31, matrix4f.m33);
            final float n3 = -determinant3x3(matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22, matrix4f.m30, matrix4f.m31, matrix4f.m32);
            final float n4 = -determinant3x3(matrix4f.m01, matrix4f.m02, matrix4f.m03, matrix4f.m21, matrix4f.m22, matrix4f.m23, matrix4f.m31, matrix4f.m32, matrix4f.m33);
            final float determinant3x5 = determinant3x3(matrix4f.m00, matrix4f.m02, matrix4f.m03, matrix4f.m20, matrix4f.m22, matrix4f.m23, matrix4f.m30, matrix4f.m32, matrix4f.m33);
            final float n5 = -determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m03, matrix4f.m20, matrix4f.m21, matrix4f.m23, matrix4f.m30, matrix4f.m31, matrix4f.m33);
            final float determinant3x6 = determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m20, matrix4f.m21, matrix4f.m22, matrix4f.m30, matrix4f.m31, matrix4f.m32);
            final float determinant3x7 = determinant3x3(matrix4f.m01, matrix4f.m02, matrix4f.m03, matrix4f.m11, matrix4f.m12, matrix4f.m13, matrix4f.m31, matrix4f.m32, matrix4f.m33);
            final float n6 = -determinant3x3(matrix4f.m00, matrix4f.m02, matrix4f.m03, matrix4f.m10, matrix4f.m12, matrix4f.m13, matrix4f.m30, matrix4f.m32, matrix4f.m33);
            final float determinant3x8 = determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m03, matrix4f.m10, matrix4f.m11, matrix4f.m13, matrix4f.m30, matrix4f.m31, matrix4f.m33);
            final float n7 = -determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m30, matrix4f.m31, matrix4f.m32);
            final float n8 = -determinant3x3(matrix4f.m01, matrix4f.m02, matrix4f.m03, matrix4f.m11, matrix4f.m12, matrix4f.m13, matrix4f.m21, matrix4f.m22, matrix4f.m23);
            final float determinant3x9 = determinant3x3(matrix4f.m00, matrix4f.m02, matrix4f.m03, matrix4f.m10, matrix4f.m12, matrix4f.m13, matrix4f.m20, matrix4f.m22, matrix4f.m23);
            final float n9 = -determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m03, matrix4f.m10, matrix4f.m11, matrix4f.m13, matrix4f.m20, matrix4f.m21, matrix4f.m23);
            final float determinant3x10 = determinant3x3(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22);
            matrix4f2.m00 = determinant3x3 * n;
            matrix4f2.m11 = determinant3x5 * n;
            matrix4f2.m22 = determinant3x8 * n;
            matrix4f2.m33 = determinant3x10 * n;
            matrix4f2.m01 = n4 * n;
            matrix4f2.m10 = n2 * n;
            matrix4f2.m20 = determinant3x4 * n;
            matrix4f2.m02 = determinant3x7 * n;
            matrix4f2.m12 = n6 * n;
            matrix4f2.m21 = n5 * n;
            matrix4f2.m03 = n8 * n;
            matrix4f2.m30 = n3 * n;
            matrix4f2.m13 = determinant3x9 * n;
            matrix4f2.m31 = determinant3x6 * n;
            matrix4f2.m32 = n7 * n;
            matrix4f2.m23 = n9 * n;
            return matrix4f2;
        }
        return null;
    }
    
    @Override
    public Matrix negate() {
        return this.negate(this);
    }
    
    public Matrix4f negate(final Matrix4f matrix4f) {
        return negate(this, matrix4f);
    }
    
    public static Matrix4f negate(final Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (matrix4f2 == null) {
            matrix4f2 = new Matrix4f();
        }
        matrix4f2.m00 = -matrix4f.m00;
        matrix4f2.m01 = -matrix4f.m01;
        matrix4f2.m02 = -matrix4f.m02;
        matrix4f2.m03 = -matrix4f.m03;
        matrix4f2.m10 = -matrix4f.m10;
        matrix4f2.m11 = -matrix4f.m11;
        matrix4f2.m12 = -matrix4f.m12;
        matrix4f2.m13 = -matrix4f.m13;
        matrix4f2.m20 = -matrix4f.m20;
        matrix4f2.m21 = -matrix4f.m21;
        matrix4f2.m22 = -matrix4f.m22;
        matrix4f2.m23 = -matrix4f.m23;
        matrix4f2.m30 = -matrix4f.m30;
        matrix4f2.m31 = -matrix4f.m31;
        matrix4f2.m32 = -matrix4f.m32;
        matrix4f2.m33 = -matrix4f.m33;
        return matrix4f2;
    }
}
