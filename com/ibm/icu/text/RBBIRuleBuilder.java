package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;

class RBBIRuleBuilder
{
    String fDebugEnv;
    String fRules;
    RBBIRuleScanner fScanner;
    RBBINode[] fTreeRoots;
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    int fDefaultTree;
    boolean fChainRules;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    RBBISetBuilder fSetBuilder;
    List fUSetNodes;
    RBBITableBuilder fForwardTables;
    RBBITableBuilder fReverseTables;
    RBBITableBuilder fSafeFwdTables;
    RBBITableBuilder fSafeRevTables;
    Map fStatusSets;
    List fRuleStatusVals;
    static final int U_BRK_ERROR_START = 66048;
    static final int U_BRK_INTERNAL_ERROR = 66049;
    static final int U_BRK_HEX_DIGITS_EXPECTED = 66050;
    static final int U_BRK_SEMICOLON_EXPECTED = 66051;
    static final int U_BRK_RULE_SYNTAX = 66052;
    static final int U_BRK_UNCLOSED_SET = 66053;
    static final int U_BRK_ASSIGN_ERROR = 66054;
    static final int U_BRK_VARIABLE_REDFINITION = 66055;
    static final int U_BRK_MISMATCHED_PAREN = 66056;
    static final int U_BRK_NEW_LINE_IN_QUOTED_STRING = 66057;
    static final int U_BRK_UNDEFINED_VARIABLE = 66058;
    static final int U_BRK_INIT_ERROR = 66059;
    static final int U_BRK_RULE_EMPTY_SET = 66060;
    static final int U_BRK_UNRECOGNIZED_OPTION = 66061;
    static final int U_BRK_MALFORMED_RULE_TAG = 66062;
    static final int U_BRK_MALFORMED_SET = 66063;
    static final int U_BRK_ERROR_LIMIT = 66064;
    
    RBBIRuleBuilder(final String fRules) {
        this.fTreeRoots = new RBBINode[4];
        this.fDefaultTree = 0;
        this.fStatusSets = new HashMap();
        this.fDebugEnv = (ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null);
        this.fRules = fRules;
        this.fUSetNodes = new ArrayList();
        this.fRuleStatusVals = new ArrayList();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }
    
    static final int align8(final int n) {
        return n + 7 & 0xFFFFFFF8;
    }
    
