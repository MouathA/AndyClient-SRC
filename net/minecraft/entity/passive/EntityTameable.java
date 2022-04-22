package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;
import java.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable
{
    protected EntityAISit aiSit;
    private static final String __OBFID;
    
    public EntityTameable(final World world) {
        super(world);
        this.aiSit = new EntityAISit(this);
        this.func_175544_ck();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, "");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.func_152113_b() == null) {
            nbtTagCompound.setString("OwnerUUID", "");
        }
        else {
            nbtTagCompound.setString("OwnerUUID", this.func_152113_b());
        }
        nbtTagCompound.setBoolean("Sitting", this.isSitting());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        String s;
        if (nbtTagCompound.hasKey("OwnerUUID", 8)) {
            s = nbtTagCompound.getString("OwnerUUID");
        }
        else {
            s = PreYggdrasilConverter.func_152719_a(nbtTagCompound.getString("Owner"));
        }
        if (s.length() > 0) {
            this.func_152115_b(s);
            this.setTamed(true);
        }
        this.aiSit.setSitting(nbtTagCompound.getBoolean("Sitting"));
        this.setSitting(nbtTagCompound.getBoolean("Sitting"));
    }
    
    protected void playTameEffect(final boolean b) {
        EnumParticleTypes enumParticleTypes = EnumParticleTypes.HEART;
        if (!b) {
            enumParticleTypes = EnumParticleTypes.SMOKE_NORMAL;
        }
        while (0 < 7) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 7) {
            this.playTameEffect(true);
        }
        else if (b == 6) {
            this.playTameEffect(false);
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public boolean isTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0x0;
    }
    
    public void setTamed(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x4));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFB));
        }
        this.func_175544_ck();
    }
    
    protected void func_175544_ck() {
    }
    
    public boolean isSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setSitting(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFE));
        }
    }
    
    @Override
    public String func_152113_b() {
        return this.dataWatcher.getWatchableObjectString(17);
    }
    
    public void func_152115_b(final String s) {
        this.dataWatcher.updateObject(17, s);
    }
    
    public EntityLivingBase func_180492_cm() {
        final UUID fromString = UUID.fromString(this.func_152113_b());
        return (fromString == null) ? null : this.worldObj.getPlayerEntityByUUID(fromString);
    }
    
    public boolean func_152114_e(final EntityLivingBase entityLivingBase) {
        return entityLivingBase == this.func_180492_cm();
    }
    
    public EntityAISit getAISit() {
        return this.aiSit;
    }
    
    public boolean func_142018_a(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        return true;
    }
    
    @Override
    public Team getTeam() {
        if (this.isTamed()) {
            final EntityLivingBase func_180492_cm = this.func_180492_cm();
            if (func_180492_cm != null) {
                return func_180492_cm.getTeam();
            }
        }
        return super.getTeam();
    }
    
    @Override
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        if (this.isTamed()) {
            final EntityLivingBase func_180492_cm = this.func_180492_cm();
            if (entityLivingBase == func_180492_cm) {
                return true;
            }
            if (func_180492_cm != null) {
                return func_180492_cm.isOnSameTeam(entityLivingBase);
            }
        }
        return super.isOnSameTeam(entityLivingBase);
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages") && this.hasCustomName() && this.func_180492_cm() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.func_180492_cm()).addChatMessage(this.getCombatTracker().func_151521_b());
        }
        super.onDeath(damageSource);
    }
    
    @Override
    public Entity getOwner() {
        return this.func_180492_cm();
    }
    
    static {
        __OBFID = "CL_00001561";
    }
}
