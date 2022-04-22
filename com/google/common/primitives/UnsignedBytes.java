package com.google.common.primitives;

import com.google.common.base.*;
import java.util.*;
import com.google.common.annotations.*;
import sun.misc.*;
import java.nio.*;

public final class UnsignedBytes
{
    public static final byte MAX_POWER_OF_TWO = Byte.MIN_VALUE;
    public static final byte MAX_VALUE = -1;
    private static final int UNSIGNED_MASK = 255;
    
    private UnsignedBytes() {
    }
    
    public static int toInt(final byte b) {
        return b & 0xFF;
    }
    
    public static byte checkedCast(final long n) {
        if (n >> 8 != 0L) {
            throw new IllegalArgumentException("Out of range: " + n);
        }
        return (byte)n;
    }
    
    public static byte saturatedCast(final long n) {
        if (n > toInt((byte)(-1))) {
            return -1;
        }
        if (n < 0L) {
            return 0;
        }
        return (byte)n;
    }
    
    public static int compare(final byte b, final byte b2) {
        return toInt(b) - toInt(b2);
    }
    
    public static byte min(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        int int1 = toInt(array[0]);
        while (1 < array.length) {
            final int int2 = toInt(array[1]);
            if (int2 < int1) {
                int1 = int2;
            }
            int n = 0;
            ++n;
        }
        return (byte)int1;
    }
    
    public static byte max(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        int int1 = toInt(array[0]);
        while (1 < array.length) {
            final int int2 = toInt(array[1]);
            if (int2 > int1) {
                int1 = int2;
            }
            int n = 0;
            ++n;
        }
        return (byte)int1;
    }
    
    @Beta
    public static String toString(final byte b) {
        return toString(b, 10);
    }
    
