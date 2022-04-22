package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class EntityRainFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntityRainFX(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = Math.random() * 0.20000000298023224 + 0.10000000149011612;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setDead();
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final BlockPos blockPos = new BlockPos(this);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        block.setBlockBoundsBasedOnState(this.worldObj, blockPos);
        final Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double blockBoundsMaxY;
            if (blockState.getBlock() instanceof BlockLiquid) {
                blockBoundsMaxY = 1.0f - BlockLiquid.getLiquidHeightPercent((int)blockState.getValue(BlockLiquid.LEVEL));
            }
            else {
                blockBoundsMaxY = block.getBlockBoundsMaxY();
            }
            if (this.posY < MathHelper.floor_double(this.posY) + blockBoundsMaxY) {
                this.setDead();
            }
        }
    }
    
    static {
        __OBFID = "CL_00000934";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityRainFX(world, n2, n3, n4);
        }
        
        static {
            __OBFID = "CL_00002572";
        }
    }
}
