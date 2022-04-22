package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.platform.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.bukkit.platform.*;
import com.viaversion.viaversion.bukkit.classgenerator.*;
import io.netty.handler.codec.*;
import io.netty.channel.*;
import com.viaversion.viaversion.classgenerator.generated.*;

public class BukkitChannelInitializer extends ChannelInitializer implements WrappedChannelInitializer
{
    private static final Method INIT_CHANNEL_METHOD;
    private final ChannelInitializer original;
    
    public BukkitChannelInitializer(final ChannelInitializer original) {
        this.original = original;
    }
    
    @Deprecated
    public ChannelInitializer getOriginal() {
        return this.original;
    }
    
    @Override
    protected void initChannel(final Channel channel) throws Exception {
        BukkitChannelInitializer.INIT_CHANNEL_METHOD.invoke(this.original, channel);
        afterChannelInitialize(channel);
    }
    
    public static void afterChannelInitialize(final Channel channel) {
        final UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
        new ProtocolPipelineImpl(userConnectionImpl);
        if (PaperViaInjector.PAPER_PACKET_LIMITER) {
            userConnectionImpl.setPacketLimiterEnabled(false);
        }
        final HandlerConstructor constructor = ClassGenerator.getConstructor();
        final MessageToByteEncoder encodeHandler = constructor.newEncodeHandler(userConnectionImpl, (MessageToByteEncoder)channel.pipeline().get("encoder"));
        final ByteToMessageDecoder decodeHandler = constructor.newDecodeHandler(userConnectionImpl, (ByteToMessageDecoder)channel.pipeline().get("decoder"));
        channel.pipeline().replace("encoder", "encoder", encodeHandler);
        channel.pipeline().replace("decoder", "decoder", decodeHandler);
    }
    
    @Override
    public ChannelInitializer original() {
        return this.original;
    }
    
    static {
        (INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class)).setAccessible(true);
    }
}
