package DTool.modules.player;

import DTool.modules.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import java.util.*;
import Mood.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class Blink extends Module
{
    public EntityOtherPlayerMP ghost;
    public ArrayList packets;
    public int add;
    
    public Blink() {
        super("Blink", 0, Category.Player);
        this.packets = new ArrayList();
        this.add = 0;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        for (final Packet packet : this.packets) {
            final Minecraft mc = Blink.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }
        final Minecraft mc2 = Blink.mc;
        Minecraft.theWorld.removeEntityFromWorld(-2);
    }
    
    @Override
    public boolean onEnable() {
        super.onEnable();
        final Client instance = Client.INSTANCE;
        Client.getModuleByName("Freecam").setState(false);
        this.packets.clear();
        final Minecraft mc = Blink.mc;
        final WorldClient theWorld = Minecraft.theWorld;
        final Minecraft mc2 = Blink.mc;
        this.ghost = new EntityOtherPlayerMP(theWorld, Minecraft.getSession().getProfile());
        final EntityOtherPlayerMP ghost = this.ghost;
        final Minecraft mc3 = Blink.mc;
        ghost.copyLocationAndAnglesFrom(Minecraft.thePlayer);
        final EntityOtherPlayerMP ghost2 = this.ghost;
        final Minecraft mc4 = Blink.mc;
        ghost2.setRotationYawHead(Minecraft.thePlayer.rotationYawHead);
        final InventoryPlayer inventory = this.ghost.inventory;
        final Minecraft mc5 = Blink.mc;
        inventory.copyInventory(Minecraft.thePlayer.inventory);
        final Minecraft mc6 = Blink.mc;
        Minecraft.theWorld.addEntityToWorld(-2, this.ghost);
        final Minecraft mc7 = Blink.mc;
        Minecraft.thePlayer.motionY = 0.05447;
        return super.onEnable();
    }
    
    @Override
    public Packet onClientSendPacket(Packet packet) {
        if (packet instanceof C03PacketPlayer) {
            if (this.add >= 8) {
                this.add = 0;
                this.packets.add(packet);
            }
            ++this.add;
            packet = null;
        }
        if (packet instanceof C0BPacketEntityAction) {
            packet = null;
        }
        if (packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            packet = null;
        }
        if (packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            packet = null;
        }
        if (packet instanceof C0APacketAnimation) {
            packet = null;
        }
        if (packet instanceof C0CPacketInput) {
            packet = null;
        }
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            this.packets.add(packet);
            packet = null;
        }
        if (packet instanceof C07PacketPlayerDigging) {
            this.packets.add(packet);
            packet = null;
        }
        if (packet instanceof C02PacketUseEntity) {
            this.packets.add(packet);
            packet = null;
        }
        return super.onClientSendPacket(packet);
    }
}
