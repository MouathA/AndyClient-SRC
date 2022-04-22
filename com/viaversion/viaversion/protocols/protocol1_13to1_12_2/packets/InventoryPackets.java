package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.nio.charset.*;
import com.google.common.base.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.google.common.primitives.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;

public class InventoryPackets extends ItemRewriter
{
    private static final String NBT_TAG_NAME;
    
    public InventoryPackets(final Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        super(protocol1_13To1_12_2);
    }
    
    public void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.SET_SLOT, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.WINDOW_ITEMS, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
                this.handler(this.this$0.itemArrayHandler(Type.FLAT_ITEM_ARRAY));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.WINDOW_PROPERTY, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler() {
                    final InventoryPackets$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                        if (shortValue >= 4 && shortValue <= 6) {
                            packetWrapper.set(Type.SHORT, 1, (short)((Protocol1_13To1_12_2)InventoryPackets.access$000(this.this$1.this$0)).getMappingData().getEnchantmentMappings().getNewId((short)packetWrapper.get(Type.SHORT, 1)));
                        }
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s = (String)packetWrapper.get(Type.STRING, 0);
                        if (s.equalsIgnoreCase("MC|StopSound")) {
                            final String s2 = (String)packetWrapper.read(Type.STRING);
                            final String s3 = (String)packetWrapper.read(Type.STRING);
                            packetWrapper.clearPacket();
                            packetWrapper.setId(76);
                            packetWrapper.write(Type.BYTE, 0);
                            if (!s2.isEmpty()) {
                                final byte b = 1;
                                Optional<SoundSource> optional = (Optional<SoundSource>)SoundSource.findBySource(s2);
                                if (!optional.isPresent()) {
                                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        Via.getPlatform().getLogger().info("Could not handle unknown sound source " + s2 + " falling back to default: master");
                                    }
                                    optional = Optional.of(SoundSource.MASTER);
                                }
                                packetWrapper.write(Type.VAR_INT, optional.get().getId());
                            }
                            if (!s3.isEmpty()) {
                                final byte b2 = 2;
                                packetWrapper.write(Type.STRING, s3);
                            }
                            packetWrapper.set(Type.BYTE, 0, 0);
                            return;
                        }
                        String newPluginChannelId;
                        if (s.equalsIgnoreCase("MC|TrList")) {
                            newPluginChannelId = "minecraft:trader_list";
                            packetWrapper.passthrough(Type.INT);
                            while (0 < (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE)) {
                                final Item item = (Item)packetWrapper.read(Type.ITEM);
                                this.this$1.this$0.handleItemToClient(item);
                                packetWrapper.write(Type.FLAT_ITEM, item);
                                final Item item2 = (Item)packetWrapper.read(Type.ITEM);
                                this.this$1.this$0.handleItemToClient(item2);
                                packetWrapper.write(Type.FLAT_ITEM, item2);
                                if (packetWrapper.passthrough(Type.BOOLEAN)) {
                                    final Item item3 = (Item)packetWrapper.read(Type.ITEM);
                                    this.this$1.this$0.handleItemToClient(item3);
                                    packetWrapper.write(Type.FLAT_ITEM, item3);
                                }
                                packetWrapper.passthrough(Type.BOOLEAN);
                                packetWrapper.passthrough(Type.INT);
                                packetWrapper.passthrough(Type.INT);
                                int n = 0;
                                ++n;
                            }
                        }
                        else {
                            final String s4 = s;
                            newPluginChannelId = InventoryPackets.getNewPluginChannelId(s);
                            if (newPluginChannelId == null) {
                                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + s4);
                                }
                                packetWrapper.cancel();
                                return;
                            }
                            if (newPluginChannelId.equals("minecraft:register") || newPluginChannelId.equals("minecraft:unregister")) {
                                final String[] split = new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                                final ArrayList<String> list = new ArrayList<String>();
                                while (0 < split.length) {
                                    final String newPluginChannelId2 = InventoryPackets.getNewPluginChannelId(split[0]);
                                    if (newPluginChannelId2 != null) {
                                        list.add(newPluginChannelId2);
                                    }
                                    else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + split[0]);
                                    }
                                    int n2 = 0;
                                    ++n2;
                                }
                                if (list.isEmpty()) {
                                    packetWrapper.cancel();
                                    return;
                                }
                                packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\0').join(list).getBytes(StandardCharsets.UTF_8));
                            }
                        }
                        packetWrapper.set(Type.STRING, 0, newPluginChannelId);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.ENTITY_EQUIPMENT, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final InventoryPackets$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final String s;
                        final String oldPluginChannelId = InventoryPackets.getOldPluginChannelId(s = (String)packetWrapper.get(Type.STRING, 0));
                        if (oldPluginChannelId == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + s);
                            }
                            packetWrapper.cancel();
                            return;
                        }
                        if (oldPluginChannelId.equals("REGISTER") || oldPluginChannelId.equals("UNREGISTER")) {
                            final String[] split = new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            final ArrayList<String> list = new ArrayList<String>();
                            while (0 < split.length) {
                                final String oldPluginChannelId2 = InventoryPackets.getOldPluginChannelId(split[0]);
                                if (oldPluginChannelId2 != null) {
                                    list.add(oldPluginChannelId2);
                                }
                                else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + split[0]);
                                }
                                int n = 0;
                                ++n;
                            }
                            packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\0').join(list).getBytes(StandardCharsets.UTF_8));
                        }
                        packetWrapper.set(Type.STRING, 0, oldPluginChannelId);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            final InventoryPackets this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
            }
        });
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        final int n = item.identifier() << 16 | (item.data() & 0xFFFF);
        final int n2 = item.identifier() << 4 | (item.data() & 0xF);
        if (isDamageable(item.identifier())) {
            if (tag == null) {
                item.setTag(tag = new CompoundTag());
            }
            tag.put("Damage", new IntTag(item.data()));
        }
        if (item.identifier() == 358) {
            if (tag == null) {
                item.setTag(tag = new CompoundTag());
            }
            tag.put("map", new IntTag(item.data()));
        }
        if (tag != null) {
            final boolean b = item.identifier() == 425;
            if ((b || item.identifier() == 442) && tag.get("BlockEntityTag") instanceof CompoundTag) {
                final CompoundTag compoundTag = (CompoundTag)tag.get("BlockEntityTag");
                if (compoundTag.get("Base") instanceof IntTag) {
                    final IntTag intTag = (IntTag)compoundTag.get("Base");
                    if (b) {
                        final int n3 = 6800 + intTag.asInt();
                    }
                    intTag.setValue(15 - intTag.asInt());
                }
                if (compoundTag.get("Patterns") instanceof ListTag) {
                    for (final Tag tag2 : (ListTag)compoundTag.get("Patterns")) {
                        if (tag2 instanceof CompoundTag) {
                            final IntTag intTag2 = (IntTag)((CompoundTag)tag2).get("Color");
                            intTag2.setValue(15 - intTag2.asInt());
                        }
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag) {
                final CompoundTag compoundTag2 = (CompoundTag)tag.get("display");
                if (compoundTag2.get("Name") instanceof StringTag) {
                    final StringTag stringTag = (StringTag)compoundTag2.get("Name");
                    compoundTag2.put(InventoryPackets.NBT_TAG_NAME + "|Name", new StringTag(stringTag.getValue()));
                    stringTag.setValue(ChatRewriter.legacyTextToJsonString(stringTag.getValue(), true));
                }
            }
            if (tag.get("ench") instanceof ListTag) {
                final ListTag listTag = (ListTag)tag.get("ench");
                final ListTag listTag2 = new ListTag(CompoundTag.class);
                for (final Tag tag3 : listTag) {
                    final NumberTag numberTag;
                    if (tag3 instanceof CompoundTag && (numberTag = (NumberTag)((CompoundTag)tag3).get("id")) != null) {
                        final CompoundTag compoundTag3 = new CompoundTag();
                        final short short1 = numberTag.asShort();
                        String string = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(short1);
                        if (string == null) {
                            string = "viaversion:legacy/" + short1;
                        }
                        compoundTag3.put("id", new StringTag(string));
                        compoundTag3.put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag3).get("lvl")).asShort()));
                        listTag2.add(compoundTag3);
                    }
                }
                tag.remove("ench");
                tag.put("Enchantments", listTag2);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                final ListTag listTag3 = (ListTag)tag.get("StoredEnchantments");
                final ListTag listTag4 = new ListTag(CompoundTag.class);
                for (final Tag tag4 : listTag3) {
                    if (tag4 instanceof CompoundTag) {
                        final CompoundTag compoundTag4 = new CompoundTag();
                        final short short2 = ((NumberTag)((CompoundTag)tag4).get("id")).asShort();
                        String string2 = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(short2);
                        if (string2 == null) {
                            string2 = "viaversion:legacy/" + short2;
                        }
                        compoundTag4.put("id", new StringTag(string2));
                        compoundTag4.put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag4).get("lvl")).asShort()));
                        listTag4.add(compoundTag4);
                    }
                }
                tag.remove("StoredEnchantments");
                tag.put("StoredEnchantments", listTag4);
            }
            int n4 = 0;
            if (tag.get("CanPlaceOn") instanceof ListTag) {
                final ListTag listTag5 = (ListTag)tag.get("CanPlaceOn");
                final ListTag listTag6 = new ListTag(StringTag.class);
                tag.put(InventoryPackets.NBT_TAG_NAME + "|CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag5)));
                final Iterator iterator4 = listTag5.iterator();
                while (iterator4.hasNext()) {
                    String replace = iterator4.next().getValue().toString().replace("minecraft:", "");
                    final String s = (String)BlockIdData.numberIdToString.get(Ints.tryParse(replace));
                    if (s != null) {
                        replace = s;
                    }
                    final String[] array = BlockIdData.blockIdMapping.get(replace.toLowerCase(Locale.ROOT));
                    if (array != null) {
                        final String[] array2 = array;
                        while (0 < array2.length) {
                            listTag6.add(new StringTag(array2[0]));
                            ++n4;
                        }
                    }
                    else {
                        listTag6.add(new StringTag(replace.toLowerCase(Locale.ROOT)));
                    }
                }
                tag.put("CanPlaceOn", listTag6);
            }
            if (tag.get("CanDestroy") instanceof ListTag) {
                final ListTag listTag7 = (ListTag)tag.get("CanDestroy");
                final ListTag listTag8 = new ListTag(StringTag.class);
                tag.put(InventoryPackets.NBT_TAG_NAME + "|CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag7)));
                final Iterator iterator5 = listTag7.iterator();
                while (iterator5.hasNext()) {
                    String replace2 = iterator5.next().getValue().toString().replace("minecraft:", "");
                    final String s2 = (String)BlockIdData.numberIdToString.get(Ints.tryParse(replace2));
                    if (s2 != null) {
                        replace2 = s2;
                    }
                    final String[] array3 = BlockIdData.blockIdMapping.get(replace2.toLowerCase(Locale.ROOT));
                    if (array3 != null) {
                        final String[] array4 = array3;
                        while (0 < array4.length) {
                            listTag8.add(new StringTag(array4[0]));
                            ++n4;
                        }
                    }
                    else {
                        listTag8.add(new StringTag(replace2.toLowerCase(Locale.ROOT)));
                    }
                }
                tag.put("CanDestroy", listTag8);
            }
            if (item.identifier() == 383 && tag.get("EntityTag") instanceof CompoundTag) {
                final CompoundTag compoundTag5 = (CompoundTag)tag.get("EntityTag");
                if (compoundTag5.get("id") instanceof StringTag) {
                    SpawnEggRewriter.getSpawnEggId(((StringTag)compoundTag5.get("id")).getValue());
                    if (16 != -1) {
                        compoundTag5.remove("id");
                        if (compoundTag5.isEmpty()) {
                            tag.remove("EntityTag");
                        }
                    }
                }
            }
            if (tag.isEmpty()) {
                item.setTag(tag = null);
            }
        }
        if (!Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(16)) {
            if (!isDamageable(item.identifier()) && item.identifier() != 358) {
                if (tag == null) {
                    item.setTag(tag = new CompoundTag());
                }
                tag.put(InventoryPackets.NBT_TAG_NAME, new IntTag(n));
            }
            if (item.identifier() != 31 || item.data() != 0) {
                if (!Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(16)) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                    }
                }
            }
        }
        item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().get(16));
        item.setData((short)0);
        return item;
    }
    
    public static String getNewPluginChannelId(final String s) {
        switch (s.hashCode()) {
            case -37059198: {
                if (s.equals("MC|TrList")) {
                    break;
                }
                break;
            }
            case -294893183: {
                if (s.equals("MC|Brand")) {
                    break;
                }
                break;
            }
            case -295921722: {
                if (s.equals("MC|BOpen")) {
                    break;
                }
                break;
            }
            case 125533714: {
                if (s.equals("MC|DebugPath")) {
                    break;
                }
                break;
            }
            case 2076087261: {
                if (s.equals("MC|DebugNeighborsUpdate")) {
                    break;
                }
                break;
            }
            case 92413603: {
                if (s.equals("REGISTER")) {
                    break;
                }
                break;
            }
            case 1321107516: {
                if (s.equals("UNREGISTER")) {
                    break;
                }
                break;
            }
            case 1537336522: {
                if (s.equals("BungeeCord")) {
                    break;
                }
                break;
            }
            case -234943831: {
                if (s.equals("bungeecord:main")) {}
                break;
            }
        }
        switch (8) {
            case 0: {
                return "minecraft:trader_list";
            }
            case 1: {
                return "minecraft:brand";
            }
            case 2: {
                return "minecraft:book_open";
            }
            case 3: {
                return "minecraft:debug/paths";
            }
            case 4: {
                return "minecraft:debug/neighbors_update";
            }
            case 5: {
                return "minecraft:register";
            }
            case 6: {
                return "minecraft:unregister";
            }
            case 7: {
                return "bungeecord:main";
            }
            case 8: {
                return null;
            }
            default: {
                final String s2 = Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get(s);
                if (s2 != null) {
                    return s2;
                }
                return MappingData.validateNewChannel(s);
            }
        }
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        Integer n = null;
        CompoundTag tag = item.tag();
        if (tag != null && tag.get(InventoryPackets.NBT_TAG_NAME) instanceof IntTag) {
            n = ((NumberTag)tag.get(InventoryPackets.NBT_TAG_NAME)).asInt();
            tag.remove(InventoryPackets.NBT_TAG_NAME);
        }
        if (n == null) {
            final int value = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().get(item.identifier());
            if (value != -1) {
                final Optional entityId = SpawnEggRewriter.getEntityId(value);
                if (entityId.isPresent()) {
                    n = 25100288;
                    if (tag == null) {
                        item.setTag(tag = new CompoundTag());
                    }
                    if (!tag.contains("EntityTag")) {
                        final CompoundTag compoundTag = new CompoundTag();
                        compoundTag.put("id", new StringTag(entityId.get()));
                        tag.put("EntityTag", compoundTag);
                    }
                }
                else {
                    n = (value >> 4 << 16 | (value & 0xF));
                }
            }
        }
        if (n == null) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.identifier());
            }
            n = 65536;
        }
        item.setIdentifier((short)(n >> 16));
        item.setData((short)(n & 0xFFFF));
        if (tag != null) {
            if (isDamageable(item.identifier()) && tag.get("Damage") instanceof IntTag) {
                if (!true) {
                    item.setData((short)(int)tag.get("Damage").getValue());
                }
                tag.remove("Damage");
            }
            if (item.identifier() == 358 && tag.get("map") instanceof IntTag) {
                if (!true) {
                    item.setData((short)(int)tag.get("map").getValue());
                }
                tag.remove("map");
            }
            if ((item.identifier() == 442 || item.identifier() == 425) && tag.get("BlockEntityTag") instanceof CompoundTag) {
                final CompoundTag compoundTag2 = (CompoundTag)tag.get("BlockEntityTag");
                if (compoundTag2.get("Base") instanceof IntTag) {
                    final IntTag intTag = (IntTag)compoundTag2.get("Base");
                    intTag.setValue(15 - intTag.asInt());
                }
                if (compoundTag2.get("Patterns") instanceof ListTag) {
                    for (final Tag tag2 : (ListTag)compoundTag2.get("Patterns")) {
                        if (tag2 instanceof CompoundTag) {
                            final IntTag intTag2 = (IntTag)((CompoundTag)tag2).get("Color");
                            intTag2.setValue(15 - intTag2.asInt());
                        }
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag) {
                final CompoundTag compoundTag3 = (CompoundTag)tag.get("display");
                if (compoundTag3.get("Name") instanceof StringTag) {
                    final StringTag stringTag = (StringTag)compoundTag3.get("Name");
                    final StringTag stringTag2 = (StringTag)compoundTag3.remove(InventoryPackets.NBT_TAG_NAME + "|Name");
                    stringTag.setValue((stringTag2 != null) ? stringTag2.getValue() : ChatRewriter.jsonToLegacyText(stringTag.getValue()));
                }
            }
            if (tag.get("Enchantments") instanceof ListTag) {
                final ListTag listTag = (ListTag)tag.get("Enchantments");
                final ListTag listTag2 = new ListTag(CompoundTag.class);
                for (final Tag tag3 : listTag) {
                    if (tag3 instanceof CompoundTag) {
                        final CompoundTag compoundTag4 = new CompoundTag();
                        final String s = (String)((CompoundTag)tag3).get("id").getValue();
                        Short value2 = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(s);
                        if (value2 == null && s.startsWith("viaversion:legacy/")) {
                            value2 = Short.valueOf(s.substring(18));
                        }
                        if (value2 == null) {
                            continue;
                        }
                        compoundTag4.put("id", new ShortTag(value2));
                        compoundTag4.put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag3).get("lvl")).asShort()));
                        listTag2.add(compoundTag4);
                    }
                }
                tag.remove("Enchantments");
                tag.put("ench", listTag2);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                final ListTag listTag3 = (ListTag)tag.get("StoredEnchantments");
                final ListTag listTag4 = new ListTag(CompoundTag.class);
                for (final Tag tag4 : listTag3) {
                    if (tag4 instanceof CompoundTag) {
                        final CompoundTag compoundTag5 = new CompoundTag();
                        final String s2 = (String)((CompoundTag)tag4).get("id").getValue();
                        Short value3 = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(s2);
                        if (value3 == null && s2.startsWith("viaversion:legacy/")) {
                            value3 = Short.valueOf(s2.substring(18));
                        }
                        if (value3 == null) {
                            continue;
                        }
                        compoundTag5.put("id", new ShortTag(value3));
                        compoundTag5.put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag4).get("lvl")).asShort()));
                        listTag4.add(compoundTag5);
                    }
                }
                tag.remove("StoredEnchantments");
                tag.put("StoredEnchantments", listTag4);
            }
            int n2 = 0;
            if (tag.get(InventoryPackets.NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
                tag.put("CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag.get(InventoryPackets.NBT_TAG_NAME + "|CanPlaceOn"))));
                tag.remove(InventoryPackets.NBT_TAG_NAME + "|CanPlaceOn");
            }
            else if (tag.get("CanPlaceOn") instanceof ListTag) {
                final ListTag listTag5 = (ListTag)tag.get("CanPlaceOn");
                final ListTag listTag6 = new ListTag(StringTag.class);
                for (final Tag tag5 : listTag5) {
                    final Object value4 = tag5.getValue();
                    final String[] array = BlockIdData.fallbackReverseMapping.get((value4 instanceof String) ? ((String)value4).replace("minecraft:", "") : null);
                    if (array != null) {
                        final String[] array2 = array;
                        while (0 < array2.length) {
                            listTag6.add(new StringTag(array2[0]));
                            ++n2;
                        }
                    }
                    else {
                        listTag6.add(tag5);
                    }
                }
                tag.put("CanPlaceOn", listTag6);
            }
            if (tag.get(InventoryPackets.NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
                tag.put("CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag.get(InventoryPackets.NBT_TAG_NAME + "|CanDestroy"))));
                tag.remove(InventoryPackets.NBT_TAG_NAME + "|CanDestroy");
            }
            else if (tag.get("CanDestroy") instanceof ListTag) {
                final ListTag listTag7 = (ListTag)tag.get("CanDestroy");
                final ListTag listTag8 = new ListTag(StringTag.class);
                for (final Tag tag6 : listTag7) {
                    final Object value5 = tag6.getValue();
                    final String[] array3 = BlockIdData.fallbackReverseMapping.get((value5 instanceof String) ? ((String)value5).replace("minecraft:", "") : null);
                    if (array3 != null) {
                        final String[] array4 = array3;
                        while (0 < array4.length) {
                            listTag8.add(new StringTag(array4[0]));
                            ++n2;
                        }
                    }
                    else {
                        listTag8.add(tag6);
                    }
                }
                tag.put("CanDestroy", listTag8);
            }
        }
        return item;
    }
    
    public static String getOldPluginChannelId(String validateNewChannel) {
        validateNewChannel = MappingData.validateNewChannel(validateNewChannel);
        if (validateNewChannel == null) {
            return null;
        }
        final String s = validateNewChannel;
        switch (s.hashCode()) {
            case 1963953250: {
                if (s.equals("minecraft:trader_list")) {
                    break;
                }
                break;
            }
            case -420924333: {
                if (s.equals("minecraft:book_open")) {
                    break;
                }
                break;
            }
            case 832866277: {
                if (s.equals("minecraft:debug/paths")) {
                    break;
                }
                break;
            }
            case 1745645488: {
                if (s.equals("minecraft:debug/neighbors_update")) {
                    break;
                }
                break;
            }
            case 339275216: {
                if (s.equals("minecraft:register")) {
                    break;
                }
                break;
            }
            case -1963049943: {
                if (s.equals("minecraft:unregister")) {
                    break;
                }
                break;
            }
            case -1149721734: {
                if (s.equals("minecraft:brand")) {
                    break;
                }
                break;
            }
            case -234943831: {
                if (s.equals("bungeecord:main")) {}
                break;
            }
        }
        switch (7) {
            case 0: {
                return "MC|TrList";
            }
            case 1: {
                return "MC|BOpen";
            }
            case 2: {
                return "MC|DebugPath";
            }
            case 3: {
                return "MC|DebugNeighborsUpdate";
            }
            case 4: {
                return "REGISTER";
            }
            case 5: {
                return "UNREGISTER";
            }
            case 6: {
                return "MC|Brand";
            }
            case 7: {
                return "BungeeCord";
            }
            default: {
                final String s2 = Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get(validateNewChannel);
                if (s2 != null) {
                    return s2;
                }
                return (validateNewChannel.length() > 20) ? validateNewChannel.substring(0, 20) : validateNewChannel;
            }
        }
    }
    
    public static boolean isDamageable(final int n) {
        return (n >= 256 && n <= 259) || n == 261 || (n >= 267 && n <= 279) || (n >= 283 && n <= 286) || (n >= 290 && n <= 294) || (n >= 298 && n <= 317) || n == 346 || n == 359 || n == 398 || n == 442 || n == 443;
    }
    
    static Protocol access$000(final InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
    
    static {
        NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();
    }
}
