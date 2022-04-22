package com.viaversion.viabackwards.api.entities.storage;

public abstract class EntityPositionStorage
{
    private double x;
    private double y;
    private double z;
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setCoordinates(final double x, final double y, final double z, final boolean b) {
        if (b) {
            this.x += x;
            this.y += y;
            this.z += z;
        }
        else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
