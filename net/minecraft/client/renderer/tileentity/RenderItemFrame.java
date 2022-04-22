package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.entity.item.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class RenderItemFrame extends Render
{
    private static final ResourceLocation mapBackgroundTextures;
    private final Minecraft field_147917_g;
    private final ModelResourceLocation field_177072_f;
    private final ModelResourceLocation field_177073_g;
    private RenderItem field_177074_h;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001002";
        mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    }
    
    public RenderItemFrame(final RenderManager renderManager, final RenderItem field_177074_h) {
        super(renderManager);
        this.field_147917_g = Minecraft.getMinecraft();
        this.field_177072_f = new ModelResourceLocation("item_frame", "normal");
        this.field_177073_g = new ModelResourceLocation("item_frame", "map");
        this.field_177074_h = field_177074_h;
    }
    
    public void doRender(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockPos func_174857_n = entityItemFrame.func_174857_n();
        GlStateManager.translate(func_174857_n.getX() - entityItemFrame.posX + n + 0.5, func_174857_n.getY() - entityItemFrame.posY + n2 + 0.5, func_174857_n.getZ() - entityItemFrame.posZ + n3 + 0.5);
        GlStateManager.rotate(180.0f - entityItemFrame.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final BlockRendererDispatcher blockRendererDispatcher = this.field_147917_g.getBlockRendererDispatcher();
        final ModelManager func_178126_b = blockRendererDispatcher.func_175023_a().func_178126_b();
        IBakedModel bakedModel;
        if (entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
            bakedModel = func_178126_b.getModel(this.field_177073_g);
        }
        else {
            bakedModel = func_178126_b.getModel(this.field_177072_f);
        }
        GlStateManager.translate(-0.5f, -0.5f, -0.5f);
        blockRendererDispatcher.func_175019_b().func_178262_a(bakedModel, 1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0f, 0.0f, 0.4375f);
        this.func_82402_b(entityItemFrame);
        this.func_147914_a(entityItemFrame, n + entityItemFrame.field_174860_b.getFrontOffsetX() * 0.3f, n2 - 0.25, n3 + entityItemFrame.field_174860_b.getFrontOffsetZ() * 0.3f);
    }
    
    protected ResourceLocation getEntityTexture(final EntityItemFrame entityItemFrame) {
        return null;
    }
    
    private void func_82402_b(final EntityItemFrame entityItemFrame) {
        final ItemStack displayedItem = entityItemFrame.getDisplayedItem();
        if (displayedItem != null) {
            final EntityItem entityItem = new EntityItem(entityItemFrame.worldObj, 0.0, 0.0, 0.0, displayedItem);
            final Item item = entityItem.getEntityItem().getItem();
            entityItem.getEntityItem().stackSize = 1;
            entityItem.hoverStart = 0.0f;
            int rotation = entityItemFrame.getRotation();
            if (item instanceof ItemMap) {
                rotation = rotation % 4 * 2;
            }
            GlStateManager.rotate(rotation * 360.0f / 8.0f, 0.0f, 0.0f, 1.0f);
            if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, entityItemFrame, this)) {
                if (item instanceof ItemMap) {
                    this.renderManager.renderEngine.bindTexture(RenderItemFrame.mapBackgroundTextures);
                    GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                    final float n = 0.0078125f;
                    GlStateManager.scale(n, n, n);
                    GlStateManager.translate(-64.0f, -64.0f, 0.0f);
                    final MapData mapData = Items.filled_map.getMapData(entityItem.getEntityItem(), entityItemFrame.worldObj);
                    GlStateManager.translate(0.0f, 0.0f, -1.0f);
                    if (mapData != null) {
                        this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(mapData, true);
                    }
                }
                else {
                    TextureAtlasSprite atlasSprite = null;
                    if (item == Items.compass) {
                        atlasSprite = this.field_147917_g.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
                        if (Config.isShaders()) {
                            ShadersTex.bindTextureMapForUpdateAndRender(this.field_147917_g.getTextureManager(), TextureMap.locationBlocksTexture);
                        }
                        else {
                            this.field_147917_g.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                        }
                        if (atlasSprite instanceof TextureCompass) {
                            final TextureCompass textureCompass = (TextureCompass)atlasSprite;
                            final double currentAngle = textureCompass.currentAngle;
                            final double angleDelta = textureCompass.angleDelta;
                            textureCompass.currentAngle = 0.0;
                            textureCompass.angleDelta = 0.0;
                            textureCompass.updateCompass(entityItemFrame.worldObj, entityItemFrame.posX, entityItemFrame.posZ, MathHelper.wrapAngleTo180_float((float)(180 + entityItemFrame.field_174860_b.getHorizontalIndex() * 90)), false, true);
                            textureCompass.currentAngle = currentAngle;
                            textureCompass.angleDelta = angleDelta;
                        }
                        else {
                            atlasSprite = null;
                        }
                    }
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    if (!this.field_177074_h.func_175050_a(entityItem.getEntityItem()) || item instanceof ItemSkull) {
                        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    }
                    this.field_177074_h.func_175043_b(entityItem.getEntityItem());
                    if (atlasSprite != null && atlasSprite.getFrameCount() > 0) {
                        atlasSprite.updateAnimation();
                    }
                }
            }
        }
        if (Config.isShaders()) {
            ShadersTex.updatingTex = null;
        }
    }
    
    protected void func_147914_a(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3) {
        if (Minecraft.isGuiEnabled() && entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().hasDisplayName() && this.renderManager.field_147941_i == entityItemFrame) {
            final float n4 = 0.016666668f * 1.6f;
            final double distanceSqToEntity = entityItemFrame.getDistanceSqToEntity(this.renderManager.livingPlayer);
            final float n5 = entityItemFrame.isSneaking() ? 32.0f : 64.0f;
            if (distanceSqToEntity < n5 * n5) {
                final String displayName = entityItemFrame.getDisplayedItem().getDisplayName();
                if (entityItemFrame.isSneaking()) {
                    final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
                    GlStateManager.translate((float)n + 0.0f, (float)n2 + entityItemFrame.height + 0.5f, (float)n3);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-n4, -n4, n4);
                    GlStateManager.translate(0.0f, 0.25f / n4, 0.0f);
                    GlStateManager.depthMask(false);
                    GlStateManager.blendFunc(770, 771);
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.startDrawingQuads();
                    final int n6 = fontRendererFromRenderManager.getStringWidth(displayName) / 2;
                    worldRenderer.func_178960_a(0.0f, 0.0f, 0.0f, 0.25f);
                    worldRenderer.addVertex(-n6 - 1, -1.0, 0.0);
                    worldRenderer.addVertex(-n6 - 1, 8.0, 0.0);
                    worldRenderer.addVertex(n6 + 1, 8.0, 0.0);
                    worldRenderer.addVertex(n6 + 1, -1.0, 0.0);
                    instance.draw();
                    GlStateManager.depthMask(true);
                    fontRendererFromRenderManager.drawString(displayName, -fontRendererFromRenderManager.getStringWidth(displayName) / 2, 0, 553648127);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.renderLivingLabel(entityItemFrame, displayName, n, n2, n3, 64);
                }
            }
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityItemFrame)entity);
    }
    
    @Override
    protected void func_177067_a(final Entity entity, final double n, final double n2, final double n3) {
        this.func_147914_a((EntityItemFrame)entity, n, n2, n3);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityItemFrame)entity, n, n2, n3, n4, n5);
    }
}
