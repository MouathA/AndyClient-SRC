package wdl.gui;

import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.net.*;

class Utils
{
    private static final Minecraft mc;
    private static final Logger logger;
    
    static {
        mc = Minecraft.getMinecraft();
        logger = LogManager.getLogger();
    }
    
    public static void drawGuiInfoBox(final String s, final int n, final int n2, final int n3) {
        drawGuiInfoBox(s, 300, 100, n, n2, n3);
    }
    
    public static void drawGuiInfoBox(final String s, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (s == null) {
            return;
        }
        final int n6 = n3 / 2 - n / 2;
        final int n7 = n4 - n5 - n2;
        int n8 = n7 + 5;
        Gui.drawRect(n6, n7, n6 + n, n7 + n2, 2130706432);
        final Iterator<String> iterator = wordWrap(s, n - 10).iterator();
        while (iterator.hasNext()) {
            Minecraft.fontRendererObj.drawString(iterator.next(), n6 + 5, n8, 16777215);
            n8 += Minecraft.fontRendererObj.FONT_HEIGHT;
        }
    }
    
    public static List wordWrap(String replace, final int n) {
        replace = replace.replace("\r", "");
        return Minecraft.fontRendererObj.listFormattedStringToWidth(replace, n);
    }
    
    public static void drawListBackground(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        drawDarkBackground(n3, n4, n5, n6);
        drawBorder(n, n2, n3, n4, n5, n6);
    }
    
    public static void drawDarkBackground(final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        Utils.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n5 = 32.0f;
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178991_c(2105376);
        worldRenderer.addVertexWithUV(0.0, n3, 0.0, 0.0f / n5, n3 / n5);
        worldRenderer.addVertexWithUV(n4, n3, 0.0, n4 / n5, n3 / n5);
        worldRenderer.addVertexWithUV(n4, n, 0.0, n4 / n5, n / n5);
        worldRenderer.addVertexWithUV(n2, n, 0.0, n2 / n5, n / n5);
        instance.draw();
    }
    
    public static void drawBorder(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        Utils.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n7 = 32.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final int n8 = n3 + n;
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178974_a(4210752, 255);
        worldRenderer.addVertexWithUV(n4, n8, 0.0, 0.0, n8 / n7);
        worldRenderer.addVertexWithUV(n6, n8, 0.0, n6 / n7, n8 / n7);
        worldRenderer.func_178974_a(4210752, 255);
        worldRenderer.addVertexWithUV(n6, n3, 0.0, n6 / n7, n3 / n7);
        worldRenderer.addVertexWithUV(n4, n3, 0.0, 0.0, n3 / n7);
        instance.draw();
        final int n9 = n5 - n2;
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178974_a(4210752, 255);
        worldRenderer.addVertexWithUV(n4, n5, 0.0, 0.0, n5 / n7);
        worldRenderer.addVertexWithUV(n6, n5, 0.0, n6 / n7, n5 / n7);
        worldRenderer.func_178974_a(4210752, 255);
        worldRenderer.addVertexWithUV(n6, n9, 0.0, n6 / n7, n9 / n7);
        worldRenderer.addVertexWithUV(n4, n9, 0.0, 0.0, n9 / n7);
        instance.draw();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.shadeModel(7425);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178974_a(0, 0);
        worldRenderer.addVertexWithUV(n4, n8 + 4, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(n6, n8 + 4, 0.0, 1.0, 1.0);
        worldRenderer.func_178974_a(0, 255);
        worldRenderer.addVertexWithUV(n6, n8, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(n4, n8, 0.0, 0.0, 0.0);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178974_a(0, 255);
        worldRenderer.addVertexWithUV(n4, n9, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(n6, n9, 0.0, 1.0, 1.0);
        worldRenderer.func_178974_a(0, 0);
        worldRenderer.addVertexWithUV(n6, n9 - 4, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(n4, n9 - 4, 0.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.shadeModel(7424);
    }
    
    public static boolean isMouseOverTextBox(final int n, final int n2, final GuiTextField guiTextField) {
        final int n3 = n - guiTextField.xPosition;
        final int n4 = n2 - guiTextField.yPosition;
        return n3 >= 0 && n3 < guiTextField.getWidth() && n4 >= 0 && n4 < 20;
    }
    
    public static void openLink(final String s) {
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new URI(s));
    }
    
    public static void drawStringWithShadow(final String s, final int n, final int n2, final int n3) {
        Minecraft.fontRendererObj.func_175063_a(s, (float)n, (float)n2, n3);
    }
}
