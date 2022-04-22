package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_16_2 extends ItemRewriter
{
    public BlockItemPackets1_16_2(final Protocol1_16_1To1_16_2 protocol1_16_1To1_16_2) {
        super(protocol1_16_1To1_16_2);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.UNLOCK_RECIPES, new PacketRemapper() {
            final BlockItemPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(BlockItemPackets1_16_2$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
                packetWrapper.read(Type.BOOLEAN);
            }
        });
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_16_2Type());
                packetWrapper.write(new Chunk1_16Type(), chunk);
                chunk.setIgnoreOldLightData(true);
                while (0 < chunk.getSections().length) {
                    final ChunkSection chunkSection = chunk.getSections()[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.access$000(this.this$0)).getMappingData().getNewBlockStateId(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
                for (final CompoundTag compoundTag : chunk.getBlockEntities()) {
                    if (compoundTag != null) {
                        BlockItemPackets1_16_2.access$100(this.this$0, compoundTag);
                    }
                }
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                BlockItemPackets1_16_2.access$100(this.this$0, (CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final long longValue = (long)packetWrapper.read(Type.LONG);
                packetWrapper.read(Type.BOOLEAN);
                final int n = (int)(longValue >> 42);
                final int n2 = (int)(longValue << 44 >> 44);
                final int n3 = (int)(longValue << 22 >> 42);
                packetWrapper.write(Type.INT, n);
                packetWrapper.write(Type.INT, n3);
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                packetWrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, array);
                while (0 < array.length) {
                    final BlockChangeRecord blockChangeRecord = array[0];
                    array[0] = new BlockChangeRecord1_8(blockChangeRecord.getSectionX(), blockChangeRecord.getY(n2), blockChangeRecord.getSectionZ(), ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.access$200(this.this$0)).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                    int n4 = 0;
                    ++n4;
                }
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() {
            final BlockItemPackets1_16_2 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
    }
    
    private void handleBlockEntity(final CompoundTag compoundTag) {
        final StringTag stringTag = (StringTag)compoundTag.get("id");
        if (stringTag == null) {
            return;
        }
        if (stringTag.getValue().equals("minecraft:skull")) {
            final Tag value = compoundTag.get("SkullOwner");
            if (!(value instanceof CompoundTag)) {
                return;
            }
            final CompoundTag compoundTag2 = (CompoundTag)value;
            if (!compoundTag2.contains("Id")) {
                return;
            }
            final CompoundTag compoundTag3 = (CompoundTag)compoundTag2.get("Properties");
            if (compoundTag3 == null) {
                return;
            }
            final ListTag listTag = (ListTag)compoundTag3.get("textures");
            if (listTag == null) {
                return;
            }
            final CompoundTag compoundTag4 = (listTag.size() > 0) ? ((CompoundTag)listTag.get(0)) : null;
            if (compoundTag4 == null) {
                return;
            }
            compoundTag2.put("Id", new IntArrayTag(new int[] { compoundTag4.get("Value").getValue().hashCode(), 0, 0, 0 }));
        }
    }
    
    static Protocol access$000(final BlockItemPackets1_16_2 blockItemPackets1_16_2) {
        return blockItemPackets1_16_2.protocol;
    }
    
    static void access$100(final BlockItemPackets1_16_2 blockItemPackets1_16_2, final CompoundTag compoundTag) {
        blockItemPackets1_16_2.handleBlockEntity(compoundTag);
    }
    
    static Protocol access$200(final BlockItemPackets1_16_2 blockItemPackets1_16_2) {
        return blockItemPackets1_16_2.protocol;
    }
}
