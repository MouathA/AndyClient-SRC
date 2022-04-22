package DTool.util;

import java.awt.*;

public class ColorUtil
{
    public static String g;
    
    static {
        ColorUtil.g = "no";
    }
    
    public static String getColor(final EnumColor enumColor) {
        final EnumColor[] values = EnumColor.values();
        while (0 < values.length) {
            if (values[0] == enumColor && 0 < 10) {
                return "§" + 0;
            }
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        if (enumColor == EnumColor.Green) {
            return "§a";
        }
        if (enumColor == EnumColor.Aqua) {
            return "§b";
        }
        if (enumColor == EnumColor.Red) {
            return "§c";
        }
        if (enumColor == EnumColor.Purple) {
            return "§d";
        }
        if (enumColor == EnumColor.Yellow) {
            return "§e";
        }
        if (enumColor == EnumColor.White) {
            return "§f";
        }
        return "§0";
    }
    
    public static int getHexColor(final EnumColor enumColor) {
        if (enumColor == EnumColor.Black) {
            return -16777216;
        }
        if (enumColor == EnumColor.DarkBlue) {
            return -16777046;
        }
        if (enumColor == EnumColor.DarkGreen) {
            return -16733696;
        }
        if (enumColor == EnumColor.DarkAqua) {
            return -16733526;
        }
        if (enumColor == EnumColor.DarkRed) {
            return -5636096;
        }
        if (enumColor == EnumColor.DarkPurple) {
            return -5635926;
        }
        if (enumColor == EnumColor.Gold) {
            return -22016;
        }
        if (enumColor == EnumColor.Gray) {
            return -5592406;
        }
        if (enumColor == EnumColor.DarkGray) {
            return -11184811;
        }
        if (enumColor == EnumColor.Blue) {
            return -11184641;
        }
        if (enumColor == EnumColor.Green) {
            return -11141291;
        }
        if (enumColor == EnumColor.Aqua) {
            return -11141121;
        }
        if (enumColor == EnumColor.Red) {
            return -43691;
        }
        if (enumColor == EnumColor.Purple) {
            return -43521;
        }
        if (enumColor == EnumColor.Yellow) {
            return -171;
        }
        if (enumColor == EnumColor.White) {
            return -1;
        }
        return -16777216;
    }
    
    public static int getRainbow(final float n, final float n2, final float n3) {
        return Color.HSBtoRGB(System.currentTimeMillis() % (int)(n * 1000.0f) / (n * 1000.0f), n2, n3);
    }
    
    public static int getRainbow(final float n, final float n2, final float n3, final long n4) {
        return Color.HSBtoRGB((System.currentTimeMillis() + n4) % (int)(n * 1000.0f) / (n * 1000.0f), n2, n3);
    }
}
