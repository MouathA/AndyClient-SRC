package Mood.Helpers;

import java.awt.*;
import net.minecraft.util.*;

public class Colors
{
    public static Color getRainbow(final long n, final double n2) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() * n2 + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f);
    }
    
    public static int getColor(final int n, final int n2, final int n3, final int n4) {
        return MathHelper.clamp_int(n4, 0, 255) << 24 | MathHelper.clamp_int(n, 0, 255) << 16 | MathHelper.clamp_int(n2, 0, 255) << 8 | MathHelper.clamp_int(n3, 0, 255);
    }
    
    public static String getColor(final int n) {
        if (n != 1) {
            if (n == 2) {
                return "§a";
            }
            if (n == 3) {
                return "§3";
            }
            if (n == 4) {
                return "§4";
            }
            if (n >= 5) {
                return "§e";
            }
        }
        return "§f";
    }
    
    public static int getColorFromPercentage(final float n) {
        return Color.HSBtoRGB(n / 3.0f, 1.0f, 1.0f);
    }
    
    public static int getColor(final int n, final int n2) {
        return 0;
    }
}
