package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntitySmokeFX extends EntityFX
{
    float smokeParticleScale;
    private static final String __OBFID;
    
    private EntitySmokeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3, n4, n5, n6, 1.0f);
    }
    
    protected EntitySmokeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += n4;
        this.motionY += n5;
        this.motionZ += n6;
        final float particleRed = (float)(Math.random() * 0.30000001192092896);
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= 0.75f;
        this.particleScale *= n7;
        this.smokeParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge *= (int)n7;
        this.noClip = false;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.particleScale = this.smokeParticleScale * MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
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
        this.motionY += 0.004;
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
    
    EntitySmokeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final Object o) {
        this(world, n, n2, n3, n4, n5, n6);
    }
    
    static {
        __OBFID = "CL_00000924";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySmokeFX(world, n2, n3, n4, n5, n6, n7, null);
        }
        
        static {
            __OBFID = "CL_00002587";
        }
    }
}
