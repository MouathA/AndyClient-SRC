package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public class Protocol1_10To1_9_3_4 extends AbstractProtocol
{
    public static final ValueTransformer TO_NEW_PITCH;
    public static final ValueTransformer TRANSFORM_METADATA;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_10To1_9_3_4() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$1.this$0.getNewSoundId((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.ENTITY_METADATA, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$8 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$9 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$10 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)));
                        if (Via.getConfig().isReplacePistons()) {
                            final int pistonReplacementId = Via.getConfig().getPistonReplacementId();
                            final ChunkSection[] sections = chunk.getSections();
                            while (0 < sections.length) {
                                final ChunkSection chunkSection = sections[0];
                                if (chunkSection != null) {
                                    chunkSection.replacePaletteEntry(36, pistonReplacementId);
                                }
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESOURCE_PACK, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ResourcePackTracker)packetWrapper.user().get(ResourcePackTracker.class)).setLastHash((String)packetWrapper.get(Type.STRING, 1));
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper() {
            final Protocol1_10To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_10To1_9_3_4$12 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.STRING, ((ResourcePackTracker)packetWrapper.user().get(ResourcePackTracker.class)).getLastHash());
                        packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.VAR_INT));
                    }
                });
            }
        });
    }
    
    public int getNewSoundId(final int n) {
        int n2 = n;
        if (n >= 24) {
            ++n2;
        }
        if (n >= 248) {
            n2 += 4;
        }
        if (n >= 296) {
            n2 += 6;
        }
        if (n >= 354) {
            n2 += 4;
        }
        if (n >= 372) {
            n2 += 4;
        }
        return n2;
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new ResourcePackTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    static {
        TO_NEW_PITCH = new ValueTransformer(Type.FLOAT) {
            public Float transform(final PacketWrapper packetWrapper, final Short n) throws Exception {
                return n / 63.0f;
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Short)o);
            }
        };
        TRANSFORM_METADATA = new ValueTransformer(Types1_9.METADATA_LIST) {
            public List transform(final PacketWrapper packetWrapper, final List list) throws Exception {
                final CopyOnWriteArrayList<Metadata> list2 = new CopyOnWriteArrayList<Metadata>(list);
                for (final Metadata metadata : list2) {
                    if (metadata.id() >= 5) {
                        metadata.setId(metadata.id() + 1);
                    }
                }
                return list2;
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (List)o);
            }
        };
    }
}
