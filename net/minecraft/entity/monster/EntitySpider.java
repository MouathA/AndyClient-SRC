package net.minecraft.entity.monster;

import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import java.util.*;

public class EntitySpider extends EntityMob
{
    private static final String __OBFID;
    
    public EntitySpider(final World world) {
        super(world);
        this.setSize(1.4f, 0.9f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new AISpiderAttack(EntityPlayer.class));
        this.tasks.addTask(4, new AISpiderAttack(EntityIronGolem.class));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new AISpiderTarget(EntityPlayer.class));
        this.targetTasks.addTask(3, new AISpiderTarget(EntityIronGolem.class));
    }
    
    @Override
    protected PathNavigate func_175447_b(final World world) {
        return new PathNavigateClimber(this, world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.string;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        super.dropFewItems(b, n);
        if (b && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + n) > 0)) {
            this.dropItem(Items.spider_eye, 1);
        }
    }
    
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    public boolean isPotionApplicable(final PotionEffect potionEffect) {
        return potionEffect.getPotionID() != Potion.poison.id && super.isPotionApplicable(potionEffect);
    }
    
    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setBesideClimbableBlock(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x1);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFE);
        }
        this.dataWatcher.updateObject(16, b2);
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        IEntityLivingData func_180482_a = super.func_180482_a(difficultyInstance, entityLivingData);
        if (this.worldObj.rand.nextInt(100) == 0) {
            final EntitySkeleton entitySkeleton = new EntitySkeleton(this.worldObj);
            entitySkeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entitySkeleton.func_180482_a(difficultyInstance, null);
            this.worldObj.spawnEntityInWorld(entitySkeleton);
            entitySkeleton.mountEntity(this);
        }
        if (func_180482_a == null) {
            func_180482_a = new GroupData();
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1f * difficultyInstance.func_180170_c()) {
                ((GroupData)func_180482_a).func_111104_a(this.worldObj.rand);
            }
        }
        if (func_180482_a instanceof GroupData) {
            final int field_111105_a = ((GroupData)func_180482_a).field_111105_a;
            if (field_111105_a > 0 && Potion.potionTypes[field_111105_a] != null) {
                this.addPotionEffect(new PotionEffect(field_111105_a, Integer.MAX_VALUE));
            }
        }
        return func_180482_a;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.65f;
    }
    
    static {
        __OBFID = "CL_00001699";
    }
    
    class AISpiderAttack extends EntityAIAttackOnCollide
    {
        private static final String __OBFID;
        final EntitySpider this$0;
        
        public AISpiderAttack(final EntitySpider this$0, final Class clazz) {
            super(this.this$0 = this$0, clazz, 1.0, true);
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.attacker.getBrightness(1.0f) >= 0.5f && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            }
            return super.continueExecuting();
        }
        
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
        
        static {
            __OBFID = "CL_00002197";
        }
    }
    
    class AISpiderTarget extends EntityAINearestAttackableTarget
    {
        private static final String __OBFID;
        final EntitySpider this$0;
        
        public AISpiderTarget(final EntitySpider this$0, final Class clazz) {
            super(this.this$0 = this$0, clazz, true);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.taskOwner.getBrightness(1.0f) < 0.5f && super.shouldExecute();
        }
        
        static {
            __OBFID = "CL_00002196";
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int field_111105_a;
        private static final String __OBFID;
        
        public void func_111104_a(final Random random) {
            final int nextInt = random.nextInt(5);
            if (nextInt <= 1) {
                this.field_111105_a = Potion.moveSpeed.id;
            }
            else if (nextInt <= 2) {
                this.field_111105_a = Potion.damageBoost.id;
            }
            else if (nextInt <= 3) {
                this.field_111105_a = Potion.regeneration.id;
            }
            else if (nextInt <= 4) {
                this.field_111105_a = Potion.invisibility.id;
            }
        }
        
        static {
            __OBFID = "CL_00001700";
        }
    }
}
