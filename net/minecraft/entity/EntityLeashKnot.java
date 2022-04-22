package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.*;

public class EntityLeashKnot extends EntityHanging
{
    private static final String __OBFID;
    
    public EntityLeashKnot(final World world) {
        super(world);
    }
    
    public EntityLeashKnot(final World world, final BlockPos blockPos) {
        super(world, blockPos);
        this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.func_174826_a(new AxisAlignedBB(this.posX - 0.1875, this.posY - 0.25 + 0.125, this.posZ - 0.1875, this.posX + 0.1875, this.posY + 0.25 + 0.125, this.posZ + 0.1875));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    public void func_174859_a(final EnumFacing enumFacing) {
    }
    
    @Override
    public int getWidthPixels() {
        return 9;
    }
    
    @Override
    public int getHeightPixels() {
        return 9;
    }
    
    @Override
    public float getEyeHeight() {
        return -0.0625f;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        return n < 1024.0;
    }
    
    @Override
    public void onBroken(final Entity entity) {
    }
    
    @Override
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        return false;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        final ItemStack heldItem = entityPlayer.getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.lead && !this.worldObj.isRemote) {
            final double n = 7.0;
            for (final EntityLiving entityLiving : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - n, this.posY - n, this.posZ - n, this.posX + n, this.posY + n, this.posZ + n))) {
                if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == entityPlayer) {
                    entityLiving.setLeashedToEntity(this, true);
                }
            }
        }
        if (!this.worldObj.isRemote && !true) {
            this.setDead();
            if (entityPlayer.capabilities.isCreativeMode) {
                final double n2 = 7.0;
                for (final EntityLiving entityLiving2 : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - n2, this.posY - n2, this.posZ - n2, this.posX + n2, this.posY + n2, this.posZ + n2))) {
                    if (entityLiving2.getLeashed() && entityLiving2.getLeashedToEntity() == this) {
                        entityLiving2.clearLeashed(true, false);
                    }
                }
            }
        }
        return true;
    }
    
    public boolean onValidSurface() {
        return this.worldObj.getBlockState(this.field_174861_a).getBlock() instanceof BlockFence;
    }
    
    public static EntityLeashKnot func_174862_a(final World world, final BlockPos blockPos) {
        final EntityLeashKnot entityLeashKnot = new EntityLeashKnot(world, blockPos);
        entityLeashKnot.forceSpawn = true;
        world.spawnEntityInWorld(entityLeashKnot);
        return entityLeashKnot;
    }
    
    public static EntityLeashKnot func_174863_b(final World world, final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (final EntityLeashKnot entityLeashKnot : world.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB(x - 1.0, y - 1.0, z - 1.0, x + 1.0, y + 1.0, z + 1.0))) {
            if (entityLeashKnot.func_174857_n().equals(blockPos)) {
                return entityLeashKnot;
            }
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001548";
    }
}
