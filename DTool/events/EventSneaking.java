package DTool.events;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EventSneaking extends Event
{
    public double x;
    public double y;
    public double z;
    public double d3;
    public double d5;
    public double offset;
    public World worldObj;
    public AxisAlignedBB boundingBox;
    public Entity entity;
    public boolean sneaking;
    public boolean revertFlagAfter;
    public boolean postEdgeOfBlock;
    
    public EventSneaking() {
        this.offset = -1.0;
        this.revertFlagAfter = false;
        this.postEdgeOfBlock = false;
    }
    
    public AxisAlignedBB getEntityBoundingBox() {
        return this.boundingBox;
    }
    
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
}
