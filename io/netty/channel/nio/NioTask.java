package io.netty.channel.nio;

import java.nio.channels.*;

public interface NioTask
{
    void channelReady(final SelectableChannel p0, final SelectionKey p1) throws Exception;
    
    void channelUnregistered(final SelectableChannel p0, final Throwable p1) throws Exception;
}
