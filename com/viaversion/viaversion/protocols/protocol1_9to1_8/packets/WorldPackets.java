package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import io.netty.buffer.*;
import java.util.*;

public class WorldPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final WorldPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.INT, 0, Effect.getNewId((int)packetWrapper.get(Type.INT, 0)));
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.INT, 0) == 2002) {
                            packetWrapper.set(Type.INT, 1, ItemRewriter.getNewEffectID((int)packetWrapper.get(Type.INT, 1)));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final WorldPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        final SoundEffect byName = SoundEffect.getByName(s);
                        String newName = s;
                        if (byName != null) {
                            byName.getCategory().getId();
                            newName = byName.getNewName();
                        }
                        packetWrapper.set(Type.STRING, 0, newName);
                        packetWrapper.write(Type.VAR_INT, 0);
                        if (byName != null && byName.isBreaksound() && ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).interactedBlockRecently((int)Math.floor((int)packetWrapper.passthrough(Type.INT) / 8.0), (int)Math.floor((int)packetWrapper.passthrough(Type.INT) / 8.0), (int)Math.floor((int)packetWrapper.passthrough(Type.INT) / 8.0))) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                        final ClientChunks clientChunks = (ClientChunks)packetWrapper.user().get(ClientChunks.class);
                        final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_8Type(clientWorld));
                        final long long1 = ClientChunks.toLong(chunk.getX(), chunk.getZ());
                        if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                            packetWrapper.setPacketType(ClientboundPackets1_9.UNLOAD_CHUNK);
                            packetWrapper.write(Type.INT, chunk.getX());
                            packetWrapper.write(Type.INT, chunk.getZ());
                            ((CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class)).unloadChunk(packetWrapper.user(), chunk.getX(), chunk.getZ());
                            clientChunks.getLoadedChunks().remove(long1);
                            if (Via.getConfig().isChunkBorderFix()) {
                                final BlockFace[] horizontal = BlockFace.HORIZONTAL;
                                while (0 < horizontal.length) {
                                    final BlockFace blockFace = horizontal[0];
                                    final int n = chunk.getX() + blockFace.modX();
                                    final int n2 = chunk.getZ() + blockFace.modZ();
                                    if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n, n2))) {
                                        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_9.UNLOAD_CHUNK);
                                        create.write(Type.INT, n);
                                        create.write(Type.INT, n2);
                                        create.send(Protocol1_9To1_8.class);
                                    }
                                    int n3 = 0;
                                    ++n3;
                                }
                            }
                        }
                        else {
                            final Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
                            packetWrapper.write(chunk1_9_1_2Type, chunk);
                            clientChunks.getLoadedChunks().add(long1);
                            if (Via.getConfig().isChunkBorderFix()) {
                                final BlockFace[] horizontal2 = BlockFace.HORIZONTAL;
                                while (0 < horizontal2.length) {
                                    final BlockFace blockFace2 = horizontal2[0];
                                    final int n4 = chunk.getX() + blockFace2.modX();
                                    final int n5 = chunk.getZ() + blockFace2.modZ();
                                    if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n4, n5))) {
                                        final PacketWrapper create2 = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                                        create2.write(chunk1_9_1_2Type, new BaseChunk(n4, n5, true, false, 0, new ChunkSection[16], new int[256], new ArrayList()));
                                        create2.send(Protocol1_9To1_8.class);
                                    }
                                    int n3 = 0;
                                    ++n3;
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                final ClientChunks clientChunks = (ClientChunks)packetWrapper.user().get(ClientChunks.class);
                final Chunk[] array = (Chunk[])packetWrapper.read(new ChunkBulk1_8Type(clientWorld));
                final Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
                final Chunk[] array2 = array;
                while (0 < array2.length) {
                    final Chunk chunk = array2[0];
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                    create.write(chunk1_9_1_2Type, chunk);
                    create.send(Protocol1_9To1_8.class);
                    clientChunks.getLoadedChunks().add(ClientChunks.toLong(chunk.getX(), chunk.getZ()));
                    if (Via.getConfig().isChunkBorderFix()) {
                        final BlockFace[] horizontal = BlockFace.HORIZONTAL;
                        while (0 < horizontal.length) {
                            final BlockFace blockFace = horizontal[0];
                            final int n = chunk.getX() + blockFace.modX();
                            final int n2 = chunk.getZ() + blockFace.modZ();
                            if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n, n2))) {
                                final PacketWrapper create2 = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                                create2.write(chunk1_9_1_2Type, new BaseChunk(n, n2, true, false, 0, new ChunkSection[16], new int[256], new ArrayList()));
                                create2.send(Protocol1_9To1_8.class);
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
        protocol.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final WorldPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (shortValue == 1) {
                            final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                            if (compoundTag != null) {
                                if (compoundTag.contains("EntityId")) {
                                    final String s = (String)compoundTag.get("EntityId").getValue();
                                    final CompoundTag compoundTag2 = new CompoundTag();
                                    compoundTag2.put("id", new StringTag(s));
                                    compoundTag.put("SpawnData", compoundTag2);
                                }
                                else {
                                    final CompoundTag compoundTag3 = new CompoundTag();
                                    compoundTag3.put("id", new StringTag("AreaEffectCloud"));
                                    compoundTag.put("SpawnData", compoundTag3);
                                }
                            }
                        }
                        if (shortValue == 2) {
                            ((CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class)).addOrUpdateBlock(packetWrapper.user(), (Position)packetWrapper.get(Type.POSITION, 0), (CompoundTag)packetWrapper.get(Type.NBT, 0));
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.PLAYER_DIGGING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION);
                this.handler(new PacketHandler() {
                    final WorldPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 6) {
                            packetWrapper.cancel();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue == 5 || intValue == 4 || intValue == 3) {
                            final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            if (entityTracker1_9.isBlocking()) {
                                entityTracker1_9.setBlocking(false);
                                if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                                    entityTracker1_9.setSecondHand(null);
                                }
                            }
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        packetWrapper.clearInputBuffer();
                        packetWrapper.setId(8);
                        packetWrapper.write(Type.POSITION, new Position(-1, (short)(-1), -1));
                        packetWrapper.write(Type.UNSIGNED_BYTE, 255);
                        final Item handItem = Protocol1_9To1_8.getHandItem(packetWrapper.user());
                        if (Via.getConfig().isShieldBlocking()) {
                            final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            final boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
                            if (showShieldWhenSwordInHand ? entityTracker1_9.hasSwordInHand() : (handItem != null && Protocol1_9To1_8.isSword(handItem.identifier()))) {
                                if (intValue == 0 && !entityTracker1_9.isBlocking()) {
                                    entityTracker1_9.setBlocking(true);
                                    if (!showShieldWhenSwordInHand && entityTracker1_9.getItemInSecondHand() == null) {
                                        entityTracker1_9.setSecondHand(new DataItem(442, (byte)1, (short)0, null));
                                    }
                                }
                                final boolean b = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
                                if ((b && intValue == 1) || (!b && intValue == 0)) {
                                    packetWrapper.cancel();
                                }
                            }
                            else {
                                if (!showShieldWhenSwordInHand) {
                                    entityTracker1_9.setSecondHand(null);
                                }
                                entityTracker1_9.setBlocking(false);
                            }
                        }
                        packetWrapper.write(Type.ITEM, handItem);
                        packetWrapper.write(Type.UNSIGNED_BYTE, 0);
                        packetWrapper.write(Type.UNSIGNED_BYTE, 0);
                        packetWrapper.write(Type.UNSIGNED_BYTE, 0);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.read(Type.VAR_INT) != 0) {
                            packetWrapper.cancel();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.write(Type.ITEM, Protocol1_9To1_8.getHandItem(packetWrapper.user()));
                    }
                });
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (shortValue == 255) {
                            return;
                        }
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        int x = position.x();
                        int y = position.y();
                        int z = position.z();
                        switch (shortValue) {
                            case 0: {
                                --y;
                                break;
                            }
                            case 1: {
                                ++y;
                                break;
                            }
                            case 2: {
                                --z;
                                break;
                            }
                            case 3: {
                                ++z;
                                break;
                            }
                            case 4: {
                                --x;
                                break;
                            }
                            case 5: {
                                ++x;
                                break;
                            }
                        }
                        ((EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).addBlockInteraction(new Position(x, y, z));
                    }
                });
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final CommandBlockProvider commandBlockProvider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        final Optional value = commandBlockProvider.get(packetWrapper.user(), position);
                        if (value.isPresent()) {
                            final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, null, packetWrapper.user());
                            create.write(Type.POSITION, position);
                            create.write(Type.UNSIGNED_BYTE, 2);
                            create.write(Type.NBT, value.get());
                            create.scheduleSend(Protocol1_9To1_8.class);
                        }
                    }
                });
            }
        });
    }
}
