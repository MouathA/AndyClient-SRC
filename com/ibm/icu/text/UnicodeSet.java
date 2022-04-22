package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.lang.*;

public class UnicodeSet extends UnicodeFilter implements Iterable, Comparable, Freezable
{
    public static final UnicodeSet EMPTY;
    public static final UnicodeSet ALL_CODE_POINTS;
    private static XSymbolTable XSYMBOL_TABLE;
    private static final int LOW = 0;
    private static final int HIGH = 1114112;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 1114111;
    private int len;
    private int[] list;
    private int[] rangeList;
    private int[] buffer;
    TreeSet strings;
    private String pat;
    private static final int START_EXTRA = 16;
    private static final int GROW_EXTRA = 16;
    private static final String ANY_ID = "ANY";
    private static final String ASCII_ID = "ASCII";
    private static final String ASSIGNED = "Assigned";
    private static UnicodeSet[] INCLUSIONS;
    private BMPSet bmpSet;
    private UnicodeSetStringSpan stringSpan;
    private static final VersionInfo NO_VERSION;
    public static final int IGNORE_SPACE = 1;
    public static final int CASE = 2;
    public static final int CASE_INSENSITIVE = 2;
    public static final int ADD_CASE_MAPPINGS = 4;
    
    public UnicodeSet() {
        this.strings = new TreeSet();
        this.pat = null;
        (this.list = new int[17])[this.len++] = 1114112;
    }
    
    public UnicodeSet(final UnicodeSet set) {
        this.strings = new TreeSet();
        this.pat = null;
        this.set(set);
    }
    
    public UnicodeSet(final int n, final int n2) {
        this();
        this.complement(n, n2);
    }
    
    public UnicodeSet(final int... array) {
        this.strings = new TreeSet();
        this.pat = null;
        if ((array.length & 0x1) != 0x0) {
            throw new IllegalArgumentException("Must have even number of integers");
        }
        this.list = new int[array.length + 1];
        this.len = this.list.length;
        while (0 < array.length) {
            final int n = array[0];
            if (-1 >= n) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            final int[] list = this.list;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            list[n2] = n;
            final int n4 = array[0] + 1;
            if (-1 >= n4) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            final int[] list2 = this.list;
            final int n5 = 0;
            ++n3;
            list2[n5] = n4;
        }
        this.list[0] = 1114112;
    }
    
    public UnicodeSet(final String s) {
        this();
        this.applyPattern(s, null, null, 1);
    }
    
    public UnicodeSet(final String s, final boolean b) {
        this();
        this.applyPattern(s, null, null, b ? 1 : 0);
    }
    
    public UnicodeSet(final String s, final int n) {
        this();
        this.applyPattern(s, null, null, n);
    }
    
    public UnicodeSet(final String s, final ParsePosition parsePosition, final SymbolTable symbolTable) {
        this();
        this.applyPattern(s, parsePosition, symbolTable, 1);
    }
    
    public UnicodeSet(final String s, final ParsePosition parsePosition, final SymbolTable symbolTable, final int n) {
        this();
        this.applyPattern(s, parsePosition, symbolTable, n);
    }
    
    public Object clone() {
        final UnicodeSet set = new UnicodeSet(this);
        set.bmpSet = this.bmpSet;
        set.stringSpan = this.stringSpan;
        return set;
    }
    
    public UnicodeSet set(final int n, final int n2) {
        this.checkFrozen();
        this.clear();
        this.complement(n, n2);
        return this;
    }
    
    public UnicodeSet set(final UnicodeSet set) {
        this.checkFrozen();
        this.list = set.list.clone();
        this.len = set.len;
        this.pat = set.pat;
        this.strings = new TreeSet((SortedSet<E>)set.strings);
        return this;
    }
    
    public final UnicodeSet applyPattern(final String s) {
        this.checkFrozen();
        return this.applyPattern(s, null, null, 1);
    }
    
    public UnicodeSet applyPattern(final String s, final boolean b) {
        this.checkFrozen();
        return this.applyPattern(s, null, null, b ? 1 : 0);
    }
    
    public UnicodeSet applyPattern(final String s, final int n) {
        this.checkFrozen();
        return this.applyPattern(s, null, null, n);
    }
    
    public static boolean resemblesPattern(final String s, final int n) {
        return (n + 1 < s.length() && s.charAt(n) == '[') || resemblesPropertyPattern(s, n);
    }
    
    private static void _appendToPat(final StringBuffer sb, final String s, final boolean b) {
        while (0 < s.length()) {
            final int codePoint = s.codePointAt(0);
            _appendToPat(sb, codePoint, b);
            final int n = 0 + Character.charCount(codePoint);
        }
    }
    
    private static void _appendToPat(final StringBuffer sb, final int n, final boolean b) {
        if (b && Utility.isUnprintable(n) && Utility.escapeUnprintable((Appendable)sb, n)) {
            return;
        }
        switch (n) {
            case 36:
            case 38:
            case 45:
            case 58:
            case 91:
            case 92:
            case 93:
            case 94:
            case 123:
            case 125: {
                sb.append('\\');
                break;
            }
            default: {
                if (PatternProps.isWhiteSpace(n)) {
                    sb.append('\\');
                    break;
                }
                break;
            }
        }
        UTF16.append(sb, n);
    }
    
    public String toPattern(final boolean b) {
        return this._toPattern(new StringBuffer(), b).toString();
    }
    
