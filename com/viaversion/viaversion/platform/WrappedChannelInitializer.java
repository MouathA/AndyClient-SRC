package com.viaversion.viaversion.platform;

import io.netty.channel.*;

public interface WrappedChannelInitializer
{
    ChannelInitializer original();
}
