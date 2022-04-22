package DTool.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class MovementUtils
{
    public static Minecraft mc;
    
    static {
        MovementUtils.mc = Minecraft.getMinecraft();
    }
    
    public static double getBlocksPerSecond() {
        if (Minecraft.thePlayer == null || Minecraft.thePlayer.ticksExisted < 1) {
            return 0.0;
        }
        final double distance = Minecraft.thePlayer.getDistance(Minecraft.thePlayer.lastTickPosX, Minecraft.thePlayer.lastTickPosY, Minecraft.thePlayer.lastTickPosZ);
        final float ticksPerSecond = Minecraft.getMinecraft().timer.ticksPerSecond;
        final Timer timer = Minecraft.getMinecraft().timer;
        return distance * (ticksPerSecond * Timer.timerSpeed);
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionX + Minecraft.thePlayer.motionZ * Minecraft.thePlayer.motionZ);
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isMoving() {
        return Minecraft.thePlayer != null && (Minecraft.thePlayer.movementInput.moveForward != 0.0f || Minecraft.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static boolean hasMotion() {
        return Minecraft.thePlayer.motionX != 0.0 && Minecraft.thePlayer.motionZ != 0.0 && Minecraft.thePlayer.motionY != 0.0;
    }
    
    public static void strafe(final float p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: invokestatic    DTool/util/MovementUtils.getDirection:()D
        //     7: dstore_1       
        //     8: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    11: dload_1        
        //    12: invokestatic    java/lang/Math.sin:(D)D
        //    15: dneg           
        //    16: fload_0        
        //    17: f2d            
        //    18: dmul           
        //    19: putfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    22: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    25: dload_1        
        //    26: invokestatic    java/lang/Math.cos:(D)D
        //    29: fload_0        
        //    30: f2d            
        //    31: dmul           
        //    32: putfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //    35: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void forward(final double n) {
        final double radians = Math.toRadians(Minecraft.thePlayer.rotationYaw);
        Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + -Math.sin(radians) * n, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Math.cos(radians) * n);
    }
    
    public static double getDirection() {
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (Minecraft.thePlayer.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static void setMotion(final double n) {
        double n2 = Minecraft.thePlayer.movementInput.moveForward;
        double n3 = Minecraft.thePlayer.movementInput.moveStrafe;
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        if (n2 == 0.0 && n3 == 0.0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            Minecraft.thePlayer.motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            Minecraft.thePlayer.motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    public static void setMotion(final double n, final float n2) {
        double n3 = Minecraft.thePlayer.movementInput.moveForward;
        double n4 = Minecraft.thePlayer.movementInput.moveStrafe;
        float n5 = n2;
        if (n3 == 0.0 && n4 == 0.0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        }
        else {
            if (n3 != 0.0) {
                if (n4 > 0.0) {
                    n5 += ((n3 > 0.0) ? -45 : 45);
                }
                else if (n4 < 0.0) {
                    n5 += ((n3 > 0.0) ? 45 : -45);
                }
                n4 = 0.0;
                if (n3 > 0.0) {
                    n3 = 1.0;
                }
                else if (n3 < 0.0) {
                    n3 = -1.0;
                }
            }
            Minecraft.thePlayer.motionX = n3 * n * Math.cos(Math.toRadians(n5 + 90.0f)) + n4 * n * Math.sin(Math.toRadians(n5 + 90.0f));
            Minecraft.thePlayer.motionZ = n3 * n * Math.sin(Math.toRadians(n5 + 90.0f)) - n4 * n * Math.cos(Math.toRadians(n5 + 90.0f));
        }
    }
    
    public static boolean isOnGround(final double n) {
        return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public static boolean isOnGround(final double n, final EntityPlayer entityPlayer) {
        return !Minecraft.theWorld.getCollidingBoundingBoxes(entityPlayer, entityPlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
}
