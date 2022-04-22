package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.item.*;

public class CopyNBTData extends Command
{
    public CopyNBTData() {
        super("CopyNBTData", "CopyNBTData", "CopyNBTData", new String[] { "CopyNBTData" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            final Minecraft mc = CopyNBTData.mc;
            final ItemStack heldItem = Minecraft.thePlayer.getHeldItem();
            if (heldItem == null) {
                Segito.msg("Lennie kell egy§e Item§7-nek a kezedben!");
                return;
            }
            HackerItemsHelper.copyString(heldItem.getTagCompound().toString().replace("§", "&"));
            Segito.msg("Sikeresen lek\u00e9rted az item adatait!");
        }
        else {
            Segito.msg("§7Haszn\u00e1lat:§b (tartsd az itemet a kezedben) -copynbtdata");
            Segito.msg("§c§lFONTOS!§7 Hogy a kezedben egy NBT");
            Segito.msg("§7Taggel rendelkez\u0151 Item legyen.");
        }
    }
}
