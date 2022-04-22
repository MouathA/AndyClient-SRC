package com.ibm.icu.text;

import java.util.*;

final class BidiLine
{
    static void setTrailingWSStart(final Bidi bidi) {
        final byte[] dirProps = bidi.dirProps;
        final byte[] levels = bidi.levels;
        int length = bidi.length;
        final byte paraLevel = bidi.paraLevel;
        if (Bidi.NoContextRTL(dirProps[length - 1]) == 7) {
            bidi.trailingWSStart = length;
            return;
        }
        while (length > 0 && (Bidi.DirPropFlagNC(dirProps[length - 1]) & Bidi.MASK_WS) != 0x0) {
            --length;
        }
        while (length > 0 && levels[length - 1] == paraLevel) {
            --length;
        }
        bidi.trailingWSStart = length;
    }
    
    static Bidi setLine(final Bidi paraBidi, final int n, final int n2) {
        final Bidi bidi3;
        final Bidi bidi2;
        final Bidi bidi;
        final Bidi trailingWSStart = bidi = (bidi2 = (bidi3 = new Bidi()));
        final int length = n2 - n;
        bidi.resultLength = length;
        bidi2.originalLength = length;
        bidi3.length = length;
        final int trailingWSStart2 = length;
        trailingWSStart.text = new char[trailingWSStart2];
        System.arraycopy(paraBidi.text, n, trailingWSStart.text, 0, trailingWSStart2);
        trailingWSStart.paraLevel = paraBidi.GetParaLevelAt(n);
        trailingWSStart.paraCount = paraBidi.paraCount;
        trailingWSStart.runs = new BidiRun[0];
        trailingWSStart.reorderingMode = paraBidi.reorderingMode;
        trailingWSStart.reorderingOptions = paraBidi.reorderingOptions;
        if (paraBidi.controlCount > 0) {
            for (int i = n; i < n2; ++i) {
                if (Bidi.IsBidiControlChar(paraBidi.text[i])) {
                    final Bidi bidi4 = trailingWSStart;
                    ++bidi4.controlCount;
                }
            }
            final Bidi bidi5 = trailingWSStart;
            bidi5.resultLength -= trailingWSStart.controlCount;
        }
        trailingWSStart.getDirPropsMemory(trailingWSStart2);
        trailingWSStart.dirProps = trailingWSStart.dirPropsMemory;
        System.arraycopy(paraBidi.dirProps, n, trailingWSStart.dirProps, 0, trailingWSStart2);
        trailingWSStart.getLevelsMemory(trailingWSStart2);
        trailingWSStart.levels = trailingWSStart.levelsMemory;
        System.arraycopy(paraBidi.levels, n, trailingWSStart.levels, 0, trailingWSStart2);
        trailingWSStart.runCount = -1;
        if (paraBidi.direction != 2) {
            trailingWSStart.direction = paraBidi.direction;
            if (paraBidi.trailingWSStart <= n) {
                trailingWSStart.trailingWSStart = 0;
            }
            else if (paraBidi.trailingWSStart < n2) {
                trailingWSStart.trailingWSStart = paraBidi.trailingWSStart - n;
            }
            else {
                trailingWSStart.trailingWSStart = trailingWSStart2;
            }
        }
        else {
            final byte[] levels = trailingWSStart.levels;
            setTrailingWSStart(trailingWSStart);
            final int trailingWSStart3 = trailingWSStart.trailingWSStart;
            Label_0412: {
                if (trailingWSStart3 == 0) {
                    trailingWSStart.direction = (byte)(trailingWSStart.paraLevel & 0x1);
                }
                else {
                    final byte direction = (byte)(levels[0] & 0x1);
                    if (trailingWSStart3 < trailingWSStart2 && (trailingWSStart.paraLevel & 0x1) != direction) {
                        trailingWSStart.direction = 2;
                    }
                    else {
                        while (trailingWSStart3 == 0) {
                            if ((levels[1] & 0x1) != direction) {
                                trailingWSStart.direction = 2;
                                break Label_0412;
                            }
                            int n3 = 0;
                            ++n3;
                        }
                        trailingWSStart.direction = direction;
                    }
                }
            }
            switch (trailingWSStart.direction) {
                case 0: {
                    trailingWSStart.paraLevel = (byte)(trailingWSStart.paraLevel + 1 & 0xFFFFFFFE);
                    trailingWSStart.trailingWSStart = 0;
                    break;
                }
                case 1: {
                    final Bidi bidi6 = trailingWSStart;
                    bidi6.paraLevel |= 0x1;
                    trailingWSStart.trailingWSStart = 0;
                    break;
                }
            }
        }
        trailingWSStart.paraBidi = paraBidi;
        return trailingWSStart;
    }
    