    private StringBuffer _toPattern(final StringBuffer sb, final boolean b) {
        if (this.pat != null) {
            while (0 < this.pat.length()) {
                final int char1 = UTF16.charAt(this.pat, 0);
                final int n = 0 + UTF16.getCharCount(char1);
                if (b && Utility.isUnprintable(char1)) {
                    if (false) {
                        sb.setLength(sb.length() - 1);
                    }
                    Utility.escapeUnprintable((Appendable)sb, char1);
                }
                else {
                    UTF16.append(sb, char1);
                    if (char1 != 92) {
                        continue;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            return sb;
        }
        return this._generatePattern(sb, b, true);
    }
    
    public StringBuffer _generatePattern(final StringBuffer sb, final boolean b) {
        return this._generatePattern(sb, b, true);
    }
    
    public StringBuffer _generatePattern(final StringBuffer sb, final boolean b, final boolean b2) {
        sb.append('[');
        final int rangeCount = this.getRangeCount();
        if (rangeCount > 1 && this.getRangeStart(0) == 0 && this.getRangeEnd(rangeCount - 1) == 1114111) {
            sb.append('^');
            while (0 < rangeCount) {
                final int n = this.getRangeEnd(-1) + 1;
                final int n2 = this.getRangeStart(0) - 1;
                _appendToPat(sb, n, b);
                if (n != n2) {
                    if (n + 1 != n2) {
                        sb.append('-');
                    }
                    _appendToPat(sb, n2, b);
                }
                int n3 = 0;
                ++n3;
            }
        }
        else {
            while (0 < rangeCount) {
                final int rangeStart = this.getRangeStart(0);
                final int rangeEnd = this.getRangeEnd(0);
                _appendToPat(sb, rangeStart, b);
                if (rangeStart != rangeEnd) {
                    if (rangeStart + 1 != rangeEnd) {
                        sb.append('-');
                    }
                    _appendToPat(sb, rangeEnd, b);
                }
                int n3 = 0;
                ++n3;
            }
        }
        if (b2 && this.strings.size() > 0) {
            for (final String s : this.strings) {
                sb.append('{');
                _appendToPat(sb, s, b);
                sb.append('}');
            }
        }
        return sb.append(']');
    }
    
    public int size() {
        while (0 < this.getRangeCount()) {
            final int n = 0 + (this.getRangeEnd(0) - this.getRangeStart(0) + 1);
            int n2 = 0;
            ++n2;
        }
        return 0 + this.strings.size();
    }
    
    public boolean isEmpty() {
        return this.len == 1 && this.strings.size() == 0;
    }
    
    public boolean matchesIndexValue(final int n) {
        while (0 < this.getRangeCount()) {
            final int rangeStart = this.getRangeStart(0);
            final int rangeEnd = this.getRangeEnd(0);
            if ((rangeStart & 0xFFFFFF00) == (rangeEnd & 0xFFFFFF00)) {
                if ((rangeStart & 0xFF) <= n && n <= (rangeEnd & 0xFF)) {
                    return true;
                }
            }
            else if ((rangeStart & 0xFF) <= n || n <= (rangeEnd & 0xFF)) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        if (this.strings.size() != 0) {
            final Iterator<String> iterator = this.strings.iterator();
            while (iterator.hasNext()) {
                if ((UTF16.charAt(iterator.next(), 0) & 0xFF) == n) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int matches(final Replaceable p0, final int[] p1, final int p2, final boolean p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_0       
        //     2: iaload         
        //     3: iload_3        
        //     4: if_icmpne       30
        //     7: aload_0        
        //     8: ldc_w           65535
        //    11: invokevirtual   com/ibm/icu/text/UnicodeSet.contains:(I)Z
        //    14: ifeq            28
        //    17: iload           4
        //    19: ifeq            26
        //    22: iconst_1       
        //    23: goto            27
        //    26: iconst_2       
        //    27: ireturn        
        //    28: iconst_0       
        //    29: ireturn        
        //    30: aload_0        
        //    31: getfield        com/ibm/icu/text/UnicodeSet.strings:Ljava/util/TreeSet;
        //    34: invokevirtual   java/util/TreeSet.size:()I
        //    37: ifeq            249
        //    40: aload_2        
        //    41: iconst_0       
        //    42: iaload         
        //    43: iload_3        
        //    44: if_icmpge       51
        //    47: iconst_1       
        //    48: goto            52
        //    51: iconst_0       
        //    52: istore          5
        //    54: aload_1        
        //    55: aload_2        
        //    56: iconst_0       
        //    57: iaload         
        //    58: invokeinterface com/ibm/icu/text/Replaceable.charAt:(I)C
        //    63: istore          6
        //    65: aload_0        
        //    66: getfield        com/ibm/icu/text/UnicodeSet.strings:Ljava/util/TreeSet;
        //    69: invokevirtual   java/util/TreeSet.iterator:()Ljava/util/Iterator;
        //    72: astore          8
        //    74: aload           8
        //    76: invokeinterface java/util/Iterator.hasNext:()Z
        //    81: ifeq            227
        //    84: aload           8
        //    86: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    91: checkcast       Ljava/lang/String;
        //    94: astore          9
        //    96: aload           9
        //    98: iload           5
        //   100: ifeq            107
        //   103: iconst_0       
        //   104: goto            114
        //   107: aload           9
        //   109: invokevirtual   java/lang/String.length:()I
        //   112: iconst_1       
        //   113: isub           
        //   114: invokevirtual   java/lang/String.charAt:(I)C
        //   117: istore          10
        //   119: iload           5
        //   121: ifeq            134
        //   124: iload           10
        //   126: iload           6
        //   128: if_icmple       134
        //   131: goto            227
        //   134: iload           10
        //   136: iload           6
        //   138: if_icmpeq       144
        //   141: goto            74
        //   144: aload_1        
        //   145: aload_2        
        //   146: iconst_0       
        //   147: iaload         
        //   148: iload_3        
        //   149: aload           9
        //   151: invokestatic    com/ibm/icu/text/UnicodeSet.matchRest:(Lcom/ibm/icu/text/Replaceable;IILjava/lang/String;)I
        //   154: istore          11
        //   156: iload           4
        //   158: ifeq            190
        //   161: iload           5
        //   163: ifeq            174
        //   166: iload_3        
        //   167: aload_2        
        //   168: iconst_0       
        //   169: iaload         
        //   170: isub           
        //   171: goto            179
        //   174: aload_2        
        //   175: iconst_0       
        //   176: iaload         
        //   177: iload_3        
        //   178: isub           
        //   179: istore          12
        //   181: iload           11
        //   183: iload           12
        //   185: if_icmpne       190
        //   188: iconst_1       
        //   189: ireturn        
        //   190: iload           11
        //   192: aload           9
        //   194: invokevirtual   java/lang/String.length:()I
        //   197: if_icmpne       224
        //   200: iload           11
        //   202: iconst_0       
        //   203: if_icmple       210
        //   206: iload           11
        //   208: istore          7
        //   210: iload           5
        //   212: ifeq            74
        //   215: iload           11
        //   217: iconst_0       
        //   218: if_icmpge       74
        //   221: goto            227
        //   224: goto            74
        //   227: iconst_0       
        //   228: ifeq            249
        //   231: aload_2        
        //   232: iconst_0       
        //   233: dup2           
        //   234: iaload         
        //   235: iload           5
        //   237: ifeq            244
        //   240: iconst_0       
        //   241: goto            240
        //   244: iconst_0       
        //   245: isub           
        //   246: iastore        
        //   247: iconst_2       
        //   248: ireturn        
        //   249: aload_0        
        //   250: aload_1        
        //   251: aload_2        
        //   252: iload_3        
        //   253: iload           4
        //   255: invokespecial   com/ibm/icu/text/UnicodeFilter.matches:(Lcom/ibm/icu/text/Replaceable;[IIZ)I
        //   258: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0240 (coming from #0241).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    private static int matchRest(final Replaceable replaceable, final int n, final int n2, final String s) {
        int length = s.length();
        int n3;
        if (n < n2) {
            n3 = n2 - n;
            if (n3 > length) {
                n3 = length;
            }
            while (1 < n3) {
                if (replaceable.charAt(n + 1) != s.charAt(1)) {
                    return 0;
                }
                int n4 = 0;
                ++n4;
            }
        }
        else {
            n3 = n - n2;
            if (n3 > length) {
                n3 = length;
            }
            --length;
            while (1 < n3) {
                if (replaceable.charAt(n - 1) != s.charAt(length - 1)) {
                    return 0;
                }
                int n4 = 0;
                ++n4;
            }
        }
        return n3;
    }
    
    @Deprecated
    public int matchesAt(final CharSequence charSequence, final int n) {
        Label_0133: {
            if (this.strings.size() != 0) {
                final char char1 = charSequence.charAt(n);
                String s = null;
                final Iterator<String> iterator = (Iterator<String>)this.strings.iterator();
                while (iterator.hasNext()) {
                    s = iterator.next();
                    final char char2 = s.charAt(0);
                    if (char2 < char1) {
                        continue;
                    }
                    if (char2 > char1) {
                        break Label_0133;
                    }
                }
                while (-1 <= matchesAt(charSequence, n, s)) {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    s = iterator.next();
                }
            }
        }
        if (-1 < 2) {
            final int char3 = UTF16.charAt(charSequence, n);
            if (this.contains(char3)) {
                UTF16.getCharCount(char3);
            }
        }
        return n - 1;
    }
    
    private static int matchesAt(final CharSequence charSequence, final int n, final CharSequence charSequence2) {
        final int length = charSequence2.length();
        if (charSequence.length() + n > length) {
            return -1;
        }
        int n2 = n;
        while (0 < length) {
            if (charSequence2.charAt(0) != charSequence.charAt(n2)) {
                return -1;
            }
            int n3 = 0;
            ++n3;
            ++n2;
        }
        return 0;
    }
    
    public void addMatchSetTo(final UnicodeSet set) {
        set.addAll(this);
    }
    
    public int indexOf(final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        while (true) {
            final int[] list = this.list;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            final int n4 = list[n2];
            if (n < n4) {
                return -1;
            }
            final int[] list2 = this.list;
            final int n5 = 0;
            ++n3;
            if (n < list2[n5]) {
                return 0 + n - n4;
            }
        }
    }
    
    public int charAt(int n) {
        if (n >= 0) {
            while (0 < (this.len & 0xFFFFFFFE)) {
                final int[] list = this.list;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                final int n4 = list[n2];
                final int[] list2 = this.list;
                final int n5 = 0;
                ++n3;
                final int n6 = list2[n5] - n4;
                if (n < n6) {
                    return n4 + n;
                }
                n -= n6;
            }
        }
        return -1;
    }
    
    public UnicodeSet add(final int n, final int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }
    
    public UnicodeSet addAll(final int n, final int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }
    
    private UnicodeSet add_unchecked(final int n, final int n2) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n < n2) {
            this.add(this.range(n, n2), 2, 0);
        }
        else if (n == n2) {
            this.add(n);
        }
        return this;
    }
    
    public final UnicodeSet add(final int n) {
        this.checkFrozen();
        return this.add_unchecked(n);
    }
    
    private final UnicodeSet add_unchecked(final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        final int codePoint = this.findCodePoint(n);
        if ((codePoint & 0x1) != 0x0) {
            return this;
        }
        if (n == this.list[codePoint] - 1) {
            if ((this.list[codePoint] = n) == 1114111) {
                this.ensureCapacity(this.len + 1);
                this.list[this.len++] = 1114112;
            }
            if (codePoint > 0 && n == this.list[codePoint - 1]) {
                System.arraycopy(this.list, codePoint + 1, this.list, codePoint - 1, this.len - codePoint - 1);
                this.len -= 2;
            }
        }
        else if (codePoint > 0 && n == this.list[codePoint - 1]) {
            final int[] list = this.list;
            final int n2 = codePoint - 1;
            ++list[n2];
        }
        else {
            if (this.len + 2 > this.list.length) {
                final int[] list2 = new int[this.len + 2 + 16];
                if (codePoint != 0) {
                    System.arraycopy(this.list, 0, list2, 0, codePoint);
                }
                System.arraycopy(this.list, codePoint, list2, codePoint + 2, this.len - codePoint);
                this.list = list2;
            }
            else {
                System.arraycopy(this.list, codePoint, this.list, codePoint + 2, this.len - codePoint);
            }
            this.list[codePoint] = n;
            this.list[codePoint + 1] = n + 1;
            this.len += 2;
        }
        this.pat = null;
        return this;
    }
    
    public final UnicodeSet add(final CharSequence charSequence) {
        this.checkFrozen();
        final int singleCP = getSingleCP(charSequence);
        if (singleCP < 0) {
            this.strings.add(charSequence.toString());
            this.pat = null;
        }
        else {
            this.add_unchecked(singleCP, singleCP);
        }
        return this;
    }
    
    private static int getSingleCP(final CharSequence charSequence) {
        if (charSequence.length() < 1) {
            throw new IllegalArgumentException("Can't use zero-length strings in UnicodeSet");
        }
        if (charSequence.length() > 2) {
            return -1;
        }
        if (charSequence.length() == 1) {
            return charSequence.charAt(0);
        }
        final int char1 = UTF16.charAt(charSequence, 0);
        if (char1 > 65535) {
            return char1;
        }
        return -1;
    }
    
    public final UnicodeSet addAll(final CharSequence charSequence) {
        this.checkFrozen();
        while (0 < charSequence.length()) {
            final int char1 = UTF16.charAt(charSequence, 0);
            this.add_unchecked(char1, char1);
            final int n = 0 + UTF16.getCharCount(char1);
        }
        return this;
    }
    
    public final UnicodeSet retainAll(final String s) {
        return this.retainAll(fromAll(s));
    }
    
    public final UnicodeSet complementAll(final String s) {
        return this.complementAll(fromAll(s));
    }
    
    public final UnicodeSet removeAll(final String s) {
        return this.removeAll(fromAll(s));
    }
    
    public final UnicodeSet removeAllStrings() {
        this.checkFrozen();
        if (this.strings.size() != 0) {
            this.strings.clear();
            this.pat = null;
        }
        return this;
    }
    
    public static UnicodeSet from(final String s) {
        return new UnicodeSet().add(s);
    }
    
    public static UnicodeSet fromAll(final String s) {
        return new UnicodeSet().addAll(s);
    }
    
    public UnicodeSet retain(final int n, final int n2) {
        this.checkFrozen();
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.retain(this.range(n, n2), 2, 0);
        }
        else {
            this.clear();
        }
        return this;
    }
    
    public final UnicodeSet retain(final int n) {
        return this.retain(n, n);
    }
    
    public final UnicodeSet retain(final String s) {
        final int singleCP = getSingleCP(s);
        if (singleCP < 0) {
            if (this.strings.contains(s) && this.size() == 1) {
                return this;
            }
            this.clear();
            this.strings.add(s);
            this.pat = null;
        }
        else {
            this.retain(singleCP, singleCP);
        }
        return this;
    }
    
    public UnicodeSet remove(final int n, final int n2) {
        this.checkFrozen();
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.retain(this.range(n, n2), 2, 2);
        }
        return this;
    }
    
    public final UnicodeSet remove(final int n) {
        return this.remove(n, n);
    }
    
    public final UnicodeSet remove(final String s) {
        final int singleCP = getSingleCP(s);
        if (singleCP < 0) {
            this.strings.remove(s);
            this.pat = null;
        }
        else {
            this.remove(singleCP, singleCP);
        }
        return this;
    }
    
    public UnicodeSet complement(final int n, final int n2) {
        this.checkFrozen();
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.xor(this.range(n, n2), 2, 0);
        }
        this.pat = null;
        return this;
    }
    
    public final UnicodeSet complement(final int n) {
        return this.complement(n, n);
    }
    
    public UnicodeSet complement() {
        this.checkFrozen();
        if (this.list[0] == 0) {
            System.arraycopy(this.list, 1, this.list, 0, this.len - 1);
            --this.len;
        }
        else {
            this.ensureCapacity(this.len + 1);
            System.arraycopy(this.list, 0, this.list, 1, this.len);
            this.list[0] = 0;
            ++this.len;
        }
        this.pat = null;
        return this;
    }
    
    public final UnicodeSet complement(final String s) {
        this.checkFrozen();
        final int singleCP = getSingleCP(s);
        if (singleCP < 0) {
            if (this.strings.contains(s)) {
                this.strings.remove(s);
            }
            else {
                this.strings.add(s);
            }
            this.pat = null;
        }
        else {
            this.complement(singleCP, singleCP);
        }
        return this;
    }
    
    @Override
    public boolean contains(final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (this.bmpSet != null) {
            return this.bmpSet.contains(n);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.contains(n);
        }
        return (this.findCodePoint(n) & 0x1) != 0x0;
    }
    
    private final int findCodePoint(final int n) {
        if (n < this.list[0]) {
            return 0;
        }
        if (this.len >= 2 && n >= this.list[this.len - 2]) {
            return this.len - 1;
        }
        int n2 = this.len - 1;
        while (true) {
            final int n3 = 0 + n2 >>> 1;
            if (n3 == 0) {
                break;
            }
            if (n >= this.list[n3]) {
                continue;
            }
            n2 = n3;
        }
        return n2;
    }
    
    public boolean contains(final int n, final int n2) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        final int codePoint = this.findCodePoint(n);
        return (codePoint & 0x1) != 0x0 && n2 < this.list[codePoint];
    }
    
    public final boolean contains(final String s) {
        final int singleCP = getSingleCP(s);
        if (singleCP < 0) {
            return this.strings.contains(s);
        }
        return this.contains(singleCP);
    }
    
    public boolean containsAll(final UnicodeSet set) {
        final int[] list = set.list;
        final int n = this.len - 1;
        final int n2 = set.len - 1;
        while (true) {
            if (false) {
                if (0 >= n) {
                    if (true && 0 >= n2) {
                        break;
                    }
                    return false;
                }
                else {
                    final int[] list2 = this.list;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    final int n5 = list2[n3];
                    final int[] list3 = this.list;
                    final int n6 = 0;
                    ++n4;
                    final int n7 = list3[n6];
                }
            }
            if (true) {
                if (0 >= n2) {
                    break;
                }
                final int[] array = list;
                final int n8 = 0;
                int n9 = 0;
                ++n9;
                final int n10 = array[n8];
                final int[] array2 = list;
                final int n11 = 0;
                ++n9;
                final int n12 = array2[n11];
            }
            if (0 >= 0) {
                continue;
            }
            if (0 >= 0 && 0 <= 0) {
                continue;
            }
            return false;
        }
        return this.strings.containsAll(set.strings);
    }
    
    public boolean containsAll(final String s) {
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            if (!this.contains(char1)) {
                return this.strings.size() != 0 && this.containsAll(s, 0);
            }
            final int n = 0 + UTF16.getCharCount(char1);
        }
        return true;
    }
    
