package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;

public class EntityTracker extends StoredObject implements ClientEntityIdChangeListener
{
    private final Map clientEntityTypes;
    private final Map metadataBuffer;
    private final Map vehicles;
    private final Map entityReplacements;
    private final Map playersByEntityId;
    private final Map playersByUniqueId;
    private final Map playerEquipment;
    private int gamemode;
    private int playerId;
    private int spectating;
    private int dimension;
    
    public EntityTracker(final UserConnection userConnection) {
        super(userConnection);
        this.clientEntityTypes = new ConcurrentHashMap();
        this.metadataBuffer = new ConcurrentHashMap();
        this.vehicles = new ConcurrentHashMap();
        this.entityReplacements = new ConcurrentHashMap();
        this.playersByEntityId = new HashMap();
        this.playersByUniqueId = new HashMap();
        this.playerEquipment = new HashMap();
        this.gamemode = 0;
        this.playerId = -1;
        this.spectating = -1;
        this.dimension = 0;
    }
    
    public void removeEntity(final int n) {
        this.clientEntityTypes.remove(n);
        if (this.entityReplacements.containsKey(n)) {
            this.entityReplacements.remove(n).despawn();
        }
        if (this.playersByEntityId.containsKey(n)) {
            this.playersByUniqueId.remove(this.playersByEntityId.remove(n));
        }
    }
    
    public void addPlayer(final Integer n, final UUID uuid) {
        this.playersByUniqueId.put(uuid, n);
        this.playersByEntityId.put(n, uuid);
    }
    
    public UUID getPlayerUUID(final int n) {
        return this.playersByEntityId.get(n);
    }
    
    public int getPlayerEntityId(final UUID uuid) {
        return this.playersByUniqueId.getOrDefault(uuid, -1);
    }
    
    public Item[] getPlayerEquipment(final UUID uuid) {
        return this.playerEquipment.get(uuid);
    }
    
    public void setPlayerEquipment(final UUID uuid, final Item[] array) {
        this.playerEquipment.put(uuid, array);
    }
    
    public Map getClientEntityTypes() {
        return this.clientEntityTypes;
    }
    
    public void addMetadataToBuffer(final int n, final List list) {
        if (this.metadataBuffer.containsKey(n)) {
            this.metadataBuffer.get(n).addAll(list);
        }
        else if (!list.isEmpty()) {
            this.metadataBuffer.put(n, list);
        }
    }
    
    public void addEntityReplacement(final EntityReplacement entityReplacement) {
        this.entityReplacements.put(entityReplacement.getEntityId(), entityReplacement);
    }
    
    public EntityReplacement getEntityReplacement(final int n) {
        return this.entityReplacements.get(n);
    }
    
    public List getBufferedMetadata(final int n) {
        return this.metadataBuffer.get(n);
    }
    
    public void sendMetadataBuffer(final int n) {
        if (!this.metadataBuffer.containsKey(n)) {
            return;
        }
        if (this.entityReplacements.containsKey(n)) {
            this.entityReplacements.get(n).updateMetadata(this.metadataBuffer.remove(n));
        }
        else {
            final Entity1_10Types.EntityType entityType = this.getClientEntityTypes().get(n);
            final PacketWrapper create = PacketWrapper.create(28, null, this.getUser());
            create.write(Type.VAR_INT, n);
            create.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(n));
            MetadataRewriter.transform(entityType, this.metadataBuffer.get(n));
            if (!this.metadataBuffer.get(n).isEmpty()) {
                PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
            }
            this.metadataBuffer.remove(n);
        }
    }
    
    public int getVehicle(final int n) {
        for (final Map.Entry<K, Integer> entry : this.vehicles.entrySet()) {
            if (entry.getValue() == n) {
                return entry.getValue();
            }
        }
        return -1;
    }
    
    public int getPassenger(final int n) {
        return this.vehicles.getOrDefault(n, -1);
    }
    
    public void setPassenger(final int n, final int n2) {
        if (n == this.spectating && this.spectating != this.playerId) {
            final PacketWrapper create = PacketWrapper.create(11, null, this.getUser());
            create.write(Type.VAR_INT, this.playerId);
            create.write(Type.VAR_INT, 0);
            create.write(Type.VAR_INT, 0);
            final PacketWrapper create2 = PacketWrapper.create(11, null, this.getUser());
            create2.write(Type.VAR_INT, this.playerId);
            create2.write(Type.VAR_INT, 1);
            create2.write(Type.VAR_INT, 0);
            PacketUtil.sendToServer(create, Protocol1_7_6_10TO1_8.class, true, true);
            this.setSpectating(this.playerId);
        }
        if (n == -1) {
            this.vehicles.remove(this.getVehicle(n2));
        }
        else if (n2 == -1) {
            this.vehicles.remove(n);
        }
        else {
            this.vehicles.put(n, n2);
        }
    }
    
    public int getSpectating() {
        return this.spectating;
    }
    
    public boolean setSpectating(final int spectating) {
        if (spectating != this.playerId && this.getPassenger(spectating) != -1) {
            final PacketWrapper create = PacketWrapper.create(11, null, this.getUser());
            create.write(Type.VAR_INT, this.playerId);
            create.write(Type.VAR_INT, 0);
            create.write(Type.VAR_INT, 0);
            final PacketWrapper create2 = PacketWrapper.create(11, null, this.getUser());
            create2.write(Type.VAR_INT, this.playerId);
            create2.write(Type.VAR_INT, 1);
            create2.write(Type.VAR_INT, 0);
            PacketUtil.sendToServer(create, Protocol1_7_6_10TO1_8.class, true, true);
            this.setSpectating(this.playerId);
            return false;
        }
        if (this.spectating != spectating && this.spectating != this.playerId) {
            final PacketWrapper create3 = PacketWrapper.create(27, null, this.getUser());
            create3.write(Type.INT, this.playerId);
            create3.write(Type.INT, -1);
            create3.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(create3, Protocol1_7_6_10TO1_8.class);
        }
        if ((this.spectating = spectating) != this.playerId) {
            final PacketWrapper create4 = PacketWrapper.create(27, null, this.getUser());
            create4.write(Type.INT, this.playerId);
            create4.write(Type.INT, this.spectating);
            create4.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(create4, Protocol1_7_6_10TO1_8.class);
        }
        return true;
    }
    
    public int getGamemode() {
        return this.gamemode;
    }
    
    public void setGamemode(final int gamemode) {
        this.gamemode = gamemode;
    }
    
    public int getPlayerId() {
        return this.playerId;
    }
    
    public void setPlayerId(final int n) {
        this.spectating = n;
        this.playerId = n;
    }
    
    public void clearEntities() {
        this.clientEntityTypes.clear();
        this.entityReplacements.clear();
        this.vehicles.clear();
        this.metadataBuffer.clear();
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public void setDimension(final int dimension) {
        this.dimension = dimension;
    }
    
    @Override
    public void setClientEntityId(final int n) {
        if (this.spectating == this.playerId) {
            this.spectating = n;
        }
        this.clientEntityTypes.remove(this.playerId);
        this.playerId = n;
        this.clientEntityTypes.put(this.playerId, Entity1_10Types.EntityType.ENTITY_HUMAN);
    }
}
