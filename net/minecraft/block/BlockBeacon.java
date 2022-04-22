package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class BlockBeacon extends BlockContainer
{
    private static final String __OBFID;
    
    public BlockBeacon() {
        super(Material.glass);
        this.setHardness(3.0f);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBeacon();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
        }
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBeacon) {
                ((TileEntityBeacon)tileEntity).func_145999_a(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            ((TileEntityBeacon)tileEntity).func_174908_m();
            world.addBlockEvent(blockPos, this, 1, 0);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public static void func_176450_d(final World world, final BlockPos blockPos) {
        HttpUtil.field_180193_a.submit((Runnable)new Runnable(blockPos) {
            private static final String __OBFID;
            private final World val$worldIn;
            private final BlockPos val$p_176450_1_;
            
            @Override
            public void run() {
                final Chunk chunkFromBlockCoords = this.val$worldIn.getChunkFromBlockCoords(this.val$p_176450_1_);
                for (int i = this.val$p_176450_1_.getY() - 1; i >= 0; --i) {
                    final BlockPos blockPos = new BlockPos(this.val$p_176450_1_.getX(), i, this.val$p_176450_1_.getZ());
                    if (!chunkFromBlockCoords.canSeeSky(blockPos)) {
                        break;
                    }
                    if (this.val$worldIn.getBlockState(blockPos).getBlock() == Blocks.beacon) {
                        ((WorldServer)this.val$worldIn).addScheduledTask(new Runnable(this.val$worldIn, blockPos) {
                            private static final String __OBFID;
                            final BlockBeacon$1 this$1;
                            private final World val$worldIn;
                            private final BlockPos val$var3;
                            
                            @Override
                            public void run() {
                                final TileEntity tileEntity = this.val$worldIn.getTileEntity(this.val$var3);
                                if (tileEntity instanceof TileEntityBeacon) {
                                    ((TileEntityBeacon)tileEntity).func_174908_m();
                                    this.val$worldIn.addBlockEvent(this.val$var3, Blocks.beacon, 1, 0);
                                }
                            }
                            
                            static {
                                __OBFID = "CL_00002135";
                            }
                        });
                    }
                }
            }
            
            static {
                __OBFID = "CL_00002136";
            }
        });
    }
    
    static {
        __OBFID = "CL_00000197";
    }
}
