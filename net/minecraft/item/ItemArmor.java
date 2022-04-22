package net.minecraft.item;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;
import net.minecraft.init.*;

public class ItemArmor extends Item
{
    private static final int[] maxDamageArray;
    public static final String[] EMPTY_SLOT_NAMES;
    private static final IBehaviorDispenseItem dispenserBehavior;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final ArmorMaterial material;
    private static final String __OBFID;
    private static final String[] lIlIlIIIllllllIl;
    private static String[] lIlIlIIlIIIIIIlI;
    
    static {
        lllllIIIlllllllI();
        lllllIIIllllllIl();
        __OBFID = ItemArmor.lIlIlIIIllllllIl[0];
        maxDamageArray = new int[] { 11, 16, 15, 13 };
        EMPTY_SLOT_NAMES = new String[] { ItemArmor.lIlIlIIIllllllIl[1], ItemArmor.lIlIlIIIllllllIl[2], ItemArmor.lIlIlIIIllllllIl[3], ItemArmor.lIlIlIIIllllllIl[4] };
        dispenserBehavior = new BehaviorDefaultDispenseItem() {
            private static final String __OBFID;
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final int x = offset.getX();
                final int y = offset.getY();
                final int z = offset.getZ();
                final List func_175647_a = blockSource.getWorld().func_175647_a(EntityLivingBase.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), Predicates.and(IEntitySelector.field_180132_d, new IEntitySelector.ArmoredMob(itemStack)));
                if (func_175647_a.size() > 0) {
                    final EntityLivingBase entityLivingBase = func_175647_a.get(0);
                    final int n = (entityLivingBase instanceof EntityPlayer) ? 1 : 0;
                    final int armorPosition = EntityLiving.getArmorPosition(itemStack);
                    final ItemStack copy = itemStack.copy();
                    copy.stackSize = 1;
                    entityLivingBase.setCurrentItemOrArmor(armorPosition - n, copy);
                    if (entityLivingBase instanceof EntityLiving) {
                        ((EntityLiving)entityLivingBase).setEquipmentDropChance(armorPosition, 2.0f);
                    }
                    --itemStack.stackSize;
                    return itemStack;
                }
                return super.dispenseStack(blockSource, itemStack);
            }
            
