package Mood.Helpers;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class RenderManager
{
    public static void drawTracerLine(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        GL11.glBegin(2);
        final double n9 = 0.0;
        final double n10 = 0.0;
        Minecraft.getMinecraft();
        GL11.glVertex3d(n9, n10 + Minecraft.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(n, n2, n3);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
    }
}