    private boolean containsAll(final String s, final int n) {
        if (n >= s.length()) {
            return true;
        }
        final int char1 = UTF16.charAt(s, n);
        if (this.contains(char1) && this.containsAll(s, n + UTF16.getCharCount(char1))) {
            return true;
        }
        for (final String s2 : this.strings) {
            if (s.startsWith(s2, n) && this.containsAll(s, n + s2.length())) {
                return true;
            }
        }
        return false;
    }
    
    @Deprecated
    public String getRegexEquivalent() {
        if (this.strings.size() == 0) {
            return this.toString();
        }
        final StringBuffer sb = new StringBuffer("(?:");
        this._generatePattern(sb, true, false);
        for (final String s : this.strings) {
            sb.append('|');
            _appendToPat(sb, s, true);
        }
        return sb.append(")").toString();
    }
    
    public boolean containsNone(final int i, final int n) {
        if (i < 0 || i > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(i, 6));
        }
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        int[] list;
        do {
            list = this.list;
            int n2 = 0;
            ++n2;
        } while (i >= list[-1]);
        return !true && n < this.list[-1];
    }
    
    public boolean containsNone(final UnicodeSet set) {
        final int[] list = set.list;
        final int n = this.len - 1;
        final int n2 = set.len - 1;
        while (true) {
            if (false) {
                if (0 >= n) {
                    break;
                }
                final int[] list2 = this.list;
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                final int n5 = list2[n3];
                final int[] list3 = this.list;
                final int n6 = 0;
                ++n4;
                final int n7 = list3[n6];
            }
            if (true) {
                if (0 >= n2) {
                    break;
                }
                final int[] array = list;
                final int n8 = 0;
                int n9 = 0;
                ++n9;
                final int n10 = array[n8];
                final int[] array2 = list;
                final int n11 = 0;
                ++n9;
                final int n12 = array2[n11];
            }
            if (0 >= 0) {
                continue;
            }
            if (0 >= 0) {
                continue;
            }
            return false;
        }
        return SortedSetRelation.hasRelation(this.strings, 5, set.strings);
    }
    
    public boolean containsNone(final String s) {
        return this.span(s, SpanCondition.NOT_CONTAINED) == s.length();
    }
    
    public final boolean containsSome(final int n, final int n2) {
        return !this.containsNone(n, n2);
    }
    
    public final boolean containsSome(final UnicodeSet set) {
        return !this.containsNone(set);
    }
    
    public final boolean containsSome(final String s) {
        return !this.containsNone(s);
    }
    
    public UnicodeSet addAll(final UnicodeSet set) {
        this.checkFrozen();
        this.add(set.list, set.len, 0);
        this.strings.addAll(set.strings);
        return this;
    }
    
    public UnicodeSet retainAll(final UnicodeSet set) {
        this.checkFrozen();
        this.retain(set.list, set.len, 0);
        this.strings.retainAll(set.strings);
        return this;
    }
    
    public UnicodeSet removeAll(final UnicodeSet set) {
        this.checkFrozen();
        this.retain(set.list, set.len, 2);
        this.strings.removeAll(set.strings);
        return this;
    }
    
    public UnicodeSet complementAll(final UnicodeSet set) {
        this.checkFrozen();
        this.xor(set.list, set.len, 0);
        SortedSetRelation.doOperation(this.strings, 5, set.strings);
        return this;
    }
    
    public UnicodeSet clear() {
        this.checkFrozen();
        this.list[0] = 1114112;
        this.len = 1;
        this.pat = null;
        this.strings.clear();
        return this;
    }
    
    public int getRangeCount() {
        return this.len / 2;
    }
    
    public int getRangeStart(final int n) {
        return this.list[n * 2];
    }
    
    public int getRangeEnd(final int n) {
        return this.list[n * 2 + 1] - 1;
    }
    
    public UnicodeSet compact() {
        this.checkFrozen();
        if (this.len != this.list.length) {
            final int[] list = new int[this.len];
            System.arraycopy(this.list, 0, list, 0, this.len);
            this.list = list;
        }
        this.rangeList = null;
        this.buffer = null;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final UnicodeSet set = (UnicodeSet)o;
        if (this.len != set.len) {
            return false;
        }
        while (0 < this.len) {
            if (this.list[0] != set.list[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return this.strings.equals(set.strings);
    }
    
    @Override
    public int hashCode() {
        int len = this.len;
        while (0 < this.len) {
            len = len * 1000003 + this.list[0];
            int n = 0;
            ++n;
        }
        return len;
    }
    
    @Override
    public String toString() {
        return this.toPattern(true);
    }
    
    @Deprecated
    public UnicodeSet applyPattern(final String s, ParsePosition parsePosition, final SymbolTable symbolTable, final int n) {
        final boolean b = parsePosition == null;
        if (b) {
            parsePosition = new ParsePosition(0);
        }
        final StringBuffer sb = new StringBuffer();
        final RuleCharacterIterator ruleCharacterIterator = new RuleCharacterIterator(s, symbolTable, parsePosition);
        this.applyPattern(ruleCharacterIterator, symbolTable, sb, n);
        if (ruleCharacterIterator.inVariable()) {
            syntaxError(ruleCharacterIterator, "Extra chars in variable value");
        }
        this.pat = sb.toString();
        if (b) {
            int n2 = parsePosition.getIndex();
            if ((n & 0x1) != 0x0) {
                n2 = PatternProps.skipWhiteSpace(s, n2);
            }
            if (n2 != s.length()) {
                throw new IllegalArgumentException("Parse of \"" + s + "\" failed at " + n2);
            }
        }
        return this;
    }
    
    void applyPattern(final RuleCharacterIterator ruleCharacterIterator, final SymbolTable symbolTable, final StringBuffer sb, final int n) {
        if ((n & 0x1) != 0x0) {}
        final StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = null;
        UnicodeSet set = null;
        Object pos = null;
        this.clear();
        while (2 != 2 && !ruleCharacterIterator.atEnd()) {
            UnicodeSet set2 = null;
            if (!resemblesPropertyPattern(ruleCharacterIterator, 3)) {
                pos = ruleCharacterIterator.getPos(pos);
                ruleCharacterIterator.next(3);
                ruleCharacterIterator.isEscaped();
                if (36 == 91 && !true) {
                    if (2 == 1) {
                        ruleCharacterIterator.setPos(pos);
                    }
                    else {
                        sb2.append('[');
                        pos = ruleCharacterIterator.getPos(pos);
                        ruleCharacterIterator.next(3);
                        ruleCharacterIterator.isEscaped();
                        if (36 == 94 && !true) {
                            sb2.append('^');
                            pos = ruleCharacterIterator.getPos(pos);
                            ruleCharacterIterator.next(3);
                            ruleCharacterIterator.isEscaped();
                        }
                        if (36 != 45) {
                            ruleCharacterIterator.setPos(pos);
                            continue;
                        }
                    }
                }
                else if (symbolTable != null) {
                    final UnicodeMatcher lookupMatcher = symbolTable.lookupMatcher(36);
                    if (lookupMatcher != null) {
                        set2 = (UnicodeSet)lookupMatcher;
                    }
                }
            }
            if (3 != 0) {
                if (true == true) {
                    if (false) {
                        syntaxError(ruleCharacterIterator, "Char expected after operator");
                    }
                    this.add_unchecked(0, 0);
                    _appendToPat(sb2, 0, false);
                }
                if (0 == 45 || 0 == 38) {
                    sb2.append('\0');
                }
                if (set2 == null) {
                    if (set == null) {
                        set = new UnicodeSet();
                    }
                    set2 = set;
                }
                switch (3) {
                    case 1: {
                        set2.applyPattern(ruleCharacterIterator, symbolTable, sb2, n);
                        break;
                    }
                    case 2: {
                        ruleCharacterIterator.skipIgnored(3);
                        set2.applyPropertyPattern(ruleCharacterIterator, sb2, symbolTable);
                        break;
                    }
                    case 3: {
                        set2._toPattern(sb2, false);
                        break;
                    }
                }
                if (2 == 0) {
                    this.set(set2);
                    break;
                }
                switch (false) {
                    case 45: {
                        this.removeAll(set2);
                        continue;
                    }
                    case 38: {
                        this.retainAll(set2);
                        continue;
                    }
                    case 0: {
                        this.addAll(set2);
                        continue;
                    }
                }
            }
            else {
                if (2 == 0) {
                    syntaxError(ruleCharacterIterator, "Missing '['");
                }
                if (!true) {
                    switch (36) {
                        case 93: {
                            if (true == true) {
                                this.add_unchecked(0, 0);
                                _appendToPat(sb2, 0, false);
                            }
                            if (0 == 45) {
                                this.add_unchecked(0, 0);
                                sb2.append('\0');
                            }
                            else if (0 == 38) {
                                syntaxError(ruleCharacterIterator, "Trailing '&'");
                            }
                            sb2.append(']');
                            continue;
                        }
                        case 45: {
                            if (!false) {
                                if (true) {
                                    final char c = 36;
                                    continue;
                                }
                                this.add_unchecked(36, 36);
                                ruleCharacterIterator.next(3);
                                ruleCharacterIterator.isEscaped();
                                if (36 == 93 && !true) {
                                    sb2.append("-]");
                                    continue;
                                }
                            }
                            syntaxError(ruleCharacterIterator, "'-' not after char or set");
                            break;
                        }
                        case 38: {
                            if (1 == 2 && !false) {
                                final char c2 = 36;
                                continue;
                            }
                            syntaxError(ruleCharacterIterator, "'&' not after set");
                            break;
                        }
                        case 94: {
                            syntaxError(ruleCharacterIterator, "'^' not after '['");
                            break;
                        }
                        case 123: {
                            if (false) {
                                syntaxError(ruleCharacterIterator, "Missing operand after operator");
                            }
                            if (true == true) {
                                this.add_unchecked(0, 0);
                                _appendToPat(sb2, 0, false);
                            }
                            if (sb3 == null) {
                                sb3 = new StringBuffer();
                            }
                            else {
                                sb3.setLength(0);
                            }
                            while (!ruleCharacterIterator.atEnd()) {
                                ruleCharacterIterator.next(3);
                                ruleCharacterIterator.isEscaped();
                                if (36 == 125 && !true) {
                                    break;
                                }
                                UTF16.append(sb3, 36);
                            }
                            if (sb3.length() < 1 || !true) {
                                syntaxError(ruleCharacterIterator, "Invalid multicharacter string");
                            }
                            this.add(sb3.toString());
                            sb2.append('{');
                            _appendToPat(sb2, sb3.toString(), false);
                            sb2.append('}');
                            continue;
                        }
                        case 36: {
                            pos = ruleCharacterIterator.getPos(pos);
                            ruleCharacterIterator.next(3);
                            ruleCharacterIterator.isEscaped();
                            final boolean b = 36 == 93 && !true;
                            if (symbolTable == null && !b) {
                                ruleCharacterIterator.setPos(pos);
                                break;
                            }
                            if (b && !false) {
                                if (true == true) {
                                    this.add_unchecked(0, 0);
                                    _appendToPat(sb2, 0, false);
                                }
                                this.add_unchecked(65535);
                                sb2.append('$').append(']');
                                continue;
                            }
                            syntaxError(ruleCharacterIterator, "Unquoted '$'");
                            break;
                        }
                    }
                }
                switch (true) {
                    case 0: {
                        continue;
                    }
                    case 1: {
                        if (0 == 45) {
                            if (0 >= 36) {
                                syntaxError(ruleCharacterIterator, "Invalid range");
                            }
                            this.add_unchecked(0, 36);
                            _appendToPat(sb2, 0, false);
                            sb2.append('\0');
                            _appendToPat(sb2, 36, false);
                            continue;
                        }
                        this.add_unchecked(0, 0);
                        _appendToPat(sb2, 0, false);
                        continue;
                    }
                    case 2: {
                        if (false) {
                            syntaxError(ruleCharacterIterator, "Set expected after operator");
                            continue;
                        }
                        continue;
                    }
                }
            }
        }
        if (2 != 2) {
            syntaxError(ruleCharacterIterator, "Missing ']'");
        }
        ruleCharacterIterator.skipIgnored(3);
        if ((n & 0x2) != 0x0) {
            this.closeOver(2);
        }
        if (true) {
            this.complement();
        }
        if (true) {
            sb.append(sb2.toString());
        }
        else {
            this._generatePattern(sb, false, true);
        }
    }
    
    private static void syntaxError(final RuleCharacterIterator ruleCharacterIterator, final String s) {
        throw new IllegalArgumentException("Error: " + s + " at \"" + Utility.escape(ruleCharacterIterator.toString()) + '\"');
    }
    
    public Collection addAllTo(final Collection collection) {
        return addAllTo(this, collection);
    }
    
    public String[] addAllTo(final String[] array) {
        return (String[])addAllTo(this, array);
    }
    
    public static String[] toArray(final UnicodeSet set) {
        return (String[])addAllTo(set, new String[set.size()]);
    }
    
    public UnicodeSet add(final Collection collection) {
        return this.addAll(collection);
    }
    
    public UnicodeSet addAll(final Collection collection) {
        this.checkFrozen();
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next().toString());
        }
        return this;
    }
    
    private void ensureCapacity(final int n) {
        if (n <= this.list.length) {
            return;
        }
        final int[] list = new int[n + 16];
        System.arraycopy(this.list, 0, list, 0, this.len);
        this.list = list;
    }
    
    private void ensureBufferCapacity(final int n) {
        if (this.buffer != null && n <= this.buffer.length) {
            return;
        }
        this.buffer = new int[n + 16];
    }
    
    private int[] range(final int n, final int n2) {
        if (this.rangeList == null) {
            this.rangeList = new int[] { n, n2 + 1, 1114112 };
        }
        else {
            this.rangeList[0] = n;
            this.rangeList[1] = n2 + 1;
        }
        return this.rangeList;
    }
    
    private UnicodeSet xor(final int[] array, final int n, final int n2) {
        this.ensureBufferCapacity(this.len + n);
        final int[] list = this.list;
        final int n3 = 0;
        int n4 = 0;
        ++n4;
        int n5 = list[n3];
        int n6 = 0;
        if (n2 == 1 || n2 == 2) {
            if (array[0] == 0) {
                ++n6;
                final int n7 = array[0];
            }
        }
        else {
            final int n8 = 0;
            ++n6;
            final int n9 = array[n8];
        }
        int n11 = 0;
        while (true) {
            if (n5 < 0) {
                final int[] buffer = this.buffer;
                final int n10 = 0;
                ++n11;
                buffer[n10] = n5;
                final int[] list2 = this.list;
                final int n12 = 0;
                ++n4;
                n5 = list2[n12];
            }
            else if (0 < n5) {
                final int[] buffer2 = this.buffer;
                final int n13 = 0;
                ++n11;
                buffer2[n13] = 0;
                final int n14 = 0;
                ++n6;
                final int n15 = array[n14];
            }
            else {
                if (n5 == 1114112) {
                    break;
                }
                final int[] list3 = this.list;
                final int n16 = 0;
                ++n4;
                n5 = list3[n16];
                final int n17 = 0;
                ++n6;
                final int n18 = array[n17];
            }
        }
        final int[] buffer3 = this.buffer;
        final int n19 = 0;
        ++n11;
        buffer3[n19] = 1114112;
        this.len = 0;
        final int[] list4 = this.list;
        this.list = this.buffer;
        this.buffer = list4;
        this.pat = null;
        return this;
    }
    
    private UnicodeSet add(final int[] array, final int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        final int[] list = this.list;
        final int n3 = 0;
        int n4 = 0;
        ++n4;
        int n5 = list[n3];
        final int n6 = 0;
        int n7 = 0;
        ++n7;
        int max = array[n6];
        int n9 = 0;
    Label_0569:
        while (true) {
            switch (n2) {
                case 0: {
                    if (n5 < max) {
                        if (0 > 0 && n5 <= this.buffer[-1]) {
                            final int n8 = this.list[0];
                            final int[] buffer = this.buffer;
                            --n9;
                            n5 = max(n8, buffer[0]);
                        }
                        else {
                            final int[] buffer2 = this.buffer;
                            final int n10 = 0;
                            ++n9;
                            buffer2[n10] = n5;
                            n5 = this.list[0];
                        }
                        ++n4;
                        n2 ^= 0x1;
                        continue;
                    }
                    if (max < n5) {
                        if (0 > 0 && max <= this.buffer[-1]) {
                            final int n11 = array[0];
                            final int[] buffer3 = this.buffer;
                            --n9;
                            max = max(n11, buffer3[0]);
                        }
                        else {
                            final int[] buffer4 = this.buffer;
                            final int n12 = 0;
                            ++n9;
                            buffer4[n12] = max;
                            max = array[0];
                        }
                        ++n7;
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0569;
                    }
                    if (0 > 0 && n5 <= this.buffer[-1]) {
                        final int n13 = this.list[0];
                        final int[] buffer5 = this.buffer;
                        --n9;
                        n5 = max(n13, buffer5[0]);
                    }
                    else {
                        final int[] buffer6 = this.buffer;
                        final int n14 = 0;
                        ++n9;
                        buffer6[n14] = n5;
                        n5 = this.list[0];
                    }
                    ++n4;
                    n2 ^= 0x1;
                    final int n15 = 0;
                    ++n7;
                    max = array[n15];
                    n2 ^= 0x2;
                    continue;
                }
                case 3: {
                    if (max <= n5) {
                        if (n5 == 1114112) {
                            break Label_0569;
                        }
                        final int[] buffer7 = this.buffer;
                        final int n16 = 0;
                        ++n9;
                        buffer7[n16] = n5;
                    }
                    else {
                        if (max == 1114112) {
                            break Label_0569;
                        }
                        final int[] buffer8 = this.buffer;
                        final int n17 = 0;
                        ++n9;
                        buffer8[n17] = max;
                    }
                    final int[] list2 = this.list;
                    final int n18 = 0;
                    ++n4;
                    n5 = list2[n18];
                    n2 ^= 0x1;
                    final int n19 = 0;
                    ++n7;
                    max = array[n19];
                    n2 ^= 0x2;
                    continue;
                }
                case 1: {
                    if (n5 < max) {
                        final int[] buffer9 = this.buffer;
                        final int n20 = 0;
                        ++n9;
                        buffer9[n20] = n5;
                        final int[] list3 = this.list;
                        final int n21 = 0;
                        ++n4;
                        n5 = list3[n21];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (max < n5) {
                        final int n22 = 0;
                        ++n7;
                        max = array[n22];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0569;
                    }
                    final int[] list4 = this.list;
                    final int n23 = 0;
                    ++n4;
                    n5 = list4[n23];
                    n2 ^= 0x1;
                    final int n24 = 0;
                    ++n7;
                    max = array[n24];
                    n2 ^= 0x2;
                    continue;
                }
                case 2: {
                    if (max < n5) {
                        final int[] buffer10 = this.buffer;
                        final int n25 = 0;
                        ++n9;
                        buffer10[n25] = max;
                        final int n26 = 0;
                        ++n7;
                        max = array[n26];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 < max) {
                        final int[] list5 = this.list;
                        final int n27 = 0;
                        ++n4;
                        n5 = list5[n27];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0569;
                    }
                    final int[] list6 = this.list;
                    final int n28 = 0;
                    ++n4;
                    n5 = list6[n28];
                    n2 ^= 0x1;
                    final int n29 = 0;
                    ++n7;
                    max = array[n29];
                    n2 ^= 0x2;
                    continue;
                }
            }
        }
        final int[] buffer11 = this.buffer;
        final int n30 = 0;
        ++n9;
        buffer11[n30] = 1114112;
        this.len = 0;
        final int[] list7 = this.list;
        this.list = this.buffer;
        this.buffer = list7;
        this.pat = null;
        return this;
    }
    
    private UnicodeSet retain(final int[] array, final int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        final int[] list = this.list;
        final int n3 = 0;
        int n4 = 0;
        ++n4;
        int n5 = list[n3];
        final int n6 = 0;
        int n7 = 0;
        ++n7;
        int n8 = array[n6];
        int n12 = 0;
    Label_0474:
        while (true) {
            switch (n2) {
                case 0: {
                    if (n5 < n8) {
                        final int[] list2 = this.list;
                        final int n9 = 0;
                        ++n4;
                        n5 = list2[n9];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (n8 < n5) {
                        final int n10 = 0;
                        ++n7;
                        n8 = array[n10];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0474;
                    }
                    final int[] buffer = this.buffer;
                    final int n11 = 0;
                    ++n12;
                    buffer[n11] = n5;
                    final int[] list3 = this.list;
                    final int n13 = 0;
                    ++n4;
                    n5 = list3[n13];
                    n2 ^= 0x1;
                    final int n14 = 0;
                    ++n7;
                    n8 = array[n14];
                    n2 ^= 0x2;
                    continue;
                }
                case 3: {
                    if (n5 < n8) {
                        final int[] buffer2 = this.buffer;
                        final int n15 = 0;
                        ++n12;
                        buffer2[n15] = n5;
                        final int[] list4 = this.list;
                        final int n16 = 0;
                        ++n4;
                        n5 = list4[n16];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (n8 < n5) {
                        final int[] buffer3 = this.buffer;
                        final int n17 = 0;
                        ++n12;
                        buffer3[n17] = n8;
                        final int n18 = 0;
                        ++n7;
                        n8 = array[n18];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0474;
                    }
                    final int[] buffer4 = this.buffer;
                    final int n19 = 0;
                    ++n12;
                    buffer4[n19] = n5;
                    final int[] list5 = this.list;
                    final int n20 = 0;
                    ++n4;
                    n5 = list5[n20];
                    n2 ^= 0x1;
                    final int n21 = 0;
                    ++n7;
                    n8 = array[n21];
                    n2 ^= 0x2;
                    continue;
                }
                case 1: {
                    if (n5 < n8) {
                        final int[] list6 = this.list;
                        final int n22 = 0;
                        ++n4;
                        n5 = list6[n22];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (n8 < n5) {
                        final int[] buffer5 = this.buffer;
                        final int n23 = 0;
                        ++n12;
                        buffer5[n23] = n8;
                        final int n24 = 0;
                        ++n7;
                        n8 = array[n24];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0474;
                    }
                    final int[] list7 = this.list;
                    final int n25 = 0;
                    ++n4;
                    n5 = list7[n25];
                    n2 ^= 0x1;
                    final int n26 = 0;
                    ++n7;
                    n8 = array[n26];
                    n2 ^= 0x2;
                    continue;
                }
                case 2: {
                    if (n8 < n5) {
                        final int n27 = 0;
                        ++n7;
                        n8 = array[n27];
                        n2 ^= 0x2;
                        continue;
                    }
                    if (n5 < n8) {
                        final int[] buffer6 = this.buffer;
                        final int n28 = 0;
                        ++n12;
                        buffer6[n28] = n5;
                        final int[] list8 = this.list;
                        final int n29 = 0;
                        ++n4;
                        n5 = list8[n29];
                        n2 ^= 0x1;
                        continue;
                    }
                    if (n5 == 1114112) {
                        break Label_0474;
                    }
                    final int[] list9 = this.list;
                    final int n30 = 0;
                    ++n4;
                    n5 = list9[n30];
                    n2 ^= 0x1;
                    final int n31 = 0;
                    ++n7;
                    n8 = array[n31];
                    n2 ^= 0x2;
                    continue;
                }
            }
        }
        final int[] buffer7 = this.buffer;
        final int n32 = 0;
        ++n12;
        buffer7[n32] = 1114112;
        this.len = 0;
        final int[] list10 = this.list;
        this.list = this.buffer;
        this.buffer = list10;
        this.pat = null;
        return this;
    }
    
    private static final int max(final int n, final int n2) {
        return (n > n2) ? n : n2;
    }
    
    private static synchronized UnicodeSet getInclusions(final int n) {
        if (UnicodeSet.INCLUSIONS == null) {
            UnicodeSet.INCLUSIONS = new UnicodeSet[12];
        }
        if (UnicodeSet.INCLUSIONS[n] == null) {
            final UnicodeSet set = new UnicodeSet();
            switch (n) {
                case 1: {
                    UCharacterProperty.INSTANCE.addPropertyStarts(set);
                    break;
                }
                case 2: {
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(set);
                    break;
                }
                case 6: {
                    UCharacterProperty.INSTANCE.addPropertyStarts(set);
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(set);
                    break;
                }
                case 7: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts(set);
                    UCaseProps.INSTANCE.addPropertyStarts(set);
                    break;
                }
                case 8: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts(set);
                    break;
                }
                case 9: {
                    Norm2AllModes.getNFKCInstance().impl.addPropertyStarts(set);
                    break;
                }
                case 10: {
                    Norm2AllModes.getNFKC_CFInstance().impl.addPropertyStarts(set);
                    break;
                }
                case 11: {
                    Norm2AllModes.getNFCInstance().impl.addCanonIterPropertyStarts(set);
                    break;
                }
                case 4: {
                    UCaseProps.INSTANCE.addPropertyStarts(set);
                    break;
                }
                case 5: {
                    UBiDiProps.INSTANCE.addPropertyStarts(set);
                    break;
                }
                default: {
                    throw new IllegalStateException("UnicodeSet.getInclusions(unknown src " + n + ")");
                }
            }
            UnicodeSet.INCLUSIONS[n] = set;
        }
        return UnicodeSet.INCLUSIONS[n];
    }
    
    private UnicodeSet applyFilter(final Filter filter, final int n) {
        this.clear();
        final UnicodeSet inclusions = getInclusions(n);
        while (0 < inclusions.getRangeCount()) {
            final int rangeStart = inclusions.getRangeStart(0);
            for (int rangeEnd = inclusions.getRangeEnd(0), i = rangeStart; i <= rangeEnd; ++i) {
                if (filter.contains(i)) {
                    if (-1 < 0) {}
                }
                else if (-1 >= 0) {
                    this.add_unchecked(-1, i - 1);
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (-1 >= 0) {
            this.add_unchecked(-1, 1114111);
        }
        return this;
    }
    
    private static String mungeCharName(String trimWhiteSpace) {
        trimWhiteSpace = PatternProps.trimWhiteSpace(trimWhiteSpace);
        StringBuilder append = null;
        while (0 < trimWhiteSpace.length()) {
            trimWhiteSpace.charAt(0);
            Label_0079: {
                if (PatternProps.isWhiteSpace(32)) {
                    if (append == null) {
                        append = new StringBuilder().append(trimWhiteSpace, 0, 0);
                    }
                    else if (append.charAt(append.length() - 1) == ' ') {
                        break Label_0079;
                    }
                }
                if (append != null) {
                    append.append(' ');
                }
            }
            int n = 0;
            ++n;
        }
        return (append == null) ? trimWhiteSpace : append.toString();
    }
    
    public UnicodeSet applyIntPropertyValue(final int n, final int n2) {
        this.checkFrozen();
        if (n == 8192) {
            this.applyFilter(new GeneralCategoryMaskFilter(n2), 1);
        }
        else if (n == 28672) {
            this.applyFilter(new ScriptExtensionsFilter(n2), 2);
        }
        else {
            this.applyFilter(new IntPropertyFilter(n, n2), UCharacterProperty.INSTANCE.getSource(n));
        }
        return this;
    }
    
    public UnicodeSet applyPropertyAlias(final String s, final String s2) {
        return this.applyPropertyAlias(s, s2, null);
    }
    
    public UnicodeSet applyPropertyAlias(final String s, final String s2, final SymbolTable symbolTable) {
        this.checkFrozen();
        if (symbolTable != null && symbolTable instanceof XSymbolTable && ((XSymbolTable)symbolTable).applyPropertyAlias(s, s2, this)) {
            return this;
        }
        if (UnicodeSet.XSYMBOL_TABLE != null && UnicodeSet.XSYMBOL_TABLE.applyPropertyAlias(s, s2, this)) {
            return this;
        }
        if (s2.length() > 0) {
            UCharacter.getPropertyEnum(s);
            if (8192 == 4101) {}
            if ((8192 >= 0 && 8192 < 57) || (8192 >= 4096 && 8192 < 4117) || (8192 >= 8192 && 8192 < 8193)) {
                UCharacter.getPropertyValueEnum(8192, s2);
            }
            else {
                switch (8192) {
                    case 12288: {
                        this.applyFilter(new NumericValueFilter(Double.parseDouble(PatternProps.trimWhiteSpace(s2))), 1);
                        return this;
                    }
                    case 16389: {
                        final int charFromExtendedName = UCharacter.getCharFromExtendedName(mungeCharName(s2));
                        if (charFromExtendedName == -1) {
                            throw new IllegalArgumentException("Invalid character name");
                        }
                        this.clear();
                        this.add_unchecked(charFromExtendedName);
                        return this;
                    }
                    case 16395: {
                        throw new IllegalArgumentException("Unicode_1_Name (na1) not supported");
                    }
                    case 16384: {
                        this.applyFilter(new VersionFilter(VersionInfo.getInstance(mungeCharName(s2))), 2);
                        return this;
                    }
                    case 28672: {
                        UCharacter.getPropertyValueEnum(4106, s2);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unsupported property");
                    }
                }
            }
        }
        else {
            final UPropertyAliases instance = UPropertyAliases.INSTANCE;
            instance.getPropertyValueEnum(8192, s);
            if (1 == -1) {
                instance.getPropertyValueEnum(8192, s);
                if (1 == -1) {
                    instance.getPropertyEnum(s);
                    if (8192 == -1) {}
                    if (8192 < 0 || 8192 >= 57) {
                        if (8192 != -1) {
                            throw new IllegalArgumentException("Missing property value");
                        }
                        if (0 == UPropertyAliases.compare("ANY", s)) {
                            this.set(0, 1114111);
                            return this;
                        }
                        if (0 == UPropertyAliases.compare("ASCII", s)) {
                            this.set(0, 127);
                            return this;
                        }
                        if (0 != UPropertyAliases.compare("Assigned", s)) {
                            throw new IllegalArgumentException("Invalid property alias: " + s + "=" + s2);
                        }
                    }
                }
            }
        }
        this.applyIntPropertyValue(8192, 1);
        if (true) {
            this.complement();
        }
        if (false && this.isEmpty()) {
            throw new IllegalArgumentException("Invalid property value");
        }
        return this;
    }
    
    private static boolean resemblesPropertyPattern(final String s, final int n) {
        return n + 5 <= s.length() && (s.regionMatches(n, "[:", 0, 2) || s.regionMatches(true, n, "\\p", 0, 2) || s.regionMatches(n, "\\N", 0, 2));
    }
    
    private static boolean resemblesPropertyPattern(final RuleCharacterIterator ruleCharacterIterator, int n) {
        n &= 0xFFFFFFFD;
        final Object pos = ruleCharacterIterator.getPos(null);
        final int next = ruleCharacterIterator.next(n);
        if (next == 91 || next == 92) {
            final int next2 = ruleCharacterIterator.next(n & 0xFFFFFFFB);
            final boolean b = (next == 91) ? (next2 == 58) : (next2 == 78 || next2 == 112 || next2 == 80);
        }
        ruleCharacterIterator.setPos(pos);
        return false;
    }
    
    private UnicodeSet applyPropertyPattern(final String s, final ParsePosition parsePosition, final SymbolTable symbolTable) {
        final int index = parsePosition.getIndex();
        if (index + 5 > s.length()) {
            return null;
        }
        int n;
        if (s.regionMatches(index, "[:", 0, 2)) {
            n = PatternProps.skipWhiteSpace(s, index + 2);
            if (n < s.length() && s.charAt(n) == '^') {
                ++n;
            }
        }
        else {
            if (!s.regionMatches(true, index, "\\p", 0, 2) && !s.regionMatches(index, "\\N", 0, 2)) {
                return null;
            }
            final char char1 = s.charAt(index + 1);
            final boolean b = char1 == 'P';
            final boolean b2 = char1 == 'N';
            n = PatternProps.skipWhiteSpace(s, index + 2);
            if (n == s.length() || s.charAt(n++) != '{') {
                return null;
            }
        }
        final int index2 = s.indexOf(true ? ":]" : "}", n);
        if (index2 < 0) {
            return null;
        }
        final int index3 = s.indexOf(61, n);
        String s2;
        String substring;
        if (index3 >= 0 && index3 < index2 && !false) {
            s2 = s.substring(n, index3);
            substring = s.substring(index3 + 1, index2);
        }
        else {
            s2 = s.substring(n, index2);
            substring = "";
            if (false) {
                substring = s2;
                s2 = "na";
            }
        }
        this.applyPropertyAlias(s2, substring, symbolTable);
        if (true) {
            this.complement();
        }
        parsePosition.setIndex(index2 + (true ? 2 : 1));
        return this;
    }
    
    private void applyPropertyPattern(final RuleCharacterIterator ruleCharacterIterator, final StringBuffer sb, final SymbolTable symbolTable) {
        final String lookahead = ruleCharacterIterator.lookahead();
        final ParsePosition parsePosition = new ParsePosition(0);
        this.applyPropertyPattern(lookahead, parsePosition, symbolTable);
        if (parsePosition.getIndex() == 0) {
            syntaxError(ruleCharacterIterator, "Invalid property pattern");
        }
        ruleCharacterIterator.jumpahead(parsePosition.getIndex());
        sb.append(lookahead.substring(0, parsePosition.getIndex()));
    }
    
    private static final void addCaseMapping(final UnicodeSet set, final int n, final StringBuilder sb) {
        if (n >= 0) {
            if (n > 31) {
                set.add(n);
            }
            else {
                set.add(sb.toString());
                sb.setLength(0);
            }
        }
    }
    
    public UnicodeSet closeOver(final int n) {
        this.checkFrozen();
        if ((n & 0x6) != 0x0) {
            final UCaseProps instance = UCaseProps.INSTANCE;
            final UnicodeSet set = new UnicodeSet(this);
            final ULocale root = ULocale.ROOT;
            if ((n & 0x2) != 0x0) {
                set.strings.clear();
            }
            final int rangeCount = this.getRangeCount();
            final StringBuilder sb = new StringBuilder();
            final int[] array = { 0 };
            while (0 < rangeCount) {
                final int rangeStart = this.getRangeStart(0);
                final int rangeEnd = this.getRangeEnd(0);
                if ((n & 0x2) != 0x0) {
                    for (int i = rangeStart; i <= rangeEnd; ++i) {
                        instance.addCaseClosure(i, set);
                    }
                }
                else {
                    for (int j = rangeStart; j <= rangeEnd; ++j) {
                        addCaseMapping(set, instance.toFullLower(j, null, sb, root, array), sb);
                        addCaseMapping(set, instance.toFullTitle(j, null, sb, root, array), sb);
                        addCaseMapping(set, instance.toFullUpper(j, null, sb, root, array), sb);
                        addCaseMapping(set, instance.toFullFolding(j, sb, 0), sb);
                    }
                }
                int n2 = 0;
                ++n2;
            }
            if (!this.strings.isEmpty()) {
                if ((n & 0x2) != 0x0) {
                    final Iterator<String> iterator = (Iterator<String>)this.strings.iterator();
                    while (iterator.hasNext()) {
                        final String foldCase = UCharacter.foldCase(iterator.next(), 0);
                        if (!instance.addStringCaseClosure(foldCase, set)) {
                            set.add(foldCase);
                        }
                    }
                }
                else {
                    final BreakIterator wordInstance = BreakIterator.getWordInstance(root);
                    for (final String s : this.strings) {
                        set.add(UCharacter.toLowerCase(root, s));
                        set.add(UCharacter.toTitleCase(root, s, wordInstance));
                        set.add(UCharacter.toUpperCase(root, s));
                        set.add(UCharacter.foldCase(s, 0));
                    }
                }
            }
            this.set(set);
        }
        return this;
    }
    
    public boolean isFrozen() {
        return this.bmpSet != null || this.stringSpan != null;
    }
    
    public UnicodeSet freeze() {
        if (!this.isFrozen()) {
            this.buffer = null;
            if (this.list.length > this.len + 16) {
                final int n = (this.len == 0) ? 1 : this.len;
                final int[] list = this.list;
                this.list = new int[n];
                int n2 = n;
                while (n2-- > 0) {
                    this.list[n2] = list[n2];
                }
            }
            if (!this.strings.isEmpty()) {
                this.stringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), 63);
                if (!this.stringSpan.needsStringSpanUTF16()) {
                    this.stringSpan = null;
                }
            }
            if (this.stringSpan == null) {
                this.bmpSet = new BMPSet(this.list, this.len);
            }
        }
        return this;
    }
    
    public int span(final CharSequence charSequence, final SpanCondition spanCondition) {
        return this.span(charSequence, 0, spanCondition);
    }
    
    public int span(final CharSequence charSequence, final int n, final SpanCondition spanCondition) {
        final int length = charSequence.length();
        if (0 >= 0) {
            if (0 >= length) {
                return length;
            }
        }
        if (this.bmpSet != null) {
            return 0 + this.bmpSet.span(charSequence, 0, length, spanCondition);
        }
        final int n2 = length - 0;
        if (this.stringSpan != null) {
            return 0 + this.stringSpan.span(charSequence, 0, n2, spanCondition);
        }
        if (!this.strings.isEmpty()) {
            final UnicodeSetStringSpan unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), (spanCondition == SpanCondition.NOT_CONTAINED) ? 41 : 42);
            if (unicodeSetStringSpan.needsStringSpanUTF16()) {
                return 0 + unicodeSetStringSpan.span(charSequence, 0, n2, spanCondition);
            }
        }
        while (spanCondition != SpanCondition.NOT_CONTAINED == this.contains(Character.codePointAt(charSequence, 0))) {
            Character.offsetByCodePoints(charSequence, 0, 1);
            if (0 >= length) {
                return 0;
            }
        }
        return 0;
    }
    
    public int spanBack(final CharSequence charSequence, final SpanCondition spanCondition) {
        return this.spanBack(charSequence, charSequence.length(), spanCondition);
    }
    
    public int spanBack(final CharSequence charSequence, int length, final SpanCondition spanCondition) {
        if (length <= 0) {
            return 0;
        }
        if (length > charSequence.length()) {
            length = charSequence.length();
        }
        if (this.bmpSet != null) {
            return this.bmpSet.spanBack(charSequence, length, spanCondition);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanBack(charSequence, length, spanCondition);
        }
        if (!this.strings.isEmpty()) {
            final UnicodeSetStringSpan unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList(this.strings), (spanCondition == SpanCondition.NOT_CONTAINED) ? 25 : 26);
            if (unicodeSetStringSpan.needsStringSpanUTF16()) {
                return unicodeSetStringSpan.spanBack(charSequence, length, spanCondition);
            }
        }
        final boolean b = spanCondition != SpanCondition.NOT_CONTAINED;
        int offsetByCodePoints = length;
        while (b == this.contains(Character.codePointBefore(charSequence, offsetByCodePoints))) {
            offsetByCodePoints = Character.offsetByCodePoints(charSequence, offsetByCodePoints, -1);
            if (offsetByCodePoints <= 0) {
                return offsetByCodePoints;
            }
        }
        return offsetByCodePoints;
    }
    
    public UnicodeSet cloneAsThawed() {
        final UnicodeSet set = (UnicodeSet)this.clone();
        set.bmpSet = null;
        set.stringSpan = null;
        return set;
    }
    
    private void checkFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }
    
    public Iterator iterator() {
        return new UnicodeSetIterator2(this);
    }
    
    public boolean containsAll(final Collection collection) {
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsNone(final Collection collection) {
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public final boolean containsSome(final Collection collection) {
        return !this.containsNone(collection);
    }
    
    public UnicodeSet addAll(final String... array) {
        this.checkFrozen();
        while (0 < array.length) {
            this.add(array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public UnicodeSet removeAll(final Collection collection) {
        this.checkFrozen();
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.remove(iterator.next());
        }
        return this;
    }
    
    public UnicodeSet retainAll(final Collection collection) {
        this.checkFrozen();
        final UnicodeSet set = new UnicodeSet();
        set.addAll(collection);
        this.retainAll(set);
        return this;
    }
    
    public int compareTo(final UnicodeSet set) {
        return this.compareTo(set, ComparisonStyle.SHORTER_FIRST);
    }
    
    public int compareTo(final UnicodeSet set, final ComparisonStyle comparisonStyle) {
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC) {
            final int n = this.size() - set.size();
            if (n != 0) {
                return (n < 0 == (comparisonStyle == ComparisonStyle.SHORTER_FIRST)) ? -1 : 1;
            }
        }
        int n2;
        while (0 == (n2 = this.list[0] - set.list[0])) {
            if (this.list[0] == 1114112) {
                return compare(this.strings, set.strings);
            }
            int n3 = 0;
            ++n3;
        }
        if (this.list[0] == 1114112) {
            if (this.strings.isEmpty()) {
                return 1;
            }
            return compare(this.strings.first(), set.list[0]);
        }
        else {
            if (set.list[0] != 1114112) {
                return false ? (-n2) : n2;
            }
            if (set.strings.isEmpty()) {
                return -1;
            }
            return -compare(set.strings.first(), this.list[0]);
        }
    }
    
    public int compareTo(final Iterable iterable) {
        return compare(this, iterable);
    }
    
    public static int compare(final String s, final int n) {
        return CharSequences.compare(s, n);
    }
    
    public static int compare(final int n, final String s) {
        return -CharSequences.compare(s, n);
    }
    
    public static int compare(final Iterable iterable, final Iterable iterable2) {
        return compare(iterable.iterator(), iterable2.iterator());
    }
    
    @Deprecated
    public static int compare(final Iterator iterator, final Iterator iterator2) {
        while (iterator.hasNext()) {
            if (!iterator2.hasNext()) {
                return 1;
            }
            final int compareTo = iterator.next().compareTo(iterator2.next());
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return iterator2.hasNext() ? -1 : 0;
    }
    
    public static int compare(final Collection collection, final Collection collection2, final ComparisonStyle comparisonStyle) {
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC) {
            final int n = collection.size() - collection2.size();
            if (n != 0) {
                return (n < 0 == (comparisonStyle == ComparisonStyle.SHORTER_FIRST)) ? -1 : 1;
            }
        }
        return compare(collection, collection2);
    }
    
    public static Collection addAllTo(final Iterable iterable, final Collection collection) {
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
        return collection;
    }
    
    public static Object[] addAllTo(final Iterable iterable, final Object[] array) {
        for (final Object next : iterable) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            array[n] = next;
        }
        return array;
    }
    
    public Iterable strings() {
        return Collections.unmodifiableSortedSet((SortedSet<Object>)this.strings);
    }
    
    @Deprecated
    public static int getSingleCodePoint(final CharSequence charSequence) {
        return CharSequences.getSingleCodePoint(charSequence);
    }
    
    @Deprecated
    public UnicodeSet addBridges(final UnicodeSet set) {
        final UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(new UnicodeSet(this).complement());
        while (unicodeSetIterator.nextRange()) {
            if (unicodeSetIterator.codepoint != 0 && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING && unicodeSetIterator.codepointEnd != 1114111 && set.contains(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd)) {
                this.add(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd);
            }
        }
        return this;
    }
    
    @Deprecated
    public int findIn(final CharSequence charSequence, int i, final boolean b) {
        while (i < charSequence.length()) {
            final int char1 = UTF16.charAt(charSequence, i);
            if (this.contains(char1) != b) {
                break;
            }
            i += UTF16.getCharCount(char1);
        }
        return i;
    }
    
    @Deprecated
    public int findLastIn(final CharSequence charSequence, int i, final boolean b) {
        --i;
        while (i >= 0) {
            final int char1 = UTF16.charAt(charSequence, i);
            if (this.contains(char1) != b) {
                break;
            }
            i -= UTF16.getCharCount(char1);
        }
        return (i < 0) ? -1 : i;
    }
    
    @Deprecated
    public String stripFrom(final CharSequence charSequence, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        while (0 < charSequence.length()) {
            final int in = this.findIn(charSequence, 0, !b);
            sb.append(charSequence.subSequence(0, in));
            this.findIn(charSequence, in, b);
        }
        return sb.toString();
    }
    
    public static XSymbolTable getDefaultXSymbolTable() {
        return UnicodeSet.XSYMBOL_TABLE;
    }
    
    public static void setDefaultXSymbolTable(final XSymbolTable xsymbol_TABLE) {
        UnicodeSet.XSYMBOL_TABLE = xsymbol_TABLE;
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((UnicodeSet)o);
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static VersionInfo access$000() {
        return UnicodeSet.NO_VERSION;
    }
    
    static int access$100(final UnicodeSet set) {
        return set.len;
    }
    
    static int[] access$200(final UnicodeSet set) {
        return set.list;
    }
    
    static {
        EMPTY = new UnicodeSet().freeze();
        ALL_CODE_POINTS = new UnicodeSet(0, 1114111).freeze();
        UnicodeSet.XSYMBOL_TABLE = null;
        UnicodeSet.INCLUSIONS = null;
        NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0);
    }
    
    public enum SpanCondition
    {
        NOT_CONTAINED("NOT_CONTAINED", 0), 
        CONTAINED("CONTAINED", 1), 
        SIMPLE("SIMPLE", 2), 
        CONDITION_COUNT("CONDITION_COUNT", 3);
        
        private static final SpanCondition[] $VALUES;
        
        private SpanCondition(final String s, final int n) {
        }
        
        static {
            $VALUES = new SpanCondition[] { SpanCondition.NOT_CONTAINED, SpanCondition.CONTAINED, SpanCondition.SIMPLE, SpanCondition.CONDITION_COUNT };
        }
    }
    
    public enum ComparisonStyle
    {
        SHORTER_FIRST("SHORTER_FIRST", 0), 
        LEXICOGRAPHIC("LEXICOGRAPHIC", 1), 
        LONGER_FIRST("LONGER_FIRST", 2);
        
        private static final ComparisonStyle[] $VALUES;
        
        private ComparisonStyle(final String s, final int n) {
        }
        
        static {
            $VALUES = new ComparisonStyle[] { ComparisonStyle.SHORTER_FIRST, ComparisonStyle.LEXICOGRAPHIC, ComparisonStyle.LONGER_FIRST };
        }
    }
    
    private static class UnicodeSetIterator2 implements Iterator
    {
        private int[] sourceList;
        private int len;
        private int item;
        private int current;
        private int limit;
        private TreeSet sourceStrings;
        private Iterator stringIterator;
        private char[] buffer;
        
        UnicodeSetIterator2(final UnicodeSet set) {
            this.len = UnicodeSet.access$100(set) - 1;
            if (this.item >= this.len) {
                this.stringIterator = set.strings.iterator();
                this.sourceList = null;
            }
            else {
                this.sourceStrings = set.strings;
                this.sourceList = UnicodeSet.access$200(set);
                this.current = this.sourceList[this.item++];
                this.limit = this.sourceList[this.item++];
            }
        }
        
        public boolean hasNext() {
            return this.sourceList != null || this.stringIterator.hasNext();
        }
        
        public String next() {
            if (this.sourceList == null) {
                return this.stringIterator.next();
            }
            final int n = this.current++;
            if (this.current >= this.limit) {
                if (this.item >= this.len) {
                    this.stringIterator = this.sourceStrings.iterator();
                    this.sourceList = null;
                }
                else {
                    this.current = this.sourceList[this.item++];
                    this.limit = this.sourceList[this.item++];
                }
            }
            if (n <= 65535) {
                return String.valueOf((char)n);
            }
            if (this.buffer == null) {
                this.buffer = new char[2];
            }
            final int n2 = n - 65536;
            this.buffer[0] = (char)((n2 >>> 10) + 55296);
            this.buffer[1] = (char)((n2 & 0x3FF) + 56320);
            return String.valueOf(this.buffer);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Object next() {
            return this.next();
        }
    }
    
    public abstract static class XSymbolTable implements SymbolTable
    {
        public UnicodeMatcher lookupMatcher(final int n) {
            return null;
        }
        
        public boolean applyPropertyAlias(final String s, final String s2, final UnicodeSet set) {
            return false;
        }
        
        public char[] lookup(final String s) {
            return null;
        }
        
        public String parseReference(final String s, final ParsePosition parsePosition, final int n) {
            return null;
        }
    }
    
    private static class VersionFilter implements Filter
    {
        VersionInfo version;
        
        VersionFilter(final VersionInfo version) {
            this.version = version;
        }
        
        public boolean contains(final int n) {
            final VersionInfo age = UCharacter.getAge(n);
            return age != UnicodeSet.access$000() && age.compareTo(this.version) <= 0;
        }
    }
    
    private interface Filter
    {
        boolean contains(final int p0);
    }
    
    private static class ScriptExtensionsFilter implements Filter
    {
        int script;
        
        ScriptExtensionsFilter(final int script) {
            this.script = script;
        }
        
        public boolean contains(final int n) {
            return UScript.hasScript(n, this.script);
        }
    }
    
    private static class IntPropertyFilter implements Filter
    {
        int prop;
        int value;
        
        IntPropertyFilter(final int prop, final int value) {
            this.prop = prop;
            this.value = value;
        }
        
        public boolean contains(final int n) {
            return UCharacter.getIntPropertyValue(n, this.prop) == this.value;
        }
    }
    
    private static class GeneralCategoryMaskFilter implements Filter
    {
        int mask;
        
        GeneralCategoryMaskFilter(final int mask) {
            this.mask = mask;
        }
        
        public boolean contains(final int n) {
            return (1 << UCharacter.getType(n) & this.mask) != 0x0;
        }
    }
    
    private static class NumericValueFilter implements Filter
    {
        double value;
        
        NumericValueFilter(final double value) {
            this.value = value;
        }
        
        public boolean contains(final int n) {
            return UCharacter.getUnicodeNumericValue(n) == this.value;
        }
    }
}
