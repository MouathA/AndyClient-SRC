package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class EntityLargeExplodeFX extends EntityFX
{
    private static final ResourceLocation field_110127_a;
    private int field_70581_a;
    private int field_70584_aq;
    private TextureManager theRenderEngine;
    private float field_70582_as;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000910";
        field_110127_a = new ResourceLocation("textures/entity/explosion.png");
    }
    
    protected EntityLargeExplodeFX(final TextureManager theRenderEngine, final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.theRenderEngine = theRenderEngine;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        final float particleRed = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.field_70582_as = 1.0f - (float)n4 * 0.5f;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final int n7 = (int)((this.field_70581_a + n) * 15.0f / this.field_70584_aq);
        if (n7 <= 15) {
            this.theRenderEngine.bindTexture(EntityLargeExplodeFX.field_110127_a);
            final float n8 = n7 % 4 / 4.0f;
            final float n9 = n8 + 0.24975f;
            final float n10 = n7 / 4 / 4.0f;
            final float n11 = n10 + 0.24975f;
            final float n12 = 2.0f * this.field_70582_as;
            final float n13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityLargeExplodeFX.interpPosX);
            final float n14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityLargeExplodeFX.interpPosY);
            final float n15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityLargeExplodeFX.interpPosZ);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            worldRenderer.startDrawingQuads();
            worldRenderer.func_178960_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
            worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
            worldRenderer.func_178963_b(240);
            worldRenderer.addVertexWithUV(n13 - n2 * n12 - n5 * n12, n14 - n3 * n12, n15 - n4 * n12 - n6 * n12, n9, n11);
            worldRenderer.addVertexWithUV(n13 - n2 * n12 + n5 * n12, n14 + n3 * n12, n15 - n4 * n12 + n6 * n12, n9, n10);
            worldRenderer.addVertexWithUV(n13 + n2 * n12 + n5 * n12, n14 + n3 * n12, n15 + n4 * n12 + n6 * n12, n8, n10);
            worldRenderer.addVertexWithUV(n13 + n2 * n12 - n5 * n12, n14 - n3 * n12, n15 + n4 * n12 - n6 * n12, n8, n11);
            Tessellator.getInstance().draw();
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 61680;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_70581_a;
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), world, n2, n3, n4, n5, n6, n7);
        }
        
        static {
            __OBFID = "CL_00002598";
        }
    }
}
