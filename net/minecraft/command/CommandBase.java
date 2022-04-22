package net.minecraft.command;

import com.google.common.primitives.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand theAdmin;
    private static final String __OBFID;
    
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public List getCommandAliases() {
        return Collections.emptyList();
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return commandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return null;
    }
    
    public static int parseInt(final String s) throws NumberInvalidException {
        return Integer.parseInt(s);
    }
    
    public static int parseInt(final String s, final int n) throws NumberInvalidException {
        return parseInt(s, n, Integer.MAX_VALUE);
    }
    
    public static int parseInt(final String s, final int n, final int n2) throws NumberInvalidException {
        final int int1 = parseInt(s);
        if (int1 < n) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { int1, n });
        }
        if (int1 > n2) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { int1, n2 });
        }
        return int1;
    }
    
    public static long parseLong(final String s) throws NumberInvalidException {
        return Long.parseLong(s);
    }
    
    public static long parseLong(final String s, final long n, final long n2) throws NumberInvalidException {
        final long long1 = parseLong(s);
        if (long1 < n) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { long1, n });
        }
        if (long1 > n2) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { long1, n2 });
        }
        return long1;
    }
    
    public static BlockPos func_175757_a(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws NumberInvalidException {
        final BlockPos position = commandSender.getPosition();
        return new BlockPos(func_175769_b(position.getX(), array[n], -30000000, 30000000, b), func_175769_b(position.getY(), array[n + 1], 0, 256, false), func_175769_b(position.getZ(), array[n + 2], -30000000, 30000000, b));
    }
    
    public static double parseDouble(final String s) throws NumberInvalidException {
        final double double1 = Double.parseDouble(s);
        if (!Doubles.isFinite(double1)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { s });
        }
        return double1;
    }
    
    public static double parseDouble(final String s, final double n) throws NumberInvalidException {
        return parseDouble(s, n, Double.MAX_VALUE);
    }
    
    public static double parseDouble(final String s, final double n, final double n2) throws NumberInvalidException {
        final double double1 = parseDouble(s);
        if (double1 < n) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { double1, n });
        }
        if (double1 > n2) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { double1, n2 });
        }
        return double1;
    }
    
    public static boolean parseBoolean(final String s) throws CommandException {
        if (s.equals("true") || s.equals("1")) {
            return true;
        }
        if (!s.equals("false") && !s.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[] { s });
        }
        return false;
    }
    
    public static EntityPlayerMP getCommandSenderAsPlayer(final ICommandSender commandSender) throws PlayerNotFoundException {
        if (commandSender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)commandSender;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static EntityPlayerMP getPlayer(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        EntityPlayerMP entityPlayerMP = PlayerSelector.matchOnePlayer(commandSender, s);
        if (entityPlayerMP == null) {
            entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().func_177451_a(UUID.fromString(s));
        }
        if (entityPlayerMP == null) {
            entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
        }
        if (entityPlayerMP == null) {
            throw new PlayerNotFoundException();
        }
        return entityPlayerMP;
    }
    
    public static Entity func_175768_b(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return func_175759_a(commandSender, s, Entity.class);
    }
    
    public static Entity func_175759_a(final ICommandSender commandSender, final String s, final Class clazz) throws EntityNotFoundException {
        Entity entity = PlayerSelector.func_179652_a(commandSender, s, clazz);
        final MinecraftServer server = MinecraftServer.getServer();
        if (entity == null) {
            entity = server.getConfigurationManager().getPlayerByUsername(s);
        }
        if (entity == null) {
            final UUID fromString = UUID.fromString(s);
            entity = server.getEntityFromUuid(fromString);
            if (entity == null) {
                entity = server.getConfigurationManager().func_177451_a(fromString);
            }
        }
        if (entity != null && clazz.isAssignableFrom(entity.getClass())) {
            return entity;
        }
        throw new EntityNotFoundException();
    }
    
    public static List func_175763_c(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return PlayerSelector.hasArguments(s) ? PlayerSelector.func_179656_b(commandSender, s, Entity.class) : Lists.newArrayList(func_175768_b(commandSender, s));
    }
    
    public static String getPlayerName(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        return getPlayer(commandSender, s).getName();
    }
    
    public static String func_175758_e(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return getPlayer(commandSender, s).getName();
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        return getChatComponentFromNthArg(commandSender, array, n, false);
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws PlayerNotFoundException {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        for (int i = n; i < array.length; ++i) {
            if (i > n) {
                chatComponentText.appendText(" ");
            }
            IChatComponent chatComponent = new ChatComponentText(array[i]);
            if (b) {
                final IChatComponent func_150869_b = PlayerSelector.func_150869_b(commandSender, array[i]);
                if (func_150869_b == null) {
                    if (PlayerSelector.hasArguments(array[i])) {
                        throw new PlayerNotFoundException();
                    }
                }
                else {
                    chatComponent = func_150869_b;
                }
            }
            chatComponentText.appendSibling(chatComponent);
        }
        return chatComponentText;
    }
    
    public static String func_180529_a(final String[] array, final int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = n; i < array.length; ++i) {
            if (i > n) {
                sb.append(" ");
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static CoordinateArg func_175770_a(final double n, final String s, final boolean b) throws NumberInvalidException {
        return func_175767_a(n, s, -30000000, 30000000, b);
    }
    
    public static CoordinateArg func_175767_a(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith("~");
        if (startsWith && Double.isNaN(n)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { n });
        }
        double n4 = 0.0;
        if (!startsWith || substring.length() > 1) {
            final boolean contains = substring.contains(".");
            if (startsWith) {
                substring = substring.substring(1);
            }
            n4 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { n4, n2 });
            }
            if (n4 > n3) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { n4, n3 });
            }
        }
        return new CoordinateArg(n4 + (startsWith ? n : 0.0), n4, startsWith);
    }
    
    public static double func_175761_b(final double n, final String s, final boolean b) throws NumberInvalidException {
        return func_175769_b(n, s, -30000000, 30000000, b);
    }
    
    public static double func_175769_b(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith("~");
        if (startsWith && Double.isNaN(n)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { n });
        }
        double n4 = startsWith ? n : 0.0;
        if (!startsWith || substring.length() > 1) {
            final boolean contains = substring.contains(".");
            if (startsWith) {
                substring = substring.substring(1);
            }
            n4 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { n4, n2 });
            }
            if (n4 > n3) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { n4, n3 });
            }
        }
        return n4;
    }
    
    public static Item getItemByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        final Item item = (Item)Item.itemRegistry.getObject(resourceLocation);
        if (item == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { resourceLocation });
        }
        return item;
    }
    
    public static Block getBlockByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (!Block.blockRegistry.containsKey(resourceLocation)) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { resourceLocation });
        }
        final Block block = (Block)Block.blockRegistry.getObject(resourceLocation);
        if (block == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { resourceLocation });
        }
        return block;
    }
    
    public static String joinNiceString(final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        while (0 < array.length) {
            final String string = array[0].toString();
            if (0 > 0) {
                if (0 == array.length - 1) {
                    sb.append(" and ");
                }
                else {
                    sb.append(", ");
                }
            }
            sb.append(string);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static IChatComponent join(final List list) {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        while (0 < list.size()) {
            if (0 > 0) {
                if (0 == list.size() - 1) {
                    chatComponentText.appendText(" and ");
                }
                else if (0 > 0) {
                    chatComponentText.appendText(", ");
                }
            }
            chatComponentText.appendSibling(list.get(0));
            int n = 0;
            ++n;
        }
        return chatComponentText;
    }
    
    public static String joinNiceStringFromCollection(final Collection collection) {
        return joinNiceString(collection.toArray(new String[collection.size()]));
    }
    
    public static List func_175771_a(final String[] array, final int n, final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        String s;
        if (array.length - 1 == n) {
            s = Integer.toString(blockPos.getX());
        }
        else if (array.length - 1 == n + 1) {
            s = Integer.toString(blockPos.getY());
        }
        else {
            if (array.length - 1 != n + 2) {
                return null;
            }
            s = Integer.toString(blockPos.getZ());
        }
        return Lists.newArrayList(s);
    }
    
    public static boolean doesStringStartWith(final String s, final String s2) {
        return s2.regionMatches(true, 0, s, 0, s.length());
    }
    
    public static List getListOfStringsMatchingLastWord(final String[] array, final String... array2) {
        return func_175762_a(array, Arrays.asList(array2));
    }
    
    public static List func_175762_a(final String[] array, final Collection collection) {
        final String s = array[array.length - 1];
        final ArrayList arrayList = Lists.newArrayList();
        if (!collection.isEmpty()) {
            for (final String s2 : Iterables.transform(collection, Functions.toStringFunction())) {
                if (doesStringStartWith(s, s2)) {
                    arrayList.add(s2);
                }
            }
            if (arrayList.isEmpty()) {
                for (final ResourceLocation next : collection) {
                    if (next instanceof ResourceLocation && doesStringStartWith(s, next.getResourcePath())) {
                        arrayList.add(String.valueOf(next));
                    }
                }
            }
        }
        return arrayList;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return false;
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final String s, final Object... array) {
        notifyOperators(commandSender, command, 0, s, array);
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final int n, final String s, final Object... array) {
        if (CommandBase.theAdmin != null) {
            CommandBase.theAdmin.notifyOperators(commandSender, command, n, s, array);
        }
    }
    
    public static void setAdminCommander(final IAdminCommand theAdmin) {
        CommandBase.theAdmin = theAdmin;
    }
    
    public int compareTo(final ICommand command) {
        return this.getCommandName().compareTo(command.getCommandName());
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ICommand)o);
    }
    
    static {
        __OBFID = "CL_00001739";
    }
    
    public static class CoordinateArg
    {
        private final double field_179633_a;
        private final double field_179631_b;
        private final boolean field_179632_c;
        private static final String __OBFID;
        
        protected CoordinateArg(final double field_179633_a, final double field_179631_b, final boolean field_179632_c) {
            this.field_179633_a = field_179633_a;
            this.field_179631_b = field_179631_b;
            this.field_179632_c = field_179632_c;
        }
        
        public double func_179628_a() {
            return this.field_179633_a;
        }
        
        public double func_179629_b() {
            return this.field_179631_b;
        }
        
        public boolean func_179630_c() {
            return this.field_179632_c;
        }
        
        static {
            __OBFID = "CL_00002365";
        }
    }
}
