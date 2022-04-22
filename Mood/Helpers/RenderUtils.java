package Mood.Helpers;

import java.util.regex.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import DTool.util.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class RenderUtils
{
    public static final Pattern COLOR_PATTERN;
    private static final AxisAlignedBB DEFAULT_AABB;
    private static int[] $SWITCH_TABLE$Mood$Helpers$RenderUtils$RainbowDirection;
    
    static {
        COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
        DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
    
    public static long renderRainbowRect(final int n, final int n2, final int n3, final int n4, final double n5, final long n6, final long n7, final RainbowDirection rainbowDirection) {
        long n8 = 0L;
        switch ($SWITCH_TABLE$Mood$Helpers$RenderUtils$RainbowDirection()[rainbowDirection.ordinal()]) {
            case 3: {
                while (0 < n3 - n) {
                    Gui.drawRect(n + 0, n2, n3, n4, Colors.getRainbow(n8 = n7 + 0 * -n6, n5).getRGB());
                    int n9 = 0;
                    ++n9;
                }
                break;
            }
            case 1: {
                while (0 < n3 - n) {
                    Gui.drawRect(n + 0, n2, n3, n4, Colors.getRainbow(n8 = n7 + 0 * n6, n5).getRGB());
                    int n9 = 0;
                    ++n9;
                }
                break;
            }
            case 4: {
                while (0 < n4 - n2) {
                    Gui.drawRect(n, n2 + 0, n3, n4, Colors.getRainbow(n8 = n7 + 0 * -n6, n5).getRGB());
                    int n9 = 0;
                    ++n9;
                }
                break;
            }
            case 2: {
                while (0 < n4 - n2) {
                    Gui.drawRect(n, n2 + 0, n3, n4, Colors.getRainbow(n8 = n7 + 0 * n6, n5).getRGB());
                    int n9 = 0;
                    ++n9;
                }
                break;
            }
        }
        return n8;
    }
    
    public static void drawSolidBox() {
        drawSolidBox(RenderUtils.DEFAULT_AABB);
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
    }
    
    public static void drawSolidBox(final AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(7);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
    }
    
    public static void drawOutlinedBox() {
        drawOutlinedBox(RenderUtils.DEFAULT_AABB);
    }
    
    public static void drawESP(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(58.0, 142.0, 238.0, 0.18250000476837158);
        drawSolidBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 0.5, n3 + 1.0));
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        drawOutlinedBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 0.5, n3 + 1.0));
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void drawWayPointTracer(final Waypoint waypoint) {
        GL11.glEnable(2848);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glColor3d(waypoint.red, waypoint.green, waypoint.blue);
        GL11.glBegin(2);
        final Vec3 rotateYaw = new Vec3(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.getPlayer().rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.getPlayer().rotationYaw));
        GL11.glVertex3d(rotateYaw.xCoord, Wrapper.getPlayer().getEyeHeight() + rotateYaw.yCoord, rotateYaw.zCoord);
        GL11.glVertex3d(waypoint.dX + 0.5, waypoint.dY + 0.5, waypoint.dZ + 0.5);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glDisable(2848);
    }
    
    public static void drawCircle(final double n, final double n2, final float n3, final int n4) {
        GL11.glColor4f((n4 >> 16 & 0xFF) / 255.0f, (n4 >> 8 & 0xFF) / 255.0f, (n4 & 0xFF) / 255.0f, (n4 >> 24 & 0xFF) / 255.0f);
        GL11.glBegin(9);
        while (0 <= 360) {
            GL11.glVertex2d(n + Math.sin(0 * 3.141526 / 180.0) * n3, n2 + Math.cos(0 * 3.141526 / 180.0) * n3);
            int n5 = 0;
            ++n5;
        }
    }
    
    public static void drawBorderedCircle(double n, double n2, float n3, final int n4, final int n5) {
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glScalef(0.1f, 0.1f, 0.1f);
        n *= 10.0;
        n2 *= 10.0;
        drawCircle(n, n2, n3 *= 10.0f, n5);
        GL11.glScalef(10.0f, 10.0f, 10.0f);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
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
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4) {
        GL11.glBegin(7);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
    }
    
    public static Color glColor(final int n) {
        final float n2 = (n >> 24 & 0xFF) / 256.0f;
        final float n3 = (n >> 16 & 0xFF) / 255.0f;
        final float n4 = (n >> 8 & 0xFF) / 255.0f;
        final float n5 = (n & 0xFF) / 255.0f;
        GL11.glColor4f(n3, n4, n5, n2);
        return new Color(n3, n4, n5, n2);
    }
    
    public Color glColor(final float n, final int n2, final int n3, final int n4) {
        final float n5 = 0.003921569f * n2;
        final float n6 = 0.003921569f * n3;
        final float n7 = 0.003921569f * n4;
        GL11.glColor4f(n5, n6, n7, n);
        return new Color(n5, n6, n7, n);
    }
    
    public static void drawRoundedRectangle(double n, double n2, double n3, double n4, final double n5, final int n6) {
        GL11.glScaled(0.5, 0.5, 0.5);
        n *= 2.0;
        n2 *= 2.0;
        n3 *= 2.0;
        n4 *= 2.0;
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        glColor(n6);
        GL11.glBegin(9);
        int n7 = 0;
        while (90 <= 90) {
            GL11.glVertex2d(n + n5 + Math.sin(90 * 3.141592653589793 / 180.0) * n5 * -1.0, n2 + n5 + Math.cos(90 * 3.141592653589793 / 180.0) * n5 * -1.0);
            ++n7;
        }
        while (90 <= 180) {
            GL11.glVertex2d(n + n5 + Math.sin(90 * 3.141592653589793 / 180.0) * n5 * -1.0, n4 - n5 + Math.cos(90 * 3.141592653589793 / 180.0) * n5 * -1.0);
            ++n7;
        }
        while (90 <= 90) {
            GL11.glVertex2d(n3 - n5 + Math.sin(90 * 3.141592653589793 / 180.0) * n5, n4 - n5 + Math.cos(90 * 3.141592653589793 / 180.0) * n5);
            ++n7;
        }
        while (90 <= 180) {
            GL11.glVertex2d(n3 - n5 + Math.sin(90 * 3.141592653589793 / 180.0) * n5, n2 + n5 + Math.cos(90 * 3.141592653589793 / 180.0) * n5);
            ++n7;
        }
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }
    
    public static int transparency(final int n, final double n2) {
        final Color color = new Color(n);
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), (float)n2).getRGB();
    }
    
    public static int getRainbow(final int n, final int n2, final float n3) {
        return Color.getHSBColor((System.currentTimeMillis() + n2) % n / (float)n, n3, 1.0f).getRGB();
    }
    
    public static void drawRect(final double n, final double n2, final double n3, final double n4, final int n5) {
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glColor(n5);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
    }
    
    static int[] $SWITCH_TABLE$Mood$Helpers$RenderUtils$RainbowDirection() {
        final int[] $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection = RenderUtils.$SWITCH_TABLE$Mood$Helpers$RenderUtils$RainbowDirection;
        if ($switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection != null) {
            return $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection;
        }
        final int[] $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2 = new int[RainbowDirection.values().length];
        $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2[RainbowDirection.DOWN.ordinal()] = 4;
        $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2[RainbowDirection.LEFT.ordinal()] = 1;
        $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2[RainbowDirection.RIGHT.ordinal()] = 3;
        $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2[RainbowDirection.UP.ordinal()] = 2;
        return RenderUtils.$SWITCH_TABLE$Mood$Helpers$RenderUtils$RainbowDirection = $switch_TABLE$Mood$Helpers$RenderUtils$RainbowDirection2;
    }
    
    public enum RainbowDirection
    {
        LEFT("LEFT", 0), 
        UP("UP", 1), 
        RIGHT("RIGHT", 2), 
        DOWN("DOWN", 3);
        
        private static final RainbowDirection[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new RainbowDirection[] { RainbowDirection.LEFT, RainbowDirection.UP, RainbowDirection.RIGHT, RainbowDirection.DOWN };
        }
        
        private RainbowDirection(final String s, final int n) {
        }
    }
}
