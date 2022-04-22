package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAILookIdle extends EntityAIBase
{
    private EntityLiving idleEntity;
    private double lookX;
    private double lookZ;
    private int idleTime;
    private static final String __OBFID;
    
    public EntityAILookIdle(final EntityLiving idleEntity) {
        this.idleEntity = idleEntity;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.idleEntity.getRNG().nextFloat() < 0.02f;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.idleTime >= 0;
    }
    
    @Override
    public void startExecuting() {
        final double n = 6.283185307179586 * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(n);
        this.lookZ = Math.sin(n);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }
    
    @Override
    public void updateTask() {
        --this.idleTime;
        this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0f, (float)this.idleEntity.getVerticalFaceSpeed());
    }
    
    static {
        __OBFID = "CL_00001607";
    }
}
