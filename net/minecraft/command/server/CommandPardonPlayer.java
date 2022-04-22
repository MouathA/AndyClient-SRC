package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandPardonPlayer extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "pardon";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.unban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(commandSender);
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1 || array[0].length() <= 0) {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile usernameBanned = server.getConfigurationManager().getBannedPlayers().isUsernameBanned(array[0]);
        if (usernameBanned == null) {
            throw new CommandException("commands.unban.failed", new Object[] { array[0] });
        }
        server.getConfigurationManager().getBannedPlayers().removeEntry(usernameBanned);
        CommandBase.notifyOperators(commandSender, this, "commands.unban.success", array[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()) : null;
    }
    
    static {
        __OBFID = "CL_00000747";
    }
}
