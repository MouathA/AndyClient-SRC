package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityCritFX extends EntitySmokeFX
{
    private static final String __OBFID;
    
    protected EntityCritFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6, 2.5f);
    }
    
    static {
        __OBFID = "CL_00000900";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityCritFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002596";
        }
    }
}
