package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandWhitelist extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "whitelist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.whitelist.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        if (array[0].equals("on")) {
            server.getConfigurationManager().setWhiteListEnabled(true);
            CommandBase.notifyOperators(commandSender, this, "commands.whitelist.enabled", new Object[0]);
        }
        else if (array[0].equals("off")) {
            server.getConfigurationManager().setWhiteListEnabled(false);
            CommandBase.notifyOperators(commandSender, this, "commands.whitelist.disabled", new Object[0]);
        }
        else if (array[0].equals("list")) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.whitelist.list", new Object[] { server.getConfigurationManager().getWhitelistedPlayerNames().length, server.getConfigurationManager().getAvailablePlayerDat().length }));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(server.getConfigurationManager().getWhitelistedPlayerNames())));
        }
        else if (array[0].equals("add")) {
            if (array.length < 2) {
                throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
            }
            final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array[1]);
            if (gameProfileForUsername == null) {
                throw new CommandException("commands.whitelist.add.failed", new Object[] { array[1] });
            }
            server.getConfigurationManager().addWhitelistedPlayer(gameProfileForUsername);
            CommandBase.notifyOperators(commandSender, this, "commands.whitelist.add.success", array[1]);
        }
        else if (array[0].equals("remove")) {
            if (array.length < 2) {
                throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
            }
            final GameProfile func_152706_a = server.getConfigurationManager().getWhitelistedPlayers().func_152706_a(array[1]);
            if (func_152706_a == null) {
                throw new CommandException("commands.whitelist.remove.failed", new Object[] { array[1] });
            }
            server.getConfigurationManager().removePlayerFromWhitelist(func_152706_a);
            CommandBase.notifyOperators(commandSender, this, "commands.whitelist.remove.success", array[1]);
        }
        else if (array[0].equals("reload")) {
            server.getConfigurationManager().loadWhiteList();
            CommandBase.notifyOperators(commandSender, this, "commands.whitelist.reloaded", new Object[0]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "on", "off", "list", "add", "remove", "reload");
        }
        if (array.length == 2) {
            if (array[0].equals("remove")) {
                return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
            }
            if (array[0].equals("add")) {
                return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getPlayerProfileCache().func_152654_a());
            }
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001186";
    }
}
