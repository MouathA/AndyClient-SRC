package net.minecraft.command;

import java.util.regex.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class PlayerSelector
{
    private static final Pattern tokenPattern;
    private static final Pattern intListPattern;
    private static final Pattern keyValueListPattern;
    private static final Set field_179666_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000086";
        tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
        intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
        keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
        field_179666_d = Sets.newHashSet("x", "y", "z", "dx", "dy", "dz", "rm", "r");
    }
    
    public static EntityPlayerMP matchOnePlayer(final ICommandSender commandSender, final String s) {
        return (EntityPlayerMP)func_179652_a(commandSender, s, EntityPlayerMP.class);
    }
    
    public static Entity func_179652_a(final ICommandSender commandSender, final String s, final Class clazz) {
        final List func_179656_b = func_179656_b(commandSender, s, clazz);
        return (func_179656_b.size() == 1) ? func_179656_b.get(0) : null;
    }
    
    public static IChatComponent func_150869_b(final ICommandSender commandSender, final String s) {
        final List func_179656_b = func_179656_b(commandSender, s, Entity.class);
        if (func_179656_b.isEmpty()) {
            return null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Entity> iterator = func_179656_b.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getDisplayName());
        }
        return CommandBase.join(arrayList);
    }
    
    public static List func_179656_b(final ICommandSender commandSender, final String s, final Class clazz) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches() || !commandSender.canCommandSenderUseCommand(1, "@")) {
            return Collections.emptyList();
        }
        final Map argumentMap = getArgumentMap(matcher.group(2));
        if (argumentMap != null) {
            return Collections.emptyList();
        }
        final String group = matcher.group(1);
        final BlockPos func_179664_b = func_179664_b(argumentMap, commandSender.getPosition());
        final List func_179654_a = func_179654_a(commandSender, argumentMap);
        final ArrayList arrayList = Lists.newArrayList();
        for (final World world : func_179654_a) {
            if (world != null) {
                final ArrayList arrayList2 = Lists.newArrayList();
                arrayList2.addAll(func_179663_a(argumentMap, group));
                arrayList2.addAll(func_179648_b(argumentMap));
                arrayList2.addAll(func_179649_c(argumentMap));
                arrayList2.addAll(func_179659_d(argumentMap));
                arrayList2.addAll(func_179657_e(argumentMap));
                arrayList2.addAll(func_179647_f(argumentMap));
                arrayList2.addAll(func_180698_a(argumentMap, func_179664_b));
                arrayList2.addAll(func_179662_g(argumentMap));
                arrayList.addAll(func_179660_a(argumentMap, clazz, arrayList2, group, world, func_179664_b));
            }
        }
        return func_179658_a(arrayList, argumentMap, commandSender, clazz, group, func_179664_b);
    }
    
    private static List func_179654_a(final ICommandSender commandSender, final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (map == 0) {
            arrayList.add(commandSender.getEntityWorld());
        }
        else {
            Collections.addAll(arrayList, MinecraftServer.getServer().worldServers);
        }
        return arrayList;
    }
    
    private static List func_179663_a(final Map map, final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        String s2 = func_179651_b(map, "type");
        final boolean b = s2 != null && s2.startsWith("!");
        if (b) {
            s2 = s2.substring(1);
        }
        final boolean b2 = !s.equals("e");
        final boolean b3 = s.equals("r") && s2 != null;
        if ((s2 == null || !s.equals("e")) && !b3) {
            if (b2) {
                arrayList.add(new Predicate() {
                    private static final String __OBFID;
                    
                    public boolean func_179624_a(final Entity entity) {
                        return entity instanceof EntityPlayer;
                    }
                    
                    @Override
                    public boolean apply(final Object o) {
                        return this.func_179624_a((Entity)o);
                    }
                    
                    static {
                        __OBFID = "CL_00002358";
                    }
                });
            }
        }
        else {
            arrayList.add(new Predicate(b) {
                private static final String __OBFID;
                private final String val$var3_f;
                private final boolean val$var4;
                
                public boolean func_179613_a(final Entity entity) {
                    return EntityList.func_180123_a(entity, this.val$var3_f) ^ this.val$var4;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179613_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002362";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179648_b(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int func_179653_a = func_179653_a(map, "lm", -1);
        final int func_179653_a2 = func_179653_a(map, "l", -1);
        if (func_179653_a > -1 || func_179653_a2 > -1) {
            arrayList.add(new Predicate(func_179653_a2) {
                private static final String __OBFID;
                private final int val$var2;
                private final int val$var3;
                
                public boolean func_179625_a(final Entity entity) {
                    if (!(entity instanceof EntityPlayerMP)) {
                        return false;
                    }
                    final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
                    return (this.val$var2 <= -1 || entityPlayerMP.experienceLevel >= this.val$var2) && (this.val$var3 <= -1 || entityPlayerMP.experienceLevel <= this.val$var3);
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179625_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002357";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179649_c(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int func_179653_a = func_179653_a(map, "m", WorldSettings.GameType.NOT_SET.getID());
        if (func_179653_a != WorldSettings.GameType.NOT_SET.getID()) {
            arrayList.add(new Predicate() {
                private static final String __OBFID;
                private final int val$var2;
                
                public boolean func_179619_a(final Entity entity) {
                    return entity instanceof EntityPlayerMP && ((EntityPlayerMP)entity).theItemInWorldManager.getGameType().getID() == this.val$var2;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179619_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002356";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179659_d(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, "team");
        final boolean b = s != null && s.startsWith("!");
        if (b) {
            s = s.substring(1);
        }
        if (s != null) {
            arrayList.add(new Predicate(b) {
                private static final String __OBFID;
                private final String val$var2_f;
                private final boolean val$var3;
                
                public boolean func_179621_a(final Entity entity) {
                    if (!(entity instanceof EntityLivingBase)) {
                        return false;
                    }
                    final Team team = ((EntityLivingBase)entity).getTeam();
                    return ((team == null) ? "" : team.getRegisteredName()).equals(this.val$var2_f) ^ this.val$var3;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179621_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002355";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179657_e(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final Map func_96560_a = func_96560_a(map);
        if (func_96560_a != null && func_96560_a.size() > 0) {
            arrayList.add(new Predicate() {
                private static final String __OBFID;
                private final Map val$var2;
                
                public boolean func_179603_a(final Entity entity) {
                    final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
                    for (final Map.Entry<String, V> entry : this.val$var2.entrySet()) {
                        String substring = entry.getKey();
                        if (substring.endsWith("_min") && substring.length() > 4) {
                            substring = substring.substring(0, substring.length() - 4);
                        }
                        final ScoreObjective objective = scoreboard.getObjective(substring);
                        if (objective == null) {
                            return false;
                        }
                        final String s = (entity instanceof EntityPlayerMP) ? entity.getName() : entity.getUniqueID().toString();
                        if (!scoreboard.func_178819_b(s, objective)) {
                            return false;
                        }
                        final int scorePoints = scoreboard.getValueFromObjective(s, objective).getScorePoints();
                        if (scorePoints < (int)entry.getValue() && true) {
                            return false;
                        }
                        if (scorePoints > (int)entry.getValue() && !true) {
                            return false;
                        }
                    }
                    return true;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179603_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002354";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179647_f(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, "name");
        final boolean b = s != null && s.startsWith("!");
        if (b) {
            s = s.substring(1);
        }
        if (s != null) {
            arrayList.add(new Predicate(b) {
                private static final String __OBFID;
                private final String val$var2_f;
                private final boolean val$var3;
                
                public boolean func_179600_a(final Entity entity) {
                    return entity.getName().equals(this.val$var2_f) ^ this.val$var3;
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179600_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002353";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_180698_a(final Map map, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final int func_179653_a = func_179653_a(map, "rm", -1);
        final int func_179653_a2 = func_179653_a(map, "r", -1);
        if (blockPos != null && (func_179653_a >= 0 || func_179653_a2 >= 0)) {
            arrayList.add(new Predicate(func_179653_a, func_179653_a * func_179653_a, func_179653_a2, func_179653_a2 * func_179653_a2) {
                private static final String __OBFID;
                private final BlockPos val$p_180698_1_;
                private final int val$var3;
                private final int val$var5;
                private final int val$var4;
                private final int val$var6;
                
                public boolean func_179594_a(final Entity entity) {
                    final int n = (int)entity.func_174831_c(this.val$p_180698_1_);
                    return (this.val$var3 < 0 || n >= this.val$var5) && (this.val$var4 < 0 || n <= this.val$var6);
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179594_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002352";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179662_g(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (map.containsKey("rym") || map.containsKey("ry")) {
            arrayList.add(new Predicate(func_179650_a(func_179653_a(map, "ry", 359))) {
                private static final String __OBFID;
                private final int val$var2;
                private final int val$var3;
                
                public boolean func_179591_a(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationYaw));
                    return (this.val$var2 > this.val$var3) ? (func_179650_a >= this.val$var2 || func_179650_a <= this.val$var3) : (func_179650_a >= this.val$var2 && func_179650_a <= this.val$var3);
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179591_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002351";
                }
            });
        }
        if (map.containsKey("rxm") || map.containsKey("rx")) {
            arrayList.add(new Predicate(func_179650_a(func_179653_a(map, "rx", 359))) {
                private static final String __OBFID;
                private final int val$var2;
                private final int val$var3;
                
                public boolean func_179616_a(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationPitch));
                    return (this.val$var2 > this.val$var3) ? (func_179650_a >= this.val$var2 || func_179650_a <= this.val$var3) : (func_179650_a >= this.val$var2 && func_179650_a <= this.val$var3);
                }
                
                @Override
                public boolean apply(final Object o) {
                    return this.func_179616_a((Entity)o);
                }
                
                static {
                    __OBFID = "CL_00002361";
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179660_a(final Map map, final Class clazz, final List list, final String s, final World world, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final String func_179651_b = func_179651_b(map, "type");
        final String s2 = (func_179651_b != null && func_179651_b.startsWith("!")) ? func_179651_b.substring(1) : func_179651_b;
        final boolean b = !s.equals("e");
        final boolean b2 = s.equals("r") && s2 != null;
        final int func_179653_a = func_179653_a(map, "dx", 0);
        final int func_179653_a2 = func_179653_a(map, "dy", 0);
        final int func_179653_a3 = func_179653_a(map, "dz", 0);
        final int func_179653_a4 = func_179653_a(map, "r", -1);
        final Predicate and = Predicates.and(list);
        final Predicate and2 = Predicates.and(IEntitySelector.selectAnything, and);
        if (blockPos != null) {
            final boolean b3 = world.playerEntities.size() < world.loadedEntityList.size() / 16;
            if (!map.containsKey("dx") && !map.containsKey("dy") && !map.containsKey("dz")) {
                if (func_179653_a4 >= 0) {
                    final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - func_179653_a4, blockPos.getY() - func_179653_a4, blockPos.getZ() - func_179653_a4, blockPos.getX() + func_179653_a4 + 1, blockPos.getY() + func_179653_a4 + 1, blockPos.getZ() + func_179653_a4 + 1);
                    if (b && b3 && !b2) {
                        arrayList.addAll(world.func_175661_b(clazz, and2));
                    }
                    else {
                        arrayList.addAll(world.func_175647_a(clazz, axisAlignedBB, and2));
                    }
                }
                else if (s.equals("a")) {
                    arrayList.addAll(world.func_175661_b(clazz, and));
                }
                else if (!s.equals("p") && (!s.equals("r") || b2)) {
                    arrayList.addAll(world.func_175644_a(clazz, and2));
                }
                else {
                    arrayList.addAll(world.func_175661_b(clazz, and2));
                }
            }
            else {
                final AxisAlignedBB func_179661_a = func_179661_a(blockPos, func_179653_a, func_179653_a2, func_179653_a3);
                if (b && b3 && !b2) {
                    arrayList.addAll(world.func_175661_b(clazz, Predicates.and(and2, new Predicate() {
                        private static final String __OBFID;
                        private final AxisAlignedBB val$var19;
                        
                        public boolean func_179609_a(final Entity entity) {
                            return entity.posX >= this.val$var19.minX && entity.posY >= this.val$var19.minY && entity.posZ >= this.val$var19.minZ && (entity.posX < this.val$var19.maxX && entity.posY < this.val$var19.maxY && entity.posZ < this.val$var19.maxZ);
                        }
                        
                        @Override
                        public boolean apply(final Object o) {
                            return this.func_179609_a((Entity)o);
                        }
                        
                        static {
                            __OBFID = "CL_00002360";
                        }
                    })));
                }
                else {
                    arrayList.addAll(world.func_175647_a(clazz, func_179661_a, and2));
                }
            }
        }
        else if (s.equals("a")) {
            arrayList.addAll(world.func_175661_b(clazz, and));
        }
        else if (!s.equals("p") && (!s.equals("r") || b2)) {
            arrayList.addAll(world.func_175644_a(clazz, and2));
        }
        else {
            arrayList.addAll(world.func_175661_b(clazz, and2));
        }
        return arrayList;
    }
    
    private static List func_179658_a(List list, final Map map, final ICommandSender commandSender, final Class clazz, final String s, final BlockPos blockPos) {
        final int func_179653_a = func_179653_a(map, "c", (!s.equals("a") && !s.equals("e")) ? 1 : 0);
        if (!s.equals("p") && !s.equals("a") && !s.equals("e")) {
            if (s.equals("r")) {
                Collections.shuffle(list);
            }
        }
        else if (blockPos != null) {
            Collections.sort(list, new Comparator() {
                private static final String __OBFID;
                private final BlockPos val$p_179658_5_;
                
                public int func_179611_a(final Entity entity, final Entity entity2) {
                    return ComparisonChain.start().compare(entity.getDistanceSq(this.val$p_179658_5_), entity2.getDistanceSq(this.val$p_179658_5_)).result();
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.func_179611_a((Entity)o, (Entity)o2);
                }
                
                static {
                    __OBFID = "CL_00002359";
                }
            });
        }
        final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
        if (commandSenderEntity != null && clazz.isAssignableFrom(commandSenderEntity.getClass()) && func_179653_a == 1 && list.contains(commandSenderEntity) && !"r".equals(s)) {
            list = (List<Object>)Lists.newArrayList(commandSenderEntity);
        }
        if (func_179653_a != 0) {
            if (func_179653_a < 0) {
                Collections.reverse(list);
            }
            list = list.subList(0, Math.min(Math.abs(func_179653_a), list.size()));
        }
        return list;
    }
    
    private static AxisAlignedBB func_179661_a(final BlockPos blockPos, final int n, final int n2, final int n3) {
        final boolean b = n < 0;
        final boolean b2 = n2 < 0;
        final boolean b3 = n3 < 0;
        return new AxisAlignedBB(blockPos.getX() + (b ? n : 0), blockPos.getY() + (b2 ? n2 : 0), blockPos.getZ() + (b3 ? n3 : 0), blockPos.getX() + (b ? 0 : n) + 1, blockPos.getY() + (b2 ? 0 : n2) + 1, blockPos.getZ() + (b3 ? 0 : n3) + 1);
    }
    
    public static int func_179650_a(int n) {
        n %= 360;
        if (n >= 160) {
            n -= 360;
        }
        if (n < 0) {
            n += 360;
        }
        return n;
    }
    
    private static BlockPos func_179664_b(final Map map, final BlockPos blockPos) {
        return new BlockPos(func_179653_a(map, "x", blockPos.getX()), func_179653_a(map, "y", blockPos.getY()), func_179653_a(map, "z", blockPos.getZ()));
    }
    
    private static int func_179653_a(final Map map, final String s, final int n) {
        return map.containsKey(s) ? MathHelper.parseIntWithDefault(map.get(s), n) : n;
    }
    
    private static String func_179651_b(final Map map, final String s) {
        return map.get(s);
    }
    
    public static Map func_96560_a(final Map map) {
        final HashMap hashMap = Maps.newHashMap();
        for (final String s : map.keySet()) {
            if (s.startsWith("score_") && s.length() > 6) {
                hashMap.put(s.substring(6), MathHelper.parseIntWithDefault((String)map.get(s), 1));
            }
        }
        return hashMap;
    }
    
    public static boolean matchesMultiplePlayers(final String s) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches()) {
            return false;
        }
        final Map argumentMap = getArgumentMap(matcher.group(2));
        final String group = matcher.group(1);
        return func_179653_a(argumentMap, "c", (!"a".equals(group) && !"e".equals(group)) ? 1 : 0) != 1;
    }
    
    public static boolean hasArguments(final String s) {
        return PlayerSelector.tokenPattern.matcher(s).matches();
    }
    
    private static Map getArgumentMap(final String s) {
        final HashMap hashMap = Maps.newHashMap();
        if (s == null) {
            return hashMap;
        }
        final Matcher matcher = PlayerSelector.intListPattern.matcher(s);
        while (matcher.find()) {
            int n = 0;
            ++n;
            final String s2 = "x";
            if (s2 != null && matcher.group(1).length() > 0) {
                hashMap.put(s2, matcher.group(1));
            }
            matcher.end();
        }
        if (-1 < s.length()) {
            final Matcher matcher2 = PlayerSelector.keyValueListPattern.matcher(s);
            while (matcher2.find()) {
                hashMap.put(matcher2.group(1), matcher2.group(2));
            }
        }
        return hashMap;
    }
}
