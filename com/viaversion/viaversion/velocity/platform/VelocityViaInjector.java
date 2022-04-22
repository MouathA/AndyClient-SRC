package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.platform.*;
import java.lang.reflect.*;
import io.netty.channel.*;
import com.viaversion.viaversion.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.velocity.handlers.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.velocitypowered.api.network.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class VelocityViaInjector implements ViaInjector
{
    public static Method getPlayerInfoForwardingMode;
    
    private ChannelInitializer getInitializer() throws Exception {
        return (ChannelInitializer)ReflectionUtil.invoke(ReflectionUtil.invoke(ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class), "getServerChannelInitializer"), "get");
    }
    
    private ChannelInitializer getBackendInitializer() throws Exception {
        return (ChannelInitializer)ReflectionUtil.invoke(ReflectionUtil.invoke(ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class), "getBackendChannelInitializer"), "get");
    }
    
    @Override
    public void inject() throws Exception {
        Via.getPlatform().getLogger().info("Replacing channel initializers; you can safely ignore the following two warnings.");
        final Object value = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        final Object invoke = ReflectionUtil.invoke(value, "getServerChannelInitializer");
        invoke.getClass().getMethod("set", ChannelInitializer.class).invoke(invoke, new VelocityChannelInitializer(this.getInitializer(), false));
        final Object invoke2 = ReflectionUtil.invoke(value, "getBackendChannelInitializer");
        invoke2.getClass().getMethod("set", ChannelInitializer.class).invoke(invoke2, new VelocityChannelInitializer(this.getBackendInitializer(), true));
    }
    
    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
    }
    
    @Override
    public int getServerProtocolVersion() throws Exception {
        return getLowestSupportedProtocolVersion();
    }
    
    @Override
    public IntSortedSet getServerProtocolVersions() throws Exception {
        final int lowestSupportedProtocolVersion = getLowestSupportedProtocolVersion();
        final IntLinkedOpenHashSet set = new IntLinkedOpenHashSet();
        for (final ProtocolVersion protocolVersion : ProtocolVersion.SUPPORTED_VERSIONS) {
            if (protocolVersion.getProtocol() >= lowestSupportedProtocolVersion) {
                set.add(protocolVersion.getProtocol());
            }
        }
        return set;
    }
    
    public static int getLowestSupportedProtocolVersion() {
        if (VelocityViaInjector.getPlayerInfoForwardingMode != null && ((Enum)VelocityViaInjector.getPlayerInfoForwardingMode.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
            return com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion();
        }
        return ProtocolVersion.MINIMUM_VERSION.getProtocol();
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("currentInitializer", this.getInitializer().getClass().getName());
        return jsonObject;
    }
    
    static {
        VelocityViaInjector.getPlayerInfoForwardingMode = Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode", (Class<?>[])new Class[0]);
    }
}
