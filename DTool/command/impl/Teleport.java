package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Teleport extends Command
{
    public Teleport() {
        super("Teleport", "Teleport", "Teleport", new String[] { "Teleport" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 4) {
            Segito.msg("§7#teleport§b <\u00e1ldozat Neve> <x> <y> <z>");
        }
        else {
            final Minecraft mc = Teleport.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = Teleport.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            Segito.msg("Az§b Item§d sikeresen le lett k\u00e9rve!");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getSpawnerFromDispenser(HackedItemUtils.getPlayerSwirler(array[0], Double.parseDouble(array[1]), Double.parseDouble(array[2]), Double.parseDouble(array[3])))));
        }
    }
}
