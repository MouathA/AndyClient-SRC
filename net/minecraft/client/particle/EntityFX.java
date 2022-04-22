package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.nbt.*;

public class EntityFX extends Entity
{
    protected int particleTextureIndexX;
    protected int particleTextureIndexY;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleScale;
    protected float particleGravity;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    protected float particleAlpha;
    protected TextureAtlasSprite particleIcon;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;
    private static final String __OBFID;
    
    protected EntityFX(final World world, final double lastTickPosX, final double lastTickPosY, final double lastTickPosZ) {
        super(world);
        this.particleAlpha = 1.0f;
        this.setSize(0.2f, 0.2f);
        this.setPosition(lastTickPosX, lastTickPosY, lastTickPosZ);
        this.lastTickPosX = lastTickPosX;
        this.lastTickPosY = lastTickPosY;
        this.lastTickPosZ = lastTickPosZ;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }
    
    public EntityFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3);
        this.motionX = n4 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionY = n5 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionZ = n6 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        final float n7 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / sqrt_double * n7 * 0.4000000059604645;
        this.motionY = this.motionY / sqrt_double * n7 * 0.4000000059604645 + 0.10000000149011612;
        this.motionZ = this.motionZ / sqrt_double * n7 * 0.4000000059604645;
    }
    
    public EntityFX multiplyVelocity(final float n) {
        this.motionX *= n;
        this.motionY = (this.motionY - 0.10000000149011612) * n + 0.10000000149011612;
        this.motionZ *= n;
        return this;
    }
    
    public EntityFX multipleParticleScaleBy(final float n) {
        this.setSize(0.2f * n, 0.2f * n);
        this.particleScale *= n;
        return this;
    }
    
    public void setRBGColorF(final float particleRed, final float particleGreen, final float particleBlue) {
        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;
    }
    
    public void setAlphaF(final float particleAlpha) {
        if (this.particleAlpha == 1.0f && particleAlpha < 1.0f) {
            Minecraft.getMinecraft().effectRenderer.func_178928_b(this);
        }
        else if (this.particleAlpha < 1.0f && particleAlpha == 1.0f) {
            Minecraft.getMinecraft().effectRenderer.func_178931_c(this);
        }
        this.particleAlpha = particleAlpha;
    }
    
    public float getRedColorF() {
        return this.particleRed;
    }
    
    public float getGreenColorF() {
        return this.particleGreen;
    }
    
    public float getBlueColorF() {
        return this.particleBlue;
    }
    
    public float func_174838_j() {
        return this.particleAlpha;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float minU = this.particleTextureIndexX / 16.0f;
        float maxU = minU + 0.0624375f;
        float minV = this.particleTextureIndexY / 16.0f;
        float maxV = minV + 0.0624375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
            minV = this.particleIcon.getMinV();
            maxV = this.particleIcon.getMaxV();
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityFX.interpPosZ);
        worldRenderer.func_178960_a(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7, maxU, maxV);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7, maxU, minV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7, minU, minV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7, minU, maxV);
    }
    
    public int getFXLayer() {
        return 0;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void func_180435_a(final TextureAtlasSprite particleIcon) {
        if (this.getFXLayer() == 1) {
            this.particleIcon = particleIcon;
            return;
        }
        throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
    }
    
    public void setParticleTextureIndex(final int n) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }
        this.particleTextureIndexX = n % 16;
        this.particleTextureIndexY = n / 16;
    }
    
    public void nextTextureIndexX() {
        ++this.particleTextureIndexX;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getSimpleName()) + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
    }
    
    static {
        __OBFID = "CL_00000914";
    }
}
