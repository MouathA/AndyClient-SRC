package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandSetSpawnpoint extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "spawnpoint";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.spawnpoint.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length > 0 && array.length < 4) {
            throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
        }
        final EntityPlayerMP entityPlayerMP = (array.length > 0) ? CommandBase.getPlayer(commandSender, array[0]) : CommandBase.getCommandSenderAsPlayer(commandSender);
        final BlockPos blockPos = (array.length > 3) ? CommandBase.func_175757_a(commandSender, array, 1, true) : entityPlayerMP.getPosition();
        if (entityPlayerMP.worldObj != null) {
            entityPlayerMP.func_180473_a(blockPos, true);
            CommandBase.notifyOperators(commandSender, this, "commands.spawnpoint.success", entityPlayerMP.getName(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : null);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00001026";
    }
}
