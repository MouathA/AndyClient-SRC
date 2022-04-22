package DTool.modules.combat;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;

public class FastBow extends Module
{
    public FastBow() {
        super("FastBow", 0, Category.Combat);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = FastBow.mc;
            if (Minecraft.thePlayer.onGround) {
                final Minecraft mc2 = FastBow.mc;
                if (Minecraft.thePlayer.getCurrentEquippedItem() != null) {
                    final Minecraft mc3 = FastBow.mc;
                    if (Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && FastBow.mc.gameSettings.keyBindUseItem.pressed) {
                        final Minecraft mc4 = FastBow.mc;
                        final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
                        final Minecraft mc5 = FastBow.mc;
                        sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.getHeldItem()));
                        final Minecraft mc6 = FastBow.mc;
                        final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                        final Minecraft mc7 = FastBow.mc;
                        thePlayer.setItemInUse(Minecraft.thePlayer.getHeldItem(), 7199);
                        final Minecraft mc8 = FastBow.mc;
                        if (Minecraft.thePlayer.ticksExisted % 2 == 0) {
                            final Minecraft mc9 = FastBow.mc;
                            final NetHandlerPlayClient sendQueue2 = Minecraft.thePlayer.sendQueue;
                            final Minecraft mc10 = FastBow.mc;
                            final double posX = Minecraft.thePlayer.posX;
                            final Minecraft mc11 = FastBow.mc;
                            final double n = Minecraft.thePlayer.posY - 0.2;
                            final Minecraft mc12 = FastBow.mc;
                            final double posZ = Minecraft.thePlayer.posZ;
                            final Minecraft mc13 = FastBow.mc;
                            sendQueue2.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, n, posZ, Minecraft.thePlayer.onGround));
                            while (0 < 20) {
                                final Minecraft mc14 = FastBow.mc;
                                final NetHandlerPlayClient sendQueue3 = Minecraft.thePlayer.sendQueue;
                                final Minecraft mc15 = FastBow.mc;
                                final double posX2 = Minecraft.thePlayer.posX;
                                final Minecraft mc16 = FastBow.mc;
                                final double n2 = Minecraft.thePlayer.posY + 1.0E-9;
                                final Minecraft mc17 = FastBow.mc;
                                final double posZ2 = Minecraft.thePlayer.posZ;
                                final Minecraft mc18 = FastBow.mc;
                                final float rotationYaw = Minecraft.thePlayer.rotationYaw;
                                final Minecraft mc19 = FastBow.mc;
                                sendQueue3.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX2, n2, posZ2, rotationYaw, Minecraft.thePlayer.rotationPitch, true));
                                int n3 = 0;
                                ++n3;
                            }
                        }
                        final Minecraft mc20 = FastBow.mc;
                        final PlayerControllerMP playerController = Minecraft.playerController;
                        final Minecraft mc21 = FastBow.mc;
                        playerController.onStoppedUsingItem(Minecraft.thePlayer);
                    }
                }
            }
        }
    }
    
    public void setSpeed(final double n) {
        final Minecraft mc = FastBow.mc;
        Minecraft.thePlayer.motionX = -MathHelper.sin(this.getDirection()) * n;
        final Minecraft mc2 = FastBow.mc;
        Minecraft.thePlayer.motionZ = MathHelper.cos(this.getDirection()) * n;
    }
    
    public float getDirection() {
        final Minecraft mc = FastBow.mc;
        final float rotationYawHead = Minecraft.thePlayer.rotationYawHead;
        final Minecraft mc2 = FastBow.mc;
        final float moveForward = Minecraft.thePlayer.moveForward;
        final Minecraft mc3 = FastBow.mc;
        final float moveStrafing = Minecraft.thePlayer.moveStrafing;
        float n = rotationYawHead + ((moveForward < 0.0f) ? 180 : 0);
        if (moveStrafing < 0.0f) {
            n += ((moveForward < 0.0f) ? -45 : ((moveForward == 0.0f) ? 90 : 45));
        }
        if (moveStrafing > 0.0f) {
            n -= ((moveForward < 0.0f) ? -45 : ((moveForward == 0.0f) ? 90 : 45));
        }
        return n * 0.017453292f;
    }
}
