package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.*;

public class PlayerPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.QUERY_BLOCK_NBT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper(protocol) {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
                        this.this$0.val$protocol.getItemRewriter().handleItemToServer(item);
                        if (item == null) {
                            return;
                        }
                        final CompoundTag tag = item.tag();
                        if (tag == null) {
                            return;
                        }
                        final Tag value = tag.get("pages");
                        if (value == null) {
                            tag.put("pages", new ListTag(Collections.singletonList(new StringTag())));
                        }
                        if (Via.getConfig().isTruncate1_14Books() && value instanceof ListTag) {
                            final ListTag listTag = (ListTag)value;
                            if (listTag.size() > 50) {
                                listTag.setValue(listTag.getValue().subList(0, 50));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.PLAYER_DIGGING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.RECIPE_BOOK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final PlayerPackets$5 this$0;
                    
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
                            packetWrapper.read(Type.BOOLEAN);
                            packetWrapper.read(Type.BOOLEAN);
                            packetWrapper.read(Type.BOOLEAN);
                            packetWrapper.read(Type.BOOLEAN);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final PlayerPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final Position position = (Position)packetWrapper.read(Type.POSITION1_14);
                        final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                        final float floatValue = (float)packetWrapper.read(Type.FLOAT);
                        final float floatValue2 = (float)packetWrapper.read(Type.FLOAT);
                        final float floatValue3 = (float)packetWrapper.read(Type.FLOAT);
                        packetWrapper.read(Type.BOOLEAN);
                        packetWrapper.write(Type.POSITION, position);
                        packetWrapper.write(Type.VAR_INT, intValue2);
                        packetWrapper.write(Type.VAR_INT, intValue);
                        packetWrapper.write(Type.FLOAT, floatValue);
                        packetWrapper.write(Type.FLOAT, floatValue2);
                        packetWrapper.write(Type.FLOAT, floatValue3);
                    }
                });
            }
        });
    }
}
