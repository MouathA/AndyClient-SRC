package net.minecraft.util;

import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CombatTracker
{
    private final List combatEntries;
    private final EntityLivingBase fighter;
    private int field_94555_c;
    private int field_152775_d;
    private int field_152776_e;
    private boolean field_94552_d;
    private boolean field_94553_e;
    private String field_94551_f;
    private static final String __OBFID;
    
    public CombatTracker(final EntityLivingBase fighter) {
        this.combatEntries = Lists.newArrayList();
        this.fighter = fighter;
    }
    
    public void func_94545_a() {
        this.func_94542_g();
        if (this.fighter.isOnLadder()) {
            final Block block = this.fighter.worldObj.getBlockState(new BlockPos(this.fighter.posX, this.fighter.getEntityBoundingBox().minY, this.fighter.posZ)).getBlock();
            if (block == Blocks.ladder) {
                this.field_94551_f = "ladder";
            }
            else if (block == Blocks.vine) {
                this.field_94551_f = "vines";
            }
        }
        else if (this.fighter.isInWater()) {
            this.field_94551_f = "water";
        }
    }
    
    public void func_94547_a(final DamageSource damageSource, final float n, final float n2) {
        this.func_94549_h();
        this.func_94545_a();
        final CombatEntry combatEntry = new CombatEntry(damageSource, this.fighter.ticksExisted, n, n2, this.field_94551_f, this.fighter.fallDistance);
        this.combatEntries.add(combatEntry);
        this.field_94555_c = this.fighter.ticksExisted;
        this.field_94553_e = true;
        if (combatEntry.func_94559_f() && !this.field_94552_d && this.fighter.isEntityAlive()) {
            this.field_94552_d = true;
            this.field_152775_d = this.fighter.ticksExisted;
            this.field_152776_e = this.field_152775_d;
            this.fighter.func_152111_bt();
        }
    }
    
    public IChatComponent func_151521_b() {
        if (this.combatEntries.size() == 0) {
            return new ChatComponentTranslation("death.attack.generic", new Object[] { this.fighter.getDisplayName() });
        }
        final CombatEntry func_94544_f = this.func_94544_f();
        final CombatEntry combatEntry = this.combatEntries.get(this.combatEntries.size() - 1);
        final IChatComponent func_151522_h = combatEntry.func_151522_h();
        final Entity entity = combatEntry.getDamageSrc().getEntity();
        IChatComponent deathMessage;
        if (func_94544_f != null && combatEntry.getDamageSrc() == DamageSource.fall) {
            final IChatComponent func_151522_h2 = func_94544_f.func_151522_h();
            if (func_94544_f.getDamageSrc() != DamageSource.fall && func_94544_f.getDamageSrc() != DamageSource.outOfWorld) {
                if (func_151522_h2 != null && (func_151522_h == null || !func_151522_h2.equals(func_151522_h))) {
                    final Entity entity2 = func_94544_f.getDamageSrc().getEntity();
                    final ItemStack itemStack = (entity2 instanceof EntityLivingBase) ? ((EntityLivingBase)entity2).getHeldItem() : null;
                    if (itemStack != null && itemStack.hasDisplayName()) {
                        deathMessage = new ChatComponentTranslation("death.fell.assist.item", new Object[] { this.fighter.getDisplayName(), func_151522_h2, itemStack.getChatComponent() });
                    }
                    else {
                        deathMessage = new ChatComponentTranslation("death.fell.assist", new Object[] { this.fighter.getDisplayName(), func_151522_h2 });
                    }
                }
                else if (func_151522_h != null) {
                    final ItemStack itemStack2 = (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getHeldItem() : null;
                    if (itemStack2 != null && itemStack2.hasDisplayName()) {
                        deathMessage = new ChatComponentTranslation("death.fell.finish.item", new Object[] { this.fighter.getDisplayName(), func_151522_h, itemStack2.getChatComponent() });
                    }
                    else {
                        deathMessage = new ChatComponentTranslation("death.fell.finish", new Object[] { this.fighter.getDisplayName(), func_151522_h });
                    }
                }
                else {
                    deathMessage = new ChatComponentTranslation("death.fell.killer", new Object[] { this.fighter.getDisplayName() });
                }
            }
            else {
                deathMessage = new ChatComponentTranslation("death.fell.accident." + this.func_94548_b(func_94544_f), new Object[] { this.fighter.getDisplayName() });
            }
        }
        else {
            deathMessage = combatEntry.getDamageSrc().getDeathMessage(this.fighter);
        }
        return deathMessage;
    }
    
    public EntityLivingBase func_94550_c() {
        EntityLivingBase entityLivingBase = null;
        EntityLivingBase entityLivingBase2 = null;
        float func_94563_c = 0.0f;
        float func_94563_c2 = 0.0f;
        for (final CombatEntry combatEntry : this.combatEntries) {
            if (combatEntry.getDamageSrc().getEntity() instanceof EntityPlayer && (entityLivingBase2 == null || combatEntry.func_94563_c() > func_94563_c2)) {
                func_94563_c2 = combatEntry.func_94563_c();
                entityLivingBase2 = (EntityPlayer)combatEntry.getDamageSrc().getEntity();
            }
            if (combatEntry.getDamageSrc().getEntity() instanceof EntityLivingBase && (entityLivingBase == null || combatEntry.func_94563_c() > func_94563_c)) {
                func_94563_c = combatEntry.func_94563_c();
                entityLivingBase = (EntityLivingBase)combatEntry.getDamageSrc().getEntity();
            }
        }
        if (entityLivingBase2 != null && func_94563_c2 >= func_94563_c / 3.0f) {
            return entityLivingBase2;
        }
        return entityLivingBase;
    }
    
    private CombatEntry func_94544_f() {
        CombatEntry combatEntry = null;
        CombatEntry combatEntry2 = null;
        float func_94561_i = 0.0f;
        while (0 < this.combatEntries.size()) {
            final CombatEntry combatEntry3 = this.combatEntries.get(0);
            final CombatEntry combatEntry4 = (0 > 0) ? this.combatEntries.get(-1) : null;
            if ((combatEntry3.getDamageSrc() == DamageSource.fall || combatEntry3.getDamageSrc() == DamageSource.outOfWorld) && combatEntry3.func_94561_i() > 0.0f && (combatEntry == null || combatEntry3.func_94561_i() > func_94561_i)) {
                if (0 > 0) {
                    combatEntry = combatEntry4;
                }
                else {
                    combatEntry = combatEntry3;
                }
                func_94561_i = combatEntry3.func_94561_i();
            }
            if (combatEntry3.func_94562_g() != null && (combatEntry2 == null || combatEntry3.func_94563_c() > 0)) {
                combatEntry2 = combatEntry3;
            }
            int n = 0;
            ++n;
        }
        if (func_94561_i > 5.0f && combatEntry != null) {
            return combatEntry;
        }
        if (0 > 5 && combatEntry2 != null) {
            return combatEntry2;
        }
        return null;
    }
    
    private String func_94548_b(final CombatEntry combatEntry) {
        return (combatEntry.func_94562_g() == null) ? "generic" : combatEntry.func_94562_g();
    }
    
    public int func_180134_f() {
        return this.field_94552_d ? (this.fighter.ticksExisted - this.field_152775_d) : (this.field_152776_e - this.field_152775_d);
    }
    
    private void func_94542_g() {
        this.field_94551_f = null;
    }
    
    public void func_94549_h() {
        final int n = this.field_94552_d ? 300 : 100;
        if (this.field_94553_e && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.field_94555_c > n)) {
            final boolean field_94552_d = this.field_94552_d;
            this.field_94553_e = false;
            this.field_94552_d = false;
            this.field_152776_e = this.fighter.ticksExisted;
            if (field_94552_d) {
                this.fighter.func_152112_bu();
            }
            this.combatEntries.clear();
        }
    }
    
    public EntityLivingBase func_180135_h() {
        return this.fighter;
    }
    
    static {
        __OBFID = "CL_00001520";
    }
}
