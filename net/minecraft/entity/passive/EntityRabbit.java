package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.pathfinding.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.ai.*;

public class EntityRabbit extends EntityAnimal
{
    private AIAvoidEntity field_175539_bk;
    private int field_175540_bm;
    private int field_175535_bn;
    private boolean field_175536_bo;
    private boolean field_175537_bp;
    private int field_175538_bq;
    private EnumMoveType field_175542_br;
    private int field_175541_bs;
    private EntityPlayer field_175543_bt;
    private static final String __OBFID;
    
    public EntityRabbit(final World world) {
        super(world);
        this.field_175540_bm = 0;
        this.field_175535_bn = 0;
        this.field_175536_bo = false;
        this.field_175537_bp = false;
        this.field_175538_bq = 0;
        this.field_175542_br = EnumMoveType.HOP;
        this.field_175541_bs = 0;
        this.field_175543_bt = null;
        this.setSize(0.6f, 0.7f);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new RabbitMoveHelper();
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.navigator.func_179678_a(2.5f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new AIPanic(1.33));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0, Items.carrot, false));
        this.tasks.addTask(3, new EntityAIMate(this, 0.8));
        this.tasks.addTask(5, new AIRaidFarm());
        this.tasks.addTask(5, new EntityAIWander(this, 0.6));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.field_175539_bk = new AIAvoidEntity(new Predicate() {
            private static final String __OBFID;
            final EntityRabbit this$0;
            
            public boolean func_180086_a(final Entity entity) {
                return entity instanceof EntityWolf;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180086_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002241";
            }
        }, 16.0f, 1.33, 1.33);
        this.tasks.addTask(4, this.field_175539_bk);
        this.func_175515_b(0.0);
    }
    
    @Override
    protected float func_175134_bD() {
        return (this.moveHelper.isUpdating() && this.moveHelper.func_179919_e() > this.posY + 0.5) ? 0.5f : this.field_175542_br.func_180074_b();
    }
    
    public void func_175522_a(final EnumMoveType field_175542_br) {
        this.field_175542_br = field_175542_br;
    }
    
    public float func_175521_o(final float n) {
        return (this.field_175535_bn == 0) ? 0.0f : ((this.field_175540_bm + n) / this.field_175535_bn);
    }
    
    public void func_175515_b(final double speed) {
        this.getNavigator().setSpeed(speed);
        this.moveHelper.setMoveTo(this.moveHelper.func_179917_d(), this.moveHelper.func_179919_e(), this.moveHelper.func_179918_f(), speed);
    }
    
    public void func_175519_a(final boolean b, final EnumMoveType enumMoveType) {
        super.setJumping(b);
        if (!b) {
            if (this.field_175542_br == EnumMoveType.ATTACK) {
                this.field_175542_br = EnumMoveType.HOP;
            }
        }
        else {
            this.func_175515_b(1.5 * enumMoveType.func_180072_a());
            this.playSound(this.func_175516_ck(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
        this.field_175536_bo = b;
    }
    
    public void func_175524_b(final EnumMoveType enumMoveType) {
        this.func_175519_a(true, enumMoveType);
        this.field_175535_bn = enumMoveType.func_180073_d();
        this.field_175540_bm = 0;
    }
    
    public boolean func_175523_cj() {
        return this.field_175536_bo;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, 0);
    }
    
    public void updateAITasks() {
        if (this.moveHelper.getSpeed() > 0.8) {
            this.func_175522_a(EnumMoveType.SPRINT);
        }
        else if (this.field_175542_br != EnumMoveType.ATTACK) {
            this.func_175522_a(EnumMoveType.HOP);
        }
        if (this.field_175538_bq > 0) {
            --this.field_175538_bq;
        }
        if (this.field_175541_bs > 0) {
            this.field_175541_bs -= this.rand.nextInt(3);
            if (this.field_175541_bs < 0) {
                this.field_175541_bs = 0;
            }
        }
        if (this.onGround) {
            if (!this.field_175537_bp) {
                this.func_175519_a(false, EnumMoveType.NONE);
                this.func_175517_cu();
            }
            if (this.func_175531_cl() == 99 && this.field_175538_bq == 0) {
                final EntityLivingBase attackTarget = this.getAttackTarget();
                if (attackTarget != null && this.getDistanceSqToEntity(attackTarget) < 16.0) {
                    this.func_175533_a(attackTarget.posX, attackTarget.posZ);
                    this.moveHelper.setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, this.moveHelper.getSpeed());
                    this.func_175524_b(EnumMoveType.ATTACK);
                    this.field_175537_bp = true;
                }
            }
            final RabbitJumpHelper rabbitJumpHelper = (RabbitJumpHelper)this.jumpHelper;
            if (!rabbitJumpHelper.func_180067_c()) {
                if (this.moveHelper.isUpdating() && this.field_175538_bq == 0) {
                    final PathEntity path = this.navigator.getPath();
                    Vec3 position = new Vec3(this.moveHelper.func_179917_d(), this.moveHelper.func_179919_e(), this.moveHelper.func_179918_f());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        position = path.getPosition(this);
                    }
                    this.func_175533_a(position.xCoord, position.zCoord);
                    this.func_175524_b(this.field_175542_br);
                }
            }
            else if (!rabbitJumpHelper.func_180065_d()) {
                this.func_175518_cr();
            }
        }
        this.field_175537_bp = this.onGround;
    }
    
    @Override
    public void func_174830_Y() {
    }
    
    private void func_175533_a(final double n, final double n2) {
        this.rotationYaw = (float)(Math.atan2(n2 - this.posZ, n - this.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    private void func_175518_cr() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
    }
    
    private void func_175520_cs() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
    }
    
    private void func_175530_ct() {
        this.field_175538_bq = this.func_175532_cm();
    }
    
    private void func_175517_cu() {
        this.func_175530_ct();
        this.func_175520_cs();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.field_175540_bm != this.field_175535_bn) {
            if (this.field_175540_bm == 0 && !this.worldObj.isRemote) {
                this.worldObj.setEntityState(this, (byte)1);
            }
            ++this.field_175540_bm;
        }
        else if (this.field_175535_bn != 0) {
            this.field_175540_bm = 0;
            this.field_175535_bn = 0;
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("RabbitType", this.func_175531_cl());
        nbtTagCompound.setInteger("MoreCarrotTicks", this.field_175541_bs);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.func_175529_r(nbtTagCompound.getInteger("RabbitType"));
        this.field_175541_bs = nbtTagCompound.getInteger("MoreCarrotTicks");
    }
    
    protected String func_175516_ck() {
        return "mob.rabbit.hop";
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.rabbit.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.rabbit.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.rabbit.death";
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (this.func_175531_cl() == 99) {
            this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0f);
        }
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    @Override
    public int getTotalArmorValue() {
        return (this.func_175531_cl() == 99) ? 8 : super.getTotalArmorValue();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return !this.func_180431_b(damageSource) && super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void addRandomArmor() {
        this.entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0f);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.rabbit_hide, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(2)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_rabbit, 1);
            }
            else {
                this.dropItem(Items.rabbit, 1);
            }
            ++n2;
        }
    }
    
    private boolean func_175525_a(final Item item) {
        return item == Items.carrot || item == Items.golden_carrot || item == Item.getItemFromBlock(Blocks.yellow_flower);
    }
    
    public EntityRabbit func_175526_b(final EntityAgeable entityAgeable) {
        final EntityRabbit entityRabbit = new EntityRabbit(this.worldObj);
        if (entityAgeable instanceof EntityRabbit) {
            entityRabbit.func_175529_r(this.rand.nextBoolean() ? this.func_175531_cl() : ((EntityRabbit)entityAgeable).func_175531_cl());
        }
        return entityRabbit;
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && this.func_175525_a(itemStack.getItem());
    }
    
    public int func_175531_cl() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    public void func_175529_r(final int n) {
        if (n == 99) {
            this.tasks.removeTask(this.field_175539_bk);
            this.tasks.addTask(4, new AIEvilAttack());
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.hasCustomName()) {
                this.setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
            }
        }
        this.dataWatcher.updateObject(18, (byte)n);
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        IEntityLivingData func_180482_a = super.func_180482_a(difficultyInstance, entityLivingData);
        int n = this.rand.nextInt(6);
        if (func_180482_a instanceof RabbitTypeData) {
            n = ((RabbitTypeData)func_180482_a).field_179427_a;
        }
        else {
            func_180482_a = new RabbitTypeData(n);
        }
        this.func_175529_r(n);
        if (true) {
            this.setGrowingAge(-24000);
        }
        return func_180482_a;
    }
    
    private boolean func_175534_cv() {
        return this.field_175541_bs == 0;
    }
    
    protected int func_175532_cm() {
        return this.field_175542_br.func_180075_c();
    }
    
    protected void func_175528_cn() {
        this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0, Block.getStateId(Blocks.carrots.getStateFromMeta(7)));
        this.field_175541_bs = 100;
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 1) {
            this.func_174808_Z();
            this.field_175535_bn = 10;
            this.field_175540_bm = 0;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.func_175526_b(entityAgeable);
    }
    
    static boolean access$0(final EntityRabbit entityRabbit) {
        return entityRabbit.func_175534_cv();
    }
    
    static {
        __OBFID = "CL_00002242";
    }
    
    class AIAvoidEntity extends EntityAIAvoidEntity
    {
        private EntityRabbit field_179511_d;
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public AIAvoidEntity(final EntityRabbit entityRabbit, final Predicate predicate, final float n, final double n2, final double n3) {
            super(this.this$0 = entityRabbit, predicate, n, n2, n3);
            this.field_179511_d = entityRabbit;
        }
        
        @Override
        public void updateTask() {
            super.updateTask();
        }
        
        static {
            __OBFID = "CL_00002238";
        }
    }
    
    class AIEvilAttack extends EntityAIAttackOnCollide
    {
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public AIEvilAttack(final EntityRabbit this$0) {
            super(this.this$0 = this$0, EntityLivingBase.class, 1.4, true);
        }
        
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
        
        static {
            __OBFID = "CL_00002240";
        }
    }
    
    class AIPanic extends EntityAIPanic
    {
        private EntityRabbit field_179486_b;
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public AIPanic(final EntityRabbit entityRabbit, final double n) {
            super(this.this$0 = entityRabbit, n);
            this.field_179486_b = entityRabbit;
        }
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.field_179486_b.func_175515_b(this.speed);
        }
        
        static {
            __OBFID = "CL_00002234";
        }
    }
    
    class AIRaidFarm extends EntityAIMoveToBlock
    {
        private boolean field_179498_d;
        private boolean field_179499_e;
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public AIRaidFarm(final EntityRabbit this$0) {
            super(this.this$0 = this$0, 0.699999988079071, 16);
            this.field_179499_e = false;
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.field_179496_a <= 0) {
                if (!this.this$0.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    return false;
                }
                this.field_179499_e = false;
                this.field_179498_d = EntityRabbit.access$0(this.this$0);
            }
            return super.shouldExecute();
        }
        
        @Override
        public boolean continueExecuting() {
            return this.field_179499_e && super.continueExecuting();
        }
        
        @Override
        public void startExecuting() {
            super.startExecuting();
        }
        
        @Override
        public void resetTask() {
            super.resetTask();
        }
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.this$0.getLookHelper().setLookPosition(this.field_179494_b.getX() + 0.5, this.field_179494_b.getY() + 1, this.field_179494_b.getZ() + 0.5, 10.0f, (float)this.this$0.getVerticalFaceSpeed());
            if (this.func_179487_f()) {
                final World worldObj = this.this$0.worldObj;
                final BlockPos offsetUp = this.field_179494_b.offsetUp();
                final IBlockState blockState = worldObj.getBlockState(offsetUp);
                final Block block = blockState.getBlock();
                if (this.field_179499_e && block instanceof BlockCarrot && (int)blockState.getValue(BlockCarrot.AGE) == 7) {
                    worldObj.setBlockState(offsetUp, Blocks.air.getDefaultState(), 2);
                    worldObj.destroyBlock(offsetUp, true);
                    this.this$0.func_175528_cn();
                }
                this.field_179499_e = false;
                this.field_179496_a = 10;
            }
        }
        
        @Override
        protected boolean func_179488_a(final World world, BlockPos offsetUp) {
            if (world.getBlockState(offsetUp).getBlock() == Blocks.farmland) {
                offsetUp = offsetUp.offsetUp();
                final IBlockState blockState = world.getBlockState(offsetUp);
                if (blockState.getBlock() instanceof BlockCarrot && (int)blockState.getValue(BlockCarrot.AGE) == 7 && this.field_179498_d && !this.field_179499_e) {
                    return this.field_179499_e = true;
                }
            }
            return false;
        }
        
        static {
            __OBFID = "CL_00002233";
        }
    }
    
    enum EnumMoveType
    {
        NONE("NONE", 0, "NONE", 0, 0.0f, 0.0f, 30, 1), 
        HOP("HOP", 1, "HOP", 1, 0.8f, 0.2f, 20, 10), 
        STEP("STEP", 2, "STEP", 2, 1.0f, 0.45f, 14, 14), 
        SPRINT("SPRINT", 3, "SPRINT", 3, 1.75f, 0.4f, 1, 8), 
        ATTACK("ATTACK", 4, "ATTACK", 4, 2.0f, 0.7f, 7, 8);
        
        private final float field_180076_f;
        private final float field_180077_g;
        private final int field_180084_h;
        private final int field_180085_i;
        private static final EnumMoveType[] $VALUES;
        private static final String __OBFID;
        private static final EnumMoveType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002239";
            ENUM$VALUES = new EnumMoveType[] { EnumMoveType.NONE, EnumMoveType.HOP, EnumMoveType.STEP, EnumMoveType.SPRINT, EnumMoveType.ATTACK };
            $VALUES = new EnumMoveType[] { EnumMoveType.NONE, EnumMoveType.HOP, EnumMoveType.STEP, EnumMoveType.SPRINT, EnumMoveType.ATTACK };
        }
        
        private EnumMoveType(final String s, final int n, final String s2, final int n2, final float field_180076_f, final float field_180077_g, final int field_180084_h, final int field_180085_i) {
            this.field_180076_f = field_180076_f;
            this.field_180077_g = field_180077_g;
            this.field_180084_h = field_180084_h;
            this.field_180085_i = field_180085_i;
        }
        
        public float func_180072_a() {
            return this.field_180076_f;
        }
        
        public float func_180074_b() {
            return this.field_180077_g;
        }
        
        public int func_180075_c() {
            return this.field_180084_h;
        }
        
        public int func_180073_d() {
            return this.field_180085_i;
        }
    }
    
    public class RabbitJumpHelper extends EntityJumpHelper
    {
        private EntityRabbit field_180070_c;
        private boolean field_180068_d;
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public RabbitJumpHelper(final EntityRabbit this$0, final EntityRabbit field_180070_c) {
            this.this$0 = this$0;
            super(field_180070_c);
            this.field_180068_d = false;
            this.field_180070_c = field_180070_c;
        }
        
        public boolean func_180067_c() {
            return this.isJumping;
        }
        
        public boolean func_180065_d() {
            return this.field_180068_d;
        }
        
        public void func_180066_a(final boolean field_180068_d) {
            this.field_180068_d = field_180068_d;
        }
        
        @Override
        public void doJump() {
            if (this.isJumping) {
                this.field_180070_c.func_175524_b(EnumMoveType.STEP);
                this.isJumping = false;
            }
        }
        
        static {
            __OBFID = "CL_00002236";
        }
    }
    
    class RabbitMoveHelper extends EntityMoveHelper
    {
        private EntityRabbit field_179929_g;
        private static final String __OBFID;
        final EntityRabbit this$0;
        
        public RabbitMoveHelper(final EntityRabbit entityRabbit) {
            super(this.this$0 = entityRabbit);
            this.field_179929_g = entityRabbit;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.field_179929_g.onGround && !this.field_179929_g.func_175523_cj()) {
                this.field_179929_g.func_175515_b(0.0);
            }
            super.onUpdateMoveHelper();
        }
        
        static {
            __OBFID = "CL_00002235";
        }
    }
    
    public static class RabbitTypeData implements IEntityLivingData
    {
        public int field_179427_a;
        private static final String __OBFID;
        
        public RabbitTypeData(final int field_179427_a) {
            this.field_179427_a = field_179427_a;
        }
        
        static {
            __OBFID = "CL_00002237";
        }
    }
}
