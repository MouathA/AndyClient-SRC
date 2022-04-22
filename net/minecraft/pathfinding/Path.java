package net.minecraft.pathfinding;

public class Path
{
    private PathPoint[] pathPoints;
    private int count;
    private static final String __OBFID;
    
    public Path() {
        this.pathPoints = new PathPoint[1024];
    }
    
    public PathPoint addPoint(final PathPoint pathPoint) {
        if (pathPoint.index >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.count == this.pathPoints.length) {
            final PathPoint[] pathPoints = new PathPoint[this.count << 1];
            System.arraycopy(this.pathPoints, 0, pathPoints, 0, this.count);
            this.pathPoints = pathPoints;
        }
        this.pathPoints[this.count] = pathPoint;
        pathPoint.index = this.count;
        this.sortBack(this.count++);
        return pathPoint;
    }
    
    public void clearPath() {
        this.count = 0;
    }
    
    public PathPoint dequeue() {
        final PathPoint pathPoint = this.pathPoints[0];
        final PathPoint[] pathPoints = this.pathPoints;
        final int n = 0;
        final PathPoint[] pathPoints2 = this.pathPoints;
        final int count = this.count - 1;
        this.count = count;
        pathPoints[n] = pathPoints2[count];
        this.pathPoints[this.count] = null;
        if (this.count > 0) {
            this.sortForward(0);
        }
        pathPoint.index = -1;
        return pathPoint;
    }
    
    public void changeDistance(final PathPoint pathPoint, final float distanceToTarget) {
        final float distanceToTarget2 = pathPoint.distanceToTarget;
        pathPoint.distanceToTarget = distanceToTarget;
        if (distanceToTarget < distanceToTarget2) {
            this.sortBack(pathPoint.index);
        }
        else {
            this.sortForward(pathPoint.index);
        }
    }
    
    private void sortBack(int i) {
        final PathPoint pathPoint = this.pathPoints[i];
        final float distanceToTarget = pathPoint.distanceToTarget;
        while (i > 0) {
            final int n = i - 1 >> 1;
            final PathPoint pathPoint2 = this.pathPoints[n];
            if (distanceToTarget >= pathPoint2.distanceToTarget) {
                break;
            }
            this.pathPoints[i] = pathPoint2;
            pathPoint2.index = i;
            i = n;
        }
        this.pathPoints[i] = pathPoint;
        pathPoint.index = i;
    }
    
    private void sortForward(int index) {
        final PathPoint pathPoint = this.pathPoints[index];
        final float distanceToTarget = pathPoint.distanceToTarget;
        while (true) {
            final int n = 1 + (index << 1);
            final int n2 = n + 1;
            if (n >= this.count) {
                break;
            }
            final PathPoint pathPoint2 = this.pathPoints[n];
            final float distanceToTarget2 = pathPoint2.distanceToTarget;
            PathPoint pathPoint3;
            float distanceToTarget3;
            if (n2 >= this.count) {
                pathPoint3 = null;
                distanceToTarget3 = Float.POSITIVE_INFINITY;
            }
            else {
                pathPoint3 = this.pathPoints[n2];
                distanceToTarget3 = pathPoint3.distanceToTarget;
            }
            if (distanceToTarget2 < distanceToTarget3) {
                if (distanceToTarget2 >= distanceToTarget) {
                    break;
                }
                this.pathPoints[index] = pathPoint2;
                pathPoint2.index = index;
                index = n;
            }
            else {
                if (distanceToTarget3 >= distanceToTarget) {
                    break;
                }
                this.pathPoints[index] = pathPoint3;
                pathPoint3.index = index;
                index = n2;
            }
        }
        this.pathPoints[index] = pathPoint;
        pathPoint.index = index;
    }
    
    public boolean isPathEmpty() {
        return this.count == 0;
    }
    
    static {
        __OBFID = "CL_00000573";
    }
}
