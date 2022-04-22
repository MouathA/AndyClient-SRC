package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public final class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        super(protocol1_18To1_17_1);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_17_1.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_17_1.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_17_1.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot1_17_1(ClientboundPackets1_17_1.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_17_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_17_1.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.EFFECT, new PacketRemapper() {
            final InventoryPackets this$0;
            
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
                    packetWrapper.set(Type.INT, 1, ((Protocol1_18To1_17_1)InventoryPackets.access$000(this.this$0)).getMappingData().getNewItemId(intValue2));
                }
            }
        });
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.SPAWN_PARTICLE, new PacketRemapper() {
            final InventoryPackets this$0;
            
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
                if (intValue == 2) {
                    packetWrapper.set(Type.INT, 0, 3);
                    packetWrapper.write(Type.VAR_INT, 7754);
                    return;
                }
                if (intValue == 3) {
                    packetWrapper.write(Type.VAR_INT, 7786);
                    return;
                }
                final ParticleMappings particleMappings = ((Protocol1_18To1_17_1)InventoryPackets.access$100(this.this$0)).getMappingData().getParticleMappings();
                if (particleMappings.isBlockParticle(intValue)) {
                    packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_18To1_17_1)InventoryPackets.access$200(this.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.passthrough(Type.VAR_INT)));
                }
                else if (particleMappings.isItemParticle(intValue)) {
                    this.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
                final int newParticleId = ((Protocol1_18To1_17_1)InventoryPackets.access$300(this.this$0)).getMappingData().getNewParticleId(intValue);
                if (newParticleId != intValue) {
                    packetWrapper.set(Type.INT, 0, newParticleId);
                }
            }
        });
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17_1.DECLARE_RECIPES);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }
    
    static Protocol access$000(final InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
    
    static Protocol access$100(final InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
    
    static Protocol access$200(final InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
    
    static Protocol access$300(final InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
}
