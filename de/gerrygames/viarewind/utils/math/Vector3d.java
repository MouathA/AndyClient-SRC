package de.gerrygames.viarewind.utils.math;

import java.util.*;

public class Vector3d
{
    double x;
    double y;
    double z;
    
    public Vector3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3d() {
    }
    
    public void set(final Vector3d vector3d) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
    }
    
    public Vector3d set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vector3d multiply(final double n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        return this;
    }
    
    public Vector3d add(final Vector3d vector3d) {
        this.x += vector3d.x;
        this.y += vector3d.y;
        this.z += vector3d.z;
        return this;
    }
    
    public Vector3d substract(final Vector3d vector3d) {
        this.x -= vector3d.x;
        this.y -= vector3d.y;
        this.z -= vector3d.z;
        return this;
    }
    
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }
    
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public Vector3d normalize() {
        this.multiply(1.0 / this.length());
        return this;
    }
    
    public Vector3d clone() {
        return new Vector3d(this.x, this.y, this.z);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Vector3d vector3d = (Vector3d)o;
        return Double.compare(vector3d.x, this.x) == 0 && Double.compare(vector3d.y, this.y) == 0 && Double.compare(vector3d.z, this.z) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }
    
    @Override
    public String toString() {
        return "Vector3d{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
