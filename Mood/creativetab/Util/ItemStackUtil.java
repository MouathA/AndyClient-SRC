package Mood.creativetab.Util;

import Mood.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ItemStackUtil
{
    static Client mood;
    public static final ItemStack empty;
    
    static {
        ItemStackUtil.mood = Client.getInstance();
        empty = new ItemStack(Blocks.air);
    }
    
    public static void addEmpty(final List list, final int n) {
        while (0 < n) {
            list.add(ItemStackUtil.empty);
            int n2 = 0;
            ++n2;
        }
    }
    
    public static void fillEmpty(final List list) {
        addEmpty(list, 9 - list.size() % 9);
    }
    
    public static void addEmpty(final List list) {
        list.add(ItemStackUtil.empty);
    }
    
    public static ItemStack stringtostack(String replace) {
        replace = replace.replace('&', '§');
        final Item item = new Item();
        final String[] split = replace.split(" ");
        final Item item2 = (Item)Item.itemRegistry.getObject(new ResourceLocation(split[0]));
        if (split.length >= 2 && split[1].matches("\\d+")) {
            Integer.parseInt(split[1]);
        }
        if (split.length >= 3 && split[2].matches("\\d+")) {
            Integer.parseInt(split[2]);
        }
        final ItemStack itemStack = new ItemStack(item2, 1, 0);
        if (split.length >= 4) {
            String string = "";
            while (3 < split.length) {
                string = String.valueOf(String.valueOf(string)) + " " + split[3];
                int n = 0;
                ++n;
            }
            itemStack.setTagCompound(JsonToNBT.func_180713_a(string));
        }
        return itemStack;
    }
    
    public static void removeSuspiciousTags(final ItemStack itemStack, final boolean b, final boolean b2, final boolean b3) {
        final NBTTagCompound tagCompound = itemStack.hasTagCompound() ? itemStack.getTagCompound() : new NBTTagCompound();
        if (b || !tagCompound.hasKey("Exploit")) {
            tagCompound.setByte("Exploit", (byte)((b2 ? 1 : 0) + (b3 ? 2 : 0)));
        }
        itemStack.setTagCompound(tagCompound);
    }
    
    public static void removeSuspiciousTags(final List list, final boolean b, final boolean b2) {
        final Iterator<ItemStack> iterator = list.iterator();
        while (iterator.hasNext()) {
            removeSuspiciousTags(iterator.next(), false, b, b2);
        }
    }
    
    public static void removeSuspiciousTags(final List list) {
        removeSuspiciousTags(list, true, true);
    }
    
    public static void modify(final ItemStack itemStack) {
        if (itemStack != null && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Exploit")) {
            final byte byte1 = itemStack.getTagCompound().getByte("Exploit");
            itemStack.getTagCompound().removeTag("Exploit");
            if (byte1 % 2 == 1 && itemStack.getTagCompound().hasKey("display", 10)) {
                itemStack.getTagCompound().removeTag("display");
            }
            if (byte1 % 4 == 1) {
                itemStack.getTagCompound().setByte("HideFlags", (byte)63);
            }
        }
    }
}
