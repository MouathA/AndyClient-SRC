package com.viaversion.viaversion.bukkit.platform;

import org.bukkit.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.util.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.platform.*;
import com.viaversion.viaversion.bukkit.handlers.*;
import org.bukkit.plugin.*;
import java.util.*;
import io.netty.channel.*;

public class BukkitViaInjector extends LegacyViaInjector
{
    private boolean protocolLib;
    
    @Override
    public void inject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return;
        }
        super.inject();
    }
    
    @Override
    public void uninject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return;
        }
        super.uninject();
    }
    
    @Override
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_PROTOCOL_METHOD) {
            return Bukkit.getUnsafe().getProtocolVersion();
        }
        final Class nms = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        final Object invokeStatic = ReflectionUtil.invokeStatic(nms, "getServer");
        final Class nms2 = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
        Object value = null;
        final Field[] declaredFields = nms.getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (field.getType() == nms2) {
                field.setAccessible(true);
                value = field.get(invokeStatic);
                break;
            }
            int n = 0;
            ++n;
        }
        final Class nms3 = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
        Object value2 = null;
        final Field[] declaredFields2 = nms2.getDeclaredFields();
        int n2 = 0;
        while (0 < declaredFields2.length) {
            final Field field2 = declaredFields2[0];
            if (field2.getType() == nms3) {
                field2.setAccessible(true);
                value2 = field2.get(value);
                break;
            }
            ++n2;
        }
        final Field[] declaredFields3 = nms3.getDeclaredFields();
        while (0 < declaredFields3.length) {
            final Field field3 = declaredFields3[0];
            if (field3.getType() == Integer.TYPE) {
                field3.setAccessible(true);
                final int intValue = (int)field3.get(value2);
                if (intValue != -1) {
                    return intValue;
                }
            }
            ++n2;
        }
        throw new RuntimeException("Failed to get server");
    }
    
    @Override
    public String getDecoderName() {
        return this.protocolLib ? "protocol_lib_decoder" : "decoder";
    }
    
    @Override
    protected Object getServerConnection() throws ReflectiveOperationException {
        final Class nms = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        final Class nms2 = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
        final Object invokeStatic = ReflectionUtil.invokeStatic(nms, "getServer");
        final Method[] declaredMethods = nms.getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method method = declaredMethods[0];
            if (method.getReturnType() == nms2) {
                if (method.getParameterTypes().length == 0) {
                    final Object invoke = method.invoke(invokeStatic, new Object[0]);
                    if (invoke != null) {
                        return invoke;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    protected WrappedChannelInitializer createChannelInitializer(final ChannelInitializer channelInitializer) {
        return new BukkitChannelInitializer(channelInitializer);
    }
    
    @Override
    protected void blame(final ChannelHandler channelHandler) throws ReflectiveOperationException {
        final ClassLoader classLoader = channelHandler.getClass().getClassLoader();
        if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
            throw new RuntimeException("Unable to inject, due to " + channelHandler.getClass().getName() + ", try without the plugin " + ((PluginDescriptionFile)ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class)).getName() + "?");
        }
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + channelHandler.getClass().getName());
    }
    
    public boolean isBinded() {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return true;
        }
        final Object serverConnection = this.getServerConnection();
        if (serverConnection == null) {
            return false;
        }
        final Field[] declaredFields = serverConnection.getClass().getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (List.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                final List list = (List)field.get(serverConnection);
                // monitorenter(list2 = list)
                if (!list.isEmpty() && list.get(0) instanceof ChannelFuture) {
                    // monitorexit(list2)
                    return true;
                }
            }
            // monitorexit(list2)
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public void setProtocolLib(final boolean protocolLib) {
        this.protocolLib = protocolLib;
    }
}
