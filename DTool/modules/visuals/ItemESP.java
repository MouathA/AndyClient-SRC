package DTool.modules.visuals;

import DTool.modules.*;
import DTool.util.*;
import java.nio.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import Mood.*;
import net.minecraft.util.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import javax.vecmath.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;

public class ItemESP extends Module
{
    private static final Frustum frustrum;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    
    static {
        frustrum = new Frustum();
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public ItemESP() {
        super("ItemESP", 0, Category.Visuals);
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
        for (final Entity entity : Minecraft.theWorld.loadedEntityList) {
            if (entity instanceof EntityItem) {
                final double n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ItemESP.mc.timer.renderPartialTicks;
                final double n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ItemESP.mc.timer.renderPartialTicks;
                final double n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ItemESP.mc.timer.renderPartialTicks;
                final double n4 = entity.width / 1.75;
                final double n5 = entity.height + 0.5;
                final double n6 = n - 0.15;
                final Client instance = Client.INSTANCE;
                final double n7 = Client.getModuleByName("ItemPhysics").toggled ? (n2 + 0.35) : (n2 + 0.65);
                final double n8 = n3 - 0.225;
                final double n9 = n + 0.15;
                final Client instance2 = Client.INSTANCE;
                final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n9, Client.getModuleByName("ItemPhysics").toggled ? (n2 - 0.15) : (n2 + 0.15), n3 + 0.225);
                final List<Vector3d> list = Arrays.asList(new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
                ItemESP.mc.entityRenderer.setupCameraTransform(ItemESP.mc.timer.renderPartialTicks, 0);
                Vector4d vector4d = null;
                for (Vector3d vector3d : list) {
                    final FloatBuffer directFloatBuffer = GLAllocation.createDirectFloatBuffer(4);
                    GL11.glGetFloat(2982, ItemESP.modelview);
                    GL11.glGetFloat(2983, ItemESP.projection);
                    GL11.glGetInteger(2978, ItemESP.viewport);
                    if (GLU.gluProject((float)(vector3d.x - ItemESP.mc.getRenderManager().viewerPosX), (float)((float)vector3d.y - ItemESP.mc.getRenderManager().viewerPosY), (float)((float)vector3d.z - ItemESP.mc.getRenderManager().viewerPosZ), ItemESP.modelview, ItemESP.projection, ItemESP.viewport, directFloatBuffer)) {
                        vector3d = new Vector3d(directFloatBuffer.get(0) / new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor(), (Display.getHeight() - directFloatBuffer.get(1)) / new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor(), directFloatBuffer.get(2));
                    }
                    if (vector3d.z >= 0.0) {
                        if (vector3d.z >= 1.0) {
                            continue;
                        }
                        if (vector4d == null) {
                            vector4d = new Vector4d(vector3d.x, vector3d.y, vector3d.z, 0.0);
                        }
                        vector4d.x = Math.min(vector3d.x, vector4d.x);
                        vector4d.y = Math.min(vector3d.y, vector4d.y);
                        vector4d.z = Math.max(vector3d.x, vector4d.z);
                        vector4d.w = Math.max(vector3d.y, vector4d.w);
                    }
                }
                ItemESP.mc.entityRenderer.setupOverlayRendering();
                if (vector4d == null) {
                    continue;
                }
                final float n10 = (float)vector4d.x;
                final float n11 = (float)vector4d.z - n10;
                final float n12 = (float)vector4d.y;
                final float n13 = (float)vector4d.w - n12;
                this.drawBorderedRect(n10, n12, n11, n13, 1.0, new Color(0, 0, 0).getRGB(), 0);
                this.drawBorderedRect(n10, n12, n11, n13, 0.5, new Color(-1, true).getRGB(), 0);
            }
        }
    }
    
    public boolean isInViewFrustrum(final Entity entity) {
        return this.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    public boolean isInViewFrustrum(final AxisAlignedBB axisAlignedBB) {
        final Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        ItemESP.frustrum.setPosition(renderViewEntity.posX, renderViewEntity.posY, renderViewEntity.posZ);
        return ItemESP.frustrum.isBoundingBoxInFrustum(axisAlignedBB);
    }
    
    public void drawBorderedRect(final double n, final double n2, final double n3, final double n4, final double n5, final int n6, final int n7) {
        Gui.drawRect(n, n2, n + n3, n2 + n4, n7);
        Gui.drawRect(n, n2, n + n3, n2 + n5, n6);
        Gui.drawRect(n, n2, n + n5, n2 + n4, n6);
        Gui.drawRect(n + n3, n2, n + n3 - n5, n2 + n4, n6);
        Gui.drawRect(n, n2 + n4, n + n3, n2 + n4 - n5, n6);
    }
    
    public void drawBar(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final int n7, final int n8) {
        final float n9 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n7 & 0xFF) / 255.0f;
        final float n13 = n4 / n5;
        GL11.glColor4f(n10, n11, n12, n9);
        float n14 = n2 + n4 - n13;
        while (0 < n6) {
            this.drawBorderedRect(n + 0.25f, n14, n3 - 0.5f, n13, 0.25, new Color(n8, true).getRGB(), n7);
            n14 -= n13;
            int n15 = 0;
            ++n15;
        }
    }
    
    private int getHealthColor(final EntityLivingBase entityLivingBase) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(entityLivingBase.getHealth(), entityLivingBase.getMaxHealth()) / entityLivingBase.getMaxHealth()) / 3.0f, 0.56f, 1.0f) | 0xFF000000;
    }
}
