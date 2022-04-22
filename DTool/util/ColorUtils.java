package DTool.util;

import java.awt.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class ColorUtils
{
    public static Color rainbowEffect(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static Color faszarainbow2(final long n, final double n2) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() * n2 + n) / 6.0) % 360.0 / 360.0), 0.8f, 0.9f);
    }
    
    public static int faszarainbow() {
        return Color.HSBtoRGB(System.currentTimeMillis() % 3000L / 3000.0f, 0.8f, 1.0f);
    }
    
    public static int toARGB(final int n, final int n2, final int n3, final int n4) {
        return (n4 & 0xFF) << 24 | (n & 0xFF) << 16 | (n2 & 0xFF) << 8 | (n3 & 0xFF);
    }
    
    public static void rainbowGL11() {
        final float n = System.currentTimeMillis() % 15000L / 4000.0f;
        GL11.glColor4d(0.5f + 0.5f * MathHelper.sin(n * 3.1415927f), 0.5f + 0.5f * MathHelper.sin((n + 1.3333334f) * 3.1415927f), 0.5f + 0.5f * MathHelper.sin((n + 2.6666667f) * 3.1415927f), 255.0);
    }
    
    public static Color rainbowColor(final long n, final int n2, final float n3) {
        final Color hsbColor = Color.getHSBColor((System.currentTimeMillis() + n) % 10000L / n2 / 10000.0f / n2, 0.8f, 0.8f);
        return new Color(hsbColor.getRed() / 255.0f, hsbColor.getGreen() / 255.0f, hsbColor.getBlue() / 255.0f, n3);
    }
    
    public static Color rainbowColor(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, n2);
    }
    
    public static Color getRainbow(final long n, final double n2) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() * n2 + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f);
    }
}
