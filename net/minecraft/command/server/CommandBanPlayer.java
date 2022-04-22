package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandBanPlayer extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "ban";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.ban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(commandSender);
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1 || array[0].length() <= 0) {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array[0]);
        if (gameProfileForUsername == null) {
            throw new CommandException("commands.ban.failed", new Object[] { array[0] });
        }
        String unformattedText = null;
        if (array.length >= 2) {
            unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 1).getUnformattedText();
        }
        server.getConfigurationManager().getBannedPlayers().addEntry(new UserListBansEntry(gameProfileForUsername, null, commandSender.getName(), null, unformattedText));
        final EntityPlayerMP playerByUsername = server.getConfigurationManager().getPlayerByUsername(array[0]);
        if (playerByUsername != null) {
            playerByUsername.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
        }
        CommandBase.notifyOperators(commandSender, this, "commands.ban.success", array[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    static {
        __OBFID = "CL_00000165";
    }
}
