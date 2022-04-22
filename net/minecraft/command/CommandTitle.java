package net.minecraft.command;

import org.apache.logging.log4j.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTitle extends CommandBase
{
    private static final Logger field_175774_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002338";
        field_175774_a = LogManager.getLogger();
    }
    
    @Override
    public String getCommandName() {
        return "title";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.title.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
        }
        if (array.length < 3) {
            if ("title".equals(array[1]) || "subtitle".equals(array[1])) {
                throw new WrongUsageException("commands.title.usage.title", new Object[0]);
            }
            if ("times".equals(array[1])) {
                throw new WrongUsageException("commands.title.usage.times", new Object[0]);
            }
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        final S45PacketTitle.Type func_179969_a = S45PacketTitle.Type.func_179969_a(array[1]);
        if (func_179969_a != S45PacketTitle.Type.CLEAR && func_179969_a != S45PacketTitle.Type.RESET) {
            if (func_179969_a == S45PacketTitle.Type.TIMES) {
                if (array.length != 5) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(CommandBase.parseInt(array[2]), CommandBase.parseInt(array[3]), CommandBase.parseInt(array[4])));
                CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
            }
            else {
                if (array.length < 3) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(func_179969_a, ChatComponentProcessor.func_179985_a(commandSender, IChatComponent.Serializer.jsonToComponent(CommandBase.func_180529_a(array, 2)), player)));
                CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
            }
        }
        else {
            if (array.length != 2) {
                throw new WrongUsageException("commands.title.usage", new Object[0]);
            }
            player.playerNetServerHandler.sendPacket(new S45PacketTitle(func_179969_a, null));
            CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, S45PacketTitle.Type.func_179971_a()) : null);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
}
