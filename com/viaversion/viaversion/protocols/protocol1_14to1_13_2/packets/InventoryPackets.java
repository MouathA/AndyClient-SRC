package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import java.util.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.libs.gson.*;

public class InventoryPackets extends ItemRewriter
{
    private static final String NBT_TAG_NAME;
    private static final Set REMOVED_RECIPE_TYPES;
    private static final ComponentRewriter COMPONENT_REWRITER;
    
    public InventoryPackets(final Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        super(protocol1_14To1_13_2);
    }
    
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, null, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final InventoryPackets$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Short n = (Short)packetWrapper.read(Type.UNSIGNED_BYTE);
                        final String s = (String)packetWrapper.read(Type.STRING);
                        final JsonElement jsonElement = (JsonElement)packetWrapper.read(Type.COMPONENT);
                        InventoryPackets.access$000().processText(jsonElement);
                        final Short n2 = (Short)packetWrapper.read(Type.UNSIGNED_BYTE);
                        if (s.equals("EntityHorse")) {
                            packetWrapper.setId(31);
                            (int)packetWrapper.read(Type.INT);
                            packetWrapper.write(Type.UNSIGNED_BYTE, n);
                            packetWrapper.write(Type.VAR_INT, (int)n2);
                            packetWrapper.write(Type.INT, 19);
                        }
                        else {
                            packetWrapper.setId(46);
                            packetWrapper.write(Type.VAR_INT, (int)n);
                            final String s2 = s;
                            switch (s2.hashCode()) {
                                case -1124126594: {
                                    if (s2.equals("minecraft:crafting_table")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1719356277: {
                                    if (s2.equals("minecraft:furnace")) {
                                        break;
                                    }
                                    break;
                                }
                                case 712019713: {
                                    if (s2.equals("minecraft:dropper")) {
                                        break;
                                    }
                                    break;
                                }
                                case 2090881320: {
                                    if (s2.equals("minecraft:dispenser")) {
                                        break;
                                    }
                                    break;
                                }
                                case 319164197: {
                                    if (s2.equals("minecraft:enchanting_table")) {
                                        break;
                                    }
                                    break;
                                }
                                case 1649065834: {
                                    if (s2.equals("minecraft:brewing_stand")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1879003021: {
                                    if (s2.equals("minecraft:villager")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1293651279: {
                                    if (s2.equals("minecraft:beacon")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1150744385: {
                                    if (s2.equals("minecraft:anvil")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1112182111: {
                                    if (s2.equals("minecraft:hopper")) {
                                        break;
                                    }
                                    break;
                                }
                                case 1374330859: {
                                    if (s2.equals("minecraft:shulker_box")) {
                                        break;
                                    }
                                    break;
                                }
                                case 1438413556: {
                                    if (s2.equals("minecraft:container")) {
                                        break;
                                    }
                                    break;
                                }
                                case -1149092108: {
                                    if (s2.equals("minecraft:chest")) {}
                                    break;
                                }
                            }
                            switch (12) {
                                case 0: {
                                    break;
                                }
                                case 1: {
                                    break;
                                }
                                case 2:
                                case 3: {
                                    break;
                                }
                                case 4: {
                                    break;
                                }
                                case 5: {
                                    break;
                                }
                                case 6: {
                                    break;
                                }
                                case 7: {
                                    break;
                                }
                                case 8: {
                                    break;
                                }
                                case 9: {
                                    break;
                                }
                                case 10: {
                                    break;
                                }
                                default: {
                                    if (n2 > 0 && n2 <= 54) {
                                        final int n3 = n2 / 9 - 1;
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (19 == -1) {
                                Via.getPlatform().getLogger().warning("Can't open inventory for 1.14 player! Type: " + s + " Size: " + n2);
                            }
                            packetWrapper.write(Type.VAR_INT, 19);
                            packetWrapper.write(Type.COMPONENT, jsonElement);
                        }
                    }
                });
            }
        });
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.equals("minecraft:trader_list") || s.equals("trader_list")) {
                            packetWrapper.setId(39);
                            packetWrapper.resetReader();
                            packetWrapper.read(Type.STRING);
                            final int intValue = (int)packetWrapper.read(Type.INT);
                            ((EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class)).setLatestTradeWindowId(intValue);
                            packetWrapper.write(Type.VAR_INT, intValue);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                                this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    this.this$1.this$0.handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.write(Type.INT, 0);
                                packetWrapper.write(Type.INT, 0);
                                packetWrapper.write(Type.FLOAT, 0.0f);
                                int n = 0;
                                ++n;
                            }
                            packetWrapper.write(Type.VAR_INT, 0);
                            packetWrapper.write(Type.VAR_INT, 0);
                            packetWrapper.write(Type.BOOLEAN, false);
                        }
                        else if (s.equals("minecraft:book_open") || s.equals("book_open")) {
                            final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                            packetWrapper.clearPacket();
                            packetWrapper.setId(45);
                            packetWrapper.write(Type.VAR_INT, intValue2);
                        }
                    }
                });
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper((RecipeRewriter)new RecipeRewriter1_13_2(this.protocol)) {
            final RecipeRewriter val$recipeRewriter;
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final RecipeRewriter recipeRewriter, final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                while (0 < intValue) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    final String s2 = (String)packetWrapper.read(Type.STRING);
                    if (InventoryPackets.access$100().contains(s2)) {
                        int n = 0;
                        ++n;
                    }
                    else {
                        packetWrapper.write(Type.STRING, s2);
                        packetWrapper.write(Type.STRING, s);
                        recipeRewriter.handle(packetWrapper, s2);
                    }
                    int n2 = 0;
                    ++n2;
                }
                packetWrapper.set(Type.VAR_INT, 0, intValue - 0);
            }
        });
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_14.SELECT_TRADE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final InventoryPackets$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final PacketWrapper create = packetWrapper.create(8);
                        create.write(Type.UNSIGNED_BYTE, (short)((EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class)).getLatestTradeWindowId());
                        create.write(Type.SHORT, -999);
                        create.write(Type.BYTE, 2);
                        create.write(Type.SHORT, (short)ThreadLocalRandom.current().nextInt());
                        create.write(Type.VAR_INT, 5);
                        final CompoundTag compoundTag = new CompoundTag();
                        compoundTag.put("force_resync", new DoubleTag(Double.NaN));
                        create.write(Type.FLAT_VAR_INT_ITEM, new DataItem(1, (byte)1, (short)0, compoundTag));
                        create.scheduleSendToServer(Protocol1_14To1_13_2.class);
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getNewItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        final Tag value = item.tag().get("display");
        if (value instanceof CompoundTag) {
            final CompoundTag compoundTag = (CompoundTag)value;
            final Tag value2 = compoundTag.get("Lore");
            if (value2 instanceof ListTag) {
                final ListTag listTag = (ListTag)value2;
                compoundTag.put(InventoryPackets.NBT_TAG_NAME + "|Lore", new ListTag(listTag.clone().getValue()));
                for (final Tag tag : listTag) {
                    if (tag instanceof StringTag) {
                        ((StringTag)tag).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)tag).getValue(), true));
                    }
                }
            }
        }
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        final Tag value = item.tag().get("display");
        if (value instanceof CompoundTag) {
            final CompoundTag compoundTag = (CompoundTag)value;
            final Tag value2 = compoundTag.get("Lore");
            if (value2 instanceof ListTag) {
                final ListTag listTag = (ListTag)value2;
                final ListTag listTag2 = (ListTag)compoundTag.remove(InventoryPackets.NBT_TAG_NAME + "|Lore");
                if (listTag2 != null) {
                    compoundTag.put("Lore", new ListTag(listTag2.getValue()));
                }
                else {
                    for (final Tag tag : listTag) {
                        if (tag instanceof StringTag) {
                            ((StringTag)tag).setValue(ChatRewriter.jsonToLegacyText(((StringTag)tag).getValue()));
                        }
                    }
                }
            }
        }
        return item;
    }
    
    static ComponentRewriter access$000() {
        return InventoryPackets.COMPONENT_REWRITER;
    }
    
    static Set access$100() {
        return InventoryPackets.REMOVED_RECIPE_TYPES;
    }
    
    static {
        NBT_TAG_NAME = "ViaVersion|" + Protocol1_14To1_13_2.class.getSimpleName();
        REMOVED_RECIPE_TYPES = Sets.newHashSet("crafting_special_banneraddpattern", "crafting_special_repairitem");
        COMPONENT_REWRITER = new ComponentRewriter() {
            @Override
            protected void handleTranslate(final JsonObject jsonObject, final String s) {
                super.handleTranslate(jsonObject, s);
                if (s.startsWith("block.") && s.endsWith(".name")) {
                    jsonObject.addProperty("translate", s.substring(0, s.length() - 5));
                }
            }
        };
    }
}
