package net.minecraft.client.particle;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntitySpellParticleFX extends EntityFX
{
    private static final Random field_174848_a;
    private int baseSpellTextureIndex;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000926";
        field_174848_a = new Random();
    }
    
    protected EntitySpellParticleFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.5 - EntitySpellParticleFX.field_174848_a.nextDouble(), n5, 0.5 - EntitySpellParticleFX.field_174848_a.nextDouble());
        this.baseSpellTextureIndex = 128;
        this.motionY *= 0.20000000298023224;
        if (n4 == 0.0 && n6 == 0.0) {
            this.motionX *= 0.10000000149011612;
            this.motionZ *= 0.10000000149011612;
        }
        this.particleScale *= 0.75f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = false;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        MathHelper.clamp_float((this.particleAge + n) / this.particleMaxAge * 32.0f, 0.0f, 1.0f);
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
        this.setParticleTextureIndex(this.baseSpellTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
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
    
    public void setBaseSpellTextureIndex(final int baseSpellTextureIndex) {
        this.baseSpellTextureIndex = baseSpellTextureIndex;
    }
    
    public static class AmbientMobFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setAlphaF(0.15f);
            entitySpellParticleFX.setRBGColorF((float)n5, (float)n6, (float)n7);
            return entitySpellParticleFX;
        }
        
        static {
            __OBFID = "CL_00002585";
        }
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002582";
        }
    }
    
    public static class InstantFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setBaseSpellTextureIndex(144);
            return entitySpellParticleFX;
        }
        
        static {
            __OBFID = "CL_00002584";
        }
    }
    
    public static class MobFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setRBGColorF((float)n5, (float)n6, (float)n7);
            return entitySpellParticleFX;
        }
        
        static {
            __OBFID = "CL_00002583";
        }
    }
    
    public static class WitchFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final EntitySpellParticleFX entitySpellParticleFX = new EntitySpellParticleFX(world, n2, n3, n4, n5, n6, n7);
            entitySpellParticleFX.setBaseSpellTextureIndex(144);
            final float n8 = world.rand.nextFloat() * 0.5f + 0.35f;
            entitySpellParticleFX.setRBGColorF(1.0f * n8, 0.0f * n8, 1.0f * n8);
            return entitySpellParticleFX;
        }
        
        static {
            __OBFID = "CL_00002581";
        }
    }
}
