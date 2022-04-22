package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;

public abstract class PlayerPositionStorage implements StorableObject
{
    private double x;
    private double y;
    private double z;
    
    protected PlayerPositionStorage() {
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
    
    public void setCoordinates(final PacketWrapper packetWrapper, final boolean b) throws Exception {
        this.setCoordinates((double)packetWrapper.get(Type.DOUBLE, 0), (double)packetWrapper.get(Type.DOUBLE, 1), (double)packetWrapper.get(Type.DOUBLE, 2), b);
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
