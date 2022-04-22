package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public abstract class Render
{
    private static final ResourceLocation shadowTextures;
    protected final RenderManager renderManager;
    protected float shadowSize;
    protected float shadowOpaque;
    
    static {
        shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    }
    
    protected Render(final RenderManager renderManager) {
        this.shadowOpaque = 1.0f;
        this.renderManager = renderManager;
    }
    
    public boolean func_177071_a(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        return entity.isInRangeToRender3d(n, n2, n3) && (entity.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox()));
    }
    
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_177067_a(entity, n, n2, n3);
    }
    
    protected void func_177067_a(final Entity entity, final double n, final double n2, final double n3) {
        if (entity != 0) {
            this.renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), n, n2, n3, 64);
        }
    }
    
    protected void func_177069_a(final Entity entity, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderLivingLabel(entity, s, n, n2, n3, 64);
    }
    
    protected abstract ResourceLocation getEntityTexture(final Entity p0);
    
    protected boolean bindEntityTexture(final Entity entity) {
        final ResourceLocation entityTexture = this.getEntityTexture(entity);
        if (entityTexture == null) {
            return false;
        }
        this.bindTexture(entityTexture);
        return true;
    }
    
    public void bindTexture(final ResourceLocation resourceLocation) {
        this.renderManager.renderEngine.bindTexture(resourceLocation);
    }
    
    private void renderEntityOnFire(final Entity entity, final double n, final double n2, final double n3, final float n4) {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite atlasSprite = textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_0");
        textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_1");
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n5 = entity.width * 1.4f;
        GlStateManager.scale(n5, n5, n5);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        float n6 = 0.5f;
        final float n7 = 0.0f;
        float n8 = entity.height / n5;
        float n9 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, -0.3f + (int)n8 * 0.02f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float n10 = 0.0f;
        worldRenderer.startDrawingQuads();
        while (n8 > 0.0f) {
            final TextureAtlasSprite textureAtlasSprite = atlasSprite;
            this.bindTexture(TextureMap.locationBlocksTexture);
            final float minU = textureAtlasSprite.getMinU();
            final float minV = textureAtlasSprite.getMinV();
            final float maxU = textureAtlasSprite.getMaxU();
            final float maxV = textureAtlasSprite.getMaxV();
            final float n11 = maxU;
            final float n12 = minU;
            final float n13 = n11;
            worldRenderer.addVertexWithUV(n6 - n7, 0.0f - n9, n10, n12, maxV);
            worldRenderer.addVertexWithUV(-n6 - n7, 0.0f - n9, n10, n13, maxV);
            worldRenderer.addVertexWithUV(-n6 - n7, 1.4f - n9, n10, n13, minV);
            worldRenderer.addVertexWithUV(n6 - n7, 1.4f - n9, n10, n12, minV);
            n8 -= 0.45f;
            n9 -= 0.45f;
            n6 *= 0.9f;
            n10 += 0.03f;
            int n14 = 0;
            ++n14;
        }
        instance.draw();
    }
    
    private void renderShadow(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            GlStateManager.blendFunc(770, 771);
            this.renderManager.renderEngine.bindTexture(Render.shadowTextures);
            final World worldFromRenderManager = this.getWorldFromRenderManager();
            GlStateManager.depthMask(false);
            float shadowSize = this.shadowSize;
            if (entity instanceof EntityLiving) {
                final EntityLiving entityLiving = (EntityLiving)entity;
                shadowSize *= entityLiving.getRenderSizeModifier();
                if (entityLiving.isChild()) {
                    shadowSize *= 0.5f;
                }
            }
            final double n6 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n5;
            final double n7 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n5;
            final double n8 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n5;
            final int floor_double = MathHelper.floor_double(n6 - shadowSize);
            final int floor_double2 = MathHelper.floor_double(n6 + shadowSize);
            final int floor_double3 = MathHelper.floor_double(n7 - shadowSize);
            final int floor_double4 = MathHelper.floor_double(n7);
            final int floor_double5 = MathHelper.floor_double(n8 - shadowSize);
            final int floor_double6 = MathHelper.floor_double(n8 + shadowSize);
            final double n9 = n - n6;
            final double n10 = n2 - n7;
            final double n11 = n3 - n8;
            final Tessellator instance = Tessellator.getInstance();
            instance.getWorldRenderer().startDrawingQuads();
            for (final BlockPos blockPos : BlockPos.getAllInBox(new BlockPos(floor_double, floor_double3, floor_double5), new BlockPos(floor_double2, floor_double4, floor_double6))) {
                final Block block = worldFromRenderManager.getBlockState(blockPos.offsetDown()).getBlock();
                if (block.getRenderType() != -1 && worldFromRenderManager.getLightFromNeighbors(blockPos) > 3) {
                    this.func_180549_a(block, n, n2, n3, blockPos, n4, shadowSize, n9, n10, n11);
                }
            }
            instance.draw();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.depthMask(true);
        }
    }
    
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }
    
    private void func_180549_a(final Block block, final double n, final double n2, final double n3, final BlockPos blockPos, final float n4, final float n5, final double n6, final double n7, final double n8) {
        if (block.isFullCube()) {
            final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            double n9 = (n4 - (n2 - (blockPos.getY() + n7)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(blockPos);
            if (n9 >= 0.0) {
                if (n9 > 1.0) {
                    n9 = 1.0;
                }
                worldRenderer.func_178960_a(1.0f, 1.0f, 1.0f, (float)n9);
                final double n10 = blockPos.getX() + block.getBlockBoundsMinX() + n6;
                final double n11 = blockPos.getX() + block.getBlockBoundsMaxX() + n6;
                final double n12 = blockPos.getY() + block.getBlockBoundsMinY() + n7 + 0.015625;
                final double n13 = blockPos.getZ() + block.getBlockBoundsMinZ() + n8;
                final double n14 = blockPos.getZ() + block.getBlockBoundsMaxZ() + n8;
                final float n15 = (float)((n - n10) / 2.0 / n5 + 0.5);
                final float n16 = (float)((n - n11) / 2.0 / n5 + 0.5);
                final float n17 = (float)((n3 - n13) / 2.0 / n5 + 0.5);
                final float n18 = (float)((n3 - n14) / 2.0 / n5 + 0.5);
                worldRenderer.addVertexWithUV(n10, n12, n13, n15, n17);
                worldRenderer.addVertexWithUV(n10, n12, n14, n15, n18);
                worldRenderer.addVertexWithUV(n11, n12, n14, n16, n18);
                worldRenderer.addVertexWithUV(n11, n12, n13, n16, n17);
            }
        }
    }
    
    public static void renderOffsetAABB(final AxisAlignedBB axisAlignedBB, final double n, final double n2, final double n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.setTranslation(n, n2, n3);
        worldRenderer.func_178980_d(0.0f, 0.0f, -1.0f);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.func_178980_d(0.0f, 0.0f, 1.0f);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.func_178980_d(0.0f, -1.0f, 0.0f);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.func_178980_d(-1.0f, 0.0f, 0.0f);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.func_178980_d(1.0f, 0.0f, 0.0f);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        instance.draw();
    }
    
    public void doRenderShadowAndFire(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.renderManager.options != null) {
            if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && !entity.isInvisible() && this.renderManager.func_178627_a()) {
                final float n6 = (float)((1.0 - this.renderManager.getDistanceToCamera(entity.posX, entity.posY, entity.posZ) / 256.0) * this.shadowOpaque);
                if (n6 > 0.0f) {
                    this.renderShadow(entity, n, n2, n3, n6, n5);
                }
            }
            if (entity.canRenderOnFire() && (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v())) {
                this.renderEntityOnFire(entity, n, n2, n3, n5);
            }
        }
    }
    
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
    
    protected void renderLivingLabel(final Entity entity, final String s, final double n, final double n2, final double n3, final int n4) {
        if (entity.getDistanceSqToEntity(this.renderManager.livingPlayer) <= n4 * n4) {
            final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
            final float n5 = 0.016666668f * 1.6f;
            GlStateManager.translate((float)n + 0.0f, (float)n2 + entity.height + 0.5f, (float)n3);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-n5, -n5, n5);
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            if (s.equals("deadmau5")) {}
            worldRenderer.startDrawingQuads();
            final int n6 = fontRendererFromRenderManager.getStringWidth(s) / 2;
            worldRenderer.func_178960_a(0.0f, 0.0f, 0.0f, 0.25f);
            worldRenderer.addVertex(-n6 - 1, -11, 0.0);
            worldRenderer.addVertex(-n6 - 1, -2, 0.0);
            worldRenderer.addVertex(n6 + 1, -2, 0.0);
            worldRenderer.addVertex(n6 + 1, -11, 0.0);
            instance.draw();
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / 2, -10, 553648127);
            GlStateManager.depthMask(true);
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / 2, -10, -1);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public RenderManager func_177068_d() {
        return this.renderManager;
    }
}
