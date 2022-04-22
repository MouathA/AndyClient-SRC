package net.minecraft.command.server;

import net.minecraft.block.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import java.util.*;

public class CommandTestForBlock extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "testforblock";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.testforblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 0, false);
        final Block blockFromName = Block.getBlockFromName(array[3]);
        if (blockFromName == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[] { array[3] });
        }
        if (array.length >= 5) {
            CommandBase.parseInt(array[4], -1, 15);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(func_175757_a)) {
            throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound func_180713_a = new NBTTagCompound();
        if (array.length >= 6 && blockFromName.hasTileEntity()) {
            func_180713_a = JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 5).getUnformattedText());
        }
        final IBlockState blockState = entityWorld.getBlockState(func_175757_a);
        final Block block = blockState.getBlock();
        if (block != blockFromName) {
            throw new CommandException("commands.testforblock.failed.tile", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ(), block.getLocalizedName(), blockFromName.getLocalizedName() });
        }
        if (-1 > -1) {
            final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
            if (metaFromState != -1) {
                throw new CommandException("commands.testforblock.failed.data", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ(), metaFromState, -1 });
            }
        }
        if (true) {
            final TileEntity tileEntity = entityWorld.getTileEntity(func_175757_a);
            if (tileEntity == null) {
                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ() });
            }
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            if (!func_175775_a(func_180713_a, nbtTagCompound, true)) {
                throw new CommandException("commands.testforblock.failed.nbt", new Object[] { func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ() });
            }
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.testforblock.success", func_175757_a.getX(), func_175757_a.getY(), func_175757_a.getZ());
    }
    
    public static boolean func_175775_a(final NBTBase nbtBase, final NBTBase nbtBase2, final boolean b) {
        if (nbtBase == nbtBase2) {
            return true;
        }
        if (nbtBase == null) {
            return true;
        }
        if (nbtBase2 == null) {
            return false;
        }
        if (!nbtBase.getClass().equals(nbtBase2.getClass())) {
            return false;
        }
        if (nbtBase instanceof NBTTagCompound) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)nbtBase;
            final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtBase2;
            for (final String s : nbtTagCompound.getKeySet()) {
                if (!func_175775_a(nbtTagCompound.getTag(s), nbtTagCompound2.getTag(s), b)) {
                    return false;
                }
            }
            return true;
        }
        if (!(nbtBase instanceof NBTTagList) || !b) {
            return nbtBase.equals(nbtBase2);
        }
        final NBTTagList list = (NBTTagList)nbtBase;
        final NBTTagList list2 = (NBTTagList)nbtBase2;
        if (list.tagCount() == 0) {
            return list2.tagCount() == 0;
        }
        while (0 < list.tagCount()) {
            final NBTBase value = list.get(0);
            while (0 < list2.tagCount() && !func_175775_a(value, list2.get(0), b)) {
                int n = 0;
                ++n;
            }
            if (!true) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length == 4) ? CommandBase.func_175762_a(array, Block.blockRegistry.getKeys()) : null);
    }
    
    static {
        __OBFID = "CL_00001181";
    }
}
