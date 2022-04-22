package net.minecraft.command.server;

import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.server.*;

public class CommandMessage extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("w", "msg");
    }
    
    @Override
    public String getCommandName() {
        return "tell";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.message.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.message.usage", new Object[0]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        if (player == commandSender) {
            throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
        }
        final IChatComponent chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, 1, !(commandSender instanceof EntityPlayer));
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { commandSender.getDisplayName(), chatComponentFromNthArg.createCopy() });
        final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { player.getDisplayName(), chatComponentFromNthArg.createCopy() });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        player.addChatMessage(chatComponentTranslation);
        commandSender.addChatMessage(chatComponentTranslation2);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00000641";
    }
}
