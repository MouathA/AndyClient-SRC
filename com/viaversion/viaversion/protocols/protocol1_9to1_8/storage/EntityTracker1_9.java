package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.flare.fastutil.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.legacy.bossbar.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;

public class EntityTracker1_9 extends EntityTrackerBase
{
    public static final String WITHER_TRANSLATABLE = "{\"translate\":\"entity.WitherBoss.name\"}";
    public static final String DRAGON_TRANSLATABLE = "{\"translate\":\"entity.EnderDragon.name\"}";
    private final Int2ObjectMap uuidMap;
    private final Int2ObjectMap metadataBuffer;
    private final Int2ObjectMap vehicleMap;
    private final Int2ObjectMap bossBarMap;
    private final IntSet validBlocking;
    private final Set knownHolograms;
    private final Set blockInteractions;
    private boolean blocking;
    private boolean autoTeam;
    private Position currentlyDigging;
    private boolean teamExists;
    private GameMode gameMode;
    private String currentTeam;
    private int heldItemSlot;
    private Item itemInSecondHand;
    
    public EntityTracker1_9(final UserConnection userConnection) {
        super(userConnection, Entity1_10Types.EntityType.PLAYER);
        this.uuidMap = Int2ObjectSyncMap.hashmap();
        this.metadataBuffer = Int2ObjectSyncMap.hashmap();
        this.vehicleMap = Int2ObjectSyncMap.hashmap();
        this.bossBarMap = Int2ObjectSyncMap.hashmap();
        this.validBlocking = Int2ObjectSyncMap.hashset();
        this.knownHolograms = Int2ObjectSyncMap.hashset();
        this.blockInteractions = Collections.newSetFromMap((Map<Object, Boolean>)CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(250L, TimeUnit.MILLISECONDS).build().asMap());
        this.blocking = false;
        this.autoTeam = false;
        this.currentlyDigging = null;
        this.teamExists = false;
        this.itemInSecondHand = null;
    }
    
    public UUID getEntityUUID(final int n) {
        UUID randomUUID = (UUID)this.uuidMap.get(n);
        if (randomUUID == null) {
            randomUUID = UUID.randomUUID();
            this.uuidMap.put(n, randomUUID);
        }
        return randomUUID;
    }
    
    public void setSecondHand(final Item item) {
        this.setSecondHand(this.clientEntityId(), item);
    }
    
