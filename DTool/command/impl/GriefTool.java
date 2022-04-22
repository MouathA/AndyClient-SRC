package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.init.*;
import Mood.*;
import net.minecraft.item.*;

public class GriefTool extends Command
{
    public static boolean enabled;
    public static String text;
    
    static {
        GriefTool.enabled = false;
        GriefTool.text = "";
    }
    
    public GriefTool() {
        super("GriefTool", "GriefTool", "GriefTool", new String[] { "GriefTool" });
    }
    
    public static void tick() {
        final Random random = new Random();
        final char[] array = { '1', '2', '3', '4', '5', '6', '7', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k' };
        final char c = array[random.nextInt(array.length)];
        if (GriefTool.enabled) {
            Minecraft.thePlayer.swingItem();
            Minecraft.thePlayer.sendChatMessage("/bc &" + c + "&l" + GriefTool.text);
        }
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("Haszn\u00e1lat:§b -grieftool <elokeszit/szoveg/elindit/leallit>");
            return;
        }
        if (array[0].equalsIgnoreCase("elokeszit")) {
            final Minecraft mc = GriefTool.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = GriefTool.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                return;
            }
            HackedItemUtils.addItem(HackedItemUtils.createItem(Items.diamond_pickaxe, 1, 0, "{ench:[0:{lvl:0,id:16},1:{lvl:32767,id:21},2:{lvl:32767,id:32},3:{lvl:32767,id:35}],Unbreakable:1,HideFlags:63,display:{Name:\"\u0102\u201a\u00c2§5\u0102\u201a\u00c2§lGrief\u0102\u201a\u00c2§d\u0102\u201a\u00c2§lTool\"}}"));
            final Minecraft mc3 = GriefTool.mc;
            Minecraft.thePlayer.sendChatMessage("/ept fill ~-8 ~-8 ~-8 ~8 ~8 ~8 air");
        }
        if (array[0].equalsIgnoreCase("szoveg")) {
            String string = "";
            while (1 < array.length) {
                string = String.valueOf(String.valueOf(string)) + array[1] + " ";
                int n = 0;
                ++n;
            }
            GriefTool.text = string.replace("", "");
            final Minecraft mc4 = GriefTool.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("A Sz\u00f6veg sikeresen be lett \u00e1ll\u00edtva!");
        }
        if (array[0].equalsIgnoreCase("elindit")) {
            final Minecraft mc5 = GriefTool.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc6 = GriefTool.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                return;
            }
            GriefTool.enabled = true;
            final Minecraft mc7 = GriefTool.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("Kezd\u0151dhet is a puszt\u00edt\u00e1s!");
        }
        if (array[0].equalsIgnoreCase("leallit")) {
            GriefTool.enabled = false;
            HackedItemUtils.addItem(null);
            final Minecraft mc8 = GriefTool.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("Sikeresen le\u00e1ll\u00edtottad a folyamatot!");
        }
    }
}
