package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.*;
import java.util.function.*;
import java.util.*;

public class ListSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "list";
    }
    
    @Override
    public String description() {
        return "Shows lists of the versions from logged in players";
    }
    
    @Override
    public String usage() {
        return "list";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final TreeMap<ProtocolVersion, Set<String>> treeMap = new TreeMap<ProtocolVersion, Set<String>>(ListSubCmd::lambda$execute$0);
        final ViaCommandSender[] onlinePlayers = Via.getPlatform().getOnlinePlayers();
        while (0 < onlinePlayers.length) {
            final ViaCommandSender viaCommandSender2 = onlinePlayers[0];
            ((Set<String>)treeMap.computeIfAbsent(ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(viaCommandSender2.getUUID())), (Function<? super ProtocolVersion, ?>)ListSubCmd::lambda$execute$1)).add(viaCommandSender2.getName());
            int n = 0;
            ++n;
        }
        for (final Map.Entry<ProtocolVersion, Set<String>> entry : treeMap.entrySet()) {
            ViaSubCommand.sendMessage(viaCommandSender, "&8[&6%s&8] (&7%d&8): &b%s", entry.getKey().getName(), entry.getValue().size(), entry.getValue());
        }
        treeMap.clear();
        return true;
    }
    
    private static Set lambda$execute$1(final ProtocolVersion protocolVersion) {
        return new HashSet();
    }
    
    private static int lambda$execute$0(final ProtocolVersion protocolVersion, final ProtocolVersion protocolVersion2) {
        return ProtocolVersion.getIndex(protocolVersion2) - ProtocolVersion.getIndex(protocolVersion);
    }
}
