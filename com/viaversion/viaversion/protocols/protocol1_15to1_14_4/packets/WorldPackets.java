package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class WorldPackets
{
    public static void register(final Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        final BlockRewriter blockRewriter = new BlockRewriter(protocol1_15To1_14_4, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_14.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_14.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14.CHUNK_DATA, new PacketRemapper(protocol1_15To1_14_4) {
            final Protocol1_15To1_14_4 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper p0) throws Exception {
                        // 
                        // This method could not be decompiled.
                        // 
                        // Original Bytecode:
                        // 
                        //     1: new             Lcom/viaversion/viaversion/protocols/protocol1_14to1_13_2/types/Chunk1_14Type;
                        //     4: dup            
                        //     5: invokespecial   com/viaversion/viaversion/protocols/protocol1_14to1_13_2/types/Chunk1_14Type.<init>:()V
                        //     8: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    13: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                        //    16: astore_2       
                        //    17: aload_1        
                        //    18: new             Lcom/viaversion/viaversion/protocols/protocol1_15to1_14_4/types/Chunk1_15Type;
                        //    21: dup            
                        //    22: invokespecial   com/viaversion/viaversion/protocols/protocol1_15to1_14_4/types/Chunk1_15Type.<init>:()V
                        //    25: aload_2        
                        //    26: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //    31: aload_2        
                        //    32: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.isFullChunk:()Z
                        //    37: ifeq            119
                        //    40: aload_2        
                        //    41: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                        //    46: astore_3       
                        //    47: sipush          1024
                        //    50: newarray        I
                        //    52: astore          4
                        //    54: aload_3        
                        //    55: ifnull          111
                        //    58: iconst_0       
                        //    59: iconst_4       
                        //    60: if_icmpge       88
                        //    63: iconst_0       
                        //    64: iconst_4       
                        //    65: if_icmpge       82
                        //    68: aload           4
                        //    70: iconst_0       
                        //    71: aload_3        
                        //    72: bipush          34
                        //    74: iaload         
                        //    75: iastore        
                        //    76: iinc            6, 1
                        //    79: goto            63
                        //    82: iinc            5, 1
                        //    85: goto            58
                        //    88: iconst_0       
                        //    89: bipush          64
                        //    91: if_icmpge       111
                        //    94: aload           4
                        //    96: iconst_0       
                        //    97: aload           4
                        //    99: iconst_0       
                        //   100: bipush          16
                        //   102: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
                        //   105: iinc            5, 1
                        //   108: goto            88
                        //   111: aload_2        
                        //   112: aload           4
                        //   114: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.setBiomeData:([I)V
                        //   119: iconst_0       
                        //   120: aload_2        
                        //   121: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //   126: arraylength    
                        //   127: if_icmpge       206
                        //   130: aload_2        
                        //   131: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //   136: iconst_0       
                        //   137: aaload         
                        //   138: astore          4
                        //   140: aload           4
                        //   142: ifnonnull       148
                        //   145: goto            200
                        //   148: iconst_0       
                        //   149: aload           4
                        //   151: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                        //   156: if_icmpge       200
                        //   159: aload           4
                        //   161: iconst_0       
                        //   162: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                        //   167: istore          6
                        //   169: aload_0        
                        //   170: getfield        com/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/WorldPackets$1$1.this$0:Lcom/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/WorldPackets$1;
                        //   173: getfield        com/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/WorldPackets$1.val$protocol:Lcom/viaversion/viaversion/protocols/protocol1_15to1_14_4/Protocol1_15To1_14_4;
                        //   176: invokevirtual   com/viaversion/viaversion/protocols/protocol1_15to1_14_4/Protocol1_15To1_14_4.getMappingData:()Lcom/viaversion/viaversion/protocols/protocol1_15to1_14_4/data/MappingData;
                        //   179: iconst_0       
                        //   180: invokevirtual   com/viaversion/viaversion/protocols/protocol1_15to1_14_4/data/MappingData.getNewBlockStateId:(I)I
                        //   183: istore          7
                        //   185: aload           4
                        //   187: iconst_0       
                        //   188: iconst_2       
                        //   189: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                        //   194: iinc            5, 1
                        //   197: goto            148
                        //   200: iinc            3, 1
                        //   203: goto            119
                        //   206: return         
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
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_14.EFFECT, 1010, 2001);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14.SPAWN_PARTICLE, new PacketRemapper(protocol1_15To1_14_4) {
            final Protocol1_15To1_14_4 val$protocol;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT, Type.DOUBLE);
                this.map(Type.FLOAT, Type.DOUBLE);
                this.map(Type.FLOAT, Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        if (intValue == 3 || intValue == 23) {
                            packetWrapper.set(Type.VAR_INT, 0, this.this$0.val$protocol.getMappingData().getNewBlockStateId((int)packetWrapper.passthrough(Type.VAR_INT)));
                        }
                        else if (intValue == 32) {
                            this.this$0.val$protocol.getItemRewriter().handleItemToClient((Item)packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                    }
                });
            }
        });
    }
}
