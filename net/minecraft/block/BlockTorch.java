package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockTorch extends Block
{
    public static final PropertyDirection FACING_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000325";
        FACING_PROP = PropertyDirection.create("facing", new Predicate() {
            private static final String __OBFID;
            
            public boolean func_176601_a(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.DOWN;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_176601_a((EnumFacing)o);
            }
            
            static {
                __OBFID = "CL_00002054";
            }
        });
    }
    
    protected BlockTorch() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTorch.FACING_PROP, EnumFacing.UP));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
    
    private boolean func_176594_d(final World world, final BlockPos blockPos) {
        if (World.doesBlockHaveSolidTopSurface(world, blockPos)) {
            return true;
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        return block instanceof BlockFence || block == Blocks.glass || block == Blocks.cobblestone_wall || block == Blocks.stained_glass;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Iterator<EnumFacing> iterator = BlockTorch.FACING_PROP.getAllowedValues().iterator();
        while (iterator.hasNext()) {
            if (this.func_176595_b(world, blockPos, iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean func_176595_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        return (enumFacing.getAxis().isHorizontal() && world.func_175677_d(offset, true)) || (enumFacing.equals(EnumFacing.UP) && this.func_176594_d(world, offset));
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (this.func_176595_b(world, blockPos, enumFacing)) {
            return this.getDefaultState().withProperty(BlockTorch.FACING_PROP, enumFacing);
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (world.func_175677_d(blockPos.offset(enumFacing2.getOpposite()), true)) {
                return this.getDefaultState().withProperty(BlockTorch.FACING_PROP, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176593_f(world, blockPos, blockState);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.func_176592_e(world, blockPos, blockState);
    }
    
    protected boolean func_176592_e(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.func_176593_f(world, blockToAir, blockState)) {
            return true;
        }
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTorch.FACING_PROP);
        final EnumFacing.Axis axis = enumFacing.getAxis();
        final EnumFacing opposite = enumFacing.getOpposite();
        if (!axis.isHorizontal() || world.func_175677_d(blockToAir.offset(opposite), true)) {
            if (!axis.isVertical() || !this.func_176594_d(world, blockToAir.offset(opposite))) {}
        }
        if (true) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            return true;
        }
        return false;
    }
    
    protected boolean func_176593_f(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (blockState.getBlock() == this && this.func_176595_b(world, blockToAir, (EnumFacing)blockState.getValue(BlockTorch.FACING_PROP))) {
            return true;
        }
        if (world.getBlockState(blockToAir).getBlock() == this) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
        return false;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final EnumFacing enumFacing = (EnumFacing)world.getBlockState(blockPos).getValue(BlockTorch.FACING_PROP);
        final float n = 0.15f;
        if (enumFacing == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
        }
        else if (enumFacing == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
        }
        else if (enumFacing == EnumFacing.NORTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
        }
        else {
            final float n2 = 0.1f;
            this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
        }
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTorch.FACING_PROP);
        final double n = blockPos.getX() + 0.5;
        final double n2 = blockPos.getY() + 0.7;
        final double n3 = blockPos.getZ() + 0.5;
        final double n4 = 0.22;
        final double n5 = 0.27;
        if (enumFacing.getAxis().isHorizontal()) {
            final EnumFacing opposite = enumFacing.getOpposite();
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int[0]);
        }
        else {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        IBlockState blockState = null;
        switch (n) {
            case 1: {
                blockState = defaultState.withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST);
                break;
            }
            case 2: {
                blockState = defaultState.withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST);
                break;
            }
            case 3: {
                blockState = defaultState.withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH);
                break;
            }
            case 4: {
                blockState = defaultState.withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH);
                break;
            }
            default: {
                blockState = defaultState.withProperty(BlockTorch.FACING_PROP, EnumFacing.UP);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        switch (SwitchEnumFacing.field_176609_a[((EnumFacing)blockState.getValue(BlockTorch.FACING_PROP)).ordinal()]) {
            case 1: {}
            case 2: {}
            case 3: {}
        }
        return 5;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTorch.FACING_PROP });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_176609_a;
        private static final String __OBFID;
        private static final String[] lIIlIlllIIIlllll;
        private static String[] lIIlIlllIIlIIIII;
        
        static {
            llIllllIlIIlIlIl();
            llIllllIlIIlIlII();
            __OBFID = SwitchEnumFacing.lIIlIlllIIIlllll[0];
            field_176609_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.DOWN.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_176609_a[EnumFacing.UP.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void llIllllIlIIlIlII() {
            (lIIlIlllIIIlllll = new String[1])[0] = llIllllIlIIlIIll(SwitchEnumFacing.lIIlIlllIIlIIIII[0], SwitchEnumFacing.lIIlIlllIIlIIIII[1]);
            SwitchEnumFacing.lIIlIlllIIlIIIII = null;
        }
        
        private static void llIllllIlIIlIlIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIlIlllIIlIIIII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIllllIlIIlIIll(final String s, final String s2) {
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
