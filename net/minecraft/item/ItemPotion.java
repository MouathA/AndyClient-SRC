package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;

public class ItemPotion extends Item
{
    private Map effectCache;
    private static final Map field_77835_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000055";
        field_77835_b = Maps.newLinkedHashMap();
    }
    
    public ItemPotion() {
        this.effectCache = Maps.newHashMap();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    public List getEffects(final ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
            final ArrayList arrayList = Lists.newArrayList();
            final NBTTagList tagList = itemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
            while (0 < tagList.tagCount()) {
                final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(0));
                if (customPotionEffectFromNBT != null) {
                    arrayList.add(customPotionEffectFromNBT);
                }
                int n = 0;
                ++n;
            }
            return arrayList;
        }
        List potionEffects = this.effectCache.get(itemStack.getMetadata());
        if (potionEffects == null) {
            potionEffects = PotionHelper.getPotionEffects(itemStack.getMetadata(), false);
            this.effectCache.put(itemStack.getMetadata(), potionEffects);
        }
        return potionEffects;
    }
    
    public List getEffects(final int n) {
        List potionEffects = this.effectCache.get(n);
        if (potionEffects == null) {
            potionEffects = PotionHelper.getPotionEffects(n, false);
            this.effectCache.put(n, potionEffects);
        }
        return potionEffects;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        if (!world.isRemote) {
            final List effects = this.getEffects(itemStack);
            if (effects != null) {
                final Iterator<PotionEffect> iterator = effects.iterator();
                while (iterator.hasNext()) {
                    entityPlayer.addPotionEffect(new PotionEffect(iterator.next()));
                }
            }
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        if (!entityPlayer.capabilities.isCreativeMode) {
            if (itemStack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.DRINK;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (isSplash(itemStack.getMetadata())) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
            }
            world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemPotion.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityPotion(world, entityPlayer, itemStack));
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            return itemStack;
        }
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    public static boolean isSplash(final int n) {
        return (n & 0x4000) != 0x0;
    }
    
    public int getColorFromDamage(final int n) {
        return PotionHelper.func_77915_a(n, false);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return (n > 0) ? 16777215 : this.getColorFromDamage(itemStack.getMetadata());
    }
    
    public boolean isEffectInstant(final int n) {
        final List effects = this.getEffects(n);
        if (effects != null && !effects.isEmpty()) {
            final Iterator<PotionEffect> iterator = effects.iterator();
            while (iterator.hasNext()) {
                if (Potion.potionTypes[iterator.next().getPotionID()].isInstant()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == 0) {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        String string = "";
        if (isSplash(itemStack.getMetadata())) {
            string = String.valueOf(StatCollector.translateToLocal("potion.prefix.grenade").trim()) + " ";
        }
        final List effects = Items.potionitem.getEffects(itemStack);
        if (effects != null && !effects.isEmpty()) {
            return String.valueOf(string) + StatCollector.translateToLocal(String.valueOf(effects.get(0).getEffectName()) + ".postfix").trim();
        }
        return String.valueOf(StatCollector.translateToLocal(PotionHelper.func_77905_c(itemStack.getMetadata())).trim()) + " " + super.getItemStackDisplayName(itemStack);
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.getMetadata() != 0) {
            final List effects = Items.potionitem.getEffects(itemStack);
            final HashMultimap create = HashMultimap.create();
            if (effects != null && !effects.isEmpty()) {
                for (final PotionEffect potionEffect : effects) {
                    String s = StatCollector.translateToLocal(potionEffect.getEffectName()).trim();
                    final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                    final Map func_111186_k = potion.func_111186_k();
                    if (func_111186_k != null && func_111186_k.size() > 0) {
                        for (final Map.Entry<K, AttributeModifier> entry : func_111186_k.entrySet()) {
                            final AttributeModifier attributeModifier = entry.getValue();
                            create.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), new AttributeModifier(attributeModifier.getName(), potion.func_111183_a(potionEffect.getAmplifier(), attributeModifier), attributeModifier.getOperation()));
                        }
                    }
                    if (potionEffect.getAmplifier() > 0) {
                        s = String.valueOf(s) + " " + StatCollector.translateToLocal("potion.potency." + potionEffect.getAmplifier()).trim();
                    }
                    if (potionEffect.getDuration() > 20) {
                        s = String.valueOf(s) + " (" + Potion.getDurationString(potionEffect) + ")";
                    }
                    if (potion.isBadEffect()) {
                        list.add(EnumChatFormatting.RED + s);
                    }
                    else {
                        list.add(EnumChatFormatting.GRAY + s);
                    }
                }
            }
            else {
                list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("potion.empty").trim());
            }
            if (!create.isEmpty()) {
                list.add("");
                list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
                for (final Map.Entry<K, AttributeModifier> entry2 : create.entries()) {
                    final AttributeModifier attributeModifier2 = entry2.getValue();
                    final double amount = attributeModifier2.getAmount();
                    double amount2;
                    if (attributeModifier2.getOperation() != 1 && attributeModifier2.getOperation() != 2) {
                        amount2 = attributeModifier2.getAmount();
                    }
                    else {
                        amount2 = attributeModifier2.getAmount() * 100.0;
                    }
                    if (amount > 0.0) {
                        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributeModifier2.getOperation(), ItemStack.DECIMALFORMAT.format(amount2), StatCollector.translateToLocal("attribute.name." + (String)entry2.getKey())));
                    }
                    else {
                        if (amount >= 0.0) {
                            continue;
                        }
                        list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributeModifier2.getOperation(), ItemStack.DECIMALFORMAT.format(amount2 * -1.0), StatCollector.translateToLocal("attribute.name." + (String)entry2.getKey())));
                    }
                }
            }
        }
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        final List effects = this.getEffects(itemStack);
        return effects != null && !effects.isEmpty();
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        super.getSubItems(item, creativeTabs, list);
        if (ItemPotion.field_77835_b.isEmpty()) {
            while (0 <= 15) {
                while (0 <= 1) {
                    if (!false) {}
                    while (0 <= 2) {
                        if (false) {
                            if (false != true) {
                                if (0 == 2) {}
                            }
                        }
                        final List potionEffects = PotionHelper.getPotionEffects(16448, false);
                        if (potionEffects != null && !potionEffects.isEmpty()) {
                            ItemPotion.field_77835_b.put(potionEffects, 16448);
                        }
                        int n = 0;
                        ++n;
                    }
                    int intValue = 0;
                    ++intValue;
                }
                int n2 = 0;
                ++n2;
            }
        }
        final Iterator<Integer> iterator = ItemPotion.field_77835_b.values().iterator();
        while (iterator.hasNext()) {
            final int intValue = iterator.next();
            list.add(new ItemStack(item, 1, 0));
        }
    }
}
