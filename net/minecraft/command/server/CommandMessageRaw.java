package net.minecraft.command.server;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandMessageRaw extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "tellraw";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.tellraw.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        player.addChatMessage(ChatComponentProcessor.func_179985_a(commandSender, IChatComponent.Serializer.jsonToComponent(CommandBase.func_180529_a(array, 1)), player));
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00000667";
    }
}
