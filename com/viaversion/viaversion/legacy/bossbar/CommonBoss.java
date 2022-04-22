package com.viaversion.viaversion.legacy.bossbar;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.legacy.bossbar.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;

public class CommonBoss implements BossBar
{
    private final UUID uuid;
    private final Map connections;
    private final Set flags;
    private String title;
    private float health;
    private BossColor color;
    private BossStyle style;
    private boolean visible;
    
    public CommonBoss(final String title, final float health, final BossColor bossColor, final BossStyle bossStyle) {
        Preconditions.checkNotNull(title, (Object)"Title cannot be null");
        Preconditions.checkArgument(health >= 0.0f && health <= 1.0f, (Object)("Health must be between 0 and 1. Input: " + health));
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.health = health;
        this.color = ((bossColor == null) ? BossColor.PURPLE : bossColor);
        this.style = ((bossStyle == null) ? BossStyle.SOLID : bossStyle);
        this.connections = new MapMaker().weakValues().makeMap();
        this.flags = new HashSet();
        this.visible = true;
    }
    
    @Override
    public BossBar setTitle(final String title) {
        Preconditions.checkNotNull(title);
        this.title = title;
        this.sendPacket(UpdateAction.UPDATE_TITLE);
        return this;
    }
    
    @Override
    public BossBar setHealth(final float health) {
        Preconditions.checkArgument(health >= 0.0f && health <= 1.0f, (Object)("Health must be between 0 and 1. Input: " + health));
        this.health = health;
        this.sendPacket(UpdateAction.UPDATE_HEALTH);
        return this;
    }
    
    @Override
    public BossColor getColor() {
        return this.color;
    }
    
    @Override
    public BossBar setColor(final BossColor color) {
        Preconditions.checkNotNull(color);
        this.color = color;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }
    
    @Override
    public BossBar setStyle(final BossStyle style) {
        Preconditions.checkNotNull(style);
        this.style = style;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }
    
    @Override
    public BossBar addPlayer(final UUID uuid) {
        final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connectedClient != null) {
            this.addConnection(connectedClient);
        }
        return this;
    }
    
    @Override
    public BossBar addConnection(final UserConnection userConnection) {
        if (this.connections.put(userConnection.getProtocolInfo().getUuid(), userConnection) == null && this.visible) {
            this.sendPacketConnection(userConnection, this.getPacket(UpdateAction.ADD, userConnection));
        }
        return this;
    }
    
    @Override
    public BossBar removePlayer(final UUID uuid) {
        final UserConnection userConnection = this.connections.remove(uuid);
        if (userConnection != null) {
            this.sendPacketConnection(userConnection, this.getPacket(UpdateAction.REMOVE, userConnection));
        }
        return this;
    }
    
    @Override
    public BossBar removeConnection(final UserConnection userConnection) {
        this.removePlayer(userConnection.getProtocolInfo().getUuid());
        return this;
    }
    
    @Override
    public BossBar addFlag(final BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        if (!this.hasFlag(bossFlag)) {
            this.flags.add(bossFlag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }
    
    @Override
    public BossBar removeFlag(final BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        if (this.hasFlag(bossFlag)) {
            this.flags.remove(bossFlag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }
    
    @Override
    public boolean hasFlag(final BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        return this.flags.contains(bossFlag);
    }
    
    @Override
    public Set getPlayers() {
        return Collections.unmodifiableSet(this.connections.keySet());
    }
    
    @Override
    public Set getConnections() {
        return Collections.unmodifiableSet((Set<?>)new HashSet<Object>(this.connections.values()));
    }
    
    @Override
    public BossBar show() {
        this.setVisible(true);
        return this;
    }
    
    @Override
    public BossBar hide() {
        this.setVisible(false);
        return this;
    }
    
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    private void setVisible(final boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            this.sendPacket(visible ? UpdateAction.ADD : UpdateAction.REMOVE);
        }
    }
    
    @Override
    public UUID getId() {
        return this.uuid;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public float getHealth() {
        return this.health;
    }
    
    @Override
    public BossStyle getStyle() {
        return this.style;
    }
    
    public Set getFlags() {
        return this.flags;
    }
    
    private void sendPacket(final UpdateAction updateAction) {
        for (final UserConnection userConnection : new ArrayList<UserConnection>(this.connections.values())) {
            this.sendPacketConnection(userConnection, this.getPacket(updateAction, userConnection));
        }
    }
    
    private void sendPacketConnection(final UserConnection userConnection, final PacketWrapper packetWrapper) {
        if (userConnection.getProtocolInfo() == null || !userConnection.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
            this.connections.remove(userConnection.getProtocolInfo().getUuid());
            return;
        }
        packetWrapper.scheduleSend(Protocol1_9To1_8.class);
    }
    
    private PacketWrapper getPacket(final UpdateAction updateAction, final UserConnection userConnection) {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, userConnection);
        create.write(Type.UUID, this.uuid);
        create.write(Type.VAR_INT, updateAction.getId());
        switch (updateAction) {
            case ADD: {
                Protocol1_9To1_8.FIX_JSON.write(create, this.title);
                create.write(Type.FLOAT, this.health);
                create.write(Type.VAR_INT, this.color.getId());
                create.write(Type.VAR_INT, this.style.getId());
                create.write(Type.BYTE, (byte)this.flagToBytes());
            }
            case UPDATE_HEALTH: {
                create.write(Type.FLOAT, this.health);
                break;
            }
            case UPDATE_TITLE: {
                Protocol1_9To1_8.FIX_JSON.write(create, this.title);
                break;
            }
            case UPDATE_STYLE: {
                create.write(Type.VAR_INT, this.color.getId());
                create.write(Type.VAR_INT, this.style.getId());
                break;
            }
            case UPDATE_FLAGS: {
                create.write(Type.BYTE, (byte)this.flagToBytes());
                break;
            }
        }
        return create;
    }
    
    private int flagToBytes() {
        final Iterator<BossFlag> iterator = this.flags.iterator();
        while (iterator.hasNext()) {
            final int n = 0x0 | iterator.next().getId();
        }
        return 0;
    }
    
    private enum UpdateAction
    {
        ADD("ADD", 0, 0), 
        REMOVE("REMOVE", 1, 1), 
        UPDATE_HEALTH("UPDATE_HEALTH", 2, 2), 
        UPDATE_TITLE("UPDATE_TITLE", 3, 3), 
        UPDATE_STYLE("UPDATE_STYLE", 4, 4), 
        UPDATE_FLAGS("UPDATE_FLAGS", 5, 5);
        
        private final int id;
        private static final UpdateAction[] $VALUES;
        
        private UpdateAction(final String s, final int n, final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        static {
            $VALUES = new UpdateAction[] { UpdateAction.ADD, UpdateAction.REMOVE, UpdateAction.UPDATE_HEALTH, UpdateAction.UPDATE_TITLE, UpdateAction.UPDATE_STYLE, UpdateAction.UPDATE_FLAGS };
        }
    }
}
