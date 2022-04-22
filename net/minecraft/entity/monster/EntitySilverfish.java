package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class EntitySilverfish extends EntityMob
{
    private AISummonSilverfish field_175460_b;
    private static final String __OBFID;
    
    public EntitySilverfish(final World world) {
        super(world);
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, this.field_175460_b = new AISummonSilverfish());
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(5, new AIHideInStone());
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    public float getEyeHeight() {
        return 0.1f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (damageSource instanceof EntityDamageSource || damageSource == DamageSource.magic) {
            this.field_175460_b.func_179462_f();
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    @Override
    public float func_180484_a(final BlockPos blockPos) {
        return (this.worldObj.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.stone) ? 10.0f : super.func_180484_a(blockPos);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.getClosestPlayerToEntity(this, 5.0) == null;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    static {
        __OBFID = "CL_00001696";
    }
    
    class AIHideInStone extends EntityAIWander
    {
        private EnumFacing field_179483_b;
        private boolean field_179484_c;
        private static final String __OBFID;
        final EntitySilverfish this$0;
        
        public AIHideInStone(final EntitySilverfish this$0) {
            super(this.this$0 = this$0, 1.0, 10);
            this.setMutexBits(1);
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.this$0.getAttackTarget() != null) {
                return false;
            }
            if (!this.this$0.getNavigator().noPath()) {
                return false;
            }
            final Random rng = this.this$0.getRNG();
            if (rng.nextInt(10) == 0) {
                this.field_179483_b = EnumFacing.random(rng);
                if (BlockSilverfish.func_176377_d(this.this$0.worldObj.getBlockState(new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ).offset(this.field_179483_b)))) {
                    return this.field_179484_c = true;
                }
            }
            this.field_179484_c = false;
            return super.shouldExecute();
        }
        
        @Override
        public boolean continueExecuting() {
            return !this.field_179484_c && super.continueExecuting();
        }
        
        @Override
        public void startExecuting() {
            if (!this.field_179484_c) {
                super.startExecuting();
            }
            else {
                final World worldObj = this.this$0.worldObj;
                final BlockPos offset = new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ).offset(this.field_179483_b);
                final IBlockState blockState = worldObj.getBlockState(offset);
                if (BlockSilverfish.func_176377_d(blockState)) {
                    worldObj.setBlockState(offset, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.func_176878_a(blockState)), 3);
                    this.this$0.spawnExplosionParticle();
                    this.this$0.setDead();
                }
            }
        }
        
        static {
            __OBFID = "CL_00002205";
        }
    }
    
    class AISummonSilverfish extends EntityAIBase
    {
        private EntitySilverfish field_179464_a;
        private int field_179463_b;
        private static final String __OBFID;
        final EntitySilverfish this$0;
        
        AISummonSilverfish(final EntitySilverfish entitySilverfish) {
            this.this$0 = entitySilverfish;
            this.field_179464_a = entitySilverfish;
        }
        
        public void func_179462_f() {
            if (this.field_179463_b == 0) {
                this.field_179463_b = 20;
            }
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179463_b > 0;
        }
        
        @Override
        public void updateTask() {
            --this.field_179463_b;
            if (this.field_179463_b <= 0) {
                final World worldObj = this.field_179464_a.worldObj;
                final Random rng = this.field_179464_a.getRNG();
                final BlockPos blockPos = new BlockPos(this.field_179464_a);
                while (0 <= 5 && 0 >= -5) {
                    while (0 <= 10 && 0 >= -10) {
                        while (0 <= 10 && 0 >= -10) {
                            final BlockPos add = blockPos.add(0, 0, 0);
                            final IBlockState blockState = worldObj.getBlockState(add);
                            if (blockState.getBlock() == Blocks.monster_egg) {
                                if (worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                                    worldObj.destroyBlock(add, true);
                                }
                                else {
                                    worldObj.setBlockState(add, ((BlockSilverfish.EnumType)blockState.getValue(BlockSilverfish.VARIANT_PROP)).func_176883_d(), 3);
                                }
                                if (rng.nextBoolean()) {
                                    return;
                                }
                            }
                            final boolean b = 0 <= 0;
                        }
                        final boolean b2 = 0 <= 0;
                    }
                    final boolean b3 = 0 <= 0;
                }
            }
        }
        
        static {
            __OBFID = "CL_00002204";
        }
    }
}
