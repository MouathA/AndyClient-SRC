package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandClearInventory extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "clear";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.clear.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final EntityPlayerMP entityPlayerMP = (array.length == 0) ? CommandBase.getCommandSenderAsPlayer(commandSender) : CommandBase.getPlayer(commandSender, array[0]);
        final Item item = (array.length >= 2) ? CommandBase.getItemByText(commandSender, array[1]) : null;
        final int n = (array.length >= 3) ? CommandBase.parseInt(array[2], -1) : -1;
        final int n2 = (array.length >= 4) ? CommandBase.parseInt(array[3], -1) : -1;
        NBTTagCompound func_180713_a = null;
        if (array.length >= 5) {
            func_180713_a = JsonToNBT.func_180713_a(CommandBase.func_180529_a(array, 4));
        }
        if (array.length >= 2 && item == null) {
            throw new CommandException("commands.clear.failure", new Object[] { entityPlayerMP.getName() });
        }
        final int func_174925_a = entityPlayerMP.inventory.func_174925_a(item, n, n2, func_180713_a);
        entityPlayerMP.inventoryContainer.detectAndSendChanges();
        if (!entityPlayerMP.capabilities.isCreativeMode) {
            entityPlayerMP.updateHeldItem();
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, func_174925_a);
        if (func_174925_a == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { entityPlayerMP.getName() });
        }
        if (n2 == 0) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.clear.testing", new Object[] { entityPlayerMP.getName(), func_174925_a }));
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.clear.success", entityPlayerMP.getName(), func_174925_a);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.func_147209_d()) : ((array.length == 2) ? CommandBase.func_175762_a(array, Item.itemRegistry.getKeys()) : null);
    }
    
    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00000218";
    }
}
