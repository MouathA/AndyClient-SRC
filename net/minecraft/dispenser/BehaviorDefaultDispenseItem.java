package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
    private static final String __OBFID;
    
    @Override
    public final ItemStack dispense(final IBlockSource blockSource, final ItemStack itemStack) {
        final ItemStack dispenseStack = this.dispenseStack(blockSource, itemStack);
        this.playDispenseSound(blockSource);
        this.spawnDispenseParticles(blockSource, BlockDispenser.getFacing(blockSource.getBlockMetadata()));
        return dispenseStack;
    }
    
    protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
        doDispense(blockSource.getWorld(), itemStack.splitStack(1), 6, BlockDispenser.getFacing(blockSource.getBlockMetadata()), BlockDispenser.getDispensePosition(blockSource));
        return itemStack;
    }
    
    public static void doDispense(final World world, final ItemStack itemStack, final int n, final EnumFacing enumFacing, final IPosition position) {
        final double x = position.getX();
        final double y = position.getY();
        final double z = position.getZ();
        double n2;
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            n2 = y - 0.125;
        }
        else {
            n2 = y - 0.15625;
        }
        final EntityItem entityItem = new EntityItem(world, x, n2, z, itemStack);
        final double n3 = world.rand.nextDouble() * 0.1 + 0.2;
        entityItem.motionX = enumFacing.getFrontOffsetX() * n3;
        entityItem.motionY = 0.20000000298023224;
        entityItem.motionZ = enumFacing.getFrontOffsetZ() * n3;
        final EntityItem entityItem2 = entityItem;
        entityItem2.motionX += world.rand.nextGaussian() * 0.007499999832361937 * n;
        final EntityItem entityItem3 = entityItem;
        entityItem3.motionY += world.rand.nextGaussian() * 0.007499999832361937 * n;
        final EntityItem entityItem4 = entityItem;
        entityItem4.motionZ += world.rand.nextGaussian() * 0.007499999832361937 * n;
        world.spawnEntityInWorld(entityItem);
    }
    
    protected void playDispenseSound(final IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
    }
    
    protected void spawnDispenseParticles(final IBlockSource blockSource, final EnumFacing enumFacing) {
        blockSource.getWorld().playAuxSFX(2000, blockSource.getBlockPos(), this.func_82488_a(enumFacing));
    }
    
    private int func_82488_a(final EnumFacing enumFacing) {
        return enumFacing.getFrontOffsetX() + 1 + (enumFacing.getFrontOffsetZ() + 1) * 3;
    }
    
    static {
        __OBFID = "CL_00001195";
    }
}
