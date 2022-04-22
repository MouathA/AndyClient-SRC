package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockWeb extends Block
{
    private static final String __OBFID;
    
    public BlockWeb() {
        super(Material.web);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        entity.setInWeb();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.string;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    static {
        __OBFID = "CL_00000333";
    }
}
