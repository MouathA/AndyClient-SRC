package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIFleeSun extends EntityAIBase
{
    private EntityCreature theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private double movementSpeed;
    private World theWorld;
    private static final String __OBFID;
    
    public EntityAIFleeSun(final EntityCreature theCreature, final double movementSpeed) {
        this.theCreature = theCreature;
        this.movementSpeed = movementSpeed;
        this.theWorld = theCreature.worldObj;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }
        if (!this.theCreature.isBurning()) {
            return false;
        }
        if (!this.theWorld.isAgainstSky(new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ))) {
            return false;
        }
        final Vec3 possibleShelter = this.findPossibleShelter();
        if (possibleShelter == null) {
            return false;
        }
        this.shelterX = possibleShelter.xCoord;
        this.shelterY = possibleShelter.yCoord;
        this.shelterZ = possibleShelter.zCoord;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theCreature.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }
    
    private Vec3 findPossibleShelter() {
        final Random rng = this.theCreature.getRNG();
        final BlockPos blockPos = new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ);
        while (0 < 10) {
            final BlockPos add = blockPos.add(rng.nextInt(20) - 10, rng.nextInt(6) - 3, rng.nextInt(20) - 10);
            if (!this.theWorld.isAgainstSky(add) && this.theCreature.func_180484_a(add) < 0.0f) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001583";
    }
}
