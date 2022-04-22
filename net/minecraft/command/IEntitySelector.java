package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public final class IEntitySelector
{
    public static final Predicate selectAnything;
    public static final Predicate field_152785_b;
    public static final Predicate selectInventories;
    public static final Predicate field_180132_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002257";
        selectAnything = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180131_a(final Entity entity) {
                return entity.isEntityAlive();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180131_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001541";
            }
        };
        field_152785_b = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180130_a(final Entity entity) {
                return entity.isEntityAlive() && entity.riddenByEntity == null && entity.ridingEntity == null;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180130_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001542";
            }
        };
        selectInventories = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180102_a(final Entity entity) {
                return entity instanceof IInventory && entity.isEntityAlive();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180102_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001867";
            }
        };
        field_180132_d = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_180103_a(final Entity entity) {
                return !(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180103_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002256";
            }
        };
    }
    
    public static class ArmoredMob implements Predicate
    {
        private final ItemStack field_96567_c;
        private static final String __OBFID;
        
        public ArmoredMob(final ItemStack field_96567_c) {
            this.field_96567_c = field_96567_c;
        }
        
        public boolean func_180100_a(final Entity entity) {
            if (!entity.isEntityAlive()) {
                return false;
            }
            if (!(entity instanceof EntityLivingBase)) {
                return false;
            }
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            return entityLivingBase.getEquipmentInSlot(EntityLiving.getArmorPosition(this.field_96567_c)) == null && ((entityLivingBase instanceof EntityLiving) ? ((EntityLiving)entityLivingBase).canPickUpLoot() : (entityLivingBase instanceof EntityArmorStand || entityLivingBase instanceof EntityPlayer));
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.func_180100_a((Entity)o);
        }
        
        static {
            __OBFID = "CL_00001543";
        }
    }
}
