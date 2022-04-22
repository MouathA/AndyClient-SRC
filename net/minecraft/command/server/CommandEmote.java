package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandEmote extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "me";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.me.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.emote", new Object[] { commandSender.getDisplayName(), CommandBase.getChatComponentFromNthArg(commandSender, array, 0, !(commandSender instanceof EntityPlayer)) }));
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
    
    static {
        __OBFID = "CL_00000351";
    }
}