    static byte getLevelAt(final Bidi bidi, final int n) {
        if (bidi.direction != 2 || n >= bidi.trailingWSStart) {
            return bidi.GetParaLevelAt(n);
        }
        return bidi.levels[n];
    }
    
    static byte[] getLevels(final Bidi bidi) {
        final int trailingWSStart = bidi.trailingWSStart;
        final int length = bidi.length;
        if (trailingWSStart != length) {
            Arrays.fill(bidi.levels, trailingWSStart, length, bidi.paraLevel);
            bidi.trailingWSStart = length;
        }
        if (length < bidi.levels.length) {
            final byte[] array = new byte[length];
            System.arraycopy(bidi.levels, 0, array, 0, length);
            return array;
        }
        return bidi.levels;
    }
    
    static BidiRun getLogicalRun(final Bidi bidi, final int n) {
        final BidiRun bidiRun = new BidiRun();
        getRuns(bidi);
        final int runCount = bidi.runCount;
        BidiRun bidiRun2 = bidi.runs[0];
        while (0 < runCount) {
            bidiRun2 = bidi.runs[0];
            final int n2 = bidiRun2.start + bidiRun2.limit - 0;
            if (n >= bidiRun2.start && n < 0) {
                break;
            }
            final int limit = bidiRun2.limit;
            int n3 = 0;
            ++n3;
        }
        bidiRun.start = bidiRun2.start;
        bidiRun.limit = 0;
        bidiRun.level = bidiRun2.level;
        return bidiRun;
    }
    
    static BidiRun getVisualRun(final Bidi bidi, final int n) {
        final int start = bidi.runs[n].start;
        final byte level = bidi.runs[n].level;
        int n2;
        if (n > 0) {
            n2 = start + bidi.runs[n].limit - bidi.runs[n - 1].limit;
        }
        else {
            n2 = start + bidi.runs[0].limit;
        }
        return new BidiRun(start, n2, level);
    }
    
    static void getSingleRun(final Bidi bidi, final byte b) {
        bidi.runs = bidi.simpleRuns;
        bidi.runCount = 1;
        bidi.runs[0] = new BidiRun(0, bidi.length, b);
    }
    
    private static void reorderLine(final Bidi bidi, final byte b, byte b2) {
        if (b2 <= (b | 0x1)) {
            return;
        }
        final byte b3 = (byte)(b + 1);
        final BidiRun[] runs = bidi.runs;
        final byte[] levels = bidi.levels;
        int runCount = bidi.runCount;
        if (bidi.trailingWSStart < bidi.length) {
            --runCount;
        }
        int n = 0;
        while (true) {
            --b2;
            if (b2 < b3) {
                break;
            }
            while (true) {
                if (0 < runCount && levels[runs[0].start] < b2) {
                    ++n;
                }
                else {
                    if (0 >= runCount) {
                        break;
                    }
                    do {
                        int n2 = 0;
                        ++n2;
                    } while (0 < runCount && levels[runs[0].start] >= b2);
                    while (0 < -1) {
                        final BidiRun bidiRun = runs[0];
                        runs[0] = runs[-1];
                        runs[-1] = bidiRun;
                        ++n;
                        int n3 = 0;
                        --n3;
                    }
                    if (runCount == 0) {
                        break;
                    }
                    continue;
                }
            }
        }
        if ((b3 & 0x1) == 0x0) {
            if (bidi.trailingWSStart == bidi.length) {
                --runCount;
            }
            while (0 < runCount) {
                final BidiRun bidiRun2 = runs[0];
                runs[0] = runs[runCount];
                runs[runCount] = bidiRun2;
                ++n;
                --runCount;
            }
        }
    }
    
    static int getRunFromLogicalIndex(final Bidi bidi, final int n) {
        final BidiRun[] runs = bidi.runs;
        while (0 < bidi.runCount) {
            final int n2 = runs[0].limit - 0;
            final int start = runs[0].start;
            if (n >= start && n < start + n2) {
                return 0;
            }
            int n3 = 0;
            ++n3;
        }
        throw new IllegalStateException("Internal ICU error in getRunFromLogicalIndex");
    }
    
