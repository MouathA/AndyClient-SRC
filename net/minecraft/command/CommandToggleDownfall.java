package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;

public class CommandToggleDownfall extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "toggledownfall";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.downfall.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        this.toggleDownfall();
        CommandBase.notifyOperators(commandSender, this, "commands.downfall.success", new Object[0]);
    }
    
    protected void toggleDownfall() {
        final WorldInfo worldInfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
        worldInfo.setRaining(!worldInfo.isRaining());
    }
    
    static {
        __OBFID = "CL_00001184";
    }
}
