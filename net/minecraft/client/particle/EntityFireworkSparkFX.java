package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class EntityFireworkSparkFX extends EntityFX
{
    private int baseTextureIndex;
    private boolean field_92054_ax;
    private boolean field_92048_ay;
    private final EffectRenderer field_92047_az;
    private float fadeColourRed;
    private float fadeColourGreen;
    private float fadeColourBlue;
    private boolean hasFadeColour;
    private static final String __OBFID;
    
    public EntityFireworkSparkFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer field_92047_az) {
        super(world, n, n2, n3);
        this.baseTextureIndex = 160;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.field_92047_az = field_92047_az;
        this.particleScale *= 0.75f;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }
    
    public void setTrail(final boolean field_92054_ax) {
        this.field_92054_ax = field_92054_ax;
    }
    
    public void setTwinkle(final boolean field_92048_ay) {
        this.field_92048_ay = field_92048_ay;
    }
    
    public void setColour(final int n) {
        final float n2 = ((n & 0xFF0000) >> 16) / 255.0f;
        final float n3 = ((n & 0xFF00) >> 8) / 255.0f;
        final float n4 = ((n & 0xFF) >> 0) / 255.0f;
        final float n5 = 1.0f;
        this.setRBGColorF(n2 * n5, n3 * n5, n4 * n5);
    }
    
    public void setFadeColour(final int n) {
        this.fadeColourRed = ((n & 0xFF0000) >> 16) / 255.0f;
        this.fadeColourGreen = ((n & 0xFF00) >> 8) / 255.0f;
        this.fadeColourBlue = ((n & 0xFF) >> 0) / 255.0f;
        this.hasFadeColour = true;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (!this.field_92048_ay || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
            super.func_180434_a(worldRenderer, entity, n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0f - (this.particleAge - (float)(this.particleMaxAge / 2)) / this.particleMaxAge);
            if (this.hasFadeColour) {
                this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2f;
                this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2f;
                this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2f;
            }
        }
        this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9100000262260437;
        this.motionY *= 0.9100000262260437;
        this.motionZ *= 0.9100000262260437;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        if (this.field_92054_ax && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
            final EntityFireworkSparkFX entityFireworkSparkFX = new EntityFireworkSparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
            entityFireworkSparkFX.setAlphaF(0.99f);
            entityFireworkSparkFX.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            entityFireworkSparkFX.particleAge = entityFireworkSparkFX.particleMaxAge / 2;
            if (this.hasFadeColour) {
                entityFireworkSparkFX.hasFadeColour = true;
                entityFireworkSparkFX.fadeColourRed = this.fadeColourRed;
                entityFireworkSparkFX.fadeColourGreen = this.fadeColourGreen;
                entityFireworkSparkFX.fadeColourBlue = this.fadeColourBlue;
            }
            entityFireworkSparkFX.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.addEffect(entityFireworkSparkFX);
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    static {
        __OBFID = "CL_00000905";
    }
}
