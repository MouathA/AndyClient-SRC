package com.viaversion.viaversion.bungee.handlers;

import net.md_5.bungee.api.plugin.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bungee.storage.*;
import com.viaversion.viaversion.bungee.service.*;
import net.md_5.bungee.event.*;
import net.md_5.bungee.api.event.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.*;
import net.md_5.bungee.protocol.packet.*;
import net.md_5.bungee.api.score.*;
import net.md_5.bungee.api.connection.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;

public class BungeeServerHandler implements Listener
{
    private static Method getHandshake;
    private static Method getRegisteredChannels;
    private static Method getBrandMessage;
    private static Method setProtocol;
    private static Method getEntityMap;
    private static Method setVersion;
    private static Field entityRewrite;
    private static Field channelWrapper;
    
    @EventHandler(priority = 120)
    public void onServerConnect(final ServerConnectEvent serverConnectEvent) {
        if (serverConnectEvent.isCancelled()) {
            return;
        }
        final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(serverConnectEvent.getPlayer().getUniqueId());
        if (connectedClient == null) {
            return;
        }
        if (!connectedClient.has(BungeeStorage.class)) {
            connectedClient.put(new BungeeStorage(serverConnectEvent.getPlayer()));
        }
        final int intValue = ProtocolDetectorService.getProtocolId(serverConnectEvent.getTarget().getName());
        BungeeServerHandler.setProtocol.invoke(BungeeServerHandler.getHandshake.invoke(serverConnectEvent.getPlayer().getPendingConnection(), new Object[0]), (Via.getManager().getProtocolManager().getProtocolPath(connectedClient.getProtocolInfo().getProtocolVersion(), intValue) == null) ? connectedClient.getProtocolInfo().getProtocolVersion() : intValue);
    }
    
    @EventHandler(priority = -120)
    public void onServerConnected(final ServerConnectedEvent serverConnectedEvent) {
        this.checkServerChange(serverConnectedEvent, Via.getManager().getConnectionManager().getConnectedClient(serverConnectedEvent.getPlayer().getUniqueId()));
    }
    
