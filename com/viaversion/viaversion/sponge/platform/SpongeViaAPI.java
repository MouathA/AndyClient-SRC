package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.*;
import org.spongepowered.api.entity.living.player.*;
import io.netty.buffer.*;

public class SpongeViaAPI extends ViaAPIBase
{
    public int getPlayerVersion(final Player player) {
        return this.getPlayerVersion(player.uniqueId());
    }
    
    public void sendRawPacket(final Player player, final ByteBuf byteBuf) throws IllegalArgumentException {
        this.sendRawPacket(player.uniqueId(), byteBuf);
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
