package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockHugeMushroom extends Block
{
    public static final PropertyEnum field_176380_a;
    private final Block field_176379_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000258";
        field_176380_a = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockHugeMushroom(final Material material, final Block field_176379_b) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHugeMushroom.field_176380_a, EnumType.ALL_OUTSIDE));
        this.field_176379_b = field_176379_b;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return Math.max(0, random.nextInt(10) - 7);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(this.field_176379_b);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(this.field_176379_b);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockHugeMushroom.field_176380_a, EnumType.func_176895_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockHugeMushroom.field_176380_a)).func_176896_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHugeMushroom.field_176380_a });
    }
    
    public enum EnumType implements IStringSerializable
    {
        NORTH_WEST("NORTH_WEST", 0, "NORTH_WEST", 0, 1, "north_west"), 
        NORTH("NORTH", 1, "NORTH", 1, 2, "north"), 
        NORTH_EAST("NORTH_EAST", 2, "NORTH_EAST", 2, 3, "north_east"), 
        WEST("WEST", 3, "WEST", 3, 4, "west"), 
        CENTER("CENTER", 4, "CENTER", 4, 5, "center"), 
        EAST("EAST", 5, "EAST", 5, 6, "east"), 
        SOUTH_WEST("SOUTH_WEST", 6, "SOUTH_WEST", 6, 7, "south_west"), 
        SOUTH("SOUTH", 7, "SOUTH", 7, 8, "south"), 
        SOUTH_EAST("SOUTH_EAST", 8, "SOUTH_EAST", 8, 9, "south_east"), 
        STEM("STEM", 9, "STEM", 9, 10, "stem"), 
        ALL_INSIDE("ALL_INSIDE", 10, "ALL_INSIDE", 10, 0, "all_inside"), 
        ALL_OUTSIDE("ALL_OUTSIDE", 11, "ALL_OUTSIDE", 11, 14, "all_outside"), 
        ALL_STEM("ALL_STEM", 12, "ALL_STEM", 12, 15, "all_stem");
        
        private static final EnumType[] field_176905_n;
        private final int field_176906_o;
        private final String field_176914_p;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002105";
            ENUM$VALUES = new EnumType[] { EnumType.NORTH_WEST, EnumType.NORTH, EnumType.NORTH_EAST, EnumType.WEST, EnumType.CENTER, EnumType.EAST, EnumType.SOUTH_WEST, EnumType.SOUTH, EnumType.SOUTH_EAST, EnumType.STEM, EnumType.ALL_INSIDE, EnumType.ALL_OUTSIDE, EnumType.ALL_STEM };
            field_176905_n = new EnumType[16];
            $VALUES = new EnumType[] { EnumType.NORTH_WEST, EnumType.NORTH, EnumType.NORTH_EAST, EnumType.WEST, EnumType.CENTER, EnumType.EAST, EnumType.SOUTH_WEST, EnumType.SOUTH, EnumType.SOUTH_EAST, EnumType.STEM, EnumType.ALL_INSIDE, EnumType.ALL_OUTSIDE, EnumType.ALL_STEM };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176905_n[enumType.func_176896_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176906_o, final String field_176914_p) {
            this.field_176906_o = field_176906_o;
            this.field_176914_p = field_176914_p;
        }
        
        public int func_176896_a() {
            return this.field_176906_o;
        }
        
        @Override
        public String toString() {
            return this.field_176914_p;
        }
        
        public static EnumType func_176895_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_176905_n.length) {}
            final EnumType enumType = EnumType.field_176905_n[0];
            return (enumType == null) ? EnumType.field_176905_n[0] : enumType;
        }
        
        @Override
        public String getName() {
            return this.field_176914_p;
        }
    }
}
