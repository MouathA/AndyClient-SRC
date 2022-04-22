package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;

import de.gerrygames.viarewind.storage.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class ChunkPacketTransformer
{
    private static byte[] transformChunkData(final byte[] array, final int n, final boolean b, final boolean b2) {
        final ByteBuf buffer = Unpooled.buffer();
        Unpooled.buffer();
        while ((n & 0x1) == 0x0) {
            int n2 = 0;
            ++n2;
        }
        while (true) {
            final short n3 = (short)((array[1] & 0xFF) << 8 | (array[0] & 0xFF));
            final int n4;
            n4 += 2;
            int n5 = BlockState.extractId(n3);
            int n6 = BlockState.extractData(n3);
            final Replacement replacement = ReplacementRegistry1_7_6_10to1_8.getReplacement(n5, n6);
            if (replacement != null) {
                n5 = replacement.getId();
                n6 = replacement.replaceData(n6);
            }
            buffer.writeByte(n5);
            final byte b3 = (byte)(n6 & 0xF);
            int n7 = 0;
            ++n7;
        }
    }
    
    private static int calcSize(final int n, final boolean b, final boolean b2) {
        return n * 2 * 16 * 16 * 16 + n * 16 * 16 * 16 / 2 + (b ? (n * 16 * 16 * 16 / 2) : 0) + (b2 ? 256 : 0);
    }
    
    public static void transformChunkBulk(final PacketWrapper p0) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
        //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //     9: checkcast       Ljava/lang/Boolean;
        //    12: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    15: istore_1       
        //    16: aload_0        
        //    17: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
        //    20: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //    25: checkcast       Ljava/lang/Integer;
        //    28: invokevirtual   java/lang/Integer.intValue:()I
        //    31: istore_2       
        //    32: iload_2        
        //    33: newarray        I
        //    35: astore_3       
        //    36: iload_2        
        //    37: newarray        I
        //    39: astore          4
        //    41: iload_2        
        //    42: newarray        I
        //    44: astore          5
        //    46: iload_2        
        //    47: anewarray       [B
        //    50: astore          6
        //    52: iconst_0       
        //    53: iload_2        
        //    54: if_icmpge       119
        //    57: aload_3        
        //    58: iconst_0       
        //    59: aload_0        
        //    60: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
        //    63: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //    68: checkcast       Ljava/lang/Integer;
        //    71: invokevirtual   java/lang/Integer.intValue:()I
        //    74: iastore        
        //    75: aload           4
        //    77: iconst_0       
        //    78: aload_0        
        //    79: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
        //    82: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //    87: checkcast       Ljava/lang/Integer;
        //    90: invokevirtual   java/lang/Integer.intValue:()I
        //    93: iastore        
        //    94: aload           5
        //    96: iconst_0       
        //    97: aload_0        
        //    98: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_SHORT:Lcom/viaversion/viaversion/api/type/types/UnsignedShortType;
        //   101: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //   106: checkcast       Ljava/lang/Integer;
        //   109: invokevirtual   java/lang/Integer.intValue:()I
        //   112: iastore        
        //   113: iinc            7, 1
        //   116: goto            52
        //   119: iconst_0       
        //   120: iload_2        
        //   121: if_icmpge       190
        //   124: aload           5
        //   126: iconst_0       
        //   127: iaload         
        //   128: invokestatic    java/lang/Integer.bitCount:(I)I
        //   131: iload_1        
        //   132: iconst_1       
        //   133: invokestatic    de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/chunks/ChunkPacketTransformer.calcSize:(IZZ)I
        //   136: istore          9
        //   138: new             Lcom/viaversion/viaversion/api/type/types/CustomByteType;
        //   141: dup            
        //   142: iconst_0       
        //   143: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   146: invokespecial   com/viaversion/viaversion/api/type/types/CustomByteType.<init>:(Ljava/lang/Integer;)V
        //   149: astore          10
        //   151: aload           6
        //   153: iconst_0       
        //   154: aload_0        
        //   155: aload           10
        //   157: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
        //   162: checkcast       [B
        //   165: aload           5
        //   167: iconst_0       
        //   168: iaload         
        //   169: iload_1        
        //   170: iconst_1       
        //   171: invokestatic    de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/chunks/ChunkPacketTransformer.transformChunkData:([BIZZ)[B
        //   174: aastore        
        //   175: iconst_0       
        //   176: aload           6
        //   178: iconst_0       
        //   179: aaload         
        //   180: arraylength    
        //   181: iadd           
        //   182: istore          7
        //   184: iinc            8, 1
        //   187: goto            119
        //   190: aload_0        
        //   191: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
        //   194: iload_2        
        //   195: i2s            
        //   196: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
        //   199: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   204: iconst_0       
        //   205: newarray        B
        //   207: astore          8
        //   209: iconst_0       
        //   210: iload_2        
        //   211: if_icmpge       245
        //   214: aload           6
        //   216: iconst_0       
        //   217: aaload         
        //   218: iconst_0       
        //   219: aload           8
        //   221: iconst_0       
        //   222: aload           6
        //   224: iconst_0       
        //   225: aaload         
        //   226: arraylength    
        //   227: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   230: iconst_0       
        //   231: aload           6
        //   233: iconst_0       
        //   234: aaload         
        //   235: arraylength    
        //   236: iadd           
        //   237: istore          9
        //   239: iinc            10, 1
        //   242: goto            209
        //   245: new             Ljava/util/zip/Deflater;
        //   248: dup            
        //   249: iconst_4       
        //   250: invokespecial   java/util/zip/Deflater.<init>:(I)V
        //   253: astore          10
        //   255: aload           10
        //   257: invokevirtual   java/util/zip/Deflater.reset:()V
        //   260: aload           10
        //   262: aload           8
        //   264: invokevirtual   java/util/zip/Deflater.setInput:([B)V
        //   267: aload           10
        //   269: invokevirtual   java/util/zip/Deflater.finish:()V
        //   272: aload           8
        //   274: arraylength    
        //   275: bipush          100
        //   277: iadd           
        //   278: newarray        B
        //   280: astore          11
        //   282: aload           10
        //   284: aload           11
        //   286: invokevirtual   java/util/zip/Deflater.deflate:([B)I
        //   289: istore          12
        //   291: iload           12
        //   293: newarray        B
        //   295: astore          13
        //   297: aload           11
        //   299: iconst_0       
        //   300: aload           13
        //   302: iconst_0       
        //   303: iload           12
        //   305: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   308: aload_0        
        //   309: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
        //   312: iload           12
        //   314: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   317: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   322: aload_0        
        //   323: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
        //   326: iload_1        
        //   327: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   330: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   335: new             Lcom/viaversion/viaversion/api/type/types/CustomByteType;
        //   338: dup            
        //   339: iload           12
        //   341: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   344: invokespecial   com/viaversion/viaversion/api/type/types/CustomByteType.<init>:(Ljava/lang/Integer;)V
        //   347: astore          14
        //   349: aload_0        
        //   350: aload           14
        //   352: aload           13
        //   354: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   359: iconst_0       
        //   360: iload_2        
        //   361: if_icmpge       431
        //   364: aload_0        
        //   365: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
        //   368: aload_3        
        //   369: iconst_0       
        //   370: iaload         
        //   371: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   374: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   379: aload_0        
        //   380: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
        //   383: aload           4
        //   385: iconst_0       
        //   386: iaload         
        //   387: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   390: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   395: aload_0        
        //   396: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
        //   399: aload           5
        //   401: iconst_0       
        //   402: iaload         
        //   403: i2s            
        //   404: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
        //   407: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   412: aload_0        
        //   413: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
        //   416: iconst_0       
        //   417: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
        //   420: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
        //   425: iinc            15, 1
        //   428: goto            359
        //   431: return         
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
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
