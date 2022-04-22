package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((Windows)packetWrapper.user().get(Windows.class)).remove((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.OPEN_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.COMPONENT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$2::lambda$registerMap$0);
                this.handler(InventoryPackets$2::lambda$registerMap$1);
                this.handler(InventoryPackets$2::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                if (((String)packetWrapper.get(Type.STRING, 0)).equalsIgnoreCase("minecraft:shulker_box")) {
                    packetWrapper.set(Type.STRING, 0, "minecraft:container");
                }
                if (((JsonElement)packetWrapper.get(Type.COMPONENT, 0)).toString().equalsIgnoreCase("{\"translate\":\"container.shulkerBox\"}")) {
                    packetWrapper.set(Type.COMPONENT, 0, JsonParser.parseString("{\"text\":\"Shulker Box\"}"));
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((Windows)packetWrapper.user().get(Windows.class)).put((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0), (String)packetWrapper.get(Type.STRING, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((String)packetWrapper.get(Type.STRING, 0)).equals("EntityHorse")) {
                    packetWrapper.passthrough(Type.INT);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.WINDOW_ITEMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                Item[] array = (Item[])packetWrapper.read(Type.ITEM_ARRAY);
                while (0 < array.length) {
                    array[0] = ItemRewriter.toClient(array[0]);
                    int n = 0;
                    ++n;
                }
                if (shortValue == 0 && array.length == 46) {
                    final Item[] array2 = array;
                    array = new Item[45];
                    System.arraycopy(array2, 0, array, 0, 45);
                }
                else {
                    final String value = ((Windows)packetWrapper.user().get(Windows.class)).get(shortValue);
                    if (value != null && value.equalsIgnoreCase("minecraft:brewing_stand")) {
                        System.arraycopy(array, 0, ((Windows)packetWrapper.user().get(Windows.class)).getBrewingItems(shortValue), 0, 4);
                        Windows.updateBrewingStand(packetWrapper.user(), array[4], shortValue);
                        final Item[] array3 = array;
                        array = new Item[array3.length - 1];
                        System.arraycopy(array3, 0, array, 0, 4);
                        System.arraycopy(array3, 5, array, 4, array3.length - 5);
                    }
                }
                packetWrapper.write(Type.ITEM_ARRAY, array);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SET_SLOT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(InventoryPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0)));
                final byte byteValue = ((Short)packetWrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue();
                final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                if (byteValue == 0 && shortValue == 45) {
                    packetWrapper.cancel();
                    return;
                }
                final String value = ((Windows)packetWrapper.user().get(Windows.class)).get(byteValue);
                if (value == null) {
                    return;
                }
                if (value.equalsIgnoreCase("minecraft:brewing_stand")) {
                    if (shortValue > 4) {
                        packetWrapper.set(Type.SHORT, 0, (short)(shortValue - 1));
                    }
                    else {
                        if (shortValue == 4) {
                            packetWrapper.cancel();
                            Windows.updateBrewingStand(packetWrapper.user(), (Item)packetWrapper.get(Type.ITEM, 0), byteValue);
                            return;
                        }
                        ((Windows)packetWrapper.user().get(Windows.class)).getBrewingItems(byteValue)[shortValue] = (Item)packetWrapper.get(Type.ITEM, 0);
                    }
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((Windows)packetWrapper.user().get(Windows.class)).remove((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.CLICK_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE, Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(InventoryPackets$6::lambda$registerMap$0);
                this.handler(InventoryPackets$6::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final String value = ((Windows)packetWrapper.user().get(Windows.class)).get((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                if (value == null) {
                    return;
                }
                if (value.equalsIgnoreCase("minecraft:brewing_stand")) {
                    final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                    if (shortValue > 3) {
                        packetWrapper.set(Type.SHORT, 0, (short)(shortValue + 1));
                    }
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item)packetWrapper.get(Type.ITEM, 0)));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_8.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(InventoryPackets$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item)packetWrapper.get(Type.ITEM, 0)));
            }
        });
    }
}
