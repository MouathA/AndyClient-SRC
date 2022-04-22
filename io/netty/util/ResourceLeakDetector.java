package io.netty.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.lang.ref.*;
import java.util.*;

public final class ResourceLeakDetector
{
    private static final String PROP_LEVEL = "io.netty.leakDetectionLevel";
    private static final Level DEFAULT_LEVEL;
    private static Level level;
    private static final InternalLogger logger;
    private static final int DEFAULT_SAMPLING_INTERVAL = 113;
    private final DefaultResourceLeak head;
    private final DefaultResourceLeak tail;
    private final ReferenceQueue refQueue;
    private final ConcurrentMap reportedLeaks;
    private final String resourceType;
    private final int samplingInterval;
    private final long maxActive;
    private long active;
    private final AtomicBoolean loggedTooManyActive;
    private long leakCheckCnt;
    
    @Deprecated
    public static void setEnabled(final boolean b) {
        setLevel(b ? Level.SIMPLE : Level.DISABLED);
    }
    
    public static boolean isEnabled() {
        return getLevel().ordinal() > Level.DISABLED.ordinal();
    }
    
    public static void setLevel(final Level level) {
        if (level == null) {
            throw new NullPointerException("level");
        }
        ResourceLeakDetector.level = level;
    }
    
    public static Level getLevel() {
        return ResourceLeakDetector.level;
    }
    
    public ResourceLeakDetector(final Class clazz) {
        this(StringUtil.simpleClassName(clazz));
    }
    
    public ResourceLeakDetector(final String s) {
        this(s, 113, Long.MAX_VALUE);
    }
    
    public ResourceLeakDetector(final Class clazz, final int n, final long n2) {
        this(StringUtil.simpleClassName(clazz), n, n2);
    }
    
    public ResourceLeakDetector(final String resourceType, final int samplingInterval, final long maxActive) {
        this.head = new DefaultResourceLeak(null);
        this.tail = new DefaultResourceLeak(null);
        this.refQueue = new ReferenceQueue();
        this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
        this.loggedTooManyActive = new AtomicBoolean();
        if (resourceType == null) {
            throw new NullPointerException("resourceType");
        }
        if (samplingInterval <= 0) {
            throw new IllegalArgumentException("samplingInterval: " + samplingInterval + " (expected: 1+)");
        }
        if (maxActive <= 0L) {
            throw new IllegalArgumentException("maxActive: " + maxActive + " (expected: 1+)");
        }
        this.resourceType = resourceType;
        this.samplingInterval = samplingInterval;
        this.maxActive = maxActive;
        DefaultResourceLeak.access$002(this.head, this.tail);
        DefaultResourceLeak.access$102(this.tail, this.head);
    }
    
    public ResourceLeak open(final Object o) {
        final Level level = ResourceLeakDetector.level;
        if (level == Level.DISABLED) {
            return null;
        }
        if (level.ordinal() >= Level.PARANOID.ordinal()) {
            this.reportLeak(level);
            return new DefaultResourceLeak(o);
        }
        if (this.leakCheckCnt++ % this.samplingInterval == 0L) {
            this.reportLeak(level);
            return new DefaultResourceLeak(o);
        }
        return null;
    }
    
