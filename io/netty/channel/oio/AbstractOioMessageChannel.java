package io.netty.channel.oio;

import java.util.*;
import java.io.*;
import io.netty.channel.*;

public abstract class AbstractOioMessageChannel extends AbstractOioChannel
{
    private final List readBuf;
    
    protected AbstractOioMessageChannel(final Channel channel) {
        super(channel);
        this.readBuf = new ArrayList();
    }
    
    @Override
    protected void doRead() {
        final ChannelConfig config = this.config();
        final ChannelPipeline pipeline = this.pipeline();
        config.getMaxMessagesPerRead();
        final Throwable t = null;
        this.doReadMessages(this.readBuf);
        while (0 < this.readBuf.size()) {
            pipeline.fireChannelRead(this.readBuf.get(0));
            int n = 0;
            ++n;
        }
        this.readBuf.clear();
        pipeline.fireChannelReadComplete();
        if (t != null) {
            if (t instanceof IOException) {}
            this.pipeline().fireExceptionCaught(t);
        }
        if (this.isOpen()) {
            this.unsafe().close(this.unsafe().voidPromise());
        }
    }
    
    protected abstract int doReadMessages(final List p0) throws Exception;
}
