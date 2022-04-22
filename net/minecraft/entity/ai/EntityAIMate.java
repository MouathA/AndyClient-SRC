package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class EntityAIMate extends EntityAIBase
{
    private EntityAnimal theAnimal;
    World theWorld;
    private EntityAnimal targetMate;
    int spawnBabyDelay;
    double moveSpeed;
    private static final String __OBFID;
    
    public EntityAIMate(final EntityAnimal theAnimal, final double moveSpeed) {
        this.theAnimal = theAnimal;
        this.theWorld = theAnimal.worldObj;
        this.moveSpeed = moveSpeed;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return false;
        }
        this.targetMate = this.getNearbyMate();
        return this.targetMate != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }
    
    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }
    
    @Override
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0f, (float)this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
    }
    
    private EntityAnimal getNearbyMate() {
        final float n = 8.0f;
        final List entitiesWithinAABB = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(n, n, n));
        double distanceSqToEntity = Double.MAX_VALUE;
        EntityAnimal entityAnimal = null;
        for (final EntityAnimal entityAnimal2 : entitiesWithinAABB) {
            if (this.theAnimal.canMateWith(entityAnimal2) && this.theAnimal.getDistanceSqToEntity(entityAnimal2) < distanceSqToEntity) {
                entityAnimal = entityAnimal2;
                distanceSqToEntity = this.theAnimal.getDistanceSqToEntity(entityAnimal2);
            }
        }
        return entityAnimal;
    }
    
    private void spawnBaby() {
        final EntityAgeable child = this.theAnimal.createChild(this.targetMate);
        if (child != null) {
            EntityPlayer entityPlayer = this.theAnimal.func_146083_cb();
            if (entityPlayer == null && this.targetMate.func_146083_cb() != null) {
                entityPlayer = this.targetMate.func_146083_cb();
            }
            if (entityPlayer != null) {
                entityPlayer.triggerAchievement(StatList.animalsBredStat);
                if (this.theAnimal instanceof EntityCow) {
                    entityPlayer.triggerAchievement(AchievementList.breedCow);
                }
            }
            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            child.setGrowingAge(-24000);
            child.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0f, 0.0f);
            this.theWorld.spawnEntityInWorld(child);
            final Random rng = this.theAnimal.getRNG();
            while (0 < 7) {
                this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + rng.nextFloat() * this.theAnimal.width * 2.0f - this.theAnimal.width, this.theAnimal.posY + 0.5 + rng.nextFloat() * this.theAnimal.height, this.theAnimal.posZ + rng.nextFloat() * this.theAnimal.width * 2.0f - this.theAnimal.width, rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, rng.nextGaussian() * 0.02, new int[0]);
                int n = 0;
                ++n;
            }
            if (this.theWorld.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, rng.nextInt(7) + 1));
            }
        }
    }
    
    static {
        __OBFID = "CL_00001578";
    }
}
