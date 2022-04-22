package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class WorldPackets1_13_1
{
    public static void register(final Protocol protocol) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION);
        protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets1_13_1$1 this$0;
                    
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
        blockRewriter.registerEffect(ClientboundPackets1_13.EFFECT, 1010, 2001);
    }
}