            static {
                __OBFID = "CL_00001767";
            }
        };
    }
    
    public ItemArmor(final ArmorMaterial material, final int renderIndex, final int armorType) {
        this.material = material;
        this.armorType = armorType;
        this.renderIndex = renderIndex;
        this.damageReduceAmount = material.getDamageReductionAmount(armorType);
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemArmor.dispenserBehavior);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n > 0) {
            return 16777215;
        }
        int color = this.getColor(itemStack);
        if (color < 0) {
            color = 16777215;
        }
        return color;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }
    
    public boolean hasColor(final ItemStack itemStack) {
        return this.material == ArmorMaterial.LEATHER && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(ItemArmor.lIlIlIIIllllllIl[5], 10) && itemStack.getTagCompound().getCompoundTag(ItemArmor.lIlIlIIIllllllIl[6]).hasKey(ItemArmor.lIlIlIIIllllllIl[7], 3);
    }
    
    public int getColor(final ItemStack itemStack) {
        if (this.material != ArmorMaterial.LEATHER) {
            return -1;
        }
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null) {
            final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.lIlIlIIIllllllIl[8]);
            if (compoundTag != null && compoundTag.hasKey(ItemArmor.lIlIlIIIllllllIl[9], 3)) {
                return compoundTag.getInteger(ItemArmor.lIlIlIIIllllllIl[10]);
            }
        }
        return 10511680;
    }
    
    public void removeColor(final ItemStack itemStack) {
        if (this.material == ArmorMaterial.LEATHER) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound != null) {
                final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.lIlIlIIIllllllIl[11]);
                if (compoundTag.hasKey(ItemArmor.lIlIlIIIllllllIl[12])) {
                    compoundTag.removeTag(ItemArmor.lIlIlIIIllllllIl[13]);
                }
            }
        }
    }
    
    public void func_82813_b(final ItemStack itemStack, final int n) {
        if (this.material != ArmorMaterial.LEATHER) {
            throw new UnsupportedOperationException(ItemArmor.lIlIlIIIllllllIl[14]);
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            itemStack.setTagCompound(tagCompound);
        }
        final NBTTagCompound compoundTag = tagCompound.getCompoundTag(ItemArmor.lIlIlIIIllllllIl[15]);
        if (!tagCompound.hasKey(ItemArmor.lIlIlIIIllllllIl[16], 10)) {
            tagCompound.setTag(ItemArmor.lIlIlIIIllllllIl[17], compoundTag);
        }
        compoundTag.setInteger(ItemArmor.lIlIlIIIllllllIl[18], n);
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.material.getBaseItemForRepair() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final int n = EntityLiving.getArmorPosition(itemStack) - 1;
        if (entityPlayer.getCurrentArmor(n) == null) {
            entityPlayer.setCurrentItemOrArmor(n, itemStack.copy());
            itemStack.stackSize = 0;
        }
        return itemStack;
    }
    
    static int[] access$0() {
        return ItemArmor.maxDamageArray;
    }
    
    private static void lllllIIIllllllIl() {
        (lIlIlIIIllllllIl = new String[19])[0] = lllllIIIllllIIlI(ItemArmor.lIlIlIIlIIIIIIlI[0], ItemArmor.lIlIlIIlIIIIIIlI[1]);
        ItemArmor.lIlIlIIIllllllIl[1] = lllllIIIllllIIll(ItemArmor.lIlIlIIlIIIIIIlI[2], ItemArmor.lIlIlIIlIIIIIIlI[3]);
        ItemArmor.lIlIlIIIllllllIl[2] = lllllIIIllllIIlI(ItemArmor.lIlIlIIlIIIIIIlI[4], ItemArmor.lIlIlIIlIIIIIIlI[5]);
        ItemArmor.lIlIlIIIllllllIl[3] = lllllIIIlllllIIl(ItemArmor.lIlIlIIlIIIIIIlI[6], ItemArmor.lIlIlIIlIIIIIIlI[7]);
        ItemArmor.lIlIlIIIllllllIl[4] = lllllIIIllllIIlI(ItemArmor.lIlIlIIlIIIIIIlI[8], ItemArmor.lIlIlIIlIIIIIIlI[9]);
        ItemArmor.lIlIlIIIllllllIl[5] = lllllIIIlllllIIl(ItemArmor.lIlIlIIlIIIIIIlI[10], ItemArmor.lIlIlIIlIIIIIIlI[11]);
        ItemArmor.lIlIlIIIllllllIl[6] = lllllIIIllllIIll(ItemArmor.lIlIlIIlIIIIIIlI[12], ItemArmor.lIlIlIIlIIIIIIlI[13]);
        ItemArmor.lIlIlIIIllllllIl[7] = lllllIIIllllIIll(ItemArmor.lIlIlIIlIIIIIIlI[14], ItemArmor.lIlIlIIlIIIIIIlI[15]);
        ItemArmor.lIlIlIIIllllllIl[8] = lllllIIIllllllII(ItemArmor.lIlIlIIlIIIIIIlI[16], ItemArmor.lIlIlIIlIIIIIIlI[17]);
        ItemArmor.lIlIlIIIllllllIl[9] = lllllIIIllllIIlI(ItemArmor.lIlIlIIlIIIIIIlI[18], ItemArmor.lIlIlIIlIIIIIIlI[19]);
        ItemArmor.lIlIlIIIllllllIl[10] = lllllIIIlllllIIl(ItemArmor.lIlIlIIlIIIIIIlI[20], ItemArmor.lIlIlIIlIIIIIIlI[21]);
        ItemArmor.lIlIlIIIllllllIl[11] = lllllIIIlllllIIl(ItemArmor.lIlIlIIlIIIIIIlI[22], ItemArmor.lIlIlIIlIIIIIIlI[23]);
        ItemArmor.lIlIlIIIllllllIl[12] = lllllIIIlllllIIl(ItemArmor.lIlIlIIlIIIIIIlI[24], ItemArmor.lIlIlIIlIIIIIIlI[25]);
        ItemArmor.lIlIlIIIllllllIl[13] = lllllIIIllllllII(ItemArmor.lIlIlIIlIIIIIIlI[26], ItemArmor.lIlIlIIlIIIIIIlI[27]);
        ItemArmor.lIlIlIIIllllllIl[14] = lllllIIIllllIIll("FNtZ1W8Mvk7PKlSrL9ke04lth5iTA5DG", "OcQXw");
        ItemArmor.lIlIlIIIllllllIl[15] = lllllIIIllllIIll("26hPKOHua+Y=", "NkNrb");
        ItemArmor.lIlIlIIIllllllIl[16] = lllllIIIlllllIIl("Kz0KIDwuLQ==", "OTyPP");
        ItemArmor.lIlIlIIIllllllIl[17] = lllllIIIlllllIIl("AhM3GQsHAw==", "fzDig");
        ItemArmor.lIlIlIIIllllllIl[18] = lllllIIIllllIIlI("QrASIg55dP0MgUYwpsisKA==", "QIhli");
        ItemArmor.lIlIlIIlIIIIIIlI = null;
    }
    
    private static void lllllIIIlllllllI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ItemArmor.lIlIlIIlIIIIIIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllllIIIllllllII(final String s, final String s2) {
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
    
    private static String lllllIIIllllIIlI(final String s, final String s2) {
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
    
    private static String lllllIIIllllIIll(final String s, final String s2) {
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
    
    private static String lllllIIIlllllIIl(String s, final String s2) {
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
    
    public enum ArmorMaterial
    {
        LEATHER("LEATHER", 0, "LEATHER", 0, "leather", 5, new int[] { 1, 3, 2, 1 }, 15), 
        CHAIN("CHAIN", 1, "CHAIN", 1, "chainmail", 15, new int[] { 2, 5, 4, 1 }, 12), 
        IRON("IRON", 2, "IRON", 2, "iron", 15, new int[] { 2, 6, 5, 2 }, 9), 
        GOLD("GOLD", 3, "GOLD", 3, "gold", 7, new int[] { 2, 5, 3, 1 }, 25), 
        DIAMOND("DIAMOND", 4, "DIAMOND", 4, "diamond", 33, new int[] { 3, 8, 6, 3 }, 10);
        
        private final String field_179243_f;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private static final ArmorMaterial[] $VALUES;
        private static final String __OBFID;
        private static final ArmorMaterial[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001768";
            ENUM$VALUES = new ArmorMaterial[] { ArmorMaterial.LEATHER, ArmorMaterial.CHAIN, ArmorMaterial.IRON, ArmorMaterial.GOLD, ArmorMaterial.DIAMOND };
            $VALUES = new ArmorMaterial[] { ArmorMaterial.LEATHER, ArmorMaterial.CHAIN, ArmorMaterial.IRON, ArmorMaterial.GOLD, ArmorMaterial.DIAMOND };
        }
        
        private ArmorMaterial(final String s, final int n, final String s2, final int n2, final String field_179243_f, final int maxDamageFactor, final int[] damageReductionAmountArray, final int enchantability) {
            this.field_179243_f = field_179243_f;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
        }
        
        public int getDurability(final int n) {
            return ItemArmor.access$0()[n] * this.maxDamageFactor;
        }
        
        public int getDamageReductionAmount(final int n) {
            return this.damageReductionAmountArray[n];
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        public Item getBaseItemForRepair() {
            return (this == ArmorMaterial.LEATHER) ? Items.leather : ((this == ArmorMaterial.CHAIN) ? Items.iron_ingot : ((this == ArmorMaterial.GOLD) ? Items.gold_ingot : ((this == ArmorMaterial.IRON) ? Items.iron_ingot : ((this == ArmorMaterial.DIAMOND) ? Items.diamond : null))));
        }
        
        public String func_179242_c() {
            return this.field_179243_f;
        }
    }
}
