package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandFill extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "fill";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.fill.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 7) {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos func_175757_a = CommandBase.func_175757_a(commandSender, array, 0, false);
        final BlockPos func_175757_a2 = CommandBase.func_175757_a(commandSender, array, 3, false);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array[6]);
        if (array.length >= 8) {
            CommandBase.parseInt(array[7], 0, 15);
        }
        final BlockPos blockPos = new BlockPos(Math.min(func_175757_a.getX(), func_175757_a2.getX()), Math.min(func_175757_a.getY(), func_175757_a2.getY()), Math.min(func_175757_a.getZ(), func_175757_a2.getZ()));
        final BlockPos blockPos2 = new BlockPos(Math.max(func_175757_a.getX(), func_175757_a2.getX()), Math.max(func_175757_a.getY(), func_175757_a2.getY()), Math.max(func_175757_a.getZ(), func_175757_a2.getZ()));
        int n = (blockPos2.getX() - blockPos.getX() + 1) * (blockPos2.getY() - blockPos.getY() + 1) * (blockPos2.getZ() - blockPos.getZ() + 1);
        if (0 > 32768) {
            throw new CommandException("commands.fill.tooManyBlocks", new Object[] { 0, 32768 });
        }
        if (blockPos.getY() < 0 || blockPos2.getY() >= 256) {
            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        for (int i = blockPos.getZ(); i < blockPos2.getZ() + 16; i += 16) {
            int x = blockPos.getX();
            while (1 < blockPos2.getX() + 16) {
                if (!entityWorld.isBlockLoaded(new BlockPos(1, blockPos2.getY() - blockPos.getY(), i))) {
                    throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                }
                x += 16;
            }
        }
        NBTTagCompound func_180713_a = new NBTTagCompound();
        if (array.length >= 10 && blockByText.hasTileEntity()) {
            func_180713_a = JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 9).getUnformattedText());
        }
        final ArrayList arrayList = Lists.newArrayList();
        for (int j = blockPos.getZ(); j <= blockPos2.getZ(); ++j) {
            for (int k = blockPos.getY(); k <= blockPos2.getY(); ++k) {
                for (int l = blockPos.getX(); l <= blockPos2.getX(); ++l) {
                    final BlockPos blockPos3 = new BlockPos(l, k, j);
                    if (array.length >= 9) {
                        if (!array[8].equals("outline") && !array[8].equals("hollow")) {
                            if (array[8].equals("destroy")) {
                                entityWorld.destroyBlock(blockPos3, true);
                            }
                            else if (array[8].equals("keep")) {
                                if (!entityWorld.isAirBlock(blockPos3)) {
                                    continue;
                                }
                            }
                            else if (array[8].equals("replace") && !blockByText.hasTileEntity()) {
                                if (array.length > 9 && entityWorld.getBlockState(blockPos3).getBlock() != CommandBase.getBlockByText(commandSender, array[9])) {
                                    continue;
                                }
                                if (array.length > 10) {
                                    final int int1 = CommandBase.parseInt(array[10]);
                                    final IBlockState blockState = entityWorld.getBlockState(blockPos3);
                                    if (blockState.getBlock().getMetaFromState(blockState) != int1) {
                                        continue;
                                    }
                                }
                            }
                        }
                        else if (l != blockPos.getX() && l != blockPos2.getX() && k != blockPos.getY() && k != blockPos2.getY() && j != blockPos.getZ() && j != blockPos2.getZ()) {
                            if (array[8].equals("hollow")) {
                                entityWorld.setBlockState(blockPos3, Blocks.air.getDefaultState(), 2);
                                arrayList.add(blockPos3);
                            }
                            continue;
                        }
                    }
                    final TileEntity tileEntity = entityWorld.getTileEntity(blockPos3);
                    if (tileEntity != null) {
                        if (tileEntity instanceof IInventory) {
                            ((IInventory)tileEntity).clearInventory();
                        }
                        entityWorld.setBlockState(blockPos3, Blocks.barrier.getDefaultState(), (blockByText == Blocks.barrier) ? 2 : 4);
                    }
                    if (entityWorld.setBlockState(blockPos3, blockByText.getStateFromMeta(0), 2)) {
                        arrayList.add(blockPos3);
                        ++n;
                        if (true) {
                            final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos3);
                            if (tileEntity2 != null) {
                                func_180713_a.setInteger("x", blockPos3.getX());
                                func_180713_a.setInteger("y", blockPos3.getY());
                                func_180713_a.setInteger("z", blockPos3.getZ());
                                tileEntity2.readFromNBT(func_180713_a);
                            }
                        }
                    }
                }
            }
        }
        for (final BlockPos blockPos4 : arrayList) {
            entityWorld.func_175722_b(blockPos4, entityWorld.getBlockState(blockPos4).getBlock());
        }
        if (0 <= 0) {
            throw new CommandException("commands.fill.failed", new Object[0]);
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        CommandBase.notifyOperators(commandSender, this, "commands.fill.success", 0);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length > 3 && array.length <= 6) ? CommandBase.func_175771_a(array, 3, blockPos) : ((array.length == 7) ? CommandBase.func_175762_a(array, Block.blockRegistry.getKeys()) : ((array.length == 9) ? CommandBase.getListOfStringsMatchingLastWord(array, "replace", "destroy", "keep", "hollow", "outline") : null)));
    }
    
    static {
        __OBFID = "CL_00002342";
    }
}
