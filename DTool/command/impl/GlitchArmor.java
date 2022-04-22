package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import DTool.util.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.item.*;

public class GlitchArmor extends Command
{
    public static Random rnd;
    public static boolean enabled;
    public static boolean GlitchChestplate;
    private static final Timer idozito;
    public static boolean Sisak;
    public static boolean Sisak2;
    public static boolean Sisak3;
    public static boolean Sisak4;
    public static boolean Sisak5;
    public static boolean Sisak6;
    public static boolean Sisak7;
    public static boolean Sisak8;
    public static boolean Sisak9;
    static int current;
    
    static {
        GlitchArmor.rnd = new Random();
        GlitchArmor.enabled = false;
        GlitchArmor.GlitchChestplate = true;
        idozito = new Timer();
        GlitchArmor.Sisak = true;
        GlitchArmor.Sisak2 = true;
        GlitchArmor.Sisak3 = true;
        GlitchArmor.Sisak4 = true;
        GlitchArmor.Sisak5 = true;
        GlitchArmor.Sisak6 = true;
        GlitchArmor.Sisak7 = true;
        GlitchArmor.Sisak8 = true;
        GlitchArmor.Sisak9 = true;
    }
    
    public GlitchArmor() {
        super("GlitchArmor", "GlitchArmor", "GlitchArmor", new String[] { "GlitchArmor" });
    }
    
    public static void tick() {
        if (GlitchArmor.enabled) {
            final int rgb = new Color(GlitchArmor.rnd.nextInt(255), GlitchArmor.rnd.nextInt(255), GlitchArmor.rnd.nextInt(255)).getRGB();
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.capabilities.isCreativeMode) {
                if (GlitchArmor.current >= 18) {
                    GlitchArmor.current = 0;
                }
                if (GlitchArmor.Sisak) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Scary Steve (upside down)\"},SkullOwner:{Id:\"9a960f44-b17a-47ed-b19f-bb21383123c3\",Properties:{textures:[{Value:\"\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(50.0f) && GlitchArmor.Sisak2) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Fast Alex\"},SkullOwner:{Id:\"5889c98e-fce9-4fe2-bcc5-e425b65d8ddf\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.GlitchChestplate) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(6, HackedItemUtils.createItem(Items.leather_chestplate, 1, 0, "{HideFlags:2,display:{Name:\"\",color:" + rgb + "}}")));
                }
                if (GlitchArmor.idozito.delay(100.0f) && GlitchArmor.Sisak3) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Walter Monster\"},SkullOwner:{Id:\"b4fdfed3-d505-4748-98de-28fa13df82bf\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak4) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Dr. Creeper\"},SkullOwner:{Id:\"23d9501c-1498-4ce2-9ac5-b407fdef3ac4\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(140.0f) && GlitchArmor.Sisak5) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Neon Cyan Ender Dragon\"},SkullOwner:{Id:\"1a969928-7d1c-4602-a7fc-807f504ab4f9\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak6) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Flesh Monster\"},SkullOwner:{Id:\"80b4f92f-cd8f-4c9b-899e-90b8e67e9647\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(70.0f) && GlitchArmor.Sisak7) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Zombie Pigman\"},SkullOwner:{Id:\"fdeb5f5e-c498-4d53-80b3-25e430513db2\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(50.0f) && GlitchArmor.Sisak8) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Eater of Worlds\"},SkullOwner:{Id:\"2c24cee8-bec9-476c-aa7f-3533148a6841\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak9) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Dr. Creeper\"},SkullOwner:{Id:\"23d9501c-1498-4ce2-9ac5-b407fdef3ac4\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(100.0f) && GlitchArmor.Sisak3) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Walter Monster\"},SkullOwner:{Id:\"b4fdfed3-d505-4748-98de-28fa13df82bf\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak4) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Dr. Creeper\"},SkullOwner:{Id:\"23d9501c-1498-4ce2-9ac5-b407fdef3ac4\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(140.0f) && GlitchArmor.Sisak5) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Neon Cyan Ender Dragon\"},SkullOwner:{Id:\"1a969928-7d1c-4602-a7fc-807f504ab4f9\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak6) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Flesh Monster\"},SkullOwner:{Id:\"80b4f92f-cd8f-4c9b-899e-90b8e67e9647\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(70.0f) && GlitchArmor.Sisak7) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Zombie Pigman\"},SkullOwner:{Id:\"fdeb5f5e-c498-4d53-80b3-25e430513db2\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(50.0f) && GlitchArmor.Sisak8) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Eater of Worlds\"},SkullOwner:{Id:\"2c24cee8-bec9-476c-aa7f-3533148a6841\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                if (GlitchArmor.idozito.delay(120.0f) && GlitchArmor.Sisak9) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, HackedItemUtils.createItem(Items.skull, 1, 3, "{display:{Name:\"Dr. Creeper\"},SkullOwner:{Id:\"23d9501c-1498-4ce2-9ac5-b407fdef3ac4\",Properties:{textures:[{Value:\"" + HackedItemUtils.getRandomText() + "\"}]}}}")));
                }
                ++GlitchArmor.current;
            }
            else {
                Segito.msg("§7Ezt csak Kreat\u00edvm\u00f3dban haszn\u00e1lhatod!");
            }
        }
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -glitcharmor <bekapcs/kikapcs>");
            return;
        }
        if (array[0].equalsIgnoreCase("bekapcs")) {
            GlitchArmor.enabled = true;
            final Minecraft mc = GlitchArmor.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§eSikeresen bekapcsoltad ezt a funkci\u00f3t!");
        }
        if (array[0].equalsIgnoreCase("kikapcs")) {
            final Minecraft mc2 = GlitchArmor.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            GlitchArmor.enabled = false;
            if (GlitchArmor.Sisak) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak2) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak3) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak4) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak5) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak6) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak7) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            if (GlitchArmor.Sisak8) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(5, null));
            }
            Segito.msg("§7Sikeresen le\u00e1ll\u00edtottad ezt a funkci\u00f3t!");
        }
    }
}
