package net.minecraft.pathfinding;

import net.minecraft.world.pathfinder.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathFinder
{
    private Path path;
    private PathPoint[] pathOptions;
    private NodeProcessor field_176190_c;
    private static final String __OBFID;
    
    public PathFinder(final NodeProcessor field_176190_c) {
        this.path = new Path();
        this.pathOptions = new PathPoint[32];
        this.field_176190_c = field_176190_c;
    }
    
    public PathEntity func_176188_a(final IBlockAccess blockAccess, final Entity entity, final Entity entity2, final float n) {
        return this.func_176189_a(blockAccess, entity, entity2.posX, entity2.getEntityBoundingBox().minY, entity2.posZ, n);
    }
    
    public PathEntity func_180782_a(final IBlockAccess blockAccess, final Entity entity, final BlockPos blockPos, final float n) {
        return this.func_176189_a(blockAccess, entity, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, n);
    }
    
    private PathEntity func_176189_a(final IBlockAccess blockAccess, final Entity entity, final double n, final double n2, final double n3, final float n4) {
        this.path.clearPath();
        this.field_176190_c.func_176162_a(blockAccess, entity);
        final PathEntity func_176187_a = this.func_176187_a(entity, this.field_176190_c.func_176161_a(entity), this.field_176190_c.func_176160_a(entity, n, n2, n3), n4);
        this.field_176190_c.func_176163_a();
        return func_176187_a;
    }
    
    private PathEntity func_176187_a(final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        pathPoint.totalPathDistance = 0.0f;
        pathPoint.distanceToNext = pathPoint.distanceToSquared(pathPoint2);
        pathPoint.distanceToTarget = pathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(pathPoint);
        PathPoint pathPoint3 = pathPoint;
        while (!this.path.isPathEmpty()) {
            final PathPoint dequeue = this.path.dequeue();
            if (dequeue.equals(pathPoint2)) {
                return this.createEntityPath(pathPoint, pathPoint2);
            }
            if (dequeue.distanceToSquared(pathPoint2) < pathPoint3.distanceToSquared(pathPoint2)) {
                pathPoint3 = dequeue;
            }
            dequeue.visited = true;
            while (0 < this.field_176190_c.func_176164_a(this.pathOptions, entity, dequeue, pathPoint2, n)) {
                final PathPoint pathPoint4 = this.pathOptions[0];
                final float totalPathDistance = dequeue.totalPathDistance + dequeue.distanceToSquared(pathPoint4);
                if (totalPathDistance < n * 2.0f && (!pathPoint4.isAssigned() || totalPathDistance < pathPoint4.totalPathDistance)) {
                    pathPoint4.previous = dequeue;
                    pathPoint4.totalPathDistance = totalPathDistance;
                    pathPoint4.distanceToNext = pathPoint4.distanceToSquared(pathPoint2);
                    if (pathPoint4.isAssigned()) {
                        this.path.changeDistance(pathPoint4, pathPoint4.totalPathDistance + pathPoint4.distanceToNext);
                    }
                    else {
                        pathPoint4.distanceToTarget = pathPoint4.totalPathDistance + pathPoint4.distanceToNext;
                        this.path.addPoint(pathPoint4);
                    }
                }
                int n2 = 0;
                ++n2;
            }
        }
        if (pathPoint3 == pathPoint) {
            return null;
        }
        return this.createEntityPath(pathPoint, pathPoint3);
    }
    
    private PathEntity createEntityPath(final PathPoint pathPoint, final PathPoint pathPoint2) {
        int n = 0;
        for (PathPoint previous = pathPoint2; previous.previous != null; previous = previous.previous) {
            ++n;
        }
        final PathPoint[] array = { null };
        PathPoint previous2 = pathPoint2;
        --n;
        array[1] = pathPoint2;
        while (previous2.previous != null) {
            previous2 = previous2.previous;
            --n;
            array[1] = previous2;
        }
        return new PathEntity(array);
    }
    
    static {
        __OBFID = "CL_00000576";
    }
}
