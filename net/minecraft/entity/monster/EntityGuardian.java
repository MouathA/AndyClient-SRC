package net.minecraft.entity.monster;

import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.nbt.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;

public class EntityGuardian extends EntityMob
{
    private float field_175482_b;
    private float field_175484_c;
    private float field_175483_bk;
    private float field_175485_bl;
    private float field_175486_bm;
    private EntityLivingBase field_175478_bn;
    private int field_175479_bo;
    private boolean field_175480_bp;
    private EntityAIWander field_175481_bq;
    private static final String __OBFID;
    
    public EntityGuardian(final World world) {
        super(world);
        this.experienceValue = 10;
        this.setSize(0.85f, 0.85f);
        this.tasks.addTask(4, new AIGuardianAttack());
        final EntityAIMoveTowardsRestriction entityAIMoveTowardsRestriction;
        this.tasks.addTask(5, entityAIMoveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, this.field_175481_bq = new EntityAIWander(this, 1.0, 80));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0f, 0.01f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.field_175481_bq.setMutexBits(3);
        entityAIMoveTowardsRestriction.setMutexBits(3);
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector()));
        this.moveHelper = new GuardianMoveHelper();
        final float nextFloat = this.rand.nextFloat();
        this.field_175482_b = nextFloat;
        this.field_175484_c = nextFloat;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.func_175467_a(nbtTagCompound.getBoolean("Elder"));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Elder", this.func_175461_cl());
    }
    
    @Override
    protected PathNavigate func_175447_b(final World world) {
        return new PathNavigateSwimmer(this, world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0);
    }
    
    private boolean func_175468_a(final int n) {
        return (this.dataWatcher.getWatchableObjectInt(16) & n) != 0x0;
    }
    
    private void func_175473_a(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(16);
        if (b) {
            this.dataWatcher.updateObject(16, watchableObjectInt | n);
        }
        else {
            this.dataWatcher.updateObject(16, watchableObjectInt & ~n);
        }
    }
    
    public boolean func_175472_n() {
        return this.func_175468_a(2);
    }
    
    private void func_175476_l(final boolean b) {
        this.func_175473_a(2, b);
    }
    
    public int func_175464_ck() {
        return this.func_175461_cl() ? 60 : 80;
    }
    
    public boolean func_175461_cl() {
        return this.func_175468_a(4);
    }
    
    public void func_175467_a(final boolean b) {
        this.func_175473_a(4, b);
        if (b) {
            this.setSize(1.9975f, 1.9975f);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
            this.enablePersistence();
            this.field_175481_bq.func_179479_b(400);
        }
    }
    
    public void func_175465_cm() {
        this.func_175467_a(true);
        final float n = 1.0f;
        this.field_175485_bl = n;
        this.field_175486_bm = n;
    }
    
    private void func_175463_b(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    public EntityLivingBase func_175466_co() {
        if (this != 0) {
            return null;
        }
        if (!this.worldObj.isRemote) {
            return this.getAttackTarget();
        }
        if (this.field_175478_bn != null) {
            return this.field_175478_bn;
        }
        final Entity entityByID = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
        if (entityByID instanceof EntityLivingBase) {
            return this.field_175478_bn = (EntityLivingBase)entityByID;
        }
        return null;
    }
    
    @Override
    public void func_145781_i(final int n) {
        super.func_145781_i(n);
        if (n == 16) {
            if (this.func_175461_cl() && this.width < 1.0f) {
                this.setSize(1.9975f, 1.9975f);
            }
        }
        else if (n == 17) {
            this.field_175479_bo = 0;
            this.field_175478_bn = null;
        }
    }
    
    @Override
    public int getTalkInterval() {
        return 160;
    }
    
    @Override
    protected String getLivingSound() {
        return this.isInWater() ? (this.func_175461_cl() ? "mob.guardian.elder.idle" : "mob.guardian.idle") : "mob.guardian.land.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return this.isInWater() ? (this.func_175461_cl() ? "mob.guardian.elder.hit" : "mob.guardian.hit") : "mob.guardian.land.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return this.isInWater() ? (this.func_175461_cl() ? "mob.guardian.elder.death" : "mob.guardian.death") : "mob.guardian.land.death";
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    @Override
    public float func_180484_a(final BlockPos blockPos) {
        return (this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.water) ? (10.0f + this.worldObj.getLightBrightness(blockPos) - 0.5f) : super.func_180484_a(blockPos);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.field_175484_c = this.field_175482_b;
            if (!this.isInWater()) {
                this.field_175483_bk = 2.0f;
                if (this.motionY > 0.0 && this.field_175480_bp && !this.isSlient()) {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0f, 1.0f, false);
                }
                this.field_175480_bp = (this.motionY < 0.0 && this.worldObj.func_175677_d(new BlockPos(this).offsetDown(), false));
            }
            else if (this.func_175472_n()) {
                if (this.field_175483_bk < 0.5f) {
                    this.field_175483_bk = 4.0f;
                }
                else {
                    this.field_175483_bk += (0.5f - this.field_175483_bk) * 0.1f;
                }
            }
            else {
                this.field_175483_bk += (0.125f - this.field_175483_bk) * 0.2f;
            }
            this.field_175482_b += this.field_175483_bk;
            this.field_175486_bm = this.field_175485_bl;
            if (!this.isInWater()) {
                this.field_175485_bl = this.rand.nextFloat();
            }
            else if (this.func_175472_n()) {
                this.field_175485_bl += (0.0f - this.field_175485_bl) * 0.25f;
            }
            else {
                this.field_175485_bl += (1.0f - this.field_175485_bl) * 0.06f;
            }
            if (this.func_175472_n() && this.isInWater()) {
                this.getLook(0.0f);
            }
            if (this != 0) {
                if (this.field_175479_bo < this.func_175464_ck()) {
                    ++this.field_175479_bo;
                }
                final EntityLivingBase func_175466_co = this.func_175466_co();
                if (func_175466_co != null) {
                    this.getLookHelper().setLookPositionWithEntity(func_175466_co, 90.0f, 90.0f);
                    this.getLookHelper().onUpdateLook();
                    final double n = this.func_175477_p(0.0f);
                    final double n2 = func_175466_co.posX - this.posX;
                    final double n3 = func_175466_co.posY + func_175466_co.height * 0.5f - (this.posY + this.getEyeHeight());
                    final double n4 = func_175466_co.posZ - this.posZ;
                    final double sqrt = Math.sqrt(n2 * n2 + n3 * n3 + n4 * n4);
                    final double n5 = n2 / sqrt;
                    final double n6 = n3 / sqrt;
                    final double n7 = n4 / sqrt;
                    double nextDouble = this.rand.nextDouble();
                    while (nextDouble < sqrt) {
                        nextDouble += 1.8 - n + this.rand.nextDouble() * (1.7 - n);
                        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + n5 * nextDouble, this.posY + n6 * nextDouble + this.getEyeHeight(), this.posZ + n7 * nextDouble, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
            }
        }
        if (this.inWater) {
            this.setAir(300);
        }
        else if (this.onGround) {
            this.motionY += 0.5;
            this.motionX += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.motionZ += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.rotationYaw = this.rand.nextFloat() * 360.0f;
            this.onGround = false;
            this.isAirBorne = true;
        }
        if (this != 0) {
            this.rotationYaw = this.rotationYawHead;
        }
        super.onLivingUpdate();
    }
    
    public float func_175471_a(final float n) {
        return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * n;
    }
    
    public float func_175469_o(final float n) {
        return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * n;
    }
    
    public float func_175477_p(final float n) {
        return (this.field_175479_bo + n) / this.func_175464_ck();
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.func_175461_cl()) {
            if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
                final Potion digSlowdown = Potion.digSlowdown;
                for (final EntityPlayerMP entityPlayerMP : this.worldObj.func_175661_b(EntityPlayerMP.class, new Predicate() {
                    private static final String __OBFID;
                    final EntityGuardian this$0;
                    
                    public boolean func_179913_a(final EntityPlayerMP entityPlayerMP) {
                        return this.this$0.getDistanceSqToEntity(entityPlayerMP) < 2500.0 && entityPlayerMP.theItemInWorldManager.func_180239_c();
                    }
                    
                    @Override
                    public boolean apply(final Object o) {
                        return this.func_179913_a((EntityPlayerMP)o);
                    }
                    
                    static {
                        __OBFID = "CL_00002212";
                    }
                })) {
                    if (!entityPlayerMP.isPotionActive(digSlowdown) || entityPlayerMP.getActivePotionEffect(digSlowdown).getAmplifier() < 2 || entityPlayerMP.getActivePotionEffect(digSlowdown).getDuration() < 1200) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0f));
                        entityPlayerMP.addPotionEffect(new PotionEffect(digSlowdown.id, 6000, 2));
                    }
                }
            }
            if (!this.hasHome()) {
                this.func_175449_a(new BlockPos(this), 16);
            }
        }
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt(3) + this.rand.nextInt(n + 1);
        if (n2 > 0) {
            this.entityDropItem(new ItemStack(Items.prismarine_shard, n2, 0), 1.0f);
        }
        if (this.rand.nextInt(3 + n) > 1) {
            this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 1.0f);
        }
        else if (this.rand.nextInt(3 + n) > 1) {
            this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0f);
        }
        if (b && this.func_175461_cl()) {
            this.entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0f);
        }
    }
    
    @Override
    protected void addRandomArmor() {
        this.entityDropItem(((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand), 1.0f);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public boolean handleLavaMovement() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return (this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (!this.func_175472_n() && !damageSource.isMagicDamage() && damageSource.getSourceOfDamage() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)damageSource.getSourceOfDamage();
            if (!damageSource.isExplosion()) {
                entityLivingBase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0f);
                entityLivingBase.playSound("damage.thorns", 0.5f, 1.0f);
            }
        }
        this.field_175481_bq.func_179480_f();
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 180;
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveFlying(n, n2, 0.1f);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421;
                this.motionY *= 0.8999999761581421;
                this.motionZ *= 0.8999999761581421;
                if (!this.func_175472_n() && this.getAttackTarget() == null) {
                    this.motionY -= 0.005;
                }
            }
            else {
                super.moveEntityWithHeading(n, n2);
            }
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
    }
    
    static void access$0(final EntityGuardian entityGuardian, final int n) {
        entityGuardian.func_175463_b(n);
    }
    
    static EntityAIWander access$1(final EntityGuardian entityGuardian) {
        return entityGuardian.field_175481_bq;
    }
    
    static void access$2(final EntityGuardian entityGuardian, final boolean b) {
        entityGuardian.func_175476_l(b);
    }
    
    static {
        __OBFID = "CL_00002213";
    }
    
    class AIGuardianAttack extends EntityAIBase
    {
        private EntityGuardian field_179456_a;
        private int field_179455_b;
        private static final String __OBFID;
        final EntityGuardian this$0;
        
        public AIGuardianAttack(final EntityGuardian entityGuardian) {
            this.this$0 = entityGuardian;
            this.field_179456_a = entityGuardian;
            this.setMutexBits(3);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.field_179456_a.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }
        
        @Override
        public boolean continueExecuting() {
            return super.continueExecuting() && (this.field_179456_a.func_175461_cl() || this.field_179456_a.getDistanceSqToEntity(this.field_179456_a.getAttackTarget()) > 9.0);
        }
        
        @Override
        public void startExecuting() {
            this.field_179455_b = -10;
            this.field_179456_a.getNavigator().clearPathEntity();
            this.field_179456_a.getLookHelper().setLookPositionWithEntity(this.field_179456_a.getAttackTarget(), 90.0f, 90.0f);
            this.field_179456_a.isAirBorne = true;
        }
        
        @Override
        public void resetTask() {
            EntityGuardian.access$0(this.field_179456_a, 0);
            this.field_179456_a.setAttackTarget(null);
            EntityGuardian.access$1(this.field_179456_a).func_179480_f();
        }
        
        @Override
        public void updateTask() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //     4: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
            //     7: astore_1       
            //     8: aload_0        
            //     9: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    12: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getNavigator:()Lnet/minecraft/pathfinding/PathNavigate;
            //    15: invokevirtual   net/minecraft/pathfinding/PathNavigate.clearPathEntity:()V
            //    18: aload_0        
            //    19: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    22: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getLookHelper:()Lnet/minecraft/entity/ai/EntityLookHelper;
            //    25: aload_1        
            //    26: ldc             90.0
            //    28: ldc             90.0
            //    30: invokevirtual   net/minecraft/entity/ai/EntityLookHelper.setLookPositionWithEntity:(Lnet/minecraft/entity/Entity;FF)V
            //    33: aload_0        
            //    34: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    37: aload_1        
            //    38: invokevirtual   net/minecraft/entity/monster/EntityGuardian.canEntityBeSeen:(Lnet/minecraft/entity/Entity;)Z
            //    41: ifne            55
            //    44: aload_0        
            //    45: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    48: aconst_null    
            //    49: invokevirtual   net/minecraft/entity/monster/EntityGuardian.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
            //    52: goto            234
            //    55: aload_0        
            //    56: dup            
            //    57: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //    60: iconst_1       
            //    61: iadd           
            //    62: putfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //    65: aload_0        
            //    66: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //    69: ifne            108
            //    72: aload_0        
            //    73: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    76: aload_0        
            //    77: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    80: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
            //    83: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityId:()I
            //    86: invokestatic    net/minecraft/entity/monster/EntityGuardian.access$0:(Lnet/minecraft/entity/monster/EntityGuardian;I)V
            //    89: aload_0        
            //    90: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //    93: getfield        net/minecraft/entity/monster/EntityGuardian.worldObj:Lnet/minecraft/world/World;
            //    96: aload_0        
            //    97: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   100: bipush          21
            //   102: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
            //   105: goto            230
            //   108: aload_0        
            //   109: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //   112: aload_0        
            //   113: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   116: invokevirtual   net/minecraft/entity/monster/EntityGuardian.func_175464_ck:()I
            //   119: if_icmplt       214
            //   122: fconst_1       
            //   123: fstore_2       
            //   124: aload_0        
            //   125: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   128: getfield        net/minecraft/entity/monster/EntityGuardian.worldObj:Lnet/minecraft/world/World;
            //   131: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
            //   134: getstatic       net/minecraft/world/EnumDifficulty.HARD:Lnet/minecraft/world/EnumDifficulty;
            //   137: if_acmpne       144
            //   140: fload_2        
            //   141: fconst_2       
            //   142: fadd           
            //   143: fstore_2       
            //   144: aload_0        
            //   145: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   148: invokevirtual   net/minecraft/entity/monster/EntityGuardian.func_175461_cl:()Z
            //   151: ifeq            158
            //   154: fload_2        
            //   155: fconst_2       
            //   156: fadd           
            //   157: fstore_2       
            //   158: aload_1        
            //   159: aload_0        
            //   160: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   163: aload_0        
            //   164: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   167: invokestatic    net/minecraft/util/DamageSource.causeIndirectMagicDamage:(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;
            //   170: fload_2        
            //   171: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
            //   174: pop            
            //   175: aload_1        
            //   176: aload_0        
            //   177: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   180: invokestatic    net/minecraft/util/DamageSource.causeMobDamage:(Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/DamageSource;
            //   183: aload_0        
            //   184: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   187: getstatic       net/minecraft/entity/SharedMonsterAttributes.attackDamage:Lnet/minecraft/entity/ai/attributes/IAttribute;
            //   190: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
            //   193: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.getAttributeValue:()D
            //   198: d2f            
            //   199: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
            //   202: pop            
            //   203: aload_0        
            //   204: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179456_a:Lnet/minecraft/entity/monster/EntityGuardian;
            //   207: aconst_null    
            //   208: invokevirtual   net/minecraft/entity/monster/EntityGuardian.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
            //   211: goto            230
            //   214: aload_0        
            //   215: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //   218: bipush          60
            //   220: if_icmplt       230
            //   223: aload_0        
            //   224: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.field_179455_b:I
            //   227: bipush          20
            //   229: irem           
            //   230: aload_0        
            //   231: invokespecial   net/minecraft/entity/ai/EntityAIBase.updateTask:()V
            //   234: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0230 (coming from #0229).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        static {
            __OBFID = "CL_00002211";
        }
    }
    
    class GuardianMoveHelper extends EntityMoveHelper
    {
        private EntityGuardian field_179930_g;
        private static final String __OBFID;
        final EntityGuardian this$0;
        
        public GuardianMoveHelper(final EntityGuardian entityGuardian) {
            super(this.this$0 = entityGuardian);
            this.field_179930_g = entityGuardian;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.update && !this.field_179930_g.getNavigator().noPath()) {
                final double n = this.posX - this.field_179930_g.posX;
                final double n2 = this.posY - this.field_179930_g.posY;
                final double n3 = this.posZ - this.field_179930_g.posZ;
                final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
                final double n5 = n2 / n4;
                this.field_179930_g.rotationYaw = this.limitAngle(this.field_179930_g.rotationYaw, (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f, 30.0f);
                this.field_179930_g.renderYawOffset = this.field_179930_g.rotationYaw;
                this.field_179930_g.setAIMoveSpeed(this.field_179930_g.getAIMoveSpeed() + ((float)(this.speed * this.field_179930_g.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()) - this.field_179930_g.getAIMoveSpeed()) * 0.125f);
                final double n6 = Math.sin((this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.5) * 0.05;
                final double cos = Math.cos(this.field_179930_g.rotationYaw * 3.1415927f / 180.0f);
                final double sin = Math.sin(this.field_179930_g.rotationYaw * 3.1415927f / 180.0f);
                final EntityGuardian field_179930_g = this.field_179930_g;
                field_179930_g.motionX += n6 * cos;
                final EntityGuardian field_179930_g2 = this.field_179930_g;
                field_179930_g2.motionZ += n6 * sin;
                final double n7 = Math.sin((this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.75) * 0.05;
                final EntityGuardian field_179930_g3 = this.field_179930_g;
                field_179930_g3.motionY += n7 * (sin + cos) * 0.25;
                final EntityGuardian field_179930_g4 = this.field_179930_g;
                field_179930_g4.motionY += this.field_179930_g.getAIMoveSpeed() * n5 * 0.1;
                final EntityLookHelper lookHelper = this.field_179930_g.getLookHelper();
                final double n8 = this.field_179930_g.posX + n / n4 * 2.0;
                final double n9 = this.field_179930_g.getEyeHeight() + this.field_179930_g.posY + n5 / n4 * 1.0;
                final double n10 = this.field_179930_g.posZ + n3 / n4 * 2.0;
                double func_180423_e = lookHelper.func_180423_e();
                double func_180422_f = lookHelper.func_180422_f();
                double func_180421_g = lookHelper.func_180421_g();
                if (!lookHelper.func_180424_b()) {
                    func_180423_e = n8;
                    func_180422_f = n9;
                    func_180421_g = n10;
                }
                this.field_179930_g.getLookHelper().setLookPosition(func_180423_e + (n8 - func_180423_e) * 0.125, func_180422_f + (n9 - func_180422_f) * 0.125, func_180421_g + (n10 - func_180421_g) * 0.125, 10.0f, 40.0f);
                EntityGuardian.access$2(this.field_179930_g, true);
            }
            else {
                this.field_179930_g.setAIMoveSpeed(0.0f);
                EntityGuardian.access$2(this.field_179930_g, false);
            }
        }
        
        static {
            __OBFID = "CL_00002209";
        }
    }
    
    class GuardianTargetSelector implements Predicate
    {
        private EntityGuardian field_179916_a;
        private static final String __OBFID;
        final EntityGuardian this$0;
        
        GuardianTargetSelector(final EntityGuardian entityGuardian) {
            this.this$0 = entityGuardian;
            this.field_179916_a = entityGuardian;
        }
        
        public boolean func_179915_a(final EntityLivingBase entityLivingBase) {
            return (entityLivingBase instanceof EntityPlayer || entityLivingBase instanceof EntitySquid) && entityLivingBase.getDistanceSqToEntity(this.field_179916_a) > 9.0;
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.func_179915_a((EntityLivingBase)o);
        }
        
        static {
            __OBFID = "CL_00002210";
        }
    }
}
