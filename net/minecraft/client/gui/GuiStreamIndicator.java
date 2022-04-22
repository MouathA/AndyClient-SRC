package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiStreamIndicator
{
    private static final ResourceLocation locationStreamIndicator;
    private final Minecraft mc;
    private float field_152443_c;
    private int field_152444_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001849";
        locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
    }
    
    public GuiStreamIndicator(final Minecraft mc) {
        this.field_152443_c = 1.0f;
        this.field_152444_d = 1;
        this.mc = mc;
    }
    
    public void render(final int n, final int n2) {
        if (this.mc.getTwitchStream().func_152934_n()) {
            final int func_152920_A = this.mc.getTwitchStream().func_152920_A();
            if (func_152920_A > 0) {
                final String string = new StringBuilder().append(func_152920_A).toString();
                final int stringWidth = Minecraft.fontRendererObj.getStringWidth(string);
                final int n3 = n - stringWidth - 1;
                final int n4 = n2 + 20 - 1;
                final int n5 = n2 + 20 + Minecraft.fontRendererObj.FONT_HEIGHT - 1;
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                GlStateManager.color(0.0f, 0.0f, 0.0f, (0.65f + 0.35000002f * this.field_152443_c) / 2.0f);
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertex(n3, n5, 0.0);
                worldRenderer.addVertex(n, n5, 0.0);
                worldRenderer.addVertex(n, n4, 0.0);
                worldRenderer.addVertex(n3, n4, 0.0);
                instance.draw();
                Minecraft.fontRendererObj.drawString(string, n - stringWidth, n2 + 20, 16777215);
            }
            this.render(n, n2, this.func_152440_b(), 0);
            this.render(n, n2, this.func_152438_c(), 17);
        }
    }
    
    private void render(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.field_152443_c);
        this.mc.getTextureManager().bindTexture(GuiStreamIndicator.locationStreamIndicator);
        final float n5 = 150.0f;
        final float n6 = 0.0f;
        final float n7 = n3 * 0.015625f;
        final float n8 = 1.0f;
        final float n9 = (n3 + 16) * 0.015625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n - 16 - n4, n2 + 16, n5, n6, n9);
        worldRenderer.addVertexWithUV(n - n4, n2 + 16, n5, n8, n9);
        worldRenderer.addVertexWithUV(n - n4, n2 + 0, n5, n8, n7);
        worldRenderer.addVertexWithUV(n - 16 - n4, n2 + 0, n5, n6, n7);
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private int func_152440_b() {
        return this.mc.getTwitchStream().isPaused() ? 16 : 0;
    }
    
    private int func_152438_c() {
        return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
    }
    
    public void func_152439_a() {
        if (this.mc.getTwitchStream().func_152934_n()) {
            this.field_152443_c += 0.025f * this.field_152444_d;
            if (this.field_152443_c < 0.0f) {
                this.field_152444_d = -this.field_152444_d;
                this.field_152443_c = 0.0f;
            }
            else if (this.field_152443_c > 1.0f) {
                this.field_152444_d = -this.field_152444_d;
                this.field_152443_c = 1.0f;
            }
        }
        else {
            this.field_152443_c = 1.0f;
            this.field_152444_d = 1;
        }
    }
}
