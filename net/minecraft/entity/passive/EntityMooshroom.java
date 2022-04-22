package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class EntityMooshroom extends EntityCow
{
    private static final String __OBFID;
    
    public EntityMooshroom(final World world) {
        super(world);
        this.setSize(0.9f, 1.3f);
        this.field_175506_bl = Blocks.mycelium;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.bowl && this.getGrowingAge() >= 0) {
            if (currentItem.stackSize == 1) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.mushroom_stew));
                return true;
            }
            if (entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew)) && !entityPlayer.capabilities.isCreativeMode) {
                entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
                return true;
            }
        }
        if (currentItem != null && currentItem.getItem() == Items.shears && this.getGrowingAge() >= 0) {
            this.setDead();
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + this.height / 2.0f, this.posZ, 0.0, 0.0, 0.0, new int[0]);
            if (!this.worldObj.isRemote) {
                final EntityCow entityCow = new EntityCow(this.worldObj);
                entityCow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                entityCow.setHealth(this.getHealth());
                entityCow.renderYawOffset = this.renderYawOffset;
                if (this.hasCustomName()) {
                    entityCow.setCustomNameTag(this.getCustomNameTag());
                }
                this.worldObj.spawnEntityInWorld(entityCow);
                while (0 < 5) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack(Blocks.red_mushroom)));
                    int n = 0;
                    ++n;
                }
                currentItem.damageItem(1, entityPlayer);
                this.playSound("mob.sheep.shear", 1.0f, 1.0f);
            }
            return true;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public EntityMooshroom createChild(final EntityAgeable entityAgeable) {
        return new EntityMooshroom(this.worldObj);
    }
    
    @Override
    public EntityCow createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    static {
        __OBFID = "CL_00001645";
    }
}
