package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.*;
import net.md_5.bungee.api.connection.*;
import io.netty.buffer.*;
import net.md_5.bungee.api.config.*;
import com.viaversion.viaversion.bungee.service.*;

public class BungeeViaAPI extends ViaAPIBase
{
    public int getPlayerVersion(final ProxiedPlayer proxiedPlayer) {
        return this.getPlayerVersion(proxiedPlayer.getUniqueId());
    }
    
    public void sendRawPacket(final ProxiedPlayer proxiedPlayer, final ByteBuf byteBuf) throws IllegalArgumentException {
        this.sendRawPacket(proxiedPlayer.getUniqueId(), byteBuf);
    }
    
    public void probeServer(final ServerInfo serverInfo) {
        ProtocolDetectorService.probeServer(serverInfo);
    }
    
    @Override
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        this.sendRawPacket((ProxiedPlayer)o, byteBuf);
    }
    
    @Override
    public int getPlayerVersion(final Object o) {
        return this.getPlayerVersion((ProxiedPlayer)o);
    }
}
