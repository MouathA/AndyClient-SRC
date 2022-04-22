package Mood.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import DTool.util.*;

public class EventBoundingBox implements Event
{
    private Block block;
    public AxisAlignedBB boundingBox;
    private double x;
    private double y;
    private double z;
    
    public EventBoundingBox(final AxisAlignedBB boundingBox, final Block block, final double x, final double y, final double z) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = x;
        this.y = y;
        this.z = z;
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
    
    public Block getBlock() {
        return this.block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }
    
    public BlockPos getBlockPos() {
        return Wrapper.getBlockPos(this.x, this.y, this.z);
    }
}
