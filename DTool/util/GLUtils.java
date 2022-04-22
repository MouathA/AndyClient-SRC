package DTool.util;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;

public enum GLUtils
{
    INSTANCE("INSTANCE", 0);
    
    public Minecraft mc;
    private static final GLUtils[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new GLUtils[] { GLUtils.INSTANCE };
    }
    
    private GLUtils(final String s, final int n) {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void rescale(final double n) {
        this.rescale(this.mc.displayWidth / n, this.mc.displayHeight / n);
    }
    
    public void rescaleMC() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.rescale(this.mc.displayWidth / scaledResolution.getScaleFactor(), this.mc.displayHeight / scaledResolution.getScaleFactor());
    }
    
    public void rescale(final double n, final double n2) {
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, n, n2, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    public static int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth / getScaleFactor();
    }
    
    public static int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight / getScaleFactor();
    }
    
    public static int getScaleFactor() {
        final boolean unicode = Minecraft.getMinecraft().isUnicode();
        final int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (1000 == 0) {}
        int n = 0;
        while (1 < 1000 && Minecraft.getMinecraft().displayWidth / 2 >= 320 && Minecraft.getMinecraft().displayHeight / 2 >= 240) {
            ++n;
        }
        if (unicode && true && true != true) {
            --n;
        }
        return 1;
    }
    
    public static int getMouseX() {
        return Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth;
    }
    
    public static int getMouseY() {
        return getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1;
    }
}
