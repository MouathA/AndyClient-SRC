package net.minecraft.block;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockNote extends BlockContainer
{
    private static final List field_176434_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000278";
        field_176434_a = Lists.newArrayList("harp", "bd", "snare", "hat", "bassattack");
    }
    
    public BlockNote() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean blockPowered = world.isBlockPowered(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            if (tileEntityNote.previousRedstoneState != blockPowered) {
                if (blockPowered) {
                    tileEntityNote.func_175108_a(world, blockPos);
                }
                tileEntityNote.previousRedstoneState = blockPowered;
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            tileEntityNote.changePitch();
            tileEntityNote.func_175108_a(world, blockPos);
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityNote) {
                ((TileEntityNote)tileEntity).func_175108_a(world, blockPos);
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityNote();
    }
    
    private String func_176433_b(final int n) {
        if (0 >= BlockNote.field_176434_a.size()) {}
        return BlockNote.field_176434_a.get(0);
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "note." + this.func_176433_b(n), 3.0f, (float)Math.pow(2.0, (n2 - 12) / 12.0));
        world.spawnParticle(EnumParticleTypes.NOTE, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, n2 / 24.0, 0.0, 0.0, new int[0]);
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
}
