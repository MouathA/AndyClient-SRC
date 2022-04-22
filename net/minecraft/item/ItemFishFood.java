package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemFishFood extends ItemFood
{
    private final boolean cooked;
    private static final String __OBFID;
    
    public ItemFishFood(final boolean cooked) {
        super(0, 0.0f, false);
        this.cooked = cooked;
    }
    
    @Override
    public int getHealAmount(final ItemStack itemStack) {
        final FishType fishTypeForItemStack = FishType.getFishTypeForItemStack(itemStack);
        return (this.cooked && fishTypeForItemStack.getCookable()) ? fishTypeForItemStack.getCookedHealAmount() : fishTypeForItemStack.getUncookedHealAmount();
    }
    
    @Override
    public float getSaturationModifier(final ItemStack itemStack) {
        final FishType fishTypeForItemStack = FishType.getFishTypeForItemStack(itemStack);
        return (this.cooked && fishTypeForItemStack.getCookable()) ? fishTypeForItemStack.getCookedSaturationModifier() : fishTypeForItemStack.getUncookedSaturationModifier();
    }
    
    @Override
    public String getPotionEffect(final ItemStack itemStack) {
        return (FishType.getFishTypeForItemStack(itemStack) == FishType.PUFFERFISH) ? PotionHelper.field_151423_m : null;
    }
    
    @Override
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (FishType.getFishTypeForItemStack(itemStack) == FishType.PUFFERFISH) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
        }
        super.onFoodEaten(itemStack, world, entityPlayer);
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final FishType[] values = FishType.values();
        while (0 < values.length) {
            final FishType fishType = values[0];
            if (!this.cooked || fishType.getCookable()) {
                list.add(new ItemStack(this, 1, fishType.getItemDamage()));
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        final FishType fishTypeForItemStack = FishType.getFishTypeForItemStack(itemStack);
        return String.valueOf(this.getUnlocalizedName()) + "." + fishTypeForItemStack.getUnlocalizedNamePart() + "." + ((this.cooked && fishTypeForItemStack.getCookable()) ? "cooked" : "raw");
    }
    
    static {
        __OBFID = "CL_00000032";
    }
    
    public enum FishType
    {
        COD("COD", 0, "COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f), 
        SALMON("SALMON", 1, "SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f), 
        CLOWNFISH("CLOWNFISH", 2, "CLOWNFISH", 2, 2, "clownfish", 1, 0.1f), 
        PUFFERFISH("PUFFERFISH", 3, "PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
        
        private static final Map itemDamageToFishTypeMap;
        private final int itemDamage;
        private final String unlocalizedNamePart;
        private final int uncookedHealAmount;
        private final float uncookedSaturationModifier;
        private final int cookedHealAmount;
        private final float cookedSaturationModifier;
        private boolean cookable;
        private static final FishType[] $VALUES;
        private static final String __OBFID;
        private static final FishType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000033";
            ENUM$VALUES = new FishType[] { FishType.COD, FishType.SALMON, FishType.CLOWNFISH, FishType.PUFFERFISH };
            itemDamageToFishTypeMap = Maps.newHashMap();
            $VALUES = new FishType[] { FishType.COD, FishType.SALMON, FishType.CLOWNFISH, FishType.PUFFERFISH };
            final FishType[] values = values();
            while (0 < values.length) {
                final FishType fishType = values[0];
                FishType.itemDamageToFishTypeMap.put(fishType.getItemDamage(), fishType);
                int n = 0;
                ++n;
            }
        }
        
        private FishType(final String s, final int n, final String s2, final int n2, final int itemDamage, final String unlocalizedNamePart, final int uncookedHealAmount, final float uncookedSaturationModifier, final int cookedHealAmount, final float cookedSaturationModifier) {
            this.cookable = false;
            this.itemDamage = itemDamage;
            this.unlocalizedNamePart = unlocalizedNamePart;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = cookedHealAmount;
            this.cookedSaturationModifier = cookedSaturationModifier;
            this.cookable = true;
        }
        
        private FishType(final String s, final int n, final String s2, final int n2, final int itemDamage, final String unlocalizedNamePart, final int uncookedHealAmount, final float uncookedSaturationModifier) {
            this.cookable = false;
            this.itemDamage = itemDamage;
            this.unlocalizedNamePart = unlocalizedNamePart;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = 0;
            this.cookedSaturationModifier = 0.0f;
            this.cookable = false;
        }
        
        public int getItemDamage() {
            return this.itemDamage;
        }
        
        public String getUnlocalizedNamePart() {
            return this.unlocalizedNamePart;
        }
        
        public int getUncookedHealAmount() {
            return this.uncookedHealAmount;
        }
        
        public float getUncookedSaturationModifier() {
            return this.uncookedSaturationModifier;
        }
        
        public int getCookedHealAmount() {
            return this.cookedHealAmount;
        }
        
        public float getCookedSaturationModifier() {
            return this.cookedSaturationModifier;
        }
        
        public boolean getCookable() {
            return this.cookable;
        }
        
        public static FishType getFishTypeForItemDamage(final int n) {
            final FishType fishType = FishType.itemDamageToFishTypeMap.get(n);
            return (fishType == null) ? FishType.COD : fishType;
        }
        
        public static FishType getFishTypeForItemStack(final ItemStack itemStack) {
            return (itemStack.getItem() instanceof ItemFishFood) ? getFishTypeForItemDamage(itemStack.getMetadata()) : FishType.COD;
        }
    }
}
