package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int xTile;
    private int yTile;
    private int zTile;
    private Block field_174853_f;
    protected boolean field_174854_a;
    public int throwableShake;
    private EntityLivingBase thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    private static final String __OBFID;
    
    public EntityThrowable(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        final double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0 * 64.0;
        return n < n2 * n2;
    }
    
    public EntityThrowable(final World world, final EntityLivingBase thrower) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.thrower = thrower;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0f * 3.1415927f) * n;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0f);
    }
    
    public EntityThrowable(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.ticksInGround = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    protected float func_70182_d() {
        return 1.5f;
    }
    
    protected float func_70183_g() {
        return 0.0f;
    }
    
    @Override
    public void setThrowableHeading(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * 0.007499999832361937 * n2;
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
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        if (this.field_174854_a) {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.field_174853_f) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
                return;
            }
            this.field_174854_a = false;
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        }
        else {
            ++this.ticksInAir;
        }
        MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY, this.posZ), new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ));
        final Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
        Vec3 vec4 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (rayTraceBlocks != null) {
            vec4 = new Vec3(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
        }
        if (!this.worldObj.isRemote) {
            Entity entity = null;
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n = 0.0;
            final EntityLivingBase thrower = this.getThrower();
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(0);
                if (entity2.canBeCollidedWith() && (entity2 != thrower || this.ticksInAir >= 5)) {
                    final float n2 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n2, n2, n2).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double distanceTo = vec3.distanceTo(calculateIntercept.hitVec);
                        if (distanceTo < n || n == 0.0) {
                            entity = entity2;
                            n = distanceTo;
                        }
                    }
                }
                int n3 = 0;
                ++n3;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
        }
        if (rayTraceBlocks != null) {
            if (rayTraceBlocks.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(rayTraceBlocks.func_178782_a()).getBlock() == Blocks.portal) {
                this.setInPortal();
            }
            else {
                this.onImpact(rayTraceBlocks);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
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
        float n4 = 0.99f;
        final float gravityVelocity = this.getGravityVelocity();
        if (this.isInWater()) {
            while (0 < 4) {
                final float n5 = 0.25f;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n5, this.posY - this.motionY * n5, this.posZ - this.motionZ * n5, this.motionX, this.motionY, this.motionZ, new int[0]);
                int n6 = 0;
                ++n6;
            }
            n4 = 0.8f;
        }
        this.motionX *= n4;
        this.motionY *= n4;
        this.motionZ *= n4;
        this.motionY -= gravityVelocity;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    protected float getGravityVelocity() {
        return 0.03f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.xTile);
        nbtTagCompound.setShort("yTile", (short)this.yTile);
        nbtTagCompound.setShort("zTile", (short)this.zTile);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.field_174853_f);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("shake", (byte)this.throwableShake);
        nbtTagCompound.setByte("inGround", (byte)(this.field_174854_a ? 1 : 0));
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getName();
        }
        nbtTagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.field_174853_f = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.field_174853_f = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.throwableShake = (nbtTagCompound.getByte("shake") & 0xFF);
        this.field_174854_a = (nbtTagCompound.getByte("inGround") == 1);
        this.throwerName = nbtTagCompound.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
    }
    
    public EntityLivingBase getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
        }
        return this.thrower;
    }
    
    static {
        __OBFID = "CL_00001723";
    }
}
