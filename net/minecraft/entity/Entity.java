package net.minecraft.entity;

import net.minecraft.command.*;
import Mood.*;
import DTool.modules.player.*;
import net.minecraft.server.*;
import DTool.events.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;

public abstract class Entity implements ICommandSender
{
    private static final AxisAlignedBB field_174836_a;
    private static int nextEntityID;
    private int entityId;
    public double renderDistanceWeight;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public boolean forceSpawn;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    public boolean isInWeb;
    private boolean isOutsideBorder;
    public boolean isDead;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    private int nextStepDistance;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected Random rand;
    public int ticksExisted;
    public int fireResistance;
    private int fire;
    protected boolean inWater;
    public int hurtResistantTime;
    protected boolean firstUpdate;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    protected boolean inPortal;
    protected int portalCounter;
    public int dimension;
    protected int teleportDirection;
    private boolean invulnerable;
    protected UUID entityUniqueID;
    private final CommandResultStats field_174837_as;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001533";
        field_174836_a = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public void setEntityId(final int entityId) {
        this.entityId = entityId;
    }
    
    public void func_174812_G() {
        this.setDead();
    }
    
    public Entity(final World worldObj) {
        this.entityId = Entity.nextEntityID++;
        this.renderDistanceWeight = 1.0;
        this.boundingBox = Entity.field_174836_a;
        this.width = 0.6f;
        this.height = 1.8f;
        this.nextStepDistance = 1;
        this.rand = new Random();
        this.fireResistance = 1;
        this.firstUpdate = true;
        this.entityUniqueID = MathHelper.func_180182_a(this.rand);
        this.field_174837_as = new CommandResultStats();
        this.worldObj = worldObj;
        this.setPosition(0.0, 0.0, 0.0);
        if (worldObj != null) {
            this.dimension = worldObj.provider.getDimensionId();
        }
        (this.dataWatcher = new DataWatcher(this)).addObject(0, 0);
        this.dataWatcher.addObject(1, 300);
        this.dataWatcher.addObject(3, 0);
        this.dataWatcher.addObject(2, "");
        this.dataWatcher.addObject(4, 0);
        this.entityInit();
    }
    
