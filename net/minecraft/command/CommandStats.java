package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;

public class CommandStats extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "stats";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.stats.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.stats.usage", new Object[0]);
        }
        if (!array[0].equals("entity")) {
            if (!array[0].equals("block")) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
        }
        if (true) {
            if (array.length < 5) {
                throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
            }
        }
        else if (array.length < 3) {
            throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
        }
        final String s = array[2];
        if ("set".equals(s)) {
            if (array.length < 6) {
                if (3 == 5) {
                    throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
            }
        }
        else {
            if (!"clear".equals(s)) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
            if (array.length < 4) {
                if (3 == 5) {
                    throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
            }
        }
        final int n = 3;
        int n2 = 0;
        ++n2;
        final CommandResultStats.Type func_179635_a = CommandResultStats.Type.func_179635_a(array[n]);
        if (func_179635_a == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        CommandResultStats commandResultStats;
        if (true) {
            final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 1, false);
            final TileEntity tileEntity = entityWorld.getTileEntity(func_175757_a);
            if (tileEntity == null) {
                throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ() });
            }
            if (tileEntity instanceof TileEntityCommandBlock) {
                commandResultStats = ((TileEntityCommandBlock)tileEntity).func_175124_c();
            }
            else {
                if (!(tileEntity instanceof TileEntitySign)) {
                    throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ() });
                }
                commandResultStats = ((TileEntitySign)tileEntity).func_174880_d();
            }
        }
        else {
            commandResultStats = CommandBase.func_175768_b(commandSender, array[1]).func_174807_aT();
        }
        if ("set".equals(s)) {
            final int n3 = 3;
            ++n2;
            final String s2 = array[n3];
            final String s3 = array[3];
            if (s2.length() == 0 || s3.length() == 0) {
                throw new CommandException("commands.stats.failed", new Object[0]);
            }
            CommandResultStats.func_179667_a(commandResultStats, func_179635_a, s2, s3);
            CommandBase.notifyOperators(commandSender, this, "commands.stats.success", func_179635_a.func_179637_b(), s3, s2);
        }
        else if ("clear".equals(s)) {
            CommandResultStats.func_179667_a(commandResultStats, func_179635_a, null, null);
            CommandBase.notifyOperators(commandSender, this, "commands.stats.cleared", func_179635_a.func_179637_b());
        }
        if (true) {
            entityWorld.getTileEntity(CommandBase.func_175757_a(commandSender, array, 1, false)).markDirty();
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "entity", "block") : ((array.length == 2 && array[0].equals("entity")) ? CommandBase.getListOfStringsMatchingLastWord(array, this.func_175776_d()) : (((array.length != 3 || !array[0].equals("entity")) && (array.length != 5 || !array[0].equals("block"))) ? (((array.length != 4 || !array[0].equals("entity")) && (array.length != 6 || !array[0].equals("block"))) ? (((array.length != 6 || !array[0].equals("entity")) && (array.length != 8 || !array[0].equals("block"))) ? null : CommandBase.func_175762_a(array, this.func_175777_e())) : CommandBase.getListOfStringsMatchingLastWord(array, CommandResultStats.Type.func_179634_c())) : CommandBase.getListOfStringsMatchingLastWord(array, "set", "clear")));
    }
    
    protected String[] func_175776_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    protected List func_175777_e() {
        final Collection scoreObjectives = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (!scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return array.length > 0 && array[0].equals("entity") && n == 1;
    }
    
    static {
        __OBFID = "CL_00002339";
    }
}
