package net.minecraft.entity.monster;

import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityEnderman extends EntityMob
{
    private static final UUID attackingSpeedBoostModifierUUID;
    private static final AttributeModifier attackingSpeedBoostModifier;
    private static final Set carriableBlocks;
    private boolean isAggressive;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001685";
        attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        attackingSpeedBoostModifier = new AttributeModifier(EntityEnderman.attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448, 0).setSaved(false);
        (carriableBlocks = Sets.newIdentityHashSet()).add(Blocks.grass);
        EntityEnderman.carriableBlocks.add(Blocks.dirt);
        EntityEnderman.carriableBlocks.add(Blocks.sand);
        EntityEnderman.carriableBlocks.add(Blocks.gravel);
        EntityEnderman.carriableBlocks.add(Blocks.yellow_flower);
        EntityEnderman.carriableBlocks.add(Blocks.red_flower);
        EntityEnderman.carriableBlocks.add(Blocks.brown_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.red_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.tnt);
        EntityEnderman.carriableBlocks.add(Blocks.cactus);
        EntityEnderman.carriableBlocks.add(Blocks.clay);
        EntityEnderman.carriableBlocks.add(Blocks.pumpkin);
        EntityEnderman.carriableBlocks.add(Blocks.melon_block);
        EntityEnderman.carriableBlocks.add(Blocks.mycelium);
    }
    
    public EntityEnderman(final World world) {
        super(world);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new AIPlaceBlock());
        this.tasks.addTask(11, new AITakeBlock());
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new AIFindPlayer());
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate() {
            private static final String __OBFID;
            final EntityEnderman this$0;
            
            public boolean func_179948_a(final EntityEndermite entityEndermite) {
                return entityEndermite.isSpawnedByPlayer();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179948_a((EntityEndermite)o);
            }
            
            static {
                __OBFID = "CL_00002223";
            }
        }));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Short((short)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final IBlockState func_175489_ck = this.func_175489_ck();
        nbtTagCompound.setShort("carried", (short)Block.getIdFromBlock(func_175489_ck.getBlock()));
        nbtTagCompound.setShort("carriedData", (short)func_175489_ck.getBlock().getMetaFromState(func_175489_ck));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        IBlockState blockState;
        if (nbtTagCompound.hasKey("carried", 8)) {
            blockState = Block.getBlockFromName(nbtTagCompound.getString("carried")).getStateFromMeta(nbtTagCompound.getShort("carriedData") & 0xFFFF);
        }
        else {
            blockState = Block.getBlockById(nbtTagCompound.getShort("carried")).getStateFromMeta(nbtTagCompound.getShort("carriedData") & 0xFFFF);
        }
        this.func_175490_a(blockState);
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer entityPlayer) {
        final ItemStack itemStack = entityPlayer.inventory.armorInventory[3];
        if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        }
        final Vec3 normalize = entityPlayer.getLook(1.0f).normalize();
        final Vec3 vec3 = new Vec3(this.posX - entityPlayer.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - (entityPlayer.posY + entityPlayer.getEyeHeight()), this.posZ - entityPlayer.posZ);
        return normalize.dotProduct(vec3.normalize()) > 1.0 - 0.025 / vec3.lengthVector() && entityPlayer.canEntityBeSeen(this);
    }
    
    @Override
    public float getEyeHeight() {
        return 2.55f;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {}
        this.isJumping = false;
        super.onLivingUpdate();
    }
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this > 0 && !this.isAggressive && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }
        if (this.worldObj.isDaytime()) {
            final float brightness = this.getBrightness(1.0f);
            if (brightness > 0.5f && this.worldObj.isAgainstSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f) {
                this.setAttackTarget(null);
                this.setScreaming(false);
                this.isAggressive = false;
                this.teleportRandomly();
            }
        }
        super.updateAITasks();
    }
    
    protected boolean teleportRandomly() {
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 64.0, this.posY + (this.rand.nextInt(64) - 32), this.posZ + (this.rand.nextDouble() - 0.5) * 64.0);
    }
    
    protected boolean teleportToEntity(final Entity entity) {
        final Vec3 normalize = new Vec3(this.posX - entity.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - entity.posY + entity.getEyeHeight(), this.posZ - entity.posZ).normalize();
        final double n = 16.0;
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.xCoord * n, this.posY + (this.rand.nextInt(16) - 8) - normalize.yCoord * n, this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.zCoord * n);
    }
    
    protected boolean teleportTo(final double posX, final double posY, final double posZ) {
        final double posX2 = this.posX;
        final double posY2 = this.posY;
        final double posZ2 = this.posZ;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ))) {}
        this.worldObj.playSoundEffect(posX2, posY2, posZ2, "mob.endermen.portal", 1.0f, 1.0f);
        this.playSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    @Override
    protected String getLivingSound() {
        return (this > 0) ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }
    
    @Override
    protected Item getDropItem() {
        return Items.ender_pearl;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            while (0 < this.rand.nextInt(2 + n)) {
                this.dropItem(dropItem, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public void func_175490_a(final IBlockState blockState) {
        this.dataWatcher.updateObject(16, (short)(Block.getStateId(blockState) & 0xFFFF));
    }
    
    public IBlockState func_175489_ck() {
        return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 0xFFFF);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if (damageSource.getEntity() == null || !(damageSource.getEntity() instanceof EntityEndermite)) {
            if (!this.worldObj.isRemote) {
                this.setScreaming(true);
            }
            if (damageSource instanceof EntityDamageSource && damageSource.getEntity() instanceof EntityPlayer) {
                if (damageSource.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)damageSource.getEntity()).theItemInWorldManager.isCreative()) {
                    this.setScreaming(false);
                }
                else {
                    this.isAggressive = true;
                }
            }
            if (damageSource instanceof EntityDamageSourceIndirect) {
                return this.isAggressive = false;
            }
        }
        super.attackEntityFrom(damageSource, n);
        if (damageSource.isUnblockable() && this.rand.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return false;
    }
    
    public void setScreaming(final boolean b) {
        this.dataWatcher.updateObject(18, (byte)(byte)(b ? 1 : 0));
    }
    
    static AttributeModifier access$0() {
        return EntityEnderman.attackingSpeedBoostModifier;
    }
    
    static boolean access$1(final EntityEnderman entityEnderman, final EntityPlayer entityPlayer) {
        return entityEnderman.shouldAttackPlayer(entityPlayer);
    }
    
    static void access$2(final EntityEnderman entityEnderman, final boolean isAggressive) {
        entityEnderman.isAggressive = isAggressive;
    }
    
    static Set access$3() {
        return EntityEnderman.carriableBlocks;
    }
    
    class AIFindPlayer extends EntityAINearestAttackableTarget
    {
        private EntityPlayer field_179448_g;
        private int field_179450_h;
        private int field_179451_i;
        private EntityEnderman field_179449_j;
        private static final String __OBFID;
        final EntityEnderman this$0;
        
        public AIFindPlayer(final EntityEnderman entityEnderman) {
            super(this.this$0 = entityEnderman, EntityPlayer.class, true);
            this.field_179449_j = entityEnderman;
        }
        
        @Override
        public boolean shouldExecute() {
            final double targetDistance = this.getTargetDistance();
            final List func_175647_a = this.taskOwner.worldObj.func_175647_a(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), this.targetEntitySelector);
            Collections.sort((List<Object>)func_175647_a, this.theNearestAttackableTargetSorter);
            if (func_175647_a.isEmpty()) {
                return false;
            }
            this.field_179448_g = func_175647_a.get(0);
            return true;
        }
        
        @Override
        public void startExecuting() {
            this.field_179450_h = 5;
            this.field_179451_i = 0;
        }
        
        @Override
        public void resetTask() {
            this.field_179448_g = null;
            this.field_179449_j.setScreaming(false);
            this.field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityEnderman.access$0());
            super.resetTask();
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.field_179448_g == null) {
                return super.continueExecuting();
            }
            if (!EntityEnderman.access$1(this.field_179449_j, this.field_179448_g)) {
                return false;
            }
            EntityEnderman.access$2(this.field_179449_j, true);
            this.field_179449_j.faceEntity(this.field_179448_g, 10.0f, 10.0f);
            return true;
        }
        
        @Override
        public void updateTask() {
            if (this.field_179448_g != null) {
                if (--this.field_179450_h <= 0) {
                    this.targetEntity = this.field_179448_g;
                    this.field_179448_g = null;
                    super.startExecuting();
                    this.field_179449_j.playSound("mob.endermen.stare", 1.0f, 1.0f);
                    this.field_179449_j.setScreaming(true);
                    this.field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(EntityEnderman.access$0());
                }
            }
            else {
                if (this.targetEntity != null) {
                    if (this.targetEntity instanceof EntityPlayer && EntityEnderman.access$1(this.field_179449_j, (EntityPlayer)this.targetEntity)) {
                        if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) < 16.0) {
                            this.field_179449_j.teleportRandomly();
                        }
                        this.field_179451_i = 0;
                    }
                    else if (this.targetEntity.getDistanceSqToEntity(this.field_179449_j) > 256.0 && this.field_179451_i++ >= 30 && this.field_179449_j.teleportToEntity(this.targetEntity)) {
                        this.field_179451_i = 0;
                    }
                }
                super.updateTask();
            }
        }
        
        static {
            __OBFID = "CL_00002221";
        }
    }
    
    class AIPlaceBlock extends EntityAIBase
    {
        private EntityEnderman field_179475_a;
        private static final String __OBFID;
        final EntityEnderman this$0;
        
        AIPlaceBlock(final EntityEnderman entityEnderman) {
            this.this$0 = entityEnderman;
            this.field_179475_a = entityEnderman;
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179475_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.field_179475_a.func_175489_ck().getBlock().getMaterial() != Material.air && this.field_179475_a.getRNG().nextInt(2000) == 0;
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.field_179475_a.getRNG();
            final World worldObj = this.field_179475_a.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.field_179475_a.posX - 1.0 + rng.nextDouble() * 2.0), MathHelper.floor_double(this.field_179475_a.posY + rng.nextDouble() * 2.0), MathHelper.floor_double(this.field_179475_a.posZ - 1.0 + rng.nextDouble() * 2.0));
            worldObj.getBlockState(blockPos).getBlock();
            final Block block = worldObj.getBlockState(blockPos.offsetDown()).getBlock();
            this.field_179475_a.func_175489_ck().getBlock();
            if (block == 0) {
                worldObj.setBlockState(blockPos, this.field_179475_a.func_175489_ck(), 3);
                this.field_179475_a.func_175490_a(Blocks.air.getDefaultState());
            }
        }
        
        static {
            __OBFID = "CL_00002222";
        }
    }
    
    class AITakeBlock extends EntityAIBase
    {
        private EntityEnderman field_179473_a;
        private static final String __OBFID;
        final EntityEnderman this$0;
        
        AITakeBlock(final EntityEnderman entityEnderman) {
            this.this$0 = entityEnderman;
            this.field_179473_a = entityEnderman;
        }
        
        @Override
        public boolean shouldExecute() {
            return this.field_179473_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.field_179473_a.func_175489_ck().getBlock().getMaterial() == Material.air && this.field_179473_a.getRNG().nextInt(20) == 0;
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.field_179473_a.getRNG();
            final World worldObj = this.field_179473_a.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.field_179473_a.posX - 2.0 + rng.nextDouble() * 4.0), MathHelper.floor_double(this.field_179473_a.posY + rng.nextDouble() * 3.0), MathHelper.floor_double(this.field_179473_a.posZ - 2.0 + rng.nextDouble() * 4.0));
            final IBlockState blockState = worldObj.getBlockState(blockPos);
            if (EntityEnderman.access$3().contains(blockState.getBlock())) {
                this.field_179473_a.func_175490_a(blockState);
                worldObj.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
        
        static {
            __OBFID = "CL_00002220";
        }
    }
}
