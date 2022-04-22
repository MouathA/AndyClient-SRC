package net.minecraft.profiler;

import java.net.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.lang.management.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private final Map field_152773_a;
    private final Map field_152774_b;
    private final String uniqueID;
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final Timer threadTrigger;
    private final Object syncLock;
    private final long minecraftStartTimeMilis;
    private boolean isRunning;
    private int selfCounter;
    private static final String __OBFID;
    
    public PlayerUsageSnooper(final String s, final IPlayerUsage playerStatsCollector, final long minecraftStartTimeMilis) {
        this.field_152773_a = Maps.newHashMap();
        this.field_152774_b = Maps.newHashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();
        this.serverUrl = new URL("http://snoop.minecraft.net/" + s + "?version=" + 2);
        this.playerStatsCollector = playerStatsCollector;
        this.minecraftStartTimeMilis = minecraftStartTimeMilis;
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.func_152766_h();
            this.threadTrigger.schedule(new TimerTask() {
                private static final String __OBFID;
                final PlayerUsageSnooper this$0;
                
                @Override
                public void run() {
                    if (PlayerUsageSnooper.access$0(this.this$0).isSnooperEnabled()) {
                        // monitorenter(access$1 = PlayerUsageSnooper.access$1(this.this$0))
                        final HashMap hashMap = Maps.newHashMap(PlayerUsageSnooper.access$2(this.this$0));
                        if (PlayerUsageSnooper.access$3(this.this$0) == 0) {
                            hashMap.putAll(PlayerUsageSnooper.access$4(this.this$0));
                        }
                        hashMap.put("snooper_count", PlayerUsageSnooper.access$308(this.this$0));
                        hashMap.put("snooper_token", PlayerUsageSnooper.access$5(this.this$0));
                        // monitorexit(access$1)
                        HttpUtil.postMap(PlayerUsageSnooper.access$6(this.this$0), hashMap, true);
                    }
                }
                
                static {
                    __OBFID = "CL_00001516";
                }
            }, 0L, 900000L);
        }
    }
    
    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", System.getProperty("os.name"));
        this.addStatToSnooper("os_version", System.getProperty("os.version"));
        this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
        this.addStatToSnooper("java_version", System.getProperty("java.version"));
        this.addStatToSnooper("version", "1.8");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    private void addJvmArgsToSnooper() {
        for (final String s : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (s.startsWith("-X")) {
                final StringBuilder sb = new StringBuilder("jvm_arg[");
                final int n = 0;
                int n2 = 0;
                ++n2;
                this.addClientStat(sb.append(n).append("]").toString(), s);
            }
        }
        this.addClientStat("jvm_args", 0);
    }
    
    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
        this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
        this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
        this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    public void addClientStat(final String s, final Object o) {
        final Object syncLock = this.syncLock;
        // monitorenter(syncLock2 = this.syncLock)
        this.field_152774_b.put(s, o);
    }
    // monitorexit(syncLock2)
    
    public void addStatToSnooper(final String s, final Object o) {
        final Object syncLock = this.syncLock;
        // monitorenter(syncLock2 = this.syncLock)
        this.field_152773_a.put(s, o);
    }
    // monitorexit(syncLock2)
    
    public Map getCurrentStats() {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final Object syncLock = this.syncLock;
        // monitorenter(syncLock2 = this.syncLock)
        this.addMemoryStatsToSnooper();
        for (final Map.Entry<Object, V> entry : this.field_152773_a.entrySet()) {
            linkedHashMap.put(entry.getKey(), entry.getValue().toString());
        }
        for (final Map.Entry<Object, V> entry2 : this.field_152774_b.entrySet()) {
            linkedHashMap.put(entry2.getKey(), entry2.getValue().toString());
        }
        // monitorexit(syncLock2)
        return (LinkedHashMap<Object, String>)linkedHashMap;
    }
    
    public boolean isSnooperRunning() {
        return this.isRunning;
    }
    
    public void stopSnooper() {
        this.threadTrigger.cancel();
    }
    
    public String getUniqueID() {
        return this.uniqueID;
    }
    
    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
    
    static int access$308(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter++;
    }
    
    static IPlayerUsage access$0(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.playerStatsCollector;
    }
    
    static Object access$1(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.syncLock;
    }
    
    static Map access$2(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152774_b;
    }
    
    static int access$3(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter;
    }
    
    static Map access$4(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152773_a;
    }
    
    static String access$5(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.uniqueID;
    }
    
    static URL access$6(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.serverUrl;
    }
    
    static {
        __OBFID = "CL_00001515";
    }
}
