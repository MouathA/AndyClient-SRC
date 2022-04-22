package javax.vecmath;

import java.io.*;

public class Matrix3f implements Serializable
{
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;
    
    public Matrix3f(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        this.set(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public Matrix3f(final float[] array) {
        this.set(array);
    }
    
    public Matrix3f(final Matrix3d matrix3d) {
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
    
    public Matrix3f(final Matrix3f matrix3f) {
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
    
    public Matrix3f() {
        this.setZero();
    }
    
    @Override
    public String toString() {
        final String s = "\ud487\ud482\ud485\ud48e\ud4c5\ud498\ud48e\ud49b\ud48a\ud499\ud48a\ud49f\ud484\ud499";
        return "[" + s + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "]" + s + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "]" + s + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "] ]";
    }
    
    public final void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }
    
    public final void setScale(final float n) {
        this.SVD(this);
        this.mul(n);
    }
    
    public final void setElement(final int n, final int n2, final float m22) {
        if (n == 0) {
            if (n2 == 0) {
                this.m00 = m22;
            }
            else if (n2 == 1) {
                this.m01 = m22;
            }
            else {
                if (n2 != 2) {
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
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
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
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
                    throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
                }
                this.m22 = m22;
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
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
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
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
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
            throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n2);
        }
    }
    
    public final void setRow(final int n, final float m20, final float m21, final float m22) {
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
    
    public final void setRow(final int n, final Vector3f vector3f) {
        if (n == 0) {
            this.m00 = vector3f.x;
            this.m01 = vector3f.y;
            this.m02 = vector3f.z;
        }
        else if (n == 1) {
            this.m10 = vector3f.x;
            this.m11 = vector3f.y;
            this.m12 = vector3f.z;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            this.m20 = vector3f.x;
            this.m21 = vector3f.y;
            this.m22 = vector3f.z;
        }
    }
    
    public final void getRow(final int n, final float[] array) {
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
    
    public final void getRow(final int n, final Vector3f vector3f) {
        if (n == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m01;
            vector3f.z = this.m02;
        }
        else if (n == 1) {
            vector3f.x = this.m10;
            vector3f.y = this.m11;
            vector3f.z = this.m12;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("row must be 0 to 2 and is " + n);
            }
            vector3f.x = this.m20;
            vector3f.y = this.m21;
            vector3f.z = this.m22;
        }
    }
    
    public final void setRow(final int n, final float[] array) {
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
    
    public final void setColumn(final int n, final float m02, final float m3, final float m4) {
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
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            this.m02 = m02;
            this.m12 = m3;
            this.m22 = m4;
        }
    }
    
    public final void setColumn(final int n, final Vector3f vector3f) {
        if (n == 0) {
            this.m00 = vector3f.x;
            this.m10 = vector3f.y;
            this.m20 = vector3f.z;
        }
        else if (n == 1) {
            this.m01 = vector3f.x;
            this.m11 = vector3f.y;
            this.m21 = vector3f.z;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            this.m02 = vector3f.x;
            this.m12 = vector3f.y;
            this.m22 = vector3f.z;
        }
    }
    
    public final void setColumn(final int n, final float[] array) {
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
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            this.m02 = array[0];
            this.m12 = array[1];
            this.m22 = array[2];
        }
    }
    
    public final void getColumn(final int n, final Vector3f vector3f) {
        if (n == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m10;
            vector3f.z = this.m20;
        }
        else if (n == 1) {
            vector3f.x = this.m01;
            vector3f.y = this.m11;
            vector3f.z = this.m21;
        }
        else {
            if (n != 2) {
                throw new ArrayIndexOutOfBoundsException("column must be 0 to 2 and is " + n);
            }
            vector3f.x = this.m02;
            vector3f.y = this.m12;
            vector3f.z = this.m22;
        }
    }
    
    public final void getColumn(final int n, final float[] array) {
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
    
    public final float getScale() {
        return this.SVD(null);
    }
    
    public final void add(final float n) {
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
    
    public final void add(final float n, final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.add(n);
    }
    
    public final void add(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.set(matrix3f.m00 + matrix3f2.m00, matrix3f.m01 + matrix3f2.m01, matrix3f.m02 + matrix3f2.m02, matrix3f.m10 + matrix3f2.m10, matrix3f.m11 + matrix3f2.m11, matrix3f.m12 + matrix3f2.m12, matrix3f.m20 + matrix3f2.m20, matrix3f.m21 + matrix3f2.m21, matrix3f.m22 + matrix3f2.m22);
    }
    
    public final void add(final Matrix3f matrix3f) {
        this.m00 += matrix3f.m00;
        this.m01 += matrix3f.m01;
        this.m02 += matrix3f.m02;
        this.m10 += matrix3f.m10;
        this.m11 += matrix3f.m11;
        this.m12 += matrix3f.m12;
        this.m20 += matrix3f.m20;
        this.m21 += matrix3f.m21;
        this.m22 += matrix3f.m22;
    }
    
    public final void sub(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.set(matrix3f.m00 - matrix3f2.m00, matrix3f.m01 - matrix3f2.m01, matrix3f.m02 - matrix3f2.m02, matrix3f.m10 - matrix3f2.m10, matrix3f.m11 - matrix3f2.m11, matrix3f.m12 - matrix3f2.m12, matrix3f.m20 - matrix3f2.m20, matrix3f.m21 - matrix3f2.m21, matrix3f.m22 - matrix3f2.m22);
    }
    
    public final void sub(final Matrix3f matrix3f) {
        this.m00 -= matrix3f.m00;
        this.m01 -= matrix3f.m01;
        this.m02 -= matrix3f.m02;
        this.m10 -= matrix3f.m10;
        this.m11 -= matrix3f.m11;
        this.m12 -= matrix3f.m12;
        this.m20 -= matrix3f.m20;
        this.m21 -= matrix3f.m21;
        this.m22 -= matrix3f.m22;
    }
    
    public final void transpose() {
        final float m01 = this.m01;
        this.m01 = this.m10;
        this.m10 = m01;
        final float m2 = this.m02;
        this.m02 = this.m20;
        this.m20 = m2;
        final float m3 = this.m12;
        this.m12 = this.m21;
        this.m21 = m3;
    }
    
    public final void transpose(final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.transpose();
    }
    
    public final void set(final Quat4f quat4f) {
        this.setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.setFromAxisAngle(axisAngle4f.x, axisAngle4f.y, axisAngle4f.z, axisAngle4f.angle);
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.setFromAxisAngle(axisAngle4d.x, axisAngle4d.y, axisAngle4d.z, axisAngle4d.angle);
    }
    
    public final void set(final Quat4d quat4d) {
        this.setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
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
    
    public final void set(final Matrix3d matrix3d) {
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
    
    public final void set(final float[] array) {
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
    
    public final void invert(final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.invert();
    }
    
    public final void invert() {
        final double n = this.determinant();
        if (n == 0.0) {
            return;
        }
        final double n2 = 1.0 / n;
        this.set(this.m11 * this.m22 - this.m12 * this.m21, this.m02 * this.m21 - this.m01 * this.m22, this.m01 * this.m12 - this.m02 * this.m11, this.m12 * this.m20 - this.m10 * this.m22, this.m00 * this.m22 - this.m02 * this.m20, this.m02 * this.m10 - this.m00 * this.m12, this.m10 * this.m21 - this.m11 * this.m20, this.m01 * this.m20 - this.m00 * this.m21, this.m00 * this.m11 - this.m01 * this.m10);
        this.mul((float)n2);
    }
    
    public final float determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m21 * this.m12) - this.m01 * (this.m10 * this.m22 - this.m20 * this.m12) + this.m02 * (this.m10 * this.m21 - this.m20 * this.m11);
    }
    
    public final void set(final float m22) {
        this.m00 = m22;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = m22;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = m22;
    }
    
    public final void rotX(final float n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = (float)cos;
        this.m12 = (float)(-sin);
        this.m20 = 0.0f;
        this.m21 = (float)sin;
        this.m22 = (float)cos;
    }
    
    public final void rotY(final float n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = (float)cos;
        this.m01 = 0.0f;
        this.m02 = (float)sin;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = (float)(-sin);
        this.m21 = 0.0f;
        this.m22 = (float)cos;
    }
    
    public final void rotZ(final float n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        this.m00 = (float)cos;
        this.m01 = (float)(-sin);
        this.m02 = 0.0f;
        this.m10 = (float)sin;
        this.m11 = (float)cos;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }
    
    public final void mul(final float n) {
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
    
    public final void mul(final float n, final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.mul(n);
    }
    
    public final void mul(final Matrix3f matrix3f) {
        this.mul(this, matrix3f);
    }
    
    public final void mul(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.set(matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m10 + matrix3f.m02 * matrix3f2.m20, matrix3f.m00 * matrix3f2.m01 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m21, matrix3f.m00 * matrix3f2.m02 + matrix3f.m01 * matrix3f2.m12 + matrix3f.m02 * matrix3f2.m22, matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m12 * matrix3f2.m20, matrix3f.m10 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m21, matrix3f.m10 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m12 * matrix3f2.m22, matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20, matrix3f.m20 * matrix3f2.m01 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21, matrix3f.m20 * matrix3f2.m02 + matrix3f.m21 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22);
    }
    
    public final void mulNormalize(final Matrix3f matrix3f) {
        this.mul(matrix3f);
        this.SVD(this);
    }
    
    public final void mulNormalize(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.mul(matrix3f, matrix3f2);
        this.SVD(this);
    }
    
    public final void mulTransposeBoth(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.mul(matrix3f2, matrix3f);
        this.transpose();
    }
    
    public final void mulTransposeRight(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.set(matrix3f.m00 * matrix3f2.m00 + matrix3f.m01 * matrix3f2.m01 + matrix3f.m02 * matrix3f2.m02, matrix3f.m00 * matrix3f2.m10 + matrix3f.m01 * matrix3f2.m11 + matrix3f.m02 * matrix3f2.m12, matrix3f.m00 * matrix3f2.m20 + matrix3f.m01 * matrix3f2.m21 + matrix3f.m02 * matrix3f2.m22, matrix3f.m10 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m02, matrix3f.m10 * matrix3f2.m10 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m12 * matrix3f2.m12, matrix3f.m10 * matrix3f2.m20 + matrix3f.m11 * matrix3f2.m21 + matrix3f.m12 * matrix3f2.m22, matrix3f.m20 * matrix3f2.m00 + matrix3f.m21 * matrix3f2.m01 + matrix3f.m22 * matrix3f2.m02, matrix3f.m20 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m12, matrix3f.m20 * matrix3f2.m20 + matrix3f.m21 * matrix3f2.m21 + matrix3f.m22 * matrix3f2.m22);
    }
    
    public final void mulTransposeLeft(final Matrix3f matrix3f, final Matrix3f matrix3f2) {
        this.set(matrix3f.m00 * matrix3f2.m00 + matrix3f.m10 * matrix3f2.m10 + matrix3f.m20 * matrix3f2.m20, matrix3f.m00 * matrix3f2.m01 + matrix3f.m10 * matrix3f2.m11 + matrix3f.m20 * matrix3f2.m21, matrix3f.m00 * matrix3f2.m02 + matrix3f.m10 * matrix3f2.m12 + matrix3f.m20 * matrix3f2.m22, matrix3f.m01 * matrix3f2.m00 + matrix3f.m11 * matrix3f2.m10 + matrix3f.m21 * matrix3f2.m20, matrix3f.m01 * matrix3f2.m01 + matrix3f.m11 * matrix3f2.m11 + matrix3f.m21 * matrix3f2.m21, matrix3f.m01 * matrix3f2.m02 + matrix3f.m11 * matrix3f2.m12 + matrix3f.m21 * matrix3f2.m22, matrix3f.m02 * matrix3f2.m00 + matrix3f.m12 * matrix3f2.m10 + matrix3f.m22 * matrix3f2.m20, matrix3f.m02 * matrix3f2.m01 + matrix3f.m12 * matrix3f2.m11 + matrix3f.m22 * matrix3f2.m21, matrix3f.m02 * matrix3f2.m02 + matrix3f.m12 * matrix3f2.m12 + matrix3f.m22 * matrix3f2.m22);
    }
    
    public final void normalize() {
        this.SVD(this);
    }
    
    public final void normalize(final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.SVD(this);
    }
    
    public final void normalizeCP() {
        this.mul((float)Math.pow(this.determinant(), -0.3333333333333333));
    }
    
    public final void normalizeCP(final Matrix3f matrix3f) {
        this.set(matrix3f);
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
        //     5: instanceof      Ljavax/vecmath/Matrix3f;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Matrix3f;
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
    
    public boolean epsilonEquals(final Matrix3f matrix3f, final double n) {
        return Math.abs(this.m00 - matrix3f.m00) <= n && Math.abs(this.m01 - matrix3f.m01) <= n && Math.abs(this.m02 - matrix3f.m02) <= n && Math.abs(this.m10 - matrix3f.m10) <= n && Math.abs(this.m11 - matrix3f.m11) <= n && Math.abs(this.m12 - matrix3f.m12) <= n && Math.abs(this.m20 - matrix3f.m20) <= n && Math.abs(this.m21 - matrix3f.m21) <= n && Math.abs(this.m22 - matrix3f.m22) <= n;
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.m00) ^ Float.floatToIntBits(this.m01) ^ Float.floatToIntBits(this.m02) ^ Float.floatToIntBits(this.m10) ^ Float.floatToIntBits(this.m11) ^ Float.floatToIntBits(this.m12) ^ Float.floatToIntBits(this.m20) ^ Float.floatToIntBits(this.m21) ^ Float.floatToIntBits(this.m22);
    }
    
    public final void setZero() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
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
    
    public final void negate(final Matrix3f matrix3f) {
        this.set(matrix3f);
        this.negate();
    }
    
    public final void transform(final Tuple3f tuple3f) {
        this.transform(tuple3f, tuple3f);
    }
    
    public final void transform(final Tuple3f tuple3f, final Tuple3f tuple3f2) {
        tuple3f2.set(this.m00 * tuple3f.x + this.m01 * tuple3f.y + this.m02 * tuple3f.z, this.m10 * tuple3f.x + this.m11 * tuple3f.y + this.m12 * tuple3f.z, this.m20 * tuple3f.x + this.m21 * tuple3f.y + this.m22 * tuple3f.z);
    }
    
    private void set(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6, final float m7, final float m8, final float m9) {
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
    
    private float SVD(final Matrix3f matrix3f) {
        final float n = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0);
        final float n2 = (n == 0.0f) ? 0.0f : (1.0f / n);
        if (matrix3f != null) {
            if (matrix3f != this) {
                matrix3f.set(this);
            }
            matrix3f.mul(n2);
        }
        return n;
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
