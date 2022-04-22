package javax.vecmath;

import java.io.*;

public class Matrix4f implements Serializable
{
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
    
    public Matrix4f(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14, final float n15, final float n16) {
        this.set(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16);
    }
    
    public Matrix4f(final float[] array) {
        this.set(array);
    }
    
    public Matrix4f(final Quat4f quat4f, final Vector3f vector3f, final float n) {
        this.set(quat4f, vector3f, n);
    }
    
    public Matrix4f(final Matrix4d matrix4d) {
        this.set(matrix4d);
    }
    
    public Matrix4f(final Matrix4f matrix4f) {
        this.set(matrix4f);
    }
    
    public Matrix4f(final Matrix3f matrix3f, final Vector3f translation, final float n) {
        this.set(matrix3f);
        this.mulRotationScale(n);
        this.setTranslation(translation);
        this.m33 = 1.0f;
    }
    
    public Matrix4f() {
        this.setZero();
    }
    
    @Override
    public String toString() {
        final String s = "\u4b4e\u4b4b\u4b4c\u4b47\u4b0c\u4b51\u4b47\u4b52\u4b43\u4b50\u4b43\u4b56\u4b4d\u4b50";
        return "[" + s + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "\t" + this.m03 + "]" + s + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "\t" + this.m13 + "]" + s + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "\t" + this.m23 + "]" + s + "  [" + this.m30 + "\t" + this.m31 + "\t" + this.m32 + "\t" + this.m33 + "] ]";
    }
    
    public final void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void setElement(final int n, final int n2, final float n3) {
        if (n == 0) {
            if (n2 == 0) {
                this.m00 = n3;
            }
            else if (n2 == 1) {
                this.m01 = n3;
            }
            else if (n2 == 2) {
                this.m02 = n3;
            }
            else {
                if (n2 != 3) {
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
                }
                this.m03 = n3;
            }
        }
        else if (n == 1) {
            if (n2 == 0) {
                this.m10 = n3;
            }
            else if (n2 == 1) {
                this.m11 = n3;
            }
            else if (n2 == 2) {
                this.m12 = n3;
            }
            else {
                if (n2 != 3) {
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
                }
                this.m13 = n3;
            }
        }
        else if (n == 2) {
            if (n2 == 0) {
                this.m20 = n3;
            }
            else if (n2 == 1) {
                this.m21 = n3;
            }
            else if (n2 == 2) {
                this.m22 = n3;
            }
            else {
                if (n2 != 3) {
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
                }
                this.m23 = n3;
            }
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            if (n2 == 0) {
                this.m30 = n3;
            }
            else if (n2 == 1) {
                this.m31 = n3;
            }
            else if (n2 == 2) {
                this.m32 = n3;
            }
            else {
                if (n2 != 3) {
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
                }
                this.m33 = n3;
            }
        }
    }
    
