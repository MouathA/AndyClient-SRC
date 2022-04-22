package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private EntityHorse horseHost;
    private double field_111178_b;
    private double field_111179_c;
    private double field_111176_d;
    private double field_111177_e;
    private static final String __OBFID;
    
    public EntityAIRunAroundLikeCrazy(final EntityHorse horseHost, final double field_111178_b) {
        this.horseHost = horseHost;
        this.field_111178_b = field_111178_b;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.horseHost.isTame() || this.horseHost.riddenByEntity == null) {
            return false;
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
        if (randomTarget == null) {
            return false;
        }
        this.field_111179_c = randomTarget.xCoord;
        this.field_111176_d = randomTarget.yCoord;
        this.field_111177_e = randomTarget.zCoord;
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.horseHost.getNavigator().tryMoveToXYZ(this.field_111179_c, this.field_111176_d, this.field_111177_e, this.field_111178_b);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
    }
    
    @Override
    public void updateTask() {
        if (this.horseHost.getRNG().nextInt(50) == 0) {
            if (this.horseHost.riddenByEntity instanceof EntityPlayer) {
                final int temper = this.horseHost.getTemper();
                final int maxTemper = this.horseHost.getMaxTemper();
                if (maxTemper > 0 && this.horseHost.getRNG().nextInt(maxTemper) < temper) {
                    this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
                    this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
                    return;
                }
                this.horseHost.increaseTemper(5);
            }
            this.horseHost.riddenByEntity.mountEntity(null);
            this.horseHost.riddenByEntity = null;
            this.horseHost.makeHorseRearWithSound();
            this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
        }
    }
    
    static {
        __OBFID = "CL_00001612";
    }
}
