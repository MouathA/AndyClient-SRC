package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viabackwards.api.rewriters.*;
import java.util.function.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.meta.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_14 extends LegacyEntityRewriter
{
    private EntityPositionHandler positionHandler;
    
    public EntityPackets1_14(final Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14, Types1_13_2.META_TYPES.optionalComponentType, Types1_13_2.META_TYPES.booleanType);
    }
    
    @Override
    protected void addTrackedEntity(final PacketWrapper packetWrapper, final int n, final EntityType entityType) throws Exception {
        super.addTrackedEntity(packetWrapper, n, entityType);
        if (entityType == Entity1_14Types.PAINTING) {
            final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
            this.positionHandler.cacheEntityPosition(packetWrapper, position.getX(), position.getY(), position.getZ(), true, false);
        }
        else if (packetWrapper.getId() != ClientboundPackets1_14.JOIN_GAME.getId()) {
            this.positionHandler.cacheEntityPosition(packetWrapper, true, false);
        }
    }
    
    @Override
    protected void registerPackets() {
        this.positionHandler = new EntityPositionHandler(this, EntityPositionStorage1_14.class, EntityPositionStorage1_14::new);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_STATUS, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.INT);
                if ((byte)packetWrapper.passthrough(Type.BYTE) != 3) {
                    return;
                }
                if (this.this$0.tracker(packetWrapper.user()).entityType(intValue) != Entity1_14Types.PLAYER) {
                    return;
                }
                while (true) {
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_13.ENTITY_EQUIPMENT);
                    create.write(Type.VAR_INT, intValue);
                    create.write(Type.VAR_INT, 0);
                    create.write(Type.FLAT_VAR_INT_ITEM, null);
                    create.send(Protocol1_13_2To1_14.class);
                    int n = 0;
                    ++n;
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_TELEPORT, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_14.access$000(this.this$0).cacheEntityPosition(packetWrapper, false, false);
            }
        });
        final PacketRemapper packetRemapper = new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler() {
                    final EntityPackets1_14$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        EntityPackets1_14.access$000(this.this$1.this$0).cacheEntityPosition(packetWrapper, (short)packetWrapper.get(Type.SHORT, 0) / 4096.0, (short)packetWrapper.get(Type.SHORT, 1) / 4096.0, (short)packetWrapper.get(Type.SHORT, 2) / 4096.0, false, true);
                    }
                });
            }
        };
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_POSITION, packetRemapper);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_POSITION_AND_ROTATION, packetRemapper);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT, Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(EntityPackets1_14.access$100(this.this$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_14$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Entity1_13Types.EntityType typeFromId = Entity1_13Types.getTypeFromId(this.this$1.this$0.newEntityId((byte)packetWrapper.get(Type.BYTE, 0)), false);
                        Entity1_13Types.ObjectType minecart;
                        if (typeFromId.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT)) {
                            minecart = Entity1_13Types.ObjectType.MINECART;
                            switch (typeFromId) {
                                case CHEST_MINECART: {}
                                case FURNACE_MINECART: {}
                                case TNT_MINECART: {}
                                case SPAWNER_MINECART: {}
                            }
                            if (6 != 0) {
                                packetWrapper.set(Type.INT, 0, 6);
                            }
                        }
                        else {
                            minecart = Entity1_13Types.ObjectType.fromEntityType(typeFromId).orElse(null);
                        }
                        if (minecart == null) {
                            return;
                        }
                        packetWrapper.set(Type.BYTE, 0, (byte)minecart.getId());
                        (int)packetWrapper.get(Type.INT, 0);
                        if (minecart == Entity1_13Types.ObjectType.FALLING_BLOCK) {
                            packetWrapper.set(Type.INT, 0, ((Protocol1_13_2To1_14)EntityPackets1_14.access$200(this.this$1.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.INT, 0)));
                        }
                        else if (typeFromId.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW)) {
                            packetWrapper.set(Type.INT, 0, 7);
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
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
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets1_14$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                        final EntityType typeFromId = Entity1_14Types.getTypeFromId(intValue);
                        this.this$1.this$0.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), typeFromId);
                        final int entityId = this.this$1.this$0.newEntityId(intValue);
                        if (entityId == -1) {
                            final EntityData access$300 = EntityPackets1_14.access$300(this.this$1.this$0, typeFromId);
                            if (access$300 == null) {
                                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + intValue + "/" + typeFromId);
                                packetWrapper.cancel();
                            }
                            else {
                                packetWrapper.set(Type.VAR_INT, 1, access$300.replacementId());
                            }
                        }
                        else {
                            packetWrapper.set(Type.VAR_INT, 1, entityId);
                        }
                    }
                });
                this.handler(EntityPackets1_14.access$400(this.this$0, Types1_13_2.METADATA_LIST));
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.EXPERIENCE_ORB);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.LIGHTNING_BOLT);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_PAINTING, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.addTrackedEntity(packetWrapper, (int)packetWrapper.get(Type.VAR_INT, 0), Entity1_14Types.PAINTING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
                this.handler(EntityPackets1_14.access$500(this.this$0, Types1_13_2.METADATA_LIST, Entity1_14Types.PLAYER));
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                EntityPackets1_14.access$000(this.this$0).cacheEntityPosition(packetWrapper, true, false);
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_14.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.JOIN_GAME, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(EntityPackets1_14.access$600(this.this$0, Entity1_14Types.PLAYER, Type.INT));
                this.handler(EntityPackets1_14.access$700(this.this$0, 1));
                this.handler(EntityPackets1_14$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)((DifficultyStorage)packetWrapper.user().get(DifficultyStorage.class)).getDifficulty());
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.read(Type.VAR_INT);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.RESPAWN, new PacketRemapper() {
            final EntityPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(EntityPackets1_14$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)((DifficultyStorage)packetWrapper.user().get(DifficultyStorage.class)).getDifficulty());
                ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).clear();
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.mapTypes(Entity1_14Types.values(), Entity1_13Types.EntityType.class);
        this.mapEntityTypeWithData(Entity1_14Types.CAT, Entity1_14Types.OCELOT).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.TRADER_LLAMA, Entity1_14Types.LLAMA).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.FOX, Entity1_14Types.WOLF).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.PANDA, Entity1_14Types.POLAR_BEAR).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.PILLAGER, Entity1_14Types.VILLAGER).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.WANDERING_TRADER, Entity1_14Types.VILLAGER).jsonName();
        this.mapEntityTypeWithData(Entity1_14Types.RAVAGER, Entity1_14Types.COW).jsonName();
        this.filter().handler(this::lambda$registerRewrites$0);
        this.filter().type(Entity1_14Types.PILLAGER).cancel(15);
        this.filter().type(Entity1_14Types.FOX).cancel(15);
        this.filter().type(Entity1_14Types.FOX).cancel(16);
        this.filter().type(Entity1_14Types.FOX).cancel(17);
        this.filter().type(Entity1_14Types.FOX).cancel(18);
        this.filter().type(Entity1_14Types.PANDA).cancel(15);
        this.filter().type(Entity1_14Types.PANDA).cancel(16);
        this.filter().type(Entity1_14Types.PANDA).cancel(17);
        this.filter().type(Entity1_14Types.PANDA).cancel(18);
        this.filter().type(Entity1_14Types.PANDA).cancel(19);
        this.filter().type(Entity1_14Types.PANDA).cancel(20);
        this.filter().type(Entity1_14Types.CAT).cancel(18);
        this.filter().type(Entity1_14Types.CAT).cancel(19);
        this.filter().type(Entity1_14Types.CAT).cancel(20);
        this.filter().handler(EntityPackets1_14::lambda$registerRewrites$1);
        this.filter().type(Entity1_14Types.AREA_EFFECT_CLOUD).index(10).handler(this::lambda$registerRewrites$2);
        this.filter().type(Entity1_14Types.FIREWORK_ROCKET).index(8).handler(EntityPackets1_14::lambda$registerRewrites$3);
        this.filter().filterFamily(Entity1_14Types.ABSTRACT_ARROW).removeIndex(9);
        this.filter().type(Entity1_14Types.VILLAGER).cancel(15);
        final MetaHandler metaHandler = this::lambda$registerRewrites$4;
        this.filter().type(Entity1_14Types.ZOMBIE_VILLAGER).index(18).handler(metaHandler);
        this.filter().type(Entity1_14Types.VILLAGER).index(16).handler(metaHandler);
        this.filter().filterFamily(Entity1_14Types.ABSTRACT_SKELETON).index(13).handler(EntityPackets1_14::lambda$registerRewrites$5);
        this.filter().filterFamily(Entity1_14Types.ZOMBIE).index(13).handler(EntityPackets1_14::lambda$registerRewrites$6);
        this.filter().filterFamily(Entity1_14Types.ZOMBIE).addIndex(16);
        this.filter().filterFamily(Entity1_14Types.LIVINGENTITY).handler(EntityPackets1_14::lambda$registerRewrites$7);
        this.filter().removeIndex(6);
        this.filter().type(Entity1_14Types.OCELOT).index(13).handler(EntityPackets1_14::lambda$registerRewrites$8);
        this.filter().type(Entity1_14Types.CAT).handler(EntityPackets1_14::lambda$registerRewrites$9);
        this.filter().handler(EntityPackets1_14::lambda$registerRewrites$10);
    }
    
    public int villagerDataToProfession(final VillagerData villagerData) {
        switch (villagerData.getProfession()) {
            case 1:
            case 10:
            case 13:
            case 14: {
                return 3;
            }
            case 2:
            case 8: {
                return 4;
            }
            case 3:
            case 9: {
                return 1;
            }
            case 4: {
                return 2;
            }
            case 5:
            case 6:
            case 7:
            case 12: {
                return 0;
            }
            default: {
                return 5;
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_14Types.getTypeFromId(n);
    }
    
    private static void lambda$registerRewrites$10(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType().typeId() > 15) {
            throw new IllegalArgumentException("Unhandled metadata: " + metadata);
        }
    }
    
    private static void lambda$registerRewrites$9(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metaHandlerEvent.index() == 15) {
            metadata.setValue(1);
        }
        else if (metaHandlerEvent.index() == 13) {
            metadata.setValue((byte)((byte)metadata.getValue() & 0x4));
        }
    }
    
    private static void lambda$registerRewrites$8(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metaHandlerEvent.setIndex(15);
        metadata.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, 0);
    }
    
    private static void lambda$registerRewrites$7(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final int index = metaHandlerEvent.index();
        if (index == 12) {
            final Position position = (Position)metadata.getValue();
            if (position != null) {
                final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_13.USE_BED, null, metaHandlerEvent.user());
                create.write(Type.VAR_INT, metaHandlerEvent.entityId());
                create.write(Type.POSITION, position);
                create.scheduleSend(Protocol1_13_2To1_14.class);
            }
            metaHandlerEvent.cancel();
        }
        else if (index > 12) {
            metaHandlerEvent.setIndex(index - 1);
        }
    }
    
    private static void lambda$registerRewrites$6(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (((byte)metadata.getValue() & 0x4) != 0x0) {
            metaHandlerEvent.createExtraMeta(new Metadata(16, Types1_13_2.META_TYPES.booleanType, true));
        }
    }
    
    private static void lambda$registerRewrites$5(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (((byte)metadata.getValue() & 0x4) != 0x0) {
            metaHandlerEvent.createExtraMeta(new Metadata(14, Types1_13_2.META_TYPES.booleanType, true));
        }
    }
    
    private void lambda$registerRewrites$4(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setTypeAndValue(Types1_13_2.META_TYPES.varIntType, this.villagerDataToProfession((VillagerData)metadata.getValue()));
        if (metadata.id() == 16) {
            metaHandlerEvent.setIndex(15);
        }
    }
    
    private static void lambda$registerRewrites$3(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_13_2.META_TYPES.varIntType);
        if (metadata.getValue() == null) {
            metadata.setValue(0);
        }
    }
    
    private void lambda$registerRewrites$2(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        this.rewriteParticle((Particle)metadata.getValue());
    }
    
    private static void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final EntityType entityType = metaHandlerEvent.entityType();
        if (entityType == null) {
            return;
        }
        if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_ILLAGER_BASE) || entityType == Entity1_14Types.RAVAGER || entityType == Entity1_14Types.WITCH) {
            final int index = metaHandlerEvent.index();
            if (index == 14) {
                metaHandlerEvent.cancel();
            }
            else if (index > 14) {
                metaHandlerEvent.setIndex(index - 1);
            }
        }
    }
    
    private void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final int typeId = metadata.metaType().typeId();
        if (typeId <= 15) {
            metadata.setMetaType(Types1_13_2.META_TYPES.byId(typeId));
        }
        final MetaType metaType = metadata.metaType();
        if (metaType == Types1_13_2.META_TYPES.itemType) {
            metadata.setValue(((Protocol1_13_2To1_14)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue()));
        }
        else if (metaType == Types1_13_2.META_TYPES.blockStateType) {
            metadata.setValue(((Protocol1_13_2To1_14)this.protocol).getMappingData().getNewBlockStateId((int)metadata.value()));
        }
    }
    
    static EntityPositionHandler access$000(final EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.positionHandler;
    }
    
    static PacketHandler access$100(final EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.getObjectTrackerHandler();
    }
    
    static Protocol access$200(final EntityPackets1_14 entityPackets1_14) {
        return entityPackets1_14.protocol;
    }
    
    static EntityData access$300(final EntityPackets1_14 entityPackets1_14, final EntityType entityType) {
        return entityPackets1_14.entityDataForType(entityType);
    }
    
    static PacketHandler access$400(final EntityPackets1_14 entityPackets1_14, final Type type) {
        return entityPackets1_14.getMobSpawnRewriter(type);
    }
    
    static PacketHandler access$500(final EntityPackets1_14 entityPackets1_14, final Type type, final EntityType entityType) {
        return entityPackets1_14.getTrackerAndMetaHandler(type, entityType);
    }
    
    static PacketHandler access$600(final EntityPackets1_14 entityPackets1_14, final EntityType entityType, final Type type) {
        return entityPackets1_14.getTrackerHandler(entityType, type);
    }
    
    static PacketHandler access$700(final EntityPackets1_14 entityPackets1_14, final int n) {
        return entityPackets1_14.getDimensionHandler(n);
    }
}
