package net.minecraft.util;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class FoodStats
{
    private int foodLevel;
    private float foodSaturationLevel;
    private float foodExhaustionLevel;
    private int foodTimer;
    private int prevFoodLevel;
    private static final String __OBFID;
    
    public FoodStats() {
        this.foodLevel = 20;
        this.foodSaturationLevel = 5.0f;
        this.prevFoodLevel = 20;
    }
    
    public void addStats(final int n, final float n2) {
        this.foodLevel = Math.min(n + this.foodLevel, 20);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + n * n2 * 2.0f, (float)this.foodLevel);
    }
    
    public void addStats(final ItemFood itemFood, final ItemStack itemStack) {
        this.addStats(itemFood.getHealAmount(itemStack), itemFood.getSaturationModifier(itemStack));
    }
    
    public void onUpdate(final EntityPlayer entityPlayer) {
        final EnumDifficulty difficulty = entityPlayer.worldObj.getDifficulty();
        this.prevFoodLevel = this.foodLevel;
        if (this.foodExhaustionLevel > 4.0f) {
            this.foodExhaustionLevel -= 4.0f;
            if (this.foodSaturationLevel > 0.0f) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0f, 0.0f);
            }
            else if (difficulty != EnumDifficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }
        if (entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.foodLevel >= 18 && entityPlayer.shouldHeal()) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                entityPlayer.heal(1.0f);
                this.addExhaustion(3.0f);
                this.foodTimer = 0;
            }
        }
        else if (this.foodLevel <= 0) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                if (entityPlayer.getHealth() > 10.0f || difficulty == EnumDifficulty.HARD || (entityPlayer.getHealth() > 1.0f && difficulty == EnumDifficulty.NORMAL)) {
                    entityPlayer.attackEntityFrom(DamageSource.starve, 1.0f);
                }
                this.foodTimer = 0;
            }
        }
        else {
            this.foodTimer = 0;
        }
    }
    
    public void readNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("foodLevel", 99)) {
            this.foodLevel = nbtTagCompound.getInteger("foodLevel");
            this.foodTimer = nbtTagCompound.getInteger("foodTickTimer");
            this.foodSaturationLevel = nbtTagCompound.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = nbtTagCompound.getFloat("foodExhaustionLevel");
        }
    }
    
    public void writeNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("foodLevel", this.foodLevel);
        nbtTagCompound.setInteger("foodTickTimer", this.foodTimer);
        nbtTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        nbtTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }
    
    public boolean needFood() {
        return this.foodLevel < 20;
    }
    
    public void addExhaustion(final float n) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + n, 40.0f);
    }
    
    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }
    
    public void setFoodLevel(final int foodLevel) {
        this.foodLevel = foodLevel;
    }
    
    public void setFoodSaturationLevel(final float foodSaturationLevel) {
        this.foodSaturationLevel = foodSaturationLevel;
    }
    
    static {
        __OBFID = "CL_00001729";
    }
}
