package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;

public final class Protocol1_17To1_17_1 extends BackwardsProtocol
{
    private static final int MAX_PAGE_LENGTH = 8192;
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_PAGES = 200;
    
    public Protocol1_17To1_17_1() {
        super(ClientboundPackets1_17_1.class, ClientboundPackets1_17.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_17_1.REMOVE_ENTITIES, null, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int[] array = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                packetWrapper.cancel();
                final int[] array2 = array;
                while (0 < array2.length) {
                    final int n = array2[0];
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                    create.write(Type.VAR_INT, n);
                    create.send(Protocol1_17To1_17_1.class);
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_17_1.CLOSE_WINDOW, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryStateIds)packetWrapper.user().get(InventoryStateIds.class)).removeStateId((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE));
            }
        });
        this.registerClientbound(ClientboundPackets1_17_1.SET_SLOT, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryStateIds)packetWrapper.user().get(InventoryStateIds.class)).setStateId((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE), (int)packetWrapper.read(Type.VAR_INT));
            }
        });
        this.registerClientbound(ClientboundPackets1_17_1.WINDOW_ITEMS, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryStateIds)packetWrapper.user().get(InventoryStateIds.class)).setStateId((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE), (int)packetWrapper.read(Type.VAR_INT));
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY, packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLOSE_WINDOW, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((InventoryStateIds)packetWrapper.user().get(InventoryStateIds.class)).removeStateId((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE));
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int removeStateId = ((InventoryStateIds)packetWrapper.user().get(InventoryStateIds.class)).removeStateId((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE));
                packetWrapper.write(Type.VAR_INT, (removeStateId == Integer.MAX_VALUE) ? 0 : removeStateId);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_17To1_17_1 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17To1_17_1$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Item item = (Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                packetWrapper.passthrough(Type.VAR_INT);
                final CompoundTag tag = item.tag();
                StringTag stringTag = null;
                ListTag listTag;
                if (tag == null || (listTag = (ListTag)tag.get("pages")) == null || (booleanValue && (stringTag = (StringTag)tag.get("title")) == null)) {
                    packetWrapper.write(Type.VAR_INT, 0);
                    packetWrapper.write(Type.BOOLEAN, false);
                    return;
                }
                if (listTag.size() > 200) {
                    listTag = new ListTag(listTag.getValue().subList(0, 200));
                }
                packetWrapper.write(Type.VAR_INT, listTag.size());
                final Iterator iterator = listTag.iterator();
                while (iterator.hasNext()) {
                    String s = ((StringTag)iterator.next()).getValue();
                    if (s.length() > 8192) {
                        s = s.substring(0, 8192);
                    }
                    packetWrapper.write(Type.STRING, s);
                }
                packetWrapper.write(Type.BOOLEAN, booleanValue);
                if (booleanValue) {
                    if (stringTag == null) {
                        stringTag = (StringTag)tag.get("title");
                    }
                    String s2 = stringTag.getValue();
                    if (s2.length() > 128) {
                        s2 = s2.substring(0, 128);
                    }
                    packetWrapper.write(Type.STRING, s2);
                }
            }
        });
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        userConnection.put(new InventoryStateIds());
    }
}
