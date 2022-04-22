package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ArmorStand extends Command
{
    public ArmorStand() {
        super("armorstand", "armorstand", "armorstand", new String[] { "armorstand" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length > 0) {
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                return;
            }
            String string = "";
            while (0 < array.length) {
                string = String.valueOf(String.valueOf(string)) + array[0] + " ";
                int n = 0;
                ++n;
            }
            final ItemStack itemStack = new ItemStack(Items.armor_stand, 1, 0);
            itemStack.setTagCompound(JsonToNBT.func_180713_a(string));
            itemStack.setStackDisplayName("§5§lCustom§d§l ArmorStand");
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, itemStack));
            Segito.msg("Az§b Item§d sikeresen le lett k\u00e9rve!");
        }
        else {
            Segito.msg("§7Haszn\u00e1lat:§b -armorstand <NBTTag>");
        }
    }
}
