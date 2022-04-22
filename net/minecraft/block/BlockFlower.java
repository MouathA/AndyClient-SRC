package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class BlockFlower extends BlockBush
{
    protected PropertyEnum field_176496_a;
    private static final String __OBFID;
    
    protected BlockFlower() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.func_176494_l(), (this.func_176495_j() == EnumFlowerColor.RED) ? EnumFlowerType.POPPY : EnumFlowerType.DANDELION));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumFlowerType)blockState.getValue(this.func_176494_l())).func_176968_b();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumFlowerType[] func_176966_a = EnumFlowerType.func_176966_a(this.func_176495_j());
        while (0 < func_176966_a.length) {
            list.add(new ItemStack(item, 1, func_176966_a[0].func_176968_b()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(this.func_176494_l(), EnumFlowerType.func_176967_a(this.func_176495_j(), n));
    }
    
    public abstract EnumFlowerColor func_176495_j();
    
    public IProperty func_176494_l() {
        if (this.field_176496_a == null) {
            this.field_176496_a = PropertyEnum.create("type", EnumFlowerType.class, new Predicate() {
                private static final String __OBFID;
                final BlockFlower this$0;
                
                public boolean func_180354_a(final EnumFlowerType enumFlowerType) {
                    return enumFlowerType.func_176964_a() == this.this$0.func_176495_j();
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_180354_a((EnumFlowerType)o);
                }
                
                static {
                    __OBFID = "CL_00002120";
                }
            });
        }
        return this.field_176496_a;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFlowerType)blockState.getValue(this.func_176494_l())).func_176968_b();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { this.func_176494_l() });
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    static {
        __OBFID = "CL_00000246";
    }
    
    public enum EnumFlowerColor
    {
        YELLOW("YELLOW", 0, "YELLOW", 0), 
        RED("RED", 1, "RED", 1);
        
        private static final EnumFlowerColor[] $VALUES;
        private static final String __OBFID;
        private static final EnumFlowerColor[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002117";
            ENUM$VALUES = new EnumFlowerColor[] { EnumFlowerColor.YELLOW, EnumFlowerColor.RED };
            $VALUES = new EnumFlowerColor[] { EnumFlowerColor.YELLOW, EnumFlowerColor.RED };
        }
        
        private EnumFlowerColor(final String s, final int n, final String s2, final int n2) {
        }
        
        public BlockFlower func_180346_a() {
            return (this == EnumFlowerColor.YELLOW) ? Blocks.yellow_flower : Blocks.red_flower;
        }
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        DANDELION("DANDELION", 0, "DANDELION", 0, EnumFlowerColor.YELLOW, 0, "dandelion"), 
        POPPY("POPPY", 1, "POPPY", 1, EnumFlowerColor.RED, 0, "poppy"), 
        BLUE_ORCHID("BLUE_ORCHID", 2, "BLUE_ORCHID", 2, EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"), 
        ALLIUM("ALLIUM", 3, "ALLIUM", 3, EnumFlowerColor.RED, 2, "allium"), 
        HOUSTONIA("HOUSTONIA", 4, "HOUSTONIA", 4, EnumFlowerColor.RED, 3, "houstonia"), 
        RED_TULIP("RED_TULIP", 5, "RED_TULIP", 5, EnumFlowerColor.RED, 4, "red_tulip", "tulipRed"), 
        ORANGE_TULIP("ORANGE_TULIP", 6, "ORANGE_TULIP", 6, EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"), 
        WHITE_TULIP("WHITE_TULIP", 7, "WHITE_TULIP", 7, EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"), 
        PINK_TULIP("PINK_TULIP", 8, "PINK_TULIP", 8, EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"), 
        OXEYE_DAISY("OXEYE_DAISY", 9, "OXEYE_DAISY", 9, EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");
        
        private static final EnumFlowerType[][] field_176981_k;
        private final EnumFlowerColor field_176978_l;
        private final int field_176979_m;
        private final String field_176976_n;
        private final String field_176977_o;
        private static final EnumFlowerType[] $VALUES;
        private static final String __OBFID;
        private static final EnumFlowerType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002119";
            ENUM$VALUES = new EnumFlowerType[] { EnumFlowerType.DANDELION, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY };
            field_176981_k = new EnumFlowerType[EnumFlowerColor.values().length][];
            $VALUES = new EnumFlowerType[] { EnumFlowerType.DANDELION, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY };
            final EnumFlowerColor[] values = EnumFlowerColor.values();
            while (0 < values.length) {
                final EnumFlowerColor enumFlowerColor = values[0];
                final Collection filter = Collections2.filter(Lists.newArrayList((Object[])values()), new Predicate() {
                    private static final String __OBFID;
                    private final EnumFlowerColor val$var3;
                    
                    public boolean func_180350_a(final EnumFlowerType enumFlowerType) {
                        return enumFlowerType.func_176964_a() == this.val$var3;
                    }
                    
                    @Override
                    public boolean apply(final Object o) {
                        return this.func_180350_a((EnumFlowerType)o);
                    }
                    
                    static {
                        __OBFID = "CL_00002118";
                    }
                });
                EnumFlowerType.field_176981_k[enumFlowerColor.ordinal()] = filter.toArray(new EnumFlowerType[filter.size()]);
                int n = 0;
                ++n;
            }
        }
        
        private EnumFlowerType(final String s, final int n, final String s2, final int n2, final EnumFlowerColor enumFlowerColor, final int n3, final String s3) {
            this(s, n, s2, n2, enumFlowerColor, n3, s3, s3);
        }
        
        private EnumFlowerType(final String s, final int n, final String s2, final int n2, final EnumFlowerColor field_176978_l, final int field_176979_m, final String field_176976_n, final String field_176977_o) {
            this.field_176978_l = field_176978_l;
            this.field_176979_m = field_176979_m;
            this.field_176976_n = field_176976_n;
            this.field_176977_o = field_176977_o;
        }
        
        public EnumFlowerColor func_176964_a() {
            return this.field_176978_l;
        }
        
        public int func_176968_b() {
            return this.field_176979_m;
        }
        
        public static EnumFlowerType func_176967_a(final EnumFlowerColor enumFlowerColor, final int n) {
            final EnumFlowerType[] array = EnumFlowerType.field_176981_k[enumFlowerColor.ordinal()];
            if (0 >= array.length) {}
            return array[0];
        }
        
        public static EnumFlowerType[] func_176966_a(final EnumFlowerColor enumFlowerColor) {
            return EnumFlowerType.field_176981_k[enumFlowerColor.ordinal()];
        }
        
        @Override
        public String toString() {
            return this.field_176976_n;
        }
        
        @Override
        public String getName() {
            return this.field_176976_n;
        }
        
        public String func_176963_d() {
            return this.field_176977_o;
        }
    }
}