    private void reportLeak(final Level level) {
        if (!ResourceLeakDetector.logger.isErrorEnabled()) {
            while (true) {
                final DefaultResourceLeak defaultResourceLeak = (DefaultResourceLeak)this.refQueue.poll();
                if (defaultResourceLeak == null) {
                    break;
                }
                defaultResourceLeak.close();
            }
            return;
        }
        if (this.active * ((level == Level.PARANOID) ? 1 : this.samplingInterval) > this.maxActive && this.loggedTooManyActive.compareAndSet(false, true)) {
            ResourceLeakDetector.logger.error("LEAK: You are creating too many " + this.resourceType + " instances.  " + this.resourceType + " is a shared resource that must be reused across the JVM," + "so that only a few instances are created.");
        }
        while (true) {
            final DefaultResourceLeak defaultResourceLeak2 = (DefaultResourceLeak)this.refQueue.poll();
            if (defaultResourceLeak2 == null) {
                break;
            }
            defaultResourceLeak2.clear();
            if (!defaultResourceLeak2.close()) {
                continue;
            }
            final String string = defaultResourceLeak2.toString();
            if (this.reportedLeaks.putIfAbsent(string, Boolean.TRUE) != null) {
                continue;
            }
            if (string.isEmpty()) {
                ResourceLeakDetector.logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel()", this.resourceType, "io.netty.leakDetectionLevel", Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this));
            }
            else {
                ResourceLeakDetector.logger.error("LEAK: {}.release() was not called before it's garbage-collected.{}", this.resourceType, string);
            }
        }
    }
    
    static String newRecord(int n) {
        final StringBuilder sb = new StringBuilder(4096);
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        while (0 < stackTrace.length) {
            final StackTraceElement stackTraceElement = stackTrace[0];
            if (n > 0) {
                --n;
            }
            else {
                final String string = stackTraceElement.toString();
                final String[] stack_TRACE_ELEMENT_EXCLUSIONS = ResourceLeakDetector.STACK_TRACE_ELEMENT_EXCLUSIONS;
                while (0 < stack_TRACE_ELEMENT_EXCLUSIONS.length && !string.startsWith(stack_TRACE_ELEMENT_EXCLUSIONS[0])) {
                    int n2 = 0;
                    ++n2;
                }
                if (!true) {
                    sb.append('\t');
                    sb.append(string);
                    sb.append(StringUtil.NEWLINE);
                }
            }
            int n3 = 0;
            ++n3;
        }
        return sb.toString();
    }
    
    static ReferenceQueue access$200(final ResourceLeakDetector resourceLeakDetector) {
        return resourceLeakDetector.refQueue;
    }
    
    static DefaultResourceLeak access$300(final ResourceLeakDetector resourceLeakDetector) {
        return resourceLeakDetector.head;
    }
    
    static long access$408(final ResourceLeakDetector resourceLeakDetector) {
        return resourceLeakDetector.active++;
    }
    
    static long access$410(final ResourceLeakDetector resourceLeakDetector) {
        return resourceLeakDetector.active--;
    }
    
