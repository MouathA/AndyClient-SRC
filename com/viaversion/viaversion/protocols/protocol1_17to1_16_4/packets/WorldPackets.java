package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public final class WorldPackets
{
    public static void register(final Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol1_17To1_16_4, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.WORLD_BORDER, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                ClientboundPackets1_17 clientboundPackets1_17 = null;
                switch (intValue) {
                    case 0: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_SIZE;
                        break;
                    }
                    case 1: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
                        break;
                    }
                    case 2: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_CENTER;
                        break;
                    }
                    case 3: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_INIT;
                        break;
                    }
                    case 4: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
                        break;
                    }
                    case 5: {
                        clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid world border type received: " + intValue);
                    }
                }
                packetWrapper.setId(clientboundPackets1_17.getId());
            }
        });
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.UPDATE_LIGHT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void writeLightArrays(final PacketWrapper packetWrapper, final int n) throws Exception {
                final ArrayList<byte[]> list = new ArrayList<byte[]>();
                while (0 < 18) {
                    if (this.isSet(n, 0)) {
                        list.add((byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                    int n2 = 0;
                    ++n2;
                }
                packetWrapper.write(Type.VAR_INT, list.size());
                final Iterator<Object> iterator = list.iterator();
                while (iterator.hasNext()) {
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, iterator.next());
                }
            }
            
            private long[] toBitSetLongArray(final int n) {
                return new long[] { n };
            }
            
            private boolean isSet(final int n, final int n2) {
                return (n & 1 << n2) != 0x0;
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(intValue));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(intValue2));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((int)packetWrapper.read(Type.VAR_INT)));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((int)packetWrapper.read(Type.VAR_INT)));
                this.writeLightArrays(packetWrapper, intValue);
                this.writeLightArrays(packetWrapper, intValue2);
            }
        });
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper(protocol1_17To1_16_4) {
            final Protocol1_17To1_16_4 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(WorldPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol1_17To1_16_4 protocol1_17To1_16_4, final PacketWrapper packetWrapper) throws Exception {
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_16_2Type());
                if (!chunk.isFullChunk()) {
                    WorldPackets.access$000(packetWrapper, chunk);
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(new Chunk1_17Type(chunk.getSections().length), chunk);
                chunk.setChunkMask(BitSet.valueOf(new long[] { chunk.getBitmask() }));
                while (0 < chunk.getSections().length) {
                    final ChunkSection chunkSection = chunk.getSections()[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, protocol1_17To1_16_4.getMappingData().getNewBlockStateId(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.STRING_ARRAY);
                this.map(Type.NBT);
                this.map(Type.NBT);
                this.handler(WorldPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Iterator iterator = ((ListTag)((CompoundTag)((CompoundTag)packetWrapper.get(Type.NBT, 0)).get("minecraft:dimension_type")).get("value")).iterator();
                while (iterator.hasNext()) {
                    WorldPackets.access$100((CompoundTag)((CompoundTag)iterator.next()).get("element"));
                }
                WorldPackets.access$100((CompoundTag)packetWrapper.get(Type.NBT, 1));
                packetWrapper.user().getEntityTracker(Protocol1_17To1_16_4.class).addEntity((int)packetWrapper.get(Type.INT, 0), Entity1_17Types.PLAYER);
            }
        });
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                WorldPackets.access$100((CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
    }
    
    private static void writeMultiBlockChangePacket(final PacketWrapper packetWrapper, final Chunk chunk) throws Exception {
        final long n = ((long)chunk.getX() & 0x3FFFFFL) << 42 | ((long)chunk.getZ() & 0x3FFFFFL) << 20;
        final ChunkSection[] sections = chunk.getSections();
        while (0 < sections.length) {
            final ChunkSection chunkSection = sections[0];
            if (chunkSection != null) {
                final PacketWrapper create = packetWrapper.create(ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
                create.write(Type.LONG, n | ((long)0 & 0xFFFFFL));
                create.write(Type.BOOLEAN, true);
                final BlockChangeRecord[] array = new BlockChangeRecord[4096];
                while (0 < 16) {
                    while (0 < 16) {
                        while (0 < 16) {
                            final int newBlockStateId = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(chunkSection.getFlatBlock(0, 0, 0));
                            final BlockChangeRecord[] array2 = array;
                            final int n2 = 0;
                            int n3 = 0;
                            ++n3;
                            array2[n2] = new BlockChangeRecord1_16_2(0, 0, 0, newBlockStateId);
                            int n4 = 0;
                            ++n4;
                        }
                        int n5 = 0;
                        ++n5;
                    }
                    int n6 = 0;
                    ++n6;
                }
                create.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, array);
                create.send(Protocol1_17To1_16_4.class);
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    private static void addNewDimensionData(final CompoundTag compoundTag) {
        compoundTag.put("min_y", new IntTag(0));
        compoundTag.put("height", new IntTag(256));
    }
    
    static void access$000(final PacketWrapper packetWrapper, final Chunk chunk) throws Exception {
        writeMultiBlockChangePacket(packetWrapper, chunk);
    }
    
    static void access$100(final CompoundTag compoundTag) {
        addNewDimensionData(compoundTag);
    }
}
