package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandGameMode extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "gamemode";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.gamemode.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }
        final WorldSettings.GameType gameModeFromCommand = this.getGameModeFromCommand(commandSender, array[0]);
        final EntityPlayerMP entityPlayerMP = (array.length >= 2) ? CommandBase.getPlayer(commandSender, array[1]) : CommandBase.getCommandSenderAsPlayer(commandSender);
        entityPlayerMP.setGameType(gameModeFromCommand);
        entityPlayerMP.fallDistance = 0.0f;
        if (commandSender.getEntityWorld().getGameRules().getGameRuleBooleanValue("sendCommandFeedback")) {
            entityPlayerMP.addChatMessage(new ChatComponentTranslation("gameMode.changed", new Object[0]));
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("gameMode." + gameModeFromCommand.getName(), new Object[0]);
        if (entityPlayerMP != commandSender) {
            CommandBase.notifyOperators(commandSender, this, 1, "commands.gamemode.success.other", entityPlayerMP.getName(), chatComponentTranslation);
        }
        else {
            CommandBase.notifyOperators(commandSender, this, 1, "commands.gamemode.success.self", chatComponentTranslation);
        }
    }
    
    protected WorldSettings.GameType getGameModeFromCommand(final ICommandSender commandSender, final String s) throws CommandException {
        return (!s.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !s.equalsIgnoreCase("s")) ? ((!s.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) && !s.equalsIgnoreCase("c")) ? ((!s.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) && !s.equalsIgnoreCase("a")) ? ((!s.equalsIgnoreCase(WorldSettings.GameType.SPECTATOR.getName()) && !s.equalsIgnoreCase("sp")) ? WorldSettings.getGameTypeById(CommandBase.parseInt(s, 0, WorldSettings.GameType.values().length - 2)) : WorldSettings.GameType.SPECTATOR) : WorldSettings.GameType.ADVENTURE) : WorldSettings.GameType.CREATIVE) : WorldSettings.GameType.SURVIVAL;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "survival", "creative", "adventure", "spectator") : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getListOfPlayerUsernames()) : null);
    }
    
    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 1;
    }
    
    static {
        __OBFID = "CL_00000448";
    }
}
