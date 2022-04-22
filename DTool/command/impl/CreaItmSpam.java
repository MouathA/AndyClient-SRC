package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import java.util.*;
import Mood.*;
import net.minecraft.nbt.*;

public class CreaItmSpam extends Command
{
    public CreaItmSpam() {
        super("CreaItmSpam", "CreaItmSpam", "CreaItmSpam", new String[] { "CreaItmSpam" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        final Minecraft mc = CreaItmSpam.mc;
        if (Minecraft.playerController.isNotCreative()) {
            Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
            return;
        }
        Segito.msg("A laggoltat\u00e1s megkezd\u0151d\u00f6tt...");
        HackedItemUtils.createItem(Items.banner, 1, new Random().nextInt(15), "").setTagCompound(new NBTTagCompound());
    }
}
