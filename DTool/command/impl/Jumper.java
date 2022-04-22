package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.awt.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import Mood.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class Jumper extends Command
{
    public static double x;
    public static double y;
    public static double z;
    public static double motionX;
    public static double motionY;
    public static double motionZ;
    public static boolean enabled;
    public static boolean emojik;
    public static Random rnd;
    public static String emojineve;
    
    static {
        Jumper.enabled = false;
        Jumper.emojik = false;
        Jumper.rnd = new Random();
        Jumper.emojineve = "Emoji";
    }
    
    public Jumper() {
        super("Jumper", "Jumper", "Jumper", new String[] { "Jumper" });
    }
    
    public static ItemStack cheatArmorstand(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7) {
        final ItemStack itemStack = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
        nbtTagCompound3.setInteger("color", new Color(Jumper.rnd.nextInt(255), Jumper.rnd.nextInt(255), Jumper.rnd.nextInt(255)).getRGB());
        nbtTagCompound2.setTag("display", nbtTagCompound3);
        final NBTTagList list = new NBTTagList();
        final NBTTagList list2 = new NBTTagList();
        final NBTTagList list3 = new NBTTagList();
        list.appendTag(new NBTTagDouble(n));
        list.appendTag(new NBTTagDouble(n2));
        list.appendTag(new NBTTagDouble(n3));
        list2.appendTag(new NBTTagDouble(n4));
        list2.appendTag(new NBTTagDouble(n5));
        list2.appendTag(new NBTTagDouble(n6));
        final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound5 = new NBTTagCompound();
        nbtTagCompound5.setString("id", "leather_boots");
        nbtTagCompound5.setByte("Count", (byte)1);
        final NBTTagCompound nbtTagCompound6 = new NBTTagCompound();
        nbtTagCompound6.setString("id", "leather_leggings");
        nbtTagCompound6.setByte("Count", (byte)1);
        final NBTTagCompound nbtTagCompound7 = new NBTTagCompound();
        nbtTagCompound7.setString("id", "leather_chestplate");
        nbtTagCompound7.setByte("Count", (byte)1);
        final NBTTagCompound nbtTagCompound8 = new NBTTagCompound();
        nbtTagCompound8.setString("id", "leather_helmet");
        nbtTagCompound8.setByte("Count", (byte)1);
        list3.appendTag(nbtTagCompound4);
        list3.appendTag(nbtTagCompound5);
        list3.appendTag(nbtTagCompound6);
        list3.appendTag(nbtTagCompound7);
        list3.appendTag(nbtTagCompound8);
        nbtTagCompound.setTag("Equipment", list3);
        nbtTagCompound.setTag("Pos", list);
        nbtTagCompound.setTag("Motion", list2);
        tagCompound.setTag("EntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    public static void emoji() {
        if (Jumper.emojik) {
            Minecraft.getMinecraft();
            Minecraft.rightClickDelayTimer = 0;
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionX = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionX = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionY = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionY = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionZ = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionZ = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            Jumper.motionX = Jumper.rnd.nextInt(50) / 10.0;
            Jumper.motionY = Jumper.rnd.nextInt(50) / 10.0;
            Jumper.motionZ = Jumper.rnd.nextInt(50) / 10.0;
            final Random random = new Random();
            final char[] array = { '1', '2', '3', '4', '5', '6', '7', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k' };
            HackedItemUtils.addItem(HackedItemUtils.createItem(Items.armor_stand, 1, 0, "{display:{Name:\"§r§lEmoji\"},EntityTag:{Pos:[" + Jumper.x + "," + Jumper.y + "," + Jumper.z + "],Motion:[" + Jumper.motionX + "," + Jumper.motionY + "," + Jumper.motionZ + "],Marker:1b,Invulnerable:1,NoBasePlate:1,Equipment:[{},{id:\"leather_boots\",Count:1b,tag:{display:{color:3158064}}},{id:\"leather_leggings\",Count:1b,tag:{display:{color:16252672}}},{id:\"leather_chestplate\",Count:1b,tag:{display:{color:16252672}}},{id:skull,Count:1,Damage:3,tag:{SkullOwner:{Id:\"2cd3dbb5-6136-4127-9e14-89c328660871\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM2ZTI2YzQ0NjU5ZTgxNDhlZDU4YWE3OWU0ZDYwZGI1OTVmNDI2NDQyMTE2ZjgxYjU0MTVjMjQ0NmVkOCJ9fX0=\"}]}}}}],CustomName:\"§" + array[random.nextInt(array.length)] + Jumper.emojineve + "\",CustomNameVisible:1b}}"));
            if (0 >= 18) {
                Jumper.Hint = 0;
            }
            Jumper.Hint = 1;
        }
    }
    
    public static void tick() {
        if (Jumper.enabled) {
            Minecraft.getMinecraft();
            Minecraft.rightClickDelayTimer = 0;
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionX = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionX = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionY = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionY = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            if (Jumper.rnd.nextInt(2) == 0) {
                Jumper.motionZ = Jumper.rnd.nextInt(50) / 10.0;
            }
            else {
                Jumper.motionZ = Jumper.rnd.nextInt(50) / 10 * -1.0;
            }
            Jumper.motionX = Jumper.rnd.nextInt(50) / 10.0;
            Jumper.motionY = Jumper.rnd.nextInt(50) / 10.0;
            Jumper.motionZ = Jumper.rnd.nextInt(50) / 10.0;
            HackedItemUtils.addItem(HackedItemUtils.createItem(Items.armor_stand, 1, 0, "{display:{Name:\"§6§lJumper\"},EntityTag:{Pos:[" + Jumper.x + "," + Jumper.y + "," + Jumper.z + "],Motion:[" + Jumper.motionX + "," + Jumper.motionY + "," + Jumper.motionZ + "],Marker:1b,Invulnerable:1,NoBasePlate:1,Equipment:[{},{id:leather_boots,Count:1,tag:{display:{Name:\"\",color:" + HackedItemUtils.discoarmor(0) + "}}},{id:leather_leggings,Count:1,tag:{display:{color:" + HackedItemUtils.discoarmor(0) + "}}},{id:leather_chestplate,Count:1,tag:{display:{color:" + HackedItemUtils.discoarmor(0) + "}}},{id:leather_helmet,Count:1,tag:{display:{color:" + HackedItemUtils.discoarmor(0) + "}}}],DisabledSlots:2039583}}"));
            if (0 >= 18) {
                Jumper.Hint = 0;
            }
            Jumper.Hint = 1;
        }
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -jumper <beallit/elindit/emoji/leallit>");
            return;
        }
        if (array[0].equalsIgnoreCase("elindit")) {
            final Minecraft mc = Jumper.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = Jumper.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                return;
            }
            Jumper.enabled = true;
            final Minecraft mc3 = Jumper.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§dElind\u00edtva!");
        }
        if (array[0].equalsIgnoreCase("leallit")) {
            final Minecraft mc4 = Jumper.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Jumper.enabled = false;
            Jumper.emojik = false;
            HackedItemUtils.addItem(null);
            Segito.msg("§7Le\u00e1ll\u00edtva!");
        }
        if (array[0].equalsIgnoreCase("beallit")) {
            final Minecraft mc5 = Jumper.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Minecraft.getMinecraft();
            Jumper.x = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            Jumper.y = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            Jumper.z = Minecraft.thePlayer.posZ;
            Segito.msg("§7A §eHologramok§7 enn\u00e9l a pontn\u00e1l fognak megjelenni!");
        }
        if (array[0].equalsIgnoreCase("emoji")) {
            final Minecraft mc6 = Jumper.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc7 = Jumper.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                return;
            }
            Jumper.emojik = true;
            final Minecraft mc8 = Jumper.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§dElind\u00edtva!");
        }
    }
}
