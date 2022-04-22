package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FlyingItems extends Command
{
    public FlyingItems() {
        super("FlyingItem", "FlyingItem", "FlyingItem", new String[] { "FlyingItem" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("§7K\u00e9rlek, adj meg Item-et.");
        }
        else {
            final Minecraft mc = FlyingItems.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = FlyingItems.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, HackedItemUtils.getSpawnerFromDispenser(HackedItemUtils.getFlyItems(array[0]))));
        }
    }
}