    public void setSecondHand(final int n, final Item itemInSecondHand) {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, null, this.user());
        create.write(Type.VAR_INT, n);
        create.write(Type.VAR_INT, 1);
        create.write(Type.ITEM, this.itemInSecondHand = itemInSecondHand);
        create.scheduleSend(Protocol1_9To1_8.class);
    }
    
    public Item getItemInSecondHand() {
        return this.itemInSecondHand;
    }
    
    public void syncShieldWithSword() {
        final boolean hasSwordInHand = this.hasSwordInHand();
        if (!hasSwordInHand || this.itemInSecondHand == null) {
            this.setSecondHand(hasSwordInHand ? new DataItem(442, (byte)1, (short)0, null) : null);
        }
    }
    
    public boolean hasSwordInHand() {
        return Protocol1_9To1_8.isSword(((InventoryTracker)this.user().get(InventoryTracker.class)).getItemId((short)0, (short)(this.heldItemSlot + 36)));
    }
    
    @Override
    public void removeEntity(final int n) {
        super.removeEntity(n);
        this.vehicleMap.remove(n);
        this.uuidMap.remove(n);
        this.validBlocking.remove(n);
        this.knownHolograms.remove(n);
        this.metadataBuffer.remove(n);
        final BossBar bossBar = (BossBar)this.bossBarMap.remove(n);
        if (bossBar != null) {
            bossBar.hide();
            ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleRemove(this.user(), bossBar.getId());
        }
    }
    
    public boolean interactedBlockRecently(final int n, final int n2, final int n3) {
        return this.blockInteractions.contains(new Position(n, (short)n2, n3));
    }
    
    public void addBlockInteraction(final Position position) {
        this.blockInteractions.add(position);
    }
    
    public void handleMetadata(final int n, final List list) {
        final EntityType entityType = this.entityType(n);
        if (entityType == null) {
            return;
        }
        for (final Metadata metadata : new ArrayList<Metadata>(list)) {
            if (entityType == Entity1_10Types.EntityType.WITHER && metadata.id() == 10) {
                list.remove(metadata);
            }
            if (entityType == Entity1_10Types.EntityType.ENDER_DRAGON && metadata.id() == 11) {
                list.remove(metadata);
            }
            if (entityType == Entity1_10Types.EntityType.SKELETON && this.getMetaByIndex(list, 12) == null) {
                list.add(new Metadata(12, MetaType1_9.Boolean, true));
            }
            if (entityType == Entity1_10Types.EntityType.HORSE && metadata.id() == 16 && (int)metadata.getValue() == Integer.MIN_VALUE) {
                metadata.setValue(0);
            }
            if (entityType == Entity1_10Types.EntityType.PLAYER) {
                if (metadata.id() == 0) {
                    final byte byteValue = (byte)metadata.getValue();
                    if (n != this.getProvidedEntityId() && Via.getConfig().isShieldBlocking()) {
                        if ((byteValue & 0x10) == 0x10) {
                            if (this.validBlocking.contains(n)) {
                                this.setSecondHand(n, new DataItem(442, (byte)1, (short)0, null));
                            }
                            else {
                                this.setSecondHand(n, null);
                            }
                        }
                        else {
                            this.setSecondHand(n, null);
                        }
                    }
                }
                if (metadata.id() == 12 && Via.getConfig().isLeftHandedHandling()) {
                    list.add(new Metadata(13, MetaType1_9.Byte, (byte)((((byte)metadata.getValue() & 0x80) == 0x0) ? 1 : 0)));
                }
            }
            if (entityType == Entity1_10Types.EntityType.ARMOR_STAND && Via.getConfig().isHologramPatch() && metadata.id() == 0 && this.getMetaByIndex(list, 10) != null) {
                final Metadata metaByIndex = this.getMetaByIndex(list, 10);
                final Metadata metaByIndex2;
                final Metadata metaByIndex3;
                if (((byte)metadata.getValue() & 0x20) == 0x20 && ((byte)metaByIndex.getValue() & 0x1) == 0x1 && (metaByIndex2 = this.getMetaByIndex(list, 2)) != null && !((String)metaByIndex2.getValue()).isEmpty() && (metaByIndex3 = this.getMetaByIndex(list, 3)) != null && (boolean)metaByIndex3.getValue() && !this.knownHolograms.contains(n)) {
                    this.knownHolograms.add(n);
                    final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_POSITION, null, this.user());
                    create.write(Type.VAR_INT, n);
                    create.write(Type.SHORT, 0);
                    create.write(Type.SHORT, (short)(128.0 * (Via.getConfig().getHologramYOffset() * 32.0)));
                    create.write(Type.SHORT, 0);
                    create.write(Type.BOOLEAN, true);
                    create.scheduleSend(Protocol1_9To1_8.class);
                }
            }
            if (Via.getConfig().isBossbarPatch() && (entityType == Entity1_10Types.EntityType.ENDER_DRAGON || entityType == Entity1_10Types.EntityType.WITHER)) {
                if (metadata.id() == 2) {
                    final BossBar bossBar = (BossBar)this.bossBarMap.get(n);
                    final String s = (String)metadata.getValue();
                    final String title = s.isEmpty() ? ((entityType == Entity1_10Types.EntityType.ENDER_DRAGON) ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}") : s;
                    if (bossBar == null) {
                        final BossBar legacyBossBar = Via.getAPI().legacyAPI().createLegacyBossBar(title, BossColor.PINK, BossStyle.SOLID);
                        this.bossBarMap.put(n, legacyBossBar);
                        legacyBossBar.addConnection(this.user());
                        legacyBossBar.show();
                        ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(this.user(), legacyBossBar.getId());
                    }
                    else {
                        bossBar.setTitle(title);
                    }
                }
                else {
                    if (metadata.id() != 6 || Via.getConfig().isBossbarAntiflicker()) {
                        continue;
                    }
                    final BossBar bossBar2 = (BossBar)this.bossBarMap.get(n);
                    final float max = Math.max(0.0f, Math.min((float)metadata.getValue() / ((entityType == Entity1_10Types.EntityType.ENDER_DRAGON) ? 200.0f : 300.0f), 1.0f));
                    if (bossBar2 == null) {
                        final BossBar legacyBossBar2 = Via.getAPI().legacyAPI().createLegacyBossBar((entityType == Entity1_10Types.EntityType.ENDER_DRAGON) ? "{\"translate\":\"entity.EnderDragon.name\"}" : "{\"translate\":\"entity.WitherBoss.name\"}", max, BossColor.PINK, BossStyle.SOLID);
                        this.bossBarMap.put(n, legacyBossBar2);
                        legacyBossBar2.addConnection(this.user());
                        legacyBossBar2.show();
                        ((BossBarProvider)Via.getManager().getProviders().get(BossBarProvider.class)).handleAdd(this.user(), legacyBossBar2.getId());
                    }
                    else {
                        bossBar2.setHealth(max);
                    }
                }
            }
        }
    }
    
    public Metadata getMetaByIndex(final List list, final int n) {
        for (final Metadata metadata : list) {
            if (n == metadata.id()) {
                return metadata;
            }
        }
        return null;
    }
    
    public void sendTeamPacket(final boolean teamExists, final boolean b) {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.TEAMS, null, this.user());
        create.write(Type.STRING, "viaversion");
        if (teamExists) {
            if (!this.teamExists) {
                create.write(Type.BYTE, 0);
                create.write(Type.STRING, "viaversion");
                create.write(Type.STRING, "§f");
                create.write(Type.STRING, "");
                create.write(Type.BYTE, 0);
                create.write(Type.STRING, "");
                create.write(Type.STRING, "never");
                create.write(Type.BYTE, 15);
            }
            else {
                create.write(Type.BYTE, 3);
            }
            create.write(Type.STRING_ARRAY, new String[] { this.user().getProtocolInfo().getUsername() });
        }
        else {
            create.write(Type.BYTE, 1);
        }
        this.teamExists = teamExists;
        if (b) {
            create.send(Protocol1_9To1_8.class);
        }
        else {
            create.scheduleSend(Protocol1_9To1_8.class);
        }
    }
    
    public void addMetadataToBuffer(final int n, final List list) {
        final List list2 = (List)this.metadataBuffer.get(n);
        if (list2 != null) {
            list2.addAll(list);
        }
        else {
            this.metadataBuffer.put(n, list);
        }
    }
    
    public void sendMetadataBuffer(final int n) {
        final List list = (List)this.metadataBuffer.get(n);
        if (list != null) {
            final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, null, this.user());
            create.write(Type.VAR_INT, n);
            create.write(Types1_9.METADATA_LIST, list);
            ((MetadataRewriter1_9To1_8)((Protocol1_9To1_8)Via.getManager().getProtocolManager().getProtocol(Protocol1_9To1_8.class)).get(MetadataRewriter1_9To1_8.class)).handleMetadata(n, list, this.user());
            this.handleMetadata(n, list);
            if (!list.isEmpty()) {
                create.scheduleSend(Protocol1_9To1_8.class);
            }
            this.metadataBuffer.remove(n);
        }
    }
    
    public int getProvidedEntityId() {
        return ((EntityIdProvider)Via.getManager().getProviders().get(EntityIdProvider.class)).getEntityId(this.user());
    }
    
    public Map getUuidMap() {
        return this.uuidMap;
    }
    
    public Map getMetadataBuffer() {
        return this.metadataBuffer;
    }
    
    public Map getVehicleMap() {
        return this.vehicleMap;
    }
    
    public Map getBossBarMap() {
        return this.bossBarMap;
    }
    
    public Set getValidBlocking() {
        return this.validBlocking;
    }
    
    public Set getKnownHolograms() {
        return this.knownHolograms;
    }
    
    public Set getBlockInteractions() {
        return this.blockInteractions;
    }
    
    public boolean isBlocking() {
        return this.blocking;
    }
    
    public void setBlocking(final boolean blocking) {
        this.blocking = blocking;
    }
    
    public boolean isAutoTeam() {
        return this.autoTeam;
    }
    
    public void setAutoTeam(final boolean autoTeam) {
        this.autoTeam = autoTeam;
    }
    
    public Position getCurrentlyDigging() {
        return this.currentlyDigging;
    }
    
    public void setCurrentlyDigging(final Position currentlyDigging) {
        this.currentlyDigging = currentlyDigging;
    }
    
    public boolean isTeamExists() {
        return this.teamExists;
    }
    
    public GameMode getGameMode() {
        return this.gameMode;
    }
    
    public void setGameMode(final GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
    public String getCurrentTeam() {
        return this.currentTeam;
    }
    
    public void setCurrentTeam(final String currentTeam) {
        this.currentTeam = currentTeam;
    }
    
    public void setHeldItemSlot(final int heldItemSlot) {
        this.heldItemSlot = heldItemSlot;
    }
}
