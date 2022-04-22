package net.minecraft.command.server;

import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import java.util.*;

public class CommandSetBlock extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "setblock";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 0, false);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array[3]);
        if (array.length >= 5) {
            CommandBase.parseInt(array[4], 0, 15);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(func_175757_a)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound func_180713_a = new NBTTagCompound();
        if (array.length >= 7 && blockByText.hasTileEntity()) {
            func_180713_a = JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 6).getUnformattedText());
        }
        if (array.length >= 6) {
            if (array[5].equals("destroy")) {
                entityWorld.destroyBlock(func_175757_a, true);
                if (blockByText == Blocks.air) {
                    CommandBase.notifyOperators(commandSender, this, "commands.setblock.success", new Object[0]);
                    return;
                }
            }
            else if (array[5].equals("keep") && !entityWorld.isAirBlock(func_175757_a)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(func_175757_a);
        if (tileEntity != null) {
            if (tileEntity instanceof IInventory) {
                ((IInventory)tileEntity).clearInventory();
            }
            entityWorld.setBlockState(func_175757_a, Blocks.air.getDefaultState(), (blockByText == Blocks.air) ? 2 : 4);
        }
        final IBlockState stateFromMeta = blockByText.getStateFromMeta(0);
        if (!entityWorld.setBlockState(func_175757_a, stateFromMeta, 2)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
        if (true) {
            final TileEntity tileEntity2 = entityWorld.getTileEntity(func_175757_a);
            if (tileEntity2 != null) {
                func_180713_a.setInteger("x", func_175757_a.getX());
                func_180713_a.setInteger("y", func_175757_a.getY());
                func_180713_a.setInteger("z", func_175757_a.getZ());
                tileEntity2.readFromNBT(func_180713_a);
            }
        }
        entityWorld.func_175722_b(func_175757_a, stateFromMeta.getBlock());
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.setblock.success", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length == 4) ? CommandBase.func_175762_a(array, Block.blockRegistry.getKeys()) : ((array.length == 6) ? CommandBase.getListOfStringsMatchingLastWord(array, "replace", "destroy", "keep") : null));
    }
    
    static {
        __OBFID = "CL_00000949";
    }
}
