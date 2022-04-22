package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockTallGrass extends BlockBush implements IGrowable
{
    public static final PropertyEnum field_176497_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000321";
        field_176497_a = PropertyEnum.create("type", EnumType.class);
    }
    
    protected BlockTallGrass() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTallGrass.field_176497_a, EnumType.DEAD_BUSH));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.8f, 0.5f + n);
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        return (blockState.getValue(BlockTallGrass.field_176497_a) == EnumType.DEAD_BUSH) ? 16777215 : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).func_180627_b(blockPos);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (random.nextInt(8) == 0) ? Items.wheat_seeds : null;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return 1 + random.nextInt(n * 2 + 1);
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 1, ((EnumType)blockState.getValue(BlockTallGrass.field_176497_a)).func_177044_a()));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (1 < 3) {
            list.add(new ItemStack(item, 1, 1));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return blockState.getValue(BlockTallGrass.field_176497_a) != EnumType.DEAD_BUSH;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        BlockDoublePlant.EnumPlantType enumPlantType = BlockDoublePlant.EnumPlantType.GRASS;
        if (blockState.getValue(BlockTallGrass.field_176497_a) == EnumType.FERN) {
            enumPlantType = BlockDoublePlant.EnumPlantType.FERN;
        }
        if (Blocks.double_plant.canPlaceBlockAt(world, blockPos)) {
            Blocks.double_plant.func_176491_a(world, blockPos, enumPlantType, 2);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTallGrass.field_176497_a, EnumType.func_177045_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockTallGrass.field_176497_a)).func_177044_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTallGrass.field_176497_a });
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XYZ;
    }
    
    public enum EnumType implements IStringSerializable
    {
        DEAD_BUSH("DEAD_BUSH", 0, "DEAD_BUSH", 0, 0, "dead_bush"), 
        GRASS("GRASS", 1, "GRASS", 1, 1, "tall_grass"), 
        FERN("FERN", 2, "FERN", 2, 2, "fern");
        
        private static final EnumType[] field_177048_d;
        private final int field_177049_e;
        private final String field_177046_f;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002055";
            ENUM$VALUES = new EnumType[] { EnumType.DEAD_BUSH, EnumType.GRASS, EnumType.FERN };
            field_177048_d = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.DEAD_BUSH, EnumType.GRASS, EnumType.FERN };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_177048_d[enumType.func_177044_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_177049_e, final String field_177046_f) {
            this.field_177049_e = field_177049_e;
            this.field_177046_f = field_177046_f;
        }
        
        public int func_177044_a() {
            return this.field_177049_e;
        }
        
        @Override
        public String toString() {
            return this.field_177046_f;
        }
        
        public static EnumType func_177045_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_177048_d.length) {}
            return EnumType.field_177048_d[0];
        }
        
        @Override
        public String getName() {
            return this.field_177046_f;
        }
    }
}
