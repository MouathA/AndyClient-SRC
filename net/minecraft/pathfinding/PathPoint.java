package net.minecraft.pathfinding;

import net.minecraft.util.*;

public class PathPoint
{
    public final int xCoord;
    public final int yCoord;
    public final int zCoord;
    private final int hash;
    int index;
    float totalPathDistance;
    float distanceToNext;
    float distanceToTarget;
    PathPoint previous;
    public boolean visited;
    private static final String __OBFID;
    
    public PathPoint(final int xCoord, final int yCoord, final int zCoord) {
        this.index = -1;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.hash = makeHash(xCoord, yCoord, zCoord);
    }
    
    public static int makeHash(final int n, final int n2, final int n3) {
        return (n2 & 0xFF) | (n & 0x7FFF) << 8 | (n3 & 0x7FFF) << 24 | ((n < 0) ? Integer.MIN_VALUE : 0) | ((n3 < 0) ? 32768 : 0);
    }
    
    public float distanceTo(final PathPoint pathPoint) {
        final float n = (float)(pathPoint.xCoord - this.xCoord);
        final float n2 = (float)(pathPoint.yCoord - this.yCoord);
        final float n3 = (float)(pathPoint.zCoord - this.zCoord);
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }
    
    public float distanceToSquared(final PathPoint pathPoint) {
        final float n = (float)(pathPoint.xCoord - this.xCoord);
        final float n2 = (float)(pathPoint.yCoord - this.yCoord);
        final float n3 = (float)(pathPoint.zCoord - this.zCoord);
        return n * n + n2 * n2 + n3 * n3;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PathPoint)) {
            return false;
        }
        final PathPoint pathPoint = (PathPoint)o;
        return this.hash == pathPoint.hash && this.xCoord == pathPoint.xCoord && this.yCoord == pathPoint.yCoord && this.zCoord == pathPoint.zCoord;
    }
    
    @Override
    public int hashCode() {
        return this.hash;
    }
    
    public boolean isAssigned() {
        return this.index >= 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.xCoord) + ", " + this.yCoord + ", " + this.zCoord;
    }
    
    static {
        __OBFID = "CL_00000574";
    }
}
