package net.minecraft.command.common;

import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandReplaceItem extends CommandBase
{
    private static final Map field_175785_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002340";
        field_175785_a = Maps.newHashMap();
        int n = 0;
        while (0 < 54) {
            CommandReplaceItem.field_175785_a.put("slot.container." + 0, 0);
            ++n;
        }
        while (0 < 9) {
            CommandReplaceItem.field_175785_a.put("slot.hotbar." + 0, 0);
            ++n;
        }
        while (0 < 27) {
            CommandReplaceItem.field_175785_a.put("slot.inventory." + 0, 9);
            ++n;
        }
        while (0 < 27) {
            CommandReplaceItem.field_175785_a.put("slot.enderchest." + 0, 200);
            ++n;
        }
        while (0 < 8) {
            CommandReplaceItem.field_175785_a.put("slot.villager." + 0, 300);
            ++n;
        }
        while (0 < 15) {
            CommandReplaceItem.field_175785_a.put("slot.horse." + 0, 500);
            ++n;
        }
        CommandReplaceItem.field_175785_a.put("slot.weapon", 99);
        CommandReplaceItem.field_175785_a.put("slot.armor.head", 103);
        CommandReplaceItem.field_175785_a.put("slot.armor.chest", 102);
        CommandReplaceItem.field_175785_a.put("slot.armor.legs", 101);
        CommandReplaceItem.field_175785_a.put("slot.armor.feet", 100);
        CommandReplaceItem.field_175785_a.put("slot.horse.saddle", 400);
        CommandReplaceItem.field_175785_a.put("slot.horse.armor", 401);
        CommandReplaceItem.field_175785_a.put("slot.horse.chest", 499);
    }
    
    @Override
    public String getCommandName() {
        return "replaceitem";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.replaceitem.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }
        if (!array[0].equals("entity")) {
            if (!array[0].equals("block")) {
                throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }
        }
        if (true) {
            if (array.length < 6) {
                throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }
        }
        else if (array.length < 4) {
            throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
        }
        final int func_175783_e = this.func_175783_e(array[2]);
        final Item itemByText = CommandBase.getItemByText(commandSender, array[3]);
        int n = 0;
        ++n;
        int int1;
        if (array.length > 3) {
            final int n2 = 3;
            ++n;
            int1 = CommandBase.parseInt(array[n2], 1, 64);
        }
        else {
            int1 = 1;
        }
        final int n3 = int1;
        int int2;
        if (array.length > 3) {
            final int n4 = 3;
            ++n;
            int2 = CommandBase.parseInt(array[n4]);
        }
        else {
            int2 = 0;
        }
        ItemStack itemStack = new ItemStack(itemByText, n3, int2);
        if (array.length > 3) {
            itemStack.setTagCompound(JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 3).getUnformattedText()));
        }
        if (itemStack.getItem() == null) {
            itemStack = null;
        }
        if (true) {
            commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 1, false);
            final TileEntity tileEntity = commandSender.getEntityWorld().getTileEntity(func_175757_a);
            if (tileEntity == null || !(tileEntity instanceof IInventory)) {
                throw new CommandException("commands.replaceitem.noContainer", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ() });
            }
            final IInventory inventory = (IInventory)tileEntity;
            if (func_175783_e >= 0 && func_175783_e < inventory.getSizeInventory()) {
                inventory.setInventorySlotContents(func_175783_e, itemStack);
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[1]);
            commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            if (func_175768_b instanceof EntityPlayer) {
                ((EntityPlayer)func_175768_b).inventoryContainer.detectAndSendChanges();
            }
            if (!func_175768_b.func_174820_d(func_175783_e, itemStack)) {
                throw new CommandException("commands.replaceitem.failed", new Object[] { func_175783_e, n3, (itemStack == null) ? "Air" : itemStack.getChatComponent() });
            }
            if (func_175768_b instanceof EntityPlayer) {
                ((EntityPlayer)func_175768_b).inventoryContainer.detectAndSendChanges();
            }
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, n3);
        CommandBase.notifyOperators(commandSender, this, "commands.replaceitem.success", func_175783_e, n3, (itemStack == null) ? "Air" : itemStack.getChatComponent());
    }
    
    private int func_175783_e(final String s) throws CommandException {
        if (!CommandReplaceItem.field_175785_a.containsKey(s)) {
            throw new CommandException("commands.generic.parameter.invalid", new Object[] { s });
        }
        return CommandReplaceItem.field_175785_a.get(s);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "entity", "block") : ((array.length == 2 && array[0].equals("entity")) ? CommandBase.getListOfStringsMatchingLastWord(array, this.func_175784_d()) : (((array.length != 3 || !array[0].equals("entity")) && (array.length != 5 || !array[0].equals("block"))) ? (((array.length != 4 || !array[0].equals("entity")) && (array.length != 6 || !array[0].equals("block"))) ? null : CommandBase.func_175762_a(array, Item.itemRegistry.getKeys())) : CommandBase.func_175762_a(array, CommandReplaceItem.field_175785_a.keySet())));
    }
    
    protected String[] func_175784_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return array.length > 0 && array[0].equals("entity") && n == 1;
    }
}
