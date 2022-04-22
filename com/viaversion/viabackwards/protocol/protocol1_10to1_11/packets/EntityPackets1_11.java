package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viabackwards.api.entities.storage.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityPackets1_11 extends LegacyEntityRewriter
{
    public EntityPackets1_11(final Protocol1_10To1_11 protocol1_10To1_11) {
        super(protocol1_10To1_11);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketRemapper() {
            final EntityPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(EntityPackets1_11$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (intValue == 2002 || intValue == 2007) {
                    if (intValue == 2007) {
                        packetWrapper.set(Type.INT, 0, 2002);
                    }
                    final int oldData = PotionSplashHandler.getOldData((int)packetWrapper.get(Type.INT, 1));
                    if (oldData != -1) {
                        packetWrapper.set(Type.INT, 1, oldData);
                    }
                }
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            final EntityPackets1_11 this$0;
            
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
                this.handler(EntityPackets1_11.access$000(this.this$0));
                this.handler(EntityPackets1_11.access$100(this.this$0, EntityPackets1_11$2::lambda$registerMap$0));
                this.handler(new PacketHandler() {
                    final EntityPackets1_11$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Optional byId = Entity1_12Types.ObjectType.findById((byte)packetWrapper.get(Type.BYTE, 0));
                        if (byId.isPresent() && byId.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                            final int intValue = (int)packetWrapper.get(Type.INT, 0);
                            final Block handleBlock = ((Protocol1_10To1_11)EntityPackets1_11.access$200(this.this$1.this$0)).getItemRewriter().handleBlock(intValue & 0xFFF, intValue >> 12 & 0xF);
                            if (handleBlock == null) {
                                return;
                            }
                            packetWrapper.set(Type.INT, 0, handleBlock.getId() | handleBlock.getData() << 12);
                        }
                    }
                });
            }
            
            private static ObjectType lambda$registerMap$0(final Byte b) {
                return Entity1_11Types.ObjectType.findById(b).orElse(null);
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, Entity1_11Types.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, Entity1_11Types.EntityType.WEATHER);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final EntityPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11.access$300(this.this$0, Type.UNSIGNED_BYTE, 0));
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityType entityType = this.this$0.tracker(packetWrapper.user()).entityType((int)packetWrapper.get(Type.VAR_INT, 0));
                final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                this.this$0.handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), list, packetWrapper.user());
                final EntityData access$400 = EntityPackets1_11.access$400(this.this$0, entityType);
                if (access$400 != null) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, (short)access$400.replacementId());
                    if (access$400.hasBaseMeta()) {
                        access$400.defaultMeta().createMeta(new WrappedMetadata(list));
                    }
                }
                if (list.isEmpty()) {
                    list.add(new Metadata(0, MetaType1_9.Byte, 0));
                }
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.SPAWN_PAINTING, Entity1_11Types.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.JOIN_GAME, Entity1_11Types.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() {
            final EntityPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_9.METADATA_LIST);
                this.handler(EntityPackets1_11.access$500(this.this$0, Types1_9.METADATA_LIST, Entity1_11Types.EntityType.PLAYER));
                this.handler(EntityPackets1_11$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                if (list.isEmpty()) {
                    list.add(new Metadata(0, MetaType1_9.Byte, 0));
                }
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ENTITY_STATUS, new PacketRemapper() {
            final EntityPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final EntityPackets1_11$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((byte)packetWrapper.get(Type.BYTE, 0) == 35) {
                            packetWrapper.clearPacket();
                            packetWrapper.setId(30);
                            packetWrapper.write(Type.UNSIGNED_BYTE, 10);
                            packetWrapper.write(Type.FLOAT, 0.0f);
                        }
                    }
                });
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ELDER_GUARDIAN, Entity1_11Types.EntityType.GUARDIAN);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.WITHER_SKELETON, Entity1_11Types.EntityType.SKELETON).plainName().spawnMetadata(this::lambda$registerRewrites$0);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.STRAY, Entity1_11Types.EntityType.SKELETON).plainName().spawnMetadata(this::lambda$registerRewrites$1);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.HUSK, Entity1_11Types.EntityType.ZOMBIE).plainName().spawnMetadata(this::lambda$registerRewrites$2);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_VILLAGER, Entity1_11Types.EntityType.ZOMBIE).spawnMetadata(this::lambda$registerRewrites$3);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$4);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.DONKEY, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$5);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.MULE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$6);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.SKELETON_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$7);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.ZOMBIE_HORSE, Entity1_11Types.EntityType.HORSE).spawnMetadata(this::lambda$registerRewrites$8);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_FANGS, Entity1_11Types.EntityType.SHULKER);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.EVOCATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName();
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.VEX, Entity1_11Types.EntityType.BAT).plainName();
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.VINDICATION_ILLAGER, Entity1_11Types.EntityType.VILLAGER).plainName().spawnMetadata(EntityPackets1_11::lambda$registerRewrites$9);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA, Entity1_11Types.EntityType.HORSE).plainName().spawnMetadata(this::lambda$registerRewrites$10);
        this.mapEntityTypeWithData(Entity1_11Types.EntityType.LIAMA_SPIT, Entity1_11Types.EntityType.SNOWBALL);
        this.mapObjectType(Entity1_11Types.ObjectType.LIAMA_SPIT, Entity1_11Types.ObjectType.SNOWBALL, -1);
        this.mapObjectType(Entity1_11Types.ObjectType.EVOCATION_FANGS, Entity1_11Types.ObjectType.FALLING_BLOCK, 4294);
        this.filter().filterFamily(Entity1_11Types.EntityType.GUARDIAN).index(12).handler(EntityPackets1_11::lambda$registerRewrites$11);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
        this.filter().filterFamily(Entity1_11Types.EntityType.ZOMBIE).handler(EntityPackets1_11::lambda$registerRewrites$12);
        this.filter().type(Entity1_11Types.EntityType.EVOCATION_ILLAGER).index(12).handler(EntityPackets1_11::lambda$registerRewrites$13);
        this.filter().type(Entity1_11Types.EntityType.VEX).index(12).handler(EntityPackets1_11::lambda$registerRewrites$14);
        this.filter().type(Entity1_11Types.EntityType.VINDICATION_ILLAGER).index(12).handler(EntityPackets1_11::lambda$registerRewrites$15);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(13).handler(this::lambda$registerRewrites$16);
        this.filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).handler(this::lambda$registerRewrites$17);
        this.filter().type(Entity1_11Types.EntityType.HORSE).index(16).toIndex(17);
        this.filter().filterFamily(Entity1_11Types.EntityType.CHESTED_HORSE).index(15).handler(this::lambda$registerRewrites$18);
        this.filter().type(Entity1_11Types.EntityType.LIAMA).handler(this::lambda$registerRewrites$19);
        this.filter().filterFamily(Entity1_11Types.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
        this.filter().type(Entity1_11Types.EntityType.VILLAGER).index(13).handler(EntityPackets1_11::lambda$registerRewrites$20);
        this.filter().type(Entity1_11Types.EntityType.SHULKER).cancel(15);
    }
    
    private Metadata getSkeletonTypeMeta(final int n) {
        return new Metadata(12, MetaType1_9.VarInt, n);
    }
    
    private Metadata getZombieTypeMeta(final int n) {
        return new Metadata(13, MetaType1_9.VarInt, n);
    }
    
    private void handleZombieType(final WrappedMetadata wrappedMetadata, final int n) {
        if (wrappedMetadata.get(13) == null) {
            wrappedMetadata.add(this.getZombieTypeMeta(n));
        }
    }
    
    private Metadata getHorseMetaType(final int n) {
        return new Metadata(14, MetaType1_9.VarInt, n);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, false);
    }
    
    @Override
    protected EntityType getObjectTypeFromId(final int n) {
        return Entity1_11Types.getTypeFromId(n, true);
    }
    
    private static void lambda$registerRewrites$20(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if ((int)metadata.getValue() == 5) {
            metadata.setValue(0);
        }
    }
    
    private void lambda$registerRewrites$19(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final ChestedHorseStorage chestedHorseStorage = (ChestedHorseStorage)this.storedEntityData(metaHandlerEvent).get(ChestedHorseStorage.class);
        switch (metaHandlerEvent.index()) {
            case 16: {
                chestedHorseStorage.setLiamaStrength((int)metadata.getValue());
                metaHandlerEvent.cancel();
                break;
            }
            case 17: {
                chestedHorseStorage.setLiamaCarpetColor((int)metadata.getValue());
                metaHandlerEvent.cancel();
                break;
            }
            case 18: {
                chestedHorseStorage.setLiamaVariant((int)metadata.getValue());
                metaHandlerEvent.cancel();
                break;
            }
        }
    }
    
    private void lambda$registerRewrites$18(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        ((ChestedHorseStorage)this.storedEntityData(metaHandlerEvent).get(ChestedHorseStorage.class)).setChested((boolean)metadata.getValue());
        metaHandlerEvent.cancel();
    }
    
    private void lambda$registerRewrites$17(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        if (!storedEntityData.has(ChestedHorseStorage.class)) {
            storedEntityData.put(new ChestedHorseStorage());
        }
    }
    
    private void lambda$registerRewrites$16(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        final StoredEntityData storedEntityData = this.storedEntityData(metaHandlerEvent);
        final byte byteValue = (byte)metadata.getValue();
        if (storedEntityData.has(ChestedHorseStorage.class) && ((ChestedHorseStorage)storedEntityData.get(ChestedHorseStorage.class)).isChested()) {
            metadata.setValue((byte)(byteValue | 0x8));
        }
    }
    
    private static void lambda$registerRewrites$15(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metaHandlerEvent.setIndex(13);
        metadata.setTypeAndValue(MetaType1_9.VarInt, (((Number)metadata.getValue()).intValue() == 1) ? 2 : 4);
    }
    
    private static void lambda$registerRewrites$14(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setValue(0);
    }
    
    private static void lambda$registerRewrites$13(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metaHandlerEvent.setIndex(13);
        metadata.setTypeAndValue(MetaType1_9.VarInt, (int)metadata.getValue());
    }
    
    private static void lambda$registerRewrites$12(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        switch (metadata.id()) {
            case 13: {
                metaHandlerEvent.cancel();
            }
            case 14: {
                metaHandlerEvent.setIndex(15);
                break;
            }
            case 15: {
                metaHandlerEvent.setIndex(14);
                break;
            }
            case 16: {
                metaHandlerEvent.setIndex(13);
                metadata.setValue(1 + (int)metadata.getValue());
                break;
            }
        }
    }
    
    private static void lambda$registerRewrites$11(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        int n = metadata.getValue() ? 2 : 0;
        if (metaHandlerEvent.entityType() == Entity1_11Types.EntityType.ELDER_GUARDIAN) {
            n |= 0x4;
        }
        metadata.setTypeAndValue(MetaType1_9.Byte, (byte)n);
    }
    
    private void lambda$registerRewrites$10(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(1));
    }
    
    private static void lambda$registerRewrites$9(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(new Metadata(13, MetaType1_9.VarInt, 4));
    }
    
    private void lambda$registerRewrites$8(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(3));
    }
    
    private void lambda$registerRewrites$7(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(4));
    }
    
    private void lambda$registerRewrites$6(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(2));
    }
    
    private void lambda$registerRewrites$5(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(1));
    }
    
    private void lambda$registerRewrites$4(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getHorseMetaType(0));
    }
    
    private void lambda$registerRewrites$3(final WrappedMetadata wrappedMetadata) {
        this.handleZombieType(wrappedMetadata, 1);
    }
    
    private void lambda$registerRewrites$2(final WrappedMetadata wrappedMetadata) {
        this.handleZombieType(wrappedMetadata, 6);
    }
    
    private void lambda$registerRewrites$1(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getSkeletonTypeMeta(2));
    }
    
    private void lambda$registerRewrites$0(final WrappedMetadata wrappedMetadata) {
        wrappedMetadata.add(this.getSkeletonTypeMeta(1));
    }
    
    static PacketHandler access$000(final EntityPackets1_11 entityPackets1_11) {
        return entityPackets1_11.getObjectTrackerHandler();
    }
    
    static PacketHandler access$100(final EntityPackets1_11 entityPackets1_11, final Function function) {
        return entityPackets1_11.getObjectRewriter(function);
    }
    
    static Protocol access$200(final EntityPackets1_11 entityPackets1_11) {
        return entityPackets1_11.protocol;
    }
    
    static PacketHandler access$300(final EntityPackets1_11 entityPackets1_11, final Type type, final int n) {
        return entityPackets1_11.getTrackerHandler(type, n);
    }
    
    static EntityData access$400(final EntityPackets1_11 entityPackets1_11, final EntityType entityType) {
        return entityPackets1_11.entityDataForType(entityType);
    }
    
    static PacketHandler access$500(final EntityPackets1_11 entityPackets1_11, final Type type, final EntityType entityType) {
        return entityPackets1_11.getTrackerAndMetaHandler(type, entityType);
    }
}
