package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderGuardian extends RenderLiving
{
    private static final ResourceLocation field_177114_e;
    private static final ResourceLocation field_177116_j;
    private static final ResourceLocation field_177117_k;
    int field_177115_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002443";
        field_177114_e = new ResourceLocation("textures/entity/guardian.png");
        field_177116_j = new ResourceLocation("textures/entity/guardian_elder.png");
        field_177117_k = new ResourceLocation("textures/entity/guardian_beam.png");
    }
    
    public RenderGuardian(final RenderManager renderManager) {
        super(renderManager, new ModelGuardian(), 0.5f);
        this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
    }
    
    public boolean func_177113_a(final EntityGuardian entityGuardian, final ICamera camera, final double n, final double n2, final double n3) {
        if (super.func_177104_a(entityGuardian, camera, n, n2, n3)) {
            return true;
        }
        if (entityGuardian.func_175474_cn()) {
            final EntityLivingBase func_175466_co = entityGuardian.func_175466_co();
            if (func_175466_co != null) {
                final Vec3 func_177110_a = this.func_177110_a(func_175466_co, func_175466_co.height * 0.5, 1.0f);
                final Vec3 func_177110_a2 = this.func_177110_a(entityGuardian, entityGuardian.getEyeHeight(), 1.0f);
                if (camera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(func_177110_a2.xCoord, func_177110_a2.yCoord, func_177110_a2.zCoord, func_177110_a.xCoord, func_177110_a.yCoord, func_177110_a.zCoord))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private Vec3 func_177110_a(final EntityLivingBase entityLivingBase, final double n, final float n2) {
        return new Vec3(entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * n2, n + entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * n2, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * n2);
    }
    
    public void func_177109_a(final EntityGuardian entityGuardian, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
            this.mainModel = new ModelGuardian();
            this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
        }
        super.doRender(entityGuardian, n, n2, n3, n4, n5);
        final EntityLivingBase func_175466_co = entityGuardian.func_175466_co();
        if (func_175466_co != null) {
            final float func_175477_p = entityGuardian.func_175477_p(n5);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(RenderGuardian.field_177117_k);
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GlStateManager.depthMask(true);
            final float n6 = 240.0f;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n6, n6);
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            final float n7 = entityGuardian.worldObj.getTotalWorldTime() + n5;
            final float n8 = n7 * 0.5f % 1.0f;
            final float eyeHeight = entityGuardian.getEyeHeight();
            GlStateManager.translate((float)n, (float)n2 + eyeHeight, (float)n3);
            final Vec3 subtract = this.func_177110_a(func_175466_co, func_175466_co.height * 0.5, n5).subtract(this.func_177110_a(entityGuardian, eyeHeight, n5));
            final double n9 = subtract.lengthVector() + 1.0;
            final Vec3 normalize = subtract.normalize();
            final float n10 = (float)Math.acos(normalize.yCoord);
            GlStateManager.rotate((1.5707964f + -(float)Math.atan2(normalize.zCoord, normalize.xCoord)) * 57.295776f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(n10 * 57.295776f, 1.0f, 0.0f, 0.0f);
            final double n11 = n7 * 0.05 * (1.0 - 1 * 2.5);
            worldRenderer.startDrawingQuads();
            final float n12 = func_175477_p * func_175477_p;
            worldRenderer.func_178961_b(64 + (int)(n12 * 240.0f), 32 + (int)(n12 * 192.0f), 128 - (int)(n12 * 64.0f), 255);
            final double n13 = 1 * 0.2;
            final double n14 = n13 * 1.41;
            final double n15 = 0.0 + Math.cos(n11 + 2.356194490192345) * n14;
            final double n16 = 0.0 + Math.sin(n11 + 2.356194490192345) * n14;
            final double n17 = 0.0 + Math.cos(n11 + 0.7853981633974483) * n14;
            final double n18 = 0.0 + Math.sin(n11 + 0.7853981633974483) * n14;
            final double n19 = 0.0 + Math.cos(n11 + 3.9269908169872414) * n14;
            final double n20 = 0.0 + Math.sin(n11 + 3.9269908169872414) * n14;
            final double n21 = 0.0 + Math.cos(n11 + 5.497787143782138) * n14;
            final double n22 = 0.0 + Math.sin(n11 + 5.497787143782138) * n14;
            final double n23 = 0.0 + Math.cos(n11 + 3.141592653589793) * n13;
            final double n24 = 0.0 + Math.sin(n11 + 3.141592653589793) * n13;
            final double n25 = 0.0 + Math.cos(n11 + 0.0) * n13;
            final double n26 = 0.0 + Math.sin(n11 + 0.0) * n13;
            final double n27 = 0.0 + Math.cos(n11 + 1.5707963267948966) * n13;
            final double n28 = 0.0 + Math.sin(n11 + 1.5707963267948966) * n13;
            final double n29 = 0.0 + Math.cos(n11 + 4.71238898038469) * n13;
            final double n30 = 0.0 + Math.sin(n11 + 4.71238898038469) * n13;
            final double n31 = 0.0;
            final double n32 = 0.4999;
            final double n33 = -1.0f + n8;
            final double n34 = n9 * (0.5 / n13) + n33;
            worldRenderer.addVertexWithUV(n23, n9, n24, n32, n34);
            worldRenderer.addVertexWithUV(n23, 0.0, n24, n32, n33);
            worldRenderer.addVertexWithUV(n25, 0.0, n26, n31, n33);
            worldRenderer.addVertexWithUV(n25, n9, n26, n31, n34);
            worldRenderer.addVertexWithUV(n27, n9, n28, n32, n34);
            worldRenderer.addVertexWithUV(n27, 0.0, n28, n32, n33);
            worldRenderer.addVertexWithUV(n29, 0.0, n30, n31, n33);
            worldRenderer.addVertexWithUV(n29, n9, n30, n31, n34);
            double n35 = 0.0;
            if (entityGuardian.ticksExisted % 2 == 0) {
                n35 = 0.5;
            }
            worldRenderer.addVertexWithUV(n15, n9, n16, 0.5, n35 + 0.5);
            worldRenderer.addVertexWithUV(n17, n9, n18, 1.0, n35 + 0.5);
            worldRenderer.addVertexWithUV(n21, n9, n22, 1.0, n35);
            worldRenderer.addVertexWithUV(n19, n9, n20, 0.5, n35);
            instance.draw();
        }
    }
    
    protected void func_177112_a(final EntityGuardian entityGuardian, final float n) {
        if (entityGuardian.func_175461_cl()) {
            GlStateManager.scale(2.35f, 2.35f, 2.35f);
        }
    }
    
    protected ResourceLocation func_177111_a(final EntityGuardian entityGuardian) {
        return entityGuardian.func_175461_cl() ? RenderGuardian.field_177116_j : RenderGuardian.field_177114_e;
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_177109_a((EntityGuardian)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    public boolean func_177104_a(final EntityLiving entityLiving, final ICamera camera, final double n, final double n2, final double n3) {
        return this.func_177113_a((EntityGuardian)entityLiving, camera, n, n2, n3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_177112_a((EntityGuardian)entityLivingBase, n);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_177109_a((EntityGuardian)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_177111_a((EntityGuardian)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_177109_a((EntityGuardian)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public boolean func_177071_a(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return this.func_177113_a((EntityGuardian)entity, camera, n, n2, n3);
    }
}
