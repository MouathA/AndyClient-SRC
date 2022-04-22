package de.gerrygames.viarewind.utils.math;

public class RayTracing
{
    public static Vector3d trace(final Ray3d ray3d, final AABB aabb, final double n) {
        final Vector3d vector3d = new Vector3d(1.0 / ray3d.dir.x, 1.0 / ray3d.dir.y, 1.0 / ray3d.dir.z);
        final boolean b = vector3d.x < 0.0;
        final boolean b2 = vector3d.y < 0.0;
        final boolean b3 = vector3d.z < 0.0;
        double n2 = ((b ? aabb.max : aabb.min).x - ray3d.start.x) * vector3d.x;
        double n3 = ((b ? aabb.min : aabb.max).x - ray3d.start.x) * vector3d.x;
        final double n4 = ((b2 ? aabb.max : aabb.min).y - ray3d.start.y) * vector3d.y;
        final double n5 = ((b2 ? aabb.min : aabb.max).y - ray3d.start.y) * vector3d.y;
        if (n2 > n5 || n4 > n3) {
            return null;
        }
        if (n4 > n2) {
            n2 = n4;
        }
        if (n5 < n3) {
            n3 = n5;
        }
        final double n6 = ((b3 ? aabb.max : aabb.min).z - ray3d.start.z) * vector3d.z;
        final double n7 = ((b3 ? aabb.min : aabb.max).z - ray3d.start.z) * vector3d.z;
        if (n2 > n7 || n6 > n3) {
            return null;
        }
        if (n6 > n2) {
            n2 = n6;
        }
        if (n7 < n3) {
            n3 = n7;
        }
        return (n2 <= n && n3 > 0.0) ? ray3d.start.clone().add(ray3d.dir.clone().normalize().multiply(n2)) : null;
    }
}
