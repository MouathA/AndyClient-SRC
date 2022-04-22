package Mood.Gui;

import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.io.*;

public class GuiChangelog extends GuiScreen
{
    public static ArrayList ujitas;
    int pos;
    
    static {
        GuiChangelog.ujitas = new ArrayList();
    }
    
    public GuiChangelog() {
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 ChunkAnimator");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 BlockParticles");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Modules");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 OWN CreativeTab");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Extra Commands");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 AntiCrashScreen");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§7 'AntiV'§6 Theme TextField");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Login Screen");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Server Informations");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 OWN Buttons");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 UIButtons");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§c Garkolym§6 Items");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§7 'AntiV'§6 Background");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 WorldDownloader");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 WorldDownloaderBypass by§7 Flori2007");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 OWN Chat");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Design Room");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 ServerScanner");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 UUIDSpoof§7§o looks like: 'Dymeth Client'");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 Old Buttons deleted");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 Old Background deleted");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 BlockOverlay deleted");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 MultiPlayer buttons deleted");
        GuiChangelog.ujitas.add("§r§l[§e§l%§r§l]§6 ViaVersion§7§o F\u00fcgg\u0151ben...");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 New Optifine Version");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Resolver");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 PortScanner");
        GuiChangelog.ujitas.add("§r§l[§c§l-§r§l]§6 MultiPlayerMenu Design Deleted");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Discord ClickGui§7§o (Not Mine)");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 ServerFinder");
        GuiChangelog.ujitas.add("§r§l[§a§l+§r§l]§6 Bugs & Errors Fixed!");
        GuiChangelog.ujitas.add("§r§l(...)");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("");
        GuiChangelog.ujitas.add("§r§lItt nincs semmixD");
        this.pos = 0;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        while (0 < GuiChangelog.ujitas.size()) {
            this.drawString(Minecraft.fontRendererObj, GuiChangelog.ujitas.get(0), 20, 10 + this.pos / 20 + 0, -1);
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.pos = Math.min(this.pos + Mouse.getDWheel(), 0);
    }
}
