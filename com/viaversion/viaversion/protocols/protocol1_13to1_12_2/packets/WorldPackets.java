package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import com.viaversion.viaversion.api.type.types.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class WorldPackets
{
    private static final IntSet VALID_BIOMES;
    
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_12_1.SPAWN_PAINTING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(new PacketHandler() {
                    final WorldPackets$1 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final PaintingProvider paintingProvider = (PaintingProvider)Via.getManager().getProviders().get(PaintingProvider.class);
                        final String s = (String)packetWrapper.read(Type.STRING);
                        final Optional intByIdentifier = paintingProvider.getIntByIdentifier(s);
                        if (!intByIdentifier.isPresent() && (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())) {
                            Via.getPlatform().getLogger().warning("Could not find painting motive: " + s + " falling back to default (0)");
                        }
                        packetWrapper.write(Type.VAR_INT, intByIdentifier.orElse(0));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler() {
                    final WorldPackets$2 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final int transform = ((BlockEntityProvider)Via.getManager().getProviders().get(BlockEntityProvider.class)).transform(packetWrapper.user(), position, (CompoundTag)packetWrapper.get(Type.NBT, 0), true);
                        if (transform != -1) {
                            final BlockStorage.ReplacementData value = ((BlockStorage)packetWrapper.user().get(BlockStorage.class)).get(position);
                            if (value != null) {
                                value.setReplacement(transform);
                            }
                        }
                        if (shortValue == 5) {
                            packetWrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$3 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                        final short shortValue2 = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 1);
                        (int)packetWrapper.get(Type.VAR_INT, 0);
                        if (472 != 25) {
                            if (472 != 33) {
                                if (472 != 29) {
                                    if (472 != 54) {
                                        if (472 != 146) {
                                            if (472 != 130) {
                                                if (472 != 138) {
                                                    if (472 != 52) {
                                                        if (472 != 209) {
                                                            if (472 < 219 || 472 <= 234) {}
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (472 == 73) {
                            final PacketWrapper create = packetWrapper.create(11);
                            create.write(Type.POSITION, position);
                            create.write(Type.VAR_INT, 249 + shortValue * 24 * 2 + shortValue2 * 2);
                            create.send(Protocol1_13To1_12_2.class);
                        }
                        packetWrapper.set(Type.VAR_INT, 0, 472);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$4 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final Position position = (Position)packetWrapper.get(Type.POSITION, 0);
                        int n = WorldPackets.toNewId((int)packetWrapper.get(Type.VAR_INT, 0));
                        final UserConnection user = packetWrapper.user();
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.updateBlockStorage(user, position.x(), position.y(), position.z(), n);
                            n = ConnectionData.connect(user, position, n);
                        }
                        packetWrapper.set(Type.VAR_INT, 0, WorldPackets.access$000(packetWrapper.user(), position, n));
                        if (Via.getConfig().isServersideBlockConnections()) {
                            packetWrapper.send(Protocol1_13To1_12_2.class);
                            packetWrapper.cancel();
                            ConnectionData.update(user, position);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler() {
                    final WorldPackets$5 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                        final UserConnection user = packetWrapper.user();
                        final BlockChangeRecord[] array2;
                        final BlockChangeRecord[] array = array2 = (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        int n = 0;
                        while (0 < array2.length) {
                            final BlockChangeRecord blockChangeRecord = array2[0];
                            final int newId = WorldPackets.toNewId(blockChangeRecord.getBlockId());
                            final Position position = new Position(blockChangeRecord.getSectionX() + intValue * 16, blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + intValue2 * 16);
                            if (Via.getConfig().isServersideBlockConnections()) {
                                ConnectionData.updateBlockStorage(user, position.x(), position.y(), position.z(), newId);
                            }
                            blockChangeRecord.setBlockId(WorldPackets.access$000(packetWrapper.user(), position, newId));
                            ++n;
                        }
                        if (Via.getConfig().isServersideBlockConnections()) {
                            final BlockChangeRecord[] array3 = array;
                            while (0 < array3.length) {
                                final BlockChangeRecord blockChangeRecord2 = array3[0];
                                final int blockId = blockChangeRecord2.getBlockId();
                                final Position position2 = new Position(blockChangeRecord2.getSectionX() + intValue * 16, blockChangeRecord2.getY(), blockChangeRecord2.getSectionZ() + intValue2 * 16);
                                final ConnectionHandler connectionHandler = ConnectionData.getConnectionHandler(blockId);
                                if (connectionHandler != null) {
                                    blockChangeRecord2.setBlockId(connectionHandler.connect(user, position2, blockId));
                                }
                                ++n;
                            }
                            packetWrapper.send(Protocol1_13To1_12_2.class);
                            packetWrapper.cancel();
                            final BlockChangeRecord[] array4 = array;
                            while (0 < array4.length) {
                                final BlockChangeRecord blockChangeRecord3 = array4[0];
                                ConnectionData.update(user, new Position(blockChangeRecord3.getSectionX() + intValue * 16, blockChangeRecord3.getY(), blockChangeRecord3.getSectionZ() + intValue2 * 16));
                                ++n;
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.EXPLOSION, new PacketRemapper() {
            @Override
            public void registerMap() {
                if (!Via.getConfig().isServersideBlockConnections()) {
                    return;
                }
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$6 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final UserConnection user = packetWrapper.user();
                        final int n = (int)Math.floor((float)packetWrapper.get(Type.FLOAT, 0));
                        final int n2 = (int)Math.floor((float)packetWrapper.get(Type.FLOAT, 1));
                        final int n3 = (int)Math.floor((float)packetWrapper.get(Type.FLOAT, 2));
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        final Position[] array = new Position[intValue];
                        int n4 = 0;
                        while (0 < intValue) {
                            final Position position = new Position(n + (byte)packetWrapper.passthrough(Type.BYTE), (short)(n2 + (byte)packetWrapper.passthrough(Type.BYTE)), n3 + (byte)packetWrapper.passthrough(Type.BYTE));
                            array[0] = position;
                            ConnectionData.updateBlockStorage(user, position.x(), position.y(), position.z(), 0);
                            ++n4;
                        }
                        packetWrapper.send(Protocol1_13To1_12_2.class);
                        packetWrapper.cancel();
                        while (0 < intValue) {
                            ConnectionData.update(user, array[0]);
                            ++n4;
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.UNLOAD_CHUNK, new PacketRemapper() {
            @Override
            public void registerMap() {
                if (Via.getConfig().isServersideBlockConnections()) {
                    this.handler(new PacketHandler() {
                        final WorldPackets$7 this$0;
                        
                        @Override
                        public void handle(final PacketWrapper packetWrapper) throws Exception {
                            ConnectionData.blockConnectionProvider.unloadChunk(packetWrapper.user(), (int)packetWrapper.passthrough(Type.INT), (int)packetWrapper.passthrough(Type.INT));
                        }
                    });
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.NAMED_SOUND, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler() {
                    final WorldPackets$8 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.set(Type.STRING, 0, NamedSoundRewriter.getNewId(((String)packetWrapper.get(Type.STRING, 0)).replace("minecraft:", "")));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(new PacketHandler() {
                    final WorldPackets$9 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper p0) throws Exception {
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
                        //    17: aload_1        
                        //    18: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //    23: ldc             Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage;.class
                        //    25: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                        //    30: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage;
                        //    33: astore_3       
                        //    34: new             Lcom/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type;
                        //    37: dup            
                        //    38: aload_2        
                        //    39: invokespecial   com/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type.<init>:(Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
                        //    42: astore          4
                        //    44: new             Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type;
                        //    47: dup            
                        //    48: aload_2        
                        //    49: invokespecial   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type.<init>:(Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
                        //    52: astore          5
                        //    54: aload_1        
                        //    55: aload           4
                        //    57: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                        //    62: checkcast       Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
                        //    65: astore          6
                        //    67: aload_1        
                        //    68: aload           5
                        //    70: aload           6
                        //    72: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                        //    77: ldc             -2147483648
                        //    79: aload           6
                        //    81: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //    86: arraylength    
                        //    87: if_icmpge       445
                        //    90: aload           6
                        //    92: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //    97: ldc             -2147483648
                        //    99: aaload         
                        //   100: astore          8
                        //   102: aload           8
                        //   104: ifnonnull       110
                        //   107: goto            439
                        //   110: iconst_0       
                        //   111: aload           8
                        //   113: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                        //   118: if_icmpge       152
                        //   121: aload           8
                        //   123: iconst_0       
                        //   124: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                        //   129: istore          10
                        //   131: iconst_1       
                        //   132: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/WorldPackets.toNewId:(I)I
                        //   135: istore          11
                        //   137: aload           8
                        //   139: iconst_0       
                        //   140: iconst_0       
                        //   141: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setPaletteEntry:(II)V
                        //   146: iinc            9, 1
                        //   149: goto            110
                        //   152: iconst_1       
                        //   153: aload           8
                        //   155: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                        //   160: if_icmpge       190
                        //   163: aload           8
                        //   165: iconst_1       
                        //   166: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                        //   171: istore          11
                        //   173: aload_3        
                        //   174: iconst_0       
                        //   175: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage.isWelcome:(I)Z
                        //   178: ifeq            184
                        //   181: goto            190
                        //   184: iinc            10, 1
                        //   187: goto            152
                        //   190: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                        //   193: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isServersideBlockConnections:()Z
                        //   198: ifeq            244
                        //   201: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.needStoreBlocks:()Z
                        //   204: ifeq            244
                        //   207: iconst_0       
                        //   208: aload           8
                        //   210: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteSize:()I
                        //   215: if_icmpge       244
                        //   218: aload           8
                        //   220: iconst_0       
                        //   221: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getPaletteEntry:(I)I
                        //   226: istore          12
                        //   228: iconst_0       
                        //   229: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.isWelcome:(I)Z
                        //   232: ifeq            238
                        //   235: goto            244
                        //   238: iinc            11, 1
                        //   241: goto            207
                        //   244: iconst_0       
                        //   245: ifeq            342
                        //   248: iconst_0       
                        //   249: bipush          16
                        //   251: if_icmpge       342
                        //   254: iconst_0       
                        //   255: bipush          16
                        //   257: if_icmpge       336
                        //   260: iconst_0       
                        //   261: bipush          16
                        //   263: if_icmpge       330
                        //   266: aload           8
                        //   268: iconst_0       
                        //   269: iconst_0       
                        //   270: iconst_0       
                        //   271: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
                        //   276: istore          14
                        //   278: aload_3        
                        //   279: iload           14
                        //   281: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage.isWelcome:(I)Z
                        //   284: ifeq            324
                        //   287: aload_3        
                        //   288: new             Lcom/viaversion/viaversion/api/minecraft/Position;
                        //   291: dup            
                        //   292: iconst_0       
                        //   293: aload           6
                        //   295: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                        //   300: iconst_4       
                        //   301: ishl           
                        //   302: iadd           
                        //   303: iconst_0       
                        //   304: i2s            
                        //   305: iconst_0       
                        //   306: aload           6
                        //   308: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                        //   313: iconst_4       
                        //   314: ishl           
                        //   315: iadd           
                        //   316: invokespecial   com/viaversion/viaversion/api/minecraft/Position.<init>:(ISI)V
                        //   319: iload           14
                        //   321: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage.store:(Lcom/viaversion/viaversion/api/minecraft/Position;I)V
                        //   324: iinc            13, 1
                        //   327: goto            260
                        //   330: iinc            12, 1
                        //   333: goto            254
                        //   336: iinc            11, 1
                        //   339: goto            248
                        //   342: iconst_1       
                        //   343: ifeq            439
                        //   346: iconst_0       
                        //   347: bipush          16
                        //   349: if_icmpge       439
                        //   352: iconst_0       
                        //   353: bipush          16
                        //   355: if_icmpge       433
                        //   358: iconst_0       
                        //   359: bipush          16
                        //   361: if_icmpge       427
                        //   364: aload           8
                        //   366: iconst_0       
                        //   367: iconst_0       
                        //   368: iconst_0       
                        //   369: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.getFlatBlock:(III)I
                        //   374: istore          14
                        //   376: iload           14
                        //   378: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.isWelcome:(I)Z
                        //   381: ifeq            421
                        //   384: getstatic       com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.blockConnectionProvider:Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/providers/BlockConnectionProvider;
                        //   387: aload_1        
                        //   388: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //   393: iconst_0       
                        //   394: aload           6
                        //   396: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                        //   401: iconst_4       
                        //   402: ishl           
                        //   403: iadd           
                        //   404: iconst_0       
                        //   405: iconst_0       
                        //   406: aload           6
                        //   408: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                        //   413: iconst_4       
                        //   414: ishl           
                        //   415: iadd           
                        //   416: iload           14
                        //   418: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/providers/BlockConnectionProvider.storeBlock:(Lcom/viaversion/viaversion/api/connection/UserConnection;IIII)V
                        //   421: iinc            13, 1
                        //   424: goto            358
                        //   427: iinc            12, 1
                        //   430: goto            352
                        //   433: iinc            11, 1
                        //   436: goto            346
                        //   439: iinc            7, 1
                        //   442: goto            77
                        //   445: aload           6
                        //   447: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.isBiomeData:()Z
                        //   452: ifeq            566
                        //   455: iconst_0       
                        //   456: sipush          256
                        //   459: if_icmpge       566
                        //   462: aload           6
                        //   464: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                        //   469: iconst_0       
                        //   470: iaload         
                        //   471: istore          9
                        //   473: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/WorldPackets.access$100:()Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
                        //   476: iconst_0       
                        //   477: invokeinterface com/viaversion/viaversion/libs/fastutil/ints/IntSet.contains:(I)Z
                        //   482: ifne            560
                        //   485: iconst_0       
                        //   486: sipush          255
                        //   489: if_icmpeq       550
                        //   492: ldc             -2147483648
                        //   494: iconst_0       
                        //   495: if_icmpeq       550
                        //   498: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                        //   501: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isSuppressConversionWarnings:()Z
                        //   506: ifeq            520
                        //   509: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
                        //   512: invokeinterface com/viaversion/viaversion/api/ViaManager.isDebug:()Z
                        //   517: ifeq            550
                        //   520: invokestatic    com/viaversion/viaversion/api/Via.getPlatform:()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
                        //   523: invokeinterface com/viaversion/viaversion/api/platform/ViaPlatform.getLogger:()Ljava/util/logging/Logger;
                        //   528: new             Ljava/lang/StringBuilder;
                        //   531: dup            
                        //   532: invokespecial   java/lang/StringBuilder.<init>:()V
                        //   535: ldc             "Received invalid biome id "
                        //   537: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        //   540: iconst_0       
                        //   541: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                        //   544: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                        //   547: invokevirtual   java/util/logging/Logger.warning:(Ljava/lang/String;)V
                        //   550: aload           6
                        //   552: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBiomeData:()[I
                        //   557: iconst_0       
                        //   558: iconst_1       
                        //   559: iastore        
                        //   560: iinc            8, 1
                        //   563: goto            455
                        //   566: invokestatic    com/viaversion/viaversion/api/Via.getManager:()Lcom/viaversion/viaversion/api/ViaManager;
                        //   569: invokeinterface com/viaversion/viaversion/api/ViaManager.getProviders:()Lcom/viaversion/viaversion/api/platform/providers/ViaProviders;
                        //   574: ldc             Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider;.class
                        //   576: invokevirtual   com/viaversion/viaversion/api/platform/providers/ViaProviders.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/platform/providers/Provider;
                        //   579: checkcast       Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider;
                        //   582: astore          7
                        //   584: aload           6
                        //   586: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getBlockEntities:()Ljava/util/List;
                        //   591: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                        //   596: astore          8
                        //   598: aload           8
                        //   600: invokeinterface java/util/Iterator.hasNext:()Z
                        //   605: ifeq            797
                        //   608: aload           8
                        //   610: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                        //   615: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
                        //   618: astore          9
                        //   620: aload           7
                        //   622: aload_1        
                        //   623: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //   628: aconst_null    
                        //   629: aload           9
                        //   631: iconst_0       
                        //   632: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider.transform:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)I
                        //   635: istore          10
                        //   637: iconst_1       
                        //   638: iconst_m1      
                        //   639: if_icmpeq       737
                        //   642: aload           9
                        //   644: ldc             "x"
                        //   646: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                        //   649: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                        //   652: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                        //   655: istore          11
                        //   657: aload           9
                        //   659: ldc             "y"
                        //   661: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                        //   664: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                        //   667: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                        //   670: istore          12
                        //   672: aload           9
                        //   674: ldc             "z"
                        //   676: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                        //   679: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag;
                        //   682: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag.asInt:()I
                        //   685: istore          13
                        //   687: new             Lcom/viaversion/viaversion/api/minecraft/Position;
                        //   690: dup            
                        //   691: iconst_0       
                        //   692: iconst_0       
                        //   693: i2s            
                        //   694: iconst_0       
                        //   695: invokespecial   com/viaversion/viaversion/api/minecraft/Position.<init>:(ISI)V
                        //   698: astore          14
                        //   700: aload_3        
                        //   701: aload           14
                        //   703: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage.get:(Lcom/viaversion/viaversion/api/minecraft/Position;)Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData;
                        //   706: astore          15
                        //   708: aload           15
                        //   710: ifnull          719
                        //   713: aload           15
                        //   715: iconst_1       
                        //   716: invokevirtual   com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData.setReplacement:(I)V
                        //   719: aload           6
                        //   721: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //   726: iconst_0       
                        //   727: aaload         
                        //   728: iconst_0       
                        //   729: iconst_0       
                        //   730: iconst_0       
                        //   731: iconst_1       
                        //   732: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/ChunkSection.setFlatBlock:(IIII)V
                        //   737: aload           9
                        //   739: ldc_w           "id"
                        //   742: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
                        //   745: astore          11
                        //   747: aload           11
                        //   749: instanceof      Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
                        //   752: ifeq            794
                        //   755: aload           11
                        //   757: checkcast       Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag;
                        //   760: invokevirtual   com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag.getValue:()Ljava/lang/String;
                        //   763: astore          12
                        //   765: aload           12
                        //   767: ldc_w           "minecraft:noteblock"
                        //   770: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                        //   773: ifne            787
                        //   776: aload           12
                        //   778: ldc_w           "minecraft:flower_pot"
                        //   781: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
                        //   784: ifeq            794
                        //   787: aload           8
                        //   789: invokeinterface java/util/Iterator.remove:()V
                        //   794: goto            598
                        //   797: invokestatic    com/viaversion/viaversion/api/Via.getConfig:()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
                        //   800: invokeinterface com/viaversion/viaversion/api/configuration/ViaVersionConfig.isServersideBlockConnections:()Z
                        //   805: ifeq            895
                        //   808: aload_1        
                        //   809: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //   814: aload           6
                        //   816: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.connectBlocks:(Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;)V
                        //   819: aload_1        
                        //   820: ldc_w           Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2;.class
                        //   823: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.send:(Ljava/lang/Class;)V
                        //   828: aload_1        
                        //   829: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                        //   834: iconst_0       
                        //   835: aload           6
                        //   837: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //   842: arraylength    
                        //   843: if_icmpge       895
                        //   846: aload           6
                        //   848: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getSections:()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
                        //   853: iconst_0       
                        //   854: aaload         
                        //   855: astore          10
                        //   857: aload           10
                        //   859: ifnonnull       865
                        //   862: goto            889
                        //   865: aload_1        
                        //   866: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                        //   871: aload           6
                        //   873: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getX:()I
                        //   878: aload           6
                        //   880: invokeinterface com/viaversion/viaversion/api/minecraft/chunks/Chunk.getZ:()I
                        //   885: iconst_0       
                        //   886: invokestatic    com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.updateChunkSectionNeighbours:(Lcom/viaversion/viaversion/api/connection/UserConnection;III)V
                        //   889: iinc            9, 1
                        //   892: goto            834
                        //   895: return         
                        //    Exceptions:
                        //  throws java.lang.Exception
                        // 
                        // The error that occurred was:
                        // 
                        // java.lang.UnsupportedOperationException
                        //     at java.util.Collections$1.remove(Unknown Source)
                        //     at java.util.AbstractCollection.removeAll(Unknown Source)
                        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2968)
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
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.SPAWN_PARTICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(new PacketHandler() {
                    final WorldPackets$10 this$0;
                    
                    @Override
                    public void handle(final PacketWrapper packetWrapper) throws Exception {
                        final int intValue = (int)packetWrapper.get(Type.INT, 0);
                        if (intValue != 37 && intValue != 38 && intValue != 46) {
                            if (intValue == 36) {}
                        }
                        final Integer[] array = new Integer[2];
                        while (0 < array.length) {
                            array[0] = (Integer)packetWrapper.read(Type.VAR_INT);
                            int n = 0;
                            ++n;
                        }
                        final Particle rewriteParticle = ParticleRewriter.rewriteParticle(intValue, array);
                        if (rewriteParticle == null || rewriteParticle.getId() == -1) {
                            packetWrapper.cancel();
                            return;
                        }
                        if (rewriteParticle.getId() == 11) {
                            final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                            final float floatValue = (float)packetWrapper.get(Type.FLOAT, 6);
                            if (intValue2 == 0) {
                                packetWrapper.set(Type.INT, 1, 1);
                                packetWrapper.set(Type.FLOAT, 6, 0.0f);
                                final List arguments = rewriteParticle.getArguments();
                                while (true) {
                                    float n2 = (float)packetWrapper.get(Type.FLOAT, 3) * floatValue;
                                    if (n2 == 0.0f) {
                                        n2 = 1.0f;
                                    }
                                    arguments.get(0).setValue(n2);
                                    packetWrapper.set(Type.FLOAT, 3, 0.0f);
                                    int n3 = 0;
                                    ++n3;
                                }
                            }
                        }
                        packetWrapper.set(Type.INT, 0, rewriteParticle.getId());
                        for (final Particle.ParticleData particleData : rewriteParticle.getArguments()) {
                            packetWrapper.write(particleData.getType(), particleData.getValue());
                        }
                    }
                });
            }
        });
    }
    
    public static int toNewId(final int n) {
        if (0 < 0) {}
        final int newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(0);
        if (newId != -1) {
            return newId;
        }
        final int newId2 = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(0);
        if (newId2 != -1) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Missing block " + 0);
            }
            return newId2;
        }
        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Missing block completely " + 0);
        }
        return 1;
    }
    
    private static int checkStorage(final UserConnection userConnection, final Position position, final int n) {
        final BlockStorage blockStorage = (BlockStorage)userConnection.get(BlockStorage.class);
        if (blockStorage.contains(position)) {
            final BlockStorage.ReplacementData value = blockStorage.get(position);
            if (value.getOriginal() == n) {
                if (value.getReplacement() != -1) {
                    return value.getReplacement();
                }
            }
            else {
                blockStorage.remove(position);
                if (blockStorage.isWelcome(n)) {
                    blockStorage.store(position, n);
                }
            }
        }
        else if (blockStorage.isWelcome(n)) {
            blockStorage.store(position, n);
        }
        return n;
    }
    
    static int access$000(final UserConnection userConnection, final Position position, final int n) {
        return checkStorage(userConnection, position, n);
    }
    
    static IntSet access$100() {
        return WorldPackets.VALID_BIOMES;
    }
    
    static {
        VALID_BIOMES = new IntOpenHashSet(70, 0.99f);
        int n = 0;
        while (160 < 50) {
            WorldPackets.VALID_BIOMES.add(160);
            ++n;
        }
        WorldPackets.VALID_BIOMES.add(127);
        while (160 <= 134) {
            WorldPackets.VALID_BIOMES.add(160);
            ++n;
        }
        WorldPackets.VALID_BIOMES.add(140);
        WorldPackets.VALID_BIOMES.add(149);
        WorldPackets.VALID_BIOMES.add(151);
        while (160 <= 158) {
            WorldPackets.VALID_BIOMES.add(160);
            ++n;
        }
        while (160 <= 167) {
            WorldPackets.VALID_BIOMES.add(160);
            ++n;
        }
    }
}