    static {
        DEFAULT_LEVEL = Level.SIMPLE;
        logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            ResourceLeakDetector.logger.debug("-Dio.netty.noResourceLeakDetection: {}", (Object)false);
            ResourceLeakDetector.logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "io.netty.leakDetectionLevel", ResourceLeakDetector.DEFAULT_LEVEL.name().toLowerCase());
        }
        final String upperCase = SystemPropertyUtil.get("io.netty.leakDetectionLevel", (false ? Level.DISABLED : ResourceLeakDetector.DEFAULT_LEVEL).name()).trim().toUpperCase();
        Level default_LEVEL = ResourceLeakDetector.DEFAULT_LEVEL;
        for (final Level level : EnumSet.allOf(Level.class)) {
            if (upperCase.equals(level.name()) || upperCase.equals(String.valueOf(level.ordinal()))) {
                default_LEVEL = level;
            }
        }
        ResourceLeakDetector.level = default_LEVEL;
        if (ResourceLeakDetector.logger.isDebugEnabled()) {
            ResourceLeakDetector.logger.debug("-D{}: {}", "io.netty.leakDetectionLevel", default_LEVEL.name().toLowerCase());
        }
        ResourceLeakDetector.STACK_TRACE_ELEMENT_EXCLUSIONS = new String[] { "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(" };
    }
    
    private final class DefaultResourceLeak extends PhantomReference implements ResourceLeak
    {
        private static final int MAX_RECORDS = 4;
        private final String creationRecord;
        private final Deque lastRecords;
        private final AtomicBoolean freed;
        private DefaultResourceLeak prev;
        private DefaultResourceLeak next;
        final ResourceLeakDetector this$0;
        
        DefaultResourceLeak(final ResourceLeakDetector this$0, final Object o) {
            this.this$0 = this$0;
            super(o, (o != null) ? ResourceLeakDetector.access$200(this$0) : null);
            this.lastRecords = new ArrayDeque();
            if (o != null) {
                if (ResourceLeakDetector.getLevel().ordinal() >= Level.ADVANCED.ordinal()) {
                    this.creationRecord = ResourceLeakDetector.newRecord(3);
                }
                else {
                    this.creationRecord = null;
                }
                // monitorenter(access$300 = ResourceLeakDetector.access$300(this$0))
                this.prev = ResourceLeakDetector.access$300(this$0);
                this.next = ResourceLeakDetector.access$300(this$0).next;
                ResourceLeakDetector.access$300(this$0).next.prev = this;
                ResourceLeakDetector.access$300(this$0).next = this;
                ResourceLeakDetector.access$408(this$0);
                // monitorexit(access$300)
                this.freed = new AtomicBoolean();
            }
            else {
                this.creationRecord = null;
                this.freed = new AtomicBoolean(true);
            }
        }
        
        @Override
        public void record() {
            if (this.creationRecord != null) {
                final String record = ResourceLeakDetector.newRecord(2);
                // monitorenter(lastRecords = this.lastRecords)
                final int size = this.lastRecords.size();
                if (size == 0 || !this.lastRecords.getLast().equals(record)) {
                    this.lastRecords.add(record);
                }
                if (size > 4) {
                    this.lastRecords.removeFirst();
                }
            }
            // monitorexit(lastRecords)
        }
        
        @Override
        public boolean close() {
            if (this.freed.compareAndSet(false, true)) {
                // monitorenter(access$300 = ResourceLeakDetector.access$300(this.this$0))
                ResourceLeakDetector.access$410(this.this$0);
                this.prev.next = this.next;
                this.next.prev = this.prev;
                this.prev = null;
                this.next = null;
                // monitorexit(access$300)
                return true;
            }
            return false;
        }
        
        @Override
        public String toString() {
            if (this.creationRecord == null) {
                return "";
            }
            // monitorenter(lastRecords = this.lastRecords)
            final Object[] array = this.lastRecords.toArray();
            // monitorexit(lastRecords)
            final StringBuilder sb = new StringBuilder(16384);
            sb.append(StringUtil.NEWLINE);
            sb.append("Recent access records: ");
            sb.append(array.length);
            sb.append(StringUtil.NEWLINE);
            if (array.length > 0) {
                for (int i = array.length - 1; i >= 0; --i) {
                    sb.append('#');
                    sb.append(i + 1);
                    sb.append(':');
                    sb.append(StringUtil.NEWLINE);
                    sb.append(array[i]);
                }
            }
            sb.append("Created at:");
            sb.append(StringUtil.NEWLINE);
            sb.append(this.creationRecord);
            sb.setLength(sb.length() - StringUtil.NEWLINE.length());
            return sb.toString();
        }
        
        static DefaultResourceLeak access$002(final DefaultResourceLeak defaultResourceLeak, final DefaultResourceLeak next) {
            return defaultResourceLeak.next = next;
        }
        
        static DefaultResourceLeak access$102(final DefaultResourceLeak defaultResourceLeak, final DefaultResourceLeak prev) {
            return defaultResourceLeak.prev = prev;
        }
    }
    
    public enum Level
    {
        DISABLED("DISABLED", 0), 
        SIMPLE("SIMPLE", 1), 
        ADVANCED("ADVANCED", 2), 
        PARANOID("PARANOID", 3);
        
        private static final Level[] $VALUES;
        
        private Level(final String s, final int n) {
        }
        
        static {
            $VALUES = new Level[] { Level.DISABLED, Level.SIMPLE, Level.ADVANCED, Level.PARANOID };
        }
    }
}
