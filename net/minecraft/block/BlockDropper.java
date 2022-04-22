package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.dispenser.*;
import net.minecraft.inventory.*;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem field_149947_P;
    private static final String __OBFID;
    
    public BlockDropper() {
        this.field_149947_P = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    protected IBehaviorDispenseItem func_149940_a(final ItemStack itemStack) {
        return this.field_149947_P;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDropper();
    }
    
    @Override
    protected void func_176439_d(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int func_146017_i = tileEntityDispenser.func_146017_i();
            if (func_146017_i < 0) {
                world.playAuxSFX(1001, blockPos, 0);
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(func_146017_i);
                if (stackInSlot != null) {
                    final EnumFacing enumFacing = (EnumFacing)world.getBlockState(blockPos).getValue(BlockDropper.FACING);
                    final BlockPos offset = blockPos.offset(enumFacing);
                    final IInventory func_145893_b = TileEntityHopper.func_145893_b(world, offset.getX(), offset.getY(), offset.getZ());
                    ItemStack itemStack;
                    if (func_145893_b == null) {
                        itemStack = this.field_149947_P.dispense(blockSourceImpl, stackInSlot);
                        if (itemStack != null && itemStack.stackSize == 0) {
                            itemStack = null;
                        }
                    }
                    else if (TileEntityHopper.func_174918_a(func_145893_b, stackInSlot.copy().splitStack(1), enumFacing.getOpposite()) == null) {
                        final ItemStack copy;
                        itemStack = (copy = stackInSlot.copy());
                        if (--copy.stackSize == 0) {
                            itemStack = null;
                        }
                    }
                    else {
                        itemStack = stackInSlot.copy();
                    }
                    tileEntityDispenser.setInventorySlotContents(func_146017_i, itemStack);
                }
            }
        }
    }
    
    static {
        __OBFID = "CL_00000233";
    }
}
