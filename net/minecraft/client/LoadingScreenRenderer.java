package net.minecraft.client;

import net.minecraft.client.shader.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private String field_73727_a;
    private Minecraft mc;
    private String currentlyDisplayedText;
    private long field_73723_d;
    private boolean field_73724_e;
    private ScaledResolution field_146587_f;
    private Framebuffer field_146588_g;
    private static final String __OBFID;
    
    public LoadingScreenRenderer(final Minecraft mc) {
        this.field_73727_a = "";
        this.currentlyDisplayedText = "";
        this.field_73723_d = Minecraft.getSystemTime();
        this.mc = mc;
        this.field_146587_f = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        (this.field_146588_g = new Framebuffer(mc.displayWidth, mc.displayHeight, false)).setFramebufferFilter(9728);
    }
    
    @Override
    public void resetProgressAndMessage(final String s) {
        this.field_73724_e = false;
        this.func_73722_d(s);
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.field_73724_e = true;
        this.func_73722_d(s);
    }
    
    private void func_73722_d(final String currentlyDisplayedText) {
        this.currentlyDisplayedText = currentlyDisplayedText;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            if (OpenGlHelper.isFramebufferEnabled()) {
                final int scaleFactor = this.field_146587_f.getScaleFactor();
                GlStateManager.ortho(0.0, ScaledResolution.getScaledWidth() * scaleFactor, ScaledResolution.getScaledHeight() * scaleFactor, 0.0, 100.0, 300.0);
            }
            else {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
            }
            GlStateManager.matrixMode(5888);
            GlStateManager.translate(0.0f, 0.0f, -200.0f);
        }
    }
    
    @Override
    public void displayLoadingString(final String field_73727_a) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            this.field_73723_d = 0L;
            this.field_73727_a = field_73727_a;
            this.setLoadingProgress(-1);
            this.field_73723_d = 0L;
        }
    }
    
    @Override
    public void setLoadingProgress(final int n) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final long systemTime = Minecraft.getSystemTime();
            if (systemTime - this.field_73723_d >= 100L) {
                this.field_73723_d = systemTime;
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                final int scaleFactor = scaledResolution.getScaleFactor();
                final int scaledWidth = ScaledResolution.getScaledWidth();
                final int scaledHeight = ScaledResolution.getScaledHeight();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferClear();
                }
                else {
                    GlStateManager.clear(256);
                }
                this.field_146588_g.bindFramebuffer(false);
                GlStateManager.matrixMode(5889);
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.translate(0.0f, 0.0f, -200.0f);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.clear(16640);
                }
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                final float n2 = 32.0f;
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178991_c(4210752);
                worldRenderer.addVertexWithUV(0.0, scaledHeight, 0.0, 0.0, scaledHeight / n2);
                worldRenderer.addVertexWithUV(scaledWidth, scaledHeight, 0.0, scaledWidth / n2, scaledHeight / n2);
                worldRenderer.addVertexWithUV(scaledWidth, 0.0, 0.0, scaledWidth / n2, 0.0);
                worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                instance.draw();
                if (n >= 0) {
                    final int n3 = scaledWidth / 2 - 0;
                    final int n4 = scaledHeight / 2 + 16;
                    worldRenderer.startDrawingQuads();
                    worldRenderer.func_178991_c(8421504);
                    worldRenderer.addVertex(n3, n4, 0.0);
                    worldRenderer.addVertex(n3, n4 + 2, 0.0);
                    worldRenderer.addVertex(n3 + 100, n4 + 2, 0.0);
                    worldRenderer.addVertex(n3 + 100, n4, 0.0);
                    worldRenderer.func_178991_c(8454016);
                    worldRenderer.addVertex(n3, n4, 0.0);
                    worldRenderer.addVertex(n3, n4 + 2, 0.0);
                    worldRenderer.addVertex(n3 + n, n4 + 2, 0.0);
                    worldRenderer.addVertex(n3 + n, n4, 0.0);
                    instance.draw();
                }
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                Minecraft.fontRendererObj.func_175063_a(this.currentlyDisplayedText, (float)((scaledWidth - Minecraft.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (float)(scaledHeight / 2 - 4 - 16), 16777215);
                Minecraft.fontRendererObj.func_175063_a(this.field_73727_a, (float)((scaledWidth - Minecraft.fontRendererObj.getStringWidth(this.field_73727_a)) / 2), (float)(scaledHeight / 2 - 4 + 8), 16777215);
                this.field_146588_g.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferRender(scaledWidth * scaleFactor, scaledHeight * scaleFactor);
                }
                this.mc.func_175601_h();
                Thread.yield();
            }
        }
    }
    
    @Override
    public void setDoneWorking() {
    }
    
    static {
        __OBFID = "CL_00000655";
    }
}
