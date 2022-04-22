package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;

public final class BlockItemPackets1_18 extends ItemRewriter
{
    public BlockItemPackets1_18(final Protocol1_17_1To1_18 protocol1_17_1To1_18) {
        super(protocol1_17_1To1_18);
    }
    
    @Override
    protected void registerPackets() {
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_18.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_18.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.EFFECT, new PacketRemapper() {
            final BlockItemPackets1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                if (intValue == 1010) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$000(this.this$0)).getMappingData().getNewItemId(intValue2));
                }
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PARTICLE, new PacketRemapper() {
            final BlockItemPackets1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (intValue == 3) {
                    if ((int)packetWrapper.read(Type.VAR_INT) == 7786) {
                        packetWrapper.set(Type.INT, 0, 3);
                    }
                    else {
                        packetWrapper.set(Type.INT, 0, 2);
                    }
                    return;
                }
                final ParticleMappings particleMappings = ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$100(this.this$0)).getMappingData().getParticleMappings();
                if (particleMappings.isBlockParticle(intValue)) {
                    packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$200(this.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.passthrough(Type.VAR_INT)));
                }
                else if (particleMappings.isItemParticle(intValue)) {
                    this.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
                final int newParticleId = ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$300(this.this$0)).getMappingData().getNewParticleId(intValue);
                if (newParticleId != intValue) {
                    packetWrapper.set(Type.INT, 0, newParticleId);
                }
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_18 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final CompoundTag compoundTag = (CompoundTag)packetWrapper.read(Type.NBT);
                final int mappedId = BlockEntityIds.mappedId(intValue);
                if (mappedId == -1) {
                    packetWrapper.cancel();
                    return;
                }
                final String s = (String)((Protocol1_17_1To1_18)BlockItemPackets1_18.access$400(this.this$0)).getMappingData().blockEntities().get(intValue);
                if (s == null) {
                    packetWrapper.cancel();
                    return;
                }
                final CompoundTag compoundTag2 = (compoundTag == null) ? new CompoundTag() : compoundTag;
                final Position position = (Position)packetWrapper.get(Type.POSITION1_14, 0);
                compoundTag2.put("id", new StringTag("minecraft:" + s));
                compoundTag2.put("x", new IntTag(position.x()));
                compoundTag2.put("y", new IntTag(position.y()));
                compoundTag2.put("z", new IntTag(position.z()));
                BlockItemPackets1_18.access$500(this.this$0, intValue, compoundTag2);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)mappedId);
                packetWrapper.write(Type.NBT, compoundTag2);
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_18 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker tracker = ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$600(this.this$0)).getEntityRewriter().tracker(packetWrapper.user());
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_17_1To1_18)BlockItemPackets1_18.access$700(this.this$0)).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent())));
                final ChunkSection[] sections = chunk.getSections();
                final BitSet set = new BitSet(chunk.getSections().length);
                final int[] array = new int[sections.length * 64];
                while (0 < sections.length) {
                    final ChunkSection chunkSection = sections[0];
                    final DataPalette palette = chunkSection.palette(PaletteType.BIOMES);
                    while (0 < 64) {
                        final int[] array2 = array;
                        final int n = 0;
                        int n2 = 0;
                        ++n2;
                        array2[n] = palette.idAt(0);
                        int intValue = 0;
                        ++intValue;
                    }
                    if (chunkSection.getNonAirBlocksCount() == 0) {
                        sections[0] = null;
                    }
                    else {
                        set.set(0);
                    }
                    int n3 = 0;
                    ++n3;
                }
                final ArrayList list = new ArrayList<CompoundTag>(chunk.blockEntities().size());
                for (final BlockEntity blockEntity : chunk.blockEntities()) {
                    final String s = (String)((Protocol1_17_1To1_18)BlockItemPackets1_18.access$800(this.this$0)).getMappingData().blockEntities().get(blockEntity.typeId());
                    if (s == null) {
                        continue;
                    }
                    CompoundTag tag;
                    if (blockEntity.tag() != null) {
                        tag = blockEntity.tag();
                        BlockItemPackets1_18.access$500(this.this$0, blockEntity.typeId(), tag);
                    }
                    else {
                        tag = new CompoundTag();
                    }
                    list.add(tag);
                    tag.put("x", new IntTag((chunk.getX() << 4) + blockEntity.sectionX()));
                    tag.put("y", new IntTag(blockEntity.y()));
                    tag.put("z", new IntTag((chunk.getZ() << 4) + blockEntity.sectionZ()));
                    tag.put("id", new StringTag("minecraft:" + s));
                }
                final BaseChunk baseChunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, set, chunk.getSections(), array, chunk.getHeightMap(), list);
                packetWrapper.write(new Chunk1_17Type(tracker.currentWorldSectionHeight()), baseChunk);
                final PacketWrapper create = packetWrapper.create(ClientboundPackets1_17_1.UPDATE_LIGHT);
                create.write(Type.VAR_INT, baseChunk.getX());
                create.write(Type.VAR_INT, baseChunk.getZ());
                create.write(Type.BOOLEAN, packetWrapper.read(Type.BOOLEAN));
                create.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                create.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                create.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                create.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                int intValue = (int)packetWrapper.read(Type.VAR_INT);
                create.write(Type.VAR_INT, 0);
                while (0 < 0) {
                    create.write(Type.BYTE_ARRAY_PRIMITIVE, packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    int intValue2 = 0;
                    ++intValue2;
                }
                int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                create.write(Type.VAR_INT, 0);
                while (0 < 0) {
                    create.write(Type.BYTE_ARRAY_PRIMITIVE, packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    int n4 = 0;
                    ++n4;
                }
                create.send(Protocol1_17_1To1_18.class);
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).cancelClientbound(ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
    }
    
    private void handleSpawner(final int n, final CompoundTag compoundTag) {
        if (n == 8) {
            final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("SpawnData");
            final CompoundTag compoundTag3;
            if (compoundTag2 != null && (compoundTag3 = (CompoundTag)compoundTag2.get("entity")) != null) {
                compoundTag.put("SpawnData", compoundTag3);
            }
        }
    }
    
    static Protocol access$000(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$100(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$200(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$300(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$400(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static void access$500(final BlockItemPackets1_18 blockItemPackets1_18, final int n, final CompoundTag compoundTag) {
        blockItemPackets1_18.handleSpawner(n, compoundTag);
    }
    
    static Protocol access$600(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$700(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
    
    static Protocol access$800(final BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }
}
