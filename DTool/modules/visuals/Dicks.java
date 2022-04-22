package DTool.modules.visuals;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

public class Dicks extends Module
{
    private int amount;
    private float spin;
    private float cumSize;
    
    public Dicks() {
        super("DildoESP", 0, Category.Visuals);
        this.amount = 0;
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
        final Minecraft mc = Dicks.mc;
        for (final EntityPlayer next : Minecraft.theWorld.loadedEntityList) {
            if (next instanceof EntityPlayer) {
                final EntityPlayer entityPlayer2;
                final EntityPlayer entityPlayer = entityPlayer2 = next;
                final Minecraft mc2 = Dicks.mc;
                if (entityPlayer2 != Minecraft.thePlayer) {
                    final double n = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * Dicks.mc.timer.renderPartialTicks;
                    Dicks.mc.getRenderManager();
                    final double n2 = n - RenderManager.renderPosX;
                    final double n3 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * Dicks.mc.timer.renderPartialTicks;
                    Dicks.mc.getRenderManager();
                    final double n4 = n3 - RenderManager.renderPosY;
                    final double n5 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * Dicks.mc.timer.renderPartialTicks;
                    Dicks.mc.getRenderManager();
                    this.esp(entityPlayer, n2, n4, n5 - RenderManager.renderPosZ);
                }
            }
            ++this.amount;
            if (this.amount > 25) {
                ++this.spin;
                if (this.spin > 50.0f) {
                    this.spin = -50.0f;
                }
                else if (this.spin < -50.0f) {
                    this.spin = 50.0f;
                }
                this.amount = 0;
            }
            ++this.cumSize;
            if (this.cumSize > 180.0f) {
                this.cumSize = -180.0f;
            }
            else {
                if (this.cumSize >= -180.0f) {
                    continue;
                }
                this.cumSize = 180.0f;
            }
        }
        super.onRender();
    }
    
    public void esp(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(n, n2, n3);
        GL11.glRotatef(-entityPlayer.rotationYaw, 0.0f, entityPlayer.height, 0.0f);
        GL11.glTranslated(-n, -n2, -n3);
        GL11.glTranslated(n, n2 + entityPlayer.height / 2.0f - 0.22499999403953552, n3);
        GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        final Cylinder cylinder = new Cylinder();
        cylinder.setDrawStyle(100013);
        cylinder.draw(0.1f, 0.11f, 1.4f, 110, 110);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        final Sphere sphere = new Sphere();
        sphere.setDrawStyle(100013);
        sphere.draw(0.14f, 10, 110);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        final Sphere sphere2 = new Sphere();
        sphere2.setDrawStyle(100013);
        sphere2.draw(0.14f, 10, 110);
        GL11.glTranslated(-0.07000000074505806, 0.0, 1.589999952316284);
        final Sphere sphere3 = new Sphere();
        sphere3.setDrawStyle(100013);
        sphere3.draw(0.13f, 15, 110);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}
