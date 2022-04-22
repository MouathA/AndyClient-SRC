package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandOp extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "op";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.op.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1 || array[0].length() <= 0) {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array[0]);
        if (gameProfileForUsername == null) {
            throw new CommandException("commands.op.failed", new Object[] { array[0] });
        }
        server.getConfigurationManager().addOp(gameProfileForUsername);
        CommandBase.notifyOperators(commandSender, this, "commands.op.success", array[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            final String s = array[array.length - 1];
            final ArrayList arrayList = Lists.newArrayList();
            final GameProfile[] gameProfiles = MinecraftServer.getServer().getGameProfiles();
            while (0 < gameProfiles.length) {
                final GameProfile gameProfile = gameProfiles[0];
                if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameProfile) && CommandBase.doesStringStartWith(s, gameProfile.getName())) {
                    arrayList.add(gameProfile.getName());
                }
                int n = 0;
                ++n;
            }
            return arrayList;
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00000694";
    }
}
