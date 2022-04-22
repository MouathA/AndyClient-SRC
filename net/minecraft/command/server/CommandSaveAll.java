package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandSaveAll extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "save-all";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.save.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MinecraftServer server = MinecraftServer.getServer();
        commandSender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (server.getConfigurationManager() != null) {
            server.getConfigurationManager().saveAllPlayerData();
        }
        int n = 0;
        while (0 < server.worldServers.length) {
            if (server.worldServers[0] != null) {
                final WorldServer worldServer = server.worldServers[0];
                final boolean disableLevelSaving = worldServer.disableLevelSaving;
                worldServer.disableLevelSaving = false;
                worldServer.saveAllChunks(true, null);
                worldServer.disableLevelSaving = disableLevelSaving;
            }
            ++n;
        }
        if (array.length > 0 && "flush".equals(array[0])) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
            while (0 < server.worldServers.length) {
                if (server.worldServers[0] != null) {
                    final WorldServer worldServer2 = server.worldServers[0];
                    final boolean disableLevelSaving2 = worldServer2.disableLevelSaving;
                    worldServer2.disableLevelSaving = false;
                    worldServer2.saveChunkData();
                    worldServer2.disableLevelSaving = disableLevelSaving2;
                }
                ++n;
            }
            commandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
        }
        CommandBase.notifyOperators(commandSender, this, "commands.save.success", new Object[0]);
    }
    
    static {
        __OBFID = "CL_00000826";
    }
}
