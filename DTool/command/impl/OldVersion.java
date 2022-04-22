package DTool.command.impl;

import DTool.command.*;
import DTool.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public class OldVersion extends Command
{
    public float MokasIdo;
    private final TimeManager timer;
    public static boolean enabled;
    
    static {
        OldVersion.enabled = true;
    }
    
    public OldVersion() {
        super("OldVersion", "OldVersion", "OldVersion", new String[] { "OldVersion" });
        this.MokasIdo = 6.0f;
        this.timer = new TimeManager();
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 0 && OldVersion.enabled && this.timer.sleep(this.timer.convertToMillis(this.MokasIdo))) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "------[§7That's Client is outdated!§4]---------"));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Sajn\u00e1lattal k\u00f6z\u00f6lj\u00fck, hogy a kliens"));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§e§lBETA§c verzi\u00f3j\u00e1t haszn\u00e1lhatja."));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Sajnos ez a verzi\u00f3 m\u00e1r"));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "§7nem eg\u00e9szen§c hasznos."));
            final ChatComponentText chatComponentText = new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_RED).append(EnumChatFormatting.UNDERLINE).append("§7§l[§8§lKattints ide§7§l]§r§c a§e premium").toString());
            chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/c/Cl3sTerMan/videos"));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(chatComponentText);
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "§cverzi\u00f3 megv\u00e1s\u00e1rol\u00e1s\u00e1hoz!"));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "-------------------------------------"));
            this.timer.resetTime();
        }
    }
}
