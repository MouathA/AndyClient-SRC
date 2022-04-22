package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockSapling extends BlockBush implements IGrowable
{
    public static final PropertyEnum TYPE_PROP;
    public static final PropertyInteger STAGE_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000305";
        TYPE_PROP = PropertyEnum.create("type", BlockPlanks.EnumType.class);
        STAGE_PROP = PropertyInteger.create("stage", 0, 1);
    }
    
    protected BlockSapling() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.OAK).withProperty(BlockSapling.STAGE_PROP, 0));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            super.updateTick(world, blockPos, blockState, random);
            if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9 && random.nextInt(7) == 0) {
                this.func_176478_d(world, blockPos, blockState, random);
            }
        }
    }
    
    public void func_176478_d(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if ((int)blockState.getValue(BlockSapling.STAGE_PROP) == 0) {
            world.setBlockState(blockPos, blockState.cycleProperty(BlockSapling.STAGE_PROP), 4);
        }
        else {
            this.func_176476_e(world, blockPos, blockState, random);
        }
    }
    
    public void func_176476_e(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        Object o = (random.nextInt(10) == 0) ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        switch (SwitchEnumType.field_177065_a[((BlockPlanks.EnumType)blockState.getValue(BlockSapling.TYPE_PROP)).ordinal()]) {
            case 1: {
            Label_0200:
                while (0 >= -1) {
                    while (0 >= -1) {
                        if (this.func_176477_a(world, blockPos.add(0, 0, 0), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(world, blockPos.add(1, 0, 0), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(world, blockPos.add(0, 0, 1), BlockPlanks.EnumType.SPRUCE) && this.func_176477_a(world, blockPos.add(1, 0, 1), BlockPlanks.EnumType.SPRUCE)) {
                            o = new WorldGenMegaPineTree(false, random.nextBoolean());
                            break Label_0200;
                        }
                        int n = 0;
                        --n;
                    }
                    int n2 = 0;
                    --n2;
                }
                if (!true) {
                    o = new WorldGenTaiga2(true);
                    break;
                }
                break;
            }
            case 2: {
                o = new WorldGenForest(true, false);
                break;
            }
            case 3: {
            Label_0354:
                while (0 >= -1) {
                    while (0 >= -1) {
                        if (this.func_176477_a(world, blockPos.add(0, 0, 0), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(world, blockPos.add(1, 0, 0), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(world, blockPos.add(0, 0, 1), BlockPlanks.EnumType.JUNGLE) && this.func_176477_a(world, blockPos.add(1, 0, 1), BlockPlanks.EnumType.JUNGLE)) {
                            o = new WorldGenMegaJungle(true, 10, 20, BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a());
                            break Label_0354;
                        }
                        int n = 0;
                        --n;
                    }
                    int n2 = 0;
                    --n2;
                }
                if (!true) {
                    o = new WorldGenTrees(true, 4 + random.nextInt(7), BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a(), false);
                    break;
                }
                break;
            }
            case 4: {
                o = new WorldGenSavannaTree(true);
                break;
            }
            case 5: {
            Label_0513:
                while (0 >= -1) {
                    while (0 >= -1) {
                        if (this.func_176477_a(world, blockPos.add(0, 0, 0), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(world, blockPos.add(1, 0, 0), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(world, blockPos.add(0, 0, 1), BlockPlanks.EnumType.DARK_OAK) && this.func_176477_a(world, blockPos.add(1, 0, 1), BlockPlanks.EnumType.DARK_OAK)) {
                            o = new WorldGenCanopyTree(true);
                            break Label_0513;
                        }
                        int n = 0;
                        --n;
                    }
                    int n2 = 0;
                    --n2;
                }
                if (!true) {
                    return;
                }
                break;
            }
        }
        final IBlockState defaultState = Blocks.air.getDefaultState();
        if (true) {
            world.setBlockState(blockPos.add(0, 0, 0), defaultState, 4);
            world.setBlockState(blockPos.add(1, 0, 0), defaultState, 4);
            world.setBlockState(blockPos.add(0, 0, 1), defaultState, 4);
            world.setBlockState(blockPos.add(1, 0, 1), defaultState, 4);
        }
        else {
            world.setBlockState(blockPos, defaultState, 4);
        }
        if (!((WorldGenerator)o).generate(world, random, blockPos.add(0, 0, 0))) {
            if (true) {
                world.setBlockState(blockPos.add(0, 0, 0), blockState, 4);
                world.setBlockState(blockPos.add(1, 0, 0), blockState, 4);
                world.setBlockState(blockPos.add(0, 0, 1), blockState, 4);
                world.setBlockState(blockPos.add(1, 0, 1), blockState, 4);
            }
            else {
                world.setBlockState(blockPos, blockState, 4);
            }
        }
    }
    
    public boolean func_176477_a(final World world, final BlockPos blockPos, final BlockPlanks.EnumType enumType) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock() == this && blockState.getValue(BlockSapling.TYPE_PROP) == enumType;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockSapling.TYPE_PROP)).func_176839_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final BlockPlanks.EnumType[] values = BlockPlanks.EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176839_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return true;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return world.rand.nextFloat() < 0.45;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176478_d(world, blockPos, blockState, random);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.func_176837_a(n & 0x7)).withProperty(BlockSapling.STAGE_PROP, (n & 0x8) >> 3);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockSapling.TYPE_PROP)).func_176839_a() | (int)blockState.getValue(BlockSapling.STAGE_PROP) << 3;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSapling.TYPE_PROP, BlockSapling.STAGE_PROP });
    }
    
    static final class SwitchEnumType
    {
        static final int[] field_177065_a;
        private static final String __OBFID;
        private static final String[] llllIlllIIIIIlI;
        private static String[] llllIlllIIIIIll;
        
        static {
            lIllIllIIIIllIlI();
            lIllIllIIIIllIIl();
            __OBFID = SwitchEnumType.llllIlllIIIIIlI[0];
            field_177065_a = new int[BlockPlanks.EnumType.values().length];
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumType.field_177065_a[BlockPlanks.EnumType.OAK.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lIllIllIIIIllIIl() {
            (llllIlllIIIIIlI = new String[1])[0] = lIllIllIIIIllIII(SwitchEnumType.llllIlllIIIIIll[0], SwitchEnumType.llllIlllIIIIIll[1]);
            SwitchEnumType.llllIlllIIIIIll = null;
        }
        
        private static void lIllIllIIIIllIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumType.llllIlllIIIIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllIllIIIIllIII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
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
