package javax.vecmath;

import java.io.*;

public class Matrix4d implements Serializable
{
    public double m00;
    public double m01;
    public double m02;
    public double m03;
    public double m10;
    public double m11;
    public double m12;
    public double m13;
    public double m20;
    public double m21;
    public double m22;
    public double m23;
    public double m30;
    public double m31;
    public double m32;
    public double m33;
    
    public Matrix4d(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9, final double n10, final double n11, final double n12, final double n13, final double n14, final double n15, final double n16) {
        this.set(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16);
    }
    
    public Matrix4d(final double[] array) {
        this.set(array);
    }
    
    public Matrix4d(final Quat4d quat4d, final Vector3d vector3d, final double n) {
        this.set(quat4d, vector3d, n);
    }
    
    public Matrix4d(final Quat4f quat4f, final Vector3d vector3d, final double n) {
        this.set(quat4f, vector3d, n);
    }
    
    public Matrix4d(final Matrix4d matrix4d) {
        this.set(matrix4d);
    }
    
    public Matrix4d(final Matrix4f matrix4f) {
        this.set(matrix4f);
    }
    
    public Matrix4d(final Matrix3f matrix3f, final Vector3d translation, final double n) {
        this.set(matrix3f);
        this.mulRotationScale(n);
        this.setTranslation(translation);
        this.m33 = 1.0;
    }
    
    public Matrix4d(final Matrix3d matrix3d, final Vector3d vector3d, final double n) {
        this.set(matrix3d, vector3d, n);
    }
    
    public Matrix4d() {
        this.setZero();
    }
    
    @Override
    public String toString() {
        final String s = "\u02ee\u02eb\u02ec\u02e7\u02ac\u02f1\u02e7\u02f2\u02e3\u02f0\u02e3\u02f6\u02ed\u02f0";
        return "[" + s + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "\t" + this.m03 + "]" + s + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "\t" + this.m13 + "]" + s + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "\t" + this.m23 + "]" + s + "  [" + this.m30 + "\t" + this.m31 + "\t" + this.m32 + "\t" + this.m33 + "] ]";
    }
    
