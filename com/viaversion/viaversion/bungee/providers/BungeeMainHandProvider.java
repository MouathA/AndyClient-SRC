package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.lang.reflect.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.connection.*;
import net.md_5.bungee.api.connection.*;

public class BungeeMainHandProvider extends MainHandProvider
{
    private static Method getSettings;
    private static Method setMainHand;
    
    @Override
    public void setMainHand(final UserConnection userConnection, final int n) {
        final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (protocolInfo == null || protocolInfo.getUuid() == null) {
            return;
        }
        final ProxiedPlayer player = ProxyServer.getInstance().getPlayer(protocolInfo.getUuid());
        if (player == null) {
            return;
        }
        final Object invoke = BungeeMainHandProvider.getSettings.invoke(player, new Object[0]);
        if (invoke != null) {
            BungeeMainHandProvider.setMainHand.invoke(invoke, n);
        }
    }
    
    static {
        BungeeMainHandProvider.getSettings = null;
        BungeeMainHandProvider.setMainHand = null;
        BungeeMainHandProvider.getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", (Class<?>[])new Class[0]);
        BungeeMainHandProvider.setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", Integer.TYPE);
    }
}
