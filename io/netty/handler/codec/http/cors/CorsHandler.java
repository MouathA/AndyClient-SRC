package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;

public class CorsHandler extends ChannelDuplexHandler
{
    private static final InternalLogger logger;
    private final CorsConfig config;
    private HttpRequest request;
    
    public CorsHandler(final CorsConfig config) {
        this.config = config;
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (this.config.isCorsSupportEnabled() && o instanceof HttpRequest) {
            this.request = (HttpRequest)o;
            if (this.request != 0) {
                this.handlePreflight(channelHandlerContext, this.request);
                return;
            }
            if (this.config.isShortCurcuit() && this != 0) {
                forbidden(channelHandlerContext, this.request);
                return;
            }
        }
        channelHandlerContext.fireChannelRead(o);
    }
    
    private void handlePreflight(final ChannelHandlerContext channelHandlerContext, final HttpRequest httpRequest) {
        final DefaultFullHttpResponse preflightHeaders = new DefaultFullHttpResponse(httpRequest.getProtocolVersion(), HttpResponseStatus.OK);
        if (preflightHeaders != null) {
            this.setAllowMethods(preflightHeaders);
            this.setAllowHeaders(preflightHeaders);
            this.setAllowCredentials(preflightHeaders);
            this.setMaxAge(preflightHeaders);
            this.setPreflightHeaders(preflightHeaders);
        }
        channelHandlerContext.writeAndFlush(preflightHeaders).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    private void setPreflightHeaders(final HttpResponse httpResponse) {
        httpResponse.headers().add(this.config.preflightResponseHeaders());
    }
    
    private void echoRequestOrigin(final HttpResponse httpResponse) {
        setOrigin(httpResponse, this.request.headers().get("Origin"));
    }
    
    private static void setVaryHeader(final HttpResponse httpResponse) {
        httpResponse.headers().set("Vary", "Origin");
    }
    
    private static void setAnyOrigin(final HttpResponse httpResponse) {
        setOrigin(httpResponse, "*");
    }
    
    private static void setOrigin(final HttpResponse httpResponse, final String s) {
        httpResponse.headers().set("Access-Control-Allow-Origin", s);
    }
    
    private void setAllowCredentials(final HttpResponse httpResponse) {
        if (this.config.isCredentialsAllowed()) {
            httpResponse.headers().set("Access-Control-Allow-Credentials", "true");
        }
    }
    
    private void setExposeHeaders(final HttpResponse httpResponse) {
        if (!this.config.exposedHeaders().isEmpty()) {
            httpResponse.headers().set("Access-Control-Expose-Headers", this.config.exposedHeaders());
        }
    }
    
    private void setAllowMethods(final HttpResponse httpResponse) {
        httpResponse.headers().set("Access-Control-Allow-Methods", this.config.allowedRequestMethods());
    }
    
    private void setAllowHeaders(final HttpResponse httpResponse) {
        httpResponse.headers().set("Access-Control-Allow-Headers", this.config.allowedRequestHeaders());
    }
    
    private void setMaxAge(final HttpResponse httpResponse) {
        httpResponse.headers().set("Access-Control-Max-Age", this.config.maxAge());
    }
    
    @Override
    public void write(final ChannelHandlerContext p0, final Object p1, final ChannelPromise p2) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        io/netty/handler/codec/http/cors/CorsHandler.config:Lio/netty/handler/codec/http/cors/CorsConfig;
        //     4: invokevirtual   io/netty/handler/codec/http/cors/CorsConfig.isCorsSupportEnabled:()Z
        //     7: ifeq            47
        //    10: aload_2        
        //    11: instanceof      Lio/netty/handler/codec/http/HttpResponse;
        //    14: ifeq            47
        //    17: aload_2        
        //    18: checkcast       Lio/netty/handler/codec/http/HttpResponse;
        //    21: astore          4
        //    23: aload_0        
        //    24: aload           4
        //    26: ifnull          47
        //    29: aload_0        
        //    30: aload           4
        //    32: invokespecial   io/netty/handler/codec/http/cors/CorsHandler.setAllowCredentials:(Lio/netty/handler/codec/http/HttpResponse;)V
        //    35: aload_0        
        //    36: aload           4
        //    38: invokespecial   io/netty/handler/codec/http/cors/CorsHandler.setAllowHeaders:(Lio/netty/handler/codec/http/HttpResponse;)V
        //    41: aload_0        
        //    42: aload           4
        //    44: invokespecial   io/netty/handler/codec/http/cors/CorsHandler.setExposeHeaders:(Lio/netty/handler/codec/http/HttpResponse;)V
        //    47: aload_1        
        //    48: aload_2        
        //    49: aload_3        
        //    50: invokeinterface io/netty/channel/ChannelHandlerContext.writeAndFlush:(Ljava/lang/Object;Lio/netty/channel/ChannelPromise;)Lio/netty/channel/ChannelFuture;
        //    55: pop            
        //    56: return         
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0047 (coming from #0026).
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
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        CorsHandler.logger.error("Caught error in CorsHandler", t);
        channelHandlerContext.fireExceptionCaught(t);
    }
    
    private static void forbidden(final ChannelHandlerContext channelHandlerContext, final HttpRequest httpRequest) {
        channelHandlerContext.writeAndFlush(new DefaultFullHttpResponse(httpRequest.getProtocolVersion(), HttpResponseStatus.FORBIDDEN)).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(CorsHandler.class);
    }
}
