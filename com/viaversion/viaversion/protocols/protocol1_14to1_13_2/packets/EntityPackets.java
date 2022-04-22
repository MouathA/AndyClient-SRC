package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.*;

public class EntityPackets
{
    public static void register(final Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        final MetadataRewriter1_14To1_13_2 metadataRewriter1_14To1_13_2 = (MetadataRewriter1_14To1_13_2)protocol1_14To1_13_2.get(MetadataRewriter1_14To1_13_2.class);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketRemapper(metadataRewriter1_14To1_13_2, protocol1_14To1_13_2) {
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler() {
                    final EntityPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        int n = this.this$0.val$metadataRewriter.newEntityId(Entity1_13Types.getTypeFromId((int)packetWrapper.get(Type.VAR_INT, 1), true).getId());
                        final EntityType typeFromId = Entity1_14Types.getTypeFromId(n);
                        if (typeFromId != null) {
                            final int intValue2 = (int)packetWrapper.get(Type.INT, 0);
                            if (typeFromId.is(Entity1_14Types.FALLING_BLOCK)) {
                                packetWrapper.set(Type.INT, 0, this.this$0.val$protocol.getMappingData().getNewBlockStateId(intValue2));
                            }
                            else if (typeFromId.is(Entity1_14Types.MINECART)) {
                                switch (intValue2) {
                                    case 1: {
                                        n = Entity1_14Types.CHEST_MINECART.getId();
                                        break;
                                    }
                                    case 2: {
                                        n = Entity1_14Types.FURNACE_MINECART.getId();
                                        break;
                                    }
                                    case 3: {
                                        n = Entity1_14Types.TNT_MINECART.getId();
                                        break;
                                    }
                                    case 4: {
                                        n = Entity1_14Types.SPAWNER_MINECART.getId();
                                        break;
                                    }
                                    case 5: {
                                        n = Entity1_14Types.HOPPER_MINECART.getId();
                                        break;
                                    }
                                    case 6: {
                                        n = Entity1_14Types.COMMAND_BLOCK_MINECART.getId();
                                        break;
                                    }
                                }
                            }
                            else if ((typeFromId.is(Entity1_14Types.ITEM) && intValue2 > 0) || typeFromId.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                                if (typeFromId.isOrHasParent(Entity1_14Types.ABSTRACT_ARROW)) {
                                    packetWrapper.set(Type.INT, 0, intValue2 - 1);
                                }
                                final PacketWrapper create = packetWrapper.create(69);
                                create.write(Type.VAR_INT, intValue);
                                create.write(Type.SHORT, packetWrapper.get(Type.SHORT, 0));
                                create.write(Type.SHORT, packetWrapper.get(Type.SHORT, 1));
                                create.write(Type.SHORT, packetWrapper.get(Type.SHORT, 2));
                                create.scheduleSend(Protocol1_14To1_13_2.class);
                            }
                            packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class).addEntity(intValue, typeFromId);
                        }
                        packetWrapper.set(Type.VAR_INT, 1, n);
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper(metadataRewriter1_14To1_13_2) {
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            
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
                this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.BYTE);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper(metadataRewriter1_14To1_13_2) {
            final MetadataRewriter1_14To1_13_2 val$metadataRewriter;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
                this.handler(this.val$metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, Entity1_14Types.PLAYER));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.ENTITY_ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE) == 2) {
                            final EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                            final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                            entityTracker1_14.setSleeping(intValue, false);
                            final PacketWrapper create = packetWrapper.create(ClientboundPackets1_14.ENTITY_METADATA);
                            create.write(Type.VAR_INT, intValue);
                            final LinkedList<Metadata> list = new LinkedList<Metadata>();
                            if (entityTracker1_14.clientEntityId() != intValue) {
                                list.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(intValue, entityTracker1_14)));
                            }
                            list.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, null));
                            create.write(Types1_14.METADATA_LIST, list);
                            create.scheduleSend(Protocol1_14To1_13_2.class);
                        }
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.USE_BED, ClientboundPackets1_14.ENTITY_METADATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        entityTracker1_14.setSleeping(intValue, true);
                        final Position position = (Position)packetWrapper.read(Type.POSITION);
                        final LinkedList<Metadata> list = new LinkedList<Metadata>();
                        list.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, position));
                        if (entityTracker1_14.clientEntityId() != intValue) {
                            list.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(intValue, entityTracker1_14)));
                        }
                        packetWrapper.write(Types1_14.METADATA_LIST, list);
                    }
                });
            }
        });
        metadataRewriter1_14To1_13_2.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
        metadataRewriter1_14To1_13_2.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
    }
}
