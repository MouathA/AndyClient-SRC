package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public class BlockItemPackets1_12 extends LegacyBlockItemRewriter
{
    public BlockItemPackets1_12(final Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MAP_DATA, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT) * 3) {
                            packetWrapper.passthrough(Type.BYTE);
                            int n = 0;
                            ++n;
                        }
                    }
                });
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.passthrough(Type.UNSIGNED_BYTE) <= 0) {
                            return;
                        }
                        (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        final byte[] array = (byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        while (0 < array.length) {
                            final short n = (short)(array[0] & 0xFF);
                            if (n > 143) {
                                array[0] = (byte)MapColorMapping.getNearestOldColor(n);
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, array);
                    }
                });
            }
        });
        this.registerSetSlot(ClientboundPackets1_12.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.PLUGIN_MESSAGE, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$2 this$1;
                    
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
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 1) {
                            packetWrapper.set(Type.ITEM, 0, null);
                            final PacketWrapper create = packetWrapper.create(ServerboundPackets1_12.WINDOW_CONFIRMATION);
                            create.write(Type.UNSIGNED_BYTE, packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                            create.write(Type.SHORT, packetWrapper.get(Type.SHORT, 1));
                            create.write(Type.BOOLEAN, false);
                            packetWrapper.sendToServer(Protocol1_11_1To1_12.class);
                            packetWrapper.cancel();
                            create.sendToServer(Protocol1_11_1To1_12.class);
                            return;
                        }
                        this.this$1.this$0.handleItemToServer((Item)packetWrapper.get(Type.ITEM, 0));
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        BlockItemPackets1_12.access$000(this.this$1.this$0, (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class))));
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$1.this$0.handleBlockID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        while (0 < array.length) {
                            final BlockChangeRecord blockChangeRecord = array[0];
                            blockChangeRecord.setBlockId(this.this$1.this$0.handleBlockID(blockChangeRecord.getBlockId()));
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$7 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$0);
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLIENT_STATUS, new PacketRemapper() {
            final BlockItemPackets1_12 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_12$8 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((int)packetWrapper.get(Type.VAR_INT, 0) == 2) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        if (item.tag() != null) {
            final CompoundTag compoundTag = new CompoundTag();
            if (this.handleNbtToClient(item.tag(), compoundTag)) {
                item.tag().put("Via|LongArrayTags", compoundTag);
            }
        }
        return item;
    }
    
    private boolean handleNbtToClient(final CompoundTag compoundTag, final CompoundTag compoundTag2) {
        final Iterator iterator = compoundTag.iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, V> entry = iterator.next();
            if (entry.getValue() instanceof CompoundTag) {
                final CompoundTag compoundTag3 = new CompoundTag();
                compoundTag2.put(entry.getKey(), compoundTag3);
                final boolean b = true | this.handleNbtToClient((CompoundTag)entry.getValue(), compoundTag3);
            }
            else {
                if (!(entry.getValue() instanceof LongArrayTag)) {
                    continue;
                }
                compoundTag2.put(entry.getKey(), this.fromLongArrayTag((LongArrayTag)entry.getValue()));
                iterator.remove();
            }
        }
        return true;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null) {
            final Tag remove = item.tag().remove("Via|LongArrayTags");
            if (remove instanceof CompoundTag) {
                this.handleNbtToServer(item.tag(), (CompoundTag)remove);
            }
        }
        return item;
    }
    
    private void handleNbtToServer(final CompoundTag compoundTag, final CompoundTag compoundTag2) {
        for (final Map.Entry<String, V> entry : compoundTag2) {
            if (entry.getValue() instanceof CompoundTag) {
                this.handleNbtToServer((CompoundTag)compoundTag.get(entry.getKey()), (CompoundTag)entry.getValue());
            }
            else {
                compoundTag.put(entry.getKey(), this.fromIntArrayTag((IntArrayTag)entry.getValue()));
            }
        }
    }
    
    private IntArrayTag fromLongArrayTag(final LongArrayTag longArrayTag) {
        final int[] array = new int[longArrayTag.length() * 2];
        final long[] value = longArrayTag.getValue();
        while (0 < value.length) {
            final long n = value[0];
            final int[] array2 = array;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            array2[n2] = (int)(n >> 32);
            final int[] array3 = array;
            final int n4 = 0;
            ++n3;
            array3[n4] = (int)n;
            int n5 = 0;
            ++n5;
        }
        return new IntArrayTag(array);
    }
    
    private LongArrayTag fromIntArrayTag(final IntArrayTag intArrayTag) {
        final long[] array = new long[intArrayTag.length() / 2];
        final int[] value = intArrayTag.getValue();
        while (0 < value.length) {
            array[0] = ((long)value[0] << 32 | ((long)value[1] & 0xFFFFFFFFL));
            final int n;
            n += 2;
            int n2 = 0;
            ++n2;
        }
        return new LongArrayTag(array);
    }
    
    private void lambda$registerPackets$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }
    
    static void access$000(final BlockItemPackets1_12 blockItemPackets1_12, final Chunk chunk) {
        blockItemPackets1_12.handleChunk(chunk);
    }
}
