package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class CopyIP extends Command
{
    public CopyIP() {
        super("CopyIP", "CopyIP", "CopyIP", new String[] { "CopyIP" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.getCurrentServerData() == null) {
            Segito.msg("§7Te igen csak retard\u00e1lt vagy, §begyj\u00e1t\u00e9kosm\u00f3dban");
            Segito.msg("§7hogyan szeretn\u00e9d megn\u00e9zni a §bSZERVER");
            Segito.msg("§7IP c\u00edm\u00e9t? Vagy ki szeretted volna fagyasztani");
            Segito.msg("§7ezt a fasza klienst? Lmao.");
            return;
        }
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(minecraft.getCurrentServerData().serverIP), null);
        Segito.msg("IP-c\u00edm sikeresen v\u00e1g\u00f3lapra m\u00e1solva!");
    }
}
