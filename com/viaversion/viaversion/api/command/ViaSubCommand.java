package com.viaversion.viaversion.api.command;

import java.util.*;
import com.viaversion.viaversion.util.*;

public abstract class ViaSubCommand
{
    public abstract String name();
    
    public abstract String description();
    
    public String usage() {
        return this.name();
    }
    
    public String permission() {
        return "viaversion.admin";
    }
    
    public abstract boolean execute(final ViaCommandSender p0, final String[] p1);
    
    public List onTabComplete(final ViaCommandSender viaCommandSender, final String[] array) {
        return Collections.emptyList();
    }
    
    public static String color(final String s) {
        return ChatColorUtil.translateAlternateColorCodes(s);
    }
    
    public static void sendMessage(final ViaCommandSender viaCommandSender, final String s, final Object... array) {
        viaCommandSender.sendMessage(color((array == null) ? s : String.format(s, array)));
    }
}
