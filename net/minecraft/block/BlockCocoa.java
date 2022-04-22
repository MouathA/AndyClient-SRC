package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockCocoa extends BlockDirectional implements IGrowable
{
    public static final PropertyInteger field_176501_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000216";
        field_176501_a = PropertyInteger.create("age", 0, 2);
    }
    
    public BlockCocoa() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCocoa.AGE, EnumFacing.NORTH).withProperty(BlockCocoa.field_176501_a, 0));
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
        }
        else if (world.rand.nextInt(5) == 0) {
            final int intValue = (int)blockState.getValue(BlockCocoa.field_176501_a);
            if (intValue < 2) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.field_176501_a, intValue + 1), 2);
            }
        }
    }
    
    public boolean canBlockStay(final World world, BlockPos offset, final IBlockState blockState) {
        offset = offset.offset((EnumFacing)blockState.getValue(BlockCocoa.AGE));
        final IBlockState blockState2 = world.getBlockState(offset);
        return blockState2.getBlock() == Blocks.log && blockState2.getValue(BlockPlanks.VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockCocoa.AGE);
        final int intValue = (int)blockState.getValue(BlockCocoa.field_176501_a);
        final int n = 4 + intValue * 2;
        final int n2 = 5 + intValue * 2;
        final float n3 = n / 2.0f;
        switch (SwitchEnumFacing.FACINGARRAY[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, (15.0f - n) / 16.0f, (8.0f + n3) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 2: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, 0.0625f, (8.0f + n3) / 16.0f, 0.75f, (1.0f + n) / 16.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0625f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, (1.0f + n) / 16.0f, 0.75f, (8.0f + n3) / 16.0f);
                break;
            }
            case 4: {
                this.setBlockBounds((15.0f - n) / 16.0f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, 0.9375f, 0.75f, (8.0f + n3) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.AGE, EnumFacing.fromAngle(entityLivingBase.rotationYaw)), 2);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, EnumFacing north, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (!north.getAxis().isHorizontal()) {
            north = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockCocoa.AGE, north.getOpposite()).withProperty(BlockCocoa.field_176501_a, 0);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
        }
    }
    
    private void dropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        this.dropBlockAsItem(world, blockPos, blockState, 0);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if ((int)blockState.getValue(BlockCocoa.field_176501_a) >= 2) {}
        while (0 < 3) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeColorDamage()));
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.dye;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return EnumDyeColor.BROWN.getDyeColorDamage();
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockCocoa.field_176501_a) < 2;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty(BlockCocoa.field_176501_a, (int)blockState.getValue(BlockCocoa.field_176501_a) + 1), 2);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCocoa.AGE, EnumFacing.getHorizontal(n)).withProperty(BlockCocoa.field_176501_a, (n & 0xF) >> 2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0x0 | ((EnumFacing)blockState.getValue(BlockCocoa.AGE)).getHorizontalIndex() | (int)blockState.getValue(BlockCocoa.field_176501_a) << 2;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCocoa.AGE, BlockCocoa.field_176501_a });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] FACINGARRAY;
        private static final String __OBFID;
        private static final String[] lllIlIllllIlIll;
        private static String[] lllIlIllllIllII;
        
        static {
            lIlIllIlllIllIlI();
            lIlIllIlllIllIIl();
            __OBFID = SwitchEnumFacing.lllIlIllllIlIll[0];
            FACINGARRAY = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.FACINGARRAY[EnumFacing.SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.FACINGARRAY[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.FACINGARRAY[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.FACINGARRAY[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIlIllIlllIllIIl() {
            (lllIlIllllIlIll = new String[1])[0] = lIlIllIlllIllIII(SwitchEnumFacing.lllIlIllllIllII[0], SwitchEnumFacing.lllIlIllllIllII[1]);
            SwitchEnumFacing.lllIlIllllIllII = null;
        }
        
        private static void lIlIllIlllIllIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lllIlIllllIllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIlIllIlllIllIII(final String s, final String s2) {
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
