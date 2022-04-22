package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class InventoryPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.WINDOW_PROPERTY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler() {
                    final InventoryPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final short shortValue2 = (short)packetWrapper.get(Type.SHORT, 0);
                        final short shortValue3 = (short)packetWrapper.get(Type.SHORT, 1);
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equalsIgnoreCase("minecraft:enchanting_table") && shortValue2 > 3 && shortValue2 < 7) {
                            final short n = (short)(shortValue3 >> 8);
                            packetWrapper.create(packetWrapper.getId(), new PacketHandler(shortValue, shortValue2, (short)(shortValue3 & 0xFF)) {
                                final short val$windowId;
                                final short val$property;
                                final short val$enchantID;
                                final InventoryPackets$1$1 this$1;
                                
                                @Override
                                public void handle(final PacketWrapper packetWrapper) throws Exception {
                                    packetWrapper.write(Type.UNSIGNED_BYTE, this.val$windowId);
                                    packetWrapper.write(Type.SHORT, this.val$property);
                                    packetWrapper.write(Type.SHORT, this.val$enchantID);
                                }
                            }).scheduleSend(Protocol1_9To1_8.class);
                            packetWrapper.set(Type.SHORT, 0, (short)(shortValue2 + 3));
                            packetWrapper.set(Type.SHORT, 1, n);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.OPEN_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final InventoryPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        ((InventoryTracker)packetWrapper.user().get(InventoryTracker.class)).setInventory((String)packetWrapper.get(Type.STRING, 0));
                    }
                });
                this.handler(new PacketHandler() {
                    final InventoryPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((String)packetWrapper.get(Type.STRING, 0)).equals("minecraft:brewing_stand")) {
                            packetWrapper.set(Type.UNSIGNED_BYTE, 1, (short)((short)packetWrapper.get(Type.UNSIGNED_BYTE, 1) + 1));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SET_SLOT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final InventoryPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                        if (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking()) {
                            final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                            final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            inventoryTracker.setItemId(((Short)packetWrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue(), (short)packetWrapper.get(Type.SHORT, 0), (item == null) ? 0 : item.identifier());
                            entityTracker1_9.syncShieldWithSword();
                        }
                        ItemRewriter.toClient(item);
                    }
                });
                this.handler(new PacketHandler() {
                    final InventoryPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand") && shortValue >= 4) {
                            packetWrapper.set(Type.SHORT, 0, (short)(shortValue + 1));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.WINDOW_ITEMS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY);
                this.handler(new PacketHandler() {
                    final InventoryPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item[] array = (Item[])packetWrapper.get(Type.ITEM_ARRAY, 0);
                        final Short n = (Short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        final boolean b = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        while (0 < array.length) {
                            final Item item = array[0];
                            if (b) {
                                inventoryTracker.setItemId(n, (short)0, (item == null) ? 0 : item.identifier());
                            }
                            ItemRewriter.toClient(item);
                            final short n2 = 1;
                        }
                        if (b) {
                            entityTracker1_9.syncShieldWithSword();
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final InventoryPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                            final Item[] array = (Item[])packetWrapper.get(Type.ITEM_ARRAY, 0);
                            final Item[] array2 = new Item[array.length + 1];
                            while (0 < array2.length) {
                                if (0 > 4) {
                                    array2[0] = array[-1];
                                }
                                else if (0 != 4) {
                                    array2[0] = array[0];
                                }
                                int n = 0;
                                ++n;
                            }
                            packetWrapper.set(Type.ITEM_ARRAY, 0, array2);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final InventoryPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        inventoryTracker.setInventory(null);
                        inventoryTracker.resetInventory((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.MAP_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler() {
                    final InventoryPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) {
                        packetWrapper.write(Type.BOOLEAN, true);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final InventoryPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                        if (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking()) {
                            final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                            final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            inventoryTracker.setItemId((short)0, (short)packetWrapper.get(Type.SHORT, 0), (item == null) ? 0 : item.identifier());
                            entityTracker1_9.syncShieldWithSword();
                        }
                        ItemRewriter.toServer(item);
                    }
                });
                this.handler(new PacketHandler() {
                    final InventoryPackets$7 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                        if (shortValue == 45) {
                            packetWrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler(shortValue) {
                                final short val$slot;
                                final InventoryPackets$7$2 this$1;
                                
                                @Override
                                public void handle(final PacketWrapper packetWrapper) throws Exception {
                                    packetWrapper.write(Type.UNSIGNED_BYTE, 0);
                                    packetWrapper.write(Type.SHORT, this.val$slot);
                                    packetWrapper.write(Type.ITEM, null);
                                }
                            }).send(Protocol1_9To1_8.class);
                            packetWrapper.set(Type.SHORT, 0, -999);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.CLICK_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT, Type.BYTE);
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final InventoryPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                        if (Via.getConfig().isShowShieldWhenSwordInHand()) {
                            ((InventoryTracker)packetWrapper.user().get(InventoryTracker.class)).handleWindowClick(packetWrapper.user(), (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0), (byte)packetWrapper.get(Type.BYTE, 1), (short)packetWrapper.get(Type.SHORT, 0), (byte)packetWrapper.get(Type.BYTE, 0));
                        }
                        ItemRewriter.toServer(item);
                    }
                });
                this.handler(new PacketHandler() {
                    final InventoryPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final short shortValue2 = (short)packetWrapper.get(Type.SHORT, 0);
                        final boolean b = shortValue2 == 45 && shortValue == 0;
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                            if (shortValue2 == 4) {}
                            if (shortValue2 > 4) {
                                packetWrapper.set(Type.SHORT, 0, (short)(shortValue2 - 1));
                            }
                        }
                        if (true) {
                            packetWrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler(shortValue, shortValue2) {
                                final short val$windowID;
                                final short val$slot;
                                final InventoryPackets$8$2 this$1;
                                
                                @Override
                                public void handle(final PacketWrapper packetWrapper) throws Exception {
                                    packetWrapper.write(Type.UNSIGNED_BYTE, this.val$windowID);
                                    packetWrapper.write(Type.SHORT, this.val$slot);
                                    packetWrapper.write(Type.ITEM, null);
                                }
                            }).scheduleSend(Protocol1_9To1_8.class);
                            packetWrapper.set(Type.BYTE, 0, 0);
                            packetWrapper.set(Type.BYTE, 1, 0);
                            packetWrapper.set(Type.SHORT, 0, -999);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.CLOSE_WINDOW, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final InventoryPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final InventoryTracker inventoryTracker = (InventoryTracker)packetWrapper.user().get(InventoryTracker.class);
                        inventoryTracker.setInventory(null);
                        inventoryTracker.resetInventory((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.HELD_ITEM_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.handler(new PacketHandler() {
                    final InventoryPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final boolean b = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (entityTracker1_9.isBlocking()) {
                            entityTracker1_9.setBlocking(false);
                            if (!b) {
                                entityTracker1_9.setSecondHand(null);
                            }
                        }
                        if (b) {
                            entityTracker1_9.setHeldItemSlot((short)packetWrapper.get(Type.SHORT, 0));
                            entityTracker1_9.syncShieldWithSword();
                        }
                    }
                });
            }
        });
    }
}
