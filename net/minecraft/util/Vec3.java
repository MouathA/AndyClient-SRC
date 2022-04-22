package net.minecraft.util;

public class Vec3
{
    public final double xCoord;
    public final double yCoord;
    public final double zCoord;
    private static final String __OBFID;
    
    public Vec3(double xCoord, double yCoord, double zCoord) {
        if (xCoord == -0.0) {
            xCoord = 0.0;
        }
        if (yCoord == -0.0) {
            yCoord = 0.0;
        }
        if (zCoord == -0.0) {
            zCoord = 0.0;
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }
    
    public Vec3 subtractReverse(final Vec3 vec3) {
        return new Vec3(vec3.xCoord - this.xCoord, vec3.yCoord - this.yCoord, vec3.zCoord - this.zCoord);
    }
    
    public Vec3 normalize() {
        final double n = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (n < 1.0E-4) ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.xCoord / n, this.yCoord / n, this.zCoord / n);
    }
    
    public double dotProduct(final Vec3 vec3) {
        return this.xCoord * vec3.xCoord + this.yCoord * vec3.yCoord + this.zCoord * vec3.zCoord;
    }
    
    public Vec3 crossProduct(final Vec3 vec3) {
        return new Vec3(this.yCoord * vec3.zCoord - this.zCoord * vec3.yCoord, this.zCoord * vec3.xCoord - this.xCoord * vec3.zCoord, this.xCoord * vec3.yCoord - this.yCoord * vec3.xCoord);
    }
    
    public Vec3 subtract(final Vec3 vec3) {
        return this.subtract(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public Vec3 subtract(final double n, final double n2, final double n3) {
        return this.addVector(-n, -n2, -n3);
    }
    
    public Vec3 add(final Vec3 vec3) {
        return this.addVector(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public Vec3 addVector(final double n, final double n2, final double n3) {
        return new Vec3(this.xCoord + n, this.yCoord + n2, this.zCoord + n3);
    }
    
    public double distanceTo(final Vec3 vec3) {
        final double n = vec3.xCoord - this.xCoord;
        final double n2 = vec3.yCoord - this.yCoord;
        final double n3 = vec3.zCoord - this.zCoord;
        return MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
    }
    
    public double squareDistanceTo(final Vec3 vec3) {
        final double n = vec3.xCoord - this.xCoord;
        final double n2 = vec3.yCoord - this.yCoord;
        final double n3 = vec3.zCoord - this.zCoord;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public double lengthVector() {
        return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }
    
    public Vec3 getIntermediateWithXValue(final Vec3 vec3, final double n) {
        final double n2 = vec3.xCoord - this.xCoord;
        final double n3 = vec3.yCoord - this.yCoord;
        final double n4 = vec3.zCoord - this.zCoord;
        if (n2 * n2 < 1.0000000116860974E-7) {
            return null;
        }
        final double n5 = (n - this.xCoord) / n2;
        return (n5 >= 0.0 && n5 <= 1.0) ? new Vec3(this.xCoord + n2 * n5, this.yCoord + n3 * n5, this.zCoord + n4 * n5) : null;
    }
    
    public Vec3 getIntermediateWithYValue(final Vec3 vec3, final double n) {
        final double n2 = vec3.xCoord - this.xCoord;
        final double n3 = vec3.yCoord - this.yCoord;
        final double n4 = vec3.zCoord - this.zCoord;
        if (n3 * n3 < 1.0000000116860974E-7) {
            return null;
        }
        final double n5 = (n - this.yCoord) / n3;
        return (n5 >= 0.0 && n5 <= 1.0) ? new Vec3(this.xCoord + n2 * n5, this.yCoord + n3 * n5, this.zCoord + n4 * n5) : null;
    }
    
    public Vec3 getIntermediateWithZValue(final Vec3 vec3, final double n) {
        final double n2 = vec3.xCoord - this.xCoord;
        final double n3 = vec3.yCoord - this.yCoord;
        final double n4 = vec3.zCoord - this.zCoord;
        if (n4 * n4 < 1.0000000116860974E-7) {
            return null;
        }
        final double n5 = (n - this.zCoord) / n4;
        return (n5 >= 0.0 && n5 <= 1.0) ? new Vec3(this.xCoord + n2 * n5, this.yCoord + n3 * n5, this.zCoord + n4 * n5) : null;
    }
    
    @Override
    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }
    
    public Vec3 rotatePitch(final float n) {
        final float cos = MathHelper.cos(n);
        final float sin = MathHelper.sin(n);
        return new Vec3(this.xCoord, this.yCoord * cos + this.zCoord * sin, this.zCoord * cos - this.yCoord * sin);
    }
    
    public Vec3 rotateYaw(final float n) {
        final float cos = MathHelper.cos(n);
        final float sin = MathHelper.sin(n);
        return new Vec3(this.xCoord * cos + this.zCoord * sin, this.yCoord, this.zCoord * cos - this.xCoord * sin);
    }
    
    static {
        __OBFID = "CL_00000612";
    }
}
