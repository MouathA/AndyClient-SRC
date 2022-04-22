package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import net.minecraft.client.*;

public class DeleteServerPlugins extends Command
{
    public DeleteServerPlugins() {
        super("DeleteServerPlugins", "DeleteServerPlugins", "DeleteServerPlugins", new String[] { "DeleteServerPlugins" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -deleteserverplugins <beallit/elindit>");
            Segito.msg("§b§lFONTOS!§7 ehhez kelleni fog egy Multiverse-Core");
            Segito.msg("§7nevezet\u0171 plugin!");
            Segito.msg("§7Valamint fontos, hogy legyen§b *§7 jogod");
            Segito.msg("§7a szerveren.");
        }
        else {
            if (array[0].equalsIgnoreCase("beallit")) {
                Segito.msg("§7Bet\u00f6lt\u00e9s...");
                Minecraft.thePlayer.sendChatMessage("/mvimport plugins/Essentials/ normal");
            }
            if (array[0].equalsIgnoreCase("elindit")) {
                Segito.msg("§7A§b Pluginok§7 sikeresen t\u00f6rl\u00e9sre ker\u00fcltek!");
                Minecraft.thePlayer.sendChatMessage("/mvdelete plugins/Essentials/");
            }
        }
    }
}
