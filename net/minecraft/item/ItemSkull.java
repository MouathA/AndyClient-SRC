package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.mojang.authlib.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ItemSkull extends Item
{
    private static final String[] skullTypes;
    private static final String __OBFID;
    private static final String[] lIlIlIIlIIIIlIIl;
    private static String[] lIlIlIIlIIIIllIl;
    
    static {
        lllllIIlIIIlIIIl();
        lllllIIlIIIlIIII();
        __OBFID = ItemSkull.lIlIlIIlIIIIlIIl[0];
        skullTypes = new String[] { ItemSkull.lIlIlIIlIIIIlIIl[1], ItemSkull.lIlIlIIlIIIIlIIl[2], ItemSkull.lIlIlIIlIIIIlIIl[3], ItemSkull.lIlIlIIlIIIIlIIl[4], ItemSkull.lIlIlIIlIIIIlIIl[5] };
    }
    
    public ItemSkull() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
                return false;
            }
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.func_175151_a(offset, enumFacing, itemStack)) {
            return false;
        }
        if (!Blocks.skull.canPlaceBlockAt(world, offset)) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockState(offset, Blocks.skull.getDefaultState().withProperty(BlockSkull.field_176418_a, enumFacing), 3);
            int skullRotation = 0;
            if (enumFacing == EnumFacing.UP) {
                skullRotation = (MathHelper.floor_double(entityPlayer.rotationYaw * 16.0f / 360.0f + 0.5) & 0xF);
            }
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntitySkull) {
                final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                if (itemStack.getMetadata() == 3) {
                    GameProfile gameProfileFromNBT = null;
                    if (itemStack.hasTagCompound()) {
                        final NBTTagCompound tagCompound = itemStack.getTagCompound();
                        if (tagCompound.hasKey(ItemSkull.lIlIlIIlIIIIlIIl[6], 10)) {
                            gameProfileFromNBT = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag(ItemSkull.lIlIlIIlIIIIlIIl[7]));
                        }
                        else if (tagCompound.hasKey(ItemSkull.lIlIlIIlIIIIlIIl[8], 8) && tagCompound.getString(ItemSkull.lIlIlIIlIIIIlIIl[9]).length() > 0) {
                            gameProfileFromNBT = new GameProfile(null, tagCompound.getString(ItemSkull.lIlIlIIlIIIIlIIl[10]));
                        }
                    }
                    tileEntitySkull.setPlayerProfile(gameProfileFromNBT);
                }
                else {
                    tileEntitySkull.setType(itemStack.getMetadata());
                }
                tileEntitySkull.setSkullRotation(skullRotation);
                Blocks.skull.func_180679_a(world, offset, tileEntitySkull);
            }
            --itemStack.stackSize;
        }
        return true;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        for (int i = 0; i < ItemSkull.skullTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        int metadata = itemStack.getMetadata();
        if (metadata < 0 || metadata >= ItemSkull.skullTypes.length) {
            metadata = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + ItemSkull.lIlIlIIlIIIIlIIl[11] + ItemSkull.skullTypes[metadata];
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == 3 && itemStack.hasTagCompound()) {
            if (itemStack.getTagCompound().hasKey(ItemSkull.lIlIlIIlIIIIlIIl[12], 8)) {
                return StatCollector.translateToLocalFormatted(ItemSkull.lIlIlIIlIIIIlIIl[13], itemStack.getTagCompound().getString(ItemSkull.lIlIlIIlIIIIlIIl[14]));
            }
            if (itemStack.getTagCompound().hasKey(ItemSkull.lIlIlIIlIIIIlIIl[15], 10)) {
                final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(ItemSkull.lIlIlIIlIIIIlIIl[16]);
                if (compoundTag.hasKey(ItemSkull.lIlIlIIlIIIIlIIl[17], 8)) {
                    return StatCollector.translateToLocalFormatted(ItemSkull.lIlIlIIlIIIIlIIl[18], compoundTag.getString(ItemSkull.lIlIlIIlIIIIlIIl[19]));
                }
            }
        }
        return super.getItemStackDisplayName(itemStack);
    }
    
    @Override
    public boolean updateItemStackNBT(final NBTTagCompound nbtTagCompound) {
        super.updateItemStackNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(ItemSkull.lIlIlIIlIIIIlIIl[20], 8) && nbtTagCompound.getString(ItemSkull.lIlIlIIlIIIIlIIl[21]).length() > 0) {
            nbtTagCompound.setTag(ItemSkull.lIlIlIIlIIIIlIIl[23], NBTUtil.writeGameProfile(new NBTTagCompound(), TileEntitySkull.updateGameprofile(new GameProfile(null, nbtTagCompound.getString(ItemSkull.lIlIlIIlIIIIlIIl[22])))));
            return true;
        }
        return false;
    }
    
    private static void lllllIIlIIIlIIII() {
        (lIlIlIIlIIIIlIIl = new String[24])[0] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[0], ItemSkull.lIlIlIIlIIIIllIl[1]);
        ItemSkull.lIlIlIIlIIIIlIIl[1] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[2], ItemSkull.lIlIlIIlIIIIllIl[3]);
        ItemSkull.lIlIlIIlIIIIlIIl[2] = lllllIIlIIIIlIII(ItemSkull.lIlIlIIlIIIIllIl[4], ItemSkull.lIlIlIIlIIIIllIl[5]);
        ItemSkull.lIlIlIIlIIIIlIIl[3] = lllllIIlIIIIlIIl(ItemSkull.lIlIlIIlIIIIllIl[6], ItemSkull.lIlIlIIlIIIIllIl[7]);
        ItemSkull.lIlIlIIlIIIIlIIl[4] = lllllIIlIIIIlIll(ItemSkull.lIlIlIIlIIIIllIl[8], ItemSkull.lIlIlIIlIIIIllIl[9]);
        ItemSkull.lIlIlIIlIIIIlIIl[5] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[10], ItemSkull.lIlIlIIlIIIIllIl[11]);
        ItemSkull.lIlIlIIlIIIIlIIl[6] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[12], ItemSkull.lIlIlIIlIIIIllIl[13]);
        ItemSkull.lIlIlIIlIIIIlIIl[7] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[14], ItemSkull.lIlIlIIlIIIIllIl[15]);
        ItemSkull.lIlIlIIlIIIIlIIl[8] = lllllIIlIIIIlIIl(ItemSkull.lIlIlIIlIIIIllIl[16], ItemSkull.lIlIlIIlIIIIllIl[17]);
        ItemSkull.lIlIlIIlIIIIlIIl[9] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[18], ItemSkull.lIlIlIIlIIIIllIl[19]);
        ItemSkull.lIlIlIIlIIIIlIIl[10] = lllllIIlIIIIlIII(ItemSkull.lIlIlIIlIIIIllIl[20], ItemSkull.lIlIlIIlIIIIllIl[21]);
        ItemSkull.lIlIlIIlIIIIlIIl[11] = lllllIIlIIIIlIII(ItemSkull.lIlIlIIlIIIIllIl[22], ItemSkull.lIlIlIIlIIIIllIl[23]);
        ItemSkull.lIlIlIIlIIIIlIIl[12] = lllllIIlIIIIlIll(ItemSkull.lIlIlIIlIIIIllIl[24], ItemSkull.lIlIlIIlIIIIllIl[25]);
        ItemSkull.lIlIlIIlIIIIlIIl[13] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[26], ItemSkull.lIlIlIIlIIIIllIl[27]);
        ItemSkull.lIlIlIIlIIIIlIIl[14] = lllllIIlIIIIlIIl(ItemSkull.lIlIlIIlIIIIllIl[28], ItemSkull.lIlIlIIlIIIIllIl[29]);
        ItemSkull.lIlIlIIlIIIIlIIl[15] = lllllIIlIIIIIlll(ItemSkull.lIlIlIIlIIIIllIl[30], ItemSkull.lIlIlIIlIIIIllIl[31]);
        ItemSkull.lIlIlIIlIIIIlIIl[16] = lllllIIlIIIIlIIl(ItemSkull.lIlIlIIlIIIIllIl[32], ItemSkull.lIlIlIIlIIIIllIl[33]);
        ItemSkull.lIlIlIIlIIIIlIIl[17] = lllllIIlIIIIlIIl("T4IdDJi0p04=", "jUjAr");
        ItemSkull.lIlIlIIlIIIIlIIl[18] = lllllIIlIIIIlIll("CBkyGVkSBiIYG08dOxUOBB95GhYMCA==", "amWtw");
        ItemSkull.lIlIlIIlIIIIlIIl[19] = lllllIIlIIIIlIll("GS00FQ==", "WLYpN");
        ItemSkull.lIlIlIIlIIIIlIIl[20] = lllllIIlIIIIIlll("cQfD2McjWHw0dNSaQHBQ9A==", "ATpki");
        ItemSkull.lIlIlIIlIIIIlIIl[21] = lllllIIlIIIIlIll("CwADNiUXHBg/Ow==", "XkvZI");
        ItemSkull.lIlIlIIlIIIIlIIl[22] = lllllIIlIIIIIlll("krrmgIeS7554S1evmkXJ+w==", "mHjwF");
        ItemSkull.lIlIlIIlIIIIlIIl[23] = lllllIIlIIIIlIII("1GWb4XENfQyQrp0t6zl2Og==", "NLfAx");
        ItemSkull.lIlIlIIlIIIIllIl = null;
    }
    
    private static void lllllIIlIIIlIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ItemSkull.lIlIlIIlIIIIllIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllllIIlIIIIIlll(final String s, final String s2) {
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
    
    private static String lllllIIlIIIIlIll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lllllIIlIIIIlIIl(final String s, final String s2) {
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
    
    private static String lllllIIlIIIIlIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
