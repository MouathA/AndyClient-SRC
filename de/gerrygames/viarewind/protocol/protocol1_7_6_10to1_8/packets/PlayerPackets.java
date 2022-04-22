package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import java.nio.charset.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.*;
import com.viaversion.viaversion.api.*;
import de.gerrygames.viarewind.utils.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.*;
import de.gerrygames.viarewind.replacement.*;
import de.gerrygames.viarewind.utils.math.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class PlayerPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.JOIN_GAME, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(PlayerPackets$1::lambda$registerMap$0);
                this.handler(PlayerPackets$1::lambda$registerMap$1);
                this.handler(PlayerPackets$1::lambda$registerMap$2);
                this.handler(PlayerPackets$1::lambda$registerMap$3);
            }
            
            private static void lambda$registerMap$3(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().put(new Scoreboard(packetWrapper.user()));
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((byte)packetWrapper.get(Type.BYTE, 0));
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.setGamemode((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                entityTracker.setPlayerId((int)packetWrapper.get(Type.INT, 0));
                entityTracker.getClientEntityTypes().put(entityTracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                entityTracker.setDimension((byte)packetWrapper.get(Type.BYTE, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                    return;
                }
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 2) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 0, 0);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.COMPONENT);
                this.handler(PlayerPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if ((byte)packetWrapper.read(Type.BYTE) == 2) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.INT, position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.UPDATE_HEALTH, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.VAR_INT, Type.SHORT);
                this.map(Type.FLOAT);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(PlayerPackets$5::lambda$registerMap$0);
                this.handler(PlayerPackets$5::lambda$registerMap$1);
                this.handler(PlayerPackets$5::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                ((ClientWorld)packetWrapper.user().get(ClientWorld.class)).setEnvironment((int)packetWrapper.get(Type.INT, 0));
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.setGamemode((short)packetWrapper.get(Type.UNSIGNED_BYTE, 1));
                if (entityTracker.getDimension() != (int)packetWrapper.get(Type.INT, 0)) {
                    entityTracker.setDimension((int)packetWrapper.get(Type.INT, 0));
                    entityTracker.clearEntities();
                    entityTracker.getClientEntityTypes().put(entityTracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                    return;
                }
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 1) == 2) {
                    packetWrapper.set(Type.UNSIGNED_BYTE, 1, 0);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$6::lambda$registerMap$0);
                this.handler(PlayerPackets$6::lambda$registerMap$1);
                this.handler(PlayerPackets$6::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                if (entityTracker.getSpectating() != entityTracker.getPlayerId()) {
                    packetWrapper.cancel();
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, ((PlayerPosition)packetWrapper.user().get(PlayerPosition.class)).isOnGround());
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                playerPosition.setPositionPacketReceived(true);
                final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                if ((byteValue & 0x1) == 0x1) {
                    packetWrapper.set(Type.DOUBLE, 0, (double)packetWrapper.get(Type.DOUBLE, 0) + playerPosition.getPosX());
                }
                double doubleValue = (double)packetWrapper.get(Type.DOUBLE, 1);
                if ((byteValue & 0x2) == 0x2) {
                    doubleValue += playerPosition.getPosY();
                }
                playerPosition.setReceivedPosY(doubleValue);
                packetWrapper.set(Type.DOUBLE, 1, doubleValue + 1.6200000047683716);
                if ((byteValue & 0x4) == 0x4) {
                    packetWrapper.set(Type.DOUBLE, 2, (double)packetWrapper.get(Type.DOUBLE, 2) + playerPosition.getPosZ());
                }
                if ((byteValue & 0x8) == 0x8) {
                    packetWrapper.set(Type.FLOAT, 0, (float)packetWrapper.get(Type.FLOAT, 0) + playerPosition.getYaw());
                }
                if ((byteValue & 0x10) == 0x10) {
                    packetWrapper.set(Type.FLOAT, 1, (float)packetWrapper.get(Type.FLOAT, 1) + playerPosition.getPitch());
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SET_EXPERIENCE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.VAR_INT, Type.SHORT);
                this.map(Type.VAR_INT, Type.SHORT);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$8::lambda$registerMap$0);
                this.handler(PlayerPackets$8::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                    ((Float)packetWrapper.get(Type.FLOAT, 0)).intValue();
                    if (0 == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                        packetWrapper.set(Type.FLOAT, 0, 0.0f);
                    }
                    ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setGamemode(0);
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper p0) throws Exception {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: getstatic       com/viaversion/viaversion/api/type/Type.UNSIGNED_BYTE:Lcom/viaversion/viaversion/api/type/types/UnsignedByteType;
                //     4: iconst_0       
                //     5: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                //    10: checkcast       Ljava/lang/Short;
                //    13: invokevirtual   java/lang/Short.shortValue:()S
                //    16: istore_1       
                //    17: iload_1        
                //    18: iconst_3       
                //    19: if_icmpeq       23
                //    22: return         
                //    23: aload_0        
                //    24: getstatic       com/viaversion/viaversion/api/type/Type.FLOAT:Lcom/viaversion/viaversion/api/type/types/FloatType;
                //    27: iconst_0       
                //    28: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.get:(Lcom/viaversion/viaversion/api/type/Type;I)Ljava/lang/Object;
                //    33: checkcast       Ljava/lang/Float;
                //    36: invokevirtual   java/lang/Float.intValue:()I
                //    39: istore_2       
                //    40: aload_0        
                //    41: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    46: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/EntityTracker;.class
                //    48: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //    53: checkcast       Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/EntityTracker;
                //    56: astore_3       
                //    57: iload_2        
                //    58: iconst_3       
                //    59: if_icmpeq       70
                //    62: aload_3        
                //    63: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/EntityTracker.getGamemode:()I
                //    66: iconst_3       
                //    67: if_icmpne       230
                //    70: aload_0        
                //    71: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    76: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.getProtocolInfo:()Lcom/viaversion/viaversion/api/connection/ProtocolInfo;
                //    81: invokeinterface com/viaversion/viaversion/api/connection/ProtocolInfo.getUuid:()Ljava/util/UUID;
                //    86: astore          4
                //    88: iload_2        
                //    89: iconst_3       
                //    90: if_icmpne       134
                //    93: aload_0        
                //    94: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //    99: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/GameProfileStorage;.class
                //   101: invokeinterface com/viaversion/viaversion/api/connection/UserConnection.get:(Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
                //   106: checkcast       Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/GameProfileStorage;
                //   109: aload           4
                //   111: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/GameProfileStorage.get:(Ljava/util/UUID;)Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/GameProfileStorage$GameProfile;
                //   114: astore          6
                //   116: iconst_5       
                //   117: anewarray       Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   120: astore          5
                //   122: aload           5
                //   124: iconst_4       
                //   125: aload           6
                //   127: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/GameProfileStorage$GameProfile.getSkull:()Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   130: aastore        
                //   131: goto            153
                //   134: aload_3        
                //   135: aload           4
                //   137: invokevirtual   de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/EntityTracker.getPlayerEquipment:(Ljava/util/UUID;)[Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   140: astore          5
                //   142: aload           5
                //   144: ifnonnull       153
                //   147: iconst_5       
                //   148: anewarray       Lcom/viaversion/viaversion/api/minecraft/item/Item;
                //   151: astore          5
                //   153: iconst_1       
                //   154: iconst_5       
                //   155: if_icmpge       230
                //   158: bipush          47
                //   160: aconst_null    
                //   161: aload_0        
                //   162: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.user:()Lcom/viaversion/viaversion/api/connection/UserConnection;
                //   167: invokestatic    com/viaversion/viaversion/api/protocol/packet/PacketWrapper.create:(ILio/netty/buffer/ByteBuf;Lcom/viaversion/viaversion/api/connection/UserConnection;)Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
                //   170: astore          7
                //   172: aload           7
                //   174: getstatic       com/viaversion/viaversion/api/type/Type.BYTE:Lcom/viaversion/viaversion/api/type/types/ByteType;
                //   177: iconst_0       
                //   178: invokestatic    java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
                //   181: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   186: aload           7
                //   188: getstatic       com/viaversion/viaversion/api/type/Type.SHORT:Lcom/viaversion/viaversion/api/type/types/ShortType;
                //   191: bipush          8
                //   193: i2s            
                //   194: invokestatic    java/lang/Short.valueOf:(S)Ljava/lang/Short;
                //   197: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   202: aload           7
                //   204: getstatic       de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/types/Types1_7_6_10.COMPRESSED_NBT_ITEM:Lcom/viaversion/viaversion/api/type/Type;
                //   207: aload           5
                //   209: iconst_1       
                //   210: aaload         
                //   211: invokeinterface com/viaversion/viaversion/api/protocol/packet/PacketWrapper.write:(Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
                //   216: aload           7
                //   218: ldc             Lde/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/Protocol1_7_6_10TO1_8;.class
                //   220: invokestatic    de/gerrygames/viarewind/utils/PacketUtil.sendPacket:(Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;Ljava/lang/Class;)Z
                //   223: pop            
                //   224: iinc            6, 1
                //   227: goto            153
                //   230: return         
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
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.INT, position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$10::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                final GameProfileStorage gameProfileStorage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                while (0 < intValue2) {
                    final UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                    if (intValue == 0) {
                        final String s = (String)packetWrapper.read(Type.STRING);
                        GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                        if (gameProfile == null) {
                            gameProfile = gameProfileStorage.put(uuid, s);
                        }
                        int intValue3 = (int)packetWrapper.read(Type.VAR_INT);
                        while (intValue3-- > 0) {
                            gameProfile.properties.add(new GameProfileStorage.Property((String)packetWrapper.read(Type.STRING), (String)packetWrapper.read(Type.STRING), ((boolean)packetWrapper.read(Type.BOOLEAN)) ? ((String)packetWrapper.read(Type.STRING)) : null));
                        }
                        final int intValue4 = (int)packetWrapper.read(Type.VAR_INT);
                        final int intValue5 = (int)packetWrapper.read(Type.VAR_INT);
                        gameProfile.ping = intValue5;
                        gameProfile.gamemode = intValue4;
                        if (packetWrapper.read(Type.BOOLEAN)) {
                            gameProfile.setDisplayName(ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
                        }
                        final PacketWrapper create = PacketWrapper.create(56, null, packetWrapper.user());
                        create.write(Type.STRING, gameProfile.name);
                        create.write(Type.BOOLEAN, true);
                        create.write(Type.SHORT, (short)intValue5);
                        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                    }
                    else if (intValue == 1) {
                        final int intValue6 = (int)packetWrapper.read(Type.VAR_INT);
                        final GameProfileStorage.GameProfile value = gameProfileStorage.get(uuid);
                        if (value != null) {
                            if (value.gamemode != intValue6) {
                                if (intValue6 == 3 || value.gamemode == 3) {
                                    final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                                    final int playerEntityId = entityTracker.getPlayerEntityId(uuid);
                                    if (playerEntityId != -1) {
                                        Item[] playerEquipment;
                                        if (intValue6 == 3) {
                                            playerEquipment = new Item[] { null, null, null, null, value.getSkull() };
                                        }
                                        else {
                                            playerEquipment = entityTracker.getPlayerEquipment(uuid);
                                            if (playerEquipment == null) {
                                                playerEquipment = new Item[5];
                                            }
                                        }
                                        while (0 < 5) {
                                            final PacketWrapper create2 = PacketWrapper.create(4, null, packetWrapper.user());
                                            create2.write(Type.INT, playerEntityId);
                                            create2.write(Type.SHORT, 0);
                                            create2.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, playerEquipment[0]);
                                            PacketUtil.sendPacket(create2, Protocol1_7_6_10TO1_8.class);
                                            final short n = 1;
                                        }
                                    }
                                }
                                value.gamemode = intValue6;
                            }
                        }
                    }
                    else if (intValue == 2) {
                        final int intValue7 = (int)packetWrapper.read(Type.VAR_INT);
                        final GameProfileStorage.GameProfile value2 = gameProfileStorage.get(uuid);
                        if (value2 != null) {
                            value2.ping = intValue7;
                            final PacketWrapper create3 = PacketWrapper.create(56, null, packetWrapper.user());
                            create3.write(Type.STRING, value2.name);
                            create3.write(Type.BOOLEAN, true);
                            create3.write(Type.SHORT, (short)intValue7);
                            PacketUtil.sendPacket(create3, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                    else if (intValue == 3) {
                        final String displayName = packetWrapper.read(Type.BOOLEAN) ? ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)) : null;
                        final GameProfileStorage.GameProfile value3 = gameProfileStorage.get(uuid);
                        if (value3 != null) {
                            if (value3.displayName != null || displayName != null) {
                                if ((value3.displayName == null && displayName != null) || (value3.displayName != null && displayName == null) || !value3.displayName.equals(displayName)) {
                                    value3.setDisplayName(displayName);
                                }
                            }
                        }
                    }
                    else if (intValue == 4) {
                        final GameProfileStorage.GameProfile remove = gameProfileStorage.remove(uuid);
                        if (remove != null) {
                            final PacketWrapper create4 = PacketWrapper.create(56, null, packetWrapper.user());
                            create4.write(Type.STRING, remove.name);
                            create4.write(Type.BOOLEAN, false);
                            create4.write(Type.SHORT, (short)remove.ping);
                            PacketUtil.sendPacket(create4, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.PLAYER_ABILITIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                final float floatValue2 = (float)packetWrapper.get(Type.FLOAT, 1);
                final PlayerAbilities playerAbilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                playerAbilities.setInvincible((byteValue & 0x8) == 0x8);
                playerAbilities.setAllowFly((byteValue & 0x4) == 0x4);
                playerAbilities.setFlying((byteValue & 0x2) == 0x2);
                playerAbilities.setCreative((byteValue & 0x1) == 0x1);
                playerAbilities.setFlySpeed(floatValue);
                playerAbilities.setWalkSpeed(floatValue2);
                if (playerAbilities.isSprinting() && playerAbilities.isFlying()) {
                    packetWrapper.set(Type.FLOAT, 0, playerAbilities.getFlySpeed() * 2.0f);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    short n;
                    if (packetWrapper.isReadable(Type.BYTE, 0)) {
                        n = (byte)packetWrapper.passthrough(Type.BYTE);
                    }
                    else {
                        n = (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    }
                    while (0 < n) {
                        packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        if (packetWrapper.passthrough(Type.BOOLEAN)) {
                            packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.read(Type.INT);
                        packetWrapper.read(Type.INT);
                        int n2 = 0;
                        ++n2;
                    }
                }
                else if (s.equalsIgnoreCase("MC|Brand")) {
                    packetWrapper.write(Type.REMAINING_BYTES, ((String)packetWrapper.read(Type.STRING)).getBytes(StandardCharsets.UTF_8));
                }
                packetWrapper.cancel();
                packetWrapper.setId(-1);
                final ByteBuf buffer = Unpooled.buffer();
                packetWrapper.writeToBuffer(buffer);
                final PacketWrapper create = PacketWrapper.create(63, buffer, packetWrapper.user());
                create.passthrough(Type.STRING);
                if (buffer.readableBytes() <= 32767) {
                    create.write(Type.SHORT, (short)buffer.readableBytes());
                    create.send(Protocol1_7_6_10TO1_8.class);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.CAMERA, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$13::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                if (entityTracker.getSpectating() != intValue) {
                    entityTracker.setSpectating(intValue);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.TITLE, null, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$14::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.cancel();
                final TitleRenderProvider titleRenderProvider = (TitleRenderProvider)Via.getManager().getProviders().get(TitleRenderProvider.class);
                if (titleRenderProvider == null) {
                    return;
                }
                final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                final UUID uuid = Utils.getUUID(packetWrapper.user());
                switch (intValue) {
                    case 0: {
                        titleRenderProvider.setTitle(uuid, (String)packetWrapper.read(Type.STRING));
                        break;
                    }
                    case 1: {
                        titleRenderProvider.setSubTitle(uuid, (String)packetWrapper.read(Type.STRING));
                        break;
                    }
                    case 2: {
                        titleRenderProvider.setTimings(uuid, (int)packetWrapper.read(Type.INT), (int)packetWrapper.read(Type.INT), (int)packetWrapper.read(Type.INT));
                        break;
                    }
                    case 3: {
                        titleRenderProvider.clear(uuid);
                        break;
                    }
                    case 4: {
                        titleRenderProvider.reset(uuid);
                        break;
                    }
                }
            }
        });
        protocol1_7_6_10TO1_8.cancelClientbound(ClientboundPackets1_8.TAB_LIST);
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.RESOURCE_PACK, ClientboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.create(Type.STRING, "MC|RPack");
                this.handler(PlayerPackets$15::lambda$registerMap$0);
                this.map(Type.STRING, Type.NOTHING);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                Type.STRING.write(buffer, packetWrapper.read(Type.STRING));
                packetWrapper.write(Type.SHORT_BYTE_ARRAY, Type.REMAINING_BYTES.read(buffer));
                buffer.release();
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.CHAT_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(PlayerPackets$16::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getGamemode() == 3 && s.toLowerCase().startsWith("/stp ")) {
                    final GameProfileStorage.GameProfile value = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(s.split(" ")[1], true);
                    if (value != null && value.uuid != null) {
                        packetWrapper.cancel();
                        final PacketWrapper create = PacketWrapper.create(24, null, packetWrapper.user());
                        create.write(Type.UUID, value.uuid);
                        PacketUtil.sendToServer(create, Protocol1_7_6_10TO1_8.class, true, true);
                    }
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.INTERACT_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
                this.map(Type.BYTE, Type.VAR_INT);
                this.handler(PlayerPackets$17::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                (int)packetWrapper.get(Type.VAR_INT, 1);
                if (2 != 0) {
                    return;
                }
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.VAR_INT, 0));
                if (!(entityReplacement instanceof ArmorStandReplacement)) {
                    return;
                }
                final AABB boundingBox = ((ArmorStandReplacement)entityReplacement).getBoundingBox();
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                final Vector3d vector3d = new Vector3d(playerPosition.getPosX(), playerPosition.getPosY() + 1.8, playerPosition.getPosZ());
                final double radians = Math.toRadians(playerPosition.getYaw());
                final double radians2 = Math.toRadians(playerPosition.getPitch());
                final Vector3d trace = RayTracing.trace(new Ray3d(vector3d, new Vector3d(-Math.cos(radians2) * Math.sin(radians), -Math.sin(radians2), Math.cos(radians2) * Math.cos(radians))), boundingBox, 5.0);
                if (trace == null) {
                    return;
                }
                trace.substract(boundingBox.getMin());
                packetWrapper.set(Type.VAR_INT, 1, 2);
                packetWrapper.write(Type.FLOAT, (float)trace.getX());
                packetWrapper.write(Type.FLOAT, (float)trace.getY());
                packetWrapper.write(Type.FLOAT, (float)trace.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_MOVEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$18::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                ((PlayerPosition)packetWrapper.user().get(PlayerPosition.class)).setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE, Type.NOTHING);
                this.map(Type.DOUBLE);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$19::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final double doubleValue = (double)packetWrapper.get(Type.DOUBLE, 0);
                double doubleValue2 = (double)packetWrapper.get(Type.DOUBLE, 1);
                final double doubleValue3 = (double)packetWrapper.get(Type.DOUBLE, 2);
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                if (playerPosition.isPositionPacketReceived()) {
                    playerPosition.setPositionPacketReceived(false);
                    doubleValue2 -= 0.01;
                    packetWrapper.set(Type.DOUBLE, 1, doubleValue2);
                }
                playerPosition.setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
                playerPosition.setPos(doubleValue, doubleValue2, doubleValue3);
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$20::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                playerPosition.setYaw((float)packetWrapper.get(Type.FLOAT, 0));
                playerPosition.setPitch((float)packetWrapper.get(Type.FLOAT, 1));
                playerPosition.setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE, Type.NOTHING);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(PlayerPackets$21::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final double doubleValue = (double)packetWrapper.get(Type.DOUBLE, 0);
                double n = (double)packetWrapper.get(Type.DOUBLE, 1);
                final double doubleValue2 = (double)packetWrapper.get(Type.DOUBLE, 2);
                final float floatValue = (float)packetWrapper.get(Type.FLOAT, 0);
                final float floatValue2 = (float)packetWrapper.get(Type.FLOAT, 1);
                final PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                if (playerPosition.isPositionPacketReceived()) {
                    playerPosition.setPositionPacketReceived(false);
                    n = playerPosition.getReceivedPosY();
                    packetWrapper.set(Type.DOUBLE, 1, n);
                }
                playerPosition.setOnGround((boolean)packetWrapper.get(Type.BOOLEAN, 0));
                playerPosition.setPos(doubleValue, n, doubleValue2);
                playerPosition.setYaw(floatValue);
                playerPosition.setPitch(floatValue2);
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_DIGGING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(PlayerPackets$22::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.POSITION, new Position((int)packetWrapper.read(Type.INT), (short)packetWrapper.read(Type.UNSIGNED_BYTE), (int)packetWrapper.read(Type.INT)));
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$23::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.POSITION, new Position((int)packetWrapper.read(Type.INT), (short)packetWrapper.read(Type.UNSIGNED_BYTE), (int)packetWrapper.read(Type.INT)));
                packetWrapper.passthrough(Type.BYTE);
                packetWrapper.write(Type.ITEM, ItemRewriter.toServer((Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM)));
                while (0 < 3) {
                    packetWrapper.passthrough(Type.BYTE);
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.ANIMATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$24::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.read(Type.INT);
                (byte)packetWrapper.read(Type.BYTE);
                if (2 == 1) {
                    return;
                }
                packetWrapper.cancel();
                switch (2) {
                    case 104: {
                        break;
                    }
                    case 105: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        return;
                    }
                }
                final PacketWrapper create = PacketWrapper.create(11, null, packetWrapper.user());
                create.write(Type.VAR_INT, intValue);
                create.write(Type.VAR_INT, 2);
                create.write(Type.VAR_INT, 0);
                PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.ENTITY_ACTION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
                this.handler(PlayerPackets$25::lambda$registerMap$0);
                this.map(Type.INT, Type.VAR_INT);
                this.handler(PlayerPackets$25::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 1);
                if (intValue == 3 || intValue == 4) {
                    final PlayerAbilities playerAbilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                    playerAbilities.setSprinting(intValue == 3);
                    final PacketWrapper create = PacketWrapper.create(57, null, packetWrapper.user());
                    create.write(Type.BYTE, playerAbilities.getFlags());
                    create.write(Type.FLOAT, playerAbilities.isSprinting() ? (playerAbilities.getFlySpeed() * 2.0f) : playerAbilities.getFlySpeed());
                    create.write(Type.FLOAT, playerAbilities.getWalkSpeed());
                    PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, (byte)packetWrapper.read(Type.BYTE) - 1);
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.STEER_VEHICLE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$26::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final boolean booleanValue = (boolean)packetWrapper.read(Type.BOOLEAN);
                final boolean booleanValue2 = (boolean)packetWrapper.read(Type.BOOLEAN);
                if (booleanValue) {
                    final short n = 1;
                }
                if (booleanValue2) {
                    final short n2 = 2;
                }
                packetWrapper.write(Type.UNSIGNED_BYTE, 0);
                if (booleanValue2) {
                    final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    if (entityTracker.getSpectating() != entityTracker.getPlayerId()) {
                        final PacketWrapper create = PacketWrapper.create(11, null, packetWrapper.user());
                        create.write(Type.VAR_INT, entityTracker.getPlayerId());
                        create.write(Type.VAR_INT, 0);
                        create.write(Type.VAR_INT, 0);
                        final PacketWrapper create2 = PacketWrapper.create(11, null, packetWrapper.user());
                        create2.write(Type.VAR_INT, entityTracker.getPlayerId());
                        create2.write(Type.VAR_INT, 1);
                        create2.write(Type.VAR_INT, 0);
                        PacketUtil.sendToServer(create, Protocol1_7_6_10TO1_8.class);
                    }
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.UPDATE_SIGN, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(PlayerPackets$27::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.POSITION, new Position((int)packetWrapper.read(Type.INT), (short)packetWrapper.read(Type.SHORT), (int)packetWrapper.read(Type.INT)));
                while (0 < 4) {
                    packetWrapper.write(Type.COMPONENT, JsonParser.parseString(ChatUtil.legacyToJson((String)packetWrapper.read(Type.STRING))));
                    int n = 0;
                    ++n;
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLAYER_ABILITIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(PlayerPackets$28::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final PlayerAbilities playerAbilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                if (playerAbilities.isAllowFly()) {
                    playerAbilities.setFlying(((byte)packetWrapper.get(Type.BYTE, 0) & 0x2) == 0x2);
                }
                packetWrapper.set(Type.FLOAT, 0, playerAbilities.getFlySpeed());
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.TAB_COMPLETE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.create(Type.OPTIONAL_POSITION, null);
                this.handler(PlayerPackets$29::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                if (s.toLowerCase().startsWith("/stp ")) {
                    packetWrapper.cancel();
                    final String[] split = s.split(" ");
                    if (split.length <= 2) {
                        final List allWithPrefix = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).getAllWithPrefix((split.length == 1) ? "" : split[1], true);
                        final PacketWrapper create = PacketWrapper.create(58, null, packetWrapper.user());
                        create.write(Type.VAR_INT, allWithPrefix.size());
                        final Iterator<GameProfileStorage.GameProfile> iterator = allWithPrefix.iterator();
                        while (iterator.hasNext()) {
                            create.write(Type.STRING, iterator.next().name);
                        }
                        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                    }
                }
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.CLIENT_SETTINGS, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BYTE, Type.NOTHING);
                this.handler(PlayerPackets$30::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)(packetWrapper.read(Type.BOOLEAN) ? 127 : 126));
            }
        });
        protocol1_7_6_10TO1_8.registerServerbound(ServerboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.SHORT, Type.NOTHING);
                this.handler(PlayerPackets$31::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.get(Type.STRING, 0);
                switch (s.hashCode()) {
                    case -278283530: {
                        if (s.equals("MC|TrSel")) {
                            break;
                        }
                        break;
                    }
                    case -751882236: {
                        if (s.equals("MC|ItemName")) {
                            break;
                        }
                        break;
                    }
                    case -296231034: {
                        if (s.equals("MC|BEdit")) {
                            break;
                        }
                        break;
                    }
                    case -295809223: {
                        if (s.equals("MC|BSign")) {
                            break;
                        }
                        break;
                    }
                    case -294893183: {
                        if (s.equals("MC|Brand")) {}
                        break;
                    }
                }
                switch (4) {
                    case 0: {
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.read(Type.REMAINING_BYTES);
                        break;
                    }
                    case 1: {
                        packetWrapper.write(Type.STRING, new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
                        final Windows windows = (Windows)packetWrapper.user().get(Windows.class);
                        final PacketWrapper create = PacketWrapper.create(49, null, packetWrapper.user());
                        create.write(Type.UNSIGNED_BYTE, windows.anvilId);
                        create.write(Type.SHORT, 0);
                        create.write(Type.SHORT, windows.levelCost);
                        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
                        break;
                    }
                    case 2:
                    case 3: {
                        final Item item = (Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                        final CompoundTag tag = item.tag();
                        if (tag != null && tag.contains("pages")) {
                            final ListTag listTag = (ListTag)tag.get("pages");
                            while (0 < listTag.size()) {
                                final StringTag stringTag = (StringTag)listTag.get(0);
                                stringTag.setValue(ChatUtil.legacyToJson(stringTag.getValue()));
                                int n = 0;
                                ++n;
                            }
                        }
                        packetWrapper.write(Type.ITEM, item);
                        break;
                    }
                    case 4: {
                        packetWrapper.write(Type.STRING, new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
                        break;
                    }
                }
            }
        });
    }
}
