package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockBed extends BlockDirectional
{
    public static final PropertyEnum PART_PROP;
    public static final PropertyBool OCCUPIED_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000198";
        PART_PROP = PropertyEnum.create("part", EnumPartType.class);
        OCCUPIED_PROP = PropertyBool.create("occupied");
    }
    
    public BlockBed() {
        super(Material.cloth);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBed.PART_PROP, EnumPartType.FOOT).withProperty(BlockBed.OCCUPIED_PROP, false));
        this.setBedBounds();
    }
    
    @Override
    public boolean onBlockActivated(final World world, BlockPos offset, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (blockState.getValue(BlockBed.PART_PROP) != EnumPartType.HEAD) {
            offset = offset.offset((EnumFacing)blockState.getValue(BlockBed.AGE));
            blockState = world.getBlockState(offset);
            if (blockState.getBlock() != this) {
                return true;
            }
        }
        if (!world.provider.canRespawnHere() || world.getBiomeGenForCoords(offset) == BiomeGenBase.hell) {
            world.setBlockToAir(offset);
            final BlockPos offset2 = offset.offset(((EnumFacing)blockState.getValue(BlockBed.AGE)).getOpposite());
            if (world.getBlockState(offset2).getBlock() == this) {
                world.setBlockToAir(offset2);
            }
            world.newExplosion(null, offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, 5.0f, true, true);
            return true;
        }
        if (blockState.getValue(BlockBed.OCCUPIED_PROP)) {
            if (this.func_176470_e(world, offset) != null) {
                entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }
            blockState = blockState.withProperty(BlockBed.OCCUPIED_PROP, false);
            world.setBlockState(offset, blockState, 4);
        }
        final EntityPlayer.EnumStatus func_180469_a = entityPlayer.func_180469_a(offset);
        if (func_180469_a == EntityPlayer.EnumStatus.OK) {
            blockState = blockState.withProperty(BlockBed.OCCUPIED_PROP, true);
            world.setBlockState(offset, blockState, 4);
            return true;
        }
        if (func_180469_a == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        }
        else if (func_180469_a == EntityPlayer.EnumStatus.NOT_SAFE) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        }
        return true;
    }
    
    private EntityPlayer func_176470_e(final World world, final BlockPos blockPos) {
        for (final EntityPlayer entityPlayer : world.playerEntities) {
            if (entityPlayer.isPlayerSleeping() && entityPlayer.playerLocation.equals(blockPos)) {
                return entityPlayer;
            }
        }
        return null;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBedBounds();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockBed.AGE);
        if (blockState.getValue(BlockBed.PART_PROP) == EnumPartType.HEAD) {
            if (world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock() != this) {
                world.setBlockToAir(blockPos);
            }
        }
        else if (world.getBlockState(blockPos.offset(enumFacing)).getBlock() != this) {
            world.setBlockToAir(blockPos);
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockBed.PART_PROP) == EnumPartType.HEAD) ? null : Items.bed;
    }
    
    private void setBedBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static BlockPos getSafeExitLocation(final World world, final BlockPos blockPos, int n) {
        final EnumFacing enumFacing = (EnumFacing)world.getBlockState(blockPos).getValue(BlockBed.AGE);
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        while (0 <= 1) {
            final int n2 = x - enumFacing.getFrontOffsetX() * 0 - 1;
            final int n3 = z - enumFacing.getFrontOffsetZ() * 0 - 1;
            final int n4 = n2 + 2;
            final int n5 = n3 + 2;
            for (int i = n2; i <= n4; ++i) {
                for (int j = n3; j <= n5; ++j) {
                    final BlockPos blockPos2 = new BlockPos(i, y, j);
                    if (func_176469_d(world, blockPos2)) {
                        if (n <= 0) {
                            return blockPos2;
                        }
                        --n;
                    }
                }
            }
            int n6 = 0;
            ++n6;
        }
        return null;
    }
    
    protected static boolean func_176469_d(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) && !world.getBlockState(blockPos).getBlock().getMaterial().isSolid() && !world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial().isSolid();
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (blockState.getValue(BlockBed.PART_PROP) == EnumPartType.FOOT) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, 0);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.bed;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockBed.PART_PROP) == EnumPartType.HEAD) {
            final BlockPos offset = blockPos.offset(((EnumFacing)blockState.getValue(BlockBed.AGE)).getOpposite());
            if (world.getBlockState(offset).getBlock() == this) {
                world.setBlockToAir(offset);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final EnumFacing horizontal = EnumFacing.getHorizontal(n);
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockBed.PART_PROP, EnumPartType.HEAD).withProperty(BlockBed.AGE, horizontal).withProperty(BlockBed.OCCUPIED_PROP, (n & 0x4) > 0) : this.getDefaultState().withProperty(BlockBed.PART_PROP, EnumPartType.FOOT).withProperty(BlockBed.AGE, horizontal);
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockBed.PART_PROP) == EnumPartType.FOOT) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.offset((EnumFacing)withProperty.getValue(BlockBed.AGE)));
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty(BlockBed.OCCUPIED_PROP, blockState.getValue(BlockBed.OCCUPIED_PROP));
            }
        }
        return withProperty;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockBed.AGE)).getHorizontalIndex();
        if (blockState.getValue(BlockBed.PART_PROP) == EnumPartType.HEAD) {
            n |= 0x8;
            if (blockState.getValue(BlockBed.OCCUPIED_PROP)) {
                n |= 0x4;
            }
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockBed.AGE, BlockBed.PART_PROP, BlockBed.OCCUPIED_PROP });
    }
    
    public enum EnumPartType implements IStringSerializable
    {
        HEAD("HEAD", 0, "HEAD", 0, "head"), 
        FOOT("FOOT", 1, "FOOT", 1, "foot");
        
        private final String field_177036_c;
        private static final EnumPartType[] $VALUES;
        private static final String __OBFID;
        private static final EnumPartType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002134";
            ENUM$VALUES = new EnumPartType[] { EnumPartType.HEAD, EnumPartType.FOOT };
            $VALUES = new EnumPartType[] { EnumPartType.HEAD, EnumPartType.FOOT };
        }
        
        private EnumPartType(final String s, final int n, final String s2, final int n2, final String field_177036_c) {
            this.field_177036_c = field_177036_c;
        }
        
        @Override
        public String toString() {
            return this.field_177036_c;
        }
        
        @Override
        public String getName() {
            return this.field_177036_c;
        }
    }
}
