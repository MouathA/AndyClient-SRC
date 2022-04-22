package font.jello;

import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.awt.*;

public class RenderSystem
{
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawBorderedRectReliant(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n6);
        glColor(n7);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawGradientBorderedRectReliant(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7, final int n8) {
        drawGradientRect(n, n2, n3, n4, n8, n7);
        glColor(n6);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawRoundedRect(float n, float n2, float n3, float n4, final int n5, final int n6) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(n *= 2.0f, n2 *= 3.0f, n4 *= 0.0f, n5);
        drawVLine(n3 *= 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawStrip(final int n, final int n2, final float n3, final double n4, final float n5, final float n6, final int n7) {
        final float n8 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n7 & 0xFF) / 255.0f;
        GL11.glTranslated(n, n2, 0.0);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n3);
        int n13 = 0;
        if (n4 > 0.0) {
            GL11.glBegin(3);
            while (0 < n4) {
                final float n12 = (float)(0 * (n4 * 3.141592653589793 / n5));
                GL11.glVertex2f((float)(Math.cos(n12) * n6), (float)(Math.sin(n12) * n6));
                ++n13;
            }
        }
        if (n4 < 0.0) {
            GL11.glBegin(3);
            while (0 > n4) {
                final float n14 = (float)(0 * (n4 * 3.141592653589793 / n5));
                GL11.glVertex2f((float)(Math.cos(n14) * -n6), (float)(Math.sin(n14) * -n6));
                --n13;
            }
        }
        GL11.glDisable(3479);
    }
    
    public static void drawRect(final int n, final int n2, final int n3, final int n4, final int n5) {
        Gui.drawRect(n, n2, n3, (double)n4, n5);
    }
    
