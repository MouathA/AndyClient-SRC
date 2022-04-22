package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        super(protocol1_13_1To1_13);
    }
    
    public void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
        this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_ITEM);
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.equals("minecraft:trader_list") || s.equals("trader_list")) {
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_ITEM));
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
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
        ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper((RecipeRewriter)new RecipeRewriter1_13_2(this.protocol)) {
            final RecipeRewriter val$recipeRewriter;
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final RecipeRewriter recipeRewriter, final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    final String s = (String)packetWrapper.passthrough(Type.STRING);
                    recipeRewriter.handle(packetWrapper, ((String)packetWrapper.passthrough(Type.STRING)).replace("minecraft:", ""));
                    int n = 0;
                    ++n;
                }
            }
        });
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
    }
}
