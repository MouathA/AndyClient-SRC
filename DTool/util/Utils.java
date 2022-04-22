package DTool.util;

import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class Utils
{
    public static Minecraft mc;
    private static final Potion[] blockedEffects;
    
    static {
        blockedEffects = new Potion[] { Potion.hunger, Potion.moveSlowdown, Potion.digSlowdown, Potion.harm, Potion.confusion, Potion.blindness, Potion.weakness, Potion.wither, Potion.poison };
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        return Minecraft.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static float[] getFacePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5;
        Minecraft.getMinecraft();
        final double n2 = n - Minecraft.thePlayer.posX;
        final double n3 = vec3.yCoord + 0.5;
        Minecraft.getMinecraft();
        final double posY = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        final double n4 = n3 - (posY + Minecraft.thePlayer.getEyeHeight());
        final double n5 = vec3.zCoord + 0.5;
        Minecraft.getMinecraft();
        final double n6 = n5 - Minecraft.thePlayer.posZ;
        final double n7 = MathHelper.sqrt_double(n2 * n2 + n6 * n6);
        final float n8 = (float)(Math.atan2(n6, n2) * 180.0 / 3.141592653589793) - 90.0f;
        final float n9 = (float)(-(Math.atan2(n4, n7) * 180.0 / 3.141592653589793));
        final float[] array = new float[2];
        final int n10 = 0;
        Minecraft.getMinecraft();
        final float rotationYaw = Minecraft.thePlayer.rotationYaw;
        final float n11 = n8;
        Minecraft.getMinecraft();
        array[n10] = rotationYaw + MathHelper.wrapAngleTo180_float(n11 - Minecraft.thePlayer.rotationYaw);
        final int n12 = 1;
        Minecraft.getMinecraft();
        final float rotationPitch = Minecraft.thePlayer.rotationPitch;
        final float n13 = n9;
        Minecraft.getMinecraft();
        array[n12] = rotationPitch + MathHelper.wrapAngleTo180_float(n13 - Minecraft.thePlayer.rotationPitch);
        return array;
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public static boolean hasBadEffect() {
        if (Minecraft.thePlayer.getActivePotionEffects().isEmpty()) {
            return false;
        }
        Potion[] blockedEffects;
        while (0 < (blockedEffects = Utils.blockedEffects).length) {
            if (Minecraft.thePlayer.isPotionActive(blockedEffects[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static MovingObjectPosition blockPlayerLookingAt(final double n) {
        final Vec3 positionEyes = Minecraft.thePlayer.getPositionEyes(1.0f);
        final Vec3 lookVec = Minecraft.thePlayer.getLookVec();
        return Minecraft.theWorld.rayTraceBlocks(positionEyes, positionEyes.addVector(lookVec.xCoord * n, lookVec.yCoord * n, lookVec.zCoord * n), !Minecraft.thePlayer.isInWater(), false, false);
    }
    
    public static void teleport(final double n, final double n2, final double n3) {
        Minecraft.getMinecraft();
        for (double distance = Minecraft.thePlayer.getDistance(n, n2, n3), n4 = 0.0; n4 < distance; n4 += 2.0) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX + (n - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetX() - Minecraft.thePlayer.posX) * n4 / distance, Minecraft.thePlayer.posY + (n2 - Minecraft.thePlayer.posY) * n4 / distance, Minecraft.thePlayer.posZ + (n3 - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetZ() - Minecraft.thePlayer.posZ) * n4 / distance, true));
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + (n - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetX() - Minecraft.thePlayer.posX) * n4 / distance, Minecraft.thePlayer.posY + (n2 - Minecraft.thePlayer.posY) * n4 / distance, Minecraft.thePlayer.posZ + (n - Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetZ() - Minecraft.thePlayer.posZ) * n4 / distance);
        }
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(n, n2, n3, true));
        Minecraft.thePlayer.setPosition(n, n2, n3);
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
    }
    
    public static void teleport() {
        Minecraft.getMinecraft();
        final MovingObjectPosition blockPlayerLooking = blockPlayerLookingAt(500.0);
        if (blockPlayerLooking == null) {
            return;
        }
        teleport(blockPlayerLooking.func_178782_a().getX() + 0.5, blockPlayerLooking.func_178782_a().getY() + 1, blockPlayerLooking.func_178782_a().getZ() + 0.5);
    }
    
    public static void teleportNormal(final double n, final double n2, final double n3) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(n, n2, n3, true));
        Minecraft.thePlayer.setPosition(n, n2, n3);
    }
    
    public static void drawColorBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        worldRenderer.addVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        instance.draw();
    }
    
    public static void blockESPBox(final BlockPos blockPos) {
        Minecraft.getMinecraft().getRenderManager();
        final double n = blockPos.getX() - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        final double n2 = blockPos.getY() - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        final double n3 = blockPos.getZ() - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glColor4d(0.0, 1.0, 0.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        drawColorBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glColor4d(44.0, 100.0, 98.0, 0.20000000298023224);
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), -1);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
}
