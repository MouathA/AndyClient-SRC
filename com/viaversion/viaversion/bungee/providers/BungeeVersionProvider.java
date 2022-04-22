package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.protocols.base.*;
import com.viaversion.viaversion.util.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public class BungeeVersionProvider extends BaseVersionProvider
{
    private static Class ref;
    
    @Override
    public int getClosestServerProtocol(final UserConnection userConnection) throws Exception {
        if (BungeeVersionProvider.ref == null) {
            return super.getClosestServerProtocol(userConnection);
        }
        final ArrayList<Object> list = new ArrayList<Object>((Collection<?>)ReflectionUtil.getStatic(BungeeVersionProvider.ref, "SUPPORTED_VERSION_IDS", List.class));
        Collections.sort((List<Comparable>)list);
        final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (list.contains(protocolInfo.getProtocolVersion())) {
            return protocolInfo.getProtocolVersion();
        }
        if (protocolInfo.getProtocolVersion() < list.get(0)) {
            return getLowestSupportedVersion();
        }
        for (final Integer n : Lists.reverse(list)) {
            if (protocolInfo.getProtocolVersion() > n && ProtocolVersion.isRegistered(n)) {
                return n;
            }
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + protocolInfo.getProtocolVersion());
        return protocolInfo.getProtocolVersion();
    }
    
    public static int getLowestSupportedVersion() {
        return ((List)ReflectionUtil.getStatic(BungeeVersionProvider.ref, "SUPPORTED_VERSION_IDS", List.class)).get(0);
    }
    
    static {
        BungeeVersionProvider.ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
    }
}
