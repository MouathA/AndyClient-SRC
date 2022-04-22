package DTool.modules.visuals;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import java.awt.*;

public class Tracers extends Module
{
    public Tracers() {
        super("Tracers", 0, Category.Visuals);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onRender() {
        if (!this.isEnable()) {
            return;
        }
        this.drawTracers();
        super.onRender();
    }
    
    public void drawTracers() {
        Label_0584: {
            break Label_0584;
            int i;
            do {
                final Minecraft mc = Tracers.mc;
                final EntityPlayer entityPlayer2;
                final EntityPlayer entityPlayer = entityPlayer2 = Minecraft.theWorld.playerEntities.get(0);
                final Minecraft mc2 = Tracers.mc;
                if (entityPlayer2 != Minecraft.thePlayer) {
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth(1.4f);
                    GL11.glDisable(3553);
                    GL11.glDisable(2929);
                    Tracers.mc.getRenderManager();
                    final double n = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * Tracers.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                    Tracers.mc.getRenderManager();
                    final double n2 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * Tracers.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                    Tracers.mc.getRenderManager();
                    final double n3 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * Tracers.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                    GL11.glBegin(1);
                    float n4 = 0.0f;
                    float n5 = 0.0f;
                    float n6 = 0.0f;
                    final Minecraft mc3 = Tracers.mc;
                    if (Minecraft.thePlayer.getDistanceToEntity(entityPlayer) > 25.0f) {
                        n4 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getRed();
                        n5 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getGreen();
                        n6 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().brighter().getBlue();
                    }
                    final Minecraft mc4 = Tracers.mc;
                    if (Minecraft.thePlayer.getDistanceToEntity(entityPlayer) <= 25.0f) {
                        n4 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().getRed();
                        n5 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().getGreen();
                        n6 = (float)Color.YELLOW.brighter().brighter().brighter().brighter().getBlue();
                    }
                    final Minecraft mc5 = Tracers.mc;
                    if (Minecraft.thePlayer.getDistanceToEntity(entityPlayer) <= 10.0f) {
                        n4 = (float)Color.ORANGE.brighter().brighter().brighter().brighter().getRed();
                        n5 = (float)Color.ORANGE.brighter().brighter().brighter().brighter().getGreen();
                        n6 = (float)Color.ORANGE.brighter().brighter().brighter().brighter().getBlue();
                    }
                    GL11.glColor4f(n4, n5, n6, 1.0f);
                    final double n7 = 0.0;
                    final Minecraft mc6 = Tracers.mc;
                    GL11.glVertex2d(n7, Minecraft.thePlayer.isSneaking() ? 1.54f : 1.62f);
                    GL11.glVertex3d(n, n2, n3);
                    GL11.glBegin(1);
                    GL11.glVertex3d(n, n2, n3);
                    GL11.glVertex3d(n, n2 + 1.0, n3);
                    GL11.glEnable(2929);
                    GL11.glEnable(3553);
                    GL11.glColor3f(1.0f, 1.0f, 1.0f);
                }
                int n8 = 0;
                ++n8;
                i = 0;
                final Minecraft mc7 = Tracers.mc;
            } while (i < Minecraft.theWorld.playerEntities.size());
        }
    }
    
    public static void drawTracer(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9, final double n10) {
        GL11.glEnable(2848);
        GL11.glColor4d(n7, n8, n9, n10);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(2);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
    }
}
