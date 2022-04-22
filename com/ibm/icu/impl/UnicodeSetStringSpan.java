package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.text.*;

public class UnicodeSetStringSpan
{
    public static final int FWD = 32;
    public static final int BACK = 16;
    public static final int UTF16 = 8;
    public static final int CONTAINED = 2;
    public static final int NOT_CONTAINED = 1;
    public static final int ALL = 63;
    public static final int FWD_UTF16_CONTAINED = 42;
    public static final int FWD_UTF16_NOT_CONTAINED = 41;
    public static final int BACK_UTF16_CONTAINED = 26;
    public static final int BACK_UTF16_NOT_CONTAINED = 25;
    static final short ALL_CP_CONTAINED = 255;
    static final short LONG_SPAN = 254;
    private UnicodeSet spanSet;
    private UnicodeSet spanNotSet;
    private ArrayList strings;
    private short[] spanLengths;
    private int maxLength16;
    private boolean all;
    private OffsetList offsets;
    
    public UnicodeSetStringSpan(final UnicodeSet set, final ArrayList strings, final int n) {
        this.spanSet = new UnicodeSet(0, 1114111);
        this.strings = strings;
        this.all = (n == 63);
        this.spanSet.retainAll(set);
        if (0x0 != (n & 0x1)) {
            this.spanNotSet = this.spanSet;
        }
        this.offsets = new OffsetList();
        final int size = this.strings.size();
        int n2 = 0;
        while (0 < size) {
            final String s = this.strings.get(0);
            final int length = s.length();
            if (this.spanSet.span(s, UnicodeSet.SpanCondition.CONTAINED) < length) {}
            if (0x0 != (n & 0x8) && length > this.maxLength16) {
                this.maxLength16 = length;
            }
            ++n2;
        }
        if (!true) {
            this.maxLength16 = 0;
            return;
        }
        if (this.all) {
            this.spanSet.freeze();
        }
        int n3;
        if (this.all) {
            n3 = size * 2;
        }
        else {
            n3 = size;
        }
        this.spanLengths = new short[n3];
        if (this.all) {}
        while (0 < size) {
            final String s2 = this.strings.get(0);
            final int length2 = s2.length();
            final int span = this.spanSet.span(s2, UnicodeSet.SpanCondition.CONTAINED);
            if (span < length2) {
                if (0x0 != (n & 0x8)) {
                    if (0x0 != (n & 0x2)) {
                        if (0x0 != (n & 0x20)) {
                            this.spanLengths[0] = makeSpanLengthByte(span);
                        }
                        if (0x0 != (n & 0x10)) {
                            this.spanLengths[0] = makeSpanLengthByte(length2 - this.spanSet.spanBack(s2, length2, UnicodeSet.SpanCondition.CONTAINED));
                        }
                    }
                    else {
                        this.spanLengths[0] = (this.spanLengths[0] = 0);
                    }
                }
                if (0x0 != (n & 0x1)) {
                    if (0x0 != (n & 0x20)) {
                        this.addToSpanNotSet(s2.codePointAt(0));
                    }
                    if (0x0 != (n & 0x10)) {
                        this.addToSpanNotSet(s2.codePointBefore(length2));
                    }
                }
            }
            else if (this.all) {
                this.spanLengths[0] = (this.spanLengths[0] = 255);
            }
            else {
                this.spanLengths[0] = 255;
            }
            ++n2;
        }
        if (this.all) {
            this.spanNotSet.freeze();
        }
    }
    
    public UnicodeSetStringSpan(final UnicodeSetStringSpan unicodeSetStringSpan, final ArrayList strings) {
        this.spanSet = unicodeSetStringSpan.spanSet;
        this.strings = strings;
        this.maxLength16 = unicodeSetStringSpan.maxLength16;
        this.all = true;
        if (unicodeSetStringSpan.spanNotSet == unicodeSetStringSpan.spanSet) {
            this.spanNotSet = this.spanSet;
        }
        else {
            this.spanNotSet = (UnicodeSet)unicodeSetStringSpan.spanNotSet.clone();
        }
        this.offsets = new OffsetList();
        this.spanLengths = unicodeSetStringSpan.spanLengths.clone();
    }
    
    public boolean needsStringSpanUTF16() {
        return this.maxLength16 != 0;
    }
    
    public boolean contains(final int n) {
        return this.spanSet.contains(n);
    }
    
    private void addToSpanNotSet(final int n) {
        if (this.spanNotSet == null || this.spanNotSet == this.spanSet) {
            if (this.spanSet.contains(n)) {
                return;
            }
            this.spanNotSet = this.spanSet.cloneAsThawed();
        }
        this.spanNotSet.add(n);
    }
    
