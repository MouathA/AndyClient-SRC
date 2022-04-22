package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.io.*;
import java.util.*;
import com.ibm.icu.text.*;

public final class Normalizer2Impl
{
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final byte[] DATA_FORMAT;
    private static final Trie2.ValueMapper segmentStarterMapper;
    public static final int MIN_CCC_LCCC_CP = 768;
    public static final int MIN_YES_YES_WITH_CC = 65281;
    public static final int JAMO_VT = 65280;
    public static final int MIN_NORMAL_MAYBE_YES = 65024;
    public static final int JAMO_L = 1;
    public static final int MAX_DELTA = 64;
    public static final int IX_NORM_TRIE_OFFSET = 0;
    public static final int IX_EXTRA_DATA_OFFSET = 1;
    public static final int IX_SMALL_FCD_OFFSET = 2;
    public static final int IX_RESERVED3_OFFSET = 3;
    public static final int IX_TOTAL_SIZE = 7;
    public static final int IX_MIN_DECOMP_NO_CP = 8;
    public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
    public static final int IX_MIN_YES_NO = 10;
    public static final int IX_MIN_NO_NO = 11;
    public static final int IX_LIMIT_NO_NO = 12;
    public static final int IX_MIN_MAYBE_YES = 13;
    public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
    public static final int IX_COUNT = 16;
    public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
    public static final int MAPPING_HAS_RAW_MAPPING = 64;
    public static final int MAPPING_NO_COMP_BOUNDARY_AFTER = 32;
    public static final int MAPPING_LENGTH_MASK = 31;
    public static final int COMP_1_LAST_TUPLE = 32768;
    public static final int COMP_1_TRIPLE = 1;
    public static final int COMP_1_TRAIL_LIMIT = 13312;
    public static final int COMP_1_TRAIL_MASK = 32766;
    public static final int COMP_1_TRAIL_SHIFT = 9;
    public static final int COMP_2_TRAIL_SHIFT = 6;
    public static final int COMP_2_TRAIL_MASK = 65472;
    private VersionInfo dataVersion;
    private int minDecompNoCP;
    private int minCompNoMaybeCP;
    private int minYesNo;
    private int minYesNoMappingsOnly;
    private int minNoNo;
    private int limitNoNo;
    private int minMaybeYes;
    private Trie2_16 normTrie;
    private String maybeYesCompositions;
    private String extraData;
    private byte[] smallFCD;
    private int[] tccc180;
    private Trie2_32 canonIterData;
    private ArrayList canonStartSets;
    private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
    private static final int CANON_HAS_COMPOSITIONS = 1073741824;
    private static final int CANON_HAS_SET = 2097152;
    private static final int CANON_VALUE_MASK = 2097151;
    
    public Normalizer2Impl load(final InputStream inputStream) {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        this.dataVersion = ICUBinary.readHeaderAndDataVersion(bufferedInputStream, Normalizer2Impl.DATA_FORMAT, Normalizer2Impl.IS_ACCEPTABLE);
        final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        final int n = dataInputStream.readInt() / 4;
        if (n <= 13) {
            throw new IOException("Normalizer2 data: not enough indexes");
        }
        final int[] array = new int[n];
        array[0] = n * 4;
        while (1 < n) {
            array[1] = dataInputStream.readInt();
            int n2 = 0;
            ++n2;
        }
        this.minDecompNoCP = array[8];
        this.minCompNoMaybeCP = array[9];
        this.minYesNo = array[10];
        this.minYesNoMappingsOnly = array[14];
        this.minNoNo = array[11];
        this.limitNoNo = array[12];
        this.minMaybeYes = array[13];
        int n2 = array[0];
        final int n3 = array[1];
        this.normTrie = Trie2_16.createFromSerialized(dataInputStream);
        final int serializedLength = this.normTrie.getSerializedLength();
        if (serializedLength > n3 - 1) {
            throw new IOException("Normalizer2 data: not enough bytes for normTrie");
        }
        dataInputStream.skipBytes(n3 - 1 - serializedLength);
        n2 = n3;
        final int n4 = array[2];
        final int n5 = (n4 - 1) / 2;
        int n6 = 0;
        if (n5 != 0) {
            final char[] array2 = new char[n5];
            while (0 < n5) {
                array2[0] = dataInputStream.readChar();
                ++n6;
            }
            this.maybeYesCompositions = new String(array2);
            this.extraData = this.maybeYesCompositions.substring(65024 - this.minMaybeYes);
        }
        n2 = n4;
        this.smallFCD = new byte[256];
        while (true) {
            this.smallFCD[0] = dataInputStream.readByte();
            ++n6;
        }
    }
    
    public Normalizer2Impl load(final String s) {
        return this.load(ICUData.getRequiredStream(s));
    }
    
    public void addPropertyStarts(final UnicodeSet set) {
        final Iterator iterator = this.normTrie.iterator();
        Trie2.Range range;
        while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
        while (true) {
            set.add(44032);
            set.add(44033);
            final int n;
            n += 28;
        }
    }
    
    public void addCanonIterPropertyStarts(final UnicodeSet set) {
        this.ensureCanonIterData();
        final Iterator iterator = this.canonIterData.iterator(Normalizer2Impl.segmentStarterMapper);
        Trie2.Range range;
        while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
    }
    
    public Trie2_16 getNormTrie() {
        return this.normTrie;
    }
    
