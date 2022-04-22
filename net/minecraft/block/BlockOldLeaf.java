package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;

public class BlockOldLeaf extends BlockLeaves
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000280";
        VARIANT_PROP = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180202_a(final BlockPlanks.EnumType enumType) {
                return enumType.func_176839_a() < 4;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180202_a((BlockPlanks.EnumType)o);
            }
            
            static {
                __OBFID = "CL_00002085";
            }
        });
    }
    
    public BlockOldLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockOldLeaf.VARIANT_PROP, BlockPlanks.EnumType.OAK).withProperty(BlockOldLeaf.field_176236_b, true).withProperty(BlockOldLeaf.field_176237_a, true));
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP);
        return (enumType == BlockPlanks.EnumType.SPRUCE) ? 6396257 : ((enumType == BlockPlanks.EnumType.BIRCH) ? 8431445 : super.getRenderColor(blockState));
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP);
            if (enumType == BlockPlanks.EnumType.SPRUCE) {
                return 6396257;
            }
            if (enumType == BlockPlanks.EnumType.BIRCH) {
                return 8431445;
            }
        }
        return super.colorMultiplier(blockAccess, blockPos, n);
    }
    
    @Override
    protected void func_176234_a(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockOldLeaf.VARIANT_PROP) == BlockPlanks.EnumType.OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    protected int func_176232_d(final IBlockState blockState) {
        return (blockState.getValue(BlockOldLeaf.VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE) ? 40 : super.func_176232_d(blockState);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.OAK.func_176839_a()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()));
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP)).func_176839_a());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockOldLeaf.VARIANT_PROP, this.func_176233_b(n)).withProperty(BlockOldLeaf.field_176237_a, (n & 0x4) == 0x0).withProperty(BlockOldLeaf.field_176236_b, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP)).func_176839_a();
        if (!(boolean)blockState.getValue(BlockOldLeaf.field_176237_a)) {
            n |= 0x4;
        }
        if (blockState.getValue(BlockOldLeaf.field_176236_b)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    public BlockPlanks.EnumType func_176233_b(final int n) {
        return BlockPlanks.EnumType.func_176837_a((n & 0x3) % 4);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockOldLeaf.VARIANT_PROP, BlockOldLeaf.field_176236_b, BlockOldLeaf.field_176237_a });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP)).func_176839_a();
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT_PROP)).func_176839_a()));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
}
