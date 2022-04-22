package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockLeaves extends BlockLeavesBase
{
    public static final PropertyBool field_176237_a;
    public static final PropertyBool field_176236_b;
    int[] field_150128_a;
    protected int field_150127_b;
    protected boolean field_176238_O;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000263";
        field_176237_a = PropertyBool.create("decayable");
        field_176236_b = PropertyBool.create("check_decay");
    }
    
    public BlockLeaves() {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setStepSound(BlockLeaves.soundTypeGrass);
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5, 1.0);
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return 4764952;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.func_180287_b(blockAccess, blockPos);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (world.isAreaLoaded(new BlockPos(x - 2, y - 2, z - 2), new BlockPos(x + 2, y + 2, z + 2))) {
            while (-1 <= 1) {
                while (-1 <= 1) {
                    while (-1 <= 1) {
                        final BlockPos add = blockPos.add(-1, -1, -1);
                        final IBlockState blockState2 = world.getBlockState(add);
                        if (blockState2.getBlock().getMaterial() == Material.leaves && !(boolean)blockState2.getValue(BlockLeaves.field_176236_b)) {
                            world.setBlockState(add, blockState2.withProperty(BlockLeaves.field_176236_b, true), 4);
                        }
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.isRemote:Z
        //     4: ifne            481
        //     7: aload_3        
        //     8: getstatic       net/minecraft/block/BlockLeaves.field_176236_b:Lnet/minecraft/block/properties/PropertyBool;
        //    11: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    16: checkcast       Ljava/lang/Boolean;
        //    19: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    22: ifeq            481
        //    25: aload_3        
        //    26: getstatic       net/minecraft/block/BlockLeaves.field_176237_a:Lnet/minecraft/block/properties/PropertyBool;
        //    29: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    34: checkcast       Ljava/lang/Boolean;
        //    37: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    40: ifeq            481
        //    43: aload_2        
        //    44: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    47: istore          7
        //    49: aload_2        
        //    50: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    53: istore          8
        //    55: aload_2        
        //    56: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    59: istore          9
        //    61: aload_0        
        //    62: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //    65: ifnonnull       76
        //    68: aload_0        
        //    69: ldc             32768
        //    71: newarray        I
        //    73: putfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //    76: aload_1        
        //    77: new             Lnet/minecraft/util/BlockPos;
        //    80: dup            
        //    81: iload           7
        //    83: iconst_5       
        //    84: isub           
        //    85: iload           8
        //    87: iconst_5       
        //    88: isub           
        //    89: iload           9
        //    91: iconst_5       
        //    92: isub           
        //    93: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    96: new             Lnet/minecraft/util/BlockPos;
        //    99: dup            
        //   100: iload           7
        //   102: iconst_5       
        //   103: iadd           
        //   104: iload           8
        //   106: iconst_5       
        //   107: iadd           
        //   108: iload           9
        //   110: iconst_5       
        //   111: iadd           
        //   112: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   115: invokevirtual   net/minecraft/world/World.isAreaLoaded:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Z
        //   118: ifeq            440
        //   121: goto            244
        //   124: goto            235
        //   127: goto            226
        //   130: aload_1        
        //   131: new             Lnet/minecraft/util/BlockPos;
        //   134: dup            
        //   135: iload           7
        //   137: iconst_1       
        //   138: iadd           
        //   139: iload           8
        //   141: bipush          -4
        //   143: iadd           
        //   144: iload           9
        //   146: bipush          -4
        //   148: iadd           
        //   149: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   152: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   155: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   160: astore          16
        //   162: aload           16
        //   164: getstatic       net/minecraft/init/Blocks.log:Lnet/minecraft/block/Block;
        //   167: if_acmpeq       214
        //   170: aload           16
        //   172: getstatic       net/minecraft/init/Blocks.log2:Lnet/minecraft/block/Block;
        //   175: if_acmpeq       214
        //   178: aload           16
        //   180: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   183: getstatic       net/minecraft/block/material/Material.leaves:Lnet/minecraft/block/material/Material;
        //   186: if_acmpne       202
        //   189: aload_0        
        //   190: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   193: sipush          892
        //   196: bipush          -2
        //   198: iastore        
        //   199: goto            223
        //   202: aload_0        
        //   203: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   206: sipush          892
        //   209: iconst_m1      
        //   210: iastore        
        //   211: goto            223
        //   214: aload_0        
        //   215: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   218: sipush          892
        //   221: iconst_0       
        //   222: iastore        
        //   223: iinc            15, 1
        //   226: bipush          -4
        //   228: iconst_4       
        //   229: if_icmple       130
        //   232: iinc            14, 1
        //   235: bipush          -4
        //   237: iconst_4       
        //   238: if_icmple       127
        //   241: iinc            13, 1
        //   244: iconst_1       
        //   245: iconst_4       
        //   246: if_icmple       124
        //   249: goto            435
        //   252: goto            426
        //   255: goto            417
        //   258: goto            408
        //   261: aload_0        
        //   262: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   265: sipush          -4228
        //   268: iaload         
        //   269: iconst_0       
        //   270: if_icmpne       405
        //   273: aload_0        
        //   274: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   277: sipush          -5252
        //   280: iaload         
        //   281: bipush          -2
        //   283: if_icmpne       295
        //   286: aload_0        
        //   287: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   290: sipush          -5252
        //   293: iconst_1       
        //   294: iastore        
        //   295: aload_0        
        //   296: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   299: sipush          -3204
        //   302: iaload         
        //   303: bipush          -2
        //   305: if_icmpne       317
        //   308: aload_0        
        //   309: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   312: sipush          -3204
        //   315: iconst_1       
        //   316: iastore        
        //   317: aload_0        
        //   318: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   321: sipush          -4260
        //   324: iaload         
        //   325: bipush          -2
        //   327: if_icmpne       339
        //   330: aload_0        
        //   331: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   334: sipush          -4260
        //   337: iconst_1       
        //   338: iastore        
        //   339: aload_0        
        //   340: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   343: sipush          -4196
        //   346: iaload         
        //   347: bipush          -2
        //   349: if_icmpne       361
        //   352: aload_0        
        //   353: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   356: sipush          -4196
        //   359: iconst_1       
        //   360: iastore        
        //   361: aload_0        
        //   362: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   365: sipush          -4229
        //   368: iaload         
        //   369: bipush          -2
        //   371: if_icmpne       383
        //   374: aload_0        
        //   375: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   378: sipush          -4229
        //   381: iconst_1       
        //   382: iastore        
        //   383: aload_0        
        //   384: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   387: sipush          -4227
        //   390: iaload         
        //   391: bipush          -2
        //   393: if_icmpne       405
        //   396: aload_0        
        //   397: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   400: sipush          -4227
        //   403: iconst_1       
        //   404: iastore        
        //   405: iinc            16, 1
        //   408: bipush          -4
        //   410: iconst_4       
        //   411: if_icmple       261
        //   414: iinc            15, 1
        //   417: bipush          -4
        //   419: iconst_4       
        //   420: if_icmple       258
        //   423: iinc            14, 1
        //   426: bipush          -4
        //   428: iconst_4       
        //   429: if_icmple       255
        //   432: iinc            13, 1
        //   435: iconst_1       
        //   436: iconst_4       
        //   437: if_icmple       252
        //   440: aload_0        
        //   441: getfield        net/minecraft/block/BlockLeaves.field_150128_a:[I
        //   444: iconst_0       
        //   445: iaload         
        //   446: istore          13
        //   448: iconst_1       
        //   449: iflt            475
        //   452: aload_1        
        //   453: aload_2        
        //   454: aload_3        
        //   455: getstatic       net/minecraft/block/BlockLeaves.field_176236_b:Lnet/minecraft/block/properties/PropertyBool;
        //   458: iconst_0       
        //   459: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   462: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //   467: iconst_4       
        //   468: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   471: pop            
        //   472: goto            481
        //   475: aload_0        
        //   476: aload_1        
        //   477: aload_2        
        //   478: invokespecial   net/minecraft/block/BlockLeaves.func_176235_d:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)V
        //   481: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (world.func_175727_C(blockPos.offsetUp()) && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) && random.nextInt(15) == 1) {
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, blockPos.getX() + random.nextFloat(), blockPos.getY() - 0.05, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    private void func_176235_d(final World world, final BlockPos blockToAir) {
        this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
        world.setBlockToAir(blockToAir);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return (random.nextInt(20) == 0) ? 1 : 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            this.func_176232_d(blockState);
            if (n2 <= 0 || 40 < 10) {}
            if (world.rand.nextInt(40) == 0) {
                Block.spawnAsEntity(world, blockPos, new ItemStack(this.getItemDropped(blockState, world.rand, n2), 1, this.damageDropped(blockState)));
            }
            if (n2 <= 0 || 40 < 40) {}
            this.func_176234_a(world, blockPos, blockState, 40);
        }
    }
    
    protected void func_176234_a(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
    }
    
    protected int func_176232_d(final IBlockState blockState) {
        return 20;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return !this.field_150121_P;
    }
    
    public void setGraphicsLevel(final boolean b) {
        this.field_176238_O = b;
        this.field_150121_P = b;
        this.field_150127_b = (b ? 0 : 1);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return this.field_176238_O ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
    }
    
    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }
    
    public abstract BlockPlanks.EnumType func_176233_b(final int p0);
}
