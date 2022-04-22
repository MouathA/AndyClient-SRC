package net.minecraft.command;

import java.util.*;
import net.minecraft.util.*;

public interface ICommand extends Comparable
{
    String getCommandName();
    
    String getCommandUsage(final ICommandSender p0);
    
    List getCommandAliases();
    
    void processCommand(final ICommandSender p0, final String[] p1) throws CommandException;
    
    boolean canCommandSenderUseCommand(final ICommandSender p0);
    
    List addTabCompletionOptions(final ICommandSender p0, final String[] p1, final BlockPos p2);
    
    boolean isUsernameIndex(final String[] p0, final int p1);
}
