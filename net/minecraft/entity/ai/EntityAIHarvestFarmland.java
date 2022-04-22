package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock
{
    private final EntityVillager field_179504_c;
    private boolean field_179502_d;
    private boolean field_179503_e;
    private int field_179501_f;
    private static final String __OBFID;
    
    public EntityAIHarvestFarmland(final EntityVillager field_179504_c, final double n) {
        super(field_179504_c, n, 16);
        this.field_179504_c = field_179504_c;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.field_179496_a <= 0) {
            if (!this.field_179504_c.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                return false;
            }
            this.field_179501_f = -1;
            this.field_179502_d = this.field_179504_c.func_175556_cs();
            this.field_179503_e = this.field_179504_c.func_175557_cr();
        }
        return super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.field_179501_f >= 0 && super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.field_179504_c.getLookHelper().setLookPosition(this.field_179494_b.getX() + 0.5, this.field_179494_b.getY() + 1, this.field_179494_b.getZ() + 0.5, 10.0f, (float)this.field_179504_c.getVerticalFaceSpeed());
        if (this.func_179487_f()) {
            final World worldObj = this.field_179504_c.worldObj;
            final BlockPos offsetUp = this.field_179494_b.offsetUp();
            final IBlockState blockState = worldObj.getBlockState(offsetUp);
            final Block block = blockState.getBlock();
            if (this.field_179501_f == 0 && block instanceof BlockCrops && (int)blockState.getValue(BlockCrops.AGE) == 7) {
                worldObj.destroyBlock(offsetUp, true);
            }
            else if (this.field_179501_f == 1 && block == Blocks.air) {
                final InventoryBasic func_175551_co = this.field_179504_c.func_175551_co();
                while (0 < func_175551_co.getSizeInventory()) {
                    final ItemStack stackInSlot = func_175551_co.getStackInSlot(0);
                    if (stackInSlot != null) {
                        if (stackInSlot.getItem() == Items.wheat_seeds) {
                            worldObj.setBlockState(offsetUp, Blocks.wheat.getDefaultState(), 3);
                        }
                        else if (stackInSlot.getItem() == Items.potato) {
                            worldObj.setBlockState(offsetUp, Blocks.potatoes.getDefaultState(), 3);
                        }
                        else if (stackInSlot.getItem() == Items.carrot) {
                            worldObj.setBlockState(offsetUp, Blocks.carrots.getDefaultState(), 3);
                        }
                    }
                    if (true) {
                        final ItemStack itemStack = stackInSlot;
                        --itemStack.stackSize;
                        if (stackInSlot.stackSize <= 0) {
                            func_175551_co.setInventorySlotContents(0, null);
                            break;
                        }
                        break;
                    }
                    else {
                        int n = 0;
                        ++n;
                    }
                }
            }
            this.field_179501_f = -1;
            this.field_179496_a = 10;
        }
    }
    
    @Override
    protected boolean func_179488_a(final World world, BlockPos offsetUp) {
        if (world.getBlockState(offsetUp).getBlock() == Blocks.farmland) {
            offsetUp = offsetUp.offsetUp();
            final IBlockState blockState = world.getBlockState(offsetUp);
            final Block block = blockState.getBlock();
            if (block instanceof BlockCrops && (int)blockState.getValue(BlockCrops.AGE) == 7 && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
                this.field_179501_f = 0;
                return true;
            }
            if (block == Blocks.air && this.field_179502_d && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
                this.field_179501_f = 1;
                return true;
            }
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00002253";
    }
}
