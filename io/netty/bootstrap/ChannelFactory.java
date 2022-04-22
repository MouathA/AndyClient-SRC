package io.netty.bootstrap;

import io.netty.channel.*;

public interface ChannelFactory
{
    Channel newChannel();
}
