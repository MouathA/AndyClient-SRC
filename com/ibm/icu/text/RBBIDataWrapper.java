package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.impl.*;

final class RBBIDataWrapper
{
    RBBIDataHeader fHeader;
    short[] fFTable;
    short[] fRTable;
    short[] fSFTable;
    short[] fSRTable;
    CharTrie fTrie;
    String fRuleSource;
    int[] fStatusTable;
    static final int DH_SIZE = 24;
    static final int DH_MAGIC = 0;
    static final int DH_FORMATVERSION = 1;
    static final int DH_LENGTH = 2;
    static final int DH_CATCOUNT = 3;
    static final int DH_FTABLE = 4;
    static final int DH_FTABLELEN = 5;
    static final int DH_RTABLE = 6;
    static final int DH_RTABLELEN = 7;
    static final int DH_SFTABLE = 8;
    static final int DH_SFTABLELEN = 9;
    static final int DH_SRTABLE = 10;
    static final int DH_SRTABLELEN = 11;
    static final int DH_TRIE = 12;
    static final int DH_TRIELEN = 13;
    static final int DH_RULESOURCE = 14;
    static final int DH_RULESOURCELEN = 15;
    static final int DH_STATUSTABLE = 16;
    static final int DH_STATUSTABLELEN = 17;
    static final int ACCEPTING = 0;
    static final int LOOKAHEAD = 1;
    static final int TAGIDX = 2;
    static final int RESERVED = 3;
    static final int NEXTSTATES = 4;
    static final int NUMSTATES = 0;
    static final int ROWLEN = 2;
    static final int FLAGS = 4;
    static final int RESERVED_2 = 6;
    static final int ROW_DATA = 8;
    static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
    static final int RBBI_BOF_REQUIRED = 2;
    static TrieFoldingFunc fTrieFoldingFunc;
    
    int getRowIndex(final int n) {
        return 8 + n * (this.fHeader.fCatCount + 4);
    }
    
