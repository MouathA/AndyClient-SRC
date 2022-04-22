package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Grief extends Command
{
    public Grief() {
        super("Grief", "Grief", "Grief", new String[] { "Grief" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 3) {
            Segito.msg("§7Haszn\u00e1lat:§b -grief <x> <y> <z>");
        }
        else {
            final Minecraft mc = Grief.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = Grief.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getSpawnerFromDispenser(HackedItemUtils.getDestroyBlock(Double.valueOf(array[0]), Double.valueOf(array[1]), Double.valueOf(array[2])))));
        }
    }
}
