package com.viaversion.viaversion.sponge.platform;

import org.spongepowered.api.*;
import com.viaversion.viaversion.platform.*;
import com.viaversion.viaversion.sponge.handlers.*;
import io.netty.channel.*;

public class SpongeViaInjector extends LegacyViaInjector
{
    @Override
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        final MinecraftVersion minecraftVersion = Sponge.platform().minecraftVersion();
        return (int)minecraftVersion.getClass().getDeclaredMethod("getProtocol", (Class<?>[])new Class[0]).invoke(minecraftVersion, new Object[0]);
    }
    
    @Override
    protected Object getServerConnection() throws ReflectiveOperationException {
        return Class.forName("net.minecraft.server.MinecraftServer").getDeclaredMethod("getConnection", (Class<?>[])new Class[0]).invoke(Sponge.server(), new Object[0]);
    }
    
    @Override
    protected WrappedChannelInitializer createChannelInitializer(final ChannelInitializer channelInitializer) {
        return new SpongeChannelInitializer(channelInitializer);
    }
    
    @Override
    protected void blame(final ChannelHandler channelHandler) {
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. Issue: " + channelHandler.getClass().getName());
    }
}
