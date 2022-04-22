package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ForceOPSign extends Command
{
    public ForceOPSign() {
        super("exsign", "exsign", "exsign", new String[] { "exsign" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("§7K\u00e9rlek, adj meg egy parancsot.");
        }
        else {
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getCommandSign(String.join(" ", (CharSequence[])array), "§8§l[§5§lMood§8§l]§r")));
        }
    }
}
