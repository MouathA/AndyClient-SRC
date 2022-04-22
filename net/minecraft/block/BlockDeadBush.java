package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;

public class BlockDeadBush extends BlockBush
{
    private static final String __OBFID;
    
    protected BlockDeadBush() {
        super(Material.vine);
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.8f, 0.5f + n);
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.sand || block == Blocks.hardened_clay || block == Blocks.stained_hardened_clay || block == Blocks.dirt;
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.deadbush, 1, 0));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    static {
        __OBFID = "CL_00000224";
    }
}
