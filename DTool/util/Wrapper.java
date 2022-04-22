package DTool.util;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.*;
import net.minecraft.client.settings.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import Mood.*;
import java.text.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;

public class Wrapper
{
    public static Minecraft mc;
    public static FontRenderer fr;
    private static boolean canTP;
    private static int delay;
    private static BlockPos endPos;
    
    static {
        Wrapper.mc = Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Wrapper.fr = Minecraft.fontRendererObj;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP getPlayer() {
        getMinecraft();
        return Minecraft.thePlayer;
    }
    
    public static boolean isMoving(final Entity entity) {
        return entity.motionX != 0.0 && entity.motionZ != 0.0 && (entity.motionY != 0.0 || entity.motionY > 0.0);
    }
    
    public static BlockPos getBlockPos(final BlockPos blockPos) {
        return blockPos;
    }
    
    public static Block getBlockAtPos(final BlockPos blockPos) {
        return Minecraft.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer entityPlayer) {
        return getBlockAtPos(new BlockPos(entityPlayer.posX, entityPlayer.posY + (Minecraft.thePlayer.motionY + 0.1) - 1.0, entityPlayer.posZ));
    }
    
    public static BlockPos getBlockPosUnderPlayer(final EntityPlayer entityPlayer) {
        return new BlockPos(entityPlayer.posX, entityPlayer.posY + (Minecraft.thePlayer.motionY + 0.1) - 1.0, entityPlayer.posZ);
    }
    
    public static Block getBlockAbovePlayer(final EntityPlayer entityPlayer, double n) {
        n += entityPlayer.height;
        return getBlockAtPos(new BlockPos(entityPlayer.posX, entityPlayer.posY + n, entityPlayer.posZ));
    }
    
    public static Block getBlockAtPosC(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        return getBlockAtPos(new BlockPos(entityPlayer.posX - n, entityPlayer.posY - n2, entityPlayer.posZ - n3));
    }
    
    public static BlockPos getBlockPos(final double n, final double n2, final double n3) {
        return getBlockPos(new BlockPos(n, n2, n3));
    }
    
    public static Block getBlockUnderPlayer2(final EntityPlayer entityPlayer, final double n) {
        return getBlockAtPos(new BlockPos(entityPlayer.posX, entityPlayer.posY - n, entityPlayer.posZ));
    }
    
    public static MovingObjectPosition getMouseOver() {
        return getMinecraft().objectMouseOver;
    }
    
    public static boolean isOnLiquid(AxisAlignedBB offset) {
        offset = offset.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        final int n = (int)offset.minY;
        for (int i = MathHelper.floor_double(offset.minX); i < MathHelper.floor_double(offset.maxX + 1.0); ++i) {
            for (int j = MathHelper.floor_double(offset.minZ); j < MathHelper.floor_double(offset.maxZ + 1.0); ++j) {
                final Block block = getBlock(new BlockPos(i, n, j));
                if (block != Blocks.air && !(block instanceof BlockLiquid)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isInLiquid(AxisAlignedBB contract) {
        contract = contract.contract(0.001, 0.001, 0.001);
        final int floor_double = MathHelper.floor_double(contract.minX);
        final int floor_double2 = MathHelper.floor_double(contract.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(contract.minY);
        final int floor_double4 = MathHelper.floor_double(contract.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(contract.minZ);
        final int floor_double6 = MathHelper.floor_double(contract.maxZ + 1.0);
        if (getWorld().getChunkFromBlockCoords(new BlockPos(getPlayer().posX, getPlayer().posY, getPlayer().posZ)) == null) {
            return false;
        }
        final Vec3 vec3 = new Vec3(0.0, 0.0, 0.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    if (getBlock(new BlockPos(i, j, k)) instanceof BlockLiquid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        Minecraft.getMinecraft();
        return Minecraft.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static String getDirection() {
        return getMinecraft().func_175606_aa().func_174811_aO().name();
    }
    
    public Float[] getRotationToPosition(final Entity entity, final BlockPos blockPos) {
        final double n = blockPos.getX() - entity.posX;
        final double n2 = blockPos.getY() - (entity.posY + entity.getEyeHeight());
        final double n3 = blockPos.getZ() - entity.posZ;
        final double n4 = MathHelper.sqrt_double(n * n + n3 * n3);
        float n5 = (float)Math.toDegrees(-Math.atan(n / n3));
        final float n6 = (float)(-Math.toDegrees(Math.atan(n2 / n4)));
        if (n3 < 0.0 && n < 0.0) {
            n5 = (float)(90.0 + Math.toDegrees(Math.atan(n3 / n)));
        }
        else if (n3 < 0.0 && n > 0.0) {
            n5 = (float)(-90.0 + Math.toDegrees(Math.atan(n3 / n)));
        }
        return new Float[] { n5, n6 };
    }
    
    public static boolean isInBlock(final Entity entity, final float n) {
        for (int i = MathHelper.floor_double(entity.getEntityBoundingBox().minX); i < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(entity.getEntityBoundingBox().minY); j < MathHelper.floor_double(entity.getEntityBoundingBox().maxY) + 1; ++j) {
                for (int k = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); k < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; ++k) {
                    final Block block = getWorld().getBlockState(new BlockPos(i, j + n, k)).getBlock();
                    if (block != null && !(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
                        final AxisAlignedBB collisionBoundingBox = block.getCollisionBoundingBox(getWorld(), new BlockPos(i, j + n, k), getWorld().getBlockState(new BlockPos(i, j + n, k)));
                        if (collisionBoundingBox != null && entity.getEntityBoundingBox().intersectsWith(collisionBoundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static float[] getBlockRotations(final double n, final double n2, final double n3) {
        final double n4 = n - Minecraft.thePlayer.posX + 0.5;
        final double n5 = n3 - Minecraft.thePlayer.posZ + 0.5;
        return new float[] { (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(n2 - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight() - 1.0), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793)) };
    }
    
    public static float getDistanceToGround(final Entity entity) {
        if (getPlayer().isCollidedVertically) {
            return 0.0f;
        }
        float n = (float)entity.posY;
        while (n > 0.0f) {
            final int[] array = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
            final int[] array2 = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
            final Block block = getBlock(new BlockPos(entity.posX, n - 1.0f, entity.posZ));
            if (!(block instanceof BlockAir)) {
                if (Block.getIdFromBlock(block) == 44 || Block.getIdFromBlock(block) == 126) {
                    return ((float)(entity.posY - n - 0.5) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 0.5));
                }
                int[] array3;
                int n2 = 0;
                while (0 < (array3 = array).length) {
                    if (Block.getIdFromBlock(block) == array3[0]) {
                        return ((float)(entity.posY - n - 1.0) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 1.0));
                    }
                    ++n2;
                }
                int[] array4;
                while (0 < (array4 = array2).length) {
                    if (Block.getIdFromBlock(block) == array4[0]) {
                        return ((float)(entity.posY - n) < 0.0f) ? 0.0f : ((float)(entity.posY - n));
                    }
                    ++n2;
                }
                return (float)(entity.posY - n + block.getBlockBoundsMaxY() - 1.0);
            }
            else {
                --n;
            }
        }
        return 0.0f;
    }
    
    public static Block getBlockatPosSpeed(final EntityPlayer entityPlayer, final float n, final float n2) {
        return getBlockAtPos(new BlockPos(entityPlayer.posX + entityPlayer.motionX * n, entityPlayer.posY, entityPlayer.posZ + entityPlayer.motionZ * n2));
    }
    
    public static boolean isMoving() {
        return isMoving(Minecraft.thePlayer);
    }
    
    public static boolean isOnLiquid() {
        if (!getBlockAtPosC(Minecraft.thePlayer, 0.30000001192092896, 0.10000000149011612, 0.30000001192092896).getMaterial().isLiquid() || getBlockAtPosC(Minecraft.thePlayer, -0.30000001192092896, 0.10000000149011612, -0.30000001192092896).getMaterial().isLiquid()) {}
        return true;
    }
    
    public static boolean isInLiquid() {
        if (getBlockAtPosC(Minecraft.thePlayer, 0.30000001192092896, 0.0, 0.30000001192092896).getMaterial().isLiquid() || getBlockAtPosC(Minecraft.thePlayer, -0.30000001192092896, 0.0, -0.30000001192092896).getMaterial().isLiquid()) {}
        return true;
    }
    
    public static boolean isInFire() {
        if (getBlockAtPosC(Minecraft.thePlayer, 0.30000001192092896, 0.0, 0.30000001192092896).getMaterial() == Material.fire || getBlockAtPosC(Minecraft.thePlayer, -0.30000001192092896, 0.0, -0.30000001192092896).getMaterial() == Material.fire) {}
        return true;
    }
    
    public static String liquidCollision() {
        String s = "";
        if (getBlockAtPosC(Minecraft.thePlayer, 0.3100000023841858, 0.0, 0.3100000023841858).getMaterial().isLiquid()) {
            s = "Positive";
        }
        if (getBlockAtPosC(Minecraft.thePlayer, -0.3100000023841858, 0.0, -0.3100000023841858).getMaterial().isLiquid()) {
            s = "Negative";
        }
        return s;
    }
    
    public static boolean stairCollision() {
        if (getBlockAtPosC(Minecraft.thePlayer, 0.3100000023841858, 0.0, 0.3100000023841858) instanceof BlockStairs || getBlockAtPosC(Minecraft.thePlayer, -0.3100000023841858, 0.0, -0.3100000023841858) instanceof BlockStairs || getBlockAtPosC(Minecraft.thePlayer, 0.3100000023841858, 0.0, -0.3100000023841858) instanceof BlockStairs || getBlockAtPosC(Minecraft.thePlayer, -0.3100000023841858, 0.0, 0.3100000023841858) instanceof BlockStairs || getBlockatPosSpeed(Minecraft.thePlayer, 1.05f, 1.05f) instanceof BlockStairs) {}
        return true;
    }
    
    public static boolean isInsideBlock() {
        for (int i = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX); i < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minY); j < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxY) + 1; ++j) {
                for (int k = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ); k < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1; ++k) {
                    final Block block = Minecraft.theWorld.getBlockState(new BlockPos(i, j, k)).getBlock();
                    final AxisAlignedBB collisionBoundingBox;
                    if (block != null && !(block instanceof BlockAir) && (collisionBoundingBox = block.getCollisionBoundingBox(Minecraft.theWorld, new BlockPos(i, j, k), Minecraft.theWorld.getBlockState(new BlockPos(i, j, k)))) != null && Minecraft.thePlayer.boundingBox.intersectsWith(collisionBoundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static WorldClient getWorld() {
        getMinecraft();
        return Minecraft.theWorld;
    }
    
    public static void sendPacket(final Packet packet) {
        Wrapper.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }
    
    public static void sendPacketDirect(final Packet packet) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    public static ItemStack getHeldItem(final EntityPlayer entityPlayer) {
        return entityPlayer.getHeldItem();
    }
    
    public static void blinkToPos(final double[] array, final BlockPos blockPos, final double n) {
        double n2 = array[0];
        double n3 = array[1];
        double n4 = array[2];
        final double n5 = blockPos.getX() + 0.5;
        final double n6 = blockPos.getY() + 1.0;
        final double n7 = blockPos.getZ() + 0.5;
        double n8 = Math.abs(n2 - n5) + Math.abs(n3 - n6) + Math.abs(n4 - n7);
        while (n8 > n) {
            n8 = Math.abs(n2 - n5) + Math.abs(n3 - n6) + Math.abs(n4 - n7);
            final double n9 = n2 - n5;
            final double n10 = n3 - n6;
            final double n11 = n4 - n7;
            final double n12 = 0.4;
            if (n9 < 0.0) {
                if (Math.abs(n9) > n12) {
                    n2 += n12;
                }
                else {
                    n2 += Math.abs(n9);
                }
            }
            if (n9 > 0.0) {
                if (Math.abs(n9) > n12) {
                    n2 -= n12;
                }
                else {
                    n2 -= Math.abs(n9);
                }
            }
            if (n10 < 0.0) {
                if (Math.abs(n10) > 0.25) {
                    n3 += 0.25;
                }
                else {
                    n3 += Math.abs(n10);
                }
            }
            if (n10 > 0.0) {
                if (Math.abs(n10) > 0.25) {
                    n3 -= 0.25;
                }
                else {
                    n3 -= Math.abs(n10);
                }
            }
            if (n11 < 0.0) {
                if (Math.abs(n11) > n12) {
                    n4 += n12;
                }
                else {
                    n4 += Math.abs(n11);
                }
            }
            if (n11 > 0.0) {
                if (Math.abs(n11) > n12) {
                    n4 -= n12;
                }
                else {
                    n4 -= Math.abs(n11);
                }
            }
            Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(n2, n3, n4, true));
            int n13 = 0;
            ++n13;
        }
    }
    
    public static void vclip(final double n) {
        final boolean b = n < 0.0;
        final double n2 = Minecraft.thePlayer.posY + (n + 0.002);
        Minecraft.thePlayer.setLocationAndAngles(Minecraft.thePlayer.posX, n2, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch);
        Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, n2 - (b ? 0.1 : 0.0), Minecraft.thePlayer.posZ, true));
        Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - (b ? 1 : 0), Minecraft.thePlayer.posZ, false));
    }
    
    public static void damagePlayer(final double n) {
        while (0 < 80.0 + 40.0 * (n - 0.5)) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.049, Minecraft.thePlayer.posZ, false));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
            int n2 = 0;
            ++n2;
        }
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
    }
    
    public static void bypassTP(final int n, final int n2, final int n3) {
        Wrapper.endPos = new BlockPos(n, n2, n3);
        blinkToPos(new double[] { Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ }, Wrapper.endPos, 0.0);
        Minecraft.thePlayer.setPosition(Wrapper.endPos.getX() + 0.5, Wrapper.endPos.getY() + 1, Wrapper.endPos.getZ() + 0.5);
        sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.endPos.getX() + 0.5, Wrapper.endPos.getY() - 1.0, Wrapper.endPos.getZ() + 0.5, false));
    }
    
    public static void bypassTP2(int n, final int n2, int n3) {
        if (n < 0) {
            --n;
        }
        if (n3 < 0) {
            --n3;
        }
        Wrapper.endPos = new BlockPos(n, n2, n3);
        blinkToPos(new double[] { Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ }, Wrapper.endPos, 0.0);
        Minecraft.thePlayer.setPosition(Wrapper.endPos.getX() + 0.5, Wrapper.endPos.getY() + 1, Wrapper.endPos.getZ() + 0.5);
        sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.endPos.getX() + 0.5, Wrapper.endPos.getY() - 1.0, Wrapper.endPos.getZ() + 0.5, false));
    }
    
    public static void phaseTP(final double n, final double n2, final double n3) {
        Segito.msg("a");
        Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(n, n2 + 0.002, n3, true));
        Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(n, n2, n3, false));
    }
    
    public static int removeDecimals(final double n) {
        return Integer.parseInt(new DecimalFormat("####################################").format(n));
    }
    
    public static FontRenderer fr() {
        getMinecraft();
        return Minecraft.fontRendererObj;
    }
    
    public static void drawBorderRect(final float n, final float n2, final float n3, final float n4, final int n5, final int n6, final float n7) {
        Gui.drawRect(n + n7, n2 + n7, n3 - n7, n4 - n7, n6);
        Gui.drawRect(n, n2, n + n7, n4, n5);
        Gui.drawRect(n + n7, n2, n3, n2 + n7, n5);
        Gui.drawRect(n + n7, n4 - n7, n3, n4, n5);
        Gui.drawRect(n3 - n7, n2 + n7, n3, n4 - n7, n5);
    }
    
    public static GuiScreen getCurrentScreen() {
        return getMinecraft().currentScreen;
    }
    
    public static InventoryPlayer getInventory() {
        return getPlayer().inventory;
    }
    
    public static FontRenderer getFontRenderer() {
        getMinecraft();
        return Minecraft.fontRendererObj;
    }
}
