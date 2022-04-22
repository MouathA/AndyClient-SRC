package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.state.pattern.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockSkull extends BlockContainer
{
    public static final PropertyDirection field_176418_a;
    public static final PropertyBool field_176417_b;
    private static final Predicate field_176419_M;
    private BlockPattern field_176420_N;
    private BlockPattern field_176421_O;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000307";
        field_176418_a = PropertyDirection.create("facing");
        field_176417_b = PropertyBool.create("nodrop");
        field_176419_M = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_177062_a(final BlockWorldState blockWorldState) {
                return blockWorldState.func_177509_a().getBlock() == Blocks.skull && blockWorldState.func_177507_b() instanceof TileEntitySkull && ((TileEntitySkull)blockWorldState.func_177507_b()).getSkullType() == 1;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_177062_a((BlockWorldState)o);
            }
            
            static {
                __OBFID = "CL_00002065";
            }
        };
    }
    
    protected BlockSkull() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSkull.field_176418_a, EnumFacing.NORTH).withProperty(BlockSkull.field_176417_b, false));
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
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
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        switch (SwitchEnumFacing.field_177063_a[((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockSkull.field_176418_a)).ordinal()]) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockSkull.field_176418_a, entityLivingBase.func_174811_aO()).withProperty(BlockSkull.field_176417_b, false);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntitySkull();
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.skull;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntitySkull) ? ((TileEntitySkull)tileEntity).getSkullType() : super.getDamageValue(world, blockPos);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            withProperty = withProperty.withProperty(BlockSkull.field_176417_b, true);
            world.setBlockState(blockPos, withProperty, 4);
        }
        super.onBlockHarvested(world, blockPos, withProperty, entityPlayer);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            if (!(boolean)blockState.getValue(BlockSkull.field_176417_b)) {
                final TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntitySkull) {
                    final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                    final ItemStack itemStack = new ItemStack(Items.skull, 1, this.getDamageValue(world, blockPos));
                    if (tileEntitySkull.getSkullType() == 3 && tileEntitySkull.getPlayerProfile() != null) {
                        itemStack.setTagCompound(new NBTTagCompound());
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        NBTUtil.writeGameProfile(nbtTagCompound, tileEntitySkull.getPlayerProfile());
                        itemStack.getTagCompound().setTag("SkullOwner", nbtTagCompound);
                    }
                    Block.spawnAsEntity(world, blockPos, itemStack);
                }
            }
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.skull;
    }
    
    public boolean func_176415_b(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        return itemStack.getMetadata() == 1 && blockPos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote && this.func_176414_j().func_177681_a(world, blockPos) != null;
    }
    
    public void func_180679_a(final World p0, final BlockPos p1, final TileEntitySkull p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/tileentity/TileEntitySkull.getSkullType:()I
        //     4: iconst_1       
        //     5: if_icmpne       478
        //     8: aload_2        
        //     9: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    12: iconst_2       
        //    13: if_icmplt       478
        //    16: aload_1        
        //    17: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //    20: getstatic       net/minecraft/world/EnumDifficulty.PEACEFUL:Lnet/minecraft/world/EnumDifficulty;
        //    23: if_acmpeq       478
        //    26: aload_1        
        //    27: getfield        net/minecraft/world/World.isRemote:Z
        //    30: ifne            478
        //    33: aload_0        
        //    34: invokevirtual   net/minecraft/block/BlockSkull.func_176416_l:()Lnet/minecraft/block/state/pattern/BlockPattern;
        //    37: astore          4
        //    39: aload           4
        //    41: aload_1        
        //    42: aload_2        
        //    43: invokevirtual   net/minecraft/block/state/pattern/BlockPattern.func_177681_a:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/pattern/BlockPattern$PatternHelper;
        //    46: astore          5
        //    48: aload           5
        //    50: ifnull          478
        //    53: goto            97
        //    56: aload           5
        //    58: iconst_0       
        //    59: iconst_0       
        //    60: iconst_0       
        //    61: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177670_a:(III)Lnet/minecraft/block/state/BlockWorldState;
        //    64: astore          7
        //    66: aload_1        
        //    67: aload           7
        //    69: invokevirtual   net/minecraft/block/state/BlockWorldState.getPos:()Lnet/minecraft/util/BlockPos;
        //    72: aload           7
        //    74: invokevirtual   net/minecraft/block/state/BlockWorldState.func_177509_a:()Lnet/minecraft/block/state/IBlockState;
        //    77: getstatic       net/minecraft/block/BlockSkull.field_176417_b:Lnet/minecraft/block/properties/PropertyBool;
        //    80: iconst_1       
        //    81: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    84: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    89: iconst_2       
        //    90: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    93: pop            
        //    94: iinc            6, 1
        //    97: iconst_0       
        //    98: iconst_3       
        //    99: if_icmplt       56
        //   102: goto            150
        //   105: goto            138
        //   108: aload           5
        //   110: iconst_0       
        //   111: iconst_0       
        //   112: iconst_0       
        //   113: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177670_a:(III)Lnet/minecraft/block/state/BlockWorldState;
        //   116: astore          8
        //   118: aload_1        
        //   119: aload           8
        //   121: invokevirtual   net/minecraft/block/state/BlockWorldState.getPos:()Lnet/minecraft/util/BlockPos;
        //   124: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //   127: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   130: iconst_2       
        //   131: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   134: pop            
        //   135: iinc            7, 1
        //   138: iconst_0       
        //   139: aload           4
        //   141: invokevirtual   net/minecraft/block/state/pattern/BlockPattern.func_177685_b:()I
        //   144: if_icmplt       108
        //   147: iinc            6, 1
        //   150: iconst_0       
        //   151: aload           4
        //   153: invokevirtual   net/minecraft/block/state/pattern/BlockPattern.func_177684_c:()I
        //   156: if_icmplt       105
        //   159: aload           5
        //   161: iconst_1       
        //   162: iconst_0       
        //   163: iconst_0       
        //   164: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177670_a:(III)Lnet/minecraft/block/state/BlockWorldState;
        //   167: invokevirtual   net/minecraft/block/state/BlockWorldState.getPos:()Lnet/minecraft/util/BlockPos;
        //   170: astore          7
        //   172: new             Lnet/minecraft/entity/boss/EntityWither;
        //   175: dup            
        //   176: aload_1        
        //   177: invokespecial   net/minecraft/entity/boss/EntityWither.<init>:(Lnet/minecraft/world/World;)V
        //   180: astore          8
        //   182: aload           5
        //   184: iconst_1       
        //   185: iconst_2       
        //   186: iconst_0       
        //   187: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177670_a:(III)Lnet/minecraft/block/state/BlockWorldState;
        //   190: invokevirtual   net/minecraft/block/state/BlockWorldState.getPos:()Lnet/minecraft/util/BlockPos;
        //   193: astore          9
        //   195: aload           8
        //   197: aload           9
        //   199: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   202: i2d            
        //   203: ldc2_w          0.5
        //   206: dadd           
        //   207: aload           9
        //   209: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   212: i2d            
        //   213: ldc2_w          0.55
        //   216: dadd           
        //   217: aload           9
        //   219: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   222: i2d            
        //   223: ldc2_w          0.5
        //   226: dadd           
        //   227: aload           5
        //   229: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177669_b:()Lnet/minecraft/util/EnumFacing;
        //   232: invokevirtual   net/minecraft/util/EnumFacing.getAxis:()Lnet/minecraft/util/EnumFacing$Axis;
        //   235: getstatic       net/minecraft/util/EnumFacing$Axis.X:Lnet/minecraft/util/EnumFacing$Axis;
        //   238: if_acmpne       245
        //   241: fconst_0       
        //   242: goto            248
        //   245: ldc_w           90.0
        //   248: fconst_0       
        //   249: invokevirtual   net/minecraft/entity/boss/EntityWither.setLocationAndAngles:(DDDFF)V
        //   252: aload           8
        //   254: aload           5
        //   256: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177669_b:()Lnet/minecraft/util/EnumFacing;
        //   259: invokevirtual   net/minecraft/util/EnumFacing.getAxis:()Lnet/minecraft/util/EnumFacing$Axis;
        //   262: getstatic       net/minecraft/util/EnumFacing$Axis.X:Lnet/minecraft/util/EnumFacing$Axis;
        //   265: if_acmpne       272
        //   268: fconst_0       
        //   269: goto            275
        //   272: ldc_w           90.0
        //   275: putfield        net/minecraft/entity/boss/EntityWither.renderYawOffset:F
        //   278: aload           8
        //   280: invokevirtual   net/minecraft/entity/boss/EntityWither.func_82206_m:()V
        //   283: aload_1        
        //   284: ldc             Lnet/minecraft/entity/player/EntityPlayer;.class
        //   286: aload           8
        //   288: invokevirtual   net/minecraft/entity/boss/EntityWither.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   291: ldc2_w          50.0
        //   294: ldc2_w          50.0
        //   297: ldc2_w          50.0
        //   300: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   303: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABB:(Ljava/lang/Class;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   306: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   311: astore          10
        //   313: goto            336
        //   316: aload           10
        //   318: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   323: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   326: astore          11
        //   328: aload           11
        //   330: getstatic       net/minecraft/stats/AchievementList.spawnWither:Lnet/minecraft/stats/Achievement;
        //   333: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   336: aload           10
        //   338: invokeinterface java/util/Iterator.hasNext:()Z
        //   343: ifne            316
        //   346: aload_1        
        //   347: aload           8
        //   349: invokevirtual   net/minecraft/world/World.spawnEntityInWorld:(Lnet/minecraft/entity/Entity;)Z
        //   352: pop            
        //   353: goto            420
        //   356: aload_1        
        //   357: getstatic       net/minecraft/util/EnumParticleTypes.SNOWBALL:Lnet/minecraft/util/EnumParticleTypes;
        //   360: aload           7
        //   362: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   365: i2d            
        //   366: aload_1        
        //   367: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   370: invokevirtual   java/util/Random.nextDouble:()D
        //   373: dadd           
        //   374: aload           7
        //   376: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   379: iconst_2       
        //   380: isub           
        //   381: i2d            
        //   382: aload_1        
        //   383: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   386: invokevirtual   java/util/Random.nextDouble:()D
        //   389: ldc2_w          3.9
        //   392: dmul           
        //   393: dadd           
        //   394: aload           7
        //   396: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   399: i2d            
        //   400: aload_1        
        //   401: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   404: invokevirtual   java/util/Random.nextDouble:()D
        //   407: dadd           
        //   408: dconst_0       
        //   409: dconst_0       
        //   410: dconst_0       
        //   411: iconst_0       
        //   412: newarray        I
        //   414: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   417: iinc            11, 1
        //   420: iconst_0       
        //   421: bipush          120
        //   423: if_icmplt       356
        //   426: goto            469
        //   429: goto            457
        //   432: aload           5
        //   434: iconst_0       
        //   435: iconst_0       
        //   436: iconst_0       
        //   437: invokevirtual   net/minecraft/block/state/pattern/BlockPattern$PatternHelper.func_177670_a:(III)Lnet/minecraft/block/state/BlockWorldState;
        //   440: astore          13
        //   442: aload_1        
        //   443: aload           13
        //   445: invokevirtual   net/minecraft/block/state/BlockWorldState.getPos:()Lnet/minecraft/util/BlockPos;
        //   448: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //   451: invokevirtual   net/minecraft/world/World.func_175722_b:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)V
        //   454: iinc            12, 1
        //   457: iconst_0       
        //   458: aload           4
        //   460: invokevirtual   net/minecraft/block/state/pattern/BlockPattern.func_177685_b:()I
        //   463: if_icmplt       432
        //   466: iinc            11, 1
        //   469: iconst_0       
        //   470: aload           4
        //   472: invokevirtual   net/minecraft/block/state/pattern/BlockPattern.func_177684_c:()I
        //   475: if_icmplt       429
        //   478: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSkull.field_176418_a, EnumFacing.getFront(n & 0x7)).withProperty(BlockSkull.field_176417_b, (n & 0x8) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockSkull.field_176418_a)).getIndex();
        if (blockState.getValue(BlockSkull.field_176417_b)) {
            n |= 0x8;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSkull.field_176418_a, BlockSkull.field_176417_b });
    }
    
    protected BlockPattern func_176414_j() {
        if (this.field_176420_N == null) {
            this.field_176420_N = FactoryBlockPattern.start().aisle("   ", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176420_N;
    }
    
    protected BlockPattern func_176416_l() {
        if (this.field_176421_O == null) {
            this.field_176421_O = FactoryBlockPattern.start().aisle("^^^", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', BlockSkull.field_176419_M).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176421_O;
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177063_a;
        private static final String __OBFID;
        private static final String[] llllIIlllllIllI;
        private static String[] llllIIlllllIlll;
        
        static {
            lIllIIllllIIlIII();
            lIllIIllllIIIlll();
            __OBFID = SwitchEnumFacing.llllIIlllllIllI[0];
            field_177063_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177063_a[EnumFacing.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177063_a[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177063_a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177063_a[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_177063_a[EnumFacing.EAST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lIllIIllllIIIlll() {
            (llllIIlllllIllI = new String[1])[0] = lIllIIllllIIIllI(SwitchEnumFacing.llllIIlllllIlll[0], SwitchEnumFacing.llllIIlllllIlll[1]);
            SwitchEnumFacing.llllIIlllllIlll = null;
        }
        
        private static void lIllIIllllIIlIII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.llllIIlllllIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllIIllllIIIllI(final String s, final String s2) {
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
