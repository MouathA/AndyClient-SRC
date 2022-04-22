package javax.vecmath;

import java.io.*;

public class Quat4f extends Tuple4f implements Serializable
{
    public Quat4f(final float n, final float n2, final float n3, final float n4) {
        super(n, n2, n3, n4);
    }
    
    public Quat4f(final float[] array) {
        super(array);
    }
    
    public Quat4f(final Quat4f quat4f) {
        super(quat4f);
    }
    
    public Quat4f(final Quat4d quat4d) {
        super(quat4d);
    }
    
    public Quat4f(final Tuple4d tuple4d) {
        super(tuple4d);
    }
    
    public Quat4f(final Tuple4f tuple4f) {
        super(tuple4f);
    }
    
    public Quat4f() {
    }
    
    public final void conjugate(final Quat4f quat4f) {
        this.x = -quat4f.x;
        this.y = -quat4f.y;
        this.z = -quat4f.z;
        this.w = quat4f.w;
    }
    
    public final void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void mul(final Quat4f quat4f, final Quat4f quat4f2) {
        this.set(quat4f.x * quat4f2.w + quat4f.w * quat4f2.x + quat4f.y * quat4f2.z - quat4f.z * quat4f2.y, quat4f.y * quat4f2.w + quat4f.w * quat4f2.y + quat4f.z * quat4f2.x - quat4f.x * quat4f2.z, quat4f.z * quat4f2.w + quat4f.w * quat4f2.z + quat4f.x * quat4f2.y - quat4f.y * quat4f2.x, quat4f.w * quat4f2.w - quat4f.x * quat4f2.x - quat4f.y * quat4f2.y - quat4f.z * quat4f2.z);
    }
    
    public final void mul(final Quat4f quat4f) {
        this.set(this.x * quat4f.w + this.w * quat4f.x + this.y * quat4f.z - this.z * quat4f.y, this.y * quat4f.w + this.w * quat4f.y + this.z * quat4f.x - this.x * quat4f.z, this.z * quat4f.w + this.w * quat4f.z + this.x * quat4f.y - this.y * quat4f.x, this.w * quat4f.w - this.x * quat4f.x - this.y * quat4f.y - this.z * quat4f.z);
    }
    
    public final void mulInverse(final Quat4f quat4f, final Quat4f quat4f2) {
        final double norm = this.norm();
        final double n = (norm == 0.0) ? norm : (1.0 / norm);
        this.set((float)((quat4f.x * quat4f2.w - quat4f.w * quat4f2.x - quat4f.y * quat4f2.z + quat4f.z * quat4f2.y) * n), (float)((quat4f.y * quat4f2.w - quat4f.w * quat4f2.y - quat4f.z * quat4f2.x + quat4f.x * quat4f2.z) * n), (float)((quat4f.z * quat4f2.w - quat4f.w * quat4f2.z - quat4f.x * quat4f2.y + quat4f.y * quat4f2.x) * n), (float)((quat4f.w * quat4f2.w + quat4f.x * quat4f2.x + quat4f.y * quat4f2.y + quat4f.z * quat4f2.z) * n));
    }
    
    public final void mulInverse(final Quat4f quat4f) {
        final double norm = this.norm();
        final double n = (norm == 0.0) ? norm : (1.0 / norm);
        this.set((float)((this.x * quat4f.w - this.w * quat4f.x - this.y * quat4f.z + this.z * quat4f.y) * n), (float)((this.y * quat4f.w - this.w * quat4f.y - this.z * quat4f.x + this.x * quat4f.z) * n), (float)((this.z * quat4f.w - this.w * quat4f.z - this.x * quat4f.y + this.y * quat4f.x) * n), (float)((this.w * quat4f.w + this.x * quat4f.x + this.y * quat4f.y + this.z * quat4f.z) * n));
    }
    
    private final double norm() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public final void inverse(final Quat4f quat4f) {
        final double norm = quat4f.norm();
        this.x = (float)(-quat4f.x / norm);
        this.y = (float)(-quat4f.y / norm);
        this.z = (float)(-quat4f.z / norm);
        this.w = (float)(quat4f.w / norm);
    }
    
    public final void inverse() {
        final double norm = this.norm();
        this.x = (float)(-this.x / norm);
        this.y = (float)(-this.y / norm);
        this.z = (float)(-this.z / norm);
        this.w /= (float)norm;
    }
    
    public final void normalize(final Quat4f quat4f) {
        final double sqrt = Math.sqrt(quat4f.norm());
        this.x = (float)(quat4f.x / sqrt);
        this.y = (float)(quat4f.y / sqrt);
        this.z = (float)(quat4f.z / sqrt);
        this.w = (float)(quat4f.w / sqrt);
    }
    
