package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntitySplashFX extends EntityRainFX
{
    private static final String __OBFID;
    
    protected EntitySplashFX(final World world, final double n, final double n2, final double n3, final double motionX, final double n4, final double motionZ) {
        super(world, n, n2, n3);
        this.particleGravity = 0.04f;
        this.nextTextureIndexX();
        if (n4 == 0.0 && (motionX != 0.0 || motionZ != 0.0)) {
            this.motionX = motionX;
            this.motionY = n4 + 0.1;
            this.motionZ = motionZ;
        }
    }
    
    static {
        __OBFID = "CL_00000927";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySplashFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002580";
        }
    }
}
