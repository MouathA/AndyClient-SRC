package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;

public class Gamemode extends Command
{
    public Gamemode() {
        super("gm", "gm", "gm", new String[] { "gm" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array[0].equalsIgnoreCase("c")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode creative");
            Segito.msg("Mostant\u00f3l kreat\u00edvm\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("creative")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode creative");
            Segito.msg("Mostant\u00f3l kreat\u00edvm\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("s")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode survival");
            Segito.msg("Mostant\u00f3l t\u00fal\u00e9l\u0151 m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("survival")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode survival");
            Segito.msg("Mostant\u00f3l t\u00fal\u00e9l\u0151 m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("sp")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode spectator");
            Segito.msg("Mostant\u00f3l szeml\u00e9l\u0151 m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("spectator")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode spectator");
            Segito.msg("Mostant\u00f3l szeml\u00e9l\u0151 m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("a")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode adventure");
            Segito.msg("Mostant\u00f3l kaland m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("a")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode adventure");
            Segito.msg("Mostant\u00f3l kaland m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("adventure")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode adventure");
            Segito.msg("Mostant\u00f3l kaland m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("0")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode survival");
            Segito.msg("Mostant\u00f3l t\u00fal\u00e9l\u0151 m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("2")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode adventure");
            Segito.msg("Mostant\u00f3l kaland m\u00f3dban vagy.");
        }
        if (array[0].equalsIgnoreCase("3")) {
            Minecraft.thePlayer.sendChatMessage("/gamemode spectator");
            Segito.msg("Mostant\u00f3l szeml\u00e9l\u0151 m\u00f3dban vagy.");
        }
    }
}