    static void getRuns(final Bidi p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/text/Bidi.runCount:I
        //     4: iflt            8
        //     7: return         
        //     8: aload_0        
        //     9: getfield        com/ibm/icu/text/Bidi.direction:B
        //    12: iconst_2       
        //    13: if_icmpeq       27
        //    16: aload_0        
        //    17: aload_0        
        //    18: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //    21: invokestatic    com/ibm/icu/text/BidiLine.getSingleRun:(Lcom/ibm/icu/text/Bidi;B)V
        //    24: goto            300
        //    27: aload_0        
        //    28: getfield        com/ibm/icu/text/Bidi.length:I
        //    31: istore_1       
        //    32: aload_0        
        //    33: getfield        com/ibm/icu/text/Bidi.levels:[B
        //    36: astore_3       
        //    37: aload_0        
        //    38: getfield        com/ibm/icu/text/Bidi.trailingWSStart:I
        //    41: istore_2       
        //    42: iconst_0       
        //    43: iconst_0       
        //    44: if_icmpge       69
        //    47: aload_3        
        //    48: iconst_0       
        //    49: baload         
        //    50: bipush          126
        //    52: if_icmpeq       63
        //    55: iinc            5, 1
        //    58: aload_3        
        //    59: iconst_0       
        //    60: baload         
        //    61: istore          6
        //    63: iinc            4, 1
        //    66: goto            42
        //    69: iconst_0       
        //    70: iconst_1       
        //    71: if_icmpne       89
        //    74: iconst_0       
        //    75: iload_1        
        //    76: if_icmpne       89
        //    79: aload_0        
        //    80: aload_3        
        //    81: iconst_0       
        //    82: baload         
        //    83: invokestatic    com/ibm/icu/text/BidiLine.getSingleRun:(Lcom/ibm/icu/text/Bidi;B)V
        //    86: goto            300
        //    89: iconst_0       
        //    90: iload_1        
        //    91: if_icmpge       97
        //    94: iinc            5, 1
        //    97: aload_0        
        //    98: iconst_0       
        //    99: invokevirtual   com/ibm/icu/text/Bidi.getRunsMemory:(I)V
        //   102: aload_0        
        //   103: getfield        com/ibm/icu/text/Bidi.runsMemory:[Lcom/ibm/icu/text/BidiRun;
        //   106: astore          7
        //   108: aload_3        
        //   109: iconst_0       
        //   110: baload         
        //   111: istore          6
        //   113: bipush          126
        //   115: bipush          62
        //   117: if_icmpge       120
        //   120: bipush          126
        //   122: iconst_0       
        //   123: if_icmple       126
        //   126: iinc            4, 1
        //   129: iconst_0       
        //   130: iconst_0       
        //   131: if_icmpge       145
        //   134: aload_3        
        //   135: iconst_0       
        //   136: baload         
        //   137: bipush          126
        //   139: if_icmpne       145
        //   142: goto            126
        //   145: aload           7
        //   147: iconst_0       
        //   148: new             Lcom/ibm/icu/text/BidiRun;
        //   151: dup            
        //   152: iconst_0       
        //   153: iconst_0       
        //   154: bipush          126
        //   156: invokespecial   com/ibm/icu/text/BidiRun.<init>:(IIB)V
        //   159: aastore        
        //   160: iinc            8, 1
        //   163: iconst_0       
        //   164: iconst_0       
        //   165: if_icmplt       108
        //   168: iconst_0       
        //   169: iload_1        
        //   170: if_icmpge       207
        //   173: aload           7
        //   175: iconst_0       
        //   176: new             Lcom/ibm/icu/text/BidiRun;
        //   179: dup            
        //   180: iconst_0       
        //   181: iload_1        
        //   182: iconst_0       
        //   183: isub           
        //   184: aload_0        
        //   185: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //   188: invokespecial   com/ibm/icu/text/BidiRun.<init>:(IIB)V
        //   191: aastore        
        //   192: aload_0        
        //   193: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //   196: bipush          62
        //   198: if_icmpge       207
        //   201: aload_0        
        //   202: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //   205: istore          10
        //   207: aload_0        
        //   208: aload           7
        //   210: putfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   213: aload_0        
        //   214: iconst_0       
        //   215: putfield        com/ibm/icu/text/Bidi.runCount:I
        //   218: aload_0        
        //   219: bipush          62
        //   221: iconst_0       
        //   222: invokestatic    com/ibm/icu/text/BidiLine.reorderLine:(Lcom/ibm/icu/text/Bidi;BB)V
        //   225: iconst_0       
        //   226: iconst_0       
        //   227: if_icmpge       267
        //   230: aload           7
        //   232: iconst_0       
        //   233: aaload         
        //   234: aload_3        
        //   235: aload           7
        //   237: iconst_0       
        //   238: aaload         
        //   239: getfield        com/ibm/icu/text/BidiRun.start:I
        //   242: baload         
        //   243: putfield        com/ibm/icu/text/BidiRun.level:B
        //   246: aload           7
        //   248: iconst_0       
        //   249: aaload         
        //   250: dup            
        //   251: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   254: iconst_0       
        //   255: iadd           
        //   256: dup_x1         
        //   257: putfield        com/ibm/icu/text/BidiRun.limit:I
        //   260: istore_2       
        //   261: iinc            4, 1
        //   264: goto            225
        //   267: iconst_0       
        //   268: iconst_0       
        //   269: if_icmpge       300
        //   272: aload_0        
        //   273: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //   276: iconst_1       
        //   277: iand           
        //   278: ifeq            285
        //   281: iconst_0       
        //   282: goto            286
        //   285: iconst_0       
        //   286: istore          12
        //   288: aload           7
        //   290: iload           12
        //   292: aaload         
        //   293: aload_0        
        //   294: getfield        com/ibm/icu/text/Bidi.paraLevel:B
        //   297: putfield        com/ibm/icu/text/BidiRun.level:B
        //   300: aload_0        
        //   301: getfield        com/ibm/icu/text/Bidi.insertPoints:Lcom/ibm/icu/text/Bidi$InsertPoints;
        //   304: getfield        com/ibm/icu/text/Bidi$InsertPoints.size:I
        //   307: ifle            364
        //   310: iconst_0       
        //   311: aload_0        
        //   312: getfield        com/ibm/icu/text/Bidi.insertPoints:Lcom/ibm/icu/text/Bidi$InsertPoints;
        //   315: getfield        com/ibm/icu/text/Bidi$InsertPoints.size:I
        //   318: if_icmpge       364
        //   321: aload_0        
        //   322: getfield        com/ibm/icu/text/Bidi.insertPoints:Lcom/ibm/icu/text/Bidi$InsertPoints;
        //   325: getfield        com/ibm/icu/text/Bidi$InsertPoints.points:[Lcom/ibm/icu/text/Bidi$Point;
        //   328: iconst_0       
        //   329: aaload         
        //   330: astore_1       
        //   331: aload_0        
        //   332: aload_1        
        //   333: getfield        com/ibm/icu/text/Bidi$Point.pos:I
        //   336: invokestatic    com/ibm/icu/text/BidiLine.getRunFromLogicalIndex:(Lcom/ibm/icu/text/Bidi;I)I
        //   339: istore_2       
        //   340: aload_0        
        //   341: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   344: iconst_0       
        //   345: aaload         
        //   346: dup            
        //   347: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   350: aload_1        
        //   351: getfield        com/ibm/icu/text/Bidi$Point.flag:I
        //   354: ior            
        //   355: putfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   358: iinc            3, 1
        //   361: goto            310
        //   364: aload_0        
        //   365: getfield        com/ibm/icu/text/Bidi.controlCount:I
        //   368: ifle            420
        //   371: iconst_0       
        //   372: aload_0        
        //   373: getfield        com/ibm/icu/text/Bidi.length:I
        //   376: if_icmpge       420
        //   379: aload_0        
        //   380: getfield        com/ibm/icu/text/Bidi.text:[C
        //   383: iconst_0       
        //   384: caload         
        //   385: istore_3       
        //   386: iconst_0       
        //   387: invokestatic    com/ibm/icu/text/Bidi.IsBidiControlChar:(I)Z
        //   390: ifeq            414
        //   393: aload_0        
        //   394: iconst_0       
        //   395: invokestatic    com/ibm/icu/text/BidiLine.getRunFromLogicalIndex:(Lcom/ibm/icu/text/Bidi;I)I
        //   398: istore_1       
        //   399: aload_0        
        //   400: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   403: iload_1        
        //   404: aaload         
        //   405: dup            
        //   406: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   409: iconst_1       
        //   410: isub           
        //   411: putfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   414: iinc            2, 1
        //   417: goto            371
        //   420: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static int[] prepareReorder(final byte[] array, final byte[] array2, final byte[] array3) {
        if (array == null || array.length <= 0) {
            return null;
        }
        int i = array.length;
        while (i > 0) {
            final byte b = array[--i];
            if (b > 62) {
                return null;
            }
            if (b < 62) {}
            if (b > 0) {
                continue;
            }
        }
        array2[0] = 62;
        array3[0] = 0;
        final int[] array4 = new int[array.length];
        for (int j = array.length; j > 0; --j, array4[j] = j) {}
        return array4;
    }
    
