package net.minecraft.command;

import net.minecraft.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandHelp extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "help";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.help.usage";
    }
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("?");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final List sortedPossibleCommands = this.getSortedPossibleCommands(commandSender);
        final int n = (sortedPossibleCommands.size() - 1) / 7;
        final int n2 = (array.length == 0) ? 0 : (CommandBase.parseInt(array[0], 1, n + 1) - 1);
        final int min = Math.min((n2 + 1) * 7, sortedPossibleCommands.size());
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.help.header", new Object[] { n2 + 1, n + 1 });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        for (int i = n2 * 7; i < min; ++i) {
            final ICommand command = sortedPossibleCommands.get(i);
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(command.getCommandUsage(commandSender), new Object[0]);
            chatComponentTranslation2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + command.getCommandName() + " "));
            commandSender.addChatMessage(chatComponentTranslation2);
        }
        if (n2 == 0 && commandSender instanceof EntityPlayer) {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.GREEN);
            commandSender.addChatMessage(chatComponentTranslation3);
        }
    }
    
    protected List getSortedPossibleCommands(final ICommandSender commandSender) {
        final List possibleCommands = MinecraftServer.getServer().getCommandManager().getPossibleCommands(commandSender);
        Collections.sort((List<Comparable>)possibleCommands);
        return possibleCommands;
    }
    
    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            final Set keySet = this.getCommands().keySet();
            return CommandBase.getListOfStringsMatchingLastWord(array, (String[])keySet.toArray(new String[keySet.size()]));
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00000529";
    }
}
