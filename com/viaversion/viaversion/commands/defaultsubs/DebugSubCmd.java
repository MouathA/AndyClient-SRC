package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.debug.*;
import java.util.*;

public class DebugSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "debug";
    }
    
    @Override
    public String description() {
        return "Toggle debug mode";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final DebugHandler debugHandler = Via.getManager().debugHandler();
        if (array.length == 0) {
            Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
            ViaSubCommand.sendMessage(viaCommandSender, "&6Debug mode is now %s", Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled");
            return true;
        }
        if (array.length == 1) {
            if (array[0].equalsIgnoreCase("clear")) {
                debugHandler.clearPacketTypesToLog();
                ViaSubCommand.sendMessage(viaCommandSender, "&6Cleared packet types to log", new Object[0]);
                return true;
            }
            if (array[0].equalsIgnoreCase("logposttransform")) {
                debugHandler.setLogPostPacketTransform(!debugHandler.logPostPacketTransform());
                ViaSubCommand.sendMessage(viaCommandSender, "&6Post transform packet logging is now %s", debugHandler.logPostPacketTransform() ? "&aenabled" : "&cdisabled");
                return true;
            }
        }
        else if (array.length == 2) {
            if (array[0].equalsIgnoreCase("add")) {
                debugHandler.addPacketTypeNameToLog(array[1].toUpperCase(Locale.ROOT));
                ViaSubCommand.sendMessage(viaCommandSender, "&6Added packet type %s to debug logging", array[1]);
                return true;
            }
            if (array[0].equalsIgnoreCase("remove")) {
                debugHandler.removePacketTypeNameToLog(array[1].toUpperCase(Locale.ROOT));
                ViaSubCommand.sendMessage(viaCommandSender, "&6Removed packet type %s from debug logging", array[1]);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public List onTabComplete(final ViaCommandSender viaCommandSender, final String[] array) {
        if (array.length == 1) {
            return Arrays.asList("clear", "logposttransform", "add", "remove");
        }
        return Collections.emptyList();
    }
}
