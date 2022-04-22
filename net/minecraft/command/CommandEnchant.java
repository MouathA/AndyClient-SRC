package net.minecraft.command;

import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandEnchant extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "enchant";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.enchant.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
        final int int1 = CommandBase.parseInt(array[1], 0);
        final ItemStack currentEquippedItem = player.getCurrentEquippedItem();
        if (currentEquippedItem == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        final Enchantment func_180306_c = Enchantment.func_180306_c(int1);
        if (func_180306_c == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[] { int1 });
        }
        if (!func_180306_c.canApply(currentEquippedItem)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (array.length >= 3) {
            CommandBase.parseInt(array[2], func_180306_c.getMinLevel(), func_180306_c.getMaxLevel());
        }
        if (currentEquippedItem.hasTagCompound()) {
            final NBTTagList enchantmentTagList = currentEquippedItem.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                while (0 < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                    if (Enchantment.func_180306_c(short1) != null) {
                        final Enchantment func_180306_c2 = Enchantment.func_180306_c(short1);
                        if (!func_180306_c2.canApplyTogether(func_180306_c)) {
                            throw new CommandException("commands.enchant.cantCombine", new Object[] { func_180306_c.getTranslatedName(1), func_180306_c2.getTranslatedName(enchantmentTagList.getCompoundTagAt(0).getShort("lvl")) });
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
        currentEquippedItem.addEnchantment(func_180306_c, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.enchant.success", new Object[0]);
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 1);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getListOfPlayers()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, Enchantment.func_180304_c()) : null);
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00000377";
    }
}
