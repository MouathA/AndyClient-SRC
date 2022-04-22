package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockTripWireHook extends Block
{
    public static final PropertyDirection field_176264_a;
    public static final PropertyBool field_176263_b;
    public static final PropertyBool field_176265_M;
    public static final PropertyBool field_176266_N;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000329";
        field_176264_a = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        field_176263_b = PropertyBool.create("powered");
        field_176265_M = PropertyBool.create("attached");
        field_176266_N = PropertyBool.create("suspended");
    }
    
    public BlockTripWireHook() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTripWireHook.field_176264_a, EnumFacing.NORTH).withProperty(BlockTripWireHook.field_176263_b, false).withProperty(BlockTripWireHook.field_176265_M, false).withProperty(BlockTripWireHook.field_176266_N, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockTripWireHook.field_176266_N, !World.doesBlockHaveSolidTopSurface(blockAccess, blockPos.offsetDown()));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
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
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing.getAxis().isHorizontal() && world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState().withProperty(BlockTripWireHook.field_176263_b, false).withProperty(BlockTripWireHook.field_176265_M, false).withProperty(BlockTripWireHook.field_176266_N, false);
        if (enumFacing.getAxis().isHorizontal()) {
            blockState = blockState.withProperty(BlockTripWireHook.field_176264_a, enumFacing);
        }
        return blockState;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        this.func_176260_a(world, blockPos, blockState, false, false, -1, null);
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: aload_0        
        //     3: if_acmpeq       65
        //     6: aload_0        
        //     7: aload_1        
        //     8: aload_2        
        //     9: aload_3        
        //    10: ifne            65
        //    13: aload_3        
        //    14: getstatic       net/minecraft/block/BlockTripWireHook.field_176264_a:Lnet/minecraft/block/properties/PropertyDirection;
        //    17: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    22: checkcast       Lnet/minecraft/util/EnumFacing;
        //    25: astore          5
        //    27: aload_1        
        //    28: aload_2        
        //    29: aload           5
        //    31: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    34: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    37: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    40: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    45: invokevirtual   net/minecraft/block/Block.isNormalCube:()Z
        //    48: ifne            65
        //    51: aload_0        
        //    52: aload_1        
        //    53: aload_2        
        //    54: aload_3        
        //    55: iconst_0       
        //    56: invokevirtual   net/minecraft/block/BlockTripWireHook.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    59: aload_1        
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    64: pop            
        //    65: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0065 (coming from #0010).
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
    
    public void func_176260_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b, final boolean b2, final int n, final IBlockState blockState2) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTripWireHook.field_176264_a);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWireHook.field_176265_M);
        final boolean booleanValue2 = (boolean)blockState.getValue(BlockTripWireHook.field_176263_b);
        final boolean b3 = !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
        final boolean b4 = !b;
        final IBlockState[] array = new IBlockState[42];
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockTripWireHook.field_176265_M, false).withProperty(BlockTripWireHook.field_176263_b, false);
        this.func_180694_a(world, blockPos, false, false, booleanValue, booleanValue2);
        if (!b) {
            world.setBlockState(blockPos, withProperty.withProperty(BlockTripWireHook.field_176264_a, enumFacing), 3);
            if (b2) {
                this.func_176262_b(world, blockPos, enumFacing);
            }
        }
        if (booleanValue) {}
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.func_176260_a(world, blockPos, blockState, false, true, -1, null);
    }
    
    private void func_180694_a(final World world, final BlockPos blockPos, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        if (b2 && !b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!b2 && b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (b && !b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!b && b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.bowhit", 0.4f, 1.2f / (world.rand.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    private void func_176262_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch (SwitchEnumFacing.field_177056_a[((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockTripWireHook.field_176264_a)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                break;
            }
            case 3: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                break;
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWireHook.field_176265_M);
        final boolean booleanValue2 = (boolean)blockState.getValue(BlockTripWireHook.field_176263_b);
        if (booleanValue || booleanValue2) {
            this.func_176260_a(world, blockPos, blockState, true, false, -1, null);
        }
        if (booleanValue2) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(((EnumFacing)blockState.getValue(BlockTripWireHook.field_176264_a)).getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockTripWireHook.field_176263_b) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockTripWireHook.field_176263_b) ? ((blockState.getValue(BlockTripWireHook.field_176264_a) == enumFacing) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTripWireHook.field_176264_a, EnumFacing.getHorizontal(n & 0x3)).withProperty(BlockTripWireHook.field_176263_b, (n & 0x8) > 0).withProperty(BlockTripWireHook.field_176265_M, (n & 0x4) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockTripWireHook.field_176264_a)).getHorizontalIndex();
        if (blockState.getValue(BlockTripWireHook.field_176263_b)) {
            n |= 0x8;
        }
        if (blockState.getValue(BlockTripWireHook.field_176265_M)) {
            n |= 0x4;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTripWireHook.field_176264_a, BlockTripWireHook.field_176263_b, BlockTripWireHook.field_176265_M, BlockTripWireHook.field_176266_N });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177056_a;
        private static final String __OBFID;
        private static final String[] lllllIIlllIIIll;
        private static String[] lllllIIlllIIlII;
        
        static {
            lIlllIIIIIIllIlI();
            lIlllIIIIIIllIIl();
            __OBFID = SwitchEnumFacing.lllllIIlllIIIll[0];
            field_177056_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177056_a[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177056_a[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177056_a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177056_a[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIlllIIIIIIllIIl() {
            (lllllIIlllIIIll = new String[1])[0] = lIlllIIIIIIllIII(SwitchEnumFacing.lllllIIlllIIlII[0], SwitchEnumFacing.lllllIIlllIIlII[1]);
            SwitchEnumFacing.lllllIIlllIIlII = null;
        }
        
        private static void lIlllIIIIIIllIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lllllIIlllIIlII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIlllIIIIIIllIII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
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
