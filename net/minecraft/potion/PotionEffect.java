package net.minecraft.potion;

import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class PotionEffect
{
    private static final Logger LOGGER;
    private int potionID;
    private int duration;
    private int amplifier;
    private boolean isSplashPotion;
    private boolean isAmbient;
    private boolean isPotionDurationMax;
    private boolean showParticles;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001529";
        LOGGER = LogManager.getLogger();
    }
    
    public PotionEffect(final int n, final int n2) {
        this(n, n2, 0);
    }
    
    public PotionEffect(final int n, final int n2, final int n3) {
        this(n, n2, n3, false, true);
    }
    
    public PotionEffect(final int potionID, final int duration, final int amplifier, final boolean isAmbient, final boolean showParticles) {
        this.potionID = potionID;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isAmbient = isAmbient;
        this.showParticles = showParticles;
    }
    
    public PotionEffect(final PotionEffect potionEffect) {
        this.potionID = potionEffect.potionID;
        this.duration = potionEffect.duration;
        this.amplifier = potionEffect.amplifier;
        this.isAmbient = potionEffect.isAmbient;
        this.showParticles = potionEffect.showParticles;
    }
    
    public void combine(final PotionEffect potionEffect) {
        if (this.potionID != potionEffect.potionID) {
            PotionEffect.LOGGER.warn("This method should only be called for matching effects!");
        }
        if (potionEffect.amplifier > this.amplifier) {
            this.amplifier = potionEffect.amplifier;
            this.duration = potionEffect.duration;
        }
        else if (potionEffect.amplifier == this.amplifier && this.duration < potionEffect.duration) {
            this.duration = potionEffect.duration;
        }
        else if (!potionEffect.isAmbient && this.isAmbient) {
            this.isAmbient = potionEffect.isAmbient;
        }
        this.showParticles = potionEffect.showParticles;
    }
    
    public int getPotionID() {
        return this.potionID;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public int getAmplifier() {
        return this.amplifier;
    }
    
    public void setSplashPotion(final boolean isSplashPotion) {
        this.isSplashPotion = isSplashPotion;
    }
    
    public boolean getIsAmbient() {
        return this.isAmbient;
    }
    
    public boolean func_180154_f() {
        return this.showParticles;
    }
    
    public boolean onUpdate(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
                this.performEffect(entityLivingBase);
            }
            this.deincrementDuration();
        }
        return this.duration > 0;
    }
    
    private int deincrementDuration() {
        return --this.duration;
    }
    
    public void performEffect(final EntityLivingBase entityLivingBase) {
        if (this.duration > 0) {
            Potion.potionTypes[this.potionID].performEffect(entityLivingBase, this.amplifier);
        }
    }
    
    public String getEffectName() {
        return Potion.potionTypes[this.potionID].getName();
    }
    
    @Override
    public int hashCode() {
        return this.potionID;
    }
    
    @Override
    public String toString() {
        String s;
        if (this.getAmplifier() > 0) {
            s = String.valueOf(this.getEffectName()) + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else {
            s = String.valueOf(this.getEffectName()) + ", Duration: " + this.getDuration();
        }
        if (this.isSplashPotion) {
            s = String.valueOf(s) + ", Splash: true";
        }
        if (!this.showParticles) {
            s = String.valueOf(s) + ", Particles: false";
        }
        return Potion.potionTypes[this.potionID].isUsable() ? ("(" + s + ")") : s;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PotionEffect)) {
            return false;
        }
        final PotionEffect potionEffect = (PotionEffect)o;
        return this.potionID == potionEffect.potionID && this.amplifier == potionEffect.amplifier && this.duration == potionEffect.duration && this.isSplashPotion == potionEffect.isSplashPotion && this.isAmbient == potionEffect.isAmbient;
    }
    
    public NBTTagCompound writeCustomPotionEffectToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Id", (byte)this.getPotionID());
        nbtTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        nbtTagCompound.setInteger("Duration", this.getDuration());
        nbtTagCompound.setBoolean("Ambient", this.getIsAmbient());
        nbtTagCompound.setBoolean("ShowParticles", this.func_180154_f());
        return nbtTagCompound;
    }
    
    public static PotionEffect readCustomPotionEffectFromNBT(final NBTTagCompound nbtTagCompound) {
        final byte byte1 = nbtTagCompound.getByte("Id");
        if (byte1 >= 0 && byte1 < Potion.potionTypes.length && Potion.potionTypes[byte1] != null) {
            final byte byte2 = nbtTagCompound.getByte("Amplifier");
            final int integer = nbtTagCompound.getInteger("Duration");
            final boolean boolean1 = nbtTagCompound.getBoolean("Ambient");
            if (nbtTagCompound.hasKey("ShowParticles", 1)) {
                nbtTagCompound.getBoolean("ShowParticles");
            }
            return new PotionEffect(byte1, integer, byte2, boolean1, true);
        }
        return null;
    }
    
    public void setPotionDurationMax(final boolean isPotionDurationMax) {
        this.isPotionDurationMax = isPotionDurationMax;
    }
    
    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }
}
