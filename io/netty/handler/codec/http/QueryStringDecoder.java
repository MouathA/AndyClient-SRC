package io.netty.handler.codec.http;

import java.nio.charset.*;
import java.net.*;
import java.util.*;

public class QueryStringDecoder
{
    private static final int DEFAULT_MAX_PARAMS = 1024;
    private final Charset charset;
    private final String uri;
    private final boolean hasPath;
    private final int maxParams;
    private String path;
    private Map params;
    private int nParams;
    
    public QueryStringDecoder(final String s) {
        this(s, HttpConstants.DEFAULT_CHARSET);
    }
    
    public QueryStringDecoder(final String s, final boolean b) {
        this(s, HttpConstants.DEFAULT_CHARSET, b);
    }
    
    public QueryStringDecoder(final String s, final Charset charset) {
        this(s, charset, true);
    }
    
    public QueryStringDecoder(final String s, final Charset charset, final boolean b) {
        this(s, charset, b, 1024);
    }
    
    public QueryStringDecoder(final String uri, final Charset charset, final boolean hasPath, final int maxParams) {
        if (uri == null) {
            throw new NullPointerException("getUri");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        if (maxParams <= 0) {
            throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
        }
        this.uri = uri;
        this.charset = charset;
        this.maxParams = maxParams;
        this.hasPath = hasPath;
    }
    
    public QueryStringDecoder(final URI uri) {
        this(uri, HttpConstants.DEFAULT_CHARSET);
    }
    
    public QueryStringDecoder(final URI uri, final Charset charset) {
        this(uri, charset, 1024);
    }
    
    public QueryStringDecoder(final URI uri, final Charset charset, final int maxParams) {
        if (uri == null) {
            throw new NullPointerException("getUri");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        if (maxParams <= 0) {
            throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
        }
        String rawPath = uri.getRawPath();
        if (rawPath != null) {
            this.hasPath = true;
        }
        else {
            rawPath = "";
            this.hasPath = false;
        }
        this.uri = rawPath + '?' + uri.getRawQuery();
        this.charset = charset;
        this.maxParams = maxParams;
    }
    
    public String path() {
        if (this.path == null) {
            if (!this.hasPath) {
                return this.path = "";
            }
            final int index = this.uri.indexOf(63);
            if (index >= 0) {
                return this.path = this.uri.substring(0, index);
            }
            this.path = this.uri;
        }
        return this.path;
    }
    
    public Map parameters() {
        if (this.params == null) {
            if (this.hasPath) {
                final int length = this.path().length();
                if (this.uri.length() == length) {
                    return Collections.emptyMap();
                }
                this.decodeParams(this.uri.substring(length + 1));
            }
            else {
                if (this.uri.isEmpty()) {
                    return Collections.emptyMap();
                }
                this.decodeParams(this.uri);
            }
        }
        return this.params;
    }
    
    private void decodeParams(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: new             Ljava/util/LinkedHashMap;
        //     4: dup            
        //     5: invokespecial   java/util/LinkedHashMap.<init>:()V
        //     8: dup_x1         
        //     9: putfield        io/netty/handler/codec/http/QueryStringDecoder.params:Ljava/util/Map;
        //    12: astore_2       
        //    13: aload_0        
        //    14: iconst_0       
        //    15: putfield        io/netty/handler/codec/http/QueryStringDecoder.nParams:I
        //    18: aconst_null    
        //    19: astore_3       
        //    20: iconst_0       
        //    21: aload_1        
        //    22: invokevirtual   java/lang/String.length:()I
        //    25: if_icmpge       140
        //    28: aload_1        
        //    29: iconst_0       
        //    30: invokevirtual   java/lang/String.charAt:(I)C
        //    33: istore          6
        //    35: iload           6
        //    37: bipush          61
        //    39: if_icmpne       66
        //    42: aload_3        
        //    43: ifnonnull       66
        //    46: goto            63
        //    49: aload_1        
        //    50: iconst_0       
        //    51: iconst_0       
        //    52: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    55: aload_0        
        //    56: getfield        io/netty/handler/codec/http/QueryStringDecoder.charset:Ljava/nio/charset/Charset;
        //    59: invokestatic    io/netty/handler/codec/http/QueryStringDecoder.decodeComponent:(Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //    62: astore_3       
        //    63: goto            134
        //    66: iload           6
        //    68: bipush          38
        //    70: if_icmpeq       80
        //    73: iload           6
        //    75: bipush          59
        //    77: if_icmpne       134
        //    80: aload_3        
        //    81: ifnonnull       108
        //    84: goto            108
        //    87: aload_0        
        //    88: aload_2        
        //    89: aload_1        
        //    90: iconst_0       
        //    91: iconst_0       
        //    92: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    95: aload_0        
        //    96: getfield        io/netty/handler/codec/http/QueryStringDecoder.charset:Ljava/nio/charset/Charset;
        //    99: invokestatic    io/netty/handler/codec/http/QueryStringDecoder.decodeComponent:(Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   102: ldc             ""
        //   104: if_icmplt       134
        //   107: return         
        //   108: aload_3        
        //   109: ifnull          134
        //   112: aload_0        
        //   113: aload_2        
        //   114: aload_3        
        //   115: aload_1        
        //   116: iconst_0       
        //   117: iconst_0       
        //   118: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   121: aload_0        
        //   122: getfield        io/netty/handler/codec/http/QueryStringDecoder.charset:Ljava/nio/charset/Charset;
        //   125: invokestatic    io/netty/handler/codec/http/QueryStringDecoder.decodeComponent:(Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   128: if_icmplt       132
        //   131: return         
        //   132: aconst_null    
        //   133: astore_3       
        //   134: iinc            5, 1
        //   137: goto            20
        //   140: goto            194
        //   143: aload_3        
        //   144: ifnonnull       171
        //   147: aload_0        
        //   148: aload_2        
        //   149: aload_1        
        //   150: iconst_0       
        //   151: iconst_0       
        //   152: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   155: aload_0        
        //   156: getfield        io/netty/handler/codec/http/QueryStringDecoder.charset:Ljava/nio/charset/Charset;
        //   159: invokestatic    io/netty/handler/codec/http/QueryStringDecoder.decodeComponent:(Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   162: ldc             ""
        //   164: invokespecial   io/netty/handler/codec/http/QueryStringDecoder.addParam:(Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;)Z
        //   167: pop            
        //   168: goto            207
        //   171: aload_0        
        //   172: aload_2        
        //   173: aload_3        
        //   174: aload_1        
        //   175: iconst_0       
        //   176: iconst_0       
        //   177: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   180: aload_0        
        //   181: getfield        io/netty/handler/codec/http/QueryStringDecoder.charset:Ljava/nio/charset/Charset;
        //   184: invokestatic    io/netty/handler/codec/http/QueryStringDecoder.decodeComponent:(Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   187: invokespecial   io/netty/handler/codec/http/QueryStringDecoder.addParam:(Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;)Z
        //   190: pop            
        //   191: goto            207
        //   194: aload_3        
        //   195: ifnull          207
        //   198: aload_0        
        //   199: aload_2        
        //   200: aload_3        
        //   201: ldc             ""
        //   203: invokespecial   io/netty/handler/codec/http/QueryStringDecoder.addParam:(Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;)Z
        //   206: pop            
        //   207: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0134 (coming from #0133).
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
    
    public static String decodeComponent(final String s) {
        return decodeComponent(s, HttpConstants.DEFAULT_CHARSET);
    }
    
    public static String decodeComponent(final String s, final Charset charset) {
        if (s == null) {
            return "";
        }
        final int length = s.length();
        int char1 = 0;
        while (0 < length) {
            char1 = s.charAt(0);
            int n = 0;
            ++n;
        }
        final byte[] array = new byte[length];
        while (0 < length) {
            char char2 = s.charAt(0);
            int n3 = 0;
            Label_0306: {
                switch (char2) {
                    case 43: {
                        final byte[] array2 = array;
                        final int n2 = 0;
                        ++char1;
                        array2[n2] = 32;
                        break Label_0306;
                    }
                    case 37: {
                        if (0 == length - 1) {
                            throw new IllegalArgumentException("unterminated escape sequence at end of string: " + s);
                        }
                        ++n3;
                        final char char3 = s.charAt(0);
                        if (char3 == '%') {
                            final byte[] array3 = array;
                            final int n4 = 0;
                            ++char1;
                            array3[n4] = 37;
                            break Label_0306;
                        }
                        if (0 == length - 1) {
                            throw new IllegalArgumentException("partial escape sequence at end of string: " + s);
                        }
                        final char decodeHexNibble = decodeHexNibble(char3);
                        ++n3;
                        final char decodeHexNibble2 = decodeHexNibble(s.charAt(0));
                        if (decodeHexNibble == '\uffff' || decodeHexNibble2 == '\uffff') {
                            throw new IllegalArgumentException("invalid escape sequence `%" + s.charAt(-1) + s.charAt(0) + "' at index " + -2 + " of: " + s);
                        }
                        char2 = (char)(decodeHexNibble * '\u0010' + decodeHexNibble2);
                        break;
                    }
                }
                final byte[] array4 = array;
                final int n5 = 0;
                ++char1;
                array4[n5] = (byte)char2;
            }
            ++n3;
        }
        return new String(array, 0, 0, charset);
    }
    
    private static char decodeHexNibble(final char c) {
        if ('0' <= c && c <= '9') {
            return (char)(c - '0');
        }
        if ('a' <= c && c <= 'f') {
            return (char)(c - 'a' + 10);
        }
        if ('A' <= c && c <= 'F') {
            return (char)(c - 'A' + 10);
        }
        return '\uffff';
    }
}
