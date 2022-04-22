package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import java.io.*;
import org.apache.http.client.*;
import org.apache.http.*;
import org.apache.http.auth.*;

@Deprecated
@Immutable
public class ResponseAuthCache implements HttpResponseInterceptor
{
    private final Log log;
    
    public ResponseAuthCache() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public void process(final HttpResponse p0, final HttpContext p1) throws HttpException, IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "HTTP request"
        //     3: invokestatic    org/apache/http/util/Args.notNull:(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //     6: pop            
        //     7: aload_2        
        //     8: ldc             "HTTP context"
        //    10: invokestatic    org/apache/http/util/Args.notNull:(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
        //    13: pop            
        //    14: aload_2        
        //    15: ldc             "http.auth.auth-cache"
        //    17: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //    22: checkcast       Lorg/apache/http/client/AuthCache;
        //    25: astore_3       
        //    26: aload_2        
        //    27: ldc             "http.target_host"
        //    29: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //    34: checkcast       Lorg/apache/http/HttpHost;
        //    37: astore          4
        //    39: aload_2        
        //    40: ldc             "http.auth.target-scope"
        //    42: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //    47: checkcast       Lorg/apache/http/auth/AuthState;
        //    50: astore          5
        //    52: aload           4
        //    54: ifnull          259
        //    57: aload           5
        //    59: ifnull          259
        //    62: aload_0        
        //    63: getfield        org/apache/http/client/protocol/ResponseAuthCache.log:Lorg/apache/commons/logging/Log;
        //    66: invokeinterface org/apache/commons/logging/Log.isDebugEnabled:()Z
        //    71: ifeq            106
        //    74: aload_0        
        //    75: getfield        org/apache/http/client/protocol/ResponseAuthCache.log:Lorg/apache/commons/logging/Log;
        //    78: new             Ljava/lang/StringBuilder;
        //    81: dup            
        //    82: invokespecial   java/lang/StringBuilder.<init>:()V
        //    85: ldc             "Target auth state: "
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: aload           5
        //    92: invokevirtual   org/apache/http/auth/AuthState.getState:()Lorg/apache/http/auth/AuthProtocolState;
        //    95: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    98: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   101: invokeinterface org/apache/commons/logging/Log.debug:(Ljava/lang/Object;)V
        //   106: aload_0        
        //   107: aload           5
        //   109: ifnull          259
        //   112: aload_2        
        //   113: ldc             "http.scheme-registry"
        //   115: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //   120: checkcast       Lorg/apache/http/conn/scheme/SchemeRegistry;
        //   123: astore          6
        //   125: aload           4
        //   127: invokevirtual   org/apache/http/HttpHost.getPort:()I
        //   130: ifge            171
        //   133: aload           6
        //   135: aload           4
        //   137: invokevirtual   org/apache/http/conn/scheme/SchemeRegistry.getScheme:(Lorg/apache/http/HttpHost;)Lorg/apache/http/conn/scheme/Scheme;
        //   140: astore          7
        //   142: new             Lorg/apache/http/HttpHost;
        //   145: dup            
        //   146: aload           4
        //   148: invokevirtual   org/apache/http/HttpHost.getHostName:()Ljava/lang/String;
        //   151: aload           7
        //   153: aload           4
        //   155: invokevirtual   org/apache/http/HttpHost.getPort:()I
        //   158: invokevirtual   org/apache/http/conn/scheme/Scheme.resolvePort:(I)I
        //   161: aload           4
        //   163: invokevirtual   org/apache/http/HttpHost.getSchemeName:()Ljava/lang/String;
        //   166: invokespecial   org/apache/http/HttpHost.<init>:(Ljava/lang/String;ILjava/lang/String;)V
        //   169: astore          4
        //   171: aload_3        
        //   172: ifnonnull       192
        //   175: new             Lorg/apache/http/impl/client/BasicAuthCache;
        //   178: dup            
        //   179: invokespecial   org/apache/http/impl/client/BasicAuthCache.<init>:()V
        //   182: astore_3       
        //   183: aload_2        
        //   184: ldc             "http.auth.auth-cache"
        //   186: aload_3        
        //   187: invokeinterface org/apache/http/protocol/HttpContext.setAttribute:(Ljava/lang/String;Ljava/lang/Object;)V
        //   192: getstatic       org/apache/http/client/protocol/ResponseAuthCache$1.$SwitchMap$org$apache$http$auth$AuthProtocolState:[I
        //   195: aload           5
        //   197: invokevirtual   org/apache/http/auth/AuthState.getState:()Lorg/apache/http/auth/AuthProtocolState;
        //   200: invokevirtual   org/apache/http/auth/AuthProtocolState.ordinal:()I
        //   203: iaload         
        //   204: lookupswitch {
        //                1: 232
        //                2: 247
        //          default: 259
        //        }
        //   232: aload_0        
        //   233: aload_3        
        //   234: aload           4
        //   236: aload           5
        //   238: invokevirtual   org/apache/http/auth/AuthState.getAuthScheme:()Lorg/apache/http/auth/AuthScheme;
        //   241: invokespecial   org/apache/http/client/protocol/ResponseAuthCache.cache:(Lorg/apache/http/client/AuthCache;Lorg/apache/http/HttpHost;Lorg/apache/http/auth/AuthScheme;)V
        //   244: goto            259
        //   247: aload_0        
        //   248: aload_3        
        //   249: aload           4
        //   251: aload           5
        //   253: invokevirtual   org/apache/http/auth/AuthState.getAuthScheme:()Lorg/apache/http/auth/AuthScheme;
        //   256: invokespecial   org/apache/http/client/protocol/ResponseAuthCache.uncache:(Lorg/apache/http/client/AuthCache;Lorg/apache/http/HttpHost;Lorg/apache/http/auth/AuthScheme;)V
        //   259: aload_2        
        //   260: ldc             "http.proxy_host"
        //   262: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //   267: checkcast       Lorg/apache/http/HttpHost;
        //   270: astore          6
        //   272: aload_2        
        //   273: ldc             "http.auth.proxy-scope"
        //   275: invokeinterface org/apache/http/protocol/HttpContext.getAttribute:(Ljava/lang/String;)Ljava/lang/Object;
        //   280: checkcast       Lorg/apache/http/auth/AuthState;
        //   283: astore          7
        //   285: aload           6
        //   287: ifnull          431
        //   290: aload           7
        //   292: ifnull          431
        //   295: aload_0        
        //   296: getfield        org/apache/http/client/protocol/ResponseAuthCache.log:Lorg/apache/commons/logging/Log;
        //   299: invokeinterface org/apache/commons/logging/Log.isDebugEnabled:()Z
        //   304: ifeq            339
        //   307: aload_0        
        //   308: getfield        org/apache/http/client/protocol/ResponseAuthCache.log:Lorg/apache/commons/logging/Log;
        //   311: new             Ljava/lang/StringBuilder;
        //   314: dup            
        //   315: invokespecial   java/lang/StringBuilder.<init>:()V
        //   318: ldc             "Proxy auth state: "
        //   320: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   323: aload           7
        //   325: invokevirtual   org/apache/http/auth/AuthState.getState:()Lorg/apache/http/auth/AuthProtocolState;
        //   328: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   331: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   334: invokeinterface org/apache/commons/logging/Log.debug:(Ljava/lang/Object;)V
        //   339: aload_0        
        //   340: aload           7
        //   342: ifnull          431
        //   345: aload_3        
        //   346: ifnonnull       366
        //   349: new             Lorg/apache/http/impl/client/BasicAuthCache;
        //   352: dup            
        //   353: invokespecial   org/apache/http/impl/client/BasicAuthCache.<init>:()V
        //   356: astore_3       
        //   357: aload_2        
        //   358: ldc             "http.auth.auth-cache"
        //   360: aload_3        
        //   361: invokeinterface org/apache/http/protocol/HttpContext.setAttribute:(Ljava/lang/String;Ljava/lang/Object;)V
        //   366: getstatic       org/apache/http/client/protocol/ResponseAuthCache$1.$SwitchMap$org$apache$http$auth$AuthProtocolState:[I
        //   369: aload           7
        //   371: invokevirtual   org/apache/http/auth/AuthState.getState:()Lorg/apache/http/auth/AuthProtocolState;
        //   374: invokevirtual   org/apache/http/auth/AuthProtocolState.ordinal:()I
        //   377: iaload         
        //   378: lookupswitch {
        //                1: 404
        //                2: 419
        //          default: 431
        //        }
        //   404: aload_0        
        //   405: aload_3        
        //   406: aload           6
        //   408: aload           7
        //   410: invokevirtual   org/apache/http/auth/AuthState.getAuthScheme:()Lorg/apache/http/auth/AuthScheme;
        //   413: invokespecial   org/apache/http/client/protocol/ResponseAuthCache.cache:(Lorg/apache/http/client/AuthCache;Lorg/apache/http/HttpHost;Lorg/apache/http/auth/AuthScheme;)V
        //   416: goto            431
        //   419: aload_0        
        //   420: aload_3        
        //   421: aload           6
        //   423: aload           7
        //   425: invokevirtual   org/apache/http/auth/AuthState.getAuthScheme:()Lorg/apache/http/auth/AuthScheme;
        //   428: invokespecial   org/apache/http/client/protocol/ResponseAuthCache.uncache:(Lorg/apache/http/client/AuthCache;Lorg/apache/http/HttpHost;Lorg/apache/http/auth/AuthScheme;)V
        //   431: return         
        //    Exceptions:
        //  throws org.apache.http.HttpException
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0431 (coming from #0342).
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
    
    private void cache(final AuthCache authCache, final HttpHost httpHost, final AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
        }
        authCache.put(httpHost, authScheme);
    }
    
    private void uncache(final AuthCache authCache, final HttpHost httpHost, final AuthScheme authScheme) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
        }
        authCache.remove(httpHost);
    }
}
