package net.minecraft.command.server;

import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTestFor extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "testfor";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.testfor.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[0]);
        NBTBase func_180713_a = null;
        if (array.length >= 2) {
            func_180713_a = JsonToNBT.func_180713_a(CommandBase.func_180529_a(array, 1));
        }
        if (func_180713_a != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            func_175768_b.writeToNBT(nbtTagCompound);
            if (!CommandTestForBlock.func_175775_a(func_180713_a, nbtTagCompound, true)) {
                throw new CommandException("commands.testfor.failure", new Object[] { func_175768_b.getName() });
            }
        }
        CommandBase.notifyOperators(commandSender, this, "commands.testfor.success", func_175768_b.getName());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    static {
        __OBFID = "CL_00001182";
    }
}
