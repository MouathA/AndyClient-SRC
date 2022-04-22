package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.type.types.version.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.utils.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import de.gerrygames.viarewind.replacement.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.*;
import com.viaversion.viaversion.api.minecraft.*;

public class SpawnPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(SpawnPackets$1::lambda$registerMap$0);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(SpawnPackets$1::lambda$registerMap$1);
                this.handler(SpawnPackets$1::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.PLAYER);
                entityTracker.sendMetadataBuffer(intValue);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, (List)packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                packetWrapper.write(Type.STRING, uuid.toString());
                final GameProfileStorage.GameProfile value = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(uuid);
                if (value == null) {
                    packetWrapper.write(Type.STRING, "");
                    packetWrapper.write(Type.VAR_INT, 0);
                }
                else {
                    packetWrapper.write(Type.STRING, (value.name.length() > 16) ? value.name.substring(0, 16) : value.name);
                    packetWrapper.write(Type.VAR_INT, value.properties.size());
                    for (final GameProfileStorage.Property property : value.properties) {
                        packetWrapper.write(Type.STRING, property.name);
                        packetWrapper.write(Type.STRING, property.value);
                        packetWrapper.write(Type.STRING, (property.signature == null) ? "" : property.signature);
                    }
                }
                if (value != null && value.gamemode == 3) {
                    final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                    final PacketWrapper create = PacketWrapper.create(4, null, packetWrapper.user());
                    create.write(Type.INT, intValue);
                    create.write(Type.SHORT, 4);
                    create.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, value.getSkull());
                    PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                    while (0 < 4) {
                        final PacketWrapper create2 = PacketWrapper.create(4, null, packetWrapper.user());
                        create2.write(Type.INT, intValue);
                        create2.write(Type.SHORT, 0);
                        create2.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, null);
                        PacketUtil.sendPacket(create2, Protocol1_7_6_10TO1_8.class);
                        final short n = 1;
                    }
                }
                ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).addPlayer((Integer)packetWrapper.get(Type.VAR_INT, 0), uuid);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(SpawnPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                int intValue2 = (int)packetWrapper.get(Type.INT, 0);
                int intValue3 = (int)packetWrapper.get(Type.INT, 1);
                int intValue4 = (int)packetWrapper.get(Type.INT, 2);
                final byte byteValue2 = (byte)packetWrapper.get(Type.BYTE, 1);
                (byte)packetWrapper.get(Type.BYTE, 2);
                if (byteValue == 71) {
                    switch (64) {
                        case -128: {
                            intValue4 += 32;
                            break;
                        }
                        case -64: {
                            intValue2 -= 32;
                            break;
                        }
                        case 0: {
                            intValue4 -= 32;
                            break;
                        }
                        case 64: {
                            intValue2 += 32;
                            break;
                        }
                    }
                }
                else if (byteValue == 78) {
                    packetWrapper.cancel();
                    final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    final ArmorStandReplacement armorStandReplacement = new ArmorStandReplacement(intValue, packetWrapper.user());
                    armorStandReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    armorStandReplacement.setYawPitch(64 * 360.0f / 256.0f, byteValue2 * 360.0f / 256.0f);
                    armorStandReplacement.setHeadYaw(64 * 360.0f / 256.0f);
                    entityTracker.addEntityReplacement(armorStandReplacement);
                }
                else if (byteValue == 10) {
                    intValue3 += 12;
                }
                packetWrapper.set(Type.BYTE, 0, byteValue);
                packetWrapper.set(Type.INT, 0, intValue2);
                packetWrapper.set(Type.INT, 1, intValue3);
                packetWrapper.set(Type.INT, 2, intValue4);
                packetWrapper.set(Type.BYTE, 1, byteValue2);
                packetWrapper.set(Type.BYTE, 2, 64);
                final EntityTracker entityTracker2 = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final Entity1_10Types.EntityType typeFromId = Entity1_10Types.getTypeFromId(byteValue, true);
                entityTracker2.getClientEntityTypes().put(intValue, typeFromId);
                entityTracker2.sendMetadataBuffer(intValue);
                int intValue5 = (int)packetWrapper.get(Type.INT, 3);
                if (typeFromId != null && typeFromId.isOrHasParent(Entity1_10Types.EntityType.FALLING_BLOCK)) {
                    int id = intValue5 & 0xFFF;
                    int replaceData = intValue5 >> 12 & 0xF;
                    final Replacement replacement = ReplacementRegistry1_7_6_10to1_8.getReplacement(id, replaceData);
                    if (replacement != null) {
                        id = replacement.getId();
                        replaceData = replacement.replaceData(replaceData);
                    }
                    packetWrapper.set(Type.INT, 3, intValue5 = (id | replaceData << 16));
                }
                if (intValue5 > 0) {
                    packetWrapper.passthrough(Type.SHORT);
                    packetWrapper.passthrough(Type.SHORT);
                    packetWrapper.passthrough(Type.SHORT);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(SpawnPackets$3::lambda$registerMap$0);
                this.handler(SpawnPackets$3::lambda$registerMap$1);
                this.handler(SpawnPackets$3::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final List list = (List)packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                if (entityTracker.getEntityReplacement(intValue) != null) {
                    entityTracker.getEntityReplacement(intValue).updateMetadata(list);
                }
                else if (entityTracker.getClientEntityTypes().containsKey(intValue)) {
                    MetadataRewriter.transform((Entity1_10Types.EntityType)entityTracker.getClientEntityTypes().get(intValue), list);
                }
                else {
                    packetWrapper.cancel();
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.getTypeFromId(shortValue, false));
                entityTracker.sendMetadataBuffer(intValue);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final short shortValue = (short)packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                final int intValue2 = (int)packetWrapper.get(Type.INT, 0);
                final int intValue3 = (int)packetWrapper.get(Type.INT, 1);
                final int intValue4 = (int)packetWrapper.get(Type.INT, 2);
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 1);
                final byte byteValue2 = (byte)packetWrapper.get(Type.BYTE, 0);
                final byte byteValue3 = (byte)packetWrapper.get(Type.BYTE, 2);
                if (shortValue == 30) {
                    packetWrapper.cancel();
                    final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    final ArmorStandReplacement armorStandReplacement = new ArmorStandReplacement(intValue, packetWrapper.user());
                    armorStandReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    armorStandReplacement.setYawPitch(byteValue2 * 360.0f / 256.0f, byteValue * 360.0f / 256.0f);
                    armorStandReplacement.setHeadYaw(byteValue3 * 360.0f / 256.0f);
                    entityTracker.addEntityReplacement(armorStandReplacement);
                }
                else if (shortValue == 68) {
                    packetWrapper.cancel();
                    final EntityTracker entityTracker2 = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    final GuardianReplacement guardianReplacement = new GuardianReplacement(intValue, packetWrapper.user());
                    guardianReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    guardianReplacement.setYawPitch(byteValue2 * 360.0f / 256.0f, byteValue * 360.0f / 256.0f);
                    guardianReplacement.setHeadYaw(byteValue3 * 360.0f / 256.0f);
                    entityTracker2.addEntityReplacement(guardianReplacement);
                }
                else if (shortValue == 67) {
                    packetWrapper.cancel();
                    final EntityTracker entityTracker3 = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    final EndermiteReplacement endermiteReplacement = new EndermiteReplacement(intValue, packetWrapper.user());
                    endermiteReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    endermiteReplacement.setYawPitch(byteValue2 * 360.0f / 256.0f, byteValue * 360.0f / 256.0f);
                    endermiteReplacement.setHeadYaw(byteValue3 * 360.0f / 256.0f);
                    entityTracker3.addEntityReplacement(endermiteReplacement);
                }
                else if (shortValue == 101 || shortValue == 255 || shortValue == -1) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(SpawnPackets$4::lambda$registerMap$0);
                this.map(Type.UNSIGNED_BYTE, Type.INT);
                this.handler(SpawnPackets$4::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.PAINTING);
                entityTracker.sendMetadataBuffer(intValue);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.INT, position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.SHORT);
                this.handler(SpawnPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(SpawnPackets$6::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.LIGHTNING);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
    }
}