    @EventHandler(priority = -120)
    public void onServerSwitch(final ServerSwitchEvent serverSwitchEvent) {
        final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(serverSwitchEvent.getPlayer().getUniqueId());
        if (connectedClient == null) {
            return;
        }
        final int entityId = ((EntityIdProvider)Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(connectedClient);
        final Iterator iterator = connectedClient.getEntityTrackers().iterator();
        while (iterator.hasNext()) {
            iterator.next().setClientEntityId(entityId);
        }
        for (final StorableObject storableObject : connectedClient.getStoredObjects().values()) {
            if (storableObject instanceof ClientEntityIdChangeListener) {
                ((ClientEntityIdChangeListener)storableObject).setClientEntityId(entityId);
            }
        }
    }
    
    public void checkServerChange(final ServerConnectedEvent serverConnectedEvent, final UserConnection userConnection) throws Exception {
        if (userConnection == null) {
            return;
        }
        if (userConnection.has(BungeeStorage.class)) {
            final BungeeStorage bungeeStorage = (BungeeStorage)userConnection.get(BungeeStorage.class);
            final ProxiedPlayer player = bungeeStorage.getPlayer();
            if (serverConnectedEvent.getServer() != null && !serverConnectedEvent.getServer().getInfo().getName().equals(bungeeStorage.getCurrentServer())) {
                final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9 != null && entityTracker1_9.isAutoTeam() && entityTracker1_9.isTeamExists()) {
                    entityTracker1_9.sendTeamPacket(false, true);
                }
                final String name = serverConnectedEvent.getServer().getInfo().getName();
                bungeeStorage.setCurrentServer(name);
                int serverProtocolVersion = ProtocolDetectorService.getProtocolId(name);
                if (serverProtocolVersion <= ProtocolVersion.v1_8.getVersion() && bungeeStorage.getBossbar() != null) {
                    if (userConnection.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                        for (final UUID uuid : bungeeStorage.getBossbar()) {
                            final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, userConnection);
                            create.write(Type.UUID, uuid);
                            create.write(Type.VAR_INT, 1);
                            create.send(Protocol1_9To1_8.class);
                        }
                    }
                    bungeeStorage.getBossbar().clear();
                }
                final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
                final int serverProtocolVersion2 = protocolInfo.getServerProtocolVersion();
                final List protocolPath = Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), serverProtocolVersion);
                final ProtocolPipeline pipeline = userConnection.getProtocolInfo().getPipeline();
                userConnection.clearStoredObjects();
                pipeline.cleanPipes();
                if (protocolPath == null) {
                    serverProtocolVersion = protocolInfo.getProtocolVersion();
                }
                else {
                    final ArrayList list = new ArrayList<Protocol>(protocolPath.size());
                    final Iterator<ProtocolPathEntry> iterator2 = protocolPath.iterator();
                    while (iterator2.hasNext()) {
                        list.add(iterator2.next().protocol());
                    }
                    pipeline.add(list);
                }
                protocolInfo.setServerProtocolVersion(serverProtocolVersion);
                pipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(serverProtocolVersion));
                final int version = ProtocolVersion.v1_13.getVersion();
                final boolean b = serverProtocolVersion2 < version && serverProtocolVersion >= version;
                final boolean b2 = serverProtocolVersion2 >= version && serverProtocolVersion < version;
                if (serverProtocolVersion2 != -1 && (b || b2)) {
                    final Collection collection = (Collection)BungeeServerHandler.getRegisteredChannels.invoke(serverConnectedEvent.getPlayer().getPendingConnection(), new Object[0]);
                    if (!collection.isEmpty()) {
                        final HashSet<String> set = new HashSet<String>();
                        final Iterator<String> iterator3 = collection.iterator();
                        while (iterator3.hasNext()) {
                            final String s2;
                            final String s = s2 = iterator3.next();
                            String s3;
                            if (b) {
                                s3 = InventoryPackets.getNewPluginChannelId(s);
                            }
                            else {
                                s3 = InventoryPackets.getOldPluginChannelId(s);
                            }
                            if (s3 == null) {
                                iterator3.remove();
                            }
                            else {
                                if (s2.equals(s3)) {
                                    continue;
                                }
                                iterator3.remove();
                                set.add(s3);
                            }
                        }
                        collection.addAll(set);
                    }
                    final PluginMessage pluginMessage = (PluginMessage)BungeeServerHandler.getBrandMessage.invoke(serverConnectedEvent.getPlayer().getPendingConnection(), new Object[0]);
                    if (pluginMessage != null) {
                        final String tag = pluginMessage.getTag();
                        String tag2;
                        if (b) {
                            tag2 = InventoryPackets.getNewPluginChannelId(tag);
                        }
                        else {
                            tag2 = InventoryPackets.getOldPluginChannelId(tag);
                        }
                        if (tag2 != null) {
                            pluginMessage.setTag(tag2);
                        }
                    }
                }
                userConnection.put(bungeeStorage);
                userConnection.setActive(protocolPath != null);
                final Iterator iterator4 = pipeline.pipes().iterator();
                while (iterator4.hasNext()) {
                    iterator4.next().init(userConnection);
                }
                final EntityTracker1_9 entityTracker1_10 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_10 != null && Via.getConfig().isAutoTeam()) {
                    String name2 = null;
                    for (final Team team : player.getScoreboard().getTeams()) {
                        if (team.getPlayers().contains(protocolInfo.getUsername())) {
                            name2 = team.getName();
                        }
                    }
                    entityTracker1_10.setAutoTeam(true);
                    if (name2 == null) {
                        entityTracker1_10.sendTeamPacket(true, true);
                        entityTracker1_10.setCurrentTeam("viaversion");
                    }
                    else {
                        entityTracker1_10.setAutoTeam(Via.getConfig().isAutoTeam());
                        entityTracker1_10.setCurrentTeam(name2);
                    }
                }
                BungeeServerHandler.setVersion.invoke(BungeeServerHandler.channelWrapper.get(player), serverProtocolVersion);
                BungeeServerHandler.entityRewrite.set(player, BungeeServerHandler.getEntityMap.invoke(null, serverProtocolVersion));
            }
        }
    }
    
    static {
        BungeeServerHandler.getEntityMap = null;
        BungeeServerHandler.setVersion = null;
        BungeeServerHandler.entityRewrite = null;
        BungeeServerHandler.channelWrapper = null;
        BungeeServerHandler.getHandshake = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getHandshake", (Class<?>[])new Class[0]);
        BungeeServerHandler.getRegisteredChannels = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRegisteredChannels", (Class<?>[])new Class[0]);
        BungeeServerHandler.getBrandMessage = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getBrandMessage", (Class<?>[])new Class[0]);
        BungeeServerHandler.setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", Integer.TYPE);
        BungeeServerHandler.getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", Integer.TYPE);
        BungeeServerHandler.setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", Integer.TYPE);
        (BungeeServerHandler.channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch")).setAccessible(true);
        (BungeeServerHandler.entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite")).setAccessible(true);
    }
}
