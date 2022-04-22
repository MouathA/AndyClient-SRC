package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.function.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.*;
import de.gerrygames.viarewind.*;

public class WorldPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                if (compoundTag != null && compoundTag.contains("SpawnData")) {
                    final String s = (String)((CompoundTag)compoundTag.get("SpawnData")).get("id").getValue();
                    compoundTag.remove("SpawnData");
                    compoundTag.put("entityId", new StringTag(s));
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(WorldPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                if (intValue >= 219 && intValue <= 234) {
                    packetWrapper.set(Type.VAR_INT, 0, 130);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(WorldPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.VAR_INT, 0, ReplacementRegistry1_8to1_9.replace((int)packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(WorldPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                while (0 < array.length) {
                    final BlockChangeRecord blockChangeRecord = array[0];
                    blockChangeRecord.setBlockId(ReplacementRegistry1_8to1_9.replace(blockChangeRecord.getBlockId()));
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.NAMED_SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(WorldPackets$5::lambda$registerMap$0);
                this.map(Type.VAR_INT, Type.NOTHING);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String oldName = SoundRemapper.getOldName((String)packetWrapper.get(Type.STRING, 0));
                if (oldName == null) {
                    packetWrapper.cancel();
                }
                else {
                    packetWrapper.set(Type.STRING, 0, oldName);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.EXPLOSION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(WorldPackets$6::lambda$registerMap$0);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.INT);
                packetWrapper.write(Type.INT, intValue);
                while (0 < intValue) {
                    packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.UNLOAD_CHUNK, ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(new Chunk1_8Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)), new BaseChunk((int)packetWrapper.read(Type.INT), (int)packetWrapper.read(Type.INT), true, false, 0, new ChunkSection[16], null, new ArrayList()));
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$8::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_9_1_2Type(clientWorld));
                final ChunkSection[] sections = chunk.getSections();
                while (0 < sections.length) {
                    final ChunkSection chunkSection = sections[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, ReplacementRegistry1_8to1_9.replace(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                    final boolean b = clientWorld.getEnvironment() == Environment.NORMAL;
                    final ChunkSection[] array = new ChunkSection[16];
                    final ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(true);
                    (array[0] = chunkSectionImpl).addPaletteEntry(0);
                    if (b) {
                        chunkSectionImpl.getLight().setSkyLight(new byte[2048]);
                    }
                    chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, array, chunk.getBiomeData(), chunk.getBlockEntities());
                }
                packetWrapper.write(new Chunk1_8Type(clientWorld), chunk);
                chunk.getBlockEntities().forEach(WorldPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final UserConnection userConnection, final CompoundTag compoundTag) {
                if (!compoundTag.contains("x") || !compoundTag.contains("y") || !compoundTag.contains("z") || !compoundTag.contains("id")) {
                    return;
                }
                final Position position = new Position((int)compoundTag.get("x").getValue(), (short)(int)compoundTag.get("y").getValue(), (int)compoundTag.get("z").getValue());
                final String s = (String)compoundTag.get("id").getValue();
                switch (s.hashCode()) {
                    case -199249700: {
                        if (s.equals("minecraft:mob_spawner")) {
                            break;
                        }
                        break;
                    }
                    case 339138444: {
                        if (s.equals("minecraft:command_block")) {
                            break;
                        }
                        break;
                    }
                    case -1293651279: {
                        if (s.equals("minecraft:beacon")) {
                            break;
                        }
                        break;
                    }
                    case -1134211248: {
                        if (s.equals("minecraft:skull")) {
                            break;
                        }
                        break;
                    }
                    case -1883218338: {
                        if (s.equals("minecraft:flower_pot")) {
                            break;
                        }
                        break;
                    }
                    case -1296947815: {
                        if (s.equals("minecraft:banner")) {}
                        break;
                    }
                }
                switch (5) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    case 5: {
                        break;
                    }
                    default: {
                        return;
                    }
                }
                final PacketWrapper create = PacketWrapper.create(9, null, userConnection);
                create.write(Type.POSITION, position);
                create.write(Type.UNSIGNED_BYTE, 6);
                create.write(Type.NBT, compoundTag);
                PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, false, false);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(WorldPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int oldId = Effect.getOldId((int)packetWrapper.get(Type.INT, 0));
                if (oldId == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.INT, 0, oldId);
                if (oldId == 2001) {
                    packetWrapper.set(Type.INT, 1, ReplacementRegistry1_8to1_9.replace((int)packetWrapper.get(Type.INT, 1)));
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PARTICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(WorldPackets$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (intValue > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                    packetWrapper.cancel();
                    return;
                }
                if (intValue == 42) {
                    packetWrapper.set(Type.INT, 0, 24);
                }
                else if (intValue == 43) {
                    packetWrapper.set(Type.INT, 0, 3);
                }
                else if (intValue == 44) {
                    packetWrapper.set(Type.INT, 0, 34);
                }
                else if (intValue == 45) {
                    packetWrapper.set(Type.INT, 0, 1);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.MAP_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SOUND, ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$12::lambda$registerMap$0);
                this.handler(WorldPackets$12::lambda$registerMap$1);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.VAR_INT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String oldNameFromId = SoundRemapper.oldNameFromId((int)packetWrapper.read(Type.VAR_INT));
                if (oldNameFromId == null) {
                    packetWrapper.cancel();
                }
                else {
                    packetWrapper.write(Type.STRING, oldNameFromId);
                }
            }
        });
    }
}
