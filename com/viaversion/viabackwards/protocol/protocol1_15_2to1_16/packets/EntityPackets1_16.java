package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_16 extends EntityRewriter
{
    private final ValueTransformer dimensionTransformer;
    
    public EntityPackets1_16(final Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        super(protocol1_15_2To1_16);
        this.dimensionTransformer = new ValueTransformer(Type.STRING, (Type)Type.INT) {
            final EntityPackets1_16 this$0;
            
            public Integer transform(final PacketWrapper packetWrapper, final String s) {
                switch (s.hashCode()) {
                    case -1526768685: {
                        if (s.equals("minecraft:the_nether")) {
                            break;
                        }
                        break;
                    }
                    case 1104210353: {
                        if (s.equals("minecraft:overworld")) {
                            break;
                        }
                        break;
                    }
                    case 1731133248: {
                        if (s.equals("minecraft:the_end")) {}
                        break;
                    }
                }
                switch (3) {
                    case 0: {
                        return -1;
                    }
                    default: {
                        return 0;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (String)o);
            }
        };
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_16 this$0;
            
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
                this.handler(this::lambda$registerMap$0);
                this.handler(this.this$0.getSpawnTrackerWithDataHandler(Entity1_16Types.FALLING_BLOCK));
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (this.this$0.typeFromId((int)packetWrapper.get(Type.VAR_INT, 1)) == Entity1_16Types.LIGHTNING_BOLT) {
                    packetWrapper.cancel();
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
                    create.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    create.write(Type.BYTE, 1);
                    create.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 0));
                    create.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 1));
                    create.write(Type.DOUBLE, packetWrapper.get(Type.DOUBLE, 2));
                    create.send(Protocol1_15_2To1_16.class);
                }
            }
        });
        this.registerSpawnTracker(ClientboundPackets1_16.SPAWN_MOB);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.RESPAWN, new PacketRemapper() {
            final EntityPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(EntityPackets1_16.access$000(this.this$0));
                this.handler(EntityPackets1_16$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final WorldNameTracker worldNameTracker = (WorldNameTracker)packetWrapper.user().get(WorldNameTracker.class);
                final String worldName = (String)packetWrapper.read(Type.STRING);
                packetWrapper.passthrough(Type.LONG);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.read(Type.BYTE);
                final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (clientWorld.getEnvironment() != null && intValue == clientWorld.getEnvironment().getId() && (packetWrapper.user().isClientSide() || Via.getPlatform().isProxy() || packetWrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion() || !worldName.equals(worldNameTracker.getWorldName()))) {
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_15.RESPAWN);
                    create.write(Type.INT, (intValue == 0) ? -1 : 0);
                    create.write(Type.LONG, 0L);
                    create.write(Type.UNSIGNED_BYTE, 0);
                    create.write(Type.STRING, "default");
                    create.send(Protocol1_15_2To1_16.class);
                }
                clientWorld.setEnvironment(intValue);
                packetWrapper.write(Type.STRING, "default");
                packetWrapper.read(Type.BOOLEAN);
                if (packetWrapper.read(Type.BOOLEAN)) {
                    packetWrapper.set(Type.STRING, 0, "flat");
                }
                packetWrapper.read(Type.BOOLEAN);
                worldNameTracker.setWorldName(worldName);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE, Type.NOTHING);
                this.map(Type.STRING_ARRAY, Type.NOTHING);
                this.map(Type.NBT, Type.NOTHING);
                this.map(EntityPackets1_16.access$000(this.this$0));
                this.handler(EntityPackets1_16$4::lambda$registerMap$0);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$registerMap$1);
            }
            
            private void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                this.this$0.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_16Types.PLAYER);
                packetWrapper.write(Type.STRING, "default");
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
                if (packetWrapper.read(Type.BOOLEAN)) {
                    packetWrapper.set(Type.STRING, 0, "flat");
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((WorldNameTracker)packetWrapper.user().get(WorldNameTracker.class)).setWorldName((String)packetWrapper.read(Type.STRING));
            }
        });
        this.registerTracker(ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, Entity1_16Types.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16.SPAWN_PAINTING, Entity1_16Types.PAINTING);
        this.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_14.METADATA_LIST);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.ENTITY_PROPERTIES, new PacketRemapper() {
            final EntityPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                while (0 < (int)packetWrapper.passthrough(Type.INT)) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    final String s2 = ((Protocol1_15_2To1_16)EntityPackets1_16.access$100(this.this$0)).getMappingData().getAttributeMappings().get(s);
                    packetWrapper.write(Type.STRING, (s2 != null) ? s2 : s.replace("minecraft:", ""));
                    packetWrapper.passthrough(Type.DOUBLE);
                    while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                        packetWrapper.passthrough(Type.UUID);
                        packetWrapper.passthrough(Type.DOUBLE);
                        packetWrapper.passthrough(Type.BYTE);
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.PLAYER_INFO, new PacketRemapper() {
            final EntityPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    packetWrapper.passthrough(Type.UUID);
                    if (intValue == 0) {
                        packetWrapper.passthrough(Type.STRING);
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                packetWrapper.passthrough(Type.STRING);
                            }
                            int n = 0;
                            ++n;
                        }
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        if (packetWrapper.passthrough(Type.BOOLEAN)) {
                            ((Protocol1_15_2To1_16)EntityPackets1_16.access$200(this.this$0)).getTranslatableRewriter().processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                        }
                    }
                    else if (intValue == 1) {
                        packetWrapper.passthrough(Type.VAR_INT);
                    }
                    else if (intValue == 2) {
                        packetWrapper.passthrough(Type.VAR_INT);
                    }
                    else if (intValue == 3 && (boolean)packetWrapper.passthrough(Type.BOOLEAN)) {
                        ((Protocol1_15_2To1_16)EntityPackets1_16.access$300(this.this$0)).getTranslatableRewriter().processText((JsonElement)packetWrapper.passthrough(Type.COMPONENT));
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(this::lambda$registerRewrites$0);
        this.mapEntityType(Entity1_16Types.ZOMBIFIED_PIGLIN, Entity1_15Types.ZOMBIE_PIGMAN);
        this.mapTypes(Entity1_16Types.values(), Entity1_15Types.class);
        this.mapEntityTypeWithData(Entity1_16Types.HOGLIN, Entity1_16Types.COW).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.ZOGLIN, Entity1_16Types.COW).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.PIGLIN, Entity1_16Types.ZOMBIFIED_PIGLIN).jsonName();
        this.mapEntityTypeWithData(Entity1_16Types.STRIDER, Entity1_16Types.MAGMA_CUBE).jsonName();
        this.filter().type(Entity1_16Types.ZOGLIN).cancel(16);
        this.filter().type(Entity1_16Types.HOGLIN).cancel(15);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(16);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(17);
        this.filter().type(Entity1_16Types.PIGLIN).cancel(18);
        this.filter().type(Entity1_16Types.STRIDER).index(15).handler(EntityPackets1_16::lambda$registerRewrites$1);
        this.filter().type(Entity1_16Types.STRIDER).cancel(16);
        this.filter().type(Entity1_16Types.STRIDER).cancel(17);
        this.filter().type(Entity1_16Types.STRIDER).cancel(18);
        this.filter().type(Entity1_16Types.FISHING_BOBBER).cancel(8);
        this.filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).cancel(8);
        this.filter().filterFamily(Entity1_16Types.ABSTRACT_ARROW).handler(EntityPackets1_16::lambda$registerRewrites$2);
        this.filter().type(Entity1_16Types.WOLF).index(16).handler(this::lambda$registerRewrites$3);
        this.filter().type(Entity1_16Types.WOLF).index(20).handler(this::lambda$registerRewrites$4);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_16Types.getTypeFromId(n);
    }
    
    private void lambda$registerRewrites$4(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final StoredEntityData entityDataIfPresent = this.tracker(metaHandlerEvent.user()).entityDataIfPresent(metaHandlerEvent.entityId());
        if (entityDataIfPresent != null) {
            final WolfDataMaskStorage wolfDataMaskStorage = (WolfDataMaskStorage)entityDataIfPresent.get(WolfDataMaskStorage.class);
            if (wolfDataMaskStorage != null) {
                wolfDataMaskStorage.tameableMask();
            }
        }
        metaHandlerEvent.createExtraMeta(new Metadata(16, Types1_14.META_TYPES.byteType, (byte)(((int)metadata.value() > 0) ? 2 : 0)));
        metaHandlerEvent.cancel();
    }
    
    private void lambda$registerRewrites$3(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        this.tracker(metaHandlerEvent.user()).entityData(metaHandlerEvent.entityId()).put(new WolfDataMaskStorage((byte)metadata.value()));
    }
    
    private static void lambda$registerRewrites$2(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metaHandlerEvent.index() >= 8) {
            metaHandlerEvent.setIndex(metaHandlerEvent.index() + 1);
        }
    }
    
    private static void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setTypeAndValue(Types1_14.META_TYPES.varIntType, metadata.value() ? 1 : 3);
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_14.META_TYPES.byId(metadata.metaType().typeId()));
        final MetaType metaType = metadata.metaType();
        if (metaType == Types1_14.META_TYPES.itemType) {
            metadata.setValue(((Protocol1_15_2To1_16)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        else if (metaType == Types1_14.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
        }
        else if (metaType == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        else if (metaType == Types1_14.META_TYPES.optionalComponentType) {
            final JsonElement jsonElement = (JsonElement)metadata.value();
            if (jsonElement != null) {
                ((Protocol1_15_2To1_16)this.protocol).getTranslatableRewriter().processText(jsonElement);
            }
        }
    }
    
    static ValueTransformer access$000(final EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.dimensionTransformer;
    }
    
    static Protocol access$100(final EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.protocol;
    }
    
    static Protocol access$200(final EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.protocol;
    }
    
    static Protocol access$300(final EntityPackets1_16 entityPackets1_16) {
        return entityPackets1_16.protocol;
    }
}
