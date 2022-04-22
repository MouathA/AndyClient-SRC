package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class EntityPortalFX extends EntityFX
{
    private float portalParticleScale;
    private double portalPosX;
    private double portalPosY;
    private double portalPosZ;
    private static final String __OBFID;
    
    protected EntityPortalFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ) {
        super(world, n, n2, n3, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.posX = n;
        this.portalPosX = n;
        this.posY = n2;
        this.portalPosY = n2;
        this.posZ = n3;
        this.portalPosZ = n3;
        final float n4 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n5 = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleScale = n5;
        this.portalParticleScale = n5;
        final float particleRed = 1.0f * n4;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 8.0));
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = 1.0f - (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.portalParticleScale * (1.0f - n7 * n7);
        super.func_180434_a(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final int brightnessForRender = super.getBrightnessForRender(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2;
        final float n4 = n3 * n3;
        final int n5 = brightnessForRender & 0xFF;
        final int n6 = 240 + (int)(n4 * 15.0f * 16.0f);
        if (240 > 240) {}
        return n5 | 0xF00000;
    }
    
    @Override
    public float getBrightness(final float n) {
        final float brightness = super.getBrightness(n);
        final float n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = n2 * n2 * n2 * n2;
        return brightness * (1.0f - n3) + n3;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final float n2;
        final float n = n2 = this.particleAge / (float)this.particleMaxAge;
        final float n3 = 1.0f - (-n + n * n * 2.0f);
        this.posX = this.portalPosX + this.motionX * n3;
        this.posY = this.portalPosY + this.motionY * n3 + (1.0f - n2);
        this.posZ = this.portalPosZ + this.motionZ * n3;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00000921";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityPortalFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002590";
        }
    }
}
