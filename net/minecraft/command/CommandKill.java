package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class CommandKill extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "kill";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.kill.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length == 0) {
            final EntityPlayerMP commandSenderAsPlayer = CommandBase.getCommandSenderAsPlayer(commandSender);
            commandSenderAsPlayer.func_174812_G();
            CommandBase.notifyOperators(commandSender, this, "commands.kill.successful", commandSenderAsPlayer.getDisplayName());
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[0]);
            func_175768_b.func_174812_G();
            CommandBase.notifyOperators(commandSender, this, "commands.kill.successful", func_175768_b.getDisplayName());
        }
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00000570";
    }
}
