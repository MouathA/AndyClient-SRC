package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ItemDye extends Item
{
    public static final int[] dyeColors;
    private static final String __OBFID;
    private static final String[] llIIIllIlIIIlIl;
    private static String[] llIIIllIlIIIllI;
    
    static {
        lIIlIIlIIlIIIIIl();
        lIIlIIlIIlIIIIII();
        __OBFID = ItemDye.llIIIllIlIIIlIl[0];
        dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
    
    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + ItemDye.llIIIllIlIIIlIl[1] + EnumDyeColor.func_176766_a(itemStack.getMetadata()).func_176762_d();
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.func_175151_a(offset.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final EnumDyeColor func_176766_a = EnumDyeColor.func_176766_a(itemStack.getMetadata());
        if (func_176766_a == EnumDyeColor.WHITE) {
            if (func_179234_a(itemStack, world, offset)) {
                if (!world.isRemote) {
                    world.playAuxSFX(2005, offset, 0);
                }
                return true;
            }
        }
        else if (func_176766_a == EnumDyeColor.BROWN) {
            final IBlockState blockState = world.getBlockState(offset);
            if (blockState.getBlock() == Blocks.log && blockState.getValue(BlockPlanks.VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE) {
                if (enumFacing == EnumFacing.DOWN) {
                    return false;
                }
                if (enumFacing == EnumFacing.UP) {
                    return false;
                }
                offset = offset.offset(enumFacing);
                if (world.isAirBlock(offset)) {
                    world.setBlockState(offset, Blocks.cocoa.onBlockPlaced(world, offset, enumFacing, n, n2, n3, 0, entityPlayer), 2);
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean func_179234_a(final ItemStack itemStack, final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IGrowable) {
            final IGrowable growable = (IGrowable)blockState.getBlock();
            if (growable.isStillGrowing(world, blockPos, blockState, world.isRemote)) {
                if (!world.isRemote) {
                    if (growable.canUseBonemeal(world, world.rand, blockPos, blockState)) {
                        growable.grow(world, world.rand, blockPos, blockState);
                    }
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        return false;
    }
    
    public static void func_180617_a(final World world, final BlockPos blockPos, int n) {
        if (n == 0) {
            n = 15;
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(world, blockPos);
            for (int i = 0; i < n; ++i) {
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, blockPos.getX() + ItemDye.itemRand.nextFloat(), blockPos.getY() + ItemDye.itemRand.nextFloat() * block.getBlockBoundsMaxY(), blockPos.getZ() + ItemDye.itemRand.nextFloat(), ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, new int[0]);
            }
        }
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntitySheep) {
            final EntitySheep entitySheep = (EntitySheep)entityLivingBase;
            final EnumDyeColor func_176766_a = EnumDyeColor.func_176766_a(itemStack.getMetadata());
            if (!entitySheep.getSheared() && entitySheep.func_175509_cj() != func_176766_a) {
                entitySheep.func_175512_b(func_176766_a);
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        for (int i = 0; i < 16; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    private static void lIIlIIlIIlIIIIII() {
        (llIIIllIlIIIlIl = new String[2])[0] = lIIlIIlIIIlllllI(ItemDye.llIIIllIlIIIllI[0], ItemDye.llIIIllIlIIIllI[1]);
        ItemDye.llIIIllIlIIIlIl[1] = lIIlIIlIIIllllll(ItemDye.llIIIllIlIIIllI[2], ItemDye.llIIIllIlIIIllI[3]);
        ItemDye.llIIIllIlIIIllI = null;
    }
    
    private static void lIIlIIlIIlIIIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ItemDye.llIIIllIlIIIllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIlIIlIIIlllllI(final String s, final String s2) {
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
    
    private static String lIIlIIlIIIllllll(final String s, final String s2) {
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
}
