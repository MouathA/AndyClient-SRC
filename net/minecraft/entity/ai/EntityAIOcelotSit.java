package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class EntityAIOcelotSit extends EntityAIMoveToBlock
{
    private final EntityOcelot field_151493_a;
    private static final String __OBFID;
    
    public EntityAIOcelotSit(final EntityOcelot field_151493_a, final double n) {
        super(field_151493_a, n, 8);
        this.field_151493_a = field_151493_a;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.field_151493_a.getAISit().setSitting(false);
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.field_151493_a.setSitting(false);
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.field_151493_a.getAISit().setSitting(false);
        if (!this.func_179487_f()) {
            this.field_151493_a.setSitting(false);
        }
        else if (!this.field_151493_a.isSitting()) {
            this.field_151493_a.setSitting(true);
        }
    }
    
    @Override
    protected boolean func_179488_a(final World world, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos.offsetUp())) {
            return false;
        }
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block == Blocks.chest) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest && ((TileEntityChest)tileEntity).numPlayersUsing < 1) {
                return true;
            }
        }
        else {
            if (block == Blocks.lit_furnace) {
                return true;
            }
            if (block == Blocks.bed && blockState.getValue(BlockBed.PART_PROP) != BlockBed.EnumPartType.HEAD) {
                return true;
            }
        }
        return false;
    }
    
    static {
        __OBFID = "CL_00001601";
    }
}
