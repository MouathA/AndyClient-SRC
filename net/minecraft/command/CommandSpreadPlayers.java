package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;

public class CommandSpreadPlayers extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "spreadplayers";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.spreadplayers.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }
        final BlockPos position = commandSender.getPosition();
        final double func_175761_b = CommandBase.func_175761_b(position.getX(), array[0], true);
        final double n = position.getZ();
        final int n2 = 1;
        int n3 = 0;
        ++n3;
        final double func_175761_b2 = CommandBase.func_175761_b(n, array[n2], true);
        final int n4 = 1;
        ++n3;
        final double double1 = CommandBase.parseDouble(array[n4], 0.0);
        final int n5 = 1;
        ++n3;
        final double double2 = CommandBase.parseDouble(array[n5], double1 + 1.0);
        final int n6 = 1;
        ++n3;
        final boolean boolean1 = CommandBase.parseBoolean(array[n6]);
        final ArrayList arrayList = Lists.newArrayList();
        while (1 < array.length) {
            final int n7 = 1;
            ++n3;
            final String s = array[n7];
            if (PlayerSelector.hasArguments(s)) {
                final List func_179656_b = PlayerSelector.func_179656_b(commandSender, s, Entity.class);
                if (func_179656_b.size() == 0) {
                    throw new EntityNotFoundException();
                }
                arrayList.addAll(func_179656_b);
            }
            else {
                final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException();
                }
                arrayList.add(playerByUsername);
            }
        }
        commandSender.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
        if (arrayList.isEmpty()) {
            throw new EntityNotFoundException();
        }
        commandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.spreading." + (boolean1 ? "teams" : "players"), new Object[] { arrayList.size(), double2, func_175761_b, func_175761_b2, double1 }));
        this.func_110669_a(commandSender, arrayList, new Position(func_175761_b, func_175761_b2), double1, double2, arrayList.get(0).worldObj, boolean1);
    }
    
    private void func_110669_a(final ICommandSender commandSender, final List list, final Position position, final double n, final double n2, final World world, final boolean b) throws CommandException {
        final Random random = new Random();
        final double n3 = position.field_111101_a - n2;
        final double n4 = position.field_111100_b - n2;
        final double n5 = position.field_111101_a + n2;
        final double n6 = position.field_111100_b + n2;
        final Position[] func_110670_a = this.func_110670_a(random, b ? this.func_110667_a(list) : list.size(), n3, n4, n5, n6);
        final int func_110668_a = this.func_110668_a(position, n, world, random, n3, n4, n5, n6, func_110670_a, b);
        final double func_110671_a = this.func_110671_a(list, world, func_110670_a, b);
        CommandBase.notifyOperators(commandSender, this, "commands.spreadplayers.success." + (b ? "teams" : "players"), func_110670_a.length, position.field_111101_a, position.field_111100_b);
        if (func_110670_a.length > 1) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.info." + (b ? "teams" : "players"), new Object[] { String.format("%.2f", func_110671_a), func_110668_a }));
        }
    }
    
    private int func_110667_a(final List list) {
        final HashSet hashSet = Sets.newHashSet();
        for (final Entity entity : list) {
            if (entity instanceof EntityPlayer) {
                hashSet.add(((EntityPlayer)entity).getTeam());
            }
            else {
                hashSet.add(null);
            }
        }
        return hashSet.size();
    }
    
    private int func_110668_a(final Position p0, final double p1, final World p2, final Random p3, final double p4, final double p5, final double p6, final double p7, final Position[] p8, final boolean p9) throws CommandException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dstore          17
        //     5: goto            283
        //     8: ldc2_w          3.4028234663852886E38
        //    11: dstore          17
        //    13: goto            217
        //    16: aload           14
        //    18: iconst_0       
        //    19: aaload         
        //    20: astore          23
        //    22: new             Lnet/minecraft/command/CommandSpreadPlayers$Position;
        //    25: dup            
        //    26: invokespecial   net/minecraft/command/CommandSpreadPlayers$Position.<init>:()V
        //    29: astore          21
        //    31: goto            118
        //    34: iconst_0       
        //    35: iconst_0       
        //    36: if_icmpeq       115
        //    39: aload           14
        //    41: iconst_0       
        //    42: aaload         
        //    43: astore          25
        //    45: aload           23
        //    47: aload           25
        //    49: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111099_a:(Lnet/minecraft/command/CommandSpreadPlayers$Position;)D
        //    52: dstore          26
        //    54: dload           26
        //    56: dload           17
        //    58: invokestatic    java/lang/Math.min:(DD)D
        //    61: dstore          17
        //    63: dload           26
        //    65: dload_2        
        //    66: dcmpg          
        //    67: ifge            115
        //    70: iinc            20, 1
        //    73: aload           21
        //    75: dup            
        //    76: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //    79: aload           25
        //    81: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //    84: aload           23
        //    86: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //    89: dsub           
        //    90: dadd           
        //    91: putfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //    94: aload           21
        //    96: dup            
        //    97: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   100: aload           25
        //   102: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   105: aload           23
        //   107: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   110: dsub           
        //   111: dadd           
        //   112: putfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   115: iinc            24, 1
        //   118: iconst_0       
        //   119: aload           14
        //   121: arraylength    
        //   122: if_icmplt       34
        //   125: iconst_0       
        //   126: ifle            198
        //   129: aload           21
        //   131: dup            
        //   132: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //   135: iconst_0       
        //   136: i2d            
        //   137: ddiv           
        //   138: putfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //   141: aload           21
        //   143: dup            
        //   144: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   147: iconst_0       
        //   148: i2d            
        //   149: ddiv           
        //   150: putfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   153: aload           21
        //   155: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111096_b:()F
        //   158: f2d            
        //   159: dstore          24
        //   161: dload           24
        //   163: dconst_0       
        //   164: dcmpl          
        //   165: ifle            183
        //   168: aload           21
        //   170: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111095_a:()V
        //   173: aload           23
        //   175: aload           21
        //   177: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111094_b:(Lnet/minecraft/command/CommandSpreadPlayers$Position;)V
        //   180: goto            198
        //   183: aload           23
        //   185: aload           5
        //   187: dload           6
        //   189: dload           8
        //   191: dload           10
        //   193: dload           12
        //   195: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111097_a:(Ljava/util/Random;DDDD)V
        //   198: aload           23
        //   200: dload           6
        //   202: dload           8
        //   204: dload           10
        //   206: dload           12
        //   208: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111093_a:(DDDD)Z
        //   211: ifeq            214
        //   214: iinc            22, 1
        //   217: iconst_0       
        //   218: aload           14
        //   220: arraylength    
        //   221: if_icmplt       16
        //   224: iconst_1       
        //   225: ifne            280
        //   228: aload           14
        //   230: astore          22
        //   232: aload           14
        //   234: arraylength    
        //   235: istore          23
        //   237: goto            274
        //   240: aload           22
        //   242: iconst_0       
        //   243: aaload         
        //   244: astore          21
        //   246: aload           21
        //   248: aload           4
        //   250: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111098_b:(Lnet/minecraft/world/World;)Z
        //   253: ifne            271
        //   256: aload           21
        //   258: aload           5
        //   260: dload           6
        //   262: dload           8
        //   264: dload           10
        //   266: dload           12
        //   268: invokevirtual   net/minecraft/command/CommandSpreadPlayers$Position.func_111097_a:(Ljava/util/Random;DDDD)V
        //   271: iinc            20, 1
        //   274: iconst_0       
        //   275: iload           23
        //   277: if_icmplt       240
        //   280: iinc            19, 1
        //   283: iconst_0       
        //   284: sipush          10000
        //   287: if_icmpge       294
        //   290: iconst_1       
        //   291: ifne            8
        //   294: iconst_0       
        //   295: sipush          10000
        //   298: if_icmplt       390
        //   301: new             Lnet/minecraft/command/CommandException;
        //   304: dup            
        //   305: new             Ljava/lang/StringBuilder;
        //   308: dup            
        //   309: ldc_w           "commands.spreadplayers.failure."
        //   312: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   315: iload           15
        //   317: ifeq            325
        //   320: ldc             "teams"
        //   322: goto            327
        //   325: ldc             "players"
        //   327: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   330: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   333: iconst_4       
        //   334: anewarray       Ljava/lang/Object;
        //   337: dup            
        //   338: iconst_0       
        //   339: aload           14
        //   341: arraylength    
        //   342: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   345: aastore        
        //   346: dup            
        //   347: iconst_1       
        //   348: aload_1        
        //   349: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111101_a:D
        //   352: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   355: aastore        
        //   356: dup            
        //   357: iconst_2       
        //   358: aload_1        
        //   359: getfield        net/minecraft/command/CommandSpreadPlayers$Position.field_111100_b:D
        //   362: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   365: aastore        
        //   366: dup            
        //   367: iconst_3       
        //   368: ldc             "%.2f"
        //   370: iconst_1       
        //   371: anewarray       Ljava/lang/Object;
        //   374: dup            
        //   375: iconst_0       
        //   376: dload           17
        //   378: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   381: aastore        
        //   382: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   385: aastore        
        //   386: invokespecial   net/minecraft/command/CommandException.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   389: athrow         
        //   390: iconst_0       
        //   391: ireturn        
        //    Exceptions:
        //  throws net.minecraft.command.CommandException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private double func_110671_a(final List list, final World world, final Position[] array, final boolean b) {
        double n = 0.0;
        final HashMap hashMap = Maps.newHashMap();
        while (0 < list.size()) {
            final Entity entity = list.get(0);
            Position position;
            if (b) {
                final Team team = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getTeam() : null;
                if (!hashMap.containsKey(team)) {
                    final HashMap<Team, Position> hashMap2 = (HashMap<Team, Position>)hashMap;
                    final Team team2 = team;
                    final int n2 = 0;
                    int n3 = 0;
                    ++n3;
                    hashMap2.put(team2, array[n2]);
                }
                position = hashMap.get(team);
            }
            else {
                final int n4 = 0;
                int n3 = 0;
                ++n3;
                position = array[n4];
            }
            entity.setPositionAndUpdate(MathHelper.floor_double(position.field_111101_a) + 0.5f, position.func_111092_a(world), MathHelper.floor_double(position.field_111100_b) + 0.5);
            double min = Double.MAX_VALUE;
            while (0 < array.length) {
                if (position != array[0]) {
                    min = Math.min(position.func_111099_a(array[0]), min);
                }
                int n5 = 0;
                ++n5;
            }
            n += min;
            int n6 = 0;
            ++n6;
        }
        return n / list.size();
    }
    
    private Position[] func_110670_a(final Random random, final int n, final double n2, final double n3, final double n4, final double n5) {
        final Position[] array = new Position[n];
        while (0 < array.length) {
            final Position position = new Position();
            position.func_111097_a(random, n2, n3, n4, n5);
            array[0] = position;
            int n6 = 0;
            ++n6;
        }
        return array;
    }
    
    static {
        __OBFID = "CL_00001080";
    }
    
    static class Position
    {
        double field_111101_a;
        double field_111100_b;
        private static final String __OBFID;
        
        Position() {
        }
        
        Position(final double field_111101_a, final double field_111100_b) {
            this.field_111101_a = field_111101_a;
            this.field_111100_b = field_111100_b;
        }
        
        double func_111099_a(final Position position) {
            final double n = this.field_111101_a - position.field_111101_a;
            final double n2 = this.field_111100_b - position.field_111100_b;
            return Math.sqrt(n * n + n2 * n2);
        }
        
        void func_111095_a() {
            final double n = this.func_111096_b();
            this.field_111101_a /= n;
            this.field_111100_b /= n;
        }
        
        float func_111096_b() {
            return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
        }
        
        public void func_111094_b(final Position position) {
            this.field_111101_a -= position.field_111101_a;
            this.field_111100_b -= position.field_111100_b;
        }
        
        public boolean func_111093_a(final double field_111101_a, final double field_111100_b, final double field_111101_a2, final double field_111100_b2) {
            if (this.field_111101_a < field_111101_a) {
                this.field_111101_a = field_111101_a;
            }
            else if (this.field_111101_a > field_111101_a2) {
                this.field_111101_a = field_111101_a2;
            }
            if (this.field_111100_b < field_111100_b) {
                this.field_111100_b = field_111100_b;
            }
            else if (this.field_111100_b > field_111100_b2) {
                this.field_111100_b = field_111100_b2;
            }
            return true;
        }
        
        public int func_111092_a(final World world) {
            BlockPos offsetDown = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            while (offsetDown.getY() > 0) {
                offsetDown = offsetDown.offsetDown();
                if (world.getBlockState(offsetDown).getBlock().getMaterial() != Material.air) {
                    return offsetDown.getY() + 1;
                }
            }
            return 257;
        }
        
        public boolean func_111098_b(final World world) {
            BlockPos offsetDown = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            while (offsetDown.getY() > 0) {
                offsetDown = offsetDown.offsetDown();
                final Material material = world.getBlockState(offsetDown).getBlock().getMaterial();
                if (material != Material.air) {
                    return !material.isLiquid() && material != Material.fire;
                }
            }
            return false;
        }
        
        public void func_111097_a(final Random random, final double n, final double n2, final double n3, final double n4) {
            this.field_111101_a = MathHelper.getRandomDoubleInRange(random, n, n3);
            this.field_111100_b = MathHelper.getRandomDoubleInRange(random, n2, n4);
        }
        
        static {
            __OBFID = "CL_00001105";
        }
    }
}
