package net.minecraft.util;

import net.minecraft.entity.*;

public class CombatEntry
{
    private final DamageSource damageSrc;
    private final int field_94567_b;
    private final float field_94568_c;
    private final float field_94565_d;
    private final String field_94566_e;
    private final float field_94564_f;
    private static final String __OBFID;
    
    public CombatEntry(final DamageSource damageSrc, final int field_94567_b, final float field_94565_d, final float field_94568_c, final String field_94566_e, final float field_94564_f) {
        this.damageSrc = damageSrc;
        this.field_94567_b = field_94567_b;
        this.field_94568_c = field_94568_c;
        this.field_94565_d = field_94565_d;
        this.field_94566_e = field_94566_e;
        this.field_94564_f = field_94564_f;
    }
    
    public DamageSource getDamageSrc() {
        return this.damageSrc;
    }
    
    public float func_94563_c() {
        return this.field_94568_c;
    }
    
    public boolean func_94559_f() {
        return this.damageSrc.getEntity() instanceof EntityLivingBase;
    }
    
    public String func_94562_g() {
        return this.field_94566_e;
    }
    
    public IChatComponent func_151522_h() {
        return (this.getDamageSrc().getEntity() == null) ? null : this.getDamageSrc().getEntity().getDisplayName();
    }
    
    public float func_94561_i() {
        return (this.damageSrc == DamageSource.outOfWorld) ? Float.MAX_VALUE : this.field_94564_f;
    }
    
    static {
        __OBFID = "CL_00001519";
    }
}