    static RBBIDataWrapper get(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
        final RBBIDataWrapper rbbiDataWrapper = new RBBIDataWrapper();
        dataInputStream.skip(128L);
        rbbiDataWrapper.fHeader = new RBBIDataHeader();
        rbbiDataWrapper.fHeader.fMagic = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fVersion = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fFormatVersion[0] = (byte)(rbbiDataWrapper.fHeader.fVersion >> 24);
        rbbiDataWrapper.fHeader.fFormatVersion[1] = (byte)(rbbiDataWrapper.fHeader.fVersion >> 16);
        rbbiDataWrapper.fHeader.fFormatVersion[2] = (byte)(rbbiDataWrapper.fHeader.fVersion >> 8);
        rbbiDataWrapper.fHeader.fFormatVersion[3] = (byte)rbbiDataWrapper.fHeader.fVersion;
        rbbiDataWrapper.fHeader.fLength = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fCatCount = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fFTable = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fFTableLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fRTable = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fRTableLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fSFTable = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fSFTableLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fSRTable = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fSRTableLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fTrie = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fTrieLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fRuleSource = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fRuleSourceLen = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fStatusTable = dataInputStream.readInt();
        rbbiDataWrapper.fHeader.fStatusTableLen = dataInputStream.readInt();
        dataInputStream.skip(24L);
        if (rbbiDataWrapper.fHeader.fMagic != 45472 || (rbbiDataWrapper.fHeader.fVersion != 1 && rbbiDataWrapper.fHeader.fFormatVersion[0] != 3)) {
            throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
        }
        if (rbbiDataWrapper.fHeader.fFTable < 96 || rbbiDataWrapper.fHeader.fFTable > rbbiDataWrapper.fHeader.fLength) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        dataInputStream.skip(rbbiDataWrapper.fHeader.fFTable - 96);
        int ffTable = rbbiDataWrapper.fHeader.fFTable;
        rbbiDataWrapper.fFTable = new short[rbbiDataWrapper.fHeader.fFTableLen / 2];
        int n = 0;
        while (0 < rbbiDataWrapper.fFTable.length) {
            rbbiDataWrapper.fFTable[0] = dataInputStream.readShort();
            ffTable += 2;
            ++n;
        }
        dataInputStream.skip(rbbiDataWrapper.fHeader.fRTable - 96);
        int frTable = rbbiDataWrapper.fHeader.fRTable;
        rbbiDataWrapper.fRTable = new short[rbbiDataWrapper.fHeader.fRTableLen / 2];
        while (0 < rbbiDataWrapper.fRTable.length) {
            rbbiDataWrapper.fRTable[0] = dataInputStream.readShort();
            frTable += 2;
            ++n;
        }
        if (rbbiDataWrapper.fHeader.fSFTableLen > 0) {
            dataInputStream.skip(rbbiDataWrapper.fHeader.fSFTable - 96);
            int fsfTable = rbbiDataWrapper.fHeader.fSFTable;
            rbbiDataWrapper.fSFTable = new short[rbbiDataWrapper.fHeader.fSFTableLen / 2];
            while (0 < rbbiDataWrapper.fSFTable.length) {
                rbbiDataWrapper.fSFTable[0] = dataInputStream.readShort();
                fsfTable += 2;
                ++n;
            }
        }
        if (rbbiDataWrapper.fHeader.fSRTableLen > 0) {
            dataInputStream.skip(rbbiDataWrapper.fHeader.fSRTable - 96);
            int fsrTable = rbbiDataWrapper.fHeader.fSRTable;
            rbbiDataWrapper.fSRTable = new short[rbbiDataWrapper.fHeader.fSRTableLen / 2];
            while (0 < rbbiDataWrapper.fSRTable.length) {
                rbbiDataWrapper.fSRTable[0] = dataInputStream.readShort();
                fsrTable += 2;
                ++n;
            }
        }
        dataInputStream.skip(rbbiDataWrapper.fHeader.fTrie - 96);
        final int fTrie = rbbiDataWrapper.fHeader.fTrie;
        dataInputStream.mark(rbbiDataWrapper.fHeader.fTrieLen + 100);
        rbbiDataWrapper.fTrie = new CharTrie(dataInputStream, RBBIDataWrapper.fTrieFoldingFunc);
        dataInputStream.reset();
        if (96 > rbbiDataWrapper.fHeader.fStatusTable) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        dataInputStream.skip(rbbiDataWrapper.fHeader.fStatusTable - 96);
        int fStatusTable = rbbiDataWrapper.fHeader.fStatusTable;
        rbbiDataWrapper.fStatusTable = new int[rbbiDataWrapper.fHeader.fStatusTableLen / 4];
        while (0 < rbbiDataWrapper.fStatusTable.length) {
            rbbiDataWrapper.fStatusTable[0] = dataInputStream.readInt();
            fStatusTable += 4;
            ++n;
        }
        if (96 > rbbiDataWrapper.fHeader.fRuleSource) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        dataInputStream.skip(rbbiDataWrapper.fHeader.fRuleSource - 96);
        int fRuleSource = rbbiDataWrapper.fHeader.fRuleSource;
        final StringBuilder sb = new StringBuilder(rbbiDataWrapper.fHeader.fRuleSourceLen / 2);
        while (0 < rbbiDataWrapper.fHeader.fRuleSourceLen) {
            sb.append(dataInputStream.readChar());
            fRuleSource += 2;
            n += 2;
        }
        rbbiDataWrapper.fRuleSource = sb.toString();
        if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
            rbbiDataWrapper.dump();
        }
        return rbbiDataWrapper;
    }
    
    static final int getNumStates(final short[] array) {
        return (array[0] << 16) + (array[1] & 0xFFFF);
    }
    
    void dump() {
        if (this.fFTable.length == 0) {
            throw new NullPointerException();
        }
        System.out.println("RBBI Data Wrapper dump ...");
        System.out.println();
        System.out.println("Forward State Table");
        this.dumpTable(this.fFTable);
        System.out.println("Reverse State Table");
        this.dumpTable(this.fRTable);
        System.out.println("Forward Safe Points Table");
        this.dumpTable(this.fSFTable);
        System.out.println("Reverse Safe Points Table");
        this.dumpTable(this.fSRTable);
        this.dumpCharCategories();
        System.out.println("Source Rules: " + this.fRuleSource);
    }
    
    public static String intToString(final int n, final int n2) {
        final StringBuilder sb = new StringBuilder(n2);
        sb.append(n);
        while (sb.length() < n2) {
            sb.insert(0, ' ');
        }
        return sb.toString();
    }
    
    public static String intToHexString(final int n, final int n2) {
        final StringBuilder sb = new StringBuilder(n2);
        sb.append(Integer.toHexString(n));
        while (sb.length() < n2) {
            sb.insert(0, ' ');
        }
        return sb.toString();
    }
    
    private void dumpTable(final short[] array) {
        if (array == null) {
            System.out.println("  -- null -- ");
        }
        else {
            final StringBuilder sb = new StringBuilder(" Row  Acc Look  Tag");
            int n = 0;
            while (0 < this.fHeader.fCatCount) {
                sb.append(intToString(0, 5));
                ++n;
            }
            System.out.println(sb.toString());
            while (0 < sb.length()) {
                System.out.print("-");
                ++n;
            }
            System.out.println();
            while (0 < getNumStates(array)) {
                this.dumpRow(array, 0);
                int n2 = 0;
                ++n2;
            }
            System.out.println();
        }
    }
    
    private void dumpRow(final short[] array, final int n) {
        final StringBuilder sb = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
        sb.append(intToString(n, 4));
        final int rowIndex = this.getRowIndex(n);
        if (array[rowIndex + 0] != 0) {
            sb.append(intToString(array[rowIndex + 0], 5));
        }
        else {
            sb.append("     ");
        }
        if (array[rowIndex + 1] != 0) {
            sb.append(intToString(array[rowIndex + 1], 5));
        }
        else {
            sb.append("     ");
        }
        sb.append(intToString(array[rowIndex + 2], 5));
        while (0 < this.fHeader.fCatCount) {
            sb.append(intToString(array[rowIndex + 4 + 0], 5));
            int n2 = 0;
            ++n2;
        }
        System.out.println(sb);
    }
    
    private void dumpCharCategories() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/ibm/icu/text/RBBIDataWrapper.fHeader:Lcom/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader;
        //     4: getfield        com/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader.fCatCount:I
        //     7: istore_1       
        //     8: iload_1        
        //     9: iconst_1       
        //    10: iadd           
        //    11: anewarray       Ljava/lang/String;
        //    14: astore_2       
        //    15: iload_1        
        //    16: iconst_1       
        //    17: iadd           
        //    18: newarray        I
        //    20: astore          8
        //    22: iconst_0       
        //    23: aload_0        
        //    24: getfield        com/ibm/icu/text/RBBIDataWrapper.fHeader:Lcom/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader;
        //    27: getfield        com/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader.fCatCount:I
        //    30: if_icmpgt       45
        //    33: aload_2        
        //    34: iconst_0       
        //    35: ldc_w           ""
        //    38: aastore        
        //    39: iinc            7, 1
        //    42: goto            22
        //    45: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    48: ldc_w           "\nCharacter Categories"
        //    51: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    54: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    57: ldc_w           "--------------------"
        //    60: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    63: iconst_0       
        //    64: ldc_w           1114111
        //    67: if_icmpgt       283
        //    70: aload_0        
        //    71: getfield        com/ibm/icu/text/RBBIDataWrapper.fTrie:Lcom/ibm/icu/impl/CharTrie;
        //    74: iconst_0       
        //    75: invokevirtual   com/ibm/icu/impl/CharTrie.getCodePointValue:(I)C
        //    78: istore          7
        //    80: iconst_0       
        //    81: iflt            95
        //    84: iconst_0       
        //    85: aload_0        
        //    86: getfield        com/ibm/icu/text/RBBIDataWrapper.fHeader:Lcom/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader;
        //    89: getfield        com/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader.fCatCount:I
        //    92: if_icmple       140
        //    95: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    98: new             Ljava/lang/StringBuilder;
        //   101: dup            
        //   102: invokespecial   java/lang/StringBuilder.<init>:()V
        //   105: ldc_w           "Error, bad category "
        //   108: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   111: iconst_0       
        //   112: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   115: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   118: ldc_w           " for char "
        //   121: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   124: iconst_0       
        //   125: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   128: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   131: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   134: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   137: goto            283
        //   140: iconst_0       
        //   141: iconst_m1      
        //   142: if_icmpne       148
        //   145: goto            277
        //   148: iconst_m1      
        //   149: iflt            272
        //   152: aload_2        
        //   153: iconst_m1      
        //   154: aaload         
        //   155: invokevirtual   java/lang/String.length:()I
        //   158: aload           8
        //   160: iconst_m1      
        //   161: iaload         
        //   162: bipush          70
        //   164: iadd           
        //   165: if_icmple       205
        //   168: aload           8
        //   170: iconst_m1      
        //   171: aload_2        
        //   172: iconst_m1      
        //   173: aaload         
        //   174: invokevirtual   java/lang/String.length:()I
        //   177: bipush          10
        //   179: iadd           
        //   180: iastore        
        //   181: new             Ljava/lang/StringBuilder;
        //   184: dup            
        //   185: invokespecial   java/lang/StringBuilder.<init>:()V
        //   188: aload_2        
        //   189: iconst_m1      
        //   190: dup2_x1        
        //   191: aaload         
        //   192: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   195: ldc_w           "\n       "
        //   198: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   201: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   204: aastore        
        //   205: new             Ljava/lang/StringBuilder;
        //   208: dup            
        //   209: invokespecial   java/lang/StringBuilder.<init>:()V
        //   212: aload_2        
        //   213: iconst_m1      
        //   214: dup2_x1        
        //   215: aaload         
        //   216: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   219: ldc_w           " "
        //   222: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   225: iconst_0       
        //   226: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   229: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   232: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   235: aastore        
        //   236: iconst_0       
        //   237: iconst_0       
        //   238: if_icmpeq       272
        //   241: new             Ljava/lang/StringBuilder;
        //   244: dup            
        //   245: invokespecial   java/lang/StringBuilder.<init>:()V
        //   248: aload_2        
        //   249: iconst_m1      
        //   250: dup2_x1        
        //   251: aaload         
        //   252: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   255: ldc_w           "-"
        //   258: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   261: iconst_0       
        //   262: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   265: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   268: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   271: aastore        
        //   272: iconst_0       
        //   273: iconst_0       
        //   274: istore          4
        //   276: istore_3       
        //   277: iinc            6, 1
        //   280: goto            63
        //   283: new             Ljava/lang/StringBuilder;
        //   286: dup            
        //   287: invokespecial   java/lang/StringBuilder.<init>:()V
        //   290: aload_2        
        //   291: iconst_m1      
        //   292: dup2_x1        
        //   293: aaload         
        //   294: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   297: ldc_w           " "
        //   300: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   303: iconst_0       
        //   304: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   307: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   310: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   313: aastore        
        //   314: iconst_0       
        //   315: iconst_0       
        //   316: if_icmpeq       350
        //   319: new             Ljava/lang/StringBuilder;
        //   322: dup            
        //   323: invokespecial   java/lang/StringBuilder.<init>:()V
        //   326: aload_2        
        //   327: iconst_m1      
        //   328: dup2_x1        
        //   329: aaload         
        //   330: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   333: ldc_w           "-"
        //   336: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   339: iconst_0       
        //   340: invokestatic    java/lang/Integer.toHexString:(I)Ljava/lang/String;
        //   343: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   346: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   349: aastore        
        //   350: iconst_0       
        //   351: aload_0        
        //   352: getfield        com/ibm/icu/text/RBBIDataWrapper.fHeader:Lcom/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader;
        //   355: getfield        com/ibm/icu/text/RBBIDataWrapper$RBBIDataHeader.fCatCount:I
        //   358: if_icmpgt       403
        //   361: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   364: new             Ljava/lang/StringBuilder;
        //   367: dup            
        //   368: invokespecial   java/lang/StringBuilder.<init>:()V
        //   371: iconst_0       
        //   372: iconst_5       
        //   373: invokestatic    com/ibm/icu/text/RBBIDataWrapper.intToString:(II)Ljava/lang/String;
        //   376: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   379: ldc_w           "  "
        //   382: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   385: aload_2        
        //   386: iconst_0       
        //   387: aaload         
        //   388: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   391: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   394: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   397: iinc            7, 1
        //   400: goto            350
        //   403: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   406: invokevirtual   java/io/PrintStream.println:()V
        //   409: return         
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
    
    static {
        RBBIDataWrapper.fTrieFoldingFunc = new TrieFoldingFunc();
    }
    
    static class TrieFoldingFunc implements Trie.DataManipulate
    {
        public int getFoldingOffset(final int n) {
            if ((n & 0x8000) != 0x0) {
                return n & 0x7FFF;
            }
            return 0;
        }
    }
    
    static final class RBBIDataHeader
    {
        int fMagic;
        int fVersion;
        byte[] fFormatVersion;
        int fLength;
        int fCatCount;
        int fFTable;
        int fFTableLen;
        int fRTable;
        int fRTableLen;
        int fSFTable;
        int fSFTableLen;
        int fSRTable;
        int fSRTableLen;
        int fTrie;
        int fTrieLen;
        int fRuleSource;
        int fRuleSourceLen;
        int fStatusTable;
        int fStatusTableLen;
        
        public RBBIDataHeader() {
            this.fMagic = 0;
            this.fFormatVersion = new byte[4];
        }
    }
}
