package DTool.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class RenderUtil
{
    public static Minecraft mc;
    
    static {
        RenderUtil.mc = Minecraft.getMinecraft();
    }
    
    public static void renderImage(final ResourceLocation resourceLocation, final float n, final float n2, final float n3, final float n4) {
        final float n5 = (n3 + n4) / 2.0f;
        final int round = Math.round(n5);
        GL11.glEnable(3042);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(n, n2);
        GL11.glTexCoord2d(0.0, n5 / round);
        GL11.glVertex2d(n, n2 + n4);
        GL11.glTexCoord2d(1.0, n5 / round);
        GL11.glVertex2d(n + n3, n2 + n4);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(n + n3, n2);
        GL11.glDisable(3042);
    }
    
    public static boolean isHovered(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        return n5 > n && n6 > n2 && n5 < n3 && n6 < n4;
    }
    
    public static void draw2DImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4, final Color color) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float)color.getAlpha());
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void color(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
}
