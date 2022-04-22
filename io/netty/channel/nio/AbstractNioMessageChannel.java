package io.netty.channel.nio;

import java.nio.channels.*;
import java.util.*;
import java.io.*;
import io.netty.channel.*;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel
{
    protected AbstractNioMessageChannel(final Channel channel, final SelectableChannel selectableChannel, final int n) {
        super(channel, selectableChannel, n);
    }
    
    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe(null);
    }
    
    @Override
    protected void doWrite(final ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        final SelectionKey selectionKey = this.selectionKey();
        final int interestOps = selectionKey.interestOps();
        while (true) {
            final Object current = channelOutboundBuffer.current();
            if (current == null) {
                if ((interestOps & 0x4) != 0x0) {
                    selectionKey.interestOps(interestOps & 0xFFFFFFFB);
                    break;
                }
                break;
            }
            else {
                for (int n = this.config().getWriteSpinCount() - 1; n >= 0 && !this.doWriteMessage(current, channelOutboundBuffer); --n) {}
                if (!true) {
                    if ((interestOps & 0x4) == 0x0) {
                        selectionKey.interestOps(interestOps | 0x4);
                    }
                    break;
                }
                channelOutboundBuffer.remove();
            }
        }
    }
    
    protected boolean continueOnWriteError() {
        return false;
    }
    
    protected abstract int doReadMessages(final List p0) throws Exception;
    
    protected abstract boolean doWriteMessage(final Object p0, final ChannelOutboundBuffer p1) throws Exception;
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }
    
    private final class NioMessageUnsafe extends AbstractNioUnsafe
    {
        private final List readBuf;
        static final boolean $assertionsDisabled;
        final AbstractNioMessageChannel this$0;
        
        private NioMessageUnsafe(final AbstractNioMessageChannel this$0) {
            this.this$0 = this$0.super();
            this.readBuf = new ArrayList();
        }
        
        @Override
        public void read() {
            assert this.this$0.eventLoop().inEventLoop();
            final ChannelConfig config = this.this$0.config();
            if (!config.isAutoRead() && !this.this$0.isReadPending()) {
                this.removeReadOp();
                return;
            }
            final int maxMessagesPerRead = config.getMaxMessagesPerRead();
            final ChannelPipeline pipeline = this.this$0.pipeline();
            final Throwable t = null;
            do {
                final int doReadMessages = this.this$0.doReadMessages(this.readBuf);
                if (doReadMessages == 0) {
                    break;
                }
                if (doReadMessages < 0) {
                    break;
                }
                if (!config.isAutoRead()) {
                    break;
                }
            } while (this.readBuf.size() < maxMessagesPerRead);
            this.this$0.setReadPending(false);
            while (0 < this.readBuf.size()) {
                pipeline.fireChannelRead(this.readBuf.get(0));
                int n = 0;
                ++n;
            }
            this.readBuf.clear();
            pipeline.fireChannelReadComplete();
            if (t != null) {
                if (t instanceof IOException) {
                    final boolean b = !(this.this$0 instanceof ServerChannel);
                }
                pipeline.fireExceptionCaught(t);
            }
            if (true && this.this$0.isOpen()) {
                this.close(this.voidPromise());
            }
            if (!config.isAutoRead() && !this.this$0.isReadPending()) {
                this.removeReadOp();
            }
        }
        
        NioMessageUnsafe(final AbstractNioMessageChannel abstractNioMessageChannel, final AbstractNioMessageChannel$1 object) {
            this(abstractNioMessageChannel);
        }
        
        static {
            $assertionsDisabled = !AbstractNioMessageChannel.class.desiredAssertionStatus();
        }
    }
}
