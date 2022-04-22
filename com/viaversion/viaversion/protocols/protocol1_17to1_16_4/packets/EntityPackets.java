package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public final class EntityPackets extends EntityRewriter
{
    public EntityPackets(final Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        super(protocol1_17To1_16_4);
        this.mapTypes(Entity1_16_2Types.values(), Entity1_17Types.class);
    }
    
    public void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_MOB);
        this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_17Types.PLAYER);
        this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.DESTROY_ENTITIES, null, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(EntityPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int[] array = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                packetWrapper.cancel();
                final EntityTracker entityTracker = packetWrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
                final int[] array2 = array;
                while (0 < array2.length) {
                    final int n = array2[0];
                    entityTracker.removeEntity(n);
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                    create.write(Type.VAR_INT, n);
                    create.send(Protocol1_17To1_16_4.class);
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.ENTITY_PROPERTIES, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.INT));
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.PLAYER_POSITION, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.COMBAT_EVENT, null, new PacketRemapper() {
            final EntityPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(EntityPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                ClientboundPackets1_17 clientboundPackets1_17 = null;
                switch (intValue) {
                    case 0: {
                        clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_ENTER;
                        break;
                    }
                    case 1: {
                        clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_END;
                        break;
                    }
                    case 2: {
                        clientboundPackets1_17 = ClientboundPackets1_17.COMBAT_KILL;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid combat type received: " + intValue);
                    }
                }
                packetWrapper.setId(clientboundPackets1_17.getId());
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).cancelClientbound(ClientboundPackets1_16_2.ENTITY_MOVEMENT);
    }
    
    @Override
    protected void registerRewrites() {
        this.filter().handler(EntityPackets::lambda$registerRewrites$0);
        this.registerMetaTypeHandler(Types1_17.META_TYPES.itemType, Types1_17.META_TYPES.blockStateType, Types1_17.META_TYPES.particleType);
        this.filter().filterFamily(Entity1_17Types.ENTITY).addIndex(7);
        this.filter().filterFamily(Entity1_17Types.MINECART_ABSTRACT).index(11).handler(this::lambda$registerRewrites$1);
        this.filter().type(Entity1_17Types.SHULKER).removeIndex(17);
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_17Types.getTypeFromId(n);
    }
    
    private void lambda$registerRewrites$1(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setValue(((Protocol1_17To1_16_4)this.protocol).getMappingData().getNewBlockStateId((int)metadata.getValue()));
    }
    
    private static void lambda$registerRewrites$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        metadata.setMetaType(Types1_17.META_TYPES.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == Types1_17.META_TYPES.poseType) {
            final int intValue = (int)metadata.value();
            if (intValue > 5) {
                metadata.setValue(intValue + 1);
            }
        }
    }
}
