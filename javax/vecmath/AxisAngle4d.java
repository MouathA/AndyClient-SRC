package javax.vecmath;

import java.io.*;

public class AxisAngle4d implements Serializable
{
    public double x;
    public double y;
    public double z;
    public double angle;
    
    public AxisAngle4d(final double n, final double n2, final double n3, final double n4) {
        this.set(n, n2, n3, n4);
    }
    
    public AxisAngle4d(final double[] array) {
        this.set(array);
    }
    
    public AxisAngle4d(final AxisAngle4d axisAngle4d) {
        this.set(axisAngle4d);
    }
    
    public AxisAngle4d(final AxisAngle4f axisAngle4f) {
        this.set(axisAngle4f);
    }
    
    public AxisAngle4d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 1.0;
        this.angle = 0.0;
    }
    
    public AxisAngle4d(final Vector3d vector3d, final double angle) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = angle;
    }
    
    public final void set(final Vector3d vector3d, final double angle) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = angle;
    }
    
    public final void set(final double x, final double y, final double z, final double angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
    
    public final void set(final double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.angle = array[3];
    }
    
    public final void set(final AxisAngle4d axisAngle4d) {
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        this.angle = axisAngle4d.angle;
    }
    
    public final void set(final AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }
    
    public final void get(final double[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.angle;
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
    
    public final void set(final Quat4f quat4f) {
        this.setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }
    
    public final void set(final Quat4d quat4d) {
        this.setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }
    
    private void setFromMat(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        final double n10 = (n + n5 + n9 - 1.0) * 0.5;
        this.x = n8 - n6;
        this.y = n3 - n7;
        this.z = n4 - n2;
        this.angle = Math.atan2(0.5 * Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z), n10);
    }
    
    private void setFromQuat(final double x, final double y, final double z, final double n) {
        this.angle = 2.0 * Math.atan2(Math.sqrt(x * x + y * y + z * z), n);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
    }
    
    public boolean equals(final AxisAngle4d axisAngle4d) {
        return axisAngle4d != null && this.x == axisAngle4d.x && this.y == axisAngle4d.y && this.z == axisAngle4d.z && this.angle == axisAngle4d.angle;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof AxisAngle4d && this.equals((AxisAngle4d)o);
    }
    
    public boolean epsilonEquals(final AxisAngle4d axisAngle4d, final double n) {
        return Math.abs(axisAngle4d.x - this.x) <= n && Math.abs(axisAngle4d.y - this.y) <= n && Math.abs(axisAngle4d.z - this.z) <= n && Math.abs(axisAngle4d.angle - this.angle) <= n;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.x);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.y);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.z);
        final long doubleToLongBits4 = Double.doubleToLongBits(this.angle);
        return (int)(doubleToLongBits ^ doubleToLongBits >> 32 ^ doubleToLongBits2 ^ doubleToLongBits2 >> 32 ^ doubleToLongBits3 ^ doubleToLongBits3 >> 32 ^ doubleToLongBits4 ^ doubleToLongBits4 >> 32);
    }
}
