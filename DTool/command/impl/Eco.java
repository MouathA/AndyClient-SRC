package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import net.minecraft.client.*;

public class Eco extends Command
{
    public Eco() {
        super("Eco", "Eco", "Eco", new String[] { "Eco" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -eco <give/reset>");
            Segito.msg("§b§lFONTOS!§7 ez csak akkor fog m\u0171k\u00f6dni,");
            Segito.msg("§7hogyha van a szerveren '§bEssentials§7'");
            Segito.msg("§7nevezet\u0171 plugin!");
        }
        else {
            if (array[0].equalsIgnoreCase("give")) {
                Segito.msg("§7Ez a folyamat eltarthat egy darabig...");
                final Minecraft mc = Eco.mc;
                Minecraft.thePlayer.sendChatMessage("/eco give ** 999999999999");
            }
            if (array[0].equalsIgnoreCase("reset")) {
                Segito.msg("§7Ez a folyamat eltarthat egy darabig...");
                final Minecraft mc2 = Eco.mc;
                Minecraft.thePlayer.sendChatMessage("/eco reset **");
            }
        }
    }
}
