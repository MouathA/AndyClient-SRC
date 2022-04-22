package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;

public class EntitySuspendFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntitySuspendFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2 - 0.125, n3, n4, n5, n6);
        this.particleRed = 0.4f;
        this.particleGreen = 0.4f;
        this.particleBlue = 0.7f;
        this.setParticleTextureIndex(0);
        this.setSize(0.01f, 0.01f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = n4 * 0.0;
        this.motionY = n5 * 0.0;
        this.motionZ = n6 * 0.0;
        this.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00000928";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntitySuspendFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002579";
        }
    }
}
