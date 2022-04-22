package javax.vecmath;

import java.io.*;

public class Quat4d extends Tuple4d implements Serializable
{
    public Quat4d(final double n, final double n2, final double n3, final double n4) {
        super(n, n2, n3, n4);
    }
    
    public Quat4d(final double[] array) {
        super(array);
    }
    
    public Quat4d(final Quat4d quat4d) {
        super(quat4d);
    }
    
    public Quat4d(final Quat4f quat4f) {
        super(quat4f);
    }
    
    public Quat4d(final Tuple4d tuple4d) {
        super(tuple4d);
    }
    
    public Quat4d(final Tuple4f tuple4f) {
        super(tuple4f);
    }
    
    public Quat4d() {
    }
    
    public final void conjugate(final Quat4d quat4d) {
        this.x = -quat4d.x;
        this.y = -quat4d.y;
        this.z = -quat4d.z;
        this.w = quat4d.w;
    }
    
    public final void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void mul(final Quat4d quat4d, final Quat4d quat4d2) {
        this.set(quat4d.x * quat4d2.w + quat4d.w * quat4d2.x + quat4d.y * quat4d2.z - quat4d.z * quat4d2.y, quat4d.y * quat4d2.w + quat4d.w * quat4d2.y + quat4d.z * quat4d2.x - quat4d.x * quat4d2.z, quat4d.z * quat4d2.w + quat4d.w * quat4d2.z + quat4d.x * quat4d2.y - quat4d.y * quat4d2.x, quat4d.w * quat4d2.w - quat4d.x * quat4d2.x - quat4d.y * quat4d2.y - quat4d.z * quat4d2.z);
    }
    
    public final void mul(final Quat4d quat4d) {
        this.set(this.x * quat4d.w + this.w * quat4d.x + this.y * quat4d.z - this.z * quat4d.y, this.y * quat4d.w + this.w * quat4d.y + this.z * quat4d.x - this.x * quat4d.z, this.z * quat4d.w + this.w * quat4d.z + this.x * quat4d.y - this.y * quat4d.x, this.w * quat4d.w - this.x * quat4d.x - this.y * quat4d.y - this.z * quat4d.z);
    }
    
    public final void mulInverse(final Quat4d quat4d, final Quat4d quat4d2) {
        final double norm = this.norm();
        final double n = (norm == 0.0) ? norm : (1.0 / norm);
        this.set((quat4d.x * quat4d2.w - quat4d.w * quat4d2.x - quat4d.y * quat4d2.z + quat4d.z * quat4d2.y) * n, (quat4d.y * quat4d2.w - quat4d.w * quat4d2.y - quat4d.z * quat4d2.x + quat4d.x * quat4d2.z) * n, (quat4d.z * quat4d2.w - quat4d.w * quat4d2.z - quat4d.x * quat4d2.y + quat4d.y * quat4d2.x) * n, (quat4d.w * quat4d2.w + quat4d.x * quat4d2.x + quat4d.y * quat4d2.y + quat4d.z * quat4d2.z) * n);
    }
    
    public final void mulInverse(final Quat4d quat4d) {
        final double norm = this.norm();
        final double n = (norm == 0.0) ? norm : (1.0 / norm);
        this.set((this.x * quat4d.w - this.w * quat4d.x - this.y * quat4d.z + this.z * quat4d.y) * n, (this.y * quat4d.w - this.w * quat4d.y - this.z * quat4d.x + this.x * quat4d.z) * n, (this.z * quat4d.w - this.w * quat4d.z - this.x * quat4d.y + this.y * quat4d.x) * n, (this.w * quat4d.w + this.x * quat4d.x + this.y * quat4d.y + this.z * quat4d.z) * n);
    }
    
    private final double norm() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public final void inverse(final Quat4d quat4d) {
        final double norm = quat4d.norm();
        this.x = -quat4d.x / norm;
        this.y = -quat4d.y / norm;
        this.z = -quat4d.z / norm;
        this.w = quat4d.w / norm;
    }
    
    public final void inverse() {
        final double norm = this.norm();
        this.x = -this.x / norm;
        this.y = -this.y / norm;
        this.z = -this.z / norm;
        this.w /= norm;
    }
    
    public final void normalize(final Quat4d quat4d) {
        final double sqrt = Math.sqrt(quat4d.norm());
        this.x = quat4d.x / sqrt;
        this.y = quat4d.y / sqrt;
        this.z = quat4d.z / sqrt;
        this.w = quat4d.w / sqrt;
    }
    
    public final void normalize() {
        final double sqrt = Math.sqrt(this.norm());
        this.x /= sqrt;
        this.y /= sqrt;
        this.z /= sqrt;
        this.w /= sqrt;
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
        final double n = Math.sin(0.5 * axisAngle4f.angle) / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w = Math.cos(0.5 * axisAngle4f.angle);
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        final double n = Math.sin(0.5 * axisAngle4d.angle) / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w = Math.cos(0.5 * axisAngle4d.angle);
    }
    
    public final void interpolate(final Quat4d quat4d, final double n) {
        this.normalize();
        final double sqrt = Math.sqrt(quat4d.norm());
        final double n2 = quat4d.x / sqrt;
        final double n3 = quat4d.y / sqrt;
        final double n4 = quat4d.z / sqrt;
        final double n5 = quat4d.w / sqrt;
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
        this.x = n7 * this.x + n8 * n2;
        this.y = n7 * this.y + n8 * n3;
        this.z = n7 * this.z + n8 * n4;
        this.w = n7 * this.w + n8 * n5;
    }
    
    public final void interpolate(final Quat4d quat4d, final Quat4d quat4d2, final double n) {
        this.set(quat4d);
        this.interpolate(quat4d2, n);
    }
    
    private void setFromMat(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        final double n10 = n + n5 + n9;
        if (n10 >= 0.0) {
            final double sqrt = Math.sqrt(n10 + 1.0);
            this.w = sqrt * 0.5;
            final double n11 = 0.5 / sqrt;
            this.x = (n8 - n6) * n11;
            this.y = (n3 - n7) * n11;
            this.z = (n4 - n2) * n11;
        }
        else {
            final double max = Math.max(Math.max(n, n5), n9);
            if (max == n) {
                final double sqrt2 = Math.sqrt(n - (n5 + n9) + 1.0);
                this.x = sqrt2 * 0.5;
                final double n12 = 0.5 / sqrt2;
                this.y = (n2 + n4) * n12;
                this.z = (n7 + n3) * n12;
                this.w = (n8 - n6) * n12;
            }
            else if (max == n5) {
                final double sqrt3 = Math.sqrt(n5 - (n9 + n) + 1.0);
                this.y = sqrt3 * 0.5;
                final double n13 = 0.5 / sqrt3;
                this.z = (n6 + n8) * n13;
                this.x = (n2 + n4) * n13;
                this.w = (n3 - n7) * n13;
            }
            else {
                final double sqrt4 = Math.sqrt(n9 - (n + n5) + 1.0);
                this.z = sqrt4 * 0.5;
                final double n14 = 0.5 / sqrt4;
                this.x = (n7 + n3) * n14;
                this.y = (n6 + n8) * n14;
                this.w = (n4 - n2) * n14;
            }
        }
    }
}
