package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.*;

public class PlayerPackets1_14 extends RewriterBase
{
    public PlayerPackets1_14(final Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SERVER_DIFFICULTY, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(PlayerPackets1_14$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((DifficultyStorage)packetWrapper.user().get(DifficultyStorage.class)).setDifficulty(((Short)packetWrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue());
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_SIGN_EDITOR, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.PLAYER_DIGGING, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final PlayerPackets1_14$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (intValue == 0) {
                            packetWrapper.passthrough(Type.STRING);
                        }
                        else if (intValue == 1) {
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.passthrough(Type.BOOLEAN);
                            packetWrapper.write(Type.BOOLEAN, false);
                            packetWrapper.write(Type.BOOLEAN, false);
                            packetWrapper.write(Type.BOOLEAN, false);
                            packetWrapper.write(Type.BOOLEAN, false);
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.UPDATE_SIGN, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            final PlayerPackets1_14 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPackets1_14$9 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Position position = (Position)packetWrapper.read(Type.POSITION);
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        final float floatValue = (float)packetWrapper.read(Type.FLOAT);
                        final float floatValue2 = (float)packetWrapper.read(Type.FLOAT);
                        final float floatValue3 = (float)packetWrapper.read(Type.FLOAT);
                        packetWrapper.write(Type.VAR_INT, intValue2);
                        packetWrapper.write(Type.POSITION1_14, position);
                        packetWrapper.write(Type.VAR_INT, intValue);
                        packetWrapper.write(Type.FLOAT, floatValue);
                        packetWrapper.write(Type.FLOAT, floatValue2);
                        packetWrapper.write(Type.FLOAT, floatValue3);
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
    }
}
