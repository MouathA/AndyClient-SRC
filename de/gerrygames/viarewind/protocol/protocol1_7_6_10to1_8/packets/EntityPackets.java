package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.utils.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.*;

public class EntityPackets
{
    public static void register(final Protocol1_7_6_10TO1_8 protocol1_7_6_10TO1_8) {
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(EntityPackets$1::lambda$registerMap$0);
                this.handler(EntityPackets$1::lambda$registerMap$1);
                this.handler(EntityPackets$1::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.isCancelled()) {
                    return;
                }
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final UUID playerUUID = entityTracker.getPlayerUUID((int)packetWrapper.get(Type.INT, 0));
                if (playerUUID == null) {
                    return;
                }
                Item[] playerEquipment = entityTracker.getPlayerEquipment(playerUUID);
                if (playerEquipment == null) {
                    entityTracker.setPlayerEquipment(playerUUID, playerEquipment = new Item[5]);
                }
                playerEquipment[packetWrapper.get(Type.SHORT, 0)] = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                final GameProfileStorage.GameProfile value = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(playerUUID);
                if (value != null && value.gamemode == 3) {
                    packetWrapper.cancel();
                }
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                if ((short)packetWrapper.get(Type.SHORT, 0) > 4) {
                    packetWrapper.cancel();
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                ItemRewriter.toClient(item);
                packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.USE_BED, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.handler(EntityPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final Position position = (Position)packetWrapper.read(Type.POSITION);
                packetWrapper.write(Type.INT, position.getX());
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)position.getY());
                packetWrapper.write(Type.INT, position.getZ());
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.COLLECT_ITEM, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.VAR_INT, Type.INT);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_VELOCITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.handler(EntityPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                int[] array = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final int[] array2 = array;
                while (0 < array2.length) {
                    entityTracker.removeEntity(array2[0]);
                    int n = 0;
                    ++n;
                }
                while (array.length > 127) {
                    final int[] array3 = new int[127];
                    System.arraycopy(array, 0, array3, 0, 127);
                    final int[] array4 = new int[array.length - 127];
                    System.arraycopy(array, 127, array4, 0, array4.length);
                    array = array4;
                    final PacketWrapper create = PacketWrapper.create(19, null, packetWrapper.user());
                    create.write(Types1_7_6_10.INT_ARRAY, array3);
                    PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
                }
                packetWrapper.write(Types1_7_6_10.INT_ARRAY, array);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_MOVEMENT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(EntityPackets$7::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.relMove((byte)packetWrapper.get(Type.BYTE, 0) / 32.0, (byte)packetWrapper.get(Type.BYTE, 1) / 32.0, (byte)packetWrapper.get(Type.BYTE, 2) / 32.0);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(EntityPackets$8::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.setYawPitch((byte)packetWrapper.get(Type.BYTE, 0) * 360.0f / 256.0f, (byte)packetWrapper.get(Type.BYTE, 1) * 360.0f / 256.0f);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(EntityPackets$9::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                    final byte byteValue2 = (byte)packetWrapper.get(Type.BYTE, 1);
                    final byte byteValue3 = (byte)packetWrapper.get(Type.BYTE, 2);
                    final byte byteValue4 = (byte)packetWrapper.get(Type.BYTE, 3);
                    final byte byteValue5 = (byte)packetWrapper.get(Type.BYTE, 4);
                    entityReplacement.relMove(byteValue / 32.0, byteValue2 / 32.0, byteValue3 / 32.0);
                    entityReplacement.setYawPitch(byteValue4 * 360.0f / 256.0f, byteValue5 * 360.0f / 256.0f);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN, Type.NOTHING);
                this.handler(EntityPackets$10::lambda$registerMap$0);
                this.handler(EntityPackets$10::lambda$registerMap$1);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    final int intValue = (int)packetWrapper.get(Type.INT, 1);
                    final int intValue2 = (int)packetWrapper.get(Type.INT, 2);
                    final int intValue3 = (int)packetWrapper.get(Type.INT, 3);
                    final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                    final byte byteValue2 = (byte)packetWrapper.get(Type.BYTE, 1);
                    entityReplacement.setLocation(intValue / 32.0, intValue2 / 32.0, intValue3 / 32.0);
                    entityReplacement.setYawPitch(byteValue * 360.0f / 256.0f, byteValue2 * 360.0f / 256.0f);
                }
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get((int)packetWrapper.get(Type.INT, 0)) == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
                    int intValue = (int)packetWrapper.get(Type.INT, 2);
                    intValue += 12;
                    packetWrapper.set(Type.INT, 2, intValue);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_HEAD_LOOK, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.handler(EntityPackets$11::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final EntityReplacement entityReplacement = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0));
                if (entityReplacement != null) {
                    packetWrapper.cancel();
                    entityReplacement.setHeadYaw((byte)packetWrapper.get(Type.BYTE, 0) * 360.0f / 256.0f);
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(EntityPackets$12::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.BOOLEAN, 0)) {
                    return;
                }
                ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPassenger((int)packetWrapper.get(Type.INT, 1), (int)packetWrapper.get(Type.INT, 0));
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(EntityPackets$13::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final List list = (List)packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                if (entityTracker.getClientEntityTypes().containsKey(intValue)) {
                    final EntityReplacement entityReplacement = entityTracker.getEntityReplacement(intValue);
                    if (entityReplacement != null) {
                        packetWrapper.cancel();
                        entityReplacement.updateMetadata(list);
                    }
                    else {
                        MetadataRewriter.transform((Entity1_10Types.EntityType)entityTracker.getClientEntityTypes().get(intValue), list);
                        if (list.isEmpty()) {
                            packetWrapper.cancel();
                        }
                    }
                }
                else {
                    entityTracker.addMetadataToBuffer(intValue, list);
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT, Type.SHORT);
                this.map(Type.BYTE, Type.NOTHING);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.REMOVE_ENTITY_EFFECT, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.map(Type.BYTE);
            }
        });
        protocol1_7_6_10TO1_8.registerClientbound(ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT, Type.INT);
                this.handler(EntityPackets$16::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                if (((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getEntityReplacement((int)packetWrapper.get(Type.INT, 0)) != null) {
                    packetWrapper.cancel();
                    return;
                }
                while (0 < (int)packetWrapper.passthrough(Type.INT)) {
                    packetWrapper.passthrough(Type.STRING);
                    packetWrapper.passthrough(Type.DOUBLE);
                    final int intValue = (int)packetWrapper.read(Type.VAR_INT);
                    packetWrapper.write(Type.SHORT, (short)intValue);
                    while (0 < intValue) {
                        packetWrapper.passthrough(Type.UUID);
                        packetWrapper.passthrough(Type.DOUBLE);
                        packetWrapper.passthrough(Type.BYTE);
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        });
        protocol1_7_6_10TO1_8.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
    }
}
