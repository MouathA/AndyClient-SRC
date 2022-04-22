package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.gui.inventory.*;
import Mood.*;

public class LaggSignOFF extends Command
{
    public LaggSignOFF() {
        super("laggsign", "laggsign", "laggsign", new String[] { "laggsign" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array[0].equalsIgnoreCase("kikapcs")) {
            GuiEditSign.laggsign = false;
            Segito.msg("§7Sikeresen le\u00e1ll\u00edtottad ezt a folyamatot!");
        }
    }
}
