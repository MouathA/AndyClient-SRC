package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2965DomainAttributeHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        }
        if (s.trim().length() == 0) {
            throw new MalformedCookieException("Blank value for domain attribute");
        }
        String domain = s.toLowerCase(Locale.ENGLISH);
        if (!s.startsWith(".")) {
            domain = '.' + domain;
        }
        setCookie.setDomain(domain);
    }
    
    public void validate(final Cookie p0, final CookieOrigin p1) throws MalformedCookieException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Cookie"
        //     3: invokestatic    org/apache/http/util/Args.notNull:(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //     6: pop            
        //     7: aload_2        
        //     8: ldc             "Cookie origin"
        //    10: invokestatic    org/apache/http/util/Args.notNull:(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //    13: pop            
        //    14: aload_2        
        //    15: invokevirtual   org/apache/http/cookie/CookieOrigin.getHost:()Ljava/lang/String;
        //    18: getstatic       java/util/Locale.ENGLISH:Ljava/util/Locale;
        //    21: invokevirtual   java/lang/String.toLowerCase:(Ljava/util/Locale;)Ljava/lang/String;
        //    24: astore_3       
        //    25: aload_1        
        //    26: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //    31: ifnonnull       44
        //    34: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //    37: dup            
        //    38: ldc             "Invalid cookie state: domain not specified"
        //    40: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //    43: athrow         
        //    44: aload_1        
        //    45: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //    50: getstatic       java/util/Locale.ENGLISH:Ljava/util/Locale;
        //    53: invokevirtual   java/lang/String.toLowerCase:(Ljava/util/Locale;)Ljava/lang/String;
        //    56: astore          4
        //    58: aload_1        
        //    59: instanceof      Lorg/apache/http/cookie/ClientCookie;
        //    62: ifeq            327
        //    65: aload_1        
        //    66: checkcast       Lorg/apache/http/cookie/ClientCookie;
        //    69: ldc             "domain"
        //    71: invokeinterface org/apache/http/cookie/ClientCookie.containsAttribute:(Ljava/lang/String;)Z
        //    76: ifeq            327
        //    79: aload           4
        //    81: ldc             "."
        //    83: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //    86: ifne            126
        //    89: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //    92: dup            
        //    93: new             Ljava/lang/StringBuilder;
        //    96: dup            
        //    97: invokespecial   java/lang/StringBuilder.<init>:()V
        //   100: ldc             "Domain attribute \""
        //   102: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   105: aload_1        
        //   106: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: ldc             "\" violates RFC 2109: domain must start with a dot"
        //   116: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   119: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   122: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //   125: athrow         
        //   126: aload           4
        //   128: bipush          46
        //   130: iconst_1       
        //   131: invokevirtual   java/lang/String.indexOf:(II)I
        //   134: istore          5
        //   136: iload           5
        //   138: iflt            153
        //   141: iload           5
        //   143: aload           4
        //   145: invokevirtual   java/lang/String.length:()I
        //   148: iconst_1       
        //   149: isub           
        //   150: if_icmpne       205
        //   153: aload           4
        //   155: ldc             ".local"
        //   157: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   160: ifne            205
        //   163: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //   166: dup            
        //   167: new             Ljava/lang/StringBuilder;
        //   170: dup            
        //   171: invokespecial   java/lang/StringBuilder.<init>:()V
        //   174: ldc             "Domain attribute \""
        //   176: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   179: aload_1        
        //   180: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   185: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   188: ldc             "\" violates RFC 2965: the value contains no embedded dots "
        //   190: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   193: ldc             "and the value is not .local"
        //   195: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   198: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   201: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //   204: athrow         
        //   205: aload_0        
        //   206: aload_3        
        //   207: aload           4
        //   209: ifne            254
        //   212: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //   215: dup            
        //   216: new             Ljava/lang/StringBuilder;
        //   219: dup            
        //   220: invokespecial   java/lang/StringBuilder.<init>:()V
        //   223: ldc             "Domain attribute \""
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   228: aload_1        
        //   229: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   234: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   237: ldc             "\" violates RFC 2965: effective host name does not "
        //   239: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   242: ldc             "domain-match domain attribute."
        //   244: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   247: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   250: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //   253: athrow         
        //   254: aload_3        
        //   255: iconst_0       
        //   256: aload_3        
        //   257: invokevirtual   java/lang/String.length:()I
        //   260: aload           4
        //   262: invokevirtual   java/lang/String.length:()I
        //   265: isub           
        //   266: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   269: astore          6
        //   271: aload           6
        //   273: bipush          46
        //   275: invokevirtual   java/lang/String.indexOf:(I)I
        //   278: iconst_m1      
        //   279: if_icmpeq       324
        //   282: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //   285: dup            
        //   286: new             Ljava/lang/StringBuilder;
        //   289: dup            
        //   290: invokespecial   java/lang/StringBuilder.<init>:()V
        //   293: ldc             "Domain attribute \""
        //   295: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   298: aload_1        
        //   299: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   304: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: ldc             "\" violates RFC 2965: "
        //   309: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   312: ldc             "effective host minus domain may not contain any dots"
        //   314: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   317: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   320: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //   323: athrow         
        //   324: goto            391
        //   327: aload_1        
        //   328: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   333: aload_3        
        //   334: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   337: ifne            391
        //   340: new             Lorg/apache/http/cookie/CookieRestrictionViolationException;
        //   343: dup            
        //   344: new             Ljava/lang/StringBuilder;
        //   347: dup            
        //   348: invokespecial   java/lang/StringBuilder.<init>:()V
        //   351: ldc             "Illegal domain attribute: \""
        //   353: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   356: aload_1        
        //   357: invokeinterface org/apache/http/cookie/Cookie.getDomain:()Ljava/lang/String;
        //   362: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   365: ldc             "\"."
        //   367: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   370: ldc             "Domain of origin: \""
        //   372: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   375: aload_3        
        //   376: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   379: ldc             "\""
        //   381: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   384: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   387: invokespecial   org/apache/http/cookie/CookieRestrictionViolationException.<init>:(Ljava/lang/String;)V
        //   390: athrow         
        //   391: return         
        //    Exceptions:
        //  throws org.apache.http.cookie.MalformedCookieException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0391 (coming from #0324).
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
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String lowerCase = cookieOrigin.getHost().toLowerCase(Locale.ENGLISH);
        final String domain = cookie.getDomain();
        return domain != 0 && lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) == -1;
    }
}
