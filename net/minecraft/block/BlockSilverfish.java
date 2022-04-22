package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockSilverfish extends Block
{
    public static final PropertyEnum VARIANT_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000271";
        VARIANT_PROP = PropertyEnum.create("variant", EnumType.class);
    }
    
    public BlockSilverfish() {
        super(Material.clay);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSilverfish.VARIANT_PROP, EnumType.STONE));
        this.setHardness(0.0f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    public static boolean func_176377_d(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return blockState == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE) || block == Blocks.cobblestone || block == Blocks.stonebrick;
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        switch (SwitchEnumType.field_180178_a[((EnumType)blockState.getValue(BlockSilverfish.VARIANT_PROP)).ordinal()]) {
            case 1: {
                return new ItemStack(Blocks.cobblestone);
            }
            case 2: {
                return new ItemStack(Blocks.stonebrick);
            }
            case 3: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetaFromState());
            }
            case 4: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetaFromState());
            }
            case 5: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetaFromState());
            }
            default: {
                return new ItemStack(Blocks.stone);
            }
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            final EntitySilverfish entitySilverfish = new EntitySilverfish(world);
            entitySilverfish.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySilverfish);
            entitySilverfish.spawnExplosionParticle();
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176881_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSilverfish.VARIANT_PROP, EnumType.func_176879_a(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSilverfish.VARIANT_PROP)).func_176881_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSilverfish.VARIANT_PROP });
    }
    
    public enum EnumType implements IStringSerializable
    {
        STONE(0, "STONE", 0, 0, "stone", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE);
            }
            
            static {
                __OBFID = "CL_00002097";
            }
        }, 
        COBBLESTONE(1, "COBBLESTONE", 1, 1, "cobblestone", "cobble", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.cobblestone.getDefaultState();
            }
            
            static {
                __OBFID = "CL_00002096";
            }
        }, 
        STONEBRICK(2, "STONEBRICK", 2, 2, "stone_brick", "brick", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.DEFAULT);
            }
            
            static {
                __OBFID = "CL_00002095";
            }
        }, 
        MOSSY_STONEBRICK(3, "MOSSY_STONEBRICK", 3, 3, "mossy_brick", "mossybrick", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
            }
            
            static {
                __OBFID = "CL_00002094";
            }
        }, 
        CRACKED_STONEBRICK(4, "CRACKED_STONEBRICK", 4, 4, "cracked_brick", "crackedbrick", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
            }
            
            static {
                __OBFID = "CL_00002093";
            }
        }, 
        CHISELED_STONEBRICK(5, "CHISELED_STONEBRICK", 5, 5, "chiseled_brick", "chiseledbrick", (SwitchEnumType)null) {
            private static final String __OBFID;
            
            @Override
            public IBlockState func_176883_d() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CHISELED);
            }
            
            static {
                __OBFID = "CL_00002092";
            }
        };
        
        private static final EnumType[] field_176885_g;
        private final int field_176893_h;
        private final String field_176894_i;
        private final String field_176891_j;
        private static final EnumType[] $VALUES;
        private static final String __OBFID;
        private static final EnumType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002098";
            ENUM$VALUES = new EnumType[] { EnumType.STONE, EnumType.COBBLESTONE, EnumType.STONEBRICK, EnumType.MOSSY_STONEBRICK, EnumType.CRACKED_STONEBRICK, EnumType.CHISELED_STONEBRICK };
            field_176885_g = new EnumType[values().length];
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.COBBLESTONE, EnumType.STONEBRICK, EnumType.MOSSY_STONEBRICK, EnumType.CRACKED_STONEBRICK, EnumType.CHISELED_STONEBRICK };
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.field_176885_g[enumType.func_176881_a()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int field_176893_h, final String field_176894_i, final String field_176891_j) {
            this.field_176893_h = field_176893_h;
            this.field_176894_i = field_176894_i;
            this.field_176891_j = field_176891_j;
        }
        
        public int func_176881_a() {
            return this.field_176893_h;
        }
        
        @Override
        public String toString() {
            return this.field_176894_i;
        }
        
        public static EnumType func_176879_a(final int n) {
            if (0 < 0 || 0 >= EnumType.field_176885_g.length) {}
            return EnumType.field_176885_g[0];
        }
        
        @Override
        public String getName() {
            return this.field_176894_i;
        }
        
        public String func_176882_c() {
            return this.field_176891_j;
        }
        
        public abstract IBlockState func_176883_d();
        
        public static EnumType func_176878_a(final IBlockState blockState) {
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                if (blockState == enumType.func_176883_d()) {
                    return enumType;
                }
                int n = 0;
                ++n;
            }
            return EnumType.STONE;
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3, final SwitchEnumType switchEnumType) {
            this(s, n, s2, n2, n3, s3);
        }
        
        private EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3, final String s4, final SwitchEnumType switchEnumType) {
            this(s, n, s2, n2, n3, s3, s4);
        }
        
        EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3, final SwitchEnumType switchEnumType, final EnumType enumType) {
            this(s, n, s2, n2, n3, s3, switchEnumType);
        }
        
        EnumType(final String s, final int n, final String s2, final int n2, final int n3, final String s3, final String s4, final SwitchEnumType switchEnumType, final EnumType enumType) {
            this(s, n, s2, n2, n3, s3, s4, switchEnumType);
        }
    }
    
    static final class SwitchEnumType
    {
        static final int[] field_180178_a;
        private static final String __OBFID;
        private static final String[] lIllllIIlIllIlI;
        private static String[] lIllllIIlIllIll;
        
        static {
            lIIIlIlIlllIIllI();
            lIIIlIlIlllIIlII();
            __OBFID = SwitchEnumType.lIllllIIlIllIlI[0];
            field_180178_a = new int[EnumType.values().length];
            try {
                SwitchEnumType.field_180178_a[EnumType.COBBLESTONE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumType.field_180178_a[EnumType.STONEBRICK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumType.field_180178_a[EnumType.MOSSY_STONEBRICK.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumType.field_180178_a[EnumType.CRACKED_STONEBRICK.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumType.field_180178_a[EnumType.CHISELED_STONEBRICK.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lIIIlIlIlllIIlII() {
            (lIllllIIlIllIlI = new String[1])[0] = lIIIlIlIlllIIIlI(SwitchEnumType.lIllllIIlIllIll[0], SwitchEnumType.lIllllIIlIllIll[1]);
            SwitchEnumType.lIllllIIlIllIll = null;
        }
        
        private static void lIIIlIlIlllIIllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumType.lIllllIIlIllIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIlIlIlllIIIlI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
