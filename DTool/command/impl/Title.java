package DTool.command.impl;

import DTool.command.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import Mood.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Title extends Command
{
    public Title() {
        super("Title", "Title", "Title", new String[] { "Title" });
    }
    
    public static String fix(final String s) {
        return s.replace('&', '§').replace(">>", ">");
    }
    
    public static ItemStack getSpawnerFromDispenser(final ItemStack itemStack) {
        final ItemStack itemStack2 = new ItemStack(Blocks.dispenser);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setShort("BurnTime", (short)0);
        nbtTagCompound.setShort("CookTime", (short)0);
        nbtTagCompound.setShort("CookTimeTotal", (short)200);
        nbtTagCompound.setString("id", "Furnace");
        nbtTagCompound.setString("Lock", "");
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setByte("Count", (byte)1);
        nbtTagCompound2.setShort("Damage", (short)itemStack.getItemDamage());
        nbtTagCompound2.setString("id", "minecraft:mob_spawner");
        nbtTagCompound2.setShort("Slot", (short)0);
        nbtTagCompound2.setTag("tag", itemStack.getTagCompound());
        list.appendTag(nbtTagCompound2);
        nbtTagCompound.setTag("Items", list);
        tagCompound.setTag("BlockEntityTag", nbtTagCompound);
        itemStack2.setTagCompound(tagCompound);
        return itemStack2;
    }
    
    public static ItemStack generateTitle(final String s) {
        final ItemStack itemStack = new ItemStack(Blocks.mob_spawner);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("EntityId", "WitherBoss");
        nbtTagCompound.setInteger("spawnCount", 0);
        nbtTagCompound.setInteger("RequiredPlayerRange", Integer.MAX_VALUE);
        nbtTagCompound.setInteger("MinSpawnDelay", 5000);
        nbtTagCompound.setInteger("MaxSpawnDelay", 5000);
        nbtTagCompound.setInteger("MaxNearbyEntities", Integer.MAX_VALUE);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setString("CustomName", s);
        nbtTagCompound2.setInteger("CustomNameVisible", 1);
        final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
        nbtTagCompound3.setString("id", "Bat");
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
        nbtTagCompound4.setInteger("Id", 14);
        nbtTagCompound4.setInteger("Amplifier", 0);
        nbtTagCompound4.setInteger("Duration", 200000000);
        nbtTagCompound4.setByte("ShowParticles", (byte)0);
        list.appendTag(nbtTagCompound4);
        nbtTagCompound3.setTag("ActiveEffects", list);
        nbtTagCompound2.setTag("Riding", nbtTagCompound3);
        nbtTagCompound.setTag("SpawnData", nbtTagCompound2);
        tagCompound.setTag("BlockEntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("Haszn\u00e1lat: §b-title <sz\u00f6veg>");
        }
        if (array.length >= 1) {
            final Minecraft mc = Title.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc2 = Title.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            }
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            final Minecraft mc3 = Title.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, getSpawnerFromDispenser(generateTitle(fix(String.join(" ", (CharSequence[])array))))));
        }
    }
}
