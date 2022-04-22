package net.minecraft.crash;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class CrashReportCategory
{
    private final CrashReport theCrashReport;
    private final String name;
    private final List children;
    private StackTraceElement[] stackTrace;
    private static final String __OBFID;
    
    public CrashReportCategory(final CrashReport theCrashReport, final String name) {
        this.children = Lists.newArrayList();
        this.stackTrace = new StackTraceElement[0];
        this.theCrashReport = theCrashReport;
        this.name = name;
    }
    
    public static String getCoordinateInfo(final double n, final double n2, final double n3) {
        return String.format("%.2f,%.2f,%.2f - %s", n, n2, n3, getCoordinateInfo(new BlockPos(n, n2, n3)));
    }
    
    public static String getCoordinateInfo(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("World: (%d,%d,%d)", x, y, z));
        sb.append(", ");
        final int n = x >> 4;
        final int n2 = z >> 4;
        sb.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", x & 0xF, y >> 4, z & 0xF, n, n2, n << 4, n2 << 4, (n + 1 << 4) - 1, (n2 + 1 << 4) - 1));
        sb.append(", ");
        final int n3 = x >> 9;
        final int n4 = z >> 9;
        sb.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", n3, n4, n3 << 5, n4 << 5, (n3 + 1 << 5) - 1, (n4 + 1 << 5) - 1, n3 << 9, n4 << 9, (n3 + 1 << 9) - 1, (n4 + 1 << 9) - 1));
        return sb.toString();
    }
    
    public void addCrashSectionCallable(final String s, final Callable callable) {
        this.addCrashSection(s, callable.call());
    }
    
    public void addCrashSection(final String s, final Object o) {
        this.children.add(new Entry(s, o));
    }
    
    public void addCrashSectionThrowable(final String s, final Throwable t) {
        this.addCrashSection(s, t);
    }
    
    public int getPrunedStackTrace(final int n) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length <= 0) {
            return 0;
        }
        System.arraycopy(stackTrace, 3 + n, this.stackTrace = new StackTraceElement[stackTrace.length - 3 - n], 0, this.stackTrace.length);
        return this.stackTrace.length;
    }
    
    public boolean firstTwoElementsOfStackTraceMatch(final StackTraceElement stackTraceElement, final StackTraceElement stackTraceElement2) {
        if (this.stackTrace.length == 0 || stackTraceElement == null) {
            return false;
        }
        final StackTraceElement stackTraceElement3 = this.stackTrace[0];
        if (stackTraceElement3.isNativeMethod() != stackTraceElement.isNativeMethod() || !stackTraceElement3.getClassName().equals(stackTraceElement.getClassName()) || !stackTraceElement3.getFileName().equals(stackTraceElement.getFileName()) || !stackTraceElement3.getMethodName().equals(stackTraceElement.getMethodName())) {
            return false;
        }
        if (stackTraceElement2 != null != this.stackTrace.length > 1) {
            return false;
        }
        if (stackTraceElement2 != null && !this.stackTrace[1].equals(stackTraceElement2)) {
            return false;
        }
        this.stackTrace[0] = stackTraceElement;
        return true;
    }
    
    public void trimStackTraceEntriesFromBottom(final int n) {
        final StackTraceElement[] stackTrace = new StackTraceElement[this.stackTrace.length - n];
        System.arraycopy(this.stackTrace, 0, stackTrace, 0, stackTrace.length);
        this.stackTrace = stackTrace;
    }
    
    public void appendToStringBuilder(final StringBuilder sb) {
        sb.append("-- ").append(this.name).append(" --\n");
        sb.append("Details:");
        for (final Entry entry : this.children) {
            sb.append("\n\t");
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            sb.append("\nStacktrace:");
            final StackTraceElement[] stackTrace = this.stackTrace;
            while (0 < stackTrace.length) {
                final StackTraceElement stackTraceElement = stackTrace[0];
                sb.append("\n\tat ");
                sb.append(stackTraceElement.toString());
                int n = 0;
                ++n;
            }
        }
    }
    
    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }
    
    public static void addBlockInfo(final CrashReportCategory crashReportCategory, final BlockPos blockPos, final Block block, final int n) {
        crashReportCategory.addCrashSectionCallable("Block type", new Callable(block) {
            private static final String __OBFID;
            private final int val$var4;
            private final Block val$blockIn;
            
            @Override
            public String call() {
                return String.format("ID #%d (%s // %s)", this.val$var4, this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName());
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001426";
            }
        });
        crashReportCategory.addCrashSectionCallable("Block data value", new Callable() {
            private static final String __OBFID;
            private final int val$blockData;
            
            @Override
            public String call() {
                if (this.val$blockData < 0) {
                    return "Unknown? (Got " + this.val$blockData + ")";
                }
                return String.format("%1$d / 0x%1$X / 0b%2$s", this.val$blockData, String.format("%4s", Integer.toBinaryString(this.val$blockData)).replace(" ", "0"));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001441";
            }
        });
        crashReportCategory.addCrashSectionCallable("Block location", new Callable() {
            private static final String __OBFID;
            private final BlockPos val$pos;
            
            @Override
            public String call() {
                return CrashReportCategory.getCoordinateInfo(this.val$pos);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00001465";
            }
        });
    }
    
    public static void addBlockInfo(final CrashReportCategory crashReportCategory, final BlockPos blockPos, final IBlockState blockState) {
        crashReportCategory.addCrashSectionCallable("Block", new Callable() {
            private static final String __OBFID;
            private final IBlockState val$state;
            
            public String func_175753_a() {
                return this.val$state.toString();
            }
            
            @Override
            public Object call() {
                return this.func_175753_a();
            }
            
            static {
                __OBFID = "CL_00002617";
            }
        });
        crashReportCategory.addCrashSectionCallable("Block location", new Callable() {
            private static final String __OBFID;
            private final BlockPos val$pos;
            
            public String func_175751_a() {
                return CrashReportCategory.getCoordinateInfo(this.val$pos);
            }
            
            @Override
            public Object call() {
                return this.func_175751_a();
            }
            
            static {
                __OBFID = "CL_00002616";
            }
        });
    }
    
    static {
        __OBFID = "CL_00001409";
    }
    
    static class Entry
    {
        private final String key;
        private final String value;
        private static final String __OBFID;
        
        public Entry(final String key, final Object o) {
            this.key = key;
            if (o == null) {
                this.value = "~~NULL~~";
            }
            else if (o instanceof Throwable) {
                final Throwable t = (Throwable)o;
                this.value = "~~ERROR~~ " + t.getClass().getSimpleName() + ": " + t.getMessage();
            }
            else {
                this.value = o.toString();
            }
        }
        
        public String getKey() {
            return this.key;
        }
        
        public String getValue() {
            return this.value;
        }
        
        static {
            __OBFID = "CL_00001489";
        }
    }
}
