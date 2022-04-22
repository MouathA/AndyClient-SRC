package com.viaversion.viaversion.bukkit.platform;

import net.kyori.adventure.key.*;
import java.lang.reflect.*;
import io.netty.channel.*;
import com.viaversion.viaversion.bukkit.handlers.*;

public final class PaperViaInjector
{
    public static final boolean PAPER_INJECTION_METHOD;
    public static final boolean PAPER_PROTOCOL_METHOD;
    public static final boolean PAPER_PACKET_LIMITER;
    
    private PaperViaInjector() {
    }
    
    public static void setPaperChannelInitializeListener() throws ReflectiveOperationException {
        final Class<?> forName = Class.forName("io.papermc.paper.network.ChannelInitializeListener");
        Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder").getDeclaredMethod("addListener", Key.class, forName).invoke(null, Key.key("viaversion", "injector"), Proxy.newProxyInstance(BukkitViaInjector.class.getClassLoader(), new Class[] { forName }, PaperViaInjector::lambda$setPaperChannelInitializeListener$0));
    }
    
    public static void removePaperChannelInitializeListener() throws ReflectiveOperationException {
        Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder").getDeclaredMethod("removeListener", Key.class).invoke(null, Key.key("viaversion", "injector"));
    }
    
    private static boolean hasServerProtocolMethod() {
        Class.forName("org.bukkit.UnsafeValues").getDeclaredMethod("getProtocolVersion", (Class<?>[])new Class[0]);
        return true;
    }
    
    private static boolean hasPaperInjectionMethod() {
        return hasClass("io.papermc.paper.network.ChannelInitializeListener");
    }
    
    private static boolean hasPacketLimiter() {
        return hasClass("com.destroystokyo.paper.PaperConfig$PacketLimit") || hasClass("io.papermc.paper.PaperConfig$PacketLimit");
    }
    
    private static boolean hasClass(final String s) {
        Class.forName(s);
        return true;
    }
    
    private static Object lambda$setPaperChannelInitializeListener$0(final Object o, final Method method, final Object[] array) throws Throwable {
        if (method.getName().equals("afterInitChannel")) {
            BukkitChannelInitializer.afterChannelInitialize((Channel)array[0]);
            return null;
        }
        return method.invoke(o, array);
    }
    
    static {
        PAPER_INJECTION_METHOD = hasPaperInjectionMethod();
        PAPER_PROTOCOL_METHOD = hasServerProtocolMethod();
        PAPER_PACKET_LIMITER = hasPacketLimiter();
    }
}
