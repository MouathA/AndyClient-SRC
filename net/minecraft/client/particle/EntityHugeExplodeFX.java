package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityHugeExplodeFX extends EntityFX
{
    private int timeSinceStart;
    private int maximumTime;
    private static final String __OBFID;
    
    protected EntityHugeExplodeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.maximumTime = 8;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    @Override
    public void onUpdate() {
        while (0 < 6) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.timeSinceStart / (float)this.maximumTime, 0.0, 0.0, new int[0]);
            int n = 0;
            ++n;
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    static {
        __OBFID = "CL_00000911";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityHugeExplodeFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002597";
        }
    }
}
