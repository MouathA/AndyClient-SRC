package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockVine extends Block
{
    public static final PropertyBool field_176277_a;
    public static final PropertyBool field_176273_b;
    public static final PropertyBool field_176278_M;
    public static final PropertyBool field_176279_N;
    public static final PropertyBool field_176280_O;
    public static final PropertyBool[] field_176274_P;
    public static final int field_176272_Q;
    public static final int field_176276_R;
    public static final int field_176275_S;
    public static final int field_176271_T;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000330";
        field_176277_a = PropertyBool.create("up");
        field_176273_b = PropertyBool.create("north");
        field_176278_M = PropertyBool.create("east");
        field_176279_N = PropertyBool.create("south");
        field_176280_O = PropertyBool.create("west");
        field_176274_P = new PropertyBool[] { BlockVine.field_176277_a, BlockVine.field_176273_b, BlockVine.field_176279_N, BlockVine.field_176280_O, BlockVine.field_176278_M };
        field_176272_Q = func_176270_b(EnumFacing.SOUTH);
        field_176276_R = func_176270_b(EnumFacing.NORTH);
        field_176275_S = func_176270_b(EnumFacing.EAST);
        field_176271_T = func_176270_b(EnumFacing.WEST);
    }
    
    public BlockVine() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockVine.field_176277_a, false).withProperty(BlockVine.field_176273_b, false).withProperty(BlockVine.field_176278_M, false).withProperty(BlockVine.field_176279_N, false).withProperty(BlockVine.field_176280_O, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockVine.field_176277_a, blockAccess.getBlockState(blockPos.offsetUp()).getBlock().isSolidFullCube());
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
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
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        float min = 1.0f;
        float n = 1.0f;
        float min2 = 1.0f;
        float max = 0.0f;
        float n2 = 0.0f;
        float max2 = 0.0f;
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.field_176280_O)) {
            max = Math.max(max, 0.0625f);
            min = 0.0f;
            n = 0.0f;
            n2 = 1.0f;
            min2 = 0.0f;
            max2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.field_176278_M)) {
            min = Math.min(min, 0.9375f);
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
            min2 = 0.0f;
            max2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.field_176273_b)) {
            max2 = Math.max(max2, 0.0625f);
            min2 = 0.0f;
            min = 0.0f;
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.field_176279_N)) {
            min2 = Math.min(min2, 0.9375f);
            max2 = 1.0f;
            min = 0.0f;
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
        }
        this.setBlockBounds(min, n, min2, max, n2, max2);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.field_177057_a[enumFacing.ordinal()]) {
            case 1: {
                return this.func_150093_a(world.getBlockState(blockPos.offsetUp()).getBlock());
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                return this.func_150093_a(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public int getBlockColor() {
        return 4764952;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return 4764952;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).func_180625_c(blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.isRemote:Z
        //     4: ifne            28
        //     7: aload_0        
        //     8: aload_1        
        //     9: aload_2        
        //    10: aload_3        
        //    11: ifeq            28
        //    14: aload_0        
        //    15: aload_1        
        //    16: aload_2        
        //    17: aload_3        
        //    18: iconst_0       
        //    19: invokevirtual   net/minecraft/block/BlockVine.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    22: aload_1        
        //    23: aload_2        
        //    24: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    27: pop            
        //    28: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0028 (coming from #0011).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && world.rand.nextInt(4) == 0) {
            final EnumFacing random2 = EnumFacing.random(random);
            if (random2 != EnumFacing.UP || blockPos.getY() >= 255 || !world.isAirBlock(blockPos.offsetUp())) {
                if (!random2.getAxis().isHorizontal() || (boolean)blockState.getValue(func_176267_a(random2))) {
                    if (blockPos.getY() > 1) {
                        final BlockPos offsetDown = blockPos.offsetDown();
                        final IBlockState blockState2 = world.getBlockState(offsetDown);
                        final Block block = blockState2.getBlock();
                        if (block.blockMaterial == Material.air) {
                            IBlockState withProperty = blockState;
                            for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                                if (random.nextBoolean()) {
                                    withProperty = withProperty.withProperty(func_176267_a(enumFacing), false);
                                }
                            }
                            if ((boolean)withProperty.getValue(BlockVine.field_176273_b) || (boolean)withProperty.getValue(BlockVine.field_176278_M) || (boolean)withProperty.getValue(BlockVine.field_176279_N) || (boolean)withProperty.getValue(BlockVine.field_176280_O)) {
                                world.setBlockState(offsetDown, withProperty, 2);
                            }
                        }
                        else if (block == this) {
                            IBlockState withProperty2 = blockState2;
                            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
                            while (iterator2.hasNext()) {
                                final PropertyBool func_176267_a = func_176267_a(iterator2.next());
                                if (random.nextBoolean() || !(boolean)blockState.getValue(func_176267_a)) {
                                    withProperty2 = withProperty2.withProperty(func_176267_a, false);
                                }
                            }
                            if ((boolean)withProperty2.getValue(BlockVine.field_176273_b) || (boolean)withProperty2.getValue(BlockVine.field_176278_M) || (boolean)withProperty2.getValue(BlockVine.field_176279_N) || (boolean)withProperty2.getValue(BlockVine.field_176280_O)) {
                                world.setBlockState(offsetDown, withProperty2, 2);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static int func_176270_b(final EnumFacing enumFacing) {
        return 1 << enumFacing.getHorizontalIndex();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockVine.field_176277_a, false).withProperty(BlockVine.field_176273_b, false).withProperty(BlockVine.field_176278_M, false).withProperty(BlockVine.field_176279_N, false).withProperty(BlockVine.field_176280_O, false);
        return enumFacing.getAxis().isHorizontal() ? withProperty.withProperty(func_176267_a(enumFacing.getOpposite()), true) : withProperty;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.vine, 1, 0));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockVine.field_176273_b, (n & BlockVine.field_176276_R) > 0).withProperty(BlockVine.field_176278_M, (n & BlockVine.field_176275_S) > 0).withProperty(BlockVine.field_176279_N, (n & BlockVine.field_176272_Q) > 0).withProperty(BlockVine.field_176280_O, (n & BlockVine.field_176271_T) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockVine.field_176273_b)) {
            final int n = 0x0 | BlockVine.field_176276_R;
        }
        if (blockState.getValue(BlockVine.field_176278_M)) {
            final int n2 = 0x0 | BlockVine.field_176275_S;
        }
        if (blockState.getValue(BlockVine.field_176279_N)) {
            final int n3 = 0x0 | BlockVine.field_176272_Q;
        }
        if (blockState.getValue(BlockVine.field_176280_O)) {
            final int n4 = 0x0 | BlockVine.field_176271_T;
        }
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockVine.field_176277_a, BlockVine.field_176273_b, BlockVine.field_176278_M, BlockVine.field_176279_N, BlockVine.field_176280_O });
    }
    
    public static PropertyBool func_176267_a(final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.field_177057_a[enumFacing.ordinal()]) {
            case 1: {
                return BlockVine.field_176277_a;
            }
            case 2: {
                return BlockVine.field_176273_b;
            }
            case 3: {
                return BlockVine.field_176279_N;
            }
            case 4: {
                return BlockVine.field_176278_M;
            }
            case 5: {
                return BlockVine.field_176280_O;
            }
            default: {
                throw new IllegalArgumentException(enumFacing + " is an invalid choice");
            }
        }
    }
    
    public static int func_176268_d(final IBlockState blockState) {
        final PropertyBool[] field_176274_P = BlockVine.field_176274_P;
        while (0 < field_176274_P.length) {
            if (blockState.getValue(field_176274_P[0])) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177057_a;
        private static final String __OBFID;
        private static final String[] lIIIlIllIlIlllIl;
        private static String[] lIIIlIllIlIlllll;
        
        static {
            llIIllIllIlIlIll();
            llIIllIllIlIlIlI();
            __OBFID = SwitchEnumFacing.lIIIlIllIlIlllIl[0];
            field_177057_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177057_a[EnumFacing.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177057_a[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177057_a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177057_a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_177057_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void llIIllIllIlIlIlI() {
            (lIIIlIllIlIlllIl = new String[1])[0] = llIIllIllIlIlIIl(SwitchEnumFacing.lIIIlIllIlIlllll[0], SwitchEnumFacing.lIIIlIllIlIlllll[1]);
            SwitchEnumFacing.lIIIlIllIlIlllll = null;
        }
        
        private static void llIIllIllIlIlIll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIIlIllIlIlllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIIllIllIlIlIIl(final String s, final String s2) {
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
