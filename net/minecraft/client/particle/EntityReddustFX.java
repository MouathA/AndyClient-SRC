package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityReddustFX extends EntityFX
{
    float reddustParticleScale;
    private static final String __OBFID;
    
    protected EntityReddustFX(final World world, final double n, final double n2, final double n3, final float n4, final float n5, final float n6) {
        this(world, n, n2, n3, 1.0f, n4, n5, n6);
    }
    
    protected EntityReddustFX(final World world, final double n, final double n2, final double n3, final float n4, float n5, final float n6, final float n7) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        if (n5 == 0.0f) {
            n5 = 1.0f;
        }
        final float n8 = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * n5 * n8;
        this.particleGreen = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * n6 * n8;
        this.particleBlue = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * n7 * n8;
        this.particleScale *= 0.75f;
        this.particleScale *= n4;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge *= (int)n4;
        this.noClip = false;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.particleScale = this.reddustParticleScale * MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
        super.func_180434_a(worldRenderer, entity, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    static {
        __OBFID = "CL_00000923";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityReddustFX(world, n2, n3, n4, (float)n5, (float)n6, (float)n7);
        }
        
        static {
            __OBFID = "CL_00002589";
        }
    }
}
