package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandDifficulty extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "difficulty";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.difficulty.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        final EnumDifficulty func_180531_e = this.func_180531_e(array[0]);
        MinecraftServer.getServer().setDifficultyForAllWorlds(func_180531_e);
        CommandBase.notifyOperators(commandSender, this, "commands.difficulty.success", new ChatComponentTranslation(func_180531_e.getDifficultyResourceKey(), new Object[0]));
    }
    
    protected EnumDifficulty func_180531_e(final String s) throws CommandException {
        return (!s.equalsIgnoreCase("peaceful") && !s.equalsIgnoreCase("p")) ? ((!s.equalsIgnoreCase("easy") && !s.equalsIgnoreCase("e")) ? ((!s.equalsIgnoreCase("normal") && !s.equalsIgnoreCase("n")) ? ((!s.equalsIgnoreCase("hard") && !s.equalsIgnoreCase("h")) ? EnumDifficulty.getDifficultyEnum(CommandBase.parseInt(s, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "peaceful", "easy", "normal", "hard") : null;
    }
    
    static {
        __OBFID = "CL_00000422";
    }
}
