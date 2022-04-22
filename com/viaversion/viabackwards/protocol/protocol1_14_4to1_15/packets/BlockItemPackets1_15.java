package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_15 extends ItemRewriter
{
    public BlockItemPackets1_15(final Protocol1_14_4To1_15 protocol1_14_4To1_15) {
        super(protocol1_14_4To1_15);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
        ((Protocol1_14_4To1_15)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() {
            final BlockItemPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipment(ClientboundPackets1_15.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_15$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_15Type());
                        packetWrapper.write(new Chunk1_14Type(), chunk);
                        if (!chunk.isFullChunk()) {
                            while (0 < chunk.getSections().length) {
                                final ChunkSection chunkSection = chunk.getSections()[0];
                                if (chunkSection != null) {
                                    while (0 < chunkSection.getPaletteSize()) {
                                        chunkSection.getPaletteEntry(0);
                                        ((Protocol1_14_4To1_15)BlockItemPackets1_15.access$000(this.this$1.this$0)).getMappingData().getNewBlockStateId(0);
                                        chunkSection.setPaletteEntry(0, 0);
                                        int n = 0;
                                        ++n;
                                    }
                                }
                                int n2 = 0;
                                ++n2;
                            }
                            return;
                        }
                        final int[] biomeData = chunk.getBiomeData();
                        final int[] array = new int[256];
                        final int n3 = biomeData[0];
                        while (true) {
                            array[0] = n3;
                            int n4 = 0;
                            ++n4;
                        }
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_15.SPAWN_PARTICLE, new PacketRemapper() {
            final BlockItemPackets1_15 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE, Type.FLOAT);
                this.map(Type.DOUBLE, Type.FLOAT);
                this.map(Type.DOUBLE, Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_15$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        if (intValue == 3 || intValue == 23) {
                            packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_14_4To1_15)BlockItemPackets1_15.access$100(this.this$1.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.passthrough(Type.VAR_INT)));
                        }
                        else if (intValue == 32) {
                            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, this.this$1.this$0.handleItemToClient((Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM)));
                        }
                        final int newParticleId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.access$200(this.this$1.this$0)).getMappingData().getNewParticleId(intValue);
                        if (intValue != newParticleId) {
                            packetWrapper.set(Type.INT, 0, newParticleId);
                        }
                    }
                });
            }
        });
    }
    
    static Protocol access$000(final BlockItemPackets1_15 blockItemPackets1_15) {
        return blockItemPackets1_15.protocol;
    }
    
    static Protocol access$100(final BlockItemPackets1_15 blockItemPackets1_15) {
        return blockItemPackets1_15.protocol;
    }
    
    static Protocol access$200(final BlockItemPackets1_15 blockItemPackets1_15) {
        return blockItemPackets1_15.protocol;
    }
}
