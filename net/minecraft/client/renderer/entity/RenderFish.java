package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;

public class RenderFish extends Render
{
    private static final ResourceLocation field_110792_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000996";
        field_110792_a = new ResourceLocation("textures/particle/particles.png");
    }
    
    public RenderFish(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void func_180558_a(final EntityFishHook entityFishHook, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        this.bindEntityTexture(entityFishHook);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float n6 = 8 / 128.0f;
        final float n7 = 16 / 128.0f;
        final float n8 = 16 / 128.0f;
        final float n9 = 24 / 128.0f;
        final float n10 = 1.0f;
        final float n11 = 0.5f;
        final float n12 = 0.5f;
        GlStateManager.rotate(180.0f - RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
        worldRenderer.addVertexWithUV(0.0f - n11, 0.0f - n12, 0.0, n6, n9);
        worldRenderer.addVertexWithUV(n10 - n11, 0.0f - n12, 0.0, n7, n9);
        worldRenderer.addVertexWithUV(n10 - n11, 1.0f - n12, 0.0, n7, n8);
        worldRenderer.addVertexWithUV(0.0f - n11, 1.0f - n12, 0.0, n6, n8);
        instance.draw();
        if (entityFishHook.angler != null) {
            final float sin = MathHelper.sin(MathHelper.sqrt_float(entityFishHook.angler.getSwingProgress(n5)) * 3.1415927f);
            final Vec3 rotatePitch = new Vec3(-0.36, 0.03, 0.35).rotatePitch(-(entityFishHook.angler.prevRotationPitch + (entityFishHook.angler.rotationPitch - entityFishHook.angler.prevRotationPitch) * n5) * 3.1415927f / 180.0f).rotateYaw(-(entityFishHook.angler.prevRotationYaw + (entityFishHook.angler.rotationYaw - entityFishHook.angler.prevRotationYaw) * n5) * 3.1415927f / 180.0f).rotateYaw(sin * 0.5f).rotatePitch(-sin * 0.7f);
            double n13 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 + rotatePitch.xCoord;
            double n14 = entityFishHook.angler.prevPosY + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 + rotatePitch.yCoord;
            double n15 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 + rotatePitch.zCoord;
            double n16 = entityFishHook.angler.getEyeHeight();
            Label_0719: {
                if (this.renderManager.options == null || this.renderManager.options.thirdPersonView <= 0) {
                    final EntityPlayer angler = entityFishHook.angler;
                    Minecraft.getMinecraft();
                    if (angler == Minecraft.thePlayer) {
                        break Label_0719;
                    }
                }
                final float n17 = (entityFishHook.angler.prevRenderYawOffset + (entityFishHook.angler.renderYawOffset - entityFishHook.angler.prevRenderYawOffset) * n5) * 3.1415927f / 180.0f;
                final double n18 = MathHelper.sin(n17);
                final double n19 = MathHelper.cos(n17);
                n13 = entityFishHook.angler.prevPosX + (entityFishHook.angler.posX - entityFishHook.angler.prevPosX) * n5 - n19 * 0.35 - n18 * 0.8;
                n14 = entityFishHook.angler.prevPosY + n16 + (entityFishHook.angler.posY - entityFishHook.angler.prevPosY) * n5 - 0.45;
                n15 = entityFishHook.angler.prevPosZ + (entityFishHook.angler.posZ - entityFishHook.angler.prevPosZ) * n5 - n18 * 0.35 + n19 * 0.8;
                n16 = (entityFishHook.angler.isSneaking() ? -0.1875 : 0.0);
            }
            final double n20 = entityFishHook.prevPosX + (entityFishHook.posX - entityFishHook.prevPosX) * n5;
            final double n21 = entityFishHook.prevPosY + (entityFishHook.posY - entityFishHook.prevPosY) * n5 + 0.25;
            final double n22 = entityFishHook.prevPosZ + (entityFishHook.posZ - entityFishHook.prevPosZ) * n5;
            final double n23 = (float)(n13 - n20);
            final double n24 = (float)(n14 - n21) + n16;
            final double n25 = (float)(n15 - n22);
            worldRenderer.startDrawing(3);
            worldRenderer.func_178991_c(0);
            while (0 <= 16) {
                final float n26 = 0 / (float)16;
                worldRenderer.addVertex(n + n23 * n26, n2 + n24 * (n26 * n26 + n26) * 0.5 + 0.25, n3 + n25 * n26);
                int n27 = 0;
                ++n27;
            }
            instance.draw();
            super.doRender(entityFishHook, n, n2, n3, n4, n5);
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityFishHook entityFishHook) {
        return RenderFish.field_110792_a;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFishHook)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180558_a((EntityFishHook)entity, n, n2, n3, n4, n5);
    }
}
