package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;

public class RenderMinecart extends Render
{
    private static final ResourceLocation minecartTextures;
    protected ModelBase modelMinecart;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001013";
        minecartTextures = new ResourceLocation("textures/entity/minecart.png");
    }
    
    public RenderMinecart(final RenderManager renderManager) {
        super(renderManager);
        this.modelMinecart = new ModelMinecart();
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityMinecart entityMinecart, double n, double n2, double n3, float n4, final float n5) {
        this.bindEntityTexture(entityMinecart);
        final long n6 = entityMinecart.getEntityId() * 493286711L;
        final long n7 = n6 * n6 * 4392167121L + n6 * 98761L;
        GlStateManager.translate((((n7 >> 16 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f, (((n7 >> 20 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f, (((n7 >> 24 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f);
        final double n8 = entityMinecart.lastTickPosX + (entityMinecart.posX - entityMinecart.lastTickPosX) * n5;
        final double n9 = entityMinecart.lastTickPosY + (entityMinecart.posY - entityMinecart.lastTickPosY) * n5;
        final double n10 = entityMinecart.lastTickPosZ + (entityMinecart.posZ - entityMinecart.lastTickPosZ) * n5;
        final double n11 = 0.30000001192092896;
        final Vec3 func_70489_a = entityMinecart.func_70489_a(n8, n9, n10);
        float n12 = entityMinecart.prevRotationPitch + (entityMinecart.rotationPitch - entityMinecart.prevRotationPitch) * n5;
        if (func_70489_a != null) {
            Vec3 func_70495_a = entityMinecart.func_70495_a(n8, n9, n10, n11);
            Vec3 func_70495_a2 = entityMinecart.func_70495_a(n8, n9, n10, -n11);
            if (func_70495_a == null) {
                func_70495_a = func_70489_a;
            }
            if (func_70495_a2 == null) {
                func_70495_a2 = func_70489_a;
            }
            n += func_70489_a.xCoord - n8;
            n2 += (func_70495_a.yCoord + func_70495_a2.yCoord) / 2.0 - n9;
            n3 += func_70489_a.zCoord - n10;
            final Vec3 addVector = func_70495_a2.addVector(-func_70495_a.xCoord, -func_70495_a.yCoord, -func_70495_a.zCoord);
            if (addVector.lengthVector() != 0.0) {
                final Vec3 normalize = addVector.normalize();
                n4 = (float)(Math.atan2(normalize.zCoord, normalize.xCoord) * 180.0 / 3.141592653589793);
                n12 = (float)(Math.atan(normalize.yCoord) * 73.0);
            }
        }
        GlStateManager.translate((float)n, (float)n2 + 0.375f, (float)n3);
        GlStateManager.rotate(180.0f - n4, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-n12, 0.0f, 0.0f, 1.0f);
        final float n13 = entityMinecart.getRollingAmplitude() - n5;
        float n14 = entityMinecart.getDamage() - n5;
        if (n14 < 0.0f) {
            n14 = 0.0f;
        }
        if (n13 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(n13) * n13 * n14 / 10.0f * entityMinecart.getRollingDirection(), 1.0f, 0.0f, 0.0f);
        }
        final int displayTileOffset = entityMinecart.getDisplayTileOffset();
        final IBlockState func_174897_t = entityMinecart.func_174897_t();
        if (func_174897_t.getBlock().getRenderType() != -1) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            final float n15 = 0.75f;
            GlStateManager.scale(n15, n15, n15);
            GlStateManager.translate(-0.5f, (displayTileOffset - 8) / 16.0f, 0.5f);
            this.func_180560_a(entityMinecart, n5, func_174897_t);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindEntityTexture(entityMinecart);
        }
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.render(entityMinecart, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        super.doRender(entityMinecart, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityMinecart entityMinecart) {
        return RenderMinecart.minecartTextures;
    }
    
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        Minecraft.getMinecraft().getBlockRendererDispatcher().func_175016_a(blockState, entityMinecart.getBrightness(n));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMinecart)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityMinecart)entity, n, n2, n3, n4, n5);
    }
}
