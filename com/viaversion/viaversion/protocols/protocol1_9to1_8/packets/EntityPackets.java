package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class EntityPackets
{
    public static final ValueTransformer toNewShort;
    
    public static void register(final Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BOOLEAN, new ValueTransformer((Type)Type.NOTHING) {
                    final EntityPackets$2 this$0;
                    
                    public Void transform(final PacketWrapper packetWrapper, final Boolean b) throws Exception {
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (!b) {
                            final int intValue = (int)packetWrapper.get(Type.INT, 0);
                            final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                            packetWrapper.cancel();
                            final PacketWrapper create = packetWrapper.create(ClientboundPackets1_9.SET_PASSENGERS);
                            if (intValue2 == -1) {
                                if (!entityTracker1_9.getVehicleMap().containsKey(intValue)) {
                                    return null;
                                }
                                create.write(Type.VAR_INT, entityTracker1_9.getVehicleMap().remove(intValue));
                                create.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                            }
                            else {
                                create.write(Type.VAR_INT, intValue2);
                                create.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { intValue });
                                entityTracker1_9.getVehicleMap().put(intValue, intValue2);
                            }
                            create.send(Protocol1_9To1_8.class);
                        }
                        return null;
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Boolean)o);
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.INT, SpawnPackets.toNewDouble);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final EntityPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (Via.getConfig().isHologramPatch() && ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getKnownHolograms().contains(intValue)) {
                            packetWrapper.set(Type.DOUBLE, 1, (double)packetWrapper.get(Type.DOUBLE, 1) + Via.getConfig().getHologramYOffset());
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BYTE, EntityPackets.toNewShort);
                this.map(Type.BOOLEAN);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.SHORT, new ValueTransformer((Type)Type.VAR_INT) {
                    final EntityPackets$6 this$0;
                    
                    public Integer transform(final PacketWrapper packetWrapper, final Short n) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId()) {
                            return n + 2;
                        }
                        return (Integer)((n > 0) ? (n + 1) : n);
                    }
                    
                    @Override
                    public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                        return this.transform(packetWrapper, (Short)o);
                    }
                });
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final EntityPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0));
                    }
                });
                this.handler(new PacketHandler() {
                    final EntityPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                        if (item != null && Protocol1_9To1_8.isSword(item.identifier())) {
                            entityTracker1_9.getValidBlocking().add(intValue);
                            return;
                        }
                        entityTracker1_9.getValidBlocking().remove(intValue);
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper(protocol1_9To1_8) {
            final Protocol1_9To1_8 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
                this.handler(new PacketHandler() {
                    final EntityPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final List list = (List)packetWrapper.get(Types1_9.METADATA_LIST, 0);
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (entityTracker1_9.hasEntity(intValue)) {
                            ((MetadataRewriter1_9To1_8)this.this$0.val$protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(intValue, list, packetWrapper.user());
                        }
                        else {
                            entityTracker1_9.addMetadataToBuffer(intValue, list);
                            packetWrapper.cancel();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final EntityPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).handleMetadata((int)packetWrapper.get(Type.VAR_INT, 0), (List)packetWrapper.get(Types1_9.METADATA_LIST, 0));
                    }
                });
                this.handler(new PacketHandler() {
                    final EntityPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((List)packetWrapper.get(Types1_9.METADATA_LIST, 0)).isEmpty()) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                        final boolean newEffectIndicator = Via.getConfig().isNewEffectIndicator();
                        packetWrapper.write(Type.BYTE, (byte)(booleanValue ? (newEffectIndicator ? 2 : 1) : 0));
                    }
                });
            }
        });
        protocol1_9To1_8.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.COMBAT_EVENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 2) {
                            packetWrapper.passthrough(Type.VAR_INT);
                            packetWrapper.passthrough(Type.INT);
                            Protocol1_9To1_8.FIX_JSON.write(packetWrapper, packetWrapper.read(Type.STRING));
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (!Via.getConfig().isMinimizeCooldown()) {
                            return;
                        }
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) != ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).getProvidedEntityId()) {
                            return;
                        }
                        final int intValue = (int)packetWrapper.read(Type.INT);
                        final HashMap hashMap = new HashMap<Object, Object>(intValue);
                        while (0 < intValue) {
                            final String s = (String)packetWrapper.read(Type.STRING);
                            final Double n = (Double)packetWrapper.read(Type.DOUBLE);
                            final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                            final ArrayList list = new ArrayList<Triple>(intValue2);
                            while (0 < intValue2) {
                                list.add(new Triple(packetWrapper.read(Type.UUID), packetWrapper.read(Type.DOUBLE), packetWrapper.read(Type.BYTE)));
                                int n2 = 0;
                                ++n2;
                            }
                            hashMap.put(s, new Pair(n, list));
                            int n3 = 0;
                            ++n3;
                        }
                        hashMap.put("generic.attackSpeed", new Pair(15.9, ImmutableList.of(new Triple(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), 0.0, 0), new Triple(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), 0.0, 2), new Triple(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), 0.0, 2))));
                        packetWrapper.write(Type.INT, hashMap.size());
                        for (final Map.Entry<Object, Object> entry : hashMap.entrySet()) {
                            packetWrapper.write(Type.STRING, entry.getKey());
                            packetWrapper.write(Type.DOUBLE, entry.getValue().key());
                            packetWrapper.write(Type.VAR_INT, ((List)entry.getValue().value()).size());
                            for (final Triple triple : (List)entry.getValue().value()) {
                                packetWrapper.write(Type.UUID, triple.first());
                                packetWrapper.write(Type.DOUBLE, triple.second());
                                packetWrapper.write(Type.BYTE, triple.third());
                            }
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.ENTITY_ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final EntityPackets$11 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.ENTITY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$12 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                        if (intValue == 6 || intValue == 8) {
                            packetWrapper.cancel();
                        }
                        if (intValue == 7) {
                            packetWrapper.set(Type.VAR_INT, 1, 6);
                        }
                    }
                });
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.INTERACT_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final EntityPackets$13 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                        if (intValue == 2) {
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.passthrough(Type.FLOAT);
                            packetWrapper.passthrough(Type.FLOAT);
                        }
                        if ((intValue == 0 || intValue == 2) && (int)packetWrapper.read(Type.VAR_INT) == 1) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
    }
    
    static {
        toNewShort = new ValueTransformer(Type.SHORT) {
            public Short transform(final PacketWrapper packetWrapper, final Byte b) {
                return (short)(b * 128);
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Byte)o);
            }
        };
    }
}
