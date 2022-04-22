package net.minecraft.command;

import org.apache.logging.log4j.*;
import net.minecraft.server.*;
import java.text.*;
import java.io.*;
import net.minecraft.profiler.*;
import java.util.*;
import net.minecraft.util.*;

public class CommandDebug extends CommandBase
{
    private static final Logger logger;
    private long field_147206_b;
    private int field_147207_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000270";
        logger = LogManager.getLogger();
    }
    
    @Override
    public String getCommandName() {
        return "debug";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.debug.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.debug.usage", new Object[0]);
        }
        if (array[0].equals("start")) {
            if (array.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            CommandBase.notifyOperators(commandSender, this, "commands.debug.start", new Object[0]);
            MinecraftServer.getServer().enableProfiling();
            this.field_147206_b = MinecraftServer.getCurrentTimeMillis();
            this.field_147207_c = MinecraftServer.getServer().getTickCounter();
        }
        else if (array[0].equals("stop")) {
            if (array.length != 1) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
                throw new CommandException("commands.debug.notStarted", new Object[0]);
            }
            final long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();
            final int tickCounter = MinecraftServer.getServer().getTickCounter();
            final long n = currentTimeMillis - this.field_147206_b;
            final int n2 = tickCounter - this.field_147207_c;
            this.func_147205_a(n, n2);
            MinecraftServer.getServer().theProfiler.profilingEnabled = false;
            CommandBase.notifyOperators(commandSender, this, "commands.debug.stop", n / 1000.0f, n2);
        }
        else if (array[0].equals("chunk")) {
            if (array.length != 4) {
                throw new WrongUsageException("commands.debug.usage", new Object[0]);
            }
            CommandBase.func_175757_a(commandSender, array, 1, true);
        }
    }
    
    private void func_147205_a(final long n, final int n2) {
        final File file = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        file.getParentFile().mkdirs();
        final FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(this.func_147204_b(n, n2));
        fileWriter.close();
    }
    
    private String func_147204_b(final long n, final int n2) {
        final StringBuilder sb = new StringBuilder();
        sb.append("---- Minecraft Profiler Results ----\n");
        sb.append("// ");
        sb.append(func_147203_d());
        sb.append("\n\n");
        sb.append("Time span: ").append(n).append(" ms\n");
        sb.append("Tick span: ").append(n2).append(" ticks\n");
        sb.append("// This is approximately ").append(String.format("%.2f", n2 / (n / 1000.0f))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        sb.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.func_147202_a(0, "root", sb);
        sb.append("--- END PROFILE DUMP ---\n\n");
        return sb.toString();
    }
    
    private void func_147202_a(final int n, final String s, final StringBuilder sb) {
        final List profilingData = MinecraftServer.getServer().theProfiler.getProfilingData(s);
        if (profilingData != null && profilingData.size() >= 3) {
            while (1 < profilingData.size()) {
                final Profiler.Result result = profilingData.get(1);
                sb.append(String.format("[%02d] ", n));
                while (0 < n) {
                    sb.append(" ");
                    int n2 = 0;
                    ++n2;
                }
                sb.append(result.field_76331_c).append(" - ").append(String.format("%.2f", result.field_76332_a)).append("%/").append(String.format("%.2f", result.field_76330_b)).append("%\n");
                if (!result.field_76331_c.equals("unspecified")) {
                    this.func_147202_a(n + 1, String.valueOf(s) + "." + result.field_76331_c, sb);
                }
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    private static String func_147203_d() {
        final String[] array = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
        return array[(int)(System.nanoTime() % array.length)];
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "start", "stop") : null;
    }
}
