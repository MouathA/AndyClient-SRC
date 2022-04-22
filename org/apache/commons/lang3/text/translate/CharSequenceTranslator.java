package org.apache.commons.lang3.text.translate;

import java.io.*;
import java.util.*;

public abstract class CharSequenceTranslator
{
    public abstract int translate(final CharSequence p0, final int p1, final Writer p2) throws IOException;
    
    public final String translate(final CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        final StringWriter stringWriter = new StringWriter(charSequence.length() * 2);
        this.translate(charSequence, stringWriter);
        return stringWriter.toString();
    }
    
    public final void translate(final CharSequence p0, final Writer p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: new             Ljava/lang/IllegalArgumentException;
        //     7: dup            
        //     8: ldc             "The Writer must not be null"
        //    10: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    13: athrow         
        //    14: aload_1        
        //    15: ifnonnull       19
        //    18: return         
        //    19: aload_1        
        //    20: invokeinterface java/lang/CharSequence.length:()I
        //    25: istore          4
        //    27: iconst_0       
        //    28: iload           4
        //    30: if_icmpge       98
        //    33: aload_0        
        //    34: aload_1        
        //    35: iconst_0       
        //    36: aload_2        
        //    37: invokevirtual   org/apache/commons/lang3/text/translate/CharSequenceTranslator.translate:(Ljava/lang/CharSequence;ILjava/io/Writer;)I
        //    40: istore          5
        //    42: iload           5
        //    44: ifne            72
        //    47: aload_1        
        //    48: iconst_0       
        //    49: invokestatic    java/lang/Character.codePointAt:(Ljava/lang/CharSequence;I)I
        //    52: invokestatic    java/lang/Character.toChars:(I)[C
        //    55: astore          6
        //    57: aload_2        
        //    58: aload           6
        //    60: invokevirtual   java/io/Writer.write:([C)V
        //    63: iconst_0       
        //    64: aload           6
        //    66: arraylength    
        //    67: iadd           
        //    68: istore_3       
        //    69: goto            27
        //    72: iconst_0       
        //    73: iload           5
        //    75: if_icmpge       95
        //    78: iconst_0       
        //    79: aload_1        
        //    80: iconst_0       
        //    81: invokestatic    java/lang/Character.codePointAt:(Ljava/lang/CharSequence;I)I
        //    84: invokestatic    java/lang/Character.charCount:(I)I
        //    87: iadd           
        //    88: istore_3       
        //    89: iinc            6, 1
        //    92: goto            72
        //    95: goto            27
        //    98: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public final CharSequenceTranslator with(final CharSequenceTranslator... array) {
        final CharSequenceTranslator[] array2 = new CharSequenceTranslator[array.length + 1];
        array2[0] = this;
        System.arraycopy(array, 0, array2, 1, array.length);
        return new AggregateTranslator(array2);
    }
    
    public static String hex(final int n) {
        return Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
    }
}
