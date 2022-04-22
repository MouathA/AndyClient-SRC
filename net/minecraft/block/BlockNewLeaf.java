package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;

public class BlockNewLeaf extends BlockLeaves
{
    public static final PropertyEnum field_176240_P;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000276";
        field_176240_P = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180195_a(final BlockPlanks.EnumType enumType) {
                return enumType.func_176839_a() >= 4;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180195_a((BlockPlanks.EnumType)o);
            }
            
            static {
                __OBFID = "CL_00002090";
            }
        });
    }
    
    public BlockNewLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLeaf.field_176240_P, BlockPlanks.EnumType.ACACIA).withProperty(BlockNewLeaf.field_176236_b, true).withProperty(BlockNewLeaf.field_176237_a, true));
    }
    
    @Override
    protected void func_176234_a(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockNewLeaf.field_176240_P) == BlockPlanks.EnumType.DARK_OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.field_176240_P)).func_176839_a();
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState) & 0x3;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.field_176240_P)).func_176839_a() - 4);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockNewLeaf.field_176240_P, this.func_176233_b(n)).withProperty(BlockNewLeaf.field_176237_a, (n & 0x4) == 0x0).withProperty(BlockNewLeaf.field_176236_b, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.field_176240_P)).func_176839_a() - 4;
        if (!(boolean)blockState.getValue(BlockNewLeaf.field_176237_a)) {
            n |= 0x4;
        }
        if (blockState.getValue(BlockNewLeaf.field_176236_b)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    public BlockPlanks.EnumType func_176233_b(final int n) {
        return BlockPlanks.EnumType.func_176837_a((n & 0x3) + 4);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNewLeaf.field_176240_P, BlockNewLeaf.field_176236_b, BlockNewLeaf.field_176237_a });
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.field_176240_P)).func_176839_a() - 4));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
}
