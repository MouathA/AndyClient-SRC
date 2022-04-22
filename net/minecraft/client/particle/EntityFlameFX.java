package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityFlameFX extends EntityFX
{
    private float flameScale;
    private static final String __OBFID;
    
    protected EntityFlameFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.motionX = this.motionX * 0.009999999776482582 + n4;
        this.motionY = this.motionY * 0.009999999776482582 + n5;
        this.motionZ = this.motionZ * 0.009999999776482582 + n6;
        final double n7 = n + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        final double n8 = n2 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        final double n9 = n3 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f;
        this.flameScale = this.particleScale;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.particleAge + n) / this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - n7 * n7 * 0.5f);
        super.func_180434_a(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        final int n2 = super.getBrightnessForRender(n) >> 16 & 0xFF;
        final int n3 = 240 + (int)(clamp_float * 15.0f * 16.0f);
        if (240 > 240) {}
        return 0xF0 | n2 << 16;
    }
    
    @Override
    public float getBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge, 0.0f, 1.0f);
        return super.getBrightness(n) * clamp_float + (1.0f - clamp_float);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    static {
        __OBFID = "CL_00000907";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFlameFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002602";
        }
    }
}
