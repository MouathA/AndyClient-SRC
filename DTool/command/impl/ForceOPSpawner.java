package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ForceOPSpawner extends Command
{
    public ForceOPSpawner() {
        super("forceopspawner", "forceopspawner", "forceopspawner", new String[] { "forceopspawner" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("§7K\u00e9rlek, adj meg egy parancsot.");
        }
        else {
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getSpawnerFromDispenser(HackedItemUtils.getForceOpSpawner(String.join(" ", (CharSequence[])array)))));
        }
    }
}
