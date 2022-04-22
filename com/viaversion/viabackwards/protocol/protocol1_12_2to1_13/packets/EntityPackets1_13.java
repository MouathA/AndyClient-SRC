package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viabackwards.api.entities.storage.*;

public class EntityPackets1_13 extends LegacyEntityRewriter
{
    public EntityPackets1_13(final Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_POSITION, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                            return;
                        }
                        final PlayerPositionStorage1_13 playerPositionStorage1_13 = (PlayerPositionStorage1_13)packetWrapper.user().get(PlayerPositionStorage1_13.class);
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        playerPositionStorage1_13.setX(this.toSet(byteValue, 0, playerPositionStorage1_13.getX(), (double)packetWrapper.get(Type.DOUBLE, 0)));
                        playerPositionStorage1_13.setY(this.toSet(byteValue, 1, playerPositionStorage1_13.getY(), (double)packetWrapper.get(Type.DOUBLE, 1)));
                        playerPositionStorage1_13.setZ(this.toSet(byteValue, 2, playerPositionStorage1_13.getZ(), (double)packetWrapper.get(Type.DOUBLE, 2)));
                    }
                    
                    private double toSet(final int n, final int n2, final double n3, final double n4) {
                        return ((n & 1 << n2) != 0x0) ? (n3 + n4) : n4;
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_13.access$000(this.this$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_13$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Optional byId = Entity1_13Types.ObjectType.findById((byte)packetWrapper.get(Type.BYTE, 0));
                        if (!byId.isPresent()) {
                            return;
                        }
                        final Entity1_13Types.ObjectType objectType = byId.get();
                        if (objectType == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                            (int)packetWrapper.get(Type.INT, 0);
                            final int newBlockStateId = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(3);
                            packetWrapper.set(Type.INT, 0, (newBlockStateId >> 4 & 0xFFF) | (newBlockStateId & 0xF) << 12);
                        }
                        else if (objectType == Entity1_13Types.ObjectType.ITEM_FRAME) {
                            (int)packetWrapper.get(Type.INT, 0);
                            switch (3) {
                                case 3: {}
                            }
                            packetWrapper.set(Type.INT, 0, 3);
                        }
                        else if (objectType == Entity1_13Types.ObjectType.TRIDENT) {
                            packetWrapper.set(Type.BYTE, 0, (byte)Entity1_13Types.ObjectType.TIPPED_ARROW.getId());
                        }
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, Entity1_13Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, Entity1_13Types.EntityType.LIGHTNING_BOLT);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
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
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets1_13$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                        final Entity1_13Types.EntityType typeFromId = Entity1_13Types.getTypeFromId(intValue, false);
                        this.this$1.this$0.tracker(packetWrapper.user()).addEntity((int)packetWrapper.get(Type.VAR_INT, 0), typeFromId);
                        final int oldId = EntityTypeMapping.getOldId(intValue);
                        if (oldId == -1) {
                            if (!EntityPackets1_13.access$100(this.this$1.this$0, typeFromId)) {
                                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + intValue + "/" + typeFromId);
                            }
                        }
                        else {
                            packetWrapper.set(Type.VAR_INT, 1, oldId);
                        }
                    }
                });
                this.handler(EntityPackets1_13.access$200(this.this$0, Types1_12.METADATA_LIST));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
                this.handler(EntityPackets1_13.access$300(this.this$0, Types1_12.METADATA_LIST, Entity1_13Types.EntityType.PLAYER));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(EntityPackets1_13.access$400(this.this$0, Entity1_13Types.EntityType.PAINTING, Type.VAR_INT));
                this.handler(new PacketHandler() {
                    final EntityPackets1_13$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, PaintingMapping.getStringId((int)packetWrapper.read(Type.VAR_INT)));
                    }
                });
            }
        });
        this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, Entity1_13Types.EntityType.PLAYER);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(EntityPackets1_13.access$500(this.this$0, 0));
                this.handler(EntityPackets1_13$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((BackwardsBlockStorage)packetWrapper.user().get(BackwardsBlockStorage.class)).clear();
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.FACE_PLAYER, null, new PacketRemapper() {
            final EntityPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final EntityPackets1_13$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                            return;
                        }
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final double doubleValue = (double)packetWrapper.read(Type.DOUBLE);
                        final double doubleValue2 = (double)packetWrapper.read(Type.DOUBLE);
                        final double doubleValue3 = (double)packetWrapper.read(Type.DOUBLE);
                        final PlayerPositionStorage1_13 playerPositionStorage1_13 = (PlayerPositionStorage1_13)packetWrapper.user().get(PlayerPositionStorage1_13.class);
                        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_12_1.PLAYER_POSITION);
                        create.write(Type.DOUBLE, 0.0);
                        create.write(Type.DOUBLE, 0.0);
                        create.write(Type.DOUBLE, 0.0);
                        EntityPositionHandler.writeFacingDegrees(create, playerPositionStorage1_13.getX(), (intValue == 1) ? (playerPositionStorage1_13.getY() + 1.62) : playerPositionStorage1_13.getY(), playerPositionStorage1_13.getZ(), doubleValue, doubleValue2, doubleValue3);
                        create.write(Type.BYTE, 7);
                        create.write(Type.VAR_INT, -1);
                        create.send(Protocol1_12_2To1_13.class);
                    }
                });
            }
        });
        if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
            final PacketRemapper packetRemapper = new PacketRemapper() {
                final EntityPackets1_13 this$0;
                
                @Override
                public void registerMap() {
                    this.map(Type.DOUBLE);
                    this.map(Type.DOUBLE);
                    this.map(Type.DOUBLE);
                    this.handler(EntityPackets1_13$8::lambda$registerMap$0);
                }
                
                private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                    ((PlayerPositionStorage1_13)packetWrapper.user().get(PlayerPositionStorage1_13.class)).setCoordinates(packetWrapper, false);
                }
            };
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION, packetRemapper);
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION_AND_ROTATION, packetRemapper);
            ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.VEHICLE_MOVE, packetRemapper);
        }
    }
    
    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.DROWNED, Entity1_13Types.EntityType.ZOMBIE_VILLAGER).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.COD, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.SALMON, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.PUFFERFISH, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.TROPICAL_FISH, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.PHANTOM, Entity1_13Types.EntityType.PARROT).plainName().spawnMetadata(EntityPackets1_13::lambda$registerRewrites$0);
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.DOLPHIN, Entity1_13Types.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(Entity1_13Types.EntityType.TURTLE, Entity1_13Types.EntityType.OCELOT).plainName();
        this.filter().handler(this::lambda$registerRewrites$1);
        this.filter().filterFamily(Entity1_13Types.EntityType.ENTITY).index(2).handler(EntityPackets1_13::lambda$registerRewrites$2);
        this.filter().filterFamily(Entity1_13Types.EntityType.ZOMBIE).removeIndex(15);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(13);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(14);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(15);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(16);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(17);
        this.filter().type(Entity1_13Types.EntityType.TURTLE).cancel(18);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(12);
        this.filter().filterFamily(Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(13);
        this.filter().type(Entity1_13Types.EntityType.PHANTOM).cancel(12);
        this.filter().type(Entity1_13Types.EntityType.BOAT).cancel(12);
        this.filter().type(Entity1_13Types.EntityType.TRIDENT).cancel(7);
        this.filter().type(Entity1_13Types.EntityType.WOLF).index(17).handler(EntityPackets1_13::lambda$registerRewrites$3);
        this.filter().type(Entity1_13Types.EntityType.AREA_EFFECT_CLOUD).index(9).handler(this::lambda$registerRewrites$4);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_13Types.getTypeFromId(n, true);
    }
    
    @Override
    public int newEntityId(final int n) {
        return EntityTypeMapping.getOldId(n);
    }
    
    private void lambda$registerRewrites$4(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final Particle particle = (Particle)metadata.getValue();
        final ParticleMapping.ParticleData mapping = ParticleMapping.getMapping(particle.getId());
        final int[] rewriteMeta = mapping.rewriteMeta((Protocol1_12_2To1_13)this.protocol, particle.getArguments());
        if (rewriteMeta != null && rewriteMeta.length != 0) {
            if (mapping.getHandler().isBlockHandler() && rewriteMeta[0] == 0) {
                rewriteMeta[0] = 102;
            }
            final int n = rewriteMeta[0];
            final int n2 = (rewriteMeta.length == 2) ? rewriteMeta[1] : 0;
        }
        metaHandlerEvent.createExtraMeta(new Metadata(9, MetaType1_12.VarInt, mapping.getHistoryId()));
        metaHandlerEvent.createExtraMeta(new Metadata(10, MetaType1_12.VarInt, 0));
        metaHandlerEvent.createExtraMeta(new Metadata(11, MetaType1_12.VarInt, 0));
        metaHandlerEvent.cancel();
    }
    
    private static void lambda$registerRewrites$3(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setValue(15 - (int)metadata.getValue());
    }
    
    private static void lambda$registerRewrites$2(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final String string = metadata.getValue().toString();
        if (!string.isEmpty()) {
            metadata.setValue(ChatRewriter.jsonToLegacyText(string));
        }
    }
    
    private void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final int typeId = metadata.metaType().typeId();
        if (typeId == 5) {
            metadata.setTypeAndValue(MetaType1_12.String, (metadata.getValue() != null) ? metadata.getValue().toString() : "");
        }
        else if (typeId == 6) {
            metadata.setTypeAndValue(MetaType1_12.Slot, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        else if (typeId == 15) {
            metaHandlerEvent.cancel();
        }
        else if (typeId > 5) {
            metadata.setMetaType(MetaType1_12.byId(typeId - 1));
        }
    }
    
    private static void lambda$registerRewrites$0(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(15, MetaType1_12.VarInt, 3));
    }
    
    static PacketHandler access$000(final EntityPackets1_13 entityPackets1_13) {
        return entityPackets1_13.getObjectTrackerHandler();
    }
    
    static boolean access$100(final EntityPackets1_13 entityPackets1_13, final EntityType entityType) {
        return entityPackets1_13.hasData(entityType);
    }
    
    static PacketHandler access$200(final EntityPackets1_13 entityPackets1_13, final Type type) {
        return entityPackets1_13.getMobSpawnRewriter(type);
    }
    
    static PacketHandler access$300(final EntityPackets1_13 entityPackets1_13, final Type type, final EntityType entityType) {
        return entityPackets1_13.getTrackerAndMetaHandler(type, entityType);
    }
    
    static PacketHandler access$400(final EntityPackets1_13 entityPackets1_13, final EntityType entityType, final Type type) {
        return entityPackets1_13.getTrackerHandler(entityType, type);
    }
    
    static PacketHandler access$500(final EntityPackets1_13 entityPackets1_13, final int n) {
        return entityPackets1_13.getDimensionHandler(n);
    }
}
