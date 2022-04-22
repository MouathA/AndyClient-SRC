package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import DTool.modules.*;
import java.util.*;

public class Mods extends Command
{
    public Mods() {
        super("Modules", "Modules", "Modules", new String[] { "Modules" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length > 0) {
            Segito.msg("Hiba");
            return;
        }
        final StringBuilder sb = new StringBuilder();
        for (final Module module : Client.modules) {
            if (!false) {
                sb.append("§8, " + (module.isEnable() ? "§a" : "§7") + module.getName());
            }
            else {
                sb.append("§6Modules:§7 " + (module.isEnable() ? "" : "") + module.getName());
            }
        }
        Segito.msg(sb.toString());
    }
}