    public synchronized Normalizer2Impl ensureCanonIterData() {
        if (this.canonIterData == null) {
            final Trie2Writable trie2Writable = new Trie2Writable(0, 0);
            this.canonStartSets = new ArrayList();
            final Iterator iterator = this.normTrie.iterator();
            Trie2.Range range;
            while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
                final int value = range.value;
                if (value != 0) {
                    if (this.minYesNo <= value && value < this.minNoNo) {
                        continue;
                    }
                    for (int i = range.startCodePoint; i <= range.endCodePoint; ++i) {
                        int value2;
                        final int n = value2 = trie2Writable.get(i);
                        if (value >= this.minMaybeYes) {
                            value2 |= Integer.MIN_VALUE;
                            if (value < 65024) {
                                value2 |= 0x40000000;
                            }
                        }
                        else if (value < this.minYesNo) {
                            value2 |= 0x40000000;
                        }
                        else {
                            int mapAlgorithmic;
                            int norm16;
                            for (mapAlgorithmic = i, norm16 = value; this.limitNoNo <= norm16 && norm16 < this.minMaybeYes; norm16 = this.getNorm16(mapAlgorithmic)) {
                                mapAlgorithmic = this.mapAlgorithmic(mapAlgorithmic, norm16);
                            }
                            if (this.minYesNo <= norm16 && norm16 < this.limitNoNo) {
                                final char char1 = this.extraData.charAt(norm16);
                                final int n2 = char1 & '\u001f';
                                if ((char1 & '\u0080') != 0x0 && i == mapAlgorithmic && (this.extraData.charAt(norm16 - 1) & '\u00ff') != 0x0) {
                                    value2 |= Integer.MIN_VALUE;
                                }
                                if (n2 != 0) {
                                    final int n3 = ++norm16 + n2;
                                    int n4 = this.extraData.codePointAt(norm16);
                                    this.addToStartSet(trie2Writable, i, n4);
                                    if (norm16 >= this.minNoNo) {
                                        while ((norm16 += Character.charCount(n4)) < n3) {
                                            n4 = this.extraData.codePointAt(norm16);
                                            final int value3 = trie2Writable.get(n4);
                                            if ((value3 & Integer.MIN_VALUE) == 0x0) {
                                                trie2Writable.set(n4, value3 | Integer.MIN_VALUE);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                this.addToStartSet(trie2Writable, i, mapAlgorithmic);
                            }
                        }
                        if (value2 != n) {
                            trie2Writable.set(i, value2);
                        }
                    }
                }
            }
            this.canonIterData = trie2Writable.toTrie2_32();
        }
        return this;
    }
    
    public int getNorm16(final int n) {
        return this.normTrie.get(n);
    }
    
    public int getCompQuickCheck(final int n) {
        if (n < this.minNoNo || 65281 <= n) {
            return 1;
        }
        if (this.minMaybeYes <= n) {
            return 2;
        }
        return 0;
    }
    
    public boolean isCompNo(final int n) {
        return this.minNoNo <= n && n < this.minMaybeYes;
    }
    
    public int getCC(final int n) {
        if (n >= 65024) {
            return n & 0xFF;
        }
        if (n < this.minNoNo || this.limitNoNo <= n) {
            return 0;
        }
        return this.getCCFromNoNo(n);
    }
    
    public static int getCCFromYesOrMaybe(final int n) {
        return (n >= 65024) ? (n & 0xFF) : 0;
    }
    
    public int getFCD16(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifge            6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: iload_1        
        //     7: sipush          384
        //    10: if_icmpge       20
        //    13: aload_0        
        //    14: getfield        com/ibm/icu/impl/Normalizer2Impl.tccc180:[I
        //    17: iload_1        
        //    18: iaload         
        //    19: ireturn        
        //    20: iload_1        
        //    21: ldc_w           65535
        //    24: if_icmpgt       34
        //    27: aload_0        
        //    28: iload_1        
        //    29: ifne            34
        //    32: iconst_0       
        //    33: ireturn        
        //    34: aload_0        
        //    35: iload_1        
        //    36: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getFCD16FromNormData:(I)I
        //    39: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0034 (coming from #0029).
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
    
    public int getFCD16FromBelow180(final int n) {
        return this.tccc180[n];
    }
    
    public int getFCD16FromNormData(int mapAlgorithmic) {
        while (true) {
            final int norm16 = this.getNorm16(mapAlgorithmic);
            if (norm16 <= this.minYesNo) {
                return 0;
            }
            if (norm16 >= 65024) {
                final int n = norm16 & 0xFF;
                return n | n << 8;
            }
            if (norm16 >= this.minMaybeYes) {
                return 0;
            }
            if (this >= norm16) {
                mapAlgorithmic = this.mapAlgorithmic(mapAlgorithmic, norm16);
            }
            else {
                final char char1 = this.extraData.charAt(norm16);
                if ((char1 & '\u001f') == 0x0) {
                    return 511;
                }
                int n2 = char1 >> 8;
                if ((char1 & '\u0080') != 0x0) {
                    n2 |= (this.extraData.charAt(norm16 - 1) & '\uff00');
                }
                return n2;
            }
        }
    }
    
    public String getDecomposition(int mapAlgorithmic) {
        int norm16;
        while (mapAlgorithmic >= this.minDecompNoCP && this < (norm16 = this.getNorm16(mapAlgorithmic))) {
            if (this == norm16) {
                final StringBuilder sb = new StringBuilder();
                Hangul.decompose(mapAlgorithmic, sb);
                return sb.toString();
            }
            if (this < norm16) {
                return this.extraData.substring(norm16, norm16 + (this.extraData.charAt(norm16++) & '\u001f'));
            }
            mapAlgorithmic = this.mapAlgorithmic(mapAlgorithmic, norm16);
        }
        return null;
    }
    
    public String getRawDecomposition(final int n) {
        int norm16;
        if (n < this.minDecompNoCP || this >= (norm16 = this.getNorm16(n))) {
            return null;
        }
        if (this == norm16) {
            final StringBuilder sb = new StringBuilder();
            Hangul.getRawDecomposition(n, sb);
            return sb.toString();
        }
        if (this >= norm16) {
            return UTF16.valueOf(this.mapAlgorithmic(n, norm16));
        }
        final char char1 = this.extraData.charAt(norm16);
        final int n2 = char1 & '\u001f';
        if ((char1 & '@') == 0x0) {
            ++norm16;
            return this.extraData.substring(norm16, norm16 + n2);
        }
        final int n3 = norm16 - (char1 >> 7 & 0x1) - 1;
        final char char2 = this.extraData.charAt(n3);
        if (char2 <= '\u001f') {
            return this.extraData.substring(n3 - char2, n3);
        }
        final StringBuilder append = new StringBuilder(n2 - 1).append(char2);
        norm16 += 3;
        return append.append(this.extraData, norm16, norm16 + n2 - 2).toString();
    }
    
    public boolean isCanonSegmentStarter(final int n) {
        return this.canonIterData.get(n) >= 0;
    }
    
    public boolean getCanonStartSet(final int n, final UnicodeSet set) {
        final int n2 = this.canonIterData.get(n) & Integer.MAX_VALUE;
        if (n2 == 0) {
            return false;
        }
        set.clear();
        final int n3 = n2 & 0x1FFFFF;
        if ((n2 & 0x200000) != 0x0) {
            set.addAll((UnicodeSet)this.canonStartSets.get(n3));
        }
        else if (n3 != 0) {
            set.add(n3);
        }
        if ((n2 & 0x40000000) != 0x0) {
            final int norm16 = this.getNorm16(n);
            if (norm16 == 1) {
                final int n4 = 44032 + (n - 4352) * 588;
                set.add(n4, n4 + 588 - 1);
            }
            else {
                this.addComposites(this.getCompositionsList(norm16), set);
            }
        }
        return true;
    }
    
    public int decompose(final CharSequence charSequence, int i, final int n, final ReorderingBuffer reorderingBuffer) {
        final int minDecompNoCP = this.minDecompNoCP;
        int n2 = i;
        while (true) {
            final int n3 = i;
            while (i != n) {
                final int fromU16SingleLead;
                if (charSequence.charAt(i) < minDecompNoCP || this >= (fromU16SingleLead = this.normTrie.getFromU16SingleLead((char)0))) {
                    ++i;
                }
                else {
                    if (!UTF16.isSurrogate((char)0)) {
                        break;
                    }
                    if (UTF16Plus.isSurrogateLead(0)) {
                        final char char1;
                        if (i + 1 != n && Character.isLowSurrogate(char1 = charSequence.charAt(i + 1))) {
                            Character.toCodePoint((char)0, char1);
                        }
                    }
                    else {
                        final char char2;
                        if (n3 < i && Character.isHighSurrogate(char2 = charSequence.charAt(i - 1))) {
                            --i;
                            Character.toCodePoint(char2, (char)0);
                        }
                    }
                    final int norm16;
                    if (this < (norm16 = this.getNorm16(0))) {
                        break;
                    }
                    i += Character.charCount(0);
                }
            }
            if (i != n3) {
                if (reorderingBuffer != null) {
                    reorderingBuffer.flushAndAppendZeroCC(charSequence, n3, i);
                }
                else {
                    n2 = i;
                }
            }
            if (i == n) {
                return i;
            }
            i += Character.charCount(0);
            if (reorderingBuffer != null) {
                this.decompose(0, 0, reorderingBuffer);
            }
            else {
                if (this < 0) {
                    break;
                }
                final int ccFromYesOrMaybe = getCCFromYesOrMaybe(0);
                if (0 > ccFromYesOrMaybe && ccFromYesOrMaybe != 0) {
                    break;
                }
                final int n4;
                if ((n4 = ccFromYesOrMaybe) > 1) {
                    continue;
                }
                n2 = i;
            }
        }
        return n2;
    }
    
    public void decomposeAndAppend(final CharSequence charSequence, final boolean b, final ReorderingBuffer reorderingBuffer) {
        final int length = charSequence.length();
        if (length == 0) {
            return;
        }
        if (b) {
            this.decompose(charSequence, 0, length, reorderingBuffer);
            return;
        }
        int n;
        int i;
        int n3;
        int n2;
        for (n = Character.codePointAt(charSequence, 0), n2 = (n3 = (i = this.getCC(this.getNorm16(n)))); i != 0; i = this.getCC(this.getNorm16(n))) {
            n2 = i;
            final int n4 = 0 + Character.charCount(n);
            if (0 >= length) {
                break;
            }
            n = Character.codePointAt(charSequence, 0);
        }
        reorderingBuffer.append(charSequence, 0, 0, n3, n2);
        reorderingBuffer.append(charSequence, 0, length);
    }
    
    public boolean compose(final CharSequence p0, final int p1, final int p2, final boolean p3, final boolean p4, final ReorderingBuffer p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/impl/Normalizer2Impl.minCompNoMaybeCP:I
        //     4: istore          7
        //     6: iload_2        
        //     7: istore          8
        //     9: iload_2        
        //    10: istore          9
        //    12: iload_2        
        //    13: iload_3        
        //    14: if_icmpeq       165
        //    17: aload_1        
        //    18: iload_2        
        //    19: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    24: dup            
        //    25: istore          10
        //    27: iload           7
        //    29: if_icmplt       48
        //    32: aload_0        
        //    33: aload_0        
        //    34: getfield        com/ibm/icu/impl/Normalizer2Impl.normTrie:Lcom/ibm/icu/impl/Trie2_16;
        //    37: iconst_0       
        //    38: i2c            
        //    39: invokevirtual   com/ibm/icu/impl/Trie2_16.getFromU16SingleLead:(C)I
        //    42: dup            
        //    43: istore          11
        //    45: if_icmpge       54
        //    48: iinc            2, 1
        //    51: goto            12
        //    54: iconst_0       
        //    55: i2c            
        //    56: invokestatic    com/ibm/icu/text/UTF16.isSurrogate:(C)Z
        //    59: ifne            65
        //    62: goto            165
        //    65: iconst_0       
        //    66: invokestatic    com/ibm/icu/impl/Normalizer2Impl$UTF16Plus.isSurrogateLead:(I)Z
        //    69: ifeq            108
        //    72: iload_2        
        //    73: iconst_1       
        //    74: iadd           
        //    75: iload_3        
        //    76: if_icmpeq       143
        //    79: aload_1        
        //    80: iload_2        
        //    81: iconst_1       
        //    82: iadd           
        //    83: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    88: dup            
        //    89: istore          13
        //    91: invokestatic    java/lang/Character.isLowSurrogate:(C)Z
        //    94: ifeq            143
        //    97: iconst_0       
        //    98: i2c            
        //    99: iconst_0       
        //   100: invokestatic    java/lang/Character.toCodePoint:(CC)I
        //   103: istore          10
        //   105: goto            143
        //   108: iload           9
        //   110: iload_2        
        //   111: if_icmpge       143
        //   114: aload_1        
        //   115: iload_2        
        //   116: iconst_1       
        //   117: isub           
        //   118: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   123: dup            
        //   124: istore          13
        //   126: invokestatic    java/lang/Character.isHighSurrogate:(C)Z
        //   129: ifeq            143
        //   132: iinc            2, -1
        //   135: iconst_0       
        //   136: iconst_0       
        //   137: i2c            
        //   138: invokestatic    java/lang/Character.toCodePoint:(CC)I
        //   141: istore          10
        //   143: aload_0        
        //   144: aload_0        
        //   145: iconst_0       
        //   146: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getNorm16:(I)I
        //   149: dup            
        //   150: istore          11
        //   152: if_icmpge       165
        //   155: iload_2        
        //   156: iconst_0       
        //   157: invokestatic    java/lang/Character.charCount:(I)I
        //   160: iadd           
        //   161: istore_2       
        //   162: goto            12
        //   165: iload_2        
        //   166: iload           9
        //   168: if_icmpeq       274
        //   171: iload_2        
        //   172: iload_3        
        //   173: if_icmpne       194
        //   176: iload           5
        //   178: ifeq            612
        //   181: aload           6
        //   183: aload_1        
        //   184: iload           9
        //   186: iload_2        
        //   187: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.flushAndAppendZeroCC:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   190: pop            
        //   191: goto            612
        //   194: iload_2        
        //   195: iconst_1       
        //   196: isub           
        //   197: istore          8
        //   199: aload_1        
        //   200: iload           8
        //   202: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   207: invokestatic    java/lang/Character.isLowSurrogate:(C)Z
        //   210: ifeq            239
        //   213: iload           9
        //   215: iload           8
        //   217: if_icmpge       239
        //   220: aload_1        
        //   221: iload           8
        //   223: iconst_1       
        //   224: isub           
        //   225: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   230: invokestatic    java/lang/Character.isHighSurrogate:(C)Z
        //   233: ifeq            239
        //   236: iinc            8, -1
        //   239: iload           5
        //   241: ifeq            268
        //   244: aload           6
        //   246: aload_1        
        //   247: iload           9
        //   249: iload           8
        //   251: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.flushAndAppendZeroCC:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   254: pop            
        //   255: aload           6
        //   257: aload_1        
        //   258: iload           8
        //   260: iload_2        
        //   261: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.append:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   264: pop            
        //   265: goto            268
        //   268: iload_2        
        //   269: istore          9
        //   271: goto            282
        //   274: iload_2        
        //   275: iload_3        
        //   276: if_icmpne       282
        //   279: goto            612
        //   282: iload_2        
        //   283: iconst_0       
        //   284: invokestatic    java/lang/Character.charCount:(I)I
        //   287: iadd           
        //   288: istore_2       
        //   289: iconst_0       
        //   290: if_icmpne       434
        //   293: iload           8
        //   295: iload           9
        //   297: if_icmpeq       434
        //   300: aload_1        
        //   301: iload           9
        //   303: iconst_1       
        //   304: isub           
        //   305: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   310: istore          13
        //   312: sipush          -4352
        //   315: i2c            
        //   316: istore          13
        //   318: iload           5
        //   320: ifne            325
        //   323: iconst_0       
        //   324: ireturn        
        //   325: ldc_w           -80540
        //   328: i2c            
        //   329: istore          15
        //   331: iload_2        
        //   332: iload_3        
        //   333: if_icmpeq       380
        //   336: aload_1        
        //   337: iload_2        
        //   338: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   343: sipush          4519
        //   346: isub           
        //   347: i2c            
        //   348: dup            
        //   349: istore          16
        //   351: bipush          28
        //   353: if_icmpge       380
        //   356: iinc            2, 1
        //   359: iload           15
        //   361: iload           16
        //   363: iadd           
        //   364: i2c            
        //   365: istore          15
        //   367: iload_2        
        //   368: istore          8
        //   370: aload           6
        //   372: iload           15
        //   374: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.setLastChar:(C)V
        //   377: goto            9
        //   380: goto            412
        //   383: iconst_0       
        //   384: invokestatic    com/ibm/icu/impl/Normalizer2Impl$Hangul.isHangulWithoutJamoT:(C)Z
        //   387: ifeq            412
        //   390: iload           5
        //   392: ifne            397
        //   395: iconst_0       
        //   396: ireturn        
        //   397: aload           6
        //   399: sipush          -4519
        //   402: i2c            
        //   403: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.setLastChar:(C)V
        //   406: iload_2        
        //   407: istore          8
        //   409: goto            9
        //   412: goto            434
        //   415: iload           5
        //   417: ifeq            431
        //   420: aload           6
        //   422: iconst_0       
        //   423: i2c            
        //   424: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.append:(C)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   427: pop            
        //   428: goto            9
        //   431: goto            9
        //   434: goto            509
        //   437: iload           4
        //   439: ifeq            486
        //   442: iload           5
        //   444: ifeq            455
        //   447: aload           6
        //   449: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.getLastCC:()I
        //   452: goto            456
        //   455: iconst_0       
        //   456: ifne            486
        //   459: iload           8
        //   461: iload           9
        //   463: if_icmpge       486
        //   466: aload_0        
        //   467: aload_1        
        //   468: iload           8
        //   470: iload           9
        //   472: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getTrailCCFromCompYesAndZeroCC:(Ljava/lang/CharSequence;II)I
        //   475: iconst_0       
        //   476: if_icmple       486
        //   479: iload           5
        //   481: ifne            506
        //   484: iconst_0       
        //   485: ireturn        
        //   486: iload           5
        //   488: ifeq            501
        //   491: aload           6
        //   493: iconst_0       
        //   494: iconst_0       
        //   495: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.append:(II)V
        //   498: goto            9
        //   501: goto            9
        //   504: iconst_0       
        //   505: ireturn        
        //   506: goto            521
        //   509: iload           5
        //   511: ifne            521
        //   514: aload_0        
        //   515: iconst_0       
        //   516: if_icmplt       521
        //   519: iconst_0       
        //   520: ireturn        
        //   521: aload_0        
        //   522: iconst_0       
        //   523: goto            533
        //   526: iload           9
        //   528: istore          8
        //   530: goto            548
        //   533: iload           5
        //   535: ifeq            548
        //   538: aload           6
        //   540: iload           9
        //   542: iload           8
        //   544: isub           
        //   545: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.removeSuffix:(I)V
        //   548: aload_0        
        //   549: aload_1        
        //   550: iload_2        
        //   551: iload_3        
        //   552: invokespecial   com/ibm/icu/impl/Normalizer2Impl.findNextCompBoundary:(Ljava/lang/CharSequence;II)I
        //   555: istore_2       
        //   556: aload           6
        //   558: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.length:()I
        //   561: istore          13
        //   563: aload_0        
        //   564: aload_1        
        //   565: iload           8
        //   567: iload_2        
        //   568: aload           6
        //   570: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.decomposeShort:(Ljava/lang/CharSequence;IILcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;)V
        //   573: aload_0        
        //   574: aload           6
        //   576: iconst_0       
        //   577: iload           4
        //   579: invokespecial   com/ibm/icu/impl/Normalizer2Impl.recompose:(Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;IZ)V
        //   582: iload           5
        //   584: ifne            606
        //   587: aload           6
        //   589: aload_1        
        //   590: iload           8
        //   592: iload_2        
        //   593: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.equals:(Ljava/lang/CharSequence;II)Z
        //   596: ifne            601
        //   599: iconst_0       
        //   600: ireturn        
        //   601: aload           6
        //   603: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.remove:()V
        //   606: iload_2        
        //   607: istore          8
        //   609: goto            9
        //   612: iconst_1       
        //   613: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int composeQuickCheck(final CharSequence charSequence, int i, final int n, final boolean b, final boolean b2) {
        final int minCompNoMaybeCP = this.minCompNoMaybeCP;
        int n2 = i;
    Label_0307:
        while (true) {
            int n3 = i;
            while (i != n) {
                final int fromU16SingleLead;
                if (charSequence.charAt(i) < minCompNoMaybeCP || this < (fromU16SingleLead = this.normTrie.getFromU16SingleLead((char)0))) {
                    ++i;
                }
                else {
                    if (UTF16.isSurrogate((char)0)) {
                        if (UTF16Plus.isSurrogateLead(0)) {
                            final char char1;
                            if (i + 1 != n && Character.isLowSurrogate(char1 = charSequence.charAt(i + 1))) {
                                Character.toCodePoint((char)0, char1);
                            }
                        }
                        else {
                            final char char2;
                            if (n3 < i && Character.isHighSurrogate(char2 = charSequence.charAt(i - 1))) {
                                --i;
                                Character.toCodePoint(char2, (char)0);
                            }
                        }
                        final int norm16;
                        if (this < (norm16 = this.getNorm16(0))) {
                            i += Character.charCount(0);
                            continue;
                        }
                    }
                    if (i != n3) {
                        n2 = i - 1;
                        if (Character.isLowSurrogate(charSequence.charAt(n2)) && n3 < n2 && Character.isHighSurrogate(charSequence.charAt(n2 - 1))) {
                            --n2;
                        }
                        n3 = i;
                    }
                    i += Character.charCount(0);
                    if (this < 0) {
                        break Label_0307;
                    }
                    final int ccFromYesOrMaybe = getCCFromYesOrMaybe(0);
                    if (b && ccFromYesOrMaybe != 0 && n2 < n3 && this.getTrailCCFromCompYesAndZeroCC(charSequence, n2, n3) > ccFromYesOrMaybe) {
                        break Label_0307;
                    }
                    if (0 > ccFromYesOrMaybe && ccFromYesOrMaybe != 0) {
                        break Label_0307;
                    }
                    if (!b2) {
                        continue Label_0307;
                    }
                    return n2 << 1;
                }
            }
            return i << 1 | 0x1;
        }
        return n2 << 1;
    }
    
    public void composeAndAppend(final CharSequence charSequence, final boolean b, final boolean b2, final ReorderingBuffer reorderingBuffer) {
        final int length = charSequence.length();
        if (!reorderingBuffer.isEmpty()) {
            final int nextCompBoundary = this.findNextCompBoundary(charSequence, 0, length);
            if (nextCompBoundary != 0) {
                final int previousCompBoundary = this.findPreviousCompBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length());
                final StringBuilder sb = new StringBuilder(reorderingBuffer.length() - previousCompBoundary + nextCompBoundary + 16);
                sb.append(reorderingBuffer.getStringBuilder(), previousCompBoundary, reorderingBuffer.length());
                reorderingBuffer.removeSuffix(reorderingBuffer.length() - previousCompBoundary);
                sb.append(charSequence, 0, nextCompBoundary);
                this.compose(sb, 0, sb.length(), b2, true, reorderingBuffer);
            }
        }
        if (b) {
            this.compose(charSequence, 0, length, b2, true, reorderingBuffer);
        }
        else {
            reorderingBuffer.append(charSequence, 0, length);
        }
    }
    
    public int makeFCD(final CharSequence p0, final int p1, final int p2, final ReorderingBuffer p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          5
        //     3: iload_2        
        //     4: istore          6
        //     6: iload_2        
        //     7: iload_3        
        //     8: if_icmpeq       150
        //    11: aload_1        
        //    12: iload_2        
        //    13: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    18: dup            
        //    19: istore          7
        //    21: sipush          768
        //    24: if_icmpge       33
        //    27: iinc            2, 1
        //    30: goto            6
        //    33: aload_0        
        //    34: iinc            2, 1
        //    37: goto            6
        //    40: iconst_0       
        //    41: i2c            
        //    42: invokestatic    com/ibm/icu/text/UTF16.isSurrogate:(C)Z
        //    45: ifeq            126
        //    48: iconst_0       
        //    49: invokestatic    com/ibm/icu/impl/Normalizer2Impl$UTF16Plus.isSurrogateLead:(I)Z
        //    52: ifeq            91
        //    55: iload_2        
        //    56: iconst_1       
        //    57: iadd           
        //    58: iload_3        
        //    59: if_icmpeq       126
        //    62: aload_1        
        //    63: iload_2        
        //    64: iconst_1       
        //    65: iadd           
        //    66: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    71: dup            
        //    72: istore          10
        //    74: invokestatic    java/lang/Character.isLowSurrogate:(C)Z
        //    77: ifeq            126
        //    80: iconst_0       
        //    81: i2c            
        //    82: iconst_m1      
        //    83: invokestatic    java/lang/Character.toCodePoint:(CC)I
        //    86: istore          7
        //    88: goto            126
        //    91: iload           6
        //    93: iload_2        
        //    94: if_icmpge       126
        //    97: aload_1        
        //    98: iload_2        
        //    99: iconst_1       
        //   100: isub           
        //   101: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   106: dup            
        //   107: istore          10
        //   109: invokestatic    java/lang/Character.isHighSurrogate:(C)Z
        //   112: ifeq            126
        //   115: iinc            2, -1
        //   118: iconst_m1      
        //   119: iconst_0       
        //   120: i2c            
        //   121: invokestatic    java/lang/Character.toCodePoint:(CC)I
        //   124: istore          7
        //   126: aload_0        
        //   127: iconst_0       
        //   128: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getFCD16FromNormData:(I)I
        //   131: dup            
        //   132: istore          9
        //   134: sipush          255
        //   137: if_icmpgt       150
        //   140: iload_2        
        //   141: iconst_0       
        //   142: invokestatic    java/lang/Character.charCount:(I)I
        //   145: iadd           
        //   146: istore_2       
        //   147: goto            6
        //   150: iload_2        
        //   151: iload           6
        //   153: if_icmpeq       307
        //   156: iload_2        
        //   157: iload_3        
        //   158: if_icmpne       179
        //   161: aload           4
        //   163: ifnull          378
        //   166: aload           4
        //   168: aload_1        
        //   169: iload           6
        //   171: iload_2        
        //   172: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.flushAndAppendZeroCC:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   175: pop            
        //   176: goto            378
        //   179: iload_2        
        //   180: istore          5
        //   182: goto            210
        //   185: aload_0        
        //   186: getfield        com/ibm/icu/impl/Normalizer2Impl.tccc180:[I
        //   189: iconst_m1      
        //   190: iaload         
        //   191: goto            199
        //   194: aload_0        
        //   195: iconst_m1      
        //   196: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getFCD16FromNormData:(I)I
        //   199: istore          8
        //   201: goto            207
        //   204: iinc            5, -1
        //   207: goto            277
        //   210: iload_2        
        //   211: iconst_1       
        //   212: isub           
        //   213: istore          10
        //   215: aload_1        
        //   216: iconst_m1      
        //   217: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   222: invokestatic    java/lang/Character.isLowSurrogate:(C)Z
        //   225: ifeq            274
        //   228: iload           6
        //   230: iconst_m1      
        //   231: if_icmpge       274
        //   234: aload_1        
        //   235: bipush          -2
        //   237: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   242: invokestatic    java/lang/Character.isHighSurrogate:(C)Z
        //   245: ifeq            274
        //   248: iinc            10, -1
        //   251: aload_0        
        //   252: aload_1        
        //   253: iconst_m1      
        //   254: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   259: aload_1        
        //   260: iconst_0       
        //   261: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   266: invokestatic    java/lang/Character.toCodePoint:(CC)I
        //   269: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getFCD16FromNormData:(I)I
        //   272: istore          8
        //   274: goto            277
        //   277: aload           4
        //   279: ifnull          301
        //   282: aload           4
        //   284: aload_1        
        //   285: iload           6
        //   287: iconst_m1      
        //   288: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.flushAndAppendZeroCC:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   291: pop            
        //   292: aload           4
        //   294: aload_1        
        //   295: iconst_m1      
        //   296: iload_2        
        //   297: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.append:(Ljava/lang/CharSequence;II)Lcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;
        //   300: pop            
        //   301: iload_2        
        //   302: istore          6
        //   304: goto            315
        //   307: iload_2        
        //   308: iload_3        
        //   309: if_icmpne       315
        //   312: goto            378
        //   315: iload_2        
        //   316: iconst_0       
        //   317: invokestatic    java/lang/Character.charCount:(I)I
        //   320: iadd           
        //   321: istore_2       
        //   322: iload_2        
        //   323: istore          5
        //   325: aload           4
        //   327: ifnull          336
        //   330: aload           4
        //   332: iconst_0       
        //   333: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.appendZeroCC:(I)V
        //   336: goto            3
        //   339: aload           4
        //   341: ifnonnull       346
        //   344: iconst_m1      
        //   345: ireturn        
        //   346: aload           4
        //   348: iload           6
        //   350: iconst_m1      
        //   351: isub           
        //   352: invokevirtual   com/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer.removeSuffix:(I)V
        //   355: aload_0        
        //   356: aload_1        
        //   357: iload_2        
        //   358: iload_3        
        //   359: invokespecial   com/ibm/icu/impl/Normalizer2Impl.findNextFCDBoundary:(Ljava/lang/CharSequence;II)I
        //   362: istore_2       
        //   363: aload_0        
        //   364: aload_1        
        //   365: iconst_m1      
        //   366: iload_2        
        //   367: aload           4
        //   369: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.decomposeShort:(Ljava/lang/CharSequence;IILcom/ibm/icu/impl/Normalizer2Impl$ReorderingBuffer;)V
        //   372: iload_2        
        //   373: istore          5
        //   375: goto            3
        //   378: iload_2        
        //   379: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0006 (coming from #0037).
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
    
    public void makeFCDAndAppend(final CharSequence charSequence, final boolean b, final ReorderingBuffer reorderingBuffer) {
        final int length = charSequence.length();
        if (!reorderingBuffer.isEmpty()) {
            final int nextFCDBoundary = this.findNextFCDBoundary(charSequence, 0, length);
            if (nextFCDBoundary != 0) {
                final int previousFCDBoundary = this.findPreviousFCDBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length());
                final StringBuilder sb = new StringBuilder(reorderingBuffer.length() - previousFCDBoundary + nextFCDBoundary + 16);
                sb.append(reorderingBuffer.getStringBuilder(), previousFCDBoundary, reorderingBuffer.length());
                reorderingBuffer.removeSuffix(reorderingBuffer.length() - previousFCDBoundary);
                sb.append(charSequence, 0, nextFCDBoundary);
                this.makeFCD(sb, 0, sb.length(), reorderingBuffer);
            }
        }
        if (b) {
            this.makeFCD(charSequence, 0, length, reorderingBuffer);
        }
        else {
            reorderingBuffer.append(charSequence, 0, length);
        }
    }
    
    public boolean hasDecompBoundary(int i, final boolean b) {
        while (i >= this.minDecompNoCP) {
            final int norm16 = this.getNorm16(i);
            if (this != norm16 || this >= norm16) {
                return true;
            }
            if (norm16 > 65024) {
                return false;
            }
            if (this >= norm16) {
                i = this.mapAlgorithmic(i, norm16);
            }
            else {
                final char char1 = this.extraData.charAt(norm16);
                if ((char1 & '\u001f') == 0x0) {
                    return false;
                }
                if (!b) {
                    if (char1 > '\u01ff') {
                        return false;
                    }
                    if (char1 <= '\u00ff') {
                        return true;
                    }
                }
                return (char1 & '\u0080') == 0x0 || (this.extraData.charAt(norm16 - 1) & '\uff00') == 0x0;
            }
        }
        return true;
    }
    
    public boolean isDecompInert(final int n) {
        return this.isDecompYesAndZeroCC(this.getNorm16(n));
    }
    
    public boolean hasCompBoundaryAfter(int mapAlgorithmic, final boolean b, final boolean b2) {
        while (true) {
            final int norm16 = this.getNorm16(mapAlgorithmic);
            if (norm16 == 0) {
                return true;
            }
            if (norm16 <= this.minYesNo) {
                return this == norm16 && !Hangul.isHangulWithoutJamoT((char)mapAlgorithmic);
            }
            if (norm16 >= (b2 ? this.minNoNo : this.minMaybeYes)) {
                return false;
            }
            if (this < norm16) {
                final char char1 = this.extraData.charAt(norm16);
                return (char1 & ' ') == 0x0 && (!b || char1 <= '\u01ff');
            }
            mapAlgorithmic = this.mapAlgorithmic(mapAlgorithmic, norm16);
        }
    }
    
    public boolean hasFCDBoundaryBefore(final int n) {
        return n < 768 || this.getFCD16(n) <= 255;
    }
    
    public boolean hasFCDBoundaryAfter(final int n) {
        final int fcd16 = this.getFCD16(n);
        return fcd16 <= 1 || (fcd16 & 0xFF) == 0x0;
    }
    
    public boolean isFCDInert(final int n) {
        return this.getFCD16(n) <= 1;
    }
    
    private int getCCFromNoNo(final int n) {
        if ((this.extraData.charAt(n) & '\u0080') != 0x0) {
            return this.extraData.charAt(n - 1) & '\u00ff';
        }
        return 0;
    }
    
    int getTrailCCFromCompYesAndZeroCC(final CharSequence charSequence, final int n, final int n2) {
        int n3;
        if (n == n2 - 1) {
            n3 = charSequence.charAt(n);
        }
        else {
            n3 = Character.codePointAt(charSequence, n);
        }
        final int norm16 = this.getNorm16(n3);
        if (norm16 <= this.minYesNo) {
            return 0;
        }
        return this.extraData.charAt(norm16) >> 8;
    }
    
    private int mapAlgorithmic(final int n, final int n2) {
        return n + n2 - (this.minMaybeYes - 64 - 1);
    }
    
    private int getCompositionsListForDecompYes(int n) {
        if (n == 0 || 65024 <= n) {
            return -1;
        }
        if ((n -= this.minMaybeYes) < 0) {
            n += 65024;
        }
        return n;
    }
    
    private int getCompositionsListForComposite(final int n) {
        return 65024 - this.minMaybeYes + n + 1 + (this.extraData.charAt(n) & '\u001f');
    }
    
    private int getCompositionsList(final int n) {
        return (this >= n) ? this.getCompositionsListForDecompYes(n) : this.getCompositionsListForComposite(n);
    }
    
    public void decomposeShort(final CharSequence charSequence, int i, final int n, final ReorderingBuffer reorderingBuffer) {
        while (i < n) {
            final int codePoint = Character.codePointAt(charSequence, i);
            i += Character.charCount(codePoint);
            this.decompose(codePoint, this.getNorm16(codePoint), reorderingBuffer);
        }
    }
    
    private void decompose(int mapAlgorithmic, int norm16, final ReorderingBuffer reorderingBuffer) {
        while (this < norm16) {
            if (this == norm16) {
                Hangul.decompose(mapAlgorithmic, reorderingBuffer);
            }
            else {
                if (this >= norm16) {
                    mapAlgorithmic = this.mapAlgorithmic(mapAlgorithmic, norm16);
                    norm16 = this.getNorm16(mapAlgorithmic);
                    continue;
                }
                final char char1 = this.extraData.charAt(norm16);
                final int n = char1 & '\u001f';
                final int n2 = char1 >> 8;
                if ((char1 & '\u0080') != 0x0) {
                    final int n3 = this.extraData.charAt(norm16 - 1) >> 8;
                }
                ++norm16;
                reorderingBuffer.append(this.extraData, norm16, norm16 + n, 0, n2);
            }
            return;
        }
        reorderingBuffer.append(mapAlgorithmic, getCCFromYesOrMaybe(norm16));
    }
    
    private static int combine(final String s, int n, final int n2) {
        if (n2 < 13312) {
            int i;
            char char1;
            for (i = n2 << 1; i > (char1 = s.charAt(n)); n += 2 + (char1 & '\u0001')) {}
            if (i == (char1 & '\u7ffe')) {
                if ((char1 & '\u0001') != 0x0) {
                    return s.charAt(n + 1) << 16 | s.charAt(n + 2);
                }
                return s.charAt(n + 1);
            }
        }
        else {
            final int n3 = 13312 + (n2 >> 9 & 0xFFFFFFFE);
            final int n4 = n2 << 6 & 0xFFFF;
            while (true) {
                final char char2;
                if (n3 > (char2 = s.charAt(n))) {
                    n += 2 + (char2 & '\u0001');
                }
                else {
                    if (n3 != (char2 & '\u7ffe')) {
                        break;
                    }
                    final char char3;
                    if (n4 > (char3 = s.charAt(n + 1))) {
                        if ((char2 & '\u8000') != 0x0) {
                            break;
                        }
                        n += 3;
                    }
                    else {
                        if (n4 == (char3 & '\uffc0')) {
                            return (char3 & 0xFFFF003F) << 16 | s.charAt(n + 2);
                        }
                        break;
                    }
                }
            }
        }
        return -1;
    }
    
    private void addComposites(int n, final UnicodeSet set) {
        char char1;
        do {
            char1 = this.maybeYesCompositions.charAt(n);
            int char2;
            if ((char1 & '\u0001') == 0x0) {
                char2 = this.maybeYesCompositions.charAt(n + 1);
                n += 2;
            }
            else {
                char2 = ((this.maybeYesCompositions.charAt(n + 1) & 0xFFFF003F) << 16 | this.maybeYesCompositions.charAt(n + 2));
                n += 3;
            }
            final int n2 = char2 >> 1;
            if ((char2 & 0x1) != 0x0) {
                this.addComposites(this.getCompositionsListForComposite(this.getNorm16(n2)), set);
            }
            set.add(n2);
        } while ((char1 & '\u8000') == 0x0);
    }
    
    private void recompose(final ReorderingBuffer reorderingBuffer, final int n, final boolean b) {
        final StringBuilder stringBuilder = reorderingBuffer.getStringBuilder();
        int n2 = n;
        if (n2 == stringBuilder.length()) {
            return;
        }
        while (true) {
            final int codePoint = stringBuilder.codePointAt(n2);
            n2 += Character.charCount(codePoint);
            final int norm16 = this.getNorm16(codePoint);
            final int ccFromYesOrMaybe = getCCFromYesOrMaybe(norm16);
            if (this <= norm16) {}
            if (n2 == stringBuilder.length()) {
                break;
            }
            if (ccFromYesOrMaybe == 0) {
                if (this.getCompositionsListForDecompYes(norm16) < 0) {
                    continue;
                }
                if (codePoint <= 65535) {
                    continue;
                }
                continue;
            }
            else {
                if (b) {
                    continue;
                }
                continue;
            }
        }
        reorderingBuffer.flush();
    }
    
    public int composePair(final int p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_1        
        //     2: invokevirtual   com/ibm/icu/impl/Normalizer2Impl.getNorm16:(I)I
        //     5: istore_3       
        //     6: iload_3        
        //     7: ifne            12
        //    10: iconst_m1      
        //    11: ireturn        
        //    12: iload_3        
        //    13: aload_0        
        //    14: getfield        com/ibm/icu/impl/Normalizer2Impl.minYesNoMappingsOnly:I
        //    17: if_icmpge       142
        //    20: iload_3        
        //    21: if_icmpne       61
        //    24: iinc_w          2, -4449
        //    30: iconst_0       
        //    31: iload_2        
        //    32: if_icmpgt       59
        //    35: iload_2        
        //    36: bipush          21
        //    38: if_icmpge       59
        //    41: ldc_w           44032
        //    44: iload_1        
        //    45: sipush          4352
        //    48: isub           
        //    49: bipush          21
        //    51: imul           
        //    52: iload_2        
        //    53: iadd           
        //    54: bipush          28
        //    56: imul           
        //    57: iadd           
        //    58: ireturn        
        //    59: iconst_m1      
        //    60: ireturn        
        //    61: aload_0        
        //    62: iload_3        
        //    63: if_icmpne       97
        //    66: iinc_w          2, -4519
        //    72: iload_1        
        //    73: i2c            
        //    74: invokestatic    com/ibm/icu/impl/Normalizer2Impl$Hangul.isHangulWithoutJamoT:(C)Z
        //    77: ifeq            95
        //    80: iconst_0       
        //    81: iload_2        
        //    82: if_icmpge       95
        //    85: iload_2        
        //    86: bipush          28
        //    88: if_icmpge       95
        //    91: iload_1        
        //    92: iload_2        
        //    93: iadd           
        //    94: ireturn        
        //    95: iconst_m1      
        //    96: ireturn        
        //    97: iload_3        
        //    98: istore          4
        //   100: iload_3        
        //   101: aload_0        
        //   102: getfield        com/ibm/icu/impl/Normalizer2Impl.minYesNo:I
        //   105: if_icmple       127
        //   108: iload           4
        //   110: iconst_1       
        //   111: aload_0        
        //   112: getfield        com/ibm/icu/impl/Normalizer2Impl.extraData:Ljava/lang/String;
        //   115: iload           4
        //   117: invokevirtual   java/lang/String.charAt:(I)C
        //   120: bipush          31
        //   122: iand           
        //   123: iadd           
        //   124: iadd           
        //   125: istore          4
        //   127: iload           4
        //   129: ldc             65024
        //   131: aload_0        
        //   132: getfield        com/ibm/icu/impl/Normalizer2Impl.minMaybeYes:I
        //   135: isub           
        //   136: iadd           
        //   137: istore          4
        //   139: goto            166
        //   142: iload_3        
        //   143: aload_0        
        //   144: getfield        com/ibm/icu/impl/Normalizer2Impl.minMaybeYes:I
        //   147: if_icmplt       156
        //   150: ldc             65024
        //   152: iload_3        
        //   153: if_icmpgt       158
        //   156: iconst_m1      
        //   157: ireturn        
        //   158: iload_3        
        //   159: aload_0        
        //   160: getfield        com/ibm/icu/impl/Normalizer2Impl.minMaybeYes:I
        //   163: isub           
        //   164: istore          4
        //   166: iload_2        
        //   167: iflt            177
        //   170: ldc_w           1114111
        //   173: iload_2        
        //   174: if_icmpge       179
        //   177: iconst_m1      
        //   178: ireturn        
        //   179: aload_0        
        //   180: getfield        com/ibm/icu/impl/Normalizer2Impl.maybeYesCompositions:Ljava/lang/String;
        //   183: iload           4
        //   185: iload_2        
        //   186: invokestatic    com/ibm/icu/impl/Normalizer2Impl.combine:(Ljava/lang/String;II)I
        //   189: iconst_1       
        //   190: ishr           
        //   191: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int findPreviousCompBoundary(final CharSequence charSequence, int i) {
        while (i > 0) {
            final int codePointBefore = Character.codePointBefore(charSequence, i);
            i -= Character.charCount(codePointBefore);
            if (this >= codePointBefore) {
                break;
            }
        }
        return i;
    }
    
    private int findNextCompBoundary(final CharSequence p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_3        
        //     2: if_icmpge       45
        //     5: aload_1        
        //     6: iload_2        
        //     7: invokestatic    java/lang/Character.codePointAt:(Ljava/lang/CharSequence;I)I
        //    10: istore          4
        //    12: aload_0        
        //    13: getfield        com/ibm/icu/impl/Normalizer2Impl.normTrie:Lcom/ibm/icu/impl/Trie2_16;
        //    16: iload           4
        //    18: invokevirtual   com/ibm/icu/impl/Trie2_16.get:(I)I
        //    21: istore          5
        //    23: aload_0        
        //    24: iload           4
        //    26: iload           5
        //    28: if_icmpge       34
        //    31: goto            45
        //    34: iload_2        
        //    35: iload           4
        //    37: invokestatic    java/lang/Character.charCount:(I)I
        //    40: iadd           
        //    41: istore_2       
        //    42: goto            0
        //    45: iload_2        
        //    46: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0000 (coming from #0042).
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
    
    private int findPreviousFCDBoundary(final CharSequence charSequence, int i) {
        while (i > 0) {
            final int codePointBefore = Character.codePointBefore(charSequence, i);
            i -= Character.charCount(codePointBefore);
            if (codePointBefore < 768) {
                break;
            }
            if (this.getFCD16(codePointBefore) <= 255) {
                break;
            }
        }
        return i;
    }
    
    private int findNextFCDBoundary(final CharSequence charSequence, int i, final int n) {
        while (i < n) {
            final int codePoint = Character.codePointAt(charSequence, i);
            if (codePoint < 768) {
                break;
            }
            if (this.getFCD16(codePoint) <= 255) {
                break;
            }
            i += Character.charCount(codePoint);
        }
        return i;
    }
    
    private void addToStartSet(final Trie2Writable trie2Writable, final int n, final int n2) {
        final int value = trie2Writable.get(n2);
        if ((value & 0x3FFFFF) == 0x0 && n != 0) {
            trie2Writable.set(n2, value | n);
        }
        else {
            UnicodeSet set;
            if ((value & 0x200000) == 0x0) {
                final int n3 = value & 0x1FFFFF;
                trie2Writable.set(n2, (value & 0xFFE00000) | 0x200000 | this.canonStartSets.size());
                this.canonStartSets.add(set = new UnicodeSet());
                if (n3 != 0) {
                    set.add(n3);
                }
            }
            else {
                set = this.canonStartSets.get(value & 0x1FFFFF);
            }
            set.add(n);
        }
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable(null);
        DATA_FORMAT = new byte[] { 78, 114, 109, 50 };
        segmentStarterMapper = new Trie2.ValueMapper() {
            public int map(final int n) {
                return n & Integer.MIN_VALUE;
            }
        };
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        private IsAcceptable() {
        }
        
        public boolean isDataVersionAcceptable(final byte[] array) {
            return array[0] == 2;
        }
        
        IsAcceptable(final Normalizer2Impl$1 valueMapper) {
            this();
        }
    }
    
    public static final class UTF16Plus
    {
        public static boolean isSurrogateLead(final int n) {
            return (n & 0x400) == 0x0;
        }
        
        public static boolean equal(final CharSequence charSequence, final CharSequence charSequence2) {
            if (charSequence == charSequence2) {
                return true;
            }
            final int length = charSequence.length();
            if (length != charSequence2.length()) {
                return false;
            }
            while (0 < length) {
                if (charSequence.charAt(0) != charSequence2.charAt(0)) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        public static boolean equal(final CharSequence charSequence, int i, final int n, final CharSequence charSequence2, int n2, final int n3) {
            if (n - i != n3 - n2) {
                return false;
            }
            if (charSequence == charSequence2 && i == n2) {
                return true;
            }
            while (i < n) {
                if (charSequence.charAt(i++) != charSequence2.charAt(n2++)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public static final class ReorderingBuffer implements Appendable
    {
        private final Normalizer2Impl impl;
        private final Appendable app;
        private final StringBuilder str;
        private final boolean appIsStringBuilder;
        private int reorderStart;
        private int lastCC;
        private int codePointStart;
        private int codePointLimit;
        
        public ReorderingBuffer(final Normalizer2Impl impl, final Appendable app, final int n) {
            this.impl = impl;
            this.app = app;
            if (this.app instanceof StringBuilder) {
                this.appIsStringBuilder = true;
                (this.str = (StringBuilder)app).ensureCapacity(n);
                this.reorderStart = 0;
                if (this.str.length() == 0) {
                    this.lastCC = 0;
                }
                else {
                    this.setIterator();
                    this.lastCC = this.previousCC();
                    if (this.lastCC > 1) {
                        while (this.previousCC() > 1) {}
                    }
                    this.reorderStart = this.codePointLimit;
                }
            }
            else {
                this.appIsStringBuilder = false;
                this.str = new StringBuilder();
                this.reorderStart = 0;
                this.lastCC = 0;
            }
        }
        
        public boolean isEmpty() {
            return this.str.length() == 0;
        }
        
        public int length() {
            return this.str.length();
        }
        
        public int getLastCC() {
            return this.lastCC;
        }
        
        public StringBuilder getStringBuilder() {
            return this.str;
        }
        
        public boolean equals(final CharSequence charSequence, final int n, final int n2) {
            return UTF16Plus.equal(this.str, 0, this.str.length(), charSequence, n, n2);
        }
        
        public void setLastChar(final char c) {
            this.str.setCharAt(this.str.length() - 1, c);
        }
        
        public void append(final int n, final int lastCC) {
            if (this.lastCC <= lastCC || lastCC == 0) {
                this.str.appendCodePoint(n);
                if ((this.lastCC = lastCC) <= 1) {
                    this.reorderStart = this.str.length();
                }
            }
            else {
                this.insert(n, lastCC);
            }
        }
        
        public void append(final CharSequence charSequence, int i, final int n, int ccFromYesOrMaybe, final int lastCC) {
            if (i == n) {
                return;
            }
            if (this.lastCC <= ccFromYesOrMaybe || ccFromYesOrMaybe == 0) {
                if (lastCC <= 1) {
                    this.reorderStart = this.str.length() + (n - i);
                }
                else if (ccFromYesOrMaybe <= 1) {
                    this.reorderStart = this.str.length() + 1;
                }
                this.str.append(charSequence, i, n);
                this.lastCC = lastCC;
            }
            else {
                final int codePoint = Character.codePointAt(charSequence, i);
                i += Character.charCount(codePoint);
                this.insert(codePoint, ccFromYesOrMaybe);
                while (i < n) {
                    final int codePoint2 = Character.codePointAt(charSequence, i);
                    i += Character.charCount(codePoint2);
                    if (i < n) {
                        ccFromYesOrMaybe = Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(codePoint2));
                    }
                    else {
                        ccFromYesOrMaybe = lastCC;
                    }
                    this.append(codePoint2, ccFromYesOrMaybe);
                }
            }
        }
        
        public ReorderingBuffer append(final char c) {
            this.str.append(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
            return this;
        }
        
        public void appendZeroCC(final int n) {
            this.str.appendCodePoint(n);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }
        
        public ReorderingBuffer append(final CharSequence charSequence) {
            if (charSequence.length() != 0) {
                this.str.append(charSequence);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }
        
        public ReorderingBuffer append(final CharSequence charSequence, final int n, final int n2) {
            if (n != n2) {
                this.str.append(charSequence, n, n2);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }
        
        public void flush() {
            if (this.appIsStringBuilder) {
                this.reorderStart = this.str.length();
            }
            else {
                this.app.append(this.str);
                this.str.setLength(0);
                this.reorderStart = 0;
            }
            this.lastCC = 0;
        }
        
        public ReorderingBuffer flushAndAppendZeroCC(final CharSequence charSequence, final int n, final int n2) {
            if (this.appIsStringBuilder) {
                this.str.append(charSequence, n, n2);
                this.reorderStart = this.str.length();
            }
            else {
                this.app.append(this.str).append(charSequence, n, n2);
                this.str.setLength(0);
                this.reorderStart = 0;
            }
            this.lastCC = 0;
            return this;
        }
        
        public void remove() {
            this.str.setLength(0);
            this.lastCC = 0;
            this.reorderStart = 0;
        }
        
        public void removeSuffix(final int n) {
            final int length = this.str.length();
            this.str.delete(length - n, length);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }
        
        private void insert(final int n, final int n2) {
            this.setIterator();
            this.skipPrevious();
            while (this.previousCC() > n2) {}
            if (n <= 65535) {
                this.str.insert(this.codePointLimit, (char)n);
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 1;
                }
            }
            else {
                this.str.insert(this.codePointLimit, Character.toChars(n));
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 2;
                }
            }
        }
        
        private void setIterator() {
            this.codePointStart = this.str.length();
        }
        
        private void skipPrevious() {
            this.codePointLimit = this.codePointStart;
            this.codePointStart = this.str.offsetByCodePoints(this.codePointStart, -1);
        }
        
        private int previousCC() {
            this.codePointLimit = this.codePointStart;
            if (this.reorderStart >= this.codePointStart) {
                return 0;
            }
            final int codePointBefore = this.str.codePointBefore(this.codePointStart);
            this.codePointStart -= Character.charCount(codePointBefore);
            if (codePointBefore < 768) {
                return 0;
            }
            return Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(codePointBefore));
        }
        
        public Appendable append(final char c) throws IOException {
            return this.append(c);
        }
        
        public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
            return this.append(charSequence, n, n2);
        }
        
        public Appendable append(final CharSequence charSequence) throws IOException {
            return this.append(charSequence);
        }
    }
    
    public static final class Hangul
    {
        public static final int JAMO_L_BASE = 4352;
        public static final int JAMO_V_BASE = 4449;
        public static final int JAMO_T_BASE = 4519;
        public static final int HANGUL_BASE = 44032;
        public static final int JAMO_L_COUNT = 19;
        public static final int JAMO_V_COUNT = 21;
        public static final int JAMO_T_COUNT = 28;
        public static final int JAMO_L_LIMIT = 4371;
        public static final int JAMO_V_LIMIT = 4470;
        public static final int JAMO_VT_COUNT = 588;
        public static final int HANGUL_COUNT = 11172;
        public static final int HANGUL_LIMIT = 55204;
        
        public static boolean isHangul(final int n) {
            return 44032 <= n && n < 55204;
        }
        
        public static boolean isHangulWithoutJamoT(final char c) {
            final char c2 = (char)(c - '\uac00');
            return c2 < '\u2ba4' && c2 % '\u001c' == 0;
        }
        
        public static boolean isJamoL(final int n) {
            return 4352 <= n && n < 4371;
        }
        
        public static boolean isJamoV(final int n) {
            return 4449 <= n && n < 4470;
        }
        
        public static int decompose(int n, final Appendable appendable) {
            n -= 44032;
            final int n2 = n % 28;
            n /= 28;
            appendable.append((char)(4352 + n / 21));
            appendable.append((char)(4449 + n % 21));
            if (n2 == 0) {
                return 2;
            }
            appendable.append((char)(4519 + n2));
            return 3;
        }
        
        public static void getRawDecomposition(int n, final Appendable appendable) {
            final int n2 = n;
            n -= 44032;
            final int n3 = n % 28;
            if (n3 == 0) {
                n /= 28;
                appendable.append((char)(4352 + n / 21));
                appendable.append((char)(4449 + n % 21));
            }
            else {
                appendable.append((char)(n2 - n3));
                appendable.append((char)(4519 + n3));
            }
        }
    }
}
