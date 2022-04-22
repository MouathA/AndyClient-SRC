package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;

public class WorldPackets
{
    public static final int SERVERSIDE_VIEW_DISTANCE = 64;
    private static final byte[] FULL_LIGHT;
    public static int air;
    public static int voidAir;
    public static int caveAir;
    
    public static void register(final Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol1_14To1_13_2, null);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.BYTE);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$0.val$protocol.getMappingData().getNewBlockId((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$0.val$protocol.getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SERVER_DIFFICULTY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final WorldPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.EXPLOSION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler() {
                    final WorldPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (true) {
                            final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                            if (floatValue < 0.0f) {
                                packetWrapper.set(Type.FLOAT, 0, (int)floatValue);
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_13Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)));
                        packetWrapper.write(new Chunk1_14Type(), chunk);
                        final int[] array = new int[256];
                        final int[] array2 = new int[256];
                        int paletteEntry = 0;
                        while (0 < chunk.getSections().length) {
                            final ChunkSection chunkSection = chunk.getSections()[0];
                            if (chunkSection == null) {
                                int n = 0;
                                ++n;
                            }
                            else {
                                int n2 = 0;
                                while (0 < chunkSection.getPaletteSize()) {
                                    paletteEntry = chunkSection.getPaletteEntry(0);
                                    this.this$0.val$protocol.getMappingData().getNewBlockStateId(0);
                                    chunkSection.setPaletteEntry(0, 0);
                                    ++n2;
                                }
                                while (true) {
                                    final int flatBlock = chunkSection.getFlatBlock(0, 0, 0);
                                    if (flatBlock != WorldPackets.air && flatBlock != WorldPackets.voidAir && flatBlock != WorldPackets.caveAir) {
                                        ++n2;
                                        array2[0] = 1;
                                    }
                                    if (this.this$0.val$protocol.getMappingData().getMotionBlocking().contains(flatBlock)) {
                                        array[0] = 1;
                                    }
                                    if (Via.getConfig().isNonFullBlockLightFix() && this.this$0.val$protocol.getMappingData().getNonFullBlocks().contains(flatBlock)) {
                                        WorldPackets.access$000(chunk, chunkSection, 0, 0, 0, 0);
                                    }
                                    int abs = 0;
                                    ++abs;
                                }
                            }
                        }
                        final CompoundTag heightMap = new CompoundTag();
                        heightMap.put("MOTION_BLOCKING", new LongArrayTag(WorldPackets.access$100(array)));
                        heightMap.put("WORLD_SURFACE", new LongArrayTag(WorldPackets.access$100(array2)));
                        chunk.setHeightMap(heightMap);
                        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_14.UPDATE_LIGHT);
                        create.write(Type.VAR_INT, chunk.getX());
                        create.write(Type.VAR_INT, chunk.getZ());
                        final int n3 = chunk.isFullChunk() ? 262143 : 0;
                        while (0 < chunk.getSections().length) {
                            final ChunkSection chunkSection2 = chunk.getSections()[0];
                            if (chunkSection2 != null) {
                                if (chunk.isFullChunk() || chunkSection2.getLight().hasSkyLight()) {}
                            }
                            ++paletteEntry;
                        }
                        create.write(Type.VAR_INT, 1);
                        create.write(Type.VAR_INT, 0);
                        create.write(Type.VAR_INT, 0);
                        create.write(Type.VAR_INT, 0);
                        if (chunk.isFullChunk()) {
                            create.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.access$200());
                        }
                        final int length = chunk.getSections().length;
                        if (chunk.isFullChunk()) {
                            create.write(Type.BYTE_ARRAY_PRIMITIVE, WorldPackets.access$200());
                        }
                        final int length2 = chunk.getSections().length;
                        final EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        Math.abs(entityTracker1_14.getChunkCenterX() - chunk.getX());
                        int abs = Math.abs(entityTracker1_14.getChunkCenterZ() - chunk.getZ());
                        if (entityTracker1_14.isForceSendCenterChunk()) {
                            final PacketWrapper create2 = packetWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
                            create2.write(Type.VAR_INT, chunk.getX());
                            create2.write(Type.VAR_INT, chunk.getZ());
                            create2.send(Protocol1_14To1_13_2.class);
                            entityTracker1_14.setChunkCenterX(chunk.getX());
                            entityTracker1_14.setChunkCenterZ(chunk.getZ());
                        }
                        create.send(Protocol1_14To1_13_2.class);
                        final ChunkSection[] sections = chunk.getSections();
                        while (0 < sections.length) {
                            final ChunkSection chunkSection3 = sections[0];
                            if (chunkSection3 != null) {
                                chunkSection3.setLight(null);
                            }
                            int n4 = 0;
                            ++n4;
                        }
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                        if (intValue == 1010) {
                            packetWrapper.set(Type.INT, 1, this.this$0.val$protocol.getMappingData().getNewItemId(intValue2));
                        }
                        else if (intValue == 2001) {
                            packetWrapper.set(Type.INT, 1, this.this$0.val$protocol.getMappingData().getNewBlockStateId(intValue2));
                        }
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final Entity1_14Types player = Entity1_14Types.PLAYER;
                        final EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        entityTracker1_14.addEntity(intValue, player);
                        entityTracker1_14.setClientEntityId(intValue);
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                        create.write(Type.UNSIGNED_BYTE, shortValue);
                        create.write(Type.BOOLEAN, false);
                        create.scheduleSend(this.this$0.val$protocol.getClass());
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.write(Type.VAR_INT, 64);
                    }
                });
                this.handler(WorldPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.send(Protocol1_14To1_13_2.class);
                packetWrapper.cancel();
                WorldPackets.access$300(packetWrapper.user());
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketRemapper(protocol1_14To1_13_2) {
            final Protocol1_14To1_13_2 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$11 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                        ((EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class)).setForceSendCenterChunk(true);
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$11 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                        create.write(Type.UNSIGNED_BYTE, shortValue);
                        create.write(Type.BOOLEAN, false);
                        create.scheduleSend(this.this$0.val$protocol.getClass());
                    }
                });
                this.handler(WorldPackets$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.send(Protocol1_14To1_13_2.class);
                packetWrapper.cancel();
                WorldPackets.access$300(packetWrapper.user());
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
    }
    
    private static void sendViewDistancePacket(final UserConnection userConnection) throws Exception {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE, null, userConnection);
        create.write(Type.VAR_INT, 64);
        create.send(Protocol1_14To1_13_2.class);
    }
    
    private static long[] encodeHeightMap(final int[] array) {
        return CompactArrayUtil.createCompactArray(9, array.length, WorldPackets::lambda$encodeHeightMap$0);
    }
    
    private static void setNonFullLight(final Chunk chunk, final ChunkSection chunkSection, final int n, final int n2, final int n3, final int n4) {
        final BlockFace[] values = BlockFace.values();
        while (0 < values.length) {
            final BlockFace blockFace = values[0];
            final NibbleArray skyLightNibbleArray = chunkSection.getLight().getSkyLightNibbleArray();
            final NibbleArray blockLightNibbleArray = chunkSection.getLight().getBlockLightNibbleArray();
            final int n5 = n2 + blockFace.modX();
            final int n6 = n3 + blockFace.modY();
            final int n7 = n4 + blockFace.modZ();
            Label_0305: {
                if (blockFace.modX() != 0) {
                    if (n5 == 16) {
                        break Label_0305;
                    }
                    if (n5 == -1) {
                        break Label_0305;
                    }
                }
                else if (blockFace.modY() == 0) {
                    if (blockFace.modZ() != 0) {
                        if (n7 == 16) {
                            break Label_0305;
                        }
                        if (n7 == -1) {
                            break Label_0305;
                        }
                    }
                }
                if (blockLightNibbleArray != null) {
                    final byte value = blockLightNibbleArray.get(n5, 15, n7);
                    if (value != 15) {
                        if (value > 14) {}
                    }
                }
                if (skyLightNibbleArray != null) {
                    final byte value2 = skyLightNibbleArray.get(n5, 15, n7);
                    if (value2 == 15) {
                        if (blockFace.modY() == 1) {}
                    }
                    else if (value2 > 14) {}
                }
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    private static long getChunkIndex(final int n, final int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | ((long)n2 & 0x3FFFFFFL);
    }
    
    private static long lambda$encodeHeightMap$0(final int[] array, final int n) {
        return array[n];
    }
    
    static void access$000(final Chunk chunk, final ChunkSection chunkSection, final int n, final int n2, final int n3, final int n4) {
        setNonFullLight(chunk, chunkSection, n, n2, n3, n4);
    }
    
    static long[] access$100(final int[] array) {
        return encodeHeightMap(array);
    }
    
    static byte[] access$200() {
        return WorldPackets.FULL_LIGHT;
    }
    
    static void access$300(final UserConnection userConnection) throws Exception {
        sendViewDistancePacket(userConnection);
    }
    
    static {
        Arrays.fill(FULL_LIGHT = new byte[2048], (byte)(-1));
    }
}
