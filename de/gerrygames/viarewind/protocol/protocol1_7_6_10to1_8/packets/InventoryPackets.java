package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.gson.*;
import de.gerrygames.viarewind.utils.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import java.util.function.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.OPEN_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                final short n = (short)Windows.getInventoryType((String)packetWrapper.read(Type.STRING));
                ((Windows)packetWrapper.user().get(Windows.class)).types.put(shortValue, n);
                packetWrapper.write(Type.UNSIGNED_BYTE, n);
                String s = ChatUtil.removeUnusedColor(ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)), '8');
                if (s.length() > 32) {
                    s = s.substring(0, 32);
                }
                packetWrapper.write(Type.STRING, s);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.write(Type.BOOLEAN, true);
                if (n == 11) {
                    packetWrapper.passthrough(Type.INT);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((Windows)packetWrapper.user().get(Windows.class)).remove((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SET_SLOT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$3::lambda$registerMap$0);
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(InventoryPackets$3::lambda$registerMap$1);
                this.handler(InventoryPackets$3::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) != 0) {
                    return;
                }
                final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                if (shortValue < 5 || shortValue > 8) {
                    return;
                }
                final Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
                Item[] playerEquipment = entityTracker.getPlayerEquipment(uuid);
                if (playerEquipment == null) {
                    entityTracker.setPlayerEquipment(uuid, playerEquipment = new Item[5]);
                }
                playerEquipment[9 - shortValue] = item;
                if (entityTracker.getGamemode() == 3) {
                    packetWrapper.cancel();
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                ItemRewriter.toClient(item);
                packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                final short value = ((Windows)packetWrapper.user().get(Windows.class)).get(shortValue);
                packetWrapper.write(Type.UNSIGNED_BYTE, shortValue);
                short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                if (value == 4) {
                    if (shortValue2 == 1) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (shortValue2 >= 2) {
                        --shortValue2;
                    }
                }
                packetWrapper.write(Type.SHORT, shortValue2);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.WINDOW_ITEMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$4::lambda$registerMap$0);
                this.handler(InventoryPackets$4::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) != 0) {
                    return;
                }
                final Item[] array = (Item[])packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
                Item[] playerEquipment = entityTracker.getPlayerEquipment(uuid);
                if (playerEquipment == null) {
                    entityTracker.setPlayerEquipment(uuid, playerEquipment = new Item[5]);
                }
                while (5 < 9) {
                    playerEquipment[4] = array[5];
                    if (entityTracker.getGamemode() == 3) {
                        array[5] = null;
                    }
                    int n = 0;
                    ++n;
                }
                if (entityTracker.getGamemode() == 3) {
                    final GameProfileStorage.GameProfile value = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(uuid);
                    if (value != null) {
                        array[5] = value.getSkull();
                    }
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //     9: checkcast       Ljava/lang/Short;
                //    12: invokevirtual   java/lang/Short.shortValue:()S
                //    15: istore_1       
                //    16: aload_0        
                //    17: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    22: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Windows;.class
                //    24: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    29: checkcast       Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Windows;
                //    32: iload_1        
                //    33: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Windows.get:(S)S
                //    36: istore_2       
                //    37: aload_0        
                //    38: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //    41: iload_1        
                //    42: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //    45: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //    50: aload_0        
                //    51: getstatic       com/viaversion/viaversion/api/type/Type.ITEM_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //    54: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    59: checkcast       [Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //    62: astore_3       
                //    63: iload_2        
                //    64: iconst_4       
                //    65: if_icmpne       100
                //    68: aload_3        
                //    69: astore          4
                //    71: aload           4
                //    73: arraylength    
                //    74: iconst_1       
                //    75: isub           
                //    76: anewarray       Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //    79: astore_3       
                //    80: aload_3        
                //    81: iconst_0       
                //    82: aload           4
                //    84: iconst_0       
                //    85: aaload         
                //    86: aastore        
                //    87: aload           4
                //    89: iconst_2       
                //    90: aload_3        
                //    91: iconst_1       
                //    92: aload           4
                //    94: arraylength    
                //    95: iconst_3       
                //    96: isub           
                //    97: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
                //   100: iconst_0       
                //   101: aload_3        
                //   102: arraylength    
                //   103: if_icmpge       121
                //   106: aload_3        
                //   107: iconst_0       
                //   108: aload_3        
                //   109: iconst_0       
                //   110: aaload         
                //   111: invokestatic    de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/items/ItemRewriter.toClient:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   114: aastore        
                //   115: iinc            4, 1
                //   118: goto            100
                //   121: aload_0        
                //   122: getstatic       de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //   125: aload_3        
                //   126: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   131: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.WINDOW_PROPERTY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(InventoryPackets$5::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                final Windows windows = (Windows)packetWrapper.user().get(Windows.class);
                final short value = windows.get(shortValue);
                final short shortValue2 = (short)packetWrapper.get(Type.SHORT, 0);
                final short shortValue3 = (short)packetWrapper.get(Type.SHORT, 1);
                if (value == -1) {
                    return;
                }
                if (value == 2) {
                    final Windows.Furnace furnace = windows.furnace.computeIfAbsent(shortValue, InventoryPackets$5::lambda$registerMap$0);
                    if (shortValue2 == 0 || shortValue2 == 1) {
                        if (shortValue2 == 0) {
                            furnace.setFuelLeft(shortValue3);
                        }
                        else {
                            furnace.setMaxFuel(shortValue3);
                        }
                        if (furnace.getMaxFuel() == 0) {
                            packetWrapper.cancel();
                            return;
                        }
                        final short n = (short)(200 * furnace.getFuelLeft() / furnace.getMaxFuel());
                        packetWrapper.set(Type.SHORT, 0, 1);
                        packetWrapper.set(Type.SHORT, 1, n);
                    }
                    else if (shortValue2 == 2 || shortValue2 == 3) {
                        if (shortValue2 == 2) {
                            furnace.setProgress(shortValue3);
                        }
                        else {
                            furnace.setMaxProgress(shortValue3);
                        }
                        if (furnace.getMaxProgress() == 0) {
                            packetWrapper.cancel();
                            return;
                        }
                        final short n2 = (short)(200 * furnace.getProgress() / furnace.getMaxProgress());
                        packetWrapper.set(Type.SHORT, 0, 0);
                        packetWrapper.set(Type.SHORT, 1, n2);
                    }
                }
                else if (value == 4) {
                    if (shortValue2 > 2) {
                        packetWrapper.cancel();
                    }
                }
                else if (value == 8) {
                    windows.levelCost = shortValue3;
                    windows.anvilId = shortValue;
                }
            }
            
            private static Windows.Furnace lambda$registerMap$0(final Short n) {
                return new Windows.Furnace();
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((Windows)packetWrapper.user().get(Windows.class)).remove((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.CLICK_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(InventoryPackets$7::lambda$registerMap$0);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(InventoryPackets$7::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                ItemRewriter.toServer(item);
                packetWrapper.set(Type.ITEM, 0, item);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short n = (byte)packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.UNSIGNED_BYTE, n);
                final short value = ((Windows)packetWrapper.user().get(Windows.class)).get(n);
                short shortValue = (short)packetWrapper.read(Type.SHORT);
                if (value == 4 && shortValue > 0) {
                    ++shortValue;
                }
                packetWrapper.write(Type.SHORT, shortValue);
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.WINDOW_CONFIRMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.BOOLEAN);
                this.handler(InventoryPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.SHORT, 0) == -89) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(InventoryPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                ItemRewriter.toServer(item);
                packetWrapper.set(Type.ITEM, 0, item);
            }
        });
    }
}
