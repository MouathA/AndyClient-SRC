package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandBroadcast extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "say";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.say.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length > 0 && array[0].length() > 0) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.announcement", new Object[] { commandSender.getDisplayName(), CommandBase.getChatComponentFromNthArg(commandSender, array, 0, true) }));
            return;
        }
        throw new WrongUsageException("commands.say.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    static {
        __OBFID = "CL_00000191";
    }
}