    static int[] reorderLogical(final byte[] array) {
        final byte[] array2 = { 0 };
        final byte[] array3 = { 0 };
        final int[] prepareReorder = prepareReorder(array, array2, array3);
        if (prepareReorder == null) {
            return null;
        }
        final byte b = array2[0];
        byte b2 = array3[0];
        if (b == b2 && (b & 0x1) == 0x0) {
            return prepareReorder;
        }
        final byte b3 = (byte)(b | 0x1);
        while (true) {
            if (0 < array.length && array[0] < b2) {
                int n = 0;
                ++n;
            }
            else {
                if (0 < array.length) {
                    do {
                        int n2 = 0;
                        ++n2;
                    } while (0 < array.length && array[0] >= b2);
                    do {
                        prepareReorder[0] = -1 - prepareReorder[0];
                        int n = 0;
                        ++n;
                    } while (0 < 0);
                    if (0 != array.length) {
                        continue;
                    }
                }
                --b2;
                if (b2 < b3) {
                    break;
                }
                continue;
            }
        }
        return prepareReorder;
    }
    
    static int[] reorderVisual(final byte[] array) {
        final byte[] array2 = { 0 };
        final byte[] array3 = { 0 };
        final int[] prepareReorder = prepareReorder(array, array2, array3);
        if (prepareReorder == null) {
            return null;
        }
        final byte b = array2[0];
        byte b2 = array3[0];
        if (b == b2 && (b & 0x1) == 0x0) {
            return prepareReorder;
        }
        final byte b3 = (byte)(b | 0x1);
        while (true) {
            if (0 < array.length && array[0] < b2) {
                int n = 0;
                ++n;
            }
            else {
                if (0 < array.length) {
                    do {
                        int n2 = 0;
                        ++n2;
                    } while (0 < array.length && array[0] >= b2);
                    while (0 < -1) {
                        final int n3 = prepareReorder[0];
                        prepareReorder[0] = prepareReorder[-1];
                        prepareReorder[-1] = n3;
                        int n = 0;
                        ++n;
                        int n4 = 0;
                        --n4;
                    }
                    if (0 != array.length) {
                        continue;
                    }
                }
                --b2;
                if (b2 < b3) {
                    break;
                }
                continue;
            }
        }
        return prepareReorder;
    }
    
