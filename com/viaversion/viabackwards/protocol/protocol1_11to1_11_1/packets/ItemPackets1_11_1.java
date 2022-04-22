package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public class ItemPackets1_11_1 extends LegacyBlockItemRewriter
{
    private LegacyEnchantmentRewriter enchantmentRewriter;
    
    public ItemPackets1_11_1(final Protocol1_11To1_11_1 protocol1_11To1_11_1) {
        super(protocol1_11To1_11_1);
    }
    
    @Override
    protected void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
            final ItemPackets1_11_1 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final ItemPackets1_11_1$1 this$1;
                    
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
        ((Protocol1_11To1_11_1)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$0);
    }
    
    @Override
    protected void registerRewrites() {
        (this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName)).registerEnchantment(22, "§7Sweeping Edge");
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        if (tag.get("ench") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.get("StoredEnchantments") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
        }
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        if (tag.contains(this.nbtTagName + "|ench")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
        }
        return item;
    }
    
    private void lambda$registerPackets$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }
}
