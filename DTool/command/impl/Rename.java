package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.item.*;

public class Rename extends Command
{
    public Rename() {
        super("Rename", "Rename", "Rename", new String[] { "Rename" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) throws Error {
        if (array.length == 0) {
            Segito.msg("§7Haszn\u00e1lat:§b -rename <T\u00e1rgy \u00faj neve>");
        }
        else {
            final Minecraft mc = Rename.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = Rename.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            else if (array.length == 0) {
                Segito.msg("Hiba t\u00f6rt\u00e9nt");
            }
            String string = array[0];
            while (1 < array.length) {
                string = String.valueOf(string) + " " + array[1];
                int n = 0;
                ++n;
            }
            final String replace = string.replace("&", "§").replace("§§", "&");
            final Minecraft mc3 = Rename.mc;
            final ItemStack currentItem = Minecraft.thePlayer.inventory.getCurrentItem();
            if (currentItem == null) {
                Segito.msg("§7Kell lennie egy t\u00e1rgynak a kezedben!");
            }
            else {
                currentItem.setStackDisplayName(replace);
                Segito.msg("§7Az item sikeresen \u00e1t lett nevezve: \"§b" + replace + "§7\"§7-ra/re!");
            }
        }
    }
}
