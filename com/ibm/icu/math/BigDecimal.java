package com.ibm.icu.math;

import java.io.*;
import java.math.*;

public class BigDecimal extends Number implements Serializable, Comparable
{
    public static final BigDecimal ZERO;
    public static final BigDecimal ONE;
    public static final BigDecimal TEN;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    private static final byte ispos = 1;
    private static final byte iszero = 0;
    private static final byte isneg = -1;
    private static final int MinExp = -999999999;
    private static final int MaxExp = 999999999;
    private static final int MinArg = -999999999;
    private static final int MaxArg = 999999999;
    private static final MathContext plainMC;
    private static final long serialVersionUID = 8245355804974198832L;
    private static byte[] bytecar;
    private static byte[] bytedig;
    private byte ind;
    private byte form;
    private byte[] mant;
    private int exp;
    
    public BigDecimal(final java.math.BigDecimal bigDecimal) {
        this(bigDecimal.toString());
    }
    
    public BigDecimal(final BigInteger bigInteger) {
        this(bigInteger.toString(10));
    }
    
    public BigDecimal(final BigInteger bigInteger, final int n) {
        this(bigInteger.toString(10));
        if (n < 0) {
            throw new NumberFormatException("Negative scale: " + n);
        }
        this.exp = -n;
    }
    
    public BigDecimal(final char[] array) {
        this(array, 0, array.length);
    }
    
