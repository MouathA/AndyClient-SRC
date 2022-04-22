package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.type.types.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets extends ItemRewriter
{
    public InventoryPackets(final Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        super(protocol1_16To1_15_2);
    }
    
    public void registerPackets() {
        final PacketHandler packetHandler = InventoryPackets::lambda$registerPackets$0;
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.OPEN_WINDOW, new PacketRemapper(packetHandler) {
            final PacketHandler val$cursorRemapper;
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.COMPONENT);
                this.handler(this.val$cursorRemapper);
                this.handler(InventoryPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final InventoryTracker1_16 inventoryTracker1_16 = (InventoryTracker1_16)packetWrapper.user().get(InventoryTracker1_16.class);
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                int intValue2 = (int)packetWrapper.get(Type.VAR_INT, 1);
                if (intValue2 >= 20) {
                    packetWrapper.set(Type.VAR_INT, 1, ++intValue2);
                }
                inventoryTracker1_16.setInventory((short)intValue);
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.CLOSE_WINDOW, new PacketRemapper(packetHandler) {
            final PacketHandler val$cursorRemapper;
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(this.val$cursorRemapper);
                this.handler(InventoryPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryTracker1_16)packetWrapper.user().get(InventoryTracker1_16.class)).setInventory((short)(-1));
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.WINDOW_PROPERTY, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(InventoryPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                if (shortValue >= 4 && shortValue <= 6) {
                    final short shortValue2 = (short)packetWrapper.get(Type.SHORT, 1);
                    if (shortValue2 >= 11) {
                        packetWrapper.set(Type.SHORT, 1, (short)(shortValue2 + 1));
                    }
                }
            }
        });
        this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, (byte)(int)packetWrapper.read(Type.VAR_INT));
                this.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2)this.protocol).registerServerbound(ServerboundPackets1_16.CLOSE_WINDOW, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryTracker1_16)packetWrapper.user().get(InventoryTracker1_16.class)).setInventory((short)(-1));
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        this.registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 771 && item.tag() != null) {
            final Tag value = item.tag().get("SkullOwner");
            if (value instanceof CompoundTag) {
                final CompoundTag compoundTag = (CompoundTag)value;
                final Tag value2 = compoundTag.get("Id");
                if (value2 instanceof StringTag) {
                    compoundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(UUID.fromString((String)value2.getValue()))));
                }
            }
        }
        oldToNewAttributes(item);
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.identifier() == 771 && item.tag() != null) {
            final Tag value = item.tag().get("SkullOwner");
            if (value instanceof CompoundTag) {
                final CompoundTag compoundTag = (CompoundTag)value;
                final Tag value2 = compoundTag.get("Id");
                if (value2 instanceof IntArrayTag) {
                    compoundTag.put("Id", new StringTag(UUIDIntArrayType.uuidFromIntArray((int[])value2.getValue()).toString()));
                }
            }
        }
        newToOldAttributes(item);
        return item;
    }
    
    public static void oldToNewAttributes(final Item item) {
        if (item.tag() == null) {
            return;
        }
        final ListTag listTag = (ListTag)item.tag().get("AttributeModifiers");
        if (listTag == null) {
            return;
        }
        for (final CompoundTag compoundTag : listTag) {
            rewriteAttributeName(compoundTag, "AttributeName", false);
            rewriteAttributeName(compoundTag, "Name", false);
            final Tag value = compoundTag.get("UUIDLeast");
            if (value != null) {
                compoundTag.put("UUID", new IntArrayTag(UUIDIntArrayType.bitsToIntArray(((NumberTag)value).asLong(), ((NumberTag)compoundTag.get("UUIDMost")).asLong())));
            }
        }
    }
    
    public static void newToOldAttributes(final Item item) {
        if (item.tag() == null) {
            return;
        }
        final ListTag listTag = (ListTag)item.tag().get("AttributeModifiers");
        if (listTag == null) {
            return;
        }
        for (final CompoundTag compoundTag : listTag) {
            rewriteAttributeName(compoundTag, "AttributeName", true);
            rewriteAttributeName(compoundTag, "Name", true);
            final IntArrayTag intArrayTag = (IntArrayTag)compoundTag.get("UUID");
            if (intArrayTag != null && intArrayTag.getValue().length == 4) {
                final UUID uuidFromIntArray = UUIDIntArrayType.uuidFromIntArray(intArrayTag.getValue());
                compoundTag.put("UUIDLeast", new LongTag(uuidFromIntArray.getLeastSignificantBits()));
                compoundTag.put("UUIDMost", new LongTag(uuidFromIntArray.getMostSignificantBits()));
            }
        }
    }
    
    public static void rewriteAttributeName(final CompoundTag compoundTag, final String s, final boolean b) {
        final StringTag stringTag = (StringTag)compoundTag.get(s);
        if (stringTag == null) {
            return;
        }
        String s2 = stringTag.getValue();
        if (b && !s2.startsWith("minecraft:")) {
            s2 = "minecraft:" + s2;
        }
        final String value = (b ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(s2);
        if (value == null) {
            return;
        }
        stringTag.setValue(value);
    }
    
    private static void lambda$registerPackets$0(final PacketWrapper packetWrapper) throws Exception {
        final PacketWrapper create = packetWrapper.create(ClientboundPackets1_16.SET_SLOT);
        create.write(Type.UNSIGNED_BYTE, -1);
        create.write(Type.SHORT, -1);
        create.write(Type.FLAT_VAR_INT_ITEM, null);
        create.send(Protocol1_16To1_15_2.class);
    }
}
