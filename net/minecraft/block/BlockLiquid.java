package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public abstract class BlockLiquid extends Block
{
    public static final PropertyInteger LEVEL;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000265";
        LEVEL = PropertyInteger.create("level", 0, 15);
    }
    
    protected BlockLiquid(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setTickRandomly(true);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.blockMaterial != Material.lava;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return (this.blockMaterial == Material.water) ? BiomeColorHelper.func_180288_c(blockAccess, blockPos) : 16777215;
    }
    
    public static float getLiquidHeightPercent(final int n) {
        if (0 >= 8) {}
        return 1 / 9.0f;
    }
    
    protected int func_176362_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (int)((blockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial) ? blockAccess.getBlockState(blockPos).getValue(BlockLiquid.LEVEL) : -1);
    }
    
    protected int func_176366_f(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int func_176362_e = this.func_176362_e(blockAccess, blockPos);
        return (func_176362_e >= 8) ? 0 : func_176362_e;
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
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return b && (int)blockState.getValue(BlockLiquid.LEVEL) == 0;
    }
    
    @Override
    public boolean isBlockSolid(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final Material material = blockAccess.getBlockState(blockPos).getBlock().getMaterial();
        return material != this.blockMaterial && (enumFacing == EnumFacing.UP || (material != Material.ice && super.isBlockSolid(blockAccess, blockPos, enumFacing)));
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return blockAccess.getBlockState(blockPos).getBlock().getMaterial() != this.blockMaterial && (enumFacing == EnumFacing.UP || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing));
    }
    
    public boolean func_176364_g(final IBlockAccess blockAccess, final BlockPos blockPos) {
        while (-1 <= 1) {
            while (-1 <= 1) {
                final Block block = blockAccess.getBlockState(blockPos.add(-1, 0, -1)).getBlock();
                if (block.getMaterial() != this.blockMaterial && !block.isFullBlock()) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return 1;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    protected Vec3 func_180687_h(final IBlockAccess blockAccess, final BlockPos blockPos) {
        Vec3 vec3 = new Vec3(0.0, 0.0, 0.0);
        final int func_176366_f = this.func_176366_f(blockAccess, blockPos);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            final int func_176366_f2 = this.func_176366_f(blockAccess, offset);
            if (func_176366_f2 < 0) {
                if (blockAccess.getBlockState(offset).getBlock().getMaterial().blocksMovement()) {
                    continue;
                }
                final int func_176366_f3 = this.func_176366_f(blockAccess, offset.offsetDown());
                if (func_176366_f3 < 0) {
                    continue;
                }
                final int n = func_176366_f3 - (func_176366_f - 8);
                vec3 = vec3.addVector((offset.getX() - blockPos.getX()) * n, (offset.getY() - blockPos.getY()) * n, (offset.getZ() - blockPos.getZ()) * n);
            }
            else {
                if (func_176366_f2 < 0) {
                    continue;
                }
                final int n2 = func_176366_f2 - func_176366_f;
                vec3 = vec3.addVector((offset.getX() - blockPos.getX()) * n2, (offset.getY() - blockPos.getY()) * n2, (offset.getZ() - blockPos.getZ()) * n2);
            }
        }
        if ((int)blockAccess.getBlockState(blockPos).getValue(BlockLiquid.LEVEL) >= 8) {
            for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                final BlockPos offset2 = blockPos.offset(enumFacing);
                if (this.isBlockSolid(blockAccess, offset2, enumFacing) || this.isBlockSolid(blockAccess, offset2.offsetUp(), enumFacing)) {
                    vec3 = vec3.normalize().addVector(0.0, -6.0, 0.0);
                    break;
                }
            }
        }
        return vec3.normalize();
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return vec3.add(this.func_180687_h(world, blockPos));
    }
    
    @Override
    public int tickRate(final World world) {
        return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (world.provider.getHasNoSky() ? 10 : 30) : 0);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int combinedLight = blockAccess.getCombinedLight(blockPos, 0);
        final int combinedLight2 = blockAccess.getCombinedLight(blockPos.offsetUp(), 0);
        final int n = combinedLight & 0xFF;
        final int n2 = combinedLight2 & 0xFF;
        final int n3 = combinedLight >> 16 & 0xFF;
        final int n4 = combinedLight2 >> 16 & 0xFF;
        return ((n > n2) ? n : n2) | ((n3 > n4) ? n3 : n4) << 16;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return (this.blockMaterial == Material.water) ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        if (this.blockMaterial == Material.water) {
            final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
            if (intValue > 0 && intValue < 8) {
                if (random.nextInt(64) == 0) {
                    world.playSound(n + 0.5, n2 + 0.5, n3 + 0.5, "liquid.water", random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f, false);
                }
            }
            else if (random.nextInt(10) == 0) {
                world.spawnParticle(EnumParticleTypes.SUSPENDED, n + random.nextFloat(), n2 + random.nextFloat(), n3 + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        if (this.blockMaterial == Material.lava && world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial() == Material.air && !world.getBlockState(blockPos.offsetUp()).getBlock().isOpaqueCube()) {
            if (random.nextInt(100) == 0) {
                final double n4 = n + random.nextFloat();
                final double n5 = n2 + this.maxY;
                final double n6 = n3 + random.nextFloat();
                world.spawnParticle(EnumParticleTypes.LAVA, n4, n5, n6, 0.0, 0.0, 0.0, new int[0]);
                world.playSound(n4, n5, n6, "liquid.lavapop", 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
            if (random.nextInt(200) == 0) {
                world.playSound(n, n2, n3, "liquid.lava", 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
        }
        if (random.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) {
            final Material material = world.getBlockState(blockPos.offsetDown(2)).getBlock().getMaterial();
            if (!material.blocksMovement() && !material.isLiquid()) {
                final double n7 = n + random.nextFloat();
                final double n8 = n2 - 1.05;
                final double n9 = n3 + random.nextFloat();
                if (this.blockMaterial == Material.water) {
                    world.spawnParticle(EnumParticleTypes.DRIP_WATER, n7, n8, n9, 0.0, 0.0, 0.0, new int[0]);
                }
                else {
                    world.spawnParticle(EnumParticleTypes.DRIP_LAVA, n7, n8, n9, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
    }
    
    public static double func_180689_a(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        final Vec3 func_180687_h = getDynamicLiquidForMaterial(material).func_180687_h(blockAccess, blockPos);
        return (func_180687_h.xCoord == 0.0 && func_180687_h.zCoord == 0.0) ? -1000.0 : (Math.atan2(func_180687_h.zCoord, func_180687_h.xCoord) - 1.5707963267948966);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176365_e(world, blockPos, blockState);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.func_176365_e(world, blockPos, blockState);
    }
    
    public boolean func_176365_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.blockMaterial == Material.lava) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.offset(enumFacing)).getBlock().getMaterial() == Material.water) {
                    break;
                }
                int n = 0;
                ++n;
            }
            if (true) {
                final Integer n2 = (Integer)blockState.getValue(BlockLiquid.LEVEL);
                if (n2 == 0) {
                    world.setBlockState(blockPos, Blocks.obsidian.getDefaultState());
                    this.func_180688_d(world, blockPos);
                    return true;
                }
                if (n2 <= 4) {
                    world.setBlockState(blockPos, Blocks.cobblestone.getDefaultState());
                    this.func_180688_d(world, blockPos);
                    return true;
                }
            }
        }
        return false;
    }
    
    protected void func_180688_d(final World world, final BlockPos blockPos) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        world.playSoundEffect(n + 0.5, n2 + 0.5, n3 + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
        while (0 < 8) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, n + Math.random(), n2 + 1.2, n3 + Math.random(), 0.0, 0.0, 0.0, new int[0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockLiquid.LEVEL, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockLiquid.LEVEL);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLiquid.LEVEL });
    }
    
    public static BlockDynamicLiquid getDynamicLiquidForMaterial(final Material material) {
        if (material == Material.water) {
            return Blocks.flowing_water;
        }
        if (material == Material.lava) {
            return Blocks.flowing_lava;
        }
        throw new IllegalArgumentException("Invalid material");
    }
    
    public static BlockStaticLiquid getStaticLiquidForMaterial(final Material material) {
        if (material == Material.water) {
            return Blocks.water;
        }
        if (material == Material.lava) {
            return Blocks.lava;
        }
        throw new IllegalArgumentException("Invalid material");
    }
}
