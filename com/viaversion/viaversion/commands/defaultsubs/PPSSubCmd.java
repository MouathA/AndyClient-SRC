package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public class PPSSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "pps";
    }
    
    @Override
    public String description() {
        return "Shows the packets per second of online players";
    }
    
    @Override
    public String usage() {
        return "pps";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final HashMap<Integer, HashSet<String>> hashMap = new HashMap<Integer, HashSet<String>>();
        long packetsPerSecond = 0L;
        final ViaCommandSender[] onlinePlayers = Via.getPlatform().getOnlinePlayers();
        while (0 < onlinePlayers.length) {
            final ViaCommandSender viaCommandSender2 = onlinePlayers[0];
            final int playerVersion = Via.getAPI().getPlayerVersion(viaCommandSender2.getUUID());
            if (!hashMap.containsKey(playerVersion)) {
                hashMap.put(playerVersion, new HashSet<String>());
            }
            final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(viaCommandSender2.getUUID());
            if (connectedClient != null && connectedClient.getPacketTracker().getPacketsPerSecond() > -1L) {
                hashMap.get(playerVersion).add(viaCommandSender2.getName() + " (" + connectedClient.getPacketTracker().getPacketsPerSecond() + " PPS)");
                final int n = (int)(0 + connectedClient.getPacketTracker().getPacketsPerSecond());
                if (connectedClient.getPacketTracker().getPacketsPerSecond() > packetsPerSecond) {
                    packetsPerSecond = connectedClient.getPacketTracker().getPacketsPerSecond();
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        final TreeMap treeMap = new TreeMap<Integer, Object>(hashMap);
        ViaSubCommand.sendMessage(viaCommandSender, "&4Live Packets Per Second", new Object[0]);
        ViaSubCommand.sendMessage(viaCommandSender, "&cNo clients to display.", new Object[0]);
        for (final Map.Entry<Integer, V> entry : treeMap.entrySet()) {
            ViaSubCommand.sendMessage(viaCommandSender, "&8[&6%s&8]: &b%s", ProtocolVersion.getProtocol(entry.getKey()).getName(), entry.getValue());
        }
        treeMap.clear();
        return true;
    }
}
