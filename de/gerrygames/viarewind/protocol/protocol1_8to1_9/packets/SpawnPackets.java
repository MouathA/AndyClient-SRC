package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;
import de.gerrygames.viarewind.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import io.netty.buffer.*;
import de.gerrygames.viarewind.utils.*;
import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.types.version.*;
import java.util.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.*;

public class SpawnPackets
{
    public static void register(final Protocol protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID, Type.NOTHING);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.INT);
                this.handler(SpawnPackets$1::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final byte byteValue = (byte)packetWrapper.get(Type.BYTE, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final Entity1_10Types.EntityType typeFromId = Entity1_10Types.getTypeFromId(byteValue, true);
                if (byteValue == 3 || byteValue == 91 || byteValue == 92 || byteValue == 93) {
                    packetWrapper.cancel();
                    return;
                }
                if (typeFromId == null) {
                    ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + byteValue);
                    packetWrapper.cancel();
                    return;
                }
                final int intValue2 = (int)packetWrapper.get(Type.INT, 0);
                int intValue3 = (int)packetWrapper.get(Type.INT, 1);
                final int intValue4 = (int)packetWrapper.get(Type.INT, 2);
                if (typeFromId.is(Entity1_10Types.EntityType.BOAT)) {
                    packetWrapper.set(Type.BYTE, 1, (byte)((byte)packetWrapper.get(Type.BYTE, 1) - 64));
                    intValue3 += 10;
                    packetWrapper.set(Type.INT, 1, intValue3);
                }
                else if (typeFromId.is(Entity1_10Types.EntityType.SHULKER_BULLET)) {
                    packetWrapper.cancel();
                    final ShulkerBulletReplacement shulkerBulletReplacement = new ShulkerBulletReplacement(intValue, packetWrapper.user());
                    shulkerBulletReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    entityTracker.addEntityReplacement(shulkerBulletReplacement);
                    return;
                }
                int intValue5 = (int)packetWrapper.get(Type.INT, 3);
                if (typeFromId.isOrHasParent(Entity1_10Types.EntityType.ARROW) && intValue5 != 0) {
                    packetWrapper.set(Type.INT, 3, --intValue5);
                }
                if (typeFromId.is(Entity1_10Types.EntityType.FALLING_BLOCK)) {
                    final Replacement replacement = ReplacementRegistry1_8to1_9.getReplacement(intValue5 & 0xFFF, intValue5 >> 12 & 0xF);
                    if (replacement != null) {
                        packetWrapper.set(Type.INT, 3, replacement.getId() | replacement.replaceData(intValue5) << 12);
                    }
                }
                if (intValue5 > 0) {
                    packetWrapper.passthrough(Type.SHORT);
                    packetWrapper.passthrough(Type.SHORT);
                    packetWrapper.passthrough(Type.SHORT);
                }
                else {
                    final short shortValue = (short)packetWrapper.read(Type.SHORT);
                    final short shortValue2 = (short)packetWrapper.read(Type.SHORT);
                    final short shortValue3 = (short)packetWrapper.read(Type.SHORT);
                    final PacketWrapper create = PacketWrapper.create(18, null, packetWrapper.user());
                    create.write(Type.VAR_INT, intValue);
                    create.write(Type.SHORT, shortValue);
                    create.write(Type.SHORT, shortValue2);
                    create.write(Type.SHORT, shortValue3);
                    PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
                }
                entityTracker.getClientEntityTypes().put(intValue, typeFromId);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.SHORT);
                this.handler(SpawnPackets$2::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.handler(SpawnPackets$3::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.LIGHTNING);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_MOB, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID, Type.NOTHING);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(SpawnPackets$4::lambda$registerMap$0);
                this.handler(SpawnPackets$4::lambda$registerMap$1);
                this.handler(SpawnPackets$4::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final List list = (List)packetWrapper.get(Types1_8.METADATA_LIST, 0);
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                final EntityReplacement entityReplacement;
                if ((entityReplacement = entityTracker.getEntityReplacement(intValue)) != null) {
                    entityReplacement.updateMetadata(list);
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
                if (shortValue == 69) {
                    packetWrapper.cancel();
                    final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    final ShulkerReplacement shulkerReplacement = new ShulkerReplacement(intValue, packetWrapper.user());
                    shulkerReplacement.setLocation(intValue2 / 32.0, intValue3 / 32.0, intValue4 / 32.0);
                    shulkerReplacement.setYawPitch(byteValue2 * 360.0f / 256.0f, byteValue * 360.0f / 256.0f);
                    shulkerReplacement.setHeadYaw(byteValue3 * 360.0f / 256.0f);
                    entityTracker.addEntityReplacement(shulkerReplacement);
                }
                else if (shortValue == -1 || shortValue == 255) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PAINTING, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID, Type.NOTHING);
                this.map(Type.STRING);
                this.map(Type.POSITION);
                this.map(Type.BYTE, Type.UNSIGNED_BYTE);
                this.handler(SpawnPackets$5::lambda$registerMap$0);
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.PAINTING);
                entityTracker.sendMetadataBuffer(intValue);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PLAYER, new PacketRemapper() {
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.handler(SpawnPackets$6::lambda$registerMap$0);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(SpawnPackets$6::lambda$registerMap$1);
                this.handler(SpawnPackets$6::lambda$registerMap$2);
            }
            
            private static void lambda$registerMap$2(final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
                final EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                entityTracker.getClientEntityTypes().put(intValue, Entity1_10Types.EntityType.PLAYER);
                entityTracker.sendMetadataBuffer(intValue);
            }
            
            private static void lambda$registerMap$1(final PacketWrapper packetWrapper) throws Exception {
                MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, (List)packetWrapper.get(Types1_8.METADATA_LIST, 0));
            }
            
            private static void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.SHORT, 0);
            }
        });
    }
}
