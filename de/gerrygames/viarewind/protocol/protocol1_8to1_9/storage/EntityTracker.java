package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;

public class EntityTracker extends StoredObject implements ClientEntityIdChangeListener
{
    private final Map vehicleMap;
    private final Map clientEntityTypes;
    private final Map metadataBuffer;
    private final Map entityReplacements;
    private final Map entityOffsets;
    private int playerId;
    private int playerGamemode;
    
    public EntityTracker(final UserConnection userConnection) {
        super(userConnection);
        this.vehicleMap = new ConcurrentHashMap();
        this.clientEntityTypes = new ConcurrentHashMap();
        this.metadataBuffer = new ConcurrentHashMap();
        this.entityReplacements = new ConcurrentHashMap();
        this.entityOffsets = new ConcurrentHashMap();
        this.playerGamemode = 0;
    }
    
    public void setPlayerId(final int playerId) {
        this.playerId = playerId;
    }
    
    public int getPlayerId() {
        return this.playerId;
    }
    
    public int getPlayerGamemode() {
        return this.playerGamemode;
    }
    
    public void setPlayerGamemode(final int playerGamemode) {
        this.playerGamemode = playerGamemode;
    }
    
    public void removeEntity(final int n) {
        this.vehicleMap.remove(n);
        this.vehicleMap.forEach(EntityTracker::lambda$removeEntity$0);
        this.vehicleMap.entrySet().removeIf(EntityTracker::lambda$removeEntity$1);
        this.clientEntityTypes.remove(n);
        this.entityOffsets.remove(n);
        if (this.entityReplacements.containsKey(n)) {
            this.entityReplacements.remove(n).despawn();
        }
    }
    
    public void resetEntityOffset(final int n) {
        this.entityOffsets.remove(n);
    }
    
    public Vector getEntityOffset(final int n) {
        return this.entityOffsets.computeIfAbsent(n, EntityTracker::lambda$getEntityOffset$2);
    }
    
    public void addToEntityOffset(final int n, final short n2, final short n3, final short n4) {
        this.entityOffsets.compute(n, EntityTracker::lambda$addToEntityOffset$3);
    }
    
    public void setEntityOffset(final int n, final short n2, final short n3, final short n4) {
        this.entityOffsets.compute(n, EntityTracker::lambda$setEntityOffset$4);
    }
    
    public void setEntityOffset(final int n, final Vector vector) {
        this.entityOffsets.put(n, vector);
    }
    
    public List getPassengers(final int n) {
        return this.vehicleMap.getOrDefault(n, new ArrayList());
    }
    
    public void setPassengers(final int n, final List list) {
        this.vehicleMap.put(n, list);
    }
    
    public void addEntityReplacement(final EntityReplacement entityReplacement) {
        this.entityReplacements.put(entityReplacement.getEntityId(), entityReplacement);
    }
    
    public EntityReplacement getEntityReplacement(final int n) {
        return this.entityReplacements.get(n);
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
    
    public List getBufferedMetadata(final int n) {
        return this.metadataBuffer.get(n);
    }
    
    public boolean isInsideVehicle(final int n) {
        final Iterator<List> iterator = this.vehicleMap.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(n)) {
                return true;
            }
        }
        return false;
    }
    
    public int getVehicle(final int n) {
        for (final Map.Entry<K, List> entry : this.vehicleMap.entrySet()) {
            if (entry.getValue().contains(n)) {
                return (int)entry.getKey();
            }
        }
        return -1;
    }
    
    public boolean isPassenger(final int n, final int n2) {
        return this.vehicleMap.containsKey(n) && this.vehicleMap.get(n).contains(n2);
    }
    
    public void sendMetadataBuffer(final int n) {
        if (!this.metadataBuffer.containsKey(n)) {
            return;
        }
        if (this.entityReplacements.containsKey(n)) {
            this.entityReplacements.get(n).updateMetadata(this.metadataBuffer.remove(n));
        }
        else {
            final PacketWrapper create = PacketWrapper.create(28, null, this.getUser());
            create.write(Type.VAR_INT, n);
            create.write(Types1_8.METADATA_LIST, this.metadataBuffer.get(n));
            MetadataRewriter.transform(this.getClientEntityTypes().get(n), this.metadataBuffer.get(n));
            if (!this.metadataBuffer.get(n).isEmpty()) {
                create.send(Protocol1_8TO1_9.class);
            }
            this.metadataBuffer.remove(n);
        }
    }
    
    @Override
    public void setClientEntityId(final int playerId) {
        this.clientEntityTypes.remove(this.playerId);
        this.playerId = playerId;
        this.clientEntityTypes.put(this.playerId, Entity1_10Types.EntityType.ENTITY_HUMAN);
    }
    
    private static Vector lambda$setEntityOffset$4(final short blockX, final short blockY, final short blockZ, final Integer n, final Vector vector) {
        if (vector == null) {
            return new Vector(blockX, blockY, blockZ);
        }
        vector.setBlockX(blockX);
        vector.setBlockY(blockY);
        vector.setBlockZ(blockZ);
        return vector;
    }
    
    private static Vector lambda$addToEntityOffset$3(final short n, final short n2, final short n3, final Integer n4, final Vector vector) {
        if (vector == null) {
            return new Vector(n, n2, n3);
        }
        vector.setBlockX(vector.getBlockX() + n);
        vector.setBlockY(vector.getBlockY() + n2);
        vector.setBlockZ(vector.getBlockZ() + n3);
        return vector;
    }
    
    private static Vector lambda$getEntityOffset$2(final Integer n) {
        return new Vector(0, 0, 0);
    }
    
    private static boolean lambda$removeEntity$1(final Map.Entry entry) {
        return entry.getValue().isEmpty();
    }
    
    private static void lambda$removeEntity$0(final int n, final Integer n2, final List list) {
        list.remove((Object)n);
    }
}
