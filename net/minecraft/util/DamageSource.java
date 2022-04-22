package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;

public class DamageSource
{
    public static DamageSource inFire;
    public static DamageSource field_180137_b;
    public static DamageSource onFire;
    public static DamageSource lava;
    public static DamageSource inWall;
    public static DamageSource drown;
    public static DamageSource starve;
    public static DamageSource cactus;
    public static DamageSource fall;
    public static DamageSource outOfWorld;
    public static DamageSource generic;
    public static DamageSource magic;
    public static DamageSource wither;
    public static DamageSource anvil;
    public static DamageSource fallingBlock;
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private boolean damageIsAbsolute;
    private float hungerDamage;
    private boolean fireDamage;
    private boolean projectile;
    private boolean difficultyScaled;
    private boolean magicDamage;
    private boolean explosion;
    public String damageType;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001521";
        DamageSource.inFire = new DamageSource("inFire").setFireDamage();
        DamageSource.field_180137_b = new DamageSource("lightningBolt");
        DamageSource.onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
        DamageSource.lava = new DamageSource("lava").setFireDamage();
        DamageSource.inWall = new DamageSource("inWall").setDamageBypassesArmor();
        DamageSource.drown = new DamageSource("drown").setDamageBypassesArmor();
        DamageSource.starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
        DamageSource.cactus = new DamageSource("cactus");
        DamageSource.fall = new DamageSource("fall").setDamageBypassesArmor();
        DamageSource.outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
        DamageSource.generic = new DamageSource("generic").setDamageBypassesArmor();
        DamageSource.magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
        DamageSource.wither = new DamageSource("wither").setDamageBypassesArmor();
        DamageSource.anvil = new DamageSource("anvil");
        DamageSource.fallingBlock = new DamageSource("fallingBlock");
    }
    
    public static DamageSource causeMobDamage(final EntityLivingBase entityLivingBase) {
        return new EntityDamageSource("mob", entityLivingBase);
    }
    
    public static DamageSource causePlayerDamage(final EntityPlayer entityPlayer) {
        return new EntityDamageSource("player", entityPlayer);
    }
    
    public static DamageSource causeArrowDamage(final EntityArrow entityArrow, final Entity entity) {
        return new EntityDamageSourceIndirect("arrow", entityArrow, entity).setProjectile();
    }
    
    public static DamageSource causeFireballDamage(final EntityFireball entityFireball, final Entity entity) {
        return (entity == null) ? new EntityDamageSourceIndirect("onFire", entityFireball, entityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", entityFireball, entity).setFireDamage().setProjectile();
    }
    
    public static DamageSource causeThrownDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("thrown", entity, entity2).setProjectile();
    }
    
    public static DamageSource causeIndirectMagicDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("indirectMagic", entity, entity2).setDamageBypassesArmor().setMagicDamage();
    }
    
    public static DamageSource causeThornsDamage(final Entity entity) {
        return new EntityDamageSource("thorns", entity).func_180138_v().setMagicDamage();
    }
    
    public static DamageSource setExplosionSource(final Explosion explosion) {
        return (explosion != null && explosion.getExplosivePlacedBy() != null) ? new EntityDamageSource("explosion.player", explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }
    
    public boolean isProjectile() {
        return this.projectile;
    }
    
    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }
    
    public boolean isExplosion() {
        return this.explosion;
    }
    
    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }
    
    public boolean isUnblockable() {
        return this.isUnblockable;
    }
    
    public float getHungerDamage() {
        return this.hungerDamage;
    }
    
    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }
    
    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }
    
    protected DamageSource(final String damageType) {
        this.hungerDamage = 0.3f;
        this.damageType = damageType;
    }
    
    public Entity getSourceOfDamage() {
        return this.getEntity();
    }
    
    public Entity getEntity() {
        return null;
    }
    
    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0f;
        return this;
    }
    
    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }
    
    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = true;
        this.hungerDamage = 0.0f;
        return this;
    }
    
    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }
    
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final EntityLivingBase func_94060_bK = entityLivingBase.func_94060_bK();
        final String string = "death.attack." + this.damageType;
        final String string2 = String.valueOf(string) + ".player";
        return (func_94060_bK != null && StatCollector.canTranslate(string2)) ? new ChatComponentTranslation(string2, new Object[] { entityLivingBase.getDisplayName(), func_94060_bK.getDisplayName() }) : new ChatComponentTranslation(string, new Object[] { entityLivingBase.getDisplayName() });
    }
    
    public boolean isFireDamage() {
        return this.fireDamage;
    }
    
    public String getDamageType() {
        return this.damageType;
    }
    
    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }
    
    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }
    
    public boolean isMagicDamage() {
        return this.magicDamage;
    }
    
    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }
    
    public boolean func_180136_u() {
        final Entity entity = this.getEntity();
        return entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode;
    }
}
