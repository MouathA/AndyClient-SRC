package de.gerrygames.viarewind.protocol.protocol1_8to1_9.bossbar;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.legacy.bossbar.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.types.version.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;

public class WitherBossBar implements BossBar
{
    private final UUID uuid;
    private String title;
    private float health;
    private boolean visible;
    private UserConnection connection;
    private final int entityId;
    private double locX;
    private double locY;
    private double locZ;
    
    public WitherBossBar(final UserConnection connection, final UUID uuid, final String title, final float health) {
        this.visible = false;
        final int entityId = 2147473647;
        WitherBossBar.highestId = 2147473648;
        this.entityId = entityId;
        this.connection = connection;
        this.uuid = uuid;
        this.title = title;
        this.health = health;
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public BossBar setTitle(final String title) {
        this.title = title;
        if (this.visible) {
            this.updateMetadata();
        }
        return this;
    }
    
    @Override
    public float getHealth() {
        return this.health;
    }
    
    @Override
    public BossBar setHealth(final float health) {
        this.health = health;
        if (this.health <= 0.0f) {
            this.health = 1.0E-4f;
        }
        if (this.visible) {
            this.updateMetadata();
        }
        return this;
    }
    
    @Override
    public BossColor getColor() {
        return null;
    }
    
    @Override
    public BossBar setColor(final BossColor bossColor) {
        throw new UnsupportedOperationException(this.getClass().getName() + " does not support color");
    }
    
    @Override
    public BossStyle getStyle() {
        return null;
    }
    
    @Override
    public BossBar setStyle(final BossStyle bossStyle) {
        throw new UnsupportedOperationException(this.getClass().getName() + " does not support styles");
    }
    
    @Override
    public BossBar addPlayer(final UUID uuid) {
        throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
    }
    
    @Override
    public BossBar addConnection(final UserConnection userConnection) {
        throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
    }
    
    @Override
    public BossBar removePlayer(final UUID uuid) {
        throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
    }
    
    @Override
    public BossBar removeConnection(final UserConnection userConnection) {
        throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
    }
    
    @Override
    public BossBar addFlag(final BossFlag bossFlag) {
        throw new UnsupportedOperationException(this.getClass().getName() + " does not support flags");
    }
    
    @Override
    public BossBar removeFlag(final BossFlag bossFlag) {
        throw new UnsupportedOperationException(this.getClass().getName() + " does not support flags");
    }
    
    @Override
    public boolean hasFlag(final BossFlag bossFlag) {
        return false;
    }
    
    @Override
    public Set getPlayers() {
        return Collections.singleton(this.connection.getProtocolInfo().getUuid());
    }
    
    @Override
    public Set getConnections() {
        throw new UnsupportedOperationException(this.getClass().getName() + " is only for one UserConnection!");
    }
    
    @Override
    public BossBar show() {
        if (!this.visible) {
            this.visible = true;
            this.spawnWither();
        }
        return this;
    }
    
    @Override
    public BossBar hide() {
        if (this.visible) {
            this.visible = false;
            this.despawnWither();
        }
        return this;
    }
    
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    @Override
    public UUID getId() {
        return this.uuid;
    }
    
    public void setLocation(final double locX, final double locY, final double locZ) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.updateLocation();
    }
    
    private void spawnWither() {
        final PacketWrapper create = PacketWrapper.create(15, null, this.connection);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.UNSIGNED_BYTE, 64);
        create.write(Type.INT, (int)(this.locX * 32.0));
        create.write(Type.INT, (int)(this.locY * 32.0));
        create.write(Type.INT, (int)(this.locZ * 32.0));
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        list.add(new Metadata(0, MetaType1_8.Byte, 32));
        list.add(new Metadata(2, MetaType1_8.String, this.title));
        list.add(new Metadata(3, MetaType1_8.Byte, 1));
        list.add(new Metadata(6, MetaType1_8.Float, this.health * 300.0f));
        create.write(Types1_8.METADATA_LIST, list);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, false);
    }
    
    private void updateLocation() {
        final PacketWrapper create = PacketWrapper.create(24, null, this.connection);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.INT, (int)(this.locX * 32.0));
        create.write(Type.INT, (int)(this.locY * 32.0));
        create.write(Type.INT, (int)(this.locZ * 32.0));
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BOOLEAN, false);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, false);
    }
    
    private void updateMetadata() {
        final PacketWrapper create = PacketWrapper.create(28, null, this.connection);
        create.write(Type.VAR_INT, this.entityId);
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        list.add(new Metadata(2, MetaType1_8.String, this.title));
        list.add(new Metadata(6, MetaType1_8.Float, this.health * 300.0f));
        create.write(Types1_8.METADATA_LIST, list);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, false);
    }
    
    private void despawnWither() {
        final PacketWrapper create = PacketWrapper.create(19, null, this.connection);
        create.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.entityId });
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, false);
    }
    
    public void setPlayerLocation(double n, double n2, double n3, final float n4, final float n5) {
        final double radians = Math.toRadians(n4);
        final double radians2 = Math.toRadians(n5);
        n -= Math.cos(radians2) * Math.sin(radians) * 48.0;
        n2 -= Math.sin(radians2) * 48.0;
        n3 += Math.cos(radians2) * Math.cos(radians) * 48.0;
        this.setLocation(n, n2, n3);
    }
}
