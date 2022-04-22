package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.*;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.*;
import java.util.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.protocol.*;

public final class BlockItemPackets1_17 extends ItemRewriter
{
    public BlockItemPackets1_17(final Protocol1_16_4To1_17 protocol1_16_4To1_17) {
        super(protocol1_16_4To1_17);
    }
    
    @Override
    protected void registerPackets() {
        final BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_17.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_17.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
        blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
        this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.CLICK_WINDOW, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT, Type.NOTHING);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 0);
                this.this$0.handleItemToServer((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.WINDOW_CONFIRMATION, null, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(BlockItemPackets1_17$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                if (!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                    return;
                }
                final short shortValue = (short)packetWrapper.read(Type.UNSIGNED_BYTE);
                final short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                if (shortValue == 0 && booleanValue && ((PingRequests)packetWrapper.user().get(PingRequests.class)).removeId(shortValue2)) {
                    final PacketWrapper create = packetWrapper.create(ServerboundPackets1_17.PONG);
                    create.write(Type.INT, (int)shortValue2);
                    create.sendToServer(Protocol1_16_4To1_17.class);
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.SPAWN_PARTICLE, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(BlockItemPackets1_17$4::lambda$registerMap$0);
                this.handler(this.this$0.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                if (intValue == 16) {
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                }
                else if (intValue == 37) {
                    packetWrapper.set(Type.INT, 0, -1);
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.UPDATE_LIGHT, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void writeLightArrays(final PacketWrapper p0, final BitSet p1, final int p2, final int p3, final int p4) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //     4: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //     9: pop            
                //    10: new             Ljava/util/ArrayList;
                //    13: dup            
                //    14: invokespecial   java/util/ArrayList.<init>:()V
                //    17: astore          6
                //    19: iconst_0       
                //    20: iload           4
                //    22: if_icmpge       49
                //    25: aload_2        
                //    26: iconst_0       
                //    27: invokevirtual   java/util/BitSet.get:(I)Z
                //    30: ifeq            43
                //    33: aload_1        
                //    34: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //    37: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    42: pop            
                //    43: iinc            7, 1
                //    46: goto            19
                //    49: aload_0        
                //    50: iload_3        
                //    51: goto            74
                //    54: aload           6
                //    56: aload_1        
                //    57: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //    60: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    65: checkcast       [B
                //    68: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //    73: pop            
                //    74: iinc            7, 1
                //    77: goto            49
                //    80: iload           4
                //    82: bipush          18
                //    84: iadd           
                //    85: istore          7
                //    87: iconst_0       
                //    88: iload           5
                //    90: iconst_2       
                //    91: iadd           
                //    92: if_icmpge       119
                //    95: aload_2        
                //    96: iconst_0       
                //    97: invokevirtual   java/util/BitSet.get:(I)Z
                //   100: ifeq            113
                //   103: aload_1        
                //   104: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   107: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   112: pop            
                //   113: iinc            7, 1
                //   116: goto            87
                //   119: aload           6
                //   121: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //   126: astore          7
                //   128: aload           7
                //   130: invokeinterface java/util/Iterator.hasNext:()Z
                //   135: ifeq            164
                //   138: aload           7
                //   140: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   145: checkcast       [B
                //   148: astore          8
                //   150: aload_1        
                //   151: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   154: aload           8
                //   156: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   161: goto            128
                //   164: return         
                //    Exceptions:
                //  throws java.lang.Exception
                // 
                // The error that occurred was:
                // 
                // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0077).
                //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
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
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = packetWrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                final int max = Math.max(0, -(entityTracker.currentMinY() >> 4));
                final long[] array = (long[])packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                final long[] array2 = (long[])packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                final int access$000 = BlockItemPackets1_17.access$000(this.this$0, array, max);
                final int access$2 = BlockItemPackets1_17.access$000(this.this$0, array2, max);
                packetWrapper.write(Type.VAR_INT, access$000);
                packetWrapper.write(Type.VAR_INT, access$2);
                final long[] array3 = (long[])packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                final long[] array4 = (long[])packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                packetWrapper.write(Type.VAR_INT, BlockItemPackets1_17.access$000(this.this$0, array3, max));
                packetWrapper.write(Type.VAR_INT, BlockItemPackets1_17.access$000(this.this$0, array4, max));
                this.writeLightArrays(packetWrapper, BitSet.valueOf(array), access$000, max, entityTracker.currentWorldSectionHeight());
                this.writeLightArrays(packetWrapper, BitSet.valueOf(array2), access$2, max, entityTracker.currentWorldSectionHeight());
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int n = (int)((long)packetWrapper.get(Type.LONG, 0) << 44 >> 44);
                if (n < 0 || n > 15) {
                    packetWrapper.cancel();
                    return;
                }
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                while (0 < array.length) {
                    final BlockChangeRecord blockChangeRecord = array[0];
                    blockChangeRecord.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.access$100(this.this$0)).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_CHANGE, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int y = ((Position)packetWrapper.get(Type.POSITION1_14, 0)).getY();
                if (y < 0 || y > 255) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_16_4To1_17)BlockItemPackets1_17.access$200(this.this$0)).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.CHUNK_DATA, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
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
                //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //     6: ldc             Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/Protocol1_16_4To1_17;.class
                //     8: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.getEntityTracker:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/data/entity/EntityTracker;
                //    13: astore_2       
                //    14: aload_2        
                //    15: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.currentWorldSectionHeight:()I
                //    20: istore_3       
                //    21: aload_1        
                //    22: new             Lcom/viaversion/viaversion/protocols/protocol1_17to1_16_4/types/Chunk1_17Type;
                //    25: dup            
                //    26: iload_3        
                //    27: invokespecial   com/viaversion/viaversion/protocols/protocol1_17to1_16_4/types/Chunk1_17Type.<init>:(I)V
                //    30: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    35: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                //    38: astore          4
                //    40: aload_1        
                //    41: new             Lcom/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/types/Chunk1_16_2Type;
                //    44: dup            
                //    45: invokespecial   com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/types/Chunk1_16_2Type.<init>:()V
                //    48: aload           4
                //    50: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //    55: iconst_0       
                //    56: aload_2        
                //    57: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.currentMinY:()I
                //    62: iconst_4       
                //    63: ishr           
                //    64: ineg           
                //    65: invokestatic    java/lang/Math.max:(II)I
                //    68: istore          5
                //    70: aload           4
                //    72: aload           4
                //    74: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //    79: iload           5
                //    81: bipush          64
                //    83: imul           
                //    84: iload           5
                //    86: bipush          64
                //    88: imul           
                //    89: sipush          1024
                //    92: iadd           
                //    93: invokestatic    java/util/Arrays.copyOfRange:([III)[I
                //    96: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.setBiomeData:([I)V
                //   101: aload           4
                //   103: aload_0        
                //   104: getfield        com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17$8.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17;
                //   107: aload           4
                //   109: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getChunkMask:()Ljava/util/BitSet;
                //   114: iload           5
                //   116: iconst_0       
                //   117: invokestatic    com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17.access$300:(Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17;Ljava/util/BitSet;IZ)I
                //   120: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.setBitmask:(I)V
                //   125: aload           4
                //   127: aconst_null    
                //   128: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.setChunkMask:(Ljava/util/BitSet;)V
                //   133: aload           4
                //   135: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   140: iload           5
                //   142: iload           5
                //   144: bipush          16
                //   146: iadd           
                //   147: invokestatic    java/util/Arrays.copyOfRange:([Ljava/lang/Object;II)[Ljava/lang/Object;
                //   150: checkcast       [Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   153: astore          6
                //   155: aload           4
                //   157: aload           6
                //   159: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.setSections:([Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;)V
                //   164: aload           4
                //   166: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getHeightMap:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   171: astore          7
                //   173: aload           7
                //   175: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.values:()Ljava/util/Collection;
                //   178: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
                //   183: astore          8
                //   185: aload           8
                //   187: invokeinterface java/util/Iterator.hasNext:()Z
                //   192: ifeq            275
                //   195: aload           8
                //   197: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   202: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   205: astore          9
                //   207: aload           9
                //   209: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag;
                //   212: astore          10
                //   214: sipush          256
                //   217: newarray        I
                //   219: astore          11
                //   221: iload_3        
                //   222: iconst_4       
                //   223: ishl           
                //   224: iconst_1       
                //   225: iadd           
                //   226: invokestatic    com/viaversion/viaversion/util/MathUtil.ceilLog2:(I)I
                //   229: istore          12
                //   231: iload           12
                //   233: aload           11
                //   235: arraylength    
                //   236: aload           10
                //   238: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag.getValue:()[J
                //   241: aload           11
                //   243: aload_2        
                //   244: invokedynamic   BootstrapMethod #1, consume:([ILcom/viaversion/viaversion/api/data/entity/EntityTracker;)Lcom/viaversion/viaversion/util/BiIntConsumer;
                //   249: invokestatic    com/viaversion/viaversion/util/CompactArrayUtil.iterateCompactArrayWithPadding:(II[JLcom/viaversion/viaversion/util/BiIntConsumer;)V
                //   252: aload           10
                //   254: bipush          9
                //   256: aload           11
                //   258: arraylength    
                //   259: aload           11
                //   261: invokedynamic   BootstrapMethod #2, applyAsLong:([I)Ljava/util/function/IntToLongFunction;
                //   266: invokestatic    com/viaversion/viaversion/util/CompactArrayUtil.createCompactArrayWithPadding:(IILjava/util/function/IntToLongFunction;)[J
                //   269: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/LongArrayTag.setValue:([J)V
                //   272: goto            185
                //   275: aload           6
                //   277: iconst_0       
                //   278: aaload         
                //   279: astore          9
                //   281: aload           9
                //   283: ifnonnull       289
                //   286: goto            342
                //   289: iconst_0       
                //   290: aload           9
                //   292: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                //   297: if_icmpge       342
                //   300: aload           9
                //   302: iconst_0       
                //   303: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                //   308: istore          11
                //   310: aload           9
                //   312: iconst_0       
                //   313: aload_0        
                //   314: getfield        com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17$8.this$0:Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17;
                //   317: invokestatic    com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17.access$400:(Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17;)Lcom/viaversion/viaversion/api/protocol/Protocol;
                //   320: checkcast       Lcom/viaversion/viabackwards/protocol/protocol1_16_4to1_17/Protocol1_16_4To1_17;
                //   323: invokevirtual   com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/Protocol1_16_4To1_17.getMappingData:()Lcom/viaversion/viabackwards/api/data/BackwardsMappings;
                //   326: iload           11
                //   328: invokevirtual   com/viaversion/viabackwards/api/data/BackwardsMappings.getNewBlockStateId:(I)I
                //   331: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                //   336: iinc            10, 1
                //   339: goto            289
                //   342: iinc            8, 1
                //   345: goto            275
                //   348: return         
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
            
            private static void lambda$registerMap$0(final int[] array, final EntityTracker entityTracker, final int n, final int n2) {
                array[n] = MathUtil.clamp(n2 + entityTracker.currentMinY(), 0, 255);
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.handler(BlockItemPackets1_17$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int y = ((Position)packetWrapper.passthrough(Type.POSITION1_14)).getY();
                if (y < 0 || y > 255) {
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(BlockItemPackets1_17$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int y = ((Position)packetWrapper.passthrough(Type.POSITION1_14)).getY();
                if (y < 0 || y > 255) {
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.MAP_DATA, new PacketRemapper() {
            final BlockItemPackets1_17 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(BlockItemPackets1_17$11::lambda$registerMap$0);
                this.map(Type.BOOLEAN);
                this.handler(BlockItemPackets1_17$11::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if (!(boolean)packetWrapper.read(Type.BOOLEAN)) {
                    packetWrapper.write(Type.VAR_INT, 0);
                }
                else {
                    MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(packetWrapper);
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
    }
    
    private int cutLightMask(final long[] array, final int n) {
        if (array.length == 0) {
            return 0;
        }
        return this.cutMask(BitSet.valueOf(array), n, true);
    }
    
    private int cutMask(final BitSet set, final int n, final boolean b) {
        int n3 = 0;
        for (int n2 = n + (b ? 18 : 16), i = n; i < n2; ++i, ++n3) {
            if (set.get(i)) {}
        }
        return 0;
    }
    
    static int access$000(final BlockItemPackets1_17 blockItemPackets1_17, final long[] array, final int n) {
        return blockItemPackets1_17.cutLightMask(array, n);
    }
    
    static Protocol access$100(final BlockItemPackets1_17 blockItemPackets1_17) {
        return blockItemPackets1_17.protocol;
    }
    
    static Protocol access$200(final BlockItemPackets1_17 blockItemPackets1_17) {
        return blockItemPackets1_17.protocol;
    }
    
    static int access$300(final BlockItemPackets1_17 blockItemPackets1_17, final BitSet set, final int n, final boolean b) {
        return blockItemPackets1_17.cutMask(set, n, b);
    }
    
    static Protocol access$400(final BlockItemPackets1_17 blockItemPackets1_17) {
        return blockItemPackets1_17.protocol;
    }
}
