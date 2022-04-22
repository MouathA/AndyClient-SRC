package com.viaversion.viaversion.bungee.handlers;

import java.lang.reflect.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;

public class BungeeChannelInitializer extends ChannelInitializer
{
    private final ChannelInitializer original;
    private Method method;
    
    public BungeeChannelInitializer(final ChannelInitializer original) {
        this.original = original;
        (this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class)).setAccessible(true);
    }
    
    @Override
    protected void initChannel(final Channel channel) throws Exception {
        if (!channel.isActive()) {
            return;
        }
        final UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
        new ProtocolPipelineImpl(userConnectionImpl);
        this.method.invoke(this.original, channel);
        if (!channel.isActive()) {
            return;
        }
        if (channel.pipeline().get("packet-encoder") == null) {
            return;
        }
        if (channel.pipeline().get("packet-decoder") == null) {
            return;
        }
        final BungeeEncodeHandler bungeeEncodeHandler = new BungeeEncodeHandler(userConnectionImpl);
        final BungeeDecodeHandler bungeeDecodeHandler = new BungeeDecodeHandler(userConnectionImpl);
        channel.pipeline().addBefore("packet-encoder", "via-encoder", bungeeEncodeHandler);
        channel.pipeline().addBefore("packet-decoder", "via-decoder", bungeeDecodeHandler);
    }
    
    public ChannelInitializer getOriginal() {
        return this.original;
    }
}
