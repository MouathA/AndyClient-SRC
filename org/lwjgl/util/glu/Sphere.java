package org.lwjgl.util.glu;

public class Sphere extends Quadric
{
    public void draw(final float p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/lwjgl/util/glu/Quadric.normals:I
        //     4: ldc             100002
        //     6: if_icmpeq       13
        //     9: iconst_1       
        //    10: goto            14
        //    13: iconst_0       
        //    14: istore          19
        //    16: aload_0        
        //    17: getfield        org/lwjgl/util/glu/Quadric.orientation:I
        //    20: ldc             100021
        //    22: if_icmpne       32
        //    25: ldc             -1.0
        //    27: fstore          20
        //    29: goto            35
        //    32: fconst_1       
        //    33: fstore          20
        //    35: ldc             3.1415927
        //    37: iload_3        
        //    38: i2f            
        //    39: fdiv           
        //    40: fstore          5
        //    42: ldc             6.2831855
        //    44: iload_2        
        //    45: i2f            
        //    46: fdiv           
        //    47: fstore          7
        //    49: aload_0        
        //    50: getfield        org/lwjgl/util/glu/Quadric.drawStyle:I
        //    53: ldc             100012
        //    55: if_icmpne       635
        //    58: aload_0        
        //    59: getfield        org/lwjgl/util/glu/Quadric.textureFlag:Z
        //    62: ifne            192
        //    65: bipush          6
        //    67: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //    70: fconst_0       
        //    71: fconst_0       
        //    72: fconst_1       
        //    73: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //    76: fconst_0       
        //    77: fconst_0       
        //    78: fload           20
        //    80: fload_1        
        //    81: fmul           
        //    82: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //    85: iconst_0       
        //    86: iload_2        
        //    87: if_icmpgt       192
        //    90: iconst_0       
        //    91: iload_2        
        //    92: if_icmpne       99
        //    95: fconst_0       
        //    96: goto            104
        //    99: iconst_0       
        //   100: i2f            
        //   101: fload           7
        //   103: fmul           
        //   104: fstore          6
        //   106: aload_0        
        //   107: fload           6
        //   109: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   112: fneg           
        //   113: aload_0        
        //   114: fload           5
        //   116: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   119: fmul           
        //   120: fstore          8
        //   122: aload_0        
        //   123: fload           6
        //   125: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   128: aload_0        
        //   129: fload           5
        //   131: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   134: fmul           
        //   135: fstore          9
        //   137: fload           20
        //   139: aload_0        
        //   140: fload           5
        //   142: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   145: fmul           
        //   146: fstore          10
        //   148: iload           19
        //   150: ifeq            171
        //   153: fload           8
        //   155: fload           20
        //   157: fmul           
        //   158: fload           9
        //   160: fload           20
        //   162: fmul           
        //   163: fload           10
        //   165: fload           20
        //   167: fmul           
        //   168: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   171: fload           8
        //   173: fload_1        
        //   174: fmul           
        //   175: fload           9
        //   177: fload_1        
        //   178: fmul           
        //   179: fload           10
        //   181: fload_1        
        //   182: fmul           
        //   183: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   186: iinc            16, 1
        //   189: goto            85
        //   192: fconst_1       
        //   193: iload_2        
        //   194: i2f            
        //   195: fdiv           
        //   196: fstore          13
        //   198: fconst_1       
        //   199: iload_3        
        //   200: i2f            
        //   201: fdiv           
        //   202: fstore          14
        //   204: fconst_1       
        //   205: fstore          12
        //   207: aload_0        
        //   208: getfield        org/lwjgl/util/glu/Quadric.textureFlag:Z
        //   211: ifeq            220
        //   214: iload_3        
        //   215: istore          18
        //   217: goto            225
        //   220: iload_3        
        //   221: iconst_1       
        //   222: isub           
        //   223: istore          18
        //   225: iconst_1       
        //   226: iload           18
        //   228: if_icmpge       481
        //   231: iconst_1       
        //   232: i2f            
        //   233: fload           5
        //   235: fmul           
        //   236: fstore          4
        //   238: bipush          8
        //   240: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   243: fconst_0       
        //   244: fstore          11
        //   246: iconst_0       
        //   247: iload_2        
        //   248: if_icmpgt       468
        //   251: iconst_0       
        //   252: iload_2        
        //   253: if_icmpne       260
        //   256: fconst_0       
        //   257: goto            265
        //   260: iconst_0       
        //   261: i2f            
        //   262: fload           7
        //   264: fmul           
        //   265: fstore          6
        //   267: aload_0        
        //   268: fload           6
        //   270: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   273: fneg           
        //   274: aload_0        
        //   275: fload           4
        //   277: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   280: fmul           
        //   281: fstore          8
        //   283: aload_0        
        //   284: fload           6
        //   286: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   289: aload_0        
        //   290: fload           4
        //   292: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   295: fmul           
        //   296: fstore          9
        //   298: fload           20
        //   300: aload_0        
        //   301: fload           4
        //   303: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   306: fmul           
        //   307: fstore          10
        //   309: iload           19
        //   311: ifeq            332
        //   314: fload           8
        //   316: fload           20
        //   318: fmul           
        //   319: fload           9
        //   321: fload           20
        //   323: fmul           
        //   324: fload           10
        //   326: fload           20
        //   328: fmul           
        //   329: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   332: aload_0        
        //   333: fload           11
        //   335: fload           12
        //   337: invokevirtual   org/lwjgl/util/glu/Sphere.TXTR_COORD:(FF)V
        //   340: fload           8
        //   342: fload_1        
        //   343: fmul           
        //   344: fload           9
        //   346: fload_1        
        //   347: fmul           
        //   348: fload           10
        //   350: fload_1        
        //   351: fmul           
        //   352: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   355: aload_0        
        //   356: fload           6
        //   358: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   361: fneg           
        //   362: aload_0        
        //   363: fload           4
        //   365: fload           5
        //   367: fadd           
        //   368: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   371: fmul           
        //   372: fstore          8
        //   374: aload_0        
        //   375: fload           6
        //   377: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   380: aload_0        
        //   381: fload           4
        //   383: fload           5
        //   385: fadd           
        //   386: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   389: fmul           
        //   390: fstore          9
        //   392: fload           20
        //   394: aload_0        
        //   395: fload           4
        //   397: fload           5
        //   399: fadd           
        //   400: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   403: fmul           
        //   404: fstore          10
        //   406: iload           19
        //   408: ifeq            429
        //   411: fload           8
        //   413: fload           20
        //   415: fmul           
        //   416: fload           9
        //   418: fload           20
        //   420: fmul           
        //   421: fload           10
        //   423: fload           20
        //   425: fmul           
        //   426: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   429: aload_0        
        //   430: fload           11
        //   432: fload           12
        //   434: fload           14
        //   436: fsub           
        //   437: invokevirtual   org/lwjgl/util/glu/Sphere.TXTR_COORD:(FF)V
        //   440: fload           11
        //   442: fload           13
        //   444: fadd           
        //   445: fstore          11
        //   447: fload           8
        //   449: fload_1        
        //   450: fmul           
        //   451: fload           9
        //   453: fload_1        
        //   454: fmul           
        //   455: fload           10
        //   457: fload_1        
        //   458: fmul           
        //   459: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   462: iinc            16, 1
        //   465: goto            246
        //   468: fload           12
        //   470: fload           14
        //   472: fsub           
        //   473: fstore          12
        //   475: iinc            15, 1
        //   478: goto            225
        //   481: aload_0        
        //   482: getfield        org/lwjgl/util/glu/Quadric.textureFlag:Z
        //   485: ifne            1050
        //   488: bipush          6
        //   490: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   493: fconst_0       
        //   494: fconst_0       
        //   495: ldc             -1.0
        //   497: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   500: fconst_0       
        //   501: fconst_0       
        //   502: fload_1        
        //   503: fneg           
        //   504: fload           20
        //   506: fmul           
        //   507: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   510: ldc             3.1415927
        //   512: fload           5
        //   514: fsub           
        //   515: fstore          4
        //   517: fconst_1       
        //   518: fstore          11
        //   520: iload_2        
        //   521: istore          16
        //   523: iconst_0       
        //   524: iload_2        
        //   525: if_icmpne       532
        //   528: fconst_0       
        //   529: goto            537
        //   532: iconst_0       
        //   533: i2f            
        //   534: fload           7
        //   536: fmul           
        //   537: fstore          6
        //   539: aload_0        
        //   540: fload           6
        //   542: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   545: fneg           
        //   546: aload_0        
        //   547: fload           4
        //   549: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   552: fmul           
        //   553: fstore          8
        //   555: aload_0        
        //   556: fload           6
        //   558: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   561: aload_0        
        //   562: fload           4
        //   564: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   567: fmul           
        //   568: fstore          9
        //   570: fload           20
        //   572: aload_0        
        //   573: fload           4
        //   575: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   578: fmul           
        //   579: fstore          10
        //   581: iload           19
        //   583: ifeq            604
        //   586: fload           8
        //   588: fload           20
        //   590: fmul           
        //   591: fload           9
        //   593: fload           20
        //   595: fmul           
        //   596: fload           10
        //   598: fload           20
        //   600: fmul           
        //   601: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   604: fload           11
        //   606: fload           13
        //   608: fsub           
        //   609: fstore          11
        //   611: fload           8
        //   613: fload_1        
        //   614: fmul           
        //   615: fload           9
        //   617: fload_1        
        //   618: fmul           
        //   619: fload           10
        //   621: fload_1        
        //   622: fmul           
        //   623: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   626: iinc            16, -1
        //   629: goto            523
        //   632: goto            1050
        //   635: aload_0        
        //   636: getfield        org/lwjgl/util/glu/Quadric.drawStyle:I
        //   639: ldc             100011
        //   641: if_icmpeq       653
        //   644: aload_0        
        //   645: getfield        org/lwjgl/util/glu/Quadric.drawStyle:I
        //   648: ldc             100013
        //   650: if_icmpne       885
        //   653: iconst_1       
        //   654: iload_3        
        //   655: if_icmpge       769
        //   658: iconst_1       
        //   659: i2f            
        //   660: fload           5
        //   662: fmul           
        //   663: fstore          4
        //   665: iconst_2       
        //   666: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   669: iconst_0       
        //   670: iload_2        
        //   671: if_icmpge       763
        //   674: iconst_0       
        //   675: i2f            
        //   676: fload           7
        //   678: fmul           
        //   679: fstore          6
        //   681: aload_0        
        //   682: fload           6
        //   684: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   687: aload_0        
        //   688: fload           4
        //   690: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   693: fmul           
        //   694: fstore          8
        //   696: aload_0        
        //   697: fload           6
        //   699: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   702: aload_0        
        //   703: fload           4
        //   705: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   708: fmul           
        //   709: fstore          9
        //   711: aload_0        
        //   712: fload           4
        //   714: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   717: fstore          10
        //   719: iload           19
        //   721: ifeq            742
        //   724: fload           8
        //   726: fload           20
        //   728: fmul           
        //   729: fload           9
        //   731: fload           20
        //   733: fmul           
        //   734: fload           10
        //   736: fload           20
        //   738: fmul           
        //   739: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   742: fload           8
        //   744: fload_1        
        //   745: fmul           
        //   746: fload           9
        //   748: fload_1        
        //   749: fmul           
        //   750: fload           10
        //   752: fload_1        
        //   753: fmul           
        //   754: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   757: iinc            16, 1
        //   760: goto            669
        //   763: iinc            15, 1
        //   766: goto            653
        //   769: iconst_0       
        //   770: iload_2        
        //   771: if_icmpge       1050
        //   774: iconst_0       
        //   775: i2f            
        //   776: fload           7
        //   778: fmul           
        //   779: fstore          6
        //   781: iconst_3       
        //   782: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   785: iconst_1       
        //   786: iload_3        
        //   787: if_icmpgt       879
        //   790: iconst_1       
        //   791: i2f            
        //   792: fload           5
        //   794: fmul           
        //   795: fstore          4
        //   797: aload_0        
        //   798: fload           6
        //   800: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   803: aload_0        
        //   804: fload           4
        //   806: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   809: fmul           
        //   810: fstore          8
        //   812: aload_0        
        //   813: fload           6
        //   815: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   818: aload_0        
        //   819: fload           4
        //   821: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   824: fmul           
        //   825: fstore          9
        //   827: aload_0        
        //   828: fload           4
        //   830: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   833: fstore          10
        //   835: iload           19
        //   837: ifeq            858
        //   840: fload           8
        //   842: fload           20
        //   844: fmul           
        //   845: fload           9
        //   847: fload           20
        //   849: fmul           
        //   850: fload           10
        //   852: fload           20
        //   854: fmul           
        //   855: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   858: fload           8
        //   860: fload_1        
        //   861: fmul           
        //   862: fload           9
        //   864: fload_1        
        //   865: fmul           
        //   866: fload           10
        //   868: fload_1        
        //   869: fmul           
        //   870: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   873: iinc            15, 1
        //   876: goto            785
        //   879: iinc            16, 1
        //   882: goto            769
        //   885: aload_0        
        //   886: getfield        org/lwjgl/util/glu/Quadric.drawStyle:I
        //   889: ldc             100010
        //   891: if_icmpne       1050
        //   894: iconst_0       
        //   895: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   898: iload           19
        //   900: ifeq            910
        //   903: fconst_0       
        //   904: fconst_0       
        //   905: fload           20
        //   907: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   910: fconst_0       
        //   911: fconst_0       
        //   912: fload_1        
        //   913: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   916: iload           19
        //   918: ifeq            929
        //   921: fconst_0       
        //   922: fconst_0       
        //   923: fload           20
        //   925: fneg           
        //   926: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   929: fconst_0       
        //   930: fconst_0       
        //   931: fload_1        
        //   932: fneg           
        //   933: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //   936: iconst_1       
        //   937: iload_3        
        //   938: iconst_1       
        //   939: isub           
        //   940: if_icmpge       1050
        //   943: iconst_1       
        //   944: i2f            
        //   945: fload           5
        //   947: fmul           
        //   948: fstore          4
        //   950: iconst_0       
        //   951: iload_2        
        //   952: if_icmpge       1044
        //   955: iconst_0       
        //   956: i2f            
        //   957: fload           7
        //   959: fmul           
        //   960: fstore          6
        //   962: aload_0        
        //   963: fload           6
        //   965: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   968: aload_0        
        //   969: fload           4
        //   971: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   974: fmul           
        //   975: fstore          8
        //   977: aload_0        
        //   978: fload           6
        //   980: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   983: aload_0        
        //   984: fload           4
        //   986: invokevirtual   org/lwjgl/util/glu/Sphere.sin:(F)F
        //   989: fmul           
        //   990: fstore          9
        //   992: aload_0        
        //   993: fload           4
        //   995: invokevirtual   org/lwjgl/util/glu/Sphere.cos:(F)F
        //   998: fstore          10
        //  1000: iload           19
        //  1002: ifeq            1023
        //  1005: fload           8
        //  1007: fload           20
        //  1009: fmul           
        //  1010: fload           9
        //  1012: fload           20
        //  1014: fmul           
        //  1015: fload           10
        //  1017: fload           20
        //  1019: fmul           
        //  1020: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //  1023: fload           8
        //  1025: fload_1        
        //  1026: fmul           
        //  1027: fload           9
        //  1029: fload_1        
        //  1030: fmul           
        //  1031: fload           10
        //  1033: fload_1        
        //  1034: fmul           
        //  1035: invokestatic    org/lwjgl/opengl/GL11.glVertex3f:(FFF)V
        //  1038: iinc            16, 1
        //  1041: goto            950
        //  1044: iinc            15, 1
        //  1047: goto            936
        //  1050: return         
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
}
