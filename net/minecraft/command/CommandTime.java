package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.world.*;

public class CommandTime extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "time";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.time.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length > 1) {
            if (array[0].equals("set")) {
                if (!array[1].equals("day")) {
                    if (!array[1].equals("night")) {
                        CommandBase.parseInt(array[1], 0);
                    }
                }
                this.setTime(commandSender, 13000);
                CommandBase.notifyOperators(commandSender, this, "commands.time.set", 13000);
                return;
            }
            if (array[0].equals("add")) {
                CommandBase.parseInt(array[1], 0);
                this.addTime(commandSender, 13000);
                CommandBase.notifyOperators(commandSender, this, "commands.time.added", 13000);
                return;
            }
            if (array[0].equals("query")) {
                if (array[1].equals("daytime")) {
                    final int n = (int)(commandSender.getEntityWorld().getWorldTime() % 2147483647L);
                    commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, 13000);
                    CommandBase.notifyOperators(commandSender, this, "commands.time.query", 13000);
                    return;
                }
                if (array[1].equals("gametime")) {
                    final int n2 = (int)(commandSender.getEntityWorld().getTotalWorldTime() % 2147483647L);
                    commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, 13000);
                    CommandBase.notifyOperators(commandSender, this, "commands.time.query", 13000);
                    return;
                }
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "set", "add", "query") : ((array.length == 2 && array[0].equals("set")) ? CommandBase.getListOfStringsMatchingLastWord(array, "day", "night") : ((array.length == 2 && array[0].equals("query")) ? CommandBase.getListOfStringsMatchingLastWord(array, "daytime", "gametime") : null));
    }
    
    protected void setTime(final ICommandSender commandSender, final int n) {
        while (0 < MinecraftServer.getServer().worldServers.length) {
            MinecraftServer.getServer().worldServers[0].setWorldTime(n);
            int n2 = 0;
            ++n2;
        }
    }
    
    protected void addTime(final ICommandSender commandSender, final int n) {
        while (0 < MinecraftServer.getServer().worldServers.length) {
            final WorldServer worldServer = MinecraftServer.getServer().worldServers[0];
            worldServer.setWorldTime(worldServer.getWorldTime() + n);
            int n2 = 0;
            ++n2;
        }
    }
    
    static {
        __OBFID = "CL_00001183";
    }
}
