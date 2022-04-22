package DTool.modules.combat;

import DTool.modules.*;
import net.minecraft.entity.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.network.*;

public class HitFly extends Module
{
    private int ticks;
    public static float yaw;
    public static float pitch;
    public static Entity entity;
    
    public HitFly() {
        super("HitFly", 0, Category.Combat);
        this.ticks = 0;
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
            ++this.ticks;
            HitFly.entity = this.closeEntity();
            if (HitFly.entity != null) {
                final Minecraft mc = HitFly.mc;
                if (Minecraft.thePlayer.getDistanceToEntity(HitFly.entity) <= (float)4.5) {
                    if (HitFly.entity.isInvisible()) {
                        return;
                    }
                    if (HitFly.entity.isEntityAlive()) {
                        final Minecraft mc2 = HitFly.mc;
                        if (Minecraft.thePlayer.getHeldItem() != null) {
                            final Minecraft mc3 = HitFly.mc;
                            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                            final Minecraft mc4 = HitFly.mc;
                            final ItemStack heldItem = Minecraft.thePlayer.getHeldItem();
                            final Minecraft mc5 = HitFly.mc;
                            thePlayer.setItemInUse(heldItem, Minecraft.thePlayer.getHeldItem().getMaxItemUseDuration());
                        }
                        LookAtEntity(HitFly.entity);
                        if (this.ticks >= 20.0 / 18.0) {
                            final Minecraft mc6 = HitFly.mc;
                            Minecraft.thePlayer.jump();
                            final Minecraft mc7 = HitFly.mc;
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(HitFly.entity, C02PacketUseEntity.Action.ATTACK));
                            final Minecraft mc8 = HitFly.mc;
                            Minecraft.thePlayer.swingItem();
                            this.ticks = 0;
                        }
                    }
                }
            }
        }
    }
    
    public Entity closeEntity() {
        Entity entity = null;
        final Minecraft mc = HitFly.mc;
        for (final Entity entity2 : Minecraft.theWorld.loadedEntityList) {
            if (entity2 instanceof EntityOtherPlayerMP && entity2.isEntityAlive() && !entity2.isInvisible()) {
                if (entity != null) {
                    final Minecraft mc2 = HitFly.mc;
                    final float distanceToEntity = Minecraft.thePlayer.getDistanceToEntity(entity2);
                    final Minecraft mc3 = HitFly.mc;
                    if (distanceToEntity >= Minecraft.thePlayer.getDistanceToEntity(entity)) {
                        continue;
                    }
                }
                entity = entity2;
            }
        }
        return entity;
    }
    
    public static float[] getRotations(final Entity entity) {
        return getRotationFromPosition(entity.posX, entity.posZ, entity.boundingBox.maxY - 4.0);
    }
    
    public static float[] getRotationFromPosition(final double n, final double n2, final double n3) {
        Minecraft.getMinecraft();
        final double n4 = n - Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        final double n5 = n2 - Minecraft.thePlayer.posZ;
        Minecraft.getMinecraft();
        final double n6 = n3 - Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        return new float[] { (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n6 + Minecraft.thePlayer.getEyeHeight(), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) };
    }
    
    public static void LookAtEntity(final Entity entity) {
        final float[] rotations = getRotations(entity);
        HitFly.yaw = rotations[0];
        HitFly.pitch = rotations[1];
    }
    
    public static void look() {
        final Minecraft mc = HitFly.mc;
        final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
        final float yaw = HitFly.yaw;
        final float pitch = HitFly.pitch;
        final Minecraft mc2 = HitFly.mc;
        sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Minecraft.thePlayer.onGround));
    }
    
    public static void poslook() {
        final Minecraft mc = HitFly.mc;
        final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
        final Minecraft mc2 = HitFly.mc;
        final double posX = Minecraft.thePlayer.posX;
        final Minecraft mc3 = HitFly.mc;
        final double minY = Minecraft.thePlayer.getEntityBoundingBox().minY;
        final Minecraft mc4 = HitFly.mc;
        final double posZ = Minecraft.thePlayer.posZ;
        final float yaw = HitFly.yaw;
        final float pitch = HitFly.pitch;
        final Minecraft mc5 = HitFly.mc;
        sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX, minY, posZ, yaw, pitch, Minecraft.thePlayer.onGround));
    }
}
