package wdl.gui;

import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import wdl.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public abstract class GuiTurningCameraBase extends GuiScreen
{
    private float yaw;
    private float yawNextTick;
    private int oldCameraMode;
    private boolean oldHideHud;
    private boolean oldShowDebug;
    private EntityPlayer.EnumChatVisibility oldChatVisibility;
    private EntityPlayerSP cam;
    private Entity oldRenderViewEntity;
    private boolean initializedCamera;
    private static final float ROTATION_SPEED = 1.0f;
    private static final float ROTATION_VARIANCE = 0.7f;
    
    public GuiTurningCameraBase() {
        this.initializedCamera = false;
    }
    
    @Override
    public void initGui() {
        if (!this.initializedCamera) {
            (this.cam = new EntityPlayerSP(WDL.minecraft, WDL.worldClient, WDL.thePlayer.sendQueue, WDL.thePlayer.getStatFileWriter())).setLocationAndAngles(WDL.thePlayer.posX, WDL.thePlayer.posY - WDL.thePlayer.getYOffset(), WDL.thePlayer.posZ, WDL.thePlayer.rotationYaw, 0.0f);
            final float rotationYaw = WDL.thePlayer.rotationYaw;
            this.yawNextTick = rotationYaw;
            this.yaw = rotationYaw;
            this.oldCameraMode = WDL.minecraft.gameSettings.thirdPersonView;
            this.oldHideHud = WDL.minecraft.gameSettings.hideGUI;
            this.oldShowDebug = WDL.minecraft.gameSettings.showDebugInfo;
            final GameSettings gameSettings = WDL.minecraft.gameSettings;
            this.oldChatVisibility = GameSettings.chatVisibility;
            WDL.minecraft.gameSettings.thirdPersonView = 0;
            WDL.minecraft.gameSettings.hideGUI = true;
            WDL.minecraft.gameSettings.showDebugInfo = false;
            final GameSettings gameSettings2 = WDL.minecraft.gameSettings;
            GameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
            this.oldRenderViewEntity = WDL.minecraft.func_175606_aa();
            this.initializedCamera = true;
        }
        WDL.minecraft.func_175607_a(this.cam);
    }
    
    @Override
    public void updateScreen() {
        this.yaw = this.yawNextTick;
        this.yawNextTick = this.yaw + 1.0f * (float)(1.0 + 0.699999988079071 * Math.cos((this.yaw + 45.0f) / 45.0 * 3.141592653589793));
    }
    
    private double truncateDistanceIfBlockInWay(final double n, final double n2, double n3) {
        final Vec3 addVector = WDL.thePlayer.getPositionVector().addVector(0.0, WDL.thePlayer.getEyeHeight(), 0.0);
        final Vec3 vec3 = new Vec3(WDL.thePlayer.posX - n3 * n, WDL.thePlayer.posY + WDL.thePlayer.getEyeHeight(), WDL.thePlayer.posZ + n2);
        while (0 < 9) {
            float n4 = false ? -0.1f : 0.1f;
            float n5 = false ? -0.1f : 0.1f;
            float n6 = false ? -0.1f : 0.1f;
            if (0 == 8) {
                n4 = 0.0f;
                n5 = 0.0f;
                n6 = 0.0f;
            }
            final Vec3 addVector2 = addVector.addVector(n4, n5, n6);
            final Vec3 addVector3 = vec3.addVector(n4, n5, n6);
            final Minecraft mc = GuiTurningCameraBase.mc;
            final MovingObjectPosition rayTraceBlocks = Minecraft.theWorld.rayTraceBlocks(addVector2, addVector3);
            if (rayTraceBlocks != null) {
                final double distanceTo = rayTraceBlocks.hitVec.distanceTo(addVector);
                if (distanceTo < n3 && distanceTo > 0.0) {
                    n3 = distanceTo;
                }
            }
            int n7 = 0;
            ++n7;
        }
        return n3 - 0.25;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.cam != null) {
            final float n4 = this.yaw + (this.yawNextTick - this.yaw) * n3;
            final EntityPlayerSP cam = this.cam;
            final EntityPlayerSP cam2 = this.cam;
            final float n5 = 0.0f;
            cam2.rotationPitch = n5;
            cam.prevRotationPitch = n5;
            final EntityPlayerSP cam3 = this.cam;
            final EntityPlayerSP cam4 = this.cam;
            final float n6 = n4;
            cam4.rotationYaw = n6;
            cam3.prevRotationYaw = n6;
            final double cos = Math.cos(n4 / 180.0 * 3.141592653589793);
            final double sin = Math.sin((n4 - 90.0f) / 180.0 * 3.141592653589793);
            final double truncateDistanceIfBlockInWay = this.truncateDistanceIfBlockInWay(cos, sin, 0.5);
            final EntityPlayerSP cam5 = this.cam;
            final EntityPlayerSP cam6 = this.cam;
            final EntityPlayerSP cam7 = this.cam;
            final double posY = WDL.thePlayer.posY;
            cam7.posY = posY;
            cam6.prevPosY = posY;
            cam5.lastTickPosY = posY;
            final EntityPlayerSP cam8 = this.cam;
            final EntityPlayerSP cam9 = this.cam;
            final EntityPlayerSP cam10 = this.cam;
            final double lastTickPosX = WDL.thePlayer.posX - truncateDistanceIfBlockInWay * cos;
            cam10.posX = lastTickPosX;
            cam9.prevPosX = lastTickPosX;
            cam8.lastTickPosX = lastTickPosX;
            final EntityPlayerSP cam11 = this.cam;
            final EntityPlayerSP cam12 = this.cam;
            final EntityPlayerSP cam13 = this.cam;
            final double lastTickPosZ = WDL.thePlayer.posZ + truncateDistanceIfBlockInWay * sin;
            cam13.posZ = lastTickPosZ;
            cam12.prevPosZ = lastTickPosZ;
            cam11.lastTickPosZ = lastTickPosZ;
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        WDL.minecraft.gameSettings.thirdPersonView = this.oldCameraMode;
        WDL.minecraft.gameSettings.hideGUI = this.oldHideHud;
        WDL.minecraft.gameSettings.showDebugInfo = this.oldShowDebug;
        final GameSettings gameSettings = WDL.minecraft.gameSettings;
        GameSettings.chatVisibility = this.oldChatVisibility;
        WDL.minecraft.func_175607_a(this.oldRenderViewEntity);
    }
}
