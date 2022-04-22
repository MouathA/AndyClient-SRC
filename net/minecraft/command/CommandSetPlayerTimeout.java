package net.minecraft.command;

import net.minecraft.server.*;

public class CommandSetPlayerTimeout extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "setidletimeout";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setidletimeout.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1) {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
        final int int1 = CommandBase.parseInt(array[0], 0);
        MinecraftServer.getServer().setPlayerIdleTimeout(int1);
        CommandBase.notifyOperators(commandSender, this, "commands.setidletimeout.success", int1);
    }
    
    static {
        __OBFID = "CL_00000999";
    }
}
