package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public final class WorldPackets
{
    public static void register(final Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14);
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int id = BlockEntityIds.newId((short)packetWrapper.read(Type.UNSIGNED_BYTE));
                packetWrapper.write(Type.VAR_INT, id);
                WorldPackets.access$000(id, (CompoundTag)packetWrapper.passthrough(Type.NBT));
            }
        });
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.UPDATE_LIGHT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                final int intValue2 = (int)packetWrapper.passthrough(Type.VAR_INT);
                if (((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).isLoaded(intValue, intValue2)) {
                    if (!Via.getConfig().cache1_17Light()) {
                        return;
                    }
                }
                else {
                    packetWrapper.cancel();
                }
                final boolean booleanValue = (boolean)packetWrapper.passthrough(Type.BOOLEAN);
                final long[] array = (long[])packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                final long[] array2 = (long[])packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                final long[] array3 = (long[])packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                final long[] array4 = (long[])packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                final int intValue3 = (int)packetWrapper.passthrough(Type.VAR_INT);
                final byte[][] array5 = new byte[intValue3][];
                while (0 < intValue3) {
                    array5[0] = (byte[])packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    int intValue4 = 0;
                    ++intValue4;
                }
                int intValue4 = (int)packetWrapper.passthrough(Type.VAR_INT);
                final byte[][] array6 = new byte[0][];
                while (0 < 0) {
                    array6[0] = (byte[])packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    int n = 0;
                    ++n;
                }
                ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).storeLight(intValue, intValue2, new ChunkLightStorage.ChunkLight(booleanValue, array, array2, array3, array4, array5, array6));
            }
        });
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.CHUNK_DATA, new PacketRemapper(protocol1_18To1_17_1) {
            final Protocol1_18To1_17_1 val$protocol;
            
            @Override
            public void registerMap() {
                this.handler(WorldPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final Protocol1_18To1_17_1 p0, final PacketWrapper p1) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/Protocol1_18To1_17_1.getEntityRewriter:()Lcom/viaversion/viaversion/rewriter/EntityRewriter;
                //     4: aload_1        
                //     5: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    10: invokevirtual   com/viaversion/viaversion/rewriter/EntityRewriter.tracker:(Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/data/entity/EntityTracker;
                //    13: astore_2       
                //    14: aload_1        
                //    15: new             Lcom/viaversion/viaversion/protocols/protocol1_17to1_16_4/types/Chunk1_17Type;
                //    18: dup            
                //    19: aload_2        
                //    20: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.currentWorldSectionHeight:()I
                //    25: invokespecial   com/viaversion/viaversion/protocols/protocol1_17to1_16_4/types/Chunk1_17Type.<init>:(I)V
                //    28: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    33: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                //    36: astore_3       
                //    37: new             Ljava/util/ArrayList;
                //    40: dup            
                //    41: aload_3        
                //    42: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //    47: invokeinterface java/util/List.size:()I
                //    52: invokespecial   java/util/ArrayList.<init>:(I)V
                //    55: astore          4
                //    57: aload_3        
                //    58: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                //    63: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //    68: astore          5
                //    70: aload           5
                //    72: invokeinterface java/util/Iterator.hasNext:()Z
                //    77: ifeq            284
                //    80: aload           5
                //    82: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //    87: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //    90: astore          6
                //    92: aload           6
                //    94: ldc             "x"
                //    96: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //    99: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   102: astore          7
                //   104: aload           6
                //   106: ldc             "y"
                //   108: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   111: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   114: astore          8
                //   116: aload           6
                //   118: ldc             "z"
                //   120: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   123: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                //   126: astore          9
                //   128: aload           6
                //   130: ldc             "id"
                //   132: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                //   135: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
                //   138: astore          10
                //   140: aload           7
                //   142: ifnull          70
                //   145: aload           8
                //   147: ifnull          70
                //   150: aload           9
                //   152: ifnull          70
                //   155: aload           10
                //   157: ifnonnull       163
                //   160: goto            70
                //   163: aload           10
                //   165: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
                //   168: astore          11
                //   170: aload_0        
                //   171: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/Protocol1_18To1_17_1.getMappingData:()Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/data/MappingData;
                //   174: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/data/MappingData.blockEntityIds:()Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntMap;
                //   177: aload           11
                //   179: ldc             "minecraft:"
                //   181: ldc             ""
                //   183: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
                //   186: invokeinterface com/viaversion/viaversion/libs/fastutil/objects/Object2IntMap.getInt:(Ljava/lang/Object;)I
                //   191: istore          12
                //   193: iconst_0       
                //   194: iconst_m1      
                //   195: if_icmpne       229
                //   198: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
                //   201: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
                //   206: new             Ljava/lang/StringBuilder;
                //   209: dup            
                //   210: invokespecial   java/lang/StringBuilder.<init>:()V
                //   213: ldc             "Unknown block entity: "
                //   215: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   218: aload           11
                //   220: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   223: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   226: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
                //   229: iconst_0       
                //   230: aload           6
                //   232: invokestatic    com/viaversion/viaversion/protocols/protocol1_18to1_17_1/packets/WorldPackets.access$000:(ILcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
                //   235: aload           7
                //   237: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   240: bipush          15
                //   242: iand           
                //   243: iconst_4       
                //   244: ishl           
                //   245: aload           9
                //   247: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                //   250: bipush          15
                //   252: iand           
                //   253: ior            
                //   254: i2b            
                //   255: istore          13
                //   257: aload           4
                //   259: new             Lcom/viaversion/viaversion/api/minecraft/blockentity/BlockEntityImpl;
                //   262: dup            
                //   263: iconst_0       
                //   264: aload           8
                //   266: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asShort:()S
                //   269: iconst_0       
                //   270: aload           6
                //   272: invokespecial   com/viaversion/viaversion/api/minecraft/blockentity/BlockEntityImpl.<init>:(BSILcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;)V
                //   275: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
                //   280: pop            
                //   281: goto            70
                //   284: aload_3        
                //   285: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                //   290: astore          5
                //   292: aload_3        
                //   293: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                //   298: astore          6
                //   300: iconst_0       
                //   301: aload           6
                //   303: arraylength    
                //   304: if_icmpge       437
                //   307: aload           6
                //   309: iconst_0       
                //   310: aaload         
                //   311: astore          8
                //   313: aload           8
                //   315: ifnonnull       371
                //   318: new             Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSectionImpl;
                //   321: dup            
                //   322: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/ChunkSectionImpl.<init>:()V
                //   325: astore          8
                //   327: aload           6
                //   329: iconst_0       
                //   330: aload           8
                //   332: aastore        
                //   333: aload           8
                //   335: iconst_0       
                //   336: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setNonAirBlocksCount:(I)V
                //   341: new             Lcom/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl;
                //   344: dup            
                //   345: sipush          4096
                //   348: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl.<init>:(I)V
                //   351: astore          9
                //   353: aload           9
                //   355: iconst_0       
                //   356: invokevirtual   com/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl.addId:(I)V
                //   359: aload           8
                //   361: getstatic       com/viaversion/viaversion/api/minecraft/chunks/PaletteType.BLOCKS:Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;
                //   364: aload           9
                //   366: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.addPalette:(Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;Lcom/viaversion/viaversion/api/minecraft/chunks/DataPalette;)V
                //   371: new             Lcom/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl;
                //   374: dup            
                //   375: bipush          64
                //   377: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl.<init>:(I)V
                //   380: astore          9
                //   382: aload           8
                //   384: getstatic       com/viaversion/viaversion/api/minecraft/chunks/PaletteType.BIOMES:Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;
                //   387: aload           9
                //   389: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.addPalette:(Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;Lcom/viaversion/viaversion/api/minecraft/chunks/DataPalette;)V
                //   394: iconst_0       
                //   395: bipush          64
                //   397: if_icmpge       431
                //   400: aload           5
                //   402: iconst_0       
                //   403: iaload         
                //   404: istore          13
                //   406: aload           9
                //   408: iconst_0       
                //   409: iconst_0       
                //   410: iconst_m1      
                //   411: if_icmpeq       418
                //   414: iconst_0       
                //   415: goto            419
                //   418: iconst_0       
                //   419: invokevirtual   com/viaversion/viaversion/api/minecraft/chunks/DataPaletteImpl.setIdAt:(II)V
                //   422: iinc            11, 1
                //   425: iinc            12, 1
                //   428: goto            394
                //   431: iinc            7, 1
                //   434: goto            300
                //   437: new             Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk1_18;
                //   440: dup            
                //   441: aload_3        
                //   442: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   447: aload_3        
                //   448: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   453: aload           6
                //   455: aload_3        
                //   456: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getHeightMap:()Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                //   461: aload           4
                //   463: invokespecial   com/viaversion/viaversion/api/minecraft/chunks/Chunk1_18.<init>:(II[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Ljava/util/List;)V
                //   466: astore          7
                //   468: aload_1        
                //   469: new             Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/types/Chunk1_18Type;
                //   472: dup            
                //   473: aload_2        
                //   474: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.currentWorldSectionHeight:()I
                //   479: aload_0        
                //   480: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/Protocol1_18To1_17_1.getMappingData:()Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/data/MappingData;
                //   483: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/data/MappingData.getBlockStateMappings:()Lcom/viaversion/viaversion/api/data/Mappings;
                //   486: invokeinterface com/viaversion/viaversion/api/data/Mappings.mappedSize:()I
                //   491: invokestatic    com/viaversion/viaversion/util/MathUtil.ceilLog2:(I)I
                //   494: aload_2        
                //   495: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.biomesSent:()I
                //   500: invokestatic    com/viaversion/viaversion/util/MathUtil.ceilLog2:(I)I
                //   503: invokespecial   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/types/Chunk1_18Type.<init>:(III)V
                //   506: aload           7
                //   508: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   513: aload_1        
                //   514: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   519: ldc             Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage;.class
                //   521: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //   526: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage;
                //   529: astore          8
                //   531: aload           8
                //   533: aload           7
                //   535: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   540: aload           7
                //   542: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   547: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage.addLoadedChunk:(II)Z
                //   550: ifne            557
                //   553: iconst_1       
                //   554: goto            558
                //   557: iconst_0       
                //   558: istore          9
                //   560: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                //   563: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.cache1_17Light:()Z
                //   568: ifeq            593
                //   571: aload           8
                //   573: aload           7
                //   575: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   580: aload           7
                //   582: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   587: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage.getLight:(II)Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight;
                //   590: goto            612
                //   593: aload           8
                //   595: aload           7
                //   597: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   602: aload           7
                //   604: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   609: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage.removeLight:(II)Lcom/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight;
                //   612: astore          10
                //   614: aload           10
                //   616: ifnonnull       800
                //   619: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
                //   622: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
                //   627: new             Ljava/lang/StringBuilder;
                //   630: dup            
                //   631: invokespecial   java/lang/StringBuilder.<init>:()V
                //   634: ldc_w           "No light data found for chunk at "
                //   637: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   640: aload           7
                //   642: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                //   647: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   650: ldc_w           ", "
                //   653: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   656: aload           7
                //   658: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                //   663: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   666: ldc_w           ". Chunk was already loaded: "
                //   669: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   672: iload           9
                //   674: invokevirtual   java/lang/StringBuilder.append:(Z)Ljava/lang/StringBuilder;
                //   677: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   680: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
                //   683: new             Ljava/util/BitSet;
                //   686: dup            
                //   687: invokespecial   java/util/BitSet.<init>:()V
                //   690: astore          11
                //   692: aload           11
                //   694: iconst_0       
                //   695: aload_2        
                //   696: invokeinterface com/viaversion/viaversion/api/data/entity/EntityTracker.currentWorldSectionHeight:()I
                //   701: iconst_2       
                //   702: iadd           
                //   703: invokevirtual   java/util/BitSet.set:(II)V
                //   706: aload_1        
                //   707: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   710: iconst_0       
                //   711: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
                //   714: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   719: aload_1        
                //   720: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   723: iconst_0       
                //   724: newarray        J
                //   726: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   731: aload_1        
                //   732: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   735: iconst_0       
                //   736: newarray        J
                //   738: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   743: aload_1        
                //   744: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   747: aload           11
                //   749: invokevirtual   java/util/BitSet.toLongArray:()[J
                //   752: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   757: aload_1        
                //   758: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   761: aload           11
                //   763: invokevirtual   java/util/BitSet.toLongArray:()[J
                //   766: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   771: aload_1        
                //   772: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   775: iconst_0       
                //   776: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   779: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   784: aload_1        
                //   785: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   788: iconst_0       
                //   789: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   792: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   797: goto            989
                //   800: aload_1        
                //   801: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   804: aload           10
                //   806: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.trustEdges:()Z
                //   809: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
                //   812: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   817: aload_1        
                //   818: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   821: aload           10
                //   823: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.skyLightMask:()[J
                //   826: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   831: aload_1        
                //   832: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   835: aload           10
                //   837: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.blockLightMask:()[J
                //   840: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   845: aload_1        
                //   846: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   849: aload           10
                //   851: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.emptySkyLightMask:()[J
                //   854: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   859: aload_1        
                //   860: getstatic       com/viaversion/viaversion/api/type/Type.LONG_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   863: aload           10
                //   865: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.emptyBlockLightMask:()[J
                //   868: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   873: aload_1        
                //   874: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   877: aload           10
                //   879: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.skyLight:()[[B
                //   882: arraylength    
                //   883: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   886: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   891: aload           10
                //   893: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.skyLight:()[[B
                //   896: astore          11
                //   898: aload           11
                //   900: arraylength    
                //   901: istore          12
                //   903: iconst_0       
                //   904: iconst_0       
                //   905: if_icmpge       931
                //   908: aload           11
                //   910: iconst_0       
                //   911: aaload         
                //   912: astore          14
                //   914: aload_1        
                //   915: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   918: aload           14
                //   920: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   925: iinc            13, 1
                //   928: goto            903
                //   931: aload_1        
                //   932: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   935: aload           10
                //   937: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.blockLight:()[[B
                //   940: arraylength    
                //   941: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   944: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   949: aload           10
                //   951: invokevirtual   com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.blockLight:()[[B
                //   954: astore          11
                //   956: aload           11
                //   958: arraylength    
                //   959: istore          12
                //   961: iconst_0       
                //   962: iconst_0       
                //   963: if_icmpge       989
                //   966: aload           11
                //   968: iconst_0       
                //   969: aaload         
                //   970: astore          14
                //   972: aload_1        
                //   973: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   976: aload           14
                //   978: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   983: iinc            13, 1
                //   986: goto            961
                //   989: return         
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
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.UNLOAD_CHUNK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$4::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((ChunkLightStorage)packetWrapper.user().get(ChunkLightStorage.class)).clear((int)packetWrapper.passthrough(Type.INT), (int)packetWrapper.passthrough(Type.INT));
            }
        });
    }
    
    private static void handleSpawners(final int n, final CompoundTag compoundTag) {
        if (n == 8) {
            final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("SpawnData");
            if (compoundTag2 != null) {
                final CompoundTag compoundTag3 = new CompoundTag();
                compoundTag.put("SpawnData", compoundTag3);
                compoundTag3.put("entity", compoundTag2);
            }
        }
    }
    
    static void access$000(final int n, final CompoundTag compoundTag) {
        handleSpawners(n, compoundTag);
    }
}
