package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;

public class EntitySlime extends EntityLiving implements IMob
{
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean field_175452_bi;
    private static final String __OBFID;
    
    public EntitySlime(final World world) {
        super(world);
        this.moveHelper = new SlimeMoveHelper();
        this.tasks.addTask(1, new AISlimeFloat());
        this.tasks.addTask(2, new AISlimeAttack());
        this.tasks.addTask(3, new AISlimeFaceRandom());
        this.tasks.addTask(5, new AISlimeHop());
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 1);
    }
    
    protected void setSlimeSize(final int experienceValue) {
        this.dataWatcher.updateObject(16, (byte)experienceValue);
        this.setSize(0.51000005f * experienceValue, 0.51000005f * experienceValue);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(experienceValue * experienceValue);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2f + 0.1f * experienceValue);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = experienceValue;
    }
    
    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Size", this.getSlimeSize() - 1);
        nbtTagCompound.setBoolean("wasOnGround", this.field_175452_bi);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        nbtTagCompound.getInteger("Size");
        this.setSlimeSize(1);
        this.field_175452_bi = nbtTagCompound.getBoolean("wasOnGround");
    }
    
    protected EnumParticleTypes func_180487_n() {
        return EnumParticleTypes.SLIME;
    }
    
    protected String getJumpSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            35
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //    14: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //    17: getstatic       net/minecraft/world/EnumDifficulty.PEACEFUL:Lnet/minecraft/world/EnumDifficulty;
        //    20: if_acmpne       35
        //    23: aload_0        
        //    24: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSlimeSize:()I
        //    27: ifle            35
        //    30: aload_0        
        //    31: iconst_1       
        //    32: putfield        net/minecraft/entity/monster/EntitySlime.isDead:Z
        //    35: aload_0        
        //    36: dup            
        //    37: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    40: aload_0        
        //    41: getfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //    44: aload_0        
        //    45: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    48: fsub           
        //    49: ldc             0.5
        //    51: fmul           
        //    52: fadd           
        //    53: putfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    56: aload_0        
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    61: putfield        net/minecraft/entity/monster/EntitySlime.prevSquishFactor:F
        //    64: aload_0        
        //    65: invokespecial   net/minecraft/entity/EntityLiving.onUpdate:()V
        //    68: aload_0        
        //    69: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //    72: ifeq            266
        //    75: aload_0        
        //    76: getfield        net/minecraft/entity/monster/EntitySlime.field_175452_bi:Z
        //    79: ifne            266
        //    82: aload_0        
        //    83: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSlimeSize:()I
        //    86: istore_1       
        //    87: goto            208
        //    90: aload_0        
        //    91: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //    94: invokevirtual   java/util/Random.nextFloat:()F
        //    97: ldc_w           3.1415927
        //   100: fmul           
        //   101: fconst_2       
        //   102: fmul           
        //   103: fstore_3       
        //   104: aload_0        
        //   105: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   108: invokevirtual   java/util/Random.nextFloat:()F
        //   111: ldc             0.5
        //   113: fmul           
        //   114: ldc             0.5
        //   116: fadd           
        //   117: fstore          4
        //   119: fload_3        
        //   120: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   123: iload_1        
        //   124: i2f            
        //   125: fmul           
        //   126: ldc             0.5
        //   128: fmul           
        //   129: fload           4
        //   131: fmul           
        //   132: fstore          5
        //   134: fload_3        
        //   135: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   138: iload_1        
        //   139: i2f            
        //   140: fmul           
        //   141: ldc             0.5
        //   143: fmul           
        //   144: fload           4
        //   146: fmul           
        //   147: fstore          6
        //   149: aload_0        
        //   150: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //   153: astore          7
        //   155: aload_0        
        //   156: invokevirtual   net/minecraft/entity/monster/EntitySlime.func_180487_n:()Lnet/minecraft/util/EnumParticleTypes;
        //   159: astore          8
        //   161: aload_0        
        //   162: getfield        net/minecraft/entity/monster/EntitySlime.posX:D
        //   165: fload           5
        //   167: f2d            
        //   168: dadd           
        //   169: dstore          9
        //   171: aload_0        
        //   172: getfield        net/minecraft/entity/monster/EntitySlime.posZ:D
        //   175: fload           6
        //   177: f2d            
        //   178: dadd           
        //   179: dstore          11
        //   181: aload           7
        //   183: aload           8
        //   185: dload           9
        //   187: aload_0        
        //   188: invokevirtual   net/minecraft/entity/monster/EntitySlime.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   191: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   194: dload           11
        //   196: dconst_0       
        //   197: dconst_0       
        //   198: dconst_0       
        //   199: iconst_0       
        //   200: newarray        I
        //   202: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   205: iinc            2, 1
        //   208: iconst_0       
        //   209: iload_1        
        //   210: bipush          8
        //   212: imul           
        //   213: if_icmplt       90
        //   216: aload_0        
        //   217: if_icmple       256
        //   220: aload_0        
        //   221: aload_0        
        //   222: invokevirtual   net/minecraft/entity/monster/EntitySlime.getJumpSound:()Ljava/lang/String;
        //   225: aload_0        
        //   226: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSoundVolume:()F
        //   229: aload_0        
        //   230: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   233: invokevirtual   java/util/Random.nextFloat:()F
        //   236: aload_0        
        //   237: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   240: invokevirtual   java/util/Random.nextFloat:()F
        //   243: fsub           
        //   244: ldc             0.2
        //   246: fmul           
        //   247: fconst_1       
        //   248: fadd           
        //   249: ldc_w           0.8
        //   252: fdiv           
        //   253: invokevirtual   net/minecraft/entity/monster/EntitySlime.playSound:(Ljava/lang/String;FF)V
        //   256: aload_0        
        //   257: ldc_w           -0.5
        //   260: putfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //   263: goto            285
        //   266: aload_0        
        //   267: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //   270: ifne            285
        //   273: aload_0        
        //   274: getfield        net/minecraft/entity/monster/EntitySlime.field_175452_bi:Z
        //   277: ifeq            285
        //   280: aload_0        
        //   281: fconst_1       
        //   282: putfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //   285: aload_0        
        //   286: aload_0        
        //   287: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //   290: putfield        net/minecraft/entity/monster/EntitySlime.field_175452_bi:Z
        //   293: aload_0        
        //   294: invokevirtual   net/minecraft/entity/monster/EntitySlime.alterSquishAmount:()V
        //   297: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }
    
    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }
    
    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }
    
    @Override
    public void func_145781_i(final int n) {
        if (n == 16) {
            final int slimeSize = this.getSlimeSize();
            this.setSize(0.51000005f * slimeSize, 0.51000005f * slimeSize);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.resetHeight();
            }
        }
        super.func_145781_i(n);
    }
    
    @Override
    public void setDead() {
        final int slimeSize = this.getSlimeSize();
        if (!this.worldObj.isRemote && slimeSize > 1 && this.getHealth() <= 0.0f) {
            while (0 < 2 + this.rand.nextInt(3)) {
                final float n = (0 - 0.5f) * slimeSize / 4.0f;
                final float n2 = (0 - 0.5f) * slimeSize / 4.0f;
                final EntitySlime instance = this.createInstance();
                if (this.hasCustomName()) {
                    instance.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    instance.enablePersistence();
                }
                instance.setSlimeSize(slimeSize / 2);
                instance.setLocationAndAngles(this.posX + n, this.posY + 0.5, this.posZ + n2, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(instance);
                int n3 = 0;
                ++n3;
            }
        }
        super.setDead();
    }
    
    @Override
    public void applyEntityCollision(final Entity p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/EntityLiving.applyEntityCollision:(Lnet/minecraft/entity/Entity;)V
        //     5: aload_1        
        //     6: instanceof      Lnet/minecraft/entity/monster/EntityIronGolem;
        //     9: ifeq            24
        //    12: aload_0        
        //    13: if_icmple       24
        //    16: aload_0        
        //    17: aload_1        
        //    18: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //    21: invokevirtual   net/minecraft/entity/monster/EntitySlime.func_175451_e:(Lnet/minecraft/entity/EntityLivingBase;)V
        //    24: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       9
        //     4: aload_0        
        //     5: aload_1        
        //     6: invokevirtual   net/minecraft/entity/monster/EntitySlime.func_175451_e:(Lnet/minecraft/entity/EntityLivingBase;)V
        //     9: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void func_175451_e(final EntityLivingBase entityLivingBase) {
        final int slimeSize = this.getSlimeSize();
        if (this.canEntityBeSeen(entityLivingBase) && this.getDistanceSqToEntity(entityLivingBase) < 0.6 * slimeSize * 0.6 * slimeSize && entityLivingBase.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
            this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.func_174815_a(this, entityLivingBase);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 0.625f * this.height;
    }
    
    protected int getAttackStrength() {
        return this.getSlimeSize();
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected Item getDropItem() {
        return (this.getSlimeSize() == 1) ? Items.slime_ball : null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final Chunk chunkFromBlockCoords = this.worldObj.getChunkFromBlockCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
            return false;
        }
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(10) == 0 && chunkFromBlockCoords.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return false;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f * this.getSlimeSize();
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }
    
    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.41999998688697815;
        this.isAirBorne = true;
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        int nextInt = this.rand.nextInt(3);
        if (nextInt < 2 && this.rand.nextFloat() < 0.5f * difficultyInstance.func_180170_c()) {
            ++nextInt;
        }
        this.setSlimeSize(1 << nextInt);
        return super.func_180482_a(difficultyInstance, entityLivingData);
    }
    
    static {
        __OBFID = "CL_00001698";
    }
    
    class AISlimeAttack extends EntityAIBase
    {
        private EntitySlime field_179466_a;
        private int field_179465_b;
        private static final String __OBFID;
        final EntitySlime this$0;
        
        public AISlimeAttack(final EntitySlime entitySlime) {
            this.this$0 = entitySlime;
            this.field_179466_a = entitySlime;
            this.setMutexBits(2);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.field_179466_a.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }
        
        @Override
        public void startExecuting() {
            this.field_179465_b = 300;
            super.startExecuting();
        }
        
        @Override
        public boolean continueExecuting() {
            final EntityLivingBase attackTarget = this.field_179466_a.getAttackTarget();
            boolean b;
            if (attackTarget == null) {
                b = false;
            }
            else if (!attackTarget.isEntityAlive()) {
                b = false;
            }
            else {
                final int field_179465_b = this.field_179465_b - 1;
                this.field_179465_b = field_179465_b;
                b = (field_179465_b > 0);
            }
            return b;
        }
        
        @Override
        public void updateTask() {
            this.field_179466_a.faceEntity(this.field_179466_a.getAttackTarget(), 10.0f, 10.0f);
            ((SlimeMoveHelper)this.field_179466_a.getMoveHelper()).func_179920_a(this.field_179466_a.rotationYaw, this.field_179466_a.canDamagePlayer());
        }
        
        static {
            __OBFID = "CL_00002202";
        }
    }
    
    class SlimeMoveHelper extends EntityMoveHelper
    {
        private float field_179922_g;
        private int field_179924_h;
        private EntitySlime field_179925_i;
        private boolean field_179923_j;
        private static final String __OBFID;
        final EntitySlime this$0;
        
        public SlimeMoveHelper(final EntitySlime entitySlime) {
            super(this.this$0 = entitySlime);
            this.field_179925_i = entitySlime;
        }
        
        public void func_179920_a(final float field_179922_g, final boolean field_179923_j) {
            this.field_179922_g = field_179922_g;
            this.field_179923_j = field_179923_j;
        }
        
        public void func_179921_a(final double speed) {
            this.speed = speed;
            this.update = true;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0f);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;
            if (!this.update) {
                this.entity.setMoveForward(0.0f);
            }
            else {
                this.update = false;
                if (this.entity.onGround) {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                    if (this.field_179924_h-- <= 0) {
                        this.field_179924_h = this.field_179925_i.getJumpDelay();
                        if (this.field_179923_j) {
                            this.field_179924_h /= 3;
                        }
                        this.field_179925_i.getJumpHelper().setJumping();
                        if (this.field_179925_i.makesSoundOnJump()) {
                            this.field_179925_i.playSound(this.field_179925_i.getJumpSound(), this.field_179925_i.getSoundVolume(), ((this.field_179925_i.getRNG().nextFloat() - this.field_179925_i.getRNG().nextFloat()) * 0.2f + 1.0f) * 0.8f);
                        }
                    }
                    else {
                        final EntitySlime field_179925_i = this.field_179925_i;
                        final EntitySlime field_179925_i2 = this.field_179925_i;
                        final float n = 0.0f;
                        field_179925_i2.moveForward = n;
                        field_179925_i.moveStrafing = n;
                        this.entity.setAIMoveSpeed(0.0f);
                    }
                }
                else {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                }
            }
        }
        
        static {
            __OBFID = "CL_00002199";
        }
    }
    
    class AISlimeFaceRandom extends EntityAIBase
    {
        private EntitySlime field_179461_a;
        private float field_179459_b;
        private int field_179460_c;
        private static final String __OBFID;
        final EntitySlime this$0;
        
        public AISlimeFaceRandom(final EntitySlime entitySlime) {
            this.this$0 = entitySlime;
            this.field_179461_a = entitySlime;
            this.setMutexBits(2);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179461_a.getAttackTarget() == null && (this.field_179461_a.onGround || this.field_179461_a.isInWater() || this.field_179461_a.func_180799_ab());
        }
        
        @Override
        public void updateTask() {
            final int field_179460_c = this.field_179460_c - 1;
            this.field_179460_c = field_179460_c;
            if (field_179460_c <= 0) {
                this.field_179460_c = 40 + this.field_179461_a.getRNG().nextInt(60);
                this.field_179459_b = (float)this.field_179461_a.getRNG().nextInt(360);
            }
            ((SlimeMoveHelper)this.field_179461_a.getMoveHelper()).func_179920_a(this.field_179459_b, false);
        }
        
        static {
            __OBFID = "CL_00002198";
        }
    }
    
    class AISlimeFloat extends EntityAIBase
    {
        private EntitySlime field_179457_a;
        private static final String __OBFID;
        final EntitySlime this$0;
        
        public AISlimeFloat(final EntitySlime entitySlime) {
            this.this$0 = entitySlime;
            this.field_179457_a = entitySlime;
            this.setMutexBits(5);
            ((PathNavigateGround)entitySlime.getNavigator()).func_179693_d(true);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179457_a.isInWater() || this.field_179457_a.func_180799_ab();
        }
        
        @Override
        public void updateTask() {
            if (this.field_179457_a.getRNG().nextFloat() < 0.8f) {
                this.field_179457_a.getJumpHelper().setJumping();
            }
            ((SlimeMoveHelper)this.field_179457_a.getMoveHelper()).func_179921_a(1.2);
        }
        
        static {
            __OBFID = "CL_00002201";
        }
    }
    
    class AISlimeHop extends EntityAIBase
    {
        private EntitySlime field_179458_a;
        private static final String __OBFID;
        final EntitySlime this$0;
        
        public AISlimeHop(final EntitySlime entitySlime) {
            this.this$0 = entitySlime;
            this.field_179458_a = entitySlime;
            this.setMutexBits(5);
        }
        
        @Override
        public boolean shouldExecute() {
            return true;
        }
        
        @Override
        public void updateTask() {
            ((SlimeMoveHelper)this.field_179458_a.getMoveHelper()).func_179921_a(1.0);
        }
        
        static {
            __OBFID = "CL_00002200";
        }
    }
}
