package DTool.modules.player;

import DTool.modules.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import Mood.*;
import net.minecraft.world.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.player.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class Freecam extends Module
{
    EntityOtherPlayerMP ghost;
    
    public Freecam() {
        super("Freecam", 0, Category.Player);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        final Minecraft mc = Freecam.mc;
        Minecraft.thePlayer.capabilities.isFlying = false;
        final Minecraft mc2 = Freecam.mc;
        Minecraft.thePlayer.noClip = false;
        final Minecraft mc3 = Freecam.mc;
        Minecraft.thePlayer.copyLocationAndAnglesFrom(this.ghost);
        final Minecraft mc4 = Freecam.mc;
        Minecraft.theWorld.removeEntityFromWorld(-2);
    }
    
    @Override
    public boolean onEnable() {
        super.onEnable();
        final Client instance = Client.INSTANCE;
        Client.getModuleByName("Blink").setState(false);
        final Minecraft mc = Freecam.mc;
        final WorldClient theWorld = Minecraft.theWorld;
        final Minecraft mc2 = Freecam.mc;
        this.ghost = new EntityOtherPlayerMP(theWorld, Minecraft.getSession().getProfile());
        final EntityOtherPlayerMP ghost = this.ghost;
        final Minecraft mc3 = Freecam.mc;
        ghost.copyLocationAndAnglesFrom(Minecraft.thePlayer);
        final EntityOtherPlayerMP ghost2 = this.ghost;
        final Minecraft mc4 = Freecam.mc;
        ghost2.setRotationYawHead(Minecraft.thePlayer.rotationYawHead);
        final InventoryPlayer inventory = this.ghost.inventory;
        final Minecraft mc5 = Freecam.mc;
        inventory.copyInventory(Minecraft.thePlayer.inventory);
        final Minecraft mc6 = Freecam.mc;
        Minecraft.theWorld.addEntityToWorld(-2, this.ghost);
        final Minecraft mc7 = Freecam.mc;
        Minecraft.thePlayer.motionY = 0.05447;
        return super.onEnable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Freecam.mc;
            Minecraft.thePlayer.capabilities.isFlying = true;
        }
    }
    
    @Override
    public Packet onClientSendPacket(Packet packet) {
        if (packet instanceof C03PacketPlayer || packet instanceof C0BPacketEntityAction || packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C05PacketPlayerLook || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof C0APacketAnimation || packet instanceof C0CPacketInput) {
            packet = null;
        }
        return super.onClientSendPacket(packet);
    }
}
