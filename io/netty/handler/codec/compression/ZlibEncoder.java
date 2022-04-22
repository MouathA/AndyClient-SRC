package io.netty.handler.codec.compression;

import io.netty.handler.codec.*;
import io.netty.channel.*;

public abstract class ZlibEncoder extends MessageToByteEncoder
{
    protected ZlibEncoder() {
        super(false);
    }
    
    public abstract boolean isClosed();
    
    public abstract ChannelFuture close();
    
    public abstract ChannelFuture close(final ChannelPromise p0);
}
