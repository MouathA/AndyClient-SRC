package net.minecraft.realms;

import net.minecraft.client.*;
import java.net.*;
import net.minecraft.util.*;
import com.mojang.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;

public class Realms
{
    private static final String __OBFID;
    
    public static boolean isTouchScreen() {
        return Minecraft.getMinecraft().gameSettings.touchscreen;
    }
    
    public static Proxy getProxy() {
        return Minecraft.getMinecraft().getProxy();
    }
    
    public static String sessionId() {
        Minecraft.getMinecraft();
        final Session session = Minecraft.getSession();
        return (session == null) ? null : session.getSessionID();
    }
    
    public static String userName() {
        Minecraft.getMinecraft();
        final Session session = Minecraft.getSession();
        return (session == null) ? null : session.getUsername();
    }
    
    public static long currentTimeMillis() {
        return Minecraft.getSystemTime();
    }
    
    public static String getSessionId() {
        Minecraft.getMinecraft();
        return Minecraft.getSession().getSessionID();
    }
    
    public static String getName() {
        Minecraft.getMinecraft();
        return Minecraft.getSession().getUsername();
    }
    
    public static String uuidToName(final String s) {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(s), null), false).getName();
    }
    
    public static void setScreen(final RealmsScreen realmsScreen) {
        Minecraft.getMinecraft().displayGuiScreen(realmsScreen.getProxy());
    }
    
    public static String getGameDirectoryPath() {
        Minecraft.getMinecraft();
        return Minecraft.mcDataDir.getAbsolutePath();
    }
    
    public static int survivalId() {
        return WorldSettings.GameType.SURVIVAL.getID();
    }
    
    public static int creativeId() {
        return WorldSettings.GameType.CREATIVE.getID();
    }
    
    public static int adventureId() {
        return WorldSettings.GameType.ADVENTURE.getID();
    }
    
    static {
        __OBFID = "CL_00001892";
    }
}
