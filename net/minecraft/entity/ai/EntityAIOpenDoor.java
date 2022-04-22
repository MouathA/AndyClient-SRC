package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean closeDoor;
    int closeDoorTemporisation;
    private static final String __OBFID;
    
    public EntityAIOpenDoor(final EntityLiving theEntity, final boolean closeDoor) {
        super(theEntity);
        this.theEntity = theEntity;
        this.closeDoor = closeDoor;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        this.closeDoorTemporisation = 20;
        this.doorBlock.func_176512_a(this.theEntity.worldObj, this.field_179507_b, true);
    }
    
    @Override
    public void resetTask() {
        if (this.closeDoor) {
            this.doorBlock.func_176512_a(this.theEntity.worldObj, this.field_179507_b, false);
        }
    }
    
    @Override
    public void updateTask() {
        --this.closeDoorTemporisation;
        super.updateTask();
    }
    
    static {
        __OBFID = "CL_00001603";
    }
}
