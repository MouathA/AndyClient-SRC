package net.minecraft.entity.monster;

import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityPigZombie extends EntityZombie
{
    private static final UUID field_110189_bq;
    private static final AttributeModifier field_110190_br;
    private int angerLevel;
    private int randomSoundDelay;
    private UUID field_175459_bn;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001693";
        field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        field_110190_br = new AttributeModifier(EntityPigZombie.field_110189_bq, "Attacking speed boost", 0.05, 0).setSaved(false);
    }
    
    public EntityPigZombie(final World world) {
        super(world);
        this.isImmuneToFire = true;
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (revengeTarget != null) {
            this.field_175459_bn = revengeTarget.getUniqueID();
        }
    }
    
    @Override
    protected void func_175456_n() {
        this.targetTasks.addTask(1, new AIHurtByAggressor());
        this.targetTasks.addTask(2, new AITargetAggressor());
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(EntityPigZombie.field_110186_bp).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    protected void updateAITasks() {
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (this > 0) {
            if (!this.isChild() && !entityAttribute.func_180374_a(EntityPigZombie.field_110190_br)) {
                entityAttribute.applyModifier(EntityPigZombie.field_110190_br);
            }
            --this.angerLevel;
        }
        else if (entityAttribute.func_180374_a(EntityPigZombie.field_110190_br)) {
            entityAttribute.removeModifier(EntityPigZombie.field_110190_br);
        }
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        if (this.angerLevel > 0 && this.field_175459_bn != null && this.getAITarget() == null) {
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.field_175459_bn);
            this.setRevengeTarget(playerEntityByUUID);
            this.attackingPlayer = playerEntityByUUID;
            this.recentlyHit = this.getRevengeTimer();
        }
        super.updateAITasks();
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public boolean handleLavaMovement() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setShort("Anger", (short)this.angerLevel);
        if (this.field_175459_bn != null) {
            nbtTagCompound.setString("HurtBy", this.field_175459_bn.toString());
        }
        else {
            nbtTagCompound.setString("HurtBy", "");
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.angerLevel = nbtTagCompound.getShort("Anger");
        final String string = nbtTagCompound.getString("HurtBy");
        if (string.length() > 0) {
            this.field_175459_bn = UUID.fromString(string);
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.field_175459_bn);
            this.setRevengeTarget(playerEntityByUUID);
            if (playerEntityByUUID != null) {
                this.attackingPlayer = playerEntityByUUID;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityPlayer) {
            this.becomeAngryAt(entity);
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    private void becomeAngryAt(final Entity entity) {
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof EntityLivingBase) {
            this.setRevengeTarget((EntityLivingBase)entity);
        }
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2 + n)) {
            this.dropItem(Items.rotten_flesh, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(2 + n)) {
            this.dropItem(Items.gold_nugget, 1);
            ++n2;
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    @Override
    protected void addRandomArmor() {
        this.dropItem(Items.gold_ingot, 1);
    }
    
    @Override
    protected void func_180481_a(final DifficultyInstance difficultyInstance) {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        super.func_180482_a(difficultyInstance, entityLivingData);
        this.setVillager(false);
        return entityLivingData;
    }
    
    static void access$0(final EntityPigZombie entityPigZombie, final Entity entity) {
        entityPigZombie.becomeAngryAt(entity);
    }
    
    class AIHurtByAggressor extends EntityAIHurtByTarget
    {
        private static final String __OBFID;
        final EntityPigZombie this$0;
        
        public AIHurtByAggressor(final EntityPigZombie this$0) {
            super(this.this$0 = this$0, true, new Class[0]);
        }
        
        @Override
        protected void func_179446_a(final EntityCreature entityCreature, final EntityLivingBase entityLivingBase) {
            super.func_179446_a(entityCreature, entityLivingBase);
            if (entityCreature instanceof EntityPigZombie) {
                EntityPigZombie.access$0((EntityPigZombie)entityCreature, entityLivingBase);
            }
        }
        
        static {
            __OBFID = "CL_00002206";
        }
    }
    
    class AITargetAggressor extends EntityAINearestAttackableTarget
    {
        private static final String __OBFID;
        final EntityPigZombie this$0;
        
        public AITargetAggressor(final EntityPigZombie this$0) {
            super(this.this$0 = this$0, EntityPlayer.class, true);
        }
        
        @Override
        public boolean shouldExecute() {
            return ((EntityPigZombie)this.taskOwner).func_175457_ck() && super.shouldExecute();
        }
        
        static {
            __OBFID = "CL_00002207";
        }
    }
}
