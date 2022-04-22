package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.*;
import java.util.function.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viabackwards.*;
import java.util.*;
import com.google.common.primitives.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.protocol.*;

public class BlockItemPackets1_13 extends ItemRewriter
{
    private final Map enchantmentMappings;
    private final String extraNbtTag;
    
    public BlockItemPackets1_13(final Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
        this.enchantmentMappings = new HashMap();
        this.extraNbtTag = "VB|" + protocol1_12_2To1_13.getClass().getSimpleName() + "|2";
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.COOLDOWN, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$1 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        (int)packetWrapper.read(Type.VAR_INT);
                        final int value = ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$000(this.this$1.this$0)).getMappingData().getItemMappings().get(25100288);
                        if (value != -1) {
                            if (SpawnEggRewriter.getEntityId(value).isPresent()) {}
                        }
                        packetWrapper.write(Type.VAR_INT, 25100288);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$2 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (209 != 73) {
                            if (209 != 99) {
                                if (209 != 92) {
                                    if (209 != 142) {
                                        if (209 != 305) {
                                            if (209 != 249) {
                                                if (209 != 257) {
                                                    if (209 != 140) {
                                                        if (209 != 472) {
                                                            if (209 < 483 || 209 <= 498) {}
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        packetWrapper.set(Type.VAR_INT, 0, 209);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$3 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final BackwardsBlockEntityProvider backwardsBlockEntityProvider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                        if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 5) {
                            packetWrapper.cancel();
                        }
                        packetWrapper.set(Type.NBT, 0, backwardsBlockEntityProvider.transform(packetWrapper.user(), (Position)packetWrapper.get(Type.POSITION, 0), (CompoundTag)packetWrapper.get(Type.NBT, 0)));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.UNLOAD_CHUNK, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$4 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int n = (int)packetWrapper.passthrough(Type.INT) << 4;
                        final int n2 = (int)packetWrapper.passthrough(Type.INT) << 4;
                        ((BackwardsBlockStorage)packetWrapper.user().get(BackwardsBlockStorage.class)).getBlocks().entrySet().removeIf(BlockItemPackets1_13$4$1::lambda$handle$0);
                    }
                    
                    private static boolean lambda$handle$0(final int n, final int n2, final int n3, final int n4, final Map.Entry entry) {
                        final Position position = entry.getKey();
                        return position.getX() >= n && position.getZ() >= n2 && position.getX() <= n3 && position.getZ() <= n4;
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$5 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        ((BackwardsBlockStorage)packetWrapper.user().get(BackwardsBlockStorage.class)).checkAndStore(position, intValue);
                        packetWrapper.write(Type.VAR_INT, ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$100(this.this$1.this$0)).getMappingData().getNewBlockStateId(intValue));
                        BlockItemPackets1_13.access$200(packetWrapper.user(), intValue, position);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$6 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final BackwardsBlockStorage backwardsBlockStorage = (BackwardsBlockStorage)packetWrapper.user().get(BackwardsBlockStorage.class);
                        final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        while (0 < array.length) {
                            final BlockChangeRecord blockChangeRecord = array[0];
                            final int intValue = (int)packetWrapper.get(Type.INT, 0);
                            final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                            final int blockId = blockChangeRecord.getBlockId();
                            final Position position = new Position(blockChangeRecord.getSectionX() + intValue * 16, blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + intValue2 * 16);
                            backwardsBlockStorage.checkAndStore(position, blockId);
                            BlockItemPackets1_13.access$200(packetWrapper.user(), blockId, position);
                            blockChangeRecord.setBlockId(((Protocol1_12_2To1_13)BlockItemPackets1_13.access$300(this.this$1.this$0)).getMappingData().getNewBlockStateId(blockId));
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLAT_ITEM_ARRAY, Type.ITEM_ARRAY);
                this.handler(this.this$0.itemArrayHandler(Type.ITEM_ARRAY));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //     6: ldc             Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;.class
                //     8: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    13: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;
                //    16: astore_2       
                //    17: new             Lcom/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type;
                //    20: dup            
                //    21: aload_2        
                //    22: invokespecial   com/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type.<init>:(Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
                //    25: astore_3       
                //    26: new             Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type;
                //    29: dup            
                //    30: aload_2        
                //    31: invokespecial   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type.<init>:(Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
                //    34: astore          4
                //    36: aload_1        
                //    37: aload           4
                //    39: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    44: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                //    47: astore          5
                //    49: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
                //    52: invokeinterface com/viaversion/viaversion/api/ViaManager.getProviders:()Lcom/viaversion/viaversion/api/platform/providers/ViaProviders;
                //    57: ldc             Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider;.class
                //    59: invokevirtual   com/viaversion/viaversion/api/platform/providers/ViaProviders.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/platform/providers/Provider;
                //    62: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider;
                //    65: astore          6
                //    67: aload_1        
                //    68: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    73: ldc             Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/storage/BackwardsBlockStorage;.class
                //    75: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    80: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/storage/BackwardsBlockStorage;
                //    83: astore          7
                //    85: aload           5
                //    87: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //    92: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //    97: astore          8
                //    99: aload           8
                //   101: invokeinterface java/util/Iterator.hasNext:()Z
                //   106: ifeq            315
                //   109: aload           8
                //   111: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   116: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   119: astore          9
                //   121: aload           9
                //   123: ldc             "id"
                //   125: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   128: astore          10
                //   130: aload           10
                //   132: ifnonnull       138
                //   135: goto            99
                //   138: aload           10
                //   140: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/Tag.getValue:()Ljava/lang/Object;
                //   143: checkcast       Ljava/lang/String;
                //   146: astore          11
                //   148: aload           6
                //   150: aload           11
                //   152: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider.isHandled:(Ljava/lang/String;)Z
                //   155: ifne            161
                //   158: goto            99
                //   161: aload           9
                //   163: ldc             "y"
                //   165: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   168: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   171: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   174: iconst_4       
                //   175: ishr           
                //   176: istore          12
                //   178: iconst_0       
                //   179: iflt            99
                //   182: iconst_0       
                //   183: bipush          15
                //   185: if_icmple       191
                //   188: goto            99
                //   191: aload           5
                //   193: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   198: iconst_0       
                //   199: aaload         
                //   200: astore          13
                //   202: aload           9
                //   204: ldc             "x"
                //   206: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   209: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   212: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   215: istore          14
                //   217: aload           9
                //   219: ldc             "y"
                //   221: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   224: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   227: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   230: istore          15
                //   232: aload           9
                //   234: ldc             "z"
                //   236: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   239: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   242: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   245: istore          16
                //   247: new             Lcom/viaversion/viaversion/api/minecraft/Position;
                //   250: dup            
                //   251: iload           14
                //   253: iload           15
                //   255: i2s            
                //   256: iload           16
                //   258: invokespecial   com/viaversion/viaversion/api/minecraft/Position.<init>:(ISI)V
                //   261: astore          17
                //   263: aload           13
                //   265: iload           14
                //   267: bipush          15
                //   269: iand           
                //   270: iload           15
                //   272: bipush          15
                //   274: iand           
                //   275: iload           16
                //   277: bipush          15
                //   279: iand           
                //   280: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
                //   285: istore          18
                //   287: aload           7
                //   289: aload           17
                //   291: iload           18
                //   293: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/storage/BackwardsBlockStorage.checkAndStore:(Lcom/viaversion/viaversion/api/minecraft/Position;I)V
                //   296: aload           6
                //   298: aload_1        
                //   299: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   304: aload           17
                //   306: aload           9
                //   308: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider.transform:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   311: pop            
                //   312: goto            99
                //   315: iconst_0       
                //   316: aload           5
                //   318: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   323: arraylength    
                //   324: if_icmpge       541
                //   327: aload           5
                //   329: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   334: iconst_0       
                //   335: aaload         
                //   336: astore          9
                //   338: aload           9
                //   340: ifnonnull       346
                //   343: goto            535
                //   346: iconst_0       
                //   347: bipush          16
                //   349: if_icmpge       476
                //   352: iconst_0       
                //   353: bipush          16
                //   355: if_icmpge       470
                //   358: iconst_0       
                //   359: bipush          16
                //   361: if_icmpge       464
                //   364: aload           9
                //   366: iconst_0       
                //   367: iconst_0       
                //   368: iconst_0       
                //   369: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
                //   374: istore          13
                //   376: iload           13
                //   378: invokestatic    com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/block_entity_handlers/FlowerPotHandler.isFlowah:(I)Z
                //   381: ifeq            458
                //   384: new             Lcom/viaversion/viaversion/api/minecraft/Position;
                //   387: dup            
                //   388: iconst_0       
                //   389: aload           5
                //   391: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   396: iconst_4       
                //   397: ishl           
                //   398: iadd           
                //   399: iconst_0       
                //   400: i2s            
                //   401: iconst_0       
                //   402: aload           5
                //   404: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   409: iconst_4       
                //   410: ishl           
                //   411: iadd           
                //   412: invokespecial   com/viaversion/viaversion/api/minecraft/Position.<init>:(ISI)V
                //   415: astore          14
                //   417: aload           7
                //   419: aload           14
                //   421: iload           13
                //   423: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/storage/BackwardsBlockStorage.checkAndStore:(Lcom/viaversion/viaversion/api/minecraft/Position;I)V
                //   426: aload           6
                //   428: aload_1        
                //   429: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   434: aload           14
                //   436: ldc             "minecraft:flower_pot"
                //   438: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/providers/BackwardsBlockEntityProvider.transform:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   441: astore          15
                //   443: aload           5
                //   445: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //   450: aload           15
                //   452: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //   457: pop            
                //   458: iinc            12, 1
                //   461: goto            358
                //   464: iinc            11, 1
                //   467: goto            352
                //   470: iinc            10, 1
                //   473: goto            346
                //   476: iconst_0       
                //   477: aload           9
                //   479: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                //   484: if_icmpge       535
                //   487: aload           9
                //   489: iconst_0       
                //   490: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                //   495: istore          11
                //   497: iconst_0       
                //   498: ifeq            529
                //   501: aload_0        
                //   502: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13$9.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13;
                //   505: invokestatic    com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.access$400:(Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13;)Lcom/viaversion/viaversion/api/protocol/Protocol;
                //   508: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13;
                //   511: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13.getMappingData:()Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings;
                //   514: iconst_0       
                //   515: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings.getNewBlockStateId:(I)I
                //   518: istore          12
                //   520: aload           9
                //   522: iconst_0       
                //   523: iconst_0       
                //   524: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                //   529: iinc            10, 1
                //   532: goto            476
                //   535: iinc            8, 1
                //   538: goto            315
                //   541: aload           5
                //   543: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.isBiomeData:()Z
                //   548: ifeq            658
                //   551: iconst_0       
                //   552: sipush          256
                //   555: if_icmpge       658
                //   558: aload           5
                //   560: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //   565: iconst_0       
                //   566: iaload         
                //   567: istore          9
                //   569: iload           9
                //   571: tableswitch {
                //               80: 628
                //               81: 628
                //               82: 628
                //               83: 628
                //               84: 637
                //               85: 637
                //               86: 637
                //               87: 631
                //               88: 631
                //               89: 631
                //               90: 634
                //          default: 637
                //        }
                //   628: goto            637
                //   631: goto            637
                //   634: goto            637
                //   637: iconst_0       
                //   638: iconst_m1      
                //   639: if_icmpeq       652
                //   642: aload           5
                //   644: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //   649: iconst_0       
                //   650: iconst_0       
                //   651: iastore        
                //   652: iinc            8, 1
                //   655: goto            551
                //   658: aload_1        
                //   659: aload_3        
                //   660: aload           5
                //   662: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   667: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
                //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
                //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
                //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
                //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
                //     at java.lang.Thread.run(Unknown Source)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.EFFECT, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$10 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                        if (intValue == 1010) {
                            packetWrapper.set(Type.INT, 1, ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$500(this.this$1.this$0)).getMappingData().getItemMappings().get(intValue2) >> 4);
                        }
                        else if (intValue == 2001) {
                            final int newBlockStateId = ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$600(this.this$1.this$0)).getMappingData().getNewBlockStateId(intValue2);
                            packetWrapper.set(Type.INT, 1, (newBlockStateId >> 4 & 0xFFF) | (newBlockStateId & 0xF) << 12);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler() {
                    final BlockItemPackets1_13$11 this$1;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                            final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                            final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                            final byte byteValue2 = (byte)packetWrapper.read(Type.BYTE);
                            final byte byteValue3 = (byte)packetWrapper.read(Type.BYTE);
                            if (packetWrapper.read(Type.BOOLEAN)) {
                                packetWrapper.read(Type.COMPONENT);
                            }
                            if (intValue > 9) {
                                packetWrapper.set(Type.VAR_INT, 1, (int)packetWrapper.get(Type.VAR_INT, 1) - 1);
                            }
                            else {
                                packetWrapper.write(Type.BYTE, (byte)(intValue << 4 | (byteValue3 & 0xF)));
                                packetWrapper.write(Type.BYTE, byteValue);
                                packetWrapper.write(Type.BYTE, byteValue2);
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.WINDOW_PROPERTY, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final short shortValue = (short)packetWrapper.get(Type.SHORT, 0);
                if (shortValue >= 4 && shortValue <= 6) {
                    packetWrapper.set(Type.SHORT, 1, (short)((Protocol1_12_2To1_13)BlockItemPackets1_13.access$700(this.this$0)).getMappingData().getEnchantmentMappings().getNewId((short)packetWrapper.get(Type.SHORT, 1)));
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.CLICK_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
    }
    
    @Override
    protected void registerRewrites() {
        this.enchantmentMappings.put("minecraft:loyalty", "§7Loyalty");
        this.enchantmentMappings.put("minecraft:impaling", "§7Impaling");
        this.enchantmentMappings.put("minecraft:riptide", "§7Riptide");
        this.enchantmentMappings.put("minecraft:channeling", "§7Channeling");
    }
    
    @Override
    public Item handleItemToClient(final Item p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: aload_1        
        //     7: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //    12: istore_2       
        //    13: aconst_null    
        //    14: astore_3       
        //    15: aload_1        
        //    16: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.tag:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //    21: astore          5
        //    23: aload           5
        //    25: ifnull          55
        //    28: aload           5
        //    30: aload_0        
        //    31: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.extraNbtTag:Ljava/lang/String;
        //    34: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //    37: dup            
        //    38: astore          6
        //    40: ifnull          55
        //    43: aload           6
        //    45: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
        //    48: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
        //    51: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    54: astore_3       
        //    55: aload_3        
        //    56: ifnonnull       183
        //    59: aload_0        
        //    60: aload_1        
        //    61: invokespecial   com/viaversion/viabackwards/api/rewriters/ItemRewriter.handleItemToClient:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)Lcom/viaversion/viaversion/api/minecraft/item/Item;
        //    64: pop            
        //    65: aload_1        
        //    66: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //    71: iconst_m1      
        //    72: if_icmpne       153
        //    75: iload_2        
        //    76: sipush          362
        //    79: if_icmpne       91
        //    82: ldc             15007744
        //    84: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    87: astore_3       
        //    88: goto            183
        //    91: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
        //    94: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isSuppressConversionWarnings:()Z
        //    99: ifeq            113
        //   102: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
        //   105: invokeinterface com/viaversion/viaversion/api/ViaManager.isDebug:()Z
        //   110: ifeq            143
        //   113: invokestatic    com/viaversion/viabackwards/ViaBackwards.getPlatform:()Lcom/viaversion/viabackwards/api/ViaBackwardsPlatform;
        //   116: invokeinterface com/viaversion/viabackwards/api/ViaBackwardsPlatform.getLogger:()Ljava/util/logging/Logger;
        //   121: new             Ljava/lang/StringBuilder;
        //   124: dup            
        //   125: invokespecial   java/lang/StringBuilder.<init>:()V
        //   128: ldc             "Failed to get 1.12 item for "
        //   130: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   133: iload_2        
        //   134: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   137: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   140: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   143: ldc_w           65536
        //   146: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   149: astore_3       
        //   150: goto            183
        //   153: aload           5
        //   155: ifnonnull       166
        //   158: aload_1        
        //   159: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.tag:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   164: astore          5
        //   166: aload_0        
        //   167: aload_1        
        //   168: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   173: aload_1        
        //   174: aload           5
        //   176: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.itemIdToRaw:(ILcom/viaversion/viaversion/api/minecraft/item/Item;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)I
        //   179: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   182: astore_3       
        //   183: aload_1        
        //   184: aload_3        
        //   185: invokevirtual   java/lang/Integer.intValue:()I
        //   188: bipush          16
        //   190: ishr           
        //   191: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setIdentifier:(I)V
        //   196: aload_1        
        //   197: aload_3        
        //   198: invokevirtual   java/lang/Integer.intValue:()I
        //   201: ldc_w           65535
        //   204: iand           
        //   205: i2s            
        //   206: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setData:(S)V
        //   211: aload           5
        //   213: ifnull          444
        //   216: aload_1        
        //   217: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   222: if_icmplt       264
        //   225: aload           5
        //   227: ldc_w           "Damage"
        //   230: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   233: astore          7
        //   235: goto            264
        //   238: aload           7
        //   240: instanceof      Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   243: ifeq            264
        //   246: aload_1        
        //   247: aload           7
        //   249: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/Tag.getValue:()Ljava/lang/Object;
        //   252: checkcast       Ljava/lang/Integer;
        //   255: invokevirtual   java/lang/Integer.intValue:()I
        //   258: i2s            
        //   259: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setData:(S)V
        //   264: aload_1        
        //   265: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   270: sipush          358
        //   273: if_icmpne       315
        //   276: aload           5
        //   278: ldc_w           "map"
        //   281: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   284: astore          7
        //   286: goto            315
        //   289: aload           7
        //   291: instanceof      Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   294: ifeq            315
        //   297: aload_1        
        //   298: aload           7
        //   300: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/Tag.getValue:()Ljava/lang/Object;
        //   303: checkcast       Ljava/lang/Integer;
        //   306: invokevirtual   java/lang/Integer.intValue:()I
        //   309: i2s            
        //   310: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setData:(S)V
        //   315: aload_0        
        //   316: aload_1        
        //   317: aload           5
        //   319: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.invertShieldAndBannerId:(Lcom/viaversion/viaversion/api/minecraft/item/Item;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //   322: aload           5
        //   324: ldc_w           "display"
        //   327: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   330: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   333: astore          7
        //   335: aload           7
        //   337: ifnull          412
        //   340: aload           7
        //   342: ldc_w           "Name"
        //   345: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   348: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
        //   351: astore          8
        //   353: aload           8
        //   355: ifnull          412
        //   358: aload           7
        //   360: new             Ljava/lang/StringBuilder;
        //   363: dup            
        //   364: invokespecial   java/lang/StringBuilder.<init>:()V
        //   367: aload_0        
        //   368: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.extraNbtTag:Ljava/lang/String;
        //   371: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   374: ldc_w           "|Name"
        //   377: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   380: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   383: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
        //   386: dup            
        //   387: aload           8
        //   389: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
        //   392: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.<init>:(Ljava/lang/String;)V
        //   395: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   398: pop            
        //   399: aload           8
        //   401: aload           8
        //   403: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
        //   406: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.jsonToLegacyText:(Ljava/lang/String;)Ljava/lang/String;
        //   409: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.setValue:(Ljava/lang/String;)V
        //   412: aload_0        
        //   413: aload           5
        //   415: iconst_0       
        //   416: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteEnchantmentsToClient:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)V
        //   419: aload_0        
        //   420: aload           5
        //   422: iconst_1       
        //   423: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteEnchantmentsToClient:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)V
        //   426: aload_0        
        //   427: aload           5
        //   429: ldc_w           "CanPlaceOn"
        //   432: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteCanPlaceToClient:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Ljava/lang/String;)V
        //   435: aload_0        
        //   436: aload           5
        //   438: ldc_w           "CanDestroy"
        //   441: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteCanPlaceToClient:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Ljava/lang/String;)V
        //   444: aload_1        
        //   445: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int itemIdToRaw(final int n, final Item item, CompoundTag compoundTag) {
        final Optional entityId = SpawnEggRewriter.getEntityId(n);
        if (entityId.isPresent()) {
            if (compoundTag == null) {
                item.setTag(compoundTag = new CompoundTag());
            }
            if (!compoundTag.contains("EntityTag")) {
                final CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.put("id", new StringTag(entityId.get()));
                compoundTag.put("EntityTag", compoundTag2);
            }
            return 25100288;
        }
        return n >> 4 << 16 | (n & 0xF);
    }
    
    private void rewriteCanPlaceToClient(final CompoundTag compoundTag, final String s) {
        if (!(compoundTag.get(s) instanceof ListTag)) {
            return;
        }
        final ListTag listTag = (ListTag)compoundTag.get(s);
        if (listTag == null) {
            return;
        }
        final ListTag listTag2 = new ListTag(StringTag.class);
        compoundTag.put(this.extraNbtTag + "|" + s, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag)));
        for (final Tag tag : listTag) {
            final Object value = tag.getValue();
            final String[] array = (value instanceof String) ? BlockIdData.fallbackReverseMapping.get(((String)value).replace("minecraft:", "")) : null;
            if (array != null) {
                final String[] array2 = array;
                while (0 < array2.length) {
                    listTag2.add(new StringTag(array2[0]));
                    int n = 0;
                    ++n;
                }
            }
            else {
                listTag2.add(tag);
            }
        }
        compoundTag.put(s, listTag2);
    }
    
    private void rewriteEnchantmentsToClient(final CompoundTag compoundTag, final boolean b) {
        final String s = b ? "StoredEnchantments" : "Enchantments";
        final ListTag listTag = (ListTag)compoundTag.get(s);
        if (listTag == null) {
            return;
        }
        final ListTag listTag2 = new ListTag(CompoundTag.class);
        final ListTag listTag3 = new ListTag(CompoundTag.class);
        final ArrayList<StringTag> value = new ArrayList<StringTag>();
        for (final CompoundTag compoundTag2 : listTag.clone()) {
            final Tag value2 = compoundTag2.get("id");
            if (!(value2 instanceof StringTag)) {
                continue;
            }
            final String s2 = (String)value2.getValue();
            final int int1 = ((NumberTag)compoundTag2.get("lvl")).asInt();
            final short n = (short)((int1 < 32767) ? ((short)int1) : 32767);
            final String s3 = this.enchantmentMappings.get(s2);
            if (s3 != null) {
                value.add(new StringTag(s3 + " " + EnchantmentRewriter.getRomanNumber(n)));
                listTag2.add(compoundTag2);
            }
            else {
                if (s2.isEmpty()) {
                    continue;
                }
                Short value3 = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(s2);
                if (value3 == null) {
                    if (!s2.startsWith("viaversion:legacy/")) {
                        listTag2.add(compoundTag2);
                        if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                            String substring = s2;
                            final int n2 = substring.indexOf(58) + 1;
                            if (n2 != 0 && n2 != substring.length()) {
                                substring = substring.substring(n2);
                            }
                            value.add(new StringTag("§7" + Character.toUpperCase(substring.charAt(0)) + substring.substring(1).toLowerCase(Locale.ENGLISH) + " " + EnchantmentRewriter.getRomanNumber(n)));
                        }
                        if (Via.getManager().isDebug()) {
                            ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + s2);
                            continue;
                        }
                        continue;
                    }
                    else {
                        value3 = Short.valueOf(s2.substring(18));
                    }
                }
                if (n != 0) {}
                final CompoundTag compoundTag3 = new CompoundTag();
                compoundTag3.put("id", new ShortTag(value3));
                compoundTag3.put("lvl", new ShortTag(n));
                listTag3.add(compoundTag3);
            }
        }
        if (!b) {}
        if (listTag2.size() != 0) {
            compoundTag.put(this.extraNbtTag + "|" + s, listTag2);
            if (!value.isEmpty()) {
                CompoundTag compoundTag4 = (CompoundTag)compoundTag.get("display");
                if (compoundTag4 == null) {
                    compoundTag.put("display", compoundTag4 = new CompoundTag());
                }
                ListTag listTag4 = (ListTag)compoundTag4.get("Lore");
                if (listTag4 == null) {
                    compoundTag4.put("Lore", listTag4 = new ListTag(StringTag.class));
                    compoundTag.put(this.extraNbtTag + "|DummyLore", new ByteTag());
                }
                else if (listTag4.size() != 0) {
                    final ListTag listTag5 = new ListTag(StringTag.class);
                    final Iterator iterator2 = listTag4.iterator();
                    while (iterator2.hasNext()) {
                        listTag5.add(iterator2.next().clone());
                    }
                    compoundTag.put(this.extraNbtTag + "|OldLore", listTag5);
                    value.addAll((Collection<?>)listTag4.getValue());
                }
                listTag4.setValue(value);
            }
        }
        compoundTag.remove("Enchantments");
        compoundTag.put(b ? s : "ench", listTag3);
    }
    
    @Override
    public Item handleItemToServer(final Item p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: aload_1        
        //     7: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.tag:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //    12: astore_2       
        //    13: aload_1        
        //    14: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //    19: bipush          16
        //    21: ishl           
        //    22: aload_1        
        //    23: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.data:()S
        //    28: ldc_w           65535
        //    31: iand           
        //    32: ior            
        //    33: istore_3       
        //    34: aload_1        
        //    35: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //    40: iconst_4       
        //    41: ishl           
        //    42: aload_1        
        //    43: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.data:()S
        //    48: bipush          15
        //    50: iand           
        //    51: ior            
        //    52: istore          4
        //    54: aload_1        
        //    55: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //    60: if_icmplt       103
        //    63: aload_2        
        //    64: ifnonnull       82
        //    67: aload_1        
        //    68: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //    71: dup            
        //    72: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.<init>:()V
        //    75: dup            
        //    76: astore_2       
        //    77: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setTag:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //    82: aload_2        
        //    83: ldc_w           "Damage"
        //    86: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //    89: dup            
        //    90: aload_1        
        //    91: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.data:()S
        //    96: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //    99: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   102: pop            
        //   103: aload_1        
        //   104: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   109: sipush          358
        //   112: if_icmpne       155
        //   115: aload_2        
        //   116: ifnonnull       134
        //   119: aload_1        
        //   120: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   123: dup            
        //   124: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.<init>:()V
        //   127: dup            
        //   128: astore_2       
        //   129: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setTag:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //   134: aload_2        
        //   135: ldc_w           "map"
        //   138: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   141: dup            
        //   142: aload_1        
        //   143: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.data:()S
        //   148: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //   151: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   154: pop            
        //   155: aload_2        
        //   156: ifnull          400
        //   159: aload_0        
        //   160: aload_1        
        //   161: aload_2        
        //   162: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.invertShieldAndBannerId:(Lcom/viaversion/viaversion/api/minecraft/item/Item;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //   165: aload_2        
        //   166: ldc_w           "display"
        //   169: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   172: astore          5
        //   174: aload           5
        //   176: instanceof      Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   179: ifeq            266
        //   182: aload           5
        //   184: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   187: astore          6
        //   189: aload           6
        //   191: ldc_w           "Name"
        //   194: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   197: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
        //   200: astore          7
        //   202: aload           7
        //   204: ifnull          266
        //   207: aload           6
        //   209: new             Ljava/lang/StringBuilder;
        //   212: dup            
        //   213: invokespecial   java/lang/StringBuilder.<init>:()V
        //   216: aload_0        
        //   217: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.extraNbtTag:Ljava/lang/String;
        //   220: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   223: ldc_w           "|Name"
        //   226: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   229: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   232: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   235: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
        //   238: astore          8
        //   240: aload           7
        //   242: aload           8
        //   244: ifnull          255
        //   247: aload           8
        //   249: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
        //   252: goto            263
        //   255: aload           7
        //   257: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
        //   260: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/ChatRewriter.legacyTextToJsonString:(Ljava/lang/String;)Ljava/lang/String;
        //   263: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.setValue:(Ljava/lang/String;)V
        //   266: aload_0        
        //   267: aload_2        
        //   268: iconst_0       
        //   269: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteEnchantmentsToServer:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)V
        //   272: aload_0        
        //   273: aload_2        
        //   274: iconst_1       
        //   275: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteEnchantmentsToServer:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)V
        //   278: aload_0        
        //   279: aload_2        
        //   280: ldc_w           "CanPlaceOn"
        //   283: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteCanPlaceToServer:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Ljava/lang/String;)V
        //   286: aload_0        
        //   287: aload_2        
        //   288: ldc_w           "CanDestroy"
        //   291: invokespecial   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.rewriteCanPlaceToServer:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Ljava/lang/String;)V
        //   294: aload_1        
        //   295: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   300: sipush          383
        //   303: if_icmpne       384
        //   306: aload_2        
        //   307: ldc_w           "EntityTag"
        //   310: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   313: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   316: astore          6
        //   318: aload           6
        //   320: ifnull          384
        //   323: aload           6
        //   325: ldc_w           "id"
        //   328: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   331: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
        //   334: dup            
        //   335: astore          7
        //   337: ifnull          384
        //   340: aload           7
        //   342: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
        //   345: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/SpawnEggRewriter.getSpawnEggId:(Ljava/lang/String;)I
        //   348: istore          4
        //   350: goto            356
        //   353: goto            384
        //   356: aload           6
        //   358: ldc_w           "id"
        //   361: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   364: pop            
        //   365: aload           6
        //   367: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.isEmpty:()Z
        //   370: ifeq            384
        //   373: aload_2        
        //   374: ldc_w           "EntityTag"
        //   377: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.remove:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   380: pop            
        //   381: goto            384
        //   384: aload_2        
        //   385: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.isEmpty:()Z
        //   388: ifeq            400
        //   391: aload_1        
        //   392: aconst_null    
        //   393: dup            
        //   394: astore_2       
        //   395: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setTag:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //   400: aload_1        
        //   401: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   406: istore          5
        //   408: aload_1        
        //   409: bipush          16
        //   411: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setIdentifier:(I)V
        //   416: aload_0        
        //   417: aload_1        
        //   418: invokespecial   com/viaversion/viabackwards/api/rewriters/ItemRewriter.handleItemToServer:(Lcom/viaversion/viaversion/api/minecraft/item/Item;)Lcom/viaversion/viaversion/api/minecraft/item/Item;
        //   421: pop            
        //   422: aload_1        
        //   423: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   428: bipush          16
        //   430: if_icmpeq       445
        //   433: aload_1        
        //   434: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   439: iconst_m1      
        //   440: if_icmpeq       445
        //   443: aload_1        
        //   444: areturn        
        //   445: aload_1        
        //   446: iload           5
        //   448: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setIdentifier:(I)V
        //   453: aload_0        
        //   454: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.protocol:Lcom/viaversion/viaversion/api/protocol/Protocol;
        //   457: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13;
        //   460: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13.getMappingData:()Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings;
        //   463: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings.getItemMappings:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   466: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.inverse:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   471: bipush          16
        //   473: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.containsKey:(I)Z
        //   478: ifne            665
        //   481: aload_1        
        //   482: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   487: if_icmplt       538
        //   490: aload_1        
        //   491: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   496: sipush          358
        //   499: if_icmpeq       538
        //   502: aload_2        
        //   503: ifnonnull       521
        //   506: aload_1        
        //   507: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
        //   510: dup            
        //   511: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.<init>:()V
        //   514: dup            
        //   515: astore_2       
        //   516: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setTag:(Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
        //   521: aload_2        
        //   522: aload_0        
        //   523: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.extraNbtTag:Ljava/lang/String;
        //   526: new             Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag;
        //   529: dup            
        //   530: iload_3        
        //   531: invokespecial   com/viaversion/viaversion/libs/opennbt/tag/builtin/IntTag.<init>:(I)V
        //   534: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.put:(Ljava/lang/String;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
        //   537: pop            
        //   538: aload_1        
        //   539: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   544: sipush          229
        //   547: if_icmpne       553
        //   550: goto            695
        //   553: aload_1        
        //   554: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   559: bipush          31
        //   561: if_icmpne       576
        //   564: aload_1        
        //   565: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.data:()S
        //   570: ifne            576
        //   573: goto            695
        //   576: aload_0        
        //   577: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.protocol:Lcom/viaversion/viaversion/api/protocol/Protocol;
        //   580: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13;
        //   583: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13.getMappingData:()Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings;
        //   586: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings.getItemMappings:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   589: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.inverse:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   594: bipush          16
        //   596: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.containsKey:(I)Z
        //   601: ifeq            607
        //   604: goto            695
        //   607: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
        //   610: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isSuppressConversionWarnings:()Z
        //   615: ifeq            629
        //   618: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
        //   621: invokeinterface com/viaversion/viaversion/api/ViaManager.isDebug:()Z
        //   626: ifeq            665
        //   629: invokestatic    com/viaversion/viabackwards/ViaBackwards.getPlatform:()Lcom/viaversion/viabackwards/api/ViaBackwardsPlatform;
        //   632: invokeinterface com/viaversion/viabackwards/api/ViaBackwardsPlatform.getLogger:()Ljava/util/logging/Logger;
        //   637: new             Ljava/lang/StringBuilder;
        //   640: dup            
        //   641: invokespecial   java/lang/StringBuilder.<init>:()V
        //   644: ldc_w           "Failed to get 1.13 item for "
        //   647: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   650: aload_1        
        //   651: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.identifier:()I
        //   656: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   659: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   662: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
        //   665: goto            695
        //   668: aload_0        
        //   669: getfield        com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.protocol:Lcom/viaversion/viaversion/api/protocol/Protocol;
        //   672: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13;
        //   675: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/Protocol1_12_2To1_13.getMappingData:()Lcom/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings;
        //   678: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/data/BackwardsMappings.getItemMappings:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   681: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.inverse:()Lcom/viaversion/viaversion/util/Int2IntBiMap;
        //   686: bipush          16
        //   688: invokeinterface com/viaversion/viaversion/util/Int2IntBiMap.get:(I)I
        //   693: istore          6
        //   695: aload_1        
        //   696: sipush          362
        //   699: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setIdentifier:(I)V
        //   704: aload_1        
        //   705: iconst_0       
        //   706: invokeinterface com/viaversion/viaversion/api/minecraft/item/Item.setData:(S)V
        //   711: aload_1        
        //   712: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void rewriteCanPlaceToServer(final CompoundTag compoundTag, final String s) {
        if (!(compoundTag.get(s) instanceof ListTag)) {
            return;
        }
        final ListTag listTag = (ListTag)compoundTag.remove(this.extraNbtTag + "|" + s);
        if (listTag != null) {
            compoundTag.put(s, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag)));
        }
        else {
            final ListTag listTag2;
            if ((listTag2 = (ListTag)compoundTag.get(s)) != null) {
                final ListTag listTag3 = new ListTag(StringTag.class);
                final Iterator iterator = listTag2.iterator();
                while (iterator.hasNext()) {
                    String replace = iterator.next().getValue().toString().replace("minecraft:", "");
                    final String s2 = (String)BlockIdData.numberIdToString.get((int)Ints.tryParse(replace));
                    if (s2 != null) {
                        replace = s2;
                    }
                    final String lowerCase = replace.toLowerCase(Locale.ROOT);
                    final String[] array = BlockIdData.blockIdMapping.get(lowerCase);
                    if (array != null) {
                        final String[] array2 = array;
                        while (0 < array2.length) {
                            listTag3.add(new StringTag(array2[0]));
                            int n = 0;
                            ++n;
                        }
                    }
                    else {
                        listTag3.add(new StringTag(lowerCase));
                    }
                }
                compoundTag.put(s, listTag3);
            }
        }
    }
    
    private void rewriteEnchantmentsToServer(final CompoundTag compoundTag, final boolean b) {
        final String s = b ? "StoredEnchantments" : "Enchantments";
        final ListTag listTag = (ListTag)compoundTag.get(b ? s : "ench");
        if (listTag == null) {
            return;
        }
        final ListTag listTag2 = new ListTag(CompoundTag.class);
        if (!b) {
            final IntTag intTag = (IntTag)compoundTag.remove(this.extraNbtTag + "|OldHideFlags");
            if (intTag != null) {
                compoundTag.put("HideFlags", new IntTag(intTag.asByte()));
            }
            else if (compoundTag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
                compoundTag.remove("HideFlags");
            }
        }
        for (final Tag tag : listTag) {
            final CompoundTag compoundTag2 = new CompoundTag();
            final short short1 = ((NumberTag)((CompoundTag)tag).get("id")).asShort();
            final short short2 = ((NumberTag)((CompoundTag)tag).get("lvl")).asShort();
            if (short1 == 0 && short2 == 0) {
                continue;
            }
            String string = Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(short1);
            if (string == null) {
                string = "viaversion:legacy/" + short1;
            }
            compoundTag2.put("id", new StringTag(string));
            compoundTag2.put("lvl", new ShortTag(short2));
            listTag2.add(compoundTag2);
        }
        final ListTag listTag3 = (ListTag)compoundTag.remove(this.extraNbtTag + "|Enchantments");
        if (listTag3 != null) {
            final Iterator iterator2 = listTag3.iterator();
            while (iterator2.hasNext()) {
                listTag2.add(iterator2.next());
            }
        }
        CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("display");
        if (compoundTag3 == null) {
            compoundTag.put("display", compoundTag3 = new CompoundTag());
        }
        final ListTag listTag4 = (ListTag)compoundTag.remove(this.extraNbtTag + "|OldLore");
        if (listTag4 != null) {
            ListTag listTag5 = (ListTag)compoundTag3.get("Lore");
            if (listTag5 == null) {
                compoundTag.put("Lore", listTag5 = new ListTag());
            }
            listTag5.setValue(listTag4.getValue());
        }
        else if (compoundTag.remove(this.extraNbtTag + "|DummyLore") != null) {
            compoundTag3.remove("Lore");
            if (compoundTag3.isEmpty()) {
                compoundTag.remove("display");
            }
        }
        if (!b) {
            compoundTag.remove("ench");
        }
        compoundTag.put(s, listTag2);
    }
    
    private void invertShieldAndBannerId(final Item item, final CompoundTag compoundTag) {
        if (item.identifier() != 442 && item.identifier() != 425) {
            return;
        }
        final Tag value = compoundTag.get("BlockEntityTag");
        if (!(value instanceof CompoundTag)) {
            return;
        }
        final CompoundTag compoundTag2 = (CompoundTag)value;
        final Tag value2 = compoundTag2.get("Base");
        if (value2 instanceof IntTag) {
            final IntTag intTag = (IntTag)value2;
            intTag.setValue(15 - intTag.asInt());
        }
        final Tag value3 = compoundTag2.get("Patterns");
        if (value3 instanceof ListTag) {
            for (final Tag tag : (ListTag)value3) {
                if (!(tag instanceof CompoundTag)) {
                    continue;
                }
                final IntTag intTag2 = (IntTag)((CompoundTag)tag).get("Color");
                intTag2.setValue(15 - intTag2.asInt());
            }
        }
    }
    
    private static void flowerPotSpecialTreatment(final UserConnection userConnection, final int n, final Position position) throws Exception {
        if (FlowerPotHandler.isFlowah(n)) {
            final CompoundTag transform = ((BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class)).transform(userConnection, position, "minecraft:flower_pot");
            final PacketWrapper create = PacketWrapper.create(11, null, userConnection);
            create.write(Type.POSITION, position);
            create.write(Type.VAR_INT, 0);
            create.scheduleSend(Protocol1_12_2To1_13.class);
            final PacketWrapper create2 = PacketWrapper.create(11, null, userConnection);
            create2.write(Type.POSITION, position);
            create2.write(Type.VAR_INT, Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n));
            create2.scheduleSend(Protocol1_12_2To1_13.class);
            final PacketWrapper create3 = PacketWrapper.create(9, null, userConnection);
            create3.write(Type.POSITION, position);
            create3.write(Type.UNSIGNED_BYTE, 5);
            create3.write(Type.NBT, transform);
            create3.scheduleSend(Protocol1_12_2To1_13.class);
        }
    }
    
    static Protocol access$000(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static Protocol access$100(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static void access$200(final UserConnection userConnection, final int n, final Position position) throws Exception {
        flowerPotSpecialTreatment(userConnection, n, position);
    }
    
    static Protocol access$300(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static Protocol access$400(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static Protocol access$500(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static Protocol access$600(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
    
    static Protocol access$700(final BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
}
