package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandPardonIp extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "pardon-ip";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(commandSender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.unbanip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1 || array[0].length() <= 1) {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        if (CommandBanIp.field_147211_a.matcher(array[0]).matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(array[0]);
            CommandBase.notifyOperators(commandSender, this, "commands.unbanip.success", array[0]);
            return;
        }
        throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
    }
    
    static {
        __OBFID = "CL_00000720";
    }
}
