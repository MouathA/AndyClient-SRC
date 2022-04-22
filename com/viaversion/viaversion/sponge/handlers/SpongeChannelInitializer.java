package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.platform.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.*;
import io.netty.channel.socket.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.handler.codec.*;
import io.netty.channel.*;

public class SpongeChannelInitializer extends ChannelInitializer implements WrappedChannelInitializer
{
    private static final Method INIT_CHANNEL_METHOD;
    private final ChannelInitializer original;
    
    public SpongeChannelInitializer(final ChannelInitializer original) {
        this.original = original;
    }
    
    @Override
    protected void initChannel(final Channel channel) throws Exception {
        if (Via.getAPI().getServerVersion().isKnown() && channel instanceof SocketChannel) {
            final UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
            new ProtocolPipelineImpl(userConnectionImpl);
            SpongeChannelInitializer.INIT_CHANNEL_METHOD.invoke(this.original, channel);
            final SpongeEncodeHandler spongeEncodeHandler = new SpongeEncodeHandler(userConnectionImpl, (MessageToByteEncoder)channel.pipeline().get("encoder"));
            final SpongeDecodeHandler spongeDecodeHandler = new SpongeDecodeHandler(userConnectionImpl, (ByteToMessageDecoder)channel.pipeline().get("decoder"));
            channel.pipeline().replace("encoder", "encoder", spongeEncodeHandler);
            channel.pipeline().replace("decoder", "decoder", spongeDecodeHandler);
        }
        else {
            SpongeChannelInitializer.INIT_CHANNEL_METHOD.invoke(this.original, channel);
        }
    }
    
    public ChannelInitializer getOriginal() {
        return this.original;
    }
    
    @Override
    public ChannelInitializer original() {
        return this.original;
    }
    
    static {
        (INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class)).setAccessible(true);
    }
}
