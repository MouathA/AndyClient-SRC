package DTool.events.listeners;

import DTool.events.*;
import net.minecraft.client.*;

public class EventMove extends Event
{
    public double x;
    public double y;
    public double z;
    
    public EventMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    @Override
    public void setX(final double x) {
        this.x = x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    @Override
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setSpeed(final double n) {
        Minecraft.getMinecraft();
        float moveForward = Minecraft.thePlayer.movementInput.moveForward;
        Minecraft.getMinecraft();
        float moveStrafe = Minecraft.thePlayer.movementInput.moveStrafe;
        Minecraft.getMinecraft();
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionZ = 0.0;
        }
        else {
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0f) {
                    rotationYaw += ((moveForward > 0.0) ? -45 : 45);
                }
                else if (moveStrafe < 0.0f) {
                    rotationYaw += ((moveForward > 0.0) ? 45 : -45);
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }
            final double n2 = moveForward * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            final double n3 = moveForward * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionX = n2;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionZ = n3;
            this.setX(n2);
            this.setZ(n3);
        }
    }
    
    public void setSpeed(final double n, float n2) {
        Minecraft.getMinecraft();
        float moveForward = Minecraft.thePlayer.movementInput.moveForward;
        Minecraft.getMinecraft();
        float moveStrafe = Minecraft.thePlayer.movementInput.moveStrafe;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionZ = 0.0;
        }
        else {
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0f) {
                    n2 += ((moveForward > 0.0) ? -45 : 45);
                }
                else if (moveStrafe < 0.0f) {
                    n2 += ((moveForward > 0.0) ? 45 : -45);
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }
            final double n3 = moveForward * n * Math.cos(Math.toRadians(n2 + 90.0f)) + moveStrafe * n * Math.sin(Math.toRadians(n2 + 90.0f));
            final double n4 = moveForward * n * Math.sin(Math.toRadians(n2 + 90.0f)) - moveStrafe * n * Math.cos(Math.toRadians(n2 + 90.0f));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionX = n3;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionZ = n4;
            this.setX(n3);
            this.setZ(n4);
        }
    }
}
