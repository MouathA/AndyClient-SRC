package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityArrow extends Entity implements IProjectile
{
    private int field_145791_d;
    private int field_145792_e;
    private int field_145789_f;
    private Block field_145790_g;
    private int inData;
    public boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage;
    private int knockbackStrength;
    private static final String __OBFID;
    
    public EntityArrow(final World world) {
        super(world);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityArrow(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(n, n2, n3);
    }
    
    public EntityArrow(final World world, final EntityLivingBase shootingEntity, final EntityLivingBase entityLivingBase, final float n, final float n2) {
        super(world);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = shootingEntity;
        if (shootingEntity instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.posY = shootingEntity.posY + shootingEntity.getEyeHeight() - 0.10000000149011612;
        final double n3 = entityLivingBase.posX - shootingEntity.posX;
        final double n4 = entityLivingBase.getEntityBoundingBox().minY + entityLivingBase.height / 3.0f - this.posY;
        final double n5 = entityLivingBase.posZ - shootingEntity.posZ;
        final double n6 = MathHelper.sqrt_double(n3 * n3 + n5 * n5);
        if (n6 >= 1.0E-7) {
            this.setLocationAndAngles(shootingEntity.posX + n3 / n6, this.posY, shootingEntity.posZ + n5 / n6, (float)(Math.atan2(n5, n3) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(n4, n6) * 180.0 / 3.141592653589793)));
            this.setThrowableHeading(n3, n4 + (float)(n6 * 0.20000000298023224), n5, n, n2);
        }
    }
    
    public EntityArrow(final World world, final EntityLivingBase shootingEntity, final float n) {
        super(world);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = shootingEntity;
        if (shootingEntity instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY + shootingEntity.getEyeHeight(), shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, n * 1.5f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void setThrowableHeading(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n3 = (float)(Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n3;
        this.prevRotationYaw = n3;
        final float n4 = (float)(Math.atan2(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n4;
        this.prevRotationPitch = n4;
        this.ticksInGround = 0;
    }
    
    @Override
    public void func_180426_a(final double n, final double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        this.setPosition(n, n2, n3);
        this.setRotation(n4, n5);
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
        final BlockPos blockPos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, blockPos);
            final AxisAlignedBB collisionBoundingBox = block.getCollisionBoundingBox(this.worldObj, blockPos, blockState);
            if (collisionBoundingBox != null && collisionBoundingBox.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            final int metaFromState = block.getMetaFromState(blockState);
            if (block == this.field_145790_g && metaFromState == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround >= 1200) {
                    this.setDead();
                }
            }
            else {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else {
            ++this.ticksInAir;
            MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY, this.posZ), new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ), false, true, false);
            final Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
            Vec3 vec4 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (rayTraceBlocks != null) {
                vec4 = new Vec3(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
            }
            Entity entity = null;
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n3 = 0.0;
            int n5 = 0;
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(0);
                if (entity2.canBeCollidedWith() && (entity2 != this.shootingEntity || this.ticksInAir >= 5)) {
                    final float n4 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n4, n4, n4).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double distanceTo = vec3.distanceTo(calculateIntercept.hitVec);
                        if (distanceTo < n3 || n3 == 0.0) {
                            entity = entity2;
                            n3 = distanceTo;
                        }
                    }
                }
                ++n5;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
            if (rayTraceBlocks != null && rayTraceBlocks.entityHit != null && rayTraceBlocks.entityHit instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)rayTraceBlocks.entityHit;
                if (entityPlayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityPlayer))) {
                    rayTraceBlocks = null;
                }
            }
            int ceiling_double_int = 0;
            if (rayTraceBlocks != null) {
                if (rayTraceBlocks.entityHit != null) {
                    ceiling_double_int = MathHelper.ceiling_double_int(MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * this.damage);
                    if (this.getIsCritical()) {
                        ceiling_double_int = 0 + this.rand.nextInt(2);
                    }
                    DamageSource damageSource;
                    if (this.shootingEntity == null) {
                        damageSource = DamageSource.causeArrowDamage(this, this);
                    }
                    else {
                        damageSource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }
                    if (this.isBurning() && !(rayTraceBlocks.entityHit instanceof EntityEnderman)) {
                        rayTraceBlocks.entityHit.setFire(5);
                    }
                    if (rayTraceBlocks.entityHit.attackEntityFrom(damageSource, 0)) {
                        if (rayTraceBlocks.entityHit instanceof EntityLivingBase) {
                            final EntityLivingBase entityLivingBase = (EntityLivingBase)rayTraceBlocks.entityHit;
                            if (!this.worldObj.isRemote) {
                                entityLivingBase.setArrowCountInEntity(entityLivingBase.getArrowCountInEntity() + 1);
                            }
                            if (this.knockbackStrength > 0) {
                                final float sqrt_double2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (sqrt_double2 > 0.0f) {
                                    rayTraceBlocks.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579 / sqrt_double2, 0.1, this.motionZ * this.knockbackStrength * 0.6000000238418579 / sqrt_double2);
                                }
                            }
                            if (this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(entityLivingBase, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entityLivingBase);
                            }
                            if (this.shootingEntity != null && rayTraceBlocks.entityHit != this.shootingEntity && rayTraceBlocks.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0f));
                            }
                        }
                        this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                        if (!(rayTraceBlocks.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    }
                    else {
                        this.motionX *= -0.10000000149011612;
                        this.motionY *= -0.10000000149011612;
                        this.motionZ *= -0.10000000149011612;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                }
                else {
                    final BlockPos func_178782_a = rayTraceBlocks.func_178782_a();
                    this.field_145791_d = func_178782_a.getX();
                    this.field_145792_e = func_178782_a.getY();
                    this.field_145789_f = func_178782_a.getZ();
                    final IBlockState blockState2 = this.worldObj.getBlockState(func_178782_a);
                    this.field_145790_g = blockState2.getBlock();
                    this.inData = this.field_145790_g.getMetaFromState(blockState2);
                    this.motionX = (float)(rayTraceBlocks.hitVec.xCoord - this.posX);
                    this.motionY = (float)(rayTraceBlocks.hitVec.yCoord - this.posY);
                    this.motionZ = (float)(rayTraceBlocks.hitVec.zCoord - this.posZ);
                    final float sqrt_double3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / sqrt_double3 * 0.05000000074505806;
                    this.posY -= this.motionY / sqrt_double3 * 0.05000000074505806;
                    this.posZ -= this.motionZ / sqrt_double3 * 0.05000000074505806;
                    this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                    if (this.field_145790_g.getMaterial() != Material.air) {
                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, func_178782_a, blockState2, this);
                    }
                }
            }
            if (this.getIsCritical()) {
                while (0 < 4) {
                    this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * 0 / 4.0, this.posY + this.motionY * 0 / 4.0, this.posZ + this.motionZ * 0 / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ, new int[0]);
                    ++n5;
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float sqrt_double4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float)(Math.atan2(this.motionY, sqrt_double4) * 180.0 / 3.141592653589793);
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float n6 = 0.99f;
            final float n7 = 0.05f;
            if (this.isInWater()) {
                while (0 < 4) {
                    final float n8 = 0.25f;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n8, this.posY - this.motionY * n8, this.posZ - this.motionZ * n8, this.motionX, this.motionY, this.motionZ, new int[0]);
                    ++ceiling_double_int;
                }
                n6 = 0.6f;
            }
            if (this.isWet()) {
                this.extinguish();
            }
            this.motionX *= n6;
            this.motionY *= n6;
            this.motionZ *= n6;
            this.motionY -= n7;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.field_145791_d);
        nbtTagCompound.setShort("yTile", (short)this.field_145792_e);
        nbtTagCompound.setShort("zTile", (short)this.field_145789_f);
        nbtTagCompound.setShort("life", (short)this.ticksInGround);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.field_145790_g);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("inData", (byte)this.inData);
        nbtTagCompound.setByte("shake", (byte)this.arrowShake);
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbtTagCompound.setByte("pickup", (byte)this.canBePickedUp);
        nbtTagCompound.setDouble("damage", this.damage);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.field_145791_d = nbtTagCompound.getShort("xTile");
        this.field_145792_e = nbtTagCompound.getShort("yTile");
        this.field_145789_f = nbtTagCompound.getShort("zTile");
        this.ticksInGround = nbtTagCompound.getShort("life");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.field_145790_g = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.field_145790_g = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.inData = (nbtTagCompound.getByte("inData") & 0xFF);
        this.arrowShake = (nbtTagCompound.getByte("shake") & 0xFF);
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
        if (nbtTagCompound.hasKey("damage", 99)) {
            this.damage = nbtTagCompound.getDouble("damage");
        }
        if (nbtTagCompound.hasKey("pickup", 99)) {
            this.canBePickedUp = nbtTagCompound.getByte("pickup");
        }
        else if (nbtTagCompound.hasKey("player", 99)) {
            this.canBePickedUp = (nbtTagCompound.getBoolean("player") ? 1 : 0);
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            final boolean b = this.canBePickedUp == 1 || (this.canBePickedUp == 2 && entityPlayer.capabilities.isCreativeMode);
            if (this.canBePickedUp != 1 || !entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {}
            if (false) {
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public void setDamage(final double damage) {
        this.damage = damage;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public void setKnockbackStrength(final int knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    public void setIsCritical(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFE));
        }
    }
    
    public boolean getIsCritical() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    static {
        __OBFID = "CL_00001715";
    }
}
