package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.*;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.rewriter.meta.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;

public class BlockItemPackets1_11 extends LegacyBlockItemRewriter
{
    private LegacyEnchantmentRewriter enchantmentRewriter;
    
    public BlockItemPackets1_11(final Protocol1_10To1_11 protocol1_10To1_11) {
        super(protocol1_10To1_11);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SET_SLOT, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (BlockItemPackets1_11.access$000(this.this$1.this$0, packetWrapper.user())) {
                            final Optional access$100 = BlockItemPackets1_11.access$100(this.this$1.this$0, packetWrapper.user());
                            if (!access$100.isPresent()) {
                                return;
                            }
                            final ChestedHorseStorage chestedHorseStorage = access$100.get();
                            final int access$101;
                            packetWrapper.set(Type.SHORT, 0, (access$101 = BlockItemPackets1_11.access$200(this.this$1.this$0, chestedHorseStorage, (short)packetWrapper.get(Type.SHORT, 0))).shortValue());
                            packetWrapper.set(Type.ITEM, 0, BlockItemPackets1_11.access$300(this.this$1.this$0, chestedHorseStorage, access$101, (Item)packetWrapper.get(Type.ITEM, 0)));
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.WINDOW_ITEMS, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Item[] array = (Item[])packetWrapper.get(Type.ITEM_ARRAY, 0);
                        while (0 < array.length) {
                            array[0] = this.this$1.this$0.handleItemToClient(array[0]);
                            int n = 0;
                            ++n;
                        }
                        if (BlockItemPackets1_11.access$000(this.this$1.this$0, packetWrapper.user())) {
                            final Optional access$100 = BlockItemPackets1_11.access$100(this.this$1.this$0, packetWrapper.user());
                            if (!access$100.isPresent()) {
                                return;
                            }
                            final ChestedHorseStorage chestedHorseStorage = access$100.get();
                            final Item[] array2 = Arrays.copyOf(array, chestedHorseStorage.isChested() ? 53 : 38);
                            for (int i = array2.length - 1; i >= 0; --i) {
                                array2[BlockItemPackets1_11.access$200(this.this$1.this$0, chestedHorseStorage, i)] = array2[i];
                                array2[i] = BlockItemPackets1_11.access$300(this.this$1.this$0, chestedHorseStorage, i, array2[i]);
                            }
                            packetWrapper.set(Type.ITEM_ARRAY, 0, array2);
                        }
                    }
                });
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$3 this$1;
                    
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
        ((Protocol1_10To1_11)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (BlockItemPackets1_11.access$000(this.this$1.this$0, packetWrapper.user())) {
                            final Optional access$100 = BlockItemPackets1_11.access$100(this.this$1.this$0, packetWrapper.user());
                            if (!access$100.isPresent()) {
                                return;
                            }
                            packetWrapper.set(Type.SHORT, 0, BlockItemPackets1_11.access$400(this.this$1.this$0, access$100.get(), (short)packetWrapper.get(Type.SHORT, 0)).shortValue());
                        }
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Chunk chunk = (Chunk)packetWrapper.passthrough(new Chunk1_9_3_4Type((ClientWorld)packetWrapper.user().get(ClientWorld.class)));
                        BlockItemPackets1_11.access$500(this.this$1.this$0, chunk);
                        final Iterator iterator = chunk.getBlockEntities().iterator();
                        while (iterator.hasNext()) {
                            final Tag value = iterator.next().get("id");
                            if (!(value instanceof StringTag)) {
                                continue;
                            }
                            if (!((String)value.getValue()).equals("minecraft:sign")) {
                                continue;
                            }
                            ((StringTag)value).setValue("Sign");
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.VAR_INT, 0, this.this$1.this$0.handleBlockID((int)packetWrapper.get(Type.VAR_INT, 0)));
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$7 this$1;
                    
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
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$8 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 10) {
                            packetWrapper.cancel();
                        }
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                            EntityIdRewriter.toClientSpawner((CompoundTag)packetWrapper.get(Type.NBT, 0), true);
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.OPEN_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.COMPONENT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$9 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        if (((String)packetWrapper.get(Type.STRING, 0)).equals("EntityHorse")) {
                            (int)packetWrapper.passthrough(Type.INT);
                        }
                        final String inventory = (String)packetWrapper.get(Type.STRING, 0);
                        final WindowTracker windowTracker = (WindowTracker)packetWrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(inventory);
                        windowTracker.setEntityId(-1);
                        if (BlockItemPackets1_11.access$000(this.this$1.this$0, packetWrapper.user())) {
                            packetWrapper.set(Type.UNSIGNED_BYTE, 1, 17);
                        }
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.CLOSE_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$10 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final WindowTracker windowTracker = (WindowTracker)packetWrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(null);
                        windowTracker.setEntityId(-1);
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLOSE_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_11 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_11$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final WindowTracker windowTracker = (WindowTracker)packetWrapper.user().get(WindowTracker.class);
                        windowTracker.setInventory(null);
                        windowTracker.setEntityId(-1);
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$0);
    }
    
    @Override
    protected void registerRewrites() {
        ((MappedLegacyBlockItem)this.replacementData.computeIfAbsent(52, BlockItemPackets1_11::lambda$registerRewrites$1)).setBlockEntityHandler(BlockItemPackets1_11::lambda$registerRewrites$2);
        (this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName)).registerEnchantment(71, "§cCurse of Vanishing");
        this.enchantmentRewriter.registerEnchantment(10, "§cCurse of Binding");
        this.enchantmentRewriter.setHideLevelForEnchants(71, 10);
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
        EntityIdRewriter.toClientItem(item, true);
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
        EntityIdRewriter.toServerItem(item, true);
        if (tag.contains(this.nbtTagName + "|ench")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
        }
        return item;
    }
    
    private boolean isLlama(final UserConnection userConnection) {
        final WindowTracker windowTracker = (WindowTracker)userConnection.get(WindowTracker.class);
        if (windowTracker.getInventory() != null && windowTracker.getInventory().equals("EntityHorse")) {
            final StoredEntityData entityData = userConnection.getEntityTracker(Protocol1_10To1_11.class).entityData(windowTracker.getEntityId());
            return entityData != null && entityData.type().is(Entity1_11Types.EntityType.LIAMA);
        }
        return false;
    }
    
    private Optional getChestedHorse(final UserConnection userConnection) {
        final WindowTracker windowTracker = (WindowTracker)userConnection.get(WindowTracker.class);
        if (windowTracker.getInventory() != null && windowTracker.getInventory().equals("EntityHorse")) {
            final StoredEntityData entityData = userConnection.getEntityTracker(Protocol1_10To1_11.class).entityData(windowTracker.getEntityId());
            if (entityData != null) {
                return Optional.of(entityData.get(ChestedHorseStorage.class));
            }
        }
        return Optional.empty();
    }
    
    private int getNewSlotId(final ChestedHorseStorage chestedHorseStorage, final int n) {
        final int n2 = chestedHorseStorage.isChested() ? 53 : 38;
        final int n3 = chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0;
        final int n4 = 2 + 3 * n3;
        final int n5 = 15 - 3 * n3;
        if (n >= n4 && n2 > n + n5) {
            return n5 + n;
        }
        if (n == 1) {
            return 0;
        }
        return n;
    }
    
    private int getOldSlotId(final ChestedHorseStorage chestedHorseStorage, final int n) {
        final int n2 = 2 + 3 * (chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0);
        final int n3 = 2 + 3 * (chestedHorseStorage.isChested() ? 5 : 0);
        final int n4 = n3 - n2;
        if (n == 1 || (n >= n2 && n < n3)) {
            return 0;
        }
        if (n >= n3) {
            return n - n4;
        }
        if (n == 0) {
            return 1;
        }
        return n;
    }
    
    private Item getNewItem(final ChestedHorseStorage chestedHorseStorage, final int n, final Item item) {
        final int n2 = 2 + 3 * (chestedHorseStorage.isChested() ? chestedHorseStorage.getLiamaStrength() : 0);
        final int n3 = 2 + 3 * (chestedHorseStorage.isChested() ? 5 : 0);
        if (n >= n2 && n < n3) {
            return new DataItem(166, (byte)1, (short)0, this.getNamedTag("§4SLOT DISABLED"));
        }
        if (n == 1) {
            return null;
        }
        return item;
    }
    
    private static CompoundTag lambda$registerRewrites$2(final int n, final CompoundTag compoundTag) {
        EntityIdRewriter.toClientSpawner(compoundTag, true);
        return compoundTag;
    }
    
    private static MappedLegacyBlockItem lambda$registerRewrites$1(final int n) {
        return new MappedLegacyBlockItem(52, (short)(-1), null, false);
    }
    
    private void lambda$registerPackets$0(final MetaHandlerEvent metaHandlerEvent, final Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }
    
    static boolean access$000(final BlockItemPackets1_11 blockItemPackets1_11, final UserConnection userConnection) {
        return blockItemPackets1_11.isLlama(userConnection);
    }
    
    static Optional access$100(final BlockItemPackets1_11 blockItemPackets1_11, final UserConnection userConnection) {
        return blockItemPackets1_11.getChestedHorse(userConnection);
    }
    
    static int access$200(final BlockItemPackets1_11 blockItemPackets1_11, final ChestedHorseStorage chestedHorseStorage, final int n) {
        return blockItemPackets1_11.getNewSlotId(chestedHorseStorage, n);
    }
    
    static Item access$300(final BlockItemPackets1_11 blockItemPackets1_11, final ChestedHorseStorage chestedHorseStorage, final int n, final Item item) {
        return blockItemPackets1_11.getNewItem(chestedHorseStorage, n, item);
    }
    
    static int access$400(final BlockItemPackets1_11 blockItemPackets1_11, final ChestedHorseStorage chestedHorseStorage, final int n) {
        return blockItemPackets1_11.getOldSlotId(chestedHorseStorage, n);
    }
    
    static void access$500(final BlockItemPackets1_11 blockItemPackets1_11, final Chunk chunk) {
        blockItemPackets1_11.handleChunk(chunk);
    }
}
