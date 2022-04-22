package net.minecraft.profiler;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import optifine.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class Profiler
{
    private static final Logger logger;
    private final List sectionList;
    private final List timestampList;
    public boolean profilingEnabled;
    private String profilingSection;
    private final Map profilingMap;
    private static final String __OBFID;
    public boolean profilerGlobalEnabled;
    private boolean profilerLocalEnabled;
    private static final String SCHEDULED_EXECUTABLES;
    private static final String TICK;
    private static final String PRE_RENDER_ERRORS;
    private static final String RENDER;
    private static final String DISPLAY;
    private static final int HASH_SCHEDULED_EXECUTABLES;
    private static final int HASH_TICK;
    private static final int HASH_PRE_RENDER_ERRORS;
    private static final int HASH_RENDER;
    private static final int HASH_DISPLAY;
    
    static {
        RENDER = "render";
        TICK = "tick";
        __OBFID = "CL_00001497";
        SCHEDULED_EXECUTABLES = "scheduledExecutables";
        PRE_RENDER_ERRORS = "preRenderErrors";
        DISPLAY = "display";
        logger = LogManager.getLogger();
        HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
        HASH_TICK = "tick".hashCode();
        HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
        HASH_RENDER = "render".hashCode();
        HASH_DISPLAY = "display".hashCode();
    }
    
    public Profiler() {
        this.sectionList = Lists.newArrayList();
        this.timestampList = Lists.newArrayList();
        this.profilingSection = "";
        this.profilingMap = Maps.newHashMap();
        this.profilerGlobalEnabled = true;
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }
    
    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = "";
        this.sectionList.clear();
        this.profilerLocalEnabled = this.profilerGlobalEnabled;
    }
    
    public void startSection(final String s) {
        if (Lagometer.isActive()) {
            final int hashCode = s.hashCode();
            if (hashCode == Profiler.HASH_SCHEDULED_EXECUTABLES && s.equals("scheduledExecutables")) {
                Lagometer.timerScheduledExecutables.start();
            }
            else if (hashCode == Profiler.HASH_TICK && s.equals("tick") && Config.isMinecraftThread()) {
                Lagometer.timerScheduledExecutables.end();
                Lagometer.timerTick.start();
            }
            else if (hashCode == Profiler.HASH_PRE_RENDER_ERRORS && s.equals("preRenderErrors")) {
                Lagometer.timerTick.end();
            }
        }
        if (Config.isFastRender()) {
            final int hashCode2 = s.hashCode();
            if (hashCode2 == Profiler.HASH_RENDER && s.equals("render")) {
                GlStateManager.clearEnabled = false;
            }
            else if (hashCode2 == Profiler.HASH_DISPLAY && s.equals("display")) {
                GlStateManager.clearEnabled = true;
            }
        }
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = String.valueOf(this.profilingSection) + ".";
            }
            this.profilingSection = String.valueOf(this.profilingSection) + s;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }
    
    public void endSection() {
        if (this.profilerLocalEnabled && this.profilingEnabled) {
            final long nanoTime = System.nanoTime();
            final long longValue = this.timestampList.remove(this.timestampList.size() - 1);
            this.sectionList.remove(this.sectionList.size() - 1);
            final long n = nanoTime - longValue;
            if (this.profilingMap.containsKey(this.profilingSection)) {
                this.profilingMap.put(this.profilingSection, this.profilingMap.get(this.profilingSection) + n);
            }
            else {
                this.profilingMap.put(this.profilingSection, n);
            }
            if (n > 100000000L) {
                Profiler.logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + n / 1000000.0 + " ms");
            }
            this.profilingSection = (this.sectionList.isEmpty() ? "" : this.sectionList.get(this.sectionList.size() - 1));
        }
    }
    
    public List getProfilingData(String string) {
        if (!(this.profilerLocalEnabled = this.profilerGlobalEnabled)) {
            return new ArrayList(Arrays.asList(new Result("root", 0.0, 0.0)));
        }
        if (!this.profilingEnabled) {
            return null;
        }
        long n = this.profilingMap.containsKey("root") ? this.profilingMap.get("root") : 0L;
        final long n2 = this.profilingMap.containsKey(string) ? this.profilingMap.get(string) : -1L;
        final ArrayList arrayList = Lists.newArrayList();
        if (string.length() > 0) {
            string = String.valueOf(string) + ".";
        }
        long n3 = 0L;
        for (final String s : this.profilingMap.keySet()) {
            if (s.length() > string.length() && s.startsWith(string) && s.indexOf(".", string.length() + 1) < 0) {
                n3 += (long)this.profilingMap.get(s);
            }
        }
        final float n4 = (float)n3;
        if (n3 < n2) {
            n3 = n2;
        }
        if (n < n3) {
            n = n3;
        }
        for (final String s2 : this.profilingMap.keySet()) {
            if (s2.length() > string.length() && s2.startsWith(string) && s2.indexOf(".", string.length() + 1) < 0) {
                final long longValue = this.profilingMap.get(s2);
                arrayList.add(new Result(s2.substring(string.length()), longValue * 100.0 / n3, longValue * 100.0 / n));
            }
        }
        for (final String s3 : this.profilingMap.keySet()) {
            this.profilingMap.put(s3, (long)this.profilingMap.get(s3) * 950L / 1000L);
        }
        if (n3 > n4) {
            arrayList.add(new Result("unspecified", (n3 - n4) * 100.0 / n3, (n3 - n4) * 100.0 / n));
        }
        Collections.sort((List<Comparable>)arrayList);
        arrayList.add(0, new Result(string, 100.0, n3 * 100.0 / n));
        return arrayList;
    }
    
    public void endStartSection(final String s) {
        if (this.profilerLocalEnabled) {
            this.endSection();
            this.startSection(s);
        }
    }
    
    public String getNameOfLastSection() {
        return (this.sectionList.size() == 0) ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
    }
    
    public static final class Result implements Comparable
    {
        public double field_76332_a;
        public double field_76330_b;
        public String field_76331_c;
        
        public Result(final String field_76331_c, final double field_76332_a, final double field_76330_b) {
            this.field_76331_c = field_76331_c;
            this.field_76332_a = field_76332_a;
            this.field_76330_b = field_76330_b;
        }
        
        public int compareTo(final Result result) {
            return (result.field_76332_a < this.field_76332_a) ? -1 : ((result.field_76332_a > this.field_76332_a) ? 1 : result.field_76331_c.compareTo(this.field_76331_c));
        }
        
        public int func_76329_a() {
            return (this.field_76331_c.hashCode() & 0xAAAAAA) + 4473924;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Result)o);
        }
    }
}
