package javax.vecmath;

import java.io.*;

public class Matrix3d implements Serializable
{
    public double m00;
    public double m01;
    public double m02;
    public double m10;
    public double m11;
    public double m12;
    public double m20;
    public double m21;
    public double m22;
    
    public Matrix3d(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        this.set(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public Matrix3d(final double[] array) {
        this.set(array);
    }
    
    public Matrix3d(final Matrix3d matrix3d) {
        this.set(matrix3d);
    }
    
    public Matrix3d(final Matrix3f matrix3f) {
        this.set(matrix3f);
    }
    
    public Matrix3d() {
        this.setZero();
    }
    
    @Override
    public String toString() {
        final String s = "\uca2e\uca2b\uca2c\uca27\uca6c\uca31\uca27\uca32\uca23\uca30\uca23\uca36\uca2d\uca30";
        return "[" + s + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "]" + s + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "]" + s + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "] ]";
    }
    
    public final void setIdentity() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }
    
    public final void setScale(final double n) {
        this.SVD(this);
        this.m00 *= n;
        this.m11 *= n;
        this.m22 *= n;
    }
    
    public final void setElement(final int n, final int n2, final double m22) {
        if (n == 0) {
            if (n2 == 0) {
                this.m00 = m22;
            }
            else if (n2 == 1) {
                this.m01 = m22;
            }
            else {
                if (n2 != 2) {
                    throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
                }
                this.m02 = m22;
            }
        }
        else if (n == 1) {
            if (n2 == 0) {
                this.m10 = m22;
            }
            else if (n2 == 1) {
                this.m11 = m22;
            }
            else {
                if (n2 != 2) {
                    throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
                }
                this.m12 = m22;
            }
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            if (n2 == 0) {
                this.m20 = m22;
            }
            else if (n2 == 1) {
                this.m21 = m22;
            }
            else {
                if (n2 != 2) {
                    throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
                }
                this.m22 = m22;
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
            throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
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
            throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            if (n2 == 0) {
                return this.m20;
            }
            if (n2 == 1) {
                return this.m21;
            }
            if (n2 == 2) {
                return this.m22;
            }
            throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n2);
        }
    }
    
    public final void setRow(final int n, final double m20, final double m21, final double m22) {
        if (n == 0) {
            this.m00 = m20;
            this.m01 = m21;
            this.m02 = m22;
        }
        else if (n == 1) {
            this.m10 = m20;
            this.m11 = m21;
            this.m12 = m22;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            this.m20 = m20;
            this.m21 = m21;
            this.m22 = m22;
        }
    }
    
    public final void setRow(final int n, final Vector3d vector3d) {
        if (n == 0) {
            this.m00 = vector3d.x;
            this.m01 = vector3d.y;
            this.m02 = vector3d.z;
        }
        else if (n == 1) {
            this.m10 = vector3d.x;
            this.m11 = vector3d.y;
            this.m12 = vector3d.z;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            this.m20 = vector3d.x;
            this.m21 = vector3d.y;
            this.m22 = vector3d.z;
        }
    }
    
    public final void setRow(final int n, final double[] array) {
        if (n == 0) {
            this.m00 = array[0];
            this.m01 = array[1];
            this.m02 = array[2];
        }
        else if (n == 1) {
            this.m10 = array[0];
            this.m11 = array[1];
            this.m12 = array[2];
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            this.m20 = array[0];
            this.m21 = array[1];
            this.m22 = array[2];
        }
    }
    
    public final void getRow(final int n, final double[] array) {
        if (n == 0) {
            array[0] = this.m00;
            array[1] = this.m01;
            array[2] = this.m02;
        }
        else if (n == 1) {
            array[0] = this.m10;
            array[1] = this.m11;
            array[2] = this.m12;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            array[0] = this.m20;
            array[1] = this.m21;
            array[2] = this.m22;
        }
    }
    
    public final void getRow(final int n, final Vector3d vector3d) {
        if (n == 0) {
            vector3d.x = this.m00;
            vector3d.y = this.m01;
            vector3d.z = this.m02;
        }
        else if (n == 1) {
            vector3d.x = this.m10;
            vector3d.y = this.m11;
            vector3d.z = this.m12;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            vector3d.x = this.m20;
            vector3d.y = this.m21;
            vector3d.z = this.m22;
        }
    }
    
    public final void setColumn(final int n, final double m02, final double m3, final double m4) {
        if (n == 0) {
            this.m00 = m02;
            this.m10 = m3;
            this.m20 = m4;
        }
        else if (n == 1) {
            this.m01 = m02;
            this.m11 = m3;
            this.m21 = m4;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n);
            }
            this.m02 = m02;
            this.m12 = m3;
            this.m22 = m4;
        }
    }
    
    public final void setColumn(final int n, final Vector3d vector3d) {
        if (n == 0) {
            this.m00 = vector3d.x;
            this.m10 = vector3d.y;
            this.m20 = vector3d.z;
        }
        else if (n == 1) {
            this.m01 = vector3d.x;
            this.m11 = vector3d.y;
            this.m21 = vector3d.z;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n);
            }
            this.m02 = vector3d.x;
            this.m12 = vector3d.y;
            this.m22 = vector3d.z;
        }
    }
    
    public final void setColumn(final int n, final double[] array) {
        if (n == 0) {
            this.m00 = array[0];
            this.m10 = array[1];
            this.m20 = array[2];
        }
        else if (n == 1) {
            this.m01 = array[0];
            this.m11 = array[1];
            this.m21 = array[2];
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("col must be 0 to 2 and is " + n);
            }
            this.m02 = array[0];
            this.m12 = array[1];
            this.m22 = array[2];
        }
    }
    
    public final void getColumn(final int n, final Vector3d vector3d) {
        if (n == 0) {
            vector3d.x = this.m00;
            vector3d.y = this.m10;
            vector3d.z = this.m20;
        }
        else if (n == 1) {
            vector3d.x = this.m01;
            vector3d.y = this.m11;
            vector3d.z = this.m21;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            vector3d.x = this.m02;
            vector3d.y = this.m12;
            vector3d.z = this.m22;
        }
    }
    
    public final void getColumn(final int n, final double[] array) {
        if (n == 0) {
            array[0] = this.m00;
            array[1] = this.m10;
            array[2] = this.m20;
        }
        else if (n == 1) {
            array[0] = this.m01;
            array[1] = this.m11;
            array[2] = this.m21;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            array[0] = this.m02;
            array[1] = this.m12;
            array[2] = this.m22;
        }
    }
    
    public final double getScale() {
        return this.SVD(null);
    }
    
    public final void add(final double n) {
        this.m00 += n;
        this.m01 += n;
        this.m02 += n;
        this.m10 += n;
        this.m11 += n;
        this.m12 += n;
        this.m20 += n;
        this.m21 += n;
        this.m22 += n;
    }
    
    public final void add(final double n, final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.add(n);
    }
    
    public final void add(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.set(matrix3d.m00 + matrix3d2.m00, matrix3d.m01 + matrix3d2.m01, matrix3d.m02 + matrix3d2.m02, matrix3d.m10 + matrix3d2.m10, matrix3d.m11 + matrix3d2.m11, matrix3d.m12 + matrix3d2.m12, matrix3d.m20 + matrix3d2.m20, matrix3d.m21 + matrix3d2.m21, matrix3d.m22 + matrix3d2.m22);
    }
    
    public final void add(final Matrix3d matrix3d) {
        this.m00 += matrix3d.m00;
        this.m01 += matrix3d.m01;
        this.m02 += matrix3d.m02;
        this.m10 += matrix3d.m10;
        this.m11 += matrix3d.m11;
        this.m12 += matrix3d.m12;
        this.m20 += matrix3d.m20;
        this.m21 += matrix3d.m21;
        this.m22 += matrix3d.m22;
    }
    
    public final void sub(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.set(matrix3d.m00 - matrix3d2.m00, matrix3d.m01 - matrix3d2.m01, matrix3d.m02 - matrix3d2.m02, matrix3d.m10 - matrix3d2.m10, matrix3d.m11 - matrix3d2.m11, matrix3d.m12 - matrix3d2.m12, matrix3d.m20 - matrix3d2.m20, matrix3d.m21 - matrix3d2.m21, matrix3d.m22 - matrix3d2.m22);
    }
    
    public final void sub(final Matrix3d matrix3d) {
        this.m00 -= matrix3d.m00;
        this.m01 -= matrix3d.m01;
        this.m02 -= matrix3d.m02;
        this.m10 -= matrix3d.m10;
        this.m11 -= matrix3d.m11;
        this.m12 -= matrix3d.m12;
        this.m20 -= matrix3d.m20;
        this.m21 -= matrix3d.m21;
        this.m22 -= matrix3d.m22;
    }
    
    public final void transpose() {
        final double m01 = this.m01;
        this.m01 = this.m10;
        this.m10 = m01;
        final double m2 = this.m02;
        this.m02 = this.m20;
        this.m20 = m2;
        final double m3 = this.m12;
        this.m12 = this.m21;
        this.m21 = m3;
    }
    
    public final void transpose(final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.transpose();
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
    
    public final void set(final Matrix3d matrix3d) {
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
    
    public final void set(final Matrix3f matrix3f) {
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
    
    public final void set(final double[] array) {
        this.m00 = array[0];
        this.m01 = array[1];
        this.m02 = array[2];
        this.m10 = array[3];
        this.m11 = array[4];
        this.m12 = array[5];
        this.m20 = array[6];
        this.m21 = array[7];
        this.m22 = array[8];
    }
    
    public final void invert(final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.invert();
    }
    
    public final void invert() {
        final double determinant = this.determinant();
        if (determinant == 0.0) {
            return;
        }
        final double n = 1.0 / determinant;
        this.set(this.m11 * this.m22 - this.m12 * this.m21, this.m02 * this.m21 - this.m01 * this.m22, this.m01 * this.m12 - this.m02 * this.m11, this.m12 * this.m20 - this.m10 * this.m22, this.m00 * this.m22 - this.m02 * this.m20, this.m02 * this.m10 - this.m00 * this.m12, this.m10 * this.m21 - this.m11 * this.m20, this.m01 * this.m20 - this.m00 * this.m21, this.m00 * this.m11 - this.m01 * this.m10);
        this.mul(n);
    }
    
    public final double determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m21 * this.m12) - this.m01 * (this.m10 * this.m22 - this.m20 * this.m12) + this.m02 * (this.m10 * this.m21 - this.m20 * this.m11);
    }
    
    public final void set(final double m22) {
        this.m00 = m22;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = m22;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = m22;
    }
    
    public final void rotX(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = cos;
        this.m12 = -sin;
        this.m20 = 0.0;
        this.m21 = sin;
        this.m22 = cos;
    }
    
    public final void rotY(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = cos;
        this.m01 = 0.0;
        this.m02 = sin;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = -sin;
        this.m21 = 0.0;
        this.m22 = cos;
    }
    
    public final void rotZ(final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = cos;
        this.m01 = -sin;
        this.m02 = 0.0;
        this.m10 = sin;
        this.m11 = cos;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }
    
    public final void mul(final double n) {
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
    
    public final void mul(final double n, final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.mul(n);
    }
    
    public final void mul(final Matrix3d matrix3d) {
        this.mul(this, matrix3d);
    }
    
    public final void mul(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.set(matrix3d.m00 * matrix3d2.m00 + matrix3d.m01 * matrix3d2.m10 + matrix3d.m02 * matrix3d2.m20, matrix3d.m00 * matrix3d2.m01 + matrix3d.m01 * matrix3d2.m11 + matrix3d.m02 * matrix3d2.m21, matrix3d.m00 * matrix3d2.m02 + matrix3d.m01 * matrix3d2.m12 + matrix3d.m02 * matrix3d2.m22, matrix3d.m10 * matrix3d2.m00 + matrix3d.m11 * matrix3d2.m10 + matrix3d.m12 * matrix3d2.m20, matrix3d.m10 * matrix3d2.m01 + matrix3d.m11 * matrix3d2.m11 + matrix3d.m12 * matrix3d2.m21, matrix3d.m10 * matrix3d2.m02 + matrix3d.m11 * matrix3d2.m12 + matrix3d.m12 * matrix3d2.m22, matrix3d.m20 * matrix3d2.m00 + matrix3d.m21 * matrix3d2.m10 + matrix3d.m22 * matrix3d2.m20, matrix3d.m20 * matrix3d2.m01 + matrix3d.m21 * matrix3d2.m11 + matrix3d.m22 * matrix3d2.m21, matrix3d.m20 * matrix3d2.m02 + matrix3d.m21 * matrix3d2.m12 + matrix3d.m22 * matrix3d2.m22);
    }
    
    public final void mulNormalize(final Matrix3d matrix3d) {
        this.mul(matrix3d);
        this.SVD(this);
    }
    
    public final void mulNormalize(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.mul(matrix3d, matrix3d2);
        this.SVD(this);
    }
    
    public final void mulTransposeBoth(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.mul(matrix3d2, matrix3d);
        this.transpose();
    }
    
    public final void mulTransposeRight(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.set(matrix3d.m00 * matrix3d2.m00 + matrix3d.m01 * matrix3d2.m01 + matrix3d.m02 * matrix3d2.m02, matrix3d.m00 * matrix3d2.m10 + matrix3d.m01 * matrix3d2.m11 + matrix3d.m02 * matrix3d2.m12, matrix3d.m00 * matrix3d2.m20 + matrix3d.m01 * matrix3d2.m21 + matrix3d.m02 * matrix3d2.m22, matrix3d.m10 * matrix3d2.m00 + matrix3d.m11 * matrix3d2.m01 + matrix3d.m12 * matrix3d2.m02, matrix3d.m10 * matrix3d2.m10 + matrix3d.m11 * matrix3d2.m11 + matrix3d.m12 * matrix3d2.m12, matrix3d.m10 * matrix3d2.m20 + matrix3d.m11 * matrix3d2.m21 + matrix3d.m12 * matrix3d2.m22, matrix3d.m20 * matrix3d2.m00 + matrix3d.m21 * matrix3d2.m01 + matrix3d.m22 * matrix3d2.m02, matrix3d.m20 * matrix3d2.m10 + matrix3d.m21 * matrix3d2.m11 + matrix3d.m22 * matrix3d2.m12, matrix3d.m20 * matrix3d2.m20 + matrix3d.m21 * matrix3d2.m21 + matrix3d.m22 * matrix3d2.m22);
    }
    
    public final void mulTransposeLeft(final Matrix3d matrix3d, final Matrix3d matrix3d2) {
        this.set(matrix3d.m00 * matrix3d2.m00 + matrix3d.m10 * matrix3d2.m10 + matrix3d.m20 * matrix3d2.m20, matrix3d.m00 * matrix3d2.m01 + matrix3d.m10 * matrix3d2.m11 + matrix3d.m20 * matrix3d2.m21, matrix3d.m00 * matrix3d2.m02 + matrix3d.m10 * matrix3d2.m12 + matrix3d.m20 * matrix3d2.m22, matrix3d.m01 * matrix3d2.m00 + matrix3d.m11 * matrix3d2.m10 + matrix3d.m21 * matrix3d2.m20, matrix3d.m01 * matrix3d2.m01 + matrix3d.m11 * matrix3d2.m11 + matrix3d.m21 * matrix3d2.m21, matrix3d.m01 * matrix3d2.m02 + matrix3d.m11 * matrix3d2.m12 + matrix3d.m21 * matrix3d2.m22, matrix3d.m02 * matrix3d2.m00 + matrix3d.m12 * matrix3d2.m10 + matrix3d.m22 * matrix3d2.m20, matrix3d.m02 * matrix3d2.m01 + matrix3d.m12 * matrix3d2.m11 + matrix3d.m22 * matrix3d2.m21, matrix3d.m02 * matrix3d2.m02 + matrix3d.m12 * matrix3d2.m12 + matrix3d.m22 * matrix3d2.m22);
    }
    
    public final void normalize() {
        this.SVD(this);
    }
    
    public final void normalize(final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.SVD(this);
    }
    
    public final void normalizeCP() {
        this.mul(Math.pow(Math.abs(this.determinant()), -0.3333333333333333));
    }
    
    public final void normalizeCP(final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.normalizeCP();
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
        //     5: instanceof      Ljavax/vecmath/Matrix3d;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Matrix3d;
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
    
    public boolean epsilonEquals(final Matrix3d matrix3d, final double n) {
        return Math.abs(this.m00 - matrix3d.m00) <= n && Math.abs(this.m01 - matrix3d.m01) <= n && Math.abs(this.m02 - matrix3d.m02) <= n && Math.abs(this.m10 - matrix3d.m10) <= n && Math.abs(this.m11 - matrix3d.m11) <= n && Math.abs(this.m12 - matrix3d.m12) <= n && Math.abs(this.m20 - matrix3d.m20) <= n && Math.abs(this.m21 - matrix3d.m21) <= n && Math.abs(this.m22 - matrix3d.m22) <= n;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.m00);
        final int n = (int)(doubleToLongBits ^ doubleToLongBits >> 32);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.m01);
        final int n2 = n ^ (int)(doubleToLongBits2 ^ doubleToLongBits2 >> 32);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.m02);
        final int n3 = n2 ^ (int)(doubleToLongBits3 ^ doubleToLongBits3 >> 32);
        final long doubleToLongBits4 = Double.doubleToLongBits(this.m10);
        final int n4 = n3 ^ (int)(doubleToLongBits4 ^ doubleToLongBits4 >> 32);
        final long doubleToLongBits5 = Double.doubleToLongBits(this.m11);
        final int n5 = n4 ^ (int)(doubleToLongBits5 ^ doubleToLongBits5 >> 32);
        final long doubleToLongBits6 = Double.doubleToLongBits(this.m12);
        final int n6 = n5 ^ (int)(doubleToLongBits6 ^ doubleToLongBits6 >> 32);
        final long doubleToLongBits7 = Double.doubleToLongBits(this.m20);
        final int n7 = n6 ^ (int)(doubleToLongBits7 ^ doubleToLongBits7 >> 32);
        final long doubleToLongBits8 = Double.doubleToLongBits(this.m21);
        final int n8 = n7 ^ (int)(doubleToLongBits8 ^ doubleToLongBits8 >> 32);
        final long doubleToLongBits9 = Double.doubleToLongBits(this.m22);
        return n8 ^ (int)(doubleToLongBits9 ^ doubleToLongBits9 >> 32);
    }
    
    public final void setZero() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
    }
    
    public final void negate() {
        this.m00 = -this.m00;
        this.m01 = -this.m01;
        this.m02 = -this.m02;
        this.m10 = -this.m10;
        this.m11 = -this.m11;
        this.m12 = -this.m12;
        this.m20 = -this.m20;
        this.m21 = -this.m21;
        this.m22 = -this.m22;
    }
    
    public final void negate(final Matrix3d matrix3d) {
        this.set(matrix3d);
        this.negate();
    }
    
    public final void transform(final Tuple3d tuple3d) {
        this.transform(tuple3d, tuple3d);
    }
    
    public final void transform(final Tuple3d tuple3d, final Tuple3d tuple3d2) {
        tuple3d2.set(this.m00 * tuple3d.x + this.m01 * tuple3d.y + this.m02 * tuple3d.z, this.m10 * tuple3d.x + this.m11 * tuple3d.y + this.m12 * tuple3d.z, this.m20 * tuple3d.x + this.m21 * tuple3d.y + this.m22 * tuple3d.z);
    }
    
    private void set(final double m00, final double m2, final double m3, final double m4, final double m5, final double m6, final double m7, final double m8, final double m9) {
        this.m00 = m00;
        this.m01 = m2;
        this.m02 = m3;
        this.m10 = m4;
        this.m11 = m5;
        this.m12 = m6;
        this.m20 = m7;
        this.m21 = m8;
        this.m22 = m9;
    }
    
    private double SVD(final Matrix3d matrix3d) {
        final double sqrt = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        if (matrix3d != null) {
            final double n = 1.0 / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
            matrix3d.m00 = this.m00 * n;
            matrix3d.m10 = this.m10 * n;
            matrix3d.m20 = this.m20 * n;
            final double n2 = 1.0 / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
            matrix3d.m01 = this.m01 * n2;
            matrix3d.m11 = this.m11 * n2;
            matrix3d.m21 = this.m21 * n2;
            final double n3 = 1.0 / Math.sqrt(this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22);
            matrix3d.m02 = this.m02 * n3;
            matrix3d.m12 = this.m12 * n3;
            matrix3d.m22 = this.m22 * n3;
        }
        return sqrt;
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
