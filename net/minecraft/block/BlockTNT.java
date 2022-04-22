package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.state.*;

public class BlockTNT extends Block
{
    public static final PropertyBool field_176246_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000324";
        field_176246_a = PropertyBool.create("explode");
    }
    
    public BlockTNT() {
        super(Material.tnt);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTNT.field_176246_a, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        super.onBlockAdded(world, blockToAir, blockState);
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.field_176246_a, true));
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.field_176246_a, true));
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        if (!world.isRemote) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, explosion.getExplosivePlacedBy());
            entityTNTPrimed.fuse = world.rand.nextInt(entityTNTPrimed.fuse / 4) + entityTNTPrimed.fuse / 8;
            world.spawnEntityInWorld(entityTNTPrimed);
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_180692_a(world, blockPos, blockState, null);
    }
    
    public void func_180692_a(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockTNT.field_176246_a)) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, entityLivingBase);
            world.spawnEntityInWorld(entityTNTPrimed);
            world.playSoundAtEntity(entityTNTPrimed, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (entityPlayer.getCurrentEquippedItem() != null) {
            final Item item = entityPlayer.getCurrentEquippedItem().getItem();
            if (item == Items.flint_and_steel || item == Items.fire_charge) {
                this.func_180692_a(world, blockToAir, blockState.withProperty(BlockTNT.field_176246_a, true), entityPlayer);
                world.setBlockToAir(blockToAir);
                if (item == Items.flint_and_steel) {
                    entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
                }
                else if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
                    --currentEquippedItem.stackSize;
                }
                return true;
            }
        }
        return super.onBlockActivated(world, blockToAir, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockToAir, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && entity instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)entity;
            if (entityArrow.isBurning()) {
                this.func_180692_a(world, blockToAir, world.getBlockState(blockToAir).withProperty(BlockTNT.field_176246_a, true), (entityArrow.shootingEntity instanceof EntityLivingBase) ? ((EntityLivingBase)entityArrow.shootingEntity) : null);
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public boolean canDropFromExplosion(final Explosion explosion) {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTNT.field_176246_a, (n & 0x1) > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockTNT.field_176246_a)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTNT.field_176246_a });
    }
}