    public final float getElement(final int n, final int n2) {
        if (n == 0) {
            if (n2 == 0) {
                return this.m00;
            }
            if (n2 == 1) {
                return this.m01;
            }
            if (n2 == 2) {
                return this.m02;
            }
            if (n2 == 3) {
                return this.m03;
            }
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
        }
        else if (n == 1) {
            if (n2 == 0) {
                return this.m10;
            }
            if (n2 == 1) {
                return this.m11;
            }
            if (n2 == 2) {
                return this.m12;
            }
            if (n2 == 3) {
                return this.m13;
            }
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
        }
        else if (n == 2) {
            if (n2 == 0) {
                return this.m20;
            }
            if (n2 == 1) {
                return this.m21;
            }
            if (n2 == 2) {
                return this.m22;
            }
            if (n2 == 3) {
                return this.m23;
            }
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            if (n2 == 0) {
                return this.m30;
            }
            if (n2 == 1) {
                return this.m31;
            }
            if (n2 == 2) {
                return this.m32;
            }
            if (n2 == 3) {
                return this.m33;
            }
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n2);
        }
    }
    
    public final void setScale(final float n) {
        this.SVD(null, this);
        this.mulRotationScale(n);
    }
    
    public final void get(final Matrix3d matrix3d) {
        this.SVD(matrix3d);
    }
    
    public final void get(final Matrix3f matrix3f) {
        this.SVD(matrix3f, null);
    }
    
    public final float get(final Matrix3f matrix3f, final Vector3f vector3f) {
        this.get(vector3f);
        return this.SVD(matrix3f, null);
    }
    
    public final void get(final Quat4f quat4f) {
        quat4f.set(this);
        quat4f.normalize();
    }
    
    public final void get(final Vector3f vector3f) {
        vector3f.x = this.m03;
        vector3f.y = this.m13;
        vector3f.z = this.m23;
    }
    
    public final void getRotationScale(final Matrix3f matrix3f) {
        matrix3f.m00 = this.m00;
        matrix3f.m01 = this.m01;
        matrix3f.m02 = this.m02;
        matrix3f.m10 = this.m10;
        matrix3f.m11 = this.m11;
        matrix3f.m12 = this.m12;
        matrix3f.m20 = this.m20;
        matrix3f.m21 = this.m21;
        matrix3f.m22 = this.m22;
    }
    
    public final float getScale() {
        return this.SVD(null);
    }
    
    public final void setRotationScale(final Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }
    
    public final void setRow(final int n, final float n2, final float n3, final float n4, final float n5) {
        if (n == 0) {
            this.m00 = n2;
            this.m01 = n3;
            this.m02 = n4;
            this.m03 = n5;
        }
        else if (n == 1) {
            this.m10 = n2;
            this.m11 = n3;
            this.m12 = n4;
            this.m13 = n5;
        }
        else if (n == 2) {
            this.m20 = n2;
            this.m21 = n3;
            this.m22 = n4;
            this.m23 = n5;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            this.m30 = n2;
            this.m31 = n3;
            this.m32 = n4;
            this.m33 = n5;
        }
    }
    
    public final void setRow(final int n, final Vector4f vector4f) {
        if (n == 0) {
            this.m00 = vector4f.x;
            this.m01 = vector4f.y;
            this.m02 = vector4f.z;
            this.m03 = vector4f.w;
        }
        else if (n == 1) {
            this.m10 = vector4f.x;
            this.m11 = vector4f.y;
            this.m12 = vector4f.z;
            this.m13 = vector4f.w;
        }
        else if (n == 2) {
            this.m20 = vector4f.x;
            this.m21 = vector4f.y;
            this.m22 = vector4f.z;
            this.m23 = vector4f.w;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            this.m30 = vector4f.x;
            this.m31 = vector4f.y;
            this.m32 = vector4f.z;
            this.m33 = vector4f.w;
        }
    }
    
    public final void setRow(final int n, final float[] array) {
        if (n == 0) {
            this.m00 = array[0];
            this.m01 = array[1];
            this.m02 = array[2];
            this.m03 = array[3];
        }
        else if (n == 1) {
            this.m10 = array[0];
            this.m11 = array[1];
            this.m12 = array[2];
            this.m13 = array[3];
        }
        else if (n == 2) {
            this.m20 = array[0];
            this.m21 = array[1];
            this.m22 = array[2];
            this.m23 = array[3];
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            this.m30 = array[0];
            this.m31 = array[1];
            this.m32 = array[2];
            this.m33 = array[3];
        }
    }
    
    public final void getRow(final int n, final Vector4f vector4f) {
        if (n == 0) {
            vector4f.x = this.m00;
            vector4f.y = this.m01;
            vector4f.z = this.m02;
            vector4f.w = this.m03;
        }
        else if (n == 1) {
            vector4f.x = this.m10;
            vector4f.y = this.m11;
            vector4f.z = this.m12;
            vector4f.w = this.m13;
        }
        else if (n == 2) {
            vector4f.x = this.m20;
            vector4f.y = this.m21;
            vector4f.z = this.m22;
            vector4f.w = this.m23;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            vector4f.x = this.m30;
            vector4f.y = this.m31;
            vector4f.z = this.m32;
            vector4f.w = this.m33;
        }
    }
    
    public final void getRow(final int n, final float[] array) {
        if (n == 0) {
            array[0] = this.m00;
            array[1] = this.m01;
            array[2] = this.m02;
            array[3] = this.m03;
        }
        else if (n == 1) {
            array[0] = this.m10;
            array[1] = this.m11;
            array[2] = this.m12;
            array[3] = this.m13;
        }
        else if (n == 2) {
            array[0] = this.m20;
            array[1] = this.m21;
            array[2] = this.m22;
            array[3] = this.m23;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            array[0] = this.m30;
            array[1] = this.m31;
            array[2] = this.m32;
            array[3] = this.m33;
        }
    }
    
    public final void setColumn(final int n, final float n2, final float n3, final float n4, final float n5) {
        if (n == 0) {
            this.m00 = n2;
            this.m10 = n3;
            this.m20 = n4;
            this.m30 = n5;
        }
        else if (n == 1) {
            this.m01 = n2;
            this.m11 = n3;
            this.m21 = n4;
            this.m31 = n5;
        }
        else if (n == 2) {
            this.m02 = n2;
            this.m12 = n3;
            this.m22 = n4;
            this.m32 = n5;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            this.m03 = n2;
            this.m13 = n3;
            this.m23 = n4;
            this.m33 = n5;
        }
    }
    
    public final void setColumn(final int n, final Vector4f vector4f) {
        if (n == 0) {
            this.m00 = vector4f.x;
            this.m10 = vector4f.y;
            this.m20 = vector4f.z;
            this.m30 = vector4f.w;
        }
        else if (n == 1) {
            this.m01 = vector4f.x;
            this.m11 = vector4f.y;
            this.m21 = vector4f.z;
            this.m31 = vector4f.w;
        }
        else if (n == 2) {
            this.m02 = vector4f.x;
            this.m12 = vector4f.y;
            this.m22 = vector4f.z;
            this.m32 = vector4f.w;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            this.m03 = vector4f.x;
            this.m13 = vector4f.y;
            this.m23 = vector4f.z;
            this.m33 = vector4f.w;
        }
    }
    
    public final void setColumn(final int n, final float[] array) {
        if (n == 0) {
            this.m00 = array[0];
            this.m10 = array[1];
            this.m20 = array[2];
            this.m30 = array[3];
        }
        else if (n == 1) {
            this.m01 = array[0];
            this.m11 = array[1];
            this.m21 = array[2];
            this.m31 = array[3];
        }
        else if (n == 2) {
            this.m02 = array[0];
            this.m12 = array[1];
            this.m22 = array[2];
            this.m32 = array[3];
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            this.m03 = array[0];
            this.m13 = array[1];
            this.m23 = array[2];
            this.m33 = array[3];
        }
    }
    
    public final void getColumn(final int n, final Vector4f vector4f) {
        if (n == 0) {
            vector4f.x = this.m00;
            vector4f.y = this.m10;
            vector4f.z = this.m20;
            vector4f.w = this.m30;
        }
        else if (n == 1) {
            vector4f.x = this.m01;
            vector4f.y = this.m11;
            vector4f.z = this.m21;
            vector4f.w = this.m31;
        }
        else if (n == 2) {
            vector4f.x = this.m02;
            vector4f.y = this.m12;
            vector4f.z = this.m22;
            vector4f.w = this.m32;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            vector4f.x = this.m03;
            vector4f.y = this.m13;
            vector4f.z = this.m23;
            vector4f.w = this.m33;
        }
    }
    
    public final void getColumn(final int n, final float[] array) {
        if (n == 0) {
            array[0] = this.m00;
            array[1] = this.m10;
            array[2] = this.m20;
            array[3] = this.m30;
        }
        else if (n == 1) {
            array[0] = this.m01;
            array[1] = this.m11;
            array[2] = this.m21;
            array[3] = this.m31;
        }
        else if (n == 2) {
            array[0] = this.m02;
            array[1] = this.m12;
            array[2] = this.m22;
            array[3] = this.m32;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            array[0] = this.m03;
            array[1] = this.m13;
            array[2] = this.m23;
            array[3] = this.m33;
        }
    }
    
    public final void add(final float n) {
        this.m00 += n;
        this.m01 += n;
        this.m02 += n;
        this.m03 += n;
        this.m10 += n;
        this.m11 += n;
        this.m12 += n;
        this.m13 += n;
        this.m20 += n;
        this.m21 += n;
        this.m22 += n;
        this.m23 += n;
        this.m30 += n;
        this.m31 += n;
        this.m32 += n;
        this.m33 += n;
    }
    
    public final void add(final float n, final Matrix4f matrix4f) {
        this.set(matrix4f);
        this.add(n);
    }
    
    public final void add(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.set(matrix4f);
        this.add(matrix4f2);
    }
    
    public final void add(final Matrix4f matrix4f) {
        this.m00 += matrix4f.m00;
        this.m01 += matrix4f.m01;
        this.m02 += matrix4f.m02;
        this.m03 += matrix4f.m03;
        this.m10 += matrix4f.m10;
        this.m11 += matrix4f.m11;
        this.m12 += matrix4f.m12;
        this.m13 += matrix4f.m13;
        this.m20 += matrix4f.m20;
        this.m21 += matrix4f.m21;
        this.m22 += matrix4f.m22;
        this.m23 += matrix4f.m23;
        this.m30 += matrix4f.m30;
        this.m31 += matrix4f.m31;
        this.m32 += matrix4f.m32;
        this.m33 += matrix4f.m33;
    }
    
    public final void sub(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.set(matrix4f.m00 - matrix4f2.m00, matrix4f.m01 - matrix4f2.m01, matrix4f.m02 - matrix4f2.m02, matrix4f.m03 - matrix4f2.m03, matrix4f.m10 - matrix4f2.m10, matrix4f.m11 - matrix4f2.m11, matrix4f.m12 - matrix4f2.m12, matrix4f.m13 - matrix4f2.m13, matrix4f.m20 - matrix4f2.m20, matrix4f.m21 - matrix4f2.m21, matrix4f.m22 - matrix4f2.m22, matrix4f.m23 - matrix4f2.m23, matrix4f.m30 - matrix4f2.m30, matrix4f.m31 - matrix4f2.m31, matrix4f.m32 - matrix4f2.m32, matrix4f.m33 - matrix4f2.m33);
    }
    
    public final void sub(final Matrix4f matrix4f) {
        this.m00 -= matrix4f.m00;
        this.m01 -= matrix4f.m01;
        this.m02 -= matrix4f.m02;
        this.m03 -= matrix4f.m03;
        this.m10 -= matrix4f.m10;
        this.m11 -= matrix4f.m11;
        this.m12 -= matrix4f.m12;
        this.m13 -= matrix4f.m13;
        this.m20 -= matrix4f.m20;
        this.m21 -= matrix4f.m21;
        this.m22 -= matrix4f.m22;
        this.m23 -= matrix4f.m23;
        this.m30 -= matrix4f.m30;
        this.m31 -= matrix4f.m31;
        this.m32 -= matrix4f.m32;
        this.m33 -= matrix4f.m33;
    }
    
    public final void transpose() {
        final float m01 = this.m01;
        this.m01 = this.m10;
        this.m10 = m01;
        final float m2 = this.m02;
        this.m02 = this.m20;
        this.m20 = m2;
        final float m3 = this.m03;
        this.m03 = this.m30;
        this.m30 = m3;
        final float m4 = this.m12;
        this.m12 = this.m21;
        this.m21 = m4;
        final float m5 = this.m13;
        this.m13 = this.m31;
        this.m31 = m5;
        final float m6 = this.m23;
        this.m23 = this.m32;
        this.m32 = m6;
    }
    
    public final void transpose(final Matrix4f matrix4f) {
        this.set(matrix4f);
        this.transpose();
    }
    
    public final void set(final Quat4f quat4f) {
        this.setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.setFromAxisAngle(axisAngle4f.x, axisAngle4f.y, axisAngle4f.z, axisAngle4f.angle);
    }
    
    public final void set(final Quat4d quat4d) {
        this.setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.setFromAxisAngle(axisAngle4d.x, axisAngle4d.y, axisAngle4d.z, axisAngle4d.angle);
    }
    
    public final void set(final Quat4d quat4d, final Vector3d vector3d, final double n) {
        this.set(quat4d);
        this.mulRotationScale((float)n);
        this.m03 = (float)vector3d.x;
        this.m13 = (float)vector3d.y;
        this.m23 = (float)vector3d.z;
    }
    
    public final void set(final Quat4f quat4f, final Vector3f vector3f, final float n) {
        this.set(quat4f);
        this.mulRotationScale(n);
        this.m03 = vector3f.x;
        this.m13 = vector3f.y;
        this.m23 = vector3f.z;
    }
    
    public final void set(final Matrix4d matrix4d) {
        this.m00 = (float)matrix4d.m00;
        this.m01 = (float)matrix4d.m01;
        this.m02 = (float)matrix4d.m02;
        this.m03 = (float)matrix4d.m03;
        this.m10 = (float)matrix4d.m10;
        this.m11 = (float)matrix4d.m11;
        this.m12 = (float)matrix4d.m12;
        this.m13 = (float)matrix4d.m13;
        this.m20 = (float)matrix4d.m20;
        this.m21 = (float)matrix4d.m21;
        this.m22 = (float)matrix4d.m22;
        this.m23 = (float)matrix4d.m23;
        this.m30 = (float)matrix4d.m30;
        this.m31 = (float)matrix4d.m31;
        this.m32 = (float)matrix4d.m32;
        this.m33 = (float)matrix4d.m33;
    }
    
    public final void set(final Matrix4f matrix4f) {
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m01;
        this.m02 = matrix4f.m02;
        this.m03 = matrix4f.m03;
        this.m10 = matrix4f.m10;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m12;
        this.m13 = matrix4f.m13;
        this.m20 = matrix4f.m20;
        this.m21 = matrix4f.m21;
        this.m22 = matrix4f.m22;
        this.m23 = matrix4f.m23;
        this.m30 = matrix4f.m30;
        this.m31 = matrix4f.m31;
        this.m32 = matrix4f.m32;
        this.m33 = matrix4f.m33;
    }
    
    public final void invert(final Matrix4f matrix4f) {
        this.set(matrix4f);
        this.invert();
    }
    
    public final void invert() {
        final float determinant = this.determinant();
        if (determinant == 0.0) {
            return;
        }
        final float n = 1.0f / determinant;
        this.set(this.m11 * (this.m22 * this.m33 - this.m23 * this.m32) + this.m12 * (this.m23 * this.m31 - this.m21 * this.m33) + this.m13 * (this.m21 * this.m32 - this.m22 * this.m31), this.m21 * (this.m02 * this.m33 - this.m03 * this.m32) + this.m22 * (this.m03 * this.m31 - this.m01 * this.m33) + this.m23 * (this.m01 * this.m32 - this.m02 * this.m31), this.m31 * (this.m02 * this.m13 - this.m03 * this.m12) + this.m32 * (this.m03 * this.m11 - this.m01 * this.m13) + this.m33 * (this.m01 * this.m12 - this.m02 * this.m11), this.m01 * (this.m13 * this.m22 - this.m12 * this.m23) + this.m02 * (this.m11 * this.m23 - this.m13 * this.m21) + this.m03 * (this.m12 * this.m21 - this.m11 * this.m22), this.m12 * (this.m20 * this.m33 - this.m23 * this.m30) + this.m13 * (this.m22 * this.m30 - this.m20 * this.m32) + this.m10 * (this.m23 * this.m32 - this.m22 * this.m33), this.m22 * (this.m00 * this.m33 - this.m03 * this.m30) + this.m23 * (this.m02 * this.m30 - this.m00 * this.m32) + this.m20 * (this.m03 * this.m32 - this.m02 * this.m33), this.m32 * (this.m00 * this.m13 - this.m03 * this.m10) + this.m33 * (this.m02 * this.m10 - this.m00 * this.m12) + this.m30 * (this.m03 * this.m12 - this.m02 * this.m13), this.m02 * (this.m13 * this.m20 - this.m10 * this.m23) + this.m03 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m00 * (this.m12 * this.m23 - this.m13 * this.m22), this.m13 * (this.m20 * this.m31 - this.m21 * this.m30) + this.m10 * (this.m21 * this.m33 - this.m23 * this.m31) + this.m11 * (this.m23 * this.m30 - this.m20 * this.m33), this.m23 * (this.m00 * this.m31 - this.m01 * this.m30) + this.m20 * (this.m01 * this.m33 - this.m03 * this.m31) + this.m21 * (this.m03 * this.m30 - this.m00 * this.m33), this.m33 * (this.m00 * this.m11 - this.m01 * this.m10) + this.m30 * (this.m01 * this.m13 - this.m03 * this.m11) + this.m31 * (this.m03 * this.m10 - this.m00 * this.m13), this.m03 * (this.m11 * this.m20 - this.m10 * this.m21) + this.m00 * (this.m13 * this.m21 - this.m11 * this.m23) + this.m01 * (this.m10 * this.m23 - this.m13 * this.m20), this.m10 * (this.m22 * this.m31 - this.m21 * this.m32) + this.m11 * (this.m20 * this.m32 - this.m22 * this.m30) + this.m12 * (this.m21 * this.m30 - this.m20 * this.m31), this.m20 * (this.m02 * this.m31 - this.m01 * this.m32) + this.m21 * (this.m00 * this.m32 - this.m02 * this.m30) + this.m22 * (this.m01 * this.m30 - this.m00 * this.m31), this.m30 * (this.m02 * this.m11 - this.m01 * this.m12) + this.m31 * (this.m00 * this.m12 - this.m02 * this.m10) + this.m32 * (this.m01 * this.m10 - this.m00 * this.m11), this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20));
        this.mul(n);
    }
    
    public final float determinant() {
        return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32) - (this.m00 * this.m12 - this.m02 * this.m10) * (this.m21 * this.m33 - this.m23 * this.m31) + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31) + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30) - (this.m01 * this.m13 - this.m03 * this.m11) * (this.m20 * this.m32 - this.m22 * this.m30) + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
    }
    
    public final void set(final Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m03 = 0.0f;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m13 = 0.0f;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void set(final Matrix3d matrix3d) {
        this.m00 = (float)matrix3d.m00;
        this.m01 = (float)matrix3d.m01;
        this.m02 = (float)matrix3d.m02;
        this.m03 = 0.0f;
        this.m10 = (float)matrix3d.m10;
        this.m11 = (float)matrix3d.m11;
        this.m12 = (float)matrix3d.m12;
        this.m13 = 0.0f;
        this.m20 = (float)matrix3d.m20;
        this.m21 = (float)matrix3d.m21;
        this.m22 = (float)matrix3d.m22;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void set(final float m22) {
        this.m00 = m22;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = m22;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = m22;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void set(final float[] array) {
        this.m00 = array[0];
        this.m01 = array[1];
        this.m02 = array[2];
        this.m03 = array[3];
        this.m10 = array[4];
        this.m11 = array[5];
        this.m12 = array[6];
        this.m13 = array[7];
        this.m20 = array[8];
        this.m21 = array[9];
        this.m22 = array[10];
        this.m23 = array[11];
        this.m30 = array[12];
        this.m31 = array[13];
        this.m32 = array[14];
        this.m33 = array[15];
    }
    
    public final void set(final Vector3f translation) {
        this.setIdentity();
        this.setTranslation(translation);
    }
    
    public final void set(final float n, final Vector3f translation) {
        this.set(n);
        this.setTranslation(translation);
    }
    
    public final void set(final Vector3f vector3f, final float m22) {
        this.m00 = m22;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = m22 * vector3f.x;
        this.m10 = 0.0f;
        this.m11 = m22;
        this.m12 = 0.0f;
        this.m13 = m22 * vector3f.y;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = m22;
        this.m23 = m22 * vector3f.z;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void set(final Matrix3f rotationScale, final Vector3f translation, final float n) {
        this.setRotationScale(rotationScale);
        this.mulRotationScale(n);
        this.setTranslation(translation);
        this.m33 = 1.0f;
    }
    
    public final void set(final Matrix3d rotationScale, final Vector3d translation, final double n) {
        this.setRotationScale(rotationScale);
        this.mulRotationScale((float)n);
        this.setTranslation(translation);
        this.m33 = 1.0f;
    }
    
    public void setTranslation(final Vector3f vector3f) {
        this.m03 = vector3f.x;
        this.m13 = vector3f.y;
        this.m23 = vector3f.z;
    }
    
    public final void rotX(final float n) {
        final float n2 = (float)Math.cos(n);
        final float m21 = (float)Math.sin(n);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = n2;
        this.m12 = -m21;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = m21;
        this.m22 = n2;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void rotY(final float n) {
        final float n2 = (float)Math.cos(n);
        final float m02 = (float)Math.sin(n);
        this.m00 = n2;
        this.m01 = 0.0f;
        this.m02 = m02;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = -m02;
        this.m21 = 0.0f;
        this.m22 = n2;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void rotZ(final float n) {
        final float n2 = (float)Math.cos(n);
        final float m10 = (float)Math.sin(n);
        this.m00 = n2;
        this.m01 = -m10;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = m10;
        this.m11 = n2;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
    
    public final void mul(final float n) {
        this.m00 *= n;
        this.m01 *= n;
        this.m02 *= n;
        this.m03 *= n;
        this.m10 *= n;
        this.m11 *= n;
        this.m12 *= n;
        this.m13 *= n;
        this.m20 *= n;
        this.m21 *= n;
        this.m22 *= n;
        this.m23 *= n;
        this.m30 *= n;
        this.m31 *= n;
        this.m32 *= n;
        this.m33 *= n;
    }
    
    public final void mul(final float n, final Matrix4f matrix4f) {
        this.set(matrix4f);
        this.mul(n);
    }
    
    public final void mul(final Matrix4f matrix4f) {
        this.mul(this, matrix4f);
    }
    
    public final void mul(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.set(matrix4f.m00 * matrix4f2.m00 + matrix4f.m01 * matrix4f2.m10 + matrix4f.m02 * matrix4f2.m20 + matrix4f.m03 * matrix4f2.m30, matrix4f.m00 * matrix4f2.m01 + matrix4f.m01 * matrix4f2.m11 + matrix4f.m02 * matrix4f2.m21 + matrix4f.m03 * matrix4f2.m31, matrix4f.m00 * matrix4f2.m02 + matrix4f.m01 * matrix4f2.m12 + matrix4f.m02 * matrix4f2.m22 + matrix4f.m03 * matrix4f2.m32, matrix4f.m00 * matrix4f2.m03 + matrix4f.m01 * matrix4f2.m13 + matrix4f.m02 * matrix4f2.m23 + matrix4f.m03 * matrix4f2.m33, matrix4f.m10 * matrix4f2.m00 + matrix4f.m11 * matrix4f2.m10 + matrix4f.m12 * matrix4f2.m20 + matrix4f.m13 * matrix4f2.m30, matrix4f.m10 * matrix4f2.m01 + matrix4f.m11 * matrix4f2.m11 + matrix4f.m12 * matrix4f2.m21 + matrix4f.m13 * matrix4f2.m31, matrix4f.m10 * matrix4f2.m02 + matrix4f.m11 * matrix4f2.m12 + matrix4f.m12 * matrix4f2.m22 + matrix4f.m13 * matrix4f2.m32, matrix4f.m10 * matrix4f2.m03 + matrix4f.m11 * matrix4f2.m13 + matrix4f.m12 * matrix4f2.m23 + matrix4f.m13 * matrix4f2.m33, matrix4f.m20 * matrix4f2.m00 + matrix4f.m21 * matrix4f2.m10 + matrix4f.m22 * matrix4f2.m20 + matrix4f.m23 * matrix4f2.m30, matrix4f.m20 * matrix4f2.m01 + matrix4f.m21 * matrix4f2.m11 + matrix4f.m22 * matrix4f2.m21 + matrix4f.m23 * matrix4f2.m31, matrix4f.m20 * matrix4f2.m02 + matrix4f.m21 * matrix4f2.m12 + matrix4f.m22 * matrix4f2.m22 + matrix4f.m23 * matrix4f2.m32, matrix4f.m20 * matrix4f2.m03 + matrix4f.m21 * matrix4f2.m13 + matrix4f.m22 * matrix4f2.m23 + matrix4f.m23 * matrix4f2.m33, matrix4f.m30 * matrix4f2.m00 + matrix4f.m31 * matrix4f2.m10 + matrix4f.m32 * matrix4f2.m20 + matrix4f.m33 * matrix4f2.m30, matrix4f.m30 * matrix4f2.m01 + matrix4f.m31 * matrix4f2.m11 + matrix4f.m32 * matrix4f2.m21 + matrix4f.m33 * matrix4f2.m31, matrix4f.m30 * matrix4f2.m02 + matrix4f.m31 * matrix4f2.m12 + matrix4f.m32 * matrix4f2.m22 + matrix4f.m33 * matrix4f2.m32, matrix4f.m30 * matrix4f2.m03 + matrix4f.m31 * matrix4f2.m13 + matrix4f.m32 * matrix4f2.m23 + matrix4f.m33 * matrix4f2.m33);
    }
    
    public final void mulTransposeBoth(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.mul(matrix4f2, matrix4f);
        this.transpose();
    }
    
    public final void mulTransposeRight(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.set(matrix4f.m00 * matrix4f2.m00 + matrix4f.m01 * matrix4f2.m01 + matrix4f.m02 * matrix4f2.m02 + matrix4f.m03 * matrix4f2.m03, matrix4f.m00 * matrix4f2.m10 + matrix4f.m01 * matrix4f2.m11 + matrix4f.m02 * matrix4f2.m12 + matrix4f.m03 * matrix4f2.m13, matrix4f.m00 * matrix4f2.m20 + matrix4f.m01 * matrix4f2.m21 + matrix4f.m02 * matrix4f2.m22 + matrix4f.m03 * matrix4f2.m23, matrix4f.m00 * matrix4f2.m30 + matrix4f.m01 * matrix4f2.m31 + matrix4f.m02 * matrix4f2.m32 + matrix4f.m03 * matrix4f2.m33, matrix4f.m10 * matrix4f2.m00 + matrix4f.m11 * matrix4f2.m01 + matrix4f.m12 * matrix4f2.m02 + matrix4f.m13 * matrix4f2.m03, matrix4f.m10 * matrix4f2.m10 + matrix4f.m11 * matrix4f2.m11 + matrix4f.m12 * matrix4f2.m12 + matrix4f.m13 * matrix4f2.m13, matrix4f.m10 * matrix4f2.m20 + matrix4f.m11 * matrix4f2.m21 + matrix4f.m12 * matrix4f2.m22 + matrix4f.m13 * matrix4f2.m23, matrix4f.m10 * matrix4f2.m30 + matrix4f.m11 * matrix4f2.m31 + matrix4f.m12 * matrix4f2.m32 + matrix4f.m13 * matrix4f2.m33, matrix4f.m20 * matrix4f2.m00 + matrix4f.m21 * matrix4f2.m01 + matrix4f.m22 * matrix4f2.m02 + matrix4f.m23 * matrix4f2.m03, matrix4f.m20 * matrix4f2.m10 + matrix4f.m21 * matrix4f2.m11 + matrix4f.m22 * matrix4f2.m12 + matrix4f.m23 * matrix4f2.m13, matrix4f.m20 * matrix4f2.m20 + matrix4f.m21 * matrix4f2.m21 + matrix4f.m22 * matrix4f2.m22 + matrix4f.m23 * matrix4f2.m23, matrix4f.m20 * matrix4f2.m30 + matrix4f.m21 * matrix4f2.m31 + matrix4f.m22 * matrix4f2.m32 + matrix4f.m23 * matrix4f2.m33, matrix4f.m30 * matrix4f2.m00 + matrix4f.m31 * matrix4f2.m01 + matrix4f.m32 * matrix4f2.m02 + matrix4f.m33 * matrix4f2.m03, matrix4f.m30 * matrix4f2.m10 + matrix4f.m31 * matrix4f2.m11 + matrix4f.m32 * matrix4f2.m12 + matrix4f.m33 * matrix4f2.m13, matrix4f.m30 * matrix4f2.m20 + matrix4f.m31 * matrix4f2.m21 + matrix4f.m32 * matrix4f2.m22 + matrix4f.m33 * matrix4f2.m23, matrix4f.m30 * matrix4f2.m30 + matrix4f.m31 * matrix4f2.m31 + matrix4f.m32 * matrix4f2.m32 + matrix4f.m33 * matrix4f2.m33);
    }
    
    public final void mulTransposeLeft(final Matrix4f matrix4f, final Matrix4f matrix4f2) {
        this.set(matrix4f.m00 * matrix4f2.m00 + matrix4f.m10 * matrix4f2.m10 + matrix4f.m20 * matrix4f2.m20 + matrix4f.m30 * matrix4f2.m30, matrix4f.m00 * matrix4f2.m01 + matrix4f.m10 * matrix4f2.m11 + matrix4f.m20 * matrix4f2.m21 + matrix4f.m30 * matrix4f2.m31, matrix4f.m00 * matrix4f2.m02 + matrix4f.m10 * matrix4f2.m12 + matrix4f.m20 * matrix4f2.m22 + matrix4f.m30 * matrix4f2.m32, matrix4f.m00 * matrix4f2.m03 + matrix4f.m10 * matrix4f2.m13 + matrix4f.m20 * matrix4f2.m23 + matrix4f.m30 * matrix4f2.m33, matrix4f.m01 * matrix4f2.m00 + matrix4f.m11 * matrix4f2.m10 + matrix4f.m21 * matrix4f2.m20 + matrix4f.m31 * matrix4f2.m30, matrix4f.m01 * matrix4f2.m01 + matrix4f.m11 * matrix4f2.m11 + matrix4f.m21 * matrix4f2.m21 + matrix4f.m31 * matrix4f2.m31, matrix4f.m01 * matrix4f2.m02 + matrix4f.m11 * matrix4f2.m12 + matrix4f.m21 * matrix4f2.m22 + matrix4f.m31 * matrix4f2.m32, matrix4f.m01 * matrix4f2.m03 + matrix4f.m11 * matrix4f2.m13 + matrix4f.m21 * matrix4f2.m23 + matrix4f.m31 * matrix4f2.m33, matrix4f.m02 * matrix4f2.m00 + matrix4f.m12 * matrix4f2.m10 + matrix4f.m22 * matrix4f2.m20 + matrix4f.m32 * matrix4f2.m30, matrix4f.m02 * matrix4f2.m01 + matrix4f.m12 * matrix4f2.m11 + matrix4f.m22 * matrix4f2.m21 + matrix4f.m32 * matrix4f2.m31, matrix4f.m02 * matrix4f2.m02 + matrix4f.m12 * matrix4f2.m12 + matrix4f.m22 * matrix4f2.m22 + matrix4f.m32 * matrix4f2.m32, matrix4f.m02 * matrix4f2.m03 + matrix4f.m12 * matrix4f2.m13 + matrix4f.m22 * matrix4f2.m23 + matrix4f.m32 * matrix4f2.m33, matrix4f.m03 * matrix4f2.m00 + matrix4f.m13 * matrix4f2.m10 + matrix4f.m23 * matrix4f2.m20 + matrix4f.m33 * matrix4f2.m30, matrix4f.m03 * matrix4f2.m01 + matrix4f.m13 * matrix4f2.m11 + matrix4f.m23 * matrix4f2.m21 + matrix4f.m33 * matrix4f2.m31, matrix4f.m03 * matrix4f2.m02 + matrix4f.m13 * matrix4f2.m12 + matrix4f.m23 * matrix4f2.m22 + matrix4f.m33 * matrix4f2.m32, matrix4f.m03 * matrix4f2.m03 + matrix4f.m13 * matrix4f2.m13 + matrix4f.m23 * matrix4f2.m23 + matrix4f.m33 * matrix4f2.m33);
    }
    
    @Override
    public boolean equals(final Object p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          21
        //     4: aload_1        
        //     5: instanceof      Ljavax/vecmath/Matrix4f;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Matrix4f;
        //    16: ifnull          21
        //    19: iconst_1       
        //    20: ireturn        
        //    21: iconst_0       
        //    22: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0021 (coming from #0016).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean epsilonEquals(final Matrix4f matrix4f, final float n) {
        return Math.abs(this.m00 - matrix4f.m00) <= n && Math.abs(this.m01 - matrix4f.m01) <= n && Math.abs(this.m02 - matrix4f.m02) <= n && Math.abs(this.m03 - matrix4f.m03) <= n && Math.abs(this.m10 - matrix4f.m10) <= n && Math.abs(this.m11 - matrix4f.m11) <= n && Math.abs(this.m12 - matrix4f.m12) <= n && Math.abs(this.m13 - matrix4f.m13) <= n && Math.abs(this.m20 - matrix4f.m20) <= n && Math.abs(this.m21 - matrix4f.m21) <= n && Math.abs(this.m22 - matrix4f.m22) <= n && Math.abs(this.m23 - matrix4f.m23) <= n && Math.abs(this.m30 - matrix4f.m30) <= n && Math.abs(this.m31 - matrix4f.m31) <= n && Math.abs(this.m32 - matrix4f.m32) <= n && Math.abs(this.m33 - matrix4f.m33) <= n;
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.m00) ^ Float.floatToIntBits(this.m01) ^ Float.floatToIntBits(this.m02) ^ Float.floatToIntBits(this.m03) ^ Float.floatToIntBits(this.m10) ^ Float.floatToIntBits(this.m11) ^ Float.floatToIntBits(this.m12) ^ Float.floatToIntBits(this.m13) ^ Float.floatToIntBits(this.m20) ^ Float.floatToIntBits(this.m21) ^ Float.floatToIntBits(this.m22) ^ Float.floatToIntBits(this.m23) ^ Float.floatToIntBits(this.m30) ^ Float.floatToIntBits(this.m31) ^ Float.floatToIntBits(this.m32) ^ Float.floatToIntBits(this.m33);
    }
    
    public final void transform(final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        tuple4f2.set(this.m00 * tuple4f.x + this.m01 * tuple4f.y + this.m02 * tuple4f.z + this.m03 * tuple4f.w, this.m10 * tuple4f.x + this.m11 * tuple4f.y + this.m12 * tuple4f.z + this.m13 * tuple4f.w, this.m20 * tuple4f.x + this.m21 * tuple4f.y + this.m22 * tuple4f.z + this.m23 * tuple4f.w, this.m30 * tuple4f.x + this.m31 * tuple4f.y + this.m32 * tuple4f.z + this.m33 * tuple4f.w);
    }
    
    public final void transform(final Tuple4f tuple4f) {
        this.transform(tuple4f, tuple4f);
    }
    
    public final void transform(final Point3f point3f, final Point3f point3f2) {
        point3f2.set(this.m00 * point3f.x + this.m01 * point3f.y + this.m02 * point3f.z + this.m03, this.m10 * point3f.x + this.m11 * point3f.y + this.m12 * point3f.z + this.m13, this.m20 * point3f.x + this.m21 * point3f.y + this.m22 * point3f.z + this.m23);
    }
    
    public final void transform(final Point3f point3f) {
        this.transform(point3f, point3f);
    }
    
    public final void transform(final Vector3f vector3f, final Vector3f vector3f2) {
        vector3f2.set(this.m00 * vector3f.x + this.m01 * vector3f.y + this.m02 * vector3f.z, this.m10 * vector3f.x + this.m11 * vector3f.y + this.m12 * vector3f.z, this.m20 * vector3f.x + this.m21 * vector3f.y + this.m22 * vector3f.z);
    }
    
    public final void transform(final Vector3f vector3f) {
        this.transform(vector3f, vector3f);
    }
    
    public final void setRotation(final Matrix3d rotationScale) {
        final float svd = this.SVD(null);
        this.setRotationScale(rotationScale);
        this.mulRotationScale(svd);
    }
    
    public final void setRotation(final Matrix3f rotationScale) {
        final float svd = this.SVD(null);
        this.setRotationScale(rotationScale);
        this.mulRotationScale(svd);
    }
    
    public final void setRotation(final Quat4f quat4f) {
        final float svd = this.SVD(null, null);
        final float m03 = this.m03;
        final float m4 = this.m13;
        final float m5 = this.m23;
        final float m6 = this.m30;
        final float m7 = this.m31;
        final float m8 = this.m32;
        final float m9 = this.m33;
        this.set(quat4f);
        this.mulRotationScale(svd);
        this.m03 = m03;
        this.m13 = m4;
        this.m23 = m5;
        this.m30 = m6;
        this.m31 = m7;
        this.m32 = m8;
        this.m33 = m9;
    }
    
    public final void setRotation(final Quat4d quat4d) {
        final float svd = this.SVD(null, null);
        final float m03 = this.m03;
        final float m4 = this.m13;
        final float m5 = this.m23;
        final float m6 = this.m30;
        final float m7 = this.m31;
        final float m8 = this.m32;
        final float m9 = this.m33;
        this.set(quat4d);
        this.mulRotationScale(svd);
        this.m03 = m03;
        this.m13 = m4;
        this.m23 = m5;
        this.m30 = m6;
        this.m31 = m7;
        this.m32 = m8;
        this.m33 = m9;
    }
    
    public final void setRotation(final AxisAngle4f axisAngle4f) {
        final float svd = this.SVD(null, null);
        final float m03 = this.m03;
        final float m4 = this.m13;
        final float m5 = this.m23;
        final float m6 = this.m30;
        final float m7 = this.m31;
        final float m8 = this.m32;
        final float m9 = this.m33;
        this.set(axisAngle4f);
        this.mulRotationScale(svd);
        this.m03 = m03;
        this.m13 = m4;
        this.m23 = m5;
        this.m30 = m6;
        this.m31 = m7;
        this.m32 = m8;
        this.m33 = m9;
    }
    
    public final void setZero() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 0.0f;
    }
    
    public final void negate() {
        this.m00 = -this.m00;
        this.m01 = -this.m01;
        this.m02 = -this.m02;
        this.m03 = -this.m03;
        this.m10 = -this.m10;
        this.m11 = -this.m11;
        this.m12 = -this.m12;
        this.m13 = -this.m13;
        this.m20 = -this.m20;
        this.m21 = -this.m21;
        this.m22 = -this.m22;
        this.m23 = -this.m23;
        this.m30 = -this.m30;
        this.m31 = -this.m31;
        this.m32 = -this.m32;
        this.m33 = -this.m33;
    }
    
    public final void negate(final Matrix4f matrix4f) {
        this.set(matrix4f);
        this.negate();
    }
    
    private void set(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6, final float m7, final float m8, final float m9, final float m10, final float m11, final float m12, final float m13, final float m14, final float m15, final float m16) {
        this.m00 = m00;
        this.m01 = m2;
        this.m02 = m3;
        this.m03 = m4;
        this.m10 = m5;
        this.m11 = m6;
        this.m12 = m7;
        this.m13 = m8;
        this.m20 = m9;
        this.m21 = m10;
        this.m22 = m11;
        this.m23 = m12;
        this.m30 = m13;
        this.m31 = m14;
        this.m32 = m15;
        this.m33 = m16;
    }
    
    private float SVD(final Matrix3f matrix3f, final Matrix4f matrix4f) {
        final float n = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        final float n2 = (n == 0.0f) ? 0.0f : (1.0f / n);
        if (matrix3f != null) {
            this.getRotationScale(matrix3f);
            matrix3f.mul(n2);
        }
        if (matrix4f != null) {
            if (matrix4f != this) {
                matrix4f.setRotationScale(this);
            }
            matrix4f.mulRotationScale(n2);
        }
        return n;
    }
    
    private float SVD(final Matrix3d matrix3d) {
        final float n = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        final float n2 = (n == 0.0f) ? 0.0f : (1.0f / n);
        if (matrix3d != null) {
            this.getRotationScale(matrix3d);
            matrix3d.mul(n2);
        }
        return n;
    }
    
    private void mulRotationScale(final float n) {
        this.m00 *= n;
        this.m01 *= n;
        this.m02 *= n;
        this.m10 *= n;
        this.m11 *= n;
        this.m12 *= n;
        this.m20 *= n;
        this.m21 *= n;
        this.m22 *= n;
    }
    
    private void setRotationScale(final Matrix4f matrix4f) {
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m01;
        this.m02 = matrix4f.m02;
        this.m10 = matrix4f.m10;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m12;
        this.m20 = matrix4f.m20;
        this.m21 = matrix4f.m21;
        this.m22 = matrix4f.m22;
    }
    
    private void setRotationScale(final Matrix3d matrix3d) {
        this.m00 = (float)matrix3d.m00;
        this.m01 = (float)matrix3d.m01;
        this.m02 = (float)matrix3d.m02;
        this.m10 = (float)matrix3d.m10;
        this.m11 = (float)matrix3d.m11;
        this.m12 = (float)matrix3d.m12;
        this.m20 = (float)matrix3d.m20;
        this.m21 = (float)matrix3d.m21;
        this.m22 = (float)matrix3d.m22;
    }
    
    private void setTranslation(final Vector3d vector3d) {
        this.m03 = (float)vector3d.x;
        this.m13 = (float)vector3d.y;
        this.m23 = (float)vector3d.z;
    }
    
    private final void getRotationScale(final Matrix3d matrix3d) {
        matrix3d.m00 = this.m00;
        matrix3d.m01 = this.m01;
        matrix3d.m02 = this.m02;
        matrix3d.m10 = this.m10;
        matrix3d.m11 = this.m11;
        matrix3d.m12 = this.m12;
        matrix3d.m20 = this.m20;
        matrix3d.m21 = this.m21;
        matrix3d.m22 = this.m22;
    }
    
    private void setFromQuat(final double n, final double n2, final double n3, final double n4) {
        final double n5 = n * n + n2 * n2 + n3 * n3 + n4 * n4;
        final double n6 = (n5 > 0.0) ? (2.0 / n5) : 0.0;
        final double n7 = n * n6;
        final double n8 = n2 * n6;
        final double n9 = n3 * n6;
        final double n10 = n4 * n7;
        final double n11 = n4 * n8;
        final double n12 = n4 * n9;
        final double n13 = n * n7;
        final double n14 = n * n8;
        final double n15 = n * n9;
        final double n16 = n2 * n8;
        final double n17 = n2 * n9;
        final double n18 = n3 * n9;
        this.setIdentity();
        this.m00 = (float)(1.0 - (n16 + n18));
        this.m01 = (float)(n14 - n12);
        this.m02 = (float)(n15 + n11);
        this.m10 = (float)(n14 + n12);
        this.m11 = (float)(1.0 - (n13 + n18));
        this.m12 = (float)(n17 - n10);
        this.m20 = (float)(n15 - n11);
        this.m21 = (float)(n17 + n10);
        this.m22 = (float)(1.0 - (n13 + n16));
    }
    
    private void setFromAxisAngle(double n, double n2, double n3, final double n4) {
        final double n5 = 1.0 / Math.sqrt(n * n + n2 * n2 + n3 * n3);
        n *= n5;
        n2 *= n5;
        n3 *= n5;
        final double cos = Math.cos(n4);
        final double sin = Math.sin(n4);
        final double n6 = 1.0 - cos;
        this.m00 = (float)(cos + n * n * n6);
        this.m11 = (float)(cos + n2 * n2 * n6);
        this.m22 = (float)(cos + n3 * n3 * n6);
        final double n7 = n * n2 * n6;
        final double n8 = n3 * sin;
        this.m01 = (float)(n7 - n8);
        this.m10 = (float)(n7 + n8);
        final double n9 = n * n3 * n6;
        final double n10 = n2 * sin;
        this.m02 = (float)(n9 + n10);
        this.m20 = (float)(n9 - n10);
        final double n11 = n2 * n3 * n6;
        final double n12 = n * sin;
        this.m12 = (float)(n11 - n12);
        this.m21 = (float)(n11 + n12);
    }
}
