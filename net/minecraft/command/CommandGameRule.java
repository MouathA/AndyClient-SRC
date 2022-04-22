package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandGameRule extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "gamerule";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.gamerule.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final GameRules gameRules = this.getGameRules();
        final String s = (array.length > 0) ? array[0] : "";
        final String s2 = (array.length > 1) ? CommandBase.func_180529_a(array, 1) : "";
        switch (array.length) {
            case 0: {
                commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(gameRules.getRules())));
                break;
            }
            case 1: {
                if (!gameRules.hasRule(s)) {
                    throw new CommandException("commands.gamerule.norule", new Object[] { s });
                }
                commandSender.addChatMessage(new ChatComponentText(s).appendText(" = ").appendText(gameRules.getGameRuleStringValue(s)));
                commandSender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, gameRules.getInt(s));
                break;
            }
            default: {
                if (gameRules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s2) && !"false".equals(s2)) {
                    throw new CommandException("commands.generic.boolean.invalid", new Object[] { s2 });
                }
                gameRules.setOrCreateGameRule(s, s2);
                func_175773_a(gameRules, s);
                CommandBase.notifyOperators(commandSender, this, "commands.gamerule.success", new Object[0]);
                break;
            }
        }
    }
    
    public static void func_175773_a(final GameRules gameRules, final String s) {
        if ("reducedDebugInfo".equals(s)) {
            final int n = gameRules.getGameRuleBooleanValue(s) ? 22 : 23;
            for (final EntityPlayerMP entityPlayerMP : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                entityPlayerMP.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(entityPlayerMP, (byte)n));
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, this.getGameRules().getRules());
        }
        if (array.length == 2 && this.getGameRules().areSameType(array[0], GameRules.ValueType.BOOLEAN_VALUE)) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "true", "false");
        }
        return null;
    }
    
    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }
    
    static {
        __OBFID = "CL_00000475";
    }
}
