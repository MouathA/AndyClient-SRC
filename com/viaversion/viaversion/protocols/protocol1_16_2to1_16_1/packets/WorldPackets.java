package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.*;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public class WorldPackets
{
    private static final BlockChangeRecord[] EMPTY_RECORDS;
    
    public static void register(final Protocol protocol) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound(ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper(protocol) {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol protocol, final PacketWrapper packetWrapper) throws Exception {
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_16Type());
                packetWrapper.write(new Chunk1_16_2Type(), chunk);
                while (0 < chunk.getSections().length) {
                    final ChunkSection chunkSection = chunk.getSections()[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, protocol.getMappingData().getNewBlockStateId(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_16.MULTI_BLOCK_CHANGE, new PacketRemapper(protocol) {
            final Protocol val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(WorldPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol p0, final PacketWrapper p1) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                //     6: aload_1        
                //     7: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //    10: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    15: checkcast       Ljava/lang/Integer;
                //    18: invokevirtual   java/lang/Integer.intValue:()I
                //    21: istore_2       
                //    22: aload_1        
                //    23: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //    26: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    31: checkcast       Ljava/lang/Integer;
                //    34: invokevirtual   java/lang/Integer.intValue:()I
                //    37: istore_3       
                //    38: lconst_0       
                //    39: lstore          4
                //    41: lload           4
                //    43: iload_2        
                //    44: i2l            
                //    45: ldc2_w          4194303
                //    48: land           
                //    49: bipush          42
                //    51: lshl           
                //    52: lor            
                //    53: lstore          4
                //    55: lload           4
                //    57: iload_3        
                //    58: i2l            
                //    59: ldc2_w          4194303
                //    62: land           
                //    63: bipush          20
                //    65: lshl           
                //    66: lor            
                //    67: lstore          4
                //    69: bipush          16
                //    71: anewarray       Ljava/util/List;
                //    74: astore          6
                //    76: aload_1        
                //    77: getstatic       com/viaversion/viaversion/api/type/Type.BLOCK_CHANGE_RECORD_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //    80: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    85: checkcast       [Lcom/viaversion/viaversion/api/minecraft/BlockChangeRecord;
                //    88: astore          7
                //    90: aload           7
                //    92: astore          8
                //    94: aload           8
                //    96: arraylength    
                //    97: istore          9
                //    99: iconst_0       
                //   100: iload           9
                //   102: if_icmpge       213
                //   105: aload           8
                //   107: iconst_0       
                //   108: aaload         
                //   109: astore          11
                //   111: aload           11
                //   113: invokeinterface com/viaversion/viaversion/api/minecraft/BlockChangeRecord.getY:()S
                //   118: iconst_4       
                //   119: ishr           
                //   120: istore          12
                //   122: aload           6
                //   124: iload           12
                //   126: aaload         
                //   127: astore          13
                //   129: aload           13
                //   131: ifnonnull       149
                //   134: aload           6
                //   136: iload           12
                //   138: new             Ljava/util/ArrayList;
                //   141: dup            
                //   142: invokespecial   java/util/ArrayList.<init>:()V
                //   145: dup            
                //   146: astore          13
                //   148: aastore        
                //   149: aload_0        
                //   150: invokeinterface com/viaversion/viaversion/api/protocol/Protocol.getMappingData:()Lcom/viaversion/viaversion/api/data/MappingData;
                //   155: aload           11
                //   157: invokeinterface com/viaversion/viaversion/api/minecraft/BlockChangeRecord.getBlockId:()I
                //   162: invokeinterface com/viaversion/viaversion/api/data/MappingData.getNewBlockStateId:(I)I
                //   167: istore          14
                //   169: aload           13
                //   171: new             Lcom/viaversion/viaversion/api/minecraft/BlockChangeRecord1_16_2;
                //   174: dup            
                //   175: aload           11
                //   177: invokeinterface com/viaversion/viaversion/api/minecraft/BlockChangeRecord.getSectionX:()B
                //   182: aload           11
                //   184: invokeinterface com/viaversion/viaversion/api/minecraft/BlockChangeRecord.getSectionY:()B
                //   189: aload           11
                //   191: invokeinterface com/viaversion/viaversion/api/minecraft/BlockChangeRecord.getSectionZ:()B
                //   196: iload           14
                //   198: invokespecial   com/viaversion/viaversion/api/minecraft/BlockChangeRecord1_16_2.<init>:(BBBI)V
                //   201: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //   206: pop            
                //   207: iinc            10, 1
                //   210: goto            99
                //   213: iconst_0       
                //   214: aload           6
                //   216: arraylength    
                //   217: if_icmpge       316
                //   220: aload           6
                //   222: iconst_0       
                //   223: aaload         
                //   224: astore          9
                //   226: aload           9
                //   228: ifnonnull       234
                //   231: goto            310
                //   234: aload_1        
                //   235: getstatic       com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE:Lcom/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/ClientboundPackets1_16_2;
                //   238: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(Lcom/viaversion/viaversion/api/protocol/packet/PacketType;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   243: astore          10
                //   245: aload           10
                //   247: getstatic       com/viaversion/viaversion/api/type/Type.LONG:Lcom/viaversion/viaversion/api/type/types/LongType;
                //   250: lload           4
                //   252: iconst_0       
                //   253: i2l            
                //   254: ldc2_w          1048575
                //   257: land           
                //   258: lor            
                //   259: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
                //   262: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   267: aload           10
                //   269: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   272: iconst_0       
                //   273: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
                //   276: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   281: aload           10
                //   283: getstatic       com/viaversion/viaversion/api/type/Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY:Lcom/viaversion/viaversion/api/type/Type;
                //   286: aload           9
                //   288: invokestatic    com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/packets/WorldPackets.access$000:()[Lcom/viaversion/viaversion/api/minecraft/BlockChangeRecord;
                //   291: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
                //   296: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   301: aload           10
                //   303: ldc             Lcom/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/Protocol1_16_2To1_16_1;.class
                //   305: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.send:(Ljava/lang/Class;)V
                //   310: iinc            8, 1
                //   313: goto            213
                //   316: return         
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
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
    }
    
    static BlockChangeRecord[] access$000() {
        return WorldPackets.EMPTY_RECORDS;
    }
    
    static {
        EMPTY_RECORDS = new BlockChangeRecord[0];
    }
}