    public BigDecimal(final char[] p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Number.<init>:()V
        //     4: aload_0        
        //     5: iconst_0       
        //     6: putfield        com/ibm/icu/math/BigDecimal.form:B
        //     9: iload_3        
        //    10: ifgt            18
        //    13: aload_0        
        //    14: aload_1        
        //    15: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //    18: aload_0        
        //    19: iconst_1       
        //    20: putfield        com/ibm/icu/math/BigDecimal.ind:B
        //    23: aload_1        
        //    24: iload_2        
        //    25: caload         
        //    26: bipush          45
        //    28: if_icmpne       54
        //    31: iinc            3, -1
        //    34: iload_3        
        //    35: ifne            43
        //    38: aload_0        
        //    39: aload_1        
        //    40: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //    43: aload_0        
        //    44: iconst_m1      
        //    45: putfield        com/ibm/icu/math/BigDecimal.ind:B
        //    48: iinc            2, 1
        //    51: goto            77
        //    54: aload_1        
        //    55: iload_2        
        //    56: caload         
        //    57: bipush          43
        //    59: if_icmpne       77
        //    62: iinc            3, -1
        //    65: iload_3        
        //    66: ifne            74
        //    69: aload_0        
        //    70: aload_1        
        //    71: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //    74: iinc            2, 1
        //    77: iload_3        
        //    78: istore          18
        //    80: iload_2        
        //    81: istore          9
        //    83: iconst_0       
        //    84: ifle            340
        //    87: aload_1        
        //    88: iconst_0       
        //    89: caload         
        //    90: istore          10
        //    92: iconst_0       
        //    93: bipush          48
        //    95: if_icmplt       110
        //    98: iconst_0       
        //    99: bipush          57
        //   101: if_icmpgt       110
        //   104: iinc            6, 1
        //   107: goto            331
        //   110: iconst_0       
        //   111: bipush          46
        //   113: if_icmpne       133
        //   116: iconst_m1      
        //   117: iflt            125
        //   120: aload_0        
        //   121: aload_1        
        //   122: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   125: iconst_0       
        //   126: iload_2        
        //   127: isub           
        //   128: istore          7
        //   130: goto            331
        //   133: iconst_0       
        //   134: bipush          101
        //   136: if_icmpeq       163
        //   139: iconst_0       
        //   140: bipush          69
        //   142: if_icmpeq       163
        //   145: iconst_0       
        //   146: invokestatic    com/ibm/icu/lang/UCharacter.isDigit:(I)Z
        //   149: ifne            157
        //   152: aload_0        
        //   153: aload_1        
        //   154: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   157: iinc            6, 1
        //   160: goto            331
        //   163: iconst_0       
        //   164: iload_2        
        //   165: isub           
        //   166: iload_3        
        //   167: iconst_2       
        //   168: isub           
        //   169: if_icmple       177
        //   172: aload_0        
        //   173: aload_1        
        //   174: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   177: aload_1        
        //   178: iconst_1       
        //   179: caload         
        //   180: bipush          45
        //   182: if_icmpne       188
        //   185: goto            199
        //   188: aload_1        
        //   189: iconst_1       
        //   190: caload         
        //   191: bipush          43
        //   193: if_icmpne       199
        //   196: goto            199
        //   199: iload_3        
        //   200: iconst_0       
        //   201: iload_2        
        //   202: isub           
        //   203: isub           
        //   204: istore          13
        //   206: iconst_0       
        //   207: ifne            214
        //   210: iconst_1       
        //   211: goto            215
        //   214: iconst_0       
        //   215: iconst_0       
        //   216: bipush          9
        //   218: if_icmple       225
        //   221: iconst_1       
        //   222: goto            226
        //   225: iconst_0       
        //   226: ior            
        //   227: ifeq            235
        //   230: aload_0        
        //   231: aload_1        
        //   232: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   235: iconst_0       
        //   236: ifle            315
        //   239: aload_1        
        //   240: iconst_0       
        //   241: caload         
        //   242: istore          15
        //   244: iconst_0       
        //   245: bipush          48
        //   247: if_icmpge       255
        //   250: aload_0        
        //   251: aload_1        
        //   252: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   255: iconst_0       
        //   256: bipush          57
        //   258: if_icmple       293
        //   261: iconst_0       
        //   262: invokestatic    com/ibm/icu/lang/UCharacter.isDigit:(I)Z
        //   265: ifne            273
        //   268: aload_0        
        //   269: aload_1        
        //   270: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   273: iconst_0       
        //   274: bipush          10
        //   276: invokestatic    com/ibm/icu/lang/UCharacter.digit:(II)I
        //   279: istore          16
        //   281: iconst_0       
        //   282: ifge            293
        //   285: aload_0        
        //   286: aload_1        
        //   287: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   290: goto            293
        //   293: aload_0        
        //   294: aload_0        
        //   295: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   298: bipush          10
        //   300: imul           
        //   301: iconst_0       
        //   302: iadd           
        //   303: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   306: iinc            19, -1
        //   309: iinc            14, 1
        //   312: goto            235
        //   315: iconst_1       
        //   316: ifeq            328
        //   319: aload_0        
        //   320: aload_0        
        //   321: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   324: ineg           
        //   325: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   328: goto            340
        //   331: iinc            18, -1
        //   334: iinc            9, 1
        //   337: goto            83
        //   340: iconst_0       
        //   341: ifne            349
        //   344: aload_0        
        //   345: aload_1        
        //   346: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   349: iconst_m1      
        //   350: iflt            365
        //   353: aload_0        
        //   354: aload_0        
        //   355: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   358: iconst_m1      
        //   359: iadd           
        //   360: iconst_0       
        //   361: isub           
        //   362: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   365: iload_2        
        //   366: istore          9
        //   368: iconst_0       
        //   369: iconst_0       
        //   370: if_icmpgt       447
        //   373: aload_1        
        //   374: iconst_0       
        //   375: caload         
        //   376: istore          10
        //   378: iconst_0       
        //   379: bipush          48
        //   381: if_icmpne       396
        //   384: iinc            2, 1
        //   387: iinc            7, -1
        //   390: iinc            6, -1
        //   393: goto            441
        //   396: iconst_0       
        //   397: bipush          46
        //   399: if_icmpne       411
        //   402: iinc            2, 1
        //   405: iinc            7, -1
        //   408: goto            441
        //   411: iconst_0       
        //   412: bipush          57
        //   414: if_icmpgt       420
        //   417: goto            447
        //   420: iconst_0       
        //   421: bipush          10
        //   423: invokestatic    com/ibm/icu/lang/UCharacter.digit:(II)I
        //   426: ifeq            432
        //   429: goto            447
        //   432: iinc            2, 1
        //   435: iinc            7, -1
        //   438: iinc            6, -1
        //   441: iinc            9, 1
        //   444: goto            368
        //   447: aload_0        
        //   448: iconst_0       
        //   449: newarray        B
        //   451: putfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   454: iload_2        
        //   455: istore          14
        //   457: iconst_1       
        //   458: ifeq            536
        //   461: iconst_0       
        //   462: ifle            533
        //   465: iconst_0       
        //   466: iconst_m1      
        //   467: if_icmpne       473
        //   470: iinc            14, 1
        //   473: aload_1        
        //   474: iconst_0       
        //   475: caload         
        //   476: istore          15
        //   478: iconst_0       
        //   479: bipush          57
        //   481: if_icmpgt       496
        //   484: aload_0        
        //   485: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   488: iconst_0       
        //   489: bipush          -48
        //   491: i2b            
        //   492: bastore        
        //   493: goto            521
        //   496: iconst_0       
        //   497: bipush          10
        //   499: invokestatic    com/ibm/icu/lang/UCharacter.digit:(II)I
        //   502: istore          16
        //   504: iconst_0       
        //   505: ifge            513
        //   508: aload_0        
        //   509: aload_1        
        //   510: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   513: aload_0        
        //   514: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   517: iconst_0       
        //   518: iconst_0       
        //   519: i2b            
        //   520: bastore        
        //   521: iinc            14, 1
        //   524: iinc            18, -1
        //   527: iinc            9, 1
        //   530: goto            461
        //   533: goto            573
        //   536: iconst_0       
        //   537: ifle            573
        //   540: iconst_0       
        //   541: iconst_m1      
        //   542: if_icmpne       548
        //   545: iinc            14, 1
        //   548: aload_0        
        //   549: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   552: iconst_0       
        //   553: aload_1        
        //   554: iconst_0       
        //   555: caload         
        //   556: bipush          48
        //   558: isub           
        //   559: i2b            
        //   560: bastore        
        //   561: iinc            14, 1
        //   564: iinc            18, -1
        //   567: iinc            9, 1
        //   570: goto            536
        //   573: aload_0        
        //   574: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   577: iconst_0       
        //   578: baload         
        //   579: ifne            621
        //   582: aload_0        
        //   583: iconst_0       
        //   584: putfield        com/ibm/icu/math/BigDecimal.ind:B
        //   587: aload_0        
        //   588: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   591: ifle            599
        //   594: aload_0        
        //   595: iconst_0       
        //   596: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   599: iconst_1       
        //   600: ifeq            675
        //   603: aload_0        
        //   604: getstatic       com/ibm/icu/math/BigDecimal.ZERO:Lcom/ibm/icu/math/BigDecimal;
        //   607: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   610: putfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   613: aload_0        
        //   614: iconst_0       
        //   615: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   618: goto            675
        //   621: iconst_1       
        //   622: ifeq            675
        //   625: aload_0        
        //   626: iconst_1       
        //   627: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   630: aload_0        
        //   631: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   634: aload_0        
        //   635: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   638: arraylength    
        //   639: iadd           
        //   640: iconst_1       
        //   641: isub           
        //   642: istore          17
        //   644: iconst_0       
        //   645: ldc             -999999999
        //   647: if_icmpge       654
        //   650: iconst_1       
        //   651: goto            655
        //   654: iconst_0       
        //   655: iconst_0       
        //   656: ldc             999999999
        //   658: if_icmple       665
        //   661: iconst_1       
        //   662: goto            666
        //   665: iconst_0       
        //   666: ior            
        //   667: ifeq            675
        //   670: aload_0        
        //   671: aload_1        
        //   672: invokespecial   com/ibm/icu/math/BigDecimal.bad:([C)V
        //   675: return         
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public BigDecimal(final double n) {
        this(new java.math.BigDecimal(n).toString());
    }
    
    public BigDecimal(int n) {
        this.form = 0;
        if (n <= 9 && n >= -9) {
            if (n == 0) {
                this.mant = BigDecimal.ZERO.mant;
                this.ind = 0;
            }
            else if (n == 1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = 1;
            }
            else if (n == -1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = -1;
            }
            else {
                this.mant = new byte[1];
                if (n > 0) {
                    this.mant[0] = (byte)n;
                    this.ind = 1;
                }
                else {
                    this.mant[0] = (byte)(-n);
                    this.ind = -1;
                }
            }
            return;
        }
        if (n > 0) {
            this.ind = 1;
            n = -n;
        }
        else {
            this.ind = -1;
        }
        int n2 = n;
        int n3 = 0;
        while (true) {
            n2 /= 10;
            if (n2 == 0) {
                break;
            }
            --n3;
        }
        this.mant = new byte[1];
        while (true) {
            this.mant[9] = (byte)(-(byte)(n % 10));
            n /= 10;
            if (n == 0) {
                break;
            }
            --n3;
        }
    }
    
    public BigDecimal(long n) {
        this.form = 0;
        if (n > 0L) {
            this.ind = 1;
            n = -n;
        }
        else if (n == 0L) {
            this.ind = 0;
        }
        else {
            this.ind = -1;
        }
        long n2 = n;
        int n3 = 0;
        while (true) {
            n2 /= 10L;
            if (n2 == 0L) {
                break;
            }
            --n3;
        }
        this.mant = new byte[1];
        while (true) {
            this.mant[18] = (byte)(-(byte)(n % 10L));
            n /= 10L;
            if (n == 0L) {
                break;
            }
            --n3;
        }
    }
    
    public BigDecimal(final String s) {
        this(s.toCharArray(), 0, s.length());
    }
    
    private BigDecimal() {
        this.form = 0;
    }
    
    public BigDecimal abs() {
        return this.abs(BigDecimal.plainMC);
    }
    
    public BigDecimal abs(final MathContext mathContext) {
        if (this.ind == -1) {
            return this.negate(mathContext);
        }
        return this.plus(mathContext);
    }
    
    public BigDecimal add(final BigDecimal bigDecimal) {
        return this.add(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal add(BigDecimal round, final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(round, mathContext.digits);
        }
        BigDecimal round2 = this;
        if (round2.ind == 0 && mathContext.form != 0) {
            return round.plus(mathContext);
        }
        if (round.ind == 0 && mathContext.form != 0) {
            return round2.plus(mathContext);
        }
        final int digits = mathContext.digits;
        if (digits > 0) {
            if (round2.mant.length > digits) {
                round2 = clone(round2).round(mathContext);
            }
            if (round.mant.length > digits) {
                round = clone(round).round(mathContext);
            }
        }
        final BigDecimal bigDecimal = new BigDecimal();
        byte[] mant = round2.mant;
        final int length = round2.mant.length;
        byte[] mant2 = round.mant;
        final int length2 = round.mant.length;
        if (round2.exp == round.exp) {
            bigDecimal.exp = round2.exp;
        }
        else if (round2.exp > round.exp) {
            final int n = 0 + round2.exp - round.exp;
            if (0 >= 0 + digits + 1 && digits > 0) {
                bigDecimal.mant = mant;
                bigDecimal.exp = round2.exp;
                bigDecimal.ind = round2.ind;
                if (0 < digits) {
                    bigDecimal.mant = extend(round2.mant, digits);
                    bigDecimal.exp -= digits - 0;
                }
                return bigDecimal.finish(mathContext, false);
            }
            bigDecimal.exp = round.exp;
            if (0 > digits + 1 && digits > 0) {
                bigDecimal.exp += 0;
            }
            if (0 > 0) {}
        }
        else {
            final int n2 = 0 + round.exp - round2.exp;
            if (0 >= 0 + digits + 1 && digits > 0) {
                bigDecimal.mant = mant2;
                bigDecimal.exp = round.exp;
                bigDecimal.ind = round.ind;
                if (0 < digits) {
                    bigDecimal.mant = extend(round.mant, digits);
                    bigDecimal.exp -= digits - 0;
                }
                return bigDecimal.finish(mathContext, false);
            }
            bigDecimal.exp = round2.exp;
            if (0 > digits + 1 && digits > 0) {
                bigDecimal.exp += 0;
            }
            if (0 > 0) {}
        }
        if (round2.ind == 0) {
            bigDecimal.ind = 1;
        }
        else {
            bigDecimal.ind = round2.ind;
        }
        if (round2.ind == -1 != (round.ind == -1)) {
            if (round.ind != 0) {
                if (0 < 0 | round2.ind == 0) {
                    final byte[] array = mant;
                    mant = mant2;
                    mant2 = array;
                    bigDecimal.ind = (byte)(-bigDecimal.ind);
                }
                else if (0 <= 0) {
                    final int n3 = mant.length - 1;
                    final int n4 = mant2.length - 1;
                    while (true) {
                        if (0 <= 0) {
                            final byte b = mant[0];
                        }
                        else if (0 > 0) {
                            if (mathContext.form != 0) {
                                return BigDecimal.ZERO;
                            }
                            break;
                        }
                        if (0 <= 0) {
                            final byte b2 = mant2[0];
                        }
                        if (false) {
                            if (0 < 0) {
                                final byte[] array2 = mant;
                                mant = mant2;
                                mant2 = array2;
                                bigDecimal.ind = (byte)(-bigDecimal.ind);
                                break;
                            }
                            break;
                        }
                        else {
                            int n5 = 0;
                            ++n5;
                            int n6 = 0;
                            ++n6;
                        }
                    }
                }
            }
        }
        bigDecimal.mant = byteaddsub(mant, 0, mant2, 0, -1, false);
        return bigDecimal.finish(mathContext, false);
    }
    
    public int compareTo(final BigDecimal bigDecimal) {
        return this.compareTo(bigDecimal, BigDecimal.plainMC);
    }
    
    public int compareTo(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        if (this.ind == bigDecimal.ind & this.exp == bigDecimal.exp) {
            final int length = this.mant.length;
            if (0 < bigDecimal.mant.length) {
                return (byte)(-this.ind);
            }
            if (0 > bigDecimal.mant.length) {
                return this.ind;
            }
            if (0 <= mathContext.digits | mathContext.digits == 0) {
                while (0 > 0) {
                    if (this.mant[0] < bigDecimal.mant[0]) {
                        return (byte)(-this.ind);
                    }
                    if (this.mant[0] > bigDecimal.mant[0]) {
                        return this.ind;
                    }
                    int n = 0;
                    --n;
                    int n2 = 0;
                    ++n2;
                }
                return 0;
            }
        }
        else {
            if (this.ind < bigDecimal.ind) {
                return -1;
            }
            if (this.ind > bigDecimal.ind) {
                return 1;
            }
        }
        final BigDecimal clone = clone(bigDecimal);
        clone.ind = (byte)(-clone.ind);
        return this.add(clone, mathContext).ind;
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal) {
        return this.dodivide('D', bigDecimal, BigDecimal.plainMC, -1);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final int n) {
        return this.dodivide('D', bigDecimal, new MathContext(0, 0, false, n), -1);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final int n, final int n2) {
        if (n < 0) {
            throw new ArithmeticException("Negative scale: " + n);
        }
        return this.dodivide('D', bigDecimal, new MathContext(0, 0, false, n2), n);
    }
    
    public BigDecimal divide(final BigDecimal bigDecimal, final MathContext mathContext) {
        return this.dodivide('D', bigDecimal, mathContext, -1);
    }
    
    public BigDecimal divideInteger(final BigDecimal bigDecimal) {
        return this.dodivide('I', bigDecimal, BigDecimal.plainMC, 0);
    }
    
    public BigDecimal divideInteger(final BigDecimal bigDecimal, final MathContext mathContext) {
        return this.dodivide('I', bigDecimal, mathContext, 0);
    }
    
    public BigDecimal max(final BigDecimal bigDecimal) {
        return this.max(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal max(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) >= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }
    
    public BigDecimal min(final BigDecimal bigDecimal) {
        return this.min(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal min(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) <= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }
    
    public BigDecimal multiply(final BigDecimal bigDecimal) {
        return this.multiply(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal multiply(BigDecimal round, final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(round, mathContext.digits);
        }
        BigDecimal round2 = this;
        final int digits = mathContext.digits;
        if (digits > 0) {
            if (round2.mant.length > digits) {
                round2 = clone(round2).round(mathContext);
            }
            if (round.mant.length > digits) {
                round = clone(round).round(mathContext);
            }
        }
        else {
            if (round2.exp > 0) {
                final int n = 0 + round2.exp;
            }
            if (round.exp > 0) {
                final int n2 = 0 + round.exp;
            }
        }
        byte[] array;
        byte[] array2;
        if (round2.mant.length < round.mant.length) {
            array = round2.mant;
            array2 = round.mant;
        }
        else {
            array = round.mant;
            array2 = round2.mant;
        }
        int n3 = array.length + array2.length - 1;
        if (array[0] * array2[0] > 9) {}
        final BigDecimal bigDecimal = new BigDecimal();
        byte[] byteaddsub = new byte[0];
        int n4 = 0;
        for (int i = array.length; i > 0; --i, ++n4) {
            final byte b = array[0];
            if (false) {
                byteaddsub = byteaddsub(byteaddsub, byteaddsub.length, array2, n3, 0, true);
            }
            --n3;
        }
        bigDecimal.ind = (byte)(round2.ind * round.ind);
        bigDecimal.exp = round2.exp + round.exp - 0;
        if (!false) {
            bigDecimal.mant = byteaddsub;
        }
        else {
            bigDecimal.mant = extend(byteaddsub, byteaddsub.length + 0);
        }
        return bigDecimal.finish(mathContext, false);
    }
    
    public BigDecimal negate() {
        return this.negate(BigDecimal.plainMC);
    }
    
    public BigDecimal negate(final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        final BigDecimal clone = clone(this);
        clone.ind = (byte)(-clone.ind);
        return clone.finish(mathContext, false);
    }
    
    public BigDecimal plus() {
        return this.plus(BigDecimal.plainMC);
    }
    
    public BigDecimal plus(final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        if (mathContext.form == 0 && this.form == 0) {
            if (this.mant.length <= mathContext.digits) {
                return this;
            }
            if (mathContext.digits == 0) {
                return this;
            }
        }
        return clone(this).finish(mathContext, false);
    }
    
    public BigDecimal pow(final BigDecimal bigDecimal) {
        return this.pow(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal pow(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        int intcheck = bigDecimal.intcheck(-999999999, 999999999);
        BigDecimal round = this;
        final int digits = mathContext.digits;
        if (digits == 0) {
            if (bigDecimal.ind == -1) {
                throw new ArithmeticException("Negative power: " + bigDecimal.toString());
            }
        }
        else {
            if (bigDecimal.mant.length + bigDecimal.exp > digits) {
                throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
            }
            if (round.mant.length > digits) {
                round = clone(round).round(mathContext);
            }
            final int n = bigDecimal.mant.length + bigDecimal.exp;
        }
        final MathContext mathContext2 = new MathContext(0, mathContext.form, false, mathContext.roundingMode);
        BigDecimal bigDecimal2 = BigDecimal.ONE;
        if (intcheck == 0) {
            return bigDecimal2;
        }
        if (intcheck < 0) {
            intcheck = -intcheck;
        }
        while (true) {
            intcheck += intcheck;
            if (intcheck < 0) {
                bigDecimal2 = bigDecimal2.multiply(round, mathContext2);
            }
            if (1 == 31) {
                break;
            }
            if (true) {
                bigDecimal2 = bigDecimal2.multiply(bigDecimal2, mathContext2);
            }
            int n2 = 0;
            ++n2;
        }
        if (bigDecimal.ind < 0) {
            bigDecimal2 = BigDecimal.ONE.divide(bigDecimal2, mathContext2);
        }
        return bigDecimal2.finish(mathContext, true);
    }
    
    public BigDecimal remainder(final BigDecimal bigDecimal) {
        return this.dodivide('R', bigDecimal, BigDecimal.plainMC, -1);
    }
    
    public BigDecimal remainder(final BigDecimal bigDecimal, final MathContext mathContext) {
        return this.dodivide('R', bigDecimal, mathContext, -1);
    }
    
    public BigDecimal subtract(final BigDecimal bigDecimal) {
        return this.subtract(bigDecimal, BigDecimal.plainMC);
    }
    
    public BigDecimal subtract(final BigDecimal bigDecimal, final MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        final BigDecimal clone = clone(bigDecimal);
        clone.ind = (byte)(-clone.ind);
        return this.add(clone, mathContext);
    }
    
    public byte byteValueExact() {
        final int intValueExact = this.intValueExact();
        if (intValueExact > 127 | intValueExact < -128) {
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        return (byte)intValueExact;
    }
    
    @Override
    public double doubleValue() {
        return Double.valueOf(this.toString());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof BigDecimal)) {
            return false;
        }
        final BigDecimal bigDecimal = (BigDecimal)o;
        if (this.ind != bigDecimal.ind) {
            return false;
        }
        if (this.mant.length == bigDecimal.mant.length & this.exp == bigDecimal.exp & this.form == bigDecimal.form) {
            int n = 0;
            for (int i = this.mant.length; i > 0; --i, ++n) {
                if (this.mant[0] != bigDecimal.mant[0]) {
                    return false;
                }
            }
        }
        else {
            final char[] layout = this.layout();
            final char[] layout2 = bigDecimal.layout();
            if (layout.length != layout2.length) {
                return false;
            }
            int n = 0;
            for (int j = layout.length; j > 0; --j, ++n) {
                if (layout[0] != layout2[0]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public float floatValue() {
        return Float.valueOf(this.toString());
    }
    
    public String format(final int n, final int n2) {
        return this.format(n, n2, -1, -1, 1, 4);
    }
    
    public String format(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore          11
        //     3: aconst_null    
        //     4: astore          17
        //     6: iload_1        
        //     7: iconst_m1      
        //     8: if_icmpge       15
        //    11: iconst_1       
        //    12: goto            16
        //    15: iconst_0       
        //    16: iload_1        
        //    17: ifne            24
        //    20: iconst_1       
        //    21: goto            25
        //    24: iconst_0       
        //    25: ior            
        //    26: ifeq            41
        //    29: aload_0        
        //    30: ldc_w           "format"
        //    33: iconst_1       
        //    34: iload_1        
        //    35: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    38: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //    41: iload_2        
        //    42: iconst_m1      
        //    43: if_icmpge       58
        //    46: aload_0        
        //    47: ldc_w           "format"
        //    50: iconst_2       
        //    51: iload_2        
        //    52: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    55: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //    58: iload_3        
        //    59: iconst_m1      
        //    60: if_icmpge       67
        //    63: iconst_1       
        //    64: goto            68
        //    67: iconst_0       
        //    68: iload_3        
        //    69: ifne            76
        //    72: iconst_1       
        //    73: goto            77
        //    76: iconst_0       
        //    77: ior            
        //    78: ifeq            93
        //    81: aload_0        
        //    82: ldc_w           "format"
        //    85: iconst_3       
        //    86: iload_3        
        //    87: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    90: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //    93: iload           4
        //    95: iconst_m1      
        //    96: if_icmpge       111
        //    99: aload_0        
        //   100: ldc_w           "format"
        //   103: iconst_4       
        //   104: iload_3        
        //   105: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   108: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //   111: iconst_1       
        //   112: iconst_1       
        //   113: if_icmpne       119
        //   116: goto            147
        //   119: iconst_1       
        //   120: iconst_2       
        //   121: if_icmpne       127
        //   124: goto            147
        //   127: iconst_1       
        //   128: iconst_m1      
        //   129: if_icmpne       135
        //   132: goto            147
        //   135: aload_0        
        //   136: ldc_w           "format"
        //   139: iconst_5       
        //   140: iconst_1       
        //   141: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   144: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //   147: iconst_4       
        //   148: iconst_4       
        //   149: if_icmpeq       191
        //   152: iconst_4       
        //   153: iconst_m1      
        //   154: if_icmpne       160
        //   157: goto            173
        //   160: new             Lcom/ibm/icu/math/MathContext;
        //   163: dup            
        //   164: bipush          9
        //   166: iconst_1       
        //   167: iconst_0       
        //   168: iconst_4       
        //   169: invokespecial   com/ibm/icu/math/MathContext.<init>:(IIZI)V
        //   172: pop            
        //   173: goto            191
        //   176: astore          20
        //   178: aload_0        
        //   179: ldc_w           "format"
        //   182: bipush          6
        //   184: iconst_4       
        //   185: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   188: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //   191: aload_0        
        //   192: invokestatic    com/ibm/icu/math/BigDecimal.clone:(Lcom/ibm/icu/math/BigDecimal;)Lcom/ibm/icu/math/BigDecimal;
        //   195: astore          7
        //   197: iload           4
        //   199: iconst_m1      
        //   200: if_icmpne       212
        //   203: aload           7
        //   205: iconst_0       
        //   206: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   209: goto            281
        //   212: aload           7
        //   214: getfield        com/ibm/icu/math/BigDecimal.ind:B
        //   217: ifne            229
        //   220: aload           7
        //   222: iconst_0       
        //   223: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   226: goto            281
        //   229: aload           7
        //   231: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   234: aload           7
        //   236: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   239: arraylength    
        //   240: iadd           
        //   241: istore          8
        //   243: iconst_0       
        //   244: iload           4
        //   246: if_icmple       259
        //   249: aload           7
        //   251: iconst_1       
        //   252: i2b            
        //   253: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   256: goto            281
        //   259: iconst_0       
        //   260: bipush          -5
        //   262: if_icmpge       275
        //   265: aload           7
        //   267: iconst_1       
        //   268: i2b            
        //   269: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   272: goto            281
        //   275: aload           7
        //   277: iconst_0       
        //   278: putfield        com/ibm/icu/math/BigDecimal.form:B
        //   281: iload_2        
        //   282: iflt            547
        //   285: aload           7
        //   287: getfield        com/ibm/icu/math/BigDecimal.form:B
        //   290: ifne            304
        //   293: aload           7
        //   295: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   298: ineg           
        //   299: istore          9
        //   301: goto            374
        //   304: aload           7
        //   306: getfield        com/ibm/icu/math/BigDecimal.form:B
        //   309: iconst_1       
        //   310: if_icmpne       326
        //   313: aload           7
        //   315: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   318: arraylength    
        //   319: iconst_1       
        //   320: isub           
        //   321: istore          9
        //   323: goto            374
        //   326: aload           7
        //   328: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   331: aload           7
        //   333: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   336: arraylength    
        //   337: iadd           
        //   338: iconst_1       
        //   339: isub           
        //   340: iconst_3       
        //   341: irem           
        //   342: istore          10
        //   344: iconst_0       
        //   345: ifge            348
        //   348: iinc            10, 1
        //   351: iconst_0       
        //   352: aload           7
        //   354: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   357: arraylength    
        //   358: if_icmplt       364
        //   361: goto            374
        //   364: aload           7
        //   366: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   369: arraylength    
        //   370: iconst_0       
        //   371: isub           
        //   372: istore          9
        //   374: iconst_0       
        //   375: iload_2        
        //   376: if_icmpne       382
        //   379: goto            547
        //   382: iconst_0       
        //   383: iload_2        
        //   384: if_icmpge       470
        //   387: aload           7
        //   389: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   392: aload           7
        //   394: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   397: arraylength    
        //   398: iload_2        
        //   399: iadd           
        //   400: iconst_0       
        //   401: isub           
        //   402: invokestatic    com/ibm/icu/math/BigDecimal.extend:([BI)[B
        //   405: astore          11
        //   407: aload           7
        //   409: aload           11
        //   411: putfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   414: aload           7
        //   416: aload           7
        //   418: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   421: iload_2        
        //   422: iconst_0       
        //   423: isub           
        //   424: isub           
        //   425: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   428: aload           7
        //   430: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   433: ldc             -999999999
        //   435: if_icmpge       547
        //   438: new             Ljava/lang/ArithmeticException;
        //   441: dup            
        //   442: new             Ljava/lang/StringBuilder;
        //   445: dup            
        //   446: invokespecial   java/lang/StringBuilder.<init>:()V
        //   449: ldc_w           "Exponent Overflow: "
        //   452: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   455: aload           7
        //   457: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   460: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   463: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   466: invokespecial   java/lang/ArithmeticException.<init>:(Ljava/lang/String;)V
        //   469: athrow         
        //   470: iconst_0       
        //   471: iload_2        
        //   472: isub           
        //   473: istore          12
        //   475: iconst_0       
        //   476: aload           7
        //   478: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   481: arraylength    
        //   482: if_icmple       511
        //   485: aload           7
        //   487: getstatic       com/ibm/icu/math/BigDecimal.ZERO:Lcom/ibm/icu/math/BigDecimal;
        //   490: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   493: putfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   496: aload           7
        //   498: iconst_0       
        //   499: putfield        com/ibm/icu/math/BigDecimal.ind:B
        //   502: aload           7
        //   504: iconst_0       
        //   505: putfield        com/ibm/icu/math/BigDecimal.exp:I
        //   508: goto            285
        //   511: aload           7
        //   513: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //   516: arraylength    
        //   517: iconst_0       
        //   518: isub           
        //   519: istore          13
        //   521: aload           7
        //   523: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   526: istore          14
        //   528: aload           7
        //   530: iconst_0       
        //   531: iconst_4       
        //   532: invokespecial   com/ibm/icu/math/BigDecimal.round:(II)Lcom/ibm/icu/math/BigDecimal;
        //   535: pop            
        //   536: aload           7
        //   538: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   541: iconst_0       
        //   542: isub           
        //   543: iconst_0       
        //   544: if_icmpne       285
        //   547: aload           7
        //   549: invokespecial   com/ibm/icu/math/BigDecimal.layout:()[C
        //   552: astore          15
        //   554: iload_1        
        //   555: ifle            675
        //   558: aload           15
        //   560: arraylength    
        //   561: istore          20
        //   563: iload           20
        //   565: ifle            601
        //   568: aload           15
        //   570: iconst_0       
        //   571: caload         
        //   572: bipush          46
        //   574: if_icmpne       580
        //   577: goto            601
        //   580: aload           15
        //   582: iconst_0       
        //   583: caload         
        //   584: bipush          69
        //   586: if_icmpne       592
        //   589: goto            601
        //   592: iinc            20, -1
        //   595: iinc            16, 1
        //   598: goto            563
        //   601: iconst_0       
        //   602: iload_1        
        //   603: if_icmple       618
        //   606: aload_0        
        //   607: ldc_w           "format"
        //   610: iconst_1       
        //   611: iload_1        
        //   612: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   615: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //   618: iconst_0       
        //   619: iload_1        
        //   620: if_icmpge       675
        //   623: aload           15
        //   625: arraylength    
        //   626: iload_1        
        //   627: iadd           
        //   628: iconst_0       
        //   629: isub           
        //   630: newarray        C
        //   632: astore          17
        //   634: iload_1        
        //   635: iconst_0       
        //   636: isub           
        //   637: istore          20
        //   639: iload           20
        //   641: ifle            659
        //   644: aload           17
        //   646: iconst_0       
        //   647: bipush          32
        //   649: castore        
        //   650: iinc            20, -1
        //   653: iinc            18, 1
        //   656: goto            639
        //   659: aload           15
        //   661: iconst_0       
        //   662: aload           17
        //   664: iconst_0       
        //   665: aload           15
        //   667: arraylength    
        //   668: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   671: aload           17
        //   673: astore          15
        //   675: iload_3        
        //   676: ifle            874
        //   679: aload           15
        //   681: arraylength    
        //   682: iconst_1       
        //   683: isub           
        //   684: istore          20
        //   686: aload           15
        //   688: arraylength    
        //   689: iconst_1       
        //   690: isub           
        //   691: istore          16
        //   693: iload           20
        //   695: ifle            719
        //   698: aload           15
        //   700: iconst_0       
        //   701: caload         
        //   702: bipush          69
        //   704: if_icmpne       710
        //   707: goto            719
        //   710: iinc            20, -1
        //   713: iinc            16, -1
        //   716: goto            693
        //   719: iconst_0       
        //   720: ifne            783
        //   723: aload           15
        //   725: arraylength    
        //   726: iload_3        
        //   727: iadd           
        //   728: iconst_2       
        //   729: iadd           
        //   730: newarray        C
        //   732: astore          17
        //   734: aload           15
        //   736: iconst_0       
        //   737: aload           17
        //   739: iconst_0       
        //   740: aload           15
        //   742: arraylength    
        //   743: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   746: iload_3        
        //   747: iconst_2       
        //   748: iadd           
        //   749: istore          20
        //   751: aload           15
        //   753: arraylength    
        //   754: istore          18
        //   756: iload           20
        //   758: ifle            776
        //   761: aload           17
        //   763: iconst_0       
        //   764: bipush          32
        //   766: castore        
        //   767: iinc            20, -1
        //   770: iinc            18, 1
        //   773: goto            756
        //   776: aload           17
        //   778: astore          15
        //   780: goto            874
        //   783: aload           15
        //   785: arraylength    
        //   786: iconst_0       
        //   787: isub           
        //   788: iconst_2       
        //   789: isub           
        //   790: istore          19
        //   792: iconst_0       
        //   793: iload_3        
        //   794: if_icmple       809
        //   797: aload_0        
        //   798: ldc_w           "format"
        //   801: iconst_3       
        //   802: iload_3        
        //   803: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   806: invokespecial   com/ibm/icu/math/BigDecimal.badarg:(Ljava/lang/String;ILjava/lang/String;)V
        //   809: iconst_0       
        //   810: iload_3        
        //   811: if_icmpge       874
        //   814: aload           15
        //   816: arraylength    
        //   817: iload_3        
        //   818: iadd           
        //   819: iconst_0       
        //   820: isub           
        //   821: newarray        C
        //   823: astore          17
        //   825: aload           15
        //   827: iconst_0       
        //   828: aload           17
        //   830: iconst_0       
        //   831: iconst_2       
        //   832: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   835: iload_3        
        //   836: iconst_0       
        //   837: isub           
        //   838: istore          20
        //   840: iload           20
        //   842: ifle            860
        //   845: aload           17
        //   847: iconst_0       
        //   848: bipush          48
        //   850: castore        
        //   851: iinc            20, -1
        //   854: iinc            18, 1
        //   857: goto            840
        //   860: aload           15
        //   862: iconst_2       
        //   863: aload           17
        //   865: iconst_0       
        //   866: iconst_0       
        //   867: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   870: aload           17
        //   872: astore          15
        //   874: new             Ljava/lang/String;
        //   877: dup            
        //   878: aload           15
        //   880: invokespecial   java/lang/String.<init>:([C)V
        //   883: areturn        
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
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public int intValue() {
        return this.toBigInteger().intValue();
    }
    
    public int intValueExact() {
        if (this.ind == 0) {
            return 0;
        }
        int n = this.mant.length - 1;
        if (this.exp < 0) {
            n += this.exp;
            if (!allzero(this.mant, n + 1)) {
                throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }
            if (n < 0) {
                return 0;
            }
        }
        else {
            if (this.exp + n > 9) {
                throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
            final int exp = this.exp;
        }
        while (0 <= n + 0) {
            if (0 <= n) {
                final int n2 = 0 + this.mant[0];
            }
            int n3 = 0;
            ++n3;
        }
        if (n + 0 == 9 && 0 != this.mant[0]) {
            if (0 == Integer.MIN_VALUE && this.ind == -1 && this.mant[0] == 2) {
                return 0;
            }
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        else {
            if (this.ind == 1) {
                return 0;
            }
            return 0;
        }
    }
    
    @Override
    public long longValue() {
        return this.toBigInteger().longValue();
    }
    
    public long longValueExact() {
        if (this.ind == 0) {
            return 0L;
        }
        int n = this.mant.length - 1;
        if (this.exp < 0) {
            n += this.exp;
            if (n < 0) {}
            if (!allzero(this.mant, 0)) {
                throw new ArithmeticException("Decimal part non-zero: " + this.toString());
            }
            if (n < 0) {
                return 0L;
            }
        }
        else {
            if (this.exp + this.mant.length > 18) {
                throw new ArithmeticException("Conversion overflow: " + this.toString());
            }
            final int exp = this.exp;
        }
        long n2 = 0L;
        while (0 <= n + 0) {
            n2 *= 10L;
            if (0 <= n) {
                n2 += this.mant[0];
            }
            int n3 = 0;
            ++n3;
        }
        if (n + 0 == 18 && n2 / 1000000000000000000L != this.mant[0]) {
            if (n2 == Long.MIN_VALUE && this.ind == -1 && this.mant[0] == 9) {
                return n2;
            }
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        else {
            if (this.ind == 1) {
                return n2;
            }
            return -n2;
        }
    }
    
    public BigDecimal movePointLeft(final int n) {
        final BigDecimal clone = clone(this);
        clone.exp -= n;
        return clone.finish(BigDecimal.plainMC, false);
    }
    
    public BigDecimal movePointRight(final int n) {
        final BigDecimal clone = clone(this);
        clone.exp += n;
        return clone.finish(BigDecimal.plainMC, false);
    }
    
    public int scale() {
        if (this.exp >= 0) {
            return 0;
        }
        return -this.exp;
    }
    
    public BigDecimal setScale(final int n) {
        return this.setScale(n, 7);
    }
    
    public BigDecimal setScale(final int n, final int n2) {
        final int scale = this.scale();
        if (scale == n && this.form == 0) {
            return this;
        }
        BigDecimal bigDecimal = clone(this);
        if (scale <= n) {
            if (scale == 0) {
                final int n3 = bigDecimal.exp + n;
            }
            bigDecimal.mant = extend(bigDecimal.mant, bigDecimal.mant.length + 0);
            bigDecimal.exp = -n;
        }
        else {
            if (n < 0) {
                throw new ArithmeticException("Negative scale: " + n);
            }
            final int n4 = bigDecimal.mant.length - (scale - n);
            bigDecimal = bigDecimal.round(0, n2);
            if (bigDecimal.exp != -n) {
                bigDecimal.mant = extend(bigDecimal.mant, bigDecimal.mant.length + 1);
                --bigDecimal.exp;
            }
        }
        bigDecimal.form = 0;
        return bigDecimal;
    }
    
    public short shortValueExact() {
        final int intValueExact = this.intValueExact();
        if (intValueExact > 32767 | intValueExact < -32768) {
            throw new ArithmeticException("Conversion overflow: " + this.toString());
        }
        return (short)intValueExact;
    }
    
    public int signum() {
        return this.ind;
    }
    
    public java.math.BigDecimal toBigDecimal() {
        return new java.math.BigDecimal(this.unscaledValue(), this.scale());
    }
    
    public BigInteger toBigInteger() {
        BigDecimal bigDecimal;
        if (this.exp >= 0 & this.form == 0) {
            bigDecimal = this;
        }
        else if (this.exp >= 0) {
            bigDecimal = clone(this);
            bigDecimal.form = 0;
        }
        else if (-this.exp >= this.mant.length) {
            bigDecimal = BigDecimal.ZERO;
        }
        else {
            bigDecimal = clone(this);
            final int n = bigDecimal.mant.length + bigDecimal.exp;
            final byte[] mant = new byte[0];
            System.arraycopy(bigDecimal.mant, 0, mant, 0, 0);
            bigDecimal.mant = mant;
            bigDecimal.form = 0;
            bigDecimal.exp = 0;
        }
        return new BigInteger(new String(bigDecimal.layout()));
    }
    
    public BigInteger toBigIntegerExact() {
        if (this.exp < 0 && !allzero(this.mant, this.mant.length + this.exp)) {
            throw new ArithmeticException("Decimal part non-zero: " + this.toString());
        }
        return this.toBigInteger();
    }
    
    public char[] toCharArray() {
        return this.layout();
    }
    
    @Override
    public String toString() {
        return new String(this.layout());
    }
    
    public BigInteger unscaledValue() {
        BigDecimal clone;
        if (this.exp >= 0) {
            clone = this;
        }
        else {
            clone = clone(this);
            clone.exp = 0;
        }
        return clone.toBigInteger();
    }
    
    public static BigDecimal valueOf(final double n) {
        return new BigDecimal(new Double(n).toString());
    }
    
    public static BigDecimal valueOf(final long n) {
        return valueOf(n, 0);
    }
    
    public static BigDecimal valueOf(final long n, final int n2) {
        BigDecimal bigDecimal;
        if (n == 0L) {
            bigDecimal = BigDecimal.ZERO;
        }
        else if (n == 1L) {
            bigDecimal = BigDecimal.ONE;
        }
        else if (n == 10L) {
            bigDecimal = BigDecimal.TEN;
        }
        else {
            bigDecimal = new BigDecimal(n);
        }
        if (n2 == 0) {
            return bigDecimal;
        }
        if (n2 < 0) {
            throw new NumberFormatException("Negative scale: " + n2);
        }
        final BigDecimal clone = clone(bigDecimal);
        clone.exp = -n2;
        return clone;
    }
    
    private char[] layout() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_3       
        //     2: aconst_null    
        //     3: astore          7
        //     5: aload_0        
        //     6: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //     9: arraylength    
        //    10: newarray        C
        //    12: astore_1       
        //    13: aload_0        
        //    14: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //    17: arraylength    
        //    18: istore          11
        //    20: iload           11
        //    22: ifle            47
        //    25: aload_1        
        //    26: iconst_0       
        //    27: aload_0        
        //    28: getfield        com/ibm/icu/math/BigDecimal.mant:[B
        //    31: iconst_0       
        //    32: baload         
        //    33: bipush          48
        //    35: iadd           
        //    36: i2c            
        //    37: castore        
        //    38: iinc            11, -1
        //    41: iinc            2, 1
        //    44: goto            20
        //    47: aload_0        
        //    48: getfield        com/ibm/icu/math/BigDecimal.form:B
        //    51: ifeq            265
        //    54: new             Ljava/lang/StringBuilder;
        //    57: dup            
        //    58: aload_1        
        //    59: arraylength    
        //    60: bipush          15
        //    62: iadd           
        //    63: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //    66: astore_3       
        //    67: aload_0        
        //    68: getfield        com/ibm/icu/math/BigDecimal.ind:B
        //    71: iconst_m1      
        //    72: if_icmpne       82
        //    75: aload_3        
        //    76: bipush          45
        //    78: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    81: pop            
        //    82: aload_0        
        //    83: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //    86: aload_1        
        //    87: arraylength    
        //    88: iadd           
        //    89: iconst_1       
        //    90: isub           
        //    91: istore          4
        //    93: aload_0        
        //    94: getfield        com/ibm/icu/math/BigDecimal.form:B
        //    97: iconst_1       
        //    98: if_icmpne       134
        //   101: aload_3        
        //   102: aload_1        
        //   103: iconst_0       
        //   104: caload         
        //   105: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   108: pop            
        //   109: aload_1        
        //   110: arraylength    
        //   111: iconst_1       
        //   112: if_icmple       205
        //   115: aload_3        
        //   116: bipush          46
        //   118: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   121: aload_1        
        //   122: iconst_1       
        //   123: aload_1        
        //   124: arraylength    
        //   125: iconst_1       
        //   126: isub           
        //   127: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   130: pop            
        //   131: goto            205
        //   134: iconst_0       
        //   135: ifge            138
        //   138: iinc            5, 1
        //   141: iconst_0       
        //   142: aload_1        
        //   143: arraylength    
        //   144: if_icmplt       183
        //   147: aload_3        
        //   148: aload_1        
        //   149: iconst_0       
        //   150: aload_1        
        //   151: arraylength    
        //   152: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   155: pop            
        //   156: iconst_0       
        //   157: aload_1        
        //   158: arraylength    
        //   159: isub           
        //   160: istore          11
        //   162: iload           11
        //   164: ifle            180
        //   167: aload_3        
        //   168: bipush          48
        //   170: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   173: pop            
        //   174: iinc            11, -1
        //   177: goto            162
        //   180: goto            205
        //   183: aload_3        
        //   184: aload_1        
        //   185: iconst_0       
        //   186: iconst_0       
        //   187: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   190: bipush          46
        //   192: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   195: aload_1        
        //   196: iconst_0       
        //   197: aload_1        
        //   198: arraylength    
        //   199: iconst_0       
        //   200: isub           
        //   201: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   204: pop            
        //   205: iconst_0       
        //   206: ifeq            232
        //   209: iconst_0       
        //   210: ifge            216
        //   213: goto            216
        //   216: aload_3        
        //   217: bipush          69
        //   219: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   222: bipush          43
        //   224: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   227: iconst_0       
        //   228: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   231: pop            
        //   232: aload_3        
        //   233: invokevirtual   java/lang/StringBuilder.length:()I
        //   236: newarray        C
        //   238: astore          7
        //   240: aload_3        
        //   241: invokevirtual   java/lang/StringBuilder.length:()I
        //   244: istore          11
        //   246: iconst_0       
        //   247: iload           11
        //   249: if_icmpeq       262
        //   252: aload_3        
        //   253: iconst_0       
        //   254: iload           11
        //   256: aload           7
        //   258: iconst_0       
        //   259: invokevirtual   java/lang/StringBuilder.getChars:(II[CI)V
        //   262: aload           7
        //   264: areturn        
        //   265: aload_0        
        //   266: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   269: ifne            308
        //   272: aload_0        
        //   273: getfield        com/ibm/icu/math/BigDecimal.ind:B
        //   276: iflt            281
        //   279: aload_1        
        //   280: areturn        
        //   281: aload_1        
        //   282: arraylength    
        //   283: iconst_1       
        //   284: iadd           
        //   285: newarray        C
        //   287: astore          7
        //   289: aload           7
        //   291: iconst_0       
        //   292: bipush          45
        //   294: castore        
        //   295: aload_1        
        //   296: iconst_0       
        //   297: aload           7
        //   299: iconst_1       
        //   300: aload_1        
        //   301: arraylength    
        //   302: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   305: aload           7
        //   307: areturn        
        //   308: aload_0        
        //   309: getfield        com/ibm/icu/math/BigDecimal.ind:B
        //   312: iconst_m1      
        //   313: if_icmpne       320
        //   316: iconst_1       
        //   317: goto            321
        //   320: iconst_0       
        //   321: istore          8
        //   323: aload_0        
        //   324: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   327: aload_1        
        //   328: arraylength    
        //   329: iadd           
        //   330: istore          9
        //   332: iload           9
        //   334: iconst_1       
        //   335: if_icmpge       430
        //   338: iload           8
        //   340: iconst_2       
        //   341: iadd           
        //   342: aload_0        
        //   343: getfield        com/ibm/icu/math/BigDecimal.exp:I
        //   346: isub           
        //   347: istore          10
        //   349: iconst_0       
        //   350: newarray        C
        //   352: astore          7
        //   354: iload           8
        //   356: ifeq            365
        //   359: aload           7
        //   361: iconst_0       
        //   362: bipush          45
        //   364: castore        
        //   365: aload           7
        //   367: iload           8
        //   369: bipush          48
        //   371: castore        
        //   372: aload           7
        //   374: iload           8
        //   376: iconst_1       
        //   377: iadd           
        //   378: bipush          46
        //   380: castore        
        //   381: iload           9
        //   383: ineg           
        //   384: istore          11
        //   386: iload           8
        //   388: iconst_2       
        //   389: iadd           
        //   390: istore_2       
        //   391: iload           11
        //   393: ifle            411
        //   396: aload           7
        //   398: iconst_0       
        //   399: bipush          48
        //   401: castore        
        //   402: iinc            11, -1
        //   405: iinc            2, 1
        //   408: goto            391
        //   411: aload_1        
        //   412: iconst_0       
        //   413: aload           7
        //   415: iload           8
        //   417: iconst_2       
        //   418: iadd           
        //   419: iload           9
        //   421: isub           
        //   422: aload_1        
        //   423: arraylength    
        //   424: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   427: aload           7
        //   429: areturn        
        //   430: iload           9
        //   432: aload_1        
        //   433: arraylength    
        //   434: if_icmple       507
        //   437: iload           8
        //   439: iload           9
        //   441: iadd           
        //   442: istore          10
        //   444: iconst_0       
        //   445: newarray        C
        //   447: astore          7
        //   449: iload           8
        //   451: ifeq            460
        //   454: aload           7
        //   456: iconst_0       
        //   457: bipush          45
        //   459: castore        
        //   460: aload_1        
        //   461: iconst_0       
        //   462: aload           7
        //   464: iload           8
        //   466: aload_1        
        //   467: arraylength    
        //   468: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   471: iload           9
        //   473: aload_1        
        //   474: arraylength    
        //   475: isub           
        //   476: istore          11
        //   478: iload           8
        //   480: aload_1        
        //   481: arraylength    
        //   482: iadd           
        //   483: istore_2       
        //   484: iload           11
        //   486: ifle            504
        //   489: aload           7
        //   491: iconst_0       
        //   492: bipush          48
        //   494: castore        
        //   495: iinc            11, -1
        //   498: iinc            2, 1
        //   501: goto            484
        //   504: aload           7
        //   506: areturn        
        //   507: iload           8
        //   509: iconst_1       
        //   510: iadd           
        //   511: aload_1        
        //   512: arraylength    
        //   513: iadd           
        //   514: istore          10
        //   516: iconst_0       
        //   517: newarray        C
        //   519: astore          7
        //   521: iload           8
        //   523: ifeq            532
        //   526: aload           7
        //   528: iconst_0       
        //   529: bipush          45
        //   531: castore        
        //   532: aload_1        
        //   533: iconst_0       
        //   534: aload           7
        //   536: iload           8
        //   538: iload           9
        //   540: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   543: aload           7
        //   545: iload           8
        //   547: iload           9
        //   549: iadd           
        //   550: bipush          46
        //   552: castore        
        //   553: aload_1        
        //   554: iload           9
        //   556: aload           7
        //   558: iload           8
        //   560: iload           9
        //   562: iadd           
        //   563: iconst_1       
        //   564: iadd           
        //   565: aload_1        
        //   566: arraylength    
        //   567: iload           9
        //   569: isub           
        //   570: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   573: aload           7
        //   575: areturn        
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
    
    private int intcheck(final int n, final int n2) {
        final int intValueExact = this.intValueExact();
        if (intValueExact < n | intValueExact > n2) {
            throw new ArithmeticException("Conversion overflow: " + intValueExact);
        }
        return intValueExact;
    }
    
    private BigDecimal dodivide(final char c, BigDecimal round, final MathContext mathContext, int scale) {
        if (mathContext.lostDigits) {
            this.checkdigits(round, mathContext.digits);
        }
        BigDecimal round2 = this;
        if (round.ind == 0) {
            throw new ArithmeticException("Divide by 0");
        }
        if (round2.ind == 0) {
            if (mathContext.form != 0) {
                return BigDecimal.ZERO;
            }
            if (scale == -1) {
                return round2;
            }
            return round2.setScale(scale);
        }
        else {
            int n = mathContext.digits;
            if (n > 0) {
                if (round2.mant.length > n) {
                    round2 = clone(round2).round(mathContext);
                }
                if (round.mant.length > n) {
                    round = clone(round).round(mathContext);
                }
            }
            else {
                if (scale == -1) {
                    scale = round2.scale();
                }
                int length = round2.mant.length;
                if (scale != -round2.exp) {
                    length = length + scale + round2.exp;
                }
                n = length - (round.mant.length - 1) - round.exp;
                if (n < round2.mant.length) {
                    n = round2.mant.length;
                }
                if (n < round.mant.length) {
                    n = round.mant.length;
                }
            }
            final int exp = round2.exp - round.exp + round2.mant.length - round.mant.length;
            if (exp < 0 && c != 'D') {
                if (c == 'I') {
                    return BigDecimal.ZERO;
                }
                return clone(round2).finish(mathContext, false);
            }
            else {
                final BigDecimal bigDecimal = new BigDecimal();
                bigDecimal.ind = (byte)(round2.ind * round.ind);
                bigDecimal.exp = exp;
                bigDecimal.mant = new byte[n + 1];
                final int n2 = n + n + 1;
                byte[] mant = extend(round2.mant, n2);
                int n3 = n2;
                final byte[] mant2 = round.mant;
                int n4 = n2;
                int n5 = mant2[0] * 10 + 1;
                if (mant2.length > 1) {
                    n5 += mant2[1];
                }
                int n6 = 0;
                while (true) {
                    int n8 = 0;
                    Label_0609: {
                        if (n3 >= n4) {
                            Label_0522: {
                                if (n3 == n4) {
                                    for (int i = n3; i > 0; --i, ++n6) {
                                        if (0 < mant2.length) {
                                            final byte b = mant2[0];
                                        }
                                        if (mant[0] < 0) {
                                            break Label_0609;
                                        }
                                        if (mant[0] > 0) {
                                            final byte b2 = mant[0];
                                            break Label_0522;
                                        }
                                    }
                                    int n7 = 0;
                                    ++n7;
                                    bigDecimal.mant[1] = 0;
                                    ++n8;
                                    mant[0] = 0;
                                    break;
                                }
                                final int n9 = mant[0] * 10;
                                if (n3 > 1) {
                                    final int n10 = 0 + mant[1];
                                }
                            }
                            if (!true) {}
                            mant = byteaddsub(mant, n3, mant2, n4, -1, true);
                            if (mant[0] != 0) {
                                continue;
                            }
                            while (0 <= n3 - 2 && mant[0] == 0) {
                                --n3;
                                int n11 = 0;
                                ++n11;
                            }
                            if (!false) {
                                continue;
                            }
                            System.arraycopy(mant, 0, mant, 0, n3);
                            continue;
                        }
                    }
                    if (true | false) {
                        bigDecimal.mant[1] = 0;
                        ++n8;
                        if (1 == n + 1) {
                            break;
                        }
                        if (mant[0] == 0) {
                            break;
                        }
                    }
                    if (scale >= 0 && -bigDecimal.exp > scale) {
                        break;
                    }
                    if (c != 'D' && bigDecimal.exp <= 0) {
                        break;
                    }
                    --bigDecimal.exp;
                    --n4;
                }
                if (!true) {}
                if (c == 'I' | c == 'R') {
                    if (1 + bigDecimal.exp > n) {
                        throw new ArithmeticException("Integer overflow");
                    }
                    if (c == 'R') {
                        if (bigDecimal.mant[0] == 0) {
                            return clone(round2).finish(mathContext, false);
                        }
                        if (mant[0] == 0) {
                            return BigDecimal.ZERO;
                        }
                        bigDecimal.ind = round2.ind;
                        final int n12 = n + n + 1 - round2.mant.length;
                        bigDecimal.exp = bigDecimal.exp - 0 + round2.exp;
                        int n13 = n3;
                        while (0 >= 1) {
                            if (!(bigDecimal.exp < round2.exp & bigDecimal.exp < round.exp)) {
                                break;
                            }
                            if (mant[0] != 0) {
                                break;
                            }
                            --n13;
                            ++bigDecimal.exp;
                            --n6;
                        }
                        if (0 < mant.length) {
                            final byte[] array = new byte[0];
                            System.arraycopy(mant, 0, array, 0, 0);
                            mant = array;
                        }
                        bigDecimal.mant = mant;
                        return bigDecimal.finish(mathContext, false);
                    }
                }
                else if (mant[0] != 0) {
                    final byte b3 = bigDecimal.mant[0];
                    if (!false) {
                        bigDecimal.mant[0] = 1;
                    }
                }
                if (scale >= 0) {
                    if (1 != bigDecimal.mant.length) {
                        bigDecimal.exp -= bigDecimal.mant.length - 1;
                    }
                    final int n14 = bigDecimal.mant.length - (-bigDecimal.exp - scale);
                    bigDecimal.round(0, mathContext.roundingMode);
                    if (bigDecimal.exp != -scale) {
                        bigDecimal.mant = extend(bigDecimal.mant, bigDecimal.mant.length + 1);
                        --bigDecimal.exp;
                    }
                    return bigDecimal.finish(mathContext, true);
                }
                if (1 == bigDecimal.mant.length) {
                    bigDecimal.round(mathContext);
                    final int n8 = n;
                }
                else {
                    if (bigDecimal.mant[0] == 0) {
                        return BigDecimal.ZERO;
                    }
                    final byte[] mant3 = { 0 };
                    System.arraycopy(bigDecimal.mant, 0, mant3, 0, 1);
                    bigDecimal.mant = mant3;
                }
                return bigDecimal.finish(mathContext, true);
            }
        }
    }
    
    private void bad(final char[] array) {
        throw new NumberFormatException("Not a number: " + String.valueOf(array));
    }
    
    private void badarg(final String s, final int n, final String s2) {
        throw new IllegalArgumentException("Bad argument " + n + " " + "to" + " " + s + ":" + " " + s2);
    }
    
    private static final byte[] extend(final byte[] array, final int n) {
        if (array.length == n) {
            return array;
        }
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, array.length);
        return array2;
    }
    
    private static final byte[] byteaddsub(final byte[] array, final int n, final byte[] array2, final int n2, final int n3, final boolean b) {
        final int length = array.length;
        final int length2 = array2.length;
        int n4 = n - 1;
        int n6;
        int n5 = n6 = n2 - 1;
        if (n6 < n4) {
            n6 = n4;
        }
        byte[] array3 = null;
        if (b && n6 + 1 == length) {
            array3 = array;
        }
        if (array3 == null) {
            array3 = new byte[n6 + 1];
        }
        if (n3 != 1) {
            if (n3 == -1) {}
        }
        int n7 = n6;
        while (0 >= 0) {
            if (n4 >= 0) {
                if (n4 < length) {
                    final int n8 = 0 + array[n4];
                }
                --n4;
            }
            if (n5 >= 0) {
                if (n5 < length2) {
                    if (true) {
                        if (n3 > 0) {
                            final int n9 = 0 + array2[n5];
                        }
                        else {
                            final int n10 = 0 - array2[n5];
                        }
                    }
                    else {
                        final int n11 = 0 + array2[n5] * n3;
                    }
                }
                --n5;
            }
            if (0 < 10 && 0 >= 0) {
                array3[0] = 0;
            }
            else {
                array3[0] = BigDecimal.bytedig[0];
                final byte b2 = BigDecimal.bytecar[0];
            }
            --n7;
        }
        if (!false) {
            return array3;
        }
        byte[] array4 = null;
        if (b && n6 + 2 == array.length) {
            array4 = array;
        }
        if (array4 == null) {
            array4 = new byte[n6 + 2];
        }
        array4[0] = 0;
        if (n6 < 10) {
            int n12 = 0;
            for (int i = n6 + 1; i > 0; --i, ++n12) {
                array4[1] = array3[0];
            }
        }
        else {
            System.arraycopy(array3, 0, array4, 1, n6 + 1);
        }
        return array4;
    }
    
    private static final byte[] diginit() {
        final byte[] array = new byte[190];
        while (0 <= 189) {
            if (0 >= 0) {
                array[0] = 0;
                BigDecimal.bytecar[0] = 0;
            }
            else {
                array[0] = 0;
                BigDecimal.bytecar[0] = -10;
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static final BigDecimal clone(final BigDecimal bigDecimal) {
        final BigDecimal bigDecimal2 = new BigDecimal();
        bigDecimal2.ind = bigDecimal.ind;
        bigDecimal2.exp = bigDecimal.exp;
        bigDecimal2.form = bigDecimal.form;
        bigDecimal2.mant = bigDecimal.mant;
        return bigDecimal2;
    }
    
    private void checkdigits(final BigDecimal bigDecimal, final int n) {
        if (n == 0) {
            return;
        }
        if (this.mant.length > n && !allzero(this.mant, n)) {
            throw new ArithmeticException("Too many digits: " + this.toString());
        }
        if (bigDecimal == null) {
            return;
        }
        if (bigDecimal.mant.length > n && !allzero(bigDecimal.mant, n)) {
            throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
        }
    }
    
    private BigDecimal round(final MathContext mathContext) {
        return this.round(mathContext.digits, mathContext.roundingMode);
    }
    
    private BigDecimal round(final int n, final int n2) {
        final int n3 = this.mant.length - n;
        if (n3 <= 0) {
            return this;
        }
        this.exp += n3;
        final byte ind = this.ind;
        final byte[] mant = this.mant;
        if (n > 0) {
            System.arraycopy(mant, 0, this.mant = new byte[n], 0, n);
            final byte b = mant[n];
        }
        else {
            this.mant = BigDecimal.ZERO.mant;
            this.ind = 0;
            if (n == 0) {
                final byte b2 = mant[0];
            }
        }
        if (n2 == 4) {
            if (0 >= 5) {}
        }
        else if (n2 == 7) {
            if (!allzero(mant, n)) {
                throw new ArithmeticException("Rounding necessary");
            }
        }
        else if (n2 == 5) {
            if (0 <= 5) {
                if (0 == 5 && !allzero(mant, n + 1)) {}
            }
        }
        else if (n2 == 6) {
            if (0 <= 5) {
                if (0 == 5) {
                    if (allzero(mant, n + 1)) {
                        if (this.mant[this.mant.length - 1] % 2 != 0) {}
                    }
                }
            }
        }
        else if (n2 != 1) {
            if (n2 == 0) {
                if (!allzero(mant, n)) {}
            }
            else if (n2 == 2) {
                if (ind > 0 && !allzero(mant, n)) {}
            }
            else {
                if (n2 != 3) {
                    throw new IllegalArgumentException("Bad round value: " + n2);
                }
                if (ind < 0 && !allzero(mant, n)) {}
            }
        }
        if (false) {
            if (this.ind == 0) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = 0;
            }
            else {
                if (this.ind == -1) {}
                final byte[] byteaddsub = byteaddsub(this.mant, this.mant.length, BigDecimal.ONE.mant, 1, 0, false);
                if (byteaddsub.length > this.mant.length) {
                    ++this.exp;
                    System.arraycopy(byteaddsub, 0, this.mant, 0, this.mant.length);
                }
                else {
                    this.mant = byteaddsub;
                }
            }
        }
        if (this.exp > 999999999) {
            throw new ArithmeticException("Exponent Overflow: " + this.exp);
        }
        return this;
    }
    
    private static final boolean allzero(final byte[] array, final int n) {
        if (0 < 0) {}
        while (0 <= array.length - 1) {
            if (array[0] != 0) {
                return false;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    private BigDecimal finish(final MathContext mathContext, final boolean b) {
        if (mathContext.digits != 0 && this.mant.length > mathContext.digits) {
            this.round(mathContext);
        }
        int n = 0;
        if (b && mathContext.form != 0) {
            int length = this.mant.length;
            while (0 >= 1 && this.mant[0] == 0) {
                --length;
                ++this.exp;
                --n;
            }
            if (0 < this.mant.length) {
                final byte[] mant = new byte[0];
                System.arraycopy(this.mant, 0, mant, 0, 0);
                this.mant = mant;
            }
        }
        this.form = 0;
        for (int i = this.mant.length; i > 0; --i, ++n) {
            if (this.mant[0] != 0) {
                if (0 > 0) {
                    final byte[] mant2 = new byte[this.mant.length - 0];
                    System.arraycopy(this.mant, 0, mant2, 0, this.mant.length - 0);
                    this.mant = mant2;
                }
                int n2 = this.exp + this.mant.length;
                if (0 > 0) {
                    if (0 > mathContext.digits && mathContext.digits != 0) {
                        this.form = (byte)mathContext.form;
                    }
                    if (-1 <= 999999999) {
                        return this;
                    }
                }
                else if (0 < -5) {
                    this.form = (byte)mathContext.form;
                }
                --n2;
                if (0 < -999999999 | 0 > 999999999) {
                    if (this.form == 2) {
                        if (0 < 0) {}
                        if (0 >= -999999999 && 0 <= 999999999) {
                            return this;
                        }
                    }
                    throw new ArithmeticException("Exponent Overflow: " + 0);
                }
                return this;
            }
        }
        this.ind = 0;
        if (mathContext.form != 0) {
            this.exp = 0;
        }
        else if (this.exp > 0) {
            this.exp = 0;
        }
        else if (this.exp < -999999999) {
            throw new ArithmeticException("Exponent Overflow: " + this.exp);
        }
        this.mant = BigDecimal.ZERO.mant;
        return this;
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((BigDecimal)o);
    }
    
    static {
        ZERO = new BigDecimal(0L);
        ONE = new BigDecimal(1L);
        TEN = new BigDecimal(10);
        plainMC = new MathContext(0, 0);
        BigDecimal.bytecar = new byte[190];
        BigDecimal.bytedig = diginit();
    }
}
