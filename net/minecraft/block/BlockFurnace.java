package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockFurnace extends BlockContainer
{
    public static final PropertyDirection FACING;
    private final boolean isBurning;
    private static boolean field_149934_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000248";
        FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockFurnace(final boolean isBurning) {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176445_e(world, blockPos, blockState);
    }
    
    private void func_176445_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            final Block block = world.getBlockState(blockPos.offsetNorth()).getBlock();
            final Block block2 = world.getBlockState(blockPos.offsetSouth()).getBlock();
            final Block block3 = world.getBlockState(blockPos.offsetWest()).getBlock();
            final Block block4 = world.getBlockState(blockPos.offsetEast()).getBlock();
            EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockFurnace.FACING);
            if (enumFacing == EnumFacing.NORTH && block.isFullBlock() && !block2.isFullBlock()) {
                enumFacing = EnumFacing.SOUTH;
            }
            else if (enumFacing == EnumFacing.SOUTH && block2.isFullBlock() && !block.isFullBlock()) {
                enumFacing = EnumFacing.NORTH;
            }
            else if (enumFacing == EnumFacing.WEST && block3.isFullBlock() && !block4.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
            }
            else if (enumFacing == EnumFacing.EAST && block4.isFullBlock() && !block3.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
            world.setBlockState(blockPos, blockState.withProperty(BlockFurnace.FACING, enumFacing), 2);
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isBurning) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockFurnace.FACING);
            final double n = blockPos.getX() + 0.5;
            final double n2 = blockPos.getY() + random.nextDouble() * 6.0 / 16.0;
            final double n3 = blockPos.getZ() + 0.5;
            final double n4 = 0.52;
            final double n5 = random.nextDouble() * 0.6 - 0.3;
            switch (SwitchEnumFacing.field_180356_a[enumFacing.ordinal()]) {
                case 1: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 2: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 3: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 4: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFurnace) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
        }
        return true;
    }
    
    public static void func_176446_a(final boolean b, final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        BlockFurnace.field_149934_M = true;
        if (b) {
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
        }
        else {
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
        }
        BlockFurnace.field_149934_M = false;
        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityFurnace();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockFurnace.FACING, entityLivingBase.func_174811_aO().getOpposite());
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockFurnace.FACING, entityLivingBase.func_174811_aO().getOpposite()), 2);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                ((TileEntityFurnace)tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!BlockFurnace.field_149934_M) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockFurnace.FACING, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockFurnace.FACING)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFurnace.FACING });
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_180356_a;
        private static final String __OBFID;
        private static final String[] lIIlIllIIIIlllIl;
        private static String[] lIIlIllIIIIllllI;
        
        static {
            llIlllIlIIIlllII();
            llIlllIlIIIllIll();
            __OBFID = SwitchEnumFacing.lIIlIllIIIIlllIl[0];
            field_180356_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_180356_a[EnumFacing.WEST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_180356_a[EnumFacing.EAST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_180356_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_180356_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void llIlllIlIIIllIll() {
            (lIIlIllIIIIlllIl = new String[1])[0] = llIlllIlIIIllIlI(SwitchEnumFacing.lIIlIllIIIIllllI[0], SwitchEnumFacing.lIIlIllIIIIllllI[1]);
            SwitchEnumFacing.lIIlIllIIIIllllI = null;
        }
        
        private static void llIlllIlIIIlllII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIlIllIIIIllllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIlllIlIIIllIlI(final String s, final String s2) {
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
