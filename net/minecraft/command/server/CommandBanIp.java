package net.minecraft.command.server;

import java.util.regex.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandBanIp extends CommandBase
{
    public static final Pattern field_147211_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000139";
        field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
    
    @Override
    public String getCommandName() {
        return "ban-ip";
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
        return "commands.banip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= 1 && array[0].length() > 1) {
            final IChatComponent chatComponent = (array.length >= 2) ? CommandBase.getChatComponentFromNthArg(commandSender, array, 1) : null;
            if (CommandBanIp.field_147211_a.matcher(array[0]).matches()) {
                this.func_147210_a(commandSender, array[0], (chatComponent == null) ? null : chatComponent.getUnformattedText());
            }
            else {
                final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(array[0]);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.func_147210_a(commandSender, playerByUsername.getPlayerIP(), (chatComponent == null) ? null : chatComponent.getUnformattedText());
            }
            return;
        }
        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    protected void func_147210_a(final ICommandSender commandSender, final String s, final String s2) {
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry(new IPBanEntry(s, null, commandSender.getName(), null, s2));
        final List playersMatchingAddress = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(s);
        final String[] array = new String[playersMatchingAddress.size()];
        for (final EntityPlayerMP entityPlayerMP : playersMatchingAddress) {
            entityPlayerMP.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = entityPlayerMP.getName();
        }
        if (playersMatchingAddress.isEmpty()) {
            CommandBase.notifyOperators(commandSender, this, "commands.banip.success", s);
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.banip.success.players", s, CommandBase.joinNiceString(array));
        }
    }
}
