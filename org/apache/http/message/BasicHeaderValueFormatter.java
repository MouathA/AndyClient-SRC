package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@Immutable
public class BasicHeaderValueFormatter implements HeaderValueFormatter
{
    @Deprecated
    public static final BasicHeaderValueFormatter DEFAULT;
    public static final BasicHeaderValueFormatter INSTANCE;
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";
    
    public static String formatElements(final HeaderElement[] array, final boolean b, final HeaderValueFormatter headerValueFormatter) {
        return ((headerValueFormatter != null) ? headerValueFormatter : BasicHeaderValueFormatter.INSTANCE).formatElements(null, array, b).toString();
    }
    
    public CharArrayBuffer formatElements(final CharArrayBuffer charArrayBuffer, final HeaderElement[] array, final boolean b) {
        Args.notNull(array, "Header element array");
        final int estimateElementsLen = this.estimateElementsLen(array);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(estimateElementsLen);
        }
        else {
            charArrayBuffer2.ensureCapacity(estimateElementsLen);
        }
        while (0 < array.length) {
            this.formatHeaderElement(charArrayBuffer2, array[0], b);
            int n = 0;
            ++n;
        }
        return charArrayBuffer2;
    }
    
    protected int estimateElementsLen(final HeaderElement[] array) {
        if (array == null || array.length < 1) {
            return 0;
        }
        int n = (array.length - 1) * 2;
        while (0 < array.length) {
            n += this.estimateHeaderElementLen(array[0]);
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static String formatHeaderElement(final HeaderElement headerElement, final boolean b, final HeaderValueFormatter headerValueFormatter) {
        return ((headerValueFormatter != null) ? headerValueFormatter : BasicHeaderValueFormatter.INSTANCE).formatHeaderElement(null, headerElement, b).toString();
    }
    
    public CharArrayBuffer formatHeaderElement(final CharArrayBuffer charArrayBuffer, final HeaderElement headerElement, final boolean b) {
        Args.notNull(headerElement, "Header element");
        final int estimateHeaderElementLen = this.estimateHeaderElementLen(headerElement);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(estimateHeaderElementLen);
        }
        else {
            charArrayBuffer2.ensureCapacity(estimateHeaderElementLen);
        }
        charArrayBuffer2.append(headerElement.getName());
        final String value = headerElement.getValue();
        if (value != null) {
            charArrayBuffer2.append('=');
            this.doFormatValue(charArrayBuffer2, value, b);
        }
        final int parameterCount = headerElement.getParameterCount();
        if (parameterCount > 0) {
            while (0 < parameterCount) {
                charArrayBuffer2.append("; ");
                this.formatNameValuePair(charArrayBuffer2, headerElement.getParameter(0), b);
                int n = 0;
                ++n;
            }
        }
        return charArrayBuffer2;
    }
    
    protected int estimateHeaderElementLen(final HeaderElement headerElement) {
        if (headerElement == null) {
            return 0;
        }
        int length = headerElement.getName().length();
        final String value = headerElement.getValue();
        if (value != null) {
            length += 3 + value.length();
        }
        final int parameterCount = headerElement.getParameterCount();
        if (parameterCount > 0) {
            while (0 < parameterCount) {
                length += 2 + this.estimateNameValuePairLen(headerElement.getParameter(0));
                int n = 0;
                ++n;
            }
        }
        return length;
    }
    
    public static String formatParameters(final NameValuePair[] array, final boolean b, final HeaderValueFormatter headerValueFormatter) {
        return ((headerValueFormatter != null) ? headerValueFormatter : BasicHeaderValueFormatter.INSTANCE).formatParameters(null, array, b).toString();
    }
    
    public CharArrayBuffer formatParameters(final CharArrayBuffer charArrayBuffer, final NameValuePair[] array, final boolean b) {
        Args.notNull(array, "Header parameter array");
        final int estimateParametersLen = this.estimateParametersLen(array);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(estimateParametersLen);
        }
        else {
            charArrayBuffer2.ensureCapacity(estimateParametersLen);
        }
        while (0 < array.length) {
            this.formatNameValuePair(charArrayBuffer2, array[0], b);
            int n = 0;
            ++n;
        }
        return charArrayBuffer2;
    }
    
    protected int estimateParametersLen(final NameValuePair[] array) {
        if (array == null || array.length < 1) {
            return 0;
        }
        int n = (array.length - 1) * 2;
        while (0 < array.length) {
            n += this.estimateNameValuePairLen(array[0]);
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static String formatNameValuePair(final NameValuePair nameValuePair, final boolean b, final HeaderValueFormatter headerValueFormatter) {
        return ((headerValueFormatter != null) ? headerValueFormatter : BasicHeaderValueFormatter.INSTANCE).formatNameValuePair(null, nameValuePair, b).toString();
    }
    
    public CharArrayBuffer formatNameValuePair(final CharArrayBuffer charArrayBuffer, final NameValuePair nameValuePair, final boolean b) {
        Args.notNull(nameValuePair, "Name / value pair");
        final int estimateNameValuePairLen = this.estimateNameValuePairLen(nameValuePair);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(estimateNameValuePairLen);
        }
        else {
            charArrayBuffer2.ensureCapacity(estimateNameValuePairLen);
        }
        charArrayBuffer2.append(nameValuePair.getName());
        final String value = nameValuePair.getValue();
        if (value != null) {
            charArrayBuffer2.append('=');
            this.doFormatValue(charArrayBuffer2, value, b);
        }
        return charArrayBuffer2;
    }
    
    protected int estimateNameValuePairLen(final NameValuePair nameValuePair) {
        if (nameValuePair == null) {
            return 0;
        }
        int length = nameValuePair.getName().length();
        final String value = nameValuePair.getValue();
        if (value != null) {
            length += 3 + value.length();
        }
        return length;
    }
    
    protected void doFormatValue(final CharArrayBuffer p0, final String p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          4
        //     3: iload           4
        //     5: ifne            38
        //     8: iconst_0       
        //     9: aload_2        
        //    10: invokevirtual   java/lang/String.length:()I
        //    13: if_icmpge       38
        //    16: iload           4
        //    18: ifne            38
        //    21: aload_0        
        //    22: aload_2        
        //    23: iconst_0       
        //    24: invokevirtual   java/lang/String.charAt:(I)C
        //    27: invokevirtual   org/apache/http/message/BasicHeaderValueFormatter.isSeparator:(C)Z
        //    30: istore          4
        //    32: iinc            5, 1
        //    35: goto            8
        //    38: iload           4
        //    40: ifeq            49
        //    43: aload_1        
        //    44: bipush          34
        //    46: invokevirtual   org/apache/http/util/CharArrayBuffer.append:(C)V
        //    49: iconst_0       
        //    50: aload_2        
        //    51: invokevirtual   java/lang/String.length:()I
        //    54: if_icmpge       88
        //    57: aload_2        
        //    58: iconst_0       
        //    59: invokevirtual   java/lang/String.charAt:(I)C
        //    62: istore          6
        //    64: aload_0        
        //    65: iload           6
        //    67: iflt            76
        //    70: aload_1        
        //    71: bipush          92
        //    73: invokevirtual   org/apache/http/util/CharArrayBuffer.append:(C)V
        //    76: aload_1        
        //    77: iload           6
        //    79: invokevirtual   org/apache/http/util/CharArrayBuffer.append:(C)V
        //    82: iinc            5, 1
        //    85: goto            49
        //    88: iload           4
        //    90: ifeq            99
        //    93: aload_1        
        //    94: bipush          34
        //    96: invokevirtual   org/apache/http/util/CharArrayBuffer.append:(C)V
        //    99: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0085).
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
    
    protected boolean isSeparator(final char c) {
        return " ;,:@()<>\\\"/[]?={}\t".indexOf(c) >= 0;
    }
    
    static {
        DEFAULT = new BasicHeaderValueFormatter();
        INSTANCE = new BasicHeaderValueFormatter();
    }
}
