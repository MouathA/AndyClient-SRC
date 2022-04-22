package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import DTool.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import Mood.*;

public class Give extends Command
{
    public Give() {
        super("Give", "Give", "Give", new String[] { "Give" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("Hiba t\u00f6rt\u00e9nt.");
        }
        final Minecraft mc = Give.mc;
        if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
            Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
        }
        Item item = Item.getFromRegistry(new ResourceLocation(array[0]));
        if (item == null && MathUtils.isInteger(array[0])) {
            item = Item.getItemById(Integer.parseInt(array[0]));
        }
        if (item == null) {
            Segito.msg("Az Item§e \"" + array[0] + "\"§7 nem tal\u00e1lhat\u00f3.");
        }
        if (array.length >= 2) {
            if (!MathUtils.isInteger(array[1])) {
                Segito.msg("§7Ez nem egy sz\u00e1m:§b " + array[1]);
            }
            Integer.valueOf(array[1]);
            if (1 < 1) {
                Segito.msg("Az \u00f6sszeg nem lehet kevesebb 1-n\u00e9l.");
            }
            if (1 > item.getItemStackLimit()) {
                Segito.msg("Az \u00f6sszeg t\u00fal sok:§e " + item.getItemStackLimit() + "§7 !");
            }
        }
        if (array.length >= 3) {
            if (!MathUtils.isInteger(array[2])) {
                Segito.msg("A MetaDat\u00e1knak sz\u00e1moknak kell lenni\u00fck.");
            }
            Integer.valueOf(array[2]);
        }
        String string = null;
        if (array.length >= 4) {
            string = array[3];
            while (4 < array.length) {
                string = String.valueOf(String.valueOf(string)) + " " + array[4];
                int n = 0;
                ++n;
            }
        }
        final ItemStack itemStack = new ItemStack(item, 1, 0);
        if (string != null) {
            itemStack.setTagCompound(JsonToNBT.func_180713_a(string));
        }
        if (HackedItemUtils.placeStackInHotbar(itemStack)) {
            Segito.msg("Az§e Item" + ((1 > 1) ? "ek" : "") + "§7 el\u0151 lett" + ((1 > 1) ? "ek k\u00e9sz\u00edtve." : "k\u00e9sz\u00edtve."));
            return;
        }
        Segito.msg("K\u00e9rlek, t\u00f6r\u00f6lj egy helyet a§b hotbarod§7-b\u00f3l.");
    }
}
