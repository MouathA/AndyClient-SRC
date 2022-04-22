package Mood;

import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class InstanceRender
{
    public boolean ingameInfos;
    public boolean cmdTab;
    public boolean ingamegui;
    public Frame modulelistframe;
    
    public InstanceRender() {
        this.ingameInfos = true;
        this.cmdTab = false;
        this.ingamegui = false;
        this.modulelistframe = null;
    }
    
    public void renderingameInfos() {
    }
    
    public static void drawESP(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), 16711680);
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawESP(final AxisAlignedBB axisAlignedBB, final double n, final double n2, final double n3) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(n, n3, n2, 0.10010000318288803);
        GL11.glColor4d(n, n3, n2, 1.0);
        drawOutlinedBoundingBox(axisAlignedBB);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.draw();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.draw();
    }
    
    public static void drawCircle(final int n, final int n2, final double n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(2);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawFilledCircle(final int n, final int n2, final double n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(6);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void dr(double n, double n2, double n3, double n4, final int n5) {
        if (n < n3) {
            final double n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n9, n10, n11, n8);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(n, n4, 0.0);
        worldRenderer.addVertex(n3, n4, 0.0);
        worldRenderer.addVertex(n3, n2, 0.0);
        worldRenderer.addVertex(n, n2, 0.0);
        worldRenderer.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
