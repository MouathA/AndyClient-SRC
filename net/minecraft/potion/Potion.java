package net.minecraft.potion;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;

public class Potion
{
    public static final Potion[] potionTypes;
    private static final Map field_180150_I;
    public static final Potion field_180151_b;
    public static final Potion moveSpeed;
    public static final Potion moveSlowdown;
    public static final Potion digSpeed;
    public static final Potion digSlowdown;
    public static final Potion damageBoost;
    public static final Potion heal;
    public static final Potion harm;
    public static final Potion jump;
    public static final Potion confusion;
    public static final Potion regeneration;
    public static final Potion resistance;
    public static final Potion fireResistance;
    public static final Potion waterBreathing;
    public static final Potion invisibility;
    public static final Potion blindness;
    public static final Potion nightVision;
    public static final Potion hunger;
    public static final Potion weakness;
    public static final Potion poison;
    public static final Potion wither;
    public static final Potion field_180152_w;
    public static final Potion absorption;
    public static final Potion saturation;
    public static final Potion field_180153_z;
    public static final Potion field_180147_A;
    public static final Potion field_180148_B;
    public static final Potion field_180149_C;
    public static final Potion field_180143_D;
    public static final Potion field_180144_E;
    public static final Potion field_180145_F;
    public static final Potion field_180146_G;
    public final int id;
    private final Map attributeModifierMap;
    private final boolean isBadEffect;
    private final int liquidColor;
    private String name;
    private int statusIconIndex;
    private double effectiveness;
    private boolean usable;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001528";
        potionTypes = new Potion[32];
        field_180150_I = Maps.newHashMap();
        field_180151_b = null;
        moveSpeed = new Potion(1, new ResourceLocation("speed"), false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224, 2);
        moveSlowdown = new Potion(2, new ResourceLocation("slowness"), true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, 2);
        digSpeed = new Potion(3, new ResourceLocation("haste"), false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5);
        digSlowdown = new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
        damageBoost = new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5, 2);
        heal = new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179).setPotionName("potion.heal");
        harm = new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481).setPotionName("potion.harm");
        jump = new Potion(8, new ResourceLocation("jump_boost"), false, 2293580).setPotionName("potion.jump").setIconIndex(2, 1);
        confusion = new Potion(9, new ResourceLocation("nausea"), true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25);
        regeneration = new Potion(10, new ResourceLocation("regeneration"), false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25);
        resistance = new Potion(11, new ResourceLocation("resistance"), false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
        fireResistance = new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
        waterBreathing = new Potion(13, new ResourceLocation("water_breathing"), false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
        invisibility = new Potion(14, new ResourceLocation("invisibility"), false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
        blindness = new Potion(15, new ResourceLocation("blindness"), true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25);
        nightVision = new Potion(16, new ResourceLocation("night_vision"), false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
        hunger = new Potion(17, new ResourceLocation("hunger"), true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
        weakness = new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
        poison = new Potion(19, new ResourceLocation("poison"), true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25);
        wither = new Potion(20, new ResourceLocation("wither"), true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25);
        field_180152_w = new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963).setPotionName("potion.healthBoost").setIconIndex(2, 2).registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
        absorption = new PotionAbsoption(22, new ResourceLocation("absorption"), false, 2445989).setPotionName("potion.absorption").setIconIndex(2, 2);
        saturation = new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179).setPotionName("potion.saturation");
        field_180153_z = null;
        field_180147_A = null;
        field_180148_B = null;
        field_180149_C = null;
        field_180143_D = null;
        field_180144_E = null;
        field_180145_F = null;
        field_180146_G = null;
    }
    
    protected Potion(final int id, final ResourceLocation resourceLocation, final boolean isBadEffect, final int liquidColor) {
        this.attributeModifierMap = Maps.newHashMap();
        this.name = "";
        this.statusIconIndex = -1;
        this.id = id;
        Potion.potionTypes[id] = this;
        Potion.field_180150_I.put(resourceLocation, this);
        this.isBadEffect = isBadEffect;
        if (isBadEffect) {
            this.effectiveness = 0.5;
        }
        else {
            this.effectiveness = 1.0;
        }
        this.liquidColor = liquidColor;
    }
    
    public static Potion func_180142_b(final String s) {
        return Potion.field_180150_I.get(new ResourceLocation(s));
    }
    
    public static String[] func_180141_c() {
        final String[] array = new String[Potion.field_180150_I.size()];
        for (final ResourceLocation resourceLocation : Potion.field_180150_I.keySet()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = resourceLocation.toString();
        }
        return array;
    }
    
    protected Potion setIconIndex(final int n, final int n2) {
        this.statusIconIndex = n + n2 * 8;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void performEffect(final EntityLivingBase entityLivingBase, final int n) {
        if (this.id == Potion.regeneration.id) {
            if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                entityLivingBase.heal(1.0f);
            }
        }
        else if (this.id == Potion.poison.id) {
            if (entityLivingBase.getHealth() > 1.0f) {
                entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0f);
            }
        }
        else if (this.id == Potion.wither.id) {
            entityLivingBase.attackEntityFrom(DamageSource.wither, 1.0f);
        }
        else if (this.id == Potion.hunger.id && entityLivingBase instanceof EntityPlayer) {
            ((EntityPlayer)entityLivingBase).addExhaustion(0.025f * (n + 1));
        }
        else if (this.id == Potion.saturation.id && entityLivingBase instanceof EntityPlayer) {
            if (!entityLivingBase.worldObj.isRemote) {
                ((EntityPlayer)entityLivingBase).getFoodStats().addStats(n + 1, 1.0f);
            }
        }
        else if ((this.id != Potion.heal.id || entityLivingBase.isEntityUndead()) && (this.id != Potion.harm.id || !entityLivingBase.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !entityLivingBase.isEntityUndead()) || (this.id == Potion.heal.id && entityLivingBase.isEntityUndead())) {
                entityLivingBase.attackEntityFrom(DamageSource.magic, (float)(6 << n));
            }
        }
        else {
            entityLivingBase.heal((float)Math.max(4 << n, 0));
        }
    }
    
    public void func_180793_a(final Entity entity, final Entity entity2, final EntityLivingBase entityLivingBase, final int n, final double n2) {
        if ((this.id != Potion.heal.id || entityLivingBase.isEntityUndead()) && (this.id != Potion.harm.id || !entityLivingBase.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !entityLivingBase.isEntityUndead()) || (this.id == Potion.heal.id && entityLivingBase.isEntityUndead())) {
                final int n3 = (int)(n2 * (6 << n) + 0.5);
                if (entity == null) {
                    entityLivingBase.attackEntityFrom(DamageSource.magic, (float)n3);
                }
                else {
                    entityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity, entity2), (float)n3);
                }
            }
        }
        else {
            entityLivingBase.heal((float)(int)(n2 * (4 << n) + 0.5));
        }
    }
    
    public boolean isInstant() {
        return false;
    }
    
    public boolean isReady(final int n, final int n2) {
        if (this.id == Potion.regeneration.id) {
            final int n3 = 50 >> n2;
            return n3 <= 0 || n % n3 == 0;
        }
        if (this.id == Potion.poison.id) {
            final int n4 = 25 >> n2;
            return n4 <= 0 || n % n4 == 0;
        }
        if (this.id == Potion.wither.id) {
            final int n5 = 40 >> n2;
            return n5 <= 0 || n % n5 == 0;
        }
        return this.id == Potion.hunger.id;
    }
    
    public Potion setPotionName(final String name) {
        this.name = name;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }
    
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }
    
    public boolean isBadEffect() {
        return this.isBadEffect;
    }
    
    public static String getDurationString(final PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return "**:**";
        }
        return StringUtils.ticksToElapsedTime(potionEffect.getDuration());
    }
    
    protected Potion setEffectiveness(final double effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }
    
    public double getEffectiveness() {
        return this.effectiveness;
    }
    
    public boolean isUsable() {
        return this.usable;
    }
    
    public int getLiquidColor() {
        return this.liquidColor;
    }
    
    public Potion registerPotionAttributeModifier(final IAttribute attribute, final String s, final double n, final int n2) {
        this.attributeModifierMap.put(attribute, new AttributeModifier(UUID.fromString(s), this.getName(), n, n2));
        return this;
    }
    
    public Map func_111186_k() {
        return this.attributeModifierMap;
    }
    
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        for (final Map.Entry<IAttribute, V> entry : this.attributeModifierMap.entrySet()) {
            final IAttributeInstance attributeInstance = baseAttributeMap.getAttributeInstance(entry.getKey());
            if (attributeInstance != null) {
                attributeInstance.removeModifier((AttributeModifier)entry.getValue());
            }
        }
    }
    
    public void applyAttributesModifiersToEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        for (final Map.Entry<IAttribute, V> entry : this.attributeModifierMap.entrySet()) {
            final IAttributeInstance attributeInstance = baseAttributeMap.getAttributeInstance(entry.getKey());
            if (attributeInstance != null) {
                final AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
                attributeInstance.removeModifier(attributeModifier);
                attributeInstance.applyModifier(new AttributeModifier(attributeModifier.getID(), String.valueOf(this.getName()) + " " + n, this.func_111183_a(n, attributeModifier), attributeModifier.getOperation()));
            }
        }
    }
    
    public double func_111183_a(final int n, final AttributeModifier attributeModifier) {
        return attributeModifier.getAmount() * (n + 1);
    }
}
