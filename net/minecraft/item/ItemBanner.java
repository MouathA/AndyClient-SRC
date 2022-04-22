package net.minecraft.item;

import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class ItemBanner extends ItemBlock
{
    private static final String __OBFID;
    
    public ItemBanner() {
        super(Blocks.standing_banner);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemValues, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
            return false;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemValues)) {
            return false;
        }
        if (!Blocks.standing_banner.canPlaceBlockAt(world, offset)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        if (enumFacing == EnumFacing.UP) {
            world.setBlockState(offset, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, MathHelper.floor_double((entityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF), 3);
        }
        else {
            world.setBlockState(offset, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.field_176412_a, enumFacing), 3);
        }
        --itemValues.stackSize;
        final TileEntity tileEntity = world.getTileEntity(offset);
        if (tileEntity instanceof TileEntityBanner) {
            ((TileEntityBanner)tileEntity).setItemValues(itemValues);
        }
        return true;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        return StatCollector.translateToLocal(String.valueOf("item.banner.") + this.func_179225_h(itemStack).func_176762_d() + ".name");
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        if (subCompound != null && subCompound.hasKey("Patterns")) {
            final NBTTagList tagList = subCompound.getTagList("Patterns", 10);
            while (0 < tagList.tagCount() && 0 < 6) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                final EnumDyeColor func_176766_a = EnumDyeColor.func_176766_a(compoundTag.getInteger("Color"));
                final TileEntityBanner.EnumBannerPattern func_177268_a = TileEntityBanner.EnumBannerPattern.func_177268_a(compoundTag.getString("Pattern"));
                if (func_177268_a != null) {
                    list.add(StatCollector.translateToLocal("item.banner." + func_177268_a.func_177271_a() + "." + func_176766_a.func_176762_d()));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n == 0) {
            return 16777215;
        }
        return this.func_179225_h(itemStack).func_176768_e().colorValue;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getDyeColorDamage()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.tabDecorations;
    }
    
    private EnumDyeColor func_179225_h(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        EnumDyeColor enumDyeColor;
        if (subCompound != null && subCompound.hasKey("Base")) {
            enumDyeColor = EnumDyeColor.func_176766_a(subCompound.getInteger("Base"));
        }
        else {
            enumDyeColor = EnumDyeColor.func_176766_a(itemStack.getMetadata());
        }
        return enumDyeColor;
    }
    
    static {
        __OBFID = "CL_00002181";
    }
}
