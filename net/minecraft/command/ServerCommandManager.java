package net.minecraft.command;

import net.minecraft.command.common.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.server.*;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    private static final String __OBFID;
    
    public ServerCommandManager() {
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandDifficulty());
        this.registerCommand(new CommandDefaultGameMode());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandWeather());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandTeleport());
        this.registerCommand(new CommandGive());
        this.registerCommand(new CommandReplaceItem());
        this.registerCommand(new CommandStats());
        this.registerCommand(new CommandEffect());
        this.registerCommand(new CommandEnchant());
        this.registerCommand(new CommandParticle());
        this.registerCommand(new CommandEmote());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandDebug());
        this.registerCommand(new CommandMessage());
        this.registerCommand(new CommandBroadcast());
        this.registerCommand(new CommandSetSpawnpoint());
        this.registerCommand(new CommandSetDefaultSpawnpoint());
        this.registerCommand(new CommandGameRule());
        this.registerCommand(new CommandClearInventory());
        this.registerCommand(new CommandTestFor());
        this.registerCommand(new CommandSpreadPlayers());
        this.registerCommand(new CommandPlaySound());
        this.registerCommand(new CommandScoreboard());
        this.registerCommand(new CommandExecuteAt());
        this.registerCommand(new CommandTrigger());
        this.registerCommand(new CommandAchievement());
        this.registerCommand(new CommandSummon());
        this.registerCommand(new CommandSetBlock());
        this.registerCommand(new CommandFill());
        this.registerCommand(new CommandClone());
        this.registerCommand(new CommandCompare());
        this.registerCommand(new CommandBlockData());
        this.registerCommand(new CommandTestForBlock());
        this.registerCommand(new CommandMessageRaw());
        this.registerCommand(new CommandWorldBorder());
        this.registerCommand(new CommandTitle());
        this.registerCommand(new CommandEntityData());
        if (MinecraftServer.getServer().isDedicatedServer()) {
            this.registerCommand(new CommandOp());
            this.registerCommand(new CommandDeOp());
            this.registerCommand(new CommandStop());
            this.registerCommand(new CommandSaveAll());
            this.registerCommand(new CommandSaveOff());
            this.registerCommand(new CommandSaveOn());
            this.registerCommand(new CommandBanIp());
            this.registerCommand(new CommandPardonIp());
            this.registerCommand(new CommandBanPlayer());
            this.registerCommand(new CommandListBans());
            this.registerCommand(new CommandPardonPlayer());
            this.registerCommand(new CommandServerKick());
            this.registerCommand(new CommandListPlayers());
            this.registerCommand(new CommandWhitelist());
            this.registerCommand(new CommandSetPlayerTimeout());
        }
        else {
            this.registerCommand(new CommandPublishLocalServer());
        }
        CommandBase.setAdminCommander(this);
    }
    
    @Override
    public void notifyOperators(final ICommandSender commandSender, final ICommand command, final int n, final String s, final Object... array) {
        final MinecraftServer server = MinecraftServer.getServer();
        if (!commandSender.sendCommandFeedback()) {}
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.admin", new Object[] { commandSender.getName(), new ChatComponentTranslation(s, array) });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
        chatComponentTranslation.getChatStyle().setItalic(true);
        if (commandSender != server && server.worldServers[0].getGameRules().getGameRuleBooleanValue("logAdminCommands")) {
            server.addChatMessage(chatComponentTranslation);
        }
        boolean b = server.worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
        if (commandSender instanceof CommandBlockLogic) {
            b = ((CommandBlockLogic)commandSender).func_175571_m();
        }
        if ((n & 0x1) != 0x1 && b) {
            commandSender.addChatMessage(new ChatComponentTranslation(s, array));
        }
    }
    
    static {
        __OBFID = "CL_00000922";
    }
}
