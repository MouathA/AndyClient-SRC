package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import optifine.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import shadersmod.client.*;
import net.minecraft.item.*;

public class ItemRenderer
{
    private static final ResourceLocation RES_MAP_BACKGROUND;
    private static final ResourceLocation RES_UNDERWATER_OVERLAY;
    private final Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager field_178111_g;
    private final RenderItem itemRenderer;
    private int equippedItemSlot;
    
    static {
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
        RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    }
    
    public ItemRenderer(final Minecraft mc) {
        this.equippedItemSlot = -1;
        this.mc = mc;
        this.field_178111_g = mc.getRenderManager();
        this.itemRenderer = mc.getRenderItem();
    }
    
    public void renderItem(final EntityLivingBase entityLivingBase, final ItemStack itemStack, final ItemCameraTransforms.TransformType transformType) {
        if (itemStack != null) {
            final Block blockFromItem = Block.getBlockFromItem(itemStack.getItem());
            if (this.itemRenderer.func_175050_a(itemStack)) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                if (this.func_178107_a(blockFromItem)) {
                    GlStateManager.depthMask(false);
                }
            }
            this.itemRenderer.func_175049_a(itemStack, entityLivingBase, transformType);
            if (this.func_178107_a(blockFromItem)) {
                GlStateManager.depthMask(true);
            }
        }
    }
    
    private boolean func_178107_a(final Block block) {
        return block != null && block.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    private void func_178101_a(final float n, final float n2) {
        GlStateManager.rotate(n, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178109_a(final AbstractClientPlayer abstractClientPlayer) {
        int n = Minecraft.theWorld.getCombinedLight(new BlockPos(abstractClientPlayer.posX, abstractClientPlayer.posY + abstractClientPlayer.getEyeHeight(), abstractClientPlayer.posZ), 0);
        if (Config.isDynamicLights()) {
            n = DynamicLights.getCombinedLight(this.mc.func_175606_aa(), n);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(n & 0xFFFF), (float)(n >> 16));
    }
    
    private void func_178110_a(final EntityPlayerSP entityPlayerSP, final float n) {
        final float n2 = entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * n;
        final float n3 = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * n;
        GlStateManager.rotate((entityPlayerSP.rotationPitch - n2) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityPlayerSP.rotationYaw - n3) * 0.1f, 0.0f, 1.0f, 0.0f);
    }
    
    private float func_178100_c(final float n) {
        return -MathHelper.cos(MathHelper.clamp_float(1.0f - n / 45.0f + 0.1f, 0.0f, 1.0f) * 3.1415927f) * 0.5f + 0.5f;
    }
    
    private void func_180534_a(final RenderPlayer renderPlayer) {
        GlStateManager.rotate(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.25f, -0.85f, 0.75f);
        renderPlayer.func_177138_b(Minecraft.thePlayer);
    }
    
    private void func_178106_b(final RenderPlayer renderPlayer) {
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.3f, -1.1f, 0.45f);
        renderPlayer.func_177139_c(Minecraft.thePlayer);
    }
    
    private void func_178102_b(final AbstractClientPlayer abstractClientPlayer) {
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        final RenderPlayer renderPlayer = (RenderPlayer)this.field_178111_g.getEntityRenderObject(Minecraft.thePlayer);
        if (!abstractClientPlayer.isInvisible()) {
            this.func_180534_a(renderPlayer);
            this.func_178106_b(renderPlayer);
        }
    }
    
    private void func_178097_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n3 * 3.1415927f));
        final float func_178100_c = this.func_178100_c(n);
        GlStateManager.translate(0.0f, 0.04f, -0.72f);
        GlStateManager.translate(0.0f, n2 * -1.2f, 0.0f);
        GlStateManager.translate(0.0f, func_178100_c * -0.5f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(func_178100_c * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        this.func_178102_b(abstractClientPlayer);
        final float sin = MathHelper.sin(n3 * n3 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-1.0f, -1.0f, 0.0f);
        GlStateManager.scale(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_MAP_BACKGROUND);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, 135.0, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(135.0, 135.0, 0.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(135.0, -7.0, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(-7.0, -7.0, 0.0, 0.0, 0.0);
        instance.draw();
        final MapData mapData = Items.filled_map.getMapData(this.itemToRender, Minecraft.theWorld);
        if (mapData != null) {
            this.mc.entityRenderer.getMapItemRenderer().func_148250_a(mapData, false);
        }
    }
    
    private void func_178095_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        GlStateManager.translate(-0.3f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f), 0.4f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f * 2.0f), -0.4f * MathHelper.sin(n2 * 3.1415927f));
        GlStateManager.translate(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        GlStateManager.rotate(MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f) * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(5.6f, 0.0f, 0.0f);
        ((RenderPlayer)this.field_178111_g.getEntityRenderObject(Minecraft.thePlayer)).func_177138_b(Minecraft.thePlayer);
    }
    
    private void func_178105_d(final float n) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n * 3.1415927f));
    }
    
    private void func_178104_a(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = abstractClientPlayer.getItemInUseCount() - n + 1.0f;
        final float n3 = n2 / this.itemToRender.getMaxItemUseDuration();
        float abs = MathHelper.abs(MathHelper.cos(n2 / 4.0f * 3.1415927f) * 0.1f);
        if (n3 >= 0.8f) {
            abs = 0.0f;
        }
        GlStateManager.translate(0.0f, abs, 0.0f);
        final float n4 = 1.0f - (float)Math.pow(n3, 27.0);
        GlStateManager.translate(n4 * 0.6f, n4 * -0.5f, n4 * 0.0f);
        GlStateManager.rotate(n4 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n4 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n4 * 30.0f, 0.0f, 0.0f, 1.0f);
    }
    
    private void func_178096_b(final float n, final float n2) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }
    
    private void func_178098_a(final float n, final AbstractClientPlayer abstractClientPlayer) {
        GlStateManager.rotate(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);
        final float n2 = this.itemToRender.getMaxItemUseDuration() - (abstractClientPlayer.getItemInUseCount() - n + 1.0f);
        final float n3 = n2 / 20.0f;
        float n4 = (n3 * n3 + n3 * 2.0f) / 3.0f;
        if (n4 > 1.0f) {
            n4 = 1.0f;
        }
        if (n4 > 0.1f) {
            final float n5 = MathHelper.sin((n2 - 0.1f) * 1.3f) * (n4 - 0.1f);
            GlStateManager.translate(n5 * 0.0f, n5 * 0.01f, n5 * 0.0f);
        }
        GlStateManager.translate(n4 * 0.0f, n4 * 0.0f, n4 * 0.1f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f + n4 * 0.2f);
    }
    
    private void func_178103_d() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void transformFirstPersonItem(final float n, final float n2) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }
    
    public void renderItemInFirstPerson(final float n) {
        final float n2 = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * n);
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        final float swingProgress = thePlayer.getSwingProgress(n);
        final float n3 = thePlayer.prevRotationPitch + (thePlayer.rotationPitch - thePlayer.prevRotationPitch) * n;
        this.func_178101_a(n3, thePlayer.prevRotationYaw + (thePlayer.rotationYaw - thePlayer.prevRotationYaw) * n);
        this.func_178109_a(thePlayer);
        this.func_178110_a(thePlayer, n);
        if (this.itemToRender != null) {
            if (this.itemToRender.getItem() instanceof ItemMap) {
                this.func_178097_a(thePlayer, n3, n2, swingProgress);
            }
            else if (thePlayer.getItemInUseCount() > 0) {
                switch (ItemRenderer$1.field_178094_a[this.itemToRender.getItemUseAction().ordinal()]) {
                    case 1: {
                        this.func_178096_b(n2, 0.0f);
                        break;
                    }
                    case 2:
                    case 3: {
                        this.func_178104_a(thePlayer, n);
                        this.func_178096_b(n2, 0.0f);
                        break;
                    }
                    case 4: {
                        this.func_178096_b(n2, 0.0f);
                        this.func_178103_d();
                        break;
                    }
                    case 5: {
                        this.func_178096_b(n2, 0.0f);
                        this.func_178098_a(n, thePlayer);
                        break;
                    }
                }
            }
            else {
                this.func_178105_d(swingProgress);
                this.func_178096_b(n2, swingProgress);
            }
            this.renderItem(thePlayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
        else if (!thePlayer.isInvisible()) {
            this.func_178095_a(thePlayer, n2, swingProgress);
        }
    }
    
    public void renderOverlays(final float n) {
        if (Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
            BlockPos blockPos = new BlockPos(Minecraft.thePlayer);
            IBlockState blockState = Minecraft.theWorld.getBlockState(blockPos);
            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
            while (0 < 8) {
                blockPos = new BlockPos(thePlayer.posX + (0 - 0.5f) * thePlayer.width * 0.8f, thePlayer.posY + (0 - 0.5f) * 0.1f + thePlayer.getEyeHeight(), thePlayer.posZ + (0 - 0.5f) * thePlayer.width * 0.8f);
                final IBlockState blockState2 = Minecraft.theWorld.getBlockState(blockPos);
                if (blockState2.getBlock().isVisuallyOpaque()) {
                    blockState = blockState2;
                }
                int n2 = 0;
                ++n2;
            }
            if (blockState.getBlock().getRenderType() != -1 && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, Minecraft.thePlayer, n, Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK), blockState, blockPos)) {
                this.func_178108_a(n, this.mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(blockState));
            }
        }
        if (!Minecraft.thePlayer.func_175149_v()) {
            if (Minecraft.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, Minecraft.thePlayer, n)) {
                this.renderWaterOverlayTexture(n);
            }
            if (Minecraft.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, Minecraft.thePlayer, n)) {
                this.renderFireInFirstPerson(n);
            }
        }
    }
    
    private void func_178108_a(final float n, final TextureAtlasSprite textureAtlasSprite) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float n2 = 0.1f;
        GlStateManager.color(n2, n2, n2, 0.5f);
        final float n3 = -1.0f;
        final float n4 = 1.0f;
        final float n5 = -1.0f;
        final float n6 = 1.0f;
        final float n7 = -0.5f;
        final float minU = textureAtlasSprite.getMinU();
        final float maxU = textureAtlasSprite.getMaxU();
        final float minV = textureAtlasSprite.getMinV();
        final float maxV = textureAtlasSprite.getMaxV();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n3, n5, n7, maxU, maxV);
        worldRenderer.addVertexWithUV(n4, n5, n7, minU, maxV);
        worldRenderer.addVertexWithUV(n4, n6, n7, minU, minV);
        worldRenderer.addVertexWithUV(n3, n6, n7, maxU, minV);
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderWaterOverlayTexture(final float n) {
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_UNDERWATER_OVERLAY);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float brightness = Minecraft.thePlayer.getBrightness(n);
        GlStateManager.color(brightness, brightness, brightness, 0.5f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float n2 = 4.0f;
        final float n3 = -1.0f;
        final float n4 = 1.0f;
        final float n5 = -1.0f;
        final float n6 = 1.0f;
        final float n7 = -0.5f;
        final float n8 = -Minecraft.thePlayer.rotationYaw / 64.0f;
        final float n9 = Minecraft.thePlayer.rotationPitch / 64.0f;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n3, n5, n7, n2 + n8, n2 + n9);
        worldRenderer.addVertexWithUV(n4, n5, n7, 0.0f + n8, n2 + n9);
        worldRenderer.addVertexWithUV(n4, n6, n7, 0.0f + n8, 0.0f + n9);
        worldRenderer.addVertexWithUV(n3, n6, n7, n2 + n8, 0.0f + n9);
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderFireInFirstPerson(final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float n2 = 1.0f;
        while (0 < 2) {
            final TextureAtlasSprite atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            final float minU = atlasSprite.getMinU();
            final float maxU = atlasSprite.getMaxU();
            final float minV = atlasSprite.getMinV();
            final float maxV = atlasSprite.getMaxV();
            final float n3 = (0.0f - n2) / 2.0f;
            final float n4 = n3 + n2;
            final float n5 = 0.0f - n2 / 2.0f;
            final float n6 = n5 + n2;
            final float n7 = -0.5f;
            GlStateManager.translate(1 * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate(-1 * 10.0f, 0.0f, 1.0f, 0.0f);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(n3, n5, n7, maxU, maxV);
            worldRenderer.addVertexWithUV(n4, n5, n7, minU, maxV);
            worldRenderer.addVertexWithUV(n4, n6, n7, minU, minV);
            worldRenderer.addVertexWithUV(n3, n6, n7, maxU, minV);
            instance.draw();
            int n8 = 0;
            ++n8;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
        final ItemStack currentItem = thePlayer.inventory.getCurrentItem();
        if (this.itemToRender != null && currentItem != null) {
            if (!this.itemToRender.getIsItemStackEqual(currentItem)) {
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists() && !Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, this.itemToRender, currentItem, this.equippedItemSlot != thePlayer.inventory.currentItem)) {
                    this.itemToRender = currentItem;
                    this.equippedItemSlot = thePlayer.inventory.currentItem;
                    return;
                }
            }
        }
        else if (this.itemToRender != null || currentItem == null) {}
        final float n = 0.4f;
        this.equippedProgress += MathHelper.clamp_float((true ? 0.0f : 1.0f) - this.equippedProgress, -n, n);
        if (this.equippedProgress < 0.1f) {
            if (Config.isShaders()) {
                Shaders.itemToRender = currentItem;
            }
            this.itemToRender = currentItem;
            this.equippedItemSlot = thePlayer.inventory.currentItem;
        }
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
    
    static final class ItemRenderer$1
    {
        static final int[] field_178094_a;
        
        static {
            field_178094_a = new int[EnumAction.values().length];
        }
        
        static final class SwitchEnumAction
        {
            static final int[] field_178094_a;
            
            static {
                field_178094_a = new int[EnumAction.values().length];
                try {
                    SwitchEnumAction.field_178094_a[EnumAction.NONE.ordinal()] = 1;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    SwitchEnumAction.field_178094_a[EnumAction.EAT.ordinal()] = 2;
                }
                catch (NoSuchFieldError noSuchFieldError2) {}
                try {
                    SwitchEnumAction.field_178094_a[EnumAction.DRINK.ordinal()] = 3;
                }
                catch (NoSuchFieldError noSuchFieldError3) {}
                try {
                    SwitchEnumAction.field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
                }
                catch (NoSuchFieldError noSuchFieldError4) {}
                try {
                    SwitchEnumAction.field_178094_a[EnumAction.BOW.ordinal()] = 5;
                }
                catch (NoSuchFieldError noSuchFieldError5) {}
            }
        }
    }
}
