package com.viaversion.viaversion.protocols.protocol1_17_1to1_17;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public final class Protocol1_17_1To1_17 extends AbstractProtocol
{
    private static final StringType PAGE_STRING_TYPE;
    private static final StringType TITLE_STRING_TYPE;
    
    public Protocol1_17_1To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }
    
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_17_1.REMOVE_ENTITIES, new PacketRemapper() {
            final Protocol1_17_1To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17_1To1_17$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { (int)packetWrapper.read(Type.VAR_INT) });
            }
        });
        this.registerClientbound(ClientboundPackets1_17.SET_SLOT, new PacketRemapper() {
            final Protocol1_17_1To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(ClientboundPackets1_17.WINDOW_ITEMS, new PacketRemapper() {
            final Protocol1_17_1To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 0);
                this.handler(Protocol1_17_1To1_17$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY));
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, null);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() {
            final Protocol1_17_1To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.read(Type.VAR_INT);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() {
            final Protocol1_17_1To1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(Protocol1_17_1To1_17$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final CompoundTag compoundTag = new CompoundTag();
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, new DataItem(942, (byte)1, (short)0, compoundTag));
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                final ListTag listTag = new ListTag(StringTag.class);
                while (0 < intValue2) {
                    listTag.add(new StringTag((String)packetWrapper.read(Protocol1_17_1To1_17.access$000())));
                    int n = 0;
                    ++n;
                }
                if (listTag.size() == 0) {
                    listTag.add(new StringTag(""));
                }
                compoundTag.put("pages", listTag);
                if (packetWrapper.read(Type.BOOLEAN)) {
                    compoundTag.put("title", new StringTag((String)packetWrapper.read(Protocol1_17_1To1_17.access$100())));
                    compoundTag.put("author", new StringTag(packetWrapper.user().getProtocolInfo().getUsername()));
                    packetWrapper.write(Type.BOOLEAN, true);
                }
                else {
                    packetWrapper.write(Type.BOOLEAN, false);
                }
                packetWrapper.write(Type.VAR_INT, intValue);
            }
        });
    }
    
    static StringType access$000() {
        return Protocol1_17_1To1_17.PAGE_STRING_TYPE;
    }
    
    static StringType access$100() {
        return Protocol1_17_1To1_17.TITLE_STRING_TYPE;
    }
    
    static {
        PAGE_STRING_TYPE = new StringType(8192);
        TITLE_STRING_TYPE = new StringType(128);
    }
}
