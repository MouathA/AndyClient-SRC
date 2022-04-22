package DTool.modules.visuals;

import DTool.modules.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class SimsESP extends Module
{
    public SimsESP() {
        super("SimsESP", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    public void color(final double n, final double n2, final double n3, final double n4) {
        GL11.glColor4d(n, n2, n3, n4);
    }
    
    public void color(final double n, final double n2, final double n3) {
        this.color(n, n2, n3, 1.0);
    }
    
    public void color(Color white) {
        if (white == null) {
            white = Color.white;
        }
        this.color(white.getRed() / 255.0f, white.getGreen() / 255.0f, white.getBlue() / 255.0f, white.getAlpha() / 255.0f);
    }
    
    public void color(Color white, final int n) {
        if (white == null) {
            white = Color.white;
        }
        this.color(white.getRed() / 255.0f, white.getGreen() / 255.0f, white.getBlue() / 255.0f, 0.5);
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glFrontFace(2304);
        final Minecraft mc = SimsESP.mc;
        for (final EntityPlayer entityPlayer : Minecraft.theWorld.playerEntities) {
            if (entityPlayer.isInvisible()) {
                continue;
            }
            final EntityPlayer entityPlayer2 = entityPlayer;
            final Minecraft mc2 = SimsESP.mc;
            if (entityPlayer2 == Minecraft.thePlayer) {
                continue;
            }
            int n = 0;
            ++n;
            Color color = Color.GREEN;
            if (entityPlayer.hurtTime > 0) {
                color = Color.RED;
            }
            GL11.glBegin(5);
            this.color(color);
            final double n2 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * SimsESP.mc.timer.renderPartialTicks - SimsESP.mc.getRenderManager().viewerPosX;
            final double n3 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * SimsESP.mc.timer.renderPartialTicks - SimsESP.mc.getRenderManager().viewerPosY + entityPlayer.getEyeHeight() + 0.4 + Math.sin(System.currentTimeMillis() % 1000000L / 333.0f + 0) / 10.0;
            final double n4 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * SimsESP.mc.timer.renderPartialTicks - SimsESP.mc.getRenderManager().viewerPosZ;
            this.color(color.darker().darker());
            GL11.glVertex3d(n2, n3, n4);
            GL11.glVertex3d(n2 - 0.1, n3 + 0.3, n4 - 0.1);
            GL11.glVertex3d(n2 - 0.1, n3 + 0.3, n4 + 0.1);
            this.color(color);
            GL11.glVertex3d(n2 + 0.1, n3 + 0.3, n4);
            this.color(color.darker().darker());
            GL11.glVertex3d(n2, n3, n4);
            this.color(color.darker().darker().darker());
            GL11.glVertex3d(n2 + 0.1, n3 + 0.3, n4);
            GL11.glVertex3d(n2 - 0.1, n3 + 0.3, n4 - 0.1);
        }
        GL11.glShadeModel(7424);
        GL11.glFrontFace(2305);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3553);
        GL11.glColor3f(255.0f, 255.0f, 255.0f);
    }
}
