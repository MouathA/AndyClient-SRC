package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityXPOrb extends Entity
{
    public int xpColor;
    public int xpOrbAge;
    public int field_70532_c;
    private int xpOrbHealth;
    private int xpValue;
    private EntityPlayer closestPlayer;
    private int xpTargetColor;
    private static final String __OBFID;
    
    public EntityXPOrb(final World world, final double n, final double n2, final double n3, final int xpValue) {
        super(world);
        this.xpOrbHealth = 5;
        this.setSize(0.5f, 0.5f);
        this.setPosition(n, n2, n3);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.motionY = (float)(Math.random() * 0.2) * 2.0f;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.xpValue = xpValue;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityXPOrb(final World world) {
        super(world);
        this.xpOrbHealth = 5;
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final float clamp_float = MathHelper.clamp_float(0.5f, 0.0f, 1.0f);
        final int n2 = super.getBrightnessForRender(n) >> 16 & 0xFF;
        final int n3 = 240 + (int)(clamp_float * 15.0f * 16.0f);
        if (240 > 240) {}
        return 0xF0 | n2 << 16;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_70532_c > 0) {
            --this.field_70532_c;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
        final double n = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > n * n) {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, n);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null && this.closestPlayer.func_175149_v()) {
            this.closestPlayer = null;
        }
        if (this.closestPlayer != null) {
            final double n2 = (this.closestPlayer.posX - this.posX) / n;
            final double n3 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / n;
            final double n4 = (this.closestPlayer.posZ - this.posZ) / n;
            final double sqrt = Math.sqrt(n2 * n2 + n3 * n3 + n4 * n4);
            final double n5 = 1.0 - sqrt;
            if (n5 > 0.0) {
                final double n6 = n5 * n5;
                this.motionX += n2 / sqrt * n6 * 0.1;
                this.motionY += n3 / sqrt * n6 * 0.1;
                this.motionZ += n4 / sqrt * n6 * 0.1;
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float n7 = 0.98f;
        if (this.onGround) {
            n7 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98f;
        }
        this.motionX *= n7;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= n7;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }
        ++this.xpColor;
        ++this.xpOrbAge;
        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }
    
    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this);
    }
    
    @Override
    protected void dealFireDamage(final int n) {
        this.attackEntityFrom(DamageSource.inFire, (float)n);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.setBeenAttacked();
        this.xpOrbHealth -= (int)n;
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("Health", (byte)this.xpOrbHealth);
        nbtTagCompound.setShort("Age", (short)this.xpOrbAge);
        nbtTagCompound.setShort("Value", (short)this.xpValue);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xpOrbHealth = (nbtTagCompound.getShort("Health") & 0xFF);
        this.xpOrbAge = nbtTagCompound.getShort("Age");
        this.xpValue = nbtTagCompound.getShort("Value");
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && this.field_70532_c == 0 && entityPlayer.xpCooldown == 0) {
            entityPlayer.xpCooldown = 2;
            this.worldObj.playSoundAtEntity(entityPlayer, "random.orb", 0.1f, 0.5f * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.8f));
            entityPlayer.onItemPickup(this, 1);
            entityPlayer.addExperience(this.xpValue);
            this.setDead();
        }
    }
    
    public int getXpValue() {
        return this.xpValue;
    }
    
    public int getTextureByXP() {
        return (this.xpValue >= 2477) ? 10 : ((this.xpValue >= 1237) ? 9 : ((this.xpValue >= 617) ? 8 : ((this.xpValue >= 307) ? 7 : ((this.xpValue >= 149) ? 6 : ((this.xpValue >= 73) ? 5 : ((this.xpValue >= 37) ? 4 : ((this.xpValue >= 17) ? 3 : ((this.xpValue >= 7) ? 2 : ((this.xpValue >= 3) ? 1 : 0)))))))));
    }
    
    public static int getXPSplit(final int n) {
        return (n >= 2477) ? 2477 : ((n >= 1237) ? 1237 : ((n >= 617) ? 617 : ((n >= 307) ? 307 : ((n >= 149) ? 149 : ((n >= 73) ? 73 : ((n >= 37) ? 37 : ((n >= 17) ? 17 : ((n >= 7) ? 7 : ((n >= 3) ? 3 : 1)))))))));
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    static {
        __OBFID = "CL_00001544";
    }
}
