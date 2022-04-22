package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockCauldron extends Block
{
    public static final PropertyInteger field_176591_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000213";
        field_176591_a = PropertyInteger.create("level", 0, 3);
    }
    
    public BlockCauldron() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCauldron.field_176591_a, 0));
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final float n = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
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
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        final int intValue = (int)blockState.getValue(BlockCauldron.field_176591_a);
        final float n = blockPos.getY() + (6.0f + 3 * intValue) / 16.0f;
        if (!world.isRemote && entity.isBurning() && intValue > 0 && entity.getEntityBoundingBox().minY <= n) {
            entity.extinguish();
            this.func_176590_a(world, blockPos, blockState, intValue - 1);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem == null) {
            return true;
        }
        final int intValue = (int)blockState.getValue(BlockCauldron.field_176591_a);
        final Item item = currentItem.getItem();
        if (item == Items.water_bucket) {
            if (intValue < 3) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
                }
                this.func_176590_a(world, blockPos, blockState, 3);
            }
            return true;
        }
        if (item == Items.glass_bottle) {
            if (intValue > 0) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = new ItemStack(Items.potionitem, 1, 0);
                    if (!entityPlayer.inventory.addItemStackToInventory(itemStack)) {
                        world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, itemStack));
                    }
                    else if (entityPlayer instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
                    }
                    final ItemStack itemStack2 = currentItem;
                    --itemStack2.stackSize;
                    if (currentItem.stackSize <= 0) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                    }
                }
                this.func_176590_a(world, blockPos, blockState, intValue - 1);
            }
            return true;
        }
        if (intValue > 0 && item instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)item;
            if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemArmor.hasColor(currentItem)) {
                itemArmor.removeColor(currentItem);
                this.func_176590_a(world, blockPos, blockState, intValue - 1);
                return true;
            }
        }
        if (intValue > 0 && item instanceof ItemBanner && TileEntityBanner.func_175113_c(currentItem) > 0) {
            final ItemStack copy = currentItem.copy();
            copy.stackSize = 1;
            TileEntityBanner.func_175117_e(copy);
            if (currentItem.stackSize <= 1 && !entityPlayer.capabilities.isCreativeMode) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, copy);
            }
            else {
                if (!entityPlayer.inventory.addItemStackToInventory(copy)) {
                    world.spawnEntityInWorld(new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5, copy));
                }
                else if (entityPlayer instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
                }
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack3 = currentItem;
                    --itemStack3.stackSize;
                }
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                this.func_176590_a(world, blockPos, blockState, intValue - 1);
            }
            return true;
        }
        return false;
    }
    
    public void func_176590_a(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        world.setBlockState(blockPos, blockState.withProperty(BlockCauldron.field_176591_a, MathHelper.clamp_int(n, 0, 3)), 2);
        world.updateComparatorOutputLevel(blockPos, this);
    }
    
    @Override
    public void fillWithRain(final World world, final BlockPos blockPos) {
        if (world.rand.nextInt(20) == 1) {
            final IBlockState blockState = world.getBlockState(blockPos);
            if ((int)blockState.getValue(BlockCauldron.field_176591_a) < 3) {
                world.setBlockState(blockPos, blockState.cycleProperty(BlockCauldron.field_176591_a), 2);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.cauldron;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.cauldron;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return (int)world.getBlockState(blockPos).getValue(BlockCauldron.field_176591_a);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCauldron.field_176591_a, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockCauldron.field_176591_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCauldron.field_176591_a });
    }
}
