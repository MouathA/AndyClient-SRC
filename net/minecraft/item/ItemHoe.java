package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    private static final String __OBFID;
    
    public ItemHoe(final ToolMaterial theToolMaterial) {
        this.theToolMaterial = theToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(theToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.func_175151_a(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial() == Material.air) {
            if (block == Blocks.grass) {
                return this.func_179232_a(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
            }
            if (block == Blocks.dirt) {
                switch (SwitchDirtType.field_179590_a[((BlockDirt.DirtType)blockState.getValue(BlockDirt.VARIANT)).ordinal()]) {
                    case 1: {
                        return this.func_179232_a(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
                    }
                    case 2: {
                        return this.func_179232_a(itemStack, entityPlayer, world, blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean func_179232_a(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, blockState.getBlock().stepSound.getStepSound(), (blockState.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, blockState.getBlock().stepSound.getFrequency() * 0.8f);
        if (world.isRemote) {
            return true;
        }
        world.setBlockState(blockPos, blockState);
        itemStack.damageItem(1, entityPlayer);
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
    
    static {
        __OBFID = "CL_00000039";
    }
    
    static final class SwitchDirtType
    {
        static final int[] field_179590_a;
        private static final String __OBFID;
        private static final String[] lIIIllllIlIIIIlI;
        private static String[] lIIIllllIlIIIIll;
        
        static {
            llIlIIllIlIIlIlI();
            llIlIIllIlIIlIIl();
            __OBFID = SwitchDirtType.lIIIllllIlIIIIlI[0];
            field_179590_a = new int[BlockDirt.DirtType.values().length];
            try {
                SwitchDirtType.field_179590_a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchDirtType.field_179590_a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
        
        private static void llIlIIllIlIIlIIl() {
            (lIIIllllIlIIIIlI = new String[1])[0] = llIlIIllIlIIlIII(SwitchDirtType.lIIIllllIlIIIIll[0], SwitchDirtType.lIIIllllIlIIIIll[1]);
            SwitchDirtType.lIIIllllIlIIIIll = null;
        }
        
        private static void llIlIIllIlIIlIlI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchDirtType.lIIIllllIlIIIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llIlIIllIlIIlIII(final String s, final String s2) {
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
}
