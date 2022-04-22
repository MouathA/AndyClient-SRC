package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.server.*;

public class ItemBlock extends Item
{
    protected final Block block;
    private static final String __OBFID;
    
    public ItemBlock(final Block block) {
        this.block = block;
    }
    
    @Override
    public ItemBlock setUnlocalizedName(final String unlocalizedName) {
        super.setUnlocalizedName(unlocalizedName);
        return this;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, EnumFacing up, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(offset);
        final Block block = blockState.getBlock();
        if (block == Blocks.snow_layer && (int)blockState.getValue(BlockSnow.LAYERS_PROP) < 1) {
            up = EnumFacing.UP;
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (!entityPlayer.func_175151_a(offset, up, itemStack)) {
            return false;
        }
        if (offset.getY() == 255 && this.block.getMaterial().isSolid()) {
            return false;
        }
        if (world.canBlockBePlaced(this.block, offset, false, up, null, itemStack)) {
            if (world.setBlockState(offset, this.block.onBlockPlaced(world, offset, up, n, n2, n3, this.getMetadata(itemStack.getMetadata()), entityPlayer), 3)) {
                final IBlockState blockState2 = world.getBlockState(offset);
                if (blockState2.getBlock() == this.block) {
                    setTileEntityNBT(world, offset, itemStack);
                    this.block.onBlockPlacedBy(world, offset, blockState2, entityPlayer, itemStack);
                }
                world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    public static boolean setTileEntityNBT(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
                tileEntity.writeToNBT(nbtTagCompound);
                nbtTagCompound.merge((NBTTagCompound)itemStack.getTagCompound().getTag("BlockEntityTag"));
                nbtTagCompound.setInteger("x", blockPos.getX());
                nbtTagCompound.setInteger("y", blockPos.getY());
                nbtTagCompound.setInteger("z", blockPos.getZ());
                if (!nbtTagCompound.equals(nbtTagCompound2)) {
                    tileEntity.readFromNBT(nbtTagCompound);
                    tileEntity.markDirty();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean canPlaceBlockOnSide(final World world, BlockPos offset, EnumFacing up, final EntityPlayer entityPlayer, final ItemStack itemStack) {
        final Block block = world.getBlockState(offset).getBlock();
        if (block == Blocks.snow_layer) {
            up = EnumFacing.UP;
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        return world.canBlockBePlaced(this.block, offset, false, up, null, itemStack);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.block.getUnlocalizedName();
    }
    
    @Override
    public String getUnlocalizedName() {
        return this.block.getUnlocalizedName();
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return this.block.getCreativeTabToDisplayOn();
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        this.block.getSubBlocks(item, creativeTabs, list);
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public static boolean setTileEntityNBT(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final ItemStack itemStack) {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            return false;
        }
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity != null) {
                if (!world.isRemote && tileEntity.func_183000_F() && !server.getConfigurationManager().canSendCommands(entityPlayer.getGameProfile())) {
                    return false;
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
                tileEntity.writeToNBT(nbtTagCompound);
                nbtTagCompound.merge((NBTTagCompound)itemStack.getTagCompound().getTag("BlockEntityTag"));
                nbtTagCompound.setInteger("x", blockPos.getX());
                nbtTagCompound.setInteger("y", blockPos.getY());
                nbtTagCompound.setInteger("z", blockPos.getZ());
                if (!nbtTagCompound.equals(nbtTagCompound2)) {
                    tileEntity.readFromNBT(nbtTagCompound);
                    tileEntity.markDirty();
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Item setUnlocalizedName(final String unlocalizedName) {
        return this.setUnlocalizedName(unlocalizedName);
    }
    
    static {
        __OBFID = "CL_00001772";
    }
}
