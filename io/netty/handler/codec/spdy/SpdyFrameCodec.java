package io.netty.handler.codec.spdy;

import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import java.util.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.handler.codec.*;

public class SpdyFrameCodec extends ByteToMessageDecoder implements SpdyFrameDecoderDelegate, ChannelOutboundHandler
{
    private static final SpdyProtocolException INVALID_FRAME;
    private final SpdyFrameDecoder spdyFrameDecoder;
    private final SpdyFrameEncoder spdyFrameEncoder;
    private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
    private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
    private SpdyHeadersFrame spdyHeadersFrame;
    private SpdySettingsFrame spdySettingsFrame;
    private ChannelHandlerContext ctx;
    
    public SpdyFrameCodec(final SpdyVersion spdyVersion) {
        this(spdyVersion, 8192, 16384, 6, 15, 8);
    }
    
    public SpdyFrameCodec(final SpdyVersion spdyVersion, final int n, final int n2, final int n3, final int n4, final int n5) {
        this(spdyVersion, n, SpdyHeaderBlockDecoder.newInstance(spdyVersion, n2), SpdyHeaderBlockEncoder.newInstance(spdyVersion, n3, n4, n5));
    }
    
    protected SpdyFrameCodec(final SpdyVersion spdyVersion, final int n, final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder) {
        this.spdyFrameDecoder = new SpdyFrameDecoder(spdyVersion, this, n);
        this.spdyFrameEncoder = new SpdyFrameEncoder(spdyVersion);
        this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
        this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.ctx = ctx;
        ctx.channel().closeFuture().addListener((GenericFutureListener)new ChannelFutureListener() {
            final SpdyFrameCodec this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                SpdyFrameCodec.access$000(this.this$0).end();
                SpdyFrameCodec.access$100(this.this$0).end();
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        this.spdyFrameDecoder.decode(byteBuf);
    }
    
    @Override
    public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }
    
    @Override
    public void read(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        if (o instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)o;
            final ByteBuf encodeDataFrame = this.spdyFrameEncoder.encodeDataFrame(channelHandlerContext.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
            spdyDataFrame.release();
            channelHandlerContext.write(encodeDataFrame, channelPromise);
        }
        else if (o instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)o;
            final ByteBuf encode = this.spdyHeaderBlockEncoder.encode(spdySynStreamFrame);
            final ByteBuf encodeSynStreamFrame = this.spdyFrameEncoder.encodeSynStreamFrame(channelHandlerContext.alloc(), spdySynStreamFrame.streamId(), spdySynStreamFrame.associatedStreamId(), spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional(), encode);
            encode.release();
            channelHandlerContext.write(encodeSynStreamFrame, channelPromise);
        }
        else if (o instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)o;
            final ByteBuf encode2 = this.spdyHeaderBlockEncoder.encode(spdySynReplyFrame);
            final ByteBuf encodeSynReplyFrame = this.spdyFrameEncoder.encodeSynReplyFrame(channelHandlerContext.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), encode2);
            encode2.release();
            channelHandlerContext.write(encodeSynReplyFrame, channelPromise);
        }
        else if (o instanceof SpdyRstStreamFrame) {
            final SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)o;
            channelHandlerContext.write(this.spdyFrameEncoder.encodeRstStreamFrame(channelHandlerContext.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code()), channelPromise);
        }
        else if (o instanceof SpdySettingsFrame) {
            channelHandlerContext.write(this.spdyFrameEncoder.encodeSettingsFrame(channelHandlerContext.alloc(), (SpdySettingsFrame)o), channelPromise);
        }
        else if (o instanceof SpdyPingFrame) {
            channelHandlerContext.write(this.spdyFrameEncoder.encodePingFrame(channelHandlerContext.alloc(), ((SpdyPingFrame)o).id()), channelPromise);
        }
        else if (o instanceof SpdyGoAwayFrame) {
            final SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)o;
            channelHandlerContext.write(this.spdyFrameEncoder.encodeGoAwayFrame(channelHandlerContext.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code()), channelPromise);
        }
        else if (o instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)o;
            final ByteBuf encode3 = this.spdyHeaderBlockEncoder.encode(spdyHeadersFrame);
            final ByteBuf encodeHeadersFrame = this.spdyFrameEncoder.encodeHeadersFrame(channelHandlerContext.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), encode3);
            encode3.release();
            channelHandlerContext.write(encodeHeadersFrame, channelPromise);
        }
        else {
            if (!(o instanceof SpdyWindowUpdateFrame)) {
                throw new UnsupportedMessageTypeException(o, new Class[0]);
            }
            final SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)o;
            channelHandlerContext.write(this.spdyFrameEncoder.encodeWindowUpdateFrame(channelHandlerContext.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize()), channelPromise);
        }
    }
    
    @Override
    public void readDataFrame(final int n, final boolean last, final ByteBuf byteBuf) {
        final DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(n, byteBuf);
        defaultSpdyDataFrame.setLast(last);
        this.ctx.fireChannelRead(defaultSpdyDataFrame);
    }
    
    @Override
    public void readSynStreamFrame(final int n, final int n2, final byte b, final boolean last, final boolean unidirectional) {
        final DefaultSpdySynStreamFrame spdyHeadersFrame = new DefaultSpdySynStreamFrame(n, n2, b);
        spdyHeadersFrame.setLast(last);
        spdyHeadersFrame.setUnidirectional(unidirectional);
        this.spdyHeadersFrame = spdyHeadersFrame;
    }
    
    @Override
    public void readSynReplyFrame(final int n, final boolean last) {
        final DefaultSpdySynReplyFrame spdyHeadersFrame = new DefaultSpdySynReplyFrame(n);
        spdyHeadersFrame.setLast(last);
        this.spdyHeadersFrame = spdyHeadersFrame;
    }
    
    @Override
    public void readRstStreamFrame(final int n, final int n2) {
        this.ctx.fireChannelRead(new DefaultSpdyRstStreamFrame(n, n2));
    }
    
    @Override
    public void readSettingsFrame(final boolean clearPreviouslyPersistedSettings) {
        (this.spdySettingsFrame = new DefaultSpdySettingsFrame()).setClearPreviouslyPersistedSettings(clearPreviouslyPersistedSettings);
    }
    
    @Override
    public void readSetting(final int n, final int n2, final boolean b, final boolean b2) {
        this.spdySettingsFrame.setValue(n, n2, b, b2);
    }
    
    @Override
    public void readSettingsEnd() {
        final SpdySettingsFrame spdySettingsFrame = this.spdySettingsFrame;
        this.spdySettingsFrame = null;
        this.ctx.fireChannelRead(spdySettingsFrame);
    }
    
    @Override
    public void readPingFrame(final int n) {
        this.ctx.fireChannelRead(new DefaultSpdyPingFrame(n));
    }
    
    @Override
    public void readGoAwayFrame(final int n, final int n2) {
        this.ctx.fireChannelRead(new DefaultSpdyGoAwayFrame(n, n2));
    }
    
    @Override
    public void readHeadersFrame(final int n, final boolean last) {
        (this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(n)).setLast(last);
    }
    
    @Override
    public void readWindowUpdateFrame(final int n, final int n2) {
        this.ctx.fireChannelRead(new DefaultSpdyWindowUpdateFrame(n, n2));
    }
    
    @Override
    public void readHeaderBlock(final ByteBuf byteBuf) {
        this.spdyHeaderBlockDecoder.decode(byteBuf, this.spdyHeadersFrame);
        byteBuf.release();
    }
    
    @Override
    public void readHeaderBlockEnd() {
        this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
        final SpdyHeadersFrame spdyHeadersFrame = this.spdyHeadersFrame;
        this.spdyHeadersFrame = null;
        if (spdyHeadersFrame != null) {
            this.ctx.fireChannelRead(spdyHeadersFrame);
        }
    }
    
    @Override
    public void readFrameError(final String s) {
        this.ctx.fireExceptionCaught(SpdyFrameCodec.INVALID_FRAME);
    }
    
    static SpdyHeaderBlockDecoder access$000(final SpdyFrameCodec spdyFrameCodec) {
        return spdyFrameCodec.spdyHeaderBlockDecoder;
    }
    
    static SpdyHeaderBlockEncoder access$100(final SpdyFrameCodec spdyFrameCodec) {
        return spdyFrameCodec.spdyHeaderBlockEncoder;
    }
    
    static {
        INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
    }
}
