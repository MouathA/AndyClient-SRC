package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.*;
import com.velocitypowered.api.proxy.*;
import io.netty.buffer.*;

public class VelocityViaAPI extends ViaAPIBase
{
    public int getPlayerVersion(final Player player) {
        return this.getPlayerVersion(player.getUniqueId());
    }
    
    public void sendRawPacket(final Player player, final ByteBuf byteBuf) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), byteBuf);
    }
    
    @Override
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        this.sendRawPacket((Player)o, byteBuf);
    }
    
    @Override
    public int getPlayerVersion(final Object o) {
        return this.getPlayerVersion((Player)o);
    }
}
