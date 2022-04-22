package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.api.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.data.entity.*;

public class SpawnPackets
{
    public static final ValueTransformer toNewDouble;
    
    public static void register(final Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final SpawnPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.UUID, ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getEntityUUID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final SpawnPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.getTypeFromId(byteValue, true));
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final SpawnPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.INT, 0) > 0) {
                            (short)packetWrapper.read(Type.SHORT);
                            (short)packetWrapper.read(Type.SHORT);
                            (short)packetWrapper.read(Type.SHORT);
                        }
                        packetWrapper.write(Type.SHORT, 0);
                        packetWrapper.write(Type.SHORT, 0);
                        packetWrapper.write(Type.SHORT, 0);
                    }
                });
                this.handler(new PacketHandler() {
                    final SpawnPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 0);
                        if (Entity1_10Types.getTypeFromId((byte)packetWrapper.get(Type.BYTE, 0), true) == Entity1_10Types.EntityType.SPLASH_POTION) {
                            final PacketWrapper create = packetWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, new PacketHandler(intValue, intValue2) {
                                final int val$entityID;
                                final int val$data;
                                final SpawnPackets$2$4 this$1;
                                
                                @Override
                                public void handle(final PacketWrapper packetWrapper) throws Exception {
                                    packetWrapper.write(Type.VAR_INT, this.val$entityID);
                                    final ArrayList<Metadata> list = new ArrayList<Metadata>();
                                    final DataItem dataItem = new DataItem(373, (byte)1, (short)this.val$data, null);
                                    ItemRewriter.toClient(dataItem);
                                    list.add(new Metadata(5, MetaType1_9.Slot, dataItem));
                                    packetWrapper.write(Types1_9.METADATA_LIST, list);
                                }
                            });
                            packetWrapper.send(Protocol1_9To1_8.class);
                            create.send(Protocol1_9To1_8.class);
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final SpawnPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.SHORT);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final SpawnPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.EntityType.LIGHTNING);
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper(protocol1_9To1_8) {
            final Protocol1_9To1_8 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final SpawnPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.UUID, ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getEntityUUID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final SpawnPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.getTypeFromId(shortValue, false));
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final SpawnPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).hasEntity(intValue)) {
                            ((MetadataRewriter1_9To1_8)this.this$0.val$protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(intValue, list, packetWrapper.user());
                        }
                        else {
                            Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + intValue);
                            list.clear();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final SpawnPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(Types1_9.METADATA_LIST, 0));
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final SpawnPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.EntityType.PAINTING);
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.handler(new PacketHandler() {
                    final SpawnPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.UUID, ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getEntityUUID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
                this.map(Type.STRING);
                this.map(Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper(protocol1_9To1_8) {
            final Protocol1_9To1_8 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(new PacketHandler() {
                    final SpawnPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        entityTracker1_9.addEntity(intValue, Entity1_10Types.EntityType.PLAYER);
                        entityTracker1_9.sendMetadataBuffer(intValue);
                    }
                });
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final SpawnPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.read(Type.SHORT);
                        if (shortValue != 0) {
                            final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, null, packetWrapper.user());
                            create.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                            create.write(Type.VAR_INT, 0);
                            create.write(Type.ITEM, new DataItem(shortValue, (byte)1, (short)0, null));
                            create.send(Protocol1_9To1_8.class);
                        }
                    }
                });
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final SpawnPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).hasEntity(intValue)) {
                            ((MetadataRewriter1_9To1_8)this.this$0.val$protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(intValue, list, packetWrapper.user());
                        }
                        else {
                            Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + intValue);
                            list.clear();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final SpawnPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(Types1_9.METADATA_LIST, 0));
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(new PacketHandler() {
                    final SpawnPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int[] array = (int[])packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
                        final EntityTracker entityTracker = packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        final int[] array2 = array;
                        while (0 < array2.length) {
                            entityTracker.removeEntity(array2[0]);
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
    }
    
    static {
        toNewDouble = new ValueTransformer(Type.DOUBLE) {
            public Double transform(final PacketWrapper packetWrapper, final Integer n) {
                return n / 32.0;
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Integer)o);
            }
        };
    }
}
