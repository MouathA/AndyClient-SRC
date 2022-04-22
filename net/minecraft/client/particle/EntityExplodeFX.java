package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityExplodeFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntityExplodeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.motionX = n4 + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.motionY = n5 + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.motionZ = n6 + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        final float particleRed = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int)(16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
        this.motionX *= 0.8999999761581421;
        this.motionY *= 0.8999999761581421;
        this.motionZ *= 0.8999999761581421;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    static {
        __OBFID = "CL_00000903";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityExplodeFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002604";
        }
    }
}
