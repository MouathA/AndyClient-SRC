package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandSetDefaultSpawnpoint extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "setworldspawn";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setworldspawn.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        BlockPos spawnLocation;
        if (array.length == 0) {
            spawnLocation = CommandBase.getCommandSenderAsPlayer(commandSender).getPosition();
        }
        else {
            if (array.length != 3 || commandSender.getEntityWorld() == null) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            spawnLocation = CommandBase.func_175757_a(commandSender, array, 0, true);
        }
        commandSender.getEntityWorld().setSpawnLocation(spawnLocation);
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S05PacketSpawnPosition(spawnLocation));
        CommandBase.notifyOperators(commandSender, this, "commands.setworldspawn.success", spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : null;
    }
    
    static {
        __OBFID = "CL_00000973";
    }
}
