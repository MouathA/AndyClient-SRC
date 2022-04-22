package DTool.command.impl;

import DTool.command.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Skull extends Command
{
    public Skull() {
        super("Skull", "Skull", "Skull", new String[] { "Skull" });
    }
    
    public static ItemStack getPlayerSkull(final String s) {
        final ItemStack itemStack = new ItemStack(Items.skull, 1, 3);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("SkullOwner", s);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("K\u00e9rlek add meg a j\u00e1t\u00e9kosnev\u00e9t!");
        }
        if (array.length == 1) {
            final Minecraft mc = Skull.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, getPlayerSkull(array[0])));
            Segito.msg("leh\u00edvtad ennek a j\u00e1t\u00e9kosnak a fej\u00e9t:§b " + array[0]);
        }
    }
}
