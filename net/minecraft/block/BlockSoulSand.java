package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import Mood.*;

public class BlockSoulSand extends Block
{
    private static final String __OBFID;
    
    public BlockSoulSand() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1 - 0.125f, blockPos.getZ() + 1);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        final Client instance = Client.INSTANCE;
        if (!Client.getModuleByName("NoSlowDown").toggled) {
            entity.motionX *= 0.4;
            entity.motionZ *= 0.4;
        }
    }
    
    static {
        __OBFID = "CL_00000310";
    }
}
