package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.potion.*;

public class ItemFood extends Item
{
    public final int itemUseDuration;
    private final int healAmount;
    private final float saturationModifier;
    private final boolean isWolfsFavoriteMeat;
    private boolean alwaysEdible;
    private int potionId;
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;
    private static final String __OBFID;
    
    public ItemFood(final int healAmount, final float saturationModifier, final boolean isWolfsFavoriteMeat) {
        this.itemUseDuration = 32;
        this.healAmount = healAmount;
        this.isWolfsFavoriteMeat = isWolfsFavoriteMeat;
        this.saturationModifier = saturationModifier;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public ItemFood(final int n, final boolean b) {
        this(n, 0.6f, b);
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        --itemStack.stackSize;
        entityPlayer.getFoodStats().addStats(this, itemStack);
        world.playSoundAtEntity(entityPlayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(itemStack, world, entityPlayer);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability) {
            entityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.EAT;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.canEat(this.alwaysEdible)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    public int getHealAmount(final ItemStack itemStack) {
        return this.healAmount;
    }
    
    public float getSaturationModifier(final ItemStack itemStack) {
        return this.saturationModifier;
    }
    
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    public ItemFood setPotionEffect(final int potionId, final int potionDuration, final int potionAmplifier, final float potionEffectProbability) {
        this.potionId = potionId;
        this.potionDuration = potionDuration;
        this.potionAmplifier = potionAmplifier;
        this.potionEffectProbability = potionEffectProbability;
        return this;
    }
    
    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
    
    static {
        __OBFID = "CL_00000036";
    }
}
