package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandDeOp extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "deop";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.deop.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1 || array[0].length() <= 0) {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileFromName = server.getConfigurationManager().getOppedPlayers().getGameProfileFromName(array[0]);
        if (gameProfileFromName == null) {
            throw new CommandException("commands.deop.failed", new Object[] { array[0] });
        }
        server.getConfigurationManager().removeOp(gameProfileFromName);
        CommandBase.notifyOperators(commandSender, this, "commands.deop.success", array[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames()) : null;
    }
    
    static {
        __OBFID = "CL_00000244";
    }
}
