package io.netty.handler.codec.spdy;

import java.util.concurrent.atomic.*;
import java.util.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.*;

public class SpdySessionHandler extends ChannelDuplexHandler
{
    private static final SpdyProtocolException PROTOCOL_EXCEPTION;
    private static final SpdyProtocolException STREAM_CLOSED;
    private static final int DEFAULT_WINDOW_SIZE = 65536;
    private int initialSendWindowSize;
    private int initialReceiveWindowSize;
    private int initialSessionReceiveWindowSize;
    private final SpdySession spdySession;
    private int lastGoodStreamId;
    private static final int DEFAULT_MAX_CONCURRENT_STREAMS = Integer.MAX_VALUE;
    private int remoteConcurrentStreams;
    private int localConcurrentStreams;
    private final AtomicInteger pings;
    private boolean sentGoAwayFrame;
    private boolean receivedGoAwayFrame;
    private ChannelFutureListener closeSessionFutureListener;
    private final boolean server;
    private final int minorVersion;
    
    public SpdySessionHandler(final SpdyVersion spdyVersion, final boolean server) {
        this.initialSendWindowSize = 65536;
        this.initialReceiveWindowSize = 65536;
        this.initialSessionReceiveWindowSize = 65536;
        this.spdySession = new SpdySession(this.initialSendWindowSize, this.initialReceiveWindowSize);
        this.remoteConcurrentStreams = Integer.MAX_VALUE;
        this.localConcurrentStreams = Integer.MAX_VALUE;
        this.pings = new AtomicInteger();
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        this.server = server;
        this.minorVersion = spdyVersion.getMinorVersion();
    }
    
