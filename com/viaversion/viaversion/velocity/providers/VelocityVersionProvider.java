package com.viaversion.viaversion.velocity.providers;

import com.viaversion.viaversion.protocols.base.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import com.velocitypowered.api.proxy.*;
import com.viaversion.viaversion.velocity.service.*;
import com.velocitypowered.api.network.*;
import java.util.function.*;
import com.viaversion.viaversion.velocity.platform.*;
import com.viaversion.viaversion.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import java.util.stream.*;

public class VelocityVersionProvider extends BaseVersionProvider
{
    private static Method getAssociation;
    
    @Override
    public int getClosestServerProtocol(final UserConnection userConnection) throws Exception {
        return userConnection.isClientSide() ? this.getBackProtocol(userConnection) : this.getFrontProtocol(userConnection);
    }
    
    private int getBackProtocol(final UserConnection userConnection) throws Exception {
        return ProtocolDetectorService.getProtocolId(((ServerConnection)VelocityVersionProvider.getAssociation.invoke(userConnection.getChannel().pipeline().get("handler"), new Object[0])).getServerInfo().getName());
    }
    
    private int getFrontProtocol(final UserConnection userConnection) throws Exception {
        final int protocolVersion = userConnection.getProtocolInfo().getProtocolVersion();
        IntStream intStream = ProtocolVersion.SUPPORTED_VERSIONS.stream().mapToInt(ProtocolVersion::getProtocol);
        if (VelocityViaInjector.getPlayerInfoForwardingMode != null && ((Enum)VelocityViaInjector.getPlayerInfoForwardingMode.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
            intStream = intStream.filter(VelocityVersionProvider::lambda$getFrontProtocol$0);
        }
        final int[] array = intStream.toArray();
        if (Arrays.binarySearch(array, protocolVersion) >= 0) {
            return protocolVersion;
        }
        if (protocolVersion < array[0]) {
            return array[0];
        }
        for (int i = array.length - 1; i >= 0; --i) {
            final int n = array[i];
            if (protocolVersion > n && com.viaversion.viaversion.api.protocol.version.ProtocolVersion.isRegistered(n)) {
                return n;
            }
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + protocolVersion);
        return protocolVersion;
    }
    
    private static boolean lambda$getFrontProtocol$0(final int n) {
        return n >= com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion();
    }
    
    static {
        VelocityVersionProvider.getAssociation = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection").getMethod("getAssociation", (Class<?>[])new Class[0]);
    }
}
