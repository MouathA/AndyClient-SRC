package DTool.command.impl;

import DTool.command.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;
import net.minecraft.client.network.*;

public class Hologram extends Command
{
    public Hologram() {
        super("Hologram", "Hologram", "Hologram", new String[] { "Hologram" });
    }
    
    public static String fix(final String s) {
        return s.replace('&', '§').replace(">>", ">");
    }
    
    public ItemStack createHologram(final String s, final double n, final double n2, final double n3) {
        final ItemStack itemStack = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("Invisible", 1);
        nbtTagCompound.setString("CustomName", s);
        nbtTagCompound.setInteger("CustomNameVisible", 1);
        nbtTagCompound.setInteger("NoGravity", 1);
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(n));
        list.appendTag(new NBTTagDouble(n2));
        list.appendTag(new NBTTagDouble(n3));
        nbtTagCompound.setTag("Pos", list);
        tagCompound.setTag("EntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 10) {
            Minecraft.getMinecraft();
            final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
            final int n = 36;
            final String join = String.join(" ", (CharSequence[])array);
            Minecraft.getMinecraft();
            final double posX = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            final double posY = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(n, this.createHologram(join, posX, posY, Minecraft.thePlayer.posZ)));
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Segito.msg("A Hologram elhelyezked\u00e9se enn\u00e9l a pontn\u00e1l lesz!");
        }
    }
}
