package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2
{
    private int field_179478_e;
    private EntityVillager field_179477_f;
    private static final String __OBFID;
    
    public EntityAIVillagerInteract(final EntityVillager field_179477_f) {
        super(field_179477_f, EntityVillager.class, 3.0f, 0.02f);
        this.field_179477_f = field_179477_f;
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        if (this.field_179477_f.func_175555_cq() && this.closestEntity instanceof EntityVillager && ((EntityVillager)this.closestEntity).func_175557_cr()) {
            this.field_179478_e = 10;
        }
        else {
            this.field_179478_e = 0;
        }
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.field_179478_e > 0) {
            --this.field_179478_e;
            if (this.field_179478_e == 0) {
                final InventoryBasic func_175551_co = this.field_179477_f.func_175551_co();
                while (0 < func_175551_co.getSizeInventory()) {
                    final ItemStack stackInSlot = func_175551_co.getStackInSlot(0);
                    ItemStack itemStack = null;
                    if (stackInSlot != null) {
                        final Item item = stackInSlot.getItem();
                        if ((item == Items.bread || item == Items.potato || item == Items.carrot) && stackInSlot.stackSize > 3) {
                            final int n = stackInSlot.stackSize / 2;
                            final ItemStack itemStack2 = stackInSlot;
                            itemStack2.stackSize -= n;
                            itemStack = new ItemStack(item, n, stackInSlot.getMetadata());
                        }
                        else if (item == Items.wheat && stackInSlot.stackSize > 5) {
                            final int n2 = stackInSlot.stackSize / 2 / 3 * 3;
                            final int n3 = n2 / 3;
                            final ItemStack itemStack3 = stackInSlot;
                            itemStack3.stackSize -= n2;
                            itemStack = new ItemStack(Items.bread, n3, 0);
                        }
                        if (stackInSlot.stackSize <= 0) {
                            func_175551_co.setInventorySlotContents(0, null);
                        }
                    }
                    if (itemStack != null) {
                        final EntityItem entityItem = new EntityItem(this.field_179477_f.worldObj, this.field_179477_f.posX, this.field_179477_f.posY - 0.30000001192092896 + this.field_179477_f.getEyeHeight(), this.field_179477_f.posZ, itemStack);
                        final float n4 = 0.3f;
                        final float rotationYawHead = this.field_179477_f.rotationYawHead;
                        final float rotationPitch = this.field_179477_f.rotationPitch;
                        entityItem.motionX = -MathHelper.sin(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionZ = MathHelper.cos(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n4 + 0.1f;
                        entityItem.setDefaultPickupDelay();
                        this.field_179477_f.worldObj.spawnEntityInWorld(entityItem);
                        break;
                    }
                    int n5 = 0;
                    ++n5;
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00002251";
    }
}
