package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public class BlockItemPackets1_10 extends LegacyBlockItemRewriter
{
    public BlockItemPackets1_10(final Protocol1_9_4To1_10 protocol1_9_4To1_10) {
        super(protocol1_9_4To1_10);
    }
    
    @Override
    protected void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
            final BlockItemPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_10$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((String)packetWrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                packetWrapper.write(Type.ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.ITEM)));
                                packetWrapper.write(Type.ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.ITEM)));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    packetWrapper.write(Type.ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.ITEM)));
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                                int n = 0;
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_10$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        BlockItemPackets1_10.access$000(this.this$1.this$0, (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class))));
                    }
                });
            }
        });
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_10$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$1.this$0.handleBlockID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_10$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        while (0 < array.length) {
                            final BlockChangeRecord blockChangeRecord = array[0];
                            blockChangeRecord.setBlockId(this.this$1.this$0.handleBlockID(blockChangeRecord.getBlockId()));
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        ((Protocol1_9_4To1_10)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$0);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PARTICLE, new PacketRemapper() {
            final BlockItemPackets1_10 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_10$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.INT, 0) == 46) {
                            packetWrapper.set(Type.INT, 0, 38);
                        }
                    }
                });
            }
        });
    }
    
    private void lambda$registerPackets$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }
    
    static void access$000(final BlockItemPackets1_10 blockItemPackets1_10, final Chunk chunk) {
        blockItemPackets1_10.handleChunk(chunk);
    }
}