    public void setSessionReceiveWindowSize(final int initialSessionReceiveWindowSize) {
        if (initialSessionReceiveWindowSize < 0) {
            throw new IllegalArgumentException("sessionReceiveWindowSize");
        }
        this.initialSessionReceiveWindowSize = initialSessionReceiveWindowSize;
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (o instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)o;
            final int streamId = spdyDataFrame.streamId();
            final int n = -1 * spdyDataFrame.content().readableBytes();
            final int updateReceiveWindowSize = this.spdySession.updateReceiveWindowSize(0, n);
            if (updateReceiveWindowSize < 0) {
                this.issueSessionError(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            if (updateReceiveWindowSize <= this.initialSessionReceiveWindowSize / 2) {
                final int n2 = this.initialSessionReceiveWindowSize - updateReceiveWindowSize;
                this.spdySession.updateReceiveWindowSize(0, n2);
                channelHandlerContext.writeAndFlush(new DefaultSpdyWindowUpdateFrame(0, n2));
            }
            if (!this.spdySession.isActiveStream(streamId)) {
                spdyDataFrame.release();
                if (streamId <= this.lastGoodStreamId) {
                    this.issueStreamError(channelHandlerContext, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                }
                else if (!this.sentGoAwayFrame) {
                    this.issueStreamError(channelHandlerContext, streamId, SpdyStreamStatus.INVALID_STREAM);
                }
                return;
            }
            if (this.spdySession.isRemoteSideClosed(streamId)) {
                spdyDataFrame.release();
                this.issueStreamError(channelHandlerContext, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
                return;
            }
            if (!this.isRemoteInitiatedId(streamId) && !this.spdySession.hasReceivedReply(streamId)) {
                spdyDataFrame.release();
                this.issueStreamError(channelHandlerContext, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            final int updateReceiveWindowSize2 = this.spdySession.updateReceiveWindowSize(streamId, n);
            if (updateReceiveWindowSize2 < this.spdySession.getReceiveWindowSizeLowerBound(streamId)) {
                spdyDataFrame.release();
                this.issueStreamError(channelHandlerContext, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                return;
            }
            if (updateReceiveWindowSize2 < 0) {
                while (spdyDataFrame.content().readableBytes() > this.initialReceiveWindowSize) {
                    channelHandlerContext.writeAndFlush(new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(this.initialReceiveWindowSize).retain()));
                }
            }
            if (updateReceiveWindowSize2 <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
                final int n3 = this.initialReceiveWindowSize - updateReceiveWindowSize2;
                this.spdySession.updateReceiveWindowSize(streamId, n3);
                channelHandlerContext.writeAndFlush(new DefaultSpdyWindowUpdateFrame(streamId, n3));
            }
            if (spdyDataFrame.isLast()) {
                this.halfCloseStream(streamId, true, channelHandlerContext.newSucceededFuture());
            }
        }
        else if (o instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)o;
            final int streamId2 = spdySynStreamFrame.streamId();
            if (spdySynStreamFrame.isInvalid() || !this.isRemoteInitiatedId(streamId2) || this.spdySession.isActiveStream(streamId2)) {
                this.issueStreamError(channelHandlerContext, streamId2, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (streamId2 <= this.lastGoodStreamId) {
                this.issueSessionError(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            if (!this.acceptStream(streamId2, spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional())) {
                this.issueStreamError(channelHandlerContext, streamId2, SpdyStreamStatus.REFUSED_STREAM);
                return;
            }
        }
        else if (o instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)o;
            final int streamId3 = spdySynReplyFrame.streamId();
            if (spdySynReplyFrame.isInvalid() || this.isRemoteInitiatedId(streamId3) || this.spdySession.isRemoteSideClosed(streamId3)) {
                this.issueStreamError(channelHandlerContext, streamId3, SpdyStreamStatus.INVALID_STREAM);
                return;
            }
            if (this.spdySession.hasReceivedReply(streamId3)) {
                this.issueStreamError(channelHandlerContext, streamId3, SpdyStreamStatus.STREAM_IN_USE);
                return;
            }
            this.spdySession.receivedReply(streamId3);
            if (spdySynReplyFrame.isLast()) {
                this.halfCloseStream(streamId3, true, channelHandlerContext.newSucceededFuture());
            }
        }
        else if (o instanceof SpdyRstStreamFrame) {
            this.removeStream(((SpdyRstStreamFrame)o).streamId(), channelHandlerContext.newSucceededFuture());
        }
        else if (o instanceof SpdySettingsFrame) {
            final SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)o;
            final int value = spdySettingsFrame.getValue(0);
            if (value >= 0 && value != this.minorVersion) {
                this.issueSessionError(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            final int value2 = spdySettingsFrame.getValue(4);
            if (value2 >= 0) {
                this.remoteConcurrentStreams = value2;
            }
            if (spdySettingsFrame.isPersisted(7)) {
                spdySettingsFrame.removeValue(7);
            }
            spdySettingsFrame.setPersistValue(7, false);
            final int value3 = spdySettingsFrame.getValue(7);
            if (value3 >= 0) {
                this.updateInitialSendWindowSize(value3);
            }
        }
        else if (o instanceof SpdyPingFrame) {
            final SpdyPingFrame spdyPingFrame = (SpdyPingFrame)o;
            if (this.isRemoteInitiatedId(spdyPingFrame.id())) {
                channelHandlerContext.writeAndFlush(spdyPingFrame);
                return;
            }
            if (this.pings.get() == 0) {
                return;
            }
            this.pings.getAndDecrement();
        }
        else if (o instanceof SpdyGoAwayFrame) {
            this.receivedGoAwayFrame = true;
        }
        else if (o instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)o;
            final int streamId4 = spdyHeadersFrame.streamId();
            if (spdyHeadersFrame.isInvalid()) {
                this.issueStreamError(channelHandlerContext, streamId4, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (this.spdySession.isRemoteSideClosed(streamId4)) {
                this.issueStreamError(channelHandlerContext, streamId4, SpdyStreamStatus.INVALID_STREAM);
                return;
            }
            if (spdyHeadersFrame.isLast()) {
                this.halfCloseStream(streamId4, true, channelHandlerContext.newSucceededFuture());
            }
        }
        else if (o instanceof SpdyWindowUpdateFrame) {
            final SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)o;
            final int streamId5 = spdyWindowUpdateFrame.streamId();
            final int deltaWindowSize = spdyWindowUpdateFrame.deltaWindowSize();
            if (streamId5 != 0 && this.spdySession.isLocalSideClosed(streamId5)) {
                return;
            }
            if (this.spdySession.getSendWindowSize(streamId5) > Integer.MAX_VALUE - deltaWindowSize) {
                if (streamId5 == 0) {
                    this.issueSessionError(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
                }
                else {
                    this.issueStreamError(channelHandlerContext, streamId5, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                }
                return;
            }
            this.updateSendWindowSize(channelHandlerContext, streamId5, deltaWindowSize);
        }
        channelHandlerContext.fireChannelRead(o);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final Iterator<Integer> iterator = this.spdySession.activeStreams().keySet().iterator();
        while (iterator.hasNext()) {
            this.removeStream(iterator.next(), channelHandlerContext.newSucceededFuture());
        }
        channelHandlerContext.fireChannelInactive();
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof SpdyProtocolException) {
            this.issueSessionError(channelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
        }
        channelHandlerContext.fireExceptionCaught(t);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.sendGoAwayFrame(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        if (o instanceof SpdyDataFrame || o instanceof SpdySynStreamFrame || o instanceof SpdySynReplyFrame || o instanceof SpdyRstStreamFrame || o instanceof SpdySettingsFrame || o instanceof SpdyPingFrame || o instanceof SpdyGoAwayFrame || o instanceof SpdyHeadersFrame || o instanceof SpdyWindowUpdateFrame) {
            this.handleOutboundMessage(channelHandlerContext, o, channelPromise);
        }
        else {
            channelHandlerContext.write(o, channelPromise);
        }
    }
    
    private void handleOutboundMessage(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        if (o instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)o;
            final int streamId = spdyDataFrame.streamId();
            if (this.spdySession.isLocalSideClosed(streamId)) {
                spdyDataFrame.release();
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            final int readableBytes = spdyDataFrame.content().readableBytes();
            final int min = Math.min(this.spdySession.getSendWindowSize(streamId), this.spdySession.getSendWindowSize(0));
            if (min <= 0) {
                this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, channelPromise));
                return;
            }
            if (min < readableBytes) {
                this.spdySession.updateSendWindowSize(streamId, -1 * min);
                this.spdySession.updateSendWindowSize(0, -1 * min);
                final DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(min).retain());
                this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, channelPromise));
                channelHandlerContext.write(defaultSpdyDataFrame).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                    final ChannelHandlerContext val$context;
                    final SpdySessionHandler this$0;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            SpdySessionHandler.access$000(this.this$0, this.val$context, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
                return;
            }
            this.spdySession.updateSendWindowSize(streamId, -1 * readableBytes);
            this.spdySession.updateSendWindowSize(0, -1 * readableBytes);
            channelPromise.addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                final ChannelHandlerContext val$context;
                final SpdySessionHandler this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        SpdySessionHandler.access$000(this.this$0, this.val$context, SpdySessionStatus.INTERNAL_ERROR);
                    }
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
            if (spdyDataFrame.isLast()) {
                this.halfCloseStream(streamId, false, channelPromise);
            }
        }
        else if (o instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)o;
            final int streamId2 = spdySynStreamFrame.streamId();
            if (this.isRemoteInitiatedId(streamId2)) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (!this.acceptStream(streamId2, spdySynStreamFrame.priority(), spdySynStreamFrame.isUnidirectional(), spdySynStreamFrame.isLast())) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
        }
        else if (o instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)o;
            final int streamId3 = spdySynReplyFrame.streamId();
            if (!this.isRemoteInitiatedId(streamId3) || this.spdySession.isLocalSideClosed(streamId3)) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (spdySynReplyFrame.isLast()) {
                this.halfCloseStream(streamId3, false, channelPromise);
            }
        }
        else if (o instanceof SpdyRstStreamFrame) {
            this.removeStream(((SpdyRstStreamFrame)o).streamId(), channelPromise);
        }
        else if (o instanceof SpdySettingsFrame) {
            final SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)o;
            final int value = spdySettingsFrame.getValue(0);
            if (value >= 0 && value != this.minorVersion) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            final int value2 = spdySettingsFrame.getValue(4);
            if (value2 >= 0) {
                this.localConcurrentStreams = value2;
            }
            if (spdySettingsFrame.isPersisted(7)) {
                spdySettingsFrame.removeValue(7);
            }
            spdySettingsFrame.setPersistValue(7, false);
            final int value3 = spdySettingsFrame.getValue(7);
            if (value3 >= 0) {
                this.updateInitialReceiveWindowSize(value3);
            }
        }
        else if (o instanceof SpdyPingFrame) {
            final SpdyPingFrame spdyPingFrame = (SpdyPingFrame)o;
            if (this.isRemoteInitiatedId(spdyPingFrame.id())) {
                channelHandlerContext.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.id()));
                return;
            }
            this.pings.getAndIncrement();
        }
        else {
            if (o instanceof SpdyGoAwayFrame) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
            if (o instanceof SpdyHeadersFrame) {
                final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)o;
                final int streamId4 = spdyHeadersFrame.streamId();
                if (this.spdySession.isLocalSideClosed(streamId4)) {
                    channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                    return;
                }
                if (spdyHeadersFrame.isLast()) {
                    this.halfCloseStream(streamId4, false, channelPromise);
                }
            }
            else if (o instanceof SpdyWindowUpdateFrame) {
                channelPromise.setFailure((Throwable)SpdySessionHandler.PROTOCOL_EXCEPTION);
                return;
            }
        }
        channelHandlerContext.write(o, channelPromise);
    }
    
