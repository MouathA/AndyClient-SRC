package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_16 extends ItemRewriter
{
    private EnchantmentRewriter enchantmentRewriter;
    
    public BlockItemPackets1_16(final Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        super(protocol1_15_2To1_16);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.DECLARE_RECIPES, new PacketRemapper(new RecipeRewriter1_14(this.protocol)) {
            final RecipeRewriter1_14 val$recipeRewriter;
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(BlockItemPackets1_16$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final RecipeRewriter1_14 recipeRewriter1_14, final PacketWrapper packetWrapper) throws Exception {
                int intValue;
                while (0 < (intValue = (int)packetWrapper.passthrough(Type.VAR_INT))) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    final String replace = s.replace("minecraft:", "");
                    if (replace.equals("smithing")) {
                        --intValue;
                        packetWrapper.read(Type.STRING);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                    }
                    else {
                        packetWrapper.write(Type.STRING, s);
                        final String s2 = (String)packetWrapper.passthrough(Type.STRING);
                        recipeRewriter1_14.handle(packetWrapper, replace);
                    }
                    int n = 0;
                    ++n;
                }
                packetWrapper.set(Type.VAR_INT, 0, intValue);
            }
        });
        this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.ENTITY_EQUIPMENT, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                final ArrayList<EquipmentData> list = new ArrayList<EquipmentData>();
                byte byteValue;
                int n;
                do {
                    byteValue = (byte)packetWrapper.read(Type.BYTE);
                    final Item handleItemToClient = this.this$0.handleItemToClient((Item)packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
                    n = (byteValue & 0x7F);
                    list.add(new EquipmentData(1, handleItemToClient, null));
                } while ((byteValue & 0xFFFFFF80) != 0x0);
                final EquipmentData equipmentData = list.get(0);
                packetWrapper.write(Type.VAR_INT, EquipmentData.access$100(equipmentData));
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, EquipmentData.access$200(equipmentData));
                while (1 < list.size()) {
                    final PacketWrapper create = packetWrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
                    final EquipmentData equipmentData2 = list.get(1);
                    create.write(Type.VAR_INT, intValue);
                    create.write(Type.VAR_INT, EquipmentData.access$100(equipmentData2));
                    create.write(Type.FLAT_VAR_INT_ITEM, EquipmentData.access$200(equipmentData2));
                    create.send(Protocol1_15_2To1_16.class);
                    ++n;
                }
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_LIGHT, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$2);
            }
            
            private void lambda$registerMap$2(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: new             Lcom/viaversion/viaversion/protocols/protocol1_16to1_15_2/types/Chunk1_16Type;
                //     4: dup            
                //     5: invokespecial   com/viaversion/viaversion/protocols/protocol1_16to1_15_2/types/Chunk1_16Type.<init>:()V
                //     8: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    13: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                //    16: astore_2       
                //    17: aload_1        
                //    18: new             Lcom/viaversion/viaversion/protocols/protocol1_15to1_14_4/types/Chunk1_15Type;
                //    21: dup            
                //    22: invokespecial   com/viaversion/viaversion/protocols/protocol1_15to1_14_4/types/Chunk1_15Type.<init>:()V
                //    25: aload_2        
                //    26: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //    31: iconst_0       
                //    32: aload_2        
                //    33: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //    38: arraylength    
                //    39: if_icmpge       119
                //    42: aload_2        
                //    43: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //    48: iconst_0       
                //    49: aaload         
                //    50: astore          4
                //    52: aload           4
                //    54: ifnonnull       60
                //    57: goto            113
                //    60: iconst_0       
                //    61: aload           4
                //    63: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                //    68: if_icmpge       113
                //    71: aload           4
                //    73: iconst_0       
                //    74: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                //    79: istore          6
                //    81: aload           4
                //    83: iconst_0       
                //    84: aload_0        
                //    85: getfield        com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16$4.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16;
                //    88: invokestatic    com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16.access$300:(Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16;)Lcom/viaversion/viaversion/api/protocol/Protocol;
                //    91: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/Protocol1_15_2To1_16;
                //    94: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/Protocol1_15_2To1_16.getMappingData:()Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/data/BackwardsMappings;
                //    97: iload           6
                //    99: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/data/BackwardsMappings.getNewBlockStateId:(I)I
                //   102: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                //   107: iinc            5, 1
                //   110: goto            60
                //   113: iinc            3, 1
                //   116: goto            31
                //   119: aload_2        
                //   120: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getHeightMap:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   125: astore_3       
                //   126: aload_3        
                //   127: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.values:()Ljava/util/Collection;
                //   130: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
                //   135: astore          4
                //   137: aload           4
                //   139: invokeinterface java/util/Iterator.hasNext:()Z
                //   144: ifeq            216
                //   147: aload           4
                //   149: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   154: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   157: astore          5
                //   159: aload           5
                //   161: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag;
                //   164: astore          6
                //   166: sipush          256
                //   169: newarray        I
                //   171: astore          7
                //   173: bipush          9
                //   175: aload           7
                //   177: arraylength    
                //   178: aload           6
                //   180: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag.getValue:()[J
                //   183: aload           7
                //   185: invokedynamic   BootstrapMethod #1, consume:([I)Lcom/viaversion/viaversion/util/BiIntConsumer;
                //   190: invokestatic    com/viaversion/viaversion/util/CompactArrayUtil.iterateCompactArrayWithPadding:(II[JLcom/viaversion/viaversion/util/BiIntConsumer;)V
                //   193: aload           6
                //   195: bipush          9
                //   197: aload           7
                //   199: arraylength    
                //   200: aload           7
                //   202: invokedynamic   BootstrapMethod #2, applyAsLong:([I)Ljava/util/function/IntToLongFunction;
                //   207: invokestatic    com/viaversion/viaversion/util/CompactArrayUtil.createCompactArray:(IILjava/util/function/IntToLongFunction;)[J
                //   210: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag.setValue:([J)V
                //   213: goto            137
                //   216: aload_2        
                //   217: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.isBiomeData:()Z
                //   222: ifeq            288
                //   225: iconst_0       
                //   226: sipush          1024
                //   229: if_icmpge       288
                //   232: aload_2        
                //   233: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //   238: iconst_0       
                //   239: iaload         
                //   240: istore          5
                //   242: iconst_0       
                //   243: tableswitch {
                //              340: 272
                //              341: 272
                //              342: 272
                //              343: 272
                //          default: 282
                //        }
                //   272: aload_2        
                //   273: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //   278: iconst_0       
                //   279: bipush          8
                //   281: iastore        
                //   282: iinc            4, 1
                //   285: goto            225
                //   288: aload_2        
                //   289: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //   294: ifnonnull       298
                //   297: return         
                //   298: aload_2        
                //   299: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //   304: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //   309: astore          4
                //   311: aload           4
                //   313: invokeinterface java/util/Iterator.hasNext:()Z
                //   318: ifeq            345
                //   321: aload           4
                //   323: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   328: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   331: astore          5
                //   333: aload_0        
                //   334: getfield        com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16$4.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16;
                //   337: aload           5
                //   339: invokestatic    com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16.access$400:(Lcom/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
                //   342: goto            311
                //   345: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            private static long lambda$registerMap$1(final int[] array, final int n) {
                return array[n];
            }
            
            private static void lambda$registerMap$0(final int[] array, final int n, final int n2) {
                array[n] = n2;
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(BlockItemPackets1_16$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                if (shortValue >= 4 && shortValue <= 6) {
                    final short shortValue2 = (short)packetWrapper.get(Type.SHORT, 1);
                    if (shortValue2 > 11) {
                        packetWrapper.set(Type.SHORT, 1, (short)(shortValue2 - 1));
                    }
                    else if (shortValue2 == 11) {
                        packetWrapper.set(Type.SHORT, 1, 9);
                    }
                }
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.MAP_DATA, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.passthrough(Type.POSITION1_14);
                (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                BlockItemPackets1_16.access$400(this.this$0, (CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_15_2To1_16)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() {
            final BlockItemPackets1_16 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
    }
    
    private void handleBlockEntity(final CompoundTag compoundTag) {
        final StringTag stringTag = (StringTag)compoundTag.get("id");
        if (stringTag == null) {
            return;
        }
        final String value = stringTag.getValue();
        if (value.equals("minecraft:conduit")) {
            final Tag remove = compoundTag.remove("Target");
            if (!(remove instanceof IntArrayTag)) {
                return;
            }
            compoundTag.put("target_uuid", new StringTag(UUIDIntArrayType.uuidFromIntArray((int[])remove.getValue()).toString()));
        }
        else if (value.equals("minecraft:skull")) {
            final Tag remove2 = compoundTag.remove("SkullOwner");
            if (!(remove2 instanceof CompoundTag)) {
                return;
            }
            final CompoundTag compoundTag2 = (CompoundTag)remove2;
            final Tag remove3 = compoundTag2.remove("Id");
            if (remove3 instanceof IntArrayTag) {
                compoundTag2.put("Id", new StringTag(UUIDIntArrayType.uuidFromIntArray((int[])remove3.getValue()).toString()));
            }
            final CompoundTag compoundTag3 = new CompoundTag();
            for (final Map.Entry<String, V> entry : compoundTag2) {
                compoundTag3.put(entry.getKey(), (Tag)entry.getValue());
            }
            compoundTag.put("Owner", compoundTag3);
        }
    }
    
    @Override
    protected void registerRewrites() {
        (this.enchantmentRewriter = new EnchantmentRewriter(this)).registerEnchantment("minecraft:soul_speed", "§7Soul Speed");
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        final CompoundTag tag = item.tag();
        if (item.identifier() == 771 && tag != null) {
            final Tag value = tag.get("SkullOwner");
            if (value instanceof CompoundTag) {
                final CompoundTag compoundTag = (CompoundTag)value;
                final Tag value2 = compoundTag.get("Id");
                if (value2 instanceof IntArrayTag) {
                    compoundTag.put("Id", new StringTag(UUIDIntArrayType.uuidFromIntArray((int[])value2.getValue()).toString()));
                }
            }
        }
        InventoryPackets.newToOldAttributes(item);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        final int identifier = item.identifier();
        super.handleItemToServer(item);
        final CompoundTag tag = item.tag();
        if (identifier == 771 && tag != null) {
            final Tag value = tag.get("SkullOwner");
            if (value instanceof CompoundTag) {
                final CompoundTag compoundTag = (CompoundTag)value;
                final Tag value2 = compoundTag.get("Id");
                if (value2 instanceof StringTag) {
                    compoundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(UUID.fromString((String)value2.getValue()))));
                }
            }
        }
        InventoryPackets.oldToNewAttributes(item);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }
    
    static Protocol access$300(final BlockItemPackets1_16 blockItemPackets1_16) {
        return blockItemPackets1_16.protocol;
    }
    
    static void access$400(final BlockItemPackets1_16 blockItemPackets1_16, final CompoundTag compoundTag) {
        blockItemPackets1_16.handleBlockEntity(compoundTag);
    }
    
    private static final class EquipmentData
    {
        private final int slot;
        private final Item item;
        
        private EquipmentData(final int slot, final Item item) {
            this.slot = slot;
            this.item = item;
        }
        
        EquipmentData(final int n, final Item item, final BlockItemPackets1_16$1 packetRemapper) {
            this(n, item);
        }
        
        static int access$100(final EquipmentData equipmentData) {
            return equipmentData.slot;
        }
        
        static Item access$200(final EquipmentData equipmentData) {
            return equipmentData.item;
        }
    }
}
