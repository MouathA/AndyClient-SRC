package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.minecraft.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.util.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import de.gerrygames.viarewind.types.*;

public class WorldPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final ClientWorld clientWorld = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                final Chunk chunk = (Chunk)packetWrapper.read(new Chunk1_8Type(clientWorld));
                packetWrapper.write(new Chunk1_7_10Type(clientWorld), chunk);
                final ChunkSection[] sections = chunk.getSections();
                while (0 < sections.length) {
                    final ChunkSection chunkSection = sections[0];
                    if (chunkSection != null) {
                        while (0 < chunkSection.getPaletteSize()) {
                            chunkSection.setPaletteEntry(0, ReplacementRegistry1_7_6_10to1_8.replace(chunkSection.getPaletteEntry(0)));
                            int n = 0;
                            ++n;
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.MULTI_BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(WorldPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
                packetWrapper.write(Type.SHORT, (short)array.length);
                packetWrapper.write(Type.INT, array.length * 4);
                final BlockChangeRecord[] array2 = array;
                while (0 < array2.length) {
                    final BlockChangeRecord blockChangeRecord = array2[0];
                    packetWrapper.write(Type.SHORT, (short)(blockChangeRecord.getSectionX() << 12 | blockChangeRecord.getSectionZ() << 8 | blockChangeRecord.getY()));
                    packetWrapper.write(Type.SHORT, (short)ReplacementRegistry1_7_6_10to1_8.replace(blockChangeRecord.getBlockId()));
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.BLOCK_CHANGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$3::lambda$registerMap$0);
                this.handler(WorldPackets$3::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                int id = intValue >> 4;
                int replaceData = intValue & 0xF;
                final Replacement replacement = ReplacementRegistry1_7_6_10to1_8.getReplacement(id, replaceData);
                if (replacement != null) {
                    id = replacement.getId();
                    replaceData = replacement.replaceData(replaceData);
                }
                packetWrapper.write(Type.VAR_INT, id);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)replaceData);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.BLOCK_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$4::lambda$registerMap$0);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.SHORT, (short)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(WorldPackets$5::lambda$registerMap$0);
                this.map(Type.BYTE);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.INT, position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(ChunkPacketTransformer::transformChunkBulk);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(WorldPackets$7::lambda$registerMap$0);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.BYTE, (byte)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PARTICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$8::lambda$registerMap$0);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(WorldPackets$8::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                String string = (String)packetWrapper.get(Type.STRING, 0);
                Particle particle = Particle.find(string);
                if (particle == Particle.ICON_CRACK || particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST) {
                    final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                    final int n = (int)((particle == Particle.ICON_CRACK) ? packetWrapper.read(Type.VAR_INT) : 0);
                    if ((intValue >= 256 && intValue <= 422) || (intValue >= 2256 && intValue <= 2267)) {
                        particle = Particle.ICON_CRACK;
                    }
                    else {
                        if ((intValue < 0 || intValue > 164) && (intValue < 170 || intValue > 175)) {
                            packetWrapper.cancel();
                            return;
                        }
                        if (particle == Particle.ICON_CRACK) {
                            particle = Particle.BLOCK_CRACK;
                        }
                    }
                    string = particle.name + "_" + intValue + "_" + n;
                }
                packetWrapper.set(Type.STRING, 0, string);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                Particle particle = Particle.find((int)packetWrapper.read(Type.INT));
                if (particle == null) {
                    particle = Particle.CRIT;
                }
                packetWrapper.write(Type.STRING, particle.name);
                packetWrapper.read(Type.BOOLEAN);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$9::lambda$registerMap$0);
                this.handler(WorldPackets$9::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                while (0 < 4) {
                    String s = ChatUtil.removeUnusedColor(ChatUtil.jsonToLegacy((String)packetWrapper.read(Type.STRING)), '0');
                    if (s.length() > 15) {
                        s = ChatColorUtil.stripColor(s);
                        if (s.length() > 15) {
                            s = s.substring(0, 15);
                        }
                    }
                    packetWrapper.write(Type.STRING, s);
                    int n = 0;
                    ++n;
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.SHORT, (short)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.MAP_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                //     6: aload_0        
                //     7: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    10: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    15: checkcast       Ljava/lang/Integer;
                //    18: invokevirtual   java/lang/Integer.intValue:()I
                //    21: istore_1       
                //    22: aload_0        
                //    23: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //    26: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    31: checkcast       Ljava/lang/Byte;
                //    34: invokevirtual   java/lang/Byte.byteValue:()B
                //    37: istore_2       
                //    38: aload_0        
                //    39: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    42: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    47: checkcast       Ljava/lang/Integer;
                //    50: invokevirtual   java/lang/Integer.intValue:()I
                //    53: istore_3       
                //    54: iload_3        
                //    55: iconst_4       
                //    56: imul           
                //    57: newarray        B
                //    59: astore          4
                //    61: iconst_0       
                //    62: iload_3        
                //    63: if_icmpge       149
                //    66: aload_0        
                //    67: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //    70: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    75: checkcast       Ljava/lang/Byte;
                //    78: invokevirtual   java/lang/Byte.byteValue:()B
                //    81: istore          6
                //    83: aload           4
                //    85: iconst_0       
                //    86: iload           6
                //    88: iconst_4       
                //    89: ishr           
                //    90: bipush          15
                //    92: iand           
                //    93: i2b            
                //    94: bastore        
                //    95: aload           4
                //    97: iconst_1       
                //    98: aload_0        
                //    99: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   102: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   107: checkcast       Ljava/lang/Byte;
                //   110: invokevirtual   java/lang/Byte.byteValue:()B
                //   113: bastore        
                //   114: aload           4
                //   116: iconst_2       
                //   117: aload_0        
                //   118: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   121: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   126: checkcast       Ljava/lang/Byte;
                //   129: invokevirtual   java/lang/Byte.byteValue:()B
                //   132: bastore        
                //   133: aload           4
                //   135: iconst_3       
                //   136: iload           6
                //   138: bipush          15
                //   140: iand           
                //   141: i2b            
                //   142: bastore        
                //   143: iinc            5, 1
                //   146: goto            61
                //   149: aload_0        
                //   150: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //   153: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   158: checkcast       Ljava/lang/Short;
                //   161: invokevirtual   java/lang/Short.shortValue:()S
                //   164: istore          5
                //   166: iconst_0       
                //   167: ifle            369
                //   170: aload_0        
                //   171: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //   174: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   179: checkcast       Ljava/lang/Short;
                //   182: invokevirtual   java/lang/Short.shortValue:()S
                //   185: istore          6
                //   187: aload_0        
                //   188: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //   191: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   196: checkcast       Ljava/lang/Short;
                //   199: invokevirtual   java/lang/Short.shortValue:()S
                //   202: istore          7
                //   204: aload_0        
                //   205: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //   208: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   213: checkcast       Ljava/lang/Short;
                //   216: invokevirtual   java/lang/Short.shortValue:()S
                //   219: istore          8
                //   221: aload_0        
                //   222: getstatic       com/viaversion/viaversion/api/type/Type.BYTE_ARRAY_PRIMITIVE:Lcom/viaversion/viaversion/api/type/Type;
                //   225: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //   230: checkcast       [B
                //   233: astore          9
                //   235: iconst_0       
                //   236: iconst_0       
                //   237: if_icmpge       369
                //   240: iload           6
                //   242: iconst_3       
                //   243: iadd           
                //   244: newarray        B
                //   246: astore          11
                //   248: aload           11
                //   250: iconst_0       
                //   251: iconst_0       
                //   252: bastore        
                //   253: aload           11
                //   255: iconst_1       
                //   256: iconst_0       
                //   257: i2b            
                //   258: bastore        
                //   259: aload           11
                //   261: iconst_2       
                //   262: iload           8
                //   264: i2b            
                //   265: bastore        
                //   266: iconst_0       
                //   267: iload           6
                //   269: if_icmpge       286
                //   272: aload           11
                //   274: iconst_3       
                //   275: aload           9
                //   277: iconst_0       
                //   278: baload         
                //   279: bastore        
                //   280: iinc            12, 1
                //   283: goto            266
                //   286: bipush          52
                //   288: aconst_null    
                //   289: aload_0        
                //   290: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   295: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   298: astore          12
                //   300: aload           12
                //   302: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   305: iload_1        
                //   306: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   309: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   314: aload           12
                //   316: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
                //   319: aload           11
                //   321: arraylength    
                //   322: i2s            
                //   323: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //   326: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   331: aload           12
                //   333: new             Lcom/viaversion/viaversion/api/type/types/CustomByteType;
                //   336: dup            
                //   337: aload           11
                //   339: arraylength    
                //   340: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   343: invokespecial   com/viaversion/viaversion/api/type/types/CustomByteType.<init>:(Ljava/lang/Integer;)V
                //   346: aload           11
                //   348: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   353: aload           12
                //   355: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   357: iconst_1       
                //   358: iconst_1       
                //   359: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;ZZ)Z
                //   362: pop            
                //   363: iinc            10, 1
                //   366: goto            235
                //   369: iload_3        
                //   370: ifle            514
                //   373: iload_3        
                //   374: iconst_3       
                //   375: imul           
                //   376: iconst_1       
                //   377: iadd           
                //   378: newarray        B
                //   380: astore          6
                //   382: aload           6
                //   384: iconst_0       
                //   385: iconst_1       
                //   386: bastore        
                //   387: iconst_0       
                //   388: iload_3        
                //   389: if_icmpge       433
                //   392: aload           6
                //   394: iconst_1       
                //   395: aload           4
                //   397: iconst_0       
                //   398: baload         
                //   399: iconst_4       
                //   400: ishl           
                //   401: aload           4
                //   403: iconst_3       
                //   404: baload         
                //   405: bipush          15
                //   407: iand           
                //   408: ior            
                //   409: i2b            
                //   410: bastore        
                //   411: aload           6
                //   413: iconst_2       
                //   414: aload           4
                //   416: iconst_1       
                //   417: baload         
                //   418: bastore        
                //   419: aload           6
                //   421: iconst_3       
                //   422: aload           4
                //   424: iconst_2       
                //   425: baload         
                //   426: bastore        
                //   427: iinc            7, 1
                //   430: goto            387
                //   433: bipush          52
                //   435: aconst_null    
                //   436: aload_0        
                //   437: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   442: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   445: astore          7
                //   447: aload           7
                //   449: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   452: iload_1        
                //   453: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   456: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   461: aload           7
                //   463: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
                //   466: aload           6
                //   468: arraylength    
                //   469: i2s            
                //   470: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //   473: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   478: new             Lcom/viaversion/viaversion/api/type/types/CustomByteType;
                //   481: dup            
                //   482: aload           6
                //   484: arraylength    
                //   485: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   488: invokespecial   com/viaversion/viaversion/api/type/types/CustomByteType.<init>:(Ljava/lang/Integer;)V
                //   491: astore          8
                //   493: aload           7
                //   495: aload           8
                //   497: aload           6
                //   499: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   504: aload           7
                //   506: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   508: iconst_1       
                //   509: iconst_1       
                //   510: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;ZZ)Z
                //   513: pop            
                //   514: bipush          52
                //   516: aconst_null    
                //   517: aload_0        
                //   518: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   523: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   526: astore          6
                //   528: aload           6
                //   530: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //   533: iload_1        
                //   534: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   537: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   542: aload           6
                //   544: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
                //   547: iconst_2       
                //   548: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //   551: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   556: aload           6
                //   558: new             Lcom/viaversion/viaversion/api/type/types/CustomByteType;
                //   561: dup            
                //   562: iconst_2       
                //   563: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   566: invokespecial   com/viaversion/viaversion/api/type/types/CustomByteType.<init>:(Ljava/lang/Integer;)V
                //   569: iconst_2       
                //   570: newarray        B
                //   572: dup            
                //   573: iconst_0       
                //   574: iconst_2       
                //   575: bastore        
                //   576: dup            
                //   577: iconst_1       
                //   578: iload_2        
                //   579: bastore        
                //   580: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   585: aload           6
                //   587: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   589: iconst_1       
                //   590: iconst_1       
                //   591: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;ZZ)Z
                //   594: pop            
                //   595: return         
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
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$11::lambda$registerMap$0);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT, Types1_7_6_10.COMPRESSED_NBT);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.SHORT, (short)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.cancelClientbound(ClientboundPackets1_8.SERVER_DIFFICULTY);
        protocol1_7_6_10TO1_8.cancelClientbound(ClientboundPackets1_8.COMBAT_EVENT);
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.WORLD_BORDER, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(WorldPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final WorldBorder worldBorder = (WorldBorder)packetWrapper.user().get(WorldBorder.class);
                if (intValue == 0) {
                    worldBorder.setSize((double)packetWrapper.read(Type.DOUBLE));
                }
                else if (intValue == 1) {
                    worldBorder.lerpSize((double)packetWrapper.read(Type.DOUBLE), (double)packetWrapper.read(Type.DOUBLE), (long)packetWrapper.read(VarLongType.VAR_LONG));
                }
                else if (intValue == 2) {
                    worldBorder.setCenter((double)packetWrapper.read(Type.DOUBLE), (double)packetWrapper.read(Type.DOUBLE));
                }
                else if (intValue == 3) {
                    worldBorder.init((double)packetWrapper.read(Type.DOUBLE), (double)packetWrapper.read(Type.DOUBLE), (double)packetWrapper.read(Type.DOUBLE), (double)packetWrapper.read(Type.DOUBLE), (long)packetWrapper.read(VarLongType.VAR_LONG), (int)packetWrapper.read(Type.VAR_INT), (int)packetWrapper.read(Type.VAR_INT), (int)packetWrapper.read(Type.VAR_INT));
                }
                else if (intValue == 4) {
                    worldBorder.setWarningTime((int)packetWrapper.read(Type.VAR_INT));
                }
                else if (intValue == 5) {
                    worldBorder.setWarningBlocks((int)packetWrapper.read(Type.VAR_INT));
                }
                packetWrapper.cancel();
            }
        });
    }
}