    private void issueSessionError(final ChannelHandlerContext channelHandlerContext, final SpdySessionStatus spdySessionStatus) {
        this.sendGoAwayFrame(channelHandlerContext, spdySessionStatus).addListener((GenericFutureListener)new ClosingChannelFutureListener(channelHandlerContext, channelHandlerContext.newPromise()));
    }
    
    private void issueStreamError(final ChannelHandlerContext channelHandlerContext, final int n, final SpdyStreamStatus spdyStreamStatus) {
        final boolean b = !this.spdySession.isRemoteSideClosed(n);
        final ChannelPromise promise = channelHandlerContext.newPromise();
        this.removeStream(n, promise);
        final DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, spdyStreamStatus);
        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame, promise);
        if (b) {
            channelHandlerContext.fireChannelRead(defaultSpdyRstStreamFrame);
        }
    }
    
    private boolean isRemoteInitiatedId(final int n) {
        final boolean serverId = SpdyCodecUtil.isServerId(n);
        return (this.server && !serverId) || (!this.server && serverId);
    }
    
    private synchronized void updateInitialSendWindowSize(final int initialSendWindowSize) {
        final int n = initialSendWindowSize - this.initialSendWindowSize;
        this.initialSendWindowSize = initialSendWindowSize;
        this.spdySession.updateAllSendWindowSizes(n);
    }
    
    private synchronized void updateInitialReceiveWindowSize(final int initialReceiveWindowSize) {
        final int n = initialReceiveWindowSize - this.initialReceiveWindowSize;
        this.initialReceiveWindowSize = initialReceiveWindowSize;
        this.spdySession.updateAllReceiveWindowSizes(n);
    }
    
    private synchronized boolean acceptStream(final int lastGoodStreamId, final byte b, final boolean b2, final boolean b3) {
        if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
            return false;
        }
        final boolean remoteInitiatedId = this.isRemoteInitiatedId(lastGoodStreamId);
        if (this.spdySession.numActiveStreams(remoteInitiatedId) >= (remoteInitiatedId ? this.localConcurrentStreams : this.remoteConcurrentStreams)) {
            return false;
        }
        this.spdySession.acceptStream(lastGoodStreamId, b, b2, b3, this.initialSendWindowSize, this.initialReceiveWindowSize, remoteInitiatedId);
        if (remoteInitiatedId) {
            this.lastGoodStreamId = lastGoodStreamId;
        }
        return true;
    }
    
    private void halfCloseStream(final int n, final boolean b, final ChannelFuture channelFuture) {
        if (b) {
            this.spdySession.closeRemoteSide(n, this.isRemoteInitiatedId(n));
        }
        else {
            this.spdySession.closeLocalSide(n, this.isRemoteInitiatedId(n));
        }
        if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
            channelFuture.addListener((GenericFutureListener)this.closeSessionFutureListener);
        }
    }
    
    private void removeStream(final int n, final ChannelFuture channelFuture) {
        this.spdySession.removeStream(n, SpdySessionHandler.STREAM_CLOSED, this.isRemoteInitiatedId(n));
        if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
            channelFuture.addListener((GenericFutureListener)this.closeSessionFutureListener);
        }
    }
    
    private void updateSendWindowSize(final ChannelHandlerContext channelHandlerContext, final int n, final int n2) {
        this.spdySession.updateSendWindowSize(n, n2);
        while (true) {
            final SpdySession.PendingWrite pendingWrite = this.spdySession.getPendingWrite(n);
            if (pendingWrite == null) {
                return;
            }
            final SpdyDataFrame spdyDataFrame = pendingWrite.spdyDataFrame;
            final int readableBytes = spdyDataFrame.content().readableBytes();
            final int streamId = spdyDataFrame.streamId();
            final int min = Math.min(this.spdySession.getSendWindowSize(streamId), this.spdySession.getSendWindowSize(0));
            if (min <= 0) {
                return;
            }
            if (min < readableBytes) {
                this.spdySession.updateSendWindowSize(streamId, -1 * min);
                this.spdySession.updateSendWindowSize(0, -1 * min);
                channelHandlerContext.writeAndFlush(new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readSlice(min).retain())).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                    final ChannelHandlerContext val$ctx;
                    final SpdySessionHandler this$0;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            SpdySessionHandler.access$000(this.this$0, this.val$ctx, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
            else {
                this.spdySession.removePendingWrite(streamId);
                this.spdySession.updateSendWindowSize(streamId, -1 * readableBytes);
                this.spdySession.updateSendWindowSize(0, -1 * readableBytes);
                if (spdyDataFrame.isLast()) {
                    this.halfCloseStream(streamId, false, pendingWrite.promise);
                }
                channelHandlerContext.writeAndFlush(spdyDataFrame, pendingWrite.promise).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                    final ChannelHandlerContext val$ctx;
                    final SpdySessionHandler this$0;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            SpdySessionHandler.access$000(this.this$0, this.val$ctx, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
        }
    }
    
    private void sendGoAwayFrame(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        final ChannelFuture sendGoAwayFrame = this.sendGoAwayFrame(channelHandlerContext, SpdySessionStatus.OK);
        if (this.spdySession.noActiveStreams()) {
            sendGoAwayFrame.addListener((GenericFutureListener)new ClosingChannelFutureListener(channelHandlerContext, channelPromise));
        }
        else {
            this.closeSessionFutureListener = new ClosingChannelFutureListener(channelHandlerContext, channelPromise);
        }
    }
    
    private synchronized ChannelFuture sendGoAwayFrame(final ChannelHandlerContext channelHandlerContext, final SpdySessionStatus spdySessionStatus) {
        if (!this.sentGoAwayFrame) {
            this.sentGoAwayFrame = true;
            return channelHandlerContext.writeAndFlush(new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, spdySessionStatus));
        }
        return channelHandlerContext.newSucceededFuture();
    }
    
    static void access$000(final SpdySessionHandler spdySessionHandler, final ChannelHandlerContext channelHandlerContext, final SpdySessionStatus spdySessionStatus) {
        spdySessionHandler.issueSessionError(channelHandlerContext, spdySessionStatus);
    }
    
    static {
        PROTOCOL_EXCEPTION = new SpdyProtocolException();
        STREAM_CLOSED = new SpdyProtocolException("Stream closed");
        SpdySessionHandler.PROTOCOL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        SpdySessionHandler.STREAM_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    
    private static final class ClosingChannelFutureListener implements ChannelFutureListener
    {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        
        ClosingChannelFutureListener(final ChannelHandlerContext ctx, final ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
        }
        
        public void operationComplete(final ChannelFuture channelFuture) throws Exception {
            this.ctx.close(this.promise);
        }
        
        @Override
        public void operationComplete(final Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    }
}