    void flattenData(final OutputStream p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: aload_1        
        //     5: invokespecial   java/io/DataOutputStream.<init>:(Ljava/io/OutputStream;)V
        //     8: astore_2       
        //     9: aload_0        
        //    10: getfield        com/ibm/icu/text/RBBIRuleBuilder.fRules:Ljava/lang/String;
        //    13: invokestatic    com/ibm/icu/text/RBBIRuleScanner.stripRules:(Ljava/lang/String;)Ljava/lang/String;
        //    16: astore          4
        //    18: aload_0        
        //    19: getfield        com/ibm/icu/text/RBBIRuleBuilder.fForwardTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //    22: invokevirtual   com/ibm/icu/text/RBBITableBuilder.getTableSize:()I
        //    25: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    28: istore          6
        //    30: aload_0        
        //    31: getfield        com/ibm/icu/text/RBBIRuleBuilder.fReverseTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //    34: invokevirtual   com/ibm/icu/text/RBBITableBuilder.getTableSize:()I
        //    37: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    40: istore          7
        //    42: aload_0        
        //    43: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSafeFwdTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //    46: invokevirtual   com/ibm/icu/text/RBBITableBuilder.getTableSize:()I
        //    49: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    52: istore          8
        //    54: aload_0        
        //    55: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSafeRevTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //    58: invokevirtual   com/ibm/icu/text/RBBITableBuilder.getTableSize:()I
        //    61: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    64: istore          9
        //    66: aload_0        
        //    67: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSetBuilder:Lcom/ibm/icu/text/RBBISetBuilder;
        //    70: invokevirtual   com/ibm/icu/text/RBBISetBuilder.getTrieSize:()I
        //    73: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    76: istore          10
        //    78: aload_0        
        //    79: getfield        com/ibm/icu/text/RBBIRuleBuilder.fRuleStatusVals:Ljava/util/List;
        //    82: invokeinterface java/util/List.size:()I
        //    87: iconst_4       
        //    88: imul           
        //    89: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //    92: istore          11
        //    94: aload           4
        //    96: invokevirtual   java/lang/String.length:()I
        //    99: iconst_2       
        //   100: imul           
        //   101: invokestatic    com/ibm/icu/text/RBBIRuleBuilder.align8:(I)I
        //   104: istore          12
        //   106: bipush          96
        //   108: iload           6
        //   110: iadd           
        //   111: iload           7
        //   113: iadd           
        //   114: iload           8
        //   116: iadd           
        //   117: iload           9
        //   119: iadd           
        //   120: iload           11
        //   122: iadd           
        //   123: iload           10
        //   125: iadd           
        //   126: iload           12
        //   128: iadd           
        //   129: istore          13
        //   131: sipush          128
        //   134: newarray        B
        //   136: astore          15
        //   138: aload_2        
        //   139: aload           15
        //   141: invokevirtual   java/io/DataOutputStream.write:([B)V
        //   144: bipush          24
        //   146: newarray        I
        //   148: astore          16
        //   150: aload           16
        //   152: iconst_0       
        //   153: ldc             45472
        //   155: iastore        
        //   156: aload           16
        //   158: iconst_1       
        //   159: ldc             50397184
        //   161: iastore        
        //   162: aload           16
        //   164: iconst_2       
        //   165: iload           13
        //   167: iastore        
        //   168: aload           16
        //   170: iconst_3       
        //   171: aload_0        
        //   172: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSetBuilder:Lcom/ibm/icu/text/RBBISetBuilder;
        //   175: invokevirtual   com/ibm/icu/text/RBBISetBuilder.getNumCharCategories:()I
        //   178: iastore        
        //   179: aload           16
        //   181: iconst_4       
        //   182: bipush          96
        //   184: iastore        
        //   185: aload           16
        //   187: iconst_5       
        //   188: iload           6
        //   190: iastore        
        //   191: aload           16
        //   193: bipush          6
        //   195: aload           16
        //   197: iconst_4       
        //   198: iaload         
        //   199: iload           6
        //   201: iadd           
        //   202: iastore        
        //   203: aload           16
        //   205: bipush          7
        //   207: iload           7
        //   209: iastore        
        //   210: aload           16
        //   212: bipush          8
        //   214: aload           16
        //   216: bipush          6
        //   218: iaload         
        //   219: iload           7
        //   221: iadd           
        //   222: iastore        
        //   223: aload           16
        //   225: bipush          9
        //   227: iload           8
        //   229: iastore        
        //   230: aload           16
        //   232: bipush          10
        //   234: aload           16
        //   236: bipush          8
        //   238: iaload         
        //   239: iload           8
        //   241: iadd           
        //   242: iastore        
        //   243: aload           16
        //   245: bipush          11
        //   247: iload           9
        //   249: iastore        
        //   250: aload           16
        //   252: bipush          12
        //   254: aload           16
        //   256: bipush          10
        //   258: iaload         
        //   259: iload           9
        //   261: iadd           
        //   262: iastore        
        //   263: aload           16
        //   265: bipush          13
        //   267: aload_0        
        //   268: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSetBuilder:Lcom/ibm/icu/text/RBBISetBuilder;
        //   271: invokevirtual   com/ibm/icu/text/RBBISetBuilder.getTrieSize:()I
        //   274: iastore        
        //   275: aload           16
        //   277: bipush          16
        //   279: aload           16
        //   281: bipush          12
        //   283: iaload         
        //   284: aload           16
        //   286: bipush          13
        //   288: iaload         
        //   289: iadd           
        //   290: iastore        
        //   291: aload           16
        //   293: bipush          17
        //   295: iload           11
        //   297: iastore        
        //   298: aload           16
        //   300: bipush          14
        //   302: aload           16
        //   304: bipush          16
        //   306: iaload         
        //   307: iload           11
        //   309: iadd           
        //   310: iastore        
        //   311: aload           16
        //   313: bipush          15
        //   315: aload           4
        //   317: invokevirtual   java/lang/String.length:()I
        //   320: iconst_2       
        //   321: imul           
        //   322: iastore        
        //   323: iconst_0       
        //   324: aload           16
        //   326: arraylength    
        //   327: if_icmpge       347
        //   330: aload_2        
        //   331: aload           16
        //   333: iconst_0       
        //   334: iaload         
        //   335: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   338: iinc            14, 4
        //   341: iinc            3, 1
        //   344: goto            323
        //   347: aload_0        
        //   348: getfield        com/ibm/icu/text/RBBIRuleBuilder.fForwardTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //   351: invokevirtual   com/ibm/icu/text/RBBITableBuilder.exportTable:()[S
        //   354: astore          17
        //   356: iconst_0       
        //   357: aload           16
        //   359: iconst_4       
        //   360: iaload         
        //   361: if_icmpne       368
        //   364: iconst_1       
        //   365: goto            369
        //   368: iconst_0       
        //   369: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   372: iconst_0       
        //   373: aload           17
        //   375: arraylength    
        //   376: if_icmpge       396
        //   379: aload_2        
        //   380: aload           17
        //   382: iconst_0       
        //   383: saload         
        //   384: invokevirtual   java/io/DataOutputStream.writeShort:(I)V
        //   387: iinc            14, 2
        //   390: iinc            3, 1
        //   393: goto            372
        //   396: aload_0        
        //   397: getfield        com/ibm/icu/text/RBBIRuleBuilder.fReverseTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //   400: invokevirtual   com/ibm/icu/text/RBBITableBuilder.exportTable:()[S
        //   403: astore          17
        //   405: iconst_0       
        //   406: aload           16
        //   408: bipush          6
        //   410: iaload         
        //   411: if_icmpne       418
        //   414: iconst_1       
        //   415: goto            419
        //   418: iconst_0       
        //   419: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   422: iconst_0       
        //   423: aload           17
        //   425: arraylength    
        //   426: if_icmpge       446
        //   429: aload_2        
        //   430: aload           17
        //   432: iconst_0       
        //   433: saload         
        //   434: invokevirtual   java/io/DataOutputStream.writeShort:(I)V
        //   437: iinc            14, 2
        //   440: iinc            3, 1
        //   443: goto            422
        //   446: iconst_0       
        //   447: aload           16
        //   449: bipush          8
        //   451: iaload         
        //   452: if_icmpne       459
        //   455: iconst_1       
        //   456: goto            460
        //   459: iconst_0       
        //   460: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   463: aload_0        
        //   464: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSafeFwdTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //   467: invokevirtual   com/ibm/icu/text/RBBITableBuilder.exportTable:()[S
        //   470: astore          17
        //   472: iconst_0       
        //   473: aload           17
        //   475: arraylength    
        //   476: if_icmpge       496
        //   479: aload_2        
        //   480: aload           17
        //   482: iconst_0       
        //   483: saload         
        //   484: invokevirtual   java/io/DataOutputStream.writeShort:(I)V
        //   487: iinc            14, 2
        //   490: iinc            3, 1
        //   493: goto            472
        //   496: iconst_0       
        //   497: aload           16
        //   499: bipush          10
        //   501: iaload         
        //   502: if_icmpne       509
        //   505: iconst_1       
        //   506: goto            510
        //   509: iconst_0       
        //   510: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   513: aload_0        
        //   514: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSafeRevTables:Lcom/ibm/icu/text/RBBITableBuilder;
        //   517: invokevirtual   com/ibm/icu/text/RBBITableBuilder.exportTable:()[S
        //   520: astore          17
        //   522: iconst_0       
        //   523: aload           17
        //   525: arraylength    
        //   526: if_icmpge       546
        //   529: aload_2        
        //   530: aload           17
        //   532: iconst_0       
        //   533: saload         
        //   534: invokevirtual   java/io/DataOutputStream.writeShort:(I)V
        //   537: iinc            14, 2
        //   540: iinc            3, 1
        //   543: goto            522
        //   546: iconst_0       
        //   547: aload           16
        //   549: bipush          12
        //   551: iaload         
        //   552: if_icmpne       559
        //   555: iconst_1       
        //   556: goto            560
        //   559: iconst_0       
        //   560: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   563: aload_0        
        //   564: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSetBuilder:Lcom/ibm/icu/text/RBBISetBuilder;
        //   567: aload_1        
        //   568: invokevirtual   com/ibm/icu/text/RBBISetBuilder.serializeTrie:(Ljava/io/OutputStream;)V
        //   571: iconst_0       
        //   572: aload           16
        //   574: bipush          13
        //   576: iaload         
        //   577: iadd           
        //   578: istore          14
        //   580: iconst_0       
        //   581: ifeq            595
        //   584: aload_2        
        //   585: iconst_0       
        //   586: invokevirtual   java/io/DataOutputStream.write:(I)V
        //   589: iinc            14, 1
        //   592: goto            580
        //   595: iconst_0       
        //   596: aload           16
        //   598: bipush          16
        //   600: iaload         
        //   601: if_icmpne       608
        //   604: iconst_1       
        //   605: goto            609
        //   608: iconst_0       
        //   609: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   612: aload_0        
        //   613: getfield        com/ibm/icu/text/RBBIRuleBuilder.fRuleStatusVals:Ljava/util/List;
        //   616: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   621: astore          18
        //   623: aload           18
        //   625: invokeinterface java/util/Iterator.hasNext:()Z
        //   630: ifeq            660
        //   633: aload           18
        //   635: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   640: checkcast       Ljava/lang/Integer;
        //   643: astore          19
        //   645: aload_2        
        //   646: aload           19
        //   648: invokevirtual   java/lang/Integer.intValue:()I
        //   651: invokevirtual   java/io/DataOutputStream.writeInt:(I)V
        //   654: iinc            14, 4
        //   657: goto            623
        //   660: iconst_0       
        //   661: ifeq            675
        //   664: aload_2        
        //   665: iconst_0       
        //   666: invokevirtual   java/io/DataOutputStream.write:(I)V
        //   669: iinc            14, 1
        //   672: goto            660
        //   675: iconst_0       
        //   676: aload           16
        //   678: bipush          14
        //   680: iaload         
        //   681: if_icmpne       688
        //   684: iconst_1       
        //   685: goto            689
        //   688: iconst_0       
        //   689: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   692: aload_2        
        //   693: aload           4
        //   695: invokevirtual   java/io/DataOutputStream.writeChars:(Ljava/lang/String;)V
        //   698: iconst_0       
        //   699: aload           4
        //   701: invokevirtual   java/lang/String.length:()I
        //   704: iconst_2       
        //   705: imul           
        //   706: iadd           
        //   707: istore          14
        //   709: iconst_0       
        //   710: ifeq            724
        //   713: aload_2        
        //   714: iconst_0       
        //   715: invokevirtual   java/io/DataOutputStream.write:(I)V
        //   718: iinc            14, 1
        //   721: goto            709
        //   724: return         
        //    Exceptions:
        //  throws java.io.IOException
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
    
    static void compileRules(final String s, final OutputStream outputStream) throws IOException {
        final RBBIRuleBuilder rbbiRuleBuilder = new RBBIRuleBuilder(s);
        rbbiRuleBuilder.fScanner.parse();
        rbbiRuleBuilder.fSetBuilder.build();
        rbbiRuleBuilder.fForwardTables = new RBBITableBuilder(rbbiRuleBuilder, 0);
        rbbiRuleBuilder.fReverseTables = new RBBITableBuilder(rbbiRuleBuilder, 1);
        rbbiRuleBuilder.fSafeFwdTables = new RBBITableBuilder(rbbiRuleBuilder, 2);
        rbbiRuleBuilder.fSafeRevTables = new RBBITableBuilder(rbbiRuleBuilder, 3);
        rbbiRuleBuilder.fForwardTables.build();
        rbbiRuleBuilder.fReverseTables.build();
        rbbiRuleBuilder.fSafeFwdTables.build();
        rbbiRuleBuilder.fSafeRevTables.build();
        if (rbbiRuleBuilder.fDebugEnv != null && rbbiRuleBuilder.fDebugEnv.indexOf("states") >= 0) {
            rbbiRuleBuilder.fForwardTables.printRuleStatusTable();
        }
        rbbiRuleBuilder.flattenData(outputStream);
    }
}
