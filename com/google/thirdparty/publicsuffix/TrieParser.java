package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

@GwtCompatible
class TrieParser
{
    private static final Joiner PREFIX_JOINER;
    
    static ImmutableMap parseTrie(final CharSequence charSequence) {
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        final int length = charSequence.length();
        while (0 < length) {
            final int n = 0 + doParseTrieToBuilder(Lists.newLinkedList(), charSequence.subSequence(0, length), builder);
        }
        return builder.build();
    }
    
    private static int doParseTrieToBuilder(final List p0, final CharSequence p1, final ImmutableMap.Builder p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface java/lang/CharSequence.length:()I
        //     6: istore_3       
        //     7: iconst_0       
        //     8: iload_3        
        //     9: if_icmpge       33
        //    12: aload_1        
        //    13: iconst_0       
        //    14: invokeinterface java/lang/CharSequence.charAt:(I)C
        //    19: istore          5
        //    21: goto            27
        //    24: goto            33
        //    27: iinc            4, 1
        //    30: goto            7
        //    33: aload_0        
        //    34: iconst_0       
        //    35: aload_1        
        //    36: iconst_0       
        //    37: iconst_0       
        //    38: invokeinterface java/lang/CharSequence.subSequence:(II)Ljava/lang/CharSequence;
        //    43: invokestatic    com/google/thirdparty/publicsuffix/TrieParser.reverse:(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
        //    46: invokeinterface java/util/List.add:(ILjava/lang/Object;)V
        //    51: goto            82
        //    54: getstatic       com/google/thirdparty/publicsuffix/TrieParser.PREFIX_JOINER:Lcom/google/common/base/Joiner;
        //    57: aload_0        
        //    58: invokevirtual   com/google/common/base/Joiner.join:(Ljava/lang/Iterable;)Ljava/lang/String;
        //    61: astore          6
        //    63: aload           6
        //    65: invokevirtual   java/lang/String.length:()I
        //    68: ifle            82
        //    71: aload_2        
        //    72: aload           6
        //    74: iconst_0       
        //    75: invokestatic    com/google/thirdparty/publicsuffix/PublicSuffixType.fromCode:(C)Lcom/google/thirdparty/publicsuffix/PublicSuffixType;
        //    78: invokevirtual   com/google/common/collect/ImmutableMap$Builder.put:(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;
        //    81: pop            
        //    82: iinc            4, 1
        //    85: iconst_0       
        //    86: iload_3        
        //    87: if_icmpge       134
        //    90: iconst_0       
        //    91: aload_0        
        //    92: aload_1        
        //    93: iconst_0       
        //    94: iload_3        
        //    95: invokeinterface java/lang/CharSequence.subSequence:(II)Ljava/lang/CharSequence;
        //   100: aload_2        
        //   101: invokestatic    com/google/thirdparty/publicsuffix/TrieParser.doParseTrieToBuilder:(Ljava/util/List;Ljava/lang/CharSequence;Lcom/google/common/collect/ImmutableMap$Builder;)I
        //   104: iadd           
        //   105: istore          4
        //   107: aload_1        
        //   108: iconst_0       
        //   109: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   114: bipush          63
        //   116: if_icmpeq       131
        //   119: aload_1        
        //   120: iconst_0       
        //   121: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   126: bipush          44
        //   128: if_icmpne       85
        //   131: iinc            4, 1
        //   134: aload_0        
        //   135: iconst_0       
        //   136: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   141: pop            
        //   142: iconst_0       
        //   143: ireturn        
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
    
    private static CharSequence reverse(final CharSequence charSequence) {
        final int length = charSequence.length();
        if (length <= 1) {
            return charSequence;
        }
        final char[] array = new char[length];
        array[0] = charSequence.charAt(length - 1);
        while (1 < length) {
            array[1] = charSequence.charAt(length - 1 - 1);
            if (Character.isSurrogatePair(array[1], array[0])) {
                swap(array, 0, 1);
            }
            int n = 0;
            ++n;
        }
        return new String(array);
    }
    
    private static void swap(final char[] array, final int n, final int n2) {
        final char c = array[n];
        array[n] = array[n2];
        array[n2] = c;
    }
    
    static {
        PREFIX_JOINER = Joiner.on("");
    }
}