    protected abstract void entityInit();
    
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Entity && ((Entity)o).entityId == this.entityId;
    }
    
    @Override
    public int hashCode() {
        return this.entityId;
    }
    
    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0 && this.posY < 256.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
                    break;
                }
                ++this.posY;
            }
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.rotationPitch = 0.0f;
        }
    }
    
    public void setDead() {
        this.isDead = true;
    }
    
    protected void setSize(final float width, final float height) {
        if (width != this.width || height != this.height) {
            final float width2 = this.width;
            this.width = width;
            this.height = height;
            this.func_174826_a(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().minX + this.width, this.getEntityBoundingBox().minY + this.height, this.getEntityBoundingBox().minZ + this.width));
            if (this.width > width2 && !this.firstUpdate && !this.worldObj.isRemote) {
                this.moveEntity(width2 - this.width, 0.0, width2 - this.width);
            }
        }
    }
    
    public void setRotation(final float n, final float n2) {
        this.rotationYaw = n % 360.0f;
        this.rotationPitch = n2 % 360.0f;
    }
    
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.func_174826_a(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    public void setAngles(final float n, final float n2) {
        final float rotationPitch = this.rotationPitch;
        final float rotationYaw = this.rotationYaw;
        this.rotationYaw += (float)(n * 0.15);
        this.rotationPitch -= (float)(n2 * 0.15);
        final Client instance = Client.INSTANCE;
        final NoHead noHead = (NoHead)Client.getModuleByName("NoHead");
        this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, noHead.isEnable() ? -100000.0f : -90.0f, noHead.isEnable() ? 100000.0f : 90.0f);
        if (noHead.isEnable()) {
            if (this.rotationPitch > 10000.0f) {
                this.rotationPitch = -90.0f;
            }
            if (this.rotationPitch < -10000.0f) {
                this.rotationPitch = 90.0f;
            }
        }
        this.prevRotationPitch += this.rotationPitch - rotationPitch;
        this.prevRotationYaw += this.rotationYaw - rotationYaw;
    }
    
    public void onUpdate() {
        this.onEntityUpdate();
    }
    
    public void onEntityUpdate() {
        this.worldObj.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer func_73046_m = ((WorldServer)this.worldObj).func_73046_m();
            final int maxInPortalTime = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (func_73046_m.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= maxInPortalTime) {
                        this.portalCounter = maxInPortalTime;
                        this.timeUntilPortal = this.getPortalCooldown();
                        if (this.worldObj.provider.getDimensionId() == -1) {}
                        this.travelToDimension(-1);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        this.func_174830_Y();
        this.handleWaterMovement();
        if (this.worldObj.isRemote) {
            this.fire = 0;
        }
        else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            }
            else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                --this.fire;
            }
        }
        if (this.func_180799_ab()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote) {
            this.setFlag(0, this.fire > 0);
        }
        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();
    }
    
    public int getMaxInPortalTime() {
        return 0;
    }
    
    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(15);
        }
    }
    
    public void setFire(final int n) {
        final int fireTimeForEntity = EnchantmentProtection.getFireTimeForEntity(this, n * 20);
        if (this.fire < fireTimeForEntity) {
            this.fire = fireTimeForEntity;
        }
    }
    
    public void extinguish() {
        this.fire = 0;
    }
    
    protected void kill() {
        this.setDead();
    }
    
    public boolean isOffsetPositionInLiquid(final double n, final double n2, final double n3) {
        return this.func_174809_b(this.getEntityBoundingBox().offset(n, n2, n3));
    }
    
    private boolean func_174809_b(final AxisAlignedBB axisAlignedBB) {
        return this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB).isEmpty() && !this.worldObj.isAnyLiquid(axisAlignedBB);
    }
    
    public void setEntityBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    private void resetPositionToBB() {
        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
        this.posY = this.getEntityBoundingBox().minY;
        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
    }
    
    public void moveEntity(double calculateXOffset, double y, double calculateZOffset) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, y, calculateZOffset));
            this.resetPositionToBB();
        }
        else {
            this.worldObj.theProfiler.startSection("move");
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                calculateXOffset *= 0.25;
                y *= 0.05000000074505806;
                calculateZOffset *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double d3 = calculateXOffset;
            final double n = y;
            double d4 = calculateZOffset;
            final boolean sneaking = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
            final EventSneaking eventSneaking = new EventSneaking();
            eventSneaking.setType(EventType.PRE);
            eventSneaking.setX(calculateXOffset);
            eventSneaking.setY(y);
            eventSneaking.setZ(calculateZOffset);
            eventSneaking.worldObj = this.worldObj;
            eventSneaking.boundingBox = this.getEntityBoundingBox();
            eventSneaking.entity = this;
            eventSneaking.d3 = d3;
            eventSneaking.d5 = d4;
            eventSneaking.sneaking = sneaking;
            Client.onEvent(eventSneaking);
            boolean sneaking2 = eventSneaking.sneaking;
            if (sneaking2 && !eventSneaking.isCancelled()) {
                final double n2 = 0.05;
                while (calculateXOffset != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, eventSneaking.offset, 0.0)).isEmpty()) {
                        break;
                    }
                    if (calculateXOffset < n2 && calculateXOffset >= -n2) {
                        calculateXOffset = 0.0;
                        eventSneaking.postEdgeOfBlock = true;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= n2;
                    }
                    else {
                        calculateXOffset += n2;
                    }
                    d3 = calculateXOffset;
                }
                while (calculateZOffset != 0.0) {
                    if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0, eventSneaking.offset, calculateZOffset)).isEmpty()) {
                        break;
                    }
                    if (calculateZOffset < n2 && calculateZOffset >= -n2) {
                        calculateZOffset = 0.0;
                        eventSneaking.postEdgeOfBlock = true;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= n2;
                    }
                    else {
                        calculateZOffset += n2;
                    }
                    d4 = calculateZOffset;
                }
                while (calculateXOffset != 0.0 && calculateZOffset != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, eventSneaking.offset, calculateZOffset)).isEmpty()) {
                    if (calculateXOffset < n2 && calculateXOffset >= -n2) {
                        calculateXOffset = 0.0;
                        eventSneaking.postEdgeOfBlock = true;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= n2;
                    }
                    else {
                        calculateXOffset += n2;
                    }
                    d3 = calculateXOffset;
                    if (calculateZOffset < n2 && calculateZOffset >= -n2) {
                        calculateZOffset = 0.0;
                        eventSneaking.postEdgeOfBlock = true;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= n2;
                    }
                    else {
                        calculateZOffset += n2;
                    }
                    d4 = calculateZOffset;
                }
            }
            eventSneaking.setType(EventType.POST);
            Client.onEvent(eventSneaking);
            if (eventSneaking.revertFlagAfter) {
                sneaking2 = (this.onGround && this.isSneaking() && this instanceof EntityPlayer);
            }
            final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(calculateXOffset, y, calculateZOffset));
            final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
            final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
            while (iterator.hasNext()) {
                y = iterator.next().calculateYOffset(this.getEntityBoundingBox(), y);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, y, 0.0));
            final boolean b = this.onGround || (n != y && n < 0.0);
            final Iterator<AxisAlignedBB> iterator2 = collidingBoundingBoxes.iterator();
            while (iterator2.hasNext()) {
                calculateXOffset = iterator2.next().calculateXOffset(this.getEntityBoundingBox(), calculateXOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, 0.0, 0.0));
            final Iterator<AxisAlignedBB> iterator3 = collidingBoundingBoxes.iterator();
            while (iterator3.hasNext()) {
                calculateZOffset = iterator3.next().calculateZOffset(this.getEntityBoundingBox(), calculateZOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, calculateZOffset));
            if (this.stepHeight > 0.0f && b && (d3 != calculateXOffset || d4 != calculateZOffset)) {
                final double n3 = calculateXOffset;
                final double n4 = y;
                final double n5 = calculateZOffset;
                final AxisAlignedBB entityBoundingBox2 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(entityBoundingBox);
                y = this.stepHeight;
                final List collidingBoundingBoxes2 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(d3, y, d4));
                final AxisAlignedBB entityBoundingBox3 = this.getEntityBoundingBox();
                final AxisAlignedBB addCoord = entityBoundingBox3.addCoord(d3, 0.0, d4);
                double calculateYOffset = y;
                final Iterator<AxisAlignedBB> iterator4 = collidingBoundingBoxes2.iterator();
                while (iterator4.hasNext()) {
                    calculateYOffset = iterator4.next().calculateYOffset(addCoord, calculateYOffset);
                }
                final AxisAlignedBB offset = entityBoundingBox3.offset(0.0, calculateYOffset, 0.0);
                double calculateXOffset2 = d3;
                final Iterator<AxisAlignedBB> iterator5 = collidingBoundingBoxes2.iterator();
                while (iterator5.hasNext()) {
                    calculateXOffset2 = iterator5.next().calculateXOffset(offset, calculateXOffset2);
                }
                final AxisAlignedBB offset2 = offset.offset(calculateXOffset2, 0.0, 0.0);
                double calculateZOffset2 = d4;
                final Iterator<AxisAlignedBB> iterator6 = collidingBoundingBoxes2.iterator();
                while (iterator6.hasNext()) {
                    calculateZOffset2 = iterator6.next().calculateZOffset(offset2, calculateZOffset2);
                }
                final AxisAlignedBB offset3 = offset2.offset(0.0, 0.0, calculateZOffset2);
                final AxisAlignedBB entityBoundingBox4 = this.getEntityBoundingBox();
                double calculateYOffset2 = y;
                final Iterator<AxisAlignedBB> iterator7 = collidingBoundingBoxes2.iterator();
                while (iterator7.hasNext()) {
                    calculateYOffset2 = iterator7.next().calculateYOffset(entityBoundingBox4, calculateYOffset2);
                }
                final AxisAlignedBB offset4 = entityBoundingBox4.offset(0.0, calculateYOffset2, 0.0);
                double calculateXOffset3 = d3;
                final Iterator<AxisAlignedBB> iterator8 = collidingBoundingBoxes2.iterator();
                while (iterator8.hasNext()) {
                    calculateXOffset3 = iterator8.next().calculateXOffset(offset4, calculateXOffset3);
                }
                final AxisAlignedBB offset5 = offset4.offset(calculateXOffset3, 0.0, 0.0);
                double calculateZOffset3 = d4;
                final Iterator<AxisAlignedBB> iterator9 = collidingBoundingBoxes2.iterator();
                while (iterator9.hasNext()) {
                    calculateZOffset3 = iterator9.next().calculateZOffset(offset5, calculateZOffset3);
                }
                final AxisAlignedBB offset6 = offset5.offset(0.0, 0.0, calculateZOffset3);
                if (calculateXOffset2 * calculateXOffset2 + calculateZOffset2 * calculateZOffset2 > calculateXOffset3 * calculateXOffset3 + calculateZOffset3 * calculateZOffset3) {
                    calculateXOffset = calculateXOffset2;
                    calculateZOffset = calculateZOffset2;
                    y = -calculateYOffset;
                    this.setEntityBoundingBox(offset3);
                }
                else {
                    calculateXOffset = calculateXOffset3;
                    calculateZOffset = calculateZOffset3;
                    y = -calculateYOffset2;
                    this.setEntityBoundingBox(offset6);
                }
                final Iterator<AxisAlignedBB> iterator10 = collidingBoundingBoxes2.iterator();
                while (iterator10.hasNext()) {
                    y = iterator10.next().calculateYOffset(this.getEntityBoundingBox(), y);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, y, 0.0));
                if (n3 * n3 + n5 * n5 >= calculateXOffset * calculateXOffset + calculateZOffset * calculateZOffset) {
                    calculateXOffset = n3;
                    y = n4;
                    calculateZOffset = n5;
                    this.setEntityBoundingBox(entityBoundingBox2);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.resetPositionToBB();
            this.isCollidedHorizontally = (d3 != calculateXOffset || d4 != calculateZOffset);
            this.isCollidedVertically = (n != y);
            this.onGround = (this.isCollidedVertically && n < 0.0);
            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
            BlockPos down = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
            Block block = this.worldObj.getBlockState(down).getBlock();
            if (block.getMaterial() == Material.air) {
                final Block block2 = this.worldObj.getBlockState(down.down()).getBlock();
                if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                    block = block2;
                    down = down.down();
                }
            }
            this.updateFallState(y, this.onGround, block, down);
            if (d3 != calculateXOffset) {
                this.motionX = 0.0;
            }
            if (d4 != calculateZOffset) {
                this.motionZ = 0.0;
            }
            if (n != y) {
                block.onLanded(this.worldObj, this);
            }
            if (this.canTriggerWalking() && !sneaking2 && this.ridingEntity == null) {
                final double n6 = this.posX - posX;
                double n7 = this.posY - posY;
                final double n8 = this.posZ - posZ;
                if (block != Blocks.ladder) {
                    n7 = 0.0;
                }
                if (block != null && this.onGround) {
                    block.onEntityCollidedWithBlock(this.worldObj, down, this);
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(n6 * n6 + n8 * n8) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(n6 * n6 + n7 * n7 + n8 * n8) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && block.getMaterial() != Material.air) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float n9 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (n9 > 1.0f) {
                            n9 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), n9, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.playStepSound(down, block);
                }
            }
            this.doBlockCollisions();
            final boolean wet = this.isWet();
            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(1);
                if (!wet) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (wet && this.fire > 0) {
                this.playSound("random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 0.0f) {
                if (block != null) {
                    block.onFallenUpon(this.worldObj, blockPos, this, this.fallDistance);
                }
                else {
                    this.fall(this.fallDistance, 1.0f);
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        final Block.SoundType stepSound = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
            final Block.SoundType stepSound2 = Blocks.snow_layer.stepSound;
            this.playSound(stepSound2.getStepSound(), stepSound2.getVolume() * 0.15f, stepSound2.getFrequency());
        }
        else if (!block.getMaterial().isLiquid()) {
            this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.15f, stepSound.getFrequency());
        }
    }
    
    private void func_174829_m() {
        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
        this.posY = this.getEntityBoundingBox().minY;
        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
    }
    
    protected String getSwimSound() {
        return "game.neutral.swim";
    }
    
    protected void doBlockCollisions() {
        final BlockPos blockPos = new BlockPos(this.getEntityBoundingBox().minX + 0.001, this.getEntityBoundingBox().minY + 0.001, this.getEntityBoundingBox().minZ + 0.001);
        final BlockPos blockPos2 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001, this.getEntityBoundingBox().maxY - 0.001, this.getEntityBoundingBox().maxZ - 0.001);
        if (this.worldObj.isAreaLoaded(blockPos, blockPos2)) {
            for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                        final BlockPos blockPos3 = new BlockPos(i, j, k);
                        final IBlockState blockState = this.worldObj.getBlockState(blockPos3);
                        blockState.getBlock().onEntityCollidedWithBlock(this.worldObj, blockPos3, blockState, this);
                    }
                }
            }
        }
    }
    
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        final Block.SoundType stepSound = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.offsetUp()).getBlock() == Blocks.snow_layer) {
            final Block.SoundType stepSound2 = Blocks.snow_layer.stepSound;
            this.playSound(stepSound2.getStepSound(), stepSound2.getVolume() * 0.15f, stepSound2.getFrequency());
        }
        else if (!block.getMaterial().isLiquid()) {
            this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.15f, stepSound.getFrequency());
        }
    }
    
    public void playSound(final String s, final float n, final float n2) {
        if (!this.isSlient()) {
            this.worldObj.playSoundAtEntity(this, s, n, n2);
        }
    }
    
    public boolean isSlient() {
        return this.dataWatcher.getWatchableObjectByte(4) == 1;
    }
    
    public void func_174810_b(final boolean b) {
        this.dataWatcher.updateObject(4, (byte)(byte)(b ? 1 : 0));
    }
    
    protected boolean canTriggerWalking() {
        return true;
    }
    
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 0.0f) {
                if (block != null) {
                    block.onFallenUpon(this.worldObj, blockPos, this, this.fallDistance);
                }
                else {
                    this.fall(this.fallDistance, 1.0f);
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    protected void dealFireDamage(final int n) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, (float)n);
        }
    }
    
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }
    
    public void fall(final float n, final float n2) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(n, n2);
        }
    }
    
    public boolean isWet() {
        return this.inWater || this.worldObj.func_175727_C(new BlockPos(this.posX, this.posY, this.posZ)) || this.worldObj.func_175727_C(new BlockPos(this.posX, this.posY + this.height, this.posZ));
    }
    
    public boolean isInWater() {
        return this.inWater;
    }
    
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    protected void resetHeight() {
        float n = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
        if (n > 1.0f) {
            n = 1.0f;
        }
        this.playSound(this.getSplashSound(), n, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        final float n2 = (float)MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int n3 = 0;
        while (0 < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ, new int[0]);
            ++n3;
        }
        while (0 < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY, this.motionZ, new int[0]);
            ++n3;
        }
    }
    
    public void func_174830_Y() {
        if (this.isSprinting() && !this.isInWater()) {
            this.func_174808_Z();
        }
    }
    
    protected void func_174808_Z() {
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ)));
        if (blockState.getBlock().getRenderType() != -1) {
            this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0, Block.getStateId(blockState));
        }
    }
    
    protected String getSplashSound() {
        return "game.neutral.swim.splash";
    }
    
    public boolean isInsideOfMaterial(final Material material) {
        final double n = this.posY + this.getEyeHeight();
        final BlockPos blockPos = new BlockPos(this.posX, n, this.posZ);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        if (blockState.getBlock().getMaterial() == material) {
            final boolean b = n < blockPos.getY() + 1 - (BlockLiquid.getLiquidHeightPercent(blockState.getBlock().getMetaFromState(blockState)) - 0.11111111f);
            return (b || !(this instanceof EntityPlayer)) && b;
        }
        return false;
    }
    
    public boolean func_180799_ab() {
        return this.worldObj.isMaterialInBB(this.getEntityBoundingBox().expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }
    
    public void moveFlying(float n, float n2, final float n3) {
        final float n4 = n * n + n2 * n2;
        if (n4 >= 1.0E-4f) {
            float sqrt_float = MathHelper.sqrt_float(n4);
            if (sqrt_float < 1.0f) {
                sqrt_float = 1.0f;
            }
            final float n5 = n3 / sqrt_float;
            n *= n5;
            n2 *= n5;
            final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += n * cos - n2 * sin;
            this.motionZ += n2 * cos + n * sin;
        }
    }
    
    public int getBrightnessForRender(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, 0.0, this.posZ);
        if (this.worldObj.isBlockLoaded(blockPos)) {
            return this.worldObj.getCombinedLight(blockPos.offsetUp(MathHelper.floor_double(this.posY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 0.66)), 0);
        }
        return 0;
    }
    
    public float getBrightness(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, 0.0, this.posZ);
        if (this.worldObj.isBlockLoaded(blockPos)) {
            return this.worldObj.getLightBrightness(blockPos.offsetUp(MathHelper.floor_double(this.posY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 0.66)));
        }
        return 0.0f;
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public void setPositionAndRotation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.posX = n;
        this.prevPosX = n;
        this.posY = n2;
        this.prevPosY = n2;
        this.posZ = n3;
        this.prevPosZ = n3;
        this.rotationYaw = n4;
        this.prevRotationYaw = n4;
        this.rotationPitch = n5;
        this.prevRotationPitch = n5;
        final double n6 = this.prevRotationYaw - n4;
        if (n6 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (n6 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(n4, n5);
    }
    
    public void func_174828_a(final BlockPos blockPos, final float n, final float n2) {
        this.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, n, n2);
    }
    
    public void setLocationAndAngles(final double lastTickPosX, final double lastTickPosY, final double lastTickPosZ, final float rotationYaw, final float rotationPitch) {
        this.posX = lastTickPosX;
        this.prevPosX = lastTickPosX;
        this.lastTickPosX = lastTickPosX;
        this.posY = lastTickPosY;
        this.prevPosY = lastTickPosY;
        this.lastTickPosY = lastTickPosY;
        this.posZ = lastTickPosZ;
        this.prevPosZ = lastTickPosZ;
        this.lastTickPosZ = lastTickPosZ;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public float getDistanceToEntity(final Entity entity) {
        final float n = (float)(this.posX - entity.posX);
        final float n2 = (float)(this.posY - entity.posY);
        final float n3 = (float)(this.posZ - entity.posZ);
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public double getDistanceSq(final BlockPos blockPos) {
        return blockPos.distanceSq(this.posX, this.posY, this.posZ);
    }
    
    public double func_174831_c(final BlockPos blockPos) {
        return blockPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
    }
    
    public double getDistance(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public double getDistanceSqToEntity(final Entity entity) {
        final double n = this.posX - entity.posX;
        final double n2 = this.posY - entity.posY;
        final double n3 = this.posZ - entity.posZ;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
    }
    
    public void applyEntityCollision(final Entity entity) {
        if (entity.riddenByEntity != this && entity.ridingEntity != this && !entity.noClip && !this.noClip) {
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double abs_max = MathHelper.abs_max(n, n2);
            if (abs_max >= 0.009999999776482582) {
                final double n3 = MathHelper.sqrt_double(abs_max);
                final double n4 = n / n3;
                final double n5 = n2 / n3;
                double n6 = 1.0 / n3;
                if (n6 > 1.0) {
                    n6 = 1.0;
                }
                final double n7 = n4 * n6;
                final double n8 = n5 * n6;
                final double n9 = n7 * 0.05000000074505806;
                final double n10 = n8 * 0.05000000074505806;
                final double n11 = n9 * (1.0f - this.entityCollisionReduction);
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                if (this.riddenByEntity == null) {
                    this.addVelocity(-n11, 0.0, -n12);
                }
                if (entity.riddenByEntity == null) {
                    entity.addVelocity(n11, 0.0, n12);
                }
            }
        }
    }
    
    public void addVelocity(final double n, final double n2, final double n3) {
        this.motionX += n;
        this.motionY += n2;
        this.motionZ += n3;
        this.isAirBorne = true;
    }
    
    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }
    
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }
    
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.func_174806_f(this.rotationPitch, this.rotationYaw);
        }
        return this.func_174806_f(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * n);
    }
    
    protected final Vec3 func_174806_f(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3(sin * n3, MathHelper.sin(-n * 0.017453292f), cos * n3);
    }
    
    public Vec3 func_174824_e(final float n) {
        if (n == 1.0f) {
            return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        }
        return new Vec3(this.prevPosX + (this.posX - this.prevPosX) * n, this.prevPosY + (this.posY - this.prevPosY) * n + this.getEyeHeight(), this.prevPosZ + (this.posZ - this.prevPosZ) * n);
    }
    
    public MovingObjectPosition func_174822_a(final double n, final float n2) {
        final Vec3 func_174824_e = this.func_174824_e(n2);
        final Vec3 look = this.getLook(n2);
        return this.worldObj.rayTraceBlocks(func_174824_e, func_174824_e.addVector(look.xCoord * n, look.yCoord * n, look.zCoord * n), false, false, true);
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void addToPlayerScore(final Entity entity, final int n) {
    }
    
    public boolean isInRangeToRender3d(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return this.isInRangeToRenderDist(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * (64.0 * this.renderDistanceWeight);
        return n < n2 * n2;
    }
    
    public boolean writeMountToNBT(final NBTTagCompound nbtTagCompound) {
        final String entityString = this.getEntityString();
        if (!this.isDead && entityString != null) {
            nbtTagCompound.setString("id", entityString);
            this.writeToNBT(nbtTagCompound);
            return true;
        }
        return false;
    }
    
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        final String entityString = this.getEntityString();
        if (!this.isDead && entityString != null && this.riddenByEntity == null) {
            nbtTagCompound.setString("id", entityString);
            this.writeToNBT(nbtTagCompound);
            return true;
        }
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
        nbtTagCompound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        nbtTagCompound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
        nbtTagCompound.setFloat("FallDistance", this.fallDistance);
        nbtTagCompound.setShort("Fire", (short)this.fire);
        nbtTagCompound.setShort("Air", (short)this.getAir());
        nbtTagCompound.setBoolean("OnGround", this.onGround);
        nbtTagCompound.setInteger("Dimension", this.dimension);
        nbtTagCompound.setBoolean("Invulnerable", this.invulnerable);
        nbtTagCompound.setInteger("PortalCooldown", this.timeUntilPortal);
        nbtTagCompound.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
        nbtTagCompound.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
        if (this.getCustomNameTag() != null && this.getCustomNameTag().length() > 0) {
            nbtTagCompound.setString("CustomName", this.getCustomNameTag());
            nbtTagCompound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        }
        this.field_174837_as.func_179670_b(nbtTagCompound);
        if (this.isSlient()) {
            nbtTagCompound.setBoolean("Silent", this.isSlient());
        }
        this.writeEntityToNBT(nbtTagCompound);
        if (this.ridingEntity != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            if (this.ridingEntity.writeMountToNBT(nbtTagCompound2)) {
                nbtTagCompound.setTag("Riding", nbtTagCompound2);
            }
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Pos", 6);
        final NBTTagList tagList2 = nbtTagCompound.getTagList("Motion", 6);
        final NBTTagList tagList3 = nbtTagCompound.getTagList("Rotation", 5);
        this.motionX = tagList2.getDouble(0);
        this.motionY = tagList2.getDouble(1);
        this.motionZ = tagList2.getDouble(2);
        if (Math.abs(this.motionX) > 10.0) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) > 10.0) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) > 10.0) {
            this.motionZ = 0.0;
        }
        final double double1 = tagList.getDouble(0);
        this.posX = double1;
        this.lastTickPosX = double1;
        this.prevPosX = double1;
        final double double2 = tagList.getDouble(1);
        this.posY = double2;
        this.lastTickPosY = double2;
        this.prevPosY = double2;
        final double double3 = tagList.getDouble(2);
        this.posZ = double3;
        this.lastTickPosZ = double3;
        this.prevPosZ = double3;
        final float float1 = tagList3.getFloat(0);
        this.rotationYaw = float1;
        this.prevRotationYaw = float1;
        final float float2 = tagList3.getFloat(1);
        this.rotationPitch = float2;
        this.prevRotationPitch = float2;
        this.fallDistance = nbtTagCompound.getFloat("FallDistance");
        this.fire = nbtTagCompound.getShort("Fire");
        this.setAir(nbtTagCompound.getShort("Air"));
        this.onGround = nbtTagCompound.getBoolean("OnGround");
        this.dimension = nbtTagCompound.getInteger("Dimension");
        this.invulnerable = nbtTagCompound.getBoolean("Invulnerable");
        this.timeUntilPortal = nbtTagCompound.getInteger("PortalCooldown");
        if (nbtTagCompound.hasKey("UUIDMost", 4) && nbtTagCompound.hasKey("UUIDLeast", 4)) {
            this.entityUniqueID = new UUID(nbtTagCompound.getLong("UUIDMost"), nbtTagCompound.getLong("UUIDLeast"));
        }
        else if (nbtTagCompound.hasKey("UUID", 8)) {
            this.entityUniqueID = UUID.fromString(nbtTagCompound.getString("UUID"));
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        if (nbtTagCompound.hasKey("CustomName", 8) && nbtTagCompound.getString("CustomName").length() > 0) {
            this.setCustomNameTag(nbtTagCompound.getString("CustomName"));
        }
        this.setAlwaysRenderNameTag(nbtTagCompound.getBoolean("CustomNameVisible"));
        this.field_174837_as.func_179668_a(nbtTagCompound);
        this.func_174810_b(nbtTagCompound.getBoolean("Silent"));
        this.readEntityFromNBT(nbtTagCompound);
        if (this.shouldSetPosAfterLoading()) {
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }
    
    protected boolean shouldSetPosAfterLoading() {
        return true;
    }
    
    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }
    
    protected abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    protected abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    public void onChunkLoad() {
    }
    
    protected NBTTagList newDoubleNBTList(final double... array) {
        final NBTTagList list = new NBTTagList();
        while (0 < array.length) {
            list.appendTag(new NBTTagDouble(array[0]));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    protected NBTTagList newFloatNBTList(final float... array) {
        final NBTTagList list = new NBTTagList();
        while (0 < array.length) {
            list.appendTag(new NBTTagFloat(array[0]));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public EntityItem dropItem(final Item item, final int n) {
        return this.dropItemWithOffset(item, n, 0.0f);
    }
    
    public EntityItem dropItemWithOffset(final Item item, final int n, final float n2) {
        return this.entityDropItem(new ItemStack(item, n, 0), n2);
    }
    
    public EntityItem entityDropItem(final ItemStack itemStack, final float n) {
        if (itemStack.stackSize != 0 && itemStack.getItem() != null) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY + n, this.posZ, itemStack);
            entityItem.setDefaultPickupDelay();
            this.worldObj.spawnEntityInWorld(entityItem);
            return entityItem;
        }
        return null;
    }
    
    public boolean isEntityAlive() {
        return !this.isDead;
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return false;
        }
        while (0 < 8) {
            if (this.worldObj.getBlockState(new BlockPos(this.posX + (0 - 0.5f) * this.width * 0.8f, this.posY + (0 - 0.5f) * 0.1f + this.getEyeHeight(), this.posZ + (0 - 0.5f) * this.width * 0.8f)).getBlock().isVisuallyOpaque()) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return null;
    }
    
    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.ridingEntity != null) {
                this.ridingEntity.updateRiderPosition();
                this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
                this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch;
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double n = this.entityRiderYawDelta * 0.5;
                double n2 = this.entityRiderPitchDelta * 0.5;
                final float n3 = 10.0f;
                if (n > n3) {
                    n = n3;
                }
                if (n < -n3) {
                    n = -n3;
                }
                if (n2 > n3) {
                    n2 = n3;
                }
                if (n2 < -n3) {
                    n2 = -n3;
                }
                this.entityRiderYawDelta -= n;
                this.entityRiderPitchDelta -= n2;
            }
        }
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    public double getYOffset() {
        return 0.0;
    }
    
    public double getMountedYOffset() {
        return this.height * 0.75;
    }
    
    public void mountEntity(final Entity ridingEntity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (ridingEntity == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (ridingEntity != null) {
                for (Entity entity = ridingEntity.ridingEntity; entity != null; entity = entity.ridingEntity) {
                    if (entity == this) {
                        return;
                    }
                }
            }
            this.ridingEntity = ridingEntity;
            ridingEntity.riddenByEntity = this;
        }
    }
    
    public void func_180426_a(final double n, double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        this.setPosition(n, n2, n3);
        this.setRotation(n4, n5);
        final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().contract(0.03125, 0.0, 0.03125));
        if (!collidingBoundingBoxes.isEmpty()) {
            double maxY = 0.0;
            for (final AxisAlignedBB axisAlignedBB : collidingBoundingBoxes) {
                if (axisAlignedBB.maxY > maxY) {
                    maxY = axisAlignedBB.maxY;
                }
            }
            n2 += maxY - this.getEntityBoundingBox().minY;
            this.setPosition(n, n2, n3);
        }
    }
    
    public float getCollisionBorderSize() {
        return 0.1f;
    }
    
    public Vec3 getLookVec() {
        return null;
    }
    
    public void setInPortal() {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        }
        else {
            final double n = this.prevPosX - this.posX;
            final double n2 = this.prevPosZ - this.posZ;
            if (!this.worldObj.isRemote && !this.inPortal) {
                int teleportDirection;
                if (MathHelper.abs((float)n) > MathHelper.abs((float)n2)) {
                    teleportDirection = ((n > 0.0) ? EnumFacing.WEST.getHorizontalIndex() : EnumFacing.EAST.getHorizontalIndex());
                }
                else {
                    teleportDirection = ((n2 > 0.0) ? EnumFacing.NORTH.getHorizontalIndex() : EnumFacing.SOUTH.getHorizontalIndex());
                }
                this.teleportDirection = teleportDirection;
            }
            this.inPortal = true;
        }
    }
    
    public int getPortalCooldown() {
        return 300;
    }
    
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public void handleHealthUpdate(final byte b) {
    }
    
    public void performHurtAnimation() {
    }
    
    public ItemStack[] getInventory() {
        return null;
    }
    
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
    }
    
    public boolean isBurning() {
        final boolean b = this.worldObj != null && this.worldObj.isRemote;
        return !this.isImmuneToFire && (this.fire > 0 || (b && this.getFlag(0)));
    }
    
    public boolean isRiding() {
        return this.ridingEntity != null;
    }
    
    public boolean isSneaking() {
        return this.getFlag(1);
    }
    
    public void setSneaking(final boolean b) {
        this.setFlag(1, b);
    }
    
    public boolean isSprinting() {
        return this.getFlag(3);
    }
    
    public void setSprinting(final boolean b) {
        this.setFlag(3, b);
    }
    
    public boolean isInvisible() {
        return this.getFlag(5);
    }
    
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        return !entityPlayer.func_175149_v() && this.isInvisible();
    }
    
    public void setInvisible(final boolean b) {
        this.setFlag(5, b);
    }
    
    public boolean isEating() {
        return this.getFlag(4);
    }
    
    public void setEating(final boolean b) {
        this.setFlag(4, b);
    }
    
    protected boolean getFlag(final int n) {
        return (this.dataWatcher.getWatchableObjectByte(0) & 1 << n) != 0x0;
    }
    
    protected void setFlag(final int n, final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0);
        if (b) {
            this.dataWatcher.updateObject(0, (byte)(watchableObjectByte | 1 << n));
        }
        else {
            this.dataWatcher.updateObject(0, (byte)(watchableObjectByte & ~(1 << n)));
        }
    }
    
    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }
    
    public void setAir(final int n) {
        this.dataWatcher.updateObject(1, (short)n);
    }
    
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        this.attackEntityFrom(DamageSource.field_180137_b, 5.0f);
        ++this.fire;
        if (this.fire == 0) {
            this.setFire(8);
        }
    }
    
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
    }
    
    protected boolean pushOutOfBlocks(final double n, final double n2, final double n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final double n4 = n - blockPos.getX();
        final double n5 = n2 - blockPos.getY();
        final double n6 = n3 - blockPos.getZ();
        if (this.worldObj.func_147461_a(this.getEntityBoundingBox()).isEmpty() && !this.worldObj.func_175665_u(blockPos)) {
            return false;
        }
        double n7 = 9999.0;
        if (!this.worldObj.func_175665_u(blockPos.offsetWest()) && n4 < n7) {
            n7 = n4;
        }
        if (!this.worldObj.func_175665_u(blockPos.offsetEast()) && 1.0 - n4 < n7) {
            n7 = 1.0 - n4;
        }
        if (!this.worldObj.func_175665_u(blockPos.offsetUp()) && 1.0 - n5 < n7) {
            n7 = 1.0 - n5;
        }
        if (!this.worldObj.func_175665_u(blockPos.offsetNorth()) && n6 < n7) {
            n7 = n6;
        }
        if (!this.worldObj.func_175665_u(blockPos.offsetSouth()) && 1.0 - n6 < n7) {}
        final float n8 = this.rand.nextFloat() * 0.2f + 0.1f;
        if (5 == 0) {
            this.motionX = -n8;
        }
        if (5 == 1) {
            this.motionX = n8;
        }
        if (5 == 3) {
            this.motionY = n8;
        }
        if (5 == 4) {
            this.motionZ = -n8;
        }
        if (5 == 5) {
            this.motionZ = n8;
        }
        return true;
    }
    
    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }
    
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        String entityString = EntityList.getEntityString(this);
        if (entityString == null) {
            entityString = "generic";
        }
        return StatCollector.translateToLocal("entity." + entityString + ".name");
    }
    
    public Entity[] getParts() {
        return null;
    }
    
    public boolean isEntityEqual(final Entity entity) {
        return this == entity;
    }
    
    public float getRotationYawHead() {
        return 0.0f;
    }
    
    public void setRotationYawHead(final float n) {
    }
    
    public boolean canAttackWithItem() {
        return true;
    }
    
    public boolean hitByEntity(final Entity entity) {
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getName(), this.entityId, (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
    }
    
    public boolean func_180431_b(final DamageSource damageSource) {
        return this.invulnerable && damageSource != DamageSource.outOfWorld && !damageSource.func_180136_u();
    }
    
    public void copyLocationAndAnglesFrom(final Entity entity) {
        this.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
    }
    
    public void func_180432_n(final Entity entity) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entity.writeToNBT(nbtTagCompound);
        this.readFromNBT(nbtTagCompound);
        this.timeUntilPortal = entity.timeUntilPortal;
        this.teleportDirection = entity.teleportDirection;
    }
    
    public void travelToDimension(final int dimension) {
        if (!this.worldObj.isRemote && !this.isDead) {
            this.worldObj.theProfiler.startSection("changeDimension");
            final MinecraftServer server = MinecraftServer.getServer();
            final int dimension2 = this.dimension;
            final WorldServer worldServerForDimension = server.worldServerForDimension(dimension2);
            WorldServer worldServer = server.worldServerForDimension(dimension);
            this.dimension = dimension;
            if (dimension2 == 1 && dimension == 1) {
                worldServer = server.worldServerForDimension(0);
                this.dimension = 0;
            }
            this.worldObj.removeEntity(this);
            this.isDead = false;
            this.worldObj.theProfiler.startSection("reposition");
            server.getConfigurationManager().transferEntityToWorld(this, dimension2, worldServerForDimension, worldServer);
            this.worldObj.theProfiler.endStartSection("reloading");
            final Entity entityByName = EntityList.createEntityByName(EntityList.getEntityString(this), worldServer);
            if (entityByName != null) {
                entityByName.func_180432_n(this);
                if (dimension2 == 1 && dimension == 1) {
                    entityByName.func_174828_a(this.worldObj.func_175672_r(worldServer.getSpawnPoint()), entityByName.rotationYaw, entityByName.rotationPitch);
                }
                worldServer.spawnEntityInWorld(entityByName);
            }
            this.isDead = true;
            this.worldObj.theProfiler.endSection();
            worldServerForDimension.resetUpdateEntityTick();
            worldServer.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        return blockState.getBlock().getExplosionResistance(this);
    }
    
    public boolean func_174816_a(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState, final float n) {
        return true;
    }
    
    public int getMaxFallHeight() {
        return 3;
    }
    
    public int getTeleportDirection() {
        return this.teleportDirection;
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }
    
    public void addEntityCrashInfo(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Entity Type", new Callable() {
            private static final String __OBFID;
            final Entity this$0;
            
            @Override
            public String call() {
                return String.valueOf(EntityList.getEntityString(this.this$0)) + " (" + this.this$0.getClass().getCanonicalName() + ")";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001534";
            }
        });
        crashReportCategory.addCrashSection("Entity ID", this.entityId);
        crashReportCategory.addCrashSectionCallable("Entity Name", new Callable() {
            private static final String __OBFID;
            final Entity this$0;
            
            @Override
            public String call() {
                return this.this$0.getName();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001535";
            }
        });
        crashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        crashReportCategory.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        crashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
        crashReportCategory.addCrashSectionCallable("Entity's Rider", new Callable() {
            private static final String __OBFID;
            final Entity this$0;
            
            public String func_180118_a() {
                return this.this$0.riddenByEntity.toString();
            }
            
            @Override
            public Object call() {
                return this.func_180118_a();
            }
            
            static {
                __OBFID = "CL_00002259";
            }
        });
        crashReportCategory.addCrashSectionCallable("Entity's Vehicle", new Callable() {
            private static final String __OBFID;
            final Entity this$0;
            
            public String func_180116_a() {
                return this.this$0.ridingEntity.toString();
            }
            
            @Override
            public Object call() {
                return this.func_180116_a();
            }
            
            static {
                __OBFID = "CL_00002258";
            }
        });
    }
    
    public boolean canRenderOnFire() {
        return this.isBurning();
    }
    
    public UUID getUniqueID() {
        return this.entityUniqueID;
    }
    
    public boolean isPushedByWater() {
        return true;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getName());
        chatComponentText.getChatStyle().setChatHoverEvent(this.func_174823_aP());
        chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentText;
    }
    
    public void setCustomNameTag(final String s) {
        this.dataWatcher.updateObject(2, s);
    }
    
    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(2);
    }
    
    public boolean hasCustomName() {
        return this.dataWatcher.getWatchableObjectString(2).length() > 0;
    }
    
    public void setAlwaysRenderNameTag(final boolean b) {
        this.dataWatcher.updateObject(3, (byte)(byte)(b ? 1 : 0));
    }
    
    public boolean getAlwaysRenderNameTag() {
        return this.dataWatcher.getWatchableObjectByte(3) == 1;
    }
    
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }
    
    public void func_145781_i(final int n) {
    }
    
    public EnumFacing func_174811_aO() {
        return EnumFacing.getHorizontal(MathHelper.floor_double(this.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    protected HoverEvent func_174823_aP() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final String entityString = EntityList.getEntityString(this);
        nbtTagCompound.setString("id", this.getUniqueID().toString());
        if (entityString != null) {
            nbtTagCompound.setString("type", entityString);
        }
        nbtTagCompound.setString("name", this.getName());
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(nbtTagCompound.toString()));
    }
    
    public boolean func_174827_a(final EntityPlayerMP entityPlayerMP) {
        return true;
    }
    
    public AxisAlignedBB getEntityBoundingBox() {
        return this.boundingBox;
    }
    
    public void func_174826_a(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public float getEyeHeight() {
        return this.height * 0.85f;
    }
    
    public boolean isOutsideBorder() {
        return this.isOutsideBorder;
    }
    
    public void setOutsideBorder(final boolean isOutsideBorder) {
        this.isOutsideBorder = isOutsideBorder;
    }
    
    public boolean func_174820_d(final int n, final ItemStack itemStack) {
        return false;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return true;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(this.posX, this.posY, this.posZ);
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return this;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return false;
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type type, final int n) {
        this.field_174837_as.func_179672_a(this, type, n);
    }
    
    public CommandResultStats func_174807_aT() {
        return this.field_174837_as;
    }
    
    public void func_174817_o(final Entity entity) {
        this.field_174837_as.func_179671_a(entity.func_174807_aT());
    }
    
    public NBTTagCompound func_174819_aU() {
        return null;
    }
    
    public void func_174834_g(final NBTTagCompound nbtTagCompound) {
    }
    
    public boolean func_174825_a(final EntityPlayer entityPlayer, final Vec3 vec3) {
        return false;
    }
    
    public boolean func_180427_aV() {
        return false;
    }
    
    protected void func_174815_a(final EntityLivingBase entityLivingBase, final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EnchantmentHelper.func_151384_a((EntityLivingBase)entity, entityLivingBase);
        }
        EnchantmentHelper.func_151385_b(entityLivingBase, entity);
    }
}