    public final void normalize() {
        final float n = (float)Math.sqrt(this.norm());
        this.x /= n;
        this.y /= n;
        this.z /= n;
        this.w /= n;
    }
    
    public final void set(final Matrix4f matrix4f) {
        this.setFromMat(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22);
    }
    
    public final void set(final Matrix4d matrix4d) {
        this.setFromMat(matrix4d.m00, matrix4d.m01, matrix4d.m02, matrix4d.m10, matrix4d.m11, matrix4d.m12, matrix4d.m20, matrix4d.m21, matrix4d.m22);
    }
    
    public final void set(final Matrix3f matrix3f) {
        this.setFromMat(matrix3f.m00, matrix3f.m01, matrix3f.m02, matrix3f.m10, matrix3f.m11, matrix3f.m12, matrix3f.m20, matrix3f.m21, matrix3f.m22);
    }
    
    public final void set(final Matrix3d matrix3d) {
        this.setFromMat(matrix3d.m00, matrix3d.m01, matrix3d.m02, matrix3d.m10, matrix3d.m11, matrix3d.m12, matrix3d.m20, matrix3d.m21, matrix3d.m22);
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        final float n = (float)(Math.sin(0.5 * axisAngle4f.angle) / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z));
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w = (float)Math.cos(0.5 * axisAngle4f.angle);
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.x = (float)axisAngle4d.x;
        this.y = (float)axisAngle4d.y;
        this.z = (float)axisAngle4d.z;
        final float n = (float)(Math.sin(0.5 * axisAngle4d.angle) / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z));
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w = (float)Math.cos(0.5 * axisAngle4d.angle);
    }
    
    public final void interpolate(final Quat4f quat4f, final double n) {
        this.normalize();
        final double sqrt = Math.sqrt(quat4f.norm());
        final double n2 = quat4f.x / sqrt;
        final double n3 = quat4f.y / sqrt;
        final double n4 = quat4f.z / sqrt;
        final double n5 = quat4f.w / sqrt;
        final double n6 = this.x * n2 + this.y * n3 + this.z * n4 + this.w * n5;
        if (1.0 <= Math.abs(n6)) {
            return;
        }
        final double acos = Math.acos(n6);
        final double sin = Math.sin(acos);
        if (sin == 0.0) {
            return;
        }
        final double n7 = Math.sin((1.0 - n) * acos) / sin;
        final double n8 = Math.sin(n * acos) / sin;
        this.x = (float)(n7 * this.x + n8 * n2);
        this.y = (float)(n7 * this.y + n8 * n3);
        this.z = (float)(n7 * this.z + n8 * n4);
        this.w = (float)(n7 * this.w + n8 * n5);
    }
    
    public final void interpolate(final Quat4f quat4f, final Quat4f quat4f2, final double n) {
        this.set(quat4f);
        this.interpolate(quat4f2, n);
    }
    
    private void setFromMat(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        final double n10 = n + n5 + n9;
        if (n10 >= 0.0) {
            final double sqrt = Math.sqrt(n10 + 1.0);
            this.w = (float)(sqrt * 0.5);
            final double n11 = 0.5 / sqrt;
            this.x = (float)((n8 - n6) * n11);
            this.y = (float)((n3 - n7) * n11);
            this.z = (float)((n4 - n2) * n11);
        }
        else {
            final double max = Math.max(Math.max(n, n5), n9);
            if (max == n) {
                final double sqrt2 = Math.sqrt(n - (n5 + n9) + 1.0);
                this.x = (float)(sqrt2 * 0.5);
                final double n12 = 0.5 / sqrt2;
                this.y = (float)((n2 + n4) * n12);
                this.z = (float)((n7 + n3) * n12);
                this.w = (float)((n8 - n6) * n12);
            }
            else if (max == n5) {
                final double sqrt3 = Math.sqrt(n5 - (n9 + n) + 1.0);
                this.y = (float)(sqrt3 * 0.5);
                final double n13 = 0.5 / sqrt3;
                this.z = (float)((n6 + n8) * n13);
                this.x = (float)((n2 + n4) * n13);
                this.w = (float)((n3 - n7) * n13);
            }
            else {
                final double sqrt4 = Math.sqrt(n9 - (n + n5) + 1.0);
                this.z = (float)(sqrt4 * 0.5);
                final double n14 = 0.5 / sqrt4;
                this.x = (float)((n7 + n3) * n14);
                this.y = (float)((n6 + n8) * n14);
                this.w = (float)((n4 - n2) * n14);
            }
        }
    }
}
