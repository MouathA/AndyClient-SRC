package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathEntity
{
    private final PathPoint[] points;
    private int currentPathIndex;
    private int pathLength;
    private static final String __OBFID;
    
    public PathEntity(final PathPoint[] points) {
        this.points = points;
        this.pathLength = points.length;
    }
    
    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }
    
    public boolean isFinished() {
        return this.currentPathIndex >= this.pathLength;
    }
    
    public PathPoint getFinalPathPoint() {
        return (this.pathLength > 0) ? this.points[this.pathLength - 1] : null;
    }
    
    public PathPoint getPathPointFromIndex(final int n) {
        return this.points[n];
    }
    
    public int getCurrentPathLength() {
        return this.pathLength;
    }
    
    public void setCurrentPathLength(final int pathLength) {
        this.pathLength = pathLength;
    }
    
    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }
    
    public void setCurrentPathIndex(final int currentPathIndex) {
        this.currentPathIndex = currentPathIndex;
    }
    
    public Vec3 getVectorFromIndex(final Entity entity, final int n) {
        return new Vec3(this.points[n].xCoord + (int)(entity.width + 1.0f) * 0.5, this.points[n].yCoord, this.points[n].zCoord + (int)(entity.width + 1.0f) * 0.5);
    }
    
    public Vec3 getPosition(final Entity entity) {
        return this.getVectorFromIndex(entity, this.currentPathIndex);
    }
    
    public boolean isSamePath(final PathEntity pathEntity) {
        if (pathEntity == null) {
            return false;
        }
        if (pathEntity.points.length != this.points.length) {
            return false;
        }
        while (0 < this.points.length) {
            if (this.points[0].xCoord != pathEntity.points[0].xCoord || this.points[0].yCoord != pathEntity.points[0].yCoord || this.points[0].zCoord != pathEntity.points[0].zCoord) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public boolean isDestinationSame(final Vec3 vec3) {
        final PathPoint finalPathPoint = this.getFinalPathPoint();
        return finalPathPoint != null && (finalPathPoint.xCoord == (int)vec3.xCoord && finalPathPoint.zCoord == (int)vec3.zCoord);
    }
    
    static {
        __OBFID = "CL_00000575";
    }
}
