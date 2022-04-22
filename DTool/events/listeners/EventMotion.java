package DTool.events.listeners;

import DTool.events.*;

public class EventMotion extends Event
{
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    
    public double getX() {
        return this.x;
    }
    
    @Override
    public void setX(final double x) {
        this.x = x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    @Override
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public EventMotion(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}