    public synchronized int span(final CharSequence p0, final int p1, final int p2, final UnicodeSet.SpanCondition p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.NOT_CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //     5: if_acmpne       16
        //     8: aload_0        
        //     9: aload_1        
        //    10: iload_2        
        //    11: iload_3        
        //    12: invokespecial   com/ibm/icu/impl/UnicodeSetStringSpan.spanNot:(Ljava/lang/CharSequence;II)I
        //    15: ireturn        
        //    16: aload_0        
        //    17: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.spanSet:Lcom/ibm/icu/text/UnicodeSet;
        //    20: aload_1        
        //    21: iload_2        
        //    22: iload_2        
        //    23: iload_3        
        //    24: iadd           
        //    25: invokeinterface java/lang/CharSequence.subSequence:(II)Ljava/lang/CharSequence;
        //    30: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //    33: invokevirtual   com/ibm/icu/text/UnicodeSet.span:(Ljava/lang/CharSequence;Lcom/ibm/icu/text/UnicodeSet$SpanCondition;)I
        //    36: istore          5
        //    38: iconst_0       
        //    39: iload_3        
        //    40: if_icmpne       45
        //    43: iload_3        
        //    44: ireturn        
        //    45: aload           4
        //    47: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //    50: if_acmpne       59
        //    53: aload_0        
        //    54: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.maxLength16:I
        //    57: istore          6
        //    59: aload_0        
        //    60: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //    63: iconst_0       
        //    64: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.setMaxLength:(I)V
        //    67: iload_2        
        //    68: iconst_0       
        //    69: iadd           
        //    70: istore          7
        //    72: iload_3        
        //    73: iconst_0       
        //    74: isub           
        //    75: istore          8
        //    77: aload_0        
        //    78: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.strings:Ljava/util/ArrayList;
        //    81: invokevirtual   java/util/ArrayList.size:()I
        //    84: istore          10
        //    86: aload           4
        //    88: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //    91: if_acmpne       232
        //    94: iconst_0       
        //    95: iload           10
        //    97: if_icmpge       382
        //   100: aload_0        
        //   101: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.spanLengths:[S
        //   104: iconst_0       
        //   105: saload         
        //   106: istore          11
        //   108: iconst_0       
        //   109: sipush          255
        //   112: if_icmpne       118
        //   115: goto            226
        //   118: aload_0        
        //   119: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.strings:Ljava/util/ArrayList;
        //   122: iconst_0       
        //   123: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   126: checkcast       Ljava/lang/String;
        //   129: astore          12
        //   131: aload           12
        //   133: invokevirtual   java/lang/String.length:()I
        //   136: istore          13
        //   138: iconst_0       
        //   139: sipush          254
        //   142: if_icmplt       154
        //   145: aload           12
        //   147: iconst_0       
        //   148: iconst_m1      
        //   149: invokevirtual   java/lang/String.offsetByCodePoints:(II)I
        //   152: istore          11
        //   154: iconst_0       
        //   155: iconst_0       
        //   156: if_icmple       159
        //   159: iconst_0       
        //   160: iload           8
        //   162: if_icmple       168
        //   165: goto            226
        //   168: aload_0        
        //   169: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   172: iconst_0       
        //   173: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.containsOffset:(I)Z
        //   176: ifne            210
        //   179: aload_1        
        //   180: iload           7
        //   182: iconst_0       
        //   183: isub           
        //   184: iload_3        
        //   185: aload           12
        //   187: iconst_0       
        //   188: invokestatic    com/ibm/icu/impl/UnicodeSetStringSpan.matches16CPB:(Ljava/lang/CharSequence;IILjava/lang/String;I)Z
        //   191: ifeq            210
        //   194: iconst_0       
        //   195: iload           8
        //   197: if_icmpne       202
        //   200: iload_3        
        //   201: ireturn        
        //   202: aload_0        
        //   203: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   206: iconst_0       
        //   207: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.addOffset:(I)V
        //   210: iconst_0       
        //   211: ifne            217
        //   214: goto            226
        //   217: iinc            11, -1
        //   220: iinc            14, 1
        //   223: goto            159
        //   226: iinc            9, 1
        //   229: goto            94
        //   232: iconst_0       
        //   233: iload           10
        //   235: if_icmpge       352
        //   238: aload_0        
        //   239: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.spanLengths:[S
        //   242: iconst_0       
        //   243: saload         
        //   244: istore          13
        //   246: aload_0        
        //   247: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.strings:Ljava/util/ArrayList;
        //   250: iconst_0       
        //   251: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   254: checkcast       Ljava/lang/String;
        //   257: astore          14
        //   259: aload           14
        //   261: invokevirtual   java/lang/String.length:()I
        //   264: istore          15
        //   266: iconst_0       
        //   267: sipush          254
        //   270: if_icmplt       277
        //   273: iload           15
        //   275: istore          13
        //   277: iconst_0       
        //   278: iconst_0       
        //   279: if_icmple       282
        //   282: iload           15
        //   284: iconst_0       
        //   285: isub           
        //   286: istore          16
        //   288: iload           16
        //   290: iload           8
        //   292: if_icmpgt       346
        //   295: iconst_0       
        //   296: iconst_0       
        //   297: if_icmpge       303
        //   300: goto            346
        //   303: iconst_0       
        //   304: iconst_0       
        //   305: if_icmpgt       314
        //   308: iload           16
        //   310: iconst_0       
        //   311: if_icmple       337
        //   314: aload_1        
        //   315: iload           7
        //   317: iconst_0       
        //   318: isub           
        //   319: iload_3        
        //   320: aload           14
        //   322: iload           15
        //   324: invokestatic    com/ibm/icu/impl/UnicodeSetStringSpan.matches16CPB:(Ljava/lang/CharSequence;IILjava/lang/String;I)Z
        //   327: ifeq            337
        //   330: iload           16
        //   332: istore          11
        //   334: goto            346
        //   337: iinc            13, -1
        //   340: iinc            16, 1
        //   343: goto            288
        //   346: iinc            9, 1
        //   349: goto            232
        //   352: iconst_0       
        //   353: ifne            360
        //   356: iconst_0       
        //   357: ifeq            382
        //   360: iload           7
        //   362: iconst_0       
        //   363: iadd           
        //   364: istore          7
        //   366: iload           8
        //   368: iconst_0       
        //   369: isub           
        //   370: istore          8
        //   372: iload           8
        //   374: ifne            379
        //   377: iload_3        
        //   378: ireturn        
        //   379: goto            86
        //   382: iconst_0       
        //   383: ifne            391
        //   386: iload           7
        //   388: ifne            406
        //   391: aload_0        
        //   392: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   395: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.isEmpty:()Z
        //   398: ifeq            522
        //   401: iload           7
        //   403: iload_2        
        //   404: isub           
        //   405: ireturn        
        //   406: aload_0        
        //   407: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   410: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.isEmpty:()Z
        //   413: ifeq            473
        //   416: aload_0        
        //   417: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.spanSet:Lcom/ibm/icu/text/UnicodeSet;
        //   420: aload_1        
        //   421: iload           7
        //   423: iload           7
        //   425: iload           8
        //   427: iadd           
        //   428: invokeinterface java/lang/CharSequence.subSequence:(II)Ljava/lang/CharSequence;
        //   433: getstatic       com/ibm/icu/text/UnicodeSet$SpanCondition.CONTAINED:Lcom/ibm/icu/text/UnicodeSet$SpanCondition;
        //   436: invokevirtual   com/ibm/icu/text/UnicodeSet.span:(Ljava/lang/CharSequence;Lcom/ibm/icu/text/UnicodeSet$SpanCondition;)I
        //   439: istore          5
        //   441: iconst_0       
        //   442: iload           8
        //   444: if_icmpeq       451
        //   447: iconst_0       
        //   448: ifne            458
        //   451: iload           7
        //   453: iconst_0       
        //   454: iadd           
        //   455: iload_2        
        //   456: isub           
        //   457: ireturn        
        //   458: iload           7
        //   460: iconst_0       
        //   461: iadd           
        //   462: istore          7
        //   464: iload           8
        //   466: iconst_0       
        //   467: isub           
        //   468: istore          8
        //   470: goto            86
        //   473: aload_0        
        //   474: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.spanSet:Lcom/ibm/icu/text/UnicodeSet;
        //   477: aload_1        
        //   478: iload           7
        //   480: iload           8
        //   482: invokestatic    com/ibm/icu/impl/UnicodeSetStringSpan.spanOne:(Lcom/ibm/icu/text/UnicodeSet;Ljava/lang/CharSequence;II)I
        //   485: istore          5
        //   487: iconst_0       
        //   488: ifle            522
        //   491: iconst_0       
        //   492: iload           8
        //   494: if_icmpne       499
        //   497: iload_3        
        //   498: ireturn        
        //   499: iload           7
        //   501: iconst_0       
        //   502: iadd           
        //   503: istore          7
        //   505: iload           8
        //   507: iconst_0       
        //   508: isub           
        //   509: istore          8
        //   511: aload_0        
        //   512: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   515: iconst_0       
        //   516: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.shift:(I)V
        //   519: goto            86
        //   522: aload_0        
        //   523: getfield        com/ibm/icu/impl/UnicodeSetStringSpan.offsets:Lcom/ibm/icu/impl/UnicodeSetStringSpan$OffsetList;
        //   526: invokevirtual   com/ibm/icu/impl/UnicodeSetStringSpan$OffsetList.popMinimum:()I
        //   529: istore          11
        //   531: iload           7
        //   533: iconst_0       
        //   534: iadd           
        //   535: istore          7
        //   537: iload           8
        //   539: iconst_0       
        //   540: isub           
        //   541: istore          8
        //   543: goto            86
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public synchronized int spanBack(final CharSequence charSequence, final int n, final UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNotBack(charSequence, n);
        }
        int n2 = this.spanSet.spanBack(charSequence, n, UnicodeSet.SpanCondition.CONTAINED);
        if (n2 == 0) {
            return 0;
        }
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            final int maxLength16 = this.maxLength16;
        }
        this.offsets.setMaxLength(0);
        final int size = this.strings.size();
        if (this.all) {}
        while (true) {
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                while (0 < size) {
                    int n3 = this.spanLengths[0];
                    if (0 != 255) {
                        final String s = this.strings.get(0);
                        s.length();
                        int offsetByCodePoints = 0;
                        if (0 >= 254) {
                            offsetByCodePoints = s.offsetByCodePoints(0, 1);
                        }
                        if (0 > 0) {}
                        while (0 <= n2) {
                            if (!this.offsets.containsOffset(0) && matches16CPB(charSequence, n2 - 0, n, s, 0)) {
                                if (n2 == 0) {
                                    return 0;
                                }
                                this.offsets.addOffset(0);
                            }
                            if (!false) {
                                break;
                            }
                            --n3;
                            ++offsetByCodePoints;
                        }
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
            else {
                while (0 < size) {
                    int n5 = this.spanLengths[0];
                    final String s2 = this.strings.get(0);
                    final int length = s2.length();
                    if (0 >= 254) {
                        n5 = length;
                    }
                    if (0 > 0) {}
                    for (int i = length - 0; i <= n2; ++i) {
                        if (0 < 0) {
                            break;
                        }
                        if ((0 > 0 || i > 0) && matches16CPB(charSequence, n2 - i, n, s2, length)) {
                            break;
                        }
                        --n5;
                    }
                    int n4 = 0;
                    ++n4;
                }
                if (false || false) {
                    n2 -= 0;
                    if (n2 == 0) {
                        return 0;
                    }
                    continue;
                }
            }
            if (false || n2 == n) {
                if (this.offsets.isEmpty()) {
                    return n2;
                }
            }
            else if (this.offsets.isEmpty()) {
                n2 = this.spanSet.spanBack(charSequence, 0, UnicodeSet.SpanCondition.CONTAINED);
                if (n2 == 0 || !false) {
                    return n2;
                }
                continue;
            }
            else {
                spanOneBack(this.spanSet, charSequence, n2);
                if (0 > 0) {
                    if (n2 == 0) {
                        return 0;
                    }
                    n2 -= 0;
                    this.offsets.shift(0);
                    continue;
                }
            }
            n2 -= this.offsets.popMinimum();
        }
    }
    
    private int spanNot(final CharSequence charSequence, final int n, final int n2) {
        int n3 = n;
        int i = n2;
        final int size = this.strings.size();
        do {
            int span = this.spanNotSet.span(charSequence.subSequence(n3, n3 + i), UnicodeSet.SpanCondition.NOT_CONTAINED);
            if (i == 0) {
                return n2;
            }
            final int n4 = n3 + 0;
            final int n5 = i - 0;
            final int spanOne = spanOne(this.spanSet, charSequence, n4, n5);
            if (spanOne > 0) {
                return n4 - n;
            }
            while (0 < size) {
                if (this.spanLengths[0] != 255) {
                    final String s = this.strings.get(0);
                    final int length = s.length();
                    if (length <= n5 && matches16CPB(charSequence, n4, n2, s, length)) {
                        return n4 - n;
                    }
                }
                ++span;
            }
            n3 = n4 - spanOne;
            i = n5 + spanOne;
        } while (i != 0);
        return n2;
    }
    
    private int spanNotBack(final CharSequence charSequence, final int n) {
        int i = n;
        final int size = this.strings.size();
        do {
            final int spanBack = this.spanNotSet.spanBack(charSequence, i, UnicodeSet.SpanCondition.NOT_CONTAINED);
            if (spanBack == 0) {
                return 0;
            }
            final int spanOneBack = spanOneBack(this.spanSet, charSequence, spanBack);
            if (spanOneBack > 0) {
                return spanBack;
            }
            while (0 < size) {
                if (this.spanLengths[0] != 255) {
                    final String s = this.strings.get(0);
                    final int length = s.length();
                    if (length <= spanBack && matches16CPB(charSequence, spanBack - length, n, s, length)) {
                        return spanBack;
                    }
                }
                int n2 = 0;
                ++n2;
            }
            i = spanBack + spanOneBack;
        } while (i != 0);
        return 0;
    }
    
    static short makeSpanLengthByte(final int n) {
        return (short)((n < 254) ? ((short)n) : 254);
    }
    
    private static boolean matches16(final CharSequence charSequence, final int n, final String s, int n2) {
        int n3 = n + n2;
        while (n2-- > 0) {
            if (charSequence.charAt(--n3) != s.charAt(n2)) {
                return false;
            }
        }
        return true;
    }
    
    static boolean matches16CPB(final CharSequence charSequence, final int n, final int n2, final String s, final int n3) {
        return (0 >= n || !com.ibm.icu.text.UTF16.isLeadSurrogate(charSequence.charAt(n - 1)) || !com.ibm.icu.text.UTF16.isTrailSurrogate(charSequence.charAt(n + 0))) && (n3 >= n2 || !com.ibm.icu.text.UTF16.isLeadSurrogate(charSequence.charAt(n + n3 - 1)) || !com.ibm.icu.text.UTF16.isTrailSurrogate(charSequence.charAt(n + n3))) && matches16(charSequence, n, s, n3);
    }
    
    static int spanOne(final UnicodeSet set, final CharSequence charSequence, final int n, final int n2) {
        final char char1 = charSequence.charAt(n);
        if (char1 >= '\ud800' && char1 <= '\udbff' && n2 >= 2) {
            final char char2 = charSequence.charAt(n + 1);
            if (com.ibm.icu.text.UTF16.isTrailSurrogate(char2)) {
                return set.contains(UCharacterProperty.getRawSupplementary(char1, char2)) ? 2 : -2;
            }
        }
        return set.contains(char1) ? 1 : -1;
    }
    
    static int spanOneBack(final UnicodeSet set, final CharSequence charSequence, final int n) {
        final char char1 = charSequence.charAt(n - 1);
        if (char1 >= '\udc00' && char1 <= '\udfff' && n >= 2) {
            final char char2 = charSequence.charAt(n - 2);
            if (com.ibm.icu.text.UTF16.isLeadSurrogate(char2)) {
                return set.contains(UCharacterProperty.getRawSupplementary(char2, char1)) ? 2 : -2;
            }
        }
        return set.contains(char1) ? 1 : -1;
    }
    
    static class OffsetList
    {
        private boolean[] list;
        private int length;
        private int start;
        
        public OffsetList() {
            this.list = new boolean[16];
        }
        
        public void setMaxLength(final int n) {
            if (n > this.list.length) {
                this.list = new boolean[n];
            }
            this.clear();
        }
        
        public void clear() {
            int length = this.list.length;
            while (length-- > 0) {
                this.list[length] = false;
            }
            final int n = 0;
            this.length = n;
            this.start = n;
        }
        
        public boolean isEmpty() {
            return this.length == 0;
        }
        
        public void shift(final int n) {
            int start = this.start + n;
            if (start >= this.list.length) {
                start -= this.list.length;
            }
            if (this.list[start]) {
                this.list[start] = false;
                --this.length;
            }
            this.start = start;
        }
        
        public void addOffset(final int n) {
            int n2 = this.start + n;
            if (n2 >= this.list.length) {
                n2 -= this.list.length;
            }
            this.list[n2] = true;
            ++this.length;
        }
        
        public boolean containsOffset(final int n) {
            int n2 = this.start + n;
            if (n2 >= this.list.length) {
                n2 -= this.list.length;
            }
            return this.list[n2];
        }
        
        public int popMinimum() {
            int start = this.start;
            do {
                ++start;
                if (0 < this.list.length) {
                    continue;
                }
                final int n = this.list.length - this.start;
                while (!this.list[0]) {
                    ++start;
                }
                this.list[0] = false;
                --this.length;
                return n + (this.start = 0);
            } while (!this.list[0]);
            this.list[0] = false;
            --this.length;
            final int n2 = 0 - this.start;
            this.start = 0;
            return n2;
        }
    }
}
