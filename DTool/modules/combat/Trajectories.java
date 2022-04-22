package DTool.modules.combat;

import DTool.modules.*;
import DTool.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import Mood.Helpers.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;

public class Trajectories extends Module
{
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private boolean hitEntity;
    private double r;
    private double g;
    private double b;
    public double pX;
    public double pY;
    public double pZ;
    
    public Trajectories() {
        super("Trajectories", 0, Category.Combat);
        this.hitEntity = false;
        this.pX = -9000.0;
        this.pY = -9000.0;
        this.pZ = -9000.0;
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
        final EntityPlayerSP player = Wrapper.getPlayer();
        if (player.inventory.getCurrentItem() != null && this.isEnable() && this.isThrowable(player.inventory.getCurrentItem().getItem())) {
            this.x = player.lastTickPosX + (player.posX - player.lastTickPosX) * Wrapper.getMinecraft().timer.renderPartialTicks - MathHelper.cos((float)Math.toRadians(player.rotationYaw)) * 0.16f;
            this.y = player.lastTickPosY + (player.posY - player.lastTickPosY) * Wrapper.getMinecraft().timer.renderPartialTicks + player.getEyeHeight() - 0.100149011612;
            this.z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * Wrapper.getMinecraft().timer.renderPartialTicks - MathHelper.sin((float)Math.toRadians(player.rotationYaw)) * 0.16f;
            float n = 1.0f;
            if (!(player.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
                n = 0.4f;
            }
            this.motionX = -MathHelper.sin((float)Math.toRadians(player.rotationYaw)) * MathHelper.cos((float)Math.toRadians(player.rotationPitch)) * n;
            this.motionZ = MathHelper.cos((float)Math.toRadians(player.rotationYaw)) * MathHelper.cos((float)Math.toRadians(player.rotationPitch)) * n;
            this.motionY = -MathHelper.sin((float)Math.toRadians(player.rotationPitch)) * n;
            final double sqrt = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.motionX /= sqrt;
            this.motionY /= sqrt;
            this.motionZ /= sqrt;
            if (player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
                final float n2 = (72000 - player.getItemInUseCount()) / 20.0f;
                float n3 = (n2 * n2 + n2 * 2.0f) / 3.0f;
                if (n3 > 1.0f) {
                    n3 = 1.0f;
                }
                if (n3 <= 0.1f) {
                    n3 = 1.0f;
                }
                final float n4 = n3 * 2.0f * 1.5f;
                this.motionX *= n4;
                this.motionY *= n4;
                this.motionZ *= n4;
            }
            else {
                this.motionX *= 1.5;
                this.motionY *= 1.5;
                this.motionZ *= 1.5;
            }
            final Vec3 vec3 = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            this.enableDefaults();
            GL11.glLineWidth(1.8f);
            GL11.glColor3d(this.r, this.g, this.b);
            GL11.glBegin(3);
            final double gravity = this.getGravity(player.inventory.getCurrentItem().getItem());
            while (0 < 1000) {
                GL11.glVertex3d(this.x * 1.0 - RenderManager.renderPosX, this.y * 1.0 - RenderManager.renderPosY, this.z * 1.0 - RenderManager.renderPosZ);
                this.x += this.motionX;
                this.y += this.motionY;
                this.z += this.motionZ;
                this.motionX *= 0.99;
                this.motionY *= 0.99;
                this.motionZ *= 0.99;
                this.motionY -= gravity;
                if (Wrapper.getWorld().rayTraceBlocks(vec3, new Vec3(this.x, this.y, this.z)) != null) {
                    break;
                }
                int n5 = 0;
                ++n5;
            }
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.x - 0.5 - RenderManager.renderPosX, this.y - 0.5 - RenderManager.renderPosY, this.z - 0.5 - RenderManager.renderPosZ, this.x - 0.5 - RenderManager.renderPosX + 1.0, this.y - 0.5 - RenderManager.renderPosY + 1.0, this.z - 0.5 - RenderManager.renderPosZ + 1.0);
            GL11.glTranslated(this.x - RenderManager.renderPosX, this.y - RenderManager.renderPosY, this.z - RenderManager.renderPosZ);
            GL11.glRotatef(Wrapper.getPlayer().rotationYaw, 0.0f, (float)(this.y - RenderManager.renderPosY), 0.0f);
            GL11.glTranslated(-(this.x - RenderManager.renderPosX), -(this.y - RenderManager.renderPosY), -(this.z - RenderManager.renderPosZ));
            RenderUtils.drawESP(this.x - 0.35 - RenderManager.renderPosX, this.y - 0.5 - RenderManager.renderPosY, this.z - 0.5 - RenderManager.renderPosZ, this.r, this.b, this.g);
            this.disableDefaults();
        }
    }
    
    public void enableDefaults() {
        GL11.glDisable(2896);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(32925);
        GL11.glDepthMask(false);
    }
    
    public void disableDefaults() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2896);
    }
    
    private double getGravity(final Item item) {
        return (item instanceof ItemBow) ? 0.05 : 0.03;
    }
    
    private boolean isThrowable(final Item item) {
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl;
    }
    
    public void drawLine3D(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
    }
}