    static int getVisualIndex(final Bidi bidi, final int n) {
        int n5 = 0;
        switch (bidi.direction) {
            case 0: {
                break;
            }
            case 1: {
                final int n2 = bidi.length - n - 1;
                break;
            }
            default: {
                getRuns(bidi);
                final BidiRun[] runs = bidi.runs;
                while (0 < bidi.runCount) {
                    final int n3 = runs[0].limit - 0;
                    final int n4 = n - runs[0].start;
                    if (n4 >= 0 && n4 < 0) {
                        if (runs[0].isEvenRun()) {
                            break;
                        }
                        break;
                    }
                    else {
                        ++n5;
                    }
                }
                if (0 >= bidi.runCount) {
                    return -1;
                }
                break;
            }
        }
        if (bidi.insertPoints.size > 0) {
            final BidiRun[] runs2 = bidi.runs;
            while (true) {
                final int n6 = runs2[0].limit - 0;
                final int insertRemove = runs2[0].insertRemove;
                int n7 = 0;
                if ((insertRemove & 0x5) > 0) {
                    ++n7;
                }
                if (-1 < runs2[0].limit) {
                    break;
                }
                if ((insertRemove & 0xA) > 0) {
                    ++n7;
                }
                ++n5;
            }
            return -1;
        }
        if (bidi.controlCount <= 0) {
            return -1;
        }
        final BidiRun[] runs3 = bidi.runs;
        if (Bidi.IsBidiControlChar(bidi.text[n])) {
            return -1;
        }
        int insertRemove2;
        int n8 = 0;
        while (true) {
            final int n7 = runs3[0].limit - 0;
            insertRemove2 = runs3[0].insertRemove;
            if (-1 < runs3[0].limit) {
                break;
            }
            n8 = 0 - insertRemove2;
            ++n5;
        }
        if (insertRemove2 == 0) {
            return -1;
        }
        int start;
        if (runs3[0].isEvenRun()) {
            start = runs3[0].start;
        }
        else {
            start = n + 1;
            final int n9 = runs3[0].start + 0;
        }
        int n10 = start;
        while (0 < 0) {
            if (Bidi.IsBidiControlChar(bidi.text[0])) {
                ++n8;
            }
            ++n10;
        }
        return -1;
    }
    
