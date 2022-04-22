package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandListBans extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "banlist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()) && super.canCommandSenderUseCommand(commandSender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.banlist.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= 1 && array[0].equalsIgnoreCase("ips")) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", new Object[] { MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length }));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
        }
        else {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.players", new Object[] { MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys().length }));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "players", "ips") : null;
    }
    
    static {
        __OBFID = "CL_00000596";
    }
}
