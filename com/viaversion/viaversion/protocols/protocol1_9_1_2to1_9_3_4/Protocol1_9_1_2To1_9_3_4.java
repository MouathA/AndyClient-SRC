package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_9_1_2To1_9_3_4 extends AbstractProtocol
{
    public Protocol1_9_1_2To1_9_3_4() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final Protocol1_9_1_2To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_1_2To1_9_3_4$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) != 9) {
                            return;
                        }
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        final CompoundTag compoundTag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                        packetWrapper.clearPacket();
                        packetWrapper.setId(ClientboundPackets1_9.UPDATE_SIGN.ordinal());
                        packetWrapper.write(Type.POSITION, position);
                        while (true) {
                            packetWrapper.write(Type.STRING, compoundTag.get("Text" + 1).getValue());
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final Protocol1_9_1_2To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_9_1_2To1_9_3_4$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                        final Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
                        final Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
                        final Chunk chunk = (Chunk)packetWrapper.read(chunk1_9_3_4Type);
                        packetWrapper.write(chunk1_9_1_2Type, chunk);
                        BlockEntity.handle(chunk.getBlockEntities(), packetWrapper.user());
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() {
            final Protocol1_9_1_2To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_1_2To1_9_3_4$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() {
            final Protocol1_9_1_2To1_9_3_4 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_1_2To1_9_3_4$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                    }
                });
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
}
