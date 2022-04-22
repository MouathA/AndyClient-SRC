package Mood.creativetab;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import Mood.creativetab.Util.*;

public class MoodTabPotion extends CreativeTabs
{
    public MoodTabPotion() {
        super("PotionsTab");
        this.setBackgroundImageName("item_search.png");
    }
    
    @Override
    public void displayAllReleventItems(final List list) {
        list.add(new ItemStack(Items.milk_bucket));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{Ambient:0,Duration:2000,Id:6,Amplifier:125}],display:{Name:\"§6Killer§7Potion\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{Duration:0,Id:6,Amplifier:1000},1:{Duration:999999,Id:10,Amplifier:1000},2:{Duration:999999,Id:21,Amplifier:1000}],display:{Name:\"§6Undead§7 Potion\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:72000,Id:2,Amplifier:127}],HideFlags:63,Potion:\"minecraft:awkward\",display:{Name:\"§6AntiRun§7 Potion\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:72000,Id:1,Amplifier:4},1:{ShowParticles:0b,Duration:72000,Id:3,Amplifier:127},2:{ShowParticles:0b,Duration:72000,Id:5,Amplifier:127},3:{ShowParticles:0b,Duration:72000,Id:8,Amplifier:1},4:{ShowParticles:0b,Duration:72000,Id:10,Amplifier:255},5:{ShowParticles:0b,Duration:72000,Id:11,Amplifier:255},6:{ShowParticles:0b,Duration:72000,Id:12,Amplifier:255},7:{ShowParticles:0b,Duration:72000,Id:13,Amplifier:255},8:{ShowParticles:0b,Duration:72000,Id:14,Amplifier:130},9:{ShowParticles:0b,Duration:72000,Id:16,Amplifier:255},10:{ShowParticles:0b,Duration:72000,Id:23,Amplifier:255}],HideFlags:63,Potion:\"minecraft:awkward\",display:{Name:\"§5§lGrief §d§lPotion\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:72000,Id:8,Amplifier:130}],HideFlags:63,Potion:\"minecraft:leaping\",display:{Name:\"§5§lAnti§d§l Jump\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 16395 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:511040,Id:1,Amplifier:50},1:{Duration:511040,Id:2,Amplifier:50},2:{ShowParticles:0b,Duration:511040,Id:3,Amplifier:50},3:{ShowParticles:0b,Duration:511040,Id:4,Amplifier:50},4:{ShowParticles:0b,Duration:511040,Id:5,Amplifier:50},5:{ShowParticles:0b,Duration:511040,Id:6,Amplifier:50},6:{ShowParticles:0b,Duration:511040,Id:7,Amplifier:50},7:{ShowParticles:0b,Duration:511040,Id:9,Amplifier:50},8:{ShowParticles:0b,Duration:511040,Id:10,Amplifier:50},9:{ShowParticles:0b,Duration:511040,Id:11,Amplifier:50},10:{ShowParticles:0b,Duration:511040,Id:12,Amplifier:50},11:{ShowParticles:0b,Duration:511040,Id:13,Amplifier:50},12:{ShowParticles:0b,Duration:511040,Id:15,Amplifier:50},13:{ShowParticles:0b,Duration:511040,Id:16,Amplifier:50},14:{ShowParticles:0b,Duration:511040,Id:17,Amplifier:50},15:{ShowParticles:0b,Duration:511040,Id:18,Amplifier:50},16:{ShowParticles:0b,Duration:511040,Id:19,Amplifier:50},17:{ShowParticles:0b,Duration:511040,Id:20,Amplifier:50},18:{ShowParticles:0b,Duration:511040,Id:21,Amplifier:50},19:{ShowParticles:0b,Duration:511040,Id:22,Amplifier:50},20:{ShowParticles:0b,Duration:511040,Id:23,Amplifier:50}],display:{Name:\"§5§lTroll§d§lPotion\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 0 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:72000,Id:14,Amplifier:130}],HideFlags:63,Potion:\"minecraft:leaping\",display:{Name:\"§5§lPerfect §d§lInvisible\"}}"));
        list.add(ItemStackUtil.stringtostack("potion 1 0 {CustomPotionEffects:[0:{ShowParticles:0b,Duration:72000,Id:1,Amplifier:4},1:{ShowParticles:0b,Duration:72000,Id:3,Amplifier:127},2:{ShowParticles:0b,Duration:72000,Id:5,Amplifier:127},3:{ShowParticles:0b,Duration:72000,Id:8,Amplifier:1},4:{ShowParticles:0b,Duration:72000,Id:10,Amplifier:255},5:{ShowParticles:0b,Duration:72000,Id:11,Amplifier:255},6:{ShowParticles:0b,Duration:72000,Id:12,Amplifier:255},7:{ShowParticles:0b,Duration:72000,Id:13,Amplifier:255},8:{ShowParticles:0b,Duration:72000,Id:14,Amplifier:130},9:{ShowParticles:0b,Duration:72000,Id:16,Amplifier:255},10:{ShowParticles:0b,Duration:72000,Id:23,Amplifier:255}],HideFlags:63,Potion:\"minecraft:awkward\",display:{Name:\"§5§lGrief §d§lPotion\"}}"));
        final String[] array = { "1", "2", "3" };
        final Integer[] array2 = { 1, 2, 5, 127, 128, 254, 255 };
        final int[] array3 = { 0, 5, 60, 100000 };
        int[] array4;
        while (0 < (array4 = new int[] { 8193, 16385 }).length) {
            final int n = array4[0];
            int n2 = 0;
            ++n2;
        }
        MoodTabExploits.removeSuspiciousTags(list);
    }
    
    @Override
    public ItemStack getIconItemStack() {
        return ItemStackUtil.stringtostack("minecraft:potion 1 8262");
    }
    
    @Override
    public String getTranslatedTabLabel() {
        return "§cB\u00e1jitalok";
    }
    
    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
