package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_11To1_10 extends AbstractProtocol
{
    private static final ValueTransformer toOldByte;
    private final EntityRewriter entityRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_11To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.entityRewriter = new MetadataRewriter1_11To1_10(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_11To1_10.access$000(this.this$0).objectTrackerHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE, Type.VAR_INT);
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
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        final Entity1_11Types.EntityType rewriteEntityType = MetadataRewriter1_11To1_10.rewriteEntityType((int)packetWrapper.get(Type.VAR_INT, 1), (List)packetWrapper.get(Types1_9.METADATA_LIST, 0));
                        if (rewriteEntityType != null) {
                            packetWrapper.set(Type.VAR_INT, 1, rewriteEntityType.getId());
                            packetWrapper.user().getEntityTracker(Protocol1_11To1_10.class).addEntity(intValue, rewriteEntityType);
                            Protocol1_11To1_10.access$000(this.this$1.this$0).handleMetadata(intValue, (List)packetWrapper.get(Types1_9.METADATA_LIST, 0), packetWrapper.user());
                        }
                    }
                });
            }
        });
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.VAR_INT, 1);
                    }
                });
            }
        });
        this.entityRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        this.registerClientbound(ClientboundPackets1_9_3.ENTITY_TELEPORT, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (Via.getConfig().isHologramPatch() && ((EntityTracker1_11)packetWrapper.user().getEntityTracker(Protocol1_11To1_10.class)).isHologram(intValue)) {
                            packetWrapper.set(Type.DOUBLE, 1, (double)packetWrapper.get(Type.DOUBLE, 1) - Via.getConfig().getHologramYOffset());
                        }
                    }
                });
            }
        });
        this.entityRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue >= 2) {
                            packetWrapper.set(Type.VAR_INT, 0, intValue + 1);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ACTION, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (Via.getConfig().isPistonAnimationPatch()) {
                            final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                            if (intValue == 33 || intValue == 29) {
                                packetWrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$8 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                            EntityIdRewriter.toClientSpawner(compoundTag);
                        }
                        if (compoundTag.contains("id")) {
                            ((StringTag)compoundTag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier((String)compoundTag.get("id").getValue()));
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$9 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)));
                        if (chunk.getBlockEntities() == null) {
                            return;
                        }
                        for (final CompoundTag compoundTag : chunk.getBlockEntities()) {
                            if (compoundTag.contains("id")) {
                                final String value = ((StringTag)compoundTag.get("id")).getValue();
                                if (value.equals("MobSpawner")) {
                                    EntityIdRewriter.toClientSpawner(compoundTag);
                                }
                                ((StringTag)compoundTag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier(value));
                            }
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(Protocol1_11To1_10$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(Protocol1_11To1_10$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(Protocol1_11To1_10$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((int)packetWrapper.get(Type.INT, 0) == 2002) {
                    (int)packetWrapper.get(Type.INT, 1);
                    final Pair newData = PotionColorMapping.getNewData(0);
                    if (newData == null) {
                        Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + 0 + ")");
                    }
                    else {
                        (int)newData.key();
                        (boolean)newData.value();
                    }
                    packetWrapper.set(Type.INT, 1, 0);
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() {
            final Protocol1_11To1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final Protocol1_11To1_10$14 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.length() > 100) {
                            packetWrapper.set(Type.STRING, 0, s.substring(0, 100));
                        }
                    }
                });
            }
        });
    }
    
    private int getNewSoundId(int n) {
        if (n == 196) {
            return -1;
        }
        if (n >= 85) {
            n += 2;
        }
        if (n >= 176) {
            ++n;
        }
        if (n >= 197) {
            n += 8;
        }
        if (n >= 207) {
            --n;
        }
        if (n >= 279) {
            n += 9;
        }
        if (n >= 296) {
            ++n;
        }
        if (n >= 390) {
            n += 4;
        }
        if (n >= 400) {
            n += 3;
        }
        if (n >= 450) {
            ++n;
        }
        if (n >= 455) {
            ++n;
        }
        if (n >= 470) {
            ++n;
        }
        return n;
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_11(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    @Override
    public com.viaversion.viaversion.api.rewriter.EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }
    
    static EntityRewriter access$000(final Protocol1_11To1_10 protocol1_11To1_10) {
        return protocol1_11To1_10.entityRewriter;
    }
    
    static ValueTransformer access$100() {
        return Protocol1_11To1_10.toOldByte;
    }
    
    static {
        toOldByte = new ValueTransformer(Type.UNSIGNED_BYTE) {
            public Short transform(final PacketWrapper packetWrapper, final Float n) throws Exception {
                return (short)(n * 16.0f);
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Float)o);
            }
        };
    }
}
