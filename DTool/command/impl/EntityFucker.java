package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class EntityFucker extends Command
{
    public EntityFucker() {
        super("EntityFucker", "EntityFucker", "EntityFucker", new String[] { "EntityFucker" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 4) {
            Segito.msg("§7#entityfucker§b <Entit\u00e1s> <Motion§a X>§b <Motion§a Y>§b <Motion§a Z>");
        }
        else {
            final Minecraft mc = EntityFucker.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = EntityFucker.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getSpawnerFromDispenser(HackedItemUtils.getEntityWerfer(array[0], Double.valueOf(array[1]), Double.valueOf(array[2]), Double.valueOf(array[3])))));
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
        }
    }
}
