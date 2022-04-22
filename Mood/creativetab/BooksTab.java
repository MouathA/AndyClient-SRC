package Mood.creativetab;

import net.minecraft.creativetab.*;
import Mood.creativetab.Util.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class BooksTab extends CreativeTabs
{
    public BooksTab() {
        super("BooksTab");
        this.setBackgroundImageName("item_search.png");
    }
    
    @Override
    public void displayAllReleventItems(final List list) {
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:0s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:1s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:2s}]}\r\n"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:3s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:4s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:5s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:6s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:7s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:8s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:17s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:18s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:19s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:20s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:32s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:33s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:34s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:35s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:48s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:49s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:50s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:51s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:61s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:127s,id:62s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {display:{Name:\"§eSzuper Nindzsa K\u00e9zi Harci fegyver\"},StoredEnchantments:[0:{lvl:127s,id:0s},1:{lvl:127s,id:1s},2:{lvl:127s,id:2s},3:{lvl:127s,id:3s},4:{lvl:127s,id:4s},5:{lvl:127s,id:5s},6:{lvl:127s,id:6s},7:{lvl:127s,id:7s},8:{lvl:127s,id:8s},9:{lvl:127s,id:16s},10:{lvl:127s,id:17s},11:{lvl:127s,id:18s},12:{lvl:127s,id:19s},13:{lvl:127s,id:20s},14:{lvl:127s,id:21s},15:{lvl:127s,id:32s},16:{lvl:127s,id:33s},17:{lvl:127s,id:34s},18:{lvl:127s,id:35s},19:{lvl:127s,id:48s},20:{lvl:127s,id:49s},21:{lvl:127s,id:50s},22:{lvl:127s,id:51s},23:{lvl:127s,id:61s},24:{lvl:127s,id:62s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {display:{Name:\"§cHacker §c§lKit\"},StoredEnchantments:[0:{lvl:32767s,id:0s},1:{lvl:32767s,id:1s},2:{lvl:32767s,id:2s},3:{lvl:32767s,id:3s},4:{lvl:32767s,id:4s},5:{lvl:32767s,id:5s},6:{lvl:32767s,id:6s},7:{lvl:32767s,id:7s},8:{lvl:32767s,id:8s},9:{lvl:32767s,id:16s},10:{lvl:32767s,id:17s},11:{lvl:32767s,id:18s},12:{lvl:32767s,id:19s},13:{lvl:32767s,id:20s},14:{lvl:32767s,id:21s},15:{lvl:32767s,id:32s},16:{lvl:32767s,id:33s},17:{lvl:32767s,id:34s},18:{lvl:32767s,id:35s},19:{lvl:32767s,id:48s},20:{lvl:32767s,id:49s},21:{lvl:32767s,id:50s},22:{lvl:32767s,id:51s},23:{lvl:32767s,id:61s},24:{lvl:32767s,id:62s}]}"));
        list.add(ItemStackUtil.stringtostack("barrier 1 0"));
        list.add(ItemStackUtil.stringtostack("barrier 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("book 1 0"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:0s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:1s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:2s}]}\r\n"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:3s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:4s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:5s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:6s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:7s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:8s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:17s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:18s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:19s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:20s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:32s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:33s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:34s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:35s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:48s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:49s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:50s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:51s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:61s}]}"));
        list.add(ItemStackUtil.stringtostack("enchanted_book 1 0 {StoredEnchantments:[0:{lvl:50s,id:62s}]}"));
        final StringBuilder sb = new StringBuilder();
        while (0 <= 15000) {
            sb.append("\\ufdfd");
            int n = 0;
            ++n;
        }
        removeSuspiciousTags(list);
    }
    
    public static void removeSuspiciousTags(final List list) {
        for (final ItemStack itemStack : list) {
            if (itemStack.hasTagCompound()) {
                itemStack.getTagCompound().setBoolean("BooksTab", true);
            }
        }
    }
    
    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Items.enchanted_book);
    }
    
    @Override
    public String getTranslatedTabLabel() {
        return "§cEnchantolt K\u00f6nyvek";
    }
    
    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
