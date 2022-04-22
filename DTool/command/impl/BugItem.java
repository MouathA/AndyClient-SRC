package DTool.command.impl;

import DTool.command.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class BugItem extends Command
{
    public BugItem() {
        super("BugItem", "BugItem", "BugItem", new String[] { "BugItem" });
    }
    
    public static ItemStack getBugItem(final String s) {
        final ItemStack itemStack = new ItemStack((Item)Item.itemRegistry.getObject(new ResourceLocation(s)));
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setByte("HideFlags", (byte)63);
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setDouble("Amount", Double.NaN);
        nbtTagCompound.setString("AttributeName", "generic.movementSpeed");
        nbtTagCompound.setString("Name", "GetRektM8");
        nbtTagCompound.setInteger("Operation", 0);
        nbtTagCompound.setString("Slot", "mainhand");
        nbtTagCompound.setInteger("UUIDLeast", 1);
        nbtTagCompound.setInteger("UUIDMost", 1);
        list.appendTag(nbtTagCompound);
        tagCompound.setTag("AttributeModifiers", list);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("K\u00e9rj\u00fck adjon meg egy§e Item§7-et!");
        }
        else {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, getBugItem(array[0])));
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
        }
    }
}
