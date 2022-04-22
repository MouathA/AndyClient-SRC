package org.apache.logging.log4j.core.helpers;

import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.status.*;

public final class OptionConverter
{
    private static final Logger LOGGER;
    private static final String DELIM_START = "${";
    private static final char DELIM_STOP = '}';
    private static final int DELIM_START_LEN = 2;
    private static final int DELIM_STOP_LEN = 1;
    private static final int ONE_K = 1024;
    
    private OptionConverter() {
    }
    
    public static String[] concatenateArrays(final String[] array, final String[] array2) {
        final String[] array3 = new String[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static String convertSpecialChars(final String s) {
        final int length = s.length();
        final StringBuilder sb = new StringBuilder(length);
        while (0 < length) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            s.charAt(n);
            if (92 == 92) {
                final int n3 = 0;
                ++n2;
                s.charAt(n3);
                if (92 != 110) {
                    if (92 != 114) {
                        if (92 != 116) {
                            if (92 != 102) {
                                if (92 != 8) {
                                    if (92 != 34) {
                                        if (92 != 39) {
                                            if (92 == 92) {}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sb.append('\\');
        }
        return sb.toString();
    }
    
    public static Object instantiateByKey(final Properties properties, final String s, final Class clazz, final Object o) {
        final String andSubst = findAndSubst(s, properties);
        if (andSubst == null) {
            OptionConverter.LOGGER.error("Could not find value for key " + s);
            return o;
        }
        return instantiateByClassName(andSubst.trim(), clazz, o);
    }
    
    public static boolean toBoolean(final String s, final boolean b) {
        if (s == null) {
            return b;
        }
        final String trim = s.trim();
        return "true".equalsIgnoreCase(trim) || (!"false".equalsIgnoreCase(trim) && b);
    }
    
    public static int toInt(final String s, final int n) {
        if (s != null) {
            return Integer.parseInt(s.trim());
        }
        return n;
    }
    
    public static long toFileSize(final String p0, final long p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: lload_1        
        //     5: lreturn        
        //     6: aload_0        
        //     7: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    10: getstatic       java/util/Locale.ENGLISH:Ljava/util/Locale;
        //    13: invokevirtual   java/lang/String.toUpperCase:(Ljava/util/Locale;)Ljava/lang/String;
        //    16: astore_3       
        //    17: lconst_1       
        //    18: lstore          4
        //    20: aload_3        
        //    21: istore          6
        //    23: iconst_m1      
        //    24: if_icmpeq       43
        //    27: ldc2_w          1024
        //    30: lstore          4
        //    32: aload_3        
        //    33: iconst_0       
        //    34: iload           6
        //    36: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    39: astore_3       
        //    40: goto            86
        //    43: aload_3        
        //    44: istore          6
        //    46: iconst_m1      
        //    47: if_icmpeq       66
        //    50: ldc2_w          1048576
        //    53: lstore          4
        //    55: aload_3        
        //    56: iconst_0       
        //    57: iload           6
        //    59: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    62: astore_3       
        //    63: goto            86
        //    66: aload_3        
        //    67: istore          6
        //    69: iconst_m1      
        //    70: if_icmpeq       86
        //    73: ldc2_w          1073741824
        //    76: lstore          4
        //    78: aload_3        
        //    79: iconst_0       
        //    80: iload           6
        //    82: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    85: astore_3       
        //    86: aload_3        
        //    87: ifnull          166
        //    90: aload_3        
        //    91: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //    94: lload           4
        //    96: lmul           
        //    97: lreturn        
        //    98: astore          7
        //   100: getstatic       org/apache/logging/log4j/core/helpers/OptionConverter.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   103: new             Ljava/lang/StringBuilder;
        //   106: dup            
        //   107: invokespecial   java/lang/StringBuilder.<init>:()V
        //   110: ldc             "["
        //   112: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   115: aload_3        
        //   116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   119: ldc             "] is not in proper int form."
        //   121: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   124: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   127: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;)V
        //   132: getstatic       org/apache/logging/log4j/core/helpers/OptionConverter.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   135: new             Ljava/lang/StringBuilder;
        //   138: dup            
        //   139: invokespecial   java/lang/StringBuilder.<init>:()V
        //   142: ldc             "["
        //   144: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   147: aload_0        
        //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   151: ldc             "] not in expected format."
        //   153: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   156: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   159: aload           7
        //   161: invokeinterface org/apache/logging/log4j/Logger.error:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   166: lload_1        
        //   167: lreturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static String findAndSubst(final String s, final Properties properties) {
        final String property = properties.getProperty(s);
        if (property == null) {
            return null;
        }
        return substVars(property, properties);
    }
    
    public static Object instantiateByClassName(final String s, final Class clazz, final Object o) {
        if (s == null) {
            return o;
        }
        final Class loadClass = Loader.loadClass(s);
        if (!clazz.isAssignableFrom(loadClass)) {
            OptionConverter.LOGGER.error("A \"" + s + "\" object is not assignable to a \"" + clazz.getName() + "\" variable.");
            OptionConverter.LOGGER.error("The class \"" + clazz.getName() + "\" was loaded by ");
            OptionConverter.LOGGER.error("[" + clazz.getClassLoader() + "] whereas object of type ");
            OptionConverter.LOGGER.error("\"" + loadClass.getName() + "\" was loaded by [" + loadClass.getClassLoader() + "].");
            return o;
        }
        return loadClass.newInstance();
    }
    
    public static String substVars(final String s, final Properties properties) throws IllegalArgumentException {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            int index = s.indexOf("${", 0);
            if (index == -1) {
                if (!false) {
                    return s;
                }
                sb.append(s.substring(0, s.length()));
                return sb.toString();
            }
            else {
                sb.append(s.substring(0, index));
                final int index2 = s.indexOf(125, index);
                if (index2 == -1) {
                    throw new IllegalArgumentException('\"' + s + "\" has no closing brace. Opening brace at position " + index + '.');
                }
                index += 2;
                final String substring = s.substring(index, index2);
                String s2 = PropertiesUtil.getProperties().getStringProperty(substring, null);
                if (s2 == null && properties != null) {
                    s2 = properties.getProperty(substring);
                }
                if (s2 == null) {
                    continue;
                }
                sb.append(substVars(s2, properties));
            }
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
