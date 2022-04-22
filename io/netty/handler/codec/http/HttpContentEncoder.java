package io.netty.handler.codec.http;

import io.netty.handler.codec.*;
import io.netty.channel.embedded.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.util.*;
import io.netty.buffer.*;

public abstract class HttpContentEncoder extends MessageToMessageCodec
{
    private final Queue acceptEncodingQueue;
    private String acceptEncoding;
    private EmbeddedChannel encoder;
    private State state;
    static final boolean $assertionsDisabled;
    
    public HttpContentEncoder() {
        this.acceptEncodingQueue = new ArrayDeque();
        this.state = State.AWAIT_HEADERS;
    }
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return o instanceof HttpContent || o instanceof HttpResponse;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final HttpRequest httpRequest, final List list) throws Exception {
        String value = httpRequest.headers().get("Accept-Encoding");
        if (value == null) {
            value = "identity";
        }
        this.acceptEncodingQueue.add(value);
        list.add(ReferenceCountUtil.retain(httpRequest));
    }
    
    protected void encode(final ChannelHandlerContext p0, final HttpObject p1, final List p2) throws Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Lio/netty/handler/codec/http/HttpResponse;
        //     4: ifeq            18
        //     7: aload_2        
        //     8: instanceof      Lio/netty/handler/codec/http/LastHttpContent;
        //    11: ifeq            18
        //    14: iconst_1       
        //    15: goto            19
        //    18: iconst_0       
        //    19: istore          4
        //    21: getstatic       io/netty/handler/codec/http/HttpContentEncoder$1.$SwitchMap$io$netty$handler$codec$http$HttpContentEncoder$State:[I
        //    24: aload_0        
        //    25: getfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //    28: invokevirtual   io/netty/handler/codec/http/HttpContentEncoder$State.ordinal:()I
        //    31: iaload         
        //    32: tableswitch {
        //                2: 60
        //                3: 409
        //                4: 432
        //          default: 461
        //        }
        //    60: aload_2        
        //    61: invokestatic    io/netty/handler/codec/http/HttpContentEncoder.ensureHeaders:(Lio/netty/handler/codec/http/HttpObject;)V
        //    64: getstatic       io/netty/handler/codec/http/HttpContentEncoder.$assertionsDisabled:Z
        //    67: ifne            85
        //    70: aload_0        
        //    71: getfield        io/netty/handler/codec/http/HttpContentEncoder.encoder:Lio/netty/channel/embedded/EmbeddedChannel;
        //    74: ifnull          85
        //    77: new             Ljava/lang/AssertionError;
        //    80: dup            
        //    81: invokespecial   java/lang/AssertionError.<init>:()V
        //    84: athrow         
        //    85: aload_2        
        //    86: checkcast       Lio/netty/handler/codec/http/HttpResponse;
        //    89: astore          5
        //    91: aload           5
        //    93: invokeinterface io/netty/handler/codec/http/HttpResponse.getStatus:()Lio/netty/handler/codec/http/HttpResponseStatus;
        //    98: invokevirtual   io/netty/handler/codec/http/HttpResponseStatus.code:()I
        //   101: bipush          100
        //   103: if_icmpne       145
        //   106: iload           4
        //   108: ifeq            126
        //   111: aload_3        
        //   112: aload           5
        //   114: invokestatic    io/netty/util/ReferenceCountUtil.retain:(Ljava/lang/Object;)Ljava/lang/Object;
        //   117: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   122: pop            
        //   123: goto            461
        //   126: aload_3        
        //   127: aload           5
        //   129: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   134: pop            
        //   135: aload_0        
        //   136: getstatic       io/netty/handler/codec/http/HttpContentEncoder$State.PASS_THROUGH:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   139: putfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   142: goto            461
        //   145: aload_0        
        //   146: aload_0        
        //   147: getfield        io/netty/handler/codec/http/HttpContentEncoder.acceptEncodingQueue:Ljava/util/Queue;
        //   150: invokeinterface java/util/Queue.poll:()Ljava/lang/Object;
        //   155: checkcast       Ljava/lang/String;
        //   158: putfield        io/netty/handler/codec/http/HttpContentEncoder.acceptEncoding:Ljava/lang/String;
        //   161: aload_0        
        //   162: getfield        io/netty/handler/codec/http/HttpContentEncoder.acceptEncoding:Ljava/lang/String;
        //   165: ifnonnull       178
        //   168: new             Ljava/lang/IllegalStateException;
        //   171: dup            
        //   172: ldc             "cannot send more responses than requests"
        //   174: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //   177: athrow         
        //   178: iload           4
        //   180: ifeq            214
        //   183: aload           5
        //   185: checkcast       Lio/netty/buffer/ByteBufHolder;
        //   188: invokeinterface io/netty/buffer/ByteBufHolder.content:()Lio/netty/buffer/ByteBuf;
        //   193: invokevirtual   io/netty/buffer/ByteBuf.isReadable:()Z
        //   196: ifne            214
        //   199: aload_3        
        //   200: aload           5
        //   202: invokestatic    io/netty/util/ReferenceCountUtil.retain:(Ljava/lang/Object;)Ljava/lang/Object;
        //   205: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   210: pop            
        //   211: goto            461
        //   214: aload_0        
        //   215: aload           5
        //   217: aload_0        
        //   218: getfield        io/netty/handler/codec/http/HttpContentEncoder.acceptEncoding:Ljava/lang/String;
        //   221: invokevirtual   io/netty/handler/codec/http/HttpContentEncoder.beginEncode:(Lio/netty/handler/codec/http/HttpResponse;Ljava/lang/String;)Lio/netty/handler/codec/http/HttpContentEncoder$Result;
        //   224: astore          6
        //   226: aload           6
        //   228: ifnonnull       270
        //   231: iload           4
        //   233: ifeq            251
        //   236: aload_3        
        //   237: aload           5
        //   239: invokestatic    io/netty/util/ReferenceCountUtil.retain:(Ljava/lang/Object;)Ljava/lang/Object;
        //   242: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   247: pop            
        //   248: goto            461
        //   251: aload_3        
        //   252: aload           5
        //   254: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   259: pop            
        //   260: aload_0        
        //   261: getstatic       io/netty/handler/codec/http/HttpContentEncoder$State.PASS_THROUGH:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   264: putfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   267: goto            461
        //   270: aload_0        
        //   271: aload           6
        //   273: invokevirtual   io/netty/handler/codec/http/HttpContentEncoder$Result.contentEncoder:()Lio/netty/channel/embedded/EmbeddedChannel;
        //   276: putfield        io/netty/handler/codec/http/HttpContentEncoder.encoder:Lio/netty/channel/embedded/EmbeddedChannel;
        //   279: aload           5
        //   281: invokeinterface io/netty/handler/codec/http/HttpResponse.headers:()Lio/netty/handler/codec/http/HttpHeaders;
        //   286: ldc             "Content-Encoding"
        //   288: aload           6
        //   290: invokevirtual   io/netty/handler/codec/http/HttpContentEncoder$Result.targetContentEncoding:()Ljava/lang/String;
        //   293: invokevirtual   io/netty/handler/codec/http/HttpHeaders.set:(Ljava/lang/String;Ljava/lang/Object;)Lio/netty/handler/codec/http/HttpHeaders;
        //   296: pop            
        //   297: aload           5
        //   299: invokeinterface io/netty/handler/codec/http/HttpResponse.headers:()Lio/netty/handler/codec/http/HttpHeaders;
        //   304: ldc             "Content-Length"
        //   306: invokevirtual   io/netty/handler/codec/http/HttpHeaders.remove:(Ljava/lang/String;)Lio/netty/handler/codec/http/HttpHeaders;
        //   309: pop            
        //   310: aload           5
        //   312: invokeinterface io/netty/handler/codec/http/HttpResponse.headers:()Lio/netty/handler/codec/http/HttpHeaders;
        //   317: ldc             "Transfer-Encoding"
        //   319: ldc             "chunked"
        //   321: invokevirtual   io/netty/handler/codec/http/HttpHeaders.set:(Ljava/lang/String;Ljava/lang/Object;)Lio/netty/handler/codec/http/HttpHeaders;
        //   324: pop            
        //   325: iload           4
        //   327: ifeq            383
        //   330: new             Lio/netty/handler/codec/http/DefaultHttpResponse;
        //   333: dup            
        //   334: aload           5
        //   336: invokeinterface io/netty/handler/codec/http/HttpResponse.getProtocolVersion:()Lio/netty/handler/codec/http/HttpVersion;
        //   341: aload           5
        //   343: invokeinterface io/netty/handler/codec/http/HttpResponse.getStatus:()Lio/netty/handler/codec/http/HttpResponseStatus;
        //   348: invokespecial   io/netty/handler/codec/http/DefaultHttpResponse.<init>:(Lio/netty/handler/codec/http/HttpVersion;Lio/netty/handler/codec/http/HttpResponseStatus;)V
        //   351: astore          7
        //   353: aload           7
        //   355: invokeinterface io/netty/handler/codec/http/HttpResponse.headers:()Lio/netty/handler/codec/http/HttpHeaders;
        //   360: aload           5
        //   362: invokeinterface io/netty/handler/codec/http/HttpResponse.headers:()Lio/netty/handler/codec/http/HttpHeaders;
        //   367: invokevirtual   io/netty/handler/codec/http/HttpHeaders.set:(Lio/netty/handler/codec/http/HttpHeaders;)Lio/netty/handler/codec/http/HttpHeaders;
        //   370: pop            
        //   371: aload_3        
        //   372: aload           7
        //   374: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   379: pop            
        //   380: goto            409
        //   383: aload_3        
        //   384: aload           5
        //   386: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   391: pop            
        //   392: aload_0        
        //   393: getstatic       io/netty/handler/codec/http/HttpContentEncoder$State.AWAIT_CONTENT:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   396: putfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   399: aload_2        
        //   400: instanceof      Lio/netty/handler/codec/http/HttpContent;
        //   403: ifne            409
        //   406: goto            461
        //   409: aload_2        
        //   410: invokestatic    io/netty/handler/codec/http/HttpContentEncoder.ensureContent:(Lio/netty/handler/codec/http/HttpObject;)V
        //   413: aload_0        
        //   414: aload_2        
        //   415: checkcast       Lio/netty/handler/codec/http/HttpContent;
        //   418: aload_3        
        //   419: ifeq            461
        //   422: aload_0        
        //   423: getstatic       io/netty/handler/codec/http/HttpContentEncoder$State.AWAIT_HEADERS:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   426: putfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   429: goto            461
        //   432: aload_2        
        //   433: invokestatic    io/netty/handler/codec/http/HttpContentEncoder.ensureContent:(Lio/netty/handler/codec/http/HttpObject;)V
        //   436: aload_3        
        //   437: aload_2        
        //   438: invokestatic    io/netty/util/ReferenceCountUtil.retain:(Ljava/lang/Object;)Ljava/lang/Object;
        //   441: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   446: pop            
        //   447: aload_2        
        //   448: instanceof      Lio/netty/handler/codec/http/LastHttpContent;
        //   451: ifeq            461
        //   454: aload_0        
        //   455: getstatic       io/netty/handler/codec/http/HttpContentEncoder$State.AWAIT_HEADERS:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   458: putfield        io/netty/handler/codec/http/HttpContentEncoder.state:Lio/netty/handler/codec/http/HttpContentEncoder$State;
        //   461: return         
        //    Exceptions:
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0461 (coming from #0419).
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
    
    private static void ensureHeaders(final HttpObject httpObject) {
        if (!(httpObject instanceof HttpResponse)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
        }
    }
    
    private static void ensureContent(final HttpObject httpObject) {
        if (!(httpObject instanceof HttpContent)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
        }
    }
    
    protected abstract Result beginEncode(final HttpResponse p0, final String p1) throws Exception;
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.handlerRemoved(channelHandlerContext);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.channelInactive(channelHandlerContext);
    }
    
    private void cleanup() {
        if (this.encoder != null) {
            if (this.encoder.finish()) {
                while (true) {
                    final ByteBuf byteBuf = (ByteBuf)this.encoder.readOutbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.encoder = null;
        }
    }
    
    private void encode(final ByteBuf byteBuf, final List list) {
        this.encoder.writeOutbound(byteBuf.retain());
        this.fetchEncoderOutput(list);
    }
    
    private void finishEncode(final List list) {
        if (this.encoder.finish()) {
            this.fetchEncoderOutput(list);
        }
        this.encoder = null;
    }
    
    private void fetchEncoderOutput(final List list) {
        while (true) {
            final ByteBuf byteBuf = (ByteBuf)this.encoder.readOutbound();
            if (byteBuf == null) {
                break;
            }
            if (!byteBuf.isReadable()) {
                byteBuf.release();
            }
            else {
                list.add(new DefaultHttpContent(byteBuf));
            }
        }
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (HttpRequest)o, list);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (HttpObject)o, list);
    }
    
    static {
        $assertionsDisabled = !HttpContentEncoder.class.desiredAssertionStatus();
    }
    
    private enum State
    {
        PASS_THROUGH("PASS_THROUGH", 0), 
        AWAIT_HEADERS("AWAIT_HEADERS", 1), 
        AWAIT_CONTENT("AWAIT_CONTENT", 2);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.PASS_THROUGH, State.AWAIT_HEADERS, State.AWAIT_CONTENT };
        }
    }
    
    public static final class Result
    {
        private final String targetContentEncoding;
        private final EmbeddedChannel contentEncoder;
        
        public Result(final String targetContentEncoding, final EmbeddedChannel contentEncoder) {
            if (targetContentEncoding == null) {
                throw new NullPointerException("targetContentEncoding");
            }
            if (contentEncoder == null) {
                throw new NullPointerException("contentEncoder");
            }
            this.targetContentEncoding = targetContentEncoding;
            this.contentEncoder = contentEncoder;
        }
        
        public String targetContentEncoding() {
            return this.targetContentEncoding;
        }
        
        public EmbeddedChannel contentEncoder() {
            return this.contentEncoder;
        }
    }
}
