package com.viaversion.viaversion.velocity.handlers;

import java.lang.reflect.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;

public class VelocityChannelInitializer extends ChannelInitializer
{
    public static final String MINECRAFT_ENCODER = "minecraft-encoder";
    public static final String MINECRAFT_DECODER = "minecraft-decoder";
    public static final String VIA_ENCODER = "via-encoder";
    public static final String VIA_DECODER = "via-decoder";
    public static final Object COMPRESSION_ENABLED_EVENT;
    private static final Method INIT_CHANNEL;
    private final ChannelInitializer original;
    private final boolean clientSide;
    
    public VelocityChannelInitializer(final ChannelInitializer original, final boolean clientSide) {
        this.original = original;
        this.clientSide = clientSide;
    }
    
    @Override
    protected void initChannel(final Channel channel) throws Exception {
        VelocityChannelInitializer.INIT_CHANNEL.invoke(this.original, channel);
        final UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel, this.clientSide);
        new ProtocolPipelineImpl(userConnectionImpl);
        channel.pipeline().addBefore("minecraft-encoder", "via-encoder", new VelocityEncodeHandler(userConnectionImpl));
        channel.pipeline().addBefore("minecraft-decoder", "via-decoder", new VelocityDecodeHandler(userConnectionImpl));
    }
    
    static {
        (INIT_CHANNEL = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class)).setAccessible(true);
        COMPRESSION_ENABLED_EVENT = Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent").getDeclaredField("COMPRESSION_ENABLED").get(null);
    }
}
