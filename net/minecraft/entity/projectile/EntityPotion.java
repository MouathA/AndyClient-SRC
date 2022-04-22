package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityPotion extends EntityThrowable
{
    private ItemStack potionDamage;
    private static final String __OBFID;
    
    public EntityPotion(final World world) {
        super(world);
    }
    
    public EntityPotion(final World world, final EntityLivingBase entityLivingBase, final int n) {
        this(world, entityLivingBase, new ItemStack(Items.potionitem, 1, n));
    }
    
    public EntityPotion(final World world, final EntityLivingBase entityLivingBase, final ItemStack potionDamage) {
        super(world, entityLivingBase);
        this.potionDamage = potionDamage;
    }
    
    public EntityPotion(final World world, final double n, final double n2, final double n3, final int n4) {
        this(world, n, n2, n3, new ItemStack(Items.potionitem, 1, n4));
    }
    
    public EntityPotion(final World world, final double n, final double n2, final double n3, final ItemStack potionDamage) {
        super(world, n, n2, n3);
        this.potionDamage = potionDamage;
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.05f;
    }
    
    @Override
    protected float func_70182_d() {
        return 0.5f;
    }
    
    @Override
    protected float func_70183_g() {
        return -20.0f;
    }
    
    public void setPotionDamage(final int itemDamage) {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }
        this.potionDamage.setItemDamage(itemDamage);
    }
    
    public int getPotionDamage() {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }
        return this.potionDamage.getMetadata();
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            final List effects = Items.potionitem.getEffects(this.potionDamage);
            if (effects != null && !effects.isEmpty()) {
                final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(4.0, 2.0, 4.0));
                if (!entitiesWithinAABB.isEmpty()) {
                    for (final EntityLivingBase entityLivingBase : entitiesWithinAABB) {
                        final double distanceSqToEntity = this.getDistanceSqToEntity(entityLivingBase);
                        if (distanceSqToEntity < 16.0) {
                            double n = 1.0 - Math.sqrt(distanceSqToEntity) / 4.0;
                            if (entityLivingBase == movingObjectPosition.entityHit) {
                                n = 1.0;
                            }
                            for (final PotionEffect potionEffect : effects) {
                                final int potionID = potionEffect.getPotionID();
                                if (Potion.potionTypes[potionID].isInstant()) {
                                    Potion.potionTypes[potionID].func_180793_a(this, this.getThrower(), entityLivingBase, potionEffect.getAmplifier(), n);
                                }
                                else {
                                    final int n2 = (int)(n * potionEffect.getDuration() + 0.5);
                                    if (n2 <= 20) {
                                        continue;
                                    }
                                    entityLivingBase.addPotionEffect(new PotionEffect(potionID, n2, potionEffect.getAmplifier()));
                                }
                            }
                        }
                    }
                }
            }
            this.worldObj.playAuxSFX(2002, new BlockPos(this), this.getPotionDamage());
            this.setDead();
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("Potion", 10)) {
            this.potionDamage = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("Potion"));
        }
        else {
            this.setPotionDamage(nbtTagCompound.getInteger("potionValue"));
        }
        if (this.potionDamage == null) {
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.potionDamage != null) {
            nbtTagCompound.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
        }
    }
    
    static {
        __OBFID = "CL_00001727";
    }
}
