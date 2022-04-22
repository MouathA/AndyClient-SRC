package net.minecraft.client.particle;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class EntityDropParticleFX extends EntityFX
{
    private Material materialType;
    private int bobTimer;
    private static final String __OBFID;
    
    protected EntityDropParticleFX(final World world, final double n, final double n2, final double n3, final Material materialType) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        if (materialType == Material.water) {
            this.particleRed = 0.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 1.0f;
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 0.0f;
            this.particleBlue = 0.0f;
        }
        this.setParticleTextureIndex(113);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.materialType = materialType;
        this.bobTimer = 40;
        this.particleMaxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        final double motionX2 = 0.0;
        this.motionZ = motionX2;
        this.motionY = motionX2;
        this.motionX = motionX2;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return (this.materialType == Material.water) ? super.getBrightnessForRender(n) : 257;
    }
    
    @Override
    public float getBrightness(final float n) {
        return (this.materialType == Material.water) ? super.getBrightness(n) : 1.0f;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.materialType == Material.water) {
            this.particleRed = 0.2f;
            this.particleGreen = 0.3f;
            this.particleBlue = 1.0f;
        }
        else {
            this.particleRed = 1.0f;
            this.particleGreen = 16.0f / (40 - this.bobTimer + 16);
            this.particleBlue = 4.0f / (40 - this.bobTimer + 8);
        }
        this.motionY -= this.particleGravity;
        if (this.bobTimer-- > 0) {
            this.motionX *= 0.02;
            this.motionY *= 0.02;
            this.motionZ *= 0.02;
            this.setParticleTextureIndex(113);
        }
        else {
            this.setParticleTextureIndex(112);
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (this.materialType == Material.water) {
                this.setDead();
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
            }
            else {
                this.setParticleTextureIndex(114);
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(this));
        final Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double n = 0.0;
            if (blockState.getBlock() instanceof BlockLiquid) {
                n = BlockLiquid.getLiquidHeightPercent((int)blockState.getValue(BlockLiquid.LEVEL));
            }
            if (this.posY < MathHelper.floor_double(this.posY) + 1 - n) {
                this.setDead();
            }
        }
    }
    
    static {
        __OBFID = "CL_00000901";
    }
    
    public static class LavaFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDropParticleFX(world, n2, n3, n4, Material.lava);
        }
        
        static {
            __OBFID = "CL_00002607";
        }
    }
    
    public static class WaterFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDropParticleFX(world, n2, n3, n4, Material.water);
        }
        
        static {
            __OBFID = "CL_00002606";
        }
    }
}
