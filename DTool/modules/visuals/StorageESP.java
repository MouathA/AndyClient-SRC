package DTool.modules.visuals;

import DTool.modules.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;

public class StorageESP extends Module
{
    public StorageESP() {
        super("StorageESP", 0, Category.Visuals);
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
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glEnable(2848);
        final Minecraft mc = StorageESP.mc;
        for (final TileEntity next : Minecraft.theWorld.loadedTileEntityList) {
            if (next == null) {
                continue;
            }
            if (!(next instanceof TileEntityChest) && !(next instanceof TileEntityEnderChest) && !(next instanceof TileEntityHopper) && !(next instanceof TileEntityDispenser)) {
                continue;
            }
            final TileEntity tileEntity = next;
            final double n = tileEntity.pos.x - RenderManager.renderPosX;
            final double n2 = tileEntity.pos.y - RenderManager.renderPosY;
            final double n3 = tileEntity.pos.z - RenderManager.renderPosZ;
            GL11.glTranslated(n, n2, n3);
            this.chestESP(0.0, 0.0, 0.0);
            GL11.glTranslated(-n, -n2, -n3);
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glDisable(2848);
    }
    
    public void chestESP(final double n, final double n2, final double n3) {
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(44.0f, 100.0f, 98.0f, 0.2f);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(7);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glColor4f(0.0f, 0.0f, 100.0f, 0.2f);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n + 1.0, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3 + 1.0);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n, n2 + 1.0, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2, n3 + 1.0);
        GL11.glTranslatef((float)n + 0.5f, (float)n2 + 0.5f, (float)n3 + 0.5f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glScalef(-0.026688f, -0.026688f, 0.026688f);
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
    }
    
    public static void load() {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
    }
    
    public static void reset() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
