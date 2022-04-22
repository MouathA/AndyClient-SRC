package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;

public class CommandStop extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "stop";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.stop.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (MinecraftServer.getServer().worldServers != null) {
            CommandBase.notifyOperators(commandSender, this, "commands.stop.start", new Object[0]);
        }
        MinecraftServer.getServer().initiateShutdown();
    }
    
    static {
        __OBFID = "CL_00001132";
    }
}
