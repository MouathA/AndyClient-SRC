package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockWall extends Block
{
    public static final PropertyBool field_176256_a;
    public static final PropertyBool field_176254_b;
    public static final PropertyBool field_176257_M;
    public static final PropertyBool field_176258_N;
    public static final PropertyBool field_176259_O;
    public static final PropertyEnum field_176255_P;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000331";
        field_176256_a = PropertyBool.create("up");
        field_176254_b = PropertyBool.create("north");
        field_176257_M = PropertyBool.create("east");
        field_176258_N = PropertyBool.create("south");
        field_176259_O = PropertyBool.create("west");
        field_176255_P = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockWall(final Block block) {
        super(block.blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWall.field_176256_a, false).withProperty(BlockWall.field_176254_b, false).withProperty(BlockWall.field_176257_M, false).withProperty(BlockWall.field_176258_N, false).withProperty(BlockWall.field_176259_O, false).withProperty(BlockWall.field_176255_P, EnumType.NORMAL));
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0f);
        this.setStepSound(block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean func_176253_e = this.func_176253_e(blockAccess, blockPos.offsetNorth());
        final boolean func_176253_e2 = this.func_176253_e(blockAccess, blockPos.offsetSouth());
        final boolean func_176253_e3 = this.func_176253_e(blockAccess, blockPos.offsetWest());
        final boolean func_176253_e4 = this.func_176253_e(blockAccess, blockPos.offsetEast());
        float n = 0.25f;
        float n2 = 0.75f;
        float n3 = 0.25f;
        float n4 = 0.75f;
        float n5 = 1.0f;
        if (func_176253_e) {
            n3 = 0.0f;
        }
        if (func_176253_e2) {
            n4 = 1.0f;
        }
        if (func_176253_e3) {
            n = 0.0f;
        }
        if (func_176253_e4) {
            n2 = 1.0f;
        }
        if (func_176253_e && func_176253_e2 && !func_176253_e3 && !func_176253_e4) {
            n5 = 0.8125f;
            n = 0.3125f;
            n2 = 0.6875f;
        }
        else if (!func_176253_e && !func_176253_e2 && func_176253_e3 && func_176253_e4) {
            n5 = 0.8125f;
            n3 = 0.3125f;
            n4 = 0.6875f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, n5, n4);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        this.maxY = 1.5;
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    public boolean func_176253_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        return block != Blocks.barrier && (block == this || block instanceof BlockFenceGate || (block.blockMaterial.isOpaque() && block.isFullCube() && block.blockMaterial != Material.gourd));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176657_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockWall.field_176255_P)).func_176657_a();
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing != EnumFacing.DOWN || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockWall.field_176255_P, EnumType.func_176660_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockWall.field_176255_P)).func_176657_a();
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockWall.field_176256_a, !blockAccess.isAirBlock(blockPos.offsetUp())).withProperty(BlockWall.field_176254_b, this.func_176253_e(blockAccess, blockPos.offsetNorth())).withProperty(BlockWall.field_176257_M, this.func_176253_e(blockAccess, blockPos.offsetEast())).withProperty(BlockWall.field_176258_N, this.func_176253_e(blockAccess, blockPos.offsetSouth())).withProperty(BlockWall.field_176259_O, this.func_176253_e(blockAccess, blockPos.offsetWest()));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockWall.field_176256_a, BlockWall.field_176254_b, BlockWall.field_176257_M, BlockWall.field_176259_O, BlockWall.field_176258_N, BlockWall.field_176255_P });
    }
    
    public enum EnumType implements IStringSerializable
    {
        NORMAL("NORMAL", 0, "NORMAL", 0, 0, "cobblestone", "normal"), 
        MOSSY("MOSSY", 1, "MOSSY", 1, 1, "mossy_cobblestone", "mossy");
        
        private static final EnumType[] field_176666_c;
        private final int field_176663_d;
        private final String field_176664_e;
        private String field_176661_f;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002048";
            ENUM$VALUES = new EnumType[] { EnumType.NORMAL, EnumType.MOSSY };
            field_176666_c = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.NORMAL, EnumType.MOSSY };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176666_c[enumType.func_176657_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176663_d, final String field_176664_e, final String field_176661_f) {
            this.field_176663_d = field_176663_d;
            this.field_176664_e = field_176664_e;
            this.field_176661_f = field_176661_f;
        }
        
        public int func_176657_a() {
            return this.field_176663_d;
        }
        
        @Override
        public String toString() {
            return this.field_176664_e;
        }
        
        public static EnumType func_176660_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_176666_c.length) {}
            return EnumType.field_176666_c[0];
        }
        
        @Override
        public String getName() {
            return this.field_176664_e;
        }
        
        public String func_176659_c() {
            return this.field_176661_f;
        }
    }
}
