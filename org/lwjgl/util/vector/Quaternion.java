package org.lwjgl.util.vector;

import java.nio.*;

public class Quaternion extends Vector implements ReadableVector4f
{
    private static final long serialVersionUID = 1L;
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Quaternion() {
        this.setIdentity();
    }
    
    public Quaternion(final ReadableVector4f readableVector4f) {
        this.set(readableVector4f);
    }
    
    public Quaternion(final float n, final float n2, final float n3, final float n4) {
        this.set(n, n2, n3, n4);
    }
    
    public void set(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Quaternion set(final ReadableVector4f readableVector4f) {
        this.x = readableVector4f.getX();
        this.y = readableVector4f.getY();
        this.z = readableVector4f.getZ();
        this.w = readableVector4f.getW();
        return this;
    }
    
    public Quaternion setIdentity() {
        return setIdentity(this);
    }
    
    public static Quaternion setIdentity(final Quaternion quaternion) {
        quaternion.x = 0.0f;
        quaternion.y = 0.0f;
        quaternion.z = 0.0f;
        quaternion.w = 1.0f;
        return quaternion;
    }
    
    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    
    public static Quaternion normalise(final Quaternion quaternion, Quaternion quaternion2) {
        final float n = 1.0f / quaternion.length();
        if (quaternion2 == null) {
            quaternion2 = new Quaternion();
        }
        quaternion2.set(quaternion.x * n, quaternion.y * n, quaternion.z * n, quaternion.w * n);
        return quaternion2;
    }
    
    public Quaternion normalise(final Quaternion quaternion) {
        return normalise(this, quaternion);
    }
    
    public static float dot(final Quaternion quaternion, final Quaternion quaternion2) {
        return quaternion.x * quaternion2.x + quaternion.y * quaternion2.y + quaternion.z * quaternion2.z + quaternion.w * quaternion2.w;
    }
    
    public Quaternion negate(final Quaternion quaternion) {
        return negate(this, quaternion);
    }
    
    public static Quaternion negate(final Quaternion quaternion, Quaternion quaternion2) {
        if (quaternion2 == null) {
            quaternion2 = new Quaternion();
        }
        quaternion2.x = -quaternion.x;
        quaternion2.y = -quaternion.y;
        quaternion2.z = -quaternion.z;
        quaternion2.w = quaternion.w;
        return quaternion2;
    }
    
    @Override
    public Vector negate() {
        return negate(this, this);
    }
    
    @Override
    public Vector load(final FloatBuffer floatBuffer) {
        this.x = floatBuffer.get();
        this.y = floatBuffer.get();
        this.z = floatBuffer.get();
        this.w = floatBuffer.get();
        return this;
    }
    
    @Override
    public Vector scale(final float n) {
        return scale(n, this, this);
    }
    
    public static Quaternion scale(final float n, final Quaternion quaternion, Quaternion quaternion2) {
        if (quaternion2 == null) {
            quaternion2 = new Quaternion();
        }
        quaternion2.x = quaternion.x * n;
        quaternion2.y = quaternion.y * n;
        quaternion2.z = quaternion.z * n;
        quaternion2.w = quaternion.w * n;
        return quaternion2;
    }
    
    @Override
    public Vector store(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.x);
        floatBuffer.put(this.y);
        floatBuffer.put(this.z);
        floatBuffer.put(this.w);
        return this;
    }
    
    public final float getX() {
        return this.x;
    }
    
    public final float getY() {
        return this.y;
    }
    
    public final void setX(final float x) {
        this.x = x;
    }
    
