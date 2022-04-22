package Mood;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class Segito
{
    public static void msg(String string) {
        if (Minecraft.thePlayer == null) {
            return;
        }
        string = ((StringBuilder)((StringBuilder)((StringBuilder)new StringBuilder("&8[&6").append(Client.name).append("&8]:&7 ").toString()).toString()).toString()).toString() + string;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(string.replace("&", "§"), new Object[0]));
    }
    
    public static void exploitfixer(String string) {
        if (Minecraft.thePlayer == null) {
            return;
        }
        string = ((StringBuilder)((StringBuilder)((StringBuilder)new StringBuilder("&8[&6").append(Client.exploitfixer).append("&8]:&7 ").toString()).toString()).toString()).toString() + string;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(string.replace("&", "§"), new Object[0]));
    }
    
    public static void exploitfixer(String string, final boolean b) {
        if (Minecraft.thePlayer == null) {
            return;
        }
        string = String.valueOf(String.valueOf(String.valueOf(b ? new StringBuilder("&8[&6").append(Client.exploitfixer).append("&8] &7").toString() : ""))) + string;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(string.replace("&", "§"), new Object[0]));
    }
    
    public static void msg(String string, final boolean b) {
        if (Minecraft.thePlayer == null) {
            return;
        }
        string = String.valueOf(String.valueOf(String.valueOf(b ? new StringBuilder("&8[&6").append(Client.name).append("&8] &7").toString() : ""))) + string;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(new ChatComponentTranslation(string.replace("&", "§"), new Object[0]));
    }
}
