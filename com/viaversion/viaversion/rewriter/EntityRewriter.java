package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.google.common.base.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.logging.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public abstract class EntityRewriter extends RewriterBase implements com.viaversion.viaversion.api.rewriter.EntityRewriter
{
    private static final Metadata[] EMPTY_ARRAY;
    protected final List metadataFilters;
    protected final boolean trackMappedType;
    protected Int2IntMap typeMappings;
    
    protected EntityRewriter(final Protocol protocol) {
        this(protocol, true);
    }
    
    protected EntityRewriter(final Protocol protocol, final boolean trackMappedType) {
        super(protocol);
        this.metadataFilters = new ArrayList();
        this.trackMappedType = trackMappedType;
        protocol.put(this);
    }
    
    public MetaFilter.Builder filter() {
        return new MetaFilter.Builder(this);
    }
    
    public void registerFilter(final MetaFilter metaFilter) {
        Preconditions.checkArgument(!this.metadataFilters.contains(metaFilter));
        this.metadataFilters.add(metaFilter);
    }
    
    @Override
    public void handleMetadata(final int n, final List list, final UserConnection userConnection) {
        final EntityType entityType = this.tracker(userConnection).entityType(n);
        final Metadata[] array = list.toArray(EntityRewriter.EMPTY_ARRAY);
        while (0 < array.length) {
            final Metadata metadata = array[0];
            if (!this.callOldMetaHandler(n, entityType, metadata, list, userConnection)) {
                final int n2 = 0;
                int n3 = 0;
                --n3;
                list.remove(n2);
            }
            else {
                MetaHandlerEvent metaHandlerEvent = null;
                int n3 = 0;
                for (final MetaFilter metaFilter : this.metadataFilters) {
                    if (!metaFilter.isFiltered(entityType, metadata)) {
                        continue;
                    }
                    if (metaHandlerEvent == null) {
                        metaHandlerEvent = new MetaHandlerEventImpl(userConnection, entityType, n, metadata, list);
                    }
                    metaFilter.handler().handle(metaHandlerEvent, metadata);
                    if (metaHandlerEvent.cancelled()) {
                        final int n4 = 0;
                        --n3;
                        list.remove(n4);
                        break;
                    }
                }
                if (metaHandlerEvent != null && metaHandlerEvent.extraMeta() != null) {
                    list.addAll(metaHandlerEvent.extraMeta());
                }
                ++n3;
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    @Deprecated
    private boolean callOldMetaHandler(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) {
        this.handleMetadata(n, entityType, metadata, list, userConnection);
        return true;
    }
    
    @Deprecated
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
    }
    
    @Override
    public int newEntityId(final int n) {
        return (this.typeMappings != null) ? this.typeMappings.getOrDefault(n, n) : n;
    }
    
    public void mapEntityType(final EntityType entityType, final EntityType entityType2) {
        Preconditions.checkArgument(entityType.getClass() != entityType2.getClass(), (Object)"EntityTypes should not be of the same class/enum");
        this.mapEntityType(entityType.getId(), entityType2.getId());
    }
    
    protected void mapEntityType(final int n, final int n2) {
        if (this.typeMappings == null) {
            (this.typeMappings = new Int2IntOpenHashMap()).defaultReturnValue(-1);
        }
        this.typeMappings.put(n, n2);
    }
    
    public void mapTypes(final EntityType[] array, final Class clazz) {
        if (this.typeMappings == null) {
            (this.typeMappings = new Int2IntOpenHashMap(array.length, 0.99f)).defaultReturnValue(-1);
        }
        while (0 < array.length) {
            final EntityType entityType = array[0];
            this.typeMappings.put(entityType.getId(), ((EntityType)Enum.valueOf((Class<Enum>)clazz, entityType.name())).getId());
            int n = 0;
            ++n;
        }
    }
    
    public void registerMetaTypeHandler(final MetaType metaType, final MetaType metaType2, final MetaType metaType3) {
        this.filter().handler(this::lambda$registerMetaTypeHandler$0);
    }
    
    public void registerTracker(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this.this$0.trackerHandler());
            }
        });
    }
    
    public void registerTrackerWithData(final ClientboundPacketType clientboundPacketType, final EntityType entityType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(entityType) {
            final EntityType val$fallingBlockType;
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(this.this$0.trackerHandler());
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final EntityType entityType, final PacketWrapper packetWrapper) throws Exception {
                if (this.this$0.tracker(packetWrapper.user()).entityType((int)packetWrapper.get(Type.VAR_INT, 0)) == entityType) {
                    packetWrapper.set(Type.INT, 0, EntityRewriter.access$000(this.this$0).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.INT, 0)));
                }
            }
        });
    }
    
    public void registerTracker(final ClientboundPacketType clientboundPacketType, final EntityType entityType, final Type type) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type, entityType) {
            final Type val$intType;
            final EntityType val$entityType;
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final EntityType entityType, final PacketWrapper packetWrapper) throws Exception {
                this.this$0.tracker(packetWrapper.user()).addEntity((int)packetWrapper.passthrough(type), entityType);
            }
        });
    }
    
    public void registerTracker(final ClientboundPacketType clientboundPacketType, final EntityType entityType) {
        this.registerTracker(clientboundPacketType, entityType, Type.VAR_INT);
    }
    
    public void registerRemoveEntities(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int[] array = (int[])packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                final EntityTracker tracker = this.this$0.tracker(packetWrapper.user());
                final int[] array2 = array;
                while (0 < array2.length) {
                    tracker.removeEntity(array2[0]);
                    int n = 0;
                    ++n;
                }
            }
        });
    }
    
    public void registerRemoveEntity(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.tracker(packetWrapper.user()).removeEntity((int)packetWrapper.passthrough(Type.VAR_INT));
            }
        });
    }
    
    public void registerMetadataRewriter(final ClientboundPacketType clientboundPacketType, final Type type, final Type type2) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(type, type2) {
            final Type val$oldMetaType;
            final Type val$newMetaType;
            final EntityRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                if (this.val$oldMetaType != null) {
                    this.map(this.val$oldMetaType, this.val$newMetaType);
                }
                else {
                    this.map(this.val$newMetaType);
                }
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final Type type, final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(type, 0), packetWrapper.user());
            }
        });
    }
    
    public void registerMetadataRewriter(final ClientboundPacketType clientboundPacketType, final Type type) {
        this.registerMetadataRewriter(clientboundPacketType, null, type);
    }
    
    public PacketHandler trackerHandler() {
        return this.trackerAndRewriterHandler(null);
    }
    
    public PacketHandler worldDataTrackerHandler(final int n) {
        return this::lambda$worldDataTrackerHandler$1;
    }
    
    public PacketHandler biomeSizeTracker() {
        return this::lambda$biomeSizeTracker$2;
    }
    
    public PacketHandler trackerAndRewriterHandler(final Type type) {
        return this::lambda$trackerAndRewriterHandler$3;
    }
    
    public PacketHandler trackerAndRewriterHandler(final Type type, final EntityType entityType) {
        return this::lambda$trackerAndRewriterHandler$4;
    }
    
    public PacketHandler objectTrackerHandler() {
        return this::lambda$objectTrackerHandler$5;
    }
    
    @Deprecated
    protected Metadata metaByIndex(final int n, final List list) {
        for (final Metadata metadata : list) {
            if (metadata.id() == n) {
                return metadata;
            }
        }
        return null;
    }
    
    protected void rewriteParticle(final Particle particle) {
        final ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
        final int id = particle.getId();
        if (particleMappings.isBlockParticle(id)) {
            final Particle.ParticleData particleData = particle.getArguments().get(0);
            particleData.setValue(this.protocol.getMappingData().getNewBlockStateId((int)particleData.get()));
        }
        else if (particleMappings.isItemParticle(id) && this.protocol.getItemRewriter() != null) {
            this.protocol.getItemRewriter().handleItemToClient((Item)particle.getArguments().get(0).get());
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(id));
    }
    
    private void logException(final Exception ex, final EntityType entityType, final List list, final Metadata metadata) {
        if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
            final Logger logger = Via.getPlatform().getLogger();
            logger.severe("An error occurred in metadata handler " + this.getClass().getSimpleName() + " for " + ((entityType != null) ? entityType.name() : "untracked") + " entity type: " + metadata);
            logger.severe(list.stream().sorted(Comparator.comparingInt(Metadata::id)).map((Function<? super Object, ?>)Metadata::toString).collect((Collector<? super Object, ?, String>)Collectors.joining("\n", "Full metadata: ", "")));
            ex.printStackTrace();
        }
    }
    
    private void lambda$objectTrackerHandler$5(final PacketWrapper packetWrapper) throws Exception {
        this.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.VAR_INT, 0), this.objectTypeFromId((byte)packetWrapper.get(Type.BYTE, 0)));
    }
    
    private void lambda$trackerAndRewriterHandler$4(final EntityType entityType, final Type type, final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
        this.tracker(packetWrapper.user()).addEntity(intValue, entityType);
        if (type != null) {
            this.handleMetadata(intValue, (List)packetWrapper.get(type, 0), packetWrapper.user());
        }
    }
    
    private void lambda$trackerAndRewriterHandler$3(final Type type, final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
        final int intValue2 = (int)packetWrapper.get(Type.VAR_INT, 1);
        final int entityId = this.newEntityId(intValue2);
        if (entityId != intValue2) {
            packetWrapper.set(Type.VAR_INT, 1, entityId);
        }
        this.tracker(packetWrapper.user()).addEntity(intValue, this.typeFromId(this.trackMappedType ? entityId : intValue2));
        if (type != null) {
            this.handleMetadata(intValue, (List)packetWrapper.get(type, 0), packetWrapper.user());
        }
    }
    
    private void lambda$biomeSizeTracker$2(final PacketWrapper packetWrapper) throws Exception {
        this.tracker(packetWrapper.user()).setBiomesSent(((ListTag)((CompoundTag)((CompoundTag)packetWrapper.get(Type.NBT, 0)).get("minecraft:worldgen/biome")).get("value")).size());
    }
    
    private void lambda$worldDataTrackerHandler$1(final int n, final PacketWrapper packetWrapper) throws Exception {
        final EntityTracker tracker = this.tracker(packetWrapper.user());
        final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, n);
        final Tag value = compoundTag.get("height");
        if (value instanceof IntTag) {
            tracker.setCurrentWorldSectionHeight(((IntTag)value).asInt() >> 4);
        }
        else {
            Via.getPlatform().getLogger().warning("Height missing in dimension data: " + compoundTag);
        }
        final Tag value2 = compoundTag.get("min_y");
        if (value2 instanceof IntTag) {
            tracker.setCurrentMinY(((IntTag)value2).asInt());
        }
        else {
            Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + compoundTag);
        }
        final String currentWorld = (String)packetWrapper.get(Type.STRING, 0);
        if (tracker.currentWorld() != null && !tracker.currentWorld().equals(currentWorld)) {
            tracker.clearEntities();
        }
        tracker.setCurrentWorld(currentWorld);
    }
    
    private void lambda$registerMetaTypeHandler$0(final MetaType metaType, final MetaType metaType2, final MetaType metaType3, final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metaType != null && metadata.metaType() == metaType) {
            this.protocol.getItemRewriter().handleItemToClient((Item)metadata.value());
        }
        else if (metaType2 != null && metadata.metaType() == metaType2) {
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId((int)metadata.value()));
        }
        else if (metaType3 != null && metadata.metaType() == metaType3) {
            this.rewriteParticle((Particle)metadata.value());
        }
    }
    
    static Protocol access$000(final EntityRewriter entityRewriter) {
        return entityRewriter.protocol;
    }
    
    static {
        EMPTY_ARRAY = new Metadata[0];
    }
}
