package net.minecraft.command;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandBlockData extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "blockdata";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.blockdata.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 0, false);
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(func_175757_a)) {
            throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(func_175757_a);
        if (tileEntity == null) {
            throw new CommandException("commands.blockdata.notValid", new Object[0]);
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
        nbtTagCompound.merge(JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 3).getUnformattedText()));
        nbtTagCompound.setInteger("x", func_175757_a.getX());
        nbtTagCompound.setInteger("y", func_175757_a.getY());
        nbtTagCompound.setInteger("z", func_175757_a.getZ());
        if (nbtTagCompound.equals(nbtTagCompound2)) {
            throw new CommandException("commands.blockdata.failed", new Object[] { nbtTagCompound.toString() });
        }
        tileEntity.readFromNBT(nbtTagCompound);
        tileEntity.markDirty();
        entityWorld.markBlockForUpdate(func_175757_a);
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.blockdata.success", nbtTagCompound.toString());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : null;
    }
    
    static {
        __OBFID = "CL_00002349";
    }
}
