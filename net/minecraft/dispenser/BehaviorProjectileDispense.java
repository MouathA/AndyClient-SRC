package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    private static final String __OBFID;
    
    public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
        final World world = blockSource.getWorld();
        final IPosition dispensePosition = BlockDispenser.getDispensePosition(blockSource);
        final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
        final IProjectile projectileEntity = this.getProjectileEntity(world, dispensePosition);
        projectileEntity.setThrowableHeading(facing.getFrontOffsetX(), facing.getFrontOffsetY() + 0.1f, facing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        world.spawnEntityInWorld((Entity)projectileEntity);
        itemStack.splitStack(1);
        return itemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(1002, blockSource.getBlockPos(), 0);
    }
    
    protected abstract IProjectile getProjectileEntity(final World p0, final IPosition p1);
    
    protected float func_82498_a() {
        return 6.0f;
    }
    
    protected float func_82500_b() {
        return 1.1f;
    }
    
    static {
        __OBFID = "CL_00001394";
    }
}
