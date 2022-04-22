package viamcp.utils;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import viamcp.*;
import viamcp.protocols.*;

public class AttackOrder
{
    private static final Minecraft mc;
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static void sendConditionalSwing(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition != null && movingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
            Minecraft.thePlayer.swingItem();
        }
    }
    
    public static void sendFixedAttack(final EntityPlayer entityPlayer, final Entity entity) {
        if (ViaMCP.getInstance().getVersion() <= ProtocolCollection.getProtocolById(47).getVersion()) {
            send1_8Attack(entityPlayer, entity);
        }
        else {
            send1_9Attack(entityPlayer, entity);
        }
    }
    
    private static void send1_8Attack(final EntityPlayer entityPlayer, final Entity entity) {
        Minecraft.thePlayer.swingItem();
        Minecraft.playerController.attackEntity(entityPlayer, entity);
    }
    
    private static void send1_9Attack(final EntityPlayer entityPlayer, final Entity entity) {
        Minecraft.playerController.attackEntity(entityPlayer, entity);
        Minecraft.thePlayer.swingItem();
    }
}
