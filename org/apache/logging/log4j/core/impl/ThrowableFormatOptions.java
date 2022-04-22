package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.core.helpers.*;
import java.util.*;

public final class ThrowableFormatOptions
{
    private static final int DEFAULT_LINES = Integer.MAX_VALUE;
    protected static final ThrowableFormatOptions DEFAULT;
    private static final String FULL = "full";
    private static final String NONE = "none";
    private static final String SHORT = "short";
    private final int lines;
    private final String separator;
    private final List packages;
    public static final String CLASS_NAME = "short.className";
    public static final String METHOD_NAME = "short.methodName";
    public static final String LINE_NUMBER = "short.lineNumber";
    public static final String FILE_NAME = "short.fileName";
    public static final String MESSAGE = "short.message";
    public static final String LOCALIZED_MESSAGE = "short.localizedMessage";
    
    protected ThrowableFormatOptions(final int lines, final String s, final List packages) {
        this.lines = lines;
        this.separator = ((s == null) ? Constants.LINE_SEP : s);
        this.packages = packages;
    }
    
    protected ThrowableFormatOptions(final List list) {
        this(Integer.MAX_VALUE, null, list);
    }
    
    protected ThrowableFormatOptions() {
        this(Integer.MAX_VALUE, null, null);
    }
    
    public int getLines() {
        return this.lines;
    }
    
    public String getSeparator() {
        return this.separator;
    }
    
    public List getPackages() {
        return this.packages;
    }
    
    public int minLines(final int n) {
        return (this.lines > n) ? n : this.lines;
    }
    
    @Override
    public String toString() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/lang/StringBuilder.<init>:()V
        //     7: astore_1       
        //     8: aload_1        
        //     9: ldc             "{"
        //    11: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    14: aload_0        
        //    15: if_icmpne       23
        //    18: ldc             "full"
        //    20: goto            52
        //    23: aload_0        
        //    24: getfield        org/apache/logging/log4j/core/impl/ThrowableFormatOptions.lines:I
        //    27: iconst_2       
        //    28: if_icmpne       36
        //    31: ldc             "short"
        //    33: goto            52
        //    36: aload_0        
        //    37: ifle            50
        //    40: aload_0        
        //    41: getfield        org/apache/logging/log4j/core/impl/ThrowableFormatOptions.lines:I
        //    44: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    47: goto            52
        //    50: ldc             "none"
        //    52: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    55: ldc             "}"
        //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    60: pop            
        //    61: aload_1        
        //    62: ldc             "{separator("
        //    64: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    67: aload_0        
        //    68: getfield        org/apache/logging/log4j/core/impl/ThrowableFormatOptions.separator:Ljava/lang/String;
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: ldc             ")}"
        //    76: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    79: pop            
        //    80: aload_0        
        //    81: ifnull          152
        //    84: aload_1        
        //    85: ldc             "{filters("
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: pop            
        //    91: aload_0        
        //    92: getfield        org/apache/logging/log4j/core/impl/ThrowableFormatOptions.packages:Ljava/util/List;
        //    95: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   100: astore_2       
        //   101: aload_2        
        //   102: invokeinterface java/util/Iterator.hasNext:()Z
        //   107: ifeq            134
        //   110: aload_2        
        //   111: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   116: checkcast       Ljava/lang/String;
        //   119: astore_3       
        //   120: aload_1        
        //   121: aload_3        
        //   122: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   125: ldc             ","
        //   127: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   130: pop            
        //   131: goto            101
        //   134: aload_1        
        //   135: aload_1        
        //   136: invokevirtual   java/lang/StringBuilder.length:()I
        //   139: iconst_1       
        //   140: isub           
        //   141: invokevirtual   java/lang/StringBuilder.deleteCharAt:(I)Ljava/lang/StringBuilder;
        //   144: pop            
        //   145: aload_1        
        //   146: ldc             ")}"
        //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   151: pop            
        //   152: aload_1        
        //   153: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   156: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static ThrowableFormatOptions newInstance(String[] array) {
        if (array == null || array.length == 0) {
            return ThrowableFormatOptions.DEFAULT;
        }
        if (array.length == 1 && array[0] != null && array[0].length() > 0) {
            final String[] split = array[0].split(",", 2);
            final String trim = split[0].trim();
            final Scanner scanner = new Scanner(trim);
            if (split.length > 1 && (trim.equalsIgnoreCase("full") || trim.equalsIgnoreCase("short") || trim.equalsIgnoreCase("none") || scanner.hasNextInt())) {
                array = new String[] { trim, split[1].trim() };
            }
            scanner.close();
        }
        final int lines = ThrowableFormatOptions.DEFAULT.lines;
        String s = ThrowableFormatOptions.DEFAULT.separator;
        List<String> packages = (List<String>)ThrowableFormatOptions.DEFAULT.packages;
        final String[] array2 = array;
        while (0 < array2.length) {
            final String s2 = array2[0];
            if (s2 != null) {
                final String trim2 = s2.trim();
                if (!trim2.isEmpty()) {
                    if (trim2.startsWith("separator(") && trim2.endsWith(")")) {
                        s = trim2.substring(10, trim2.length() - 1);
                    }
                    else if (trim2.startsWith("filters(") && trim2.endsWith(")")) {
                        final String substring = trim2.substring(8, trim2.length() - 1);
                        if (substring.length() > 0) {
                            final String[] split2 = substring.split(",");
                            if (split2.length > 0) {
                                packages = new ArrayList<String>(split2.length);
                                final String[] array3 = split2;
                                while (0 < array3.length) {
                                    final String trim3 = array3[0].trim();
                                    if (trim3.length() > 0) {
                                        packages.add(trim3);
                                    }
                                    int n = 0;
                                    ++n;
                                }
                            }
                        }
                    }
                    else if (!trim2.equalsIgnoreCase("none")) {
                        if (!trim2.equalsIgnoreCase("short") && !trim2.equalsIgnoreCase("short.className") && !trim2.equalsIgnoreCase("short.methodName") && !trim2.equalsIgnoreCase("short.lineNumber") && !trim2.equalsIgnoreCase("short.fileName") && !trim2.equalsIgnoreCase("short.message") && !trim2.equalsIgnoreCase("short.localizedMessage")) {
                            if (!trim2.equalsIgnoreCase("full")) {
                                Integer.parseInt(trim2);
                            }
                        }
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return new ThrowableFormatOptions(2, s, packages);
    }
    
    static {
        DEFAULT = new ThrowableFormatOptions();
    }
}
