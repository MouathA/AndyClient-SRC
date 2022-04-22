package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;

public class EntityBubbleFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntityBubbleFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(32);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = n4 * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.motionY = n5 * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.motionZ = n6 * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579;
        this.motionY *= 0.8500000238418579;
        this.motionZ *= 0.8500000238418579;
        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
    
    static {
        __OBFID = "CL_00000898";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBubbleFX(world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002610";
        }
    }
}
