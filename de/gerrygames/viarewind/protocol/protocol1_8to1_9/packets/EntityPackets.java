package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.util.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.type.types.version.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.util.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;

public class EntityPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_STATUS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(EntityPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                if (byteValue > 23) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.BYTE, byteValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final short shortValue = (short)packetWrapper.read(Type.SHORT);
                final short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                final short shortValue3 = (short)packetWrapper.read(Type.SHORT);
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement(intValue);
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.relMove(shortValue / 4096.0, shortValue2 / 4096.0, shortValue3 / 4096.0);
                    return;
                }
                final Vector[] calculateRelativeMoves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), intValue, shortValue, shortValue2, shortValue3);
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockX());
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockY());
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockZ());
                final boolean booleanValue = (boolean)packetWrapper.passthrough(Type.BOOLEAN);
                if (calculateRelativeMoves.length > 1) {
                    final PacketWrapper create = PacketWrapper.create(21, null, packetWrapper.user());
                    create.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockX());
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockY());
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockZ());
                    create.write(Type.BOOLEAN, booleanValue);
                    PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final short shortValue = (short)packetWrapper.read(Type.SHORT);
                final short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                final short shortValue3 = (short)packetWrapper.read(Type.SHORT);
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement(intValue);
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.relMove(shortValue / 4096.0, shortValue2 / 4096.0, shortValue3 / 4096.0);
                    entityReplacement.setYawPitch((byte)packetWrapper.read(Type.BYTE) * 360.0f / 256.0f, (byte)packetWrapper.read(Type.BYTE) * 360.0f / 256.0f);
                    return;
                }
                final Vector[] calculateRelativeMoves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), intValue, shortValue, shortValue2, shortValue3);
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockX());
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockY());
                packetWrapper.write(Type.BYTE, (byte)calculateRelativeMoves[0].getBlockZ());
                byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
                final byte byteValue2 = (byte)packetWrapper.passthrough(Type.BYTE);
                final boolean booleanValue = (boolean)packetWrapper.passthrough(Type.BOOLEAN);
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(intValue) == Entity1_10Types.EntityType.BOAT) {
                    byteValue -= 64;
                    packetWrapper.set(Type.BYTE, 3, byteValue);
                }
                if (calculateRelativeMoves.length > 1) {
                    final PacketWrapper create = PacketWrapper.create(23, null, packetWrapper.user());
                    create.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockX());
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockY());
                    create.write(Type.BYTE, (byte)calculateRelativeMoves[1].getBlockZ());
                    create.write(Type.BYTE, byteValue);
                    create.write(Type.BYTE, byteValue2);
                    create.write(Type.BOOLEAN, booleanValue);
                    PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets$4::lambda$registerMap$0);
                this.handler(EntityPackets$4::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get((int)packetWrapper.get(Type.VAR_INT, 0)) == Entity1_10Types.EntityType.BOAT) {
                    packetWrapper.set(Type.BYTE, 0, (byte)((byte)packetWrapper.get(Type.BYTE, 0) - 64));
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.VAR_INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.setYawPitch((byte)packetWrapper.get(Type.BYTE, 0) * 360.0f / 256.0f, (byte)packetWrapper.get(Type.BYTE, 1) * 360.0f / 256.0f);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.VEHICLE_MOVE, ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$5::lambda$registerMap$0);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.map(Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.handler(EntityPackets$5::lambda$registerMap$1);
                this.create(Type.BOOLEAN, true);
                this.handler(EntityPackets$5::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get((int)packetWrapper.get(Type.VAR_INT, 0)) == Entity1_10Types.EntityType.BOAT) {
                    packetWrapper.set(Type.BYTE, 0, (byte)((byte)packetWrapper.get(Type.BYTE, 1) - 64));
                    int intValue = (int)packetWrapper.get(Type.INT, 1);
                    intValue += 10;
                    packetWrapper.set(Type.INT, 1, intValue);
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.isCancelled()) {
                    return;
                }
                ((PlayerPosition)packetWrapper.user().get(PlayerPosition.class)).setPos((int)packetWrapper.get(Type.INT, 0) / 32.0, (int)packetWrapper.get(Type.INT, 1) / 32.0, (int)packetWrapper.get(Type.INT, 2) / 32.0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final int vehicle = entityTracker.getVehicle(entityTracker.getPlayerId());
                if (vehicle == -1) {
                    packetWrapper.cancel();
                }
                packetWrapper.write(Type.VAR_INT, vehicle);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.DESTROY_ENTITIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(EntityPackets$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final int[] array = (int[])packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
                while (0 < array.length) {
                    entityTracker.removeEntity(array[0]);
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(EntityPackets$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                if (byteValue > 23) {
                    packetWrapper.cancel();
                }
                if (byteValue == 25) {
                    if ((int)packetWrapper.get(Type.VAR_INT, 0) != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                        return;
                    }
                    ((Levitation)packetWrapper.user().get(Levitation.class)).setActive(false);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_HEAD_LOOK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(EntityPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.VAR_INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.setHeadYaw((byte)packetWrapper.get(Type.BYTE, 0) * 360.0f / 256.0f);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_METADATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(EntityPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final List list = (List)packetWrapper.get(Types1_8.METADATA_LIST, 0);
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                if (entityTracker.getClientEntityTypes().containsKey(intValue)) {
                    MetadataRewriter.transform((Entity1_10Types.EntityType)entityTracker.getClientEntityTypes().get(intValue), list);
                    if (list.isEmpty()) {
                        packetWrapper.cancel();
                    }
                }
                else {
                    entityTracker.addMetadataToBuffer(intValue, list);
                    packetWrapper.cancel();
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ATTACH_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.create(Type.BOOLEAN, true);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_EQUIPMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(EntityPackets$11::lambda$registerMap$0);
                this.map(Type.ITEM);
                this.handler(EntityPackets$11::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0)));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                int intValue = (int)packetWrapper.read(Type.VAR_INT);
                if (intValue == 1) {
                    packetWrapper.cancel();
                }
                else if (intValue > 1) {
                    --intValue;
                }
                packetWrapper.write(Type.SHORT, (short)intValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SET_PASSENGERS, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.cancel:()V
                //     6: aload_0        
                //     7: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    12: ldc             Lde/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/EntityTracker;.class
                //    14: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    19: checkcast       Lde/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/EntityTracker;
                //    22: astore_1       
                //    23: aload_0        
                //    24: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    27: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    32: checkcast       Ljava/lang/Integer;
                //    35: invokevirtual   java/lang/Integer.intValue:()I
                //    38: istore_2       
                //    39: aload_0        
                //    40: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    43: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    48: checkcast       Ljava/lang/Integer;
                //    51: invokevirtual   java/lang/Integer.intValue:()I
                //    54: istore_3       
                //    55: new             Ljava/util/ArrayList;
                //    58: dup            
                //    59: invokespecial   java/util/ArrayList.<init>:()V
                //    62: astore          4
                //    64: iconst_0       
                //    65: iload_3        
                //    66: if_icmpge       93
                //    69: aload           4
                //    71: aload_0        
                //    72: getstatic       com/viaversion/viaversion/api/type/Type.VAR_INT:Lcom/viaversion/viaversion/api/type/types/VarIntType;
                //    75: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.read:(Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
                //    80: checkcast       Ljava/lang/Integer;
                //    83: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
                //    86: pop            
                //    87: iinc            5, 1
                //    90: goto            64
                //    93: aload_1        
                //    94: iload_2        
                //    95: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/EntityTracker.getPassengers:(I)Ljava/util/List;
                //    98: astore          5
                //   100: aload_1        
                //   101: iload_2        
                //   102: aload           4
                //   104: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_8to1_9/storage/EntityTracker.setPassengers:(ILjava/util/List;)V
                //   107: aload           5
                //   109: invokeinterface java/util/List.isEmpty:()Z
                //   114: ifne            213
                //   117: aload           5
                //   119: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
                //   124: astore          6
                //   126: aload           6
                //   128: invokeinterface java/util/Iterator.hasNext:()Z
                //   133: ifeq            213
                //   136: aload           6
                //   138: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
                //   143: checkcast       Ljava/lang/Integer;
                //   146: astore          7
                //   148: bipush          27
                //   150: aconst_null    
                //   151: aload_0        
                //   152: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   157: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   160: astore          8
                //   162: aload           8
                //   164: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //   167: aload           7
                //   169: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   174: aload           8
                //   176: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //   179: iconst_m1      
                //   180: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   183: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   188: aload           8
                //   190: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   193: iconst_0       
                //   194: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
                //   197: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   202: aload           8
                //   204: ldc             Lde/gerrygames/viarewind/protocol/protocol1_8to1_9/Protocol1_8TO1_9;.class
                //   206: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   209: pop            
                //   210: goto            126
                //   213: iconst_0       
                //   214: iload_3        
                //   215: if_icmpge       322
                //   218: iload_2        
                //   219: goto            234
                //   222: aload           4
                //   224: iconst_m1      
                //   225: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
                //   228: checkcast       Ljava/lang/Integer;
                //   231: invokevirtual   java/lang/Integer.intValue:()I
                //   234: istore          7
                //   236: aload           4
                //   238: iconst_0       
                //   239: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
                //   242: checkcast       Ljava/lang/Integer;
                //   245: invokevirtual   java/lang/Integer.intValue:()I
                //   248: istore          8
                //   250: bipush          27
                //   252: aconst_null    
                //   253: aload_0        
                //   254: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   259: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   262: astore          9
                //   264: aload           9
                //   266: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //   269: iload           8
                //   271: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   274: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   279: aload           9
                //   281: getstatic       com/viaversion/viaversion/api/type/Type.INT:Lcom/viaversion/viaversion/api/type/types/IntType;
                //   284: iload           7
                //   286: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   289: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   294: aload           9
                //   296: getstatic       com/viaversion/viaversion/api/type/Type.BOOLEAN:Lcom/viaversion/viaversion/api/type/types/BooleanType;
                //   299: iconst_0       
                //   300: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
                //   303: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   308: aload           9
                //   310: ldc             Lde/gerrygames/viarewind/protocol/protocol1_8to1_9/Protocol1_8TO1_9;.class
                //   312: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   315: pop            
                //   316: iinc            6, 1
                //   319: goto            213
                //   322: return         
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
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_TELEPORT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets$13::lambda$registerMap$0);
                this.handler(EntityPackets$13::lambda$registerMap$1);
                this.handler(EntityPackets$13::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.VAR_INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    final int intValue = (int)packetWrapper.get(Type.INT, 0);
                    final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                    final int intValue3 = (int)packetWrapper.get(Type.INT, 2);
                    final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                    final byte byteValue2 = (byte)packetWrapper.get(Type.BYTE, 1);
                    entityReplacement.setLocation(intValue / 32.0, intValue2 / 32.0, intValue3 / 32.0);
                    entityReplacement.setYawPitch(byteValue * 360.0f / 256.0f, byteValue2 * 360.0f / 256.0f);
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).resetEntityOffset((int)packetWrapper.get(Type.VAR_INT, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get((int)packetWrapper.get(Type.VAR_INT, 0)) == Entity1_10Types.EntityType.BOAT) {
                    packetWrapper.set(Type.BYTE, 0, (byte)((byte)packetWrapper.get(Type.BYTE, 1) - 64));
                    int intValue = (int)packetWrapper.get(Type.INT, 1);
                    intValue += 10;
                    packetWrapper.set(Type.INT, 1, intValue);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_PROPERTIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.handler(EntityPackets$14::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final boolean b = (int)packetWrapper.get(Type.VAR_INT, 0) == ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId();
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                while (0 < intValue) {
                    final String s = (String)packetWrapper.read(Type.STRING);
                    final boolean b2 = !Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(s);
                    final double doubleValue = (double)packetWrapper.read(Type.DOUBLE);
                    final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                    if (!b2) {
                        packetWrapper.write(Type.STRING, s);
                        packetWrapper.write(Type.DOUBLE, doubleValue);
                        packetWrapper.write(Type.VAR_INT, intValue2);
                    }
                    else {
                        int n = 0;
                        ++n;
                    }
                    final ArrayList<Pair> list = new ArrayList<Pair>();
                    while (0 < intValue2) {
                        final UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                        final double doubleValue2 = (double)packetWrapper.read(Type.DOUBLE);
                        final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                        list.add(new Pair(byteValue, doubleValue2));
                        if (!b2) {
                            packetWrapper.write(Type.UUID, uuid);
                            packetWrapper.write(Type.DOUBLE, doubleValue2);
                            packetWrapper.write(Type.BYTE, byteValue);
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    if (b && s.equals("generic.attackSpeed")) {
                        ((Cooldown)packetWrapper.user().get(Cooldown.class)).setAttackSpeed(doubleValue, list);
                    }
                    int n3 = 0;
                    ++n3;
                }
                packetWrapper.set(Type.INT, 0, intValue - 0);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.ENTITY_EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(EntityPackets$15::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                if (byteValue > 23) {
                    packetWrapper.cancel();
                }
                if (byteValue == 25) {
                    if ((int)packetWrapper.get(Type.VAR_INT, 0) != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                        return;
                    }
                    final Levitation levitation = (Levitation)packetWrapper.user().get(Levitation.class);
                    levitation.setActive(true);
                    levitation.setAmplifier((byte)packetWrapper.get(Type.BYTE, 1));
                }
            }
        });
    }
}
