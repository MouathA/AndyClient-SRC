package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.*;

public class CommandExecuteAt extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "execute";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.execute.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 5) {
            throw new WrongUsageException("commands.execute.usage", new Object[0]);
        }
        final Entity func_175759_a = CommandBase.func_175759_a(commandSender, array[0], Entity.class);
        final double func_175761_b = CommandBase.func_175761_b(func_175759_a.posX, array[1], false);
        final double func_175761_b2 = CommandBase.func_175761_b(func_175759_a.posY, array[2], false);
        final double func_175761_b3 = CommandBase.func_175761_b(func_175759_a.posZ, array[3], false);
        final BlockPos blockPos = new BlockPos(func_175761_b, func_175761_b2, func_175761_b3);
        if ("detect".equals(array[4]) && array.length > 10) {
            final World entityWorld = commandSender.getEntityWorld();
            final double func_175761_b4 = CommandBase.func_175761_b(func_175761_b, array[5], false);
            final double func_175761_b5 = CommandBase.func_175761_b(func_175761_b2, array[6], false);
            final double func_175761_b6 = CommandBase.func_175761_b(func_175761_b3, array[7], false);
            final Block blockByText = CommandBase.getBlockByText(commandSender, array[8]);
            final int int1 = CommandBase.parseInt(array[9], -1, 15);
            final IBlockState blockState = entityWorld.getBlockState(new BlockPos(func_175761_b4, func_175761_b5, func_175761_b6));
            if (blockState.getBlock() != blockByText || (int1 >= 0 && blockState.getBlock().getMetaFromState(blockState) != int1)) {
                throw new CommandException("commands.execute.failed", new Object[] { "detect", func_175759_a.getName() });
            }
        }
        final String func_180529_a = CommandBase.func_180529_a(array, 10);
        if (MinecraftServer.getServer().getCommandManager().executeCommand(new ICommandSender(func_175759_a, commandSender, blockPos, func_175761_b, func_175761_b2, func_175761_b3) {
            private static final String __OBFID;
            final CommandExecuteAt this$0;
            private final Entity val$var3;
            private final ICommandSender val$sender;
            private final BlockPos val$var10;
            private final double val$var4;
            private final double val$var6;
            private final double val$var8;
            
            @Override
            public String getName() {
                return this.val$var3.getName();
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$var3.getDisplayName();
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
                this.val$sender.addChatMessage(chatComponent);
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return this.val$sender.canCommandSenderUseCommand(n, s);
            }
            
            @Override
            public BlockPos getPosition() {
                return this.val$var10;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.val$var4, this.val$var6, this.val$var8);
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$var3.worldObj;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$var3;
            }
            
            @Override
            public boolean sendCommandFeedback() {
                final MinecraftServer server = MinecraftServer.getServer();
                return server == null || server.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
            }
            
            @Override
            public void func_174794_a(final CommandResultStats.Type type, final int n) {
                this.val$var3.func_174794_a(type, n);
            }
            
            static {
                __OBFID = "CL_00002343";
            }
        }, func_180529_a) < 1) {
            throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { func_180529_a });
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : ((array.length > 5 && array.length <= 8 && "detect".equals(array[4])) ? CommandBase.func_175771_a(array, 5, blockPos) : ((array.length == 9 && "detect".equals(array[4])) ? CommandBase.func_175762_a(array, Block.blockRegistry.getKeys()) : null)));
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00002344";
    }
}
