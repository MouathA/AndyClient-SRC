package DTool.command.impl;

import DTool.command.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import java.util.*;

public class CopyItem extends Command
{
    public CopyItem() {
        super("CopyItem", "CopyItem", "CopyItem", new String[] { "CopyItem" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length != 2) {
            Segito.msg("§7Haszn\u00e1lat:§b -copyitem <jatekosneve>");
            Segito.msg("§b<targy/sisak/mellvert/labszarvedo/csizma>");
        }
        else {
            final Minecraft mc = CopyItem.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = CopyItem.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            ItemStack itemStack = null;
            final Minecraft mc3 = CopyItem.mc;
        Label_0326:
            for (final EntityOtherPlayerMP next : Minecraft.theWorld.loadedEntityList) {
                if (next instanceof EntityOtherPlayerMP) {
                    final EntityOtherPlayerMP entityOtherPlayerMP = next;
                    if (!entityOtherPlayerMP.getName().equalsIgnoreCase(array[0])) {
                        continue;
                    }
                    final String lowerCase;
                    switch ((lowerCase = array[1].toLowerCase()).hashCode()) {
                        case -1369120545: {
                            if (!lowerCase.equals("labszarvedo")) {
                                break Label_0326;
                            }
                            itemStack = entityOtherPlayerMP.inventory.armorItemInSlot(1);
                            break Label_0326;
                        }
                        case -1351227659: {
                            if (!lowerCase.equals("csizma")) {
                                break Label_0326;
                            }
                            itemStack = entityOtherPlayerMP.inventory.armorItemInSlot(0);
                            break Label_0326;
                        }
                        case -668338615: {
                            if (!lowerCase.equals("mellvert")) {
                                break Label_0326;
                            }
                            itemStack = entityOtherPlayerMP.inventory.armorItemInSlot(2);
                            break Label_0326;
                        }
                        case 109446599: {
                            if (!lowerCase.equals("sisak")) {
                                break Label_0326;
                            }
                            itemStack = entityOtherPlayerMP.inventory.armorItemInSlot(3);
                            break Label_0326;
                        }
                        case 110131031: {
                            if (!lowerCase.equals("targy")) {
                                break Label_0326;
                            }
                            itemStack = entityOtherPlayerMP.inventory.getCurrentItem();
                            break Label_0326;
                        }
                        default: {
                            break Label_0326;
                        }
                    }
                }
            }
            if (itemStack == null) {
                Segito.msg("§7Ilyen nev\u0171 j\u00e1t\u00e9kos: \"§b" + array[0] + "§7\"§7 nem tal\u00e1lhat\u00f3.");
            }
            else {
                Segito.msg("§7K\u00e9rlek, \u0171r\u00edts ki egy helyet a §bhotbarod§7-b\u00f3l.");
            }
        }
    }
}