    @Beta
    public static String toString(final byte b, final int n) {
        Preconditions.checkArgument(n >= 2 && n <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", n);
        return Integer.toString(toInt(b), n);
    }
    
    @Beta
    public static byte parseUnsignedByte(final String s) {
        return parseUnsignedByte(s, 10);
    }
    
    @Beta
    public static byte parseUnsignedByte(final String s, final int n) {
        final int int1 = Integer.parseInt((String)Preconditions.checkNotNull(s), n);
        if (int1 >> 8 == 0) {
            return (byte)int1;
        }
        throw new NumberFormatException("out of range: " + int1);
    }
    
    public static String join(final String s, final byte... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * (3 + s.length()));
        sb.append(toInt(array[0]));
        while (1 < array.length) {
            sb.append(s).append(toString(array[1]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Comparator lexicographicalComparator() {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }
    
    @VisibleForTesting
    static Comparator lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }
    
    @VisibleForTesting
    static class LexicographicalComparatorHolder
    {
        static final String UNSAFE_COMPARATOR_NAME;
        static final Comparator BEST_COMPARATOR;
        
        static Comparator getBestComparator() {
            return (Comparator)Class.forName(LexicographicalComparatorHolder.UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
        }
        
        static {
            UNSAFE_COMPARATOR_NAME = LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator";
            BEST_COMPARATOR = getBestComparator();
        }
        
        enum PureJavaComparator implements Comparator
        {
            INSTANCE("INSTANCE", 0);
            
            private static final PureJavaComparator[] $VALUES;
            
            private PureJavaComparator(final String s, final int n) {
            }
            
            public int compare(final byte[] array, final byte[] array2) {
                while (0 < Math.min(array.length, array2.length)) {
                    final int compare = UnsignedBytes.compare(array[0], array2[0]);
                    if (compare != 0) {
                        return compare;
                    }
                    int n = 0;
                    ++n;
                }
                return array.length - array2.length;
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((byte[])o, (byte[])o2);
            }
            
            static {
                $VALUES = new PureJavaComparator[] { PureJavaComparator.INSTANCE };
            }
        }
        
        @VisibleForTesting
        enum UnsafeComparator implements Comparator
        {
            INSTANCE("INSTANCE", 0);
            
            static final boolean BIG_ENDIAN;
            static final Unsafe theUnsafe;
            static final int BYTE_ARRAY_BASE_OFFSET;
            private static final UnsafeComparator[] $VALUES;
            
            private UnsafeComparator(final String s, final int n) {
            }
            
            private static Unsafe getUnsafe() {
                return Unsafe.getUnsafe();
            }
            
            public int compare(final byte[] p0, final byte[] p1) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: arraylength    
                //     2: aload_2        
                //     3: arraylength    
                //     4: invokestatic    java/lang/Math.min:(II)I
                //     7: istore_3       
                //     8: iload_3        
                //     9: bipush          8
                //    11: idiv           
                //    12: istore          4
                //    14: iconst_0       
                //    15: iload           4
                //    17: bipush          8
                //    19: imul           
                //    20: if_icmpge       117
                //    23: getstatic       com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator.theUnsafe:Lsun/misc/Unsafe;
                //    26: aload_1        
                //    27: getstatic       com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator.BYTE_ARRAY_BASE_OFFSET:I
                //    30: i2l            
                //    31: iconst_0       
                //    32: i2l            
                //    33: ladd           
                //    34: invokevirtual   sun/misc/Unsafe.getLong:(Ljava/lang/Object;J)J
                //    37: lstore          6
                //    39: getstatic       com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator.theUnsafe:Lsun/misc/Unsafe;
                //    42: aload_2        
                //    43: getstatic       com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator.BYTE_ARRAY_BASE_OFFSET:I
                //    46: i2l            
                //    47: iconst_0       
                //    48: i2l            
                //    49: ladd           
                //    50: invokevirtual   sun/misc/Unsafe.getLong:(Ljava/lang/Object;J)J
                //    53: lstore          8
                //    55: lload           6
                //    57: lload           8
                //    59: lcmp           
                //    60: ifeq            111
                //    63: getstatic       com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator.BIG_ENDIAN:Z
                //    66: ifeq            77
                //    69: lload           6
                //    71: lload           8
                //    73: invokestatic    com/google/common/primitives/UnsignedLongs.compare:(JJ)I
                //    76: ireturn        
                //    77: lload           6
                //    79: lload           8
                //    81: lxor           
                //    82: invokestatic    java/lang/Long.numberOfTrailingZeros:(J)I
                //    85: bipush          -8
                //    87: iand           
                //    88: istore          10
                //    90: lload           6
                //    92: iload           10
                //    94: lushr          
                //    95: ldc2_w          255
                //    98: land           
                //    99: lload           8
                //   101: iload           10
                //   103: lushr          
                //   104: ldc2_w          255
                //   107: land           
                //   108: lsub           
                //   109: l2i            
                //   110: ireturn        
                //   111: iinc            5, 8
                //   114: goto            14
                //   117: iload           4
                //   119: bipush          8
                //   121: imul           
                //   122: istore          5
                //   124: iconst_0       
                //   125: iload_3        
                //   126: if_icmpge       154
                //   129: aload_1        
                //   130: iconst_0       
                //   131: baload         
                //   132: aload_2        
                //   133: iconst_0       
                //   134: baload         
                //   135: invokestatic    com/google/common/primitives/UnsignedBytes.compare:(BB)I
                //   138: istore          6
                //   140: iload           6
                //   142: ifeq            148
                //   145: iload           6
                //   147: ireturn        
                //   148: iinc            5, 1
                //   151: goto            124
                //   154: aload_1        
                //   155: arraylength    
                //   156: aload_2        
                //   157: arraylength    
                //   158: isub           
                //   159: ireturn        
                // 
                // The error that occurred was:
                // 
                // java.util.ConcurrentModificationException
                //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
                //     at java.util.ArrayList$Itr.next(Unknown Source)
                //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
                //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
                //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
                //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
                //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
                //     at java.lang.Thread.run(Unknown Source)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((byte[])o, (byte[])o2);
            }
            
            static {
                $VALUES = new UnsafeComparator[] { UnsafeComparator.INSTANCE };
                BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
                theUnsafe = getUnsafe();
                BYTE_ARRAY_BASE_OFFSET = UnsafeComparator.theUnsafe.arrayBaseOffset(byte[].class);
                if (UnsafeComparator.theUnsafe.arrayIndexScale(byte[].class) != 1) {
                    throw new AssertionError();
                }
            }
        }
    }
}
