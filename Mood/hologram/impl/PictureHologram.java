package Mood.hologram.impl;

import net.minecraft.item.*;
import net.minecraft.client.*;
import java.net.*;
import javax.imageio.*;
import Mood.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PictureHologram
{
    public static PictureHologram client;
    public double x;
    public double y;
    public double z;
    public ItemStack[] stands;
    public int counter;
    public PictureHologramLoader is;
    
    public PictureHologram() {
        this.x = -1.0;
        this.y = -1.0;
        this.z = -1.0;
        this.counter = 0;
    }
    
    public void init() {
        PictureHologram.client = this;
    }
    
    public void setLoc() {
        Minecraft.getMinecraft();
        this.x = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        this.y = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        this.z = Minecraft.thePlayer.posZ;
    }
    
    public void setup(final String s, final int n, final int n2) {
        this.is = new PictureHologramLoader(ImageIO.read(new URL(s)), n, '\u2b1b');
        if (this.is != null) {
            this.stands = this.is.getArmorStands(this.x, this.y, this.z);
            this.counter = 0;
            this.giveNextArmorstand();
        }
    }
    
    public void giveNextArmorstand() {
        if (this.stands != null) {
            Minecraft.getMinecraft();
            Minecraft.rightClickDelayTimer = 0;
            HackedItemUtils.addItem(this.stands[this.counter++]);
            if (this.counter >= this.stands.length) {
                this.counter = 0;
                this.stands = null;
            }
        }
        else {
            HackedItemUtils.addItem(null);
            Segito.msg("§7A§e Hologram§7 sikeresen le lett id\u00e9zve.");
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.getHeldItem() != null && Minecraft.thePlayer.getHeldItem().getDisplayName().startsWith("#")) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(PlayerControllerMP.currentPlayerItem + 36, null));
            }
        }
    }
}
