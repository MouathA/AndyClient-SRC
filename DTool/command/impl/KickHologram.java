package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import Mood.*;

public class KickHologram extends Command
{
    public KickHologram() {
        super("KickHologram", "KickHologram", "KickHologram", new String[] { "KickHologram" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Minecraft.getMinecraft();
        KickHologram.posX = (int)Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        KickHologram.posY = (int)Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        KickHologram.posZ = (int)Minecraft.thePlayer.posZ;
        HackedItemUtils.addItem(HackedItemUtils.createItem(Items.armor_stand, 1, 0, "{display:{Lore:[0:\"§6Poz\u00edci\u00f3§e X§7: §6" + 1 + "\",1:\"§6Poz\u00edci\u00f3§e Y§7: §6" + 1 + "\",2:\"§6Poz\u00edci\u00f3§e Z§7: §6" + 1 + "\"],Name:\"§cKick §7Hologram\"},EntityTag:{Pos:[" + 1 + ".0," + 1 + ".0," + 1 + ".0],Equipment:[0:{},1:{},2:{},3:{},4:{id:\"leather_helmet\",Count:1,tag:{display:{color:2147483647}}}]}}"));
        Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
        Segito.msg("A Hologram elhelyezked\u00e9se enn\u00e9l a pontn\u00e1l lesz!");
    }
}
