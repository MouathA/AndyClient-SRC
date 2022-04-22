package net.minecraft.command;

import net.minecraft.world.border.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import java.util.*;

public class CommandWorldBorder extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "worldborder";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.worldborder.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
        }
        final WorldBorder worldBorder = this.getWorldBorder();
        if (array[0].equals("set")) {
            if (array.length != 2 && array.length != 3) {
                throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
            }
            final double targetSize = worldBorder.getTargetSize();
            final double double1 = CommandBase.parseDouble(array[1], 1.0, 6.0E7);
            final long n = (array.length > 2) ? (CommandBase.parseLong(array[2], 0L, 9223372036854775L) * 1000L) : 0L;
            if (n > 0L) {
                worldBorder.setTransition(targetSize, double1, n);
                if (targetSize > double1) {
                    CommandBase.notifyOperators(commandSender, this, "commands.worldborder.setSlowly.shrink.success", String.format("%.1f", double1), String.format("%.1f", targetSize), Long.toString(n / 1000L));
                }
                else {
                    CommandBase.notifyOperators(commandSender, this, "commands.worldborder.setSlowly.grow.success", String.format("%.1f", double1), String.format("%.1f", targetSize), Long.toString(n / 1000L));
                }
            }
            else {
                worldBorder.setTransition(double1);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.set.success", String.format("%.1f", double1), String.format("%.1f", targetSize));
            }
        }
        else if (array[0].equals("add")) {
            if (array.length != 2 && array.length != 3) {
                throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
            }
            final double diameter = worldBorder.getDiameter();
            final double transition = diameter + CommandBase.parseDouble(array[1], -diameter, 6.0E7 - diameter);
            final long n2 = worldBorder.getTimeUntilTarget() + ((array.length > 2) ? (CommandBase.parseLong(array[2], 0L, 9223372036854775L) * 1000L) : 0L);
            if (n2 > 0L) {
                worldBorder.setTransition(diameter, transition, n2);
                if (diameter > transition) {
                    CommandBase.notifyOperators(commandSender, this, "commands.worldborder.setSlowly.shrink.success", String.format("%.1f", transition), String.format("%.1f", diameter), Long.toString(n2 / 1000L));
                }
                else {
                    CommandBase.notifyOperators(commandSender, this, "commands.worldborder.setSlowly.grow.success", String.format("%.1f", transition), String.format("%.1f", diameter), Long.toString(n2 / 1000L));
                }
            }
            else {
                worldBorder.setTransition(transition);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.set.success", String.format("%.1f", transition), String.format("%.1f", diameter));
            }
        }
        else if (array[0].equals("center")) {
            if (array.length != 3) {
                throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
            }
            final BlockPos position = commandSender.getPosition();
            final double func_175761_b = CommandBase.func_175761_b(position.getX() + 0.5, array[1], true);
            final double func_175761_b2 = CommandBase.func_175761_b(position.getZ() + 0.5, array[2], true);
            worldBorder.setCenter(func_175761_b, func_175761_b2);
            CommandBase.notifyOperators(commandSender, this, "commands.worldborder.center.success", func_175761_b, func_175761_b2);
        }
        else if (array[0].equals("damage")) {
            if (array.length < 2) {
                throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
            }
            if (array[1].equals("buffer")) {
                if (array.length != 3) {
                    throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
                }
                final double double2 = CommandBase.parseDouble(array[2], 0.0);
                final double damageBuffer = worldBorder.getDamageBuffer();
                worldBorder.setDamageBuffer(double2);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.damage.buffer.success", String.format("%.1f", double2), String.format("%.1f", damageBuffer));
            }
            else if (array[1].equals("amount")) {
                if (array.length != 3) {
                    throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
                }
                final double double3 = CommandBase.parseDouble(array[2], 0.0);
                final double func_177727_n = worldBorder.func_177727_n();
                worldBorder.func_177744_c(double3);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.damage.amount.success", String.format("%.2f", double3), String.format("%.2f", func_177727_n));
            }
        }
        else if (array[0].equals("warning")) {
            if (array.length < 2) {
                throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
            }
            final int int1 = CommandBase.parseInt(array[2], 0);
            if (array[1].equals("time")) {
                if (array.length != 3) {
                    throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
                }
                final int warningTime = worldBorder.getWarningTime();
                worldBorder.setWarningTime(int1);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.warning.time.success", int1, warningTime);
            }
            else if (array[1].equals("distance")) {
                if (array.length != 3) {
                    throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
                }
                final int warningDistance = worldBorder.getWarningDistance();
                worldBorder.setWarningDistance(int1);
                CommandBase.notifyOperators(commandSender, this, "commands.worldborder.warning.distance.success", int1, warningDistance);
            }
        }
        else if (array[0].equals("get")) {
            final double diameter2 = worldBorder.getDiameter();
            commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(diameter2 + 0.5));
            commandSender.addChatMessage(new ChatComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", diameter2) }));
        }
    }
    
    protected WorldBorder getWorldBorder() {
        return MinecraftServer.getServer().worldServers[0].getWorldBorder();
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "set", "center", "damage", "warning", "add", "get") : ((array.length == 2 && array[0].equals("damage")) ? CommandBase.getListOfStringsMatchingLastWord(array, "buffer", "amount") : ((array.length == 2 && array[0].equals("warning")) ? CommandBase.getListOfStringsMatchingLastWord(array, "time", "distance") : null));
    }
    
    static {
        __OBFID = "CL_00002336";
    }
}
