package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandSaveOff extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "save-off";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.save-off.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MinecraftServer server = MinecraftServer.getServer();
        while (0 < server.worldServers.length) {
            if (server.worldServers[0] != null) {
                final WorldServer worldServer = server.worldServers[0];
                if (!worldServer.disableLevelSaving) {
                    worldServer.disableLevelSaving = true;
                }
            }
            int n = 0;
            ++n;
        }
        CommandBase.notifyOperators(commandSender, this, "commands.save.disabled", new Object[0]);
    }
    
    static {
        __OBFID = "CL_00000847";
    }
}
