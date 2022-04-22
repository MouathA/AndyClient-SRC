package DTool.command.impl;

import DTool.command.*;
import DTool.modules.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.*;
import Mood.Helpers.*;
import net.minecraft.network.*;
import Mood.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class ThrowItem extends Command
{
    public Module toggled;
    public static boolean enabled;
    public static boolean names;
    public static boolean stacks;
    
    static {
        ThrowItem.enabled = false;
        ThrowItem.names = true;
        ThrowItem.stacks = true;
    }
    
    public ThrowItem() {
        super("ThrowItems", "ThrowItem", "ThrowItem", new String[] { "ThrowItem" });
    }
    
    public static void onTick() {
        if (ThrowItem.enabled) {
            while (0 != 6) {
                int n;
                for (n = new Random().nextInt(431); Item.getItemById(n) == null || (ThrowItem.stacks && new ItemStack(Item.getItemById(n)).getMaxStackSize() != 64); n = new Random().nextInt(431)) {}
                final ItemStack item = HackedItemUtils.createItem(Item.getItemById(n), 64, 0, "");
                if (ThrowItem.names) {
                    final char[] array = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'k', 'l', 'm', 'n', 'o', 'r' };
                    item.setStackDisplayName("§" + array[new Random().nextInt(array.length)] + "§" + array[new Random().nextInt(array.length)] + HackedItemUtils.getThrowItemsData());
                }
                if (!HackedItemUtils.addItemNoInv(item)) {
                    return;
                }
                final C07PacketPlayerDigging.Action drop_ALL_ITEMS = C07PacketPlayerDigging.Action.DROP_ALL_ITEMS;
                final Minecraft mc = ThrowItem.mc;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(drop_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    @Override
    public void onCommand(final String[] array, final String s) throws Error {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -throwitems <bekapcs/kikapcs>");
            return;
        }
        if (array[0].equalsIgnoreCase("bekapcs")) {
            ThrowItem.enabled = true;
            final Minecraft mc = ThrowItem.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§eSikeresen bekapcsoltad ezt a funkci\u00f3t!");
        }
        if (array[0].equalsIgnoreCase("kikapcs")) {
            final Minecraft mc2 = ThrowItem.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            ThrowItem.enabled = false;
            Segito.msg("§7Sikeresen le\u00e1ll\u00edtottad ezt a funkci\u00f3t!");
        }
    }
}
