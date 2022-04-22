package net.minecraft.client.particle;

import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class EntityDiggingFX extends EntityFX
{
    private IBlockState field_174847_a;
    private static final String __OBFID;
    
    protected EntityDiggingFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final IBlockState field_174847_a) {
        super(world, n, n2, n3, n4, n5, n6);
        this.field_174847_a = field_174847_a;
        this.func_180435_a(Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178122_a(field_174847_a));
        this.particleGravity = field_174847_a.getBlock().blockParticleGravity;
        final float particleRed = 0.6f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale /= 2.0f;
    }
    
    public EntityDiggingFX func_174846_a(final BlockPos blockPos) {
        if (this.field_174847_a.getBlock() == Blocks.grass) {
            return this;
        }
        final int colorMultiplier = this.field_174847_a.getBlock().colorMultiplier(this.worldObj, blockPos);
        this.particleRed *= (colorMultiplier >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (colorMultiplier >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (colorMultiplier & 0xFF) / 255.0f;
        return this;
    }
    
    public EntityDiggingFX func_174845_l() {
        final Block block = this.field_174847_a.getBlock();
        if (block == Blocks.grass) {
            return this;
        }
        final int renderColor = block.getRenderColor(this.field_174847_a);
        this.particleRed *= (renderColor >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (renderColor >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (renderColor & 0xFF) / 255.0f;
        return this;
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float interpolatedU = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float interpolatedU2 = interpolatedU + 0.015609375f;
        float interpolatedV = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float interpolatedV2 = interpolatedV + 0.015609375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            interpolatedU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            interpolatedU2 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            interpolatedV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            interpolatedV2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityDiggingFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityDiggingFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityDiggingFX.interpPosZ);
        worldRenderer.func_178986_b(this.particleRed, this.particleGreen, this.particleBlue);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7, interpolatedU, interpolatedV2);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7, interpolatedU, interpolatedV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7, interpolatedU2, interpolatedV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7, interpolatedU2, interpolatedV2);
    }
    
    static {
        __OBFID = "CL_00000932";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityDiggingFX(world, n2, n3, n4, n5, n6, n7, Block.getStateById(array[0])).func_174845_l();
        }
        
        static {
            __OBFID = "CL_00002575";
        }
    }
}
