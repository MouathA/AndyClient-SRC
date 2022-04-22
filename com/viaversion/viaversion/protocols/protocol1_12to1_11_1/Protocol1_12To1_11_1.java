package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.data.entity.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_12To1_11_1 extends AbstractProtocol
{
    private final EntityRewriter metadataRewriter;
    private final ItemRewriter itemRewriter;
    
    public Protocol1_12To1_11_1() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
        this.metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
        this.itemRewriter = new InventoryPackets(this);
    }
    
    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_12To1_11_1.access$000(this.this$0).objectTrackerHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
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
                this.map(Types1_12.METADATA_LIST);
                this.handler(Protocol1_12To1_11_1.access$000(this.this$0).trackerAndRewriterHandler(Types1_12.METADATA_LIST));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_12To1_11_1$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (!Via.getConfig().is1_12NBTArrayFix()) {
                            return;
                        }
                        final JsonElement jsonElement = (JsonElement)Protocol1_9To1_8.FIX_JSON.transform(null, ((JsonElement)packetWrapper.passthrough(Type.COMPONENT)).toString());
                        TranslateRewriter.toClient(jsonElement, packetWrapper.user());
                        ChatItemRewriter.toClient(jsonElement, packetWrapper.user());
                        packetWrapper.set(Type.COMPONENT, 0, jsonElement);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_12To1_11_1$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)));
                        while (0 < chunk.getSections().length) {
                            final ChunkSection chunkSection = chunk.getSections()[0];
                            if (chunkSection != null) {
                                while (0 < 16) {
                                    while (0 < 16) {
                                        while (0 < 16) {
                                            if (chunkSection.getBlockWithoutData(0, 0, 0) == 26) {
                                                final CompoundTag compoundTag = new CompoundTag();
                                                compoundTag.put("color", new IntTag(14));
                                                compoundTag.put("x", new IntTag(0 + (chunk.getX() << 4)));
                                                compoundTag.put("y", new IntTag(0));
                                                compoundTag.put("z", new IntTag(0 + (chunk.getZ() << 4)));
                                                compoundTag.put("id", new StringTag("minecraft:bed"));
                                                chunk.getBlockEntities().add(compoundTag);
                                            }
                                            int n = 0;
                                            ++n;
                                        }
                                        int n2 = 0;
                                        ++n2;
                                    }
                                    int n3 = 0;
                                    ++n3;
                                }
                            }
                            int n4 = 0;
                            ++n4;
                        }
                    }
                });
            }
        });
        this.metadataRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.metadataRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(Protocol1_12To1_11_1$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(Protocol1_12To1_11_1$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
            }
        });
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
        this.registerServerbound(ServerboundPackets1_12.CLIENT_SETTINGS, new PacketRemapper() {
            final Protocol1_12To1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final Protocol1_12To1_11_1$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.length() > 7) {
                            packetWrapper.set(Type.STRING, 0, s.substring(0, 7));
                        }
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
        this.cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
    }
    
    private int getNewSoundId(final int n) {
        int n2 = n;
        if (n >= 26) {
            n2 += 2;
        }
        if (n >= 70) {
            n2 += 4;
        }
        if (n >= 74) {
            ++n2;
        }
        if (n >= 143) {
            n2 += 3;
        }
        if (n >= 185) {
            ++n2;
        }
        if (n >= 263) {
            n2 += 7;
        }
        if (n >= 301) {
            n2 += 33;
        }
        if (n >= 317) {
            n2 += 2;
        }
        if (n >= 491) {
            n2 += 3;
        }
        return n2;
    }
    
    @Override
    public void register(final ViaProviders viaProviders) {
        viaProviders.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_12Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    @Override
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }
    
    @Override
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
    
    @Override
    public com.viaversion.viaversion.api.rewriter.EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }
    
    static EntityRewriter access$000(final Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        return protocol1_12To1_11_1.metadataRewriter;
    }
}
