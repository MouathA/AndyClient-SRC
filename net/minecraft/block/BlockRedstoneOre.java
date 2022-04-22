package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockRedstoneOre extends Block
{
    private final boolean isOn;
    private static final String __OBFID;
    
    public BlockRedstoneOre(final boolean isOn) {
        super(Material.rock);
        if (isOn) {
            this.setTickRandomly(true);
        }
        this.isOn = isOn;
    }
    
    @Override
    public int tickRate(final World world) {
        return 30;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.setOn(world, blockPos);
        super.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.setOn(world, blockPos);
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.setOn(world, blockPos);
        return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
    
    private void setOn(final World world, final BlockPos blockPos) {
        this.spawnRedstoneParticles(world, blockPos);
        if (this == Blocks.redstone_ore) {
            world.setBlockState(blockPos, Blocks.lit_redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this == Blocks.lit_redstone_ore) {
            world.setBlockState(blockPos, Blocks.redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return this.quantityDropped(random) + random.nextInt(n + 1);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 4 + random.nextInt(2);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (this.getItemDropped(blockState, world.rand, n2) != Item.getItemFromBlock(this)) {
            this.dropXpOnBlockBreak(world, blockPos, 1 + world.rand.nextInt(5));
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isOn) {
            this.spawnRedstoneParticles(world, blockPos);
        }
    }
    
    private void spawnRedstoneParticles(final World world, final BlockPos blockPos) {
        final Random rand = world.rand;
        final double n = 0.0625;
        while (0 < 6) {
            double n2 = blockPos.getX() + rand.nextFloat();
            double n3 = blockPos.getY() + rand.nextFloat();
            double n4 = blockPos.getZ() + rand.nextFloat();
            if (!false && !world.getBlockState(blockPos.offsetUp()).getBlock().isOpaqueCube()) {
                n3 = blockPos.getY() + n + 1.0;
            }
            if (false == true && !world.getBlockState(blockPos.offsetDown()).getBlock().isOpaqueCube()) {
                n3 = blockPos.getY() - n;
            }
            if (0 == 2 && !world.getBlockState(blockPos.offsetSouth()).getBlock().isOpaqueCube()) {
                n4 = blockPos.getZ() + n + 1.0;
            }
            if (0 == 3 && !world.getBlockState(blockPos.offsetNorth()).getBlock().isOpaqueCube()) {
                n4 = blockPos.getZ() - n;
            }
            if (0 == 4 && !world.getBlockState(blockPos.offsetEast()).getBlock().isOpaqueCube()) {
                n2 = blockPos.getX() + n + 1.0;
            }
            if (0 == 5 && !world.getBlockState(blockPos.offsetWest()).getBlock().isOpaqueCube()) {
                n2 = blockPos.getX() - n;
            }
            if (n2 < blockPos.getX() || n2 > blockPos.getX() + 1 || n3 < 0.0 || n3 > blockPos.getY() + 1 || n4 < blockPos.getZ() || n4 > blockPos.getZ() + 1) {
                world.spawnParticle(EnumParticleTypes.REDSTONE, n2, n3, n4, 0.0, 0.0, 0.0, new int[0]);
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Blocks.redstone_ore);
    }
    
    static {
        __OBFID = "CL_00000294";
    }
}
