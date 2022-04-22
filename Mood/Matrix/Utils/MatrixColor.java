package Mood.Matrix.Utils;

import java.awt.*;

public enum MatrixColor
{
    INSTANCE("INSTANCE", 0);
    
    private static final MatrixColor[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new MatrixColor[] { MatrixColor.INSTANCE };
    }
    
    private MatrixColor(final String s, final int n) {
    }
    
    public static Color transpartensColor(final Color color, final int n) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
    }
    
    public static Color rainbow() {
        final long n = 0L;
        final float n2 = 1.0f;
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static int rainbow(final long n) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0) / 360.0f, 0.8f, 0.7f).getRGB();
    }
    
    public static Color crainbow(final long n) {
        return new Color((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0) / 360.0f, 0.8f, 0.7f);
    }
    
    public static Color getRainbow(final long n, final double n2) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() * n2 + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f);
    }
    
    public static int toARGB(final int n, final int n2, final int n3, final int n4) {
        return (n4 & 0xFF) << 24 | (n & 0xFF) << 16 | (n2 & 0xFF) << 8 | (n3 & 0xFF);
    }
    
    public static Color rainbowColor(final long n, final int n2, final float n3) {
        final Color hsbColor = Color.getHSBColor((System.currentTimeMillis() + n) % 10000L / n2 / 10000.0f / n2, 0.8f, 0.8f);
        return new Color(hsbColor.getRed() / 255.0f, hsbColor.getGreen() / 255.0f, hsbColor.getBlue() / 255.0f, n3);
    }
    
    public static Color rainbowColor(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, n2);
    }
    
    public int IntRainbow(final long n) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0) / 360.0f, 0.8f, 0.7f).getRGB();
    }
}
