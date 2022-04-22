package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class EntityDamageSource extends DamageSource
{
    protected Entity damageSourceEntity;
    private boolean field_180140_r;
    private static final String __OBFID;
    
    public EntityDamageSource(final String s, final Entity damageSourceEntity) {
        super(s);
        this.field_180140_r = false;
        this.damageSourceEntity = damageSourceEntity;
    }
    
    public EntityDamageSource func_180138_v() {
        this.field_180140_r = true;
        return this;
    }
    
    public boolean func_180139_w() {
        return this.field_180140_r;
    }
    
    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }
    
    @Override
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final ItemStack itemStack = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
        final String string = "death.attack." + this.damageType;
        final String string2 = String.valueOf(string) + ".item";
        return (itemStack != null && itemStack.hasDisplayName() && StatCollector.canTranslate(string2)) ? new ChatComponentTranslation(string2, new Object[] { entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemStack.getChatComponent() }) : new ChatComponentTranslation(string, new Object[] { entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName() });
    }
    
    @Override
    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
    }
    
    static {
        __OBFID = "CL_00001522";
    }
}