    static int getLogicalIndex(final Bidi bidi, int i) {
        final BidiRun[] runs = bidi.runs;
        final int runCount = bidi.runCount;
        int n3 = 0;
        if (bidi.insertPoints.size > 0) {
            while (true) {
                final int n = runs[0].limit - 0;
                final int insertRemove = runs[0].insertRemove;
                int n2 = 0;
                if (0 > 0) {
                    if (i <= 0) {
                        return -1;
                    }
                    ++n2;
                }
                if (i < runs[0].limit + 0) {
                    i -= 0;
                    break;
                }
                if (0 > 0) {
                    if (i == 0 + n + 0) {
                        return -1;
                    }
                    ++n2;
                }
                ++n3;
            }
        }
        else if (bidi.controlCount > 0) {
            while (true) {
                final int n4 = runs[0].limit - 0;
                final int insertRemove2 = runs[0].insertRemove;
                if (i < runs[0].limit - 0 + 0) {
                    break;
                }
                ++n3;
            }
            if (!false) {
                i += 0;
            }
            else {
                final int start = runs[0].start;
                final boolean evenRun = runs[0].isEvenRun();
                final int n5 = start + 0 - 1;
                while (0 < 0) {
                    if (Bidi.IsBidiControlChar(bidi.text[evenRun ? (start + 0) : (n5 - 0)])) {
                        int n2 = 0;
                        ++n2;
                    }
                    if (i + 0 == 0) {
                        break;
                    }
                    int n6 = 0;
                    ++n6;
                }
                i += 0;
            }
        }
        if (runCount <= 10) {
            while (i >= runs[0].limit) {
                ++n3;
            }
        }
        else {
            while (true) {
                if (i >= runs[0].limit) {
                    continue;
                }
                if (!false) {
                    break;
                }
                if (i >= runs[-1].limit) {
                    break;
                }
            }
        }
        final int start2 = runs[0].start;
        if (runs[0].isEvenRun()) {
            if (0 > 0) {
                i -= runs[-1].limit;
            }
            return start2 + i;
        }
        return start2 + runs[0].limit - i - 1;
    }
    
    static int[] getLogicalMap(final Bidi bidi) {
        final BidiRun[] runs = bidi.runs;
        final int[] array = new int[bidi.length];
        if (bidi.length > bidi.resultLength) {
            Arrays.fill(array, -1);
        }
        int n7 = 0;
        while (0 < bidi.runCount) {
            int start = runs[0].start;
            final int limit = runs[0].limit;
            if (runs[0].isEvenRun()) {
                do {
                    final int[] array2 = array;
                    final int n = start++;
                    final int n2 = 0;
                    int n3 = 0;
                    ++n3;
                    array2[n] = n2;
                } while (0 < limit);
            }
            else {
                int n4 = start + (limit - 0);
                do {
                    final int[] array3 = array;
                    final int n5 = --n4;
                    final int n6 = 0;
                    int n3 = 0;
                    ++n3;
                    array3[n5] = n6;
                } while (0 < limit);
            }
            ++n7;
        }
        if (bidi.insertPoints.size > 0) {
            final int runCount = bidi.runCount;
            final BidiRun[] runs2 = bidi.runs;
            while (0 < runCount) {
                final int n8 = runs2[0].limit - 0;
                final int insertRemove = runs2[0].insertRemove;
                if ((insertRemove & 0x5) > 0) {
                    ++n7;
                }
                if (0 > 0) {
                    final int start2 = runs2[0].start;
                    final int n9 = start2 + n8;
                    int n10 = start2;
                    while (0 < n9) {
                        final int[] array4 = array;
                        final int n11 = 0;
                        array4[n11] += 0;
                        ++n10;
                    }
                }
                if ((insertRemove & 0xA) > 0) {
                    ++n7;
                }
                int n12 = 0;
                ++n12;
                final int n3 = 0 + n8;
            }
        }
        else if (bidi.controlCount > 0) {
            final int runCount2 = bidi.runCount;
            final BidiRun[] runs3 = bidi.runs;
            while (0 < runCount2) {
                final int n13 = runs3[0].limit - 0;
                final int insertRemove2 = runs3[0].insertRemove;
                if (0 - insertRemove2 != 0) {
                    final int start3 = runs3[0].start;
                    final boolean evenRun = runs3[0].isEvenRun();
                    final int n14 = start3 + n13;
                    if (insertRemove2 == 0) {
                        int n15 = start3;
                        while (0 < n14) {
                            final int[] array5 = array;
                            final int n16 = 0;
                            array5[n16] -= 0;
                            ++n15;
                        }
                    }
                    else {
                        while (0 < n13) {
                            final int n17 = evenRun ? (start3 + 0) : (n14 - 0 - 1);
                            if (Bidi.IsBidiControlChar(bidi.text[n17])) {
                                ++n7;
                                array[n17] = -1;
                            }
                            else {
                                final int[] array6 = array;
                                final int n18 = n17;
                                array6[n18] -= 0;
                            }
                            int n15 = 0;
                            ++n15;
                        }
                    }
                }
                int n12 = 0;
                ++n12;
                final int n3 = 0 + n13;
            }
        }
        return array;
    }
    
