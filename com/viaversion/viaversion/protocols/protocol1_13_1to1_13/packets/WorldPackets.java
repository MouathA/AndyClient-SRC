package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class WorldPackets
{
    public static void register(final Protocol protocol) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION);
        protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper(protocol) {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ChunkSection[] sections = ((Chunk)packetWrapper.passthrough(new Chunk1_13Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)))).getSections();
                        while (0 < sections.length) {
                            final ChunkSection chunkSection = sections[0];
                            if (chunkSection != null) {
                                while (0 < chunkSection.getPaletteSize()) {
                                    chunkSection.setPaletteEntry(0, this.this$0.val$protocol.getMappingData().getNewBlockStateId(chunkSection.getPaletteEntry(0)));
                                    int n = 0;
                                    ++n;
                                }
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                });
            }
        });
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketRemapper(protocol) {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(WorldPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol protocol, final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (intValue == 2000) {
                    switch ((int)packetWrapper.get(Type.INT, 1)) {
                        case 1: {
                            packetWrapper.set(Type.INT, 1, 2);
                            break;
                        }
                        case 0:
                        case 3:
                        case 6: {
                            packetWrapper.set(Type.INT, 1, 4);
                            break;
                        }
                        case 2:
                        case 5:
                        case 8: {
                            packetWrapper.set(Type.INT, 1, 5);
                            break;
                        }
                        case 7: {
                            packetWrapper.set(Type.INT, 1, 3);
                            break;
                        }
                        default: {
                            packetWrapper.set(Type.INT, 1, 0);
                            break;
                        }
                    }
                }
                else if (intValue == 1010) {
                    packetWrapper.set(Type.INT, 1, protocol.getMappingData().getNewItemId((int)packetWrapper.get(Type.INT, 1)));
                }
                else if (intValue == 2001) {
                    packetWrapper.set(Type.INT, 1, protocol.getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.INT, 1)));
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                    }
                });
            }
        });
    }
}
