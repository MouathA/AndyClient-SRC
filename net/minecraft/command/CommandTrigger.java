package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandTrigger extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "trigger";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.trigger.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }
        EntityPlayerMP entityPlayerMP;
        if (commandSender instanceof EntityPlayerMP) {
            entityPlayerMP = (EntityPlayerMP)commandSender;
        }
        else {
            final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
            if (!(commandSenderEntity instanceof EntityPlayerMP)) {
                throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }
            entityPlayerMP = (EntityPlayerMP)commandSenderEntity;
        }
        final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(array[0]);
        if (objective == null || objective.getCriteria() != IScoreObjectiveCriteria.field_178791_c) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { array[0] });
        }
        final int int1 = CommandBase.parseInt(array[2]);
        if (!scoreboard.func_178819_b(entityPlayerMP.getName(), objective)) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { array[0] });
        }
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityPlayerMP.getName(), objective);
        if (valueFromObjective.func_178816_g()) {
            throw new CommandException("commands.trigger.disabled", new Object[] { array[0] });
        }
        if ("set".equals(array[1])) {
            valueFromObjective.setScorePoints(int1);
        }
        else {
            if (!"add".equals(array[1])) {
                throw new CommandException("commands.trigger.invalidMode", new Object[] { array[1] });
            }
            valueFromObjective.increseScore(int1);
        }
        valueFromObjective.func_178815_a(true);
        if (entityPlayerMP.theItemInWorldManager.isCreative()) {
            CommandBase.notifyOperators(commandSender, this, "commands.trigger.success", array[0], array[1], array[2]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
            final ArrayList arrayList = Lists.newArrayList();
            for (final ScoreObjective scoreObjective : scoreboard.getScoreObjectives()) {
                if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.field_178791_c) {
                    arrayList.add(scoreObjective.getName());
                }
            }
            return CommandBase.getListOfStringsMatchingLastWord(array, (String[])arrayList.toArray(new String[arrayList.size()]));
        }
        return (array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, "add", "set") : null;
    }
    
    static {
        __OBFID = "CL_00002337";
    }
}
