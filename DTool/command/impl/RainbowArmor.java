package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.item.*;

public class RainbowArmor extends Command
{
    public static boolean enabled;
    public static boolean Sisak;
    public static boolean Mellvert;
    public static boolean Nadrag;
    public static Random rnd;
    public static boolean Csizma;
    static int current;
    
    static {
        RainbowArmor.enabled = false;
        RainbowArmor.Sisak = true;
        RainbowArmor.Mellvert = true;
        RainbowArmor.Nadrag = true;
        RainbowArmor.rnd = new Random();
        RainbowArmor.Csizma = true;
    }
    
    public RainbowArmor() {
        super("RainbowArmor", "RainbowArmor", "RainbowArmor", new String[] { "RainbowArmor" });
    }
    
    public static void tick() {
        if (RainbowArmor.enabled) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.capabilities.isCreativeMode) {
                final int rgb = new Color(RainbowArmor.rnd.nextInt(255), RainbowArmor.rnd.nextInt(255), RainbowArmor.rnd.nextInt(255)).getRGB();
                if (RainbowArmor.current >= 18) {
                    RainbowArmor.current = 0;
                }
                if (RainbowArmor.Sisak) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.leather_helmet, 1, 0, "{HideFlags:2,display:{Name:\"\",color:" + rgb + "}}")));
                }
                if (RainbowArmor.Mellvert) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(6, HackedItemUtils.createItem(Items.leather_chestplate, 1, 0, "{HideFlags:2,display:{Name:\"\",color:" + rgb + "}}")));
                }
                if (RainbowArmor.Nadrag) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(7, HackedItemUtils.createItem(Items.leather_leggings, 1, 0, "{HideFlags:2,display:{Name:\"\",color:" + rgb + "}}")));
                }
                if (RainbowArmor.Csizma) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(8, HackedItemUtils.createItem(Items.leather_boots, 1, 0, "{HideFlags:2,display:{Name:\"\",color:" + rgb + "}}")));
                }
                ++RainbowArmor.current;
            }
        }
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -rainbowarmor <bekapcs/kikapcs>");
            return;
        }
        if (array[0].equalsIgnoreCase("bekapcs")) {
            RainbowArmor.enabled = true;
            final Minecraft mc = RainbowArmor.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§7Sikeresen bekapcsoltad ezt a funkci\u00f3t!");
        }
        if (array[0].equalsIgnoreCase("kikapcs")) {
            final Minecraft mc2 = RainbowArmor.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            RainbowArmor.enabled = false;
            if (RainbowArmor.Sisak) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (RainbowArmor.Mellvert) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(6, null));
            }
            if (RainbowArmor.Nadrag) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(7, null));
            }
            if (RainbowArmor.Csizma) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(8, null));
            }
            Segito.msg("§7Sikeresen le\u00e1ll\u00edtottad ezt a funkci\u00f3t!");
        }
    }
}
