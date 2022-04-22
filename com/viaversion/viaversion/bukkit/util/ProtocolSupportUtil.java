package com.viaversion.viaversion.bukkit.util;

import java.lang.reflect.*;
import org.bukkit.entity.*;

public class ProtocolSupportUtil
{
    private static Method protocolVersionMethod;
    private static Method getIdMethod;
    
    public static int getProtocolVersion(final Player player) {
        if (ProtocolSupportUtil.protocolVersionMethod == null) {
            return -1;
        }
        return (int)ProtocolSupportUtil.getIdMethod.invoke(ProtocolSupportUtil.protocolVersionMethod.invoke(null, player), new Object[0]);
    }
    
    static {
        ProtocolSupportUtil.protocolVersionMethod = null;
        ProtocolSupportUtil.getIdMethod = null;
        ProtocolSupportUtil.protocolVersionMethod = Class.forName("protocolsupport.api.ProtocolSupportAPI").getMethod("getProtocolVersion", Player.class);
        ProtocolSupportUtil.getIdMethod = Class.forName("protocolsupport.api.ProtocolVersion").getMethod("getId", (Class<?>[])new Class[0]);
    }
}
