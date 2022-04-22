package net.minecraft.command;

import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandPlaySound extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "playsound";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.playsound.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(commandSender), new Object[0]);
        }
        final String s = array[0];
        final int n = 1;
        int n2 = 0;
        ++n2;
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[n]);
        final Vec3 positionVector = commandSender.getPositionVector();
        double n3 = positionVector.xCoord;
        if (array.length > 1) {
            final double n4 = n3;
            final int n5 = 1;
            ++n2;
            n3 = CommandBase.func_175761_b(n4, array[n5], true);
        }
        double n6 = positionVector.yCoord;
        if (array.length > 1) {
            final double n7 = n6;
            final int n8 = 1;
            ++n2;
            n6 = CommandBase.func_175769_b(n7, array[n8], 0, 0, false);
        }
        double n9 = positionVector.zCoord;
        if (array.length > 1) {
            final double n10 = n9;
            final int n11 = 1;
            ++n2;
            n9 = CommandBase.func_175761_b(n10, array[n11], true);
        }
        double double1 = 1.0;
        if (array.length > 1) {
            final int n12 = 1;
            ++n2;
            double1 = CommandBase.parseDouble(array[n12], 0.0, 3.4028234663852886E38);
        }
        double double2 = 1.0;
        if (array.length > 1) {
            final int n13 = 1;
            ++n2;
            double2 = CommandBase.parseDouble(array[n13], 0.0, 2.0);
        }
        double double3 = 0.0;
        if (array.length > 1) {
            double3 = CommandBase.parseDouble(array[1], 0.0, 1.0);
        }
        if (player.getDistance(n3, n6, n9) > ((double1 > 1.0) ? (double1 * 16.0) : 16.0)) {
            if (double3 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", new Object[] { player.getName() });
            }
            final double n14 = n3 - player.posX;
            final double n15 = n6 - player.posY;
            final double n16 = n9 - player.posZ;
            final double sqrt = Math.sqrt(n14 * n14 + n15 * n15 + n16 * n16);
            if (sqrt > 0.0) {
                n3 = player.posX + n14 / sqrt * 2.0;
                n6 = player.posY + n15 / sqrt * 2.0;
                n9 = player.posZ + n16 / sqrt * 2.0;
            }
            double1 = double3;
        }
        player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(s, n3, n6, n9, (float)double1, (float)double2));
        CommandBase.notifyOperators(commandSender, this, "commands.playsound.success", s, player.getName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length > 2 && array.length <= 5) ? CommandBase.func_175771_a(array, 2, blockPos) : null);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 1;
    }
    
    static {
        __OBFID = "CL_00000774";
    }
}
