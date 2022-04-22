package net.minecraft.client.renderer;

import java.nio.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RenderHelper
{
    private static FloatBuffer colorBuffer;
    private static final Vec3 field_82884_b;
    private static final Vec3 field_82885_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000629";
        RenderHelper.colorBuffer = GLAllocation.createDirectFloatBuffer(16);
        field_82884_b = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
        field_82885_c = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    }
    
    public static void disableStandardItemLighting() {
        GlStateManager.disableBooleanStateAt(0);
        GlStateManager.disableBooleanStateAt(1);
    }
    
    public static void enableStandardItemLighting() {
        GlStateManager.enableBooleanStateAt(0);
        GlStateManager.enableBooleanStateAt(1);
        GlStateManager.colorMaterial(1032, 5634);
        final float n = 0.4f;
        final float n2 = 0.6f;
        final float n3 = 0.0f;
        GL11.glLight(16384, 4611, setColorBuffer(RenderHelper.field_82884_b.xCoord, RenderHelper.field_82884_b.yCoord, RenderHelper.field_82884_b.zCoord, 0.0));
        GL11.glLight(16384, 4609, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(16384, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, setColorBuffer(n3, n3, n3, 1.0f));
        GL11.glLight(16385, 4611, setColorBuffer(RenderHelper.field_82885_c.xCoord, RenderHelper.field_82885_c.yCoord, RenderHelper.field_82885_c.zCoord, 0.0));
        GL11.glLight(16385, 4609, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(16385, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, setColorBuffer(n3, n3, n3, 1.0f));
        GlStateManager.shadeModel(7424);
        GL11.glLightModel(2899, setColorBuffer(n, n, n, 1.0f));
    }
    
    private static FloatBuffer setColorBuffer(final double n, final double n2, final double n3, final double n4) {
        return setColorBuffer((float)n, (float)n2, (float)n3, (float)n4);
    }
    
    private static FloatBuffer setColorBuffer(final float n, final float n2, final float n3, final float n4) {
        RenderHelper.colorBuffer.clear();
        RenderHelper.colorBuffer.put(n).put(n2).put(n3).put(n4);
        RenderHelper.colorBuffer.flip();
        return RenderHelper.colorBuffer;
    }
    
    public static void enableGUIStandardItemLighting() {
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
    }
    
    public static void renderImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glEnable(3042);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
    }
    
    public static void renderImage(final String s, final int n, final int n2, final int n3, final int n4) {
        renderImage(new ResourceLocation(s), n, n2, n3, n4);
    }
    
    public static void drawTextureAt(final float n, final float n2, final ResourceLocation resourceLocation, final int n3, final int n4) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture((int)n, (int)n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
