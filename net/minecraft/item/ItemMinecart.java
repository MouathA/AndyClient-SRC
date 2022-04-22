package net.minecraft.item;

import net.minecraft.entity.item.*;
import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;

public class ItemMinecart extends Item
{
    private static final IBehaviorDispenseItem dispenserMinecartBehavior;
    private final EntityMinecart.EnumMinecartType minecartType;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000049";
        dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
            private static final String __OBFID;
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final World world = blockSource.getWorld();
                final double n = blockSource.getX() + facing.getFrontOffsetX() * 1.125;
                final double n2 = Math.floor(blockSource.getY()) + facing.getFrontOffsetY();
                final double n3 = blockSource.getZ() + facing.getFrontOffsetZ() * 1.125;
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final IBlockState blockState = world.getBlockState(offset);
                final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)((blockState.getBlock() instanceof BlockRailBase) ? blockState.getValue(((BlockRailBase)blockState.getBlock()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                double n4;
                if (BlockRailBase.func_176563_d(blockState)) {
                    if (enumRailDirection.func_177018_c()) {
                        n4 = 0.6;
                    }
                    else {
                        n4 = 0.1;
                    }
                }
                else {
                    if (blockState.getBlock().getMaterial() != Material.air || !BlockRailBase.func_176563_d(world.getBlockState(offset.offsetDown()))) {
                        return this.behaviourDefaultDispenseItem.dispense(blockSource, itemStack);
                    }
                    final IBlockState blockState2 = world.getBlockState(offset.offsetDown());
                    final BlockRailBase.EnumRailDirection enumRailDirection2 = (BlockRailBase.EnumRailDirection)((blockState2.getBlock() instanceof BlockRailBase) ? blockState2.getValue(((BlockRailBase)blockState2.getBlock()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                    if (facing != EnumFacing.DOWN && enumRailDirection2.func_177018_c()) {
                        n4 = -0.4;
                    }
                    else {
                        n4 = -0.9;
                    }
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, n, n2 + n4, n3, ItemMinecart.access$0((ItemMinecart)itemStack.getItem()));
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
                itemStack.splitStack(1);
                return itemStack;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
            }
            
            static {
                __OBFID = "CL_00000050";
            }
        };
    }
    
    public ItemMinecart(final EntityMinecart.EnumMinecartType minecartType) {
        this.maxStackSize = 1;
        this.minecartType = minecartType;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemMinecart.dispenserMinecartBehavior);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (BlockRailBase.func_176563_d(blockState)) {
            if (!world.isRemote) {
                final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)((blockState.getBlock() instanceof BlockRailBase) ? blockState.getValue(((BlockRailBase)blockState.getBlock()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                double n4 = 0.0;
                if (enumRailDirection.func_177018_c()) {
                    n4 = 0.5;
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + n4, blockPos.getZ() + 0.5, this.minecartType);
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
            }
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static EntityMinecart.EnumMinecartType access$0(final ItemMinecart itemMinecart) {
        return itemMinecart.minecartType;
    }
}