    public final void setY(final float y) {
        this.y = y;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public void setW(final float w) {
        this.w = w;
    }
    
    public float getW() {
        return this.w;
    }
    
    @Override
    public String toString() {
        return "Quaternion: " + this.x + " " + this.y + " " + this.z + " " + this.w;
    }
    
    public static Quaternion mul(final Quaternion quaternion, final Quaternion quaternion2, Quaternion quaternion3) {
        if (quaternion3 == null) {
            quaternion3 = new Quaternion();
        }
        quaternion3.set(quaternion.x * quaternion2.w + quaternion.w * quaternion2.x + quaternion.y * quaternion2.z - quaternion.z * quaternion2.y, quaternion.y * quaternion2.w + quaternion.w * quaternion2.y + quaternion.z * quaternion2.x - quaternion.x * quaternion2.z, quaternion.z * quaternion2.w + quaternion.w * quaternion2.z + quaternion.x * quaternion2.y - quaternion.y * quaternion2.x, quaternion.w * quaternion2.w - quaternion.x * quaternion2.x - quaternion.y * quaternion2.y - quaternion.z * quaternion2.z);
        return quaternion3;
    }
    
    public static Quaternion mulInverse(final Quaternion quaternion, final Quaternion quaternion2, Quaternion quaternion3) {
        final float lengthSquared = quaternion2.lengthSquared();
        final float n = (lengthSquared == 0.0) ? lengthSquared : (1.0f / lengthSquared);
        if (quaternion3 == null) {
            quaternion3 = new Quaternion();
        }
        quaternion3.set((quaternion.x * quaternion2.w - quaternion.w * quaternion2.x - quaternion.y * quaternion2.z + quaternion.z * quaternion2.y) * n, (quaternion.y * quaternion2.w - quaternion.w * quaternion2.y - quaternion.z * quaternion2.x + quaternion.x * quaternion2.z) * n, (quaternion.z * quaternion2.w - quaternion.w * quaternion2.z - quaternion.x * quaternion2.y + quaternion.y * quaternion2.x) * n, (quaternion.w * quaternion2.w + quaternion.x * quaternion2.x + quaternion.y * quaternion2.y + quaternion.z * quaternion2.z) * n);
        return quaternion3;
    }
    
    public final void setFromAxisAngle(final Vector4f vector4f) {
        this.x = vector4f.x;
        this.y = vector4f.y;
        this.z = vector4f.z;
        final float n = (float)(Math.sin(0.5 * vector4f.w) / (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z));
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w = (float)Math.cos(0.5 * vector4f.w);
    }
    
    public final Quaternion setFromMatrix(final Matrix4f matrix4f) {
        return setFromMatrix(matrix4f, this);
    }
    
    public static Quaternion setFromMatrix(final Matrix4f matrix4f, final Quaternion quaternion) {
        return quaternion.setFromMat(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22);
    }
    
    public final Quaternion setFromMatrix(final Matrix3f matrix3f) {
        return setFromMatrix(matrix3f, this);
    }
    
    public static Quaternion setFromMatrix(final Matrix3f matrix3f, final Quaternion quaternion) {
        return quaternion.setFromMat(matrix3f.m00, matrix3f.m01, matrix3f.m02, matrix3f.m10, matrix3f.m11, matrix3f.m12, matrix3f.m20, matrix3f.m21, matrix3f.m22);
    }
    
    private Quaternion setFromMat(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        final float n10 = n + n5 + n9;
        if (n10 >= 0.0) {
            final float n11 = (float)Math.sqrt(n10 + 1.0);
            this.w = n11 * 0.5f;
            final float n12 = 0.5f / n11;
            this.x = (n8 - n6) * n12;
            this.y = (n3 - n7) * n12;
            this.z = (n4 - n2) * n12;
        }
        else {
            final float max = Math.max(Math.max(n, n5), n9);
            if (max == n) {
                final float n13 = (float)Math.sqrt(n - (n5 + n9) + 1.0);
                this.x = n13 * 0.5f;
                final float n14 = 0.5f / n13;
                this.y = (n2 + n4) * n14;
                this.z = (n7 + n3) * n14;
                this.w = (n8 - n6) * n14;
            }
            else if (max == n5) {
                final float n15 = (float)Math.sqrt(n5 - (n9 + n) + 1.0);
                this.y = n15 * 0.5f;
                final float n16 = 0.5f / n15;
                this.z = (n6 + n8) * n16;
                this.x = (n2 + n4) * n16;
                this.w = (n3 - n7) * n16;
            }
            else {
                final float n17 = (float)Math.sqrt(n9 - (n + n5) + 1.0);
                this.z = n17 * 0.5f;
                final float n18 = 0.5f / n17;
                this.x = (n7 + n3) * n18;
                this.y = (n6 + n8) * n18;
                this.w = (n4 - n2) * n18;
            }
        }
        return this;
    }
}
