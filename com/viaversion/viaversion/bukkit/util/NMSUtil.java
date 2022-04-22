package com.viaversion.viaversion.bukkit.util;

import org.bukkit.*;

public class NMSUtil
{
    private static final String BASE;
    private static final String NMS;
    private static final boolean DEBUG_PROPERTY;
    
    private static boolean loadDebugProperty() {
        final Class nms = nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        return (boolean)nms.getMethod("isDebugging", (Class[])new Class[0]).invoke(nms.getDeclaredMethod("getServer", (Class[])new Class[0]).invoke(null, new Object[0]), new Object[0]);
    }
    
    public static Class nms(final String s) throws ClassNotFoundException {
        return Class.forName(NMSUtil.NMS + "." + s);
    }
    
    public static Class nms(final String s, final String s2) throws ClassNotFoundException {
        return Class.forName(NMSUtil.NMS + "." + s);
    }
    
    public static Class obc(final String s) throws ClassNotFoundException {
        return Class.forName(NMSUtil.BASE + "." + s);
    }
    
    public static String getVersion() {
        return NMSUtil.BASE.substring(NMSUtil.BASE.lastIndexOf(46) + 1);
    }
    
    public static boolean isDebugPropertySet() {
        return NMSUtil.DEBUG_PROPERTY;
    }
    
    static {
        BASE = Bukkit.getServer().getClass().getPackage().getName();
        NMS = NMSUtil.BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        DEBUG_PROPERTY = loadDebugProperty();
    }
}
