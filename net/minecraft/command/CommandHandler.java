package net.minecraft.command;

import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandHandler implements ICommandManager
{
    private static final Logger logger;
    private final Map commandMap;
    private final Set commandSet;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001765";
        logger = LogManager.getLogger();
    }
    
    public CommandHandler() {
        this.commandMap = Maps.newHashMap();
        this.commandSet = Sets.newHashSet();
    }
    
    @Override
    public int executeCommand(final ICommandSender commandSender, String s) {
        s = s.trim();
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        final String[] split = s.split(" ");
        final String s2 = split[0];
        final String[] dropFirstString = dropFirstString(split);
        final ICommand command = this.commandMap.get(s2);
        final int usernameIndex = this.getUsernameIndex(command, dropFirstString);
        if (command == null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
        }
        else if (command.canCommandSenderUseCommand(commandSender)) {
            if (usernameIndex > -1) {
                final List func_179656_b = PlayerSelector.func_179656_b(commandSender, dropFirstString[usernameIndex], Entity.class);
                final String s3 = dropFirstString[usernameIndex];
                commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, func_179656_b.size());
                final Iterator<Entity> iterator = func_179656_b.iterator();
                while (iterator.hasNext()) {
                    dropFirstString[usernameIndex] = iterator.next().getUniqueID().toString();
                    if (this.func_175786_a(commandSender, dropFirstString, command, s)) {
                        int n = 0;
                        ++n;
                    }
                }
                dropFirstString[usernameIndex] = s3;
            }
            else {
                commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
                if (this.func_175786_a(commandSender, dropFirstString, command, s)) {
                    int n = 0;
                    ++n;
                }
            }
        }
        else {
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation2);
        }
        commandSender.func_174794_a(CommandResultStats.Type.SUCCESS_COUNT, 0);
        return 0;
    }
    
    protected boolean func_175786_a(final ICommandSender commandSender, final String[] array, final ICommand command, final String s) {
        command.processCommand(commandSender, array);
        return true;
    }
    
    public ICommand registerCommand(final ICommand command) {
        this.commandMap.put(command.getCommandName(), command);
        this.commandSet.add(command);
        for (final String s : command.getCommandAliases()) {
            final ICommand command2 = this.commandMap.get(s);
            if (command2 == null || !command2.getCommandName().equals(s)) {
                this.commandMap.put(s, command);
            }
        }
        return command;
    }
    
    private static String[] dropFirstString(final String[] array) {
        final String[] array2 = new String[array.length - 1];
        System.arraycopy(array, 1, array2, 0, array.length - 1);
        return array2;
    }
    
    @Override
    public List getTabCompletionOptions(final ICommandSender commandSender, final String s, final BlockPos blockPos) {
        final String[] split = s.split(" ", -1);
        final String s2 = split[0];
        if (split.length == 1) {
            final ArrayList arrayList = Lists.newArrayList();
            for (final Map.Entry<String, V> entry : this.commandMap.entrySet()) {
                if (CommandBase.doesStringStartWith(s2, entry.getKey()) && ((ICommand)entry.getValue()).canCommandSenderUseCommand(commandSender)) {
                    arrayList.add(entry.getKey());
                }
            }
            return arrayList;
        }
        if (split.length > 1) {
            final ICommand command = this.commandMap.get(s2);
            if (command != null && command.canCommandSenderUseCommand(commandSender)) {
                return command.addTabCompletionOptions(commandSender, dropFirstString(split), blockPos);
            }
        }
        return null;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender commandSender) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final ICommand command : this.commandSet) {
            if (command.canCommandSenderUseCommand(commandSender)) {
                arrayList.add(command);
            }
        }
        return arrayList;
    }
    
    @Override
    public Map getCommands() {
        return this.commandMap;
    }
    
    private int getUsernameIndex(final ICommand command, final String[] array) {
        if (command == null) {
            return -1;
        }
        while (0 < array.length) {
            if (command.isUsernameIndex(array, 0) && PlayerSelector.matchesMultiplePlayers(array[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
}