    public static void glColor(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void glColor(final float n, final int n2, final int n3, final int n4) {
        GL11.glColor4f(0.003921569f * n2, 0.003921569f * n3, 0.003921569f * n4, n);
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
    
    public static void drawPoint(final int n, final int n2, final int n3) {
        drawRect(n, n2, n + 1, n2 + 1, n3);
    }
    
    public static void drawVerticalLine(final int n, final int n2, final int n3, final int n4) {
        drawRect(n, n2, n + 1, n3, n4);
    }
    
    public static void drawHorizontalLine(final int n, final int n2, final int n3, final int n4) {
        drawRect(n, n2, n3, n2 + 1, n4);
    }
    
    public static void drawBorderedRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        drawRect(n + 1, n2 + 1, n3, n4, n6);
        drawVerticalLine(n, n2, n4, n5);
        drawVerticalLine(n3, n2, n4, n5);
        drawHorizontalLine(n + 1, n2, n3, n5);
        drawHorizontalLine(n, n4, n3 + 1, n5);
    }
    
    public static void drawFineBorderedRect(int n, int n2, int n3, int n4, final int n5, final int n6) {
        GL11.glScaled(0.5, 0.5, 0.5);
        n *= 2;
        n2 *= 2;
        n3 *= 2;
        n4 *= 2;
        drawRect(n + 1, n2 + 1, n3, n4, n6);
        drawVerticalLine(n, n2, n4, n5);
        drawVerticalLine(n3, n2, n4, n5);
        drawHorizontalLine(n + 1, n2, n3, n5);
        drawHorizontalLine(n, n4, n3 + 1, n5);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawBorderRectNoCorners(int n, int n2, int n3, int n4, final int n5, final int n6) {
        n *= 2;
        n2 *= 2;
        n3 *= 2;
        n4 *= 2;
        GL11.glScaled(0.5, 0.5, 0.5);
        drawRect(n + 1, n2 + 1, n3, n4, n6);
        drawVerticalLine(n, n2 + 1, n4, n5);
        drawVerticalLine(n3, n2 + 1, n4, n5);
        drawHorizontalLine(n + 1, n2, n3, n5);
        drawHorizontalLine(n + 1, n4, n3, n5);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawBorderedGradient(int n, int n2, int n3, int n4, final int n5, final int n6, final int n7) {
        GL11.glScaled(0.5, 0.5, 0.5);
        n *= 2;
        n2 *= 2;
        n3 *= 2;
        n4 *= 2;
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        final float n12 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n13 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n14 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n15 = (n7 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glVertex2d(n3, n2 + 1);
        GL11.glVertex2d(n + 1, n2 + 1);
        GL11.glColor4f(n13, n14, n15, n12);
        GL11.glVertex2d(n + 1, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        drawVLine((float)n, (float)n2, (float)(n4 - 1), n5);
        drawVLine((float)(n3 - 1), (float)n2, (float)(n4 - 1), n5);
        drawHLine((float)n, (float)(n3 - 1), (float)n2, n5);
        drawHLine((float)n, (float)(n3 - 1), (float)(n4 - 1), n5);
        GL11.glScaled(2.0, 2.0, 2.0);
    }
    
    public static void drawHLine(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        drawRect(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public static void drawVLine(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        drawRect(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public static void drawGradientBorderedRect(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7, final int n8) {
        final float n9 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n6 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3042);
        GL11.glColor4f(n10, n11, n12, n9);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        drawGradientRect(n, n2, n3, n4, n7, n8);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    public static void drawGradientRect(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glColor4f(n8, n9, n10, n7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glColor4f(n12, n13, n14, n11);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawSidewaysGradientRect(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glColor4f(n8, n9, n10, n7);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glColor4f(n12, n13, n14, n11);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawBorderedCircle(int n, int n2, float n3, final int n4, final int n5) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glScalef(0.1f, 0.1f, 0.1f);
        n *= 10;
        n2 *= 10;
        n3 *= 10.0f;
        drawCircle(n, n2, n3, n5);
        drawUnfilledCircle(n, n2, n3, 1.0f, n4);
        GL11.glScalef(10.0f, 10.0f, 10.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawUnfilledCircle(final int n, final int n2, final float n3, final float n4, final int n5) {
        GL11.glColor4f((n5 >> 16 & 0xFF) / 255.0f, (n5 >> 8 & 0xFF) / 255.0f, (n5 & 0xFF) / 255.0f, (n5 >> 24 & 0xFF) / 255.0f);
        GL11.glLineWidth(n4);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        GL11.glDisable(2848);
    }
    
    public static void drawCircle(final int n, final int n2, final float n3, final int n4) {
        GL11.glColor4f((n4 >> 16 & 0xFF) / 255.0f, (n4 >> 8 & 0xFF) / 255.0f, (n4 & 0xFF) / 255.0f, (n4 >> 24 & 0xFF) / 255.0f);
        GL11.glBegin(9);
    }
    
    public static void drawCircle(float n, float n2, float n3, final int n4, final int n5) {
        n *= 2.0f;
        n2 *= 2.0f;
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        final float n10 = (float)(6.2831852 / n4);
        final float n11 = (float)Math.cos(n10);
        final float n12 = (float)Math.sin(n10);
        float n13;
        n3 = (n13 = n3 * 2.0f);
        float n14 = 0.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(2);
        while (0 < n4) {
            GL11.glVertex2f(n13 + n, n14 + n2);
            final float n15 = n13;
            n13 = n11 * n13 - n12 * n14;
            n14 = n12 * n15 + n11 * n14;
            int n16 = 0;
            ++n16;
        }
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawFullCircle(int n, int n2, double n3, final int n4) {
        n3 *= 2.0;
        n *= 2;
        n2 *= 2;
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void setColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static double getAlphaFromHex(final int n) {
        return (n >> 24 & 0xFF) / 255.0f;
    }
    
    public static double getRedFromHex(final int n) {
        return (n >> 16 & 0xFF) / 255.0f;
    }
    
    public static double getGreenFromHex(final int n) {
        return (n >> 8 & 0xFF) / 255.0f;
    }
    
    public static double getBlueFromHex(final int n) {
        return (n & 0xFF) / 255.0f;
    }
}
