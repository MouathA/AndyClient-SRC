package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class ObjectLoader extends Command
{
    public static boolean enabled;
    public static ArrayList items;
    
    static {
        ObjectLoader.enabled = false;
        ObjectLoader.items = new ArrayList();
    }
    
    public ObjectLoader() {
        super("ObjectLoader", "ObjectLoader", "ObjectLoader", new String[] { "" });
    }
    
    public static void onTick() {
        if (ObjectLoader.enabled) {
            Minecraft.getMinecraft();
            Minecraft.rightClickDelayTimer = 0;
        }
    }
    
    public static void onPlace(final ItemStack itemStack) {
        Minecraft.getMinecraft();
        if (Minecraft.theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.func_178782_a()).getBlock().isFullBlock() && itemStack.getItem().equals(Items.armor_stand) && ObjectLoader.items.size() > 0) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, ObjectLoader.items.get(0)));
            ObjectLoader.items.remove(0);
            if (ObjectLoader.items.size() == 0) {
                Segito.msg("Az§e Objektum§7 sikeresen le lett id\u00e9zve!", true);
                HackedItemUtils.addItem(null);
                ObjectLoader.enabled = false;
            }
        }
    }
    
    public static void create(final String s) {
        ObjectLoader.items.clear();
        String s2;
        if (System.getProperty("os.name").toUpperCase().contains("WIN")) {
            s2 = System.getenv("AppData");
        }
        else {
            s2 = String.valueOf(String.valueOf(System.getProperty("user.home"))) + "/Library/Application Support";
        }
        final Scanner scanner = new Scanner(new File(String.valueOf(String.valueOf(s2)) + "/.minecraft/Creeper/Objekte/" + s + ".txt"));
        while (scanner.hasNextLine()) {
            final String replace = scanner.nextLine().replace("id:", "Count:1,id:").replace("Id:", "Count:1,Id:").replace("ArmorItems", "Equipment").replace("{},{},{},{", "{},{},{},{},{");
            if (replace.toUpperCase().startsWith("SUMMON")) {
                final String s3 = replace.split(" ")[1];
                final double doubleValue = Double.valueOf(replace.split(" ")[2].replace("~", ""));
                final double n = Double.valueOf(replace.split(" ")[3].replace("~", "")) + 3.0;
                final double doubleValue2 = Double.valueOf(replace.split(" ")[4].replace("~", ""));
                String s4 = "{";
                final String[] split = replace.split(Pattern.quote("{"));
                while (1 < split.length) {
                    if (split.length - 1 != 1) {
                        s4 = String.valueOf(String.valueOf(s4)) + split[1] + "{";
                    }
                    else {
                        s4 = String.valueOf(String.valueOf(s4)) + split[1];
                    }
                    int n2 = 0;
                    ++n2;
                }
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                ObjectLoader.items.add(createTeleportItem(doubleValue + Minecraft.thePlayer.posX, n + Minecraft.thePlayer.posY, doubleValue2 + Minecraft.thePlayer.posZ, s4));
                int n3 = 0;
                ++n3;
            }
        }
        Segito.msg("§7Az§e Objektum§7 enn\u00e9l a pontn\u00e1l fog megjelenni:", true);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Segito.msg("§8X" + (int)Minecraft.thePlayer.posX + " Y: " + (int)Minecraft.thePlayer.posY + " Z: " + (int)Minecraft.thePlayer.posZ, true);
        Segito.msg("§e" + 0 + " §7lerakand\u00f3 t\u00e1rgy maradt m\u00e9g!", true);
        scanner.close();
    }
    
    public static ItemStack createTeleportItem(final double n, final double n2, final double n3, final String s) {
        final ItemStack itemStack = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound func_180713_a = JsonToNBT.func_180713_a(s);
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(n));
        list.appendTag(new NBTTagDouble(n2));
        list.appendTag(new NBTTagDouble(n3));
        func_180713_a.setTag("Pos", list);
        func_180713_a.setInteger("Invisible", 1);
        func_180713_a.setDouble("Rotation", 0.0);
        tagCompound.setTag("EntityTag", func_180713_a);
        itemStack.setTagCompound(tagCompound);
        itemStack.setStackDisplayName("§cObjektum:§a " + ObjectLoader.items.size());
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Segito.msg("§7Haszn\u00e1lat:§b -objectloader betolt (f\u00e1jl neve)");
        if (array[0].equalsIgnoreCase("betolt")) {
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            ObjectLoader.enabled = true;
            create(array[1]);
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, createTeleportItem(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, "{Small:1,DisabledSlots:1,Invisible:1,NoGravity:1b,ArmorItems:[{},{},{},{id:coal_block,Count:1b}]}")));
        }
    }
}
