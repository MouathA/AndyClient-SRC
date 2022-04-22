package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandXP extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "xp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.xp.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
        String substring = array[0];
        final boolean b = substring.endsWith("l") || substring.endsWith("L");
        if (b && substring.length() > 1) {
            substring = substring.substring(0, substring.length() - 1);
        }
        int int1 = CommandBase.parseInt(substring);
        final boolean b2 = int1 < 0;
        if (b2) {
            int1 = -int1;
        }
        final EntityPlayerMP entityPlayerMP = (array.length > 1) ? CommandBase.getPlayer(commandSender, array[1]) : CommandBase.getCommandSenderAsPlayer(commandSender);
        if (b) {
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, entityPlayerMP.experienceLevel);
            if (b2) {
                entityPlayerMP.addExperienceLevel(-int1);
                CommandBase.notifyOperators(commandSender, this, "commands.xp.success.negative.levels", int1, entityPlayerMP.getName());
            }
            else {
                entityPlayerMP.addExperienceLevel(int1);
                CommandBase.notifyOperators(commandSender, this, "commands.xp.success.levels", int1, entityPlayerMP.getName());
            }
        }
        else {
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, entityPlayerMP.experienceTotal);
            if (b2) {
                throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
            }
            entityPlayerMP.addExperience(int1);
            CommandBase.notifyOperators(commandSender, this, "commands.xp.success", int1, entityPlayerMP.getName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getAllUsernames()) : null;
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 1;
    }
    
    static {
        __OBFID = "CL_00000398";
    }
}
