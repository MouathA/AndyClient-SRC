package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class ItemMonsterPlacer extends Item
{
    private static final String __OBFID;
    
    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        String s = new StringBuilder().append(StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name")).toString().trim();
        final String stringFromID = EntityList.getStringFromID(itemStack.getMetadata());
        if (stringFromID != null) {
            s = String.valueOf(s) + " " + StatCollector.translateToLocal("entity." + stringFromID + ".name");
        }
        return s;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(itemStack.getMetadata());
        return (entityEggInfo != null) ? ((n == 0) ? entityEggInfo.primaryColor : entityEggInfo.secondaryColor) : 16777215;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (!entityPlayer.func_175151_a(offset.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(EntityList.getStringFromID(itemStack.getMetadata()));
                tileEntity.markDirty();
                world.markBlockForUpdate(offset);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        offset = offset.offset(enumFacing);
        double n4 = 0.0;
        if (enumFacing == EnumFacing.UP && blockState instanceof BlockFence) {
            n4 = 0.5;
        }
        final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), offset.getX() + 0.5, offset.getY() + n4, offset.getZ() + 0.5);
        if (spawnCreature != null) {
            if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                spawnCreature.setCustomNameTag(itemStack.getDisplayName());
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (world.isRemote) {
            return itemStack;
        }
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos func_178782_a = movingObjectPositionFromPlayer.func_178782_a();
            if (!world.isBlockModifiable(entityPlayer, func_178782_a)) {
                return itemStack;
            }
            if (!entityPlayer.func_175151_a(func_178782_a, movingObjectPositionFromPlayer.field_178784_b, itemStack)) {
                return itemStack;
            }
            if (world.getBlockState(func_178782_a).getBlock() instanceof BlockLiquid) {
                final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), func_178782_a.getX() + 0.5, func_178782_a.getY() + 0.5, func_178782_a.getZ() + 0.5);
                if (spawnCreature != null) {
                    if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                        ((EntityLiving)spawnCreature).setCustomNameTag(itemStack.getDisplayName());
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                }
            }
        }
        return itemStack;
    }
    
    public static Entity spawnCreature(final World world, final int n, final double n2, final double n3, final double n4) {
        if (!EntityList.entityEggs.containsKey(n)) {
            return null;
        }
        Entity entityByID = null;
        while (0 < 1) {
            entityByID = EntityList.createEntityByID(n, world);
            if (entityByID instanceof EntityLivingBase) {
                final EntityLiving entityLiving = (EntityLiving)entityByID;
                entityByID.setLocationAndAngles(n2, n3, n4, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
                entityLiving.rotationYawHead = entityLiving.rotationYaw;
                entityLiving.renderYawOffset = entityLiving.rotationYaw;
                entityLiving.func_180482_a(world.getDifficultyForLocation(new BlockPos(entityLiving)), null);
                world.spawnEntityInWorld(entityByID);
                entityLiving.playLivingSound();
            }
            int n5 = 0;
            ++n5;
        }
        return entityByID;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final Iterator<EntityList.EntityEggInfo> iterator = EntityList.entityEggs.values().iterator();
        while (iterator.hasNext()) {
            list.add(new ItemStack(item, 1, iterator.next().spawnedID));
        }
    }
    
    static {
        __OBFID = "CL_00000070";
    }
}