    public final void setIdentity() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void setElement(final int n, final int n2, final double n3) {
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
    
    public final double getElement(final int n, final int n2) {
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
    
    public final void get(final Matrix3d matrix3d) {
        this.SVD(matrix3d, null);
    }
    
    public final void get(final Matrix3f matrix3f) {
        this.SVD(matrix3f);
    }
    
    public final double get(final Matrix3d matrix3d, final Vector3d vector3d) {
        this.get(vector3d);
        return this.SVD(matrix3d, null);
    }
    
    public final double get(final Matrix3f matrix3f, final Vector3d vector3d) {
        this.get(vector3d);
        return this.SVD(matrix3f);
    }
    
    public final void get(final Quat4f quat4f) {
        quat4f.set(this);
        quat4f.normalize();
    }
    
    public final void get(final Quat4d quat4d) {
        quat4d.set(this);
        quat4d.normalize();
    }
    
    public final void get(final Vector3d vector3d) {
        vector3d.x = this.m03;
        vector3d.y = this.m13;
        vector3d.z = this.m23;
    }
    
    public final void getRotationScale(final Matrix3f matrix3f) {
        matrix3f.m00 = (float)this.m00;
        matrix3f.m01 = (float)this.m01;
        matrix3f.m02 = (float)this.m02;
        matrix3f.m10 = (float)this.m10;
        matrix3f.m11 = (float)this.m11;
        matrix3f.m12 = (float)this.m12;
        matrix3f.m20 = (float)this.m20;
        matrix3f.m21 = (float)this.m21;
        matrix3f.m22 = (float)this.m22;
    }
    
    public final void getRotationScale(final Matrix3d matrix3d) {
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
    
    public final double getScale() {
        return this.SVD(null);
    }
    
    public final void setRotationScale(final Matrix3d matrix3d) {
        this.m00 = matrix3d.m00;
        this.m01 = matrix3d.m01;
        this.m02 = matrix3d.m02;
        this.m10 = matrix3d.m10;
        this.m11 = matrix3d.m11;
        this.m12 = matrix3d.m12;
        this.m20 = matrix3d.m20;
        this.m21 = matrix3d.m21;
        this.m22 = matrix3d.m22;
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
    
    public final void setScale(final double n) {
        this.SVD(null, this);
        this.mulRotationScale(n);
    }
    
    public final void setRow(final int n, final double n2, final double n3, final double n4, final double n5) {
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
    
    public final void setRow(final int n, final Vector4d vector4d) {
        if (n == 0) {
            this.m00 = vector4d.x;
            this.m01 = vector4d.y;
            this.m02 = vector4d.z;
            this.m03 = vector4d.w;
        }
        else if (n == 1) {
            this.m10 = vector4d.x;
            this.m11 = vector4d.y;
            this.m12 = vector4d.z;
            this.m13 = vector4d.w;
        }
        else if (n == 2) {
            this.m20 = vector4d.x;
            this.m21 = vector4d.y;
            this.m22 = vector4d.z;
            this.m23 = vector4d.w;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            this.m30 = vector4d.x;
            this.m31 = vector4d.y;
            this.m32 = vector4d.z;
            this.m33 = vector4d.w;
        }
    }
    
    public final void setRow(final int n, final double[] array) {
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
    
    public final void getRow(final int n, final Vector4d vector4d) {
        if (n == 0) {
            vector4d.x = this.m00;
            vector4d.y = this.m01;
            vector4d.z = this.m02;
            vector4d.w = this.m03;
        }
        else if (n == 1) {
            vector4d.x = this.m10;
            vector4d.y = this.m11;
            vector4d.z = this.m12;
            vector4d.w = this.m13;
        }
        else if (n == 2) {
            vector4d.x = this.m20;
            vector4d.y = this.m21;
            vector4d.z = this.m22;
            vector4d.w = this.m23;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 3 and is " + n);
            }
            vector4d.x = this.m30;
            vector4d.y = this.m31;
            vector4d.z = this.m32;
            vector4d.w = this.m33;
        }
    }
    
    public final void getRow(final int n, final double[] array) {
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
    
    public final void setColumn(final int n, final double n2, final double n3, final double n4, final double n5) {
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
    
    public final void setColumn(final int n, final Vector4d vector4d) {
        if (n == 0) {
            this.m00 = vector4d.x;
            this.m10 = vector4d.y;
            this.m20 = vector4d.z;
            this.m30 = vector4d.w;
        }
        else if (n == 1) {
            this.m01 = vector4d.x;
            this.m11 = vector4d.y;
            this.m21 = vector4d.z;
            this.m31 = vector4d.w;
        }
        else if (n == 2) {
            this.m02 = vector4d.x;
            this.m12 = vector4d.y;
            this.m22 = vector4d.z;
            this.m32 = vector4d.w;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            this.m03 = vector4d.x;
            this.m13 = vector4d.y;
            this.m23 = vector4d.z;
            this.m33 = vector4d.w;
        }
    }
    
    public final void setColumn(final int n, final double[] array) {
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
    
    public final void getColumn(final int n, final Vector4d vector4d) {
        if (n == 0) {
            vector4d.x = this.m00;
            vector4d.y = this.m10;
            vector4d.z = this.m20;
            vector4d.w = this.m30;
        }
        else if (n == 1) {
            vector4d.x = this.m01;
            vector4d.y = this.m11;
            vector4d.z = this.m21;
            vector4d.w = this.m31;
        }
        else if (n == 2) {
            vector4d.x = this.m02;
            vector4d.y = this.m12;
            vector4d.z = this.m22;
            vector4d.w = this.m32;
        }
        else {
            if (n != 3) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 3 and is " + n);
            }
            vector4d.x = this.m03;
            vector4d.y = this.m13;
            vector4d.z = this.m23;
            vector4d.w = this.m33;
        }
    }
    
    public final void getColumn(final int n, final double[] array) {
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
    
    public final void add(final double n) {
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
    
    public final void add(final double n, final Matrix4d matrix4d) {
        this.set(matrix4d);
        this.add(n);
    }
    
    public final void add(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.set(matrix4d.m00 + matrix4d2.m00, matrix4d.m01 + matrix4d2.m01, matrix4d.m02 + matrix4d2.m02, matrix4d.m03 + matrix4d2.m03, matrix4d.m10 + matrix4d2.m10, matrix4d.m11 + matrix4d2.m11, matrix4d.m12 + matrix4d2.m12, matrix4d.m13 + matrix4d2.m13, matrix4d.m20 + matrix4d2.m20, matrix4d.m21 + matrix4d2.m21, matrix4d.m22 + matrix4d2.m22, matrix4d.m23 + matrix4d2.m23, matrix4d.m30 + matrix4d2.m30, matrix4d.m31 + matrix4d2.m31, matrix4d.m32 + matrix4d2.m32, matrix4d.m33 + matrix4d2.m33);
    }
    
    public final void add(final Matrix4d matrix4d) {
        this.m00 += matrix4d.m00;
        this.m01 += matrix4d.m01;
        this.m02 += matrix4d.m02;
        this.m03 += matrix4d.m03;
        this.m10 += matrix4d.m10;
        this.m11 += matrix4d.m11;
        this.m12 += matrix4d.m12;
        this.m13 += matrix4d.m13;
        this.m20 += matrix4d.m20;
        this.m21 += matrix4d.m21;
        this.m22 += matrix4d.m22;
        this.m23 += matrix4d.m23;
        this.m30 += matrix4d.m30;
        this.m31 += matrix4d.m31;
        this.m32 += matrix4d.m32;
        this.m33 += matrix4d.m33;
    }
    
    public final void sub(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.set(matrix4d.m00 - matrix4d2.m00, matrix4d.m01 - matrix4d2.m01, matrix4d.m02 - matrix4d2.m02, matrix4d.m03 - matrix4d2.m03, matrix4d.m10 - matrix4d2.m10, matrix4d.m11 - matrix4d2.m11, matrix4d.m12 - matrix4d2.m12, matrix4d.m13 - matrix4d2.m13, matrix4d.m20 - matrix4d2.m20, matrix4d.m21 - matrix4d2.m21, matrix4d.m22 - matrix4d2.m22, matrix4d.m23 - matrix4d2.m23, matrix4d.m30 - matrix4d2.m30, matrix4d.m31 - matrix4d2.m31, matrix4d.m32 - matrix4d2.m32, matrix4d.m33 - matrix4d2.m33);
    }
    
    public final void sub(final Matrix4d matrix4d) {
        this.m00 -= matrix4d.m00;
        this.m01 -= matrix4d.m01;
        this.m02 -= matrix4d.m02;
        this.m03 -= matrix4d.m03;
        this.m10 -= matrix4d.m10;
        this.m11 -= matrix4d.m11;
        this.m12 -= matrix4d.m12;
        this.m13 -= matrix4d.m13;
        this.m20 -= matrix4d.m20;
        this.m21 -= matrix4d.m21;
        this.m22 -= matrix4d.m22;
        this.m23 -= matrix4d.m23;
        this.m30 -= matrix4d.m30;
        this.m31 -= matrix4d.m31;
        this.m32 -= matrix4d.m32;
        this.m33 -= matrix4d.m33;
    }
    
    public final void transpose() {
        final double m01 = this.m01;
        this.m01 = this.m10;
        this.m10 = m01;
        final double m2 = this.m02;
        this.m02 = this.m20;
        this.m20 = m2;
        final double m3 = this.m03;
        this.m03 = this.m30;
        this.m30 = m3;
        final double m4 = this.m12;
        this.m12 = this.m21;
        this.m21 = m4;
        final double m5 = this.m13;
        this.m13 = this.m31;
        this.m31 = m5;
        final double m6 = this.m23;
        this.m23 = this.m32;
        this.m32 = m6;
    }
    
    public final void transpose(final Matrix4d matrix4d) {
        this.set(matrix4d);
        this.transpose();
    }
    
    public final void set(final double[] array) {
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
    
    public final void set(final Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m03 = 0.0;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m13 = 0.0;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void set(final Matrix3d matrix3d) {
        this.m00 = matrix3d.m00;
        this.m01 = matrix3d.m01;
        this.m02 = matrix3d.m02;
        this.m03 = 0.0;
        this.m10 = matrix3d.m10;
        this.m11 = matrix3d.m11;
        this.m12 = matrix3d.m12;
        this.m13 = 0.0;
        this.m20 = matrix3d.m20;
        this.m21 = matrix3d.m21;
        this.m22 = matrix3d.m22;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void set(final Quat4d quat4d) {
        this.setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.setFromAxisAngle(axisAngle4d.x, axisAngle4d.y, axisAngle4d.z, axisAngle4d.angle);
    }
    
    public final void set(final Quat4f quat4f) {
        this.setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.setFromAxisAngle(axisAngle4f.x, axisAngle4f.y, axisAngle4f.z, axisAngle4f.angle);
    }
    
    public final void set(final Quat4d quat4d, final Vector3d vector3d, final double n) {
        this.set(quat4d);
        this.mulRotationScale(n);
        this.m03 = vector3d.x;
        this.m13 = vector3d.y;
        this.m23 = vector3d.z;
    }
    
    public final void set(final Quat4f quat4f, final Vector3d vector3d, final double n) {
        this.set(quat4f);
        this.mulRotationScale(n);
        this.m03 = vector3d.x;
        this.m13 = vector3d.y;
        this.m23 = vector3d.z;
    }
    
    public final void set(final Quat4f quat4f, final Vector3f vector3f, final float n) {
        this.set(quat4f);
        this.mulRotationScale(n);
        this.m03 = vector3f.x;
        this.m13 = vector3f.y;
        this.m23 = vector3f.z;
    }
    
    public final void set(final Matrix4d matrix4d) {
        this.m00 = matrix4d.m00;
        this.m01 = matrix4d.m01;
        this.m02 = matrix4d.m02;
        this.m03 = matrix4d.m03;
        this.m10 = matrix4d.m10;
        this.m11 = matrix4d.m11;
        this.m12 = matrix4d.m12;
        this.m13 = matrix4d.m13;
        this.m20 = matrix4d.m20;
        this.m21 = matrix4d.m21;
        this.m22 = matrix4d.m22;
        this.m23 = matrix4d.m23;
        this.m30 = matrix4d.m30;
        this.m31 = matrix4d.m31;
        this.m32 = matrix4d.m32;
        this.m33 = matrix4d.m33;
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
    
    public final void invert(final Matrix4d matrix4d) {
        this.set(matrix4d);
        this.invert();
    }
    
    public final void invert() {
        final double determinant = this.determinant();
        if (determinant == 0.0) {
            return;
        }
        final double n = 1.0 / determinant;
        this.set(this.m11 * (this.m22 * this.m33 - this.m23 * this.m32) + this.m12 * (this.m23 * this.m31 - this.m21 * this.m33) + this.m13 * (this.m21 * this.m32 - this.m22 * this.m31), this.m21 * (this.m02 * this.m33 - this.m03 * this.m32) + this.m22 * (this.m03 * this.m31 - this.m01 * this.m33) + this.m23 * (this.m01 * this.m32 - this.m02 * this.m31), this.m31 * (this.m02 * this.m13 - this.m03 * this.m12) + this.m32 * (this.m03 * this.m11 - this.m01 * this.m13) + this.m33 * (this.m01 * this.m12 - this.m02 * this.m11), this.m01 * (this.m13 * this.m22 - this.m12 * this.m23) + this.m02 * (this.m11 * this.m23 - this.m13 * this.m21) + this.m03 * (this.m12 * this.m21 - this.m11 * this.m22), this.m12 * (this.m20 * this.m33 - this.m23 * this.m30) + this.m13 * (this.m22 * this.m30 - this.m20 * this.m32) + this.m10 * (this.m23 * this.m32 - this.m22 * this.m33), this.m22 * (this.m00 * this.m33 - this.m03 * this.m30) + this.m23 * (this.m02 * this.m30 - this.m00 * this.m32) + this.m20 * (this.m03 * this.m32 - this.m02 * this.m33), this.m32 * (this.m00 * this.m13 - this.m03 * this.m10) + this.m33 * (this.m02 * this.m10 - this.m00 * this.m12) + this.m30 * (this.m03 * this.m12 - this.m02 * this.m13), this.m02 * (this.m13 * this.m20 - this.m10 * this.m23) + this.m03 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m00 * (this.m12 * this.m23 - this.m13 * this.m22), this.m13 * (this.m20 * this.m31 - this.m21 * this.m30) + this.m10 * (this.m21 * this.m33 - this.m23 * this.m31) + this.m11 * (this.m23 * this.m30 - this.m20 * this.m33), this.m23 * (this.m00 * this.m31 - this.m01 * this.m30) + this.m20 * (this.m01 * this.m33 - this.m03 * this.m31) + this.m21 * (this.m03 * this.m30 - this.m00 * this.m33), this.m33 * (this.m00 * this.m11 - this.m01 * this.m10) + this.m30 * (this.m01 * this.m13 - this.m03 * this.m11) + this.m31 * (this.m03 * this.m10 - this.m00 * this.m13), this.m03 * (this.m11 * this.m20 - this.m10 * this.m21) + this.m00 * (this.m13 * this.m21 - this.m11 * this.m23) + this.m01 * (this.m10 * this.m23 - this.m13 * this.m20), this.m10 * (this.m22 * this.m31 - this.m21 * this.m32) + this.m11 * (this.m20 * this.m32 - this.m22 * this.m30) + this.m12 * (this.m21 * this.m30 - this.m20 * this.m31), this.m20 * (this.m02 * this.m31 - this.m01 * this.m32) + this.m21 * (this.m00 * this.m32 - this.m02 * this.m30) + this.m22 * (this.m01 * this.m30 - this.m00 * this.m31), this.m30 * (this.m02 * this.m11 - this.m01 * this.m12) + this.m31 * (this.m00 * this.m12 - this.m02 * this.m10) + this.m32 * (this.m01 * this.m10 - this.m00 * this.m11), this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20));
        this.mul(n);
    }
    
    public final double determinant() {
        return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32) - (this.m00 * this.m12 - this.m02 * this.m10) * (this.m21 * this.m33 - this.m23 * this.m31) + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31) + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30) - (this.m01 * this.m13 - this.m03 * this.m11) * (this.m20 * this.m32 - this.m22 * this.m30) + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
    }
    
    public final void set(final double m22) {
        this.m00 = m22;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = m22;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = m22;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void set(final Vector3d translation) {
        this.setIdentity();
        this.setTranslation(translation);
    }
    
    public final void set(final double n, final Vector3d translation) {
        this.set(n);
        this.setTranslation(translation);
    }
    
    public final void set(final Vector3d vector3d, final double m22) {
        this.m00 = m22;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = m22 * vector3d.x;
        this.m10 = 0.0;
        this.m11 = m22;
        this.m12 = 0.0;
        this.m13 = m22 * vector3d.y;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = m22;
        this.m23 = m22 * vector3d.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void set(final Matrix3f rotationScale, final Vector3f translation, final float n) {
        this.setRotationScale(rotationScale);
        this.mulRotationScale(n);
        this.setTranslation(translation);
        this.m33 = 1.0;
    }
    
    public final void set(final Matrix3d rotationScale, final Vector3d translation, final double n) {
        this.setRotationScale(rotationScale);
        this.mulRotationScale(n);
        this.setTranslation(translation);
        this.m33 = 1.0;
    }
    
    public final void setTranslation(final Vector3d vector3d) {
        this.m03 = vector3d.x;
        this.m13 = vector3d.y;
        this.m23 = vector3d.z;
    }
    
    public final void rotX(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = cos;
        this.m12 = -sin;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = sin;
        this.m22 = cos;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void rotY(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = cos;
        this.m01 = 0.0;
        this.m02 = sin;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = -sin;
        this.m21 = 0.0;
        this.m22 = cos;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void rotZ(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = cos;
        this.m01 = -sin;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = sin;
        this.m11 = cos;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }
    
    public final void mul(final double n) {
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
    
    public final void mul(final double n, final Matrix4d matrix4d) {
        this.set(matrix4d);
        this.mul(n);
    }
    
    public final void mul(final Matrix4d matrix4d) {
        this.mul(this, matrix4d);
    }
    
    public final void mul(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.set(matrix4d.m00 * matrix4d2.m00 + matrix4d.m01 * matrix4d2.m10 + matrix4d.m02 * matrix4d2.m20 + matrix4d.m03 * matrix4d2.m30, matrix4d.m00 * matrix4d2.m01 + matrix4d.m01 * matrix4d2.m11 + matrix4d.m02 * matrix4d2.m21 + matrix4d.m03 * matrix4d2.m31, matrix4d.m00 * matrix4d2.m02 + matrix4d.m01 * matrix4d2.m12 + matrix4d.m02 * matrix4d2.m22 + matrix4d.m03 * matrix4d2.m32, matrix4d.m00 * matrix4d2.m03 + matrix4d.m01 * matrix4d2.m13 + matrix4d.m02 * matrix4d2.m23 + matrix4d.m03 * matrix4d2.m33, matrix4d.m10 * matrix4d2.m00 + matrix4d.m11 * matrix4d2.m10 + matrix4d.m12 * matrix4d2.m20 + matrix4d.m13 * matrix4d2.m30, matrix4d.m10 * matrix4d2.m01 + matrix4d.m11 * matrix4d2.m11 + matrix4d.m12 * matrix4d2.m21 + matrix4d.m13 * matrix4d2.m31, matrix4d.m10 * matrix4d2.m02 + matrix4d.m11 * matrix4d2.m12 + matrix4d.m12 * matrix4d2.m22 + matrix4d.m13 * matrix4d2.m32, matrix4d.m10 * matrix4d2.m03 + matrix4d.m11 * matrix4d2.m13 + matrix4d.m12 * matrix4d2.m23 + matrix4d.m13 * matrix4d2.m33, matrix4d.m20 * matrix4d2.m00 + matrix4d.m21 * matrix4d2.m10 + matrix4d.m22 * matrix4d2.m20 + matrix4d.m23 * matrix4d2.m30, matrix4d.m20 * matrix4d2.m01 + matrix4d.m21 * matrix4d2.m11 + matrix4d.m22 * matrix4d2.m21 + matrix4d.m23 * matrix4d2.m31, matrix4d.m20 * matrix4d2.m02 + matrix4d.m21 * matrix4d2.m12 + matrix4d.m22 * matrix4d2.m22 + matrix4d.m23 * matrix4d2.m32, matrix4d.m20 * matrix4d2.m03 + matrix4d.m21 * matrix4d2.m13 + matrix4d.m22 * matrix4d2.m23 + matrix4d.m23 * matrix4d2.m33, matrix4d.m30 * matrix4d2.m00 + matrix4d.m31 * matrix4d2.m10 + matrix4d.m32 * matrix4d2.m20 + matrix4d.m33 * matrix4d2.m30, matrix4d.m30 * matrix4d2.m01 + matrix4d.m31 * matrix4d2.m11 + matrix4d.m32 * matrix4d2.m21 + matrix4d.m33 * matrix4d2.m31, matrix4d.m30 * matrix4d2.m02 + matrix4d.m31 * matrix4d2.m12 + matrix4d.m32 * matrix4d2.m22 + matrix4d.m33 * matrix4d2.m32, matrix4d.m30 * matrix4d2.m03 + matrix4d.m31 * matrix4d2.m13 + matrix4d.m32 * matrix4d2.m23 + matrix4d.m33 * matrix4d2.m33);
    }
    
    public final void mulTransposeBoth(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.mul(matrix4d2, matrix4d);
        this.transpose();
    }
    
    public final void mulTransposeRight(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.set(matrix4d.m00 * matrix4d2.m00 + matrix4d.m01 * matrix4d2.m01 + matrix4d.m02 * matrix4d2.m02 + matrix4d.m03 * matrix4d2.m03, matrix4d.m00 * matrix4d2.m10 + matrix4d.m01 * matrix4d2.m11 + matrix4d.m02 * matrix4d2.m12 + matrix4d.m03 * matrix4d2.m13, matrix4d.m00 * matrix4d2.m20 + matrix4d.m01 * matrix4d2.m21 + matrix4d.m02 * matrix4d2.m22 + matrix4d.m03 * matrix4d2.m23, matrix4d.m00 * matrix4d2.m30 + matrix4d.m01 * matrix4d2.m31 + matrix4d.m02 * matrix4d2.m32 + matrix4d.m03 * matrix4d2.m33, matrix4d.m10 * matrix4d2.m00 + matrix4d.m11 * matrix4d2.m01 + matrix4d.m12 * matrix4d2.m02 + matrix4d.m13 * matrix4d2.m03, matrix4d.m10 * matrix4d2.m10 + matrix4d.m11 * matrix4d2.m11 + matrix4d.m12 * matrix4d2.m12 + matrix4d.m13 * matrix4d2.m13, matrix4d.m10 * matrix4d2.m20 + matrix4d.m11 * matrix4d2.m21 + matrix4d.m12 * matrix4d2.m22 + matrix4d.m13 * matrix4d2.m23, matrix4d.m10 * matrix4d2.m30 + matrix4d.m11 * matrix4d2.m31 + matrix4d.m12 * matrix4d2.m32 + matrix4d.m13 * matrix4d2.m33, matrix4d.m20 * matrix4d2.m00 + matrix4d.m21 * matrix4d2.m01 + matrix4d.m22 * matrix4d2.m02 + matrix4d.m23 * matrix4d2.m03, matrix4d.m20 * matrix4d2.m10 + matrix4d.m21 * matrix4d2.m11 + matrix4d.m22 * matrix4d2.m12 + matrix4d.m23 * matrix4d2.m13, matrix4d.m20 * matrix4d2.m20 + matrix4d.m21 * matrix4d2.m21 + matrix4d.m22 * matrix4d2.m22 + matrix4d.m23 * matrix4d2.m23, matrix4d.m20 * matrix4d2.m30 + matrix4d.m21 * matrix4d2.m31 + matrix4d.m22 * matrix4d2.m32 + matrix4d.m23 * matrix4d2.m33, matrix4d.m30 * matrix4d2.m00 + matrix4d.m31 * matrix4d2.m01 + matrix4d.m32 * matrix4d2.m02 + matrix4d.m33 * matrix4d2.m03, matrix4d.m30 * matrix4d2.m10 + matrix4d.m31 * matrix4d2.m11 + matrix4d.m32 * matrix4d2.m12 + matrix4d.m33 * matrix4d2.m13, matrix4d.m30 * matrix4d2.m20 + matrix4d.m31 * matrix4d2.m21 + matrix4d.m32 * matrix4d2.m22 + matrix4d.m33 * matrix4d2.m23, matrix4d.m30 * matrix4d2.m30 + matrix4d.m31 * matrix4d2.m31 + matrix4d.m32 * matrix4d2.m32 + matrix4d.m33 * matrix4d2.m33);
    }
    
    public final void mulTransposeLeft(final Matrix4d matrix4d, final Matrix4d matrix4d2) {
        this.set(matrix4d.m00 * matrix4d2.m00 + matrix4d.m10 * matrix4d2.m10 + matrix4d.m20 * matrix4d2.m20 + matrix4d.m30 * matrix4d2.m30, matrix4d.m00 * matrix4d2.m01 + matrix4d.m10 * matrix4d2.m11 + matrix4d.m20 * matrix4d2.m21 + matrix4d.m30 * matrix4d2.m31, matrix4d.m00 * matrix4d2.m02 + matrix4d.m10 * matrix4d2.m12 + matrix4d.m20 * matrix4d2.m22 + matrix4d.m30 * matrix4d2.m32, matrix4d.m00 * matrix4d2.m03 + matrix4d.m10 * matrix4d2.m13 + matrix4d.m20 * matrix4d2.m23 + matrix4d.m30 * matrix4d2.m33, matrix4d.m01 * matrix4d2.m00 + matrix4d.m11 * matrix4d2.m10 + matrix4d.m21 * matrix4d2.m20 + matrix4d.m31 * matrix4d2.m30, matrix4d.m01 * matrix4d2.m01 + matrix4d.m11 * matrix4d2.m11 + matrix4d.m21 * matrix4d2.m21 + matrix4d.m31 * matrix4d2.m31, matrix4d.m01 * matrix4d2.m02 + matrix4d.m11 * matrix4d2.m12 + matrix4d.m21 * matrix4d2.m22 + matrix4d.m31 * matrix4d2.m32, matrix4d.m01 * matrix4d2.m03 + matrix4d.m11 * matrix4d2.m13 + matrix4d.m21 * matrix4d2.m23 + matrix4d.m31 * matrix4d2.m33, matrix4d.m02 * matrix4d2.m00 + matrix4d.m12 * matrix4d2.m10 + matrix4d.m22 * matrix4d2.m20 + matrix4d.m32 * matrix4d2.m30, matrix4d.m02 * matrix4d2.m01 + matrix4d.m12 * matrix4d2.m11 + matrix4d.m22 * matrix4d2.m21 + matrix4d.m32 * matrix4d2.m31, matrix4d.m02 * matrix4d2.m02 + matrix4d.m12 * matrix4d2.m12 + matrix4d.m22 * matrix4d2.m22 + matrix4d.m32 * matrix4d2.m32, matrix4d.m02 * matrix4d2.m03 + matrix4d.m12 * matrix4d2.m13 + matrix4d.m22 * matrix4d2.m23 + matrix4d.m32 * matrix4d2.m33, matrix4d.m03 * matrix4d2.m00 + matrix4d.m13 * matrix4d2.m10 + matrix4d.m23 * matrix4d2.m20 + matrix4d.m33 * matrix4d2.m30, matrix4d.m03 * matrix4d2.m01 + matrix4d.m13 * matrix4d2.m11 + matrix4d.m23 * matrix4d2.m21 + matrix4d.m33 * matrix4d2.m31, matrix4d.m03 * matrix4d2.m02 + matrix4d.m13 * matrix4d2.m12 + matrix4d.m23 * matrix4d2.m22 + matrix4d.m33 * matrix4d2.m32, matrix4d.m03 * matrix4d2.m03 + matrix4d.m13 * matrix4d2.m13 + matrix4d.m23 * matrix4d2.m23 + matrix4d.m33 * matrix4d2.m33);
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
        //     5: instanceof      Ljavax/vecmath/Matrix4d;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Matrix4d;
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
    
    @Deprecated
    public boolean epsilonEquals(final Matrix4d matrix4d, final float n) {
        return Math.abs(this.m00 - matrix4d.m00) <= n && Math.abs(this.m01 - matrix4d.m01) <= n && Math.abs(this.m02 - matrix4d.m02) <= n && Math.abs(this.m03 - matrix4d.m03) <= n && Math.abs(this.m10 - matrix4d.m10) <= n && Math.abs(this.m11 - matrix4d.m11) <= n && Math.abs(this.m12 - matrix4d.m12) <= n && Math.abs(this.m13 - matrix4d.m13) <= n && Math.abs(this.m20 - matrix4d.m20) <= n && Math.abs(this.m21 - matrix4d.m21) <= n && Math.abs(this.m22 - matrix4d.m22) <= n && Math.abs(this.m23 - matrix4d.m23) <= n && Math.abs(this.m30 - matrix4d.m30) <= n && Math.abs(this.m31 - matrix4d.m31) <= n && Math.abs(this.m32 - matrix4d.m32) <= n && Math.abs(this.m33 - matrix4d.m33) <= n;
    }
    
    public boolean epsilonEquals(final Matrix4d matrix4d, final double n) {
        return Math.abs(this.m00 - matrix4d.m00) <= n && Math.abs(this.m01 - matrix4d.m01) <= n && Math.abs(this.m02 - matrix4d.m02) <= n && Math.abs(this.m03 - matrix4d.m03) <= n && Math.abs(this.m10 - matrix4d.m10) <= n && Math.abs(this.m11 - matrix4d.m11) <= n && Math.abs(this.m12 - matrix4d.m12) <= n && Math.abs(this.m13 - matrix4d.m13) <= n && Math.abs(this.m20 - matrix4d.m20) <= n && Math.abs(this.m21 - matrix4d.m21) <= n && Math.abs(this.m22 - matrix4d.m22) <= n && Math.abs(this.m23 - matrix4d.m23) <= n && Math.abs(this.m30 - matrix4d.m30) <= n && Math.abs(this.m31 - matrix4d.m31) <= n && Math.abs(this.m32 - matrix4d.m32) <= n && Math.abs(this.m33 - matrix4d.m33) <= n;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.m00);
        final int n = (int)(doubleToLongBits ^ doubleToLongBits >> 32);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.m01);
        final int n2 = n ^ (int)(doubleToLongBits2 ^ doubleToLongBits2 >> 32);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.m02);
        final int n3 = n2 ^ (int)(doubleToLongBits3 ^ doubleToLongBits3 >> 32);
        final long doubleToLongBits4 = Double.doubleToLongBits(this.m03);
        final int n4 = n3 ^ (int)(doubleToLongBits4 ^ doubleToLongBits4 >> 32);
        final long doubleToLongBits5 = Double.doubleToLongBits(this.m10);
        final int n5 = n4 ^ (int)(doubleToLongBits5 ^ doubleToLongBits5 >> 32);
        final long doubleToLongBits6 = Double.doubleToLongBits(this.m11);
        final int n6 = n5 ^ (int)(doubleToLongBits6 ^ doubleToLongBits6 >> 32);
        final long doubleToLongBits7 = Double.doubleToLongBits(this.m12);
        final int n7 = n6 ^ (int)(doubleToLongBits7 ^ doubleToLongBits7 >> 32);
        final long doubleToLongBits8 = Double.doubleToLongBits(this.m13);
        final int n8 = n7 ^ (int)(doubleToLongBits8 ^ doubleToLongBits8 >> 32);
        final long doubleToLongBits9 = Double.doubleToLongBits(this.m20);
        final int n9 = n8 ^ (int)(doubleToLongBits9 ^ doubleToLongBits9 >> 32);
        final long doubleToLongBits10 = Double.doubleToLongBits(this.m21);
        final int n10 = n9 ^ (int)(doubleToLongBits10 ^ doubleToLongBits10 >> 32);
        final long doubleToLongBits11 = Double.doubleToLongBits(this.m22);
        final int n11 = n10 ^ (int)(doubleToLongBits11 ^ doubleToLongBits11 >> 32);
        final long doubleToLongBits12 = Double.doubleToLongBits(this.m23);
        final int n12 = n11 ^ (int)(doubleToLongBits12 ^ doubleToLongBits12 >> 32);
        final long doubleToLongBits13 = Double.doubleToLongBits(this.m30);
        final int n13 = n12 ^ (int)(doubleToLongBits13 ^ doubleToLongBits13 >> 32);
        final long doubleToLongBits14 = Double.doubleToLongBits(this.m31);
        final int n14 = n13 ^ (int)(doubleToLongBits14 ^ doubleToLongBits14 >> 32);
        final long doubleToLongBits15 = Double.doubleToLongBits(this.m32);
        final int n15 = n14 ^ (int)(doubleToLongBits15 ^ doubleToLongBits15 >> 32);
        final long doubleToLongBits16 = Double.doubleToLongBits(this.m33);
        return n15 ^ (int)(doubleToLongBits16 ^ doubleToLongBits16 >> 32);
    }
    
    public final void transform(final Tuple4d tuple4d, final Tuple4d tuple4d2) {
        tuple4d2.set(this.m00 * tuple4d.x + this.m01 * tuple4d.y + this.m02 * tuple4d.z + this.m03 * tuple4d.w, this.m10 * tuple4d.x + this.m11 * tuple4d.y + this.m12 * tuple4d.z + this.m13 * tuple4d.w, this.m20 * tuple4d.x + this.m21 * tuple4d.y + this.m22 * tuple4d.z + this.m23 * tuple4d.w, this.m30 * tuple4d.x + this.m31 * tuple4d.y + this.m32 * tuple4d.z + this.m33 * tuple4d.w);
    }
    
    public final void transform(final Tuple4d tuple4d) {
        this.transform(tuple4d, tuple4d);
    }
    
    public final void transform(final Tuple4f tuple4f, final Tuple4f tuple4f2) {
        tuple4f2.set((float)(this.m00 * tuple4f.x + this.m01 * tuple4f.y + this.m02 * tuple4f.z + this.m03 * tuple4f.w), (float)(this.m10 * tuple4f.x + this.m11 * tuple4f.y + this.m12 * tuple4f.z + this.m13 * tuple4f.w), (float)(this.m20 * tuple4f.x + this.m21 * tuple4f.y + this.m22 * tuple4f.z + this.m23 * tuple4f.w), (float)(this.m30 * tuple4f.x + this.m31 * tuple4f.y + this.m32 * tuple4f.z + this.m33 * tuple4f.w));
    }
    
    public final void transform(final Tuple4f tuple4f) {
        this.transform(tuple4f, tuple4f);
    }
    
    public final void transform(final Point3d point3d, final Point3d point3d2) {
        point3d2.set(this.m00 * point3d.x + this.m01 * point3d.y + this.m02 * point3d.z + this.m03, this.m10 * point3d.x + this.m11 * point3d.y + this.m12 * point3d.z + this.m13, this.m20 * point3d.x + this.m21 * point3d.y + this.m22 * point3d.z + this.m23);
    }
    
    public final void transform(final Point3d point3d) {
        this.transform(point3d, point3d);
    }
    
    public final void transform(final Point3f point3f, final Point3f point3f2) {
        point3f2.set((float)(this.m00 * point3f.x + this.m01 * point3f.y + this.m02 * point3f.z + this.m03), (float)(this.m10 * point3f.x + this.m11 * point3f.y + this.m12 * point3f.z + this.m13), (float)(this.m20 * point3f.x + this.m21 * point3f.y + this.m22 * point3f.z + this.m23));
    }
    
    public final void transform(final Point3f point3f) {
        this.transform(point3f, point3f);
    }
    
    public final void transform(final Vector3d vector3d, final Vector3d vector3d2) {
        vector3d2.set(this.m00 * vector3d.x + this.m01 * vector3d.y + this.m02 * vector3d.z, this.m10 * vector3d.x + this.m11 * vector3d.y + this.m12 * vector3d.z, this.m20 * vector3d.x + this.m21 * vector3d.y + this.m22 * vector3d.z);
    }
    
    public final void transform(final Vector3d vector3d) {
        this.transform(vector3d, vector3d);
    }
    
    public final void transform(final Vector3f vector3f, final Vector3f vector3f2) {
        vector3f2.set((float)(this.m00 * vector3f.x + this.m01 * vector3f.y + this.m02 * vector3f.z), (float)(this.m10 * vector3f.x + this.m11 * vector3f.y + this.m12 * vector3f.z), (float)(this.m20 * vector3f.x + this.m21 * vector3f.y + this.m22 * vector3f.z));
    }
    
    public final void transform(final Vector3f vector3f) {
        this.transform(vector3f, vector3f);
    }
    
    public final void setRotation(final Matrix3d rotationScale) {
        final double svd = this.SVD(null, null);
        this.setRotationScale(rotationScale);
        this.mulRotationScale(svd);
    }
    
    public final void setRotation(final Matrix3f rotationScale) {
        final double svd = this.SVD(null, null);
        this.setRotationScale(rotationScale);
        this.mulRotationScale(svd);
    }
    
    public final void setRotation(final Quat4f quat4f) {
        final double svd = this.SVD(null, null);
        final double m03 = this.m03;
        final double m4 = this.m13;
        final double m5 = this.m23;
        final double m6 = this.m30;
        final double m7 = this.m31;
        final double m8 = this.m32;
        final double m9 = this.m33;
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
        final double svd = this.SVD(null, null);
        final double m03 = this.m03;
        final double m4 = this.m13;
        final double m5 = this.m23;
        final double m6 = this.m30;
        final double m7 = this.m31;
        final double m8 = this.m32;
        final double m9 = this.m33;
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
    
    public final void setRotation(final AxisAngle4d axisAngle4d) {
        final double svd = this.SVD(null, null);
        final double m03 = this.m03;
        final double m4 = this.m13;
        final double m5 = this.m23;
        final double m6 = this.m30;
        final double m7 = this.m31;
        final double m8 = this.m32;
        final double m9 = this.m33;
        this.set(axisAngle4d);
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
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 0.0;
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
    
    public final void negate(final Matrix4d matrix4d) {
        this.set(matrix4d);
        this.negate();
    }
    
    private void set(final double m00, final double m2, final double m3, final double m4, final double m5, final double m6, final double m7, final double m8, final double m9, final double m10, final double m11, final double m12, final double m13, final double m14, final double m15, final double m16) {
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
    
    private double SVD(final Matrix3d matrix3d, final Matrix4d matrix4d) {
        final double sqrt = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        if (matrix3d != null) {
            this.getRotationScale(matrix3d);
            final double n = 1.0 / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
            matrix3d.m00 *= n;
            matrix3d.m10 *= n;
            matrix3d.m20 *= n;
            final double n2 = 1.0 / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
            matrix3d.m01 *= n2;
            matrix3d.m11 *= n2;
            matrix3d.m21 *= n2;
            final double n3 = 1.0 / Math.sqrt(this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22);
            matrix3d.m02 *= n3;
            matrix3d.m12 *= n3;
            matrix3d.m22 *= n3;
        }
        if (matrix4d != null) {
            if (matrix4d != this) {
                matrix4d.setRotationScale(this);
            }
            final double n4 = 1.0 / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
            matrix4d.m00 *= n4;
            matrix4d.m10 *= n4;
            matrix4d.m20 *= n4;
            final double n5 = 1.0 / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
            matrix4d.m01 *= n5;
            matrix4d.m11 *= n5;
            matrix4d.m21 *= n5;
            final double n6 = 1.0 / Math.sqrt(this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22);
            matrix4d.m02 *= n6;
            matrix4d.m12 *= n6;
            matrix4d.m22 *= n6;
        }
        return sqrt;
    }
    
    private float SVD(final Matrix3f matrix3f) {
        final double sqrt = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        final double n = (sqrt == 0.0) ? 0.0 : (1.0 / sqrt);
        if (matrix3f != null) {
            this.getRotationScale(matrix3f);
            matrix3f.mul((float)n);
        }
        return (float)sqrt;
    }
    
    private void mulRotationScale(final double n) {
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
    
    private void setRotationScale(final Matrix4d matrix4d) {
        this.m00 = matrix4d.m00;
        this.m01 = matrix4d.m01;
        this.m02 = matrix4d.m02;
        this.m10 = matrix4d.m10;
        this.m11 = matrix4d.m11;
        this.m12 = matrix4d.m12;
        this.m20 = matrix4d.m20;
        this.m21 = matrix4d.m21;
        this.m22 = matrix4d.m22;
    }
    
    private void setTranslation(final Vector3f vector3f) {
        this.m03 = vector3f.x;
        this.m13 = vector3f.y;
        this.m23 = vector3f.z;
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
        this.m00 = 1.0 - (n16 + n18);
        this.m01 = n14 - n12;
        this.m02 = n15 + n11;
        this.m10 = n14 + n12;
        this.m11 = 1.0 - (n13 + n18);
        this.m12 = n17 - n10;
        this.m20 = n15 - n11;
        this.m21 = n17 + n10;
        this.m22 = 1.0 - (n13 + n16);
    }
    
    private void setFromAxisAngle(double n, double n2, double n3, final double n4) {
        final double n5 = 1.0 / Math.sqrt(n * n + n2 * n2 + n3 * n3);
        n *= n5;
        n2 *= n5;
        n3 *= n5;
        final double cos = Math.cos(n4);
        final double sin = Math.sin(n4);
        final double n6 = 1.0 - cos;
        this.m00 = cos + n * n * n6;
        this.m11 = cos + n2 * n2 * n6;
        this.m22 = cos + n3 * n3 * n6;
        final double n7 = n * n2 * n6;
        final double n8 = n3 * sin;
        this.m01 = n7 - n8;
        this.m10 = n7 + n8;
        final double n9 = n * n3 * n6;
        final double n10 = n2 * sin;
        this.m02 = n9 + n10;
        this.m20 = n9 - n10;
        final double n11 = n2 * n3 * n6;
        final double n12 = n * sin;
        this.m12 = n11 - n12;
        this.m21 = n11 + n12;
    }
}
