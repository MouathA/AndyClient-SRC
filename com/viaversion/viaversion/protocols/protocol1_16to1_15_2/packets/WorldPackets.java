package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class WorldPackets
{
    public static void register(final Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol1_16To1_15_2, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.UPDATE_LIGHT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper(protocol1_16To1_15_2) {
            final Protocol1_16To1_15_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(WorldPackets$2::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final Protocol1_16To1_15_2 protocol1_16To1_15_2, final PacketWrapper packetWrapper) throws Exception {
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_15Type());
                packetWrapper.write(new Chunk1_16Type(), chunk);
                chunk.setIgnoreOldLightData(chunk.isFullChunk());
                while (0 < chunk.getSections().length) {
                    final ChunkSection chunkSection = chunk.getSections()[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, protocol1_16To1_15_2.getMappingData().getNewBlockStateId(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
                for (final LongArrayTag longArrayTag : chunk.getHeightMap().values()) {
                    final int[] array = new int[256];
                    CompactArrayUtil.iterateCompactArray(9, array.length, longArrayTag.getValue(), WorldPackets$2::lambda$null$0);
                    longArrayTag.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, array.length, WorldPackets$2::lambda$null$1));
                }
                if (chunk.getBlockEntities() == null) {
                    return;
                }
                final Iterator iterator2 = chunk.getBlockEntities().iterator();
                while (iterator2.hasNext()) {
                    WorldPackets.access$000(iterator2.next());
                }
            }
            
            private static long lambda$null$1(final int[] array, final int n) {
                return array[n];
            }
            
            private static void lambda$null$0(final int[] array, final int n, final int n2) {
                array[n] = n2;
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.passthrough(Type.POSITION1_14);
                (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                WorldPackets.access$000((CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
    }
    
    private static void handleBlockEntity(final CompoundTag compoundTag) {
        final StringTag stringTag = (StringTag)compoundTag.get("id");
        if (stringTag == null) {
            return;
        }
        final String value = stringTag.getValue();
        if (value.equals("minecraft:conduit")) {
            final Tag remove = compoundTag.remove("target_uuid");
            if (!(remove instanceof StringTag)) {
                return;
            }
            compoundTag.put("Target", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(UUID.fromString((String)remove.getValue()))));
        }
        else if (value.equals("minecraft:skull") && compoundTag.get("Owner") instanceof CompoundTag) {
            final CompoundTag compoundTag2 = (CompoundTag)compoundTag.remove("Owner");
            final StringTag stringTag2 = (StringTag)compoundTag2.remove("Id");
            if (stringTag2 != null) {
                compoundTag2.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(UUID.fromString(stringTag2.getValue()))));
            }
            final CompoundTag compoundTag3 = new CompoundTag();
            for (final Map.Entry<String, V> entry : compoundTag2.entrySet()) {
                compoundTag3.put(entry.getKey(), (Tag)entry.getValue());
            }
            compoundTag.put("SkullOwner", compoundTag3);
        }
    }
    
    static void access$000(final CompoundTag compoundTag) {
        handleBlockEntity(compoundTag);
    }
}