    static int[] getVisualMap(final Bidi p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //     4: astore_1       
        //     5: aload_0        
        //     6: getfield        com/ibm/icu/text/Bidi.length:I
        //     9: aload_0        
        //    10: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //    13: if_icmple       23
        //    16: aload_0        
        //    17: getfield        com/ibm/icu/text/Bidi.length:I
        //    20: goto            27
        //    23: aload_0        
        //    24: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //    27: istore          5
        //    29: iload           5
        //    31: newarray        I
        //    33: astore          6
        //    35: iconst_0       
        //    36: aload_0        
        //    37: getfield        com/ibm/icu/text/Bidi.runCount:I
        //    40: if_icmpge       123
        //    43: aload_1        
        //    44: iconst_0       
        //    45: aaload         
        //    46: getfield        com/ibm/icu/text/BidiRun.start:I
        //    49: istore_2       
        //    50: aload_1        
        //    51: iconst_0       
        //    52: aaload         
        //    53: getfield        com/ibm/icu/text/BidiRun.limit:I
        //    56: istore          4
        //    58: aload_1        
        //    59: iconst_0       
        //    60: aaload         
        //    61: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //    64: ifeq            90
        //    67: aload           6
        //    69: iconst_0       
        //    70: iinc            7, 1
        //    73: iload_2        
        //    74: iinc            2, 1
        //    77: iastore        
        //    78: iinc            3, 1
        //    81: iconst_0       
        //    82: iload           4
        //    84: if_icmplt       67
        //    87: goto            117
        //    90: iload_2        
        //    91: iload           4
        //    93: iconst_0       
        //    94: isub           
        //    95: iadd           
        //    96: istore_2       
        //    97: aload           6
        //    99: iconst_0       
        //   100: iinc            7, 1
        //   103: iinc            2, -1
        //   106: iload_2        
        //   107: iastore        
        //   108: iinc            3, 1
        //   111: iconst_0       
        //   112: iload           4
        //   114: if_icmplt       97
        //   117: iinc            8, 1
        //   120: goto            35
        //   123: aload_0        
        //   124: getfield        com/ibm/icu/text/Bidi.insertPoints:Lcom/ibm/icu/text/Bidi$InsertPoints;
        //   127: getfield        com/ibm/icu/text/Bidi$InsertPoints.size:I
        //   130: ifle            310
        //   133: aload_0        
        //   134: getfield        com/ibm/icu/text/Bidi.runCount:I
        //   137: istore          9
        //   139: aload_0        
        //   140: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   143: astore_1       
        //   144: iconst_0       
        //   145: iload           9
        //   147: if_icmpge       185
        //   150: aload_1        
        //   151: iconst_0       
        //   152: aaload         
        //   153: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   156: istore          10
        //   158: iload           10
        //   160: iconst_5       
        //   161: iand           
        //   162: ifle            168
        //   165: iinc            8, 1
        //   168: iload           10
        //   170: bipush          10
        //   172: iand           
        //   173: ifle            179
        //   176: iinc            8, 1
        //   179: iinc            11, 1
        //   182: goto            144
        //   185: aload_0        
        //   186: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //   189: istore          13
        //   191: iload           9
        //   193: iconst_1       
        //   194: isub           
        //   195: istore          11
        //   197: iconst_0       
        //   198: iflt            307
        //   201: iconst_0       
        //   202: ifle            307
        //   205: aload_1        
        //   206: iconst_0       
        //   207: aaload         
        //   208: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   211: istore          10
        //   213: iload           10
        //   215: bipush          10
        //   217: iand           
        //   218: ifle            232
        //   221: aload           6
        //   223: iinc            13, -1
        //   226: iconst_0       
        //   227: iconst_m1      
        //   228: iastore        
        //   229: iinc            8, -1
        //   232: iconst_0       
        //   233: ifle            245
        //   236: aload_1        
        //   237: iconst_m1      
        //   238: aaload         
        //   239: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   242: goto            246
        //   245: iconst_0       
        //   246: istore_3       
        //   247: aload_1        
        //   248: iconst_0       
        //   249: aaload         
        //   250: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   253: iconst_1       
        //   254: isub           
        //   255: istore          12
        //   257: iconst_0       
        //   258: iconst_0       
        //   259: if_icmplt       283
        //   262: iconst_0       
        //   263: ifle            283
        //   266: aload           6
        //   268: iinc            13, -1
        //   271: iconst_0       
        //   272: aload           6
        //   274: iconst_0       
        //   275: iaload         
        //   276: iastore        
        //   277: iinc            12, -1
        //   280: goto            257
        //   283: iload           10
        //   285: iconst_5       
        //   286: iand           
        //   287: ifle            301
        //   290: aload           6
        //   292: iinc            13, -1
        //   295: iconst_0       
        //   296: iconst_m1      
        //   297: iastore        
        //   298: iinc            8, -1
        //   301: iinc            11, -1
        //   304: goto            197
        //   307: goto            482
        //   310: aload_0        
        //   311: getfield        com/ibm/icu/text/Bidi.controlCount:I
        //   314: ifle            482
        //   317: aload_0        
        //   318: getfield        com/ibm/icu/text/Bidi.runCount:I
        //   321: istore          8
        //   323: aload_0        
        //   324: getfield        com/ibm/icu/text/Bidi.runs:[Lcom/ibm/icu/text/BidiRun;
        //   327: astore_1       
        //   328: iconst_0       
        //   329: iconst_0       
        //   330: if_icmpge       482
        //   333: aload_1        
        //   334: iconst_0       
        //   335: aaload         
        //   336: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   339: iconst_0       
        //   340: isub           
        //   341: istore          11
        //   343: aload_1        
        //   344: iconst_0       
        //   345: aaload         
        //   346: getfield        com/ibm/icu/text/BidiRun.insertRemove:I
        //   349: istore          10
        //   351: iload           10
        //   353: ifne            364
        //   356: iconst_0       
        //   357: iconst_0       
        //   358: if_icmpne       364
        //   361: goto            476
        //   364: iload           10
        //   366: ifne            400
        //   369: aload_1        
        //   370: iconst_0       
        //   371: aaload         
        //   372: getfield        com/ibm/icu/text/BidiRun.limit:I
        //   375: istore          4
        //   377: iconst_0       
        //   378: iload           4
        //   380: if_icmpge       476
        //   383: aload           6
        //   385: iconst_0       
        //   386: iinc            14, 1
        //   389: aload           6
        //   391: iconst_0       
        //   392: iaload         
        //   393: iastore        
        //   394: iinc            13, 1
        //   397: goto            377
        //   400: aload_1        
        //   401: iconst_0       
        //   402: aaload         
        //   403: getfield        com/ibm/icu/text/BidiRun.start:I
        //   406: istore_2       
        //   407: aload_1        
        //   408: iconst_0       
        //   409: aaload         
        //   410: invokevirtual   com/ibm/icu/text/BidiRun.isEvenRun:()Z
        //   413: istore          17
        //   415: iload_2        
        //   416: iconst_0       
        //   417: iadd           
        //   418: iconst_1       
        //   419: isub           
        //   420: istore          9
        //   422: iconst_0       
        //   423: iconst_0       
        //   424: if_icmpge       476
        //   427: iload           17
        //   429: ifeq            438
        //   432: iload_2        
        //   433: iconst_0       
        //   434: iadd           
        //   435: goto            442
        //   438: iload           9
        //   440: iconst_0       
        //   441: isub           
        //   442: istore          15
        //   444: aload_0        
        //   445: getfield        com/ibm/icu/text/Bidi.text:[C
        //   448: iload           15
        //   450: caload         
        //   451: istore          16
        //   453: iload           16
        //   455: invokestatic    com/ibm/icu/text/Bidi.IsBidiControlChar:(I)Z
        //   458: ifne            470
        //   461: aload           6
        //   463: iconst_0       
        //   464: iinc            14, 1
        //   467: iload           15
        //   469: iastore        
        //   470: iinc            13, 1
        //   473: goto            422
        //   476: iinc            12, 1
        //   479: goto            328
        //   482: iload           5
        //   484: aload_0        
        //   485: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //   488: if_icmpne       494
        //   491: aload           6
        //   493: areturn        
        //   494: aload_0        
        //   495: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //   498: newarray        I
        //   500: astore          8
        //   502: aload           6
        //   504: iconst_0       
        //   505: aload           8
        //   507: iconst_0       
        //   508: aload_0        
        //   509: getfield        com/ibm/icu/text/Bidi.resultLength:I
        //   512: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   515: aload           8
        //   517: areturn        
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
    
    static int[] invertMap(final int[] array) {
        final int length = array.length;
        int n2 = 0;
        int n4 = 0;
        while (0 < length) {
            final int n = array[0];
            if (n > -1) {
                n2 = n;
            }
            if (n >= 0) {
                int n3 = 0;
                ++n3;
            }
            ++n4;
        }
        ++n2;
        final int[] array2 = new int[-1];
        if (0 < -1) {
            Arrays.fill(array2, -1);
        }
        while (0 < length) {
            final int n5 = array[0];
            if (n5 >= 0) {
                array2[n5] = 0;
            }
            ++n4;
        }
        return array2;
    }
}
