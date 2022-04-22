package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.command.server.*;
import net.minecraft.block.state.*;

public class BlockCommandBlock extends BlockContainer
{
    public static final PropertyBool TRIGGERED_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000219";
        TRIGGERED_PROP = PropertyBool.create("triggered");
    }
    
    public BlockCommandBlock() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCommandBlock.TRIGGERED_PROP, false));
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            final boolean booleanValue = (boolean)blockState.getValue(BlockCommandBlock.TRIGGERED_PROP);
            if (blockPowered && !booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCommandBlock.TRIGGERED_PROP, true), 4);
                world.scheduleUpdate(blockPos, this, this.tickRate(world));
            }
            else if (!blockPowered && booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCommandBlock.TRIGGERED_PROP, false), 4);
            }
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().trigger(world);
            world.updateComparatorOutputLevel(blockPos, this);
        }
    }
    
    @Override
    public int tickRate(final World world) {
        return 1;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().func_175574_a(entityPlayer);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().getSuccessCount() : 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            final CommandBlockLogic commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
            if (itemStack.hasDisplayName()) {
                commandBlockLogic.func_145754_b(itemStack.getDisplayName());
            }
            if (!world.isRemote) {
                commandBlockLogic.func_175573_a(world.getGameRules().getGameRuleBooleanValue("sendCommandFeedback"));
            }
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCommandBlock.TRIGGERED_PROP, (n & 0x1) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockCommandBlock.TRIGGERED_PROP)) {}
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCommandBlock.TRIGGERED_PROP });
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockCommandBlock.TRIGGERED_PROP, false);
    }
}
