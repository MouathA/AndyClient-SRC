package shadersmod.client;

import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import optifine.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.*;

public class ShadersRender
{
    public static void setFrustrumPosition(final Frustrum frustrum, final double n, final double n2, final double n3) {
        frustrum.setPosition(n, n2, n3);
    }
    
    public static void setupTerrain(final RenderGlobal renderGlobal, final Entity entity, final double n, final ICamera camera, final int n2, final boolean b) {
        renderGlobal.func_174970_a(entity, n, camera, n2, b);
    }
    
    public static void updateChunks(final RenderGlobal renderGlobal, final long n) {
        renderGlobal.func_174967_a(n);
    }
    
    public static void beginTerrainSolid() {
        if (Shaders.isRenderingWorld) {
            Shaders.fogEnabled = true;
            Shaders.useProgram(7);
        }
    }
    
    public static void beginTerrainCutoutMipped() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }
    
    public static void beginTerrainCutout() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }
    
    public static void endTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }
    
    public static void beginTranslucent() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.usedDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33995);
                Shaders.checkGLError("pre copy depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
                Shaders.checkGLError("copy depth");
                GlStateManager.setActiveTexture(33984);
            }
            Shaders.useProgram(12);
        }
    }
    
    public static void endTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }
    
    public static void renderHand0(final EntityRenderer entityRenderer, final float n, final int n2) {
        if (!Shaders.isShadowPass) {
            final Item item = (Shaders.itemToRender != null) ? Shaders.itemToRender.getItem() : null;
            final Block block = (item instanceof ItemBlock) ? ((ItemBlock)item).getBlock() : null;
            if (!(item instanceof ItemBlock) || !(block instanceof Block) || block.getBlockLayer() == EnumWorldBlockLayer.SOLID) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                entityRenderer.renderHand(n, n2);
                Shaders.isHandRendered = true;
            }
        }
    }
    
    public static void renderHand1(final EntityRenderer entityRenderer, final float n, final int n2) {
        if (!Shaders.isShadowPass && !Shaders.isHandRendered) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            entityRenderer.renderHand(n, n2);
            Shaders.isHandRendered = true;
        }
    }
    
    public static void renderItemFP(final ItemRenderer itemRenderer, final float n) {
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        itemRenderer.renderItemInFirstPerson(n);
    }
    
    public static void renderFPOverlay(final EntityRenderer entityRenderer, final float n, final int n2) {
        if (!Shaders.isShadowPass) {
            entityRenderer.renderHand(n, n2);
        }
    }
    
    public static void beginBlockDamage() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(11);
            if (Shaders.programsID[11] == Shaders.programsID[7]) {
                Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
                GlStateManager.depthMask(false);
            }
        }
    }
    
    public static void endBlockDamage() {
        if (Shaders.isRenderingWorld) {
            GlStateManager.depthMask(true);
            Shaders.useProgram(3);
        }
    }
    
    public static void renderShadowMap(final EntityRenderer entityRenderer, final int n, final float cameraShadow, final long n2) {
        if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.mcProfiler.endStartSection("shadow pass");
            final RenderGlobal renderGlobal = minecraft.renderGlobal;
            Shaders.isShadowPass = true;
            Shaders.shadowPassCounter = Shaders.shadowPassInterval;
            Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.thirdPersonView;
            minecraft.gameSettings.thirdPersonView = 1;
            Shaders.checkGLError("pre shadow");
            GL11.glMatrixMode(5889);
            GL11.glMatrixMode(5888);
            minecraft.mcProfiler.endStartSection("shadow clear");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
            Shaders.checkGLError("shadow bind sfb");
            Shaders.useProgram(30);
            minecraft.mcProfiler.endStartSection("shadow camera");
            entityRenderer.setupCameraTransform(cameraShadow, 2);
            Shaders.setCameraShadow(cameraShadow);
            ActiveRenderInfo.updateRenderInfo(Minecraft.thePlayer, minecraft.gameSettings.thirdPersonView == 2);
            Shaders.checkGLError("shadow camera");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers");
            GL11.glReadBuffer(0);
            Shaders.checkGLError("shadow readbuffer");
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
            if (Shaders.usedShadowColorBuffers != 0) {
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
            }
            Shaders.checkFramebufferStatus("shadow fb");
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
            Shaders.checkGLError("shadow clear");
            minecraft.mcProfiler.endStartSection("shadow frustum");
            final ClippingHelper instance = ClippingHelperShadow.getInstance();
            minecraft.mcProfiler.endStartSection("shadow culling");
            final Frustrum frustrum = new Frustrum(instance);
            final Entity func_175606_aa = minecraft.func_175606_aa();
            frustrum.setPosition(func_175606_aa.lastTickPosX + (func_175606_aa.posX - func_175606_aa.lastTickPosX) * cameraShadow, func_175606_aa.lastTickPosY + (func_175606_aa.posY - func_175606_aa.lastTickPosY) * cameraShadow, func_175606_aa.lastTickPosZ + (func_175606_aa.posZ - func_175606_aa.lastTickPosZ) * cameraShadow);
            GlStateManager.shadeModel(7425);
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            minecraft.mcProfiler.endStartSection("shadow prepareterrain");
            minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            minecraft.mcProfiler.endStartSection("shadow setupterrain");
            renderGlobal.func_174970_a(func_175606_aa, cameraShadow, frustrum, entityRenderer.field_175084_ae++, Minecraft.thePlayer.func_175149_v());
            minecraft.mcProfiler.endStartSection("shadow updatechunks");
            minecraft.mcProfiler.endStartSection("shadow terrain");
            GlStateManager.matrixMode(5888);
            renderGlobal.func_174977_a(EnumWorldBlockLayer.SOLID, cameraShadow, 2, func_175606_aa);
            Shaders.checkGLError("shadow terrain solid");
            renderGlobal.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, cameraShadow, 2, func_175606_aa);
            Shaders.checkGLError("shadow terrain cutoutmipped");
            minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
            renderGlobal.func_174977_a(EnumWorldBlockLayer.CUTOUT, cameraShadow, 2, func_175606_aa);
            Shaders.checkGLError("shadow terrain cutout");
            minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
            GlStateManager.shadeModel(7424);
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.matrixMode(5888);
            minecraft.mcProfiler.endStartSection("shadow entities");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
            }
            renderGlobal.func_180446_a(func_175606_aa, frustrum, cameraShadow);
            Shaders.checkGLError("shadow entities");
            GlStateManager.matrixMode(5888);
            GlStateManager.depthMask(true);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            if (Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33989);
                Shaders.checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                Shaders.checkGLError("copy shadow depth");
                GlStateManager.setActiveTexture(33984);
            }
            GlStateManager.depthMask(true);
            minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.shadeModel(7425);
            Shaders.checkGLError("shadow pre-translucent");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers pre-translucent");
            Shaders.checkFramebufferStatus("shadow pre-translucent");
            minecraft.mcProfiler.endStartSection("shadow translucent");
            renderGlobal.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, cameraShadow, 2, func_175606_aa);
            Shaders.checkGLError("shadow translucent");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, 1);
                renderGlobal.func_180446_a(func_175606_aa, frustrum, cameraShadow);
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, -1);
                Shaders.checkGLError("shadow entities 1");
            }
            GlStateManager.shadeModel(7424);
            GlStateManager.depthMask(true);
            Shaders.checkGLError("shadow flush");
            Shaders.isShadowPass = false;
            minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
            minecraft.mcProfiler.endStartSection("shadow postprocess");
            if (Shaders.hasGlGenMipmap) {
                if (Shaders.usedShadowDepthBuffers >= 1) {
                    if (Shaders.shadowMipmapEnabled[0]) {
                        GlStateManager.setActiveTexture(33988);
                        GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
                    }
                    if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
                        GlStateManager.setActiveTexture(33989);
                        GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
                    }
                    GlStateManager.setActiveTexture(33984);
                }
                if (Shaders.usedShadowColorBuffers >= 1) {
                    if (Shaders.shadowColorMipmapEnabled[0]) {
                        GlStateManager.setActiveTexture(33997);
                        GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
                    }
                    if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
                        GlStateManager.setActiveTexture(33998);
                        GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
                    }
                    GlStateManager.setActiveTexture(33984);
                }
            }
            Shaders.checkGLError("shadow postprocess");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.activeDrawBuffers = null;
            minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            Shaders.useProgram(7);
            GL11.glMatrixMode(5888);
            GL11.glMatrixMode(5889);
            GL11.glMatrixMode(5888);
            Shaders.checkGLError("shadow end");
        }
    }
    
    public static void preRenderChunkLayer() {
        if (OpenGlHelper.func_176075_f()) {
            GL11.glEnableClientState(32885);
            GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        }
    }
    
    public static void postRenderChunkLayer() {
        if (OpenGlHelper.func_176075_f()) {
            GL11.glDisableClientState(32885);
            GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        }
    }
    
    public static void setupArrayPointersVbo() {
        GL11.glVertexPointer(3, 5126, 56, 0L);
        GL11.glColorPointer(4, 5121, 56, 12L);
        GL11.glTexCoordPointer(2, 5126, 56, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer(2, 5122, 56, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glNormalPointer(5120, 56, 28L);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
    }
    
    public static void beaconBeamBegin() {
        Shaders.useProgram(14);
    }
    
    public static void beaconBeamStartQuad1() {
    }
    
    public static void beaconBeamStartQuad2() {
    }
    
    public static void beaconBeamDraw1() {
    }
    
    public static void beaconBeamDraw2() {
    }
    
    public static void layerArmorBaseDrawEnchantedGlintBegin() {
        Shaders.useProgram(17);
    }
    
    public static void layerArmorBaseDrawEnchantedGlintEnd() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(16);
        }
        else {
            Shaders.useProgram(0);
        }
    }
}
