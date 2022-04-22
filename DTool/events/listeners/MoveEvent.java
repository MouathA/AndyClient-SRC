package DTool.events.listeners;

import DTool.events.*;
import DTool.util.*;

public class MoveEvent extends Event
{
    public double x;
    public double y;
    public double z;
    private Location location;
    private boolean safeWalk;
    private boolean onGround;
    
    public MoveEvent(final Location location, final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.location = location;
    }
    
    public double getX() {
        return this.x;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public boolean isSafeWalk() {
        return this.safeWalk;
    }
    
    @Override
    public void setX(final double x) {
        this.x = x;
    }
    
    @Override
    public void setY(final double y) {
        this.y = y;
    }
    
    @Override
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setSafeWalk(final boolean safeWalk) {
        this.safeWalk = safeWalk;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
}
