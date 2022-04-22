package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.connection.*;

public class Protocol1_9_3To1_9_1_2 extends AbstractProtocol
{
    public static final ValueTransformer ADJUST_PITCH;
    
    public Protocol1_9_3To1_9_1_2() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, null, new PacketRemapper() {
            final Protocol1_9_3To1_9_1_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_9_3To1_9_1_2$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Position position = (Position)packetWrapper.read(Type.POSITION);
                        final JsonElement[] array = new JsonElement[4];
                        while (true) {
                            array[0] = (JsonElement)packetWrapper.read(Type.COMPONENT);
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper() {
            final Protocol1_9_3To1_9_1_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final Protocol1_9_3To1_9_1_2$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                        final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_9_1_2Type(clientWorld));
                        packetWrapper.write(new Chunk1_9_3_4Type(clientWorld), chunk);
                        final List blockEntities = chunk.getBlockEntities();
                        while (0 < chunk.getSections().length) {
                            final ChunkSection chunkSection = chunk.getSections()[0];
                            if (chunkSection == null) {
                                int n = 0;
                                ++n;
                            }
                            else {
                                while (true) {
                                    final int blockWithoutData = chunkSection.getBlockWithoutData(0, 0, 0);
                                    if (FakeTileEntity.isTileEntity(blockWithoutData)) {
                                        blockEntities.add(FakeTileEntity.createTileEntity(0 + (chunk.getX() << 4), 0, 0 + (chunk.getZ() << 4), blockWithoutData));
                                    }
                                    int n2 = 0;
                                    ++n2;
                                }
                            }
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() {
            final Protocol1_9_3To1_9_1_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_3To1_9_1_2$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 1));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketRemapper() {
            final Protocol1_9_3To1_9_1_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final Protocol1_9_3To1_9_1_2$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketRemapper() {
            final Protocol1_9_3To1_9_1_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Protocol1_9_3To1_9_1_2.ADJUST_PITCH);
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
    
    static {
        ADJUST_PITCH = new ValueTransformer(Type.UNSIGNED_BYTE, Type.UNSIGNED_BYTE) {
            public Short transform(final PacketWrapper packetWrapper, final Short n) throws Exception {
                return (short)Math.round(n / 63.5f * 63.0f);
            }
            
            @Override
            public Object transform(final PacketWrapper packetWrapper, final Object o) throws Exception {
                return this.transform(packetWrapper, (Short)o);
            }
        };
    }
}
