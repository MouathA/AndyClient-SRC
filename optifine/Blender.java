package optifine;

import net.minecraft.client.renderer.*;

public class Blender
{
    public static int parseBlend(String trim) {
        if (trim == null) {
            return 1;
        }
        trim = trim.toLowerCase().trim();
        if (trim.equals("alpha")) {
            return 0;
        }
        if (trim.equals("add")) {
            return 1;
        }
        if (trim.equals("subtract")) {
            return 2;
        }
        if (trim.equals("multiply")) {
            return 3;
        }
        if (trim.equals("dodge")) {
            return 4;
        }
        if (trim.equals("burn")) {
            return 5;
        }
        if (trim.equals("screen")) {
            return 6;
        }
        if (trim.equals("overlay")) {
            return 7;
        }
        if (trim.equals("replace")) {
            return 8;
        }
        Config.warn("Unknown blend: " + trim);
        return 1;
    }
    
    public static void setupBlend(final int n, final float n2) {
        switch (n) {
            case 0: {
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
            case 1: {
                GlStateManager.blendFunc(770, 1);
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
            case 2: {
                GlStateManager.blendFunc(775, 0);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 3: {
                GlStateManager.blendFunc(774, 771);
                GlStateManager.color(n2, n2, n2, n2);
                break;
            }
            case 4: {
                GlStateManager.blendFunc(1, 1);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 5: {
                GlStateManager.blendFunc(0, 769);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 6: {
                GlStateManager.blendFunc(1, 769);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 7: {
                GlStateManager.blendFunc(774, 768);
                GlStateManager.color(n2, n2, n2, 1.0f);
                break;
            }
            case 8: {
                GlStateManager.color(1.0f, 1.0f, 1.0f, n2);
                break;
            }
        }
    }
    
    public static void clearBlend(final float n) {
        GlStateManager.blendFunc(770, 1);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
    }
}
