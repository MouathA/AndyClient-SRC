package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockStone extends Block
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000317";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStone.VARIANT_PROP, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockStone.VARIANT_PROP) == EnumType.STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStone.VARIANT_PROP)).getMetaFromState();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetaFromState()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStone.VARIANT_PROP, EnumType.getStateFromMeta(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockStone.VARIANT_PROP)).getMetaFromState();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStone.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        STONE("STONE", 0, "STONE", 0, 0, "stone"), 
        GRANITE("GRANITE", 1, "GRANITE", 1, 1, "granite"), 
        GRANITE_SMOOTH("GRANITE_SMOOTH", 2, "GRANITE_SMOOTH", 2, 2, "smooth_granite", "graniteSmooth"), 
        DIORITE("DIORITE", 3, "DIORITE", 3, 3, "diorite"), 
        DIORITE_SMOOTH("DIORITE_SMOOTH", 4, "DIORITE_SMOOTH", 4, 4, "smooth_diorite", "dioriteSmooth"), 
        ANDESITE("ANDESITE", 5, "ANDESITE", 5, 5, "andesite"), 
        ANDESITE_SMOOTH("ANDESITE_SMOOTH", 6, "ANDESITE_SMOOTH", 6, 6, "smooth_andesite", "andesiteSmooth");
        
        private static final EnumType[] BLOCKSTATES;
        private final int meta;
        private final String name;
        private final String field_176654_k;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002058";
            ENUM$VALUES = new EnumType[] { EnumType.STONE, EnumType.GRANITE, EnumType.GRANITE_SMOOTH, EnumType.DIORITE, EnumType.DIORITE_SMOOTH, EnumType.ANDESITE, EnumType.ANDESITE_SMOOTH };
            BLOCKSTATES = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.GRANITE, EnumType.GRANITE_SMOOTH, EnumType.DIORITE, EnumType.DIORITE_SMOOTH, EnumType.ANDESITE, EnumType.ANDESITE_SMOOTH };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.BLOCKSTATES[enumType.getMetaFromState()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int meta, final String name, final String field_176654_k) {
            this.meta = meta;
            this.name = name;
            this.field_176654_k = field_176654_k;
        }
        
        public int getMetaFromState() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static EnumType getStateFromMeta(final int n) {
            if (0 < 0 || 0 >= EnumType.BLOCKSTATES.length) {}
            return EnumType.BLOCKSTATES[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public String func_176644_c() {
            return this.field_176654_k;
        }
    }
}
