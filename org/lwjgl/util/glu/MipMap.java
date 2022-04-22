package org.lwjgl.util.glu;

import java.nio.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class MipMap extends Util
{
    public static int gluBuild2DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        if (n3 < 1 || n4 < 1) {
            return 100901;
        }
        final int bytesPerPixel = Util.bytesPerPixel(n5, n6);
        if (bytesPerPixel == 0) {
            return 100900;
        }
        final int glGetIntegerv = Util.glGetIntegerv(3379);
        int nearestPower = Util.nearestPower(n3);
        if (nearestPower > glGetIntegerv) {
            nearestPower = glGetIntegerv;
        }
        int nearestPower2 = Util.nearestPower(n4);
        if (nearestPower2 > glGetIntegerv) {
            nearestPower2 = glGetIntegerv;
        }
        final PixelStoreState pixelStoreState = new PixelStoreState();
        GL11.glPixelStorei(3330, 0);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3331, 0);
        GL11.glPixelStorei(3332, 0);
        ByteBuffer byteBuffer2;
        if (nearestPower != n3 || nearestPower2 != n4) {
            byteBuffer2 = BufferUtils.createByteBuffer((nearestPower + 4) * nearestPower2 * bytesPerPixel);
            if (gluScaleImage(n5, n3, n4, n6, byteBuffer, nearestPower, nearestPower2, n6, byteBuffer2) != 0) {}
            GL11.glPixelStorei(3314, 0);
            GL11.glPixelStorei(3317, 1);
            GL11.glPixelStorei(3315, 0);
            GL11.glPixelStorei(3316, 0);
        }
        else {
            byteBuffer2 = byteBuffer;
        }
        ByteBuffer byteBuffer3 = null;
        ByteBuffer byteBuffer4 = null;
        while (!true) {
            if (byteBuffer2 != byteBuffer) {
                GL11.glPixelStorei(3314, 0);
                GL11.glPixelStorei(3317, 1);
                GL11.glPixelStorei(3315, 0);
                GL11.glPixelStorei(3316, 0);
            }
            GL11.glTexImage2D(n, 0, n2, nearestPower, nearestPower2, 0, n5, n6, byteBuffer2);
            if (nearestPower == 1 && nearestPower2 == 1) {
                break;
            }
            final int n7 = (nearestPower < 2) ? 1 : (nearestPower >> 1);
            final int n8 = (nearestPower2 < 2) ? 1 : (nearestPower2 >> 1);
            ByteBuffer byteBuffer5;
            if (byteBuffer3 == null) {
                byteBuffer3 = (byteBuffer5 = BufferUtils.createByteBuffer((n7 + 4) * n8 * bytesPerPixel));
            }
            else if (byteBuffer4 == null) {
                byteBuffer4 = (byteBuffer5 = BufferUtils.createByteBuffer((n7 + 4) * n8 * bytesPerPixel));
            }
            else {
                byteBuffer5 = byteBuffer4;
            }
            if (gluScaleImage(n5, nearestPower, nearestPower2, n6, byteBuffer2, n7, n8, n6, byteBuffer5) != 0) {}
            byteBuffer2 = byteBuffer5;
            if (byteBuffer4 != null) {
                byteBuffer4 = byteBuffer3;
            }
            nearestPower = n7;
            nearestPower2 = n8;
            int n9 = 0;
            ++n9;
        }
        pixelStoreState.save();
        return 0;
    }
    
    public static int gluScaleImage(final int p0, final int p1, final int p2, final int p3, final ByteBuffer p4, final int p5, final int p6, final int p7, final ByteBuffer p8) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/lwjgl/util/glu/MipMap.compPerPix:(I)I
        //     4: istore          9
        //     6: iload           9
        //     8: iconst_m1      
        //     9: if_icmpne       15
        //    12: ldc             100900
        //    14: ireturn        
        //    15: iload_1        
        //    16: iload_2        
        //    17: imul           
        //    18: iload           9
        //    20: imul           
        //    21: newarray        F
        //    23: astore          13
        //    25: iload           5
        //    27: iload           6
        //    29: imul           
        //    30: iload           9
        //    32: imul           
        //    33: newarray        F
        //    35: astore          14
        //    37: iload_3        
        //    38: lookupswitch {
        //             5121: 64
        //             5126: 67
        //          default: 70
        //        }
        //    64: goto            74
        //    67: goto            74
        //    70: sipush          1280
        //    73: ireturn        
        //    74: iload           7
        //    76: lookupswitch {
        //             5121: 104
        //             5126: 107
        //          default: 110
        //        }
        //   104: goto            114
        //   107: goto            114
        //   110: sipush          1280
        //   113: ireturn        
        //   114: new             Lorg/lwjgl/util/glu/PixelStoreState;
        //   117: dup            
        //   118: invokespecial   org/lwjgl/util/glu/PixelStoreState.<init>:()V
        //   121: astore          21
        //   123: aload           21
        //   125: getfield        org/lwjgl/util/glu/PixelStoreState.unpackRowLength:I
        //   128: ifle            141
        //   131: aload           21
        //   133: getfield        org/lwjgl/util/glu/PixelStoreState.unpackRowLength:I
        //   136: istore          20
        //   138: goto            144
        //   141: iload_1        
        //   142: istore          20
        //   144: iconst_4       
        //   145: aload           21
        //   147: getfield        org/lwjgl/util/glu/PixelStoreState.unpackAlignment:I
        //   150: if_icmplt       163
        //   153: iload           9
        //   155: iload           20
        //   157: imul           
        //   158: istore          19
        //   160: goto            188
        //   163: aload           21
        //   165: getfield        org/lwjgl/util/glu/PixelStoreState.unpackAlignment:I
        //   168: iconst_4       
        //   169: idiv           
        //   170: iload           9
        //   172: iload           20
        //   174: imul           
        //   175: iconst_4       
        //   176: imul           
        //   177: aload           21
        //   179: getfield        org/lwjgl/util/glu/PixelStoreState.unpackAlignment:I
        //   182: invokestatic    org/lwjgl/util/glu/MipMap.ceil:(II)I
        //   185: imul           
        //   186: istore          19
        //   188: iload_3        
        //   189: lookupswitch {
        //             5121: 216
        //             5126: 293
        //          default: 367
        //        }
        //   216: aload           4
        //   218: invokevirtual   java/nio/ByteBuffer.rewind:()Ljava/nio/Buffer;
        //   221: pop            
        //   222: iconst_0       
        //   223: iload_2        
        //   224: if_icmpge       370
        //   227: iconst_0       
        //   228: iload           19
        //   230: imul           
        //   231: aload           21
        //   233: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipRows:I
        //   236: iload           19
        //   238: imul           
        //   239: iadd           
        //   240: aload           21
        //   242: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipPixels:I
        //   245: iload           9
        //   247: imul           
        //   248: iadd           
        //   249: istore          22
        //   251: iconst_0       
        //   252: iload_1        
        //   253: iload           9
        //   255: imul           
        //   256: if_icmpge       287
        //   259: aload           13
        //   261: iconst_0       
        //   262: iinc            12, 1
        //   265: aload           4
        //   267: iload           22
        //   269: iinc            22, 1
        //   272: invokevirtual   java/nio/ByteBuffer.get:(I)B
        //   275: sipush          255
        //   278: iand           
        //   279: i2f            
        //   280: fastore        
        //   281: iinc            11, 1
        //   284: goto            251
        //   287: iinc            10, 1
        //   290: goto            222
        //   293: aload           4
        //   295: invokevirtual   java/nio/ByteBuffer.rewind:()Ljava/nio/Buffer;
        //   298: pop            
        //   299: iconst_0       
        //   300: iload_2        
        //   301: if_icmpge       370
        //   304: iconst_4       
        //   305: iconst_0       
        //   306: iload           19
        //   308: imul           
        //   309: aload           21
        //   311: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipRows:I
        //   314: iload           19
        //   316: imul           
        //   317: iadd           
        //   318: aload           21
        //   320: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipPixels:I
        //   323: iload           9
        //   325: imul           
        //   326: iadd           
        //   327: imul           
        //   328: istore          22
        //   330: iconst_0       
        //   331: iload_1        
        //   332: iload           9
        //   334: imul           
        //   335: if_icmpge       361
        //   338: aload           13
        //   340: iconst_0       
        //   341: iinc            12, 1
        //   344: aload           4
        //   346: iload           22
        //   348: invokevirtual   java/nio/ByteBuffer.getFloat:(I)F
        //   351: fastore        
        //   352: iinc            22, 4
        //   355: iinc            11, 1
        //   358: goto            330
        //   361: iinc            10, 1
        //   364: goto            299
        //   367: ldc             100900
        //   369: ireturn        
        //   370: iload_1        
        //   371: i2f            
        //   372: iload           5
        //   374: i2f            
        //   375: fdiv           
        //   376: fstore          15
        //   378: iload_2        
        //   379: i2f            
        //   380: iload           6
        //   382: i2f            
        //   383: fdiv           
        //   384: fstore          16
        //   386: iload           9
        //   388: newarray        F
        //   390: astore          22
        //   392: iconst_0       
        //   393: iload           6
        //   395: if_icmpge       622
        //   398: iconst_0       
        //   399: iload           5
        //   401: if_icmpge       616
        //   404: iconst_0       
        //   405: i2f            
        //   406: fload           15
        //   408: fmul           
        //   409: f2i            
        //   410: istore          27
        //   412: iconst_1       
        //   413: i2f            
        //   414: fload           15
        //   416: fmul           
        //   417: f2i            
        //   418: istore          28
        //   420: iconst_0       
        //   421: i2f            
        //   422: fload           16
        //   424: fmul           
        //   425: f2i            
        //   426: istore          29
        //   428: iconst_1       
        //   429: i2f            
        //   430: fload           16
        //   432: fmul           
        //   433: f2i            
        //   434: istore          30
        //   436: iconst_0       
        //   437: iload           9
        //   439: if_icmpge       453
        //   442: aload           22
        //   444: iconst_0       
        //   445: fconst_0       
        //   446: fastore        
        //   447: iinc            32, 1
        //   450: goto            436
        //   453: iload           27
        //   455: istore          32
        //   457: iconst_0       
        //   458: iload           28
        //   460: if_icmpge       526
        //   463: iload           29
        //   465: istore          33
        //   467: iload           33
        //   469: iload           30
        //   471: if_icmpge       520
        //   474: iload           33
        //   476: iload_1        
        //   477: imul           
        //   478: iconst_0       
        //   479: iadd           
        //   480: iload           9
        //   482: imul           
        //   483: istore          23
        //   485: iconst_0       
        //   486: iload           9
        //   488: if_icmpge       511
        //   491: aload           22
        //   493: iconst_0       
        //   494: dup2           
        //   495: faload         
        //   496: aload           13
        //   498: iload           23
        //   500: iconst_0       
        //   501: iadd           
        //   502: faload         
        //   503: fadd           
        //   504: fastore        
        //   505: iinc            34, 1
        //   508: goto            485
        //   511: iinc            31, 1
        //   514: iinc            33, 1
        //   517: goto            467
        //   520: iinc            32, 1
        //   523: goto            457
        //   526: iconst_0       
        //   527: iload           5
        //   529: imul           
        //   530: iconst_0       
        //   531: iadd           
        //   532: iload           9
        //   534: imul           
        //   535: istore          24
        //   537: iconst_0       
        //   538: ifne            583
        //   541: iload           29
        //   543: iload_1        
        //   544: imul           
        //   545: iload           27
        //   547: iadd           
        //   548: iload           9
        //   550: imul           
        //   551: istore          23
        //   553: iconst_0       
        //   554: iload           9
        //   556: if_icmpge       580
        //   559: aload           14
        //   561: iload           24
        //   563: iinc            24, 1
        //   566: aload           13
        //   568: iload           23
        //   570: iconst_0       
        //   571: iadd           
        //   572: faload         
        //   573: fastore        
        //   574: iinc            32, 1
        //   577: goto            553
        //   580: goto            610
        //   583: iconst_0       
        //   584: iload           9
        //   586: if_icmpge       610
        //   589: aload           14
        //   591: iload           24
        //   593: iinc            24, 1
        //   596: aload           22
        //   598: iconst_0       
        //   599: faload         
        //   600: iconst_0       
        //   601: i2f            
        //   602: fdiv           
        //   603: fastore        
        //   604: iinc            12, 1
        //   607: goto            583
        //   610: iinc            26, 1
        //   613: goto            398
        //   616: iinc            25, 1
        //   619: goto            392
        //   622: aload           21
        //   624: getfield        org/lwjgl/util/glu/PixelStoreState.packRowLength:I
        //   627: ifle            640
        //   630: aload           21
        //   632: getfield        org/lwjgl/util/glu/PixelStoreState.packRowLength:I
        //   635: istore          20
        //   637: goto            644
        //   640: iload           5
        //   642: istore          20
        //   644: iconst_4       
        //   645: aload           21
        //   647: getfield        org/lwjgl/util/glu/PixelStoreState.packAlignment:I
        //   650: if_icmplt       663
        //   653: iload           9
        //   655: iload           20
        //   657: imul           
        //   658: istore          19
        //   660: goto            688
        //   663: aload           21
        //   665: getfield        org/lwjgl/util/glu/PixelStoreState.packAlignment:I
        //   668: iconst_4       
        //   669: idiv           
        //   670: iload           9
        //   672: iload           20
        //   674: imul           
        //   675: iconst_4       
        //   676: imul           
        //   677: aload           21
        //   679: getfield        org/lwjgl/util/glu/PixelStoreState.packAlignment:I
        //   682: invokestatic    org/lwjgl/util/glu/MipMap.ceil:(II)I
        //   685: imul           
        //   686: istore          19
        //   688: iload           7
        //   690: lookupswitch {
        //             5121: 716
        //             5126: 786
        //          default: 856
        //        }
        //   716: iconst_0       
        //   717: iload           6
        //   719: if_icmpge       859
        //   722: iconst_0       
        //   723: iload           19
        //   725: imul           
        //   726: aload           21
        //   728: getfield        org/lwjgl/util/glu/PixelStoreState.packSkipRows:I
        //   731: iload           19
        //   733: imul           
        //   734: iadd           
        //   735: aload           21
        //   737: getfield        org/lwjgl/util/glu/PixelStoreState.packSkipPixels:I
        //   740: iload           9
        //   742: imul           
        //   743: iadd           
        //   744: istore          25
        //   746: iconst_0       
        //   747: iload           5
        //   749: iload           9
        //   751: imul           
        //   752: if_icmpge       780
        //   755: aload           8
        //   757: iconst_0       
        //   758: iinc            25, 1
        //   761: aload           14
        //   763: iconst_0       
        //   764: iinc            12, 1
        //   767: faload         
        //   768: f2i            
        //   769: i2b            
        //   770: invokevirtual   java/nio/ByteBuffer.put:(IB)Ljava/nio/ByteBuffer;
        //   773: pop            
        //   774: iinc            11, 1
        //   777: goto            746
        //   780: iinc            10, 1
        //   783: goto            716
        //   786: iconst_0       
        //   787: iload           6
        //   789: if_icmpge       859
        //   792: iconst_4       
        //   793: iconst_0       
        //   794: iload           19
        //   796: imul           
        //   797: aload           21
        //   799: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipRows:I
        //   802: iload           19
        //   804: imul           
        //   805: iadd           
        //   806: aload           21
        //   808: getfield        org/lwjgl/util/glu/PixelStoreState.unpackSkipPixels:I
        //   811: iload           9
        //   813: imul           
        //   814: iadd           
        //   815: imul           
        //   816: istore          25
        //   818: iconst_0       
        //   819: iload           5
        //   821: iload           9
        //   823: imul           
        //   824: if_icmpge       850
        //   827: aload           8
        //   829: iconst_0       
        //   830: aload           14
        //   832: iconst_0       
        //   833: iinc            12, 1
        //   836: faload         
        //   837: invokevirtual   java/nio/ByteBuffer.putFloat:(IF)Ljava/nio/ByteBuffer;
        //   840: pop            
        //   841: iinc            25, 4
        //   844: iinc            11, 1
        //   847: goto            818
        //   850: iinc            10, 1
        //   853: goto            786
        //   856: ldc             100900
        //   858: ireturn        
        //   859: iconst_0       
        //   860: ireturn        
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
