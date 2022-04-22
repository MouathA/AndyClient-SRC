package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandServerKick extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "kick";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.kick.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0 || array[0].length() <= 1) {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
        final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(array[0]);
        String unformattedText = "Kicked by an operator.";
        if (playerByUsername == null) {
            throw new PlayerNotFoundException();
        }
        if (array.length >= 2) {
            unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 1).getUnformattedText();
        }
        playerByUsername.playerNetServerHandler.kickPlayerFromServer(unformattedText);
        if (true) {
            CommandBase.notifyOperators(commandSender, this, "commands.kick.success.reason", playerByUsername.getName(), unformattedText);
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.kick.success", playerByUsername.getName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    static {
        __OBFID = "CL_00000550";
    }
}
