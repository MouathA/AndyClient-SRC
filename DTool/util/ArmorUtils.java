package DTool.util;

import net.minecraft.client.*;
import net.minecraft.item.*;

public class ArmorUtils
{
    public static boolean IsBetterArmor(final int n, final int[] array) {
        if (Minecraft.thePlayer.inventory.armorInventory[n] != null) {
            int n3 = 0;
            while (0 < array.length && Item.getIdFromItem(Minecraft.thePlayer.inventory.armorInventory[n].getItem()) != array[0]) {
                int n2 = 0;
                ++n2;
                ++n3;
            }
            while (0 < array.length && getItem(array[0]) == -1) {
                int n4 = 0;
                ++n4;
                ++n3;
            }
        }
        return false;
    }
    
    public static int getItem(final int n) {
        return -1;
    }
}
